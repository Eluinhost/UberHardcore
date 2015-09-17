package gg.uhc.uberhardcore.nms.v1_8_R1.mobs.rabbit;

import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class RabbitSpawnHandler implements Listener {

    protected static final Random random = new Random();

    @EventHandler
    public void on(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Rabbit)) return;

        Rabbit rabbit  = (Rabbit) event.getEntity();

        // switch to a killer rabbit
        if (random.nextDouble() < .01D) rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
    }
}
