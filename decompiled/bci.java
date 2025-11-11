import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bci {
   private static final Logger a = LogManager.getLogger();
   private final bbr b;
   private final bcb[] c = new bcb[bch.c()];
   private bcb d;

   public bci(bbr var1) {
      this.b = _snowman;
      this.a(bch.k);
   }

   public void a(bch<?> var1) {
      if (this.d == null || _snowman != this.d.i()) {
         if (this.d != null) {
            this.d.e();
         }

         this.d = this.b((bch<bcb>)_snowman);
         if (!this.b.l.v) {
            this.b.ab().b(bbr.b, _snowman.b());
         }

         a.debug("Dragon is now in phase {} on the {}", _snowman, this.b.l.v ? "client" : "server");
         this.d.d();
      }
   }

   public bcb a() {
      return this.d;
   }

   public <T extends bcb> T b(bch<T> var1) {
      int _snowman = _snowman.b();
      if (this.c[_snowman] == null) {
         this.c[_snowman] = _snowman.a(this.b);
      }

      return (T)this.c[_snowman];
   }
}
