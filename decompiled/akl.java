import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;

public class akl extends akk {
   private static final Map<String, String> a = ImmutableMap.builder()
      .put("minecraft:acacia_bark", "minecraft:acacia_wood")
      .put("minecraft:birch_bark", "minecraft:birch_wood")
      .put("minecraft:dark_oak_bark", "minecraft:dark_oak_wood")
      .put("minecraft:jungle_bark", "minecraft:jungle_wood")
      .put("minecraft:oak_bark", "minecraft:oak_wood")
      .put("minecraft:spruce_bark", "minecraft:spruce_wood")
      .build();

   public akl(Schema var1, boolean var2) {
      super(_snowman, _snowman, "Recipes renamening fix", var0 -> a.getOrDefault(var0, var0));
   }
}
