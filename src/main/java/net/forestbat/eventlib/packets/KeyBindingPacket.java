package net.forestbat.eventlib.packets;

import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.minecraft.network.NetworkState;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.util.PacketByteBuf;

public class KeyBindingPacket implements Packet {
    private int keyCode;
    private String keyName;

    public int getKeyCode() {
        return keyCode;
    }

    public KeyBindingPacket setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        return this;
    }

    public String getKeyName() {
        return keyName;
    }

    public KeyBindingPacket setKeyName(String keyName){
        this.keyName=keyName;
        return this;
    }
    @Override
    public void read(PacketByteBuf buf) {
        this.keyName=buf.readString();
        this.keyCode=buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(FabricKeyBinding.getLocalizedName(keyName).get());
        buf.writeInt(keyCode);
    }

    @Override
    public void apply(PacketListener listener) {
        listener.getConnection().setState(NetworkState.PLAY);
    }
}
