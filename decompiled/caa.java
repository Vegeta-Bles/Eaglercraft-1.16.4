import java.util.Random;
import javax.annotation.Nullable;

public class caa extends buo {
   public static final cfg a = cex.aq;
   protected static final ddh[] b = new ddh[]{
      dde.a(),
      buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected caa(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(1)));
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      switch (_snowman) {
         case a:
            return _snowman.c(a) < 5;
         case b:
            return false;
         case c:
            return false;
         default:
            return false;
      }
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return b[_snowman.c(a)];
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return b[_snowman.c(a) - 1];
   }

   @Override
   public ddh e(ceh var1, brc var2, fx var3) {
      return b[_snowman.c(a)];
   }

   @Override
   public ddh a(ceh var1, brc var2, fx var3, dcs var4) {
      return b[_snowman.c(a)];
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      ceh _snowman = _snowman.d_(_snowman.c());
      if (_snowman.a(bup.cD) || _snowman.a(bup.gT) || _snowman.a(bup.go)) {
         return false;
      } else {
         return !_snowman.a(bup.ne) && !_snowman.a(bup.cM) ? buo.a(_snowman.k(_snowman, _snowman.c()), gc.b) || _snowman.b() == this && _snowman.c(a) == 8 : true;
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.a(bsf.b, _snowman) > 11) {
         c(_snowman, _snowman, _snowman);
         _snowman.a(_snowman, false);
      }
   }

   @Override
   public boolean a(ceh var1, bny var2) {
      int _snowman = _snowman.c(a);
      if (_snowman.m().b() != this.h() || _snowman >= 8) {
         return _snowman == 1;
      } else {
         return _snowman.c() ? _snowman.j() == gc.b : true;
      }
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = _snowman.p().d_(_snowman.a());
      if (_snowman.a(this)) {
         int _snowmanx = _snowman.c(a);
         return _snowman.a(a, Integer.valueOf(Math.min(8, _snowmanx + 1)));
      } else {
         return super.a(_snowman);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
