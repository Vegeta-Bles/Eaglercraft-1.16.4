import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cnj extends cnl {
   public static final Codec<cnj> a = RecordCodecBuilder.create(var0 -> b(var0).apply(var0, cnj::new));

   public cnj(afw var1, afw var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected cnm<?> a() {
      return cnm.i;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      fx _snowman = _snowman.a().b(_snowman);
      boolean _snowmanx = _snowman.c();
      if (_snowmanx) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman + 2, _snowman, -1, _snowmanx, _snowman);
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman + 3, _snowman, 0, _snowmanx, _snowman);
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman + 2, _snowman, 1, _snowmanx, _snowman);
         if (_snowman.nextBoolean()) {
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, 2, _snowmanx, _snowman);
         }
      } else {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman + 2, _snowman, -1, _snowmanx, _snowman);
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman + 1, _snowman, 0, _snowmanx, _snowman);
      }
   }

   @Override
   public int a(Random var1, int var2, cmz var3) {
      return 4;
   }

   @Override
   protected boolean b(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return _snowman != 0 || !_snowman || _snowman != -_snowman && _snowman < _snowman || _snowman != -_snowman && _snowman < _snowman ? super.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman) : true;
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      if (_snowman == -1 && !_snowman) {
         return _snowman == _snowman && _snowman == _snowman;
      } else {
         return _snowman == 1 ? _snowman + _snowman > _snowman * 2 - 2 : false;
      }
   }
}
