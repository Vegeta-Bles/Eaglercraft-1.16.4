import com.google.common.collect.Lists;
import java.util.List;

public class bsj {
   private final List<bsj.a> a = Lists.newArrayList();

   public bsj() {
   }

   public void a(fx var1, double var2) {
      if (_snowman != 0.0) {
         this.a.add(new bsj.a(_snowman, _snowman));
      }
   }

   public double b(fx var1, double var2) {
      if (_snowman == 0.0) {
         return 0.0;
      } else {
         double _snowman = 0.0;

         for (bsj.a _snowmanx : this.a) {
            _snowman += _snowmanx.a(_snowman);
         }

         return _snowman * _snowman;
      }
   }

   static class a {
      private final fx a;
      private final double b;

      public a(fx var1, double var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public double a(fx var1) {
         double _snowman = this.a.j(_snowman);
         return _snowman == 0.0 ? Double.POSITIVE_INFINITY : this.b / Math.sqrt(_snowman);
      }
   }
}
