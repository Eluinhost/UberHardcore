/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.mobs.zombie.CustomZombieSeige
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

package gg.uhc.uberhardcore.nms.v1_9_R1.mobs.zombie;

import net.minecraft.server.v1_9_R1.*;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Iterator;
import java.util.List;

/**
 * Exact copy/paste of VillageSeige but with CustomZombie replacement.
 * Only public method is tick() and it's overwriten so we should be good here
 */
public class CustomZombieSeige extends VillageSiege {
    private World a;
    private boolean b;
    private int c = -1;
    private int d;
    private int e;
    private Village f;
    private int g;
    private int h;
    private int i;

    public CustomZombieSeige(World world) {
        super(world);
    }

    public void a() {
        if(this.a.B()) {
            this.c = 0;
        } else if(this.c != 2) {
            if(this.c == 0) {
                float f = this.a.c(0.0F);
                if((double)f < 0.5D || (double)f > 0.501D) {
                    return;
                }

                this.c = this.a.random.nextInt(10) == 0?1:2;
                this.b = false;
                if(this.c == 2) {
                    return;
                }
            }

            if(this.c != -1) {
                if(!this.b) {
                    if(!this.b()) {
                        return;
                    }

                    this.b = true;
                }

                if(this.e > 0) {
                    --this.e;
                } else {
                    this.e = 2;
                    if(this.d > 0) {
                        this.c();
                        --this.d;
                    } else {
                        this.c = 2;
                    }
                }
            }
        }

    }

    private boolean b() {
        List list = this.a.players;
        Iterator iterator = list.iterator();

        Vec3D var11;
        do {
            do {
                do {
                    do {
                        do {
                            EntityHuman entityhuman;
                            do {
                                if(!iterator.hasNext()) {
                                    return false;
                                }

                                entityhuman = (EntityHuman)iterator.next();
                            } while(entityhuman.isSpectator());

                            this.f = this.a.ai().getClosestVillage(new BlockPosition(entityhuman), 1);
                        } while(this.f == null);
                    } while(this.f.c() < 10);
                } while(this.f.d() < 20);
            } while(this.f.e() < 20);

            BlockPosition blockposition = this.f.a();
            float f = (float)this.f.b();
            boolean flag = false;

            for(int i = 0; i < 10; ++i) {
                float vec3d = this.a.random.nextFloat() * 6.2831855F;
                this.g = blockposition.getX() + (int)((double)(MathHelper.cos(vec3d) * f) * 0.9D);
                this.h = blockposition.getY();
                this.i = blockposition.getZ() + (int)((double)(MathHelper.sin(vec3d) * f) * 0.9D);
                flag = false;
                Iterator iterator1 = this.a.ai().getVillages().iterator();

                while(iterator1.hasNext()) {
                    Village village = (Village)iterator1.next();
                    if(village != this.f && village.a(new BlockPosition(this.g, this.h, this.i))) {
                        flag = true;
                        break;
                    }
                }

                if(!flag) {
                    break;
                }
            }

            if(flag) {
                return false;
            }

            var11 = this.a(new BlockPosition(this.g, this.h, this.i));
        } while(var11 == null);

        this.e = 0;
        this.d = 20;
        return true;
    }

    private boolean c() {
        Vec3D vec3d = this.a(new BlockPosition(this.g, this.h, this.i));
        if(vec3d == null) {
            return false;
        } else {
            CustomZombie entityzombie;
            try {
                entityzombie = new CustomZombie(this.a);
                entityzombie.prepare(this.a.D(new BlockPosition(entityzombie)), (GroupDataEntity)null);
                entityzombie.clearVillagerType();
            } catch (Exception var4) {
                var4.printStackTrace();
                return false;
            }

            entityzombie.setPositionRotation(vec3d.x, vec3d.y, vec3d.z, this.a.random.nextFloat() * 360.0F, 0.0F);
            this.a.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION);
            BlockPosition blockposition = this.f.a();
            entityzombie.a(blockposition, this.f.b());
            return true;
        }
    }

    private Vec3D a(BlockPosition blockposition) {
        for(int i = 0; i < 10; ++i) {
            BlockPosition blockposition1 = blockposition.a(this.a.random.nextInt(16) - 8, this.a.random.nextInt(6) - 3, this.a.random.nextInt(16) - 8);
            if(this.f.a(blockposition1) && SpawnerCreature.a(EntityInsentient.EnumEntityPositionType.ON_GROUND, this.a, blockposition1)) {
                return new Vec3D((double)blockposition1.getX(), (double)blockposition1.getY(), (double)blockposition1.getZ());
            }
        }

        return null;
    }
}
