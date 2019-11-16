package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.IWorld;

public interface PortalSpawnCallback {
    ActionResult accept(PlayerEntity playerEntity, IWorld world);
    Event<PortalSpawnCallback> SKIN_LOAD_CALLBACK_EVENT = EventFactory.createArrayBacked(PortalSpawnCallback.class,
            listeners->(player,world)->{
                for(PortalSpawnCallback callback:listeners){
                    if(callback.accept(player,world)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
}
