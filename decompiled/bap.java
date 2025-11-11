import java.util.List;
import java.util.function.Predicate;

public class bap extends azw {
   private static final us<Integer> b = uv.a(bap.class, uu.b);
   private int c;
   private int d;
   private static final Predicate<aqm> bo = var0 -> {
      if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof bfw) || !var0.a_() && !((bfw)var0).b_() ? var0.dC() != aqq.e : false;
      }
   };

   public bap(aqe<? extends bap> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, 0);
   }

   public int eN() {
      return this.R.a(b);
   }

   public void b(int var1) {
      this.R.b(b, _snowman);
   }

   @Override
   public void a(us<?> var1) {
      if (b.equals(_snowman)) {
         this.x_();
      }

      super.a(_snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("PuffState", this.eN());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.b(_snowman.h("PuffState"));
   }

   @Override
   protected bmb eK() {
      return new bmb(bmd.lU);
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(1, new bap.a(this));
   }

   @Override
   public void j() {
      if (!this.l.v && this.aX() && this.dS()) {
         if (this.c > 0) {
            if (this.eN() == 0) {
               this.a(adq.lS, this.dG(), this.dH());
               this.b(1);
            } else if (this.c > 40 && this.eN() == 1) {
               this.a(adq.lS, this.dG(), this.dH());
               this.b(2);
            }

            this.c++;
         } else if (this.eN() != 0) {
            if (this.d > 60 && this.eN() == 2) {
               this.a(adq.lR, this.dG(), this.dH());
               this.b(1);
            } else if (this.d > 100 && this.eN() == 1) {
               this.a(adq.lR, this.dG(), this.dH());
               this.b(0);
            }

            this.d++;
         }
      }

      super.j();
   }

   @Override
   public void k() {
      super.k();
      if (this.aX() && this.eN() > 0) {
         for (aqn _snowman : this.l.a(aqn.class, this.cc().g(0.3), bo)) {
            if (_snowman.aX()) {
               this.a(_snowman);
            }
         }
      }
   }

   private void a(aqn var1) {
      int _snowman = this.eN();
      if (_snowman.a(apk.c(this), (float)(1 + _snowman))) {
         _snowman.c(new apu(apw.s, 60 * _snowman, 0));
         this.a(adq.lW, 1.0F, 1.0F);
      }
   }

   @Override
   public void a_(bfw var1) {
      int _snowman = this.eN();
      if (_snowman instanceof aah && _snowman > 0 && _snowman.a(apk.c(this), (float)(1 + _snowman))) {
         if (!this.aA()) {
            ((aah)_snowman).b.a(new pq(pq.j, 0.0F));
         }

         _snowman.c(new apu(apw.s, 60 * _snowman, 0));
      }
   }

   @Override
   protected adp I() {
      return adq.lQ;
   }

   @Override
   protected adp dq() {
      return adq.lT;
   }

   @Override
   protected adp e(apk var1) {
      return adq.lV;
   }

   @Override
   protected adp eM() {
      return adq.lU;
   }

   @Override
   public aqb a(aqx var1) {
      return super.a(_snowman).a(s(this.eN()));
   }

   private static float s(int var0) {
      switch (_snowman) {
         case 0:
            return 0.5F;
         case 1:
            return 0.7F;
         default:
            return 1.0F;
      }
   }

   static class a extends avv {
      private final bap a;

      public a(bap var1) {
         this.a = _snowman;
      }

      @Override
      public boolean a() {
         List<aqm> _snowman = this.a.l.a(aqm.class, this.a.cc().g(2.0), bap.bo);
         return !_snowman.isEmpty();
      }

      @Override
      public void c() {
         this.a.c = 1;
         this.a.d = 0;
      }

      @Override
      public void d() {
         this.a.c = 0;
      }

      @Override
      public boolean b() {
         List<aqm> _snowman = this.a.l.a(aqm.class, this.a.cc().g(2.0), bap.bo);
         return !_snowman.isEmpty();
      }
   }
}
