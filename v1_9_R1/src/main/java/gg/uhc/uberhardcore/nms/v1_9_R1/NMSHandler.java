/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R3.NMSHandler
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

import com.google.common.collect.ImmutableList;
import gg.uhc.uberhardcore.api.MobOverride;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.chicken.CustomChicken;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.chicken.ThrownEggHandler;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.creeper.CreeperDeathHandler;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.rabbit.RabbitSpawnHandler;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.sheep.CustomSheep;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.skeleton.CustomSkeleton;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.spider.CustomSpider;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.spider.SpiderDeathHandler;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.zombie.CustomZombie;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.zombie.ZombieSeigeHandler;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class NMSHandler extends gg.uhc.uberhardcore.api.NMSHandler {

    protected final EntityChecker entityChecker;
    protected final EntityClassReplacer entityClassReplacer;
    protected final NewSpawnsModifier newSpawnsModifier;
    protected final List<MobOverride> mobOverrides;

    public NMSHandler(Plugin plugin) {
        super(plugin);

        entityChecker = new EntityChecker();
        newSpawnsModifier = new NewSpawnsModifier();
        entityClassReplacer = new EntityClassReplacer(plugin.getLogger());

        mobOverrides = ImmutableList.of(
                new MobOverride()
                    .withOverridingClasses(EntityChicken.class, CustomChicken.class)
                    .withListeners(new ThrownEggHandler())
                    .withSupressedInvalidSpawnReasons(CreatureSpawnEvent.SpawnReason.MOUNT),
                new MobOverride()
                    .withOverridingClasses(EntitySkeleton.class, CustomSkeleton.class)
                    .withSupressedInvalidSpawnReasons(CreatureSpawnEvent.SpawnReason.JOCKEY),
                new MobOverride()
                    .withListeners(new CreeperDeathHandler()),
                new MobOverride()
                    .withListeners(new RabbitSpawnHandler()),
                new MobOverride()
                    .withOverridingClasses(EntitySheep.class, CustomSheep.class),
                new MobOverride()
                    .withOverridingClasses(EntitySpider.class, CustomSpider.class)
                    .withListeners(new SpiderDeathHandler()),
                new MobOverride()
                    .withOverridingClasses(EntityZombie.class, CustomZombie.class)
                    .withListeners(new ZombieSeigeHandler())
                    .withSupressedInvalidSpawnReasons(CreatureSpawnEvent.SpawnReason.REINFORCEMENTS)
        );
    }

    @Override
    public gg.uhc.uberhardcore.api.EntityChecker getEntityChecker() {
        return entityChecker;
    }

    @Override
    public gg.uhc.uberhardcore.api.EntityClassReplacer getEntityClassReplacer() {
        return entityClassReplacer;
    }

    @Override
    public List<MobOverride> getMobOverrides() {
        return mobOverrides;
    }

    @Override
    public NewSpawnsModifier getNewSpawnsModifier() {
        return newSpawnsModifier;
    }
}
