package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;

import javax.crypto.SecretKey;

public interface PreLoginCallback {
    Event<PreLoginCallback> PRE_LOGIN_CALLBACK_EVENT = EventFactory.createArrayBacked(PreLoginCallback.class,
            listeners->(server,key,entity)->{
                for(PreLoginCallback callback:listeners){
                    if(callback.accept(server,key,entity)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(MinecraftServer server, SecretKey key,PlayerEntity entity);
}
