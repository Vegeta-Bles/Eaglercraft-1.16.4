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

   public PillagerOutpostFeature(Codec<StructurePoolFeatureConfig> _snowman) {
      super(_snowman, 0, true, true);
   }

   @Override
   public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
      return MONSTER_SPAWNS;
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, StructurePoolFeatureConfig _snowman) {
      int _snowmanxxxxxxxxx = _snowman >> 4;
      int _snowmanxxxxxxxxxx = _snowman >> 4;
      _snowman.setSeed((long)(_snowmanxxxxxxxxx ^ _snowmanxxxxxxxxxx << 4) ^ _snowman);
      _snowman.nextInt();
      return _snowman.nextInt(5) != 0 ? false : !this.method_30845(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private boolean method_30845(ChunkGenerator _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman) {
      StructureConfig _snowmanxxxxx = _snowman.getStructuresConfig().getForType(StructureFeature.VILLAGE);
      if (_snowmanxxxxx == null) {
         return false;
      } else {
         for (int _snowmanxxxxxx = _snowman - 10; _snowmanxxxxxx <= _snowman + 10; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = _snowman - 10; _snowmanxxxxxxx <= _snowman + 10; _snowmanxxxxxxx++) {
               ChunkPos _snowmanxxxxxxxx = StructureFeature.VILLAGE.getStartChunk(_snowmanxxxxx, _snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxx);
               if (_snowmanxxxxxx == _snowmanxxxxxxxx.x && _snowmanxxxxxxx == _snowmanxxxxxxxx.z) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
