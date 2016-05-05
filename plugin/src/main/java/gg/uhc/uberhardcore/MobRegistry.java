/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.MobRegistry
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
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MobRegistry {

    protected final Plugin plugin;
    protected final EntityClassReplacer replacer;
    protected final EntityChecker entityChecker;
    protected final NewSpawnsModifier newSpawnsModifier;
    protected final EntityKiller entityKiller;
    protected final List<MobOverride> overrides;

    public MobRegistry(Plugin plugin, EntityClassReplacer replacer, EntityChecker entityChecker, NewSpawnsModifier newSpawnsModifier, EntityKiller entityKiller, List<MobOverride> overrides) {
        this.plugin = plugin;
        this.replacer = replacer;
        this.entityChecker = entityChecker;
        this.newSpawnsModifier = newSpawnsModifier;
        this.entityKiller = entityKiller;
        this.overrides = overrides;
    }

    /**
     * Register our custom entities. Kills any loaded entities of the original type
     */
    public void registerEntities() {
        for (MobOverride override : overrides) {
            if (override.isRunningClassReplace()) {
                // replace original classes
                replacer.replaceClasses(override.getNmsClass(), override.getOverrideClass());

                // add a listener for original class
                Bukkit.getPluginManager().registerEvents(new InvalidSpawnListener(plugin, entityChecker, override.getNmsClass(), override.getSuppressedInvalidSpawnReasons()), plugin);
            }

            // register events for the override
            for (Listener listener : override.getListeners()) {
                if (listener instanceof PluginDependantListener) {
                    ((PluginDependantListener) listener).setPlugin(plugin);
                }

                Bukkit.getPluginManager().registerEvents(listener, plugin);
            }

            // kill all of the original classes
            for (World world : Bukkit.getWorlds()) {
                entityKiller.killEntitiesInWorld(override.getNmsClass(), world);
            }
        }

        newSpawnsModifier.setup();
    }

    /**
     * Should only be called in onDisable, does not cleanup listeners
     */
    public void deregisterEntities() {
        newSpawnsModifier.desetup();

        for (MobOverride override : overrides) {
            if (override.isRunningClassReplace()) {
                // reverse the replace
                replacer.replaceClasses(override.getOverrideClass(), override.getNmsClass());
            }

            // kill the replacement classes
            for (World world : Bukkit.getWorlds()) {
                entityKiller.killEntitiesInWorld(override.getOverrideClass(), world);
            }
        }
    }
}
