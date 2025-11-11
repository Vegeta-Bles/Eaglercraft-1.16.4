import java.util.List;

public class byy extends buf {
   public static final cey d = cex.w;
   private final byy.a e;

   protected byy(byy.a var1, ceg.c var2) {
      super(_snowman);
      this.j(this.n.b().a(d, Boolean.valueOf(false)));
      this.e = _snowman;
   }

   @Override
   protected int g(ceh var1) {
      return _snowman.c(d) ? 15 : 0;
   }

   @Override
   protected ceh a(ceh var1, int var2) {
      return _snowman.a(d, Boolean.valueOf(_snowman > 0));
   }

   @Override
   protected void a(bry var1, fx var2) {
      if (this.as != cva.y && this.as != cva.z) {
         _snowman.a(null, _snowman, adq.oR, adr.e, 0.3F, 0.6F);
      } else {
         _snowman.a(null, _snowman, adq.ru, adr.e, 0.3F, 0.8F);
      }
   }

   @Override
   protected void b(bry var1, fx var2) {
      if (this.as != cva.y && this.as != cva.z) {
         _snowman.a(null, _snowman, adq.oQ, adr.e, 0.3F, 0.5F);
      } else {
         _snowman.a(null, _snowman, adq.rt, adr.e, 0.3F, 0.7F);
      }
   }

   @Override
   protected int b(brx var1, fx var2) {
      dci _snowman = c.a(_snowman);
      List<? extends aqa> _snowmanx;
      switch (this.e) {
         case a:
            _snowmanx = _snowman.a(null, _snowman);
            break;
         case b:
            _snowmanx = _snowman.a(aqm.class, _snowman);
            break;
         default:
            return 0;
      }

      if (!_snowmanx.isEmpty()) {
         for (aqa _snowman : _snowmanx) {
            if (!_snowman.bQ()) {
               return 15;
            }
         }
      }

      return 0;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(d);
   }

   public static enum a {
      a,
      b;

      private a() {
      }
   }
}
