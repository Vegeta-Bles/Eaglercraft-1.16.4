import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amj extends aln {
   public amj(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      _snowman.put("minecraft:cod", _snowman.remove("minecraft:cod_mob"));
      _snowman.put("minecraft:salmon", _snowman.remove("minecraft:salmon_mob"));
      return _snowman;
   }
}
