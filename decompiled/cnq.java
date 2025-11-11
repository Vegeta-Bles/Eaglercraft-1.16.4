import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cnq extends cnl {
   public static final Codec<cnq> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(afw.a(0, 16, 8).fieldOf("trunk_height").forGetter(var0x -> var0x.b)).apply(var0, cnq::new)
   );
   private final afw b;

   public cnq(afw var1, afw var2, afw var3) {
      super(_snowman, _snowman);
      this.b = _snowman;
   }

   @Override
   protected cnm<?> a() {
      return cnm.b;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      fx _snowman = _snowman.a();
      int _snowmanx = _snowman.nextInt(2);
      int _snowmanxx = 1;
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = _snowman; _snowmanxxxx >= -_snowman; _snowmanxxxx--) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowman, _snowmanxxxx, _snowman.c(), _snowman);
         if (_snowmanx >= _snowmanxx) {
            _snowmanx = _snowmanxxx;
            _snowmanxxx = 1;
            _snowmanxx = Math.min(_snowmanxx + 1, _snowman + _snowman.b());
         } else {
            _snowmanx++;
         }
      }
   }

   @Override
   public int a(Random var1, int var2, cmz var3) {
      return Math.max(4, _snowman - this.b.a(_snowman));
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return _snowman == _snowman && _snowman == _snowman && _snowman > 0;
   }
}
