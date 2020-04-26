package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.BlockEntityConstructCallback;
import net.forestbat.eventlib.callbacks.ChunkLoadCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
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
    @Inject(method = "setBlockEntity",at=@At("RETURN"),cancellable = true)
    public void beforeSetBlockEntity(BlockPos blockPos, BlockEntity blockEntity, CallbackInfo ci){
        if(BlockEntityConstructCallback.BLOCK_ENTITY_CONSTRUCT_CALLBACK_EVENT.invoker().
                accept(blockEntity,blockPos,blockEntity.getWorld())==ActionResult.FAIL)
            ci.cancel();
    }
}
