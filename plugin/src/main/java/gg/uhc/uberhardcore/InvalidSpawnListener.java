package gg.uhc.uberhardcore;

import com.google.common.collect.ImmutableSet;
import gg.uhc.uberhardcore.api.EntityChecker;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class InvalidSpawnListener implements Listener {

    protected final Plugin plugin;
    protected final Class toCheck;
    protected final EntityChecker entityChecker;
    protected final Set<CreatureSpawnEvent.SpawnReason> skips;

    public InvalidSpawnListener(Plugin plugin, EntityChecker entityChecker, Class toCheck, Set<CreatureSpawnEvent.SpawnReason> skips) {
        this.plugin = plugin;
        this.toCheck = toCheck;
        this.entityChecker = entityChecker;
        this.skips = ImmutableSet.copyOf(skips);
    }

    @EventHandler
    public void on(CreatureSpawnEvent event) {
        if (!entityChecker.isEntityOfClassExact(event.getEntity(), toCheck)) return;

        if (skips.contains(event.getSpawnReason())) return;

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
