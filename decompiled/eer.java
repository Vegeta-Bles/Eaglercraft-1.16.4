import java.util.Random;

public class eer extends efw<bdg, dua<bdg>> {
   private static final vk a = new vk("textures/entity/enderman/enderman.png");
   private final Random g = new Random();

   public eer(eet var1) {
      super(_snowman, new dua<>(0.0F), 0.5F);
      this.a(new eie<>(this));
      this.a(new ehv(this));
   }

   public void a(bdg var1, float var2, float var3, dfm var4, eag var5, int var6) {
      ceh _snowman = _snowman.eM();
      dua<bdg> _snowmanx = this.c();
      _snowmanx.a = _snowman != null;
      _snowmanx.b = _snowman.eN();
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public dcn a(bdg var1, float var2) {
      if (_snowman.eN()) {
         double _snowman = 0.02;
         return new dcn(this.g.nextGaussian() * 0.02, 0.0, this.g.nextGaussian() * 0.02);
      } else {
         return super.a(_snowman, _snowman);
      }
   }

   public vk a(bdg var1) {
      return a;
   }
}
