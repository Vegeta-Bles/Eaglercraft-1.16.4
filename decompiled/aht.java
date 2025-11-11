import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class aht extends ajv {
   public aht(Schema var1, boolean var2) {
      super(_snowman, _snowman, "EntityHorseSaddleFix", akn.p, "EntityHorse");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      OpticFinder<Pair<String, String>> _snowman = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      Type<?> _snowmanx = this.getInputSchema().getTypeRaw(akn.l);
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("SaddleItem", _snowmanx);
      Optional<? extends Typed<?>> _snowmanxxx = _snowman.getOptionalTyped(_snowmanxx);
      Dynamic<?> _snowmanxxxx = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
      if (!_snowmanxxx.isPresent() && _snowmanxxxx.get("Saddle").asBoolean(false)) {
         Typed<?> _snowmanxxxxx = (Typed<?>)_snowmanx.pointTyped(_snowman.getOps()).orElseThrow(IllegalStateException::new);
         _snowmanxxxxx = _snowmanxxxxx.set(_snowman, Pair.of(akn.r.typeName(), "minecraft:saddle"));
         Dynamic<?> _snowmanxxxxxx = _snowmanxxxx.emptyMap();
         _snowmanxxxxxx = _snowmanxxxxxx.set("Count", _snowmanxxxxxx.createByte((byte)1));
         _snowmanxxxxxx = _snowmanxxxxxx.set("Damage", _snowmanxxxxxx.createShort((short)0));
         _snowmanxxxxx = _snowmanxxxxx.set(DSL.remainderFinder(), _snowmanxxxxxx);
         _snowmanxxxx.remove("Saddle");
         return _snowman.set(_snowmanxx, _snowmanxxxxx).set(DSL.remainderFinder(), _snowmanxxxx);
      } else {
         return _snowman;
      }
   }
}
