public class efn extends eeu<bcp> {
   private static final elu a = new elu("item_frame", "map=false");
   private static final elu e = new elu("item_frame", "map=true");
   private final djz f = djz.C();
   private final efo g;

   public efn(eet var1, efo var2) {
      super(_snowman);
      this.g = _snowman;
   }

   public void a(bcp var1, float var2, float var3, dfm var4, eag var5, int var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.a();
      gc _snowman = _snowman.bZ();
      dcn _snowmanx = this.a(_snowman, _snowman);
      _snowman.a(-_snowmanx.a(), -_snowmanx.b(), -_snowmanx.c());
      double _snowmanxx = 0.46875;
      _snowman.a((double)_snowman.i() * 0.46875, (double)_snowman.j() * 0.46875, (double)_snowman.k() * 0.46875);
      _snowman.a(g.b.a(_snowman.q));
      _snowman.a(g.d.a(180.0F - _snowman.p));
      boolean _snowmanxxx = _snowman.bF();
      if (!_snowmanxxx) {
         eax _snowmanxxxx = this.f.ab();
         elt _snowmanxxxxx = _snowmanxxxx.a().a();
         elu _snowmanxxxxxx = _snowman.o().b() == bmd.nf ? e : a;
         _snowman.a();
         _snowman.a(-0.5, -0.5, -0.5);
         _snowmanxxxx.b().a(_snowman.c(), _snowman.getBuffer(ear.g()), null, _snowmanxxxxx.a(_snowmanxxxxxx), 1.0F, 1.0F, 1.0F, _snowman, ejw.a);
         _snowman.b();
      }

      bmb _snowmanxxxx = _snowman.o();
      if (!_snowmanxxxx.a()) {
         boolean _snowmanxxxxx = _snowmanxxxx.b() == bmd.nf;
         if (_snowmanxxx) {
            _snowman.a(0.0, 0.0, 0.5);
         } else {
            _snowman.a(0.0, 0.0, 0.4375);
         }

         int _snowmanxxxxxx = _snowmanxxxxx ? _snowman.p() % 4 * 2 : _snowman.p();
         _snowman.a(g.f.a((float)_snowmanxxxxxx * 360.0F / 8.0F));
         if (_snowmanxxxxx) {
            _snowman.a(g.f.a(180.0F));
            float _snowmanxxxxxxx = 0.0078125F;
            _snowman.a(0.0078125F, 0.0078125F, 0.0078125F);
            _snowman.a(-64.0, -64.0, 0.0);
            cxx _snowmanxxxxxxxx = bmh.b(_snowmanxxxx, _snowman.l);
            _snowman.a(0.0, 0.0, -1.0);
            if (_snowmanxxxxxxxx != null) {
               this.f.h.h().a(_snowman, _snowman, _snowmanxxxxxxxx, true, _snowman);
            }
         } else {
            _snowman.a(0.5F, 0.5F, 0.5F);
            this.g.a(_snowmanxxxx, ebm.b.i, _snowman, ejw.a, _snowman, _snowman);
         }
      }

      _snowman.b();
   }

   public dcn a(bcp var1, float var2) {
      return new dcn((double)((float)_snowman.bZ().i() * 0.3F), -0.25, (double)((float)_snowman.bZ().k() * 0.3F));
   }

   public vk a(bcp var1) {
      return ekb.d;
   }

   protected boolean b(bcp var1) {
      if (djz.x() && !_snowman.o().a() && _snowman.o().t() && this.b.c == _snowman) {
         double _snowman = this.b.b(_snowman);
         float _snowmanx = _snowman.bx() ? 32.0F : 64.0F;
         return _snowman < (double)(_snowmanx * _snowmanx);
      } else {
         return false;
      }
   }

   protected void a(bcp var1, nr var2, dfm var3, eag var4, int var5) {
      super.a(_snowman, _snowman.o().r(), _snowman, _snowman, _snowman);
   }
}
