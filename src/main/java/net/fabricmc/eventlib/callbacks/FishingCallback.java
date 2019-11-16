package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.loot.context.LootContextType;
import net.minecraft.world.loot.context.LootContextTypes;

public interface FishingCallback {
    Event<FishingCallback> FISHING_CALLBACK_EVENT= EventFactory.createArrayBacked(FishingCallback.class,
            callbacks->((world, player) -> {
                for(FishingCallback callback:callbacks) {
                    if(callback.fishing(world,player).getResult()!=ActionResult.FAIL)
                    return new TypedActionResult<>(ActionResult.PASS, LootContextTypes.FISHING);
                }
                return new TypedActionResult<>(ActionResult.FAIL,null);
            }));
    TypedActionResult<LootContextType> fishing(World world, PlayerEntity player);
}
