package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.BlockEntityConstructCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class MixinBlockEntity {
    @Shadow protected BlockPos pos;

    @Shadow protected World world;

    @Inject(method = "<init>",at = @At("RETURN"),cancellable = true)
    public void beforeInit(BlockEntityType<? extends BlockEntity> blockEntityType, CallbackInfo ci){
        if(BlockEntityConstructCallback.BLOCK_ENTITY_CONSTRUCT_CALLBACK_EVENT.invoker().accept(blockEntityType,pos,world)== ActionResult.FAIL)
            ci.cancel();
    }
}
