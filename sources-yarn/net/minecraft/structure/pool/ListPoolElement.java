package net.minecraft.structure.pool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ListPoolElement extends StructurePoolElement {
   public static final Codec<ListPoolElement> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(StructurePoolElement.CODEC.listOf().fieldOf("elements").forGetter(arg -> arg.elements), method_28883())
            .apply(instance, ListPoolElement::new)
   );
   private final List<StructurePoolElement> elements;

   public ListPoolElement(List<StructurePoolElement> elements, StructurePool.Projection projection) {
      super(projection);
      if (elements.isEmpty()) {
         throw new IllegalArgumentException("Elements are empty");
      } else {
         this.elements = elements;
         this.setAllElementsProjection(projection);
      }
   }

   @Override
   public List<Structure.StructureBlockInfo> getStructureBlockInfos(StructureManager structureManager, BlockPos pos, BlockRotation rotation, Random random) {
      return this.elements.get(0).getStructureBlockInfos(structureManager, pos, rotation, random);
   }

   @Override
   public BlockBox getBoundingBox(StructureManager structureManager, BlockPos pos, BlockRotation rotation) {
      BlockBox lv = BlockBox.empty();

      for (StructurePoolElement lv2 : this.elements) {
         BlockBox lv3 = lv2.getBoundingBox(structureManager, pos, rotation);
         lv.encompass(lv3);
      }

      return lv;
   }

   @Override
   public boolean generate(
      StructureManager structureManager,
      StructureWorldAccess arg2,
      StructureAccessor arg3,
      ChunkGenerator arg4,
      BlockPos arg5,
      BlockPos arg6,
      BlockRotation arg7,
      BlockBox arg8,
      Random random,
      boolean keepJigsaws
   ) {
      for (StructurePoolElement lv : this.elements) {
         if (!lv.generate(structureManager, arg2, arg3, arg4, arg5, arg6, arg7, arg8, random, keepJigsaws)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.LIST_POOL_ELEMENT;
   }

   @Override
   public StructurePoolElement setProjection(StructurePool.Projection projection) {
      super.setProjection(projection);
      this.setAllElementsProjection(projection);
      return this;
   }

   @Override
   public String toString() {
      return "List[" + this.elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
   }

   private void setAllElementsProjection(StructurePool.Projection arg) {
      this.elements.forEach(arg2 -> arg2.setProjection(arg));
   }
}
