package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;

public class EntityRidingToPassengerFix extends DataFix {
   public EntityRidingToPassengerFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      Schema _snowmanx = this.getOutputSchema();
      Type<?> _snowmanxx = _snowman.getTypeRaw(TypeReferences.ENTITY_TREE);
      Type<?> _snowmanxxx = _snowmanx.getTypeRaw(TypeReferences.ENTITY_TREE);
      Type<?> _snowmanxxxx = _snowman.getTypeRaw(TypeReferences.ENTITY);
      return this.fixEntityTree(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule fixEntityTree(
      Schema inputSchema, Schema outputSchema, Type<OldEntityTree> inputEntityTreeType, Type<NewEntityTree> outputEntityTreeType, Type<Entity> inputEntityType
   ) {
      Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> _snowman = DSL.named(
         TypeReferences.ENTITY_TREE.typeName(), DSL.and(DSL.optional(DSL.field("Riding", inputEntityTreeType)), inputEntityType)
      );
      Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> _snowmanx = DSL.named(
         TypeReferences.ENTITY_TREE.typeName(), DSL.and(DSL.optional(DSL.field("Passengers", DSL.list(outputEntityTreeType))), inputEntityType)
      );
      Type<?> _snowmanxx = inputSchema.getType(TypeReferences.ENTITY_TREE);
      Type<?> _snowmanxxx = outputSchema.getType(TypeReferences.ENTITY_TREE);
      if (!Objects.equals(_snowmanxx, _snowman)) {
         throw new IllegalStateException("Old entity type is not what was expected.");
      } else if (!_snowmanxxx.equals(_snowmanx, true, true)) {
         throw new IllegalStateException("New entity type is not what was expected.");
      } else {
         OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> _snowmanxxxx = DSL.typeFinder(_snowman);
         OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> _snowmanxxxxx = DSL.typeFinder(_snowmanx);
         OpticFinder<NewEntityTree> _snowmanxxxxxx = DSL.typeFinder(outputEntityTreeType);
         Type<?> _snowmanxxxxxxx = inputSchema.getType(TypeReferences.PLAYER);
         Type<?> _snowmanxxxxxxxx = outputSchema.getType(TypeReferences.PLAYER);
         return TypeRewriteRule.seq(
            this.fixTypeEverywhere(
               "EntityRidingToPassengerFix",
               _snowman,
               _snowmanx,
               _snowmanxxxxxxxxx -> _snowmanxxxxxxxxxxx -> {
                     Optional<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> _snowmanxxxxxxx = Optional.empty();
                     Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx;

                     while (true) {
                        Either<List<NewEntityTree>, Unit> _snowmanxxxxxxxxxxxx = (Either<List<NewEntityTree>, Unit>)DataFixUtils.orElse(
                           _snowmanxxxxxxx.map(
                              _snowmanxxxxxxxxxxxxxxxxxxxx -> {
                                 Typed<NewEntityTree> _snowmanxxxxx = (Typed<NewEntityTree>)outputEntityTreeType.pointTyped(_snowmanxxxxx)
                                    .orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
                                 NewEntityTree _snowmanxxxxxx = (NewEntityTree)_snowmanxxxxx.set(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx)
                                    .getOptional(_snowman)
                                    .orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                                 return Either.left(ImmutableList.of(_snowmanxxxxxx));
                              }
                           ),
                           Either.right(DSL.unit())
                        );
                        _snowmanxxxxxxx = Optional.of(
                           Pair.of(TypeReferences.ENTITY_TREE.typeName(), Pair.of(_snowmanxxxxxxxxxxxx, ((Pair)_snowmanxxxxxxxxxxx.getSecond()).getSecond()))
                        );
                        Optional<OldEntityTree> _snowmanxxxxxxxxxxxxx = ((Either)((Pair)_snowmanxxxxxxxxxxx.getSecond()).getFirst()).left();
                        if (!_snowmanxxxxxxxxxxxxx.isPresent()) {
                           return _snowmanxxxxxxx.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                        }

                        _snowmanxxxxxxxxxxx = (Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>)new Typed(inputEntityTreeType, _snowmanxxxxx, _snowmanxxxxxxxxxxxxx.get())
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
