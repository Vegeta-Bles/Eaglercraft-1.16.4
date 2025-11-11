public class bux extends buo {
   public static final cfg a = cex.al;
   protected static final ddh[] b = new ddh[]{
      buo.a(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      buo.a(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      buo.a(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      buo.a(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      buo.a(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      buo.a(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),
      buo.a(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)
   };

   protected bux(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b[_snowman.c(a)];
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         bmb _snowman = _snowman.b(_snowman);
         if (this.a(_snowman, _snowman, _snowman, _snowman).a()) {
            return aou.a;
         }

         if (_snowman.a()) {
            return aou.b;
         }
      }

      return this.a(_snowman, _snowman, _snowman, _snowman);
   }

   private aou a(bry var1, fx var2, ceh var3, bfw var4) {
      if (!_snowman.q(false)) {
         return aou.c;
      } else {
         _snowman.a(aea.T);
         _snowman.eI().a(2, 0.1F);
         int _snowman = _snowman.c(a);
         if (_snowman < 6) {
            _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman + 1)), 3);
         } else {
            _snowman.a(_snowman, false);
         }

         return aou.a;
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman == gc.a && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return _snowman.d_(_snowman.c()).c().b();
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return (7 - _snowman.c(a)) * 2;
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
