package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ExplosionCallback;
import net.forestbat.eventlib.callbacks.ServerWorldTickCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class MixinServerWorld {
    ServerWorld world=(ServerWorld)(Object)this;
    @Inject(method = "tick",at=@At("HEAD"),cancellable = true)
    public void beforeTick(BooleanSupplier booleanSupplier, CallbackInfo ci){
        if(ServerWorldTickCallback.SERVER_WORLD_CALLBACK.invoker().accept(world)==ActionResult.FAIL)
            ci.cancel();
    }
    @Inject(method ="createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;",
            at = @At("HEAD"),cancellable = true)
    public void beforeExplosion(Entity entity, DamageSource damageSource, double d, double e, double f, float g, boolean bl, Explosion.DestructionType destructionType, CallbackInfoReturnable<Explosion> cir) {
        if(ExplosionCallback.EXPLOSION_CALLBACK_EVENT.invoker().accept(entity.world,entity.getBlockPos())== ActionResult.FAIL)
            cir.cancel();
    }
}
