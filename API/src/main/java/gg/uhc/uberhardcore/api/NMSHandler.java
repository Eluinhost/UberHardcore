package gg.uhc.uberhardcore.api;

import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Extend this class for each version of Bukkit to support.
 *
 * Also requries implementing EntityChecker, EntityClassReplacer and providing a list of MobOverride
 */
public abstract class NMSHandler {

    protected final Plugin plugin;

    public NMSHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @return NMS specific EntityChecker
     */
    public abstract EntityChecker getEntityChecker();

    /**
     * @return NMS specific EntityClassReplacer
     */
    public abstract EntityClassReplacer getEntityClassReplacer();

    /**
     * @return NMS specific MobOverride list
     */
    public abstract List<MobOverride> getMobOverrides();
}
