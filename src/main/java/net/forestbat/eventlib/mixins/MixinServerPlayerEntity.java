package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.OpenContainerCallback;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
    @Inject(method = "openContainer(Lnet/minecraft/container/NameableContainerProvider;)Ljava/util/OptionalInt;",
            at=@At("HEAD"),cancellable = true)
    public void beforeOpenContainer(NameableContainerProvider nameableContainerProvider, CallbackInfoReturnable<OptionalInt> cir){
        if(OpenContainerCallback.OPEN_CONTAINER_CALLBACK_EVENT.invoker().accept((ServerPlayerEntity)(Object)this)== ActionResult.FAIL)
            cir.cancel();
    }
}
