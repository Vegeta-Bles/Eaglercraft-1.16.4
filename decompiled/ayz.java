import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;

public class ayz extends azb<bfj> {
   public ayz() {
      super(40);
   }

   protected void a(aag var1, bfj var2) {
      vj<brx> _snowman = _snowman.Y();
      fx _snowmanx = _snowman.cB();
      List<gf> _snowmanxx = Lists.newArrayList();
      int _snowmanxxx = 4;

      for (int _snowmanxxxx = -4; _snowmanxxxx <= 4; _snowmanxxxx++) {
         for (int _snowmanxxxxx = -2; _snowmanxxxxx <= 2; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = -4; _snowmanxxxxxx <= 4; _snowmanxxxxxx++) {
               fx _snowmanxxxxxxx = _snowmanx.b(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
               if (_snowman.eX().b().d().contains(_snowman.d_(_snowmanxxxxxxx).b())) {
                  _snowmanxx.add(gf.a(_snowman, _snowmanxxxxxxx));
               }
            }
         }
      }

      arf<?> _snowmanxxxx = _snowman.cJ();
      if (!_snowmanxx.isEmpty()) {
         _snowmanxxxx.a(ayd.f, _snowmanxx);
      } else {
         _snowmanxxxx.b(ayd.f);
      }
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.f);
   }
}
