package gg.uhc.uberhardcore.nms.v1_8_R1;

import gg.uhc.uberhardcore.PluginDependantListener;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class InvalidSpawnListener extends PluginDependantListener {

    protected final Class<? extends EntityInsentient> toWatch;

    public InvalidSpawnListener(Class<? extends EntityInsentient> toWatch) {
        this.toWatch = toWatch;
    }

    @EventHandler
    public void on(CreatureSpawnEvent event) {
        EntityLiving entity = ((CraftLivingEntity) event.getEntity()).getHandle();
        if (entity.getClass() == toWatch) {
            plugin.getLogger().severe(String.format("Invalid spawn occured for entity type %s at x:%f y:%f z:%f, automatically cancelling...", entity.getClass().getName(), entity.locX, entity.locY, entity.locZ));

            event.setCancelled(true);
        }
    }
}
