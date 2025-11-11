import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class bth extends bsy {
   private static final bth.a g = new bth.a(-7, ImmutableList.of(1.0, 1.0));
   public static final MapCodec<bth> e = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.LONG.fieldOf("seed").forGetter(var0x -> var0x.r),
               RecordCodecBuilder.create(
                     var0x -> var0x.group(bsv.c.a.fieldOf("parameters").forGetter(Pair::getFirst), bsv.d.fieldOf("biome").forGetter(Pair::getSecond))
                           .apply(var0x, Pair::of)
                  )
                  .listOf()
                  .fieldOf("biomes")
                  .forGetter(var0x -> var0x.p),
               bth.a.a.fieldOf("temperature_noise").forGetter(var0x -> var0x.h),
               bth.a.a.fieldOf("humidity_noise").forGetter(var0x -> var0x.i),
               bth.a.a.fieldOf("altitude_noise").forGetter(var0x -> var0x.j),
               bth.a.a.fieldOf("weirdness_noise").forGetter(var0x -> var0x.k)
            )
            .apply(var0, bth::new)
   );
   public static final Codec<bth> f = Codec.mapEither(bth.c.a, e)
      .xmap(var0 -> (bth)var0.map(bth.c::d, Function.identity()), var0 -> var0.d().<Either>map(Either::left).orElseGet(() -> Either.right(var0)))
      .codec();
   private final bth.a h;
   private final bth.a i;
   private final bth.a j;
   private final bth.a k;
   private final cua l;
   private final cua m;
   private final cua n;
   private final cua o;
   private final List<Pair<bsv.c, Supplier<bsv>>> p;
   private final boolean q;
   private final long r;
   private final Optional<Pair<gm<bsv>, bth.b>> s;

   private bth(long var1, List<Pair<bsv.c, Supplier<bsv>>> var3, Optional<Pair<gm<bsv>, bth.b>> var4) {
      this(_snowman, _snowman, g, g, g, g, _snowman);
   }

   private bth(long var1, List<Pair<bsv.c, Supplier<bsv>>> var3, bth.a var4, bth.a var5, bth.a var6, bth.a var7) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, Optional.empty());
   }

   private bth(long var1, List<Pair<bsv.c, Supplier<bsv>>> var3, bth.a var4, bth.a var5, bth.a var6, bth.a var7, Optional<Pair<gm<bsv>, bth.b>> var8) {
      super(_snowman.stream().map(Pair::getSecond));
      this.r = _snowman;
      this.s = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = cua.a(new chx(_snowman), _snowman.a(), _snowman.b());
      this.m = cua.a(new chx(_snowman + 1L), _snowman.a(), _snowman.b());
      this.n = cua.a(new chx(_snowman + 2L), _snowman.a(), _snowman.b());
      this.o = cua.a(new chx(_snowman + 3L), _snowman.a(), _snowman.b());
      this.p = _snowman;
      this.q = false;
   }

   @Override
   protected Codec<? extends bsy> a() {
      return f;
   }

   @Override
   public bsy a(long var1) {
      return new bth(_snowman, this.p, this.h, this.i, this.j, this.k, this.s);
   }

   private Optional<bth.c> d() {
      return this.s.map(var1 -> new bth.c((bth.b)var1.getSecond(), (gm)var1.getFirst(), this.r));
   }

   @Override
   public bsv b(int var1, int var2, int var3) {
      int _snowman = this.q ? _snowman : 0;
      bsv.c _snowmanx = new bsv.c(
         (float)this.l.a((double)_snowman, (double)_snowman, (double)_snowman),
         (float)this.m.a((double)_snowman, (double)_snowman, (double)_snowman),
         (float)this.n.a((double)_snowman, (double)_snowman, (double)_snowman),
         (float)this.o.a((double)_snowman, (double)_snowman, (double)_snowman),
         0.0F
      );
      return this.p.stream().min(Comparator.comparing(var1x -> ((bsv.c)var1x.getFirst()).a(_snowman))).map(Pair::getSecond).map(Supplier::get).orElse(kt.b);
   }

   public boolean b(long var1) {
      return this.r == _snowman && this.s.isPresent() && Objects.equals(this.s.get().getSecond(), bth.b.a);
   }

   static class a {
      private final int b;
      private final DoubleList c;
      public static final Codec<bth.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(Codec.INT.fieldOf("firstOctave").forGetter(bth.a::a), Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(bth.a::b))
               .apply(var0, bth.a::new)
      );

      public a(int var1, List<Double> var2) {
         this.b = _snowman;
         this.c = new DoubleArrayList(_snowman);
      }

      public int a() {
         return this.b;
      }

      public DoubleList b() {
         return this.c;
      }
   }

   public static class b {
      private static final Map<vk, bth.b> b = Maps.newHashMap();
      public static final bth.b a = new bth.b(
         new vk("nether"),
         (var0, var1, var2) -> new bth(
               var2,
               ImmutableList.of(
                  Pair.of(new bsv.c(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), (Supplier<bsv>)() -> var1.d(btb.i)),
                  Pair.of(new bsv.c(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), (Supplier<bsv>)() -> var1.d(btb.ax)),
                  Pair.of(new bsv.c(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), (Supplier<bsv>)() -> var1.d(btb.ay)),
                  Pair.of(new bsv.c(0.0F, 0.5F, 0.0F, 0.0F, 0.375F), (Supplier<bsv>)() -> var1.d(btb.az)),
                  Pair.of(new bsv.c(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F), (Supplier<bsv>)() -> var1.d(btb.aA))
               ),
               Optional.of(Pair.of(var1, var0))
            )
      );
      private final vk c;
      private final Function3<bth.b, gm<bsv>, Long, bth> d;

      public b(vk var1, Function3<bth.b, gm<bsv>, Long, bth> var2) {
         this.c = _snowman;
         this.d = _snowman;
         b.put(_snowman, this);
      }

      public bth a(gm<bsv> var1, long var2) {
         return (bth)this.d.apply(this, _snowman, _snowman);
      }
   }

   static final class c {
      public static final MapCodec<bth.c> a = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  vk.a
                     .flatXmap(
                        var0x -> Optional.ofNullable(bth.b.b.get(var0x))
                              .<DataResult>map(DataResult::success)
                              .orElseGet(() -> DataResult.error("Unknown preset: " + var0x)),
                        var0x -> DataResult.success(var0x.c)
                     )
                     .fieldOf("preset")
                     .stable()
                     .forGetter(bth.c::a),
                  vg.a(gm.ay).forGetter(bth.c::b),
                  Codec.LONG.fieldOf("seed").stable().forGetter(bth.c::c)
               )
               .apply(var0, var0.stable(bth.c::new))
      );
      private final bth.b b;
      private final gm<bsv> c;
      private final long d;

      private c(bth.b var1, gm<bsv> var2, long var3) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public bth.b a() {
         return this.b;
      }

      public gm<bsv> b() {
         return this.c;
      }

      public long c() {
         return this.d;
      }

      public bth d() {
         return this.b.a(this.c, this.d);
      }
   }
}
