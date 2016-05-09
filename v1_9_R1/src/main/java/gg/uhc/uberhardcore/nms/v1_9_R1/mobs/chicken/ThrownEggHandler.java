/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R3.mobs.chicken.ThrownEggHandler
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

package gg.uhc.uberhardcore.nms.v1_9_R1.mobs.chicken;

import net.minecraft.server.v1_9_R1.EntityEgg;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEgg;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ThrownEggHandler implements Listener {

    @EventHandler
    public void on(ProjectileLaunchEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Egg)) return;

        Egg egg = (Egg) entity;

        // skip our own eggs
        if (((CraftEgg) entity).getHandle() instanceof CustomChickenEgg) return;

        // cancel the launch
        event.setCancelled(true);

        EntityEgg original = ((CraftEgg) egg).getHandle();

        // create our own egg from the original and spawn that
        CustomChickenEgg newEgg = new CustomChickenEgg(original.getWorld(), original.getShooter());
        original.getWorld().addEntity(newEgg, CreatureSpawnEvent.SpawnReason.EGG);
    }
}
