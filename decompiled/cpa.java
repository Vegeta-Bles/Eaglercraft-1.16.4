import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cpa extends cpb {
   public static final Codec<cpa> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, cpa::new));

   public cpa(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected cpc<?> a() {
      return cpc.a;
   }

   @Override
   public List<cnl.b> a(bsb var1, Random var2, int var3, fx var4, Set<fx> var5, cra var6, cmz var7) {
      a(_snowman, _snowman.c());

      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         a(_snowman, _snowman, _snowman.b(_snowman), _snowman, _snowman, _snowman);
      }

      return ImmutableList.of(new cnl.b(_snowman.b(_snowman), 0, false));
   }
}
