import java.util.List;
import java.util.Map;
import java.util.Random;

public class cbd extends buo {
   public static final cey a = cex.w;
   public static final cey b = cex.a;
   public static final cey c = cex.d;
   public static final cey d = bys.a;
   public static final cey e = bys.b;
   public static final cey f = bys.c;
   public static final cey g = bys.d;
   private static final Map<gc, cey> j = bvt.f;
   protected static final ddh h = buo.a(0.0, 1.0, 0.0, 16.0, 2.5, 16.0);
   protected static final ddh i = buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private final cbe k;

   public cbd(cbe var1, ceg.c var2) {
      super(_snowman);
      this.j(
         this.n
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
            .a(f, Boolean.valueOf(false))
            .a(g, Boolean.valueOf(false))
      );
      this.k = _snowman;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return _snowman.c(b) ? h : i;
   }

   @Override
   public ceh a(bny var1) {
      brc _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      return this.n()
         .a(d, Boolean.valueOf(this.a(_snowman.d_(_snowmanx.d()), gc.c)))
         .a(e, Boolean.valueOf(this.a(_snowman.d_(_snowmanx.g()), gc.f)))
         .a(f, Boolean.valueOf(this.a(_snowman.d_(_snowmanx.e()), gc.d)))
         .a(g, Boolean.valueOf(this.a(_snowman.d_(_snowmanx.f()), gc.e)));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman.n().d() ? _snowman.a(j.get(_snowman), Boolean.valueOf(this.a(_snowman, _snowman))) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         this.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman && !_snowman.a(_snowman.b())) {
         this.a(_snowman, _snowman, _snowman.a(a, Boolean.valueOf(true)));
      }
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      if (!_snowman.v && !_snowman.dD().a() && _snowman.dD().b() == bmd.ng) {
         _snowman.a(_snowman, _snowman.a(c, Boolean.valueOf(true)), 4);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private void a(brx var1, fx var2, ceh var3) {
      for (gc _snowman : new gc[]{gc.d, gc.e}) {
         for (int _snowmanx = 1; _snowmanx < 42; _snowmanx++) {
            fx _snowmanxx = _snowman.a(_snowman, _snowmanx);
            ceh _snowmanxxx = _snowman.d_(_snowmanxx);
            if (_snowmanxxx.a(this.k)) {
               if (_snowmanxxx.c(cbe.a) == _snowman.f()) {
                  this.k.a(_snowman, _snowmanxx, _snowmanxxx, false, true, _snowmanx, _snowman);
               }
               break;
            }

            if (!_snowmanxxx.a(this)) {
               break;
            }
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.v) {
         if (!_snowman.c(a)) {
            this.a(_snowman, _snowman);
         }
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.d_(_snowman).c(a)) {
         this.a(_snowman, _snowman);
      }
   }

   private void a(brx var1, fx var2) {
      ceh _snowman = _snowman.d_(_snowman);
      boolean _snowmanx = _snowman.c(a);
      boolean _snowmanxx = false;
      List<? extends aqa> _snowmanxxx = _snowman.a(null, _snowman.j(_snowman, _snowman).a().a(_snowman));
      if (!_snowmanxxx.isEmpty()) {
         for (aqa _snowmanxxxx : _snowmanxxx) {
            if (!_snowmanxxxx.bQ()) {
               _snowmanxx = true;
               break;
            }
         }
      }

      if (_snowmanxx != _snowmanx) {
         _snowman = _snowman.a(a, Boolean.valueOf(_snowmanxx));
         _snowman.a(_snowman, _snowman, 3);
         this.a(_snowman, _snowman, _snowman);
      }

      if (_snowmanxx) {
         _snowman.J().a(new fx(_snowman), this, 10);
      }
   }

   public boolean a(ceh var1, gc var2) {
      buo _snowman = _snowman.b();
      return _snowman == this.k ? _snowman.c(cbe.a) == _snowman.f() : _snowman == this;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      switch (_snowman) {
         case c:
            return _snowman.a(d, _snowman.c(f)).a(e, _snowman.c(g)).a(f, _snowman.c(d)).a(g, _snowman.c(e));
         case d:
            return _snowman.a(d, _snowman.c(e)).a(e, _snowman.c(f)).a(f, _snowman.c(g)).a(g, _snowman.c(d));
         case b:
            return _snowman.a(d, _snowman.c(g)).a(e, _snowman.c(d)).a(f, _snowman.c(e)).a(g, _snowman.c(f));
         default:
            return _snowman;
      }
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      switch (_snowman) {
         case b:
            return _snowman.a(d, _snowman.c(f)).a(f, _snowman.c(d));
         case c:
            return _snowman.a(e, _snowman.c(g)).a(g, _snowman.c(e));
         default:
            return super.a(_snowman, _snowman);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c, d, e, g, f);
   }
}
