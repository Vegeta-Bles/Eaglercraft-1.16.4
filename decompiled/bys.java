import com.google.common.collect.Maps;
import java.util.Map;

public class bys extends buo {
   private static final gc[] i = gc.values();
   public static final cey a = cex.I;
   public static final cey b = cex.J;
   public static final cey c = cex.K;
   public static final cey d = cex.L;
   public static final cey e = cex.G;
   public static final cey f = cex.H;
   public static final Map<gc, cey> g = x.a(Maps.newEnumMap(gc.class), var0 -> {
      var0.put(gc.c, a);
      var0.put(gc.f, b);
      var0.put(gc.d, c);
      var0.put(gc.e, d);
      var0.put(gc.b, e);
      var0.put(gc.a, f);
   });
   protected final ddh[] h;

   protected bys(float var1, ceg.c var2) {
      super(_snowman);
      this.h = this.a(_snowman);
   }

   private ddh[] a(float var1) {
      float _snowman = 0.5F - _snowman;
      float _snowmanx = 0.5F + _snowman;
      ddh _snowmanxx = buo.a((double)(_snowman * 16.0F), (double)(_snowman * 16.0F), (double)(_snowman * 16.0F), (double)(_snowmanx * 16.0F), (double)(_snowmanx * 16.0F), (double)(_snowmanx * 16.0F));
      ddh[] _snowmanxxx = new ddh[i.length];

      for (int _snowmanxxxx = 0; _snowmanxxxx < i.length; _snowmanxxxx++) {
         gc _snowmanxxxxx = i[_snowmanxxxx];
         _snowmanxxx[_snowmanxxxx] = dde.a(
            0.5 + Math.min((double)(-_snowman), (double)_snowmanxxxxx.i() * 0.5),
            0.5 + Math.min((double)(-_snowman), (double)_snowmanxxxxx.j() * 0.5),
            0.5 + Math.min((double)(-_snowman), (double)_snowmanxxxxx.k() * 0.5),
            0.5 + Math.max((double)_snowman, (double)_snowmanxxxxx.i() * 0.5),
            0.5 + Math.max((double)_snowman, (double)_snowmanxxxxx.j() * 0.5),
            0.5 + Math.max((double)_snowman, (double)_snowmanxxxxx.k() * 0.5)
         );
      }

      ddh[] _snowmanxxxx = new ddh[64];

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 64; _snowmanxxxxx++) {
         ddh _snowmanxxxxxx = _snowmanxx;

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < i.length; _snowmanxxxxxxx++) {
            if ((_snowmanxxxxx & 1 << _snowmanxxxxxxx) != 0) {
               _snowmanxxxxxx = dde.a(_snowmanxxxxxx, _snowmanxxx[_snowmanxxxxxxx]);
            }
         }

         _snowmanxxxx[_snowmanxxxxx] = _snowmanxxxxxx;
      }

      return _snowmanxxxx;
   }

   @Override
   public boolean b(ceh var1, brc var2, fx var3) {
      return false;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.h[this.h(_snowman)];
   }

   protected int h(ceh var1) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < i.length; _snowmanx++) {
         if (_snowman.c(g.get(i[_snowmanx]))) {
            _snowman |= 1 << _snowmanx;
         }
      }

      return _snowman;
   }
}
