package com.wallhack.gui;

import com.wallhack.WallHackMod;
import com.wallhack.config.EntityConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

public class WallHackScreen extends Screen {

    private static final int ENTRY_HEIGHT   = 20;
    private static final int CATEGORY_HEIGHT = 24;

    private final EntityConfig config;
    private final Map<String, Boolean> categoryExpanded = new HashMap<>();
    private final List<ListEntry> entries = new ArrayList<>();

    private int scrollOffset = 0;
    private int maxScroll    = 0;

    public WallHackScreen() {
        super(Text.literal("WallHack - Configuration"));
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

        // Enable All
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Tout Activer"), btn -> {
            config.enableAll();
        }).dimensions(this.width / 2 - 210, buttonY, buttonWidth, buttonHeight).build());

        // Disable All
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Tout Desactiver"), btn -> {
            config.disableAll();
        }).dimensions(this.width / 2 - 105, buttonY, buttonWidth, buttonHeight).build());

        // Toggle WallHack
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(WallHackMod.getInstance().isWallhackEnabled() ? "WH: ON" : "WH: OFF"),
                btn -> {
                    WallHackMod.getInstance().toggleWallhack();
                    btn.setMessage(Text.literal(WallHackMod.getInstance().isWallhackEnabled() ? "WH: ON" : "WH: OFF"));
                }
        ).dimensions(this.width / 2, buttonY, buttonWidth, buttonHeight).build());

        // Close
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Fermer"), btn -> {
            this.close();
        }).dimensions(this.width / 2 + 110, buttonY, buttonWidth, buttonHeight).build());
    }

    private void buildEntryList() {
        entries.clear();
        for (Map.Entry<String, List<Identifier>> catEntry : config.getCategories().entrySet()) {
            String category = catEntry.getKey();
            List<Identifier> entities = catEntry.getValue();
            entries.add(new ListEntry(category, null, true));
            if (categoryExpanded.getOrDefault(category, false)) {
                for (Identifier entity : entities) {
                    entries.add(new ListEntry(category, entity, false));
                }
            }
        }

        int totalHeight  = entries.size() * ENTRY_HEIGHT;
        int visibleHeight = this.height - 80;
        maxScroll = Math.max(0, totalHeight - visibleHeight);
        scrollOffset = Math.min(scrollOffset, maxScroll);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int listX = 20;
        int listWidth  = this.width - 40;
        int listY = 30;
        int listHeight = this.height - 70;

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);

        // Scissor
        context.enableScissor(listX, listY, listX + listWidth, listY + listHeight);

        int currentY = listY - scrollOffset;
        for (ListEntry entry : entries) {
            int entryHeight = entry.isCategory ? CATEGORY_HEIGHT : ENTRY_HEIGHT;

            if (currentY + entryHeight > listY && currentY < listY + listHeight) {
                if (entry.isCategory) {
                    boolean expanded = categoryExpanded.getOrDefault(entry.category, false);
                    String arrow = expanded ? "▼ " : "▶ ";
                    int bgColor = 0xFF333355;
                    context.fill(listX, currentY, listX + listWidth, currentY + entryHeight, bgColor);
                    context.drawTextWithShadow(this.textRenderer, arrow + entry.category + "  [Toggle]", listX + 4, currentY + 7, 0xFFFFAA);
                } else {
                    boolean enabled = config.isEntityVisible(entry.entity);
                    int color   = config.getEntityColor(entry.entity);
                    int bgColor = enabled ? 0xAA223322 : 0xAA332222;
                    context.fill(listX + 10, currentY, listX + listWidth, currentY + entryHeight, bgColor);

                    // Checkbox
                    int checkboxX = listX + 14;
                    int checkboxY = currentY + 4;
                    context.fill(checkboxX, checkboxY, checkboxX + 12, checkboxY + 12, enabled ? 0xFF44FF44 : 0xFF888888);

                    // Color box
                    int colorBoxX = listX + 32;
                    context.fill(colorBoxX, checkboxY, colorBoxX + 12, checkboxY + 12, 0xFF000000 | color);

                    // Name
                    context.drawTextWithShadow(this.textRenderer,
                            config.getEntityDisplayName(entry.entity),
                            colorBoxX + 16, currentY + 6, enabled ? 0xFFFFFF : 0x888888);
                }
            }
            currentY += entryHeight;
        }

        context.disableScissor();

        // Scrollbar
        if (maxScroll > 0) {
            int scrollbarX = this.width - 24;
            int scrollbarHeight = listHeight;
            int thumbHeight = Math.max(20, scrollbarHeight * listHeight / (listHeight + maxScroll));
            int thumbY = listY + (scrollOffset * (scrollbarHeight - thumbHeight)) / maxScroll;
            context.fill(scrollbarX, listY, scrollbarX + 6, listY + scrollbarHeight, 0x44FFFFFF);
            context.fill(scrollbarX, thumbY, scrollbarX + 6, thumbY + thumbHeight, 0xAAFFFFFF);
        }

        context.drawTextWithShadow(this.textRenderer,
                "Cliquez sur une entite pour toggle | Molette pour defiler",
                listX, this.height - 50, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer,
                "Appuyez sur V en jeu pour activer/desactiver le WallHack",
                listX, this.height - 40, 0xAAAAAA);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int listX = 20;
        int listWidth  = this.width - 40;
        int listY = 30;
        int listHeight = this.height - 70;

        if (mouseX >= listX && mouseX <= listX + listWidth && mouseY >= listY && mouseY <= listY + listHeight) {
            int currentY = listY - scrollOffset;
            for (ListEntry entry : entries) {
                int entryHeight = entry.isCategory ? CATEGORY_HEIGHT : ENTRY_HEIGHT;
                if (mouseY >= currentY && mouseY < currentY + entryHeight) {
                    if (entry.isCategory) {
                        boolean current = categoryExpanded.getOrDefault(entry.category, false);
                        categoryExpanded.put(entry.category, !current);
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

    private record ListEntry(String category, Identifier entity, boolean isCategory) {}
}
