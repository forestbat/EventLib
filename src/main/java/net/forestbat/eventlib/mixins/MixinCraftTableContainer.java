package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.RecipeCallback;
import net.minecraft.container.CraftingTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingTableContainer.class)
public class MixinCraftTableContainer {
    @Inject(method = "updateResult",at=@At("HEAD"))
    private static void onUpdateResult(int slot, World world, PlayerEntity player, CraftingInventory craftingInventory,
                                       CraftingResultInventory craftingResultInventory, CallbackInfo ci){
        if(RecipeCallback.RECIPE_CALLBACK_EVENT.invoker().accept(slot, world, player)== ActionResult.FAIL)
            ci.cancel();
    }
}
