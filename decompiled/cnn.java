import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cnn extends cnl {
   public static final Codec<cnn> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(Codec.intRange(0, 16).fieldOf("height").forGetter(var0x -> var0x.b)).apply(var0, cnn::new)
   );
   protected final int b;

   public cnn(afw var1, afw var2, int var3) {
      super(_snowman, _snowman);
      this.b = _snowman;
   }

   @Override
   protected cnm<?> a() {
      return cnm.g;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      int _snowman = _snowman.c() ? _snowman : 1 + _snowman.nextInt(2);

      for (int _snowmanx = _snowman; _snowmanx >= _snowman - _snowman; _snowmanx--) {
         int _snowmanxx = _snowman + _snowman.b() + 1 - _snowmanx;
         this.a(_snowman, _snowman, _snowman, _snowman.a(), _snowmanxx, _snowman, _snowmanx, _snowman.c(), _snowman);
      }
   }

   @Override
   public int a(Random var1, int var2, cmz var3) {
      return this.b;
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return _snowman + _snowman >= 7 ? true : _snowman * _snowman + _snowman * _snowman > _snowman * _snowman;
   }
}
