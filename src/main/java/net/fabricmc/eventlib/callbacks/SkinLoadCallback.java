package net.fabricmc.eventlib.callbacks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public interface SkinLoadCallback {
    public static Event<SkinLoadCallback> SKIN_LOAD_CALLBACK_EVENT = EventFactory.createArrayBacked(SkinLoadCallback.class,
            listeners->(world,player,identifier)->{
                for(SkinLoadCallback callback:listeners){
                    if(callback.accept(world,player,identifier)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
    ActionResult accept(World world, ClientPlayerEntity playerEntity,Identifier identifier);
}
