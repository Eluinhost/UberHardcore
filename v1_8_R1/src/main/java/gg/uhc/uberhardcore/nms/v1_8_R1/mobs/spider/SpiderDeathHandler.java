/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R1.mobs.spider.SpiderDeathHandler
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

package gg.uhc.uberhardcore.nms.v1_8_R1.mobs.spider;

import net.minecraft.server.v1_8_R1.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class SpiderDeathHandler implements Listener {

    protected static final Random random = new Random();

    @EventHandler
    public void on(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Spider)) return;

        Entity entity = ((CraftEntity) event.getEntity()).getHandle();

        // spawn 5 random webs
        for (int i = 0; i < 5; i++) {
            spawnRandomWeb(entity);
        }

        // spawn 30 random particle effects
        for (int i = 0; i < 30; i++) {
            spawnRandomRedstoneParticle(entity);
        }
    }

    /**
     * Spawns random falling block cobweb.
     *
     * Takes initial 50% momentum from given entity, then adds += .2 to each direction. Vertical momentum is guarenteed
     * to be at least .25
     *
     * @param entity location to spawn
     */
    protected static void spawnRandomWeb(Entity entity) {
        EntityFallingBlock block = new EntityFallingBlock(entity.world, entity.locX, entity.locY, entity.locZ, Blocks.WEB.getBlockData());

        // don't drop the item if it fails to place
        block.dropItem = false;

        // don't despawn instantly
        block.ticksLived = 1;

        // add (reduced) spider momentum
        block.motX = entity.motX *= .5D;
        block.motY = entity.motY *= .5D;
        block.motZ = entity.motZ *= .5D;

        // add some random spread
        block.motX += (random.nextDouble() * 0.4D) - 0.2D;
        block.motY += (random.nextDouble() * 0.4D) - 0.2D;
        block.motZ += (random.nextDouble() * 0.4D) - 0.2D;

        // add some base vertical motion
        block.motY += 0.25D;

        // make sure it goes up
        block.motY = Math.min(block.motY, 0.25D);
        // isAirborne
        block.ai = true;

        entity.world.addEntity(block, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    /**
     * Spawns a redstone particle effect at the given entity.
     *
     * Adds a random positional offset from the given entity (1 block each direction max)
     *
     * @param entity location to spawn
     */
    protected static void spawnRandomRedstoneParticle(Entity entity) {
        ((WorldServer)entity.world).sendParticles(
                null,
                EnumParticle.REDSTONE,
                false,
                entity.locX + (random.nextDouble() * 2.0D) - 1D,
                entity.locY + (random.nextDouble() * 2.0D) - 1D,
                entity.locZ + (random.nextDouble() * 2.0D) - 1D,
                3, // count
                0D, // offsets
                0D,
                0D,
                0 // speed
        );
    }
}
