/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R2.mobs.zombie.ZombieSeigeHandler
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

package gg.uhc.uberhardcore.nms.v1_8_R2.mobs.zombie;

import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import java.lang.reflect.Field;

/**
 * Handles loading of worlds to replace the Village seige mechanics to stop EntityZombie being spawned instead of
 * UberZombie
 */
public class ZombieSeigeHandler implements Listener {

    protected static Field seigeHandlerField;

    // overwrite the seige with our own for every world loaded
    @EventHandler
    public void onWorldInit(WorldInitEvent event) throws NoSuchFieldException, IllegalAccessException {
        WorldServer world = ((CraftWorld) event.getWorld()).getHandle();

        if (seigeHandlerField == null) {
            seigeHandlerField = WorldServer.class.getDeclaredField("siegeManager");
            seigeHandlerField.setAccessible(true);
        }

        seigeHandlerField.set(world, new CustomZombieSeige(world));
    }
}
