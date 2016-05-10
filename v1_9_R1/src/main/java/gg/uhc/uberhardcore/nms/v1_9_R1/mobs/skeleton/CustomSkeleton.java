/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.mobs.skeleton.CustomSkeleton
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

package gg.uhc.uberhardcore.nms.v1_9_R1.mobs.skeleton;

import gg.uhc.uberhardcore.nms.v1_9_R1.AIUtil;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.craftbukkit.v1_9_R1.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class CustomSkeleton extends EntitySkeleton {

    public CustomSkeleton(World world) {
        super(world);

        AIUtil ai = AIUtil.getInstance();

        if (this.random.nextFloat() < 0.3F) {
            // make it a wither skeleton
            // must happen before AI removal as it changes the AI
            this.setSkeletonType(1);
        }

        // remove both types of attack AI
        ai.removeAIGoals(this.goalSelector, PathfinderGoalMeleeAttack.class, PathfinderGoalArrowAttack.class);

        // readd the arrow attacks with longer range + faster attacks at range
        this.goalSelector.a(4, new PathfinderGoalArrowAttack(this, 1.0D, 20, 30, 40.0F));

        // increase base follow range
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(60);
    }
}
