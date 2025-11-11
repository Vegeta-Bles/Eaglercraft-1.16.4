import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amd extends aln {
   public amd(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(
         false,
         akn.t,
         () -> DSL.optionalFields("Children", DSL.list(DSL.optionalFields("CA", akn.m.in(_snowman), "CB", akn.m.in(_snowman), "CC", akn.m.in(_snowman), "CD", akn.m.in(_snowman))))
      );
   }
}
