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

   private static <C extends CarverConfig, F extends Carver<C>> F register(String _snowman, F _snowman) {
      return Registry.register(Registry.CARVER, _snowman, _snowman);
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
      Random _snowman = new Random(seed + (long)chunkX + (long)chunkZ);
      double _snowmanx = (double)(chunkX * 16 + 8);
      double _snowmanxx = (double)(chunkZ * 16 + 8);
      if (!(x < _snowmanx - 16.0 - yaw * 2.0) && !(z < _snowmanxx - 16.0 - yaw * 2.0) && !(x > _snowmanx + 16.0 + yaw * 2.0) && !(z > _snowmanxx + 16.0 + yaw * 2.0)) {
         int _snowmanxxx = Math.max(MathHelper.floor(x - yaw) - chunkX * 16 - 1, 0);
         int _snowmanxxxx = Math.min(MathHelper.floor(x + yaw) - chunkX * 16 + 1, 16);
         int _snowmanxxxxx = Math.max(MathHelper.floor(y - pitch) - 1, 1);
         int _snowmanxxxxxx = Math.min(MathHelper.floor(y + pitch) + 1, this.heightLimit - 8);
         int _snowmanxxxxxxx = Math.max(MathHelper.floor(z - yaw) - chunkZ * 16 - 1, 0);
         int _snowmanxxxxxxxx = Math.min(MathHelper.floor(z + yaw) - chunkZ * 16 + 1, 16);
         if (this.isRegionUncarvable(chunk, chunkX, chunkZ, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx)) {
            return false;
         } else {
            boolean _snowmanxxxxxxxxx = false;
            BlockPos.Mutable _snowmanxxxxxxxxxx = new BlockPos.Mutable();
            BlockPos.Mutable _snowmanxxxxxxxxxxx = new BlockPos.Mutable();
            BlockPos.Mutable _snowmanxxxxxxxxxxxx = new BlockPos.Mutable();

            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx + chunkX * 16;
               double _snowmanxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxx + 0.5 - x) / yaw;

               for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + chunkZ * 16;
                  double _snowmanxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxx + 0.5 - z) / yaw;
                  if (!(_snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx >= 1.0)) {
                     MutableBoolean _snowmanxxxxxxxxxxxxxxxxxxx = new MutableBoolean(false);

                     for (int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx--) {
                        double _snowmanxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxxxxxx - 0.5 - y) / pitch;
                        if (!this.isPositionExcluded(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx)) {
                           _snowmanxxxxxxxxx |= this.carveAtPoint(
                              chunk,
                              posToBiome,
                              carvingMask,
                              _snowman,
                              _snowmanxxxxxxxxxx,
                              _snowmanxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxx,
                              seaLevel,
                              chunkX,
                              chunkZ,
                              _snowmanxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxx
                           );
                        }
                     }
                  }
               }
            }

            return _snowmanxxxxxxxxx;
         }
      } else {
         return false;
      }
   }

   protected boolean carveAtPoint(
      Chunk chunk,
      Function<BlockPos, Biome> posToBiome,
      BitSet carvingMask,
      Random _snowman,
      BlockPos.Mutable _snowman,
      BlockPos.Mutable _snowman,
      BlockPos.Mutable _snowman,
      int seaLevel,
      int mainChunkX,
      int mainChunkZ,
      int x,
      int z,
      int relativeX,
      int y,
      int relativeZ,
      MutableBoolean _snowman
   ) {
      int _snowmanxxxxx = relativeX | relativeZ << 4 | y << 8;
      if (carvingMask.get(_snowmanxxxxx)) {
         return false;
      } else {
         carvingMask.set(_snowmanxxxxx);
         _snowman.set(x, y, z);
         BlockState _snowmanxxxxxx = chunk.getBlockState(_snowman);
         BlockState _snowmanxxxxxxx = chunk.getBlockState(_snowman.set(_snowman, Direction.UP));
         if (_snowmanxxxxxx.isOf(Blocks.GRASS_BLOCK) || _snowmanxxxxxx.isOf(Blocks.MYCELIUM)) {
            _snowman.setTrue();
         }

         if (!this.canCarveBlock(_snowmanxxxxxx, _snowmanxxxxxxx)) {
            return false;
         } else {
            if (y < 11) {
               chunk.setBlockState(_snowman, LAVA.getBlockState(), false);
            } else {
               chunk.setBlockState(_snowman, CAVE_AIR, false);
               if (_snowman.isTrue()) {
                  _snowman.set(_snowman, Direction.DOWN);
                  if (chunk.getBlockState(_snowman).isOf(Blocks.DIRT)) {
                     chunk.setBlockState(_snowman, posToBiome.apply(_snowman).getGenerationSettings().getSurfaceConfig().getTopMaterial(), false);
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
      C var10
   );

   public abstract boolean shouldCarve(Random random, int chunkX, int chunkZ, C config);

   protected boolean canAlwaysCarveBlock(BlockState state) {
      return this.alwaysCarvableBlocks.contains(state.getBlock());
   }

   protected boolean canCarveBlock(BlockState state, BlockState stateAbove) {
      return this.canAlwaysCarveBlock(state) || (state.isOf(Blocks.SAND) || state.isOf(Blocks.GRAVEL)) && !stateAbove.getFluidState().isIn(FluidTags.WATER);
   }

   protected boolean isRegionUncarvable(Chunk chunk, int mainChunkX, int mainChunkZ, int relMinX, int relMaxX, int minY, int maxY, int relMinZ, int relMaxZ) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();

      for (int _snowmanx = relMinX; _snowmanx < relMaxX; _snowmanx++) {
         for (int _snowmanxx = relMinZ; _snowmanxx < relMaxZ; _snowmanxx++) {
            for (int _snowmanxxx = minY - 1; _snowmanxxx <= maxY + 1; _snowmanxxx++) {
               if (this.carvableFluids.contains(chunk.getFluidState(_snowman.set(_snowmanx + mainChunkX * 16, _snowmanxxx, _snowmanxx + mainChunkZ * 16)).getFluid())) {
                  return true;
               }

               if (_snowmanxxx != maxY + 1 && !this.isOnBoundary(relMinX, relMaxX, relMinZ, relMaxZ, _snowmanx, _snowmanxx)) {
                  _snowmanxxx = maxY;
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
      double _snowman = (double)(mainChunkX * 16 + 8);
      double _snowmanx = (double)(mainChunkZ * 16 + 8);
      double _snowmanxx = x - _snowman;
      double _snowmanxxx = z - _snowmanx;
      double _snowmanxxxx = (double)(branchCount - branch);
      double _snowmanxxxxx = (double)(baseWidth + 2.0F + 16.0F);
      return _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx - _snowmanxxxx * _snowmanxxxx <= _snowmanxxxxx * _snowmanxxxxx;
   }

   protected abstract boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y);
}
