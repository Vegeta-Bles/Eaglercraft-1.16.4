public class bwr extends bxm {
   public static final cey a = cex.u;
   public static final cey b = cex.w;
   public static final cey c = cex.q;
   protected static final ddh d = buo.a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final ddh e = buo.a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
   protected static final ddh f = buo.a(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
   protected static final ddh g = buo.a(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
   protected static final ddh h = buo.a(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
   protected static final ddh i = buo.a(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
   protected static final ddh j = dde.a(buo.a(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), buo.a(14.0, 5.0, 7.0, 16.0, 16.0, 9.0));
   protected static final ddh k = dde.a(buo.a(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), buo.a(7.0, 5.0, 14.0, 9.0, 16.0, 16.0));
   protected static final ddh o = dde.a(buo.a(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), buo.a(14.0, 2.0, 7.0, 16.0, 13.0, 9.0));
   protected static final ddh p = dde.a(buo.a(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), buo.a(7.0, 2.0, 14.0, 9.0, 13.0, 16.0));

   public bwr(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      if (_snowman.c(c)) {
         return _snowman.c(aq).n() == gc.a.a ? g : f;
      } else {
         return _snowman.c(aq).n() == gc.a.a ? e : d;
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      gc.a _snowman = _snowman.n();
      if (_snowman.c(aq).g().n() != _snowman) {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         boolean _snowmanx = this.h(_snowman) || this.h(_snowman.d_(_snowman.a(_snowman.f())));
         return _snowman.a(c, Boolean.valueOf(_snowmanx));
      }
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      if (_snowman.c(a)) {
         return dde.a();
      } else {
         return _snowman.c(aq).n() == gc.a.c ? h : i;
      }
   }

   @Override
   public ddh d(ceh var1, brc var2, fx var3) {
      if (_snowman.c(c)) {
         return _snowman.c(aq).n() == gc.a.a ? p : o;
      } else {
         return _snowman.c(aq).n() == gc.a.a ? k : j;
      }
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      switch (_snowman) {
         case a:
            return _snowman.c(a);
         case b:
            return false;
         case c:
            return _snowman.c(a);
         default:
            return false;
      }
   }

   @Override
   public ceh a(bny var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      boolean _snowmanxx = _snowman.r(_snowmanx);
      gc _snowmanxxx = _snowman.f();
      gc.a _snowmanxxxx = _snowmanxxx.n();
      boolean _snowmanxxxxx = _snowmanxxxx == gc.a.c && (this.h(_snowman.d_(_snowmanx.f())) || this.h(_snowman.d_(_snowmanx.g()))) || _snowmanxxxx == gc.a.a && (this.h(_snowman.d_(_snowmanx.d())) || this.h(_snowman.d_(_snowmanx.e())));
      return this.n().a(aq, _snowmanxxx).a(a, Boolean.valueOf(_snowmanxx)).a(b, Boolean.valueOf(_snowmanxx)).a(c, Boolean.valueOf(_snowmanxxxxx));
   }

   private boolean h(ceh var1) {
      return _snowman.b().a(aed.F);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.c(a)) {
         _snowman = _snowman.a(a, Boolean.valueOf(false));
         _snowman.a(_snowman, _snowman, 10);
      } else {
         gc _snowman = _snowman.bZ();
         if (_snowman.c(aq) == _snowman.f()) {
            _snowman = _snowman.a(aq, _snowman);
         }

         _snowman = _snowman.a(a, Boolean.valueOf(true));
         _snowman.a(_snowman, _snowman, 10);
      }

      _snowman.a(_snowman, _snowman.c(a) ? 1008 : 1014, _snowman, 0);
      return aou.a(_snowman.v);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (!_snowman.v) {
         boolean _snowman = _snowman.r(_snowman);
         if (_snowman.c(b) != _snowman) {
            _snowman.a(_snowman, _snowman.a(b, Boolean.valueOf(_snowman)).a(a, Boolean.valueOf(_snowman)), 2);
            if (_snowman.c(a) != _snowman) {
               _snowman.a(null, _snowman ? 1008 : 1014, _snowman, 0);
            }
         }
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(aq, a, b, c);
   }

   public static boolean a(ceh var0, gc var1) {
      return _snowman.c(aq).n() == _snowman.g().n();
   }
}
