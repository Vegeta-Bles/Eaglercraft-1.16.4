import java.util.EnumSet;
import java.util.function.Predicate;

public class avn extends avv {
   private static final Predicate<ceh> a = cer.a(bup.aR);
   private final aqn b;
   private final brx c;
   private int d;

   public avn(aqn var1) {
      this.b = _snowman;
      this.c = _snowman.l;
      this.a(EnumSet.of(avv.a.a, avv.a.b, avv.a.c));
   }

   @Override
   public boolean a() {
      if (this.b.cY().nextInt(this.b.w_() ? 50 : 1000) != 0) {
         return false;
      } else {
         fx _snowman = this.b.cB();
         return a.test(this.c.d_(_snowman)) ? true : this.c.d_(_snowman.c()).a(bup.i);
      }
   }

   @Override
   public void c() {
      this.d = 40;
      this.c.a(this.b, (byte)10);
      this.b.x().o();
   }

   @Override
   public void d() {
      this.d = 0;
   }

   @Override
   public boolean b() {
      return this.d > 0;
   }

   public int g() {
      return this.d;
   }

   @Override
   public void e() {
      this.d = Math.max(0, this.d - 1);
      if (this.d == 4) {
         fx _snowman = this.b.cB();
         if (a.test(this.c.d_(_snowman))) {
            if (this.c.V().b(brt.b)) {
               this.c.b(_snowman, false);
            }

            this.b.B();
         } else {
            fx _snowmanx = _snowman.c();
            if (this.c.d_(_snowmanx).a(bup.i)) {
               if (this.c.V().b(brt.b)) {
                  this.c.c(2001, _snowmanx, buo.i(bup.i.n()));
                  this.c.a(_snowmanx, bup.j.n(), 2);
               }

               this.b.B();
            }
         }
      }
   }
}
