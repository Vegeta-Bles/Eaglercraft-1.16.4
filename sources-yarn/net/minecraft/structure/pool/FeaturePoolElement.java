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
      instance -> instance.group(ConfiguredFeature.REGISTRY_CODEC.fieldOf("feature").forGetter(arg -> arg.feature), method_28883())
            .apply(instance, FeaturePoolElement::new)
   );
   private final Supplier<ConfiguredFeature<?, ?>> feature;
   private final CompoundTag tag;

   protected FeaturePoolElement(Supplier<ConfiguredFeature<?, ?>> feature, StructurePool.Projection projection) {
      super(projection);
      this.feature = feature;
      this.tag = this.createDefaultJigsawTag();
   }

   private CompoundTag createDefaultJigsawTag() {
      CompoundTag lv = new CompoundTag();
      lv.putString("name", "minecraft:bottom");
      lv.putString("final_state", "minecraft:air");
      lv.putString("pool", "minecraft:empty");
      lv.putString("target", "minecraft:empty");
      lv.putString("joint", JigsawBlockEntity.Joint.ROLLABLE.asString());
      return lv;
   }

   public BlockPos getStart(StructureManager arg, BlockRotation arg2) {
      return BlockPos.ORIGIN;
   }

   @Override
   public List<Structure.StructureBlockInfo> getStructureBlockInfos(StructureManager structureManager, BlockPos pos, BlockRotation rotation, Random random) {
      List<Structure.StructureBlockInfo> list = Lists.newArrayList();
      list.add(
         new Structure.StructureBlockInfo(
            pos, Blocks.JIGSAW.getDefaultState().with(JigsawBlock.ORIENTATION, JigsawOrientation.byDirections(Direction.DOWN, Direction.SOUTH)), this.tag
         )
      );
      return list;
   }

   @Override
   public BlockBox getBoundingBox(StructureManager structureManager, BlockPos pos, BlockRotation rotation) {
      BlockPos lv = this.getStart(structureManager, rotation);
      return new BlockBox(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + lv.getX(), pos.getY() + lv.getY(), pos.getZ() + lv.getZ());
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
      return this.feature.get().generate(arg2, arg4, random, arg5);
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
