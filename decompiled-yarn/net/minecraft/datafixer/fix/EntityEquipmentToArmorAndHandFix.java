package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class EntityEquipmentToArmorAndHandFix extends DataFix {
   public EntityEquipmentToArmorAndHandFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixEquipment(this.getInputSchema().getTypeRaw(TypeReferences.ITEM_STACK));
   }

   private <IS> TypeRewriteRule fixEquipment(Type<IS> _snowman) {
      Type<Pair<Either<List<IS>, Unit>, Dynamic<?>>> _snowmanx = DSL.and(DSL.optional(DSL.field("Equipment", DSL.list(_snowman))), DSL.remainderType());
      Type<Pair<Either<List<IS>, Unit>, Pair<Either<List<IS>, Unit>, Dynamic<?>>>> _snowmanxx = DSL.and(
         DSL.optional(DSL.field("ArmorItems", DSL.list(_snowman))), DSL.optional(DSL.field("HandItems", DSL.list(_snowman))), DSL.remainderType()
      );
      OpticFinder<Pair<Either<List<IS>, Unit>, Dynamic<?>>> _snowmanxxx = DSL.typeFinder(_snowmanx);
      OpticFinder<List<IS>> _snowmanxxxx = DSL.fieldFinder("Equipment", DSL.list(_snowman));
      return this.fixTypeEverywhereTyped(
         "EntityEquipmentToArmorAndHandFix",
         this.getInputSchema().getType(TypeReferences.ENTITY),
         this.getOutputSchema().getType(TypeReferences.ENTITY),
         _snowmanxxxxxxxxxxxxxxxx -> {
            Either<List<IS>, Unit> _snowmanxxxxx = Either.right(DSL.unit());
            Either<List<IS>, Unit> _snowmanxxxxxx = Either.right(DSL.unit());
            Dynamic<?> _snowmanxxxxxxx = (Dynamic<?>)_snowmanxxxxxxxxxxxxxxxx.getOrCreate(DSL.remainderFinder());
            Optional<List<IS>> _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.getOptional(_snowman);
            if (_snowmanxxxxxxxx.isPresent()) {
               List<IS> _snowmanxxxxxxxxx = _snowmanxxxxxxxx.get();
               IS _snowmanxxxxxxxxxx = (IS)((Pair)_snowman.read(_snowmanxxxxxxx.emptyMap())
                     .result()
                     .orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack.")))
                  .getFirst();
               if (!_snowmanxxxxxxxxx.isEmpty()) {
                  _snowmanxxxxx = Either.left(Lists.newArrayList(new Object[]{_snowmanxxxxxxxxx.get(0), _snowmanxxxxxxxxxx}));
               }

               if (_snowmanxxxxxxxxx.size() > 1) {
                  List<IS> _snowmanxxxxxxxxxxx = Lists.newArrayList(new Object[]{_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx});

                  for (int _snowmanxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxx < Math.min(_snowmanxxxxxxxxx.size(), 5); _snowmanxxxxxxxxxxxx++) {
                     _snowmanxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxx - 1, _snowmanxxxxxxxxx.get(_snowmanxxxxxxxxxxxx));
                  }

                  _snowmanxxxxxx = Either.left(_snowmanxxxxxxxxxxx);
               }
            }

            Dynamic<?> _snowmanxxxxxxxxxxx = _snowmanxxxxxxx;
            Optional<? extends Stream<? extends Dynamic<?>>> _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx.get("DropChances").asStreamOpt().result();
            if (_snowmanxxxxxxxxxxxx.isPresent()) {
               Iterator<? extends Dynamic<?>> _snowmanxxxxxxx = Stream.concat(
                     (Stream<? extends Dynamic<?>>)_snowmanxxxxxxxxxxxx.get(), Stream.generate(() -> _snowmanxxxxxx.createInt(0))
                  )
                  .iterator();
               float _snowmanxxxxxxxx = _snowmanxxxxxxx.next().asFloat(0.0F);
               if (!_snowmanxxxxxxx.get("HandDropChances").result().isPresent()) {
                  Dynamic<?> _snowmanxxxxxxxxx = _snowmanxxxxxxx.createList(Stream.of(_snowmanxxxxxxxx, 0.0F).map(_snowmanxxxxxxx::createFloat));
                  _snowmanxxxxxxx = _snowmanxxxxxxx.set("HandDropChances", _snowmanxxxxxxxxx);
               }

               if (!_snowmanxxxxxxx.get("ArmorDropChances").result().isPresent()) {
                  Dynamic<?> _snowmanxxxxxxxxx = _snowmanxxxxxxx.createList(
                     Stream.of(_snowmanxxxxxxx.next().asFloat(0.0F), _snowmanxxxxxxx.next().asFloat(0.0F), _snowmanxxxxxxx.next().asFloat(0.0F), _snowmanxxxxxxx.next().asFloat(0.0F))
                        .map(_snowmanxxxxxxx::createFloat)
                  );
                  _snowmanxxxxxxx = _snowmanxxxxxxx.set("ArmorDropChances", _snowmanxxxxxxxxx);
               }

               _snowmanxxxxxxx = _snowmanxxxxxxx.remove("DropChances");
            }

            return _snowmanxxxxxxxxxxxxxxxx.set(_snowman, _snowman, Pair.of(_snowmanxxxxx, Pair.of(_snowmanxxxxxx, _snowmanxxxxxxx)));
         }
      );
   }
}
