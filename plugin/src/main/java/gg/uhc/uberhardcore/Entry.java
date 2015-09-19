package gg.uhc.uberhardcore;

import gg.uhc.uberhardcore.api.EntityChecker;
import gg.uhc.uberhardcore.api.EntityClassReplacer;
import gg.uhc.uberhardcore.api.MobOverride;
import gg.uhc.uberhardcore.api.NMSHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Entry extends JavaPlugin {

    protected MobRegistry registry;

    @Override
    public void onEnable() {
        NMSHandler handler = getNMSHandler();

        if (handler == null) {
            getLogger().severe("This version of Spigot/Bukkit is not supported by this version of UberHardcore");
            setEnabled(false);
            return;
        }

        EntityClassReplacer replacer = handler.getEntityClassReplacer();
        EntityChecker checker = handler.getEntityChecker();
        EntityKiller killer = new EntityKiller(checker);
        List<MobOverride> overrides = handler.getMobOverrides();

        try {
            replacer.initialize();
        } catch (Exception e) {
            getLogger().severe("This version of Spigot/Bukkit is not supported by this version of UberHardcore");
            e.printStackTrace();
            setEnabled(false);
        }

        registry = new MobRegistry(
                this,
                replacer,
                checker,
                killer,
                overrides
        );

        registry.registerEntities();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling plugin, replacing original entity classes");

        if (registry != null) registry.deregisterEntities();
    }

    protected NMSHandler getNMSHandler() {
        String packageName = getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf(".") + 1);

        switch (version) {
            case "v1_8_R1":
                return new gg.uhc.uberhardcore.nms.v1_8_R1.NMSHandler(this);
            case "v1_8_R2":
                return new gg.uhc.uberhardcore.nms.v1_8_R2.NMSHandler(this);
            case "v1_8_R3":
                return new gg.uhc.uberhardcore.nms.v1_8_R3.NMSHandler(this);
            default:
                return null;
        }
    }
}
