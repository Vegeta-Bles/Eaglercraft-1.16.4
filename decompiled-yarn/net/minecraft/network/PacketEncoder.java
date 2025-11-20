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

   protected void encode(ChannelHandlerContext _snowman, Packet<?> _snowman, ByteBuf _snowman) throws Exception {
      NetworkState _snowmanxxx = (NetworkState)_snowman.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get();
      if (_snowmanxxx == null) {
         throw new RuntimeException("ConnectionProtocol unknown: " + _snowman);
      } else {
         Integer _snowmanxxxx = _snowmanxxx.getPacketId(this.side, _snowman);
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(MARKER, "OUT: [{}:{}] {}", _snowman.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get(), _snowmanxxxx, _snowman.getClass().getName());
         }

         if (_snowmanxxxx == null) {
            throw new IOException("Can't serialize unregistered packet");
         } else {
            PacketByteBuf _snowmanxxxxx = new PacketByteBuf(_snowman);
            _snowmanxxxxx.writeVarInt(_snowmanxxxx);

            try {
               _snowman.write(_snowmanxxxxx);
            } catch (Throwable var8) {
               LOGGER.error(var8);
               if (_snowman.isWritingErrorSkippable()) {
                  throw new PacketEncoderException(var8);
               } else {
                  throw var8;
               }
            }
         }
      }
   }
}
