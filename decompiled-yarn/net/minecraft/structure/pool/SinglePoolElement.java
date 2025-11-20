package net.minecraft.structure.pool;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.JigsawReplacementStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SinglePoolElement extends StructurePoolElement {
   private static final Codec<Either<Identifier, Structure>> field_24951 = Codec.of(SinglePoolElement::method_28877, Identifier.CODEC.map(Either::left));
   public static final Codec<SinglePoolElement> field_24952 = RecordCodecBuilder.create(
      _snowman -> _snowman.group(method_28882(), method_28880(), method_28883()).apply(_snowman, SinglePoolElement::new)
   );
   protected final Either<Identifier, Structure> field_24015;
   protected final Supplier<StructureProcessorList> processors;

   private static <T> DataResult<T> method_28877(Either<Identifier, Structure> _snowman, DynamicOps<T> _snowman, T _snowman) {
      Optional<Identifier> _snowmanxxx = _snowman.left();
      return !_snowmanxxx.isPresent() ? DataResult.error("Can not serialize a runtime pool element") : Identifier.CODEC.encode(_snowmanxxx.get(), _snowman, _snowman);
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Supplier<StructureProcessorList>> method_28880() {
      return StructureProcessorType.REGISTRY_CODEC.fieldOf("processors").forGetter(_snowman -> _snowman.processors);
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Either<Identifier, Structure>> method_28882() {
      return field_24951.fieldOf("location").forGetter(_snowman -> _snowman.field_24015);
   }

   protected SinglePoolElement(Either<Identifier, Structure> _snowman, Supplier<StructureProcessorList> _snowman, StructurePool.Projection _snowman) {
      super(_snowman);
      this.field_24015 = _snowman;
      this.processors = _snowman;
   }

   public SinglePoolElement(Structure _snowman) {
      this(Either.right(_snowman), () -> StructureProcessorLists.EMPTY, StructurePool.Projection.RIGID);
   }

   private Structure method_27233(StructureManager _snowman) {
      return (Structure)this.field_24015.map(_snowman::getStructureOrBlank, Function.identity());
   }

   public List<Structure.StructureBlockInfo> getDataStructureBlocks(StructureManager _snowman, BlockPos _snowman, BlockRotation _snowman, boolean mirroredAndRotated) {
      Structure _snowmanxxx = this.method_27233(_snowman);
      List<Structure.StructureBlockInfo> _snowmanxxxx = _snowmanxxx.getInfosForBlock(
         _snowman, new StructurePlacementData().setRotation(_snowman), Blocks.STRUCTURE_BLOCK, mirroredAndRotated
      );
      List<Structure.StructureBlockInfo> _snowmanxxxxx = Lists.newArrayList();

      for (Structure.StructureBlockInfo _snowmanxxxxxx : _snowmanxxxx) {
         if (_snowmanxxxxxx.tag != null) {
            StructureBlockMode _snowmanxxxxxxx = StructureBlockMode.valueOf(_snowmanxxxxxx.tag.getString("mode"));
            if (_snowmanxxxxxxx == StructureBlockMode.DATA) {
               _snowmanxxxxx.add(_snowmanxxxxxx);
            }
         }
      }

      return _snowmanxxxxx;
   }

   @Override
   public List<Structure.StructureBlockInfo> getStructureBlockInfos(StructureManager structureManager, BlockPos pos, BlockRotation rotation, Random random) {
      Structure _snowman = this.method_27233(structureManager);
      List<Structure.StructureBlockInfo> _snowmanx = _snowman.getInfosForBlock(pos, new StructurePlacementData().setRotation(rotation), Blocks.JIGSAW, true);
      Collections.shuffle(_snowmanx, random);
      return _snowmanx;
   }

   @Override
   public BlockBox getBoundingBox(StructureManager structureManager, BlockPos pos, BlockRotation rotation) {
      Structure _snowman = this.method_27233(structureManager);
      return _snowman.calculateBoundingBox(new StructurePlacementData().setRotation(rotation), pos);
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
      Structure _snowmanxxxxxxxx = this.method_27233(structureManager);
      StructurePlacementData _snowmanxxxxxxxxx = this.createPlacementData(_snowman, _snowman, keepJigsaws);
      if (!_snowmanxxxxxxxx.place(_snowman, _snowman, _snowman, _snowmanxxxxxxxxx, _snowman, 18)) {
         return false;
      } else {
         for (Structure.StructureBlockInfo _snowmanxxxxxxxxxx : Structure.process(_snowman, _snowman, _snowman, _snowmanxxxxxxxxx, this.getDataStructureBlocks(structureManager, _snowman, _snowman, false))) {
            this.method_16756(_snowman, _snowmanxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman);
         }

         return true;
      }
   }

   protected StructurePlacementData createPlacementData(BlockRotation _snowman, BlockBox _snowman, boolean keepJigsaws) {
      StructurePlacementData _snowmanxx = new StructurePlacementData();
      _snowmanxx.setBoundingBox(_snowman);
      _snowmanxx.setRotation(_snowman);
      _snowmanxx.setUpdateNeighbors(true);
      _snowmanxx.setIgnoreEntities(false);
      _snowmanxx.addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
      _snowmanxx.method_27264(true);
      if (!keepJigsaws) {
         _snowmanxx.addProcessor(JigsawReplacementStructureProcessor.INSTANCE);
      }

      this.processors.get().getList().forEach(_snowmanxx::addProcessor);
      this.getProjection().getProcessors().forEach(_snowmanxx::addProcessor);
      return _snowmanxx;
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.SINGLE_POOL_ELEMENT;
   }

   @Override
   public String toString() {
      return "Single[" + this.field_24015 + "]";
   }
}
