import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alr extends Schema {
   public alr(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(true, akn.s, () -> DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", akn.o.in(_snowman))), "SpawnData", akn.o.in(_snowman)));
   }
}
