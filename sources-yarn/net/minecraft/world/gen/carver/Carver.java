package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class Carver<C extends CarverConfig> {
   public static final Carver<ProbabilityConfig> CAVE = register("cave", new CaveCarver(ProbabilityConfig.CODEC, 256));
   public static final Carver<ProbabilityConfig> NETHER_CAVE = register("nether_cave", new NetherCaveCarver(ProbabilityConfig.CODEC));
   public static final Carver<ProbabilityConfig> CANYON = register("canyon", new RavineCarver(ProbabilityConfig.CODEC));
   public static final Carver<ProbabilityConfig> UNDERWATER_CANYON = register("underwater_canyon", new UnderwaterRavineCarver(ProbabilityConfig.CODEC));
   public static final Carver<ProbabilityConfig> UNDERWATER_CAVE = register("underwater_cave", new UnderwaterCaveCarver(ProbabilityConfig.CODEC));
   protected static final BlockState AIR = Blocks.AIR.getDefaultState();
   protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
   protected static final FluidState WATER = Fluids.WATER.getDefaultState();
   protected static final FluidState LAVA = Fluids.LAVA.getDefaultState();
   protected Set<Block> alwaysCarvableBlocks = ImmutableSet.of(
      Blocks.STONE,
      Blocks.GRANITE,
      Blocks.DIORITE,
      Blocks.ANDESITE,
      Blocks.DIRT,
      Blocks.COARSE_DIRT,
      new Block[]{
         Blocks.PODZOL,
         Blocks.GRASS_BLOCK,
         Blocks.TERRACOTTA,
         Blocks.WHITE_TERRACOTTA,
         Blocks.ORANGE_TERRACOTTA,
         Blocks.MAGENTA_TERRACOTTA,
         Blocks.LIGHT_BLUE_TERRACOTTA,
         Blocks.YELLOW_TERRACOTTA,
         Blocks.LIME_TERRACOTTA,
         Blocks.PINK_TERRACOTTA,
         Blocks.GRAY_TERRACOTTA,
         Blocks.LIGHT_GRAY_TERRACOTTA,
         Blocks.CYAN_TERRACOTTA,
         Blocks.PURPLE_TERRACOTTA,
         Blocks.BLUE_TERRACOTTA,
         Blocks.BROWN_TERRACOTTA,
         Blocks.GREEN_TERRACOTTA,
         Blocks.RED_TERRACOTTA,
         Blocks.BLACK_TERRACOTTA,
         Blocks.SANDSTONE,
         Blocks.RED_SANDSTONE,
         Blocks.MYCELIUM,
         Blocks.SNOW,
         Blocks.PACKED_ICE
      }
   );
   protected Set<Fluid> carvableFluids = ImmutableSet.of(Fluids.WATER);
   private final Codec<ConfiguredCarver<C>> codec;
   protected final int heightLimit;

   private static <C extends CarverConfig, F extends Carver<C>> F register(String string, F arg) {
      return Registry.register(Registry.CARVER, string, arg);
   }

   public Carver(Codec<C> configCodec, int heightLimit) {
      this.heightLimit = heightLimit;
      this.codec = configCodec.fieldOf("config").xmap(this::configure, ConfiguredCarver::getConfig).codec();
   }

   public ConfiguredCarver<C> configure(C config) {
      return new ConfiguredCarver<>(this, config);
   }

   public Codec<ConfiguredCarver<C>> getCodec() {
      return this.codec;
   }

   public int getBranchFactor() {
      return 4;
   }

   protected boolean carveRegion(
      Chunk chunk,
      Function<BlockPos, Biome> posToBiome,
      long seed,
      int seaLevel,
      int chunkX,
      int chunkZ,
      double x,
      double y,
      double z,
      double yaw,
      double pitch,
      BitSet carvingMask
   ) {
      Random random = new Random(seed + (long)chunkX + (long)chunkZ);
      double m = (double)(chunkX * 16 + 8);
      double n = (double)(chunkZ * 16 + 8);
      if (!(x < m - 16.0 - yaw * 2.0) && !(z < n - 16.0 - yaw * 2.0) && !(x > m + 16.0 + yaw * 2.0) && !(z > n + 16.0 + yaw * 2.0)) {
         int o = Math.max(MathHelper.floor(x - yaw) - chunkX * 16 - 1, 0);
         int p = Math.min(MathHelper.floor(x + yaw) - chunkX * 16 + 1, 16);
         int q = Math.max(MathHelper.floor(y - pitch) - 1, 1);
         int r = Math.min(MathHelper.floor(y + pitch) + 1, this.heightLimit - 8);
         int s = Math.max(MathHelper.floor(z - yaw) - chunkZ * 16 - 1, 0);
         int t = Math.min(MathHelper.floor(z + yaw) - chunkZ * 16 + 1, 16);
         if (this.isRegionUncarvable(chunk, chunkX, chunkZ, o, p, q, r, s, t)) {
            return false;
         } else {
            boolean bl = false;
            BlockPos.Mutable lv = new BlockPos.Mutable();
            BlockPos.Mutable lv2 = new BlockPos.Mutable();
            BlockPos.Mutable lv3 = new BlockPos.Mutable();

            for (int u = o; u < p; u++) {
               int v = u + chunkX * 16;
               double w = ((double)v + 0.5 - x) / yaw;

               for (int xx = s; xx < t; xx++) {
                  int yx = xx + chunkZ * 16;
                  double zx = ((double)yx + 0.5 - z) / yaw;
                  if (!(w * w + zx * zx >= 1.0)) {
                     MutableBoolean mutableBoolean = new MutableBoolean(false);

                     for (int aa = r; aa > q; aa--) {
                        double ab = ((double)aa - 0.5 - y) / pitch;
                        if (!this.isPositionExcluded(w, ab, zx, aa)) {
                           bl |= this.carveAtPoint(
                              chunk, posToBiome, carvingMask, random, lv, lv2, lv3, seaLevel, chunkX, chunkZ, v, yx, u, aa, xx, mutableBoolean
                           );
                        }
                     }
                  }
               }
            }

            return bl;
         }
      } else {
         return false;
      }
   }

   protected boolean carveAtPoint(
      Chunk chunk,
      Function<BlockPos, Biome> posToBiome,
      BitSet carvingMask,
      Random random,
      BlockPos.Mutable arg2,
      BlockPos.Mutable arg3,
      BlockPos.Mutable arg4,
      int seaLevel,
      int mainChunkX,
      int mainChunkZ,
      int x,
      int z,
      int relativeX,
      int y,
      int relativeZ,
      MutableBoolean mutableBoolean
   ) {
      int q = relativeX | relativeZ << 4 | y << 8;
      if (carvingMask.get(q)) {
         return false;
      } else {
         carvingMask.set(q);
         arg2.set(x, y, z);
         BlockState lv = chunk.getBlockState(arg2);
         BlockState lv2 = chunk.getBlockState(arg3.set(arg2, Direction.UP));
         if (lv.isOf(Blocks.GRASS_BLOCK) || lv.isOf(Blocks.MYCELIUM)) {
            mutableBoolean.setTrue();
         }

         if (!this.canCarveBlock(lv, lv2)) {
            return false;
         } else {
            if (y < 11) {
               chunk.setBlockState(arg2, LAVA.getBlockState(), false);
            } else {
               chunk.setBlockState(arg2, CAVE_AIR, false);
               if (mutableBoolean.isTrue()) {
                  arg4.set(arg2, Direction.DOWN);
                  if (chunk.getBlockState(arg4).isOf(Blocks.DIRT)) {
                     chunk.setBlockState(arg4, posToBiome.apply(arg2).getGenerationSettings().getSurfaceConfig().getTopMaterial(), false);
                  }
               }
            }

            return true;
         }
      }
   }

   public abstract boolean carve(
      Chunk chunk,
      Function<BlockPos, Biome> posToBiome,
      Random random,
      int seaLevel,
      int chunkX,
      int chunkZ,
      int mainChunkX,
      int mainChunkZ,
      BitSet carvingMask,
      C arg2
   );

   public abstract boolean shouldCarve(Random random, int chunkX, int chunkZ, C config);

   protected boolean canAlwaysCarveBlock(BlockState state) {
      return this.alwaysCarvableBlocks.contains(state.getBlock());
   }

   protected boolean canCarveBlock(BlockState state, BlockState stateAbove) {
      return this.canAlwaysCarveBlock(state) || (state.isOf(Blocks.SAND) || state.isOf(Blocks.GRAVEL)) && !stateAbove.getFluidState().isIn(FluidTags.WATER);
   }

   protected boolean isRegionUncarvable(Chunk chunk, int mainChunkX, int mainChunkZ, int relMinX, int relMaxX, int minY, int maxY, int relMinZ, int relMaxZ) {
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (int q = relMinX; q < relMaxX; q++) {
         for (int r = relMinZ; r < relMaxZ; r++) {
            for (int s = minY - 1; s <= maxY + 1; s++) {
               if (this.carvableFluids.contains(chunk.getFluidState(lv.set(q + mainChunkX * 16, s, r + mainChunkZ * 16)).getFluid())) {
                  return true;
               }

               if (s != maxY + 1 && !this.isOnBoundary(relMinX, relMaxX, relMinZ, relMaxZ, q, r)) {
                  s = maxY;
               }
            }
         }
      }

      return false;
   }

   private boolean isOnBoundary(int minX, int maxX, int minZ, int maxZ, int x, int z) {
      return x == minX || x == maxX - 1 || z == minZ || z == maxZ - 1;
   }

   protected boolean canCarveBranch(int mainChunkX, int mainChunkZ, double x, double z, int branch, int branchCount, float baseWidth) {
      double g = (double)(mainChunkX * 16 + 8);
      double h = (double)(mainChunkZ * 16 + 8);
      double m = x - g;
      double n = z - h;
      double o = (double)(branchCount - branch);
      double p = (double)(baseWidth + 2.0F + 16.0F);
      return m * m + n * n - o * o <= p * p;
   }

   protected abstract boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y);
}
