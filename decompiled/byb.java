import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class byb extends buo implements but {
   public static final cfg a = cex.av;
   protected final cuv b;
   private final List<cux> d;
   public static final ddh c = buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

   protected byb(cuv var1, ceg.c var2) {
      super(_snowman);
      this.b = _snowman;
      this.d = Lists.newArrayList();
      this.d.add(_snowman.a(false));

      for (int _snowman = 1; _snowman < 8; _snowman++) {
         this.d.add(_snowman.a(8 - _snowman, false));
      }

      this.d.add(_snowman.a(8, true));
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return _snowman.a(c, _snowman, true) && _snowman.c(a) == 0 && _snowman.a(_snowman.b(_snowman.b()), this.b) ? c : dde.a();
   }

   @Override
   public boolean a_(ceh var1) {
      return _snowman.m().f();
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      _snowman.m().b(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean b(ceh var1, brc var2, fx var3) {
      return false;
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return !this.b.a(aef.c);
   }

   @Override
   public cux d(ceh var1) {
      int _snowman = _snowman.c(a);
      return this.d.get(Math.min(_snowman, 8));
   }

   @Override
   public boolean a(ceh var1, ceh var2, gc var3) {
      return _snowman.m().a().a(this.b);
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.a;
   }

   @Override
   public List<bmb> a(ceh var1, cyv.a var2) {
      return Collections.emptyList();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return dde.a();
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (this.a(_snowman, _snowman, _snowman)) {
         _snowman.I().a(_snowman, _snowman.m().a(), this.b.a(_snowman));
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.m().b() || _snowman.m().b()) {
         _snowman.I().a(_snowman, _snowman.m().a(), this.b.a(_snowman));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (this.a(_snowman, _snowman, _snowman)) {
         _snowman.I().a(_snowman, _snowman.m().a(), this.b.a(_snowman));
      }
   }

   private boolean a(brx var1, fx var2, ceh var3) {
      if (this.b.a(aef.c)) {
         boolean _snowman = _snowman.d_(_snowman.c()).a(bup.cN);

         for (gc _snowmanx : gc.values()) {
            if (_snowmanx != gc.a) {
               fx _snowmanxx = _snowman.a(_snowmanx);
               if (_snowman.b(_snowmanxx).a(aef.b)) {
                  buo _snowmanxxx = _snowman.b(_snowman).b() ? bup.bK : bup.m;
                  _snowman.a(_snowman, _snowmanxxx.n());
                  this.a(_snowman, _snowman);
                  return false;
               }

               if (_snowman && _snowman.d_(_snowmanxx).a(bup.kV)) {
                  _snowman.a(_snowman, bup.cO.n());
                  this.a(_snowman, _snowman);
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void a(bry var1, fx var2) {
      _snowman.c(1501, _snowman, 0);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public cuw b(bry var1, fx var2, ceh var3) {
      if (_snowman.c(a) == 0) {
         _snowman.a(_snowman, bup.a.n(), 11);
         return this.b;
      } else {
         return cuy.a;
      }
   }
}
