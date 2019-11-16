package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface WeatherCallback {
    Event<WeatherCallback> WEATHER_CALLBACK_EVENT= EventFactory.createArrayBacked(WeatherCallback.class,
            listeners->(world,player,pos)->{
                for(WeatherCallback callback:listeners){
                    if(callback.accept(world,player,pos)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, PlayerEntity playerEntity, BlockPos pos);
}
