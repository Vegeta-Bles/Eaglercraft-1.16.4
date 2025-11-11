import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class ajs extends ajv {
   public ajs(Schema var1, String var2) {
      super(_snowman, false, "Memory expiry data fix (" + _snowman + ")", akn.p, _snowman);
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.update("Brain", this::b);
   }

   private Dynamic<?> b(Dynamic<?> var1) {
      return _snowman.update("memories", this::c);
   }

   private Dynamic<?> c(Dynamic<?> var1) {
      return _snowman.updateMapValues(this::a);
   }

   private Pair<Dynamic<?>, Dynamic<?>> a(Pair<Dynamic<?>, Dynamic<?>> var1) {
      return _snowman.mapSecond(this::d);
   }

   private Dynamic<?> d(Dynamic<?> var1) {
      return _snowman.createMap(ImmutableMap.of(_snowman.createString("value"), _snowman));
   }
}
