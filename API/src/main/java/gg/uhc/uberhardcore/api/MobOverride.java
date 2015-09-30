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
