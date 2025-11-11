import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class ahj extends ajv {
   public ahj(Schema var1, boolean var2) {
      super(_snowman, _snowman, "Colorless shulker entity fix", akn.p, "minecraft:shulker");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), var0 -> var0.get("Color").asInt(0) == 10 ? var0.set("Color", var0.createByte((byte)16)) : var0);
   }
}
