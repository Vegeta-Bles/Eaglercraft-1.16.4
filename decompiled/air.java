import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class air extends DataFix {
   public air(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, Dynamic<?>>> _snowman = DSL.named(akn.j.typeName(), DSL.remainderType());
      if (!Objects.equals(_snowman, this.getInputSchema().getType(akn.j))) {
         throw new IllegalStateException("Poi type is not what was expected.");
      } else {
         return this.fixTypeEverywhere("POI rebuild", _snowman, var0 -> var0x -> var0x.mapSecond(air::a));
      }
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      return _snowman.update("Sections", var0x -> var0x.updateMapValues(var0xx -> var0xx.mapSecond(var0xxx -> var0xxx.remove("Valid"))));
   }
}
