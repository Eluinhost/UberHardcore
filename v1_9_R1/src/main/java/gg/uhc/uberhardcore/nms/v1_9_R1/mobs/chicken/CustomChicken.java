/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R3.mobs.chicken.CustomChicken
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

import gg.uhc.uberhardcore.nms.v1_9_R1.AIUtil;
import net.minecraft.server.v1_9_R1.*;

public class CustomChicken extends EntityChicken implements IRangedEntity {

    public CustomChicken(World world) {
        super(world);

        AIUtil ai = AIUtil.getInstance();

        ai.removeAIGoals(this.goalSelector, PathfinderGoalPanic.class, PathfinderGoalMakeLove.class, PathfinderGoalTempt.class, PathfinderGoalFollowParent.class);

        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.25D, 20, 10.0F));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, false));
    }

    // override creating a child to stop an EntityChicken being spawned
    // and spawn our mob instead
    @Override
    public EntityChicken createChild(EntityAgeable ageable) {
        return new CustomChicken(this.world);
    }

    // taken logic from EntitySnowman
    @Override
    public void a(EntityLiving entityliving, float v) {
        // change snowball to egg
        CustomChickenEgg egg = new CustomChickenEgg(this.world, this);
        double d0 = entityliving.locY + (double)entityliving.getHeadHeight() - 1.100000023841858D;
        double d1 = entityliving.locX - this.locX;
        double d2 = d0 - egg.locY;
        double d3 = entityliving.locZ - this.locZ;
        float f1 = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        egg.shoot(d1, d2 + (double) f1, d3, 1.6F, 12.0F);
        // change bow noise to plop noise
        this.makeSound("mob.chicken.plop", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(egg);
    }
}
