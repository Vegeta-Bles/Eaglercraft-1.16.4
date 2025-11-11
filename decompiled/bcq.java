import java.util.List;
import javax.annotation.Nullable;

public class bcq extends bco {
   public bcq(aqe<? extends bcq> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bcq(brx var1, fx var2) {
      super(aqe.O, _snowman, _snowman);
      this.d((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5);
      float _snowman = 0.125F;
      float _snowmanx = 0.1875F;
      float _snowmanxx = 0.25F;
      this.a(new dci(this.cD() - 0.1875, this.cE() - 0.25 + 0.125, this.cH() - 0.1875, this.cD() + 0.1875, this.cE() + 0.25 + 0.125, this.cH() + 0.1875));
      this.k = true;
   }

   @Override
   public void d(double var1, double var3, double var5) {
      super.d((double)afm.c(_snowman) + 0.5, (double)afm.c(_snowman) + 0.5, (double)afm.c(_snowman) + 0.5);
   }

   @Override
   protected void g() {
      this.o((double)this.c.u() + 0.5, (double)this.c.v() + 0.5, (double)this.c.w() + 0.5);
   }

   @Override
   public void a(gc var1) {
   }

   @Override
   public int i() {
      return 9;
   }

   @Override
   public int k() {
      return 9;
   }

   @Override
   protected float a(aqx var1, aqb var2) {
      return -0.0625F;
   }

   @Override
   public boolean a(double var1) {
      return _snowman < 1024.0;
   }

   @Override
   public void a(@Nullable aqa var1) {
      this.a(adq.gZ, 1.0F, 1.0F);
   }

   @Override
   public void b(md var1) {
   }

   @Override
   public void a(md var1) {
   }

   @Override
   public aou a(bfw var1, aot var2) {
      if (this.l.v) {
         return aou.a;
      } else {
         boolean _snowman = false;
         double _snowmanx = 7.0;
         List<aqn> _snowmanxx = this.l.a(aqn.class, new dci(this.cD() - 7.0, this.cE() - 7.0, this.cH() - 7.0, this.cD() + 7.0, this.cE() + 7.0, this.cH() + 7.0));

         for (aqn _snowmanxxx : _snowmanxx) {
            if (_snowmanxxx.eC() == _snowman) {
               _snowmanxxx.b(this, true);
               _snowman = true;
            }
         }

         if (!_snowman) {
            this.ad();
            if (_snowman.bC.d) {
               for (aqn _snowmanxxxx : _snowmanxx) {
                  if (_snowmanxxxx.eB() && _snowmanxxxx.eC() == this) {
                     _snowmanxxxx.a(true, false);
                  }
               }
            }
         }

         return aou.b;
      }
   }

   @Override
   public boolean h() {
      return this.l.d_(this.c).b().a(aed.M);
   }

   public static bcq a(brx var0, fx var1) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.v();
      int _snowmanxx = _snowman.w();

      for (bcq _snowmanxxx : _snowman.a(bcq.class, new dci((double)_snowman - 1.0, (double)_snowmanx - 1.0, (double)_snowmanxx - 1.0, (double)_snowman + 1.0, (double)_snowmanx + 1.0, (double)_snowmanxx + 1.0))) {
         if (_snowmanxxx.n().equals(_snowman)) {
            return _snowmanxxx;
         }
      }

      bcq _snowmanxxxx = new bcq(_snowman, _snowman);
      _snowman.c(_snowmanxxxx);
      _snowmanxxxx.m();
      return _snowmanxxxx;
   }

   @Override
   public void m() {
      this.a(adq.ha, 1.0F, 1.0F);
   }

   @Override
   public oj<?> P() {
      return new on(this, this.X(), 0, this.n());
   }

   @Override
   public dcn o(float var1) {
      return this.l(_snowman).b(0.0, 0.2, 0.0);
   }
}
