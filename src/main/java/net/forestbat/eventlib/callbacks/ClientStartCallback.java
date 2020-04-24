package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public interface ClientStartCallback {
     Event<ClientStartCallback> EVENT= EventFactory.createArrayBacked(ClientStartCallback.class,listeners->client->{

     });
     void onClientStart(MinecraftClient client);
}
