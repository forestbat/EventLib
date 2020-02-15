package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntityTeleportCallback {
    Event<EntityTeleportCallback> ENTITY_TELEPORT_CALLBACK_EVENT = EventFactory.createArrayBacked(EntityTeleportCallback.class,
            listeners->(entity,startPos,destPos,startWorld,destWorld)->{
                for(EntityTeleportCallback callback:listeners){
                    if(callback.accept(entity,startPos,destPos,startWorld,destWorld)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(Entity entity, BlockPos startPos, BlockPos destPos, World startWorld,World destWorld);

}
