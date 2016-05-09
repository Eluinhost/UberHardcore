/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R3.mobs.creeper.CreeperDeathHandler
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

package gg.uhc.uberhardcore.nms.v1_9_R1.mobs.creeper;

import gg.uhc.uberhardcore.api.PluginDependantListener;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class CreeperDeathHandler extends PluginDependantListener {

    @EventHandler
    public void on(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Creeper)) return;

        Creeper creeper = (Creeper) event.getEntity();

        // explode after 2 seconds ticks
        ExplosionTask task = new ExplosionTask(creeper.getLocation(), creeper.isPowered() ? 6 : 3, false);
        task.runTaskLater(plugin, 40);

        // set up some layered smoke effects
        for (int i = 0; i < 5; i++) {
            // increase the count over time
            ImpendingExplosionParticleTask smoke = new ImpendingExplosionParticleTask(creeper.getLocation(), 20 * (i+1));
            smoke.runTaskLater(plugin, 10 + (i * 10));
        }
    }
}
