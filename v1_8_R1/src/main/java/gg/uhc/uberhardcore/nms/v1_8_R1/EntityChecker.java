package gg.uhc.uberhardcore.nms.v1_8_R1;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class EntityChecker implements gg.uhc.uberhardcore.api.EntityChecker {
    @Override
    public boolean isEntityOfClassExact(Entity entity, Class klass) {
        return ((CraftEntity) entity).getHandle().getClass() == klass;
    }
}
