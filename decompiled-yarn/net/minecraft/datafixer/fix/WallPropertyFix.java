package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Set;
import net.minecraft.datafixer.TypeReferences;

public class WallPropertyFix extends DataFix {
   private static final Set<String> TARGET_BLOCK_IDS = ImmutableSet.of(
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

   public WallPropertyFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "WallPropertyFix",
         this.getInputSchema().getType(TypeReferences.BLOCK_STATE),
         _snowman -> _snowman.update(DSL.remainderFinder(), WallPropertyFix::updateWallProperties)
      );
   }

   private static String booleanToWallType(String value) {
      return "true".equals(value) ? "low" : "none";
   }

   private static <T> Dynamic<T> updateWallValueReference(Dynamic<T> _snowman, String _snowman) {
      return _snowman.update(_snowman, _snowmanxx -> (Dynamic)DataFixUtils.orElse(_snowmanxx.asString().result().map(WallPropertyFix::booleanToWallType).map(_snowmanxx::createString), _snowmanxx));
   }

   private static <T> Dynamic<T> updateWallProperties(Dynamic<T> _snowman) {
      boolean _snowmanx = _snowman.get("Name").asString().result().filter(TARGET_BLOCK_IDS::contains).isPresent();
      return !_snowmanx ? _snowman : _snowman.update("Properties", _snowmanxx -> {
         Dynamic<?> _snowmanxxx = updateWallValueReference(_snowmanxx, "east");
         _snowmanxxx = updateWallValueReference((Dynamic<T>)_snowmanxxx, "west");
         _snowmanxxx = updateWallValueReference((Dynamic<T>)_snowmanxxx, "north");
         return updateWallValueReference((Dynamic<T>)_snowmanxxx, "south");
      });
   }
}
