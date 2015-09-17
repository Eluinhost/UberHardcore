package gg.uhc.uberhardcore.nms.v1_8_R2;

import com.google.common.collect.Lists;
import gg.uhc.uberhardcore.CustomMobRegistry;
import gg.uhc.uberhardcore.PluginDependantListener;
import net.minecraft.server.v1_8_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class MobRegistry extends CustomMobRegistry {

    public MobRegistry(Plugin plugin) {
        super(plugin);
    }

    public void registerEntities() {
        if (!ensureNms()) return;

        BiomeBase[] biomes = BiomeBase.getBiomes();

        // create one big list for easy replacements
        List<BiomeBase.BiomeMeta> spawns = Lists.newArrayList();
        for (BiomeBase biome : biomes) {
            if (biome == null) continue;

            spawns.addAll(biome.getMobs(EnumCreatureType.CREATURE));
            spawns.addAll(biome.getMobs(EnumCreatureType.AMBIENT));
            spawns.addAll(biome.getMobs(EnumCreatureType.WATER_CREATURE));
            spawns.addAll(biome.getMobs(EnumCreatureType.MONSTER));
        }

        // run each override
        for (CustomMob mob : CustomMob.values()) {
            if (mob.nms != null) {
                overwriteVanillaMob(mob.nms, mob.override);
            }

            // replace spawn classes
            for (BiomeBase.BiomeMeta spawn : spawns) {
                if (spawn.b == mob.nms) {
                    spawn.b = mob.override;
                }
            }

            // register events
            for (Listener listener : mob.listeners) {
                if (listener instanceof PluginDependantListener) {
                    ((PluginDependantListener) listener).setPlugin(plugin);
                }

                Bukkit.getPluginManager().registerEvents(listener, plugin);
            }
        }
    }

    protected static Map<Class<? extends Entity>, Integer> classToIdMapping;
    protected static Map<Class<? extends Entity>, String> classToStringMapping;
    protected static Map<Integer, Class<? extends Entity>> idToClassMapping;
    protected static Map<String, Class<? extends Entity>> stringToClassMapping;


    @SuppressWarnings("unchecked")
    protected static boolean ensureNms() {
        try {
            Field classToIdMappingField = EntityTypes.class.getDeclaredField("f");
            Field classToStringMappingField = EntityTypes.class.getDeclaredField("d");
            Field idToClassMappingField = EntityTypes.class.getDeclaredField("e");
            Field stringToClassMappingField = EntityTypes.class.getDeclaredField("c");

            classToIdMappingField.setAccessible(true);
            classToStringMappingField.setAccessible(true);
            idToClassMappingField.setAccessible(true);
            stringToClassMappingField.setAccessible(true);

            classToIdMapping = (Map<Class<? extends Entity>, Integer>) classToIdMappingField.get(null);
            classToStringMapping = (Map<Class<? extends Entity>, String>) classToStringMappingField.get(null);
            idToClassMapping = (Map<Integer, Class<? extends Entity>>) idToClassMappingField.get(null);
            stringToClassMapping = (Map<String, Class<? extends Entity>>) stringToClassMappingField.get(null);

            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Replaces a vanilla mob class with a custom class.
     *
     * @param original    the vanilla mob to replace
     * @param replacement the class to use as a replacement
     */
    protected static void overwriteVanillaMob(Class<? extends EntityInsentient> original, Class<? extends EntityInsentient> replacement) {
        // grab details from original settings
        System.out.println("Overwriting mob " + original.getSimpleName() + " with " + replacement.getSimpleName());

        if (!classToIdMapping.containsKey(original)) {
            System.out.println("Couldn't find original class, skipping...");
            return;
        }

        int id = classToIdMapping.get(original);
        System.out.println("Original ID: " + id);
        String name = classToStringMapping.get(original);
        System.out.println("Original name: " + id);

        // modify names <-> class
        stringToClassMapping.put(name, replacement); // overwrites original mapping
        classToStringMapping.remove(original); // remove original class
        classToStringMapping.put(replacement, name); // add our one in

        // modify id <-> class
        idToClassMapping.put(id, replacement); // overwrite original mapping
        classToIdMapping.remove(original); // remove original class
        classToIdMapping.put(replacement, id); // add our one in

        // string <-> id mapping requires no changes
    }
}
