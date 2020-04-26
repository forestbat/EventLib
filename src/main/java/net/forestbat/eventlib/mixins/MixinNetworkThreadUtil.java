package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.PlayerChatCallback;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(NetworkThreadUtils.class)
public class MixinNetworkThreadUtil {
    @Inject(method="forceMainThread(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/ThreadExecutor;)V"
    ,at=@At("RETURN"),cancellable = true)
    private static <T extends PacketListener> void afterForceMainThread(Packet<T> packet, T packetListener,
                                                                        ThreadExecutor<?> threadExecutor, CallbackInfo ci) {
        if(threadExecutor instanceof MinecraftServer) {
            List<ServerPlayerEntity> playerList=((MinecraftServer) threadExecutor).getPlayerManager().getPlayerList();
            if (packet instanceof ChatMessageC2SPacket) {
                for (ServerPlayerEntity player : playerList)
                    if (PlayerChatCallback.PLAYER_CHAT_CALLBACK_EVENT.invoker().accept
                            (player, (new LiteralText(((ChatMessageC2SPacket) packet).getChatMessage()))).getResult() ==
                            ActionResult.FAIL)
                        ci.cancel();
            }
        }
    }
}
