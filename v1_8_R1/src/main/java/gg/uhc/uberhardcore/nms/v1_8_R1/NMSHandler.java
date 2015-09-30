package gg.uhc.uberhardcore.nms.v1_8_R1;

import com.google.common.collect.ImmutableList;
import gg.uhc.uberhardcore.api.*;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.chicken.CustomChicken;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.chicken.ThrownEggHandler;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.creeper.CreeperDeathHandler;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.rabbit.RabbitSpawnHandler;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.sheep.CustomSheep;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.skeleton.CustomSkeleton;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.spider.CustomSpider;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.spider.SpiderDeathHandler;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.zombie.CustomZombie;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.zombie.ZombieSeigeHandler;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class NMSHandler extends gg.uhc.uberhardcore.api.NMSHandler {

    protected final EntityChecker entityChecker;
    protected final EntityClassReplacer entityClassReplacer;
    protected final List<MobOverride> mobOverrides;
    protected final NewSpawnsModifier newSpawnsModifier;

    public NMSHandler(Plugin plugin) {
        super(plugin);

        entityChecker = new EntityChecker();
        entityClassReplacer = new EntityClassReplacer(plugin.getLogger());
        newSpawnsModifier = new NewSpawnsModifier();

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
    public gg.uhc.uberhardcore.api.NewSpawnsModifier getNewSpawnsModifier() {
        return null;
    }
}
