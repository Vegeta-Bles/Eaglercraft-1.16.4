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
      BlockPos.Mutable lv = new BlockPos.Mutable(x, 0, z);
      Biome lv2 = world.getBiome(lv);
      boolean bl2 = world.getDimension().hasCeiling();
      BlockState lv3 = lv2.getGenerationSettings().getSurfaceConfig().getTopMaterial();
      if (validSpawnNeeded && !lv3.getBlock().isIn(BlockTags.VALID_SPAWN)) {
         return null;
      } else {
         WorldChunk lv4 = world.getChunk(x >> 4, z >> 4);
         int k = bl2 ? world.getChunkManager().getChunkGenerator().getSpawnHeight() : lv4.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, x & 15, z & 15);
         if (k < 0) {
            return null;
         } else {
            int l = lv4.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x & 15, z & 15);
            if (l <= k && l > lv4.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, x & 15, z & 15)) {
               return null;
            } else {
               for (int m = k + 1; m >= 0; m--) {
                  lv.set(x, m, z);
                  BlockState lv5 = world.getBlockState(lv);
                  if (!lv5.getFluidState().isEmpty()) {
                     break;
                  }

                  if (lv5.equals(lv3)) {
                     return lv.up().toImmutable();
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   public static BlockPos findServerSpawnPoint(ServerWorld world, ChunkPos chunkPos, boolean validSpawnNeeded) {
      for (int i = chunkPos.getStartX(); i <= chunkPos.getEndX(); i++) {
         for (int j = chunkPos.getStartZ(); j <= chunkPos.getEndZ(); j++) {
            BlockPos lv = findOverworldSpawn(world, i, j, validSpawnNeeded);
            if (lv != null) {
               return lv;
            }
         }
      }

      return null;
   }
}
