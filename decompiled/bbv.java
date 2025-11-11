import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bbv extends bbt {
   private static final Logger b = LogManager.getLogger();
   private dcn c;
   private int d;

   public bbv(bbr var1) {
      super(_snowman);
   }

   @Override
   public void c() {
      if (this.c == null) {
         b.warn("Aborting charge player as no target was set.");
         this.a.eK().a(bch.a);
      } else if (this.d > 0 && this.d++ >= 10) {
         this.a.eK().a(bch.a);
      } else {
         double _snowman = this.c.c(this.a.cD(), this.a.cE(), this.a.cH());
         if (_snowman < 100.0 || _snowman > 22500.0 || this.a.u || this.a.v) {
            this.d++;
         }
      }
   }

   @Override
   public void d() {
      this.c = null;
      this.d = 0;
   }

   public void a(dcn var1) {
      this.c = _snowman;
   }

   @Override
   public float f() {
      return 3.0F;
   }

   @Nullable
   @Override
   public dcn g() {
      return this.c;
   }

   @Override
   public bch<bbv> i() {
      return bch.i;
   }
}
