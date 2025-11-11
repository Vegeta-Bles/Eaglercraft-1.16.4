import java.util.Random;

public class crf extends crq {
   private boolean e;
   private boolean f;
   private boolean g;
   private boolean h;
   private static final crf.a i = new crf.a();

   public crf(Random var1, int var2, int var3) {
      super(clb.G, _snowman, _snowman, 64, _snowman, 12, 10, 15);
   }

   public crf(csw var1, md var2) {
      super(clb.G, _snowman);
      this.e = _snowman.q("placedMainChest");
      this.f = _snowman.q("placedHiddenChest");
      this.g = _snowman.q("placedTrap1");
      this.h = _snowman.q("placedTrap2");
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      _snowman.a("placedMainChest", this.e);
      _snowman.a("placedHiddenChest", this.f);
      _snowman.a("placedTrap1", this.g);
      _snowman.a("placedTrap2", this.h);
   }

   @Override
   public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
      if (!this.a(_snowman, _snowman, 0)) {
         return false;
      } else {
         this.a(_snowman, _snowman, 0, -4, 0, this.a - 1, 0, this.c - 1, false, _snowman, i);
         this.a(_snowman, _snowman, 2, 1, 2, 9, 2, 2, false, _snowman, i);
         this.a(_snowman, _snowman, 2, 1, 12, 9, 2, 12, false, _snowman, i);
         this.a(_snowman, _snowman, 2, 1, 3, 2, 2, 11, false, _snowman, i);
         this.a(_snowman, _snowman, 9, 1, 3, 9, 2, 11, false, _snowman, i);
         this.a(_snowman, _snowman, 1, 3, 1, 10, 6, 1, false, _snowman, i);
         this.a(_snowman, _snowman, 1, 3, 13, 10, 6, 13, false, _snowman, i);
         this.a(_snowman, _snowman, 1, 3, 2, 1, 6, 12, false, _snowman, i);
         this.a(_snowman, _snowman, 10, 3, 2, 10, 6, 12, false, _snowman, i);
         this.a(_snowman, _snowman, 2, 3, 2, 9, 3, 12, false, _snowman, i);
         this.a(_snowman, _snowman, 2, 6, 2, 9, 6, 12, false, _snowman, i);
         this.a(_snowman, _snowman, 3, 7, 3, 8, 7, 11, false, _snowman, i);
         this.a(_snowman, _snowman, 4, 8, 4, 7, 8, 10, false, _snowman, i);
         this.b(_snowman, _snowman, 3, 1, 3, 8, 2, 11);
         this.b(_snowman, _snowman, 4, 3, 6, 7, 3, 9);
         this.b(_snowman, _snowman, 2, 4, 2, 9, 5, 12);
         this.b(_snowman, _snowman, 4, 6, 5, 7, 6, 9);
         this.b(_snowman, _snowman, 5, 7, 6, 6, 7, 8);
         this.b(_snowman, _snowman, 5, 1, 2, 6, 2, 2);
         this.b(_snowman, _snowman, 5, 2, 12, 6, 2, 12);
         this.b(_snowman, _snowman, 5, 5, 1, 6, 5, 1);
         this.b(_snowman, _snowman, 5, 5, 13, 6, 5, 13);
         this.a(_snowman, bup.a.n(), 1, 5, 5, _snowman);
         this.a(_snowman, bup.a.n(), 10, 5, 5, _snowman);
         this.a(_snowman, bup.a.n(), 1, 5, 9, _snowman);
         this.a(_snowman, bup.a.n(), 10, 5, 9, _snowman);

         for (int _snowman = 0; _snowman <= 14; _snowman += 14) {
            this.a(_snowman, _snowman, 2, 4, _snowman, 2, 5, _snowman, false, _snowman, i);
            this.a(_snowman, _snowman, 4, 4, _snowman, 4, 5, _snowman, false, _snowman, i);
            this.a(_snowman, _snowman, 7, 4, _snowman, 7, 5, _snowman, false, _snowman, i);
            this.a(_snowman, _snowman, 9, 4, _snowman, 9, 5, _snowman, false, _snowman, i);
         }

         this.a(_snowman, _snowman, 5, 6, 0, 6, 6, 0, false, _snowman, i);

         for (int _snowman = 0; _snowman <= 11; _snowman += 11) {
            for (int _snowmanx = 2; _snowmanx <= 12; _snowmanx += 2) {
               this.a(_snowman, _snowman, _snowman, 4, _snowmanx, _snowman, 5, _snowmanx, false, _snowman, i);
            }

            this.a(_snowman, _snowman, _snowman, 6, 5, _snowman, 6, 5, false, _snowman, i);
            this.a(_snowman, _snowman, _snowman, 6, 9, _snowman, 6, 9, false, _snowman, i);
         }

         this.a(_snowman, _snowman, 2, 7, 2, 2, 9, 2, false, _snowman, i);
         this.a(_snowman, _snowman, 9, 7, 2, 9, 9, 2, false, _snowman, i);
         this.a(_snowman, _snowman, 2, 7, 12, 2, 9, 12, false, _snowman, i);
         this.a(_snowman, _snowman, 9, 7, 12, 9, 9, 12, false, _snowman, i);
         this.a(_snowman, _snowman, 4, 9, 4, 4, 9, 4, false, _snowman, i);
         this.a(_snowman, _snowman, 7, 9, 4, 7, 9, 4, false, _snowman, i);
         this.a(_snowman, _snowman, 4, 9, 10, 4, 9, 10, false, _snowman, i);
         this.a(_snowman, _snowman, 7, 9, 10, 7, 9, 10, false, _snowman, i);
         this.a(_snowman, _snowman, 5, 9, 7, 6, 9, 7, false, _snowman, i);
         ceh _snowman = bup.ci.n().a(cak.a, gc.f);
         ceh _snowmanx = bup.ci.n().a(cak.a, gc.e);
         ceh _snowmanxx = bup.ci.n().a(cak.a, gc.d);
         ceh _snowmanxxx = bup.ci.n().a(cak.a, gc.c);
         this.a(_snowman, _snowmanxxx, 5, 9, 6, _snowman);
         this.a(_snowman, _snowmanxxx, 6, 9, 6, _snowman);
         this.a(_snowman, _snowmanxx, 5, 9, 8, _snowman);
         this.a(_snowman, _snowmanxx, 6, 9, 8, _snowman);
         this.a(_snowman, _snowmanxxx, 4, 0, 0, _snowman);
         this.a(_snowman, _snowmanxxx, 5, 0, 0, _snowman);
         this.a(_snowman, _snowmanxxx, 6, 0, 0, _snowman);
         this.a(_snowman, _snowmanxxx, 7, 0, 0, _snowman);
         this.a(_snowman, _snowmanxxx, 4, 1, 8, _snowman);
         this.a(_snowman, _snowmanxxx, 4, 2, 9, _snowman);
         this.a(_snowman, _snowmanxxx, 4, 3, 10, _snowman);
         this.a(_snowman, _snowmanxxx, 7, 1, 8, _snowman);
         this.a(_snowman, _snowmanxxx, 7, 2, 9, _snowman);
         this.a(_snowman, _snowmanxxx, 7, 3, 10, _snowman);
         this.a(_snowman, _snowman, 4, 1, 9, 4, 1, 9, false, _snowman, i);
         this.a(_snowman, _snowman, 7, 1, 9, 7, 1, 9, false, _snowman, i);
         this.a(_snowman, _snowman, 4, 1, 10, 7, 2, 10, false, _snowman, i);
         this.a(_snowman, _snowman, 5, 4, 5, 6, 4, 5, false, _snowman, i);
         this.a(_snowman, _snowman, 4, 4, 5, _snowman);
         this.a(_snowman, _snowmanx, 7, 4, 5, _snowman);

         for (int _snowmanxxxx = 0; _snowmanxxxx < 4; _snowmanxxxx++) {
            this.a(_snowman, _snowmanxx, 5, 0 - _snowmanxxxx, 6 + _snowmanxxxx, _snowman);
            this.a(_snowman, _snowmanxx, 6, 0 - _snowmanxxxx, 6 + _snowmanxxxx, _snowman);
            this.b(_snowman, _snowman, 5, 0 - _snowmanxxxx, 7 + _snowmanxxxx, 6, 0 - _snowmanxxxx, 9 + _snowmanxxxx);
         }

         this.b(_snowman, _snowman, 1, -3, 12, 10, -1, 13);
         this.b(_snowman, _snowman, 1, -3, 1, 3, -1, 13);
         this.b(_snowman, _snowman, 1, -3, 1, 9, -1, 5);

         for (int _snowmanxxxx = 1; _snowmanxxxx <= 13; _snowmanxxxx += 2) {
            this.a(_snowman, _snowman, 1, -3, _snowmanxxxx, 1, -2, _snowmanxxxx, false, _snowman, i);
         }

         for (int _snowmanxxxx = 2; _snowmanxxxx <= 12; _snowmanxxxx += 2) {
            this.a(_snowman, _snowman, 1, -1, _snowmanxxxx, 3, -1, _snowmanxxxx, false, _snowman, i);
         }

         this.a(_snowman, _snowman, 2, -2, 1, 5, -2, 1, false, _snowman, i);
         this.a(_snowman, _snowman, 7, -2, 1, 9, -2, 1, false, _snowman, i);
         this.a(_snowman, _snowman, 6, -3, 1, 6, -3, 1, false, _snowman, i);
         this.a(_snowman, _snowman, 6, -1, 1, 6, -1, 1, false, _snowman, i);
         this.a(_snowman, bup.el.n().a(cbe.a, gc.f).a(cbe.c, Boolean.valueOf(true)), 1, -3, 8, _snowman);
         this.a(_snowman, bup.el.n().a(cbe.a, gc.e).a(cbe.c, Boolean.valueOf(true)), 4, -3, 8, _snowman);
         this.a(_snowman, bup.em.n().a(cbd.e, Boolean.valueOf(true)).a(cbd.g, Boolean.valueOf(true)).a(cbd.b, Boolean.valueOf(true)), 2, -3, 8, _snowman);
         this.a(_snowman, bup.em.n().a(cbd.e, Boolean.valueOf(true)).a(cbd.g, Boolean.valueOf(true)).a(cbd.b, Boolean.valueOf(true)), 3, -3, 8, _snowman);
         ceh _snowmanxxxx = bup.bS.n().a(bzd.a, cfl.b).a(bzd.c, cfl.b);
         this.a(_snowman, _snowmanxxxx, 5, -3, 7, _snowman);
         this.a(_snowman, _snowmanxxxx, 5, -3, 6, _snowman);
         this.a(_snowman, _snowmanxxxx, 5, -3, 5, _snowman);
         this.a(_snowman, _snowmanxxxx, 5, -3, 4, _snowman);
         this.a(_snowman, _snowmanxxxx, 5, -3, 3, _snowman);
         this.a(_snowman, _snowmanxxxx, 5, -3, 2, _snowman);
         this.a(_snowman, bup.bS.n().a(bzd.a, cfl.b).a(bzd.d, cfl.b), 5, -3, 1, _snowman);
         this.a(_snowman, bup.bS.n().a(bzd.b, cfl.b).a(bzd.d, cfl.b), 4, -3, 1, _snowman);
         this.a(_snowman, bup.bJ.n(), 3, -3, 1, _snowman);
         if (!this.g) {
            this.g = this.a(_snowman, _snowman, _snowman, 3, -2, 1, gc.c, cyq.B);
         }

         this.a(_snowman, bup.dP.n().a(cbi.d, Boolean.valueOf(true)), 3, -2, 2, _snowman);
         this.a(_snowman, bup.el.n().a(cbe.a, gc.c).a(cbe.c, Boolean.valueOf(true)), 7, -3, 1, _snowman);
         this.a(_snowman, bup.el.n().a(cbe.a, gc.d).a(cbe.c, Boolean.valueOf(true)), 7, -3, 5, _snowman);
         this.a(_snowman, bup.em.n().a(cbd.d, Boolean.valueOf(true)).a(cbd.f, Boolean.valueOf(true)).a(cbd.b, Boolean.valueOf(true)), 7, -3, 2, _snowman);
         this.a(_snowman, bup.em.n().a(cbd.d, Boolean.valueOf(true)).a(cbd.f, Boolean.valueOf(true)).a(cbd.b, Boolean.valueOf(true)), 7, -3, 3, _snowman);
         this.a(_snowman, bup.em.n().a(cbd.d, Boolean.valueOf(true)).a(cbd.f, Boolean.valueOf(true)).a(cbd.b, Boolean.valueOf(true)), 7, -3, 4, _snowman);
         this.a(_snowman, bup.bS.n().a(bzd.b, cfl.b).a(bzd.d, cfl.b), 8, -3, 6, _snowman);
         this.a(_snowman, bup.bS.n().a(bzd.d, cfl.b).a(bzd.c, cfl.b), 9, -3, 6, _snowman);
         this.a(_snowman, bup.bS.n().a(bzd.a, cfl.b).a(bzd.c, cfl.a), 9, -3, 5, _snowman);
         this.a(_snowman, bup.bJ.n(), 9, -3, 4, _snowman);
         this.a(_snowman, _snowmanxxxx, 9, -2, 4, _snowman);
         if (!this.h) {
            this.h = this.a(_snowman, _snowman, _snowman, 9, -2, 3, gc.e, cyq.B);
         }

         this.a(_snowman, bup.dP.n().a(cbi.c, Boolean.valueOf(true)), 8, -1, 3, _snowman);
         this.a(_snowman, bup.dP.n().a(cbi.c, Boolean.valueOf(true)), 8, -2, 3, _snowman);
         if (!this.e) {
            this.e = this.a(_snowman, _snowman, _snowman, 8, -3, 3, cyq.A);
         }

         this.a(_snowman, bup.bJ.n(), 9, -3, 2, _snowman);
         this.a(_snowman, bup.bJ.n(), 8, -3, 1, _snowman);
         this.a(_snowman, bup.bJ.n(), 4, -3, 5, _snowman);
         this.a(_snowman, bup.bJ.n(), 5, -2, 5, _snowman);
         this.a(_snowman, bup.bJ.n(), 5, -1, 5, _snowman);
         this.a(_snowman, bup.bJ.n(), 6, -3, 5, _snowman);
         this.a(_snowman, bup.bJ.n(), 7, -2, 5, _snowman);
         this.a(_snowman, bup.bJ.n(), 7, -1, 5, _snowman);
         this.a(_snowman, bup.bJ.n(), 8, -3, 5, _snowman);
         this.a(_snowman, _snowman, 9, -1, 1, 9, -1, 5, false, _snowman, i);
         this.b(_snowman, _snowman, 8, -3, 8, 10, -1, 10);
         this.a(_snowman, bup.dx.n(), 8, -2, 11, _snowman);
         this.a(_snowman, bup.dx.n(), 9, -2, 11, _snowman);
         this.a(_snowman, bup.dx.n(), 10, -2, 11, _snowman);
         ceh _snowmanxxxxx = bup.cp.n().a(bya.aq, gc.c).a(bya.u, cet.b);
         this.a(_snowman, _snowmanxxxxx, 8, -2, 12, _snowman);
         this.a(_snowman, _snowmanxxxxx, 9, -2, 12, _snowman);
         this.a(_snowman, _snowmanxxxxx, 10, -2, 12, _snowman);
         this.a(_snowman, _snowman, 8, -3, 8, 8, -3, 10, false, _snowman, i);
         this.a(_snowman, _snowman, 10, -3, 8, 10, -3, 10, false, _snowman, i);
         this.a(_snowman, bup.bJ.n(), 10, -2, 9, _snowman);
         this.a(_snowman, _snowmanxxxx, 8, -2, 9, _snowman);
         this.a(_snowman, _snowmanxxxx, 8, -2, 10, _snowman);
         this.a(_snowman, bup.bS.n().a(bzd.a, cfl.b).a(bzd.c, cfl.b).a(bzd.b, cfl.b).a(bzd.d, cfl.b), 10, -1, 9, _snowman);
         this.a(_snowman, bup.aP.n().a(cea.a, gc.b), 9, -2, 8, _snowman);
         this.a(_snowman, bup.aP.n().a(cea.a, gc.e), 10, -2, 8, _snowman);
         this.a(_snowman, bup.aP.n().a(cea.a, gc.e), 10, -1, 8, _snowman);
         this.a(_snowman, bup.cX.n().a(bzi.aq, gc.c), 10, -2, 10, _snowman);
         if (!this.f) {
            this.f = this.a(_snowman, _snowman, _snowman, 9, -3, 10, cyq.A);
         }

         return true;
      }
   }

   static class a extends cru.a {
      private a() {
      }

      @Override
      public void a(Random var1, int var2, int var3, int var4, boolean var5) {
         if (_snowman.nextFloat() < 0.4F) {
            this.a = bup.m.n();
         } else {
            this.a = bup.bJ.n();
         }
      }
   }
}
