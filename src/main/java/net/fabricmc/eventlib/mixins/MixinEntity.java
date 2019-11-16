package net.fabricmc.eventlib.mixins;

import net.fabricmc.eventlib.callbacks.EntityDeathCallback;
import net.fabricmc.eventlib.callbacks.EntityMoveCallback;
import net.fabricmc.eventlib.callbacks.EntityTeleportCallback;
import net.fabricmc.eventlib.callbacks.RidingCallback;
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
import java.util.function.Predicate;

@Mixin(Entity.class)
public class MixinEntity {
    @Shadow
    public World world;
    Entity itSelf= (Entity)(Object)this;

    @Inject(method = "kill",at=@At("HEAD"),cancellable = true)
    public void onDeath(CallbackInfo ci){
        if(!EntityDeathCallback.ENTITY_DEATH_CALLBACK_EVENT.invoker().accept(world, itSelf,
                world.getClosestPlayer(itSelf,32D), itSelf.getBlockPos()))
            ci.cancel();
    }
    @Inject(method = "move",at=@At("HEAD"))
    public void beforeMove(MovementType movementType, Vec3d vec3d, CallbackInfo ci){
        if(EntityMoveCallback.ENTITY_MOVE_CALLBACK_EVENT.invoker().accept(world,itSelf,itSelf.getPos())== ActionResult.FAIL)
            ci.cancel();
    }
    @Inject(method = "requestTeleport",at = @At("HEAD"))
    public void beforeTeleport(double posX, double posY, double posZ, CallbackInfo ci){
        Objects.requireNonNull(itSelf.getServer()).getWorlds().forEach(destWorld-> {
            if (EntityTeleportCallback.EVENT.invoker().accept(itSelf, itSelf.getBlockPos(), new BlockPos(posX, posY, posZ), itSelf.world,
                    destWorld)==ActionResult.FAIL)
                ci.cancel();
        });
    }
    @Inject(method = "startRiding(Lnet/minecraft/entity/Entity;Z)Z",at=@At("HEAD"))
    public void beforeRiding(Entity horse, boolean tamed, CallbackInfoReturnable<Boolean> cir){
        for(PlayerEntity player:horse.world.getEntities(PlayerEntity.class, Box.from(MutableIntBoundingBox.empty()))) {
        if (RidingCallback.RIDING_CALLBACK_EVENT.invoker().accept(player,horse)==ActionResult.FAIL)
            cir.cancel();
        }
    }
}