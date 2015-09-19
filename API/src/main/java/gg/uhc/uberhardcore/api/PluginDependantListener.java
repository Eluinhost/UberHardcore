package gg.uhc.uberhardcore.api;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Useful for NMS classes to specify a listener that requires a plugin object.
 *
 * The plugin object is injected via setPlugin just before registering for events, the plugin object
 * should not be used until it is first set.
 */
public abstract class PluginDependantListener implements Listener {

    protected Plugin plugin;

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
