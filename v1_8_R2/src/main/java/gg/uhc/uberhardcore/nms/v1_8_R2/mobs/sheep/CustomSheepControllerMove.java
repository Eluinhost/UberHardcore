package gg.uhc.uberhardcore.nms.v1_8_R2.mobs.sheep;

import net.minecraft.server.v1_8_R2.AxisAlignedBB;
import net.minecraft.server.v1_8_R2.ControllerMove;
import net.minecraft.server.v1_8_R2.MathHelper;

// copied from entity ghast and renamed for the sheep
public class CustomSheepControllerMove extends ControllerMove {

    private CustomSheep sheep;
    private int h;

    public CustomSheepControllerMove(CustomSheep sheep) {
        super(sheep);
        this.sheep = sheep;
    }

    public void c() {
        if(this.f) {
            double d0 = this.b - this.sheep.locX;
            double d1 = this.c - this.sheep.locY;
            double d2 = this.d - this.sheep.locZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if(this.h-- <= 0) {
                this.h += this.sheep.bc().nextInt(5) + 2;
                d3 = (double) MathHelper.sqrt(d3);
                if(this.b(this.b, this.c, this.d, d3)) {
                    this.sheep.motX += d0 / d3 * 0.1D;
                    this.sheep.motY += d1 / d3 * 0.1D;
                    this.sheep.motZ += d2 / d3 * 0.1D;
                } else {
                    this.f = false;
                }
            }
        }

    }

    private boolean b(double d0, double d1, double d2, double d3) {
        double d4 = (d0 - this.sheep.locX) / d3;
        double d5 = (d1 - this.sheep.locY) / d3;
        double d6 = (d2 - this.sheep.locZ) / d3;
        AxisAlignedBB axisalignedbb = this.sheep.getBoundingBox();

        for(int i = 1; (double)i < d3; ++i) {
            axisalignedbb = axisalignedbb.c(d4, d5, d6);
            if(!this.sheep.world.getCubes(this.sheep, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
