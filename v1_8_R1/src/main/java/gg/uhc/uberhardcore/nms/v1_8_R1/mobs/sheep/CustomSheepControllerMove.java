package gg.uhc.uberhardcore.nms.v1_8_R1.mobs.sheep;

import net.minecraft.server.v1_8_R1.AxisAlignedBB;
import net.minecraft.server.v1_8_R1.ControllerMove;
import net.minecraft.server.v1_8_R1.MathHelper;

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
            double var1 = this.b - this.sheep.locX;
            double var3 = this.c - this.sheep.locY;
            double var5 = this.d - this.sheep.locZ;
            double var7 = var1 * var1 + var3 * var3 + var5 * var5;
            if(this.h-- <= 0) {
                this.h += this.sheep.bb().nextInt(5) + 2;
                var7 = (double)MathHelper.sqrt(var7);
                if(this.b(this.b, this.c, this.d, var7)) {
                    this.sheep.motX += var1 / var7 * 0.1D;
                    this.sheep.motY += var3 / var7 * 0.1D;
                    this.sheep.motZ += var5 / var7 * 0.1D;
                } else {
                    this.f = false;
                }
            }

        }
    }

    private boolean b(double var1, double var3, double var5, double var7) {
        double var9 = (var1 - this.sheep.locX) / var7;
        double var11 = (var3 - this.sheep.locY) / var7;
        double var13 = (var5 - this.sheep.locZ) / var7;
        AxisAlignedBB var15 = this.sheep.getBoundingBox();

        for(int var16 = 1; (double)var16 < var7; ++var16) {
            var15 = var15.c(var9, var11, var13);
            if(!this.sheep.world.getCubes(this.sheep, var15).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
