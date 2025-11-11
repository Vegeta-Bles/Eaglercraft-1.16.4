import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.tuple.Pair;

public class baj extends bae implements arb {
   private static final us<String> bo = uv.a(baj.class, uu.d);
   private aps bp;
   private int bq;
   private UUID br;

   public baj(aqe<? extends baj> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   public float a(fx var1, brz var2) {
      return _snowman.d_(_snowman.c()).a(bup.dT) ? 10.0F : _snowman.y(_snowman) - 0.5F;
   }

   public static boolean c(aqe<baj> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.d_(_snowman.c()).a(bup.dT) && _snowman.b(_snowman, 0) > 8;
   }

   @Override
   public void a(aag var1, aql var2) {
      UUID _snowman = _snowman.bS();
      if (!_snowman.equals(this.br)) {
         this.a(this.eL() == baj.a.a ? baj.a.b : baj.a.a);
         this.br = _snowman;
         this.a(adq.hK, 2.0F, 1.0F);
      }
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, baj.a.a.c);
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.kQ && !this.w_()) {
         boolean _snowmanx = false;
         bmb _snowmanxx;
         if (this.bp != null) {
            _snowmanx = true;
            _snowmanxx = new bmb(bmd.qR);
            bne.a(_snowmanxx, this.bp, this.bq);
            this.bp = null;
            this.bq = 0;
         } else {
            _snowmanxx = new bmb(bmd.kR);
         }

         bmb _snowmanxxx = bmc.a(_snowman, _snowman, _snowmanxx, false);
         _snowman.a(_snowman, _snowmanxxx);
         adp _snowmanxxxx;
         if (_snowmanx) {
            _snowmanxxxx = adq.hN;
         } else {
            _snowmanxxxx = adq.hM;
         }

         this.a(_snowmanxxxx, 1.0F, 1.0F);
         return aou.a(this.l.v);
      } else if (_snowman.b() == bmd.ng && this.K_()) {
         this.a(adr.h);
         if (!this.l.v) {
            _snowman.a(1, _snowman, var1x -> var1x.d(_snowman));
         }

         return aou.a(this.l.v);
      } else if (this.eL() == baj.a.b && _snowman.b().a(aeg.I)) {
         if (this.bp != null) {
            for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
               this.l.a(hh.S, this.cD() + this.J.nextDouble() / 2.0, this.e(0.5), this.cH() + this.J.nextDouble() / 2.0, 0.0, this.J.nextDouble() / 5.0, 0.0);
            }
         } else {
            Optional<Pair<aps, Integer>> _snowmanxxx = this.l(_snowman);
            if (!_snowmanxxx.isPresent()) {
               return aou.c;
            }

            Pair<aps, Integer> _snowmanxxxx = _snowmanxxx.get();
            if (!_snowman.bC.d) {
               _snowman.g(1);
            }

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 4; _snowmanxxxxx++) {
               this.l.a(hh.p, this.cD() + this.J.nextDouble() / 2.0, this.e(0.5), this.cH() + this.J.nextDouble() / 2.0, 0.0, this.J.nextDouble() / 5.0, 0.0);
            }

            this.bp = (aps)_snowmanxxxx.getLeft();
            this.bq = (Integer)_snowmanxxxx.getRight();
            this.a(adq.hL, 2.0F, 1.0F);
         }

         return aou.a(this.l.v);
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   @Override
   public void a(adr var1) {
      this.l.a(null, this, adq.hO, _snowman, 1.0F, 1.0F);
      if (!this.l.s_()) {
         ((aag)this.l).a(hh.w, this.cD(), this.e(0.5), this.cH(), 1, 0.0, 0.0, 0.0, 0.0);
         this.ad();
         bae _snowman = aqe.l.a(this.l);
         _snowman.b(this.cD(), this.cE(), this.cH(), this.p, this.q);
         _snowman.c(this.dk());
         _snowman.aA = this.aA;
         if (this.S()) {
            _snowman.a(this.T());
            _snowman.n(this.bX());
         }

         if (this.eu()) {
            _snowman.es();
         }

         _snowman.m(this.bM());
         this.l.c(_snowman);

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            this.l.c(new bcv(this.l, this.cD(), this.e(1.0), this.cH(), new bmb(this.eL().d.b())));
         }
      }
   }

   @Override
   public boolean K_() {
      return this.aX() && !this.w_();
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("Type", this.eL().c);
      if (this.bp != null) {
         _snowman.a("EffectId", (byte)aps.a(this.bp));
         _snowman.b("EffectDuration", this.bq);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.a(baj.a.b(_snowman.l("Type")));
      if (_snowman.c("EffectId", 1)) {
         this.bp = aps.a(_snowman.f("EffectId"));
      }

      if (_snowman.c("EffectDuration", 3)) {
         this.bq = _snowman.h("EffectDuration");
      }
   }

   private Optional<Pair<aps, Integer>> l(bmb var1) {
      blx _snowman = _snowman.b();
      if (_snowman instanceof bkh) {
         buo _snowmanx = ((bkh)_snowman).e();
         if (_snowmanx instanceof bwu) {
            bwu _snowmanxx = (bwu)_snowmanx;
            return Optional.of(Pair.of(_snowmanxx.c(), _snowmanxx.d()));
         }
      }

      return Optional.empty();
   }

   private void a(baj.a var1) {
      this.R.b(bo, _snowman.c);
   }

   public baj.a eL() {
      return baj.a.b(this.R.a(bo));
   }

   public baj c(aag var1, apy var2) {
      baj _snowman = aqe.ab.a(_snowman);
      _snowman.a(this.a((baj)_snowman));
      return _snowman;
   }

   private baj.a a(baj var1) {
      baj.a _snowman = this.eL();
      baj.a _snowmanx = _snowman.eL();
      baj.a _snowmanxx;
      if (_snowman == _snowmanx && this.J.nextInt(1024) == 0) {
         _snowmanxx = _snowman == baj.a.b ? baj.a.a : baj.a.b;
      } else {
         _snowmanxx = this.J.nextBoolean() ? _snowman : _snowmanx;
      }

      return _snowmanxx;
   }

   public static enum a {
      a("red", bup.bD.n()),
      b("brown", bup.bC.n());

      private final String c;
      private final ceh d;

      private a(String var3, ceh var4) {
         this.c = _snowman;
         this.d = _snowman;
      }

      public ceh a() {
         return this.d;
      }

      private static baj.a b(String var0) {
         for (baj.a _snowman : values()) {
            if (_snowman.c.equals(_snowman)) {
               return _snowman;
            }
         }

         return a;
      }
   }
}
