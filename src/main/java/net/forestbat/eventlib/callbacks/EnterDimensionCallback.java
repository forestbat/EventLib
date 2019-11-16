package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

public interface EnterDimensionCallback {
    Event<EnterDimensionCallback> ENTER_DIMENSION_CALLBACK_EVENT= EventFactory.createArrayBacked(
            EnterDimensionCallback.class,callbacks->((dimension, entity, world, dimensionType) -> {
                for(EnterDimensionCallback callback:callbacks)
                    if(callback.enterDimension(dimension,entity,world,dimensionType)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                    return ActionResult.FAIL;
            })
    );
    ActionResult enterDimension(Dimension dimension, Entity entity, World world, DimensionType dimensionType);
}
