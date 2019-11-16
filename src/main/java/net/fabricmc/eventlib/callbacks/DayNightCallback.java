package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface DayNightCallback {
    Event<DayNightCallback> DAY_NIGHT_CALLBACK_EVENT= EventFactory.createArrayBacked(
            DayNightCallback.class,callbacks->(world,player) -> {});
    void accept(World world, PlayerEntity player);
}
