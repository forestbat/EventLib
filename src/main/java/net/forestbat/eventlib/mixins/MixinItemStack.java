package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.RecipeCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class MixinItemStack {
    @Inject(method = "onCraft",at=@At("HEAD"),cancellable = true)
    public void beforeCraft(World world, PlayerEntity playerEntity, int amount, CallbackInfo ci){
        if(RecipeCallback.RECIPE_CALLBACK_EVENT.invoker().accept(world,playerEntity,(ItemStack)(Object)this)== ActionResult.FAIL)
            ci.cancel();
    }
}
