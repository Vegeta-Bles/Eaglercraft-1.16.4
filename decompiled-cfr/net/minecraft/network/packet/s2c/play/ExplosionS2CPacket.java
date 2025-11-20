/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ExplosionS2CPacket
implements Packet<ClientPlayPacketListener> {
    private double x;
    private double y;
    private double z;
    private float radius;
    private List<BlockPos> affectedBlocks;
    private float playerVelocityX;
    private float playerVelocityY;
    private float playerVelocityZ;

    public ExplosionS2CPacket() {
    }

    public ExplosionS2CPacket(double x, double y, double z, float radius, List<BlockPos> affectedBlocks, Vec3d playerVelocity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.affectedBlocks = Lists.newArrayList(affectedBlocks);
        if (playerVelocity != null) {
            this.playerVelocityX = (float)playerVelocity.x;
            this.playerVelocityY = (float)playerVelocity.y;
            this.playerVelocityZ = (float)playerVelocity.z;
        }
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.z = buf.readFloat();
        this.radius = buf.readFloat();
        int n = buf.readInt();
        this.affectedBlocks = Lists.newArrayListWithCapacity((int)n);
        _snowman = MathHelper.floor(this.x);
        _snowman = MathHelper.floor(this.y);
        _snowman = MathHelper.floor(this.z);
        for (_snowman = 0; _snowman < n; ++_snowman) {
            _snowman = buf.readByte() + _snowman;
            _snowman = buf.readByte() + _snowman;
            _snowman = buf.readByte() + _snowman;
            this.affectedBlocks.add(new BlockPos(_snowman, _snowman, _snowman));
        }
        this.playerVelocityX = buf.readFloat();
        this.playerVelocityY = buf.readFloat();
        this.playerVelocityZ = buf.readFloat();
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeFloat((float)this.x);
        buf.writeFloat((float)this.y);
        buf.writeFloat((float)this.z);
        buf.writeFloat(this.radius);
        buf.writeInt(this.affectedBlocks.size());
        int n = MathHelper.floor(this.x);
        _snowman = MathHelper.floor(this.y);
        _snowman = MathHelper.floor(this.z);
        for (BlockPos blockPos : this.affectedBlocks) {
            int n2 = blockPos.getX() - n;
            _snowman = blockPos.getY() - _snowman;
            _snowman = blockPos.getZ() - _snowman;
            buf.writeByte(n2);
            buf.writeByte(_snowman);
            buf.writeByte(_snowman);
        }
        buf.writeFloat(this.playerVelocityX);
        buf.writeFloat(this.playerVelocityY);
        buf.writeFloat(this.playerVelocityZ);
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onExplosion(this);
    }

    public float getPlayerVelocityX() {
        return this.playerVelocityX;
    }

    public float getPlayerVelocityY() {
        return this.playerVelocityY;
    }

    public float getPlayerVelocityZ() {
        return this.playerVelocityZ;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getRadius() {
        return this.radius;
    }

    public List<BlockPos> getAffectedBlocks() {
        return this.affectedBlocks;
    }
}

