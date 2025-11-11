import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alq extends Schema {
   public alq(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(false, akn.w, () -> DSL.constType(aln.a()));
      _snowman.registerType(
         false,
         akn.b,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", akn.o.in(_snowman)),
               "Inventory",
               DSL.list(akn.l.in(_snowman)),
               "EnderItems",
               DSL.list(akn.l.in(_snowman)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  akn.o.in(_snowman),
                  "ShoulderEntityRight",
                  akn.o.in(_snowman),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(akn.w.in(_snowman)), "toBeDisplayed", DSL.list(akn.w.in(_snowman)))
               )
            )
      );
      _snowman.registerType(false, akn.d, () -> DSL.compoundList(DSL.list(akn.l.in(_snowman))));
   }
}
