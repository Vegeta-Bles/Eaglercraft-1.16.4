package net.minecraft.structure.pool;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public abstract class StructurePoolElement {
   public static final Codec<StructurePoolElement> CODEC = Registry.STRUCTURE_POOL_ELEMENT
      .dispatch("element_type", StructurePoolElement::getType, StructurePoolElementType::codec);
   @Nullable
   private volatile StructurePool.Projection projection;

   protected static <E extends StructurePoolElement> RecordCodecBuilder<E, StructurePool.Projection> method_28883() {
      return StructurePool.Projection.field_24956.fieldOf("projection").forGetter(StructurePoolElement::getProjection);
   }

   protected StructurePoolElement(StructurePool.Projection projection) {
      this.projection = projection;
   }

   public abstract List<Structure.StructureBlockInfo> getStructureBlockInfos(
      StructureManager structureManager, BlockPos pos, BlockRotation rotation, Random random
   );

   public abstract BlockBox getBoundingBox(StructureManager structureManager, BlockPos pos, BlockRotation rotation);

   public abstract boolean generate(
      StructureManager structureManager,
      StructureWorldAccess var2,
      StructureAccessor var3,
      ChunkGenerator var4,
      BlockPos var5,
      BlockPos var6,
      BlockRotation var7,
      BlockBox var8,
      Random var9,
      boolean keepJigsaws
   );

   public abstract StructurePoolElementType<?> getType();

   public void method_16756(WorldAccess _snowman, Structure.StructureBlockInfo _snowman, BlockPos _snowman, BlockRotation _snowman, Random _snowman, BlockBox _snowman) {
   }

   public StructurePoolElement setProjection(StructurePool.Projection projection) {
      this.projection = projection;
      return this;
   }

   public StructurePool.Projection getProjection() {
      StructurePool.Projection _snowman = this.projection;
      if (_snowman == null) {
         throw new IllegalStateException();
      } else {
         return _snowman;
      }
   }

   public int getGroundLevelDelta() {
      return 1;
   }

   public static Function<StructurePool.Projection, EmptyPoolElement> method_30438() {
      return _snowman -> EmptyPoolElement.INSTANCE;
   }

   public static Function<StructurePool.Projection, LegacySinglePoolElement> method_30425(String _snowman) {
      return _snowmanxx -> new LegacySinglePoolElement(Either.left(new Identifier(_snowman)), () -> StructureProcessorLists.EMPTY, _snowmanxx);
   }

   public static Function<StructurePool.Projection, LegacySinglePoolElement> method_30426(String _snowman, StructureProcessorList _snowman) {
      return _snowmanxxx -> new LegacySinglePoolElement(Either.left(new Identifier(_snowman)), () -> _snowman, _snowmanxxx);
   }

   public static Function<StructurePool.Projection, SinglePoolElement> method_30434(String _snowman) {
      return _snowmanxx -> new SinglePoolElement(Either.left(new Identifier(_snowman)), () -> StructureProcessorLists.EMPTY, _snowmanxx);
   }

   public static Function<StructurePool.Projection, SinglePoolElement> method_30435(String _snowman, StructureProcessorList _snowman) {
      return _snowmanxxx -> new SinglePoolElement(Either.left(new Identifier(_snowman)), () -> _snowman, _snowmanxxx);
   }

   public static Function<StructurePool.Projection, FeaturePoolElement> method_30421(ConfiguredFeature<?, ?> _snowman) {
      return _snowmanxx -> new FeaturePoolElement(() -> _snowman, _snowmanxx);
   }

   public static Function<StructurePool.Projection, ListPoolElement> method_30429(List<Function<StructurePool.Projection, ? extends StructurePoolElement>> _snowman) {
      return _snowmanxx -> new ListPoolElement(_snowman.stream().map(_snowmanxxxx -> (StructurePoolElement)_snowmanxxxx.apply(_snowmanxx)).collect(Collectors.toList()), _snowmanxx);
   }
}
