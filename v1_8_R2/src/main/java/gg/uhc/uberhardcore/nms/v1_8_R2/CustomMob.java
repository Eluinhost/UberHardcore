package gg.uhc.uberhardcore.nms.v1_8_R2;

import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.chicken.CustomChicken;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.chicken.ThrownEggHandler;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.creeper.CreeperDeathHandler;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.rabbit.RabbitSpawnHandler;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.sheep.CustomSheep;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.skeleton.CustomSkeleton;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.spider.CustomSpider;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.spider.SpiderDeathHandler;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.zombie.CustomZombie;
import gg.uhc.uberhardcore.nms.v1_8_R2.mobs.zombie.ZombieSeigeHandler;
import net.minecraft.server.v1_8_R2.*;
import org.bukkit.event.Listener;

import java.util.Arrays;

public enum CustomMob {
    CHICKEN(EntityChicken.class, CustomChicken.class, new ThrownEggHandler()),
    CREEPER(null, null, new CreeperDeathHandler()),
    RABBIT(null, null, new RabbitSpawnHandler()),
    SHEEP(EntitySheep.class, CustomSheep.class),
    SKELETON(EntitySkeleton.class, CustomSkeleton.class),
    SPIDER(EntitySpider.class, CustomSpider.class, new SpiderDeathHandler()),
    ZOMBIE(EntityZombie.class, CustomZombie.class, new ZombieSeigeHandler())
    ;
    protected final Class<? extends EntityInsentient> nms;
    protected final Class<? extends EntityInsentient> override;
    protected final Listener[] listeners;

    CustomMob(Class<? extends EntityInsentient> nms, Class<? extends EntityInsentient> override, Listener... listeners) {
        this.nms = nms;
        this.override = override;
        this.listeners = Arrays.copyOf(listeners, listeners.length + 1);
        this.listeners[listeners.length] = new InvalidSpawnListener(nms);
    }
}
