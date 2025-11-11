public class ayh extends ayj {
   public ayh(aqn var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected cxf a(int var1) {
      this.o = new cxa();
      this.o.a(true);
      return new cxf(this.o, _snowman);
   }

   @Override
   protected boolean a() {
      return this.r() && this.p() || !this.a.br();
   }

   @Override
   protected dcn b() {
      return this.a.cA();
   }

   @Override
   public cxd a(aqa var1, int var2) {
      return this.a(_snowman.cB(), _snowman);
   }

   @Override
   public void c() {
      this.e++;
      if (this.m) {
         this.j();
      }

      if (!this.m()) {
         if (this.a()) {
            this.l();
         } else if (this.c != null && !this.c.c()) {
            dcn _snowman = this.c.a(this.a);
            if (afm.c(this.a.cD()) == afm.c(_snowman.b) && afm.c(this.a.cE()) == afm.c(_snowman.c) && afm.c(this.a.cH()) == afm.c(_snowman.d)) {
               this.c.a();
            }
         }

         rz.a(this.b, this.a, this.c, this.l);
         if (!this.m()) {
            dcn _snowman = this.c.a(this.a);
            this.a.u().a(_snowman.b, _snowman.c, _snowman.d, this.d);
         }
      }
   }

   @Override
   protected boolean a(dcn var1, dcn var2, int var3, int var4, int var5) {
      int _snowman = afm.c(_snowman.b);
      int _snowmanx = afm.c(_snowman.c);
      int _snowmanxx = afm.c(_snowman.d);
      double _snowmanxxx = _snowman.b - _snowman.b;
      double _snowmanxxxx = _snowman.c - _snowman.c;
      double _snowmanxxxxx = _snowman.d - _snowman.d;
      double _snowmanxxxxxx = _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx + _snowmanxxxxx * _snowmanxxxxx;
      if (_snowmanxxxxxx < 1.0E-8) {
         return false;
      } else {
         double _snowmanxxxxxxx = 1.0 / Math.sqrt(_snowmanxxxxxx);
         _snowmanxxx *= _snowmanxxxxxxx;
         _snowmanxxxx *= _snowmanxxxxxxx;
         _snowmanxxxxx *= _snowmanxxxxxxx;
         double _snowmanxxxxxxxx = 1.0 / Math.abs(_snowmanxxx);
         double _snowmanxxxxxxxxx = 1.0 / Math.abs(_snowmanxxxx);
         double _snowmanxxxxxxxxxx = 1.0 / Math.abs(_snowmanxxxxx);
         double _snowmanxxxxxxxxxxx = (double)_snowman - _snowman.b;
         double _snowmanxxxxxxxxxxxx = (double)_snowmanx - _snowman.c;
         double _snowmanxxxxxxxxxxxxx = (double)_snowmanxx - _snowman.d;
         if (_snowmanxxx >= 0.0) {
            _snowmanxxxxxxxxxxx++;
         }

         if (_snowmanxxxx >= 0.0) {
            _snowmanxxxxxxxxxxxx++;
         }

         if (_snowmanxxxxx >= 0.0) {
            _snowmanxxxxxxxxxxxxx++;
         }

         _snowmanxxxxxxxxxxx /= _snowmanxxx;
         _snowmanxxxxxxxxxxxx /= _snowmanxxxx;
         _snowmanxxxxxxxxxxxxx /= _snowmanxxxxx;
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxx < 0.0 ? -1 : 1;
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx < 0.0 ? -1 : 1;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxx < 0.0 ? -1 : 1;
         int _snowmanxxxxxxxxxxxxxxxxx = afm.c(_snowman.b);
         int _snowmanxxxxxxxxxxxxxxxxxx = afm.c(_snowman.c);
         int _snowmanxxxxxxxxxxxxxxxxxxx = afm.c(_snowman.d);
         int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - _snowman;
         int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx - _snowmanx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx - _snowmanxx;

         while (_snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx > 0 || _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx > 0 || _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx > 0) {
            if (_snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx && _snowmanxxxxxxxxxxx <= _snowmanxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxx += _snowmanxxxxxxxx;
               _snowman += _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - _snowman;
            } else if (_snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxx += _snowmanxxxxxxxxx;
               _snowmanx += _snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx - _snowmanx;
            } else {
               _snowmanxxxxxxxxxxxxx += _snowmanxxxxxxxxxx;
               _snowmanxx += _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx - _snowmanxx;
            }
         }

         return true;
      }
   }

   public void a(boolean var1) {
      this.o.b(_snowman);
   }

   public void b(boolean var1) {
      this.o.a(_snowman);
   }

   @Override
   public boolean a(fx var1) {
      return this.b.d_(_snowman).a(this.b, _snowman, this.a);
   }
}
