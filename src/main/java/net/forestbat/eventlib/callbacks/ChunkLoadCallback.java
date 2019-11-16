package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.world.chunk.WorldChunk;

public interface ChunkLoadCallback {
    ActionResult accept(WorldChunk chunk);
    Event<ChunkLoadCallback> CHUNK_LOAD_CALLBACK_EVENT= EventFactory.createArrayBacked(ChunkLoadCallback.class,
            listeners->chunk->{
                for(ChunkLoadCallback callback:listeners){
                    if(callback.accept(chunk)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
}
