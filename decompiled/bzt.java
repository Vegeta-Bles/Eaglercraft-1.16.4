public abstract class bzt extends bud implements bzu {
   public static final cey a = cex.C;
   protected static final ddh b = buo.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   private final cfq c;

   protected bzt(ceg.c var1, cfq var2) {
      super(_snowman);
      this.c = _snowman;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(a)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b;
   }

   @Override
   public boolean ai_() {
      return true;
   }

   @Override
   public ccj a(brc var1) {
      return new cdf();
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      bmb _snowman = _snowman.b(_snowman);
      boolean _snowmanx = _snowman.b() instanceof bky && _snowman.bC.e;
      if (_snowman.v) {
         return _snowmanx ? aou.a : aou.b;
      } else {
         ccj _snowmanxx = _snowman.c(_snowman);
         if (_snowmanxx instanceof cdf) {
            cdf _snowmanxxx = (cdf)_snowmanxx;
            if (_snowmanx) {
               boolean _snowmanxxxx = _snowmanxxx.a(((bky)_snowman.b()).d());
               if (_snowmanxxxx && !_snowman.b_()) {
                  _snowman.g(1);
               }
            }

            return _snowmanxxx.b(_snowman) ? aou.a : aou.c;
         } else {
            return aou.c;
         }
      }
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(a) ? cuy.c.a(false) : super.d(_snowman);
   }

   public cfq c() {
      return this.c;
   }
}
