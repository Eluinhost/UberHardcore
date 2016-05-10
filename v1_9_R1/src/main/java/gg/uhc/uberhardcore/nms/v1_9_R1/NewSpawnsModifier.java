/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.NewSpawnsModifier
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Graham Howden <graham_howden1 at yahoo.co.uk>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package gg.uhc.uberhardcore.nms.v1_9_R1;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.EntityGhast;
import net.minecraft.server.v1_9_R1.EnumCreatureType;

import java.util.Iterator;
import java.util.List;

public class NewSpawnsModifier implements gg.uhc.uberhardcore.api.NewSpawnsModifier {

    protected List<BiomeBase> skipped;

    public void setup() {
        skipped = Lists.newArrayList();

        outer:
        for (BiomeBase biome : BiomeBase.i) {
            if (biome == null) continue;

            List<BiomeBase.BiomeMeta> spawns = biome.getMobs(EnumCreatureType.MONSTER);

            // skip if it already has a ghast entry
            for (BiomeBase.BiomeMeta meta : spawns) {
                if (meta.b == EntityGhast.class) {
                    skipped.add(biome);
                    continue outer;
                }
            }

            spawns.add(new BiomeBase.BiomeMeta(EntityGhast.class, 50, 4, 4));
        }
    }

    public void desetup() {
        for (BiomeBase biome : BiomeBase.i) {
            if (biome == null || skipped.contains(biome)) continue;

            Iterator<BiomeBase.BiomeMeta> iterator = biome.getMobs(EnumCreatureType.MONSTER).iterator();

            while(iterator.hasNext()) {
                BiomeBase.BiomeMeta meta = iterator.next();

                if (meta.b == EntityGhast.class) {
                    iterator.remove();
                }
            }
        }
    }
}
