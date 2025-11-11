import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public abstract class aki extends DataFix {
   public aki(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(akn.j.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(akn.j))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI rename", _snowman, var1x -> var1xx -> var1xx.mapSecond(this::a));
      }
   }

   private <T> Dynamic<T> a(Dynamic<T> var1) {
      return _snowman.update(
         "Sections",
         var1x -> var1x.updateMapValues(
               var1xx -> var1xx.mapSecond(var1xxx -> var1xxx.update("Records", var1xxxx -> (Dynamic)DataFixUtils.orElse(this.b(var1xxxx), var1xxxx)))
            )
      );
   }

   private <T> Optional<Dynamic<T>> b(Dynamic<T> var1) {
      return _snowman.asStreamOpt()
         .map(
            var2 -> _snowman.createList(
                  var2.map(
                     var1x -> var1x.update(
                           "type", var1xx -> (Dynamic)DataFixUtils.orElse(var1xx.asString().map(this::a).map(var1xx::createString).result(), var1xx)
                        )
                  )
               )
         )
         .result();
   }

   protected abstract String a(String var1);
}
