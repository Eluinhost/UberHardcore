package gg.uhc.uberhardcore.nms.v1_8_R3;

import com.google.common.collect.ImmutableList;
import gg.uhc.uberhardcore.api.*;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.chicken.CustomChicken;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.chicken.ThrownEggHandler;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.creeper.CreeperDeathHandler;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.rabbit.RabbitSpawnHandler;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.sheep.CustomSheep;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.skeleton.CustomSkeleton;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.spider.CustomSpider;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.spider.SpiderDeathHandler;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.zombie.CustomZombie;
import gg.uhc.uberhardcore.nms.v1_8_R3.mobs.zombie.ZombieSeigeHandler;
import net.minecraft.server.v1_8_R3.*;
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

        MobOverride chicken = new MobOverride(EntityChicken.class, CustomChicken.class, new ThrownEggHandler());
        chicken.setReasonsNotToLog(CreatureSpawnEvent.SpawnReason.MOUNT);

        MobOverride skeleton = new MobOverride(EntitySkeleton.class, CustomSkeleton.class);
        skeleton.setReasonsNotToLog(CreatureSpawnEvent.SpawnReason.JOCKEY);

        mobOverrides = ImmutableList.of(
                chicken,
                new MobOverride(new CreeperDeathHandler()),
                new MobOverride(new RabbitSpawnHandler()),
                new MobOverride(EntitySheep.class, CustomSheep.class),
                skeleton,
                new MobOverride(EntitySpider.class, CustomSpider.class, new SpiderDeathHandler()),
                new MobOverride(EntityZombie.class, CustomZombie.class, new ZombieSeigeHandler())
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
