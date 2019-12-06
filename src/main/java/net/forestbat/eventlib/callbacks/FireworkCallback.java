package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface FireworkCallback {
    Event<FireworkCallback> FIREWORK_CALLBACK_EVENT= EventFactory.createArrayBacked(FireworkCallback.class,
            callbacks->(world, player,firework) -> {
                for(FireworkCallback callback:callbacks) {
                    if(callback.accept(world,player,firework)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, PlayerEntity player, FireworkItem firework);

}
