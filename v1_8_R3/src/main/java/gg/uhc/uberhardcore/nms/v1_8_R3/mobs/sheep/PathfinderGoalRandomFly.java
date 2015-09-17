package gg.uhc.uberhardcore.nms.v1_8_R3.mobs.sheep;

import net.minecraft.server.v1_8_R3.ControllerMove;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

import java.util.Random;

// taken from entity ghast and modified
public class PathfinderGoalRandomFly extends PathfinderGoal {
    private CustomSheep sheep;

    public PathfinderGoalRandomFly(CustomSheep sheep) {
        this.sheep = sheep;
        this.a(1);
    }

    public boolean a() {
        ControllerMove controllermove = this.sheep.getControllerMove();
        if(!controllermove.a()) {
            return true;
        } else {
            double d0 = controllermove.d() - this.sheep.locX;
            double d1 = controllermove.e() - this.sheep.locY;
            double d2 = controllermove.f() - this.sheep.locZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
        }
    }

    public boolean b() {
        return false;
    }

    public void c() {
        Random random = this.sheep.bc();
        double d0 = this.sheep.locX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.sheep.locY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.sheep.locZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.sheep.getControllerMove().a(d0, d1, d2, 1.0D);
    }
}
