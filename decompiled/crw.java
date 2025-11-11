import java.util.Random;

public class crw extends crq {
   private boolean e;
   private boolean f;

   public crw(Random var1, int var2, int var3) {
      super(clb.K, _snowman, _snowman, 64, _snowman, 7, 7, 9);
   }

   public crw(csw var1, md var2) {
      super(clb.K, _snowman);
      this.e = _snowman.q("Witch");
      this.f = _snowman.q("Cat");
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      _snowman.a("Witch", this.e);
      _snowman.a("Cat", this.f);
   }

   @Override
   public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
      if (!this.a(_snowman, _snowman, 0)) {
         return false;
      } else {
         this.a(_snowman, _snowman, 1, 1, 1, 5, 1, 7, bup.o.n(), bup.o.n(), false);
         this.a(_snowman, _snowman, 1, 4, 2, 5, 4, 7, bup.o.n(), bup.o.n(), false);
         this.a(_snowman, _snowman, 2, 1, 0, 4, 1, 0, bup.o.n(), bup.o.n(), false);
         this.a(_snowman, _snowman, 2, 2, 2, 3, 3, 2, bup.o.n(), bup.o.n(), false);
         this.a(_snowman, _snowman, 1, 2, 3, 1, 3, 6, bup.o.n(), bup.o.n(), false);
         this.a(_snowman, _snowman, 5, 2, 3, 5, 3, 6, bup.o.n(), bup.o.n(), false);
         this.a(_snowman, _snowman, 2, 2, 7, 4, 3, 7, bup.o.n(), bup.o.n(), false);
         this.a(_snowman, _snowman, 1, 0, 2, 1, 3, 2, bup.J.n(), bup.J.n(), false);
         this.a(_snowman, _snowman, 5, 0, 2, 5, 3, 2, bup.J.n(), bup.J.n(), false);
         this.a(_snowman, _snowman, 1, 0, 7, 1, 3, 7, bup.J.n(), bup.J.n(), false);
         this.a(_snowman, _snowman, 5, 0, 7, 5, 3, 7, bup.J.n(), bup.J.n(), false);
         this.a(_snowman, bup.cJ.n(), 2, 3, 2, _snowman);
         this.a(_snowman, bup.cJ.n(), 3, 3, 7, _snowman);
         this.a(_snowman, bup.a.n(), 1, 3, 4, _snowman);
         this.a(_snowman, bup.a.n(), 5, 3, 4, _snowman);
         this.a(_snowman, bup.a.n(), 5, 3, 5, _snowman);
         this.a(_snowman, bup.eQ.n(), 1, 3, 5, _snowman);
         this.a(_snowman, bup.bV.n(), 3, 2, 6, _snowman);
         this.a(_snowman, bup.eb.n(), 4, 2, 6, _snowman);
         this.a(_snowman, bup.cJ.n(), 1, 2, 1, _snowman);
         this.a(_snowman, bup.cJ.n(), 5, 2, 1, _snowman);
         ceh _snowman = bup.eo.n().a(cak.a, gc.c);
         ceh _snowmanx = bup.eo.n().a(cak.a, gc.f);
         ceh _snowmanxx = bup.eo.n().a(cak.a, gc.e);
         ceh _snowmanxxx = bup.eo.n().a(cak.a, gc.d);
         this.a(_snowman, _snowman, 0, 4, 1, 6, 4, 1, _snowman, _snowman, false);
         this.a(_snowman, _snowman, 0, 4, 2, 0, 4, 7, _snowmanx, _snowmanx, false);
         this.a(_snowman, _snowman, 6, 4, 2, 6, 4, 7, _snowmanxx, _snowmanxx, false);
         this.a(_snowman, _snowman, 0, 4, 8, 6, 4, 8, _snowmanxxx, _snowmanxxx, false);
         this.a(_snowman, _snowman.a(cak.c, cfn.e), 0, 4, 1, _snowman);
         this.a(_snowman, _snowman.a(cak.c, cfn.d), 6, 4, 1, _snowman);
         this.a(_snowman, _snowmanxxx.a(cak.c, cfn.d), 0, 4, 8, _snowman);
         this.a(_snowman, _snowmanxxx.a(cak.c, cfn.e), 6, 4, 8, _snowman);

         for (int _snowmanxxxx = 2; _snowmanxxxx <= 7; _snowmanxxxx += 5) {
            for (int _snowmanxxxxx = 1; _snowmanxxxxx <= 5; _snowmanxxxxx += 4) {
               this.b(_snowman, bup.J.n(), _snowmanxxxxx, -1, _snowmanxxxx, _snowman);
            }
         }

         if (!this.e) {
            int _snowmanxxxx = this.a(2, 5);
            int _snowmanxxxxx = this.d(2);
            int _snowmanxxxxxx = this.b(2, 5);
            if (_snowman.b(new fx(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx))) {
               this.e = true;
               beg _snowmanxxxxxxx = aqe.aS.a(_snowman.E());
               _snowmanxxxxxxx.es();
               _snowmanxxxxxxx.b((double)_snowmanxxxx + 0.5, (double)_snowmanxxxxx, (double)_snowmanxxxxxx + 0.5, 0.0F, 0.0F);
               _snowmanxxxxxxx.a(_snowman, _snowman.d(new fx(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx)), aqp.d, null, null);
               _snowman.l(_snowmanxxxxxxx);
            }
         }

         this.a(_snowman, _snowman);
         return true;
      }
   }

   private void a(bsk var1, cra var2) {
      if (!this.f) {
         int _snowman = this.a(2, 5);
         int _snowmanx = this.d(2);
         int _snowmanxx = this.b(2, 5);
         if (_snowman.b(new fx(_snowman, _snowmanx, _snowmanxx))) {
            this.f = true;
            bab _snowmanxxx = aqe.h.a(_snowman.E());
            _snowmanxxx.es();
            _snowmanxxx.b((double)_snowman + 0.5, (double)_snowmanx, (double)_snowmanxx + 0.5, 0.0F, 0.0F);
            _snowmanxxx.a(_snowman, _snowman.d(new fx(_snowman, _snowmanx, _snowmanxx)), aqp.d, null, null);
            _snowman.l(_snowmanxxx);
         }
      }
   }
}
