/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.InvalidSpawnListener
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
