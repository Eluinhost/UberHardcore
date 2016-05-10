/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_9_R1.mobs.zombie.WrappedGroupDataZombie
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

import net.minecraft.server.v1_9_R1.GroupDataEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class WrappedGroupDataZombie {
    protected final GroupDataEntity handle;

    protected static Class<? extends GroupDataEntity> klass;
    protected static Constructor<GroupDataEntity> constructor;
    protected static Field field1;
    protected static Field field2;

    @SuppressWarnings("unchecked")
    public static void initialize() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
        klass = (Class<? extends GroupDataEntity>) Class.forName("net.minecraft.server.v1_9_R1.EntityZombie$GroupDataZombie");
        constructor = (Constructor<GroupDataEntity>) klass.getDeclaredConstructor(Boolean.class, Boolean.class);
        field1 = klass.getDeclaredField("a");
        field2 = klass.getDeclaredField("b");
    }

    public WrappedGroupDataZombie(GroupDataEntity data) {
        this.handle = data;

        if (!isInstanceOf(data)) {
            throw new IllegalArgumentException();
        }
    }

    public WrappedGroupDataZombie(boolean flag1, boolean flag2) {
        try {
            handle = constructor.newInstance(flag1, flag2);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public GroupDataEntity getHandle() {
        return handle;
    }

    public boolean getA() {
        try {
            return (boolean) field1.get(handle);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public boolean getB() {
        try {
            return (boolean) field1.get(handle);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public static boolean isInstanceOf(Object other) {
        return klass.isAssignableFrom(other.getClass());
    }
}
