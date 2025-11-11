import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

public class cbk extends buo implements bzu {
   public static final cey a = cex.G;
   public static final cfe<cfp> b = cex.S;
   public static final cfe<cfp> c = cex.T;
   public static final cfe<cfp> d = cex.U;
   public static final cfe<cfp> e = cex.V;
   public static final cey f = cex.C;
   private final Map<ceh, ddh> g;
   private final Map<ceh, ddh> h;
   private static final ddh i = buo.a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final ddh j = buo.a(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
   private static final ddh k = buo.a(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
   private static final ddh o = buo.a(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final ddh p = buo.a(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

   public cbk(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Boolean.valueOf(true)).a(c, cfp.a).a(b, cfp.a).a(d, cfp.a).a(e, cfp.a).a(f, Boolean.valueOf(false)));
      this.g = this.a(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
      this.h = this.a(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   private static ddh a(ddh var0, cfp var1, ddh var2, ddh var3) {
      if (_snowman == cfp.c) {
         return dde.a(_snowman, _snowman);
      } else {
         return _snowman == cfp.b ? dde.a(_snowman, _snowman) : _snowman;
      }
   }

   private Map<ceh, ddh> a(float var1, float var2, float var3, float var4, float var5, float var6) {
      float _snowman = 8.0F - _snowman;
      float _snowmanx = 8.0F + _snowman;
      float _snowmanxx = 8.0F - _snowman;
      float _snowmanxxx = 8.0F + _snowman;
      ddh _snowmanxxxx = buo.a((double)_snowman, 0.0, (double)_snowman, (double)_snowmanx, (double)_snowman, (double)_snowmanx);
      ddh _snowmanxxxxx = buo.a((double)_snowmanxx, (double)_snowman, 0.0, (double)_snowmanxxx, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxx = buo.a((double)_snowmanxx, (double)_snowman, (double)_snowmanxx, (double)_snowmanxxx, (double)_snowman, 16.0);
      ddh _snowmanxxxxxxx = buo.a(0.0, (double)_snowman, (double)_snowmanxx, (double)_snowmanxxx, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxxxx = buo.a((double)_snowmanxx, (double)_snowman, (double)_snowmanxx, 16.0, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxxxxx = buo.a((double)_snowmanxx, (double)_snowman, 0.0, (double)_snowmanxxx, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxxxxxx = buo.a((double)_snowmanxx, (double)_snowman, (double)_snowmanxx, (double)_snowmanxxx, (double)_snowman, 16.0);
      ddh _snowmanxxxxxxxxxxx = buo.a(0.0, (double)_snowman, (double)_snowmanxx, (double)_snowmanxxx, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxxxxxxxx = buo.a((double)_snowmanxx, (double)_snowman, (double)_snowmanxx, 16.0, (double)_snowman, (double)_snowmanxxx);
      Builder<ceh, ddh> _snowmanxxxxxxxxxxxxx = ImmutableMap.builder();

      for (Boolean _snowmanxxxxxxxxxxxxxx : a.a()) {
         for (cfp _snowmanxxxxxxxxxxxxxxx : b.a()) {
            for (cfp _snowmanxxxxxxxxxxxxxxxx : c.a()) {
               for (cfp _snowmanxxxxxxxxxxxxxxxxx : e.a()) {
                  for (cfp _snowmanxxxxxxxxxxxxxxxxxx : d.a()) {
                     ddh _snowmanxxxxxxxxxxxxxxxxxxx = dde.a();
                     _snowmanxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxx) {
                        _snowmanxxxxxxxxxxxxxxxxxxx = dde.a(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxx);
                     }

                     ceh _snowmanxxxxxxxxxxxxxxxxxxxx = this.n()
                        .a(a, _snowmanxxxxxxxxxxxxxx)
                        .a(b, _snowmanxxxxxxxxxxxxxxx)
                        .a(e, _snowmanxxxxxxxxxxxxxxxxx)
                        .a(c, _snowmanxxxxxxxxxxxxxxxx)
                        .a(d, _snowmanxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxxxxxxxxxxx.a(f, Boolean.valueOf(false)), _snowmanxxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxxxxxxxxxxx.a(f, Boolean.valueOf(true)), _snowmanxxxxxxxxxxxxxxxxxxx);
                  }
               }
            }
         }
      }

      return _snowmanxxxxxxxxxxxxx.build();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.g.get(_snowman);
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return this.h.get(_snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   private boolean a(ceh var1, boolean var2, gc var3) {
      buo _snowman = _snowman.b();
      boolean _snowmanx = _snowman instanceof bwr && bwr.a(_snowman, _snowman);
      return _snowman.a(aed.F) || !b(_snowman) && _snowman || _snowman instanceof bxq || _snowmanx;
   }

   @Override
   public ceh a(bny var1) {
      brz _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      cux _snowmanxx = _snowman.p().b(_snowman.a());
      fx _snowmanxxx = _snowmanx.d();
      fx _snowmanxxxx = _snowmanx.g();
      fx _snowmanxxxxx = _snowmanx.e();
      fx _snowmanxxxxxx = _snowmanx.f();
      fx _snowmanxxxxxxx = _snowmanx.b();
      ceh _snowmanxxxxxxxx = _snowman.d_(_snowmanxxx);
      ceh _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxx);
      ceh _snowmanxxxxxxxxxx = _snowman.d_(_snowmanxxxxx);
      ceh _snowmanxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxx);
      ceh _snowmanxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxx);
      boolean _snowmanxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxx, _snowmanxxxxxxxx.d(_snowman, _snowmanxxx, gc.d), gc.d);
      boolean _snowmanxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.d(_snowman, _snowmanxxxx, gc.e), gc.e);
      boolean _snowmanxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx.d(_snowman, _snowmanxxxxx, gc.c), gc.c);
      boolean _snowmanxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx.d(_snowman, _snowmanxxxxxx, gc.f), gc.f);
      ceh _snowmanxxxxxxxxxxxxxxxxx = this.n().a(f, Boolean.valueOf(_snowmanxx.a() == cuy.c));
      return this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(f)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      if (_snowman == gc.a) {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else {
         return _snowman == gc.b ? this.a(_snowman, _snowman, _snowman, _snowman) : this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private static boolean a(ceh var0, cfj<cfp> var1) {
      return _snowman.c(_snowman) != cfp.a;
   }

   private static boolean a(ddh var0, ddh var1) {
      return !dde.c(_snowman, _snowman, dcr.e);
   }

   private ceh a(brz var1, ceh var2, fx var3, ceh var4) {
      boolean _snowman = a(_snowman, c);
      boolean _snowmanx = a(_snowman, b);
      boolean _snowmanxx = a(_snowman, d);
      boolean _snowmanxxx = a(_snowman, e);
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx);
   }

   private ceh a(brz var1, fx var2, ceh var3, fx var4, ceh var5, gc var6) {
      gc _snowman = _snowman.f();
      boolean _snowmanx = _snowman == gc.c ? this.a(_snowman, _snowman.d(_snowman, _snowman, _snowman), _snowman) : a(_snowman, c);
      boolean _snowmanxx = _snowman == gc.f ? this.a(_snowman, _snowman.d(_snowman, _snowman, _snowman), _snowman) : a(_snowman, b);
      boolean _snowmanxxx = _snowman == gc.d ? this.a(_snowman, _snowman.d(_snowman, _snowman, _snowman), _snowman) : a(_snowman, d);
      boolean _snowmanxxxx = _snowman == gc.e ? this.a(_snowman, _snowman.d(_snowman, _snowman, _snowman), _snowman) : a(_snowman, e);
      fx _snowmanxxxxx = _snowman.b();
      ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
      return this.a(_snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   private ceh a(brz var1, ceh var2, fx var3, ceh var4, boolean var5, boolean var6, boolean var7, boolean var8) {
      ddh _snowman = _snowman.k(_snowman, _snowman).a(gc.a);
      ceh _snowmanx = this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      return _snowmanx.a(a, Boolean.valueOf(this.a(_snowmanx, _snowman, _snowman)));
   }

   private boolean a(ceh var1, ceh var2, ddh var3) {
      boolean _snowman = _snowman.b() instanceof cbk && _snowman.c(a);
      if (_snowman) {
         return true;
      } else {
         cfp _snowmanx = _snowman.c(c);
         cfp _snowmanxx = _snowman.c(d);
         cfp _snowmanxxx = _snowman.c(b);
         cfp _snowmanxxxx = _snowman.c(e);
         boolean _snowmanxxxxx = _snowmanxx == cfp.a;
         boolean _snowmanxxxxxx = _snowmanxxxx == cfp.a;
         boolean _snowmanxxxxxxx = _snowmanxxx == cfp.a;
         boolean _snowmanxxxxxxxx = _snowmanx == cfp.a;
         boolean _snowmanxxxxxxxxx = _snowmanxxxxxxxx && _snowmanxxxxx && _snowmanxxxxxx && _snowmanxxxxxxx || _snowmanxxxxxxxx != _snowmanxxxxx || _snowmanxxxxxx != _snowmanxxxxxxx;
         if (_snowmanxxxxxxxxx) {
            return true;
         } else {
            boolean _snowmanxxxxxxxxxx = _snowmanx == cfp.c && _snowmanxx == cfp.c || _snowmanxxx == cfp.c && _snowmanxxxx == cfp.c;
            return _snowmanxxxxxxxxxx ? false : _snowman.b().a(aed.as) || a(_snowman, i);
         }
      }
   }

   private ceh a(ceh var1, boolean var2, boolean var3, boolean var4, boolean var5, ddh var6) {
      return _snowman.a(c, this.a(_snowman, _snowman, j)).a(b, this.a(_snowman, _snowman, p)).a(d, this.a(_snowman, _snowman, k)).a(e, this.a(_snowman, _snowman, o));
   }

   private cfp a(boolean var1, ddh var2, ddh var3) {
      if (_snowman) {
         return a(_snowman, _snowman) ? cfp.c : cfp.b;
      } else {
         return cfp.a;
      }
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(f) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public boolean b(ceh var1, brc var2, fx var3) {
      return !_snowman.c(f);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, c, b, e, d, f);
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      switch (_snowman) {
         case c:
            return _snowman.a(c, _snowman.c(d)).a(b, _snowman.c(e)).a(d, _snowman.c(c)).a(e, _snowman.c(b));
         case d:
            return _snowman.a(c, _snowman.c(b)).a(b, _snowman.c(d)).a(d, _snowman.c(e)).a(e, _snowman.c(c));
         case b:
            return _snowman.a(c, _snowman.c(e)).a(b, _snowman.c(c)).a(d, _snowman.c(b)).a(e, _snowman.c(d));
         default:
            return _snowman;
      }
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      switch (_snowman) {
         case b:
            return _snowman.a(c, _snowman.c(d)).a(d, _snowman.c(c));
         case c:
            return _snowman.a(b, _snowman.c(e)).a(e, _snowman.c(b));
         default:
            return super.a(_snowman, _snowman);
      }
   }
}
