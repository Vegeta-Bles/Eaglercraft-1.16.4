import java.util.Random;

public class efm extends eeu<bcv> {
   private final efo a;
   private final Random e = new Random();

   public efm(eet var1, efo var2) {
      super(_snowman);
      this.a = _snowman;
      this.c = 0.15F;
      this.d = 0.75F;
   }

   private int a(bmb var1) {
      int _snowman = 1;
      if (_snowman.E() > 48) {
         _snowman = 5;
      } else if (_snowman.E() > 32) {
         _snowman = 4;
      } else if (_snowman.E() > 16) {
         _snowman = 3;
      } else if (_snowman.E() > 1) {
         _snowman = 2;
      }

      return _snowman;
   }

   public void a(bcv var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      bmb _snowman = _snowman.g();
      int _snowmanx = _snowman.a() ? 187 : blx.a(_snowman.b()) + _snowman.g();
      this.e.setSeed((long)_snowmanx);
      elo _snowmanxx = this.a.a(_snowman, _snowman.l, null);
      boolean _snowmanxxx = _snowmanxx.b();
      int _snowmanxxxx = this.a(_snowman);
      float _snowmanxxxxx = 0.25F;
      float _snowmanxxxxxx = afm.a(((float)_snowman.k() + _snowman) / 10.0F + _snowman.b) * 0.1F + 0.1F;
      float _snowmanxxxxxxx = _snowmanxx.f().a(ebm.b.h).d.b();
      _snowman.a(0.0, (double)(_snowmanxxxxxx + 0.25F * _snowmanxxxxxxx), 0.0);
      float _snowmanxxxxxxxx = _snowman.a(_snowman);
      _snowman.a(g.d.c(_snowmanxxxxxxxx));
      float _snowmanxxxxxxxxx = _snowmanxx.f().h.d.a();
      float _snowmanxxxxxxxxxx = _snowmanxx.f().h.d.b();
      float _snowmanxxxxxxxxxxx = _snowmanxx.f().h.d.c();
      if (!_snowmanxxx) {
         float _snowmanxxxxxxxxxxxx = -0.0F * (float)(_snowmanxxxx - 1) * 0.5F * _snowmanxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxx = -0.0F * (float)(_snowmanxxxx - 1) * 0.5F * _snowmanxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxx = -0.09375F * (float)(_snowmanxxxx - 1) * 0.5F * _snowmanxxxxxxxxxxx;
         _snowman.a((double)_snowmanxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxx);
      }

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxxxx++) {
         _snowman.a();
         if (_snowmanxxxxxxxxxxxx > 0) {
            if (_snowmanxxx) {
               float _snowmanxxxxxxxxxxxxx = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float _snowmanxxxxxxxxxxxxxx = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float _snowmanxxxxxxxxxxxxxxx = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               _snowman.a((double)_snowmanxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxx);
            } else {
               float _snowmanxxxxxxxxxxxxx = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               float _snowmanxxxxxxxxxxxxxx = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               _snowman.a((double)_snowmanxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxx, 0.0);
            }
         }

         this.a.a(_snowman, ebm.b.h, false, _snowman, _snowman, _snowman, ejw.a, _snowmanxx);
         _snowman.b();
         if (!_snowmanxxx) {
            _snowman.a((double)(0.0F * _snowmanxxxxxxxxx), (double)(0.0F * _snowmanxxxxxxxxxx), (double)(0.09375F * _snowmanxxxxxxxxxxx));
         }
      }

      _snowman.b();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public vk a(bcv var1) {
      return ekb.d;
   }
}
