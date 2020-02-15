package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.TamedEntityCallback;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TameableEntity.class)
public class MixinTameableEntity {
    TameableEntity entity=(TameableEntity)(Object)this;
    @Inject(method = "setTamed",at=@At("HEAD"),cancellable = true)
    public void beforeTamed(boolean tamed, CallbackInfo ci){
        if(TamedEntityCallback.TAMED_ENTITY_CALLBACK_EVENT.invoker().accept(entity.world,(PlayerEntity)entity.getOwner(),entity)== ActionResult.FAIL)
            ci.cancel();
    }
}
