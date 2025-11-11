import java.util.Random;

public class bwi extends bud {
   protected static final ddh a = buo.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

   protected bwi(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ccj a(brc var1) {
      return new cdl();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (_snowman instanceof aag && !_snowman.br() && !_snowman.bs() && _snowman.bO() && dde.c(dde.a(_snowman.cc().d((double)(-_snowman.u()), (double)(-_snowman.v()), (double)(-_snowman.w()))), _snowman.j(_snowman, _snowman), dcr.i)) {
         vj<brx> _snowman = _snowman.Y() == brx.i ? brx.g : brx.i;
         aag _snowmanx = ((aag)_snowman).l().a(_snowman);
         if (_snowmanx == null) {
            return;
         }

         _snowman.b(_snowmanx);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      double _snowman = (double)_snowman.u() + _snowman.nextDouble();
      double _snowmanx = (double)_snowman.v() + 0.8;
      double _snowmanxx = (double)_snowman.w() + _snowman.nextDouble();
      _snowman.a(hh.S, _snowman, _snowmanx, _snowmanxx, 0.0, 0.0, 0.0);
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return bmb.b;
   }

   @Override
   public boolean a(ceh var1, cuw var2) {
      return false;
   }
}
