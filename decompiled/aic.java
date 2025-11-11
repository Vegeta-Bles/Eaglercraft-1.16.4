import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class aic extends akw {
   public static final Map<String, String> a = ImmutableMap.builder().put("minecraft:illager_beast_spawn_egg", "minecraft:ravager_spawn_egg").build();

   public aic(Schema var1, boolean var2) {
      super("EntityRavagerRenameFix", _snowman, _snowman);
   }

   @Override
   protected String a(String var1) {
      return Objects.equals("minecraft:illager_beast", _snowman) ? "minecraft:ravager" : _snowman;
   }
}
