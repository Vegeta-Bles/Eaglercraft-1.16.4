import java.util.EnumSet;

public class axf extends avv {
   private static final azg c = new azg().a(10.0).a().b().d().c();
   protected final aqu a;
   private final double d;
   private double e;
   private double f;
   private double g;
   private double h;
   private double i;
   protected bfw b;
   private int j;
   private boolean k;
   private final bon l;
   private final boolean m;

   public axf(aqu var1, double var2, bon var4, boolean var5) {
      this(_snowman, _snowman, _snowman, _snowman);
   }

   public axf(aqu var1, double var2, boolean var4, bon var5) {
      this.a = _snowman;
      this.d = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
      if (!(_snowman.x() instanceof ayi) && !(_snowman.x() instanceof ayh)) {
         throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
      }
   }

   @Override
   public boolean a() {
      if (this.j > 0) {
         this.j--;
         return false;
      } else {
         this.b = this.a.l.a(c, this.a);
         return this.b == null ? false : this.a(this.b.dD()) || this.a(this.b.dE());
      }
   }

   protected boolean a(bmb var1) {
      return this.l.a(_snowman);
   }

   @Override
   public boolean b() {
      if (this.g()) {
         if (this.a.h(this.b) < 36.0) {
            if (this.b.h(this.e, this.f, this.g) > 0.010000000000000002) {
               return false;
            }

            if (Math.abs((double)this.b.q - this.h) > 5.0 || Math.abs((double)this.b.p - this.i) > 5.0) {
               return false;
            }
         } else {
            this.e = this.b.cD();
            this.f = this.b.cE();
            this.g = this.b.cH();
         }

         this.h = (double)this.b.q;
         this.i = (double)this.b.p;
      }

      return this.a();
   }

   protected boolean g() {
      return this.m;
   }

   @Override
   public void c() {
      this.e = this.b.cD();
      this.f = this.b.cE();
      this.g = this.b.cH();
      this.k = true;
   }

   @Override
   public void d() {
      this.b = null;
      this.a.x().o();
      this.j = 100;
      this.k = false;
   }

   @Override
   public void e() {
      this.a.t().a(this.b, (float)(this.a.Q() + 20), (float)this.a.O());
      if (this.a.h(this.b) < 6.25) {
         this.a.x().o();
      } else {
         this.a.x().a(this.b, this.d);
      }
   }

   public boolean h() {
      return this.k;
   }
}
