package gg.uhc.uberhardcore;

import gg.uhc.uberhardcore.api.EntityChecker;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

public class InvalidSpawnListener implements Listener {

    protected final Plugin plugin;
    protected final Class toCheck;
    protected final EntityChecker entityChecker;

    public InvalidSpawnListener(Plugin plugin, EntityChecker entityChecker, Class toCheck) {
        this.plugin = plugin;
        this.toCheck = toCheck;
        this.entityChecker = entityChecker;
    }

    @EventHandler
    public void on(CreatureSpawnEvent event) {
        if (entityChecker.isEntityOfClassExact(event.getEntity(), toCheck)) {
            Location loc = event.getEntity().getLocation();
            plugin.getLogger().severe(
                    String.format("Invalid spawn occured for entity type %s at x:%f y:%f z:%f, automatically cancelling...",
                            toCheck.getName(),
                            loc.getX(),
                            loc.getY(),
                            loc.getZ()
                    )
            );

            event.setCancelled(true);
        }
    }
}
