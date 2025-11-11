public class bvv extends bud {
   public static final cfg a = cex.az;
   public static final cey b = cex.p;
   protected static final ddh c = buo.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

   public bvv(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)).a(b, Boolean.valueOf(false)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return c;
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(a);
   }

   public static void d(ceh var0, brx var1, fx var2) {
      if (_snowman.k().b()) {
         int _snowman = _snowman.a(bsf.a, _snowman) - _snowman.c();
         float _snowmanx = _snowman.a(1.0F);
         boolean _snowmanxx = _snowman.c(b);
         if (_snowmanxx) {
            _snowman = 15 - _snowman;
         } else if (_snowman > 0) {
            float _snowmanxxx = _snowmanx < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            _snowmanx += (_snowmanxxx - _snowmanx) * 0.2F;
            _snowman = Math.round((float)_snowman * afm.b(_snowmanx));
         }

         _snowman = afm.a(_snowman, 0, 15);
         if (_snowman.c(a) != _snowman) {
            _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman)), 3);
         }
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.eK()) {
         if (_snowman.v) {
            return aou.a;
         } else {
            ceh _snowman = _snowman.a(b);
            _snowman.a(_snowman, _snowman, 4);
            d(_snowman, _snowman, _snowman);
            return aou.b;
         }
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   public ccj a(brc var1) {
      return new ccr();
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }
}
