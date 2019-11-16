package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public interface PlayerChatCallback {
    TypedActionResult<Text> accept(PlayerEntity entity, World world,Text text);
    public static Event<PlayerChatCallback> SKIN_LOAD_CALLBACK_EVENT = EventFactory.createArrayBacked(PlayerChatCallback.class,
            listeners->(world,player,text)->{
                for(PlayerChatCallback callback:listeners){
                    if(callback.accept(world,player,text).getResult()!=ActionResult.FAIL)
                        return new TypedActionResult<>(ActionResult.PASS,text);
                }
                return new TypedActionResult<>(ActionResult.PASS,text);
            });
}