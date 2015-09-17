package gg.uhc.uberhardcore;

import org.bukkit.plugin.java.JavaPlugin;

public class Entry extends JavaPlugin {

    @Override
    public void onEnable() {
        CustomMobRegistry registry = getMobRegistry();

        if (registry == null) {
            getLogger().severe("This version of Spigot/Bukkit is not supported by this version of UberHardcore");
            setEnabled(false);
            return;
        }

        registry.registerEntities();
    }

    protected CustomMobRegistry getMobRegistry() {
        String packageName = getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf(".") + 1);

        switch (version) {
            case "v1_8_R1":
                return new gg.uhc.uberhardcore.nms.v1_8_R1.MobRegistry(this);
            case "v1_8_R2":
                return new gg.uhc.uberhardcore.nms.v1_8_R2.MobRegistry(this);
            case "v1_8_R3":
                return new gg.uhc.uberhardcore.nms.v1_8_R3.MobRegistry(this);
            default:
                return null;
        }
    }
}
