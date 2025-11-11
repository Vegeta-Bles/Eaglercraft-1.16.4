import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cng extends cnl {
   public static final Codec<cng> a = RecordCodecBuilder.create(var0 -> b(var0).apply(var0, cng::new));

   public cng(afw var1, afw var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected cnm<?> a() {
      return cnm.d;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      boolean _snowman = _snowman.c();
      fx _snowmanx = _snowman.a().b(_snowman);
      this.a(_snowman, _snowman, _snowman, _snowmanx, _snowman + _snowman.b(), _snowman, -1 - _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman, _snowmanx, _snowman - 1, _snowman, -_snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman, _snowmanx, _snowman + _snowman.b() - 1, _snowman, 0, _snowman, _snowman);
   }

   @Override
   public int a(Random var1, int var2, cmz var3) {
      return 0;
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return _snowman == 0 ? (_snowman > 1 || _snowman > 1) && _snowman != 0 && _snowman != 0 : _snowman == _snowman && _snowman == _snowman && _snowman > 0;
   }
}
