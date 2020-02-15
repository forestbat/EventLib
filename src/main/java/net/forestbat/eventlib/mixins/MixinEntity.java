package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.EntityDeathCallback;
import net.forestbat.eventlib.callbacks.EntityMoveCallback;
import net.forestbat.eventlib.callbacks.EntityTeleportCallback;
import net.forestbat.eventlib.callbacks.RidingCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
    Entity itSelf= (Entity)(Object)this;

    @Inject(method = "kill",at=@At("HEAD"),cancellable = true)
    public void onDeath(CallbackInfo ci){
        if(EntityDeathCallback.ENTITY_DEATH_CALLBACK_EVENT.invoker().accept(world, itSelf,
                world.getClosestPlayer(itSelf,32D), itSelf.getBlockPos())==ActionResult.FAIL)
            ci.cancel();
    }
    @Inject(method = "move",at=@At("HEAD"),cancellable = true)
    public void beforeMove(MovementType movementType, Vec3d vec3d, CallbackInfo ci){
        if(EntityMoveCallback.ENTITY_MOVE_CALLBACK_EVENT.invoker().accept(world,itSelf,itSelf.getPos())== ActionResult.FAIL)
            ci.cancel();
    }
    @Inject(method = "requestTeleport",at = @At("HEAD"),cancellable = true)
    public void beforeTeleport(double posX, double posY, double posZ, CallbackInfo ci){
        Objects.requireNonNull(itSelf.getServer()).getWorlds().forEach(destWorld-> {
            if (EntityTeleportCallback.ENTITY_TELEPORT_CALLBACK_EVENT.invoker().accept(itSelf, itSelf.getBlockPos(), new BlockPos(posX, posY, posZ), itSelf.world,
                    destWorld)==ActionResult.FAIL)
                ci.cancel();
        });
    }
    @Inject(method = "startRiding(Lnet/minecraft/entity/Entity;Z)Z",at=@At("HEAD"),cancellable = true)
    public void beforeRiding(Entity horse, boolean tamed, CallbackInfoReturnable<Boolean> cir){
        for(PlayerEntity player:horse.world.getEntities(PlayerEntity.class, Box.from(MutableIntBoundingBox.empty()))) {
        if (RidingCallback.RIDING_CALLBACK_EVENT.invoker().accept(player,horse)==ActionResult.FAIL)
            cir.cancel();
        }
    }
}
