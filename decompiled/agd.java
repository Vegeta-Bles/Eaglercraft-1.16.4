import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class agd extends DataFix {
   protected static final Logger a = LogManager.getLogger();
   protected TypeReference b;

   public agd(Schema var1, TypeReference var2) {
      super(_snowman, false);
      this.b = _snowman;
   }

   protected Typed<?> a(Typed<?> var1, String var2, Function<Dynamic<?>, Dynamic<?>> var3) {
      Type<?> _snowman = this.getInputSchema().getChoiceType(this.b, _snowman);
      Type<?> _snowmanx = this.getOutputSchema().getChoiceType(this.b, _snowman);
      return _snowman.updateTyped(DSL.namedChoice(_snowman, _snowman), _snowmanx, var1x -> var1x.update(DSL.remainderFinder(), _snowman));
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0, String var1, String var2) {
      return a(_snowman, _snowman).map(var3 -> _snowman.remove(_snowman).set(_snowman, var3));
   }

   protected static Optional<Dynamic<?>> b(Dynamic<?> var0, String var1, String var2) {
      return _snowman.get(_snowman).result().flatMap(agd::a).map(var3 -> _snowman.remove(_snowman).set(_snowman, var3));
   }

   protected static Optional<Dynamic<?>> c(Dynamic<?> var0, String var1, String var2) {
      String _snowman = _snowman + "Most";
      String _snowmanx = _snowman + "Least";
      return d(_snowman, _snowman, _snowmanx).map(var4x -> _snowman.remove(_snowman).remove(_snowman).set(_snowman, var4x));
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0, String var1) {
      return _snowman.get(_snowman).result().flatMap(var1x -> {
         String _snowman = var1x.asString(null);
         if (_snowman != null) {
            try {
               UUID _snowmanx = UUID.fromString(_snowman);
               return a(_snowman, _snowmanx.getMostSignificantBits(), _snowmanx.getLeastSignificantBits());
            } catch (IllegalArgumentException var4) {
            }
         }

         return Optional.empty();
      });
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0) {
      return d(_snowman, "M", "L");
   }

   protected static Optional<Dynamic<?>> d(Dynamic<?> var0, String var1, String var2) {
      long _snowman = _snowman.get(_snowman).asLong(0L);
      long _snowmanx = _snowman.get(_snowman).asLong(0L);
      return _snowman != 0L && _snowmanx != 0L ? a(_snowman, _snowman, _snowmanx) : Optional.empty();
   }

   protected static Optional<Dynamic<?>> a(Dynamic<?> var0, long var1, long var3) {
      return Optional.of(_snowman.createIntList(Arrays.stream(new int[]{(int)(_snowman >> 32), (int)_snowman, (int)(_snowman >> 32), (int)_snowman})));
   }
}
