package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Entity.class)
public class MixinEntity {
    @Shadow
    public World world;

    @Inject(method = "remove",at=@At("HEAD"),cancellable = true)
    public void onDeath(CallbackInfo ci){
        if(EntityDeathCallback.ENTITY_DEATH_CALLBACK_EVENT.invoker().accept(world, (Entity)(Object)this,
                world.getClosestPlayer((Entity)(Object)this,32D), ((Entity)(Object)this).getBlockPos())==ActionResult.FAIL)
            ci.cancel();
    }
    @Inject(method = "move",at=@At("HEAD"),cancellable = true)
    public void beforeMove(MovementType movementType, Vec3d vec3d, CallbackInfo ci){
        if(EntityMoveCallback.ENTITY_MOVE_CALLBACK_EVENT.invoker().accept(world,(Entity)(Object)this,
                vec3d)== ActionResult.FAIL)
            ci.cancel();
    }
    @Inject(method = "requestTeleport",at = @At("HEAD"),cancellable = true)
    public void beforeTeleport(double posX, double posY, double posZ, CallbackInfo ci){
         if (EntityTeleportCallback.ENTITY_TELEPORT_CALLBACK_EVENT.invoker().accept((Entity) (Object) this,
                 ((Entity) (Object) this).getBlockPos(),new BlockPos(posX, posY, posZ)) == ActionResult.FAIL)
                    ci.cancel();
    }
    @Inject(method = "startRiding(Lnet/minecraft/entity/Entity;Z)Z",at=@At("HEAD"),cancellable = true)
    public void beforeRiding(Entity horse, boolean tamed, CallbackInfoReturnable<Boolean> cir){
        for(PlayerEntity player:horse.world.getPlayers()) {
        if (RidingCallback.RIDING_CALLBACK_EVENT.invoker().accept(player,horse)==ActionResult.FAIL)
            cir.cancel();
        }
    }
    @Inject(method = "changeDimension",at=@At("RETURN"),cancellable = true)
    public void afterChangeDimension(DimensionType dimension, CallbackInfoReturnable<Entity> callbackInfo){
        if(EnterDimensionCallback.ENTER_DIMENSION_CALLBACK_EVENT.invoker().enterDimension(dimension.create(world),
                (Entity)(Object)this,((Entity)(Object)this).world,dimension)== ActionResult.FAIL)
            callbackInfo.cancel();
    }
}
