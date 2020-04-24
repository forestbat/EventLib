package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.EnchantmentCallback;
import net.minecraft.container.EnchantingTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableContainer.class)
public class MixinEnchantment {
    @Shadow @Final private Inventory inventory;

    @Inject(method = "onButtonClick",at = @At("HEAD"),cancellable = true)
    public void beforeButtonClick(PlayerEntity playerEntity, int i, CallbackInfoReturnable<Boolean> cir){
        if(EnchantmentCallback.ENCHANTMENT_CALLBACK_EVENT.invoker().accept(playerEntity,
                inventory.getInvStack(0),i)== ActionResult.FAIL)
            cir.cancel();
    }
}
