/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.network.packet.s2c.login;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientLoginPacketListener;
import net.minecraft.util.dynamic.DynamicSerializableUuid;

public class LoginSuccessS2CPacket
implements Packet<ClientLoginPacketListener> {
    private GameProfile profile;

    public LoginSuccessS2CPacket() {
    }

    public LoginSuccessS2CPacket(GameProfile profile) {
        this.profile = profile;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        int[] nArray = new int[4];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = buf.readInt();
        }
        UUID _snowman2 = DynamicSerializableUuid.toUuid(nArray);
        String _snowman3 = buf.readString(16);
        this.profile = new GameProfile(_snowman2, _snowman3);
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        for (int n : DynamicSerializableUuid.toIntArray(this.profile.getId())) {
            buf.writeInt(n);
        }
        buf.writeString(this.profile.getName());
    }

    @Override
    public void apply(ClientLoginPacketListener clientLoginPacketListener) {
        clientLoginPacketListener.onLoginSuccess(this);
    }

    public GameProfile getProfile() {
        return this.profile;
    }
}

