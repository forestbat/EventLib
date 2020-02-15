package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface PortalSpawnCallback {
    ActionResult accept(PlayerEntity playerEntity, World world);
    Event<PortalSpawnCallback> PORTAL_SPAWN_CALLBACK_EVENT = EventFactory.createArrayBacked(PortalSpawnCallback.class,
            listeners->(player,world)->{
                for(PortalSpawnCallback callback:listeners){
                    if(callback.accept(player,world)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
}
