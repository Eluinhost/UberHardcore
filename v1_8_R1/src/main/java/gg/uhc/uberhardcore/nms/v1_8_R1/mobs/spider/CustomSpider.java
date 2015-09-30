package gg.uhc.uberhardcore.nms.v1_8_R1.mobs.spider;

import gg.uhc.uberhardcore.nms.v1_8_R1.AIUtil;
import gg.uhc.uberhardcore.nms.v1_8_R1.mobs.skeleton.CustomSkeleton;
import net.minecraft.server.v1_8_R1.*;
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
        this.getAttributeInstance(GenericAttributes.d).setValue(0.45D);
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
