package net.forestbat.eventlib.mixins;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.forestbat.eventlib.callbacks.AdvancementCallback;
import net.minecraft.client.network.packet.AdvancementUpdateS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayHandler {
    @Shadow @Final private MinecraftServer server;

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V",
    at=@At("RETURN"),cancellable = true)
    public void afterSendPacket(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>>
            genericFutureListener, CallbackInfo ci){
        if(packet instanceof AdvancementUpdateS2CPacket){
            for(ServerPlayerEntity player:server.getPlayerManager().getPlayerList())
                if(AdvancementCallback.ADVANCEMENT_CALLBACK_EVENT.invoker().accept(player,player.world,
                        (AdvancementUpdateS2CPacket)packet)== ActionResult.FAIL)
                    ci.cancel();
        }
    }
}
