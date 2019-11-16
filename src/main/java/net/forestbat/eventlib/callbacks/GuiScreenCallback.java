package net.forestbat.eventlib.callbacks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public interface GuiScreenCallback {
    Event<GuiScreenCallback> GUI_SCREEN_CALLBACK_EVENT= EventFactory.createArrayBacked(GuiScreenCallback.class,
            callbacks->screen -> {
                for(GuiScreenCallback callback:callbacks) {
                    if(callback.accept(screen)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(Screen screen);
}
