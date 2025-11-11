import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amb extends aln {
   public amb(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerBlockEntities(_snowman);
      _snowman.remove("minecraft:flower_pot");
      _snowman.remove("minecraft:noteblock");
      return _snowman;
   }
}
