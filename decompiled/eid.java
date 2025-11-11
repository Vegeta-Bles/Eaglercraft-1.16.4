public class eid<T extends aqm, M extends duc<T>> extends eit<T, M> {
   private static final vk a = new vk("textures/entity/elytra.png");
   private final dtz<T> b = new dtz<>();

   public eid(egk<T, M> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      bmb _snowman = _snowman.b(aqf.e);
      if (_snowman.b() == bmd.qo) {
         vk _snowmanx;
         if (_snowman instanceof dzj) {
            dzj _snowmanxx = (dzj)_snowman;
            if (_snowmanxx.q() && _snowmanxx.r() != null) {
               _snowmanx = _snowmanxx.r();
            } else if (_snowmanxx.c() && _snowmanxx.p() != null && _snowmanxx.a(bfx.a)) {
               _snowmanx = _snowmanxx.p();
            } else {
               _snowmanx = a;
            }
         } else {
            _snowmanx = a;
         }

         _snowman.a();
         _snowman.a(0.0, 0.0, 0.125);
         this.aC_().a(this.b);
         this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         dfq _snowmanxx = efo.a(_snowman, eao.a(_snowmanx), false, _snowman.u());
         this.b.a(_snowman, _snowmanxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
         _snowman.b();
      }
   }
}
