import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class cpb {
   public static final Codec<cpb> c = gm.aY.dispatch(cpb::a, cpc::a);
   protected final int d;
   protected final int e;
   protected final int f;

   protected static <P extends cpb> P3<Mu<P>, Integer, Integer, Integer> a(Instance<P> var0) {
      return _snowman.group(
         Codec.intRange(0, 32).fieldOf("base_height").forGetter(var0x -> var0x.d),
         Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(var0x -> var0x.e),
         Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter(var0x -> var0x.f)
      );
   }

   public cpb(int var1, int var2, int var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   protected abstract cpc<?> a();

   public abstract List<cnl.b> a(bsb var1, Random var2, int var3, fx var4, Set<fx> var5, cra var6, cmz var7);

   public int a(Random var1) {
      return this.d + _snowman.nextInt(this.e + 1) + _snowman.nextInt(this.f + 1);
   }

   protected static void a(bse var0, fx var1, ceh var2, cra var3) {
      cld.b(_snowman, _snowman, _snowman);
      _snowman.c(new cra(_snowman, _snowman));
   }

   private static boolean a(bsc var0, fx var1) {
      return _snowman.a(_snowman, var0x -> {
         buo _snowman = var0x.b();
         return cjl.b(_snowman) && !var0x.a(bup.i) && !var0x.a(bup.dT);
      });
   }

   protected static void a(bsb var0, fx var1) {
      if (!a((bsc)_snowman, _snowman)) {
         cld.b(_snowman, _snowman, bup.j.n());
      }
   }

   protected static boolean a(bsb var0, Random var1, fx var2, Set<fx> var3, cra var4, cmz var5) {
      if (cld.e(_snowman, _snowman)) {
         a(_snowman, _snowman, _snowman.b.a(_snowman, _snowman), _snowman);
         _snowman.add(_snowman.h());
         return true;
      } else {
         return false;
      }
   }

   protected static void a(bsb var0, Random var1, fx.a var2, Set<fx> var3, cra var4, cmz var5) {
      if (cld.c(_snowman, _snowman)) {
         a(_snowman, _snowman, (fx)_snowman, _snowman, _snowman, _snowman);
      }
   }
}
