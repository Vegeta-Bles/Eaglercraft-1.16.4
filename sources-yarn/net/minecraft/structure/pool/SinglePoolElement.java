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
      instance -> instance.group(method_28882(), method_28880(), method_28883()).apply(instance, SinglePoolElement::new)
   );
   protected final Either<Identifier, Structure> field_24015;
   protected final Supplier<StructureProcessorList> processors;

   private static <T> DataResult<T> method_28877(Either<Identifier, Structure> either, DynamicOps<T> dynamicOps, T object) {
      Optional<Identifier> optional = either.left();
      return !optional.isPresent() ? DataResult.error("Can not serialize a runtime pool element") : Identifier.CODEC.encode(optional.get(), dynamicOps, object);
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Supplier<StructureProcessorList>> method_28880() {
      return StructureProcessorType.REGISTRY_CODEC.fieldOf("processors").forGetter(arg -> arg.processors);
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Either<Identifier, Structure>> method_28882() {
      return field_24951.fieldOf("location").forGetter(arg -> arg.field_24015);
   }

   protected SinglePoolElement(Either<Identifier, Structure> either, Supplier<StructureProcessorList> supplier, StructurePool.Projection arg) {
      super(arg);
      this.field_24015 = either;
      this.processors = supplier;
   }

   public SinglePoolElement(Structure arg) {
      this(Either.right(arg), () -> StructureProcessorLists.EMPTY, StructurePool.Projection.RIGID);
   }

   private Structure method_27233(StructureManager arg) {
      return (Structure)this.field_24015.map(arg::getStructureOrBlank, Function.identity());
   }

   public List<Structure.StructureBlockInfo> getDataStructureBlocks(StructureManager arg, BlockPos arg2, BlockRotation arg3, boolean mirroredAndRotated) {
      Structure lv = this.method_27233(arg);
      List<Structure.StructureBlockInfo> list = lv.getInfosForBlock(
         arg2, new StructurePlacementData().setRotation(arg3), Blocks.STRUCTURE_BLOCK, mirroredAndRotated
      );
      List<Structure.StructureBlockInfo> list2 = Lists.newArrayList();

      for (Structure.StructureBlockInfo lv2 : list) {
         if (lv2.tag != null) {
            StructureBlockMode lv3 = StructureBlockMode.valueOf(lv2.tag.getString("mode"));
            if (lv3 == StructureBlockMode.DATA) {
               list2.add(lv2);
            }
         }
      }

      return list2;
   }

   @Override
   public List<Structure.StructureBlockInfo> getStructureBlockInfos(StructureManager structureManager, BlockPos pos, BlockRotation rotation, Random random) {
      Structure lv = this.method_27233(structureManager);
      List<Structure.StructureBlockInfo> list = lv.getInfosForBlock(pos, new StructurePlacementData().setRotation(rotation), Blocks.JIGSAW, true);
      Collections.shuffle(list, random);
      return list;
   }

   @Override
   public BlockBox getBoundingBox(StructureManager structureManager, BlockPos pos, BlockRotation rotation) {
      Structure lv = this.method_27233(structureManager);
      return lv.calculateBoundingBox(new StructurePlacementData().setRotation(rotation), pos);
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
      Structure lv = this.method_27233(structureManager);
      StructurePlacementData lv2 = this.createPlacementData(arg7, arg8, keepJigsaws);
      if (!lv.place(arg2, arg5, arg6, lv2, random, 18)) {
         return false;
      } else {
         for (Structure.StructureBlockInfo lv3 : Structure.process(arg2, arg5, arg6, lv2, this.getDataStructureBlocks(structureManager, arg5, arg7, false))) {
            this.method_16756(arg2, lv3, arg5, arg7, random, arg8);
         }

         return true;
      }
   }

   protected StructurePlacementData createPlacementData(BlockRotation arg, BlockBox arg2, boolean keepJigsaws) {
      StructurePlacementData lv = new StructurePlacementData();
      lv.setBoundingBox(arg2);
      lv.setRotation(arg);
      lv.setUpdateNeighbors(true);
      lv.setIgnoreEntities(false);
      lv.addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
      lv.method_27264(true);
      if (!keepJigsaws) {
         lv.addProcessor(JigsawReplacementStructureProcessor.INSTANCE);
      }

      this.processors.get().getList().forEach(lv::addProcessor);
      this.getProjection().getProcessors().forEach(lv::addProcessor);
      return lv;
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
