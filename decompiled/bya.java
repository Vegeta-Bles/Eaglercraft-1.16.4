import java.util.Random;

public class bya extends bwn {
   public static final cey a = cex.w;
   protected static final ddh b = buo.a(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
   protected static final ddh c = buo.a(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
   protected static final ddh d = buo.a(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
   protected static final ddh e = buo.a(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
   protected static final ddh f = buo.a(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
   protected static final ddh g = buo.a(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
   protected static final ddh h = buo.a(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
   protected static final ddh i = buo.a(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

   protected bya(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(aq, gc.c).a(a, Boolean.valueOf(false)).a(u, cet.b));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch ((cet)_snowman.c(u)) {
         case a:
            switch (_snowman.c(aq).n()) {
               case a:
                  return g;
               case c:
               default:
                  return f;
            }
         case b:
            switch ((gc)_snowman.c(aq)) {
               case f:
                  return e;
               case e:
                  return d;
               case d:
                  return c;
               case c:
               default:
                  return b;
            }
         case c:
         default:
            switch (_snowman.c(aq).n()) {
               case a:
                  return i;
               case c:
               default:
                  return h;
            }
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         ceh _snowman = _snowman.a(a);
         if (_snowman.c(a)) {
            a(_snowman, _snowman, _snowman, 1.0F);
         }

         return aou.a;
      } else {
         ceh _snowman = this.d(_snowman, _snowman, _snowman);
         float _snowmanx = _snowman.c(a) ? 0.6F : 0.5F;
         _snowman.a(null, _snowman, adq.hb, adr.e, 0.3F, _snowmanx);
         return aou.b;
      }
   }

   public ceh d(ceh var1, brx var2, fx var3) {
      _snowman = _snowman.a(a);
      _snowman.a(_snowman, _snowman, 3);
      this.e(_snowman, _snowman, _snowman);
      return _snowman;
   }

   private static void a(ceh var0, bry var1, fx var2, float var3) {
      gc _snowman = _snowman.c(aq).f();
      gc _snowmanx = h(_snowman).f();
      double _snowmanxx = (double)_snowman.u() + 0.5 + 0.1 * (double)_snowman.i() + 0.2 * (double)_snowmanx.i();
      double _snowmanxxx = (double)_snowman.v() + 0.5 + 0.1 * (double)_snowman.j() + 0.2 * (double)_snowmanx.j();
      double _snowmanxxxx = (double)_snowman.w() + 0.5 + 0.1 * (double)_snowman.k() + 0.2 * (double)_snowmanx.k();
      _snowman.a(new hd(1.0F, 0.0F, 0.0F, _snowman), _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(a) && _snowman.nextFloat() < 0.25F) {
         a(_snowman, _snowman, _snowman, 0.5F);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman && !_snowman.a(_snowman.b())) {
         if (_snowman.c(a)) {
            this.e(_snowman, _snowman, _snowman);
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

   private void e(ceh var1, brx var2, fx var3) {
      _snowman.b(_snowman, this);
      _snowman.b(_snowman.a(h(_snowman).f()), this);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(u, aq, a);
   }
}
