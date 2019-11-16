package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.MobSpawnerCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerLogic.class)
public abstract class MixinMobSpawnerLogic {
    @Shadow public abstract World getWorld();

    @Shadow private Entity renderedEntity;

    @Inject(method = "update",at = @At("HEAD"))
    public void onUpdate(CallbackInfo ci){
    for(PlayerEntity player:this.getWorld().getPlayers())
        if(MobSpawnerCallback.MOB_SPAWNER_CALLBACK_EVENT.invoker().accept(this.getWorld(),player,this.renderedEntity)== ActionResult.FAIL)
            ci.cancel();
    }
}
