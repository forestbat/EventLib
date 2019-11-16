package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface RidingCallback {
    ActionResult accept(PlayerEntity player, Entity horse);
    Event<RidingCallback> RIDING_CALLBACK_EVENT= EventFactory.createArrayBacked(RidingCallback.class,listeners->(player, horse) -> {
        for(RidingCallback callback:listeners){
            if(callback.accept(player,horse)!=ActionResult.FAIL)
                return ActionResult.PASS;
        }
        return ActionResult.PASS;
    });
}
