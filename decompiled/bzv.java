public class bzv extends btq {
   public static final cfg a = cex.aD;
   protected static final ddh b = buo.a(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

   protected bzv(bzv.a var1, ceg.c var2) {
      super(_snowman, _snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public ddh d(ceh var1, brc var2, fx var3) {
      return dde.a();
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, Integer.valueOf(afm.c((double)(_snowman.h() * 16.0F / 360.0F) + 0.5) & 15));
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, Integer.valueOf(_snowman.a(_snowman.c(a), 16)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(a, Integer.valueOf(_snowman.a(_snowman.c(a), 16)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   public interface a {
   }

   public static enum b implements bzv.a {
      a,
      b,
      c,
      d,
      e,
      f;

      private b() {
      }
   }
}
