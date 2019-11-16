package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public interface PlayerSleepCallback {
    ActionResult accept(PlayerEntity player, BlockPos bedPos,long time);
    public static Event<PlayerSleepCallback> PLAYER_SLEEP_CALLBACK_EVENT= EventFactory.createArrayBacked(PlayerSleepCallback.class,listeners->(
            (player, bedPos, time) ->{
                for(PlayerSleepCallback callback:listeners){
                    if(callback.accept(player,bedPos,time)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            } ));
}
