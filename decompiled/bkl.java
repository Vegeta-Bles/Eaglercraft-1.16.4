import java.util.List;

public class bkl extends blx {
   public bkl(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      List<apz> _snowman = _snowman.a(apz.class, _snowman.cc().g(2.0), var0 -> var0 != null && var0.aX() && var0.t() instanceof bbr);
      bmb _snowmanx = _snowman.b(_snowman);
      if (!_snowman.isEmpty()) {
         apz _snowmanxx = _snowman.get(0);
         _snowmanxx.a(_snowmanxx.g() - 0.5F);
         _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.bc, adr.g, 1.0F, 1.0F);
         return aov.a(this.a(_snowmanx, _snowman, new bmb(bmd.qi)), _snowman.s_());
      } else {
         dcl _snowmanxx = a(_snowman, _snowman, brf.b.b);
         if (_snowmanxx.c() == dcl.a.a) {
            return aov.c(_snowmanx);
         } else {
            if (_snowmanxx.c() == dcl.a.b) {
               fx _snowmanxxx = ((dcj)_snowmanxx).a();
               if (!_snowman.a(_snowman, _snowmanxxx)) {
                  return aov.c(_snowmanx);
               }

               if (_snowman.b(_snowmanxxx).a(aef.b)) {
                  _snowman.a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.bb, adr.g, 1.0F, 1.0F);
                  return aov.a(this.a(_snowmanx, _snowman, bnv.a(new bmb(bmd.nv), bnw.b)), _snowman.s_());
               }
            }

            return aov.c(_snowmanx);
         }
      }
   }

   protected bmb a(bmb var1, bfw var2, bmb var3) {
      _snowman.b(aea.c.b(this));
      return bmc.a(_snowman, _snowman, _snowman);
   }
}
