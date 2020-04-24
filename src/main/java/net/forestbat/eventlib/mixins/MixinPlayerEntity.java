package net.forestbat.eventlib.mixins;

import com.mojang.datafixers.util.Either;
import net.forestbat.eventlib.callbacks.OpenContainerCallback;
import net.forestbat.eventlib.callbacks.PlayerChatCallback;
import net.forestbat.eventlib.callbacks.PlayerLevelupCallback;
import net.forestbat.eventlib.callbacks.PlayerSleepCallback;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    private final PlayerEntity player=(PlayerEntity)(Object)this;
    @Inject(method = "trySleep",at=@At("HEAD"),cancellable = true)
    public void beforeSleep(BlockPos blockPos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir){
        if(PlayerSleepCallback.PLAYER_SLEEP_CALLBACK_EVENT.invoker().accept(player,blockPos,player.world.getTime())==ActionResult.FAIL)
            cir.cancel();
    }
    //Should be more complex,include block and entity player "got"
    @Inject(method = "addExperienceLevels",at=@At("HEAD"),cancellable = true)
    public void beforeAddLevel(int level, CallbackInfo ci){
        if(PlayerLevelupCallback.PLAYER_LEVELUP_CALLBACK_EVENT.invoker().accept(player,level)==ActionResult.FAIL)
            ci.cancel();
    }
}
