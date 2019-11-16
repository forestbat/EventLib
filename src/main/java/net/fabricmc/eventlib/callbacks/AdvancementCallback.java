package net.fabricmc.eventlib.callbacks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.packet.AdvancementUpdateS2CPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

@Environment(value = EnvType.CLIENT)
public interface AdvancementCallback {
    public static Event<AdvancementCallback> ADVANCEMENT_CALLBACK_EVENT= EventFactory.createArrayBacked(AdvancementCallback.class,
            listeners->(player,world,advancement)->{
                for(AdvancementCallback callback:listeners){
                    if(callback.accept(player,world,advancement)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(PlayerEntity playerEntity, World world, AdvancementUpdateS2CPacket advancementPacket);
}
