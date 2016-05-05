/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R2.mobs.creeper.ImpendingExplosionParticleTask
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

package gg.uhc.uberhardcore.nms.v1_8_R2.mobs.creeper;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

public class ImpendingExplosionParticleTask extends BukkitRunnable {

    protected final Location location;
    protected final int count;

    public ImpendingExplosionParticleTask(Location location, int count) {
        this.location = location;
        this.count = count;
    }

    @Override
    public void run() {
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();

        world.sendParticles(
                null,
                EnumParticle.SMOKE_LARGE,
                false,
                location.getX(),
                location.getY(),
                location.getZ(),
                count,
                1D,
                1D,
                1D,
                0
        );
    }
}
