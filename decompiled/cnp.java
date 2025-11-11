import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cnp extends cnl {
   public static final Codec<cnp> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(afw.a(0, 16, 8).fieldOf("height").forGetter(var0x -> var0x.b)).apply(var0, cnp::new)
   );
   private final afw b;

   public cnp(afw var1, afw var2, afw var3) {
      super(_snowman, _snowman);
      this.b = _snowman;
   }

   @Override
   protected cnm<?> a() {
      return cnm.c;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      int _snowman = 0;

      for (int _snowmanx = _snowman; _snowmanx >= _snowman - _snowman; _snowmanx--) {
         this.a(_snowman, _snowman, _snowman, _snowman.a(), _snowman, _snowman, _snowmanx, _snowman.c(), _snowman);
         if (_snowman >= 1 && _snowmanx == _snowman - _snowman + 1) {
            _snowman--;
         } else if (_snowman < _snowman + _snowman.b()) {
            _snowman++;
         }
      }
   }

   @Override
   public int a(Random var1, int var2) {
      return super.a(_snowman, _snowman) + _snowman.nextInt(_snowman + 1);
   }

   @Override
   public int a(Random var1, int var2, cmz var3) {
      return this.b.a(_snowman);
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return _snowman == _snowman && _snowman == _snowman && _snowman > 0;
   }
}
