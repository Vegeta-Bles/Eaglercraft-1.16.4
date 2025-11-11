import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amo extends aln {
   public amo(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerBlockEntities(_snowman);
      a(_snowman, _snowman, "minecraft:barrel");
      a(_snowman, _snowman, "minecraft:smoker");
      a(_snowman, _snowman, "minecraft:blast_furnace");
      _snowman.register(_snowman, "minecraft:lectern", var1x -> DSL.optionalFields("Book", akn.l.in(_snowman)));
      _snowman.registerSimple(_snowman, "minecraft:bell");
      return _snowman;
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman))));
   }
}
