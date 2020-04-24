package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface RecipeCallback {
    Event<RecipeCallback> RECIPE_CALLBACK_EVENT = EventFactory.createArrayBacked(RecipeCallback.class,
            listeners->(world,player,itemStack)->{
                for(RecipeCallback callback:listeners){
                    if(callback.accept(world,player,itemStack)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, PlayerEntity player, ItemStack itemStack);
}
