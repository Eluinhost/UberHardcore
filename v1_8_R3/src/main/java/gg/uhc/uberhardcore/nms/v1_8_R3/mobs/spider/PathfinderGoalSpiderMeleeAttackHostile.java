package gg.uhc.uberhardcore.nms.v1_8_R3.mobs.spider;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;

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
