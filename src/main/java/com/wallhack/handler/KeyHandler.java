package com.wallhack.handler;

import com.wallhack.WallHackMod;
import com.wallhack.gui.WallHackScreen;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {

    public static KeyMapping OPEN_MENU;
    public static KeyMapping TOGGLE_WALLHACK;

    public static void register() {
        OPEN_MENU = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.wallhack.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "key.categories.wallhack"
        ));
        TOGGLE_WALLHACK = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.wallhack.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.categories.wallhack"
        ));
    }

    public static void onClientTick(Minecraft client) {
        if (OPEN_MENU.consumeClick()) {
            if (client.screen == null) {
                client.setScreen(new WallHackScreen());
            }
        }
        if (TOGGLE_WALLHACK.consumeClick()) {
            WallHackMod mod = WallHackMod.getInstance();
            mod.toggleWallhack();
            String status = mod.isWallhackEnabled() ? "§aActive" : "§cDesactive";
            if (client.player != null) {
                client.player.displayClientMessage(Component.literal("§6[WallHack] §f" + status), true);
            }
        }
    }
}
