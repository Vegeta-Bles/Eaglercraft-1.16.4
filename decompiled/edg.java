import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class edg implements edh.a {
   private final djz a;
   private double b = Double.MIN_VALUE;
   private List<ddh> c = Collections.emptyList();

   public edg(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      double _snowman = (double)x.c();
      if (_snowman - this.b > 1.0E8) {
         this.b = _snowman;
         aqa _snowmanx = this.a.h.k().g();
         this.c = _snowmanx.l.d(_snowmanx, _snowmanx.cc().g(6.0), var0 -> true).collect(Collectors.toList());
      }

      dfq _snowmanx = _snowman.getBuffer(eao.t());

      for (ddh _snowmanxx : this.c) {
         eae.a(_snowman, _snowmanx, _snowmanxx, -_snowman, -_snowman, -_snowman, 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
