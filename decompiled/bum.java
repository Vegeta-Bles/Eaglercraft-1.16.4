import javax.annotation.Nullable;

public class bum extends bud {
   public static final cfb a = bxm.aq;
   public static final cfe<cew> b = cex.R;
   public static final cey c = cex.w;
   private static final ddh d = buo.a(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
   private static final ddh e = buo.a(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   private static final ddh f = buo.a(5.0, 6.0, 5.0, 11.0, 13.0, 11.0);
   private static final ddh g = buo.a(4.0, 4.0, 4.0, 12.0, 6.0, 12.0);
   private static final ddh h = dde.a(g, f);
   private static final ddh i = dde.a(h, buo.a(7.0, 13.0, 0.0, 9.0, 15.0, 16.0));
   private static final ddh j = dde.a(h, buo.a(0.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final ddh k = dde.a(h, buo.a(0.0, 13.0, 7.0, 13.0, 15.0, 9.0));
   private static final ddh o = dde.a(h, buo.a(3.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final ddh p = dde.a(h, buo.a(7.0, 13.0, 0.0, 9.0, 15.0, 13.0));
   private static final ddh q = dde.a(h, buo.a(7.0, 13.0, 3.0, 9.0, 15.0, 16.0));
   private static final ddh r = dde.a(h, buo.a(7.0, 13.0, 7.0, 9.0, 16.0, 9.0));

   public bum(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, cew.a).a(c, Boolean.valueOf(false)));
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      boolean _snowman = _snowman.r(_snowman);
      if (_snowman != _snowman.c(c)) {
         if (_snowman) {
            this.a(_snowman, _snowman, null);
         }

         _snowman.a(_snowman, _snowman.a(c, Boolean.valueOf(_snowman)), 3);
      }
   }

   @Override
   public void a(brx var1, ceh var2, dcj var3, bgm var4) {
      aqa _snowman = _snowman.v();
      bfw _snowmanx = _snowman instanceof bfw ? (bfw)_snowman : null;
      this.a(_snowman, _snowman, _snowman, _snowmanx, true);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      return this.a(_snowman, _snowman, _snowman, _snowman, true) ? aou.a(_snowman.v) : aou.c;
   }

   public boolean a(brx var1, ceh var2, dcj var3, @Nullable bfw var4, boolean var5) {
      gc _snowman = _snowman.b();
      fx _snowmanx = _snowman.a();
      boolean _snowmanxx = !_snowman || this.a(_snowman, _snowman, _snowman.e().c - (double)_snowmanx.v());
      if (_snowmanxx) {
         boolean _snowmanxxx = this.a(_snowman, _snowmanx, _snowman);
         if (_snowmanxxx && _snowman != null) {
            _snowman.a(aea.ay);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean a(ceh var1, gc var2, double var3) {
      if (_snowman.n() != gc.a.b && !(_snowman > 0.8124F)) {
         gc _snowman = _snowman.c(a);
         cew _snowmanx = _snowman.c(b);
         switch (_snowmanx) {
            case a:
               return _snowman.n() == _snowman.n();
            case c:
            case d:
               return _snowman.n() != _snowman.n();
            case b:
               return true;
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   public boolean a(brx var1, fx var2, @Nullable gc var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (!_snowman.v && _snowman instanceof cch) {
         if (_snowman == null) {
            _snowman = _snowman.d_(_snowman).c(a);
         }

         ((cch)_snowman).a(_snowman);
         _snowman.a(null, _snowman, adq.aJ, adr.e, 2.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }

   private ddh h(ceh var1) {
      gc _snowman = _snowman.c(a);
      cew _snowmanx = _snowman.c(b);
      if (_snowmanx == cew.a) {
         return _snowman != gc.c && _snowman != gc.d ? e : d;
      } else if (_snowmanx == cew.b) {
         return r;
      } else if (_snowmanx == cew.d) {
         return _snowman != gc.c && _snowman != gc.d ? j : i;
      } else if (_snowman == gc.c) {
         return p;
      } else if (_snowman == gc.d) {
         return q;
      } else {
         return _snowman == gc.f ? o : k;
      }
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return this.h(_snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.h(_snowman);
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      gc _snowman = _snowman.j();
      fx _snowmanx = _snowman.a();
      brx _snowmanxx = _snowman.p();
      gc.a _snowmanxxx = _snowman.n();
      if (_snowmanxxx == gc.a.b) {
         ceh _snowmanxxxx = this.n().a(b, _snowman == gc.a ? cew.b : cew.a).a(a, _snowman.f());
         if (_snowmanxxxx.a((brz)_snowman.p(), _snowmanx)) {
            return _snowmanxxxx;
         }
      } else {
         boolean _snowmanxxxx = _snowmanxxx == gc.a.a && _snowmanxx.d_(_snowmanx.f()).d(_snowmanxx, _snowmanx.f(), gc.f) && _snowmanxx.d_(_snowmanx.g()).d(_snowmanxx, _snowmanx.g(), gc.e)
            || _snowmanxxx == gc.a.c && _snowmanxx.d_(_snowmanx.d()).d(_snowmanxx, _snowmanx.d(), gc.d) && _snowmanxx.d_(_snowmanx.e()).d(_snowmanxx, _snowmanx.e(), gc.c);
         ceh _snowmanxxxxx = this.n().a(a, _snowman.f()).a(b, _snowmanxxxx ? cew.d : cew.c);
         if (_snowmanxxxxx.a((brz)_snowman.p(), _snowman.a())) {
            return _snowmanxxxxx;
         }

         boolean _snowmanxxxxxx = _snowmanxx.d_(_snowmanx.c()).d(_snowmanxx, _snowmanx.c(), gc.b);
         _snowmanxxxxx = _snowmanxxxxx.a(b, _snowmanxxxxxx ? cew.a : cew.b);
         if (_snowmanxxxxx.a((brz)_snowman.p(), _snowman.a())) {
            return _snowmanxxxxx;
         }
      }

      return null;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      cew _snowman = _snowman.c(b);
      gc _snowmanx = l(_snowman).f();
      if (_snowmanx == _snowman && !_snowman.a(_snowman, _snowman) && _snowman != cew.d) {
         return bup.a.n();
      } else {
         if (_snowman.n() == _snowman.c(a).n()) {
            if (_snowman == cew.d && !_snowman.d(_snowman, _snowman, _snowman)) {
               return _snowman.a(b, cew.c).a(a, _snowman.f());
            }

            if (_snowman == cew.c && _snowmanx.f() == _snowman && _snowman.d(_snowman, _snowman, _snowman.c(a))) {
               return _snowman.a(b, cew.d);
            }
         }

         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      gc _snowman = l(_snowman).f();
      return _snowman == gc.b ? buo.a(_snowman, _snowman.b(), gc.a) : bwn.b(_snowman, _snowman, _snowman);
   }

   private static gc l(ceh var0) {
      switch ((cew)_snowman.c(b)) {
         case a:
            return gc.b;
         case b:
            return gc.a;
         default:
            return _snowman.c(a).f();
      }
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.b;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c);
   }

   @Nullable
   @Override
   public ccj a(brc var1) {
      return new cch();
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
