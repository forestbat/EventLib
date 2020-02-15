package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ClientStartCallback;
import net.forestbat.eventlib.callbacks.ClientStopCallback;
import net.forestbat.eventlib.callbacks.GuiScreenCallback;
import net.forestbat.eventlib.callbacks.KeyInputCallback;
import net.forestbat.eventlib.packets.KeyBindingPacket;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(value = MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow @Final private static Logger LOGGER;

    @Shadow public Window window;

    @Shadow public ClientPlayerEntity player;

    @Inject(method = "init",at = @At("HEAD"),cancellable = true)
    public void onClientStart(CallbackInfo ci){
        ClientStartCallback.EVENT.invoker().onClientStart((MinecraftClient)(Object)this);
        LOGGER.info("Client Started!");
    }
    @Inject(method = "stop",at=@At("HEAD"),cancellable = true)
    public void onClientStop(CallbackInfo callbackInfo){
        ClientStopCallback.EVENT.invoker().onClientStop((MinecraftClient)(Object)this);
        LOGGER.info("Client Shutdown!");
    }
    @Inject(method = "handleInputEvents",at=@At("HEAD"),cancellable = true)
    public void beforeHandleInput(CallbackInfo ci) throws IOException {
        ClassReader classReader=new ClassReader("org.lwjgl.glfw.GLFW");
        ClassNode classNode=new ClassNode();
        classReader.accept(classNode,ClassReader.SKIP_DEBUG);
        classNode.fields.forEach(fieldNode -> {
            if(fieldNode.name.contains("GLFW_KEY") || fieldNode.name.contains("GLFW_MOUSE")) {
                int keyCode = GLFW.glfwGetKey(window.getHandle(), (Integer) fieldNode.value);
                if (KeyInputCallback.KEY_INPUT_CALLBACK_EVENT.invoker().accept(keyCode,player).getResult() != ActionResult.FAIL)
                    ClientSidePacketRegistry.INSTANCE.sendToServer(new KeyBindingPacket().setKeyCode(keyCode));
                else ci.cancel();
            }});
    }
    //todo arguments are too less
    @Inject(method = "openScreen",at=@At("HEAD"),cancellable = true)
    public void beforeScreen(Screen screen, CallbackInfo ci){
        if(GuiScreenCallback.GUI_SCREEN_CALLBACK_EVENT.invoker().accept(screen)==ActionResult.FAIL)
            ci.cancel();
    }
}
