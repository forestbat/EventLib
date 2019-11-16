package net.fabricmc.eventlib.mixins;

import net.fabricmc.eventlib.callbacks.EnchantmentCallback;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class MixinEnchantment {
    @Shadow
    private CompoundTag tag;

    @Inject(method="addEnchantment",at=@At("RETURN"))
    public void beforeEnchantment(Enchantment enchantment, int id, CallbackInfo info){
        if(EnchantmentCallback.ENTER_DIMENSION_CALLBACK_EVENT.invoker().accept(enchantment,(ItemStack)(Object)this)== ActionResult.FAIL)
            info.cancel();
    }
}
