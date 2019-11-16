package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntityDeathCallback {
    public static Event<EntityDeathCallback> ENTITY_DEATH_CALLBACK_EVENT= EventFactory.createArrayBacked(EntityDeathCallback.class,
            callbacks->((world, entity, playerEntity, pos) -> true));
    public boolean accept(World world, Entity entity, PlayerEntity playerEntity, BlockPos pos);
}
