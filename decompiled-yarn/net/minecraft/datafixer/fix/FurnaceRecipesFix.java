package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class FurnaceRecipesFix extends DataFix {
   public FurnaceRecipesFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      return this.updateBlockEntities(this.getOutputSchema().getTypeRaw(TypeReferences.RECIPE));
   }

   private <R> TypeRewriteRule updateBlockEntities(Type<R> _snowman) {
      Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> _snowmanx = DSL.and(
         DSL.optional(DSL.field("RecipesUsed", DSL.and(DSL.compoundList(_snowman, DSL.intType()), DSL.remainderType()))), DSL.remainderType()
      );
      OpticFinder<?> _snowmanxx = DSL.namedChoice("minecraft:furnace", this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:furnace"));
      OpticFinder<?> _snowmanxxx = DSL.namedChoice(
         "minecraft:blast_furnace", this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:blast_furnace")
      );
      OpticFinder<?> _snowmanxxxx = DSL.namedChoice("minecraft:smoker", this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:smoker"));
      Type<?> _snowmanxxxxx = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:furnace");
      Type<?> _snowmanxxxxxx = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:blast_furnace");
      Type<?> _snowmanxxxxxxx = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:smoker");
      Type<?> _snowmanxxxxxxxx = this.getInputSchema().getType(TypeReferences.BLOCK_ENTITY);
      Type<?> _snowmanxxxxxxxxx = this.getOutputSchema().getType(TypeReferences.BLOCK_ENTITY);
      return this.fixTypeEverywhereTyped(
         "FurnaceRecipesFix",
         _snowmanxxxxxxxx,
         _snowmanxxxxxxxxx,
         _snowmanxxxxxxxxxx -> _snowmanxxxxxxxxxx.updateTyped(_snowman, _snowman, _snowmanxxxxxxxxxxx -> this.updateBlockEntityData(_snowman, _snowman, _snowmanxxxxxxxxxxx))
               .updateTyped(_snowman, _snowman, _snowmanxxxxxxxxxxx -> this.updateBlockEntityData(_snowman, _snowman, _snowmanxxxxxxxxxxx))
               .updateTyped(_snowman, _snowman, _snowmanxxxxxxxxxxx -> this.updateBlockEntityData(_snowman, _snowman, _snowmanxxxxxxxxxxx))
      );
   }

   private <R> Typed<?> updateBlockEntityData(Type<R> _snowman, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> _snowman, Typed<?> _snowman) {
      Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowman.getOrCreate(DSL.remainderFinder());
      int _snowmanxxxx = _snowmanxxx.get("RecipesUsedSize").asInt(0);
      _snowmanxxx = _snowmanxxx.remove("RecipesUsedSize");
      List<Pair<R, Integer>> _snowmanxxxxx = Lists.newArrayList();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
         String _snowmanxxxxxxx = "RecipeLocation" + _snowmanxxxxxx;
         String _snowmanxxxxxxxx = "RecipeAmount" + _snowmanxxxxxx;
         Optional<? extends Dynamic<?>> _snowmanxxxxxxxxx = _snowmanxxx.get(_snowmanxxxxxxx).result();
         int _snowmanxxxxxxxxxx = _snowmanxxx.get(_snowmanxxxxxxxx).asInt(0);
         if (_snowmanxxxxxxxxxx > 0) {
            _snowmanxxxxxxxxx.ifPresent(_snowmanxxxxxxxxxxx -> {
               Optional<? extends Pair<R, ? extends Dynamic<?>>> _snowmanxxxxxxxxxxxx = _snowman.read(_snowmanxxxxxxxxxxx).result();
               _snowmanxxxxxxxxxxxx.ifPresent(_snowmanxxxxxxxxxxxxx -> _snowman.add(Pair.of(_snowmanxxxxxxxxxxxxx.getFirst(), _snowman)));
            });
         }

         _snowmanxxx = _snowmanxxx.remove(_snowmanxxxxxxx).remove(_snowmanxxxxxxxx);
      }

      return _snowman.set(DSL.remainderFinder(), _snowman, Pair.of(Either.left(Pair.of(_snowmanxxxxx, _snowmanxxx.emptyMap())), _snowmanxxx));
   }
}
