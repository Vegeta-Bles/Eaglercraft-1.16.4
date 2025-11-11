import javax.annotation.Nullable;

public class bwb extends buo {
   public static final cfb a = bxm.aq;
   public static final cey b = cex.u;
   public static final cfe<cfc> c = cex.aH;
   public static final cey d = cex.w;
   public static final cfe<cfd> e = cex.aa;
   protected static final ddh f = buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final ddh g = buo.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final ddh h = buo.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final ddh i = buo.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

   protected bwb(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(false)).a(c, cfc.a).a(d, Boolean.valueOf(false)).a(e, cfd.b));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      gc _snowman = _snowman.c(a);
      boolean _snowmanx = !_snowman.c(b);
      boolean _snowmanxx = _snowman.c(c) == cfc.b;
      switch (_snowman) {
         case f:
         default:
            return _snowmanx ? i : (_snowmanxx ? g : f);
         case d:
            return _snowmanx ? f : (_snowmanxx ? i : h);
         case e:
            return _snowmanx ? h : (_snowmanxx ? f : g);
         case c:
            return _snowmanx ? g : (_snowmanxx ? h : i);
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      cfd _snowman = _snowman.c(e);
      if (_snowman.n() != gc.a.b || _snowman == cfd.b != (_snowman == gc.b)) {
         return _snowman == cfd.b && _snowman == gc.a && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         return _snowman.a(this) && _snowman.c(e) != _snowman ? _snowman.a(a, _snowman.c(a)).a(b, _snowman.c(b)).a(c, _snowman.c(c)).a(d, _snowman.c(d)) : bup.a.n();
      }
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      if (!_snowman.v && _snowman.b_()) {
         bwd.b(_snowman, _snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      switch (_snowman) {
         case a:
            return _snowman.c(b);
         case b:
            return false;
         case c:
            return _snowman.c(b);
         default:
            return false;
      }
   }

   private int c() {
      return this.as == cva.J ? 1011 : 1012;
   }

   private int d() {
      return this.as == cva.J ? 1005 : 1006;
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      fx _snowman = _snowman.a();
      if (_snowman.v() < 255 && _snowman.p().d_(_snowman.b()).a(_snowman)) {
         brx _snowmanx = _snowman.p();
         boolean _snowmanxx = _snowmanx.r(_snowman) || _snowmanx.r(_snowman.b());
         return this.n().a(a, _snowman.f()).a(c, this.b(_snowman)).a(d, Boolean.valueOf(_snowmanxx)).a(b, Boolean.valueOf(_snowmanxx)).a(e, cfd.b);
      } else {
         return null;
      }
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      _snowman.a(_snowman.b(), _snowman.a(e, cfd.a), 3);
   }

   private cfc b(bny var1) {
      brc _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      gc _snowmanxx = _snowman.f();
      fx _snowmanxxx = _snowmanx.b();
      gc _snowmanxxxx = _snowmanxx.h();
      fx _snowmanxxxxx = _snowmanx.a(_snowmanxxxx);
      ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
      fx _snowmanxxxxxxx = _snowmanxxx.a(_snowmanxxxx);
      ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxxxxxx);
      gc _snowmanxxxxxxxxx = _snowmanxx.g();
      fx _snowmanxxxxxxxxxx = _snowmanx.a(_snowmanxxxxxxxxx);
      ceh _snowmanxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxx);
      fx _snowmanxxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxx);
      ceh _snowmanxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxx = (_snowmanxxxxxx.r(_snowman, _snowmanxxxxx) ? -1 : 0)
         + (_snowmanxxxxxxxx.r(_snowman, _snowmanxxxxxxx) ? -1 : 0)
         + (_snowmanxxxxxxxxxxx.r(_snowman, _snowmanxxxxxxxxxx) ? 1 : 0)
         + (_snowmanxxxxxxxxxxxxx.r(_snowman, _snowmanxxxxxxxxxxxx) ? 1 : 0);
      boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxx.a(this) && _snowmanxxxxxx.c(e) == cfd.b;
      boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.a(this) && _snowmanxxxxxxxxxxx.c(e) == cfd.b;
      if ((!_snowmanxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxxx) && _snowmanxxxxxxxxxxxxxx <= 0) {
         if ((!_snowmanxxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxx) && _snowmanxxxxxxxxxxxxxx >= 0) {
            int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxx.i();
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxx.k();
            dcn _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.k();
            double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.b - (double)_snowmanx.u();
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.d - (double)_snowmanx.w();
            return (_snowmanxxxxxxxxxxxxxxxxx >= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxxx < 0.5))
                  && (_snowmanxxxxxxxxxxxxxxxxx <= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxxx > 0.5))
                  && (_snowmanxxxxxxxxxxxxxxxxxx >= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxx > 0.5))
                  && (_snowmanxxxxxxxxxxxxxxxxxx <= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxx < 0.5))
               ? cfc.a
               : cfc.b;
         } else {
            return cfc.a;
         }
      } else {
         return cfc.b;
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (this.as == cva.J) {
         return aou.c;
      } else {
         _snowman = _snowman.a(b);
         _snowman.a(_snowman, _snowman, 10);
         _snowman.a(_snowman, _snowman.c(b) ? this.d() : this.c(), _snowman, 0);
         return aou.a(_snowman.v);
      }
   }

   public boolean h(ceh var1) {
      return _snowman.c(b);
   }

   public void a(brx var1, ceh var2, fx var3, boolean var4) {
      if (_snowman.a(this) && _snowman.c(b) != _snowman) {
         _snowman.a(_snowman, _snowman.a(b, Boolean.valueOf(_snowman)), 10);
         this.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      boolean _snowman = _snowman.r(_snowman) || _snowman.r(_snowman.a(_snowman.c(e) == cfd.b ? gc.b : gc.a));
      if (_snowman != this && _snowman != _snowman.c(d)) {
         if (_snowman != _snowman.c(b)) {
            this.a(_snowman, _snowman, _snowman);
         }

         _snowman.a(_snowman, _snowman.a(d, Boolean.valueOf(_snowman)).a(b, Boolean.valueOf(_snowman)), 2);
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.c();
      ceh _snowmanx = _snowman.d_(_snowman);
      return _snowman.c(e) == cfd.b ? _snowmanx.d(_snowman, _snowman, gc.b) : _snowmanx.a(this);
   }

   private void a(brx var1, fx var2, boolean var3) {
      _snowman.a(null, _snowman ? this.d() : this.c(), _snowman, 0);
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.b;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman == byg.a ? _snowman : _snowman.a(_snowman.a(_snowman.c(a))).a(c);
   }

   @Override
   public long a(ceh var1, fx var2) {
      return afm.c(_snowman.u(), _snowman.c(_snowman.c(e) == cfd.b ? 0 : 1).v(), _snowman.w());
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(e, a, b, c, d);
   }

   public static boolean a(brx var0, fx var1) {
      return l(_snowman.d_(_snowman));
   }

   public static boolean l(ceh var0) {
      return _snowman.b() instanceof bwb && (_snowman.c() == cva.y || _snowman.c() == cva.z);
   }
}
