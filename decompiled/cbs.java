public class cbs extends buf {
   public static final cfg d = cex.az;
   private final int e;

   protected cbs(int var1, ceg.c var2) {
      super(_snowman);
      this.j(this.n.b().a(d, Integer.valueOf(0)));
      this.e = _snowman;
   }

   @Override
   protected int b(brx var1, fx var2) {
      int _snowman = Math.min(_snowman.a(aqa.class, c.a(_snowman)).size(), this.e);
      if (_snowman > 0) {
         float _snowmanx = (float)Math.min(this.e, _snowman) / (float)this.e;
         return afm.f(_snowmanx * 15.0F);
      } else {
         return 0;
      }
   }

   @Override
   protected void a(bry var1, fx var2) {
      _snowman.a(null, _snowman, adq.hG, adr.e, 0.3F, 0.90000004F);
   }

   @Override
   protected void b(bry var1, fx var2) {
      _snowman.a(null, _snowman, adq.hF, adr.e, 0.3F, 0.75F);
   }

   @Override
   protected int g(ceh var1) {
      return _snowman.c(d);
   }

   @Override
   protected ceh a(ceh var1, int var2) {
      return _snowman.a(d, Integer.valueOf(_snowman));
   }

   @Override
   protected int c() {
      return 10;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(d);
   }
}
