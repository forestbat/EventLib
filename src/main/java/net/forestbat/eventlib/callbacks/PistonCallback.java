package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.ActionResult;

public interface PistonCallback {
    ActionResult accept(PistonBlockEntity piston);
    Event<PistonCallback> PISTON_CALLBACK_EVENT= EventFactory.createArrayBacked(PistonCallback.class,listeners->piston -> {
        for(PistonCallback callback:listeners){
            if(callback.accept(piston)!=ActionResult.FAIL)
                return ActionResult.PASS;
        }
        return ActionResult.PASS;
    });
}
