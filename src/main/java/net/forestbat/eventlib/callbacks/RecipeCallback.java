package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface RecipeCallback {
    Event<RecipeCallback> RECIPE_CALLBACK_EVENT = EventFactory.createArrayBacked(RecipeCallback.class,
            listeners->(slot,world,player)->{
                for(RecipeCallback callback:listeners){
                    if(callback.accept(slot,world,player)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(int slot, World world,PlayerEntity player);
}
