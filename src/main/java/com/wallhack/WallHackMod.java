package com.wallhack;

import com.wallhack.config.EntityConfig;
import com.wallhack.handler.KeyHandler;
import com.wallhack.handler.RenderHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WallHackMod implements ClientModInitializer {

    public static final String MODID = "wallhack";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static WallHackMod instance;

    private EntityConfig entityConfig;
    private boolean wallhackEnabled = false;

    @Override
    public void onInitializeClient() {
        instance = this;
        entityConfig = new EntityConfig();
        LOGGER.info("WallHack Mod - Client Setup");

        KeyHandler.register();
        ClientTickEvents.END_CLIENT_TICK.register(KeyHandler::onClientTick);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(RenderHandler::onRenderLevel);
    }

    public static WallHackMod getInstance() {
        return instance;
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    public boolean isWallhackEnabled() {
        return wallhackEnabled;
    }

    public void setWallhackEnabled(boolean enabled) {
        this.wallhackEnabled = enabled;
    }

    public void toggleWallhack() {
        this.wallhackEnabled = !this.wallhackEnabled;
    }
}
