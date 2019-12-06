package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.RaidCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.Raid;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class MixinRaid {
    @Inject(method = "start",at=@At("HEAD"),cancellable = true)
    public void beforeStart(PlayerEntity player, CallbackInfo ci){
        if(RaidCallback.RAID_CALLBACK_EVENT.invoker().accept((Raid)(Object)this,player)== ActionResult.FAIL)
            ci.cancel();
    }
}
