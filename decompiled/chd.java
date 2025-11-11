import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;

public class chd {
   public static final vk a = new vk("overworld");
   public static final vk b = new vk("the_nether");
   public static final vk c = new vk("the_end");
   public static final Codec<chd> d = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.LONG
                  .optionalFieldOf("fixed_time")
                  .xmap(
                     var0x -> var0x.map(OptionalLong::of).orElseGet(OptionalLong::empty),
                     var0x -> var0x.isPresent() ? Optional.of(var0x.getAsLong()) : Optional.empty()
                  )
                  .forGetter(var0x -> var0x.o),
               Codec.BOOL.fieldOf("has_skylight").forGetter(chd::b),
               Codec.BOOL.fieldOf("has_ceiling").forGetter(chd::c),
               Codec.BOOL.fieldOf("ultrawarm").forGetter(chd::d),
               Codec.BOOL.fieldOf("natural").forGetter(chd::e),
               Codec.doubleRange(1.0E-5F, 3.0E7).fieldOf("coordinate_scale").forGetter(chd::f),
               Codec.BOOL.fieldOf("piglin_safe").forGetter(chd::g),
               Codec.BOOL.fieldOf("bed_works").forGetter(chd::h),
               Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(chd::i),
               Codec.BOOL.fieldOf("has_raids").forGetter(chd::j),
               Codec.intRange(0, 256).fieldOf("logical_height").forGetter(chd::k),
               vk.a.fieldOf("infiniburn").forGetter(var0x -> var0x.B),
               vk.a.fieldOf("effects").orElse(a).forGetter(var0x -> var0x.C),
               Codec.FLOAT.fieldOf("ambient_light").forGetter(var0x -> var0x.D)
            )
            .apply(var0, chd::new)
   );
   public static final float[] e = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   public static final vj<chd> f = vj.a(gm.K, new vk("overworld"));
   public static final vj<chd> g = vj.a(gm.K, new vk("the_nether"));
   public static final vj<chd> h = vj.a(gm.K, new vk("the_end"));
   protected static final chd i = new chd(OptionalLong.empty(), true, false, false, true, 1.0, false, false, true, false, true, 256, btf.a, aed.aE.a(), a, 0.0F);
   protected static final chd j = new chd(
      OptionalLong.of(18000L), false, true, true, false, 8.0, false, true, false, true, false, 128, bte.a, aed.aF.a(), b, 0.1F
   );
   protected static final chd k = new chd(
      OptionalLong.of(6000L), false, false, false, false, 1.0, true, false, false, false, true, 256, bte.a, aed.aG.a(), c, 0.0F
   );
   public static final vj<chd> l = vj.a(gm.K, new vk("overworld_caves"));
   protected static final chd m = new chd(OptionalLong.empty(), true, true, false, true, 1.0, false, false, true, false, true, 256, btf.a, aed.aE.a(), a, 0.0F);
   public static final Codec<Supplier<chd>> n = vf.a(gm.K, d);
   private final OptionalLong o;
   private final boolean p;
   private final boolean q;
   private final boolean r;
   private final boolean s;
   private final double t;
   private final boolean u;
   private final boolean v;
   private final boolean w;
   private final boolean x;
   private final boolean y;
   private final int z;
   private final bta A;
   private final vk B;
   private final vk C;
   private final float D;
   private final transient float[] E;

   protected chd(
      OptionalLong var1,
      boolean var2,
      boolean var3,
      boolean var4,
      boolean var5,
      double var6,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      int var12,
      vk var13,
      vk var14,
      float var15
   ) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false, _snowman, _snowman, _snowman, _snowman, _snowman, bte.a, _snowman, _snowman, _snowman);
   }

   protected chd(
      OptionalLong var1,
      boolean var2,
      boolean var3,
      boolean var4,
      boolean var5,
      double var6,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      boolean var12,
      int var13,
      bta var14,
      vk var15,
      vk var16,
      float var17
   ) {
      this.o = _snowman;
      this.p = _snowman;
      this.q = _snowman;
      this.r = _snowman;
      this.s = _snowman;
      this.t = _snowman;
      this.u = _snowman;
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      this.y = _snowman;
      this.z = _snowman;
      this.A = _snowman;
      this.B = _snowman;
      this.C = _snowman;
      this.D = _snowman;
      this.E = a(_snowman);
   }

   private static float[] a(float var0) {
      float[] _snowman = new float[16];

      for (int _snowmanx = 0; _snowmanx <= 15; _snowmanx++) {
         float _snowmanxx = (float)_snowmanx / 15.0F;
         float _snowmanxxx = _snowmanxx / (4.0F - 3.0F * _snowmanxx);
         _snowman[_snowmanx] = afm.g(_snowman, _snowmanxxx, 1.0F);
      }

      return _snowman;
   }

   @Deprecated
   public static DataResult<vj<brx>> a(Dynamic<?> var0) {
      Optional<Number> _snowman = _snowman.asNumber().result();
      if (_snowman.isPresent()) {
         int _snowmanx = _snowman.get().intValue();
         if (_snowmanx == -1) {
            return DataResult.success(brx.h);
         }

         if (_snowmanx == 0) {
            return DataResult.success(brx.g);
         }

         if (_snowmanx == 1) {
            return DataResult.success(brx.i);
         }
      }

      return brx.f.parse(_snowman);
   }

   public static gn.b a(gn.b var0) {
      gs<chd> _snowman = _snowman.b(gm.K);
      _snowman.a(f, i, Lifecycle.stable());
      _snowman.a(l, m, Lifecycle.stable());
      _snowman.a(g, j, Lifecycle.stable());
      _snowman.a(h, k, Lifecycle.stable());
      return _snowman;
   }

   private static cfy a(gm<bsv> var0, gm<chp> var1, long var2) {
      return new cho(new btk(_snowman, _snowman), _snowman, () -> _snowman.d(chp.f));
   }

   private static cfy b(gm<bsv> var0, gm<chp> var1, long var2) {
      return new cho(bth.b.a.a(_snowman, _snowman), _snowman, () -> _snowman.d(chp.e));
   }

   public static gi<che> a(gm<chd> var0, gm<bsv> var1, gm<chp> var2, long var3) {
      gi<che> _snowman = new gi<>(gm.M, Lifecycle.experimental());
      _snowman.a(che.c, new che(() -> _snowman.d(g), b(_snowman, _snowman, _snowman)), Lifecycle.stable());
      _snowman.a(che.d, new che(() -> _snowman.d(h), a(_snowman, _snowman, _snowman)), Lifecycle.stable());
      return _snowman;
   }

   public static double a(chd var0, chd var1) {
      double _snowman = _snowman.f();
      double _snowmanx = _snowman.f();
      return _snowman / _snowmanx;
   }

   @Deprecated
   public String a() {
      return this.a(k) ? "_end" : "";
   }

   public static File a(vj<brx> var0, File var1) {
      if (_snowman == brx.g) {
         return _snowman;
      } else if (_snowman == brx.i) {
         return new File(_snowman, "DIM1");
      } else {
         return _snowman == brx.h ? new File(_snowman, "DIM-1") : new File(_snowman, "dimensions/" + _snowman.a().b() + "/" + _snowman.a().a());
      }
   }

   public boolean b() {
      return this.p;
   }

   public boolean c() {
      return this.q;
   }

   public boolean d() {
      return this.r;
   }

   public boolean e() {
      return this.s;
   }

   public double f() {
      return this.t;
   }

   public boolean g() {
      return this.v;
   }

   public boolean h() {
      return this.w;
   }

   public boolean i() {
      return this.x;
   }

   public boolean j() {
      return this.y;
   }

   public int k() {
      return this.z;
   }

   public boolean l() {
      return this.u;
   }

   public bta m() {
      return this.A;
   }

   public boolean n() {
      return this.o.isPresent();
   }

   public float a(long var1) {
      double _snowman = afm.h((double)this.o.orElse(_snowman) / 24000.0 - 0.25);
      double _snowmanx = 0.5 - Math.cos(_snowman * Math.PI) / 2.0;
      return (float)(_snowman * 2.0 + _snowmanx) / 3.0F;
   }

   public int b(long var1) {
      return (int)(_snowman / 24000L % 8L + 8L) % 8;
   }

   public float a(int var1) {
      return this.E[_snowman];
   }

   public ael<buo> o() {
      ael<buo> _snowman = aed.a().a(this.B);
      return (ael<buo>)(_snowman != null ? _snowman : aed.aE);
   }

   public vk p() {
      return this.C;
   }

   public boolean a(chd var1) {
      return this == _snowman
         ? true
         : this.p == _snowman.p
            && this.q == _snowman.q
            && this.r == _snowman.r
            && this.s == _snowman.s
            && this.t == _snowman.t
            && this.u == _snowman.u
            && this.v == _snowman.v
            && this.w == _snowman.w
            && this.x == _snowman.x
            && this.y == _snowman.y
            && this.z == _snowman.z
            && Float.compare(_snowman.D, this.D) == 0
            && this.o.equals(_snowman.o)
            && this.A.equals(_snowman.A)
            && this.B.equals(_snowman.B)
            && this.C.equals(_snowman.C);
   }
}
