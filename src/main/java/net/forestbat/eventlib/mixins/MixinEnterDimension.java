package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.EnterDimensionCallback;
import net.forestbat.eventlib.callbacks.EntityDeathCallback;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public class MixinEnterDimension {
    @Shadow public World world;

    @Inject(method = "changeDimension",at=@At("HEAD"),cancellable = true)
    public void beforeChangeDimension(DimensionType dimension, CallbackInfoReturnable<Entity> callbackInfo){
        World world=callbackInfo.getReturnValue().world;
        if(EnterDimensionCallback.ENTER_DIMENSION_CALLBACK_EVENT.invoker().enterDimension(dimension.create(world),
                callbackInfo.getReturnValue(),world,dimension)== ActionResult.FAIL)
            callbackInfo.cancel();
    }

    @Inject(method = "kill",at=@At("HEAD"),cancellable = true)
    public void beforeKill(CallbackInfo info){
        Entity entity=(Entity)(Object)this;
        if(EntityDeathCallback.ENTITY_DEATH_CALLBACK_EVENT.invoker().accept(world,entity,
                world.getClosestPlayer(entity,32),entity.getBlockPos())==ActionResult.FAIL)
            info.cancel();
    }
}
