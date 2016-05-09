/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R3.mobs.sheep.CustomSheepControllerMove
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

package gg.uhc.uberhardcore.nms.v1_9_R1.mobs.sheep;

import net.minecraft.server.v1_9_R1.AxisAlignedBB;
import net.minecraft.server.v1_9_R1.ControllerMove;
import net.minecraft.server.v1_9_R1.MathHelper;

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
