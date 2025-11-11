import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;

public class aku extends DataFix {
   public aku(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.writeFixAndRead("SavedDataVillageCropFix", this.getInputSchema().getType(akn.t), this.getOutputSchema().getType(akn.t), this::a);
   }

   private <T> Dynamic<T> a(Dynamic<T> var1) {
      return _snowman.update("Children", aku::b);
   }

   private static <T> Dynamic<T> b(Dynamic<T> var0) {
      return _snowman.asStreamOpt().map(aku::a).map(_snowman::createList).result().orElse(_snowman);
   }

   private static Stream<? extends Dynamic<?>> a(Stream<? extends Dynamic<?>> var0) {
      return _snowman.map(var0x -> {
         String _snowman = var0x.get("id").asString("");
         if ("ViF".equals(_snowman)) {
            return c(var0x);
         } else {
            return "ViDF".equals(_snowman) ? d(var0x) : var0x;
         }
      });
   }

   private static <T> Dynamic<T> c(Dynamic<T> var0) {
      _snowman = a(_snowman, "CA");
      return a(_snowman, "CB");
   }

   private static <T> Dynamic<T> d(Dynamic<T> var0) {
      _snowman = a(_snowman, "CA");
      _snowman = a(_snowman, "CB");
      _snowman = a(_snowman, "CC");
      return a(_snowman, "CD");
   }

   private static <T> Dynamic<T> a(Dynamic<T> var0, String var1) {
      return _snowman.get(_snowman).asNumber().result().isPresent() ? _snowman.set(_snowman, agz.b(_snowman.get(_snowman).asInt(0) << 4)) : _snowman;
   }
}
