package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1451v6 extends IdentifierNormalizingSchema {
   public Schema1451v6(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      Supplier<TypeTemplate> _snowmanx = () -> DSL.compoundList(TypeReferences.ITEM_NAME.in(_snowman), DSL.constType(DSL.intType()));
      _snowman.registerType(
         false,
         TypeReferences.STATS,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(TypeReferences.BLOCK_NAME.in(_snowman), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  _snowman.get(),
                  "minecraft:used",
                  _snowman.get(),
                  "minecraft:broken",
                  _snowman.get(),
                  "minecraft:picked_up",
                  _snowman.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     _snowman.get(),
                     "minecraft:killed",
                     DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(getIdentifierType()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
   }
}
