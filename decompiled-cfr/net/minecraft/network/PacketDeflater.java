/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;
import net.minecraft.network.PacketByteBuf;

public class PacketDeflater
extends MessageToByteEncoder<ByteBuf> {
    private final byte[] deflateBuffer = new byte[8192];
    private final Deflater deflater;
    private int compressionThreshold;

    public PacketDeflater(int n) {
        this.compressionThreshold = n;
        this.deflater = new Deflater();
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n = byteBuf.readableBytes();
        PacketByteBuf _snowman2 = new PacketByteBuf(byteBuf2);
        if (n < this.compressionThreshold) {
            _snowman2.writeVarInt(0);
            _snowman2.writeBytes(byteBuf);
        } else {
            byte[] byArray = new byte[n];
            byteBuf.readBytes(byArray);
            _snowman2.writeVarInt(byArray.length);
            this.deflater.setInput(byArray, 0, n);
            this.deflater.finish();
            while (!this.deflater.finished()) {
                int n2 = this.deflater.deflate(this.deflateBuffer);
                _snowman2.writeBytes(this.deflateBuffer, 0, n2);
            }
            this.deflater.reset();
        }
    }

    public void setCompressionThreshold(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }

    protected /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

