import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class cmz implements cma {
   public static final Codec<cmz> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               cnt.a.fieldOf("trunk_provider").forGetter(var0x -> var0x.b),
               cnt.a.fieldOf("leaves_provider").forGetter(var0x -> var0x.c),
               cnl.d.fieldOf("foliage_placer").forGetter(var0x -> var0x.f),
               cpb.c.fieldOf("trunk_placer").forGetter(var0x -> var0x.g),
               cnb.a.fieldOf("minimum_size").forGetter(var0x -> var0x.h),
               cor.c.listOf().fieldOf("decorators").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("max_water_depth").orElse(0).forGetter(var0x -> var0x.i),
               Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter(var0x -> var0x.j),
               chn.a.g.fieldOf("heightmap").forGetter(var0x -> var0x.l)
            )
            .apply(var0, cmz::new)
   );
   public final cnt b;
   public final cnt c;
   public final List<cor> d;
   public transient boolean e;
   public final cnl f;
   public final cpb g;
   public final cnb h;
   public final int i;
   public final boolean j;
   public final chn.a l;

   protected cmz(cnt var1, cnt var2, cnl var3, cpb var4, cnb var5, List<cor> var6, int var7, boolean var8, chn.a var9) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.f = _snowman;
      this.h = _snowman;
      this.g = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.l = _snowman;
   }

   public void b() {
      this.e = true;
   }

   public cmz a(List<cor> var1) {
      return new cmz(this.b, this.c, this.f, this.g, this.h, _snowman, this.i, this.j, this.l);
   }

   public static class a {
      public final cnt a;
      public final cnt b;
      private final cnl c;
      private final cpb d;
      private final cnb e;
      private List<cor> f = ImmutableList.of();
      private int g;
      private boolean h;
      private chn.a i = chn.a.d;

      public a(cnt var1, cnt var2, cnl var3, cpb var4, cnb var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      public cmz.a a(List<cor> var1) {
         this.f = _snowman;
         return this;
      }

      public cmz.a a(int var1) {
         this.g = _snowman;
         return this;
      }

      public cmz.a a() {
         this.h = true;
         return this;
      }

      public cmz.a a(chn.a var1) {
         this.i = _snowman;
         return this;
      }

      public cmz b() {
         return new cmz(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
      }
   }
}
