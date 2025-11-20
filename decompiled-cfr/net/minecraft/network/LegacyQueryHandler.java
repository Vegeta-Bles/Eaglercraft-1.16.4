/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelFutureListener
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelInboundHandlerAdapter
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerNetworkIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LegacyQueryHandler
extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ServerNetworkIo networkIo;

    public LegacyQueryHandler(ServerNetworkIo networkIo) {
        this.networkIo = networkIo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        ByteBuf byteBuf = (ByteBuf)object;
        byteBuf.markReaderIndex();
        boolean _snowman2 = true;
        try {
            if (byteBuf.readUnsignedByte() != 254) {
                return;
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
            MinecraftServer _snowman3 = this.networkIo.getServer();
            int _snowman4 = byteBuf.readableBytes();
            switch (_snowman4) {
                case 0: {
                    LOGGER.debug("Ping: (<1.3.x) from {}:{}", (Object)inetSocketAddress.getAddress(), (Object)inetSocketAddress.getPort());
                    String string = String.format("%s\u00a7%d\u00a7%d", _snowman3.getServerMotd(), _snowman3.getCurrentPlayerCount(), _snowman3.getMaxPlayerCount());
                    this.reply(channelHandlerContext, this.toBuffer(string));
                    break;
                }
                case 1: {
                    if (byteBuf.readUnsignedByte() != 1) {
                        return;
                    }
                    LOGGER.debug("Ping: (1.4-1.5.x) from {}:{}", (Object)inetSocketAddress.getAddress(), (Object)inetSocketAddress.getPort());
                    String _snowman5 = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, _snowman3.getVersion(), _snowman3.getServerMotd(), _snowman3.getCurrentPlayerCount(), _snowman3.getMaxPlayerCount());
                    this.reply(channelHandlerContext, this.toBuffer(_snowman5));
                    break;
                }
                default: {
                    boolean _snowman6 = byteBuf.readUnsignedByte() == 1;
                    _snowman6 &= byteBuf.readUnsignedByte() == 250;
                    _snowman6 &= "MC|PingHost".equals(new String(byteBuf.readBytes(byteBuf.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                    int _snowman7 = byteBuf.readUnsignedShort();
                    _snowman6 &= byteBuf.readUnsignedByte() >= 73;
                    _snowman6 &= 3 + byteBuf.readBytes(byteBuf.readShort() * 2).array().length + 4 == _snowman7;
                    _snowman6 &= byteBuf.readInt() <= 65535;
                    if (!(_snowman6 &= byteBuf.readableBytes() == 0)) {
                        return;
                    }
                    LOGGER.debug("Ping: (1.6) from {}:{}", (Object)inetSocketAddress.getAddress(), (Object)inetSocketAddress.getPort());
                    String _snowman8 = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, _snowman3.getVersion(), _snowman3.getServerMotd(), _snowman3.getCurrentPlayerCount(), _snowman3.getMaxPlayerCount());
                    _snowman = this.toBuffer(_snowman8);
                    try {
                        this.reply(channelHandlerContext, _snowman);
                        break;
                    }
                    finally {
                        _snowman.release();
                    }
                }
            }
            byteBuf.release();
            _snowman2 = false;
        }
        catch (RuntimeException runtimeException) {
        }
        finally {
            if (_snowman2) {
                byteBuf.resetReaderIndex();
                channelHandlerContext.channel().pipeline().remove("legacy_query");
                channelHandlerContext.fireChannelRead(object);
            }
        }
    }

    private void reply(ChannelHandlerContext ctx, ByteBuf buf) {
        ctx.pipeline().firstContext().writeAndFlush((Object)buf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }

    private ByteBuf toBuffer(String s) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeByte(255);
        char[] _snowman2 = s.toCharArray();
        byteBuf.writeShort(_snowman2.length);
        for (char c : _snowman2) {
            byteBuf.writeChar((int)c);
        }
        return byteBuf;
    }
}

