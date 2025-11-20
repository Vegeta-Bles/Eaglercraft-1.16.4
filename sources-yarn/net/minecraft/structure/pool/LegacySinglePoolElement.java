package net.minecraft.structure.pool;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;

public class LegacySinglePoolElement extends SinglePoolElement {
   public static final Codec<LegacySinglePoolElement> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(method_28882(), method_28880(), method_28883()).apply(instance, LegacySinglePoolElement::new)
   );

   protected LegacySinglePoolElement(Either<Identifier, Structure> either, Supplier<StructureProcessorList> supplier, StructurePool.Projection arg) {
      super(either, supplier, arg);
   }

   @Override
   protected StructurePlacementData createPlacementData(BlockRotation arg, BlockBox arg2, boolean keepJigsaws) {
      StructurePlacementData lv = super.createPlacementData(arg, arg2, keepJigsaws);
      lv.removeProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
      lv.addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
      return lv;
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.LEGACY_SINGLE_POOL_ELEMENT;
   }

   @Override
   public String toString() {
      return "LegacySingle[" + this.field_24015 + "]";
   }
}
