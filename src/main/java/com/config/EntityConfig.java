package com.wallhack.config;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

import java.util.*;

public class EntityConfig {

    public static final String CATEGORY_HOSTILE = "Monstres Hostiles";
    public static final String CATEGORY_PASSIVE = "Animaux Passifs";
    public static final String CATEGORY_NEUTRAL = "Creatures Neutres";
    public static final String CATEGORY_BOSS    = "CATEGORY_BOSS";
    public static final String CATEGORY_PLAYER  = "Joueurs";
    public static final String CATEGORY_OTHER   = "Autres";

    private final Map<Identifier, Boolean> entityVisibility = new LinkedHashMap<>();
    private final Map<Identifier, Integer> entityColors     = new HashMap<>();
    private final Map<String, List<Identifier>> categories  = new LinkedHashMap<>();

    public EntityConfig() {
        initializeCategories();
        initializeEntities();
    }

    private void initializeCategories() {
        categories.put(CATEGORY_BOSS,    new ArrayList<>());
        categories.put(CATEGORY_PLAYER,  new ArrayList<>());
        categories.put(CATEGORY_HOSTILE, new ArrayList<>());
        categories.put(CATEGORY_NEUTRAL, new ArrayList<>());
        categories.put(CATEGORY_PASSIVE, new ArrayList<>());
        categories.put(CATEGORY_OTHER,   new ArrayList<>());
    }

    private void initializeEntities() {
        // Players
        addEntity(CATEGORY_PLAYER, "minecraft:player", 0xFF0000);

        // Bosses
        addEntity(CATEGORY_BOSS, "minecraft:ender_dragon", 0xAA00FF);
        addEntity(CATEGORY_BOSS, "minecraft:wither",       0x333333);
        addEntity(CATEGORY_BOSS, "minecraft:elder_guardian", 0x8888FF);
        addEntity(CATEGORY_BOSS, "minecraft:warden",       0x00CCAA);

        // Hostile
        addEntity(CATEGORY_HOSTILE, "minecraft:zombie",         0x00AA00);
        addEntity(CATEGORY_HOSTILE, "minecraft:skeleton",       0xFFFFFF);
        addEntity(CATEGORY_HOSTILE, "minecraft:creeper",        0x00FF00);
        addEntity(CATEGORY_HOSTILE, "minecraft:spider",         0x992200);
        addEntity(CATEGORY_HOSTILE, "minecraft:cave_spider",    0x550000);
        addEntity(CATEGORY_HOSTILE, "minecraft:enderman",       0x220033);
        addEntity(CATEGORY_HOSTILE, "minecraft:slime",          0x44FF44);
        addEntity(CATEGORY_HOSTILE, "minecraft:magma_cube",     0xFF4400);
        addEntity(CATEGORY_HOSTILE, "minecraft:ghast",          0xFFFFDD);
        addEntity(CATEGORY_HOSTILE, "minecraft:blaze",          0xFFAA00);
        addEntity(CATEGORY_HOSTILE, "minecraft:witch",          0x553300);
        addEntity(CATEGORY_HOSTILE, "minecraft:guardian",       0x00AAAA);
        addEntity(CATEGORY_HOSTILE, "minecraft:shulker",        0xAA88FF);
        addEntity(CATEGORY_HOSTILE, "minecraft:husk",           0xCCAA44);
        addEntity(CATEGORY_HOSTILE, "minecraft:stray",          0xAAFFFF);
        addEntity(CATEGORY_HOSTILE, "minecraft:phantom",        0x8800AA);
        addEntity(CATEGORY_HOSTILE, "minecraft:drowned",        0x004488);
        addEntity(CATEGORY_HOSTILE, "minecraft:pillager",       0x888888);
        addEntity(CATEGORY_HOSTILE, "minecraft:ravager",        0x885500);
        addEntity(CATEGORY_HOSTILE, "minecraft:vindicator",     0xCCCCCC);
        addEntity(CATEGORY_HOSTILE, "minecraft:evoker",         0x444466);
        addEntity(CATEGORY_HOSTILE, "minecraft:vex",            0x8888FF);
        addEntity(CATEGORY_HOSTILE, "minecraft:hoglin",         0xFF8844);
        addEntity(CATEGORY_HOSTILE, "minecraft:zoglin",         0xFF4488);
        addEntity(CATEGORY_HOSTILE, "minecraft:piglin_brute",   0xFF6600);
        addEntity(CATEGORY_HOSTILE, "minecraft:wither_skeleton",0x111111);
        addEntity(CATEGORY_HOSTILE, "minecraft:zombie_villager",0x336600);
        addEntity(CATEGORY_HOSTILE, "minecraft:silverfish",     0x666666);
        addEntity(CATEGORY_HOSTILE, "minecraft:endermite",      0x440044);
        addEntity(CATEGORY_HOSTILE, "minecraft:breeze",         0x44AAFF);
        addEntity(CATEGORY_HOSTILE, "minecraft:bogged",         0x226600);

        // Neutral
        addEntity(CATEGORY_NEUTRAL, "minecraft:wolf",           0xDDDDDD);
        addEntity(CATEGORY_NEUTRAL, "minecraft:bee",            0xFFCC00);
        addEntity(CATEGORY_NEUTRAL, "minecraft:llama",          0xDDAA66);
        addEntity(CATEGORY_NEUTRAL, "minecraft:trader_llama",   0xFFBB44);
        addEntity(CATEGORY_NEUTRAL, "minecraft:polar_bear",     0xFFFFFF);
        addEntity(CATEGORY_NEUTRAL, "minecraft:iron_golem",     0xBBBBBB);
        addEntity(CATEGORY_NEUTRAL, "minecraft:snow_golem",     0xAADDFF);
        addEntity(CATEGORY_NEUTRAL, "minecraft:dolphin",        0x558899);
        addEntity(CATEGORY_NEUTRAL, "minecraft:panda",          0xEEEEEE);
        addEntity(CATEGORY_NEUTRAL, "minecraft:fox",            0xFF8800);
        addEntity(CATEGORY_NEUTRAL, "minecraft:goat",           0xCCBBAA);
        addEntity(CATEGORY_NEUTRAL, "minecraft:piglin",         0xFFAAAA);
        addEntity(CATEGORY_NEUTRAL, "minecraft:zombified_piglin", 0x886644);

        // Passive
        addEntity(CATEGORY_PASSIVE, "minecraft:pig",            0xFFAABB);
        addEntity(CATEGORY_PASSIVE, "minecraft:cow",            0x886644);
        addEntity(CATEGORY_PASSIVE, "minecraft:sheep",          0xEEEEEE);
        addEntity(CATEGORY_PASSIVE, "minecraft:chicken",        0xFFFFCC);
        addEntity(CATEGORY_PASSIVE, "minecraft:horse",          0xAA8855);
        addEntity(CATEGORY_PASSIVE, "minecraft:donkey",         0x887766);
        addEntity(CATEGORY_PASSIVE, "minecraft:mule",           0x664422);
        addEntity(CATEGORY_PASSIVE, "minecraft:rabbit",         0xDDAA88);
        addEntity(CATEGORY_PASSIVE, "minecraft:cat",            0xFF8844);
        addEntity(CATEGORY_PASSIVE, "minecraft:ocelot",         0xFFCC44);
        addEntity(CATEGORY_PASSIVE, "minecraft:parrot",         0xFF4444);
        addEntity(CATEGORY_PASSIVE, "minecraft:bat",            0x443322);
        addEntity(CATEGORY_PASSIVE, "minecraft:squid",          0x334455);
        addEntity(CATEGORY_PASSIVE, "minecraft:glow_squid",     0x00FFAA);
        addEntity(CATEGORY_PASSIVE, "minecraft:turtle",         0x44AA44);
        addEntity(CATEGORY_PASSIVE, "minecraft:cod",            0xDDAA66);
        addEntity(CATEGORY_PASSIVE, "minecraft:salmon",         0xFF6644);
        addEntity(CATEGORY_PASSIVE, "minecraft:pufferfish",     0xFFAA00);
        addEntity(CATEGORY_PASSIVE, "minecraft:tropical_fish",  0xFF6600);
        addEntity(CATEGORY_PASSIVE, "minecraft:mooshroom",      0xCC4444);
        addEntity(CATEGORY_PASSIVE, "minecraft:strider",        0xFF4444);
        addEntity(CATEGORY_PASSIVE, "minecraft:villager",       0xFFCCAA);
        addEntity(CATEGORY_PASSIVE, "minecraft:wandering_trader",0x3399FF);
        addEntity(CATEGORY_PASSIVE, "minecraft:axolotl",        0xFF88AA);
        addEntity(CATEGORY_PASSIVE, "minecraft:frog",           0xCC8844);
        addEntity(CATEGORY_PASSIVE, "minecraft:tadpole",        0x886633);
        addEntity(CATEGORY_PASSIVE, "minecraft:allay",          0x88CCFF);
        addEntity(CATEGORY_PASSIVE, "minecraft:camel",          0xDDBB66);
        addEntity(CATEGORY_PASSIVE, "minecraft:sniffer",        0x884422);
        addEntity(CATEGORY_PASSIVE, "minecraft:armadillo",      0xAA8866);

        // Other
        addEntity(CATEGORY_OTHER, "minecraft:armor_stand",      0xAAAAAA);
        addEntity(CATEGORY_OTHER, "minecraft:item_frame",       0xAA8855);
        addEntity(CATEGORY_OTHER, "minecraft:glow_item_frame",  0xFFFF88);
        addEntity(CATEGORY_OTHER, "minecraft:painting",         0xCC9966);
        addEntity(CATEGORY_OTHER, "minecraft:minecart",         0xAAAAAA);
        addEntity(CATEGORY_OTHER, "minecraft:chest_minecart",   0xAA8855);
        addEntity(CATEGORY_OTHER, "minecraft:hopper_minecart",  0x888888);
        addEntity(CATEGORY_OTHER, "minecraft:tnt_minecart",     0xFF4444);
        addEntity(CATEGORY_OTHER, "minecraft:furnace_minecart", 0x886644);
        addEntity(CATEGORY_OTHER, "minecraft:oak_boat",         0xAA8855);
        addEntity(CATEGORY_OTHER, "minecraft:oak_chest_boat",   0xAA7744);
        addEntity(CATEGORY_OTHER, "minecraft:end_crystal",      0xFF00FF);
        addEntity(CATEGORY_OTHER, "minecraft:tnt",              0xFF2200);
        addEntity(CATEGORY_OTHER, "minecraft:falling_block",    0x888888);
        addEntity(CATEGORY_OTHER, "minecraft:item",             0xFFFF00);
        addEntity(CATEGORY_OTHER, "minecraft:experience_orb",   0x00FF88);
    }

    private void addEntity(String category, String entityId, int color) {
        Identifier id = Identifier.of(entityId);
        entityVisibility.put(id, true);
        entityColors.put(id, color);
        categories.get(category).add(id);
    }

    public boolean isEntityVisible(Identifier entityId) {
        return entityVisibility.getOrDefault(entityId, false);
    }

    public boolean isEntityVisible(EntityType<?> entityType) {
        return isEntityVisible(EntityType.getId(entityType));
    }

    public void setEntityVisible(Identifier entityId, boolean visible) {
        entityVisibility.put(entityId, visible);
    }

    public void toggleEntity(Identifier entityId) {
        entityVisibility.put(entityId, !isEntityVisible(entityId));
    }

    public int getEntityColor(Identifier entityId) {
        return entityColors.getOrDefault(entityId, 0xFFFFFF);
    }

    public int getEntityColor(EntityType<?> entityType) {
        return getEntityColor(EntityType.getId(entityType));
    }

    public Map<Identifier, Boolean> getAllEntities() {
        return entityVisibility;
    }

    public Map<String, List<Identifier>> getCategories() {
        return categories;
    }

    public void enableAll() {
        entityVisibility.keySet().forEach(id -> entityVisibility.put(id, true));
    }

    public void disableAll() {
        entityVisibility.keySet().forEach(id -> entityVisibility.put(id, false));
    }

    public void enableCategory(String category) {
        List<Identifier> entities = categories.get(category);
        if (entities != null) entities.forEach(id -> entityVisibility.put(id, true));
    }

    public void disableCategory(String category) {
        List<Identifier> entities = categories.get(category);
        if (entities != null) entities.forEach(id -> entityVisibility.put(id, false));
    }

    public void toggleCategory(String category) {
        List<Identifier> entities = categories.get(category);
        if (entities == null || entities.isEmpty()) return;
        boolean anyEnabled = entities.stream().anyMatch(this::isEntityVisible);
        if (anyEnabled) disableCategory(category);
        else enableCategory(category);
    }

    public String getEntityDisplayName(Identifier entityId) {
        String path = entityId.getPath();
        String[] words = path.split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            result.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1))
                  .append(" ");
        }
        return result.toString().trim();
    }
}
