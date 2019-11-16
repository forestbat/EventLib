package net.fabricmc.eventlib.mixins;

import net.fabricmc.eventlib.callbacks.ClientStartCallback;
import net.fabricmc.eventlib.callbacks.ClientStopCallback;
import net.fabricmc.eventlib.callbacks.KeyInputCallback;
import net.fabricmc.eventlib.packets.KeyBindingPacket;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
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

    @Inject(method = "init",at = @At("HEAD"))
    public void onClientStart(CallbackInfo ci){
        ClientStartCallback.EVENT.invoker().onClientStart((MinecraftClient)(Object)this);
        LOGGER.info("Client Started!");
    }
    @Inject(method = "stop",at=@At("HEAD"))
    public void onClientStop(CallbackInfo callbackInfo){
        ClientStopCallback.EVENT.invoker().onClientStop((MinecraftClient)(Object)this);
        LOGGER.info("Client Shutdown!");
    }
    @Inject(method = "handleInputEvents",at=@At("HEAD"))
    public void beforeHandleInput(CallbackInfo ci){
        for(int key=0;key<104;key++) {
            int keyCode=GLFW.glfwGetKey(window.getHandle(),key);
            if (KeyInputCallback.KEY_INPUT_CALLBACK_EVENT.invoker().accept(keyCode).getResult() != ActionResult.FAIL) {
                ClientSidePacketRegistry.INSTANCE.sendToServer(new KeyBindingPacket().setKeyCode(keyCode));
            }
            else ci.cancel();
        }
    }
}
