import javax.annotation.Nullable;

public class bub extends buo implements bzu {
   public static final cey b = cex.C;
   private static final ddh a = buo.a(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);

   protected bub(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(b, Boolean.valueOf(true)));
   }

   protected void a(ceh var1, bry var2, fx var3) {
      if (!c(_snowman, _snowman, _snowman)) {
         _snowman.J().a(_snowman, this, 60 + _snowman.u_().nextInt(40));
      }
   }

   protected static boolean c(ceh var0, brc var1, fx var2) {
      if (_snowman.c(b)) {
         return true;
      } else {
         for (gc _snowman : gc.values()) {
            if (_snowman.b(_snowman.a(_snowman)).a(aef.b)) {
               return true;
            }
         }

         return false;
      }
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      cux _snowman = _snowman.p().b(_snowman.a());
      return this.n().a(b, Boolean.valueOf(_snowman.a(aef.b) && _snowman.e() == 8));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(b)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return _snowman == gc.a && !this.a(_snowman, (brz)_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.c();
      return _snowman.d_(_snowman).d(_snowman, _snowman, gc.b);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(b) ? cuy.c.a(false) : super.d(_snowman);
   }
}
