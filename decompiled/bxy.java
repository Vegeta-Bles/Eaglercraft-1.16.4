import java.util.Random;
import javax.annotation.Nullable;

public class bxy extends bud {
   public static final cfb a = bxm.aq;
   public static final cey b = cex.w;
   public static final cey c = cex.o;
   public static final ddh d = buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final ddh e = buo.a(4.0, 2.0, 4.0, 12.0, 14.0, 12.0);
   public static final ddh f = dde.a(d, e);
   public static final ddh g = buo.a(0.0, 15.0, 0.0, 16.0, 15.0, 16.0);
   public static final ddh h = dde.a(f, g);
   public static final ddh i = dde.a(
      buo.a(1.0, 10.0, 0.0, 5.333333, 14.0, 16.0), buo.a(5.333333, 12.0, 0.0, 9.666667, 16.0, 16.0), buo.a(9.666667, 14.0, 0.0, 14.0, 18.0, 16.0), f
   );
   public static final ddh j = dde.a(
      buo.a(0.0, 10.0, 1.0, 16.0, 14.0, 5.333333), buo.a(0.0, 12.0, 5.333333, 16.0, 16.0, 9.666667), buo.a(0.0, 14.0, 9.666667, 16.0, 18.0, 14.0), f
   );
   public static final ddh k = dde.a(
      buo.a(15.0, 10.0, 0.0, 10.666667, 14.0, 16.0), buo.a(10.666667, 12.0, 0.0, 6.333333, 16.0, 16.0), buo.a(6.333333, 14.0, 0.0, 2.0, 18.0, 16.0), f
   );
   public static final ddh o = dde.a(
      buo.a(0.0, 10.0, 15.0, 16.0, 14.0, 10.666667), buo.a(0.0, 12.0, 10.666667, 16.0, 16.0, 6.333333), buo.a(0.0, 14.0, 6.333333, 16.0, 18.0, 2.0), f
   );

   protected bxy(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   public ddh d(ceh var1, brc var2, fx var3) {
      return f;
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public ceh a(bny var1) {
      brx _snowman = _snowman.p();
      bmb _snowmanx = _snowman.m();
      md _snowmanxx = _snowmanx.o();
      bfw _snowmanxxx = _snowman.n();
      boolean _snowmanxxxx = false;
      if (!_snowman.v && _snowmanxxx != null && _snowmanxx != null && _snowmanxxx.eV() && _snowmanxx.e("BlockEntityTag")) {
         md _snowmanxxxxx = _snowmanxx.p("BlockEntityTag");
         if (_snowmanxxxxx.e("Book")) {
            _snowmanxxxx = true;
         }
      }

      return this.n().a(a, _snowman.f().f()).a(c, Boolean.valueOf(_snowmanxxxx));
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return h;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch ((gc)_snowman.c(a)) {
         case c:
            return j;
         case d:
            return o;
         case f:
            return k;
         case e:
            return i;
         default:
            return f;
      }
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(a)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c);
   }

   @Nullable
   @Override
   public ccj a(brc var1) {
      return new cdb();
   }

   public static boolean a(brx var0, fx var1, ceh var2, bmb var3) {
      if (!_snowman.c(c)) {
         if (!_snowman.v) {
            b(_snowman, _snowman, _snowman, _snowman);
         }

         return true;
      } else {
         return false;
      }
   }

   private static void b(brx var0, fx var1, ceh var2, bmb var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdb) {
         cdb _snowmanx = (cdb)_snowman;
         _snowmanx.a(_snowman.a(1));
         a(_snowman, _snowman, _snowman, true);
         _snowman.a(null, _snowman, adq.aY, adr.e, 1.0F, 1.0F);
      }
   }

   public static void a(brx var0, fx var1, ceh var2, boolean var3) {
      _snowman.a(_snowman, _snowman.a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(_snowman)), 3);
      b(_snowman, _snowman, _snowman);
   }

   public static void a(brx var0, fx var1, ceh var2) {
      b(_snowman, _snowman, _snowman, true);
      _snowman.J().a(_snowman, _snowman.b(), 2);
      _snowman.c(1043, _snowman, 0);
   }

   private static void b(brx var0, fx var1, ceh var2, boolean var3) {
      _snowman.a(_snowman, _snowman.a(b, Boolean.valueOf(_snowman)), 3);
      b(_snowman, _snowman, _snowman);
   }

   private static void b(brx var0, fx var1, ceh var2) {
      _snowman.b(_snowman.c(), _snowman.b());
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      b(_snowman, _snowman, _snowman, false);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         if (_snowman.c(c)) {
            this.d(_snowman, _snowman, _snowman);
         }

         if (_snowman.c(b)) {
            _snowman.b(_snowman.c(), this);
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private void d(ceh var1, brx var2, fx var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdb) {
         cdb _snowmanx = (cdb)_snowman;
         gc _snowmanxx = _snowman.c(a);
         bmb _snowmanxxx = _snowmanx.f().i();
         float _snowmanxxxx = 0.25F * (float)_snowmanxx.i();
         float _snowmanxxxxx = 0.25F * (float)_snowmanxx.k();
         bcv _snowmanxxxxxx = new bcv(_snowman, (double)_snowman.u() + 0.5 + (double)_snowmanxxxx, (double)(_snowman.v() + 1), (double)_snowman.w() + 0.5 + (double)_snowmanxxxxx, _snowmanxxx);
         _snowmanxxxxxx.m();
         _snowman.c(_snowmanxxxxxx);
         _snowmanx.Y_();
      }
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(b) ? 15 : 0;
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman == gc.b && _snowman.c(b) ? 15 : 0;
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      if (_snowman.c(c)) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cdb) {
            return ((cdb)_snowman).j();
         }
      }

      return 0;
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.c(c)) {
         if (!_snowman.v) {
            this.a(_snowman, _snowman, _snowman);
         }

         return aou.a(_snowman.v);
      } else {
         bmb _snowman = _snowman.b(_snowman);
         return !_snowman.a() && !_snowman.b().a(aeg.Z) ? aou.b : aou.c;
      }
   }

   @Nullable
   @Override
   public aox b(ceh var1, brx var2, fx var3) {
      return !_snowman.c(c) ? null : super.b(_snowman, _snowman, _snowman);
   }

   private void a(brx var1, fx var2, bfw var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdb) {
         _snowman.a((cdb)_snowman);
         _snowman.a(aea.at);
      }
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
