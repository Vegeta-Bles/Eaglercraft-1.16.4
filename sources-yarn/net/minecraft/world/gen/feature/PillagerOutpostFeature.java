package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;

public class PillagerOutpostFeature extends JigsawFeature {
   private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of(new SpawnSettings.SpawnEntry(EntityType.PILLAGER, 1, 1, 1));

   public PillagerOutpostFeature(Codec<StructurePoolFeatureConfig> codec) {
      super(codec, 0, true, true);
   }

   @Override
   public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
      return MONSTER_SPAWNS;
   }

   protected boolean shouldStartAt(
      ChunkGenerator arg, BiomeSource arg2, long l, ChunkRandom arg3, int i, int j, Biome arg4, ChunkPos arg5, StructurePoolFeatureConfig arg6
   ) {
      int k = i >> 4;
      int m = j >> 4;
      arg3.setSeed((long)(k ^ m << 4) ^ l);
      arg3.nextInt();
      return arg3.nextInt(5) != 0 ? false : !this.method_30845(arg, l, arg3, i, j);
   }

   private boolean method_30845(ChunkGenerator arg, long l, ChunkRandom arg2, int i, int j) {
      StructureConfig lv = arg.getStructuresConfig().getForType(StructureFeature.VILLAGE);
      if (lv == null) {
         return false;
      } else {
         for (int k = i - 10; k <= i + 10; k++) {
            for (int m = j - 10; m <= j + 10; m++) {
               ChunkPos lv2 = StructureFeature.VILLAGE.getStartChunk(lv, l, arg2, k, m);
               if (k == lv2.x && m == lv2.z) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
