/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandler$Sharable
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.PacketByteBuf;

@ChannelHandler.Sharable
public class SizePrepender
extends MessageToByteEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n = byteBuf.readableBytes();
        _snowman = PacketByteBuf.getVarIntSizeBytes(n);
        if (_snowman > 3) {
            throw new IllegalArgumentException("unable to fit " + n + " into " + 3);
        }
        PacketByteBuf _snowman2 = new PacketByteBuf(byteBuf2);
        _snowman2.ensureWritable(_snowman + n);
        _snowman2.writeVarInt(n);
        _snowman2.writeBytes(byteBuf, byteBuf.readerIndex(), n);
    }

    protected /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

