import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class aif extends DataFix {
   public aif(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      Schema _snowmanx = this.getOutputSchema();
      Type<?> _snowmanxx = _snowman.getTypeRaw(akn.o);
      Type<?> _snowmanxxx = _snowmanx.getTypeRaw(akn.o);
      Type<?> _snowmanxxxx = _snowman.getTypeRaw(akn.p);
      return this.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule a(
      Schema var1, Schema var2, Type<OldEntityTree> var3, Type<NewEntityTree> var4, Type<Entity> var5
   ) {
      Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> _snowman = DSL.named(akn.o.typeName(), DSL.and(DSL.optional(DSL.field("Riding", _snowman)), _snowman));
      Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> _snowmanx = DSL.named(
         akn.o.typeName(), DSL.and(DSL.optional(DSL.field("Passengers", DSL.list(_snowman))), _snowman)
      );
      Type<?> _snowmanxx = _snowman.getType(akn.o);
      Type<?> _snowmanxxx = _snowman.getType(akn.o);
      if (!Objects.equals(_snowmanxx, _snowman)) {
         throw new IllegalStateException("Old entity type is not what was expected.");
      } else if (!_snowmanxxx.equals(_snowmanx, true, true)) {
         throw new IllegalStateException("New entity type is not what was expected.");
      } else {
         OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> _snowmanxxxx = DSL.typeFinder(_snowman);
         OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> _snowmanxxxxx = DSL.typeFinder(_snowmanx);
         OpticFinder<NewEntityTree> _snowmanxxxxxx = DSL.typeFinder(_snowman);
         Type<?> _snowmanxxxxxxx = _snowman.getType(akn.b);
         Type<?> _snowmanxxxxxxxx = _snowman.getType(akn.b);
         return TypeRewriteRule.seq(
            this.fixTypeEverywhere(
               "EntityRidingToPassengerFix",
               _snowman,
               _snowmanx,
               var5x -> var6x -> {
                     Optional<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> _snowmanxxxxxxxxx = Optional.empty();
                     Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>> _snowmanx = var6x;

                     while (true) {
                        Either<List<NewEntityTree>, Unit> _snowmanxx = (Either<List<NewEntityTree>, Unit>)DataFixUtils.orElse(
                           _snowmanxxxxxxxxx.map(
                              var4x -> {
                                 Typed<NewEntityTree> _snowmanxxx = (Typed<NewEntityTree>)_snowman.pointTyped(var5x)
                                    .orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
                                 NewEntityTree _snowmanx = (NewEntityTree)_snowmanxxx.set(_snowman, var4x)
                                    .getOptional(_snowman)
                                    .orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                                 return Either.left(ImmutableList.of(_snowmanx));
                              }
                           ),
                           Either.right(DSL.unit())
                        );
                        _snowmanxxxxxxxxx = Optional.of(Pair.of(akn.o.typeName(), Pair.of(_snowmanxx, ((Pair)_snowmanx.getSecond()).getSecond())));
                        Optional<OldEntityTree> _snowmanxxx = ((Either)((Pair)_snowmanx.getSecond()).getFirst()).left();
                        if (!_snowmanxxx.isPresent()) {
                           return _snowmanxxxxxxxxx.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                        }

                        _snowmanx = (Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>)new Typed(_snowman, var5x, _snowmanxxx.get())
                           .getOptional(_snowman)
                           .orElseThrow(() -> new IllegalStateException("Should always have an entity here"));
                     }
                  }
            ),
            this.writeAndRead("player RootVehicle injecter", _snowmanxxxxxxx, _snowmanxxxxxxxx)
         );
      }
   }
}
