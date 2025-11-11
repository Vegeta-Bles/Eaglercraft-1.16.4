import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class ege extends efh<aqn, dvc<aqn>> {
   private static final Map<aqe<?>, vk> a = ImmutableMap.of(
      aqe.ai,
      new vk("textures/entity/piglin/piglin.png"),
      aqe.bb,
      new vk("textures/entity/piglin/zombified_piglin.png"),
      aqe.aj,
      new vk("textures/entity/piglin/piglin_brute.png")
   );

   public ege(eet var1, boolean var2) {
      super(_snowman, a(_snowman), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
      this.a(new eik<>(this, new dum(0.5F), new dum(1.02F)));
   }

   private static dvc<aqn> a(boolean var0) {
      dvc<aqn> _snowman = new dvc<>(0.0F, 64, 64);
      if (_snowman) {
         _snowman.b.h = false;
      }

      return _snowman;
   }

   @Override
   public vk a(aqn var1) {
      vk _snowman = a.get(_snowman.X());
      if (_snowman == null) {
         throw new IllegalArgumentException("I don't know what texture to use for " + _snowman.X());
      } else {
         return _snowman;
      }
   }

   protected boolean c(aqn var1) {
      return _snowman instanceof ber && ((ber)_snowman).eL();
   }
}
