package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class BlockEntityJukeboxFix extends ChoiceFix {
   public BlockEntityJukeboxFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "BlockEntityJukeboxFix", TypeReferences.BLOCK_ENTITY, "minecraft:jukebox");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      Type<?> _snowman = this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:jukebox");
      Type<?> _snowmanx = _snowman.findFieldType("RecordItem");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("RecordItem", _snowmanx);
      Dynamic<?> _snowmanxxx = (Dynamic<?>)inputType.get(DSL.remainderFinder());
      int _snowmanxxxx = _snowmanxxx.get("Record").asInt(0);
      if (_snowmanxxxx > 0) {
         _snowmanxxx.remove("Record");
         String _snowmanxxxxx = ItemInstanceTheFlatteningFix.getItem(ItemIdFix.fromId(_snowmanxxxx), 0);
         if (_snowmanxxxxx != null) {
            Dynamic<?> _snowmanxxxxxx = _snowmanxxx.emptyMap();
            _snowmanxxxxxx = _snowmanxxxxxx.set("id", _snowmanxxxxxx.createString(_snowmanxxxxx));
            _snowmanxxxxxx = _snowmanxxxxxx.set("Count", _snowmanxxxxxx.createByte((byte)1));
            return inputType.set(
                  _snowmanxx,
                  (Typed)((Pair)_snowmanx.readTyped(_snowmanxxxxxx).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack."))).getFirst()
               )
               .set(DSL.remainderFinder(), _snowmanxxx);
         }
      }

      return inputType;
   }
}
