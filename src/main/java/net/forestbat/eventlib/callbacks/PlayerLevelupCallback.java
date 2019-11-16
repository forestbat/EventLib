package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerLevelupCallback {
    ActionResult accept(PlayerEntity player, int level);

     Event<PlayerLevelupCallback> SKIN_LOAD_CALLBACK_EVENT = EventFactory.createArrayBacked(PlayerLevelupCallback.class,
            listeners -> (player, level) -> {
                for (PlayerLevelupCallback callback : listeners) {
                    if (callback.accept(player, level) != ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
}
