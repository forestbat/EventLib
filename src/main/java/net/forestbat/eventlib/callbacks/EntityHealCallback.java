package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface EntityHealCallback {
    Event<EntityHealCallback> ENTITY_HEAL_CALLBACK_EVENT= EventFactory.createArrayBacked(
            EntityHealCallback.class,callbacks->(entity,world,health) -> {
                for(EntityHealCallback callback:callbacks)
                    if(callback.accept(entity,world,health)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                return ActionResult.PASS;
            });
    ActionResult accept(LivingEntity entity, World world, float health);
}
