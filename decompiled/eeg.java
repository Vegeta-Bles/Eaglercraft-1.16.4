import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class eeg<T extends bba> extends edv<T, dtq<T>> {
   private static final Map<aqe<?>, vk> a = Maps.newHashMap(
      ImmutableMap.of(aqe.o, new vk("textures/entity/horse/donkey.png"), aqe.aa, new vk("textures/entity/horse/mule.png"))
   );

   public eeg(eet var1, float var2) {
      super(_snowman, new dtq<>(0.0F), _snowman);
   }

   public vk a(T var1) {
      return a.get(_snowman.X());
   }
}
