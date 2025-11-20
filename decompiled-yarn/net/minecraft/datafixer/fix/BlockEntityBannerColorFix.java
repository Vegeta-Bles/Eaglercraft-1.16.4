package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class BlockEntityBannerColorFix extends ChoiceFix {
   public BlockEntityBannerColorFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "BlockEntityBannerColorFix", TypeReferences.BLOCK_ENTITY, "minecraft:banner");
   }

   public Dynamic<?> fixBannerColor(Dynamic<?> _snowman) {
      _snowman = _snowman.update("Base", _snowmanx -> _snowmanx.createInt(15 - _snowmanx.asInt(0)));
      return _snowman.update(
         "Patterns",
         _snowmanx -> (Dynamic)DataFixUtils.orElse(
               _snowmanx.asStreamOpt().map(_snowmanxx -> _snowmanxx.map(_snowmanxxx -> _snowmanxxx.update("Color", _snowmanxxxx -> _snowmanxxxx.createInt(15 - _snowmanxxxx.asInt(0))))).map(_snowmanx::createList).result(),
               _snowmanx
            )
      );
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::fixBannerColor);
   }
}
