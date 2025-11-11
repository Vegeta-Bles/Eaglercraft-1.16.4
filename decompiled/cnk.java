import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cnk extends cnh {
   public static final Codec<cnk> c = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, cnk::new));

   public cnk(afw var1, afw var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected cnm<?> a() {
      return cnm.f;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      for (int _snowman = _snowman; _snowman >= _snowman - _snowman; _snowman--) {
         int _snowmanx = _snowman + (_snowman != _snowman && _snowman != _snowman - _snowman ? 1 : 0);
         this.a(_snowman, _snowman, _snowman, _snowman.a(), _snowmanx, _snowman, _snowman, _snowman.c(), _snowman);
      }
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return afm.k((float)_snowman + 0.5F) + afm.k((float)_snowman + 0.5F) > (float)(_snowman * _snowman);
   }
}
