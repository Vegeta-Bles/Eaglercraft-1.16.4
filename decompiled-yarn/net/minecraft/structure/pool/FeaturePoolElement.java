package net.minecraft.structure.pool;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.enums.JigsawOrientation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class FeaturePoolElement extends StructurePoolElement {
   public static final Codec<FeaturePoolElement> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(ConfiguredFeature.REGISTRY_CODEC.fieldOf("feature").forGetter(_snowmanx -> _snowmanx.feature), method_28883()).apply(_snowman, FeaturePoolElement::new)
   );
   private final Supplier<ConfiguredFeature<?, ?>> feature;
   private final CompoundTag tag;

   protected FeaturePoolElement(Supplier<ConfiguredFeature<?, ?>> feature, StructurePool.Projection projection) {
      super(projection);
      this.feature = feature;
      this.tag = this.createDefaultJigsawTag();
   }

   private CompoundTag createDefaultJigsawTag() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("name", "minecraft:bottom");
      _snowman.putString("final_state", "minecraft:air");
      _snowman.putString("pool", "minecraft:empty");
      _snowman.putString("target", "minecraft:empty");
      _snowman.putString("joint", JigsawBlockEntity.Joint.ROLLABLE.asString());
      return _snowman;
   }

   public BlockPos getStart(StructureManager _snowman, BlockRotation _snowman) {
      return BlockPos.ORIGIN;
   }

   @Override
   public List<Structure.StructureBlockInfo> getStructureBlockInfos(StructureManager structureManager, BlockPos pos, BlockRotation rotation, Random random) {
      List<Structure.StructureBlockInfo> _snowman = Lists.newArrayList();
      _snowman.add(
         new Structure.StructureBlockInfo(
            pos, Blocks.JIGSAW.getDefaultState().with(JigsawBlock.ORIENTATION, JigsawOrientation.byDirections(Direction.DOWN, Direction.SOUTH)), this.tag
         )
      );
      return _snowman;
   }

   @Override
   public BlockBox getBoundingBox(StructureManager structureManager, BlockPos pos, BlockRotation rotation) {
      BlockPos _snowman = this.getStart(structureManager, rotation);
      return new BlockBox(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + _snowman.getX(), pos.getY() + _snowman.getY(), pos.getZ() + _snowman.getZ());
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
      return this.feature.get().generate(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public StructurePoolElementType<?> getType() {
      return StructurePoolElementType.FEATURE_POOL_ELEMENT;
   }

   @Override
   public String toString() {
      return "Feature[" + Registry.FEATURE.getId(this.feature.get().getFeature()) + "]";
   }
}
