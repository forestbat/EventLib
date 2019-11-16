package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface OpenContainerCallback {
    Event<OpenContainerCallback> OPEN_CONTAINER_CALLBACK_EVENT= EventFactory.createArrayBacked(OpenContainerCallback.class,
            listeners->player->{
                for(OpenContainerCallback containerCallback:listeners){
                    if(containerCallback.accept(player)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(PlayerEntity playerEntity);
}
