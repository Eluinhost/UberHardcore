package gg.uhc.uberhardcore.nms.v1_8_R1.mobs.creeper;

import gg.uhc.uberhardcore.api.PluginDependantListener;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class CreeperDeathHandler extends PluginDependantListener {

    @EventHandler
    public void on(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Creeper)) return;

        Creeper creeper = (Creeper) event.getEntity();

        // explode after 2 seconds ticks
        ExplosionTask task = new ExplosionTask(creeper.getLocation(), creeper.isPowered() ? 6 : 3, false);
        task.runTaskLater(plugin, 40);

        // set up some layered smoke effects
        for (int i = 0; i < 5; i++) {
            // increase the count over time
            ImpendingExplosionParticleTask smoke = new ImpendingExplosionParticleTask(creeper.getLocation(), 20 * (i+1));
            smoke.runTaskLater(plugin, 10 + (i * 10));
        }
    }
}
