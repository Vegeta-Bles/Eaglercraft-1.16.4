import com.google.common.collect.ImmutableSet;
import java.util.Set;

public class bml extends bkv {
   private static final Set<buo> a = ImmutableSet.of(
      bup.fD,
      bup.H,
      bup.m,
      bup.aO,
      bup.bU,
      bup.bT,
      new buo[]{
         bup.aN,
         bup.bE,
         bup.F,
         bup.I,
         bup.cD,
         bup.bF,
         bup.G,
         bup.ar,
         bup.aq,
         bup.bJ,
         bup.cL,
         bup.gT,
         bup.kV,
         bup.ch,
         bup.cy,
         bup.at,
         bup.au,
         bup.av,
         bup.hH,
         bup.hI,
         bup.hG,
         bup.b,
         bup.c,
         bup.d,
         bup.e,
         bup.f,
         bup.g,
         bup.h,
         bup.hQ,
         bup.hR,
         bup.hS,
         bup.hU,
         bup.hV,
         bup.hW,
         bup.hX,
         bup.hY,
         bup.hZ,
         bup.ia,
         bup.ic,
         bup.if_,
         bup.ig,
         bup.ie,
         bup.id,
         bup.cB,
         bup.cq,
         bup.lr,
         bup.ls,
         bup.lt,
         bup.lu,
         bup.lv,
         bup.lw,
         bup.lx,
         bup.ly,
         bup.lz,
         bup.lA,
         bup.lB,
         bup.lC,
         bup.lD,
         bup.iP,
         bup.jf,
         bup.jb,
         bup.jc,
         bup.iZ,
         bup.iX,
         bup.jd,
         bup.iT,
         bup.iY,
         bup.iV,
         bup.iS,
         bup.iR,
         bup.iW,
         bup.ja,
         bup.je,
         bup.iQ,
         bup.iU,
         bup.aW,
         bup.aP,
         bup.aX
      }
   );

   protected bml(bnh var1, int var2, float var3, blx.a var4) {
      super((float)_snowman, _snowman, _snowman, a, _snowman);
   }

   @Override
   public boolean b(ceh var1) {
      int _snowman = this.g().d();
      if (_snowman.a(bup.bK) || _snowman.a(bup.ni) || _snowman.a(bup.ng) || _snowman.a(bup.nj) || _snowman.a(bup.nh)) {
         return _snowman >= 3;
      } else if (_snowman.a(bup.bU) || _snowman.a(bup.bT) || _snowman.a(bup.ej) || _snowman.a(bup.en) || _snowman.a(bup.bE) || _snowman.a(bup.F) || _snowman.a(bup.cy)) {
         return _snowman >= 2;
      } else if (!_snowman.a(bup.bF) && !_snowman.a(bup.G) && !_snowman.a(bup.ar) && !_snowman.a(bup.aq)) {
         cva _snowmanx = _snowman.c();
         return _snowmanx == cva.I || _snowmanx == cva.J || _snowmanx == cva.L || _snowman.a(bup.I);
      } else {
         return _snowman >= 1;
      }
   }

   @Override
   public float a(bmb var1, ceh var2) {
      cva _snowman = _snowman.c();
      return _snowman != cva.J && _snowman != cva.L && _snowman != cva.I ? super.a(_snowman, _snowman) : this.b;
   }
}
