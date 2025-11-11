import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class ehf extends edv<bbb, duk<bbb>> {
   private static final Map<aqe<?>, vk> a = Maps.newHashMap(
      ImmutableMap.of(aqe.aZ, new vk("textures/entity/horse/horse_zombie.png"), aqe.aw, new vk("textures/entity/horse/horse_skeleton.png"))
   );

   public ehf(eet var1) {
      super(_snowman, new duk<>(0.0F), 1.0F);
   }

   public vk a(bbb var1) {
      return a.get(_snowman.X());
   }
}
