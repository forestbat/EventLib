package net.fabricmc.eventlib.mixins;

import net.fabricmc.eventlib.callbacks.CommandStartCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandManager.class)
public class MixinCommandManager {
    @Inject(method = "execute",at=@At("HEAD"))
    public void beforeExecute(ServerCommandSource source, String command, CallbackInfoReturnable<Integer> cir){
        if(CommandStartCallback.COMMAND_START_CALLBACK_EVENT.invoker().accept(source,command)== ActionResult.FAIL)
            cir.cancel();
    }
}
