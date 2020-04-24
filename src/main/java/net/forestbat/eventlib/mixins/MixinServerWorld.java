package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ServerWorldTickCallback;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class MixinServerWorld {
    ServerWorld world=(ServerWorld)(Object)this;
    @Inject(method = "tick",at=@At("HEAD"),cancellable = true)
    public void beforeTick(BooleanSupplier booleanSupplier, CallbackInfo ci){
        if(ServerWorldTickCallback.SERVER_WORLD_CALLBACK.invoker().accept(world)==ActionResult.FAIL)
            ci.cancel();
    }
}
