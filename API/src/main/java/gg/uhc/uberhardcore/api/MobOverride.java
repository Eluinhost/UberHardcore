package gg.uhc.uberhardcore.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MobOverride {

    protected final Class nmsClass;
    protected final Class overrideClass;
    protected final List<Listener> listeners;
    protected Set<CreatureSpawnEvent.SpawnReason> reasonsNotToLog = ImmutableSet.of();

    /**
     * Create new override. If a NMS class is provided an override class must also be provided. An override class must
     * be a subclass of the NMS class.
     *
     * @param nmsClass the NMS class that will be replaced
     * @param overrideClass the class to override with
     * @param listeners the listeners to register to help with the mobs
     * @param <T> the NMS class
     */
    public <T> MobOverride(Class<T> nmsClass, Class<? extends T> overrideClass, List<Listener> listeners) {
        if (nmsClass == null) {
            Preconditions.checkArgument(overrideClass == null, "Cannot provide an override class if not providing an NMS class");
        } else {
            Preconditions.checkNotNull(overrideClass, "Must provide an overriding class if providing an NMS class");
        }

        this.nmsClass = nmsClass;
        this.overrideClass = overrideClass;
        this.listeners = listeners;
    }

    /**
     * @see MobOverride#MobOverride(Class, Class, List)
     */
    public <T> MobOverride(Class<T> nmsClass, Class<? extends T> overrideClass, Listener... listeners) {
        this(nmsClass, overrideClass, Arrays.asList(listeners));
    }

    /**
     * Create a mob override with listeners only, no overrides
     *
     * @see MobOverride#MobOverride(Class, Class, List)
     */
    public MobOverride(Listener... listeners) {
        this(null, null, listeners);
    }

    /**
     * Create a mob override with listeners only, no overrides
     *
     * @see MobOverride#MobOverride(Class, Class, List)
     */
    public MobOverride(List<Listener> listeners) {
        this(null, null, listeners);
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
    public Set<CreatureSpawnEvent.SpawnReason> getReasonsNotToLog() {
        return reasonsNotToLog;
    }

    /**
     * @param reasons spawn reasons not to log if they have invalid spawns, cancel silently.
     */
    public void setReasonsNotToLog(CreatureSpawnEvent.SpawnReason... reasons) {
        this.reasonsNotToLog = ImmutableSet.copyOf(reasons);
    }
}
