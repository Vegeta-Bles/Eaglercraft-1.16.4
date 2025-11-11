import java.util.List;
import java.util.function.Predicate;

public class bki extends blx {
   private static final Predicate<aqa> a = aqd.g.and(aqa::aT);
   private final bhn.b b;

   public bki(bhn.b var1, blx.a var2) {
      super(_snowman);
      this.b = _snowman;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      dcl _snowmanx = a(_snowman, _snowman, brf.b.c);
      if (_snowmanx.c() == dcl.a.a) {
         return aov.c(_snowman);
      } else {
         dcn _snowmanxx = _snowman.f(1.0F);
         double _snowmanxxx = 5.0;
         List<aqa> _snowmanxxxx = _snowman.a(_snowman, _snowman.cc().b(_snowmanxx.a(5.0)).g(1.0), a);
         if (!_snowmanxxxx.isEmpty()) {
            dcn _snowmanxxxxx = _snowman.j(1.0F);

            for (aqa _snowmanxxxxxx : _snowmanxxxx) {
               dci _snowmanxxxxxxx = _snowmanxxxxxx.cc().g((double)_snowmanxxxxxx.bg());
               if (_snowmanxxxxxxx.d(_snowmanxxxxx)) {
                  return aov.c(_snowman);
               }
            }
         }

         if (_snowmanx.c() == dcl.a.b) {
            bhn _snowmanxxxxx = new bhn(_snowman, _snowmanx.e().b, _snowmanx.e().c, _snowmanx.e().d);
            _snowmanxxxxx.a(this.b);
            _snowmanxxxxx.p = _snowman.p;
            if (!_snowman.a_(_snowmanxxxxx, _snowmanxxxxx.cc().g(-0.1))) {
               return aov.d(_snowman);
            } else {
               if (!_snowman.v) {
                  _snowman.c(_snowmanxxxxx);
                  if (!_snowman.bC.d) {
                     _snowman.g(1);
                  }
               }

               _snowman.b(aea.c.b(this));
               return aov.a(_snowman, _snowman.s_());
            }
         } else {
            return aov.c(_snowman);
         }
      }
   }
}
