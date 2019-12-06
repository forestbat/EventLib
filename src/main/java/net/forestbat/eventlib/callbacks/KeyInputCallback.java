package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public interface KeyInputCallback {
    TypedActionResult<Integer> accept(Integer keyCode);
    Event<KeyInputCallback> KEY_INPUT_CALLBACK_EVENT= EventFactory.createArrayBacked(KeyInputCallback.class,
            listeners-> keyCode -> {
        for(KeyInputCallback callback:listeners){
            if(!callback.accept(keyCode).getResult().equals(ActionResult.FAIL))
                return new TypedActionResult<>(ActionResult.PASS,keyCode);
        }
        return new TypedActionResult<>(ActionResult.PASS,keyCode);
    });
}
