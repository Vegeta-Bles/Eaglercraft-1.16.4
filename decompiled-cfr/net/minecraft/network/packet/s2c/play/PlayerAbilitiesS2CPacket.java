/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class PlayerAbilitiesS2CPacket
implements Packet<ClientPlayPacketListener> {
    private boolean invulnerable;
    private boolean flying;
    private boolean allowFlying;
    private boolean creativeMode;
    private float flySpeed;
    private float walkSpeed;

    public PlayerAbilitiesS2CPacket() {
    }

    public PlayerAbilitiesS2CPacket(PlayerAbilities playerAbilities) {
        this.invulnerable = playerAbilities.invulnerable;
        this.flying = playerAbilities.flying;
        this.allowFlying = playerAbilities.allowFlying;
        this.creativeMode = playerAbilities.creativeMode;
        this.flySpeed = playerAbilities.getFlySpeed();
        this.walkSpeed = playerAbilities.getWalkSpeed();
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        byte by = buf.readByte();
        this.invulnerable = (by & 1) != 0;
        this.flying = (by & 2) != 0;
        this.allowFlying = (by & 4) != 0;
        this.creativeMode = (by & 8) != 0;
        this.flySpeed = buf.readFloat();
        this.walkSpeed = buf.readFloat();
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        byte by = 0;
        if (this.invulnerable) {
            by = (byte)(by | 1);
        }
        if (this.flying) {
            by = (byte)(by | 2);
        }
        if (this.allowFlying) {
            by = (byte)(by | 4);
        }
        if (this.creativeMode) {
            by = (byte)(by | 8);
        }
        buf.writeByte(by);
        buf.writeFloat(this.flySpeed);
        buf.writeFloat(this.walkSpeed);
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onPlayerAbilities(this);
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public boolean allowFlying() {
        return this.allowFlying;
    }

    public boolean isCreativeMode() {
        return this.creativeMode;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }
}

