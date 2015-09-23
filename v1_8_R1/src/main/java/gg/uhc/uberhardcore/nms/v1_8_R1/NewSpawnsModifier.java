package gg.uhc.uberhardcore.nms.v1_8_R1;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_8_R1.BiomeMeta;
import net.minecraft.server.v1_8_R1.BiomeBase;
import net.minecraft.server.v1_8_R1.EntityGhast;
import net.minecraft.server.v1_8_R1.EnumCreatureType;

import java.util.Iterator;
import java.util.List;

public class NewSpawnsModifier implements gg.uhc.uberhardcore.api.NewSpawnsModifier {

    protected List<BiomeBase> skipped;

    public void setup() {
        skipped = Lists.newArrayList();

        outer:
        for (BiomeBase biome : BiomeBase.getBiomes()) {
            if (biome == null) continue;

            List<BiomeMeta> spawns = biome.getMobs(EnumCreatureType.MONSTER);

            // skip if it already has a ghast entry
            for (BiomeMeta meta : spawns) {
                if (meta.b == EntityGhast.class) {
                    skipped.add(biome);
                    continue outer;
                }
            }

            spawns.add(new BiomeMeta(EntityGhast.class, 50, 4, 4));
        }
    }

    public void desetup() {
        for (BiomeBase biome : BiomeBase.getBiomes()) {
            if (biome == null || skipped.contains(biome)) continue;

            Iterator<BiomeMeta> iterator = biome.getMobs(EnumCreatureType.MONSTER).iterator();

            while(iterator.hasNext()) {
                BiomeMeta meta = iterator.next();

                if (meta.b == EntityGhast.class) {
                    iterator.remove();
                }
            }
        }
    }
}
