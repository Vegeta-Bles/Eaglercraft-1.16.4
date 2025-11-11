public class eee extends efw<bab, dtp<bab>> {
   public eee(eet var1) {
      super(_snowman, new dtp<>(0.0F), 0.4F);
      this.a(new ehw(this));
   }

   public vk a(bab var1) {
      return _snowman.eU();
   }

   protected void a(bab var1, dfm var2, float var3) {
      super.a(_snowman, _snowman, _snowman);
      _snowman.a(0.8F, 0.8F, 0.8F);
   }

   protected void a(bab var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = _snowman.y(_snowman);
      if (_snowman > 0.0F) {
         _snowman.a((double)(0.4F * _snowman), (double)(0.15F * _snowman), (double)(0.1F * _snowman));
         _snowman.a(g.f.a(afm.h(_snowman, 0.0F, 90.0F)));
         fx _snowmanx = _snowman.cB();

         for (bfw _snowmanxx : _snowman.l.a(bfw.class, new dci(_snowmanx).c(2.0, 2.0, 2.0))) {
            if (_snowmanxx.em()) {
               _snowman.a((double)(0.15F * _snowman), 0.0, 0.0);
               break;
            }
         }
      }
   }
}
