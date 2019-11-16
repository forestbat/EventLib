package net.forestbat.eventlib.mixins;

import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.packet.AdvancementUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientAdvancementManager.class)
public class MixinClientAdvManager {
    @Inject(method = "onAdvancements",at = @At("HEAD"))
    public void onAdvMixin(AdvancementUpdateS2CPacket packet, CallbackInfo callbackInfo){

    }
}
