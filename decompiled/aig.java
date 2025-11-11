import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class aig extends ajv {
   public aig(Schema var1, boolean var2) {
      super(_snowman, _snowman, "EntityShulkerColorFix", akn.p, "minecraft:shulker");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return !_snowman.get("Color").map(Dynamic::asNumber).result().isPresent() ? _snowman.set("Color", _snowman.createByte((byte)10)) : _snowman;
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
