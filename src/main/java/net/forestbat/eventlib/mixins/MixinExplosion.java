package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ExplosionCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class MixinExplosion {
    @Inject(method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;" +
            "DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;", at=@At("HEAD"),cancellable = true)
    public void beforeExplosion(Entity entity, DamageSource damageSource, double posX, double posY, double posZ,
                                float strength, boolean hasSmoke, Explosion.DestructionType destructionType,
                                CallbackInfoReturnable<Explosion> callbackInfoReturnable){
        if(ExplosionCallback.EXPLOSION_CALLBACK_EVENT.invoker().accept(entity.world,entity.getBlockPos())== ActionResult.FAIL)
            callbackInfoReturnable.cancel();
    }
    @Inject(method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;" +
            "DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;",at=@At("RETURN"),cancellable = true)
    public void afterExplosion(Entity entity, DamageSource damageSource, double posX, double posY, double posZ,
                               float strength, boolean hasSmoke, Explosion.DestructionType destructionType,
                               CallbackInfoReturnable<Explosion> callbackInfoReturnable){
        callbackInfoReturnable.getReturnValue().affectWorld(true);
    }
}
