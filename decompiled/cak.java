import java.util.Random;
import java.util.stream.IntStream;

public class cak extends buo implements bzu {
   public static final cfb a = bxm.aq;
   public static final cfe<cff> b = cex.ab;
   public static final cfe<cfn> c = cex.aL;
   public static final cey d = cex.C;
   protected static final ddh e = bzw.d;
   protected static final ddh f = bzw.c;
   protected static final ddh g = buo.a(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
   protected static final ddh h = buo.a(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
   protected static final ddh i = buo.a(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
   protected static final ddh j = buo.a(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
   protected static final ddh k = buo.a(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
   protected static final ddh o = buo.a(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
   protected static final ddh p = buo.a(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
   protected static final ddh q = buo.a(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
   protected static final ddh[] r = a(e, g, k, h, o);
   protected static final ddh[] s = a(f, i, p, j, q);
   private static final int[] t = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
   private final buo u;
   private final ceh v;

   private static ddh[] a(ddh var0, ddh var1, ddh var2, ddh var3, ddh var4) {
      return IntStream.range(0, 16).mapToObj(var5 -> a(var5, _snowman, _snowman, _snowman, _snowman, _snowman)).toArray(ddh[]::new);
   }

   private static ddh a(int var0, ddh var1, ddh var2, ddh var3, ddh var4, ddh var5) {
      ddh _snowman = _snowman;
      if ((_snowman & 1) != 0) {
         _snowman = dde.a(_snowman, _snowman);
      }

      if ((_snowman & 2) != 0) {
         _snowman = dde.a(_snowman, _snowman);
      }

      if ((_snowman & 4) != 0) {
         _snowman = dde.a(_snowman, _snowman);
      }

      if ((_snowman & 8) != 0) {
         _snowman = dde.a(_snowman, _snowman);
      }

      return _snowman;
   }

   protected cak(ceh var1, ceg.c var2) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, cff.b).a(c, cfn.a).a(d, Boolean.valueOf(false)));
      this.u = _snowman.b();
      this.v = _snowman;
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return (_snowman.c(b) == cff.a ? r : s)[t[this.l(_snowman)]];
   }

   private int l(ceh var1) {
      return _snowman.c(c).ordinal() * 4 + _snowman.c(a).d();
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      this.u.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, bfw var4) {
      this.v.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(bry var1, fx var2, ceh var3) {
      this.u.a(_snowman, _snowman, _snowman);
   }

   @Override
   public float f() {
      return this.u.f();
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         this.v.a(_snowman, _snowman, bup.a, _snowman, false);
         this.u.b(this.v, _snowman, _snowman, _snowman, false);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         this.v.b(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(brx var1, fx var2, aqa var3) {
      this.u.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a_(ceh var1) {
      return this.u.a_(_snowman);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      this.u.b(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      this.u.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      return this.v.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(brx var1, fx var2, brp var3) {
      this.u.a(_snowman, _snowman, _snowman);
   }

   @Override
   public ceh a(bny var1) {
      gc _snowman = _snowman.j();
      fx _snowmanx = _snowman.a();
      cux _snowmanxx = _snowman.p().b(_snowmanx);
      ceh _snowmanxxx = this.n()
         .a(a, _snowman.f())
         .a(b, _snowman != gc.a && (_snowman == gc.b || !(_snowman.k().c - (double)_snowmanx.v() > 0.5)) ? cff.b : cff.a)
         .a(d, Boolean.valueOf(_snowmanxx.a() == cuy.c));
      return _snowmanxxx.a(c, g(_snowmanxxx, _snowman.p(), _snowmanx));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(d)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return _snowman.n().d() ? _snowman.a(c, g(_snowman, _snowman, _snowman)) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static cfn g(ceh var0, brc var1, fx var2) {
      gc _snowman = _snowman.c(a);
      ceh _snowmanx = _snowman.d_(_snowman.a(_snowman));
      if (h(_snowmanx) && _snowman.c(b) == _snowmanx.c(b)) {
         gc _snowmanxx = _snowmanx.c(a);
         if (_snowmanxx.n() != _snowman.c(a).n() && d(_snowman, _snowman, _snowman, _snowmanxx.f())) {
            if (_snowmanxx == _snowman.h()) {
               return cfn.d;
            }

            return cfn.e;
         }
      }

      ceh _snowmanxx = _snowman.d_(_snowman.a(_snowman.f()));
      if (h(_snowmanxx) && _snowman.c(b) == _snowmanxx.c(b)) {
         gc _snowmanxxx = _snowmanxx.c(a);
         if (_snowmanxxx.n() != _snowman.c(a).n() && d(_snowman, _snowman, _snowman, _snowmanxxx)) {
            if (_snowmanxxx == _snowman.h()) {
               return cfn.b;
            }

            return cfn.c;
         }
      }

      return cfn.a;
   }

   private static boolean d(ceh var0, brc var1, fx var2, gc var3) {
      ceh _snowman = _snowman.d_(_snowman.a(_snowman));
      return !h(_snowman) || _snowman.c(a) != _snowman.c(a) || _snowman.c(b) != _snowman.c(b);
   }

   public static boolean h(ceh var0) {
      return _snowman.b() instanceof cak;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      gc _snowman = _snowman.c(a);
      cfn _snowmanx = _snowman.c(c);
      switch (_snowman) {
         case b:
            if (_snowman.n() == gc.a.c) {
               switch (_snowmanx) {
                  case b:
                     return _snowman.a(bzm.c).a(c, cfn.c);
                  case c:
                     return _snowman.a(bzm.c).a(c, cfn.b);
                  case d:
                     return _snowman.a(bzm.c).a(c, cfn.e);
                  case e:
                     return _snowman.a(bzm.c).a(c, cfn.d);
                  default:
                     return _snowman.a(bzm.c);
               }
            }
            break;
         case c:
            if (_snowman.n() == gc.a.a) {
               switch (_snowmanx) {
                  case b:
                     return _snowman.a(bzm.c).a(c, cfn.b);
                  case c:
                     return _snowman.a(bzm.c).a(c, cfn.c);
                  case d:
                     return _snowman.a(bzm.c).a(c, cfn.e);
                  case e:
                     return _snowman.a(bzm.c).a(c, cfn.d);
                  case a:
                     return _snowman.a(bzm.c);
               }
            }
      }

      return super.a(_snowman, _snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c, d);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(d) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
