import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class eil extends eit<bai, duo<bai>> {
   private static final Map<bai.a, vk> a = ImmutableMap.of(
      bai.a.b,
      new vk("textures/entity/iron_golem/iron_golem_crackiness_low.png"),
      bai.a.c,
      new vk("textures/entity/iron_golem/iron_golem_crackiness_medium.png"),
      bai.a.d,
      new vk("textures/entity/iron_golem/iron_golem_crackiness_high.png")
   );

   public eil(egk<bai, duo<bai>> var1) {
      super(_snowman);
   }

   public void a(dfm var1, eag var2, int var3, bai var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (!_snowman.bF()) {
         bai.a _snowman = _snowman.eK();
         if (_snowman != bai.a.a) {
            vk _snowmanx = a.get(_snowman);
            a(this.aC_(), _snowmanx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
         }
      }
   }
}
