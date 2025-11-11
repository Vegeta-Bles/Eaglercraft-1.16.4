import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class agr extends ajv {
   public agr(Schema var1, boolean var2) {
      super(_snowman, _snowman, "BlockEntityJukeboxFix", akn.k, "minecraft:jukebox");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      Type<?> _snowman = this.getInputSchema().getChoiceType(akn.k, "minecraft:jukebox");
      Type<?> _snowmanx = _snowman.findFieldType("RecordItem");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("RecordItem", _snowmanx);
      Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
      int _snowmanxxxx = _snowmanxxx.get("Record").asInt(0);
      if (_snowmanxxxx > 0) {
         _snowmanxxx.remove("Record");
         String _snowmanxxxxx = ajh.a(aiy.a(_snowmanxxxx), 0);
         if (_snowmanxxxxx != null) {
            Dynamic<?> _snowmanxxxxxx = _snowmanxxx.emptyMap();
            _snowmanxxxxxx = _snowmanxxxxxx.set("id", _snowmanxxxxxx.createString(_snowmanxxxxx));
            _snowmanxxxxxx = _snowmanxxxxxx.set("Count", _snowmanxxxxxx.createByte((byte)1));
            return _snowman.set(
                  _snowmanxx,
                  (Typed)((Pair)_snowmanx.readTyped(_snowmanxxxxxx).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack."))).getFirst()
               )
               .set(DSL.remainderFinder(), _snowmanxxx);
         }
      }

      return _snowman;
   }
}
