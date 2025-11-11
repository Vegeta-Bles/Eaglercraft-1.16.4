import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class btj extends bsy {
   public static final Codec<btj> e = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.LONG.fieldOf("seed").stable().forGetter(var0x -> var0x.h),
               Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", false, Lifecycle.stable()).forGetter(var0x -> var0x.i),
               Codec.BOOL.fieldOf("large_biomes").orElse(false).stable().forGetter(var0x -> var0x.j),
               vg.a(gm.ay).forGetter(var0x -> var0x.k)
            )
            .apply(var0, var0.stable(btj::new))
   );
   private final cvv f;
   private static final List<vj<bsv>> g = ImmutableList.of(
      btb.a,
      btb.b,
      btb.c,
      btb.d,
      btb.e,
      btb.f,
      btb.g,
      btb.h,
      btb.k,
      btb.l,
      btb.m,
      btb.n,
      new vj[]{
         btb.o,
         btb.p,
         btb.q,
         btb.r,
         btb.s,
         btb.t,
         btb.u,
         btb.v,
         btb.w,
         btb.x,
         btb.y,
         btb.z,
         btb.A,
         btb.B,
         btb.C,
         btb.D,
         btb.E,
         btb.F,
         btb.G,
         btb.H,
         btb.I,
         btb.J,
         btb.K,
         btb.L,
         btb.M,
         btb.N,
         btb.S,
         btb.T,
         btb.U,
         btb.V,
         btb.W,
         btb.X,
         btb.Y,
         btb.aa,
         btb.ab,
         btb.ac,
         btb.ad,
         btb.ae,
         btb.af,
         btb.ag,
         btb.ah,
         btb.ai,
         btb.aj,
         btb.ak,
         btb.al,
         btb.am,
         btb.an,
         btb.ao,
         btb.ap,
         btb.aq,
         btb.ar,
         btb.as,
         btb.at,
         btb.au
      }
   );
   private final long h;
   private final boolean i;
   private final boolean j;
   private final gm<bsv> k;

   public btj(long var1, boolean var3, boolean var4, gm<bsv> var5) {
      super(g.stream().map(var1x -> () -> _snowman.d((vj<bsv>)var1x)));
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.f = cvx.a(_snowman, _snowman, _snowman ? 6 : 4, 4);
   }

   @Override
   protected Codec<? extends bsy> a() {
      return e;
   }

   @Override
   public bsy a(long var1) {
      return new btj(_snowman, this.i, this.j, this.k);
   }

   @Override
   public bsv b(int var1, int var2, int var3) {
      return this.f.a(this.k, _snowman, _snowman);
   }
}
