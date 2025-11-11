import javax.annotation.Nullable;

public class dxv {
   public static class a implements dyj<hi> {
      private final dyw a;

      public a(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxv.b _snowman = new dxv.b(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class b extends dzb {
      private b(dwt var1, double var2, double var4, double var6) {
         super(_snowman, _snowman, _snowman, _snowman);
         this.t = 4;
      }

      @Override
      public dyk b() {
         return dyk.c;
      }

      @Override
      public void a(dfq var1, djk var2, float var3) {
         this.e(0.6F - ((float)this.s + _snowman - 1.0F) * 0.25F * 0.5F);
         super.a(_snowman, _snowman, _snowman);
      }

      @Override
      public float b(float var1) {
         return 7.1F * afm.a(((float)this.s + _snowman - 1.0F) * 0.25F * (float) Math.PI);
      }
   }

   static class c extends dyp {
      private boolean b;
      private boolean D;
      private final dyi E;
      private float F;
      private float G;
      private float H;
      private boolean I;

      private c(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyi var14, dyw var15) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, -0.004F);
         this.j = _snowman;
         this.k = _snowman;
         this.l = _snowman;
         this.E = _snowman;
         this.B *= 0.75F;
         this.t = 48 + this.r.nextInt(12);
         this.b(_snowman);
      }

      public void a(boolean var1) {
         this.b = _snowman;
      }

      public void b(boolean var1) {
         this.D = _snowman;
      }

      @Override
      public void a(dfq var1, djk var2, float var3) {
         if (!this.D || this.s < this.t / 3 || (this.s + this.t) / 3 % 2 == 0) {
            super.a(_snowman, _snowman, _snowman);
         }
      }

      @Override
      public void a() {
         super.a();
         if (this.b && this.s < this.t / 2 && (this.s + this.t) % 2 == 0) {
            dxv.c _snowman = new dxv.c(this.c, this.g, this.h, this.i, 0.0, 0.0, 0.0, this.E, this.a);
            _snowman.e(0.99F);
            _snowman.a(this.v, this.w, this.x);
            _snowman.s = _snowman.t / 2;
            if (this.I) {
               _snowman.I = true;
               _snowman.F = this.F;
               _snowman.G = this.G;
               _snowman.H = this.H;
            }

            _snowman.D = this.D;
            this.E.a(_snowman);
         }
      }
   }

   public static class d implements dyj<hi> {
      private final dyw a;

      public d(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxv.c _snowman = new dxv.c(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, djz.C().f, this.a);
         _snowman.e(0.99F);
         return _snowman;
      }
   }

   public static class e extends dye {
      private int a;
      private final dyi b;
      private mj B;
      private boolean C;

      public e(dwt var1, double var2, double var4, double var6, double var8, double var10, double var12, dyi var14, @Nullable md var15) {
         super(_snowman, _snowman, _snowman, _snowman);
         this.j = _snowman;
         this.k = _snowman;
         this.l = _snowman;
         this.b = _snowman;
         this.t = 8;
         if (_snowman != null) {
            this.B = _snowman.d("Explosions", 10);
            if (this.B.isEmpty()) {
               this.B = null;
            } else {
               this.t = this.B.size() * 2 - 1;

               for (int _snowman = 0; _snowman < this.B.size(); _snowman++) {
                  md _snowmanx = this.B.a(_snowman);
                  if (_snowmanx.q("Flicker")) {
                     this.C = true;
                     this.t += 15;
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void a() {
         if (this.a == 0 && this.B != null) {
            boolean _snowman = this.c();
            boolean _snowmanx = false;
            if (this.B.size() >= 3) {
               _snowmanx = true;
            } else {
               for (int _snowmanxx = 0; _snowmanxx < this.B.size(); _snowmanxx++) {
                  md _snowmanxxx = this.B.a(_snowmanxx);
                  if (blm.a.a(_snowmanxxx.f("Type")) == blm.a.b) {
                     _snowmanx = true;
                     break;
                  }
               }
            }

            adp _snowmanxxx;
            if (_snowmanx) {
               _snowmanxxx = _snowman ? adq.ed : adq.ec;
            } else {
               _snowmanxxx = _snowman ? adq.eb : adq.ea;
            }

            this.c.a(this.g, this.h, this.i, _snowmanxxx, adr.i, 20.0F, 0.95F + this.r.nextFloat() * 0.1F, true);
         }

         if (this.a % 2 == 0 && this.B != null && this.a / 2 < this.B.size()) {
            int _snowmanxxx = this.a / 2;
            md _snowmanxxxx = this.B.a(_snowmanxxx);
            blm.a _snowmanxxxxx = blm.a.a(_snowmanxxxx.f("Type"));
            boolean _snowmanxxxxxx = _snowmanxxxx.q("Trail");
            boolean _snowmanxxxxxxx = _snowmanxxxx.q("Flicker");
            int[] _snowmanxxxxxxxx = _snowmanxxxx.n("Colors");
            int[] _snowmanxxxxxxxxx = _snowmanxxxx.n("FadeColors");
            if (_snowmanxxxxxxxx.length == 0) {
               _snowmanxxxxxxxx = new int[]{bkx.p.g()};
            }

            switch (_snowmanxxxxx) {
               case a:
               default:
                  this.a(0.25, 2, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
                  break;
               case b:
                  this.a(0.5, 4, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
                  break;
               case c:
                  this.a(
                     0.5,
                     new double[][]{
                        {0.0, 1.0},
                        {0.3455, 0.309},
                        {0.9511, 0.309},
                        {0.3795918367346939, -0.12653061224489795},
                        {0.6122448979591837, -0.8040816326530612},
                        {0.0, -0.35918367346938773}
                     },
                     _snowmanxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxx,
                     _snowmanxxxxxxx,
                     false
                  );
                  break;
               case d:
                  this.a(
                     0.5,
                     new double[][]{
                        {0.0, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.6},
                        {0.6, 0.6},
                        {0.6, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.0},
                        {0.4, 0.0},
                        {0.4, -0.6},
                        {0.2, -0.6},
                        {0.2, -0.4},
                        {0.0, -0.4}
                     },
                     _snowmanxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxx,
                     _snowmanxxxxxxx,
                     true
                  );
                  break;
               case e:
                  this.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
            }

            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx[0];
            float _snowmanxxxxxxxxxxx = (float)((_snowmanxxxxxxxxxx & 0xFF0000) >> 16) / 255.0F;
            float _snowmanxxxxxxxxxxxx = (float)((_snowmanxxxxxxxxxx & 0xFF00) >> 8) / 255.0F;
            float _snowmanxxxxxxxxxxxxx = (float)((_snowmanxxxxxxxxxx & 0xFF) >> 0) / 255.0F;
            dyg _snowmanxxxxxxxxxxxxxx = this.b.a(hh.D, this.g, this.h, this.i, 0.0, 0.0, 0.0);
            _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
         }

         this.a++;
         if (this.a > this.t) {
            if (this.C) {
               boolean _snowmanxxx = this.c();
               adp _snowmanxxxx = _snowmanxxx ? adq.eh : adq.eg;
               this.c.a(this.g, this.h, this.i, _snowmanxxxx, adr.i, 20.0F, 0.9F + this.r.nextFloat() * 0.15F, true);
            }

            this.j();
         }
      }

      private boolean c() {
         djz _snowman = djz.C();
         return _snowman.h.k().b().c(this.g, this.h, this.i) >= 256.0;
      }

      private void a(double var1, double var3, double var5, double var7, double var9, double var11, int[] var13, int[] var14, boolean var15, boolean var16) {
         dxv.c _snowman = (dxv.c)this.b.a(hh.y, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(_snowman);
         _snowman.b(_snowman);
         _snowman.e(0.99F);
         int _snowmanx = this.r.nextInt(_snowman.length);
         _snowman.b(_snowman[_snowmanx]);
         if (_snowman.length > 0) {
            _snowman.c(x.a(_snowman, this.r));
         }
      }

      private void a(double var1, int var3, int[] var4, int[] var5, boolean var6, boolean var7) {
         double _snowman = this.g;
         double _snowmanx = this.h;
         double _snowmanxx = this.i;

         for (int _snowmanxxx = -_snowman; _snowmanxxx <= _snowman; _snowmanxxx++) {
            for (int _snowmanxxxx = -_snowman; _snowmanxxxx <= _snowman; _snowmanxxxx++) {
               for (int _snowmanxxxxx = -_snowman; _snowmanxxxxx <= _snowman; _snowmanxxxxx++) {
                  double _snowmanxxxxxx = (double)_snowmanxxxx + (this.r.nextDouble() - this.r.nextDouble()) * 0.5;
                  double _snowmanxxxxxxx = (double)_snowmanxxx + (this.r.nextDouble() - this.r.nextDouble()) * 0.5;
                  double _snowmanxxxxxxxx = (double)_snowmanxxxxx + (this.r.nextDouble() - this.r.nextDouble()) * 0.5;
                  double _snowmanxxxxxxxxx = (double)afm.a(_snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx) / _snowman + this.r.nextGaussian() * 0.05;
                  this.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx / _snowmanxxxxxxxxx, _snowmanxxxxxxx / _snowmanxxxxxxxxx, _snowmanxxxxxxxx / _snowmanxxxxxxxxx, _snowman, _snowman, _snowman, _snowman);
                  if (_snowmanxxx != -_snowman && _snowmanxxx != _snowman && _snowmanxxxx != -_snowman && _snowmanxxxx != _snowman) {
                     _snowmanxxxxx += _snowman * 2 - 1;
                  }
               }
            }
         }
      }

      private void a(double var1, double[][] var3, int[] var4, int[] var5, boolean var6, boolean var7, boolean var8) {
         double _snowman = _snowman[0][0];
         double _snowmanx = _snowman[0][1];
         this.a(this.g, this.h, this.i, _snowman * _snowman, _snowmanx * _snowman, 0.0, _snowman, _snowman, _snowman, _snowman);
         float _snowmanxx = this.r.nextFloat() * (float) Math.PI;
         double _snowmanxxx = _snowman ? 0.034 : 0.34;

         for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
            double _snowmanxxxxx = (double)_snowmanxx + (double)((float)_snowmanxxxx * (float) Math.PI) * _snowmanxxx;
            double _snowmanxxxxxx = _snowman;
            double _snowmanxxxxxxx = _snowmanx;

            for (int _snowmanxxxxxxxx = 1; _snowmanxxxxxxxx < _snowman.length; _snowmanxxxxxxxx++) {
               double _snowmanxxxxxxxxx = _snowman[_snowmanxxxxxxxx][0];
               double _snowmanxxxxxxxxxx = _snowman[_snowmanxxxxxxxx][1];

               for (double _snowmanxxxxxxxxxxx = 0.25; _snowmanxxxxxxxxxxx <= 1.0; _snowmanxxxxxxxxxxx += 0.25) {
                  double _snowmanxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx) * _snowman;
                  double _snowmanxxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxx) * _snowman;
                  double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx * Math.sin(_snowmanxxxxx);
                  _snowmanxxxxxxxxxxxx *= Math.cos(_snowmanxxxxx);

                  for (double _snowmanxxxxxxxxxxxxxxx = -1.0; _snowmanxxxxxxxxxxxxxxx <= 1.0; _snowmanxxxxxxxxxxxxxxx += 2.0) {
                     this.a(this.g, this.h, this.i, _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman);
                  }
               }

               _snowmanxxxxxx = _snowmanxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxx;
            }
         }
      }

      private void a(int[] var1, int[] var2, boolean var3, boolean var4) {
         double _snowman = this.r.nextGaussian() * 0.05;
         double _snowmanx = this.r.nextGaussian() * 0.05;

         for (int _snowmanxx = 0; _snowmanxx < 70; _snowmanxx++) {
            double _snowmanxxx = this.j * 0.5 + this.r.nextGaussian() * 0.15 + _snowman;
            double _snowmanxxxx = this.l * 0.5 + this.r.nextGaussian() * 0.15 + _snowmanx;
            double _snowmanxxxxx = this.k * 0.5 + this.r.nextDouble() * 0.5;
            this.a(this.g, this.h, this.i, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }
}
