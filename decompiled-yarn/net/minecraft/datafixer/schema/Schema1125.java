package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema1125 extends IdentifierNormalizingSchema {
   public Schema1125(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerBlockEntities(_snowman);
      _snowman.registerSimple(_snowmanx, "minecraft:bed");
      return _snowmanx;
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(
         false,
         TypeReferences.ADVANCEMENTS,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.BIOME.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(_snowman), DSL.constType(DSL.string())))
            )
      );
      _snowman.registerType(false, TypeReferences.BIOME, () -> DSL.constType(getIdentifierType()));
      _snowman.registerType(false, TypeReferences.ENTITY_NAME, () -> DSL.constType(getIdentifierType()));
   }
}
