package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface TamedEntityCallback {
    public static Event<TamedEntityCallback> TAMED_ENTITY_CALLBACK_EVENT = EventFactory.createArrayBacked(TamedEntityCallback.class,
            listeners->(world,player,entity)->{
                for(TamedEntityCallback callback:listeners){
                    if(callback.accept(world,player,entity)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, PlayerEntity playerEntity, TameableEntity entity);
}
