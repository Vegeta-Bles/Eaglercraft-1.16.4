public class ehu extends eit<dzj, dvd<dzj>> {
   public ehu(egk<dzj, dvd<dzj>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, dzj var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (_snowman.c() && !_snowman.bF() && _snowman.a(bfx.a) && _snowman.p() != null) {
         bmb _snowman = _snowman.b(aqf.e);
         if (_snowman.b() != bmd.qo) {
            _snowman.a();
            _snowman.a(0.0, 0.0, 0.125);
            double _snowmanx = afm.d((double)_snowman, _snowman.bv, _snowman.by) - afm.d((double)_snowman, _snowman.m, _snowman.cD());
            double _snowmanxx = afm.d((double)_snowman, _snowman.bw, _snowman.bz) - afm.d((double)_snowman, _snowman.n, _snowman.cE());
            double _snowmanxxx = afm.d((double)_snowman, _snowman.bx, _snowman.bA) - afm.d((double)_snowman, _snowman.o, _snowman.cH());
            float _snowmanxxxx = _snowman.aB + (_snowman.aA - _snowman.aB);
            double _snowmanxxxxx = (double)afm.a(_snowmanxxxx * (float) (Math.PI / 180.0));
            double _snowmanxxxxxx = (double)(-afm.b(_snowmanxxxx * (float) (Math.PI / 180.0)));
            float _snowmanxxxxxxx = (float)_snowmanxx * 10.0F;
            _snowmanxxxxxxx = afm.a(_snowmanxxxxxxx, -6.0F, 32.0F);
            float _snowmanxxxxxxxx = (float)(_snowmanx * _snowmanxxxxx + _snowmanxxx * _snowmanxxxxxx) * 100.0F;
            _snowmanxxxxxxxx = afm.a(_snowmanxxxxxxxx, 0.0F, 150.0F);
            float _snowmanxxxxxxxxx = (float)(_snowmanx * _snowmanxxxxxx - _snowmanxxx * _snowmanxxxxx) * 100.0F;
            _snowmanxxxxxxxxx = afm.a(_snowmanxxxxxxxxx, -20.0F, 20.0F);
            if (_snowmanxxxxxxxx < 0.0F) {
               _snowmanxxxxxxxx = 0.0F;
            }

            float _snowmanxxxxxxxxxx = afm.g(_snowman, _snowman.bs, _snowman.bt);
            _snowmanxxxxxxx += afm.a(afm.g(_snowman, _snowman.z, _snowman.A) * 6.0F) * 32.0F * _snowmanxxxxxxxxxx;
            if (_snowman.bz()) {
               _snowmanxxxxxxx += 25.0F;
            }

            _snowman.a(g.b.a(6.0F + _snowmanxxxxxxxx / 2.0F + _snowmanxxxxxxx));
            _snowman.a(g.f.a(_snowmanxxxxxxxxx / 2.0F));
            _snowman.a(g.d.a(180.0F - _snowmanxxxxxxxxx / 2.0F));
            dfq _snowmanxxxxxxxxxxx = _snowman.getBuffer(eao.b(_snowman.p()));
            this.aC_().b(_snowman, _snowmanxxxxxxxxxxx, _snowman, ejw.a);
            _snowman.b();
         }
      }
   }
}
