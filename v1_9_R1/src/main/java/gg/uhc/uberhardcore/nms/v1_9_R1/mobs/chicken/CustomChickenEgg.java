/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.mobs.chicken.CustomChickenEgg
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


import net.minecraft.server.v1_9_R1.*;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Used to replace an EntityEgg with a version that spawns an CustomChicken instead.
 *
 * Also reduces rates of babies spawning from eggs too to avoid overpopulation
 */
public class CustomChickenEgg extends EntityEgg {

    public CustomChickenEgg(World worldIn, EntityLiving shooter) {
        super(worldIn, shooter);
    }

    // replace 1/8 chance to spawn baby to 1/32 + no chance for 4 to spawn
    @Override
    protected void a(MovingObjectPosition movingobjectposition)
    {
        if(movingobjectposition.entity != null) {
            // added this.getThrower() instanceof CustomChicken ? 1.0F : 0.0F]
            // causes non-chicken thrown eggs to do no damage
            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), this.getShooter() instanceof CustomChicken ? 1.0F : 0.0F);
        }

        // 1/32 chance to spawn
        if (this.random.nextInt(32) == 0) {
            CustomChicken chicken = new CustomChicken(this.getWorld());
            chicken.setAge(-24000);
            chicken.setLocation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            this.world.addEntity(chicken, CreatureSpawnEvent.SpawnReason.EGG);
        }

        // standard egg particles
        for(int i = 0; i < 8; ++i) {
            this.world.addParticle(EnumParticle.ITEM_CRACK, this.locX, this.locY, this.locZ, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, new int[]{Item.getId(Items.EGG)});
        }

        // RIP
        this.die();
    }
}
