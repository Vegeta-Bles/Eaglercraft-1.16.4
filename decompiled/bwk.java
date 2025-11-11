import java.util.Random;

public class bwk extends bvz {
   protected static final ddh b = buo.a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   protected static final ddh c = buo.a(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
   protected static final ddh d = buo.a(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

   protected bwk(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.b));
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(a, _snowman.b(_snowman.c(a)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch (_snowman.c(a).n()) {
         case a:
         default:
            return d;
         case c:
            return c;
         case b:
            return b;
      }
   }

   @Override
   public ceh a(bny var1) {
      gc _snowman = _snowman.j();
      ceh _snowmanx = _snowman.p().d_(_snowman.a().a(_snowman.f()));
      return _snowmanx.a(this) && _snowmanx.c(a) == _snowman ? this.n().a(a, _snowman.f()) : this.n().a(a, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      gc _snowman = _snowman.c(a);
      double _snowmanx = (double)_snowman.u() + 0.55 - (double)(_snowman.nextFloat() * 0.1F);
      double _snowmanxx = (double)_snowman.v() + 0.55 - (double)(_snowman.nextFloat() * 0.1F);
      double _snowmanxxx = (double)_snowman.w() + 0.55 - (double)(_snowman.nextFloat() * 0.1F);
      double _snowmanxxxx = (double)(0.4F - (_snowman.nextFloat() + _snowman.nextFloat()) * 0.4F);
      if (_snowman.nextInt(5) == 0) {
         _snowman.a(
            hh.t,
            _snowmanx + (double)_snowman.i() * _snowmanxxxx,
            _snowmanxx + (double)_snowman.j() * _snowmanxxxx,
            _snowmanxxx + (double)_snowman.k() * _snowmanxxxx,
            _snowman.nextGaussian() * 0.005,
            _snowman.nextGaussian() * 0.005,
            _snowman.nextGaussian() * 0.005
         );
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.a;
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
