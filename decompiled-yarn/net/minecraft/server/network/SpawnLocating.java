package net.minecraft.server.network;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

public class SpawnLocating {
   @Nullable
   protected static BlockPos findOverworldSpawn(ServerWorld world, int x, int z, boolean validSpawnNeeded) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable(x, 0, z);
      Biome _snowmanx = world.getBiome(_snowman);
      boolean _snowmanxx = world.getDimension().hasCeiling();
      BlockState _snowmanxxx = _snowmanx.getGenerationSettings().getSurfaceConfig().getTopMaterial();
      if (validSpawnNeeded && !_snowmanxxx.getBlock().isIn(BlockTags.VALID_SPAWN)) {
         return null;
      } else {
         WorldChunk _snowmanxxxx = world.getChunk(x >> 4, z >> 4);
         int _snowmanxxxxx = _snowmanxx
            ? world.getChunkManager().getChunkGenerator().getSpawnHeight()
            : _snowmanxxxx.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, x & 15, z & 15);
         if (_snowmanxxxxx < 0) {
            return null;
         } else {
            int _snowmanxxxxxx = _snowmanxxxx.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x & 15, z & 15);
            if (_snowmanxxxxxx <= _snowmanxxxxx && _snowmanxxxxxx > _snowmanxxxx.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, x & 15, z & 15)) {
               return null;
            } else {
               for (int _snowmanxxxxxxx = _snowmanxxxxx + 1; _snowmanxxxxxxx >= 0; _snowmanxxxxxxx--) {
                  _snowman.set(x, _snowmanxxxxxxx, z);
                  BlockState _snowmanxxxxxxxx = world.getBlockState(_snowman);
                  if (!_snowmanxxxxxxxx.getFluidState().isEmpty()) {
                     break;
                  }

                  if (_snowmanxxxxxxxx.equals(_snowmanxxx)) {
                     return _snowman.up().toImmutable();
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   public static BlockPos findServerSpawnPoint(ServerWorld world, ChunkPos chunkPos, boolean validSpawnNeeded) {
      for (int _snowman = chunkPos.getStartX(); _snowman <= chunkPos.getEndX(); _snowman++) {
         for (int _snowmanx = chunkPos.getStartZ(); _snowmanx <= chunkPos.getEndZ(); _snowmanx++) {
            BlockPos _snowmanxx = findOverworldSpawn(world, _snowman, _snowmanx, validSpawnNeeded);
            if (_snowmanxx != null) {
               return _snowmanxx;
            }
         }
      }

      return null;
   }
}
