import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class cmn implements cma {
   public static final Codec<cmn> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               cnt.a.fieldOf("state_provider").forGetter(var0x -> var0x.b),
               cll.a.fieldOf("block_placer").forGetter(var0x -> var0x.c),
               ceh.b.listOf().fieldOf("whitelist").forGetter(var0x -> var0x.d.stream().map(buo::n).collect(Collectors.toList())),
               ceh.b.listOf().fieldOf("blacklist").forGetter(var0x -> ImmutableList.copyOf(var0x.e)),
               Codec.INT.fieldOf("tries").orElse(128).forGetter(var0x -> var0x.f),
               Codec.INT.fieldOf("xspread").orElse(7).forGetter(var0x -> var0x.g),
               Codec.INT.fieldOf("yspread").orElse(3).forGetter(var0x -> var0x.h),
               Codec.INT.fieldOf("zspread").orElse(7).forGetter(var0x -> var0x.i),
               Codec.BOOL.fieldOf("can_replace").orElse(false).forGetter(var0x -> var0x.j),
               Codec.BOOL.fieldOf("project").orElse(true).forGetter(var0x -> var0x.l),
               Codec.BOOL.fieldOf("need_water").orElse(false).forGetter(var0x -> var0x.m)
            )
            .apply(var0, cmn::new)
   );
   public final cnt b;
   public final cll c;
   public final Set<buo> d;
   public final Set<ceh> e;
   public final int f;
   public final int g;
   public final int h;
   public final int i;
   public final boolean j;
   public final boolean l;
   public final boolean m;

   private cmn(cnt var1, cll var2, List<ceh> var3, List<ceh> var4, int var5, int var6, int var7, int var8, boolean var9, boolean var10, boolean var11) {
      this(_snowman, _snowman, _snowman.stream().map(ceg.a::b).collect(Collectors.toSet()), ImmutableSet.copyOf(_snowman), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private cmn(cnt var1, cll var2, Set<buo> var3, Set<ceh> var4, int var5, int var6, int var7, int var8, boolean var9, boolean var10, boolean var11) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.l = _snowman;
      this.m = _snowman;
   }

   public static class a {
      private final cnt a;
      private final cll b;
      private Set<buo> c = ImmutableSet.of();
      private Set<ceh> d = ImmutableSet.of();
      private int e = 64;
      private int f = 7;
      private int g = 3;
      private int h = 7;
      private boolean i;
      private boolean j = true;
      private boolean k = false;

      public a(cnt var1, cll var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public cmn.a a(Set<buo> var1) {
         this.c = _snowman;
         return this;
      }

      public cmn.a b(Set<ceh> var1) {
         this.d = _snowman;
         return this;
      }

      public cmn.a a(int var1) {
         this.e = _snowman;
         return this;
      }

      public cmn.a b(int var1) {
         this.f = _snowman;
         return this;
      }

      public cmn.a c(int var1) {
         this.g = _snowman;
         return this;
      }

      public cmn.a d(int var1) {
         this.h = _snowman;
         return this;
      }

      public cmn.a a() {
         this.i = true;
         return this;
      }

      public cmn.a b() {
         this.j = false;
         return this;
      }

      public cmn.a c() {
         this.k = true;
         return this;
      }

      public cmn d() {
         return new cmn(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k);
      }
   }
}
