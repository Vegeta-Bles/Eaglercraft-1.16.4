import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ags extends ajv {
   public ags(Schema var1, boolean var2) {
      super(_snowman, _snowman, "BlockEntityKeepPacked", akn.k, "DUMMY");
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      return _snowman.set("keepPacked", _snowman.createBoolean(true));
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), ags::a);
   }
}
