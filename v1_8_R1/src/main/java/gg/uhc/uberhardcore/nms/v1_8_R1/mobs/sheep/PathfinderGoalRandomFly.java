/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R1.mobs.sheep.PathfinderGoalRandomFly
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
