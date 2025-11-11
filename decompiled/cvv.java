import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cvv {
   private static final Logger a = LogManager.getLogger();
   private final cvh b;

   public cvv(cvg<cvh> var1) {
      this.b = _snowman.make();
   }

   public bsv a(gm<bsv> var1, int var2, int var3) {
      int _snowman = this.b.a(_snowman, _snowman);
      vj<bsv> _snowmanx = kt.a(_snowman);
      if (_snowmanx == null) {
         throw new IllegalStateException("Unknown biome id emitted by layers: " + _snowman);
      } else {
         bsv _snowmanxx = _snowman.a(_snowmanx);
         if (_snowmanxx == null) {
            if (w.d) {
               throw (IllegalStateException)x.c(new IllegalStateException("Unknown biome id: " + _snowman));
            } else {
               a.warn("Unknown biome id: ", _snowman);
               return _snowman.a(kt.a(0));
            }
         } else {
            return _snowmanxx;
         }
      }
   }
}
