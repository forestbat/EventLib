package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.EntitySpawnCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(value = ServerWorld.class)
public class MixinEntitySpawn {
    @Inject(method = "spawnEntity",at=@At("HEAD"),cancellable = true)
    public void beforeSpawnEntity(Entity entity, CallbackInfoReturnable<Boolean> info){
        for (PlayerEntity playerEntity : entity.world.getPlayers()) {
            if (EntitySpawnCallback.EVENT_SPAWN_CALLBACK.invoker().accept(entity.world,playerEntity, entity, entity.getBlockPos()) == ActionResult.FAIL)
                info.cancel();
        }
    }
}
