import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class bfe extends apy implements bfi, bqu {
   private static final us<Integer> bp = uv.a(bfe.class, uu.b);
   @Nullable
   private bfw bq;
   @Nullable
   protected bqw bo;
   private final apa br = new apa(8);

   public bfe(aqe<? extends bfe> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(cwz.l, 16.0F);
      this.a(cwz.m, -1.0F);
   }

   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman == null) {
         _snowman = new apy.a(false);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public int eK() {
      return this.R.a(bp);
   }

   public void s(int var1) {
      this.R.b(bp, _snowman);
   }

   @Override
   public int eL() {
      return 0;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return this.w_() ? 0.81F : 1.62F;
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bp, 0);
   }

   @Override
   public void f(@Nullable bfw var1) {
      this.bq = _snowman;
   }

   @Nullable
   @Override
   public bfw eM() {
      return this.bq;
   }

   public boolean eN() {
      return this.bq != null;
   }

   @Override
   public bqw eO() {
      if (this.bo == null) {
         this.bo = new bqw();
         this.eW();
      }

      return this.bo;
   }

   @Override
   public void a(@Nullable bqw var1) {
   }

   @Override
   public void t(int var1) {
   }

   @Override
   public void a(bqv var1) {
      _snowman.j();
      this.e = -this.D();
      this.b(_snowman);
      if (this.bq instanceof aah) {
         ac.s.a((aah)this.bq, this, _snowman.d());
      }
   }

   protected abstract void b(bqv var1);

   @Override
   public boolean eP() {
      return true;
   }

   @Override
   public void k(bmb var1) {
      if (!this.l.v && this.e > -this.D() + 20) {
         this.e = -this.D();
         this.a(this.t(!_snowman.a()), this.dG(), this.dH());
      }
   }

   @Override
   public adp eQ() {
      return adq.pY;
   }

   protected adp t(boolean var1) {
      return _snowman ? adq.pY : adq.pW;
   }

   public void eR() {
      this.a(adq.pT, this.dG(), this.dH());
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      bqw _snowman = this.eO();
      if (!_snowman.isEmpty()) {
         _snowman.a("Offers", _snowman.a());
      }

      _snowman.a("Inventory", this.br.g());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("Offers", 10)) {
         this.bo = new bqw(_snowman.p("Offers"));
      }

      this.br.a(_snowman.d("Inventory", 10));
   }

   @Nullable
   @Override
   public aqa b(aag var1) {
      this.eT();
      return super.b(_snowman);
   }

   protected void eT() {
      this.f(null);
   }

   @Override
   public void a(apk var1) {
      super.a(_snowman);
      this.eT();
   }

   protected void a(hf var1) {
      for (int _snowman = 0; _snowman < 5; _snowman++) {
         double _snowmanx = this.J.nextGaussian() * 0.02;
         double _snowmanxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxx = this.J.nextGaussian() * 0.02;
         this.l.a(_snowman, this.d(1.0), this.cF() + 1.0, this.g(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   @Override
   public boolean a(bfw var1) {
      return false;
   }

   public apa eU() {
      return this.br;
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      if (super.a_(_snowman, _snowman)) {
         return true;
      } else {
         int _snowman = _snowman - 300;
         if (_snowman >= 0 && _snowman < this.br.Z_()) {
            this.br.a(_snowman, _snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public brx eV() {
      return this.l;
   }

   protected abstract void eW();

   protected void a(bqw var1, bfn.f[] var2, int var3) {
      Set<Integer> _snowman = Sets.newHashSet();
      if (_snowman.length > _snowman) {
         while (_snowman.size() < _snowman) {
            _snowman.add(this.J.nextInt(_snowman.length));
         }
      } else {
         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            _snowman.add(_snowmanx);
         }
      }

      for (Integer _snowmanx : _snowman) {
         bfn.f _snowmanxx = _snowman[_snowmanx];
         bqv _snowmanxxx = _snowmanxx.a(this, this.J);
         if (_snowmanxxx != null) {
            _snowman.add(_snowmanxxx);
         }
      }
   }

   @Override
   public dcn o(float var1) {
      float _snowman = afm.g(_snowman, this.aB, this.aA) * (float) (Math.PI / 180.0);
      dcn _snowmanx = new dcn(0.0, this.cc().c() - 1.0, 0.2);
      return this.l(_snowman).e(_snowmanx.b(-_snowman));
   }
}
