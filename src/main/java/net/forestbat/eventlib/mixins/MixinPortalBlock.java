package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.PortalSpawnCallback;
import net.minecraft.block.PortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalBlock.AreaHelper.class)
public class MixinPortalBlock {
   @Final @Shadow
   private IWorld world;
   @Inject(method = "createPortal",at = @At("HEAD"),cancellable = true)
   public void beforeCreatePortal(CallbackInfo ci){
       for(PlayerEntity playerEntity:world.getPlayers())
       if(PortalSpawnCallback.PORTAL_SPAWN_CALLBACK_EVENT.invoker().accept(playerEntity, (World) world)== ActionResult.FAIL)
           ci.cancel();
   }
}
