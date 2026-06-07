package com.wallhack.config;

import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import java.util.*;

public class EntityConfig {
    public static final String CATEGORY_HOSTILE = "Hostiles";
    public static final String CATEGORY_PASSIVE = "Passifs";
    public static final String CATEGORY_NEUTRAL = "Neutres";
    public static final String CATEGORY_BOSS    = "Boss";
    public static final String CATEGORY_PLAYER  = "Joueurs";
    public static final String CATEGORY_OTHER   = "Autres";

    private final Map<ResourceLocation, Boolean> entityVisibility = new LinkedHashMap<>();
    private final Map<ResourceLocation, Integer> entityColors = new HashMap<>();
    private final Map<String, List<ResourceLocation>> categories = new LinkedHashMap<>();

    public EntityConfig() {
        categories.put(CATEGORY_BOSS,    new ArrayList<>());
        categories.put(CATEGORY_PLAYER,  new ArrayList<>());
        categories.put(CATEGORY_HOSTILE, new ArrayList<>());
        categories.put(CATEGORY_NEUTRAL, new ArrayList<>());
        categories.put(CATEGORY_PASSIVE, new ArrayList<>());
        categories.put(CATEGORY_OTHER,   new ArrayList<>());
        addEntity(CATEGORY_PLAYER,  "minecraft:player",           0xFF0000);
        addEntity(CATEGORY_BOSS,    "minecraft:ender_dragon",     0xAA00FF);
        addEntity(CATEGORY_BOSS,    "minecraft:wither",           0x333333);
        addEntity(CATEGORY_BOSS,    "minecraft:elder_guardian",   0x8888FF);
        addEntity(CATEGORY_BOSS,    "minecraft:warden",           0x00CCAA);
        addEntity(CATEGORY_HOSTILE, "minecraft:zombie",           0x00AA00);
        addEntity(CATEGORY_HOSTILE, "minecraft:skeleton",         0xFFFFFF);
        addEntity(CATEGORY_HOSTILE, "minecraft:creeper",          0x00FF00);
        addEntity(CATEGORY_HOSTILE, "minecraft:spider",           0x992200);
        addEntity(CATEGORY_HOSTILE, "minecraft:cave_spider",      0x550000);
        addEntity(CATEGORY_HOSTILE, "minecraft:enderman",         0x220033);
        addEntity(CATEGORY_HOSTILE, "minecraft:witch",            0x553300);
        addEntity(CATEGORY_HOSTILE, "minecraft:guardian",         0x00AAAA);
        addEntity(CATEGORY_HOSTILE, "minecraft:shulker",          0xAA88FF);
        addEntity(CATEGORY_HOSTILE, "minecraft:husk",             0xCCAA44);
        addEntity(CATEGORY_HOSTILE, "minecraft:stray",            0xAAFFFF);
        addEntity(CATEGORY_HOSTILE, "minecraft:phantom",          0x8800AA);
        addEntity(CATEGORY_HOSTILE, "minecraft:drowned",          0x004488);
        addEntity(CATEGORY_HOSTILE, "minecraft:pillager",         0x888888);
        addEntity(CATEGORY_HOSTILE, "minecraft:ravager",          0x885500);
        addEntity(CATEGORY_HOSTILE, "minecraft:vindicator",       0xCCCCCC);
        addEntity(CATEGORY_HOSTILE, "minecraft:hoglin",           0xFF8844);
        addEntity(CATEGORY_HOSTILE, "minecraft:piglin_brute",     0xFF6600);
        addEntity(CATEGORY_HOSTILE, "minecraft:wither_skeleton",  0x111111);
        addEntity(CATEGORY_HOSTILE, "minecraft:zombie_villager",  0x336600);
        addEntity(CATEGORY_HOSTILE, "minecraft:breeze",           0x44AAFF);
        addEntity(CATEGORY_NEUTRAL, "minecraft:wolf",             0xDDDDDD);
        addEntity(CATEGORY_NEUTRAL, "minecraft:bee",              0xFFCC00);
        addEntity(CATEGORY_NEUTRAL, "minecraft:polar_bear",       0xFFFFFF);
        addEntity(CATEGORY_NEUTRAL, "minecraft:iron_golem",       0xBBBBBB);
        addEntity(CATEGORY_NEUTRAL, "minecraft:dolphin",          0x558899);
        addEntity(CATEGORY_NEUTRAL, "minecraft:panda",            0xEEEEEE);
        addEntity(CATEGORY_NEUTRAL, "minecraft:piglin",           0xFFAAAA);
        addEntity(CATEGORY_NEUTRAL, "minecraft:zombified_piglin", 0x886644);
        addEntity(CATEGORY_PASSIVE, "minecraft:pig",              0xFFAABB);
        addEntity(CATEGORY_PASSIVE, "minecraft:cow",              0x886644);
        addEntity(CATEGORY_PASSIVE, "minecraft:sheep",            0xEEEEEE);
        addEntity(CATEGORY_PASSIVE, "minecraft:chicken",          0xFFFFCC);
        addEntity(CATEGORY_PASSIVE, "minecraft:horse",            0xAA8855);
        addEntity(CATEGORY_PASSIVE, "minecraft:rabbit",           0xDDAA88);
        addEntity(CATEGORY_PASSIVE, "minecraft:cat",              0xFF8844);
        addEntity(CATEGORY_PASSIVE, "minecraft:villager",         0xFFCCAA);
        addEntity(CATEGORY_PASSIVE, "minecraft:allay",            0x88CCFF);
        addEntity(CATEGORY_PASSIVE, "minecraft:sniffer",          0x884422);
        addEntity(CATEGORY_OTHER,   "minecraft:armor_stand",      0xAAAAAA);
        addEntity(CATEGORY_OTHER,   "minecraft:item_frame",       0xAA8855);
        addEntity(CATEGORY_OTHER,   "minecraft:minecart",         0xAAAAAA);
        addEntity(CATEGORY_OTHER,   "minecraft:end_crystal",      0xFF00FF);
        addEntity(CATEGORY_OTHER,   "minecraft:item",             0xFFFF00);
    }

    private void addEntity(String category, String entityId, int color) {
        ResourceLocation id = ResourceLocation.parse(entityId);
        entityVisibility.put(id, true);
        entityColors.put(id, color);
        categories.get(category).add(id);
    }

    public boolean isEntityVisible(ResourceLocation entityId) { return entityVisibility.getOrDefault(entityId, false); }
    public boolean isEntityVisible(EntityType<?> type) { return isEntityVisible(BuiltInRegistries.ENTITY_TYPE.getKey(type)); }
    public void toggleEntity(ResourceLocation entityId) { entityVisibility.put(entityId, !isEntityVisible(entityId)); }
    public int getEntityColor(ResourceLocation entityId) { return entityColors.getOrDefault(entityId, 0xFFFFFF); }
    public int getEntityColor(EntityType<?> type) { return getEntityColor(BuiltInRegistries.ENTITY_TYPE.getKey(type)); }
    public Map<String, List<ResourceLocation>> getCategories() { return categories; }
    public void enableAll() { entityVisibility.keySet().forEach(id -> entityVisibility.put(id, true)); }
    public void disableAll() { entityVisibility.keySet().forEach(id -> entityVisibility.put(id, false)); }
    public void toggleCategory(String category) {
        List<ResourceLocation> entities = categories.get(category);
        if (entities == null || entities.isEmpty()) return;
        boolean anyEnabled = entities.stream().anyMatch(this::isEntityVisible);
        entities.forEach(id -> entityVisibility.put(id, !anyEnabled));
    }
    public String getEntityDisplayName(ResourceLocation id) {
        StringBuilder sb = new StringBuilder();
        for (String w : id.getPath().split("_"))
            if (!w.isEmpty()) sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
        return sb.toString().trim();
    }
}
