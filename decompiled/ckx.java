import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ckx extends cjl<cmv> {
   private static final LoadingCache<Long, List<ckx.a>> a = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new ckx.b());

   public ckx(Codec<cmv> var1) {
      super(_snowman);
   }

   public static List<ckx.a> a(bsr var0) {
      Random _snowman = new Random(_snowman.C());
      long _snowmanx = _snowman.nextLong() & 65535L;
      return (List<ckx.a>)a.getUnchecked(_snowmanx);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmv var5) {
      List<ckx.a> _snowman = _snowman.c();
      if (_snowman.isEmpty()) {
         _snowman = a(_snowman);
      }

      for (ckx.a _snowmanx : _snowman) {
         if (_snowmanx.a(_snowman)) {
            this.a(_snowman, _snowman, _snowman, _snowmanx);
         }
      }

      return true;
   }

   private void a(bsk var1, Random var2, cmv var3, ckx.a var4) {
      int _snowman = _snowman.c();

      for (fx _snowmanx : fx.a(new fx(_snowman.a() - _snowman, 0, _snowman.b() - _snowman), new fx(_snowman.a() + _snowman, _snowman.d() + 10, _snowman.b() + _snowman))) {
         if (_snowmanx.a((double)_snowman.a(), (double)_snowmanx.v(), (double)_snowman.b(), false) <= (double)(_snowman * _snowman + 1) && _snowmanx.v() < _snowman.d()) {
            this.a(_snowman, _snowmanx, bup.bK.n());
         } else if (_snowmanx.v() > 65) {
            this.a(_snowman, _snowmanx, bup.a.n());
         }
      }

      if (_snowman.e()) {
         int _snowmanxx = -2;
         int _snowmanxxx = 2;
         int _snowmanxxxx = 3;
         fx.a _snowmanxxxxx = new fx.a();

         for (int _snowmanxxxxxx = -2; _snowmanxxxxxx <= 2; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = -2; _snowmanxxxxxxx <= 2; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx <= 3; _snowmanxxxxxxxx++) {
                  boolean _snowmanxxxxxxxxx = afm.a(_snowmanxxxxxx) == 2;
                  boolean _snowmanxxxxxxxxxx = afm.a(_snowmanxxxxxxx) == 2;
                  boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx == 3;
                  if (_snowmanxxxxxxxxx || _snowmanxxxxxxxxxx || _snowmanxxxxxxxxxxx) {
                     boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxx == -2 || _snowmanxxxxxx == 2 || _snowmanxxxxxxxxxxx;
                     boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx == -2 || _snowmanxxxxxxx == 2 || _snowmanxxxxxxxxxxx;
                     ceh _snowmanxxxxxxxxxxxxxx = bup.dH
                        .n()
                        .a(bxq.a, Boolean.valueOf(_snowmanxxxxxxxxxxxx && _snowmanxxxxxxx != -2))
                        .a(bxq.c, Boolean.valueOf(_snowmanxxxxxxxxxxxx && _snowmanxxxxxxx != 2))
                        .a(bxq.d, Boolean.valueOf(_snowmanxxxxxxxxxxxxx && _snowmanxxxxxx != -2))
                        .a(bxq.b, Boolean.valueOf(_snowmanxxxxxxxxxxxxx && _snowmanxxxxxx != 2));
                     this.a(_snowman, _snowmanxxxxx.d(_snowman.a() + _snowmanxxxxxx, _snowman.d() + _snowmanxxxxxxxx, _snowman.b() + _snowmanxxxxxxx), _snowmanxxxxxxxxxxxxxx);
                  }
               }
            }
         }
      }

      bbq _snowmanxx = aqe.s.a(_snowman.E());
      _snowmanxx.a(_snowman.d());
      _snowmanxx.m(_snowman.b());
      _snowmanxx.b((double)_snowman.a() + 0.5, (double)(_snowman.d() + 1), (double)_snowman.b() + 0.5, _snowman.nextFloat() * 360.0F, 0.0F);
      _snowman.c(_snowmanxx);
      this.a(_snowman, new fx(_snowman.a(), _snowman.d(), _snowman.b()), bup.z.n());
   }

   public static class a {
      public static final Codec<ckx.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("centerX").orElse(0).forGetter(var0x -> var0x.b),
                  Codec.INT.fieldOf("centerZ").orElse(0).forGetter(var0x -> var0x.c),
                  Codec.INT.fieldOf("radius").orElse(0).forGetter(var0x -> var0x.d),
                  Codec.INT.fieldOf("height").orElse(0).forGetter(var0x -> var0x.e),
                  Codec.BOOL.fieldOf("guarded").orElse(false).forGetter(var0x -> var0x.f)
               )
               .apply(var0, ckx.a::new)
      );
      private final int b;
      private final int c;
      private final int d;
      private final int e;
      private final boolean f;
      private final dci g;

      public a(int var1, int var2, int var3, int var4, boolean var5) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = new dci((double)(_snowman - _snowman), 0.0, (double)(_snowman - _snowman), (double)(_snowman + _snowman), 256.0, (double)(_snowman + _snowman));
      }

      public boolean a(fx var1) {
         return _snowman.u() >> 4 == this.b >> 4 && _snowman.w() >> 4 == this.c >> 4;
      }

      public int a() {
         return this.b;
      }

      public int b() {
         return this.c;
      }

      public int c() {
         return this.d;
      }

      public int d() {
         return this.e;
      }

      public boolean e() {
         return this.f;
      }

      public dci f() {
         return this.g;
      }
   }

   static class b extends CacheLoader<Long, List<ckx.a>> {
      private b() {
      }

      public List<ckx.a> a(Long var1) {
         List<Integer> _snowman = IntStream.range(0, 10).boxed().collect(Collectors.toList());
         Collections.shuffle(_snowman, new Random(_snowman));
         List<ckx.a> _snowmanx = Lists.newArrayList();

         for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
            int _snowmanxxx = afm.c(42.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 10) * (double)_snowmanxx)));
            int _snowmanxxxx = afm.c(42.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 10) * (double)_snowmanxx)));
            int _snowmanxxxxx = _snowman.get(_snowmanxx);
            int _snowmanxxxxxx = 2 + _snowmanxxxxx / 3;
            int _snowmanxxxxxxx = 76 + _snowmanxxxxx * 3;
            boolean _snowmanxxxxxxxx = _snowmanxxxxx == 1 || _snowmanxxxxx == 2;
            _snowmanx.add(new ckx.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx));
         }

         return _snowmanx;
      }
   }
}
