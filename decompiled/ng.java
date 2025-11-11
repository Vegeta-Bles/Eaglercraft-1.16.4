import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class ng extends ByteToMessageDecoder {
   private static final Logger a = LogManager.getLogger();
   private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", nd.b);
   private final ok c;

   public ng(ok var1) {
      this.c = _snowman;
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
      if (_snowman.readableBytes() != 0) {
         nf _snowman = new nf(_snowman);
         int _snowmanx = _snowman.i();
         oj<?> _snowmanxx = ((ne)_snowman.channel().attr(nd.c).get()).a(this.c, _snowmanx);
         if (_snowmanxx == null) {
            throw new IOException("Bad packet id " + _snowmanx);
         } else {
            _snowmanxx.a(_snowman);
            if (_snowman.readableBytes() > 0) {
               throw new IOException(
                  "Packet "
                     + ((ne)_snowman.channel().attr(nd.c).get()).a()
                     + "/"
                     + _snowmanx
                     + " ("
                     + _snowmanxx.getClass().getSimpleName()
                     + ") was larger than I expected, found "
                     + _snowman.readableBytes()
                     + " bytes extra whilst reading packet "
                     + _snowmanx
               );
            } else {
               _snowman.add(_snowmanxx);
               if (a.isDebugEnabled()) {
                  a.debug(b, " IN: [{}:{}] {}", _snowman.channel().attr(nd.c).get(), _snowmanx, _snowmanxx.getClass().getName());
               }
            }
         }
      }
   }
}
