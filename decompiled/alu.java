import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alu extends Schema {
   public alu(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(
         false,
         akn.b,
         () -> DSL.optionalFields(
               "RootVehicle", DSL.optionalFields("Entity", akn.o.in(_snowman)), "Inventory", DSL.list(akn.l.in(_snowman)), "EnderItems", DSL.list(akn.l.in(_snowman))
            )
      );
      _snowman.registerType(true, akn.o, () -> DSL.optionalFields("Passengers", DSL.list(akn.o.in(_snowman)), akn.p.in(_snowman)));
   }
}
