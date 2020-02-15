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
    private PlayerEntity player=(PlayerEntity)(Object)this;

    @Inject(method = "openContainer",at=@At("HEAD"),cancellable = true)
    public void beforeOpenContainer(NameableContainerProvider provider, CallbackInfoReturnable<OptionalInt> cir){
        if(OpenContainerCallback.OPEN_CONTAINER_CALLBACK_EVENT.invoker().accept((PlayerEntity)(Object)this)!= ActionResult.PASS)
            cir.cancel();
    }
    @Inject(method = "addChatMessage" ,at=@At("HEAD"),cancellable = true)
    public void beforeChat(Text text, boolean boolean_1, CallbackInfo ci){
        if(PlayerChatCallback.PLAYER_CHAT_CALLBACK_EVENT.invoker().accept(player,player.world,text).getResult()==ActionResult.FAIL)
            ci.cancel();
    }
    @Inject(method = "trySleep",at=@At("HEAD"),cancellable = true)
    public void beforeSleep(BlockPos blockPos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir){
        if(PlayerSleepCallback.PLAYER_SLEEP_CALLBACK_EVENT.invoker().accept(player,blockPos,player.world.getTime())==ActionResult.FAIL)
            cir.cancel();
    }
    //Should be more complex,include block and entity player "got"
    @Inject(method = "addExperienceLevels",at=@At("HEAD"),cancellable = true)
    public void beforeAddLevel(int level, CallbackInfo ci){
        if(PlayerLevelupCallback.PLAYER_LEVELUP_CALLBACK_EVENT.invoker().accept(player,player.experienceLevel)==ActionResult.FAIL)
            ci.cancel();
    }
}
