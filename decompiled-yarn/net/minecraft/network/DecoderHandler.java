package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class DecoderHandler extends ByteToMessageDecoder {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Marker MARKER = MarkerManager.getMarker("PACKET_RECEIVED", ClientConnection.MARKER_NETWORK_PACKETS);
   private final NetworkSide side;

   public DecoderHandler(NetworkSide side) {
      this.side = side;
   }

   protected void decode(ChannelHandlerContext _snowman, ByteBuf _snowman, List<Object> _snowman) throws Exception {
      if (_snowman.readableBytes() != 0) {
         PacketByteBuf _snowmanxxx = new PacketByteBuf(_snowman);
         int _snowmanxxxx = _snowmanxxx.readVarInt();
         Packet<?> _snowmanxxxxx = ((NetworkState)_snowman.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get()).getPacketHandler(this.side, _snowmanxxxx);
         if (_snowmanxxxxx == null) {
            throw new IOException("Bad packet id " + _snowmanxxxx);
         } else {
            _snowmanxxxxx.read(_snowmanxxx);
            if (_snowmanxxx.readableBytes() > 0) {
               throw new IOException(
                  "Packet "
                     + ((NetworkState)_snowman.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get()).getId()
                     + "/"
                     + _snowmanxxxx
                     + " ("
                     + _snowmanxxxxx.getClass().getSimpleName()
                     + ") was larger than I expected, found "
                     + _snowmanxxx.readableBytes()
                     + " bytes extra whilst reading packet "
                     + _snowmanxxxx
               );
            } else {
               _snowman.add(_snowmanxxxxx);
               if (LOGGER.isDebugEnabled()) {
                  LOGGER.debug(MARKER, " IN: [{}:{}] {}", _snowman.channel().attr(ClientConnection.ATTR_KEY_PROTOCOL).get(), _snowmanxxxx, _snowmanxxxxx.getClass().getName());
               }
            }
         }
      }
   }
}
