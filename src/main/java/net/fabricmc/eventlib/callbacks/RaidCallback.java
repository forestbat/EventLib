package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.Raid;
import net.minecraft.util.ActionResult;

public interface RaidCallback {
    Event<RaidCallback> RAID_CALLBACK_EVENT = EventFactory.createArrayBacked(RaidCallback.class,
            listeners->(raid,player)->{
                for(RaidCallback callback:listeners){
                    if(callback.accept(raid,player)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(Raid raid, PlayerEntity player);
}
