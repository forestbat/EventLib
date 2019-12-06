package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.EntitySpawnCallback;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ModifiableWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ServerWorld.class)
public class MixinEntitySpawn {
    @Inject(method = "spawnEntity",at=@At("HEAD"),cancellable = true)
    public void beforeSpawnEntity(Entity entity, CallbackInfoReturnable<Boolean> info){
        boolean willCancel= EntitySpawnCallback.EVENT_SPAWN_CALLBACK.invoker().accept(entity.world,
                entity.world.getPlayers().iterator().next(),entity,entity.getBlockPos());
        if(!willCancel)
        info.cancel();
    }
}
