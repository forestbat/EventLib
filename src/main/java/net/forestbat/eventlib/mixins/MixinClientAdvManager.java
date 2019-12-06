package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.AdvancementCallback;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.packet.AdvancementUpdateS2CPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientAdvancementManager.class)
public abstract class MixinClientAdvManager {
    @Shadow public abstract AdvancementManager getManager();
    @Final @Shadow private MinecraftClient client;

    @Inject(method = "onAdvancements",at = @At("HEAD"),cancellable = true)
    public void onAdvMixin(AdvancementUpdateS2CPacket packet, CallbackInfo callbackInfo){
        for(PlayerEntity player:client.world.getPlayers())
            if(AdvancementCallback.ADVANCEMENT_CALLBACK_EVENT.invoker().accept(player,client.world,packet)== ActionResult.FAIL)
                callbackInfo.cancel();
    }
}
