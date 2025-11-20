package net.minecraft.world;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.ColorResolver;

public interface WorldView extends BlockRenderView, CollisionView, BiomeAccess.Storage {
   @Nullable
   Chunk getChunk(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create);

   @Deprecated
   boolean isChunkLoaded(int chunkX, int chunkZ);

   int getTopY(Heightmap.Type heightmap, int x, int z);

   int getAmbientDarkness();

   BiomeAccess getBiomeAccess();

   default Biome getBiome(BlockPos pos) {
      return this.getBiomeAccess().getBiome(pos);
   }

   default Stream<BlockState> method_29556(Box _snowman) {
      int _snowmanx = MathHelper.floor(_snowman.minX);
      int _snowmanxx = MathHelper.floor(_snowman.maxX);
      int _snowmanxxx = MathHelper.floor(_snowman.minY);
      int _snowmanxxxx = MathHelper.floor(_snowman.maxY);
      int _snowmanxxxxx = MathHelper.floor(_snowman.minZ);
      int _snowmanxxxxxx = MathHelper.floor(_snowman.maxZ);
      return this.isRegionLoaded(_snowmanx, _snowmanxxx, _snowmanxxxxx, _snowmanxx, _snowmanxxxx, _snowmanxxxxxx) ? this.method_29546(_snowman) : Stream.empty();
   }

   @Override
   default int getColor(BlockPos pos, ColorResolver colorResolver) {
      return colorResolver.getColor(this.getBiome(pos), (double)pos.getX(), (double)pos.getZ());
   }

   @Override
   default Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
      Chunk _snowman = this.getChunk(biomeX >> 2, biomeZ >> 2, ChunkStatus.BIOMES, false);
      return _snowman != null && _snowman.getBiomeArray() != null
         ? _snowman.getBiomeArray().getBiomeForNoiseGen(biomeX, biomeY, biomeZ)
         : this.getGeneratorStoredBiome(biomeX, biomeY, biomeZ);
   }

   Biome getGeneratorStoredBiome(int biomeX, int biomeY, int biomeZ);

   boolean isClient();

   @Deprecated
   int getSeaLevel();

   DimensionType getDimension();

   default BlockPos getTopPosition(Heightmap.Type heightmap, BlockPos pos) {
      return new BlockPos(pos.getX(), this.getTopY(heightmap, pos.getX(), pos.getZ()), pos.getZ());
   }

   default boolean isAir(BlockPos pos) {
      return this.getBlockState(pos).isAir();
   }

   default boolean isSkyVisibleAllowingSea(BlockPos pos) {
      if (pos.getY() >= this.getSeaLevel()) {
         return this.isSkyVisible(pos);
      } else {
         BlockPos _snowman = new BlockPos(pos.getX(), this.getSeaLevel(), pos.getZ());
         if (!this.isSkyVisible(_snowman)) {
            return false;
         } else {
            for (BlockPos var4 = _snowman.down(); var4.getY() > pos.getY(); var4 = var4.down()) {
               BlockState _snowmanx = this.getBlockState(var4);
               if (_snowmanx.getOpacity(this, var4) > 0 && !_snowmanx.getMaterial().isLiquid()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Deprecated
   default float getBrightness(BlockPos pos) {
      return this.getDimension().method_28516(this.getLightLevel(pos));
   }

   default int getStrongRedstonePower(BlockPos pos, Direction direction) {
      return this.getBlockState(pos).getStrongRedstonePower(this, pos, direction);
   }

   default Chunk getChunk(BlockPos pos) {
      return this.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
   }

   default Chunk getChunk(int chunkX, int chunkZ) {
      return this.getChunk(chunkX, chunkZ, ChunkStatus.FULL, true);
   }

   default Chunk getChunk(int chunkX, int chunkZ, ChunkStatus status) {
      return this.getChunk(chunkX, chunkZ, status, true);
   }

   @Nullable
   @Override
   default BlockView getExistingChunk(int chunkX, int chunkZ) {
      return this.getChunk(chunkX, chunkZ, ChunkStatus.EMPTY, false);
   }

   default boolean isWater(BlockPos pos) {
      return this.getFluidState(pos).isIn(FluidTags.WATER);
   }

   default boolean containsFluid(Box box) {
      int _snowman = MathHelper.floor(box.minX);
      int _snowmanx = MathHelper.ceil(box.maxX);
      int _snowmanxx = MathHelper.floor(box.minY);
      int _snowmanxxx = MathHelper.ceil(box.maxY);
      int _snowmanxxxx = MathHelper.floor(box.minZ);
      int _snowmanxxxxx = MathHelper.ceil(box.maxZ);
      BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxx = _snowman; _snowmanxxxxxxx < _snowmanx; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowmanxx; _snowmanxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxx++) {
            for (int _snowmanxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxx++) {
               BlockState _snowmanxxxxxxxxxx = this.getBlockState(_snowmanxxxxxx.set(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx));
               if (!_snowmanxxxxxxxxxx.getFluidState().isEmpty()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   default int getLightLevel(BlockPos pos) {
      return this.getLightLevel(pos, this.getAmbientDarkness());
   }

   default int getLightLevel(BlockPos pos, int ambientDarkness) {
      return pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000
         ? this.getBaseLightLevel(pos, ambientDarkness)
         : 15;
   }

   @Deprecated
   default boolean isChunkLoaded(BlockPos pos) {
      return this.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
   }

   @Deprecated
   default boolean isRegionLoaded(BlockPos min, BlockPos max) {
      return this.isRegionLoaded(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
   }

   @Deprecated
   default boolean isRegionLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      if (maxY >= 0 && minY < 256) {
         minX >>= 4;
         minZ >>= 4;
         maxX >>= 4;
         maxZ >>= 4;

         for (int _snowman = minX; _snowman <= maxX; _snowman++) {
            for (int _snowmanx = minZ; _snowmanx <= maxZ; _snowmanx++) {
               if (!this.isChunkLoaded(_snowman, _snowmanx)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
