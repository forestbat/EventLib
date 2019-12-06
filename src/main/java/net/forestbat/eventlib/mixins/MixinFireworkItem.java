package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.FireworkCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkItem.class)
public class MixinFireworkItem {
    @Inject(method = "use",at=@At("HEAD"),cancellable = true)
    public void beforeUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(FireworkCallback.FIREWORK_CALLBACK_EVENT.invoker().accept(world,player,(FireworkItem)(Object)this)== ActionResult.FAIL)
            cir.cancel();
    }
}
