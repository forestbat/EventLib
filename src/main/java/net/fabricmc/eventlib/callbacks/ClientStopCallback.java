package net.fabricmc.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

public interface ClientStopCallback {
    public static Event<ClientStopCallback> EVENT= EventFactory.createArrayBacked(ClientStopCallback.class,listeners->client->{});
    void onClientStop(MinecraftClient client);
}
