import java.util.EnumSet;

public class avj extends awj {
   private final bab g;

   public avj(bab var1, double var2, int var4) {
      super(_snowman, _snowman, _snowman, 6);
      this.g = _snowman;
      this.f = -2;
      this.a(EnumSet.of(avv.a.c, avv.a.a));
   }

   @Override
   public boolean a() {
      return this.g.eK() && !this.g.eO() && !this.g.eW() && super.a();
   }

   @Override
   public void c() {
      super.c();
      this.g.v(false);
   }

   @Override
   protected int a(aqu var1) {
      return 40;
   }

   @Override
   public void d() {
      super.d();
      this.g.x(false);
   }

   @Override
   public void e() {
      super.e();
      this.g.v(false);
      if (!this.l()) {
         this.g.x(false);
      } else if (!this.g.eW()) {
         this.g.x(true);
      }
   }

   @Override
   protected boolean a(brz var1, fx var2) {
      return _snowman.w(_snowman.b()) && _snowman.d_(_snowman).b().a(aed.L);
   }
}
