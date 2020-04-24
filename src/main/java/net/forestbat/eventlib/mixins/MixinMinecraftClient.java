package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ClientStartCallback;
import net.forestbat.eventlib.callbacks.ClientStopCallback;
import net.forestbat.eventlib.callbacks.GuiScreenCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow @Final private static Logger LOGGER;

    @Shadow public Window window;

    @Shadow public ClientPlayerEntity player;

    @Inject(method = "init",at = @At("RETURN"),cancellable = true)
    public void onClientStart(CallbackInfo ci){
        ClientStartCallback.EVENT.invoker().onClientStart((MinecraftClient)(Object)this);
    }
    @Inject(method = "stop",at=@At("HEAD"),cancellable = true)
    public void onClientStop(CallbackInfo callbackInfo){
        ClientStopCallback.EVENT.invoker().onClientStop((MinecraftClient)(Object)this);
    }
    //todo arguments are too less
    @Inject(method = "openScreen",at=@At("HEAD"),cancellable = true)
    public void beforeScreen(Screen screen, CallbackInfo ci){
        if(screen!=null&&GuiScreenCallback.GUI_SCREEN_CALLBACK_EVENT.invoker().accept(screen)==ActionResult.FAIL)
            ci.cancel();
    }
}
