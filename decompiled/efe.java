public class efe extends efw<bdm, duh> {
   private static final vk a = new vk("textures/entity/guardian.png");
   private static final vk g = new vk("textures/entity/guardian_beam.png");
   private static final eao h = eao.d(g);

   public efe(eet var1) {
      this(_snowman, 0.5F);
   }

   protected efe(eet var1, float var2) {
      super(_snowman, new duh(), _snowman);
   }

   public boolean a(bdm var1, ecz var2, double var3, double var5, double var7) {
      if (super.a(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else {
         if (_snowman.eO()) {
            aqm _snowman = _snowman.eP();
            if (_snowman != null) {
               dcn _snowmanx = this.a(_snowman, (double)_snowman.cz() * 0.5, 1.0F);
               dcn _snowmanxx = this.a(_snowman, (double)_snowman.ce(), 1.0F);
               return _snowman.a(new dci(_snowmanxx.b, _snowmanxx.c, _snowmanxx.d, _snowmanx.b, _snowmanx.c, _snowmanx.d));
            }
         }

         return false;
      }
   }

   private dcn a(aqm var1, double var2, float var4) {
      double _snowman = afm.d((double)_snowman, _snowman.D, _snowman.cD());
      double _snowmanx = afm.d((double)_snowman, _snowman.E, _snowman.cE()) + _snowman;
      double _snowmanxx = afm.d((double)_snowman, _snowman.F, _snowman.cH());
      return new dcn(_snowman, _snowmanx, _snowmanxx);
   }

   public void a(bdm var1, float var2, float var3, dfm var4, eag var5, int var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      aqm _snowman = _snowman.eP();
      if (_snowman != null) {
         float _snowmanx = _snowman.A(_snowman);
         float _snowmanxx = (float)_snowman.l.T() + _snowman;
         float _snowmanxxx = _snowmanxx * 0.5F % 1.0F;
         float _snowmanxxxx = _snowman.ce();
         _snowman.a();
         _snowman.a(0.0, (double)_snowmanxxxx, 0.0);
         dcn _snowmanxxxxx = this.a(_snowman, (double)_snowman.cz() * 0.5, _snowman);
         dcn _snowmanxxxxxx = this.a(_snowman, (double)_snowmanxxxx, _snowman);
         dcn _snowmanxxxxxxx = _snowmanxxxxx.d(_snowmanxxxxxx);
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxxxx.f() + 1.0);
         _snowmanxxxxxxx = _snowmanxxxxxxx.d();
         float _snowmanxxxxxxxxx = (float)Math.acos(_snowmanxxxxxxx.c);
         float _snowmanxxxxxxxxxx = (float)Math.atan2(_snowmanxxxxxxx.d, _snowmanxxxxxxx.b);
         _snowman.a(g.d.a(((float) (Math.PI / 2) - _snowmanxxxxxxxxxx) * (180.0F / (float)Math.PI)));
         _snowman.a(g.b.a(_snowmanxxxxxxxxx * (180.0F / (float)Math.PI)));
         int _snowmanxxxxxxxxxxx = 1;
         float _snowmanxxxxxxxxxxxx = _snowmanxx * 0.05F * -1.5F;
         float _snowmanxxxxxxxxxxxxx = _snowmanx * _snowmanx;
         int _snowmanxxxxxxxxxxxxxx = 64 + (int)(_snowmanxxxxxxxxxxxxx * 191.0F);
         int _snowmanxxxxxxxxxxxxxxx = 32 + (int)(_snowmanxxxxxxxxxxxxx * 191.0F);
         int _snowmanxxxxxxxxxxxxxxxx = 128 - (int)(_snowmanxxxxxxxxxxxxx * 64.0F);
         float _snowmanxxxxxxxxxxxxxxxxx = 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxx = 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + (float) (Math.PI / 4)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + (float) (Math.PI / 4)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + (float) Math.PI) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + (float) Math.PI) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + 0.0F) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + 0.0F) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + (float) (Math.PI / 2)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + (float) (Math.PI / 2)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.4999F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -1.0F + _snowmanxxx;
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx * 2.5F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         dfq _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(h);
         dfm.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.c();
         b _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a();
         a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b();
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.4999F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
         if (_snowman.K % 2 == 0) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.5F;
         }

         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.5F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            1.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            1.0F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            0.5F,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         _snowman.b();
      }
   }

   private static void a(dfq var0, b var1, a var2, float var3, float var4, float var5, int var6, int var7, int var8, float var9, float var10) {
      _snowman.a(_snowman, _snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, 255).a(_snowman, _snowman).b(ejw.a).a(15728880).a(_snowman, 0.0F, 1.0F, 0.0F).d();
   }

   public vk a(bdm var1) {
      return a;
   }
}
