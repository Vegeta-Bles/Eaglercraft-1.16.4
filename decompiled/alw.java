import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alw extends aln {
   public alw(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerBlockEntities(_snowman);
      _snowman.register(_snowman, "minecraft:trapped_chest", () -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman))));
      return _snowman;
   }
}
