/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.mobs.spider.PathfinderGoalSpiderMeleeAttackHostile
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

package gg.uhc.uberhardcore.nms.v1_9_R1.mobs.spider;

import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EntitySpider;
import net.minecraft.server.v1_9_R1.PathfinderGoalMeleeAttack;

// copied from EntitySpider.PathfinderGoalSpiderMeleeAttack but without daylight dependencies
public class PathfinderGoalSpiderMeleeAttackHostile extends PathfinderGoalMeleeAttack {

    public PathfinderGoalSpiderMeleeAttackHostile(EntitySpider entityspider, Class<? extends Entity> oclass) {
        super(entityspider, oclass, 1.0D, true);
    }

//    public boolean b() {
//        float f = this.b.c(1.0F);
//        if(f >= 0.5F && this.b.bc().nextInt(100) == 0) {
//            this.b.setGoalTarget((EntityLiving)null);
//            return false;
//        } else {
//            return super.b();
//        }
//    }

    protected double a(EntityLiving entityliving) {
        return (double)(4.0F + entityliving.width);
    }
}
