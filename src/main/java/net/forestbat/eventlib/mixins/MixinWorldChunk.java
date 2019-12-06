package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ChunkLoadCallback;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldChunk.class)
public class MixinWorldChunk {
    //FIXME
    @Shadow
    private boolean loadedToWorld;
    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/ProtoChunk;)V",at=@At("RETURN"),cancellable = true)
    public void onChunkConstruct(World world_1, ProtoChunk protoChunk_1, CallbackInfo ci){
        if(ChunkLoadCallback.CHUNK_LOAD_CALLBACK_EVENT.invoker().accept((WorldChunk)(Object)this)== ActionResult.FAIL)
            loadedToWorld=false;
    }
}
