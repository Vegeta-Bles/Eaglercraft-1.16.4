import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class agt extends ajv {
   public agt(Schema var1, boolean var2) {
      super(_snowman, _snowman, "BlockEntityShulkerBoxColorFix", akn.k, "minecraft:shulker_box");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), var0 -> var0.remove("Color"));
   }
}
