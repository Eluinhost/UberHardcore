/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.mobs.sheep.CustomSheep
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

import gg.uhc.uberhardcore.nms.v1_9_R1.AIUtil;
import net.minecraft.server.v1_9_R1.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomSheep extends EntitySheep {

    protected Method getColourMixMethod;

    public CustomSheep(World world) {
        super(world);

        AIUtil.getInstance().removeAIGoals(this.goalSelector, PathfinderGoalFloat.class, PathfinderGoalRandomStroll.class, PathfinderGoalFollowParent.class);

        this.moveController = new CustomSheepControllerMove(this);
        this.goalSelector.a(5, new PathfinderGoalRandomFly(this));

        try {
            this.getColourMixMethod = EntitySheep.class.getDeclaredMethod("a", EntityAnimal.class, EntityAnimal.class);
            this.getColourMixMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // override createBabySheep to make sure the baby is not an EntitySheep
    @Override
    public EntitySheep b(EntityAgeable ageable)
    {
        EntitySheep entitysheep = (EntitySheep) ageable;
        CustomSheep baby = new CustomSheep(this.world);
        try {
            baby.setColor((EnumColor) getColourMixMethod.invoke(this, this, entitysheep));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return baby;
    }

    // move entity with heading, taken from EntityFlying
    public void g(float var1, float var2) {
        if(this.V()) {
            this.a(var1, var2, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.800000011920929D;
            this.motY *= 0.800000011920929D;
            this.motZ *= 0.800000011920929D;
        } else if(this.ab()) {
            this.a(var1, var2, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
        } else {
            float var3 = 0.91F;
            if(this.onGround) {
                var3 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
            }

            float var4 = 0.16277136F / (var3 * var3 * var3);
            this.a(var1, var2, this.onGround?0.1F * var4:0.02F);
            var3 = 0.91F;
            if(this.onGround) {
                var3 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
            }

            this.move(this.motX, this.motY, this.motZ);
            this.motX *= (double)var3;
            this.motY *= (double)var3;
            this.motZ *= (double)var3;
        }

        this.aA = this.aB;
        double var5 = this.locX - this.lastX;
        double var7 = this.locZ - this.lastZ;
        float var9 = MathHelper.sqrt(var5 * var5 + var7 * var7) * 4.0F;
        if(var9 > 1.0F) {
            var9 = 1.0F;
        }

        this.aB += (var9 - this.aB) * 0.4F;
        this.aC += this.aB;
    }


    // is on ladder, from EntityFlying
    public boolean k_() {
        return false;
    }

    // fall, taken from EntityFlying
    public void e(float var1, float var2) {}

    // update fall state, taken from EnitityFlying
    protected void a(double var1, boolean var3, Block var4, BlockPosition var5) {}
}
