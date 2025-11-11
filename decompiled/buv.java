import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public abstract class buv extends bwn {
   public static final cey a = cex.w;
   protected static final ddh b = buo.a(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
   protected static final ddh c = buo.a(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
   protected static final ddh d = buo.a(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
   protected static final ddh e = buo.a(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
   protected static final ddh f = buo.a(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
   protected static final ddh g = buo.a(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
   protected static final ddh h = buo.a(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final ddh i = buo.a(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
   protected static final ddh j = buo.a(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
   protected static final ddh k = buo.a(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
   protected static final ddh o = buo.a(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
   protected static final ddh p = buo.a(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
   protected static final ddh q = buo.a(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
   protected static final ddh r = buo.a(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
   protected static final ddh s = buo.a(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final ddh t = buo.a(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
   private final boolean v;

   protected buv(boolean var1, ceg.c var2) {
      super(_snowman);
      this.j(this.n.b().a(aq, gc.c).a(a, Boolean.valueOf(false)).a(u, cet.b));
      this.v = _snowman;
   }

   private int c() {
      return this.v ? 30 : 20;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      gc _snowman = _snowman.c(aq);
      boolean _snowmanx = _snowman.c(a);
      switch ((cet)_snowman.c(u)) {
         case a:
            if (_snowman.n() == gc.a.a) {
               return _snowmanx ? o : d;
            }

            return _snowmanx ? p : e;
         case b:
            switch (_snowman) {
               case f:
                  return _snowmanx ? t : i;
               case e:
                  return _snowmanx ? s : h;
               case d:
                  return _snowmanx ? r : g;
               case c:
               default:
                  return _snowmanx ? q : f;
            }
         case c:
         default:
            if (_snowman.n() == gc.a.a) {
               return _snowmanx ? j : b;
            } else {
               return _snowmanx ? k : c;
            }
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.c(a)) {
         return aou.b;
      } else {
         this.d(_snowman, _snowman, _snowman);
         this.a(_snowman, _snowman, _snowman, true);
         return aou.a(_snowman.v);
      }
   }

   public void d(ceh var1, brx var2, fx var3) {
      _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(true)), 3);
      this.f(_snowman, _snowman, _snowman);
      _snowman.J().a(_snowman, this, this.c());
   }

   protected void a(@Nullable bfw var1, bry var2, fx var3, boolean var4) {
      _snowman.a(_snowman ? _snowman : null, _snowman, this.a(_snowman), adr.e, 0.3F, _snowman ? 0.6F : 0.5F);
   }

   protected abstract adp a(boolean var1);

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman && !_snowman.a(_snowman.b())) {
         if (_snowman.c(a)) {
            this.f(_snowman, _snowman, _snowman);
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(a) ? 15 : 0;
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(a) && h(_snowman) == _snowman ? 15 : 0;
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(a)) {
         if (this.v) {
            this.e(_snowman, _snowman, _snowman);
         } else {
            _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(false)), 3);
            this.f(_snowman, _snowman, _snowman);
            this.a(null, _snowman, _snowman, false);
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.v && this.v && !_snowman.c(a)) {
         this.e(_snowman, _snowman, _snowman);
      }
   }

   private void e(ceh var1, brx var2, fx var3) {
      List<? extends aqa> _snowman = _snowman.a(bga.class, _snowman.j(_snowman, _snowman).a().a(_snowman));
      boolean _snowmanx = !_snowman.isEmpty();
      boolean _snowmanxx = _snowman.c(a);
      if (_snowmanx != _snowmanxx) {
         _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(_snowmanx)), 3);
         this.f(_snowman, _snowman, _snowman);
         this.a(null, _snowman, _snowman, _snowmanx);
      }

      if (_snowmanx) {
         _snowman.J().a(new fx(_snowman), this, this.c());
      }
   }

   private void f(ceh var1, brx var2, fx var3) {
      _snowman.b(_snowman, this);
      _snowman.b(_snowman.a(h(_snowman).f()), this);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(aq, a, u);
   }
}
