package gg.uhc.uberhardcore.nms.v1_8_R3.mobs.zombie;

import gg.uhc.uberhardcore.nms.v1_8_R3.AIUtil;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;

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
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4.0D);
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

            EntityInsentient entityinsentient = (EntityInsentient)entityliving;
            EntityZombie entityzombie = new CustomZombie(this.world); // EntityZombie -> CustomZombie
            entityzombie.m(entityliving);
            this.world.kill(entityliving);
            entityzombie.prepare(this.world.E(new BlockPosition(entityzombie)), (GroupDataEntity) null);
            entityzombie.setVillager(true);
            if(entityliving.isBaby()) {
                entityzombie.setBaby(true);
            }

            entityzombie.k(entityinsentient.ce());
            if(entityinsentient.hasCustomName()) {
                entityzombie.setCustomName(entityinsentient.getCustomName());
                entityzombie.setCustomNameVisible(entityinsentient.getCustomNameVisible());
            }

            this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.INFECTION);
            this.world.a((EntityHuman) null, 1016, new BlockPosition((int) this.locX, (int) this.locY, (int) this.locZ), 0);
        }
    }

    // copy from EntityZombie to replace EntityZombie->CustomZombie
    public boolean damageEntity(DamageSource damagesource, float f) {
        if(super.damageEntity(damagesource, f)) {
            EntityLiving entityliving = this.getGoalTarget();
            if(entityliving == null && damagesource.getEntity() instanceof EntityLiving) {
                entityliving = (EntityLiving)damagesource.getEntity();
            }

            if(entityliving != null && this.world.getDifficulty() == EnumDifficulty.HARD && (double)this.random.nextFloat() < this.getAttributeInstance(a).getValue()) {
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
                            this.getAttributeInstance(a).b(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                            entityzombie.getAttributeInstance(a).b(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
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
}
