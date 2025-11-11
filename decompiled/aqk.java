public interface aqk {
   boolean O_();

   void a_(dcn var1);

   float N_();

   default boolean a(aqn var1, aqj var2, dcn var3) {
      if (!_snowman.aX()) {
         return false;
      } else {
         aqa _snowman = _snowman.cn().isEmpty() ? null : _snowman.cn().get(0);
         if (_snowman.bs() && _snowman.er() && _snowman instanceof bfw) {
            _snowman.p = _snowman.p;
            _snowman.r = _snowman.p;
            _snowman.q = _snowman.q * 0.5F;
            _snowman.a(_snowman.p, _snowman.q);
            _snowman.aA = _snowman.p;
            _snowman.aC = _snowman.p;
            _snowman.G = 1.0F;
            _snowman.aE = _snowman.dN() * 0.1F;
            if (_snowman.a && _snowman.b++ > _snowman.c) {
               _snowman.a = false;
            }

            if (_snowman.cs()) {
               float _snowmanx = this.N_();
               if (_snowman.a) {
                  _snowmanx += _snowmanx * 1.15F * afm.a((float)_snowman.b / (float)_snowman.c * (float) Math.PI);
               }

               _snowman.q(_snowmanx);
               this.a_(new dcn(0.0, 0.0, 1.0));
               _snowman.aU = 0;
            } else {
               _snowman.a(_snowman, false);
               _snowman.f(dcn.a);
            }

            return true;
         } else {
            _snowman.G = 0.5F;
            _snowman.aE = 0.02F;
            this.a_(_snowman);
            return false;
         }
      }
   }
}
