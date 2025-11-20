package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherFortressFeature extends StructureFeature<DefaultFeatureConfig> {
   private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of(
      new SpawnSettings.SpawnEntry(EntityType.BLAZE, 10, 2, 3),
      new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 5, 4, 4),
      new SpawnSettings.SpawnEntry(EntityType.WITHER_SKELETON, 8, 5, 5),
      new SpawnSettings.SpawnEntry(EntityType.SKELETON, 2, 5, 5),
      new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 3, 4, 4)
   );

   public NetherFortressFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, DefaultFeatureConfig _snowman) {
      return _snowman.nextInt(5) < 2;
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return NetherFortressFeature.Start::new;
   }

   @Override
   public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
      return MONSTER_SPAWNS;
   }

   public static class Start extends StructureStart<DefaultFeatureConfig> {
      public Start(StructureFeature<DefaultFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, DefaultFeatureConfig _snowman) {
         NetherFortressGenerator.Start _snowmanxxxxxxx = new NetherFortressGenerator.Start(this.random, (_snowman << 4) + 2, (_snowman << 4) + 2);
         this.children.add(_snowmanxxxxxxx);
         _snowmanxxxxxxx.fillOpenings(_snowmanxxxxxxx, this.children, this.random);
         List<StructurePiece> _snowmanxxxxxxxx = _snowmanxxxxxxx.pieces;

         while (!_snowmanxxxxxxxx.isEmpty()) {
            int _snowmanxxxxxxxxx = this.random.nextInt(_snowmanxxxxxxxx.size());
            StructurePiece _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.remove(_snowmanxxxxxxxxx);
            _snowmanxxxxxxxxxx.fillOpenings(_snowmanxxxxxxx, this.children, this.random);
         }

         this.setBoundingBoxFromChildren();
         this.randomUpwardTranslation(this.random, 48, 70);
      }
   }
}
