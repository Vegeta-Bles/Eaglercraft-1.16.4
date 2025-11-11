import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;

public class bvk extends buo implements apf {
   public static final cfg a = cex.as;
   public static final Object2FloatMap<brw> b = new Object2FloatOpenHashMap();
   private static final ddh c = dde.b();
   private static final ddh[] d = x.a(new ddh[9], var0 -> {
      for (int _snowman = 0; _snowman < 8; _snowman++) {
         var0[_snowman] = dde.a(c, buo.a(2.0, (double)Math.max(2, 1 + _snowman * 2), 2.0, 14.0, 16.0, 14.0), dcr.e);
      }

      var0[8] = var0[7];
   });

   public static void c() {
      b.defaultReturnValue(-1.0F);
      float _snowman = 0.3F;
      float _snowmanx = 0.5F;
      float _snowmanxx = 0.65F;
      float _snowmanxxx = 0.85F;
      float _snowmanxxxx = 1.0F;
      a(0.3F, bmd.au);
      a(0.3F, bmd.ar);
      a(0.3F, bmd.as);
      a(0.3F, bmd.aw);
      a(0.3F, bmd.av);
      a(0.3F, bmd.at);
      a(0.3F, bmd.x);
      a(0.3F, bmd.y);
      a(0.3F, bmd.z);
      a(0.3F, bmd.A);
      a(0.3F, bmd.B);
      a(0.3F, bmd.C);
      a(0.3F, bmd.qg);
      a(0.3F, bmd.ni);
      a(0.3F, bmd.aL);
      a(0.3F, bmd.bE);
      a(0.3F, bmd.nk);
      a(0.3F, bmd.nj);
      a(0.3F, bmd.aO);
      a(0.3F, bmd.rm);
      a(0.3F, bmd.kV);
      a(0.5F, bmd.ma);
      a(0.5F, bmd.gn);
      a(0.5F, bmd.cX);
      a(0.5F, bmd.bD);
      a(0.5F, bmd.dR);
      a(0.5F, bmd.bA);
      a(0.5F, bmd.bB);
      a(0.5F, bmd.bC);
      a(0.5F, bmd.nh);
      a(0.65F, bmd.aP);
      a(0.65F, bmd.ed);
      a(0.65F, bmd.di);
      a(0.65F, bmd.dj);
      a(0.65F, bmd.dQ);
      a(0.65F, bmd.kb);
      a(0.65F, bmd.qf);
      a(0.65F, bmd.oY);
      a(0.65F, bmd.ms);
      a(0.65F, bmd.oZ);
      a(0.65F, bmd.kW);
      a(0.65F, bmd.bu);
      a(0.65F, bmd.bv);
      a(0.65F, bmd.dM);
      a(0.65F, bmd.bw);
      a(0.65F, bmd.bx);
      a(0.65F, bmd.nu);
      a(0.65F, bmd.by);
      a(0.65F, bmd.bz);
      a(0.65F, bmd.rp);
      a(0.65F, bmd.bh);
      a(0.65F, bmd.bi);
      a(0.65F, bmd.bj);
      a(0.65F, bmd.bk);
      a(0.65F, bmd.bl);
      a(0.65F, bmd.bm);
      a(0.65F, bmd.bn);
      a(0.65F, bmd.bo);
      a(0.65F, bmd.bp);
      a(0.65F, bmd.bq);
      a(0.65F, bmd.br);
      a(0.65F, bmd.bs);
      a(0.65F, bmd.bt);
      a(0.65F, bmd.aM);
      a(0.65F, bmd.gj);
      a(0.65F, bmd.gk);
      a(0.65F, bmd.gl);
      a(0.65F, bmd.gm);
      a(0.65F, bmd.go);
      a(0.85F, bmd.fL);
      a(0.85F, bmd.dK);
      a(0.85F, bmd.dL);
      a(0.85F, bmd.hj);
      a(0.85F, bmd.hk);
      a(0.85F, bmd.kX);
      a(0.85F, bmd.pa);
      a(0.85F, bmd.ne);
      a(1.0F, bmd.mN);
      a(1.0F, bmd.pn);
   }

   private static void a(float var0, brw var1) {
      b.put(_snowman.h(), _snowman);
   }

   public bvk(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   public static void a(brx var0, fx var1, boolean var2) {
      ceh _snowman = _snowman.d_(_snowman);
      _snowman.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), _snowman ? adq.bW : adq.bV, adr.e, 1.0F, 1.0F, false);
      double _snowmanx = _snowman.j(_snowman, _snowman).b(gc.a.b, 0.5, 0.5) + 0.03125;
      double _snowmanxx = 0.13125F;
      double _snowmanxxx = 0.7375F;
      Random _snowmanxxxx = _snowman.u_();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 10; _snowmanxxxxx++) {
         double _snowmanxxxxxx = _snowmanxxxx.nextGaussian() * 0.02;
         double _snowmanxxxxxxx = _snowmanxxxx.nextGaussian() * 0.02;
         double _snowmanxxxxxxxx = _snowmanxxxx.nextGaussian() * 0.02;
         _snowman.a(
            hh.F,
            (double)_snowman.u() + 0.13125F + 0.7375F * (double)_snowmanxxxx.nextFloat(),
            (double)_snowman.v() + _snowmanx + (double)_snowmanxxxx.nextFloat() * (1.0 - _snowmanx),
            (double)_snowman.w() + 0.13125F + 0.7375F * (double)_snowmanxxxx.nextFloat(),
            _snowmanxxxxxx,
            _snowmanxxxxxxx,
            _snowmanxxxxxxxx
         );
      }
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return d[_snowman.c(a)];
   }

   @Override
   public ddh a_(ceh var1, brc var2, fx var3) {
      return c;
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return d[0];
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (_snowman.c(a) == 7) {
         _snowman.J().a(_snowman, _snowman.b(), 20);
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      int _snowman = _snowman.c(a);
      bmb _snowmanx = _snowman.b(_snowman);
      if (_snowman < 8 && b.containsKey(_snowmanx.b())) {
         if (_snowman < 7 && !_snowman.v) {
            ceh _snowmanxx = b(_snowman, _snowman, _snowman, _snowmanx);
            _snowman.c(1500, _snowman, _snowman != _snowmanxx ? 1 : 0);
            if (!_snowman.bC.d) {
               _snowmanx.g(1);
            }
         }

         return aou.a(_snowman.v);
      } else if (_snowman == 8) {
         d(_snowman, _snowman, _snowman);
         return aou.a(_snowman.v);
      } else {
         return aou.c;
      }
   }

   public static ceh a(ceh var0, aag var1, bmb var2, fx var3) {
      int _snowman = _snowman.c(a);
      if (_snowman < 7 && b.containsKey(_snowman.b())) {
         ceh _snowmanx = b(_snowman, _snowman, _snowman, _snowman);
         _snowman.g(1);
         return _snowmanx;
      } else {
         return _snowman;
      }
   }

   public static ceh d(ceh var0, brx var1, fx var2) {
      if (!_snowman.v) {
         float _snowman = 0.7F;
         double _snowmanx = (double)(_snowman.t.nextFloat() * 0.7F) + 0.15F;
         double _snowmanxx = (double)(_snowman.t.nextFloat() * 0.7F) + 0.060000002F + 0.6;
         double _snowmanxxx = (double)(_snowman.t.nextFloat() * 0.7F) + 0.15F;
         bcv _snowmanxxxx = new bcv(_snowman, (double)_snowman.u() + _snowmanx, (double)_snowman.v() + _snowmanxx, (double)_snowman.w() + _snowmanxxx, new bmb(bmd.mK));
         _snowmanxxxx.m();
         _snowman.c(_snowmanxxxx);
      }

      ceh _snowman = d(_snowman, (bry)_snowman, _snowman);
      _snowman.a(null, _snowman, adq.bU, adr.e, 1.0F, 1.0F);
      return _snowman;
   }

   private static ceh d(ceh var0, bry var1, fx var2) {
      ceh _snowman = _snowman.a(a, Integer.valueOf(0));
      _snowman.a(_snowman, _snowman, 3);
      return _snowman;
   }

   private static ceh b(ceh var0, bry var1, fx var2, bmb var3) {
      int _snowman = _snowman.c(a);
      float _snowmanx = b.getFloat(_snowman.b());
      if ((_snowman != 0 || !(_snowmanx > 0.0F)) && !(_snowman.u_().nextDouble() < (double)_snowmanx)) {
         return _snowman;
      } else {
         int _snowmanxx = _snowman + 1;
         ceh _snowmanxxx = _snowman.a(a, Integer.valueOf(_snowmanxx));
         _snowman.a(_snowman, _snowmanxxx, 3);
         if (_snowmanxx == 7) {
            _snowman.J().a(_snowman, _snowman.b(), 20);
         }

         return _snowmanxxx;
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(a) == 7) {
         _snowman.a(_snowman, _snowman.a(a), 3);
         _snowman.a(null, _snowman, adq.bX, adr.e, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return _snowman.c(a);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   @Override
   public ape a(ceh var1, bry var2, fx var3) {
      int _snowman = _snowman.c(a);
      if (_snowman == 8) {
         return new bvk.c(_snowman, _snowman, _snowman, new bmb(bmd.mK));
      } else {
         return (ape)(_snowman < 7 ? new bvk.b(_snowman, _snowman, _snowman) : new bvk.a());
      }
   }

   static class a extends apa implements ape {
      public a() {
         super(0);
      }

      @Override
      public int[] a(gc var1) {
         return new int[0];
      }

      @Override
      public boolean a(int var1, bmb var2, @Nullable gc var3) {
         return false;
      }

      @Override
      public boolean b(int var1, bmb var2, gc var3) {
         return false;
      }
   }

   static class b extends apa implements ape {
      private final ceh a;
      private final bry b;
      private final fx c;
      private boolean d;

      public b(ceh var1, bry var2, fx var3) {
         super(1);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public int V_() {
         return 1;
      }

      @Override
      public int[] a(gc var1) {
         return _snowman == gc.b ? new int[]{0} : new int[0];
      }

      @Override
      public boolean a(int var1, bmb var2, @Nullable gc var3) {
         return !this.d && _snowman == gc.b && bvk.b.containsKey(_snowman.b());
      }

      @Override
      public boolean b(int var1, bmb var2, gc var3) {
         return false;
      }

      @Override
      public void X_() {
         bmb _snowman = this.a(0);
         if (!_snowman.a()) {
            this.d = true;
            ceh _snowmanx = bvk.b(this.a, this.b, this.c, _snowman);
            this.b.c(1500, this.c, _snowmanx != this.a ? 1 : 0);
            this.b(0);
         }
      }
   }

   static class c extends apa implements ape {
      private final ceh a;
      private final bry b;
      private final fx c;
      private boolean d;

      public c(ceh var1, bry var2, fx var3, bmb var4) {
         super(_snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public int V_() {
         return 1;
      }

      @Override
      public int[] a(gc var1) {
         return _snowman == gc.a ? new int[]{0} : new int[0];
      }

      @Override
      public boolean a(int var1, bmb var2, @Nullable gc var3) {
         return false;
      }

      @Override
      public boolean b(int var1, bmb var2, gc var3) {
         return !this.d && _snowman == gc.a && _snowman.b() == bmd.mK;
      }

      @Override
      public void X_() {
         bvk.d(this.a, this.b, this.c);
         this.d = true;
      }
   }
}
