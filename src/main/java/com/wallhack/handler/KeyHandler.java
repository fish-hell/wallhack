package com.wallhack.handler;

import com.wallhack.WallHackMod;
import com.wallhack.gui.WallHackScreen;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {
    public static KeyMapping OPEN_MENU;
    public static KeyMapping TOGGLE_WALLHACK;

    public static void register() {
        OPEN_MENU = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.wallhack.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "key.categories.wallhack"
        ));
        TOGGLE_WALLHACK = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.wallhack.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.categories.wallhack"
        ));
    }

    public static void onClientTick(Minecraft client) {
        if (OPEN_MENU.consumeClick()) {
            if (client.screen == null) client.setScreen(new WallHackScreen());
        }
        if (TOGGLE_WALLHACK.consumeClick()) {
            WallHackMod mod = WallHackMod.getInstance();
            mod.toggleWallhack();
            String status = mod.isWallhackEnabled() ? "\u00a7aActive" : "\u00a7cDesactive";
            if (client.player != null)
                client.player.sendSystemMessage(Component.literal("\u00a76[WallHack] \u00a7f" + status));
        }
    }
}
