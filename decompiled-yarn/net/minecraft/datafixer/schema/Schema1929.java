package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1929 extends IdentifierNormalizingSchema {
   public Schema1929(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowman.register(
         _snowmanx,
         "minecraft:wandering_trader",
         _snowmanxx -> DSL.optionalFields(
               "Inventory",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "Offers",
               DSL.optionalFields(
                  "Recipes",
                  DSL.list(
                     DSL.optionalFields(
                        "buy", TypeReferences.ITEM_STACK.in(_snowman), "buyB", TypeReferences.ITEM_STACK.in(_snowman), "sell", TypeReferences.ITEM_STACK.in(_snowman)
                     )
                  )
               ),
               Schema100.targetItems(_snowman)
            )
      );
      _snowman.register(
         _snowmanx,
         "minecraft:trader_llama",
         _snowmanxx -> DSL.optionalFields(
               "Items",
               DSL.list(TypeReferences.ITEM_STACK.in(_snowman)),
               "SaddleItem",
               TypeReferences.ITEM_STACK.in(_snowman),
               "DecorItem",
               TypeReferences.ITEM_STACK.in(_snowman),
               Schema100.targetItems(_snowman)
            )
      );
      return _snowmanx;
   }
}
