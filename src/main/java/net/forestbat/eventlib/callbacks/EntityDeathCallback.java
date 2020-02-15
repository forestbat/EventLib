package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntityDeathCallback {
     Event<EntityDeathCallback> ENTITY_DEATH_CALLBACK_EVENT= EventFactory.createArrayBacked(EntityDeathCallback.class,
            callbacks->((world, entity, player, pos) -> {
                for(EntityDeathCallback callback:callbacks)
                    if(callback.accept(world,entity,player,pos)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                return ActionResult.PASS;
            }));
     ActionResult accept(World world, Entity entity, PlayerEntity player, BlockPos pos);
}
