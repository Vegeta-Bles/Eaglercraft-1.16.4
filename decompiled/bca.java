import java.util.Random;
import javax.annotation.Nullable;

public class bca extends bbt {
   private dcn b;

   public bca(bbr var1) {
      super(_snowman);
   }

   @Override
   public void b() {
      dcn _snowman = this.a.x(1.0F).d();
      _snowman.b((float) (-Math.PI / 4));
      double _snowmanx = this.a.bo.cD();
      double _snowmanxx = this.a.bo.e(0.5);
      double _snowmanxxx = this.a.bo.cH();

      for (int _snowmanxxxx = 0; _snowmanxxxx < 8; _snowmanxxxx++) {
         Random _snowmanxxxxx = this.a.cY();
         double _snowmanxxxxxx = _snowmanx + _snowmanxxxxx.nextGaussian() / 2.0;
         double _snowmanxxxxxxx = _snowmanxx + _snowmanxxxxx.nextGaussian() / 2.0;
         double _snowmanxxxxxxxx = _snowmanxxx + _snowmanxxxxx.nextGaussian() / 2.0;
         dcn _snowmanxxxxxxxxx = this.a.cC();
         this.a.l.a(hh.i, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, -_snowman.b * 0.08F + _snowmanxxxxxxxxx.b, -_snowman.c * 0.3F + _snowmanxxxxxxxxx.c, -_snowman.d * 0.08F + _snowmanxxxxxxxxx.d);
         _snowman.b((float) (Math.PI / 16));
      }
   }

   @Override
   public void c() {
      if (this.b == null) {
         this.b = dcn.c(this.a.l.a(chn.a.f, cjk.a));
      }

      if (this.b.c(this.a.cD(), this.a.cE(), this.a.cH()) < 1.0) {
         this.a.eK().b(bch.f).j();
         this.a.eK().a(bch.g);
      }
   }

   @Override
   public float f() {
      return 1.5F;
   }

   @Override
   public float h() {
      float _snowman = afm.a(aqa.c(this.a.cC())) + 1.0F;
      float _snowmanx = Math.min(_snowman, 40.0F);
      return _snowmanx / _snowman;
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Nullable
   @Override
   public dcn g() {
      return this.b;
   }

   @Override
   public bch<bca> i() {
      return bch.d;
   }
}
