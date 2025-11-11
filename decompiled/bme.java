import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bme extends blx {
   private static final Logger a = LogManager.getLogger();

   public bme(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      md _snowmanx = _snowman.o();
      if (!_snowman.bC.d) {
         _snowman.a(_snowman, bmb.b);
      }

      if (_snowmanx != null && _snowmanx.c("Recipes", 9)) {
         if (!_snowman.v) {
            mj _snowmanxx = _snowmanx.d("Recipes", 8);
            List<boq<?>> _snowmanxxx = Lists.newArrayList();
            bor _snowmanxxxx = _snowman.l().aF();

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxx.size(); _snowmanxxxxx++) {
               String _snowmanxxxxxx = _snowmanxx.j(_snowmanxxxxx);
               Optional<? extends boq<?>> _snowmanxxxxxxx = _snowmanxxxx.a(new vk(_snowmanxxxxxx));
               if (!_snowmanxxxxxxx.isPresent()) {
                  a.error("Invalid recipe: {}", _snowmanxxxxxx);
                  return aov.d(_snowman);
               }

               _snowmanxxx.add((boq<?>)_snowmanxxxxxxx.get());
            }

            _snowman.a(_snowmanxxx);
            _snowman.b(aea.c.b(this));
         }

         return aov.a(_snowman, _snowman.s_());
      } else {
         a.error("Tag not valid: {}", _snowmanx);
         return aov.d(_snowman);
      }
   }
}
