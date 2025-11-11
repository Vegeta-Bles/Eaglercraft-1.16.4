import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class cic extends cia {
   public cic(Codec<cmk> var1) {
      super(_snowman, 128);
      this.j = ImmutableSet.of(
         bup.b, bup.c, bup.e, bup.g, bup.j, bup.k, new buo[]{bup.l, bup.i, bup.cL, bup.cM, bup.cN, bup.mu, bup.ml, bup.iK, bup.mn, bup.cO, bup.np}
      );
      this.k = ImmutableSet.of(cuy.e, cuy.c);
   }

   @Override
   protected int a() {
      return 10;
   }

   @Override
   protected float a(Random var1) {
      return (_snowman.nextFloat() * 2.0F + _snowman.nextFloat()) * 2.0F;
   }

   @Override
   protected double b() {
      return 5.0;
   }

   @Override
   protected int b(Random var1) {
      return _snowman.nextInt(this.l);
   }

   @Override
   protected boolean a(
      cfw var1,
      Function<fx, bsv> var2,
      BitSet var3,
      Random var4,
      fx.a var5,
      fx.a var6,
      fx.a var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      MutableBoolean var16
   ) {
      int _snowman = _snowman | _snowman << 4 | _snowman << 8;
      if (_snowman.get(_snowman)) {
         return false;
      } else {
         _snowman.set(_snowman);
         _snowman.d(_snowman, _snowman, _snowman);
         if (this.a(_snowman.d_(_snowman))) {
            ceh _snowmanx;
            if (_snowman <= 31) {
               _snowmanx = i.g();
            } else {
               _snowmanx = g;
            }

            _snowman.a(_snowman, _snowmanx, false);
            return true;
         } else {
            return false;
         }
      }
   }
}
