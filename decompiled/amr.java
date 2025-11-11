import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amr extends aln {
   public amr(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static TypeTemplate a(Schema var0) {
      return DSL.optionalFields("ArmorItems", DSL.list(akn.l.in(_snowman)), "HandItems", DSL.list(akn.l.in(_snowman)));
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> a(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      _snowman.remove("minecraft:illager_beast");
      a(_snowman, _snowman, "minecraft:ravager");
      return _snowman;
   }
}
