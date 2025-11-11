import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;

public class aih extends ajv {
   public aih(Schema var1) {
      super(_snowman, false, "EntityShulkerRotationFix", akn.p, "minecraft:shulker");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      List<Double> _snowman = _snowman.get("Rotation").asList(var0 -> var0.asDouble(180.0));
      if (!_snowman.isEmpty()) {
         _snowman.set(0, _snowman.get(0) - 180.0);
         return _snowman.set("Rotation", _snowman.createList(_snowman.stream().map(_snowman::createDouble)));
      } else {
         return _snowman;
      }
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
