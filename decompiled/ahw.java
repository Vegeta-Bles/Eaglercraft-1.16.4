import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ahw extends ajv {
   public ahw(Schema var1, boolean var2) {
      super(_snowman, _snowman, "EntityItemFrameDirectionFix", akn.p, "minecraft:item_frame");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.set("Facing", _snowman.createByte(a(_snowman.get("Facing").asByte((byte)0))));
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }

   private static byte a(byte var0) {
      switch (_snowman) {
         case 0:
            return 3;
         case 1:
            return 4;
         case 2:
         default:
            return 2;
         case 3:
            return 5;
      }
   }
}
