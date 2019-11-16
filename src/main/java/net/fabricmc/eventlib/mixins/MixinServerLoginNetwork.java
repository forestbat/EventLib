package net.fabricmc.eventlib.mixins;

import net.fabricmc.eventlib.callbacks.PreLoginCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.LoginKeyC2SPacket;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.crypto.SecretKey;

@Mixin(ServerLoginNetworkHandler.class)
public class MixinServerLoginNetwork {
    //FIXME
    @Shadow @Final private MinecraftServer server;

    @Shadow private ServerPlayerEntity clientEntity;

    @Shadow private SecretKey secretKey;

    @Inject(method = "onKey",at=@At("HEAD"))
    public void beforeKey(LoginKeyC2SPacket loginKeyC2SPacket, CallbackInfo ci){
        if(PreLoginCallback.PRE_LOGIN_CALLBACK_EVENT.invoker().accept
                (this.server,this.secretKey,this.clientEntity)== ActionResult.FAIL)
        ci.cancel();
    }
}
