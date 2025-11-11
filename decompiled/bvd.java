import javax.annotation.Nullable;

public class bvd extends bzl implements bzu {
   public static final cey a = cex.C;
   protected static final ddh b = buo.a(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
   protected static final ddh c = buo.a(6.5, 6.5, 0.0, 9.5, 9.5, 16.0);
   protected static final ddh d = buo.a(0.0, 6.5, 6.5, 16.0, 9.5, 9.5);

   public bvd(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Boolean.valueOf(false)).a(e, gc.a.b));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch ((gc.a)_snowman.c(e)) {
         case a:
         default:
            return d;
         case c:
            return c;
         case b:
            return b;
      }
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      cux _snowman = _snowman.p().b(_snowman.a());
      boolean _snowmanx = _snowman.a() == cuy.c;
      return super.a(_snowman).a(a, Boolean.valueOf(_snowmanx));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(a)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a).a(e);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(a) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
