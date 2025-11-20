/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 */
package net.minecraft.network.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class PacketEncryptionManager {
    private final Cipher cipher;
    private byte[] conversionBuffer = new byte[0];
    private byte[] encryptionBuffer = new byte[0];

    protected PacketEncryptionManager(Cipher cipher) {
        this.cipher = cipher;
    }

    private byte[] toByteArray(ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (this.conversionBuffer.length < n) {
            this.conversionBuffer = new byte[n];
        }
        byteBuf.readBytes(this.conversionBuffer, 0, n);
        return this.conversionBuffer;
    }

    protected ByteBuf decrypt(ChannelHandlerContext context, ByteBuf byteBuf) throws ShortBufferException {
        int n = byteBuf.readableBytes();
        byte[] _snowman2 = this.toByteArray(byteBuf);
        ByteBuf _snowman3 = context.alloc().heapBuffer(this.cipher.getOutputSize(n));
        _snowman3.writerIndex(this.cipher.update(_snowman2, 0, n, _snowman3.array(), _snowman3.arrayOffset()));
        return _snowman3;
    }

    protected void encrypt(ByteBuf buffer, ByteBuf byteBuf) throws ShortBufferException {
        int n = buffer.readableBytes();
        byte[] _snowman2 = this.toByteArray(buffer);
        _snowman = this.cipher.getOutputSize(n);
        if (this.encryptionBuffer.length < _snowman) {
            this.encryptionBuffer = new byte[_snowman];
        }
        byteBuf.writeBytes(this.encryptionBuffer, 0, this.cipher.update(_snowman2, 0, n, this.encryptionBuffer));
    }
}

