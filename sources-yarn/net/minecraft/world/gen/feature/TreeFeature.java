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
   public TreeFeature(Codec<TreeFeatureConfig> codec) {
      super(codec);
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
         Block lv = state.getBlock();
         return isSoil(lv) || lv == Blocks.FARMLAND;
      });
   }

   private static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
      return world.testBlockState(pos, state -> {
         Material lv = state.getMaterial();
         return lv == Material.REPLACEABLE_PLANT;
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
      int i = config.trunkPlacer.getHeight(random);
      int j = config.foliagePlacer.getRandomHeight(random, i, config);
      int k = i - j;
      int l = config.foliagePlacer.getRandomRadius(random, k);
      BlockPos lv;
      if (!config.skipFluidCheck) {
         int m = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos).getY();
         int n = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY();
         if (n - m > config.maxWaterDepth) {
            return false;
         }

         int o;
         if (config.heightmap == Heightmap.Type.OCEAN_FLOOR) {
            o = m;
         } else if (config.heightmap == Heightmap.Type.WORLD_SURFACE) {
            o = n;
         } else {
            o = world.getTopPosition(config.heightmap, pos).getY();
         }

         lv = new BlockPos(pos.getX(), o, pos.getZ());
      } else {
         lv = pos;
      }

      if (lv.getY() < 1 || lv.getY() + i + 1 > 256) {
         return false;
      } else if (!isDirtOrGrass(world, lv.down())) {
         return false;
      } else {
         OptionalInt optionalInt = config.minimumSize.getMinClippedHeight();
         int r = this.method_29963(world, i, lv, config);
         if (r >= i || optionalInt.isPresent() && r >= optionalInt.getAsInt()) {
            List<FoliagePlacer.TreeNode> list = config.trunkPlacer.generate(world, random, r, lv, logPositions, box, config);
            list.forEach(arg4 -> config.foliagePlacer.generate(world, random, config, r, arg4, j, l, leavesPositions, box));
            return true;
         } else {
            return false;
         }
      }
   }

   private int method_29963(TestableWorld arg, int i, BlockPos arg2, TreeFeatureConfig arg3) {
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (int j = 0; j <= i + 1; j++) {
         int k = arg3.minimumSize.method_27378(i, j);

         for (int l = -k; l <= k; l++) {
            for (int m = -k; m <= k; m++) {
               lv.set(arg2, l, j, m);
               if (!canTreeReplace(arg, lv) || !arg3.ignoreVines && isVine(arg, lv)) {
                  return j - 2;
               }
            }
         }
      }

      return i;
   }

   @Override
   protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
      setBlockStateWithoutUpdatingNeighbors(world, pos, state);
   }

   public final boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, TreeFeatureConfig arg4) {
      Set<BlockPos> set = Sets.newHashSet();
      Set<BlockPos> set2 = Sets.newHashSet();
      Set<BlockPos> set3 = Sets.newHashSet();
      BlockBox lv = BlockBox.empty();
      boolean bl = this.generate(arg, random, arg3, set, set2, lv, arg4);
      if (lv.minX <= lv.maxX && bl && !set.isEmpty()) {
         if (!arg4.decorators.isEmpty()) {
            List<BlockPos> list = Lists.newArrayList(set);
            List<BlockPos> list2 = Lists.newArrayList(set2);
            list.sort(Comparator.comparingInt(Vec3i::getY));
            list2.sort(Comparator.comparingInt(Vec3i::getY));
            arg4.decorators.forEach(decorator -> decorator.generate(arg, random, list, list2, set3, lv));
         }

         VoxelSet lv2 = this.placeLogsAndLeaves(arg, lv, set, set3);
         Structure.updateCorner(arg, 3, lv2, lv.minX, lv.minY, lv.minZ);
         return true;
      } else {
         return false;
      }
   }

   private VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
      List<Set<BlockPos>> list = Lists.newArrayList();
      VoxelSet lv = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
      int i = 6;

      for (int j = 0; j < 6; j++) {
         list.add(Sets.newHashSet());
      }

      BlockPos.Mutable lv2 = new BlockPos.Mutable();

      for (BlockPos lv3 : Lists.newArrayList(leaves)) {
         if (box.contains(lv3)) {
            lv.set(lv3.getX() - box.minX, lv3.getY() - box.minY, lv3.getZ() - box.minZ, true, true);
         }
      }

      for (BlockPos lv4 : Lists.newArrayList(logs)) {
         if (box.contains(lv4)) {
            lv.set(lv4.getX() - box.minX, lv4.getY() - box.minY, lv4.getZ() - box.minZ, true, true);
         }

         for (Direction lv5 : Direction.values()) {
            lv2.set(lv4, lv5);
            if (!logs.contains(lv2)) {
               BlockState lv6 = world.getBlockState(lv2);
               if (lv6.contains(Properties.DISTANCE_1_7)) {
                  list.get(0).add(lv2.toImmutable());
                  setBlockStateWithoutUpdatingNeighbors(world, lv2, lv6.with(Properties.DISTANCE_1_7, Integer.valueOf(1)));
                  if (box.contains(lv2)) {
                     lv.set(lv2.getX() - box.minX, lv2.getY() - box.minY, lv2.getZ() - box.minZ, true, true);
                  }
               }
            }
         }
      }

      for (int k = 1; k < 6; k++) {
         Set<BlockPos> set3 = list.get(k - 1);
         Set<BlockPos> set4 = list.get(k);

         for (BlockPos lv7 : set3) {
            if (box.contains(lv7)) {
               lv.set(lv7.getX() - box.minX, lv7.getY() - box.minY, lv7.getZ() - box.minZ, true, true);
            }

            for (Direction lv8 : Direction.values()) {
               lv2.set(lv7, lv8);
               if (!set3.contains(lv2) && !set4.contains(lv2)) {
                  BlockState lv9 = world.getBlockState(lv2);
                  if (lv9.contains(Properties.DISTANCE_1_7)) {
                     int l = lv9.get(Properties.DISTANCE_1_7);
                     if (l > k + 1) {
                        BlockState lv10 = lv9.with(Properties.DISTANCE_1_7, Integer.valueOf(k + 1));
                        setBlockStateWithoutUpdatingNeighbors(world, lv2, lv10);
                        if (box.contains(lv2)) {
                           lv.set(lv2.getX() - box.minX, lv2.getY() - box.minY, lv2.getZ() - box.minZ, true, true);
                        }

                        set4.add(lv2.toImmutable());
                     }
                  }
               }
            }
         }
      }

      return lv;
   }
}
