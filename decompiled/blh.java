import java.util.List;

public class blh extends blx {
   public blh(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (!_snowmanxx.a(bup.bK) && !_snowmanxx.a(bup.z)) {
         return aou.d;
      } else {
         fx _snowmanxxx = _snowmanx.b();
         if (!_snowman.w(_snowmanxxx)) {
            return aou.d;
         } else {
            double _snowmanxxxx = (double)_snowmanxxx.u();
            double _snowmanxxxxx = (double)_snowmanxxx.v();
            double _snowmanxxxxxx = (double)_snowmanxxx.w();
            List<aqa> _snowmanxxxxxxx = _snowman.a(null, new dci(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxx + 1.0, _snowmanxxxxx + 2.0, _snowmanxxxxxx + 1.0));
            if (!_snowmanxxxxxxx.isEmpty()) {
               return aou.d;
            } else {
               if (_snowman instanceof aag) {
                  bbq _snowmanxxxxxxxx = new bbq(_snowman, _snowmanxxxx + 0.5, _snowmanxxxxx, _snowmanxxxxxx + 0.5);
                  _snowmanxxxxxxxx.a(false);
                  _snowman.c(_snowmanxxxxxxxx);
                  chg _snowmanxxxxxxxxx = ((aag)_snowman).D();
                  if (_snowmanxxxxxxxxx != null) {
                     _snowmanxxxxxxxxx.e();
                  }
               }

               _snowman.m().g(1);
               return aou.a(_snowman.v);
            }
         }
      }
   }

   @Override
   public boolean e(bmb var1) {
      return true;
   }
}
