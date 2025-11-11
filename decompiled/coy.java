import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class coy extends cpb {
   public static final Codec<coy> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, coy::new));

   public coy(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected cpc<?> a() {
      return cpc.c;
   }

   @Override
   public List<cnl.b> a(bsb var1, Random var2, int var3, fx var4, Set<fx> var5, cra var6, cmz var7) {
      fx _snowman = _snowman.c();
      a(_snowman, _snowman);
      a(_snowman, _snowman.g());
      a(_snowman, _snowman.e());
      a(_snowman, _snowman.e().g());
      fx.a _snowmanx = new fx.a();

      for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
         a(_snowman, _snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman, 0, _snowmanxx, 0);
         if (_snowmanxx < _snowman - 1) {
            a(_snowman, _snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman, 1, _snowmanxx, 0);
            a(_snowman, _snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman, 1, _snowmanxx, 1);
            a(_snowman, _snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman, 0, _snowmanxx, 1);
         }
      }

      return ImmutableList.of(new cnl.b(_snowman.b(_snowman), 0, true));
   }

   private static void a(bsb var0, Random var1, fx.a var2, Set<fx> var3, cra var4, cmz var5, fx var6, int var7, int var8, int var9) {
      _snowman.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
