package net.minecraft.datafixer.fix;

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

public abstract class AbstractUuidFix extends DataFix {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected TypeReference typeReference;

   public AbstractUuidFix(Schema outputSchema, TypeReference typeReference) {
      super(outputSchema, false);
      this.typeReference = typeReference;
   }

   protected Typed<?> updateTyped(Typed<?> typed, String name, Function<Dynamic<?>, Dynamic<?>> updater) {
      Type<?> _snowman = this.getInputSchema().getChoiceType(this.typeReference, name);
      Type<?> _snowmanx = this.getOutputSchema().getChoiceType(this.typeReference, name);
      return typed.updateTyped(DSL.namedChoice(name, _snowman), _snowmanx, _snowmanxx -> _snowmanxx.update(DSL.remainderFinder(), updater));
   }

   protected static Optional<Dynamic<?>> updateStringUuid(Dynamic<?> _snowman, String oldKey, String newKey) {
      return createArrayFromStringUuid(_snowman, oldKey).map(_snowmanxx -> _snowman.remove(oldKey).set(newKey, _snowmanxx));
   }

   protected static Optional<Dynamic<?>> updateCompoundUuid(Dynamic<?> _snowman, String oldKey, String newKey) {
      return _snowman.get(oldKey).result().flatMap(AbstractUuidFix::createArrayFromCompoundUuid).map(_snowmanxx -> _snowman.remove(oldKey).set(newKey, _snowmanxx));
   }

   protected static Optional<Dynamic<?>> updateRegularMostLeast(Dynamic<?> _snowman, String oldKey, String newKey) {
      String _snowmanx = oldKey + "Most";
      String _snowmanxx = oldKey + "Least";
      return createArrayFromMostLeastTags(_snowman, _snowmanx, _snowmanxx).map(_snowmanxxx -> _snowman.remove(_snowman).remove(_snowman).set(newKey, _snowmanxxx));
   }

   protected static Optional<Dynamic<?>> createArrayFromStringUuid(Dynamic<?> _snowman, String key) {
      return _snowman.get(key).result().flatMap(_snowmanxxxx -> {
         String _snowmanxx = _snowmanxxxx.asString(null);
         if (_snowmanxx != null) {
            try {
               UUID _snowmanxxx = UUID.fromString(_snowmanxx);
               return createArray(_snowman, _snowmanxxx.getMostSignificantBits(), _snowmanxxx.getLeastSignificantBits());
            } catch (IllegalArgumentException var4) {
            }
         }

         return Optional.empty();
      });
   }

   protected static Optional<Dynamic<?>> createArrayFromCompoundUuid(Dynamic<?> _snowman) {
      return createArrayFromMostLeastTags(_snowman, "M", "L");
   }

   protected static Optional<Dynamic<?>> createArrayFromMostLeastTags(Dynamic<?> _snowman, String mostBitsKey, String leastBitsKey) {
      long _snowmanx = _snowman.get(mostBitsKey).asLong(0L);
      long _snowmanxx = _snowman.get(leastBitsKey).asLong(0L);
      return _snowmanx != 0L && _snowmanxx != 0L ? createArray(_snowman, _snowmanx, _snowmanxx) : Optional.empty();
   }

   protected static Optional<Dynamic<?>> createArray(Dynamic<?> _snowman, long mostBits, long leastBits) {
      return Optional.of(_snowman.createIntList(Arrays.stream(new int[]{(int)(mostBits >> 32), (int)mostBits, (int)(leastBits >> 32), (int)leastBits})));
   }
}
