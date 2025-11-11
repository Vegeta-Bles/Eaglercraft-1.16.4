import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Set;

public class alh extends DataFix {
   private static final Set<String> a = ImmutableSet.of(
      "minecraft:andesite_wall",
      "minecraft:brick_wall",
      "minecraft:cobblestone_wall",
      "minecraft:diorite_wall",
      "minecraft:end_stone_brick_wall",
      "minecraft:granite_wall",
      new String[]{
         "minecraft:mossy_cobblestone_wall",
         "minecraft:mossy_stone_brick_wall",
         "minecraft:nether_brick_wall",
         "minecraft:prismarine_wall",
         "minecraft:red_nether_brick_wall",
         "minecraft:red_sandstone_wall",
         "minecraft:sandstone_wall",
         "minecraft:stone_brick_wall"
      }
   );

   public alh(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("WallPropertyFix", this.getInputSchema().getType(akn.m), var0 -> var0.update(DSL.remainderFinder(), alh::a));
   }

   private static String a(String var0) {
      return "true".equals(_snowman) ? "low" : "none";
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0, String var1) {
      return _snowman.update(_snowman, var0x -> (Dynamic)DataFixUtils.orElse(var0x.asString().result().map(alh::a).map(var0x::createString), var0x));
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      boolean _snowman = _snowman.get("Name").asString().result().filter(a::contains).isPresent();
      return !_snowman ? _snowman : _snowman.update("Properties", var0x -> {
         Dynamic<?> _snowmanx = a(var0x, "east");
         _snowmanx = a((Dynamic<T>)_snowmanx, "west");
         _snowmanx = a((Dynamic<T>)_snowmanx, "north");
         return a((Dynamic<T>)_snowmanx, "south");
      });
   }
}
