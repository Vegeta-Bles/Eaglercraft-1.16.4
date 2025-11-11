import com.google.common.collect.Maps;
import java.util.Map;

public class eij extends eit<bbd, duk<bbd>> {
   private static final Map<bbf, vk> a = x.a(Maps.newEnumMap(bbf.class), var0 -> {
      var0.put(bbf.a, null);
      var0.put(bbf.b, new vk("textures/entity/horse/horse_markings_white.png"));
      var0.put(bbf.c, new vk("textures/entity/horse/horse_markings_whitefield.png"));
      var0.put(bbf.d, new vk("textures/entity/horse/horse_markings_whitedots.png"));
      var0.put(bbf.e, new vk("textures/entity/horse/horse_markings_blackdots.png"));
   });

   public eij(egk<bbd, duk<bbd>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, bbd var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      vk _snowman = a.get(_snowman.eO());
      if (_snowman != null && !_snowman.bF()) {
         dfq _snowmanx = _snowman.getBuffer(eao.h(_snowman));
         this.aC_().a(_snowman, _snowmanx, _snowman, efr.c(_snowman, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
