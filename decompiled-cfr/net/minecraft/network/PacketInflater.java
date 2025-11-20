/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  io.netty.handler.codec.DecoderException
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;
import net.minecraft.network.PacketByteBuf;

public class PacketInflater
extends ByteToMessageDecoder {
    private final Inflater inflater;
    private int compressionThreshold;

    public PacketInflater(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
        this.inflater = new Inflater();
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() == 0) {
            return;
        }
        PacketByteBuf packetByteBuf = new PacketByteBuf(byteBuf);
        int _snowman2 = packetByteBuf.readVarInt();
        if (_snowman2 == 0) {
            list.add(packetByteBuf.readBytes(packetByteBuf.readableBytes()));
        } else {
            if (_snowman2 < this.compressionThreshold) {
                throw new DecoderException("Badly compressed packet - size of " + _snowman2 + " is below server threshold of " + this.compressionThreshold);
            }
            if (_snowman2 > 0x200000) {
                throw new DecoderException("Badly compressed packet - size of " + _snowman2 + " is larger than protocol maximum of " + 0x200000);
            }
            byte[] byArray = new byte[packetByteBuf.readableBytes()];
            packetByteBuf.readBytes(byArray);
            this.inflater.setInput(byArray);
            _snowman = new byte[_snowman2];
            this.inflater.inflate(_snowman);
            list.add(Unpooled.wrappedBuffer((byte[])_snowman));
            this.inflater.reset();
        }
    }

    public void setCompressionThreshold(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }
}

