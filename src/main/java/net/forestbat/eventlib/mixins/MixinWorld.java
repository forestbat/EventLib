package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.DayNightCallback;
import net.forestbat.eventlib.callbacks.ExplosionCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.level.LevelProperties;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {
    @Shadow public abstract World getWorld();

    @Inject(method = "getTimeOfDay",at=@At("RETURN"),cancellable = true)
    public void onGetTimeOfDay(CallbackInfoReturnable<Long> cir){
        if (cir.getReturnValue() == 450) {
            for (PlayerEntity player : getWorld().getPlayers()) {
                DayNightCallback.DAY_NIGHT_CALLBACK_EVENT.invoker().accept(getWorld(), player);
            }
        }
            if (cir.getReturnValue() == 12010) {
                for (PlayerEntity player : getWorld().getPlayers()) {
                    DayNightCallback.DAY_NIGHT_CALLBACK_EVENT.invoker().accept(getWorld(), player);
                }
            }
        }
}

