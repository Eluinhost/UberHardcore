package gg.uhc.uberhardcore;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class PluginDependantListener implements Listener {

    protected Plugin plugin;

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
