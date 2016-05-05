/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R2.mobs.skeleton.CustomSkeleton
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

package gg.uhc.uberhardcore.nms.v1_8_R2.mobs.skeleton;

import gg.uhc.uberhardcore.nms.v1_8_R2.AIUtil;
import net.minecraft.server.v1_8_R2.*;
import org.bukkit.craftbukkit.v1_8_R2.event.CraftEventFactory;
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
        this.getAttributeInstance(GenericAttributes.b).setValue(60);
    }

    // copied from EntitySkeleton with more accurate arrows
    // attackEntityWithRangedAttack
    public void a(EntityLiving entityliving, float f) {
//        EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6F, (float)(14 - this.world.getDifficulty().a() * 4));
        EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6F, 0);
        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, this.bA());
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, this.bA());
        entityarrow.b((double)(f * 2.0F) + this.random.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().a() * 0.11F));
        if(i > 0) {
            entityarrow.b(entityarrow.j() + (double)i * 0.5D + 0.5D);
        }

        if(j > 0) {
            entityarrow.setKnockbackStrength(j);
        }

        if(EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bA()) > 0 || this.getSkeletonType() == 1) {
            EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
            this.world.getServer().getPluginManager().callEvent(event);
            if(!event.isCancelled()) {
                entityarrow.setOnFire(event.getDuration());
            }
        }

        EntityShootBowEvent event1 = CraftEventFactory.callEntityShootBowEvent(this, this.bA(), entityarrow, 0.8F);
        if(event1.isCancelled()) {
            event1.getProjectile().remove();
        } else {
            if(event1.getProjectile() == entityarrow.getBukkitEntity()) {
                this.world.addEntity(entityarrow);
            }

            this.makeSound("random.bow", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
        }
    }
}
