import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class ago extends ajv {
   public ago(Schema var1, boolean var2) {
      super(_snowman, _snowman, "BlockEntityBlockStateFix", akn.k, "minecraft:piston");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      Type<?> _snowman = this.getOutputSchema().getChoiceType(akn.k, "minecraft:piston");
      Type<?> _snowmanx = _snowman.findFieldType("blockState");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("blockState", _snowmanx);
      Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
      int _snowmanxxxx = _snowmanxxx.get("blockId").asInt(0);
      _snowmanxxx = _snowmanxxx.remove("blockId");
      int _snowmanxxxxx = _snowmanxxx.get("blockData").asInt(0) & 15;
      _snowmanxxx = _snowmanxxx.remove("blockData");
      Dynamic<?> _snowmanxxxxxx = agz.b(_snowmanxxxx << 4 | _snowmanxxxxx);
      Typed<?> _snowmanxxxxxxx = (Typed<?>)_snowman.pointTyped(_snowman.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
      return _snowmanxxxxxxx.set(DSL.remainderFinder(), _snowmanxxx)
         .set(
            _snowmanxx,
            (Typed)((Pair)_snowmanx.readTyped(_snowmanxxxxxx).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag.")))
               .getFirst()
         );
   }
}
