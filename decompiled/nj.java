import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class nj extends nd {
   private static final Logger g = LogManager.getLogger();
   private static final nr h = new of("disconnect.exceeded_packet_rate");
   private final int i;

   public nj(int var1) {
      super(ok.a);
      this.i = _snowman;
   }

   @Override
   protected void b() {
      super.b();
      float _snowman = this.n();
      if (_snowman > (float)this.i) {
         g.warn("Player exceeded rate-limit (sent {} packets per second)", _snowman);
         this.a(new pm(h), var1x -> this.a(h));
         this.l();
      }
   }
}
