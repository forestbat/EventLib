package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public interface ServerWorldTickCallback {
    ActionResult accept(ServerWorld world);
    Event<ServerWorldTickCallback> SERVER_WORLD_CALLBACK= EventFactory.createArrayBacked(ServerWorldTickCallback.class,
        listeners->world -> {
        for(ServerWorldTickCallback callback:listeners){
            if(callback.accept(world)!=ActionResult.FAIL)
                return ActionResult.PASS;
        }
        return ActionResult.PASS;
    });
}
