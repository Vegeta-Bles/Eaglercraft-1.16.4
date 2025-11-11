import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class aml extends aln {
   public aml(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> alo.a(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      a(_snowman, _snowman, "minecraft:panda");
      _snowman.register(_snowman, "minecraft:pillager", var1x -> DSL.optionalFields("Inventory", DSL.list(akn.l.in(_snowman)), alo.a(_snowman)));
      return _snowman;
   }
}
