import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amv extends aln {
   public amv(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   private static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "RecipesUsed", DSL.compoundList(akn.w.in(_snowman), DSL.constType(DSL.intType()))));
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerBlockEntities(_snowman);
      a(_snowman, _snowman, "minecraft:furnace");
      a(_snowman, _snowman, "minecraft:smoker");
      a(_snowman, _snowman, "minecraft:blast_furnace");
      return _snowman;
   }
}
