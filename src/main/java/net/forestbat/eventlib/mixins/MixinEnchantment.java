package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.EnchantmentCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class MixinEnchantment {
    @Shadow
    private CompoundTag tag;

    @Inject(method="addEnchantment",at=@At("RETURN"),cancellable = true)
    public void beforeEnchantment(Enchantment enchantment, int id, CallbackInfo info){
        if(EnchantmentCallback.ENCHANTMENT_CALLBACK_EVENT.invoker().accept(enchantment,(ItemStack)(Object)this)== ActionResult.FAIL)
            info.cancel();
    }
}
