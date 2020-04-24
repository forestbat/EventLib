package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public interface ClientStopCallback {
    Event<ClientStopCallback> EVENT= EventFactory.createArrayBacked(ClientStopCallback.class,listeners->client->{

    });
    void onClientStop(MinecraftClient client);
}
