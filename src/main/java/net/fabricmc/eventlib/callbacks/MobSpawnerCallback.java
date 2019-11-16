package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface MobSpawnerCallback {
    Event<MobSpawnerCallback> MOB_SPAWNER_CALLBACK_EVENT= EventFactory.createArrayBacked(MobSpawnerCallback.class,
            callbacks->(world, player,mob) -> {
                for(MobSpawnerCallback callback:callbacks) {
                    if(callback.accept(world,player,mob)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, PlayerEntity playerEntity, Entity mobEntity);
}
