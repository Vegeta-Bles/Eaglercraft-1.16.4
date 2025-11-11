import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ain extends ajv {
   public ain(Schema var1, boolean var2) {
      super(_snowman, _snowman, "EntityWolfColorFix", akn.p, "minecraft:wolf");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.update("CollarColor", var0 -> var0.createByte((byte)(15 - var0.asInt(0))));
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
