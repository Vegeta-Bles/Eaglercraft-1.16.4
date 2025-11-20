/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.Marker
 *  org.apache.logging.log4j.MarkerManager
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.NetworkState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.PacketEncoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketEncoder
extends MessageToByteEncoder<Packet<?>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker MARKER = MarkerManager.getMarker((String)"PACKET_SENT", (Marker)ClientConnection.MARKER_NETWORK_PACKETS);
    private final NetworkSide side;

    public PacketEncoder(NetworkSide side) {
        this.side = side;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, ByteBuf byteBuf) throws Exception {
        NetworkState networkState = (NetworkState)((Object)channelHandlerContext.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get());
        if (networkState == null) {
            throw new RuntimeException("ConnectionProtocol unknown: " + packet);
        }
        Integer _snowman2 = networkState.getPacketId(this.side, packet);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(MARKER, "OUT: [{}:{}] {}", channelHandlerContext.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get(), (Object)_snowman2, (Object)packet.getClass().getName());
        }
        if (_snowman2 == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        PacketByteBuf _snowman3 = new PacketByteBuf(byteBuf);
        _snowman3.writeVarInt(_snowman2);
        try {
            packet.write(_snowman3);
        }
        catch (Throwable _snowman4) {
            LOGGER.error((Object)_snowman4);
            if (packet.isWritingErrorSkippable()) {
                throw new PacketEncoderException(_snowman4);
            }
            throw _snowman4;
        }
    }

    protected /* synthetic */ void encode(ChannelHandlerContext ctx, Object packet, ByteBuf out) throws Exception {
        this.encode(ctx, (Packet)packet, out);
    }
}

