package gg.uhc.uberhardcore.nms.v1_8_R3.mobs.creeper;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class ExplosionTask extends BukkitRunnable {

    protected final Location location;
    protected final int radius;
    protected final boolean setFire;

    public ExplosionTask(Location location, int radius, boolean setFire) {
        this.location = location;
        this.radius = radius;
        this.setFire = setFire;
    }

    @Override
    public void run() {
        location.getWorld().createExplosion(location, radius, setFire);
    }
}
