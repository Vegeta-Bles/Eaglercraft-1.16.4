import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class nh extends MessageToByteEncoder<oj<?>> {
   private static final Logger a = LogManager.getLogger();
   private static final Marker b = MarkerManager.getMarker("PACKET_SENT", nd.b);
   private final ok c;

   public nh(ok var1) {
      this.c = _snowman;
   }

   protected void a(ChannelHandlerContext var1, oj<?> var2, ByteBuf var3) throws Exception {
      ne _snowman = (ne)_snowman.channel().attr(nd.c).get();
      if (_snowman == null) {
         throw new RuntimeException("ConnectionProtocol unknown: " + _snowman);
      } else {
         Integer _snowmanx = _snowman.a(this.c, _snowman);
         if (a.isDebugEnabled()) {
            a.debug(b, "OUT: [{}:{}] {}", _snowman.channel().attr(nd.c).get(), _snowmanx, _snowman.getClass().getName());
         }

         if (_snowmanx == null) {
            throw new IOException("Can't serialize unregistered packet");
         } else {
            nf _snowmanxx = new nf(_snowman);
            _snowmanxx.d(_snowmanx);

            try {
               _snowman.b(_snowmanxx);
            } catch (Throwable var8) {
               a.error(var8);
               if (_snowman.a()) {
                  throw new nk(var8);
               } else {
                  throw var8;
               }
            }
         }
      }
   }
}
