package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Marker MARKER = MarkerManager.getMarker("PACKET_SENT", ClientConnection.MARKER_NETWORK_PACKETS);
   private final NetworkSide side;

   public PacketEncoder(NetworkSide side) {
      this.side = side;
   }

   protected void encode(ChannelHandlerContext channelHandlerContext, Packet<?> arg, ByteBuf byteBuf) throws Exception {
      NetworkState lv = (NetworkState)channelHandlerContext.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get();
      if (lv == null) {
         throw new RuntimeException("ConnectionProtocol unknown: " + arg);
      } else {
         Integer integer = lv.getPacketId(this.side, arg);
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
               MARKER, "OUT: [{}:{}] {}", channelHandlerContext.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get(), integer, arg.getClass().getName()
            );
         }

         if (integer == null) {
            throw new IOException("Can't serialize unregistered packet");
         } else {
            PacketByteBuf lv2 = new PacketByteBuf(byteBuf);
            lv2.writeVarInt(integer);

            try {
               arg.write(lv2);
            } catch (Throwable var8) {
               LOGGER.error(var8);
               if (arg.isWritingErrorSkippable()) {
                  throw new PacketEncoderException(var8);
               } else {
                  throw var8;
               }
            }
         }
      }
   }
}
