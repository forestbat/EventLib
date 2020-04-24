package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.PlaySoundCallback;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    World world=(World)(Object)this;
    @Inject(method = "playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V",
            at=@At("HEAD"),cancellable = true)
    public void beforePlaySound(double d, double e, double f, SoundEvent event, SoundCategory category, float g, float h, boolean bl,
                                CallbackInfo ci){
        for(PlayerEntity player:world.getPlayers()) {
            if (PlaySoundCallback.PLAY_SOUND_CALLBACK_EVENT.invoker().accept(player.world, player, player.getBlockPos(), event, category) ==
                    ActionResult.FAIL)
                ci.cancel();
        }
    }
}
