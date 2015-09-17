package gg.uhc.uberhardcore.nms.v1_8_R1.mobs.sheep;

import net.minecraft.server.v1_8_R1.ControllerMove;
import net.minecraft.server.v1_8_R1.PathfinderGoal;

import java.util.Random;

// taken from entity ghast and modified
public class PathfinderGoalRandomFly extends PathfinderGoal {
    private CustomSheep sheep;

    public PathfinderGoalRandomFly(CustomSheep sheep) {
        this.sheep = sheep;
        this.a(1);
    }

    public boolean a() {
        ControllerMove var1 = this.sheep.getControllerMove();
        if(!var1.a()) {
            return true;
        } else {
            double var2 = var1.d() - this.sheep.locX;
            double var4 = var1.e() - this.sheep.locY;
            double var6 = var1.f() - this.sheep.locZ;
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;
            return var8 < 1.0D || var8 > 3600.0D;
        }
    }

    public boolean b() {
        return false;
    }

    public void c() {
        Random var1 = this.sheep.bb();
        double var2 = this.sheep.locX + (double)((var1.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double var4 = this.sheep.locY + (double)((var1.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double var6 = this.sheep.locZ + (double)((var1.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.sheep.getControllerMove().a(var2, var4, var6, 1.0D);
    }
}
