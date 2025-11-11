import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class aiv extends DataFix {
   public aiv(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.t);
      Type<?> _snowmanx = this.getOutputSchema().getType(akn.t);
      return this.writeFixAndRead("IglooMetadataRemovalFix", _snowman, _snowmanx, aiv::a);
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0) {
      boolean _snowman = _snowman.get("Children").asStreamOpt().map(var0x -> var0x.allMatch(aiv::c)).result().orElse(false);
      return _snowman ? _snowman.set("id", _snowman.createString("Igloo")).remove("Children") : _snowman.update("Children", aiv::b);
   }

   private static <T> Dynamic<T> b(Dynamic<T> var0) {
      return _snowman.asStreamOpt().map(var0x -> var0x.filter(var0xx -> !c((Dynamic<?>)var0xx))).map(_snowman::createList).result().orElse(_snowman);
   }

   private static boolean c(Dynamic<?> var0) {
      return _snowman.get("id").asString("").equals("Iglu");
   }
}
