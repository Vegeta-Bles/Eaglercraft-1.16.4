import com.google.common.collect.Maps;
import java.util.Map;

public final class efg extends edv<bbd, duk<bbd>> {
   private static final Map<bbk, vk> a = x.a(Maps.newEnumMap(bbk.class), var0 -> {
      var0.put(bbk.a, new vk("textures/entity/horse/horse_white.png"));
      var0.put(bbk.b, new vk("textures/entity/horse/horse_creamy.png"));
      var0.put(bbk.c, new vk("textures/entity/horse/horse_chestnut.png"));
      var0.put(bbk.d, new vk("textures/entity/horse/horse_brown.png"));
      var0.put(bbk.e, new vk("textures/entity/horse/horse_black.png"));
      var0.put(bbk.f, new vk("textures/entity/horse/horse_gray.png"));
      var0.put(bbk.g, new vk("textures/entity/horse/horse_darkbrown.png"));
   });

   public efg(eet var1) {
      super(_snowman, new duk<>(0.0F), 1.1F);
      this.a(new eij(this));
      this.a(new eii(this));
   }

   public vk a(bbd var1) {
      return a.get(_snowman.eM());
   }
}
