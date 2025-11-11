import javax.annotation.Nullable;

public class cxg extends cxc {
   private final boolean j;

   public cxg(boolean var1) {
      this.j = _snowman;
   }

   @Override
   public cxb b() {
      return super.a(afm.c(this.b.cc().a), afm.c(this.b.cc().b + 0.5), afm.c(this.b.cc().c));
   }

   @Override
   public cxh a(double var1, double var3, double var5) {
      return new cxh(super.a(afm.c(_snowman - (double)(this.b.cy() / 2.0F)), afm.c(_snowman + 0.5), afm.c(_snowman - (double)(this.b.cy() / 2.0F))));
   }

   @Override
   public int a(cxb[] var1, cxb var2) {
      int _snowman = 0;

      for (gc _snowmanx : gc.values()) {
         cxb _snowmanxx = this.b(_snowman.a + _snowmanx.i(), _snowman.b + _snowmanx.j(), _snowman.c + _snowmanx.k());
         if (_snowmanxx != null && !_snowmanxx.i) {
            _snowman[_snowman++] = _snowmanxx;
         }
      }

      return _snowman;
   }

   @Override
   public cwz a(brc var1, int var2, int var3, int var4, aqn var5, int var6, int var7, int var8, boolean var9, boolean var10) {
      return this.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public cwz a(brc var1, int var2, int var3, int var4) {
      fx _snowman = new fx(_snowman, _snowman, _snowman);
      cux _snowmanx = _snowman.b(_snowman);
      ceh _snowmanxx = _snowman.d_(_snowman);
      if (_snowmanx.c() && _snowmanxx.a(_snowman, _snowman.c(), cxe.b) && _snowmanxx.g()) {
         return cwz.u;
      } else {
         return _snowmanx.a(aef.b) && _snowmanxx.a(_snowman, _snowman, cxe.b) ? cwz.h : cwz.a;
      }
   }

   @Nullable
   private cxb b(int var1, int var2, int var3) {
      cwz _snowman = this.c(_snowman, _snowman, _snowman);
      return (!this.j || _snowman != cwz.u) && _snowman != cwz.h ? null : this.a(_snowman, _snowman, _snowman);
   }

   @Nullable
   @Override
   protected cxb a(int var1, int var2, int var3) {
      cxb _snowman = null;
      cwz _snowmanx = this.a(this.b.l, _snowman, _snowman, _snowman);
      float _snowmanxx = this.b.a(_snowmanx);
      if (_snowmanxx >= 0.0F) {
         _snowman = super.a(_snowman, _snowman, _snowman);
         _snowman.l = _snowmanx;
         _snowman.k = Math.max(_snowman.k, _snowmanxx);
         if (this.a.b(new fx(_snowman, _snowman, _snowman)).c()) {
            _snowman.k += 8.0F;
         }
      }

      return _snowmanx == cwz.b ? _snowman : _snowman;
   }

   private cwz c(int var1, int var2, int var3) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = _snowman; _snowmanx < _snowman + this.d; _snowmanx++) {
         for (int _snowmanxx = _snowman; _snowmanxx < _snowman + this.e; _snowmanxx++) {
            for (int _snowmanxxx = _snowman; _snowmanxxx < _snowman + this.f; _snowmanxxx++) {
               cux _snowmanxxxx = this.a.b(_snowman.d(_snowmanx, _snowmanxx, _snowmanxxx));
               ceh _snowmanxxxxx = this.a.d_(_snowman.d(_snowmanx, _snowmanxx, _snowmanxxx));
               if (_snowmanxxxx.c() && _snowmanxxxxx.a(this.a, _snowman.c(), cxe.b) && _snowmanxxxxx.g()) {
                  return cwz.u;
               }

               if (!_snowmanxxxx.a(aef.b)) {
                  return cwz.a;
               }
            }
         }
      }

      ceh _snowmanx = this.a.d_(_snowman);
      return _snowmanx.a(this.a, _snowman, cxe.b) ? cwz.h : cwz.a;
   }
}
