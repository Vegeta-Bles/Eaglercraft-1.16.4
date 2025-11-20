package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class MissingDimensionFix extends DataFix {
   public MissingDimensionFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   private static <A> Type<Pair<A, Dynamic<?>>> method_29913(String _snowman, Type<A> _snowman) {
      return DSL.and(DSL.field(_snowman, _snowman), DSL.remainderType());
   }

   private static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> method_29915(String _snowman, Type<A> _snowman) {
      return DSL.and(DSL.optional(DSL.field(_snowman, _snowman)), DSL.remainderType());
   }

   private static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> method_29914(String _snowman, Type<A1> _snowman, String _snowman, Type<A2> _snowman) {
      return DSL.and(DSL.optional(DSL.field(_snowman, _snowman)), DSL.optional(DSL.field(_snowman, _snowman)), DSL.remainderType());
   }

   protected TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      TaggedChoiceType<String> _snowmanx = new TaggedChoiceType(
         "type",
         DSL.string(),
         ImmutableMap.of(
            "minecraft:debug",
            DSL.remainderType(),
            "minecraft:flat",
            method_29915(
               "settings",
               method_29914("biome", _snowman.getType(TypeReferences.BIOME), "layers", DSL.list(method_29915("block", _snowman.getType(TypeReferences.BLOCK_NAME))))
            ),
            "minecraft:noise",
            method_29914(
               "biome_source",
               DSL.taggedChoiceType(
                  "type",
                  DSL.string(),
                  ImmutableMap.of(
                     "minecraft:fixed",
                     method_29913("biome", _snowman.getType(TypeReferences.BIOME)),
                     "minecraft:multi_noise",
                     DSL.list(method_29913("biome", _snowman.getType(TypeReferences.BIOME))),
                     "minecraft:checkerboard",
                     method_29913("biomes", DSL.list(_snowman.getType(TypeReferences.BIOME))),
                     "minecraft:vanilla_layered",
                     DSL.remainderType(),
                     "minecraft:the_end",
                     DSL.remainderType()
                  )
               ),
               "settings",
               DSL.or(DSL.string(), method_29914("default_block", _snowman.getType(TypeReferences.BLOCK_NAME), "default_fluid", _snowman.getType(TypeReferences.BLOCK_NAME)))
            )
         )
      );
      CompoundListType<String, ?> _snowmanxx = DSL.compoundList(IdentifierNormalizingSchema.getIdentifierType(), method_29913("generator", _snowmanx));
      Type<?> _snowmanxxx = DSL.and(_snowmanxx, DSL.remainderType());
      Type<?> _snowmanxxxx = _snowman.getType(TypeReferences.CHUNK_GENERATOR_SETTINGS);
      FieldFinder<?> _snowmanxxxxx = new FieldFinder("dimensions", _snowmanxxx);
      if (!_snowmanxxxx.findFieldType("dimensions").equals(_snowmanxxx)) {
         throw new IllegalStateException();
      } else {
         OpticFinder<? extends List<? extends Pair<String, ?>>> _snowmanxxxxxx = _snowmanxx.finder();
         return this.fixTypeEverywhereTyped(
            "MissingDimensionFix", _snowmanxxxx, _snowmanxxxxxxx -> _snowmanxxxxxxx.updateTyped(_snowman, _snowmanxxxxxxxx -> _snowmanxxxxxxxx.updateTyped(_snowman, _snowmanxxxxxxxxxx -> {
                     if (!(_snowmanxxxxxxxxxx.getValue() instanceof List)) {
                        throw new IllegalStateException("List exptected");
                     } else if (((List)_snowmanxxxxxxxxxx.getValue()).isEmpty()) {
                        Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
                        Dynamic<?> _snowmanxxxxxxxxxx = this.method_29912(_snowmanxxx);
                        return (Typed)DataFixUtils.orElse(_snowman.readTyped(_snowmanxxxxxxxxxx).result().map(Pair::getFirst), _snowmanxxxxxxxxxx);
                     } else {
                        return _snowmanxxxxxxxxxx;
                     }
                  }))
         );
      }
   }

   private <T> Dynamic<T> method_29912(Dynamic<T> _snowman) {
      long _snowmanx = _snowman.get("seed").asLong(0L);
      return new Dynamic(_snowman.getOps(), StructureSeparationDataFix.method_29917(_snowman, _snowmanx, StructureSeparationDataFix.method_29916(_snowman, _snowmanx), false));
   }
}
