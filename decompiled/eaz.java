import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class eaz {
   private final dko a;
   private static final ThreadLocal<eaz.d> b = ThreadLocal.withInitial(() -> new eaz.d());

   public eaz(dko var1) {
      this.a = _snowman;
   }

   public boolean a(bra var1, elo var2, ceh var3, fx var4, dfm var5, dfq var6, boolean var7, Random var8, long var9, int var11) {
      boolean _snowman = djz.B() && _snowman.f() == 0 && _snowman.a();
      dcn _snowmanx = _snowman.n(_snowman, _snowman);
      _snowman.a(_snowmanx.b, _snowmanx.c, _snowmanx.d);

      try {
         return _snowman ? this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman) : this.c(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } catch (Throwable var17) {
         l _snowmanxx = l.a(var17, "Tesselating block model");
         m _snowmanxxx = _snowmanxx.a("Block model being tesselated");
         m.a(_snowmanxxx, _snowman, _snowman);
         _snowmanxxx.a("Using AO", _snowman);
         throw new u(_snowmanxx);
      }
   }

   public boolean b(bra var1, elo var2, ceh var3, fx var4, dfm var5, dfq var6, boolean var7, Random var8, long var9, int var11) {
      boolean _snowman = false;
      float[] _snowmanx = new float[gc.values().length * 2];
      BitSet _snowmanxx = new BitSet(3);
      eaz.b _snowmanxxx = new eaz.b();

      for (gc _snowmanxxxx : gc.values()) {
         _snowman.setSeed(_snowman);
         List<eba> _snowmanxxxxx = _snowman.a(_snowman, _snowmanxxxx, _snowman);
         if (!_snowmanxxxxx.isEmpty() && (!_snowman || buo.c(_snowman, _snowman, _snowman, _snowmanxxxx))) {
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, _snowman);
            _snowman = true;
         }
      }

      _snowman.setSeed(_snowman);
      List<eba> _snowmanxxxxx = _snowman.a(_snowman, null, _snowman);
      if (!_snowmanxxxxx.isEmpty()) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, _snowman);
         _snowman = true;
      }

      return _snowman;
   }

   public boolean c(bra var1, elo var2, ceh var3, fx var4, dfm var5, dfq var6, boolean var7, Random var8, long var9, int var11) {
      boolean _snowman = false;
      BitSet _snowmanx = new BitSet(3);

      for (gc _snowmanxx : gc.values()) {
         _snowman.setSeed(_snowman);
         List<eba> _snowmanxxx = _snowman.a(_snowman, _snowmanxx, _snowman);
         if (!_snowmanxxx.isEmpty() && (!_snowman || buo.c(_snowman, _snowman, _snowman, _snowmanxx))) {
            int _snowmanxxxx = eae.a(_snowman, _snowman, _snowman.a(_snowmanxx));
            this.a(_snowman, _snowman, _snowman, _snowmanxxxx, _snowman, false, _snowman, _snowman, _snowmanxxx, _snowmanx);
            _snowman = true;
         }
      }

      _snowman.setSeed(_snowman);
      List<eba> _snowmanxxx = _snowman.a(_snowman, null, _snowman);
      if (!_snowmanxxx.isEmpty()) {
         this.a(_snowman, _snowman, _snowman, -1, _snowman, true, _snowman, _snowman, _snowmanxxx, _snowmanx);
         _snowman = true;
      }

      return _snowman;
   }

   private void a(bra var1, ceh var2, fx var3, dfm var4, dfq var5, List<eba> var6, float[] var7, BitSet var8, eaz.b var9, int var10) {
      for (eba _snowman : _snowman) {
         this.a(_snowman, _snowman, _snowman, _snowman.b(), _snowman.e(), _snowman, _snowman);
         _snowman.a(_snowman, _snowman, _snowman, _snowman.e(), _snowman, _snowman, _snowman.f());
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman.c(), _snowman, _snowman.b[0], _snowman.b[1], _snowman.b[2], _snowman.b[3], _snowman.c[0], _snowman.c[1], _snowman.c[2], _snowman.c[3], _snowman);
      }
   }

   private void a(
      bra var1,
      ceh var2,
      fx var3,
      dfq var4,
      dfm.a var5,
      eba var6,
      float var7,
      float var8,
      float var9,
      float var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15
   ) {
      float _snowman;
      float _snowmanx;
      float _snowmanxx;
      if (_snowman.c()) {
         int _snowmanxxx = this.a.a(_snowman, _snowman, _snowman, _snowman.d());
         _snowman = (float)(_snowmanxxx >> 16 & 0xFF) / 255.0F;
         _snowmanx = (float)(_snowmanxxx >> 8 & 0xFF) / 255.0F;
         _snowmanxx = (float)(_snowmanxxx & 0xFF) / 255.0F;
      } else {
         _snowman = 1.0F;
         _snowmanx = 1.0F;
         _snowmanxx = 1.0F;
      }

      _snowman.a(_snowman, _snowman, new float[]{_snowman, _snowman, _snowman, _snowman}, _snowman, _snowmanx, _snowmanxx, new int[]{_snowman, _snowman, _snowman, _snowman}, _snowman, true);
   }

   private void a(bra var1, ceh var2, fx var3, int[] var4, gc var5, @Nullable float[] var6, BitSet var7) {
      float _snowman = 32.0F;
      float _snowmanx = 32.0F;
      float _snowmanxx = 32.0F;
      float _snowmanxxx = -32.0F;
      float _snowmanxxxx = -32.0F;
      float _snowmanxxxxx = -32.0F;

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
         float _snowmanxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxx * 8]);
         float _snowmanxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxx * 8 + 1]);
         float _snowmanxxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxx * 8 + 2]);
         _snowman = Math.min(_snowman, _snowmanxxxxxxx);
         _snowmanx = Math.min(_snowmanx, _snowmanxxxxxxxx);
         _snowmanxx = Math.min(_snowmanxx, _snowmanxxxxxxxxx);
         _snowmanxxx = Math.max(_snowmanxxx, _snowmanxxxxxxx);
         _snowmanxxxx = Math.max(_snowmanxxxx, _snowmanxxxxxxxx);
         _snowmanxxxxx = Math.max(_snowmanxxxxx, _snowmanxxxxxxxxx);
      }

      if (_snowman != null) {
         _snowman[gc.e.c()] = _snowman;
         _snowman[gc.f.c()] = _snowmanxxx;
         _snowman[gc.a.c()] = _snowmanx;
         _snowman[gc.b.c()] = _snowmanxxxx;
         _snowman[gc.c.c()] = _snowmanxx;
         _snowman[gc.d.c()] = _snowmanxxxxx;
         int _snowmanxxxxxx = gc.values().length;
         _snowman[gc.e.c() + _snowmanxxxxxx] = 1.0F - _snowman;
         _snowman[gc.f.c() + _snowmanxxxxxx] = 1.0F - _snowmanxxx;
         _snowman[gc.a.c() + _snowmanxxxxxx] = 1.0F - _snowmanx;
         _snowman[gc.b.c() + _snowmanxxxxxx] = 1.0F - _snowmanxxxx;
         _snowman[gc.c.c() + _snowmanxxxxxx] = 1.0F - _snowmanxx;
         _snowman[gc.d.c() + _snowmanxxxxxx] = 1.0F - _snowmanxxxxx;
      }

      float _snowmanxxxxxx = 1.0E-4F;
      float _snowmanxxxxxxx = 0.9999F;
      switch (_snowman) {
         case a:
            _snowman.set(1, _snowman >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            _snowman.set(0, _snowmanx == _snowmanxxxx && (_snowmanx < 1.0E-4F || _snowman.r(_snowman, _snowman)));
            break;
         case b:
            _snowman.set(1, _snowman >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            _snowman.set(0, _snowmanx == _snowmanxxxx && (_snowmanxxxx > 0.9999F || _snowman.r(_snowman, _snowman)));
            break;
         case c:
            _snowman.set(1, _snowman >= 1.0E-4F || _snowmanx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxx <= 0.9999F);
            _snowman.set(0, _snowmanxx == _snowmanxxxxx && (_snowmanxx < 1.0E-4F || _snowman.r(_snowman, _snowman)));
            break;
         case d:
            _snowman.set(1, _snowman >= 1.0E-4F || _snowmanx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxx <= 0.9999F);
            _snowman.set(0, _snowmanxx == _snowmanxxxxx && (_snowmanxxxxx > 0.9999F || _snowman.r(_snowman, _snowman)));
            break;
         case e:
            _snowman.set(1, _snowmanx >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            _snowman.set(0, _snowman == _snowmanxxx && (_snowman < 1.0E-4F || _snowman.r(_snowman, _snowman)));
            break;
         case f:
            _snowman.set(1, _snowmanx >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            _snowman.set(0, _snowman == _snowmanxxx && (_snowmanxxx > 0.9999F || _snowman.r(_snowman, _snowman)));
      }
   }

   private void a(bra var1, ceh var2, fx var3, int var4, int var5, boolean var6, dfm var7, dfq var8, List<eba> var9, BitSet var10) {
      for (eba _snowman : _snowman) {
         if (_snowman) {
            this.a(_snowman, _snowman, _snowman, _snowman.b(), _snowman.e(), null, _snowman);
            fx _snowmanx = _snowman.get(0) ? _snowman.a(_snowman.e()) : _snowman;
            _snowman = eae.a(_snowman, _snowman, _snowmanx);
         }

         float _snowmanx = _snowman.a(_snowman.e(), _snowman.f());
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman.c(), _snowman, _snowmanx, _snowmanx, _snowmanx, _snowmanx, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public void a(dfm.a var1, dfq var2, @Nullable ceh var3, elo var4, float var5, float var6, float var7, int var8, int var9) {
      Random _snowman = new Random();
      long _snowmanx = 42L;

      for (gc _snowmanxx : gc.values()) {
         _snowman.setSeed(42L);
         a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.a(_snowman, _snowmanxx, _snowman), _snowman, _snowman);
      }

      _snowman.setSeed(42L);
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.a(_snowman, null, _snowman), _snowman, _snowman);
   }

   private static void a(dfm.a var0, dfq var1, float var2, float var3, float var4, List<eba> var5, int var6, int var7) {
      for (eba _snowman : _snowman) {
         float _snowmanx;
         float _snowmanxx;
         float _snowmanxxx;
         if (_snowman.c()) {
            _snowmanx = afm.a(_snowman, 0.0F, 1.0F);
            _snowmanxx = afm.a(_snowman, 0.0F, 1.0F);
            _snowmanxxx = afm.a(_snowman, 0.0F, 1.0F);
         } else {
            _snowmanx = 1.0F;
            _snowmanxx = 1.0F;
            _snowmanxxx = 1.0F;
         }

         _snowman.a(_snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowman, _snowman);
      }
   }

   public static void a() {
      b.get().a();
   }

   public static void b() {
      b.get().b();
   }

   public static enum a {
      a(
         new gc[]{gc.e, gc.f, gc.c, gc.d},
         0.5F,
         true,
         new eaz.e[]{eaz.e.k, eaz.e.d, eaz.e.k, eaz.e.j, eaz.e.e, eaz.e.j, eaz.e.e, eaz.e.d},
         new eaz.e[]{eaz.e.k, eaz.e.c, eaz.e.k, eaz.e.i, eaz.e.e, eaz.e.i, eaz.e.e, eaz.e.c},
         new eaz.e[]{eaz.e.l, eaz.e.c, eaz.e.l, eaz.e.i, eaz.e.f, eaz.e.i, eaz.e.f, eaz.e.c},
         new eaz.e[]{eaz.e.l, eaz.e.d, eaz.e.l, eaz.e.j, eaz.e.f, eaz.e.j, eaz.e.f, eaz.e.d}
      ),
      b(
         new gc[]{gc.f, gc.e, gc.c, gc.d},
         1.0F,
         true,
         new eaz.e[]{eaz.e.f, eaz.e.d, eaz.e.f, eaz.e.j, eaz.e.l, eaz.e.j, eaz.e.l, eaz.e.d},
         new eaz.e[]{eaz.e.f, eaz.e.c, eaz.e.f, eaz.e.i, eaz.e.l, eaz.e.i, eaz.e.l, eaz.e.c},
         new eaz.e[]{eaz.e.e, eaz.e.c, eaz.e.e, eaz.e.i, eaz.e.k, eaz.e.i, eaz.e.k, eaz.e.c},
         new eaz.e[]{eaz.e.e, eaz.e.d, eaz.e.e, eaz.e.j, eaz.e.k, eaz.e.j, eaz.e.k, eaz.e.d}
      ),
      c(
         new gc[]{gc.b, gc.a, gc.f, gc.e},
         0.8F,
         true,
         new eaz.e[]{eaz.e.b, eaz.e.k, eaz.e.b, eaz.e.e, eaz.e.h, eaz.e.e, eaz.e.h, eaz.e.k},
         new eaz.e[]{eaz.e.b, eaz.e.l, eaz.e.b, eaz.e.f, eaz.e.h, eaz.e.f, eaz.e.h, eaz.e.l},
         new eaz.e[]{eaz.e.a, eaz.e.l, eaz.e.a, eaz.e.f, eaz.e.g, eaz.e.f, eaz.e.g, eaz.e.l},
         new eaz.e[]{eaz.e.a, eaz.e.k, eaz.e.a, eaz.e.e, eaz.e.g, eaz.e.e, eaz.e.g, eaz.e.k}
      ),
      d(
         new gc[]{gc.e, gc.f, gc.a, gc.b},
         0.8F,
         true,
         new eaz.e[]{eaz.e.b, eaz.e.k, eaz.e.h, eaz.e.k, eaz.e.h, eaz.e.e, eaz.e.b, eaz.e.e},
         new eaz.e[]{eaz.e.a, eaz.e.k, eaz.e.g, eaz.e.k, eaz.e.g, eaz.e.e, eaz.e.a, eaz.e.e},
         new eaz.e[]{eaz.e.a, eaz.e.l, eaz.e.g, eaz.e.l, eaz.e.g, eaz.e.f, eaz.e.a, eaz.e.f},
         new eaz.e[]{eaz.e.b, eaz.e.l, eaz.e.h, eaz.e.l, eaz.e.h, eaz.e.f, eaz.e.b, eaz.e.f}
      ),
      e(
         new gc[]{gc.b, gc.a, gc.c, gc.d},
         0.6F,
         true,
         new eaz.e[]{eaz.e.b, eaz.e.d, eaz.e.b, eaz.e.j, eaz.e.h, eaz.e.j, eaz.e.h, eaz.e.d},
         new eaz.e[]{eaz.e.b, eaz.e.c, eaz.e.b, eaz.e.i, eaz.e.h, eaz.e.i, eaz.e.h, eaz.e.c},
         new eaz.e[]{eaz.e.a, eaz.e.c, eaz.e.a, eaz.e.i, eaz.e.g, eaz.e.i, eaz.e.g, eaz.e.c},
         new eaz.e[]{eaz.e.a, eaz.e.d, eaz.e.a, eaz.e.j, eaz.e.g, eaz.e.j, eaz.e.g, eaz.e.d}
      ),
      f(
         new gc[]{gc.a, gc.b, gc.c, gc.d},
         0.6F,
         true,
         new eaz.e[]{eaz.e.g, eaz.e.d, eaz.e.g, eaz.e.j, eaz.e.a, eaz.e.j, eaz.e.a, eaz.e.d},
         new eaz.e[]{eaz.e.g, eaz.e.c, eaz.e.g, eaz.e.i, eaz.e.a, eaz.e.i, eaz.e.a, eaz.e.c},
         new eaz.e[]{eaz.e.h, eaz.e.c, eaz.e.h, eaz.e.i, eaz.e.b, eaz.e.i, eaz.e.b, eaz.e.c},
         new eaz.e[]{eaz.e.h, eaz.e.d, eaz.e.h, eaz.e.j, eaz.e.b, eaz.e.j, eaz.e.b, eaz.e.d}
      );

      private final gc[] g;
      private final boolean h;
      private final eaz.e[] i;
      private final eaz.e[] j;
      private final eaz.e[] k;
      private final eaz.e[] l;
      private static final eaz.a[] m = x.a(new eaz.a[6], var0 -> {
         var0[gc.a.c()] = a;
         var0[gc.b.c()] = b;
         var0[gc.c.c()] = c;
         var0[gc.d.c()] = d;
         var0[gc.e.c()] = e;
         var0[gc.f.c()] = f;
      });

      private a(gc[] var3, float var4, boolean var5, eaz.e[] var6, eaz.e[] var7, eaz.e[] var8, eaz.e[] var9) {
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
         this.k = _snowman;
         this.l = _snowman;
      }

      public static eaz.a a(gc var0) {
         return m[_snowman.c()];
      }
   }

   class b {
      private final float[] b = new float[4];
      private final int[] c = new int[4];

      public b() {
      }

      public void a(bra var1, ceh var2, fx var3, gc var4, float[] var5, BitSet var6, boolean var7) {
         fx _snowman = _snowman.get(0) ? _snowman.a(_snowman) : _snowman;
         eaz.a _snowmanx = eaz.a.a(_snowman);
         fx.a _snowmanxx = new fx.a();
         eaz.d _snowmanxxx = eaz.b.get();
         _snowmanxx.a(_snowman, _snowmanx.g[0]);
         ceh _snowmanxxxx = _snowman.d_(_snowmanxx);
         int _snowmanxxxxx = _snowmanxxx.a(_snowmanxxxx, _snowman, _snowmanxx);
         float _snowmanxxxxxx = _snowmanxxx.b(_snowmanxxxx, _snowman, _snowmanxx);
         _snowmanxx.a(_snowman, _snowmanx.g[1]);
         ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxx);
         int _snowmanxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxx, _snowman, _snowmanxx);
         float _snowmanxxxxxxxxx = _snowmanxxx.b(_snowmanxxxxxxx, _snowman, _snowmanxx);
         _snowmanxx.a(_snowman, _snowmanx.g[2]);
         ceh _snowmanxxxxxxxxxx = _snowman.d_(_snowmanxx);
         int _snowmanxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxxx, _snowman, _snowmanxx);
         float _snowmanxxxxxxxxxxxx = _snowmanxxx.b(_snowmanxxxxxxxxxx, _snowman, _snowmanxx);
         _snowmanxx.a(_snowman, _snowmanx.g[3]);
         ceh _snowmanxxxxxxxxxxxxx = _snowman.d_(_snowmanxx);
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxxxxxx, _snowman, _snowmanxx);
         float _snowmanxxxxxxxxxxxxxxx = _snowmanxxx.b(_snowmanxxxxxxxxxxxxx, _snowman, _snowmanxx);
         _snowmanxx.a(_snowman, _snowmanx.g[0]).c(_snowman);
         boolean _snowmanxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx).b(_snowman, _snowmanxx) == 0;
         _snowmanxx.a(_snowman, _snowmanx.g[1]).c(_snowman);
         boolean _snowmanxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx).b(_snowman, _snowmanxx) == 0;
         _snowmanxx.a(_snowman, _snowmanx.g[2]).c(_snowman);
         boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx).b(_snowman, _snowmanxx) == 0;
         _snowmanxx.a(_snowman, _snowmanx.g[3]).c(_snowman);
         boolean _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx).b(_snowman, _snowmanxx) == 0;
         float _snowmanxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx;
         } else {
            _snowmanxx.a(_snowman, _snowmanx.g[0]).c(_snowmanx.g[2]);
            ceh _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx;
         } else {
            _snowmanxx.a(_snowman, _snowmanx.g[0]).c(_snowmanx.g[3]);
            ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx;
         } else {
            _snowmanxx.a(_snowman, _snowmanx.g[1]).c(_snowmanx.g[2]);
            ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx;
         } else {
            _snowmanxx.a(_snowman, _snowmanx.g[1]).c(_snowmanx.g[3]);
            ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
         }

         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.a(_snowman, _snowman, _snowman);
         _snowmanxx.a(_snowman, _snowman);
         ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.d_(_snowmanxx);
         if (_snowman.get(0) || !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.i(_snowman, _snowmanxx)) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.get(0) ? _snowmanxxx.b(_snowman.d_(_snowman), _snowman, _snowman) : _snowmanxxx.b(_snowman.d_(_snowman), _snowman, _snowman);
         eaz.c _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = eaz.c.a(_snowman);
         if (_snowman.get(1) && _snowmanx.h) {
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx + _snowmanxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.i[0].m] * _snowman[_snowmanx.i[1].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.i[2].m] * _snowman[_snowmanx.i[3].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.i[4].m] * _snowman[_snowmanx.i[5].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.i[6].m] * _snowman[_snowmanx.i[7].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.j[0].m] * _snowman[_snowmanx.j[1].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.j[2].m] * _snowman[_snowmanx.j[3].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.j[4].m] * _snowman[_snowmanx.j[5].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.j[6].m] * _snowman[_snowmanx.j[7].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.k[0].m] * _snowman[_snowmanx.k[1].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.k[2].m] * _snowman[_snowmanx.k[3].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.k[4].m] * _snowman[_snowmanx.k[5].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.k[6].m] * _snowman[_snowmanx.k[7].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.l[0].m] * _snowman[_snowmanx.l[1].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.l[2].m] * _snowman[_snowmanx.l[3].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.l[4].m] * _snowman[_snowmanx.l[5].m];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman[_snowmanx.l[6].m] * _snowman[_snowmanx.l[7].m];
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.g] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.h] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.i] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.j] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
               _snowmanxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
               _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.g] = this.a(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.h] = this.a(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.i] = this.a(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.j] = this.a(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
         } else {
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx + _snowmanxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               * 0.25F;
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.g] = this.a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.h] = this.a(_snowmanxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.i] = this.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            this.c[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.j] = this.a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.g] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.h] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.i] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.j] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.a(_snowman, _snowman);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < this.b.length; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
            this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] = this.b[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         }
      }

      private int a(int var1, int var2, int var3, int var4) {
         if (_snowman == 0) {
            _snowman = _snowman;
         }

         if (_snowman == 0) {
            _snowman = _snowman;
         }

         if (_snowman == 0) {
            _snowman = _snowman;
         }

         return _snowman + _snowman + _snowman + _snowman >> 2 & 16711935;
      }

      private int a(int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         int _snowman = (int)((float)(_snowman >> 16 & 0xFF) * _snowman + (float)(_snowman >> 16 & 0xFF) * _snowman + (float)(_snowman >> 16 & 0xFF) * _snowman + (float)(_snowman >> 16 & 0xFF) * _snowman) & 0xFF;
         int _snowmanx = (int)((float)(_snowman & 0xFF) * _snowman + (float)(_snowman & 0xFF) * _snowman + (float)(_snowman & 0xFF) * _snowman + (float)(_snowman & 0xFF) * _snowman) & 0xFF;
         return _snowman << 16 | _snowmanx;
      }
   }

   static enum c {
      a(0, 1, 2, 3),
      b(2, 3, 0, 1),
      c(3, 0, 1, 2),
      d(0, 1, 2, 3),
      e(3, 0, 1, 2),
      f(1, 2, 3, 0);

      private final int g;
      private final int h;
      private final int i;
      private final int j;
      private static final eaz.c[] k = x.a(new eaz.c[6], var0 -> {
         var0[gc.a.c()] = a;
         var0[gc.b.c()] = b;
         var0[gc.c.c()] = c;
         var0[gc.d.c()] = d;
         var0[gc.e.c()] = e;
         var0[gc.f.c()] = f;
      });

      private c(int var3, int var4, int var5, int var6) {
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
      }

      public static eaz.c a(gc var0) {
         return k[_snowman.c()];
      }
   }

   static class d {
      private boolean a;
      private final Long2IntLinkedOpenHashMap b = x.a((Supplier<Long2IntLinkedOpenHashMap>)(() -> {
         Long2IntLinkedOpenHashMap _snowman = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int var1) {
            }
         };
         _snowman.defaultReturnValue(Integer.MAX_VALUE);
         return _snowman;
      }));
      private final Long2FloatLinkedOpenHashMap c = x.a((Supplier<Long2FloatLinkedOpenHashMap>)(() -> {
         Long2FloatLinkedOpenHashMap _snowman = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int var1) {
            }
         };
         _snowman.defaultReturnValue(Float.NaN);
         return _snowman;
      }));

      private d() {
      }

      public void a() {
         this.a = true;
      }

      public void b() {
         this.a = false;
         this.b.clear();
         this.c.clear();
      }

      public int a(ceh var1, bra var2, fx var3) {
         long _snowman = _snowman.a();
         if (this.a) {
            int _snowmanx = this.b.get(_snowman);
            if (_snowmanx != Integer.MAX_VALUE) {
               return _snowmanx;
            }
         }

         int _snowmanx = eae.a(_snowman, _snowman, _snowman);
         if (this.a) {
            if (this.b.size() == 100) {
               this.b.removeFirstInt();
            }

            this.b.put(_snowman, _snowmanx);
         }

         return _snowmanx;
      }

      public float b(ceh var1, bra var2, fx var3) {
         long _snowman = _snowman.a();
         if (this.a) {
            float _snowmanx = this.c.get(_snowman);
            if (!Float.isNaN(_snowmanx)) {
               return _snowmanx;
            }
         }

         float _snowmanx = _snowman.f(_snowman, _snowman);
         if (this.a) {
            if (this.c.size() == 100) {
               this.c.removeFirstFloat();
            }

            this.c.put(_snowman, _snowmanx);
         }

         return _snowmanx;
      }
   }

   public static enum e {
      a(gc.a, false),
      b(gc.b, false),
      c(gc.c, false),
      d(gc.d, false),
      e(gc.e, false),
      f(gc.f, false),
      g(gc.a, true),
      h(gc.b, true),
      i(gc.c, true),
      j(gc.d, true),
      k(gc.e, true),
      l(gc.f, true);

      private final int m;

      private e(gc var3, boolean var4) {
         this.m = _snowman.c() + (_snowman ? gc.values().length : 0);
      }
   }
}
