import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class aiq extends akw {
   public static final Map<String, String> a = ImmutableMap.builder().put("minecraft:zombie_pigman_spawn_egg", "minecraft:zombified_piglin_spawn_egg").build();

   public aiq(Schema var1) {
      super("EntityZombifiedPiglinRenameFix", _snowman, true);
   }

   @Override
   protected String a(String var1) {
      return Objects.equals("minecraft:zombie_pigman", _snowman) ? "minecraft:zombified_piglin" : _snowman;
   }
}
