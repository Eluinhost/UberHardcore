package gg.uhc.uberhardcore.nms.v1_8_R2.mobs.spider;

import gg.uhc.uberhardcore.nms.v1_8_R2.AIUtil;
import net.minecraft.server.v1_8_R2.*;

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
}
