package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.EntityHealCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    LivingEntity itSelf=(LivingEntity)(Object)this;
    @Inject(method = "heal",at=@At("HEAD"),cancellable = true)
    public void beforeHeal(float health, CallbackInfo ci){
        if(EntityHealCallback.ENTITY_HEAL_CALLBACK_EVENT.invoker().accept(itSelf,itSelf.world,health)== ActionResult.FAIL)
            ci.cancel();
    }
}
