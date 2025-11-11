import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;

public class aho extends akw {
   public static final Map<String, String> a = ImmutableMap.builder()
      .put("minecraft:salmon_mob", "minecraft:salmon")
      .put("minecraft:cod_mob", "minecraft:cod")
      .build();
   public static final Map<String, String> b = ImmutableMap.builder()
      .put("minecraft:salmon_mob_spawn_egg", "minecraft:salmon_spawn_egg")
      .put("minecraft:cod_mob_spawn_egg", "minecraft:cod_spawn_egg")
      .build();

   public aho(Schema var1, boolean var2) {
      super("EntityCodSalmonFix", _snowman, _snowman);
   }

   @Override
   protected String a(String var1) {
      return a.getOrDefault(_snowman, _snowman);
   }
}
