/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.scoreboard.ScoreboardObjective;

public class ScoreboardDisplayS2CPacket
implements Packet<ClientPlayPacketListener> {
    private int slot;
    private String name;

    public ScoreboardDisplayS2CPacket() {
    }

    public ScoreboardDisplayS2CPacket(int slot, @Nullable ScoreboardObjective objective) {
        this.slot = slot;
        this.name = objective == null ? "" : objective.getName();
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.slot = buf.readByte();
        this.name = buf.readString(16);
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeByte(this.slot);
        buf.writeString(this.name);
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onScoreboardDisplay(this);
    }

    public int getSlot() {
        return this.slot;
    }

    @Nullable
    public String getName() {
        return Objects.equals(this.name, "") ? null : this.name;
    }
}

