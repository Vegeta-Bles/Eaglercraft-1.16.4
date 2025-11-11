import java.util.Random;

public class cau extends buu implements buq {
   public static final cfg a = cex.ag;
   private static final ddh b = buo.a(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
   private static final ddh c = buo.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public cau(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(bmd.rm);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      if (_snowman.c(a) == 0) {
         return b;
      } else {
         return _snowman.c(a) < 3 ? c : super.b(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.c(a) < 3;
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      int _snowman = _snowman.c(a);
      if (_snowman < 3 && _snowman.nextInt(5) == 0 && _snowman.b(_snowman.b(), 0) >= 9) {
         _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman + 1)), 2);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (_snowman instanceof aqm && _snowman.X() != aqe.C && _snowman.X() != aqe.e) {
         _snowman.a(_snowman, new dcn(0.8F, 0.75, 0.8F));
         if (!_snowman.v && _snowman.c(a) > 0 && (_snowman.D != _snowman.cD() || _snowman.F != _snowman.cH())) {
            double _snowman = Math.abs(_snowman.cD() - _snowman.D);
            double _snowmanx = Math.abs(_snowman.cH() - _snowman.F);
            if (_snowman >= 0.003F || _snowmanx >= 0.003F) {
               _snowman.a(apk.u, 1.0F);
            }
         }
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      int _snowman = _snowman.c(a);
      boolean _snowmanx = _snowman == 3;
      if (!_snowmanx && _snowman.b(_snowman).b() == bmd.mK) {
         return aou.c;
      } else if (_snowman > 1) {
         int _snowmanxx = 1 + _snowman.t.nextInt(2);
         a(_snowman, _snowman, new bmb(bmd.rm, _snowmanxx + (_snowmanx ? 1 : 0)));
         _snowman.a(null, _snowman, adq.oZ, adr.e, 1.0F, 0.8F + _snowman.t.nextFloat() * 0.4F);
         _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(1)), 2);
         return aou.a(_snowman.v);
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return _snowman.c(a) < 3;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      int _snowman = Math.min(3, _snowman.c(a) + 1);
      _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman)), 2);
   }
}
