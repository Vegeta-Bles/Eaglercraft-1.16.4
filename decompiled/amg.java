import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amg extends aln {
   public amg(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> alo.a(_snowman));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      a(_snowman, _snowman, "minecraft:turtle");
      a(_snowman, _snowman, "minecraft:cod_mob");
      a(_snowman, _snowman, "minecraft:tropical_fish");
      a(_snowman, _snowman, "minecraft:salmon_mob");
      a(_snowman, _snowman, "minecraft:puffer_fish");
      a(_snowman, _snowman, "minecraft:phantom");
      a(_snowman, _snowman, "minecraft:dolphin");
      a(_snowman, _snowman, "minecraft:drowned");
      _snowman.register(_snowman, "minecraft:trident", var1x -> DSL.optionalFields("inBlockState", akn.m.in(_snowman)));
      return _snowman;
   }
}
