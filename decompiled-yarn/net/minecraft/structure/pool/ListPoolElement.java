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
      _snowman -> _snowman.group(StructurePoolElement.CODEC.listOf().fieldOf("elements").forGetter(_snowmanx -> _snowmanx.elements), method_28883()).apply(_snowman, ListPoolElement::new)
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
      BlockBox _snowman = BlockBox.empty();

      for (StructurePoolElement _snowmanx : this.elements) {
         BlockBox _snowmanxx = _snowmanx.getBoundingBox(structureManager, pos, rotation);
         _snowman.encompass(_snowmanxx);
      }

      return _snowman;
   }

   @Override
   public boolean generate(
      StructureManager structureManager,
      StructureWorldAccess _snowman,
      StructureAccessor _snowman,
      ChunkGenerator _snowman,
      BlockPos _snowman,
      BlockPos _snowman,
      BlockRotation _snowman,
      BlockBox _snowman,
      Random _snowman,
      boolean keepJigsaws
   ) {
      for (StructurePoolElement _snowmanxxxxxxxx : this.elements) {
         if (!_snowmanxxxxxxxx.generate(structureManager, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, keepJigsaws)) {
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

   private void setAllElementsProjection(StructurePool.Projection _snowman) {
      this.elements.forEach(_snowmanxx -> _snowmanxx.setProjection(_snowman));
   }
}
