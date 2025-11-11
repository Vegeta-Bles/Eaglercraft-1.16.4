public class eev extends eeu<bge> {
   private static final vk a = new vk("textures/entity/illager/evoker_fangs.png");
   private final dud<bge> e = new dud<>();

   public eev(eet var1) {
      super(_snowman);
   }

   public void a(bge var1, float var2, float var3, dfm var4, eag var5, int var6) {
      float _snowman = _snowman.a(_snowman);
      if (_snowman != 0.0F) {
         float _snowmanx = 2.0F;
         if (_snowman > 0.9F) {
            _snowmanx = (float)((double)_snowmanx * ((1.0 - (double)_snowman) / 0.1F));
         }

         _snowman.a();
         _snowman.a(g.d.a(90.0F - _snowman.p));
         _snowman.a(-_snowmanx, -_snowmanx, _snowmanx);
         float _snowmanxx = 0.03125F;
         _snowman.a(0.0, -0.626F, 0.0);
         _snowman.a(0.5F, 0.5F, 0.5F);
         this.e.a(_snowman, _snowman, 0.0F, 0.0F, _snowman.p, _snowman.q);
         dfq _snowmanxxx = _snowman.getBuffer(this.e.a(a));
         this.e.a(_snowman, _snowmanxxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
         _snowman.b();
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public vk a(bge var1) {
      return a;
   }
}
