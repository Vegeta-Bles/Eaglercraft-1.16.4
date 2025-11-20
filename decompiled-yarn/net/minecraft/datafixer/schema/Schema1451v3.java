package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1451v3 extends IdentifierNormalizingSchema {
   public Schema1451v3(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowman.registerSimple(_snowmanx, "minecraft:egg");
      _snowman.registerSimple(_snowmanx, "minecraft:ender_pearl");
      _snowman.registerSimple(_snowmanx, "minecraft:fireball");
      _snowman.register(_snowmanx, "minecraft:potion", _snowmanxx -> DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(_snowman)));
      _snowman.registerSimple(_snowmanx, "minecraft:small_fireball");
      _snowman.registerSimple(_snowmanx, "minecraft:snowball");
      _snowman.registerSimple(_snowmanx, "minecraft:wither_skull");
      _snowman.registerSimple(_snowmanx, "minecraft:xp_bottle");
      _snowman.register(_snowmanx, "minecraft:arrow", () -> DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(_snowman)));
      _snowman.register(_snowmanx, "minecraft:enderman", () -> DSL.optionalFields("carriedBlockState", TypeReferences.BLOCK_STATE.in(_snowman), Schema100.targetItems(_snowman)));
      _snowman.register(
         _snowmanx,
         "minecraft:falling_block",
         () -> DSL.optionalFields("BlockState", TypeReferences.BLOCK_STATE.in(_snowman), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(_snowman))
      );
      _snowman.register(_snowmanx, "minecraft:spectral_arrow", () -> DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(_snowman)));
      _snowman.register(
         _snowmanx,
         "minecraft:chest_minecart",
         () -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      _snowman.register(_snowmanx, "minecraft:commandblock_minecart", () -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      _snowman.register(_snowmanx, "minecraft:furnace_minecart", () -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      _snowman.register(
         _snowmanx,
         "minecraft:hopper_minecart",
         () -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman), "Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)))
      );
      _snowman.register(_snowmanx, "minecraft:minecart", () -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      _snowman.register(
         _snowmanx, "minecraft:spawner_minecart", () -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman), TypeReferences.UNTAGGED_SPAWNER.in(_snowman))
      );
      _snowman.register(_snowmanx, "minecraft:tnt_minecart", () -> DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(_snowman)));
      return _snowmanx;
   }
}
