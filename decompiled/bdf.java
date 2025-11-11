import java.util.List;

public class bdf extends bdm {
   public static final float b = aqe.r.j() / aqe.F.j();

   public bdf(aqe<? extends bdf> var1, brx var2) {
      super(_snowman, _snowman);
      this.es();
      if (this.c != null) {
         this.c.a(400);
      }
   }

   public static ark.a m() {
      return bdm.eM().a(arl.d, 0.3F).a(arl.f, 8.0).a(arl.a, 80.0);
   }

   @Override
   public int eK() {
      return 60;
   }

   @Override
   protected adp I() {
      return this.aH() ? adq.dd : adq.de;
   }

   @Override
   protected adp e(apk var1) {
      return this.aH() ? adq.dj : adq.dk;
   }

   @Override
   protected adp dq() {
      return this.aH() ? adq.dg : adq.dh;
   }

   @Override
   protected adp eL() {
      return adq.di;
   }

   @Override
   protected void N() {
      super.N();
      int _snowman = 1200;
      if ((this.K + this.Y()) % 1200 == 0) {
         aps _snowmanx = apw.d;
         List<aah> _snowmanxx = ((aag)this.l).a(var1x -> this.h(var1x) < 2500.0 && var1x.d.d());
         int _snowmanxxx = 2;
         int _snowmanxxxx = 6000;
         int _snowmanxxxxx = 1200;

         for (aah _snowmanxxxxxx : _snowmanxx) {
            if (!_snowmanxxxxxx.a(_snowmanx) || _snowmanxxxxxx.b(_snowmanx).c() < 2 || _snowmanxxxxxx.b(_snowmanx).b() < 1200) {
               _snowmanxxxxxx.b.a(new pq(pq.k, this.aA() ? 0.0F : 1.0F));
               _snowmanxxxxxx.c(new apu(_snowmanx, 6000, 2));
            }
         }
      }

      if (!this.ez()) {
         this.a(this.cB(), 16);
      }
   }
}
