package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntitySpawnCallback {
     Event<EntitySpawnCallback> EVENT_SPAWN_CALLBACK= EventFactory.createArrayBacked(EntitySpawnCallback.class,
            callbacks->(world, player, self, pos) -> {
                for(EntitySpawnCallback callback:callbacks)
                    if(callback.accept(world,player,self,pos)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                return ActionResult.PASS;
            });

    ActionResult accept(World world, PlayerEntity player, Entity self, BlockPos pos);
}
