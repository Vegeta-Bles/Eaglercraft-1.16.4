import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class chr {
   public static final Codec<chr> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 256).fieldOf("height").forGetter(chr::a),
               chq.a.fieldOf("sampling").forGetter(chr::b),
               chs.a.fieldOf("top_slide").forGetter(chr::c),
               chs.a.fieldOf("bottom_slide").forGetter(chr::d),
               Codec.intRange(1, 4).fieldOf("size_horizontal").forGetter(chr::e),
               Codec.intRange(1, 4).fieldOf("size_vertical").forGetter(chr::f),
               Codec.DOUBLE.fieldOf("density_factor").forGetter(chr::g),
               Codec.DOUBLE.fieldOf("density_offset").forGetter(chr::h),
               Codec.BOOL.fieldOf("simplex_surface_noise").forGetter(chr::i),
               Codec.BOOL.optionalFieldOf("random_density_offset", false, Lifecycle.experimental()).forGetter(chr::j),
               Codec.BOOL.optionalFieldOf("island_noise_override", false, Lifecycle.experimental()).forGetter(chr::k),
               Codec.BOOL.optionalFieldOf("amplified", false, Lifecycle.experimental()).forGetter(chr::l)
            )
            .apply(var0, chr::new)
   );
   private final int b;
   private final chq c;
   private final chs d;
   private final chs e;
   private final int f;
   private final int g;
   private final double h;
   private final double i;
   private final boolean j;
   private final boolean k;
   private final boolean l;
   private final boolean m;

   public chr(int var1, chq var2, chs var3, chs var4, int var5, int var6, double var7, double var9, boolean var11, boolean var12, boolean var13, boolean var14) {
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

   public chq b() {
      return this.c;
   }

   public chs c() {
      return this.d;
   }

   public chs d() {
      return this.e;
   }

   public int e() {
      return this.f;
   }

   public int f() {
      return this.g;
   }

   public double g() {
      return this.h;
   }

   public double h() {
      return this.i;
   }

   @Deprecated
   public boolean i() {
      return this.j;
   }

   @Deprecated
   public boolean j() {
      return this.k;
   }

   @Deprecated
   public boolean k() {
      return this.l;
   }

   @Deprecated
   public boolean l() {
      return this.m;
   }
}
