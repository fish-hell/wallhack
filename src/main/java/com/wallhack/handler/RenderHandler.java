package com.wallhack.handler;

import com.mojang.blaze3d.vertex.*;
import com.wallhack.WallHackMod;
import com.wallhack.config.EntityConfig;
import net.fabricmc.fabric.api.client.rendering.v1.LevelRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class RenderHandler {
    public static void onRenderLevel(LevelRenderContext context) {
        WallHackMod mod = WallHackMod.getInstance();
        if (!mod.isWallhackEnabled()) return;

        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        EntityConfig config = mod.getEntityConfig();
        Vec3 cameraPos = context.camera().getPosition();
        PoseStack poseStack = context.poseStack();

        for (Entity entity : client.level.entitiesForRendering()) {
            if (entity == client.player) continue;
            var entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            if (entityId == null) continue;
            if (entity instanceof Player && !config.isEntityVisible(net.minecraft.resources.ResourceLocation.parse("minecraft:player"))) continue;
            if (!config.isEntityVisible(entityId)) continue;

            int color = config.getEntityColor(entityId);
            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8)  & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;

            AABB box = entity.getBoundingBox().move(-cameraPos.x, -cameraPos.y, -cameraPos.z);
            renderBox(poseStack, box, r, g, b, 0.2f);
            renderBoxOutline(poseStack, box, r, g, b, 1.0f);
        }
    }

    private static void renderBox(PoseStack poseStack, AABB box, float r, float g, float b, float a) {
        Matrix4f m = poseStack.last().pose();
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buf = tess.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        float x1=(float)box.minX,y1=(float)box.minY,z1=(float)box.minZ;
        float x2=(float)box.maxX,y2=(float)box.maxY,z2=(float)box.maxZ;
        buf.addVertex(m,x1,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y1,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y1,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y1,z2).setColor(r,g,b,a);
        buf.addVertex(m,x1,y2,z1).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z2).setColor(r,g,b,a);
        buf.addVertex(m,x2,y2,z2).setColor(r,g,b,a); buf.addVertex(m,x2,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x1,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y2,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y1,z1).setColor(r,g,b,a);
        buf.addVertex(m,x1,y1,z2).setColor(r,g,b,a); buf.addVertex(m,x2,y1,z2).setColor(r,g,b,a);
        buf.addVertex(m,x2,y2,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z2).setColor(r,g,b,a);
        buf.addVertex(m,x1,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x1,y1,z2).setColor(r,g,b,a);
        buf.addVertex(m,x1,y2,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y2,z2).setColor(r,g,b,a); buf.addVertex(m,x2,y1,z2).setColor(r,g,b,a);
        BufferUploader.drawWithShader(buf.buildOrThrow());
    }

    private static void renderBoxOutline(PoseStack poseStack, AABB box, float r, float g, float b, float a) {
        Matrix4f m = poseStack.last().pose();
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buf = tess.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        float x1=(float)box.minX,y1=(float)box.minY,z1=(float)box.minZ;
        float x2=(float)box.maxX,y2=(float)box.maxY,z2=(float)box.maxZ;
        buf.addVertex(m,x1,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y1,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y1,z2).setColor(r,g,b,a);
        buf.addVertex(m,x2,y1,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y1,z2).setColor(r,g,b,a);
        buf.addVertex(m,x1,y1,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y1,z1).setColor(r,g,b,a);
        buf.addVertex(m,x1,y2,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y2,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y2,z2).setColor(r,g,b,a);
        buf.addVertex(m,x2,y2,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z2).setColor(r,g,b,a);
        buf.addVertex(m,x1,y2,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x1,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y1,z1).setColor(r,g,b,a); buf.addVertex(m,x2,y2,z1).setColor(r,g,b,a);
        buf.addVertex(m,x2,y1,z2).setColor(r,g,b,a); buf.addVertex(m,x2,y2,z2).setColor(r,g,b,a);
        buf.addVertex(m,x1,y1,z2).setColor(r,g,b,a); buf.addVertex(m,x1,y2,z2).setColor(r,g,b,a);
        BufferUploader.drawWithShader(buf.buildOrThrow());
    }
}
