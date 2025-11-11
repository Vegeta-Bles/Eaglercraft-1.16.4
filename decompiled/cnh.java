import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.Set;

public class cnh extends cnl {
   public static final Codec<cnh> a = RecordCodecBuilder.create(var0 -> a(var0).apply(var0, cnh::new));
   protected final int b;

   protected static <P extends cnh> P3<Mu<P>, afw, afw, Integer> a(Instance<P> var0) {
      return b(_snowman).and(Codec.intRange(0, 16).fieldOf("height").forGetter(var0x -> var0x.b));
   }

   public cnh(afw var1, afw var2, int var3) {
      super(_snowman, _snowman);
      this.b = _snowman;
   }

   @Override
   protected cnm<?> a() {
      return cnm.a;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      for (int _snowman = _snowman; _snowman >= _snowman - _snowman; _snowman--) {
         int _snowmanx = Math.max(_snowman + _snowman.b() - 1 - _snowman / 2, 0);
         this.a(_snowman, _snowman, _snowman, _snowman.a(), _snowmanx, _snowman, _snowman, _snowman.c(), _snowman);
      }
   }

   @Override
   public int a(Random var1, int var2, cmz var3) {
      return this.b;
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return _snowman == _snowman && _snowman == _snowman && (_snowman.nextInt(2) == 0 || _snowman == 0);
   }
}
