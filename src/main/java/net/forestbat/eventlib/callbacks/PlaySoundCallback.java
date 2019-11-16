package net.forestbat.eventlib.callbacks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public interface PlaySoundCallback {
    ActionResult accept(World world, PlayerEntity player, BlockPos pos, SoundEvent event, SoundCategory category);
     Event<PlaySoundCallback> PLAYER_SLEEP_CALLBACK_EVENT= EventFactory.createArrayBacked(PlaySoundCallback.class,
            listeners->(
            (world,player,pos,event,category) ->{
                for(PlaySoundCallback callback:listeners){
                    if(callback.accept(world,player,pos,event,category)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            } ));
}
