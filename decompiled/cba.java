import java.util.Random;

public class cba extends buo {
   protected static final ddh d = buo.a(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
   protected final hf e;

   protected cba(ceg.c var1, hf var2) {
      super(_snowman);
      this.e = _snowman;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return d;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman == gc.a && !this.a(_snowman, _snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return a(_snowman, _snowman.c(), gc.b);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      double _snowman = (double)_snowman.u() + 0.5;
      double _snowmanx = (double)_snowman.v() + 0.7;
      double _snowmanxx = (double)_snowman.w() + 0.5;
      _snowman.a(hh.S, _snowman, _snowmanx, _snowmanxx, 0.0, 0.0, 0.0);
      _snowman.a(this.e, _snowman, _snowmanx, _snowmanxx, 0.0, 0.0, 0.0);
   }
}
