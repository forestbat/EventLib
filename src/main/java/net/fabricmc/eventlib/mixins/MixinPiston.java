package net.fabricmc.eventlib.mixins;

import net.minecraft.block.PistonBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class MixinPiston {
    //todo It should be more complex
    @Inject(method = "shouldExtend", at = @At("HEAD"))
    public void beforeExtend(World world, BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockEntity(blockPos) instanceof BlockEntity)
            if (PistonCallback.PISTON_CALLBACK_EVENT.invoker().accept((PistonBlockEntity)world.getBlockEntity(blockPos)) == ActionResult.FAIL)
                cir.cancel();
    }
}
