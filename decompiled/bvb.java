import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bvb extends bxm implements bnq {
   public static final cfb a = bxm.aq;
   @Nullable
   private cem b;
   @Nullable
   private cem c;
   @Nullable
   private cem d;
   @Nullable
   private cem e;
   private static final Predicate<ceh> f = var0 -> var0 != null && (var0.a(bup.cU) || var0.a(bup.cV));

   protected bvb(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c));
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         this.a(_snowman, _snowman);
      }
   }

   public boolean a(brz var1, fx var2) {
      return this.c().a(_snowman, _snowman) != null || this.e().a(_snowman, _snowman) != null;
   }

   private void a(brx var1, fx var2) {
      cem.b _snowman = this.d().a(_snowman, _snowman);
      if (_snowman != null) {
         for (int _snowmanx = 0; _snowmanx < this.d().b(); _snowmanx++) {
            cel _snowmanxx = _snowman.a(0, _snowmanx, 0);
            _snowman.a(_snowmanxx.d(), bup.a.n(), 2);
            _snowman.c(2001, _snowmanxx.d(), buo.i(_snowmanxx.a()));
         }

         bau _snowmanx = aqe.az.a(_snowman);
         fx _snowmanxx = _snowman.a(0, 2, 0).d();
         _snowmanx.b((double)_snowmanxx.u() + 0.5, (double)_snowmanxx.v() + 0.05, (double)_snowmanxx.w() + 0.5, 0.0F, 0.0F);
         _snowman.c(_snowmanx);

         for (aah _snowmanxxx : _snowman.a(aah.class, _snowmanx.cc().g(5.0))) {
            ac.n.a(_snowmanxxx, _snowmanx);
         }

         for (int _snowmanxxx = 0; _snowmanxxx < this.d().b(); _snowmanxxx++) {
            cel _snowmanxxxx = _snowman.a(0, _snowmanxxx, 0);
            _snowman.a(_snowmanxxxx.d(), bup.a);
         }
      } else {
         _snowman = this.t().a(_snowman, _snowman);
         if (_snowman != null) {
            for (int _snowmanx = 0; _snowmanx < this.t().c(); _snowmanx++) {
               for (int _snowmanxx = 0; _snowmanxx < this.t().b(); _snowmanxx++) {
                  cel _snowmanxxx = _snowman.a(_snowmanx, _snowmanxx, 0);
                  _snowman.a(_snowmanxxx.d(), bup.a.n(), 2);
                  _snowman.c(2001, _snowmanxxx.d(), buo.i(_snowmanxxx.a()));
               }
            }

            fx _snowmanx = _snowman.a(1, 2, 0).d();
            bai _snowmanxx = aqe.K.a(_snowman);
            _snowmanxx.u(true);
            _snowmanxx.b((double)_snowmanx.u() + 0.5, (double)_snowmanx.v() + 0.05, (double)_snowmanx.w() + 0.5, 0.0F, 0.0F);
            _snowman.c(_snowmanxx);

            for (aah _snowmanxxx : _snowman.a(aah.class, _snowmanxx.cc().g(5.0))) {
               ac.n.a(_snowmanxxx, _snowmanxx);
            }

            for (int _snowmanxxx = 0; _snowmanxxx < this.t().c(); _snowmanxxx++) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < this.t().b(); _snowmanxxxx++) {
                  cel _snowmanxxxxx = _snowman.a(_snowmanxxx, _snowmanxxxx, 0);
                  _snowman.a(_snowmanxxxxx.d(), bup.a);
               }
            }
         }
      }
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, _snowman.f().f());
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   private cem c() {
      if (this.b == null) {
         this.b = cen.a().a(" ", "#", "#").a('#', cel.a(cer.a(bup.cE))).b();
      }

      return this.b;
   }

   private cem d() {
      if (this.c == null) {
         this.c = cen.a().a("^", "#", "#").a('^', cel.a(f)).a('#', cel.a(cer.a(bup.cE))).b();
      }

      return this.c;
   }

   private cem e() {
      if (this.d == null) {
         this.d = cen.a().a("~ ~", "###", "~#~").a('#', cel.a(cer.a(bup.bF))).a('~', cel.a(cep.a(cva.a))).b();
      }

      return this.d;
   }

   private cem t() {
      if (this.e == null) {
         this.e = cen.a().a("~^~", "###", "~#~").a('^', cel.a(f)).a('#', cel.a(cer.a(bup.bF))).a('~', cel.a(cep.a(cva.a))).b();
      }

      return this.e;
   }
}
