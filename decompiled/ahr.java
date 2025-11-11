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

public class ahr extends DataFix {
   public ahr(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.a(this.getInputSchema().getTypeRaw(akn.l));
   }

   private <IS> TypeRewriteRule a(Type<IS> var1) {
      Type<Pair<Either<List<IS>, Unit>, Dynamic<?>>> _snowman = DSL.and(DSL.optional(DSL.field("Equipment", DSL.list(_snowman))), DSL.remainderType());
      Type<Pair<Either<List<IS>, Unit>, Pair<Either<List<IS>, Unit>, Dynamic<?>>>> _snowmanx = DSL.and(
         DSL.optional(DSL.field("ArmorItems", DSL.list(_snowman))), DSL.optional(DSL.field("HandItems", DSL.list(_snowman))), DSL.remainderType()
      );
      OpticFinder<Pair<Either<List<IS>, Unit>, Dynamic<?>>> _snowmanxx = DSL.typeFinder(_snowman);
      OpticFinder<List<IS>> _snowmanxxx = DSL.fieldFinder("Equipment", DSL.list(_snowman));
      return this.fixTypeEverywhereTyped(
         "EntityEquipmentToArmorAndHandFix",
         this.getInputSchema().getType(akn.p),
         this.getOutputSchema().getType(akn.p),
         var4x -> {
            Either<List<IS>, Unit> _snowmanxxxx = Either.right(DSL.unit());
            Either<List<IS>, Unit> _snowmanx = Either.right(DSL.unit());
            Dynamic<?> _snowmanxx = (Dynamic<?>)var4x.getOrCreate(DSL.remainderFinder());
            Optional<List<IS>> _snowmanxxx = var4x.getOptional(_snowman);
            if (_snowmanxxx.isPresent()) {
               List<IS> _snowmanxxxx = _snowmanxxx.get();
               IS _snowmanxxxxx = (IS)((Pair)_snowman.read(_snowmanxx.emptyMap())
                     .result()
                     .orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack.")))
                  .getFirst();
               if (!_snowmanxxxx.isEmpty()) {
                  _snowmanxxxx = Either.left(Lists.newArrayList(new Object[]{_snowmanxxxx.get(0), _snowmanxxxxx}));
               }

               if (_snowmanxxxx.size() > 1) {
                  List<IS> _snowmanxxxxxx = Lists.newArrayList(new Object[]{_snowmanxxxxx, _snowmanxxxxx, _snowmanxxxxx, _snowmanxxxxx});

                  for (int _snowmanxxxxxxx = 1; _snowmanxxxxxxx < Math.min(_snowmanxxxx.size(), 5); _snowmanxxxxxxx++) {
                     _snowmanxxxxxx.set(_snowmanxxxxxxx - 1, _snowmanxxxx.get(_snowmanxxxxxxx));
                  }

                  _snowmanx = Either.left(_snowmanxxxxxx);
               }
            }

            Dynamic<?> _snowmanxxxxxx = _snowmanxx;
            Optional<? extends Stream<? extends Dynamic<?>>> _snowmanxxxxxxx = _snowmanxx.get("DropChances").asStreamOpt().result();
            if (_snowmanxxxxxxx.isPresent()) {
               Iterator<? extends Dynamic<?>> _snowmanxxxxxxxx = Stream.concat((Stream<? extends Dynamic<?>>)_snowmanxxxxxxx.get(), Stream.generate(() -> _snowman.createInt(0)))
                  .iterator();
               float _snowmanxxxxxxxxx = _snowmanxxxxxxxx.next().asFloat(0.0F);
               if (!_snowmanxx.get("HandDropChances").result().isPresent()) {
                  Dynamic<?> _snowmanxxxxxxxxxx = _snowmanxx.createList(Stream.of(_snowmanxxxxxxxxx, 0.0F).map(_snowmanxx::createFloat));
                  _snowmanxx = _snowmanxx.set("HandDropChances", _snowmanxxxxxxxxxx);
               }

               if (!_snowmanxx.get("ArmorDropChances").result().isPresent()) {
                  Dynamic<?> _snowmanxxxxxxxxxx = _snowmanxx.createList(
                     Stream.of(_snowmanxxxxxxxx.next().asFloat(0.0F), _snowmanxxxxxxxx.next().asFloat(0.0F), _snowmanxxxxxxxx.next().asFloat(0.0F), _snowmanxxxxxxxx.next().asFloat(0.0F))
                        .map(_snowmanxx::createFloat)
                  );
                  _snowmanxx = _snowmanxx.set("ArmorDropChances", _snowmanxxxxxxxxxx);
               }

               _snowmanxx = _snowmanxx.remove("DropChances");
            }

            return var4x.set(_snowman, _snowman, Pair.of(_snowmanxxxx, Pair.of(_snowmanx, _snowmanxx)));
         }
      );
   }
}
