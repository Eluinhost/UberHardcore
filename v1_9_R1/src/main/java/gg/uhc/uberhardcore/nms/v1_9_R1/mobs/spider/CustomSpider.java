/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.mobs.spider.CustomSpider
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

import gg.uhc.uberhardcore.nms.v1_9_R1.AIUtil;
import gg.uhc.uberhardcore.nms.v1_9_R1.mobs.skeleton.CustomSkeleton;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomSpider extends EntitySpider {

    protected static Class<? extends PathfinderGoal> spiderAttackClass = null;
    protected static Class<? extends PathfinderGoal> spiderTargetClass = null;

    public CustomSpider(World world) {
        super(world);

        if (spiderAttackClass == null) {
            try {
                spiderAttackClass = (Class<? extends PathfinderGoal>) Class.forName(EntitySpider.class.getName() + "$PathfinderGoalSpiderMeleeAttack");
                spiderTargetClass = (Class<? extends PathfinderGoal>) Class.forName(EntitySpider.class.getName() + "$PathfinderGoalSpiderNearestAttackableTarget");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }

        AIUtil ai = AIUtil.getInstance();

        ai.removeAIGoals(this.goalSelector, spiderAttackClass);
        ai.removeAIGoals(this.targetSelector, spiderTargetClass);

        // replace with non-daylight dependant attacks/targetting on players only
        this.goalSelector.a(4, new PathfinderGoalSpiderMeleeAttackHostile(this, EntityPlayer.class));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, true));

        // give a movement speed buff
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.45D);
    }

    // taken from entityspider and change to CustomSkeleton
    @Override
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        GroupDataEntity object = super.prepare(difficultydamagescaler, groupdataentity);

        if(this.world.random.nextInt(100) == 0) {
            CustomSkeleton i = new CustomSkeleton(this.world);
            i.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            i.prepare(difficultydamagescaler, (GroupDataEntity)null);
            this.world.addEntity(i, CreatureSpawnEvent.SpawnReason.JOCKEY);
            i.mount(this);
        }

        // snip the reset of the method to avoid it running twice, this method is just here to make sure there is a chance
        // for skeleton jockeys to appear

        return object;
    }
}
