package gg.uhc.uberhardcore.nms.v1_8_R3.mobs.zombie;

import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import java.lang.reflect.Field;

/**
 * Handles loading of worlds to replace the Village seige mechanics to stop EntityZombie being spawned instead of
 * UberZombie
 */
public class ZombieSeigeHandler implements Listener {

    protected static Field seigeHandlerField;

    // overwrite the seige with our own for every world loaded
    @EventHandler
    public void onWorldInit(WorldInitEvent event) throws NoSuchFieldException, IllegalAccessException {
        WorldServer world = ((CraftWorld) event.getWorld()).getHandle();

        if (seigeHandlerField == null) {
            seigeHandlerField = WorldServer.class.getDeclaredField("siegeManager");
            seigeHandlerField.setAccessible(true);
        }

        seigeHandlerField.set(world, new CustomZombieSeige(world));
    }
}
