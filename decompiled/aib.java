import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class aib extends akw {
   public static final Map<String, String> a = ImmutableMap.builder().put("minecraft:puffer_fish_spawn_egg", "minecraft:pufferfish_spawn_egg").build();

   public aib(Schema var1, boolean var2) {
      super("EntityPufferfishRenameFix", _snowman, _snowman);
   }

   @Override
   protected String a(String var1) {
      return Objects.equals("minecraft:puffer_fish", _snowman) ? "minecraft:pufferfish" : _snowman;
   }
}
