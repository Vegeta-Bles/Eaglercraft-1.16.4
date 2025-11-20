package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class TreeFeature extends Feature<TreeFeatureConfig> {
   public TreeFeature(Codec<TreeFeatureConfig> _snowman) {
      super(_snowman);
   }

   public static boolean canTreeReplace(TestableWorld world, BlockPos pos) {
      return canReplace(world, pos) || world.testBlockState(pos, state -> state.isIn(BlockTags.LOGS));
   }

   private static boolean isVine(TestableWorld world, BlockPos pos) {
      return world.testBlockState(pos, state -> state.isOf(Blocks.VINE));
   }

   private static boolean isWater(TestableWorld world, BlockPos pos) {
      return world.testBlockState(pos, state -> state.isOf(Blocks.WATER));
   }

   public static boolean isAirOrLeaves(TestableWorld world, BlockPos pos) {
      return world.testBlockState(pos, state -> state.isAir() || state.isIn(BlockTags.LEAVES));
   }

   private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
      return world.testBlockState(pos, state -> {
         Block _snowman = state.getBlock();
         return isSoil(_snowman) || _snowman == Blocks.FARMLAND;
      });
   }

   private static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
      return world.testBlockState(pos, state -> {
         Material _snowman = state.getMaterial();
         return _snowman == Material.REPLACEABLE_PLANT;
      });
   }

   public static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
      world.setBlockState(pos, state, 19);
   }

   public static boolean canReplace(TestableWorld world, BlockPos pos) {
      return isAirOrLeaves(world, pos) || isReplaceablePlant(world, pos) || isWater(world, pos);
   }

   private boolean generate(
      ModifiableTestableWorld world,
      Random random,
      BlockPos pos,
      Set<BlockPos> logPositions,
      Set<BlockPos> leavesPositions,
      BlockBox box,
      TreeFeatureConfig config
   ) {
      int _snowman = config.trunkPlacer.getHeight(random);
      int _snowmanx = config.foliagePlacer.getRandomHeight(random, _snowman, config);
      int _snowmanxx = _snowman - _snowmanx;
      int _snowmanxxx = config.foliagePlacer.getRandomRadius(random, _snowmanxx);
      BlockPos _snowmanxxxx;
      if (!config.skipFluidCheck) {
         int _snowmanxxxxx = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos).getY();
         int _snowmanxxxxxx = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY();
         if (_snowmanxxxxxx - _snowmanxxxxx > config.maxWaterDepth) {
            return false;
         }

         int _snowmanxxxxxxx;
         if (config.heightmap == Heightmap.Type.OCEAN_FLOOR) {
            _snowmanxxxxxxx = _snowmanxxxxx;
         } else if (config.heightmap == Heightmap.Type.WORLD_SURFACE) {
            _snowmanxxxxxxx = _snowmanxxxxxx;
         } else {
            _snowmanxxxxxxx = world.getTopPosition(config.heightmap, pos).getY();
         }

         _snowmanxxxx = new BlockPos(pos.getX(), _snowmanxxxxxxx, pos.getZ());
      } else {
         _snowmanxxxx = pos;
      }

      if (_snowmanxxxx.getY() < 1 || _snowmanxxxx.getY() + _snowman + 1 > 256) {
         return false;
      } else if (!isDirtOrGrass(world, _snowmanxxxx.down())) {
         return false;
      } else {
         OptionalInt _snowmanxxxxxxx = config.minimumSize.getMinClippedHeight();
         int _snowmanxxxxxxxx = this.method_29963(world, _snowman, _snowmanxxxx, config);
         if (_snowmanxxxxxxxx >= _snowman || _snowmanxxxxxxx.isPresent() && _snowmanxxxxxxxx >= _snowmanxxxxxxx.getAsInt()) {
            List<FoliagePlacer.TreeNode> _snowmanxxxxxxxxx = config.trunkPlacer.generate(world, random, _snowmanxxxxxxxx, _snowmanxxxx, logPositions, box, config);
            _snowmanxxxxxxxxx.forEach(_snowmanxxxxxxxxxx -> config.foliagePlacer.generate(world, random, config, _snowman, _snowmanxxxxxxxxxx, _snowman, _snowman, leavesPositions, box));
            return true;
         } else {
            return false;
         }
      }
   }

   private int method_29963(TestableWorld _snowman, int _snowman, BlockPos _snowman, TreeFeatureConfig _snowman) {
      BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx <= _snowman + 1; _snowmanxxxxx++) {
         int _snowmanxxxxxx = _snowman.minimumSize.method_27378(_snowman, _snowmanxxxxx);

         for (int _snowmanxxxxxxx = -_snowmanxxxxxx; _snowmanxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = -_snowmanxxxxxx; _snowmanxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxx++) {
               _snowmanxxxx.set(_snowman, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx);
               if (!canTreeReplace(_snowman, _snowmanxxxx) || !_snowman.ignoreVines && isVine(_snowman, _snowmanxxxx)) {
                  return _snowmanxxxxx - 2;
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
      setBlockStateWithoutUpdatingNeighbors(world, pos, state);
   }

   public final boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, TreeFeatureConfig _snowman) {
      Set<BlockPos> _snowmanxxxxx = Sets.newHashSet();
      Set<BlockPos> _snowmanxxxxxx = Sets.newHashSet();
      Set<BlockPos> _snowmanxxxxxxx = Sets.newHashSet();
      BlockBox _snowmanxxxxxxxx = BlockBox.empty();
      boolean _snowmanxxxxxxxxx = this.generate(_snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowman);
      if (_snowmanxxxxxxxx.minX <= _snowmanxxxxxxxx.maxX && _snowmanxxxxxxxxx && !_snowmanxxxxx.isEmpty()) {
         if (!_snowman.decorators.isEmpty()) {
            List<BlockPos> _snowmanxxxxxxxxxx = Lists.newArrayList(_snowmanxxxxx);
            List<BlockPos> _snowmanxxxxxxxxxxx = Lists.newArrayList(_snowmanxxxxxx);
            _snowmanxxxxxxxxxx.sort(Comparator.comparingInt(Vec3i::getY));
            _snowmanxxxxxxxxxxx.sort(Comparator.comparingInt(Vec3i::getY));
            _snowman.decorators.forEach(decorator -> decorator.generate(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
         }

         VoxelSet _snowmanxxxxxxxxxx = this.placeLogsAndLeaves(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
         Structure.updateCorner(_snowman, 3, _snowmanxxxxxxxxxx, _snowmanxxxxxxxx.minX, _snowmanxxxxxxxx.minY, _snowmanxxxxxxxx.minZ);
         return true;
      } else {
         return false;
      }
   }

   private VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
      List<Set<BlockPos>> _snowman = Lists.newArrayList();
      VoxelSet _snowmanx = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
      int _snowmanxx = 6;

      for (int _snowmanxxx = 0; _snowmanxxx < 6; _snowmanxxx++) {
         _snowman.add(Sets.newHashSet());
      }

      BlockPos.Mutable _snowmanxxx = new BlockPos.Mutable();

      for (BlockPos _snowmanxxxx : Lists.newArrayList(leaves)) {
         if (box.contains(_snowmanxxxx)) {
            _snowmanx.set(_snowmanxxxx.getX() - box.minX, _snowmanxxxx.getY() - box.minY, _snowmanxxxx.getZ() - box.minZ, true, true);
         }
      }

      for (BlockPos _snowmanxxxxx : Lists.newArrayList(logs)) {
         if (box.contains(_snowmanxxxxx)) {
            _snowmanx.set(_snowmanxxxxx.getX() - box.minX, _snowmanxxxxx.getY() - box.minY, _snowmanxxxxx.getZ() - box.minZ, true, true);
         }

         for (Direction _snowmanxxxxxx : Direction.values()) {
            _snowmanxxx.set(_snowmanxxxxx, _snowmanxxxxxx);
            if (!logs.contains(_snowmanxxx)) {
               BlockState _snowmanxxxxxxx = world.getBlockState(_snowmanxxx);
               if (_snowmanxxxxxxx.contains(Properties.DISTANCE_1_7)) {
                  _snowman.get(0).add(_snowmanxxx.toImmutable());
                  setBlockStateWithoutUpdatingNeighbors(world, _snowmanxxx, _snowmanxxxxxxx.with(Properties.DISTANCE_1_7, Integer.valueOf(1)));
                  if (box.contains(_snowmanxxx)) {
                     _snowmanx.set(_snowmanxxx.getX() - box.minX, _snowmanxxx.getY() - box.minY, _snowmanxxx.getZ() - box.minZ, true, true);
                  }
               }
            }
         }
      }

      for (int _snowmanxxxxx = 1; _snowmanxxxxx < 6; _snowmanxxxxx++) {
         Set<BlockPos> _snowmanxxxxxxx = _snowman.get(_snowmanxxxxx - 1);
         Set<BlockPos> _snowmanxxxxxxxx = _snowman.get(_snowmanxxxxx);

         for (BlockPos _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
            if (box.contains(_snowmanxxxxxxxxx)) {
               _snowmanx.set(_snowmanxxxxxxxxx.getX() - box.minX, _snowmanxxxxxxxxx.getY() - box.minY, _snowmanxxxxxxxxx.getZ() - box.minZ, true, true);
            }

            for (Direction _snowmanxxxxxxxxxx : Direction.values()) {
               _snowmanxxx.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
               if (!_snowmanxxxxxxx.contains(_snowmanxxx) && !_snowmanxxxxxxxx.contains(_snowmanxxx)) {
                  BlockState _snowmanxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
                  if (_snowmanxxxxxxxxxxx.contains(Properties.DISTANCE_1_7)) {
                     int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.get(Properties.DISTANCE_1_7);
                     if (_snowmanxxxxxxxxxxxx > _snowmanxxxxx + 1) {
                        BlockState _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.with(Properties.DISTANCE_1_7, Integer.valueOf(_snowmanxxxxx + 1));
                        setBlockStateWithoutUpdatingNeighbors(world, _snowmanxxx, _snowmanxxxxxxxxxxxxx);
                        if (box.contains(_snowmanxxx)) {
                           _snowmanx.set(_snowmanxxx.getX() - box.minX, _snowmanxxx.getY() - box.minY, _snowmanxxx.getZ() - box.minZ, true, true);
                        }

                        _snowmanxxxxxxxx.add(_snowmanxxx.toImmutable());
                     }
                  }
               }
            }
         }
      }

      return _snowmanx;
   }
}
