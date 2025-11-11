import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amu extends aln {
   public amu(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> alo.a(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      a(_snowman, _snowman, "minecraft:bee");
      a(_snowman, _snowman, "minecraft:bee_stinger");
      return _snowman;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerBlockEntities(_snowman);
      _snowman.register(
         _snowman, "minecraft:beehive", () -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "Bees", DSL.list(DSL.optionalFields("EntityData", akn.o.in(_snowman))))
      );
      return _snowman;
   }
}
