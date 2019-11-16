package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface EntityMoveCallback {
     Event<EntityMoveCallback> ENTITY_MOVE_CALLBACK_EVENT = EventFactory.createArrayBacked(EntityMoveCallback.class,
            listeners->(world,entity,direction)->{
                for(EntityMoveCallback callback:listeners){
                    if(callback.accept(world,entity,direction)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, Entity self, Vec3d direction);
}
