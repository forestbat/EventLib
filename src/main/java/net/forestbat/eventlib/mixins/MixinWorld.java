package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.DayNightCallback;
import net.forestbat.eventlib.callbacks.ExplosionCallback;
import net.forestbat.eventlib.callbacks.PlaySoundCallback;
import net.forestbat.eventlib.callbacks.WeatherCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {
    @Shadow public abstract World getWorld();
    @Shadow @Final protected LevelProperties properties;

    @Shadow @Final protected static Logger LOGGER;

    @Inject(method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;" +
            "DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;",
            at = @At("HEAD"),cancellable = true)
    public void beforeExplosion(Entity entity_1, DamageSource damageSource_1, double double_1, double double_2, double double_3,
                                float float_1, boolean boolean_1, Explosion.DestructionType explosion$DestructionType_1,
                                CallbackInfoReturnable<Explosion> cir) {
        if(ExplosionCallback.EXPLOSION_CALLBACK_EVENT.invoker().accept(entity_1.world,entity_1.getBlockPos())== ActionResult.FAIL)
            cir.cancel();
    }
    //FIXME
    /*@Inject(method = "getLevelProperties",at=@At("HEAD"),cancellable = true)
    public void afterProperties(CallbackInfoReturnable<LevelProperties> cir){
        for(PlayerEntity player:getWorld().getPlayers())
        if(properties.isRaining() && WeatherCallback.WEATHER_CALLBACK_EVENT.invoker().
                accept(getWorld(),player,player.getBlockPos())==ActionResult.FAIL)
            cir.cancel();
    }*/
    @Inject(method = "getTimeOfDay",at=@At("RETURN"),cancellable = true)
    public void onGetTimeOfDay(CallbackInfoReturnable<Long> cir){
        for(PlayerEntity player:getWorld().getPlayers()) {
            DayNightCallback.DAY_NIGHT_CALLBACK_EVENT.invoker().accept(getWorld(), player);
            if (cir.getReturnValue() == 450)
                LOGGER.info("Sunrise finished!");
            if (cir.getReturnValue() == 12010)
                LOGGER.info("Sunset start!");
        }
    }
}

