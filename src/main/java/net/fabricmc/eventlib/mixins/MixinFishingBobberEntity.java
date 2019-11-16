package net.fabricmc.eventlib.mixins;

import net.fabricmc.eventlib.callbacks.FishingCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class MixinFishingBobberEntity {
    @Shadow @Final private PlayerEntity owner;

    @Inject(method = "method_6957",at=@At("HEAD"))
    public void beforeUse(ItemStack itemStack_1, CallbackInfoReturnable<Integer> cir){
        if(FishingCallback.FISHING_CALLBACK_EVENT.invoker().fishing(this.owner.world,this.owner).getResult()== ActionResult.FAIL)
            cir.cancel();
    }
}
