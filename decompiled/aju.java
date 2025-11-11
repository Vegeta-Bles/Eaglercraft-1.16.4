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

public class aju extends DataFix {
   public aju(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private Dynamic<?> a(Dynamic<?> var1) {
      if (!"MobSpawner".equals(_snowman.get("id").asString(""))) {
         return _snowman;
      } else {
         Optional<String> _snowman = _snowman.get("EntityId").asString().result();
         if (_snowman.isPresent()) {
            Dynamic<?> _snowmanx = (Dynamic<?>)DataFixUtils.orElse(_snowman.get("SpawnData").result(), _snowman.emptyMap());
            _snowmanx = _snowmanx.set("id", _snowmanx.createString(_snowman.get().isEmpty() ? "Pig" : _snowman.get()));
            _snowman = _snowman.set("SpawnData", _snowmanx);
            _snowman = _snowman.remove("EntityId");
         }

         Optional<? extends Stream<? extends Dynamic<?>>> _snowmanx = _snowman.get("SpawnPotentials").asStreamOpt().result();
         if (_snowmanx.isPresent()) {
            _snowman = _snowman.set("SpawnPotentials", _snowman.createList(_snowmanx.get().map(var0 -> {
               Optional<String> _snowmanxx = var0.get("Type").asString().result();
               if (_snowmanxx.isPresent()) {
                  Dynamic<?> _snowmanx = ((Dynamic)DataFixUtils.orElse(var0.get("Properties").result(), var0.emptyMap())).set("id", var0.createString(_snowmanxx.get()));
                  return var0.set("Entity", _snowmanx).remove("Type").remove("Properties");
               } else {
                  return (Dynamic)var0;
               }
            })));
         }

         return _snowman;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(akn.s);
      return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(akn.s), _snowman, var2 -> {
         Dynamic<?> _snowmanx = (Dynamic<?>)var2.get(DSL.remainderFinder());
         _snowmanx = _snowmanx.set("id", _snowmanx.createString("MobSpawner"));
         DataResult<? extends Pair<? extends Typed<?>, ?>> _snowmanx = _snowman.readTyped(this.a(_snowmanx));
         return !_snowmanx.result().isPresent() ? var2 : (Typed)((Pair)_snowmanx.result().get()).getFirst();
      });
   }
}
