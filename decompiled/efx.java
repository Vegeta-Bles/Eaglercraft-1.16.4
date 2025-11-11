import com.google.common.collect.Maps;
import java.util.Map;

public class efx extends efw<baj, dtv<baj>> {
   private static final Map<baj.a, vk> a = x.a(Maps.newHashMap(), var0 -> {
      var0.put(baj.a.b, new vk("textures/entity/cow/brown_mooshroom.png"));
      var0.put(baj.a.a, new vk("textures/entity/cow/red_mooshroom.png"));
   });

   public efx(eet var1) {
      super(_snowman, new dtv<>(), 0.7F);
      this.a(new eip<>(this));
   }

   public vk a(baj var1) {
      return a.get(_snowman.eL());
   }
}
