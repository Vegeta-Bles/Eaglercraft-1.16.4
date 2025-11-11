import java.util.Random;
import javax.annotation.Nullable;

public class bvh extends bxm implements buq {
   public static final cfg a = cex.af;
   protected static final ddh[] b = new ddh[]{
      buo.a(11.0, 7.0, 6.0, 15.0, 12.0, 10.0), buo.a(9.0, 5.0, 5.0, 15.0, 12.0, 11.0), buo.a(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)
   };
   protected static final ddh[] c = new ddh[]{
      buo.a(1.0, 7.0, 6.0, 5.0, 12.0, 10.0), buo.a(1.0, 5.0, 5.0, 7.0, 12.0, 11.0), buo.a(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)
   };
   protected static final ddh[] d = new ddh[]{
      buo.a(6.0, 7.0, 1.0, 10.0, 12.0, 5.0), buo.a(5.0, 5.0, 1.0, 11.0, 12.0, 7.0), buo.a(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)
   };
   protected static final ddh[] e = new ddh[]{
      buo.a(6.0, 7.0, 11.0, 10.0, 12.0, 15.0), buo.a(5.0, 5.0, 9.0, 11.0, 12.0, 15.0), buo.a(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)
   };

   public bvh(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(aq, gc.c).a(a, Integer.valueOf(0)));
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(a) < 2;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.t.nextInt(5) == 0) {
         int _snowman = _snowman.c(a);
         if (_snowman < 2) {
            _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman + 1)), 2);
         }
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      buo _snowman = _snowman.d_(_snowman.a(_snowman.c(aq))).b();
      return _snowman.a(aed.x);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      int _snowman = _snowman.c(a);
      switch ((gc)_snowman.c(aq)) {
         case d:
            return e[_snowman];
         case c:
         default:
            return d[_snowman];
         case e:
            return c[_snowman];
         case f:
            return b[_snowman];
      }
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = this.n();
      brz _snowmanx = _snowman.p();
      fx _snowmanxx = _snowman.a();

      for (gc _snowmanxxx : _snowman.e()) {
         if (_snowmanxxx.n().d()) {
            _snowman = _snowman.a(aq, _snowmanxxx);
            if (_snowman.a(_snowmanx, _snowmanxx)) {
               return _snowman;
            }
         }
      }

      return null;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman == _snowman.c(aq) && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return _snowman.c(a) < 2;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman.c(a) + 1)), 2);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(aq, a);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
