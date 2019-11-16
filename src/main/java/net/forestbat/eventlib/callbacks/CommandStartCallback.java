package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.ActionResult;

public interface CommandStartCallback {
    ActionResult accept(ServerCommandSource source,String command);
    Event<CommandStartCallback> COMMAND_START_CALLBACK_EVENT= EventFactory.createArrayBacked(CommandStartCallback.class,
            listeners->(source,command)->{
                for(CommandStartCallback callback:listeners){
                    if(callback.accept(source,command)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
}
