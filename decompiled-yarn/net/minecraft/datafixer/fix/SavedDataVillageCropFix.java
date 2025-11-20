package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class SavedDataVillageCropFix extends DataFix {
   public SavedDataVillageCropFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.writeFixAndRead(
         "SavedDataVillageCropFix",
         this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE),
         this.getOutputSchema().getType(TypeReferences.STRUCTURE_FEATURE),
         this::method_5152
      );
   }

   private <T> Dynamic<T> method_5152(Dynamic<T> _snowman) {
      return _snowman.update("Children", SavedDataVillageCropFix::method_5157);
   }

   private static <T> Dynamic<T> method_5157(Dynamic<T> _snowman) {
      return _snowman.asStreamOpt().map(SavedDataVillageCropFix::fixVillageChildren).map(_snowman::createList).result().orElse(_snowman);
   }

   private static Stream<? extends Dynamic<?>> fixVillageChildren(Stream<? extends Dynamic<?>> villageChildren) {
      return villageChildren.map(_snowman -> {
         String _snowmanx = _snowman.get("id").asString("");
         if ("ViF".equals(_snowmanx)) {
            return fixSmallPlotCropIds(_snowman);
         } else {
            return "ViDF".equals(_snowmanx) ? fixLargePlotCropIds(_snowman) : _snowman;
         }
      });
   }

   private static <T> Dynamic<T> fixSmallPlotCropIds(Dynamic<T> _snowman) {
      _snowman = fixCropId(_snowman, "CA");
      return fixCropId(_snowman, "CB");
   }

   private static <T> Dynamic<T> fixLargePlotCropIds(Dynamic<T> _snowman) {
      _snowman = fixCropId(_snowman, "CA");
      _snowman = fixCropId(_snowman, "CB");
      _snowman = fixCropId(_snowman, "CC");
      return fixCropId(_snowman, "CD");
   }

   private static <T> Dynamic<T> fixCropId(Dynamic<T> _snowman, String cropId) {
      return _snowman.get(cropId).asNumber().result().isPresent() ? _snowman.set(cropId, BlockStateFlattening.lookupState(_snowman.get(cropId).asInt(0) << 4)) : _snowman;
   }
}
