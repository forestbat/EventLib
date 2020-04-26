package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ChunkLoadCallback;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.ChunkDataS2CPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayHandler {
    @Shadow private ClientWorld world;

    @Inject(method = "onChunkData",at = @At("RETURN"),cancellable = true)
    public void afterChunkData(ChunkDataS2CPacket chunkDataS2CPacket, CallbackInfo ci){
        if(ChunkLoadCallback.CHUNK_LOAD_CALLBACK_EVENT.invoker().accept((WorldChunk)world.getChunk(chunkDataS2CPacket.getX(),
                chunkDataS2CPacket.getZ()))== ActionResult.FAIL)
            ci.cancel();
    }
}
