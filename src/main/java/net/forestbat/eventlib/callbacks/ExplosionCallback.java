package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ExplosionCallback {
    Event<ExplosionCallback> EXPLOSION_CALLBACK_EVENT= EventFactory.createArrayBacked(ExplosionCallback.class,
            listeners->(world,pos)->{
                for(ExplosionCallback callback:listeners){
                    if(callback.accept(world,pos)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, BlockPos pos);
}
