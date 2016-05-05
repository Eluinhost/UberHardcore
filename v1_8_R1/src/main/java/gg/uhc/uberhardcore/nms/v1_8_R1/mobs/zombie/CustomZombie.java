/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R1.mobs.zombie.CustomZombie
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

package gg.uhc.uberhardcore.nms.v1_8_R1.mobs.zombie;

import gg.uhc.uberhardcore.nms.v1_8_R1.AIUtil;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.chicken.CustomChicken;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.List;

public class CustomZombie extends EntityZombie {
    public CustomZombie(World world) {
        super(world);

        this.fireProof = true;

        AIUtil ai = AIUtil.getInstance();

        ai.removeAIGoals(this.goalSelector,
                PathfinderGoalMeleeAttack.class, // readd later with different values
                PathfinderGoalMoveThroughVillage.class,
                PathfinderGoalRandomStroll.class // readd later with different values
        );

        ai.removeAIGoals(this.targetSelector, PathfinderGoalNearestAttackableTarget.class); // readd later with different values

        // slow stroll
        this.targetSelector.a(7, new PathfinderGoalRandomStroll(this, .5D));

        // leap at targets
        this.goalSelector.a(1, new PathfinderGoalLeapAtTarget(this, .4F));

        // agro on players with slightly faster move speed, always keep target
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityPlayer.class, 1.2D, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, false));

        // buff base damage and health
        this.getAttributeInstance(GenericAttributes.e).setValue(4.0D);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0D);
    }

    // copy/pasted from EntityZombie
    // stops EntityZombie being created on villager conversion
    public void a(EntityLiving entityliving) {
        super.a(entityliving);
        if((this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) && entityliving instanceof EntityVillager) {
            if(this.world.getDifficulty() != EnumDifficulty.HARD && this.random.nextBoolean()) {
                return;
            }

            EntityZombie entityzombie = new CustomZombie(this.world);
            entityzombie.m(entityliving);
            this.world.kill(entityliving);
            entityzombie.prepare(this.world.E(new BlockPosition(entityzombie)), (GroupDataEntity) null);
            entityzombie.setVillager(true);
            if(entityliving.isBaby()) {
                entityzombie.setBaby(true);
            }

            this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.INFECTION);
            this.world.a((EntityHuman)null, 1016, new BlockPosition((int)this.locX, (int)this.locY, (int)this.locZ), 0);
        }
    }

    // copy from EntityZombie to replace EntityZombie->CustomZombie
    public boolean damageEntity(DamageSource damagesource, float f) {
        if(super.damageEntity(damagesource, f)) {
            EntityLiving entityliving = this.getGoalTarget();
            if(entityliving == null && damagesource.getEntity() instanceof EntityLiving) {
                entityliving = (EntityLiving)damagesource.getEntity();
            }

            if(entityliving != null && this.world.getDifficulty() == EnumDifficulty.HARD && (double)this.random.nextFloat() < this.getAttributeInstance(b).getValue()) {
                int i = MathHelper.floor(this.locX);
                int j = MathHelper.floor(this.locY);
                int k = MathHelper.floor(this.locZ);
                EntityZombie entityzombie = new CustomZombie(this.world);

                for(int l = 0; l < 50; ++l) {
                    int i1 = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int j1 = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int k1 = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    if(World.a(this.world, new BlockPosition(i1, j1 - 1, k1)) && this.world.getLightLevel(new BlockPosition(i1, j1, k1)) < 10) {
                        entityzombie.setPosition((double)i1, (double)j1, (double)k1);
                        if(!this.world.isPlayerNearby((double)i1, (double)j1, (double)k1, 7.0D) && this.world.a(entityzombie.getBoundingBox(), entityzombie) && this.world.getCubes(entityzombie, entityzombie.getBoundingBox()).isEmpty() && !this.world.containsLiquid(entityzombie.getBoundingBox())) {
                            this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.REINFORCEMENTS);
                            entityzombie.setGoalTarget(entityliving, EntityTargetEvent.TargetReason.REINFORCEMENT_TARGET, true);
                            entityzombie.prepare(this.world.E(new BlockPosition(entityzombie)), (GroupDataEntity)null);
                            this.getAttributeInstance(b).b(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                            entityzombie.getAttributeInstance(b).b(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                            break;
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    // give chance for chicken jockey with correct chicken if zombie is a baby
    // taken from EntityZombie and changed to CustomChicken
    @Override
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        GroupDataEntity object = super.prepare(difficultydamagescaler, groupdataentity);

        if (this.isBaby()) {
            if ((double) this.world.random.nextFloat() < 0.05D) {
                List entitychicken1 = this.world.a(CustomChicken.class, this.getBoundingBox().grow(5.0D, 3.0D, 5.0D), IEntitySelector.b);
                if (!entitychicken1.isEmpty()) {
                    CustomChicken entitychicken = (CustomChicken) entitychicken1.get(0);
                    entitychicken.l(true);
                    this.mount(entitychicken);
                }
            } else if ((double) this.world.random.nextFloat() < 0.05D) {
                CustomChicken entitychicken11 = new CustomChicken(this.world);
                entitychicken11.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
                entitychicken11.prepare(difficultydamagescaler, (GroupDataEntity) null);
                entitychicken11.l(true);
                this.world.addEntity(entitychicken11, CreatureSpawnEvent.SpawnReason.MOUNT);
                this.mount(entitychicken11);
            }
        }

        // snip the reset of the method to avoid it running twice, this method is just here to make sure there is a chance
        // for zombie jockeys to appear

        return object;
    }
}
