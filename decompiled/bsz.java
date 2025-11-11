import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class bsz {
   public static final Codec<bsz> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("fog_color").forGetter(var0x -> var0x.b),
               Codec.INT.fieldOf("water_color").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("water_fog_color").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("sky_color").forGetter(var0x -> var0x.e),
               Codec.INT.optionalFieldOf("foliage_color").forGetter(var0x -> var0x.f),
               Codec.INT.optionalFieldOf("grass_color").forGetter(var0x -> var0x.g),
               bsz.b.d.optionalFieldOf("grass_color_modifier", bsz.b.a).forGetter(var0x -> var0x.h),
               bsu.a.optionalFieldOf("particle").forGetter(var0x -> var0x.i),
               adp.a.optionalFieldOf("ambient_sound").forGetter(var0x -> var0x.j),
               bst.a.optionalFieldOf("mood_sound").forGetter(var0x -> var0x.k),
               bss.a.optionalFieldOf("additions_sound").forGetter(var0x -> var0x.l),
               adn.a.optionalFieldOf("music").forGetter(var0x -> var0x.m)
            )
            .apply(var0, bsz::new)
   );
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final Optional<Integer> f;
   private final Optional<Integer> g;
   private final bsz.b h;
   private final Optional<bsu> i;
   private final Optional<adp> j;
   private final Optional<bst> k;
   private final Optional<bss> l;
   private final Optional<adn> m;

   private bsz(
      int var1,
      int var2,
      int var3,
      int var4,
      Optional<Integer> var5,
      Optional<Integer> var6,
      bsz.b var7,
      Optional<bsu> var8,
      Optional<adp> var9,
      Optional<bst> var10,
      Optional<bss> var11,
      Optional<adn> var12
   ) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
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

   public Optional<Integer> e() {
      return this.f;
   }

   public Optional<Integer> f() {
      return this.g;
   }

   public bsz.b g() {
      return this.h;
   }

   public Optional<bsu> h() {
      return this.i;
   }

   public Optional<adp> i() {
      return this.j;
   }

   public Optional<bst> j() {
      return this.k;
   }

   public Optional<bss> k() {
      return this.l;
   }

   public Optional<adn> l() {
      return this.m;
   }

   public static class a {
      private OptionalInt a = OptionalInt.empty();
      private OptionalInt b = OptionalInt.empty();
      private OptionalInt c = OptionalInt.empty();
      private OptionalInt d = OptionalInt.empty();
      private Optional<Integer> e = Optional.empty();
      private Optional<Integer> f = Optional.empty();
      private bsz.b g = bsz.b.a;
      private Optional<bsu> h = Optional.empty();
      private Optional<adp> i = Optional.empty();
      private Optional<bst> j = Optional.empty();
      private Optional<bss> k = Optional.empty();
      private Optional<adn> l = Optional.empty();

      public a() {
      }

      public bsz.a a(int var1) {
         this.a = OptionalInt.of(_snowman);
         return this;
      }

      public bsz.a b(int var1) {
         this.b = OptionalInt.of(_snowman);
         return this;
      }

      public bsz.a c(int var1) {
         this.c = OptionalInt.of(_snowman);
         return this;
      }

      public bsz.a d(int var1) {
         this.d = OptionalInt.of(_snowman);
         return this;
      }

      public bsz.a e(int var1) {
         this.e = Optional.of(_snowman);
         return this;
      }

      public bsz.a f(int var1) {
         this.f = Optional.of(_snowman);
         return this;
      }

      public bsz.a a(bsz.b var1) {
         this.g = _snowman;
         return this;
      }

      public bsz.a a(bsu var1) {
         this.h = Optional.of(_snowman);
         return this;
      }

      public bsz.a a(adp var1) {
         this.i = Optional.of(_snowman);
         return this;
      }

      public bsz.a a(bst var1) {
         this.j = Optional.of(_snowman);
         return this;
      }

      public bsz.a a(bss var1) {
         this.k = Optional.of(_snowman);
         return this;
      }

      public bsz.a a(adn var1) {
         this.l = Optional.of(_snowman);
         return this;
      }

      public bsz a() {
         return new bsz(
            this.a.orElseThrow(() -> new IllegalStateException("Missing 'fog' color.")),
            this.b.orElseThrow(() -> new IllegalStateException("Missing 'water' color.")),
            this.c.orElseThrow(() -> new IllegalStateException("Missing 'water fog' color.")),
            this.d.orElseThrow(() -> new IllegalStateException("Missing 'sky' color.")),
            this.e,
            this.f,
            this.g,
            this.h,
            this.i,
            this.j,
            this.k,
            this.l
         );
      }
   }

   public static enum b implements afs {
      a("none") {
         @Override
         public int a(double var1, double var3, int var5) {
            return _snowman;
         }
      },
      b("dark_forest") {
         @Override
         public int a(double var1, double var3, int var5) {
            return (_snowman & 16711422) + 2634762 >> 1;
         }
      },
      c("swamp") {
         @Override
         public int a(double var1, double var3, int var5) {
            double _snowman = bsv.f.a(_snowman * 0.0225, _snowman * 0.0225, false);
            return _snowman < -0.1 ? 5011004 : 6975545;
         }
      };

      private final String e;
      public static final Codec<bsz.b> d = afs.a(bsz.b::values, bsz.b::a);
      private static final Map<String, bsz.b> f = Arrays.stream(values()).collect(Collectors.toMap(bsz.b::b, var0 -> (bsz.b)var0));

      public abstract int a(double var1, double var3, int var5);

      private b(String var3) {
         this.e = _snowman;
      }

      public String b() {
         return this.e;
      }

      @Override
      public String a() {
         return this.e;
      }

      public static bsz.b a(String var0) {
         return f.get(_snowman);
      }
   }
}
