package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class BlockEntityBlockStateFix extends ChoiceFix {
   public BlockEntityBlockStateFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "BlockEntityBlockStateFix", TypeReferences.BLOCK_ENTITY, "minecraft:piston");
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      Type<?> _snowman = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:piston");
      Type<?> _snowmanx = _snowman.findFieldType("blockState");
      OpticFinder<?> _snowmanxx = DSL.fieldFinder("blockState", _snowmanx);
      Dynamic<?> _snowmanxxx = (Dynamic<?>)inputType.get(DSL.remainderFinder());
      int _snowmanxxxx = _snowmanxxx.get("blockId").asInt(0);
      _snowmanxxx = _snowmanxxx.remove("blockId");
      int _snowmanxxxxx = _snowmanxxx.get("blockData").asInt(0) & 15;
      _snowmanxxx = _snowmanxxx.remove("blockData");
      Dynamic<?> _snowmanxxxxxx = BlockStateFlattening.lookupState(_snowmanxxxx << 4 | _snowmanxxxxx);
      Typed<?> _snowmanxxxxxxx = (Typed<?>)_snowman.pointTyped(inputType.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
      return _snowmanxxxxxxx.set(DSL.remainderFinder(), _snowmanxxx)
         .set(
            _snowmanxx,
            (Typed)((Pair)_snowmanx.readTyped(_snowmanxxxxxx).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag.")))
               .getFirst()
         );
   }
}
