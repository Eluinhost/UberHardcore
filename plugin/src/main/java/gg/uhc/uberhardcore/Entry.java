/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.Entry
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Graham Howden <graham_howden1 at yahoo.co.uk>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package gg.uhc.uberhardcore;

import gg.uhc.uberhardcore.api.*;
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
        NewSpawnsModifier newSpawnsModifier = handler.getNewSpawnsModifier();
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
                newSpawnsModifier,
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
