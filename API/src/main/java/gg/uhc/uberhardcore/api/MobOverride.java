/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.api.MobOverride
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

package gg.uhc.uberhardcore.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Set;

public class MobOverride {

    protected Class nmsClass = null;
    protected Class overrideClass = null;
    protected List<Listener> listeners = ImmutableList.of();
    protected Set<CreatureSpawnEvent.SpawnReason> suppressedInvalidSpawnReasons = ImmutableSet.of();

    public <T> MobOverride withOverridingClasses(Class<T> nmsClass, Class<? extends T> overrideClass) {
        this.nmsClass = nmsClass;
        this.overrideClass = overrideClass;
        return this;
    }

    public MobOverride withListeners(Listener... listeners) {
        this.listeners = ImmutableList.copyOf(listeners);
        return this;
    }

    public MobOverride withSupressedInvalidSpawnReasons(CreatureSpawnEvent.SpawnReason... reasons) {
        this.suppressedInvalidSpawnReasons = ImmutableSet.copyOf(reasons);
        return this;
    }

    /**
     * @return the class of the NMS object
     */
    public Class getNmsClass() {
        return nmsClass;
    }

    /**
     * @return the class to replace the NMS class with
     */
    public Class getOverrideClass() {
        return overrideClass;
    }

    /**
     * @return all listeners to register with Bukkit to help control the mob
     */
    public List<Listener> getListeners() {
        return listeners;
    }

    /**
     * @return whether the mob requires class replacing or not
     */
    public boolean isRunningClassReplace() {
        return nmsClass != null;
    }

    /**
     * @return spawn reasons not to log if they have invalid spawns, cancel silently.
     */
    public Set<CreatureSpawnEvent.SpawnReason> getSuppressedInvalidSpawnReasons() {
        return suppressedInvalidSpawnReasons;
    }
}
