package net.fabricmc.eventlib.mixins;

import net.fabricmc.eventlib.callbacks.PortalSpawnCallback;
import net.minecraft.block.PortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalBlock.class)
public class MixinPortalBlock {
   @Inject(method = "createPortalAt",at = @At("HEAD"))
   public void beforePortalAt(IWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
       for(PlayerEntity entity:world.getEntities(PlayerEntity.class,Box.from(MutableIntBoundingBox.empty())))
       if(PortalSpawnCallback.SKIN_LOAD_CALLBACK_EVENT.invoker().accept(entity,world)== ActionResult.FAIL)
           cir.cancel();
   }
}
