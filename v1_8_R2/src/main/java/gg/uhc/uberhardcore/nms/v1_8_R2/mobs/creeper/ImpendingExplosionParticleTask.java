package gg.uhc.uberhardcore.nms.v1_8_R2.mobs.creeper;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

public class ImpendingExplosionParticleTask extends BukkitRunnable {

    protected final Location location;
    protected final int count;

    public ImpendingExplosionParticleTask(Location location, int count) {
        this.location = location;
        this.count = count;
    }

    @Override
    public void run() {
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();

        world.sendParticles(
                null,
                EnumParticle.SMOKE_LARGE,
                false,
                location.getX(),
                location.getY(),
                location.getZ(),
                count,
                1D,
                1D,
                1D,
                0
        );
    }
}
