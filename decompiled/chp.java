import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class chp {
   public static final Codec<chp> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               chv.a.fieldOf("structures").forGetter(chp::a),
               chr.a.fieldOf("noise").forGetter(chp::b),
               ceh.b.fieldOf("default_block").forGetter(chp::c),
               ceh.b.fieldOf("default_fluid").forGetter(chp::d),
               Codec.intRange(-20, 276).fieldOf("bedrock_roof_position").forGetter(chp::e),
               Codec.intRange(-20, 276).fieldOf("bedrock_floor_position").forGetter(chp::f),
               Codec.intRange(0, 255).fieldOf("sea_level").forGetter(chp::g),
               Codec.BOOL.fieldOf("disable_mob_generation").forGetter(chp::h)
            )
            .apply(var0, chp::new)
   );
   public static final Codec<Supplier<chp>> b = vf.a(gm.ar, a);
   private final chv i;
   private final chr j;
   private final ceh k;
   private final ceh l;
   private final int m;
   private final int n;
   private final int o;
   private final boolean p;
   public static final vj<chp> c = vj.a(gm.ar, new vk("overworld"));
   public static final vj<chp> d = vj.a(gm.ar, new vk("amplified"));
   public static final vj<chp> e = vj.a(gm.ar, new vk("nether"));
   public static final vj<chp> f = vj.a(gm.ar, new vk("end"));
   public static final vj<chp> g = vj.a(gm.ar, new vk("caves"));
   public static final vj<chp> h = vj.a(gm.ar, new vk("floating_islands"));
   private static final chp q = a(c, a(new chv(true), false, c.a()));

   private chp(chv var1, chr var2, ceh var3, ceh var4, int var5, int var6, int var7, boolean var8) {
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
      this.p = _snowman;
   }

   public chv a() {
      return this.i;
   }

   public chr b() {
      return this.j;
   }

   public ceh c() {
      return this.k;
   }

   public ceh d() {
      return this.l;
   }

   public int e() {
      return this.m;
   }

   public int f() {
      return this.n;
   }

   public int g() {
      return this.o;
   }

   @Deprecated
   protected boolean h() {
      return this.p;
   }

   public boolean a(vj<chp> var1) {
      return Objects.equals(this, hk.j.a(_snowman));
   }

   private static chp a(vj<chp> var0, chp var1) {
      hk.a(hk.j, _snowman.a(), _snowman);
      return _snowman;
   }

   public static chp i() {
      return q;
   }

   private static chp a(chv var0, ceh var1, ceh var2, vk var3, boolean var4, boolean var5) {
      return new chp(
         _snowman,
         new chr(128, new chq(2.0, 1.0, 80.0, 160.0), new chs(-3000, 64, -46), new chs(-30, 7, 1), 2, 1, 0.0, 0.0, true, false, _snowman, false),
         _snowman,
         _snowman,
         -10,
         -10,
         0,
         _snowman
      );
   }

   private static chp a(chv var0, ceh var1, ceh var2, vk var3) {
      Map<cla<?>, cmy> _snowman = Maps.newHashMap(chv.b);
      _snowman.put(cla.h, new cmy(25, 10, 34222645));
      return new chp(
         new chv(Optional.ofNullable(_snowman.b()), _snowman),
         new chr(128, new chq(1.0, 3.0, 80.0, 60.0), new chs(120, 3, 0), new chs(320, 4, -1), 1, 2, 0.0, 0.019921875, false, false, false, false),
         _snowman,
         _snowman,
         0,
         0,
         32,
         false
      );
   }

   private static chp a(chv var0, boolean var1, vk var2) {
      double _snowman = 0.9999999814507745;
      return new chp(
         _snowman,
         new chr(
            256,
            new chq(0.9999999814507745, 0.9999999814507745, 80.0, 160.0),
            new chs(-10, 3, 0),
            new chs(-30, 0, 0),
            1,
            2,
            1.0,
            -0.46875,
            true,
            true,
            false,
            _snowman
         ),
         bup.b.n(),
         bup.A.n(),
         -10,
         0,
         63,
         false
      );
   }

   static {
      a(d, a(new chv(true), true, d.a()));
      a(e, a(new chv(false), bup.cL.n(), bup.B.n(), e.a()));
      a(f, a(new chv(false), bup.ee.n(), bup.a.n(), f.a(), true, true));
      a(g, a(new chv(true), bup.b.n(), bup.A.n(), g.a()));
      a(h, a(new chv(true), bup.b.n(), bup.A.n(), h.a(), false, false));
   }
}
