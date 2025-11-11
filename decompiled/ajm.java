import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;

public class ajm extends DataFix {
   private static final Map<String, String> a = ImmutableMap.builder()
      .put("down", "down_south")
      .put("up", "up_north")
      .put("north", "north_up")
      .put("south", "south_up")
      .put("west", "west_up")
      .put("east", "east_up")
      .build();

   public ajm(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      Optional<String> _snowman = _snowman.get("Name").asString().result();
      return _snowman.equals(Optional.of("minecraft:jigsaw")) ? _snowman.update("Properties", var0x -> {
         String _snowmanx = var0x.get("facing").asString("north");
         return var0x.remove("facing").set("orientation", var0x.createString(a.getOrDefault(_snowmanx, _snowmanx)));
      }) : _snowman;
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("jigsaw_rotation_fix", this.getInputSchema().getType(akn.m), var0 -> var0.update(DSL.remainderFinder(), ajm::a));
   }
}
