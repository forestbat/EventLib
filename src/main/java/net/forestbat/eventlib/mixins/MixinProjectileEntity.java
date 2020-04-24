package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ShootingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public class MixinProjectileEntity {
    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;)V",at=@At(
            "RETURN"),cancellable = true)
    public void beforeInit(EntityType<? extends ProjectileEntity> entityType, LivingEntity livingEntity, World world, CallbackInfo ci){
        if(ShootingCallback.SHOOTING_CALLBACK_EVENT.invoker().accept(livingEntity,world,(ProjectileEntity)(Object)this)== ActionResult.FAIL)
            ci.cancel();
    }
}
