import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ahl extends ajv {
   public ahl(Schema var1, boolean var2) {
      super(_snowman, _snowman, "EntityArmorStandSilentFix", akn.p, "ArmorStand");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.get("Silent").asBoolean(false) && !_snowman.get("Marker").asBoolean(false) ? _snowman.remove("Silent") : _snowman;
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
