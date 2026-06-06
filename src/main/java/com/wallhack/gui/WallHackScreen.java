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
        for (String cat : config.getCategories().keySet()) {
            categoryExpanded.put(cat, false);
        }
    }

    @Override
    protected void init() {
        super.init();
        buildEntryList();

        int buttonWidth  = 100;
        int buttonHeight = 20;
        int buttonY      = this.height - 30;

        this.addRenderableWidget(Button.builder(Component.literal("Tout Activer"), btn -> config.enableAll())
                .bounds(this.width / 2 - 210, buttonY, buttonWidth, buttonHeight).build());
        this.addRenderableWidget(Button.builder(Component.literal("Tout Desactiver"), btn -> config.disableAll())
                .bounds(this.width / 2 - 105, buttonY, buttonWidth, buttonHeight).build());
        this.addRenderableWidget(Button.builder(
                Component.literal(WallHackMod.getInstance().isWallhackEnabled() ? "WH: ON" : "WH: OFF"),
                btn -> {
                    WallHackMod.getInstance().toggleWallhack();
                    btn.setMessage(Component.literal(WallHackMod.getInstance().isWallhackEnabled() ? "WH: ON" : "WH: OFF"));
                }).bounds(this.width / 2, buttonY, buttonWidth, buttonHeight).build());
        this.addRenderableWidget(Button.builder(Component.literal("Fermer"), btn -> this.onClose())
                .bounds(this.width / 2 + 110, buttonY, buttonWidth, buttonHeight).build());
    }

    private void buildEntryList() {
        entries.clear();
        for (Map.Entry<String, List<ResourceLocation>> catEntry : config.getCategories().entrySet()) {
            String category = catEntry.getKey();
            List<ResourceLocation> entities = catEntry.getValue();
            entries.add(new ListEntry(category, null, true));
            if (categoryExpanded.getOrDefault(category, false)) {
                for (ResourceLocation entity : entities) {
                    entries.add(new ListEntry(category, entity, false));
                }
            }
        }
        int totalHeight   = entries.size() * ENTRY_HEIGHT;
        int visibleHeight = this.height - 80;
        maxScroll    = Math.max(0, totalHeight - visibleHeight);
        scrollOffset = Math.min(scrollOffset, maxScroll);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int listX = 20, listY = 30;
        int listWidth  = this.width - 40;
        int listHeight = this.height - 70;

        context.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);
        context.enableScissor(listX, listY, listX + listWidth, listY + listHeight);

        int currentY = listY - scrollOffset;
        for (ListEntry entry : entries) {
            int entryHeight = entry.isCategory ? CATEGORY_HEIGHT : ENTRY_HEIGHT;
            if (currentY + entryHeight > listY && currentY < listY + listHeight) {
                if (entry.isCategory) {
                    boolean expanded = categoryExpanded.getOrDefault(entry.category, false);
                    context.fill(listX, currentY, listX + listWidth, currentY + entryHeight, 0xFF333355);
                    context.drawString(this.font, (expanded ? "▼ " : "▶ ") + entry.category, listX + 4, currentY + 7, 0xFFFFAA);
                } else {
                    boolean enabled = config.isEntityVisible(entry.entity);
                    int color = config.getEntityColor(entry.entity);
                    context.fill(listX + 10, currentY, listX + listWidth, currentY + entryHeight, enabled ? 0xAA223322 : 0xAA332222);
                    int checkboxX = listX + 14, checkboxY = currentY + 4;
                    context.fill(checkboxX, checkboxY, checkboxX + 12, checkboxY + 12, enabled ? 0xFF44FF44 : 0xFF888888);
                    context.fill(listX + 32, checkboxY, listX + 44, checkboxY + 12, 0xFF000000 | color);
                    context.drawString(this.font, config.getEntityDisplayName(entry.entity), listX + 48, currentY + 6, enabled ? 0xFFFFFF : 0x888888);
                }
            }
            currentY += entryHeight;
        }

        context.disableScissor();

        if (maxScroll > 0) {
            int scrollbarX = this.width - 24;
            int thumbHeight = Math.max(20, listHeight * listHeight / (listHeight + maxScroll));
            int thumbY = listY + (scrollOffset * (listHeight - thumbHeight)) / maxScroll;
            context.fill(scrollbarX, listY, scrollbarX + 6, listY + listHeight, 0x44FFFFFF);
            context.fill(scrollbarX, thumbY, scrollbarX + 6, thumbY + thumbHeight, 0xAAFFFFFF);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int listX = 20, listY = 30;
        int listWidth = this.width - 40, listHeight = this.height - 70;
        if (mouseX >= listX && mouseX <= listX + listWidth && mouseY >= listY && mouseY <= listY + listHeight) {
            int currentY = listY - scrollOffset;
            for (ListEntry entry : entries) {
                int entryHeight = entry.isCategory ? CATEGORY_HEIGHT : ENTRY_HEIGHT;
                if (mouseY >= currentY && mouseY < currentY + entryHeight) {
                    if (entry.isCategory) {
                        categoryExpanded.put(entry.category, !categoryExpanded.getOrDefault(entry.category, false));
                        buildEntryList();
                    } else {
                        config.toggleEntity(entry.entity);
                    }
                    return true;
                }
                currentY += entryHeight;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset - (int)(verticalAmount * ENTRY_HEIGHT)));
        return true;
    }

    private record ListEntry(String category, ResourceLocation entity, boolean isCategory) {}
}
