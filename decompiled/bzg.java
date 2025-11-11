import java.util.Random;
import javax.annotation.Nullable;

public class bzg extends bzf {
   public static final cfb b = bxm.aq;
   public static final cey c = bzf.a;

   protected bzg(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(b, gc.c).a(c, Boolean.valueOf(true)));
   }

   @Override
   public String i() {
      return this.h().a();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return cbn.h(_snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return bup.bM.a(_snowman, _snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return bup.bM.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = bup.bM.a(_snowman);
      return _snowman == null ? null : this.n().a(b, _snowman.c(b));
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(c)) {
         gc _snowman = _snowman.c(b).f();
         double _snowmanx = 0.27;
         double _snowmanxx = (double)_snowman.u() + 0.5 + (_snowman.nextDouble() - 0.5) * 0.2 + 0.27 * (double)_snowman.i();
         double _snowmanxxx = (double)_snowman.v() + 0.7 + (_snowman.nextDouble() - 0.5) * 0.2 + 0.22;
         double _snowmanxxxx = (double)_snowman.w() + 0.5 + (_snowman.nextDouble() - 0.5) * 0.2 + 0.27 * (double)_snowman.k();
         _snowman.a(this.e, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected boolean a(brx var1, fx var2, ceh var3) {
      gc _snowman = _snowman.c(b).f();
      return _snowman.a(_snowman.a(_snowman), _snowman);
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(c) && _snowman.c(b) != _snowman ? 15 : 0;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return bup.bM.a(_snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return bup.bM.a(_snowman, _snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b, c);
   }
}
