package net.forestbat.eventlib.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class MixinExplosion {
    private final String createExplosion="createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;" +
            "DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;";
    @Inject(method = createExplosion, at=@At("HEAD"),cancellable = true)
    public void beforeExplosion(Entity entity, DamageSource damageSource, double posX, double posY, double posZ,
                                float strength, boolean hasSmoke, Explosion.DestructionType destructionType,
                                CallbackInfoReturnable<Explosion> callbackInfoReturnable){

    }
    @Inject(method = createExplosion,at=@At("RETURN"),cancellable = true)
    public void afterExplosion(Entity entity, DamageSource damageSource, double posX, double posY, double posZ,
                               float strength, boolean hasSmoke, Explosion.DestructionType destructionType,
                               CallbackInfoReturnable<Explosion> callbackInfoReturnable){
        callbackInfoReturnable.getReturnValue().affectWorld(true);
    }
}
