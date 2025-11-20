package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema703 extends Schema {
   public Schema703(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowmanx.remove("EntityHorse");
      _snowman.register(
         _snowmanx,
         "Horse",
         () -> DSL.optionalFields("ArmorItem", TypeReferences.ITEM_STACK.in(_snowman), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      _snowman.register(
         _snowmanx,
         "Donkey",
         () -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      _snowman.register(
         _snowmanx,
         "Mule",
         () -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman)), "SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman))
      );
      _snowman.register(_snowmanx, "ZombieHorse", () -> DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman)));
      _snowman.register(_snowmanx, "SkeletonHorse", () -> DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(_snowman), Schema100.targetItems(_snowman)));
      return _snowmanx;
   }
}
