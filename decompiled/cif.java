import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class cif extends cia {
   public cif(Codec<cmk> var1) {
      super(_snowman, 256);
      this.j = ImmutableSet.of(
         bup.b,
         bup.c,
         bup.e,
         bup.g,
         bup.j,
         bup.k,
         new buo[]{
            bup.l,
            bup.i,
            bup.gR,
            bup.fF,
            bup.fG,
            bup.fH,
            bup.fI,
            bup.fJ,
            bup.fK,
            bup.fL,
            bup.fM,
            bup.fN,
            bup.fO,
            bup.fP,
            bup.fQ,
            bup.fR,
            bup.fS,
            bup.fT,
            bup.fU,
            bup.at,
            bup.hG,
            bup.dT,
            bup.cC,
            bup.C,
            bup.E,
            bup.A,
            bup.B,
            bup.bK,
            bup.a,
            bup.lb,
            bup.gT
         }
      );
   }

   @Override
   protected boolean a(cfw var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      return false;
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
      return a(this, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected static boolean a(
      cig<?> var0, cfw var1, BitSet var2, Random var3, fx.a var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12
   ) {
      if (_snowman >= _snowman) {
         return false;
      } else {
         int _snowman = _snowman | _snowman << 4 | _snowman << 8;
         if (_snowman.get(_snowman)) {
            return false;
         } else {
            _snowman.set(_snowman);
            _snowman.d(_snowman, _snowman, _snowman);
            ceh _snowmanx = _snowman.d_(_snowman);
            if (!_snowman.a(_snowmanx)) {
               return false;
            } else if (_snowman == 10) {
               float _snowmanxx = _snowman.nextFloat();
               if ((double)_snowmanxx < 0.25) {
                  _snowman.a(_snowman, bup.iJ.n(), false);
                  _snowman.n().a(_snowman, bup.iJ, 0);
               } else {
                  _snowman.a(_snowman, bup.bK.n(), false);
               }

               return true;
            } else if (_snowman < 10) {
               _snowman.a(_snowman, bup.B.n(), false);
               return false;
            } else {
               boolean _snowmanxx = false;

               for (gc _snowmanxxx : gc.c.a) {
                  int _snowmanxxxx = _snowman + _snowmanxxx.i();
                  int _snowmanxxxxx = _snowman + _snowmanxxx.k();
                  if (_snowmanxxxx >> 4 != _snowman || _snowmanxxxxx >> 4 != _snowman || _snowman.d_(_snowman.d(_snowmanxxxx, _snowman, _snowmanxxxxx)).g()) {
                     _snowman.a(_snowman, h.g(), false);
                     _snowman.o().a(_snowman, h.a(), 0);
                     _snowmanxx = true;
                     break;
                  }
               }

               _snowman.d(_snowman, _snowman, _snowman);
               if (!_snowmanxx) {
                  _snowman.a(_snowman, h.g(), false);
                  return true;
               } else {
                  return true;
               }
            }
         }
      }
   }
}
