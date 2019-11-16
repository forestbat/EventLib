package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntitySpawnCallback {
    public static Event<EntitySpawnCallback> EVENT_SPAWN_CALLBACK= EventFactory.createArrayBacked(EntitySpawnCallback.class,
            callbacks->(world, player, self, pos) -> true);

    boolean accept(World world, PlayerEntity player, Entity self, BlockPos pos);
}
