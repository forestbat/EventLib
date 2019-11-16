package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

public interface ClientStartCallback {
    public static Event<ClientStartCallback> EVENT= EventFactory.createArrayBacked(ClientStartCallback.class,listeners->client->{

    });
    void onClientStart(MinecraftClient client);
}
