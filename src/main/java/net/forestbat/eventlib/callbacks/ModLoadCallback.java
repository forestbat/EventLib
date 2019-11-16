package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface ModLoadCallback {
     Event<ModLoadCallback> MOD_LOAD_CALLBACK_EVENT = EventFactory.createArrayBacked(ModLoadCallback.class, listeners ->
            (modid) -> {
                for (ModLoadCallback callback : listeners) {
                    if (callback.accept(modid) != ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.FAIL;
            });

    ActionResult accept(String modid);
}
