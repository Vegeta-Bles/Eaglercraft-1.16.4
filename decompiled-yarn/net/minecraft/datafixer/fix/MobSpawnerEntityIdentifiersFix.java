package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class MobSpawnerEntityIdentifiersFix extends DataFix {
   public MobSpawnerEntityIdentifiersFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   private Dynamic<?> fixSpawner(Dynamic<?> _snowman) {
      if (!"MobSpawner".equals(_snowman.get("id").asString(""))) {
         return _snowman;
      } else {
         Optional<String> _snowmanx = _snowman.get("EntityId").asString().result();
         if (_snowmanx.isPresent()) {
            Dynamic<?> _snowmanxx = (Dynamic<?>)DataFixUtils.orElse(_snowman.get("SpawnData").result(), _snowman.emptyMap());
            _snowmanxx = _snowmanxx.set("id", _snowmanxx.createString(_snowmanx.get().isEmpty() ? "Pig" : _snowmanx.get()));
            _snowman = _snowman.set("SpawnData", _snowmanxx);
            _snowman = _snowman.remove("EntityId");
         }

         Optional<? extends Stream<? extends Dynamic<?>>> _snowmanxx = _snowman.get("SpawnPotentials").asStreamOpt().result();
         if (_snowmanxx.isPresent()) {
            _snowman = _snowman.set(
               "SpawnPotentials",
               _snowman.createList(
                  _snowmanxx.get()
                     .map(
                        _snowmanxxx -> {
                           Optional<String> _snowmanxxxx = _snowmanxxx.get("Type").asString().result();
                           if (_snowmanxxxx.isPresent()) {
                              Dynamic<?> _snowmanxxxxx = ((Dynamic)DataFixUtils.orElse(_snowmanxxx.get("Properties").result(), _snowmanxxx.emptyMap()))
                                 .set("id", _snowmanxxx.createString(_snowmanxxxx.get()));
                              return _snowmanxxx.set("Entity", _snowmanxxxxx).remove("Type").remove("Properties");
                           } else {
                              return _snowmanxxx;
                           }
                        }
                     )
               )
            );
         }

         return _snowman;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(TypeReferences.UNTAGGED_SPAWNER);
      return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(TypeReferences.UNTAGGED_SPAWNER), _snowman, _snowmanx -> {
         Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanx.get(DSL.remainderFinder());
         _snowmanx = _snowmanx.set("id", _snowmanx.createString("MobSpawner"));
         DataResult<? extends Pair<? extends Typed<?>, ?>> _snowmanxx = _snowman.readTyped(this.fixSpawner(_snowmanx));
         return !_snowmanxx.result().isPresent() ? _snowmanx : (Typed)((Pair)_snowmanxx.result().get()).getFirst();
      });
   }
}
