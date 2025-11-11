import com.google.common.collect.UnmodifiableIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;

public class bvt extends buo implements bzu {
   public static final cey a = bys.a;
   public static final cey b = bys.b;
   public static final cey c = bys.c;
   public static final cey d = bys.d;
   public static final cey e = cex.C;
   protected static final Map<gc, cey> f = bys.g.entrySet().stream().filter(var0 -> var0.getKey().n().d()).collect(x.a());
   protected final ddh[] g;
   protected final ddh[] h;
   private final Object2IntMap<ceh> i = new Object2IntOpenHashMap();

   protected bvt(float var1, float var2, float var3, float var4, float var5, ceg.c var6) {
      super(_snowman);
      this.g = this.a(_snowman, _snowman, _snowman, 0.0F, _snowman);
      this.h = this.a(_snowman, _snowman, _snowman, 0.0F, _snowman);
      UnmodifiableIterator var7 = this.n.a().iterator();

      while (var7.hasNext()) {
         ceh _snowman = (ceh)var7.next();
         this.g(_snowman);
      }
   }

   protected ddh[] a(float var1, float var2, float var3, float var4, float var5) {
      float _snowman = 8.0F - _snowman;
      float _snowmanx = 8.0F + _snowman;
      float _snowmanxx = 8.0F - _snowman;
      float _snowmanxxx = 8.0F + _snowman;
      ddh _snowmanxxxx = buo.a((double)_snowman, 0.0, (double)_snowman, (double)_snowmanx, (double)_snowman, (double)_snowmanx);
      ddh _snowmanxxxxx = buo.a((double)_snowmanxx, (double)_snowman, 0.0, (double)_snowmanxxx, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxx = buo.a((double)_snowmanxx, (double)_snowman, (double)_snowmanxx, (double)_snowmanxxx, (double)_snowman, 16.0);
      ddh _snowmanxxxxxxx = buo.a(0.0, (double)_snowman, (double)_snowmanxx, (double)_snowmanxxx, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxxxx = buo.a((double)_snowmanxx, (double)_snowman, (double)_snowmanxx, 16.0, (double)_snowman, (double)_snowmanxxx);
      ddh _snowmanxxxxxxxxx = dde.a(_snowmanxxxxx, _snowmanxxxxxxxx);
      ddh _snowmanxxxxxxxxxx = dde.a(_snowmanxxxxxx, _snowmanxxxxxxx);
      ddh[] _snowmanxxxxxxxxxxx = new ddh[]{
         dde.a(),
         _snowmanxxxxxx,
         _snowmanxxxxxxx,
         _snowmanxxxxxxxxxx,
         _snowmanxxxxx,
         dde.a(_snowmanxxxxxx, _snowmanxxxxx),
         dde.a(_snowmanxxxxxxx, _snowmanxxxxx),
         dde.a(_snowmanxxxxxxxxxx, _snowmanxxxxx),
         _snowmanxxxxxxxx,
         dde.a(_snowmanxxxxxx, _snowmanxxxxxxxx),
         dde.a(_snowmanxxxxxxx, _snowmanxxxxxxxx),
         dde.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx),
         _snowmanxxxxxxxxx,
         dde.a(_snowmanxxxxxx, _snowmanxxxxxxxxx),
         dde.a(_snowmanxxxxxxx, _snowmanxxxxxxxxx),
         dde.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)
      };

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxx++) {
         _snowmanxxxxxxxxxxx[_snowmanxxxxxxxxxxxx] = dde.a(_snowmanxxxx, _snowmanxxxxxxxxxxx[_snowmanxxxxxxxxxxxx]);
      }

      return _snowmanxxxxxxxxxxx;
   }

   @Override
   public boolean b(ceh var1, brc var2, fx var3) {
      return !_snowman.c(e);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.h[this.g(_snowman)];
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return this.g[this.g(_snowman)];
   }

   private static int a(gc var0) {
      return 1 << _snowman.d();
   }

   protected int g(ceh var1) {
      return this.i.computeIntIfAbsent(_snowman, var0 -> {
         int _snowman = 0;
         if (var0.c(a)) {
            _snowman |= a(gc.c);
         }

         if (var0.c(b)) {
            _snowman |= a(gc.f);
         }

         if (var0.c(c)) {
            _snowman |= a(gc.d);
         }

         if (var0.c(d)) {
            _snowman |= a(gc.e);
         }

         return _snowman;
      });
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(e) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      switch (_snowman) {
         case c:
            return _snowman.a(a, _snowman.c(c)).a(b, _snowman.c(d)).a(c, _snowman.c(a)).a(d, _snowman.c(b));
         case d:
            return _snowman.a(a, _snowman.c(b)).a(b, _snowman.c(c)).a(c, _snowman.c(d)).a(d, _snowman.c(a));
         case b:
            return _snowman.a(a, _snowman.c(d)).a(b, _snowman.c(a)).a(c, _snowman.c(b)).a(d, _snowman.c(c));
         default:
            return _snowman;
      }
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      switch (_snowman) {
         case b:
            return _snowman.a(a, _snowman.c(c)).a(c, _snowman.c(a));
         case c:
            return _snowman.a(b, _snowman.c(d)).a(d, _snowman.c(b));
         default:
            return super.a(_snowman, _snowman);
      }
   }
}
