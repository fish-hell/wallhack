package com.wallhack.handler;

import com.wallhack.WallHackMod;
import com.wallhack.gui.WallHackScreen;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {

    public static KeyBinding OPEN_MENU;
    public static KeyBinding TOGGLE_WALLHACK;

    public static void register() {
        OPEN_MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wallhack.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "key.categories.wallhack"
        ));
        TOGGLE_WALLHACK = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wallhack.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.categories.wallhack"
        ));
    }

    public static void onClientTick(MinecraftClient client) {
        if (OPEN_MENU.wasPressed()) {
            if (client.currentScreen == null) {
                client.setScreen(new WallHackScreen());
            }
        }
        if (TOGGLE_WALLHACK.wasPressed()) {
            WallHackMod mod = WallHackMod.getInstance();
            mod.toggleWallhack();
            String status = mod.isWallhackEnabled() ? "§aActive" : "§cDesactive";
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§6[WallHack] §f" + status), true);
            }
        }
    }
}
