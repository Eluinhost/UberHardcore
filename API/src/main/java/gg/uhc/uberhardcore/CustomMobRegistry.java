package gg.uhc.uberhardcore;

import org.bukkit.plugin.Plugin;

public abstract class CustomMobRegistry {

    protected final Plugin plugin;

    protected CustomMobRegistry(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract void registerEntities();
}
