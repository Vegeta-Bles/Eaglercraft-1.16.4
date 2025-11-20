/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.packet.c2s.play;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;

public class PlayerInputC2SPacket
implements Packet<ServerPlayPacketListener> {
    private float sideways;
    private float forward;
    private boolean jumping;
    private boolean sneaking;

    public PlayerInputC2SPacket() {
    }

    public PlayerInputC2SPacket(float sideways, float forward, boolean jumping, boolean sneaking) {
        this.sideways = sideways;
        this.forward = forward;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.sideways = buf.readFloat();
        this.forward = buf.readFloat();
        byte by = buf.readByte();
        this.jumping = (by & 1) > 0;
        this.sneaking = (by & 2) > 0;
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeFloat(this.sideways);
        buf.writeFloat(this.forward);
        byte by = 0;
        if (this.jumping) {
            by = (byte)(by | 1);
        }
        if (this.sneaking) {
            by = (byte)(by | 2);
        }
        buf.writeByte(by);
    }

    @Override
    public void apply(ServerPlayPacketListener serverPlayPacketListener) {
        serverPlayPacketListener.onPlayerInput(this);
    }

    public float getSideways() {
        return this.sideways;
    }

    public float getForward() {
        return this.forward;
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }
}

