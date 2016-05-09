/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R3.mobs.sheep.PathfinderGoalRandomFly
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

import net.minecraft.server.v1_9_R1.ControllerMove;
import net.minecraft.server.v1_9_R1.PathfinderGoal;

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
