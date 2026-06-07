package com.wallhack.gui;

import com.wallhack.WallHackMod;
import com.wallhack.config.EntityConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.*;

public class WallHackScreen extends Screen {
    private static final int ENTRY_HEIGHT    = 20;
    private static final int CATEGORY_HEIGHT = 24;

    private final EntityConfig config;
    private final Map<String, Boolean> categoryExpanded = new HashMap<>();
    private final List<ListEntry> entries = new ArrayList<>();
    private int scrollOffset = 0;
    private int maxScroll    = 0;

    public WallHackScreen() {
        super(Component.literal("WallHack - Configuration"));
        this.config = WallHackMod.getInstance().getEntityConfig();
        for (String cat : config.getCategories().keySet())
            categoryExpanded.put(cat, false);
    }

    @Override
    protected void init() {
        super.init();
        buildEntryList();
        int bw = 100, bh = 20, by = this.height - 30;
        addRenderableWidget(Button.builder(Component.literal("Tout Activer"), b -> config.enableAll())
                .bounds(this.width/2-210, by, bw, bh).build());
        addRenderableWidget(Button.builder(Component.literal("Tout Desactiver"), b -> config.disableAll())
                .bounds(this.width/2-105, by, bw, bh).build());
        addRenderableWidget(Button.builder(
                Component.literal(WallHackMod.getInstance().isWallhackEnabled() ? "WH: ON" : "WH: OFF"),
                b -> { WallHackMod.getInstance().toggleWallhack();
                       b.setMessage(Component.literal(WallHackMod.getInstance().isWallhackEnabled() ? "WH: ON" : "WH: OFF")); })
                .bounds(this.width/2, by, bw, bh).build());
        addRenderableWidget(Button.builder(Component.literal("Fermer"), b -> this.onClose())
                .bounds(this.width/2+110, by, bw, bh).build());
    }

    private void buildEntryList() {
        entries.clear();
        for (Map.Entry<String, List<ResourceLocation>> ce : config.getCategories().entrySet()) {
            String cat = ce.getKey();
            entries.add(new ListEntry(cat, null, true));
            if (categoryExpanded.getOrDefault(cat, false))
                for (ResourceLocation e : ce.getValue())
                    entries.add(new ListEntry(cat, e, false));
        }
        int total = entries.size() * ENTRY_HEIGHT;
        int visible = this.height - 80;
        maxScroll = Math.max(0, total - visible);
        scrollOffset = Math.min(scrollOffset, maxScroll);
    }

    @Override
    public void render(GuiGraphics ctx, int mx, int my, float delta) {
        super.render(ctx, mx, my, delta);
        int lx=20, ly=30, lw=this.width-40, lh=this.height-70;
        ctx.drawCenteredString(this.font, this.title, this.width/2, 10, 0xFFFFFF);
        ctx.enableScissor(lx, ly, lx+lw, ly+lh);
        int cy = ly - scrollOffset;
        for (ListEntry entry : entries) {
            int eh = entry.isCategory ? CATEGORY_HEIGHT : ENTRY_HEIGHT;
            if (cy+eh > ly && cy < ly+lh) {
                if (entry.isCategory) {
                    boolean exp = categoryExpanded.getOrDefault(entry.category, false);
                    ctx.fill(lx, cy, lx+lw, cy+eh, 0xFF333355);
                    ctx.drawString(this.font, (exp?"\u25bc ":"\u25b6 ")+entry.category, lx+4, cy+7, 0xFFFFAA);
                } else {
                    boolean en = config.isEntityVisible(entry.entity);
                    int color = config.getEntityColor(entry.entity);
                    ctx.fill(lx+10, cy, lx+lw, cy+eh, en ? 0xAA223322 : 0xAA332222);
                    ctx.fill(lx+14, cy+4, lx+26, cy+16, en ? 0xFF44FF44 : 0xFF888888);
                    ctx.fill(lx+32, cy+4, lx+44, cy+16, 0xFF000000|color);
                    ctx.drawString(this.font, config.getEntityDisplayName(entry.entity), lx+48, cy+6, en ? 0xFFFFFF : 0x888888);
                }
            }
            cy += eh;
        }
        ctx.disableScissor();
        if (maxScroll > 0) {
            int sx = this.width-24;
            int th = Math.max(20, lh*lh/(lh+maxScroll));
            int ty = ly + (scrollOffset*(lh-th))/maxScroll;
            ctx.fill(sx, ly, sx+6, ly+lh, 0x44FFFFFF);
            ctx.fill(sx, ty, sx+6, ty+th, 0xAAFFFFFF);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        int lx=20, ly=30, lw=this.width-40, lh=this.height-70;
        if (mx>=lx && mx<=lx+lw && my>=ly && my<=ly+lh) {
            int cy = ly - scrollOffset;
            for (ListEntry entry : entries) {
                int eh = entry.isCategory ? CATEGORY_HEIGHT : ENTRY_HEIGHT;
                if (my>=cy && my<cy+eh) {
                    if (entry.isCategory) {
                        categoryExpanded.put(entry.category, !categoryExpanded.getOrDefault(entry.category, false));
                        buildEntryList();
                    } else {
                        config.toggleEntity(entry.entity);
                    }
                    return true;
                }
                cy += eh;
            }
        }
        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double ha, double va) {
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset-(int)(va*ENTRY_HEIGHT)));
        return true;
    }

    private record ListEntry(String category, ResourceLocation entity, boolean isCategory) {}
}
