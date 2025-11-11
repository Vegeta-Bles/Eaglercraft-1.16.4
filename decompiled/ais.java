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

public class ais extends DataFix {
   public ais(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      return this.a(this.getOutputSchema().getTypeRaw(akn.w));
   }

   private <R> TypeRewriteRule a(Type<R> var1) {
      Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> _snowman = DSL.and(
         DSL.optional(DSL.field("RecipesUsed", DSL.and(DSL.compoundList(_snowman, DSL.intType()), DSL.remainderType()))), DSL.remainderType()
      );
      OpticFinder<?> _snowmanx = DSL.namedChoice("minecraft:furnace", this.getInputSchema().getChoiceType(akn.k, "minecraft:furnace"));
      OpticFinder<?> _snowmanxx = DSL.namedChoice("minecraft:blast_furnace", this.getInputSchema().getChoiceType(akn.k, "minecraft:blast_furnace"));
      OpticFinder<?> _snowmanxxx = DSL.namedChoice("minecraft:smoker", this.getInputSchema().getChoiceType(akn.k, "minecraft:smoker"));
      Type<?> _snowmanxxxx = this.getOutputSchema().getChoiceType(akn.k, "minecraft:furnace");
      Type<?> _snowmanxxxxx = this.getOutputSchema().getChoiceType(akn.k, "minecraft:blast_furnace");
      Type<?> _snowmanxxxxxx = this.getOutputSchema().getChoiceType(akn.k, "minecraft:smoker");
      Type<?> _snowmanxxxxxxx = this.getInputSchema().getType(akn.k);
      Type<?> _snowmanxxxxxxxx = this.getOutputSchema().getType(akn.k);
      return this.fixTypeEverywhereTyped(
         "FurnaceRecipesFix",
         _snowmanxxxxxxx,
         _snowmanxxxxxxxx,
         var9x -> var9x.updateTyped(_snowman, _snowman, var3x -> this.a(_snowman, _snowman, var3x))
               .updateTyped(_snowman, _snowman, var3x -> this.a(_snowman, _snowman, var3x))
               .updateTyped(_snowman, _snowman, var3x -> this.a(_snowman, _snowman, var3x))
      );
   }

   private <R> Typed<?> a(Type<R> var1, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> var2, Typed<?> var3) {
      Dynamic<?> _snowman = (Dynamic<?>)_snowman.getOrCreate(DSL.remainderFinder());
      int _snowmanx = _snowman.get("RecipesUsedSize").asInt(0);
      _snowman = _snowman.remove("RecipesUsedSize");
      List<Pair<R, Integer>> _snowmanxx = Lists.newArrayList();

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx; _snowmanxxx++) {
         String _snowmanxxxx = "RecipeLocation" + _snowmanxxx;
         String _snowmanxxxxx = "RecipeAmount" + _snowmanxxx;
         Optional<? extends Dynamic<?>> _snowmanxxxxxx = _snowman.get(_snowmanxxxx).result();
         int _snowmanxxxxxxx = _snowman.get(_snowmanxxxxx).asInt(0);
         if (_snowmanxxxxxxx > 0) {
            _snowmanxxxxxx.ifPresent(var3x -> {
               Optional<? extends Pair<R, ? extends Dynamic<?>>> _snowmanxxxxxxxx = _snowman.read(var3x).result();
               _snowmanxxxxxxxx.ifPresent(var2x -> _snowman.add(Pair.of(var2x.getFirst(), _snowman)));
            });
         }

         _snowman = _snowman.remove(_snowmanxxxx).remove(_snowmanxxxxx);
      }

      return _snowman.set(DSL.remainderFinder(), _snowman, Pair.of(Either.left(Pair.of(_snowmanxx, _snowman.emptyMap())), _snowman));
   }
}
