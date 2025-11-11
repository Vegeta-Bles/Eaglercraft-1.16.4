import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cno extends cnl {
   public static final Codec<cno> a = RecordCodecBuilder.create(
      var0 -> b(var0).and(afw.a(0, 16, 8).fieldOf("crown_height").forGetter(var0x -> var0x.b)).apply(var0, cno::new)
   );
   private final afw b;

   public cno(afw var1, afw var2, afw var3) {
      super(_snowman, _snowman);
      this.b = _snowman;
   }

   @Override
   protected cnm<?> a() {
      return cnm.h;
   }

   @Override
   protected void a(bsb var1, Random var2, cmz var3, int var4, cnl.b var5, int var6, int var7, Set<fx> var8, int var9, cra var10) {
      fx _snowman = _snowman.a();
      int _snowmanx = 0;

      for (int _snowmanxx = _snowman.v() - _snowman + _snowman; _snowmanxx <= _snowman.v() + _snowman; _snowmanxx++) {
         int _snowmanxxx = _snowman.v() - _snowmanxx;
         int _snowmanxxxx = _snowman + _snowman.b() + afm.d((float)_snowmanxxx / (float)_snowman * 3.5F);
         int _snowmanxxxxx;
         if (_snowmanxxx > 0 && _snowmanxxxx == _snowmanx && (_snowmanxx & 1) == 0) {
            _snowmanxxxxx = _snowmanxxxx + 1;
         } else {
            _snowmanxxxxx = _snowmanxxxx;
         }

         this.a(_snowman, _snowman, _snowman, new fx(_snowman.u(), _snowmanxx, _snowman.w()), _snowmanxxxxx, _snowman, 0, _snowman.c(), _snowman);
         _snowmanx = _snowmanxxxx;
      }
   }

   @Override
   public int a(Random var1, int var2, cmz var3) {
      return this.b.a(_snowman);
   }

   @Override
   protected boolean a(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return _snowman + _snowman >= 7 ? true : _snowman * _snowman + _snowman * _snowman > _snowman * _snowman;
   }
}
