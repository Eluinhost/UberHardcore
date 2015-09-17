package gg.uhc.uberhardcore.nms.v1_8_R3.mobs.skeleton;

import gg.uhc.uberhardcore.nms.v1_8_R3.AIUtil;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
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
