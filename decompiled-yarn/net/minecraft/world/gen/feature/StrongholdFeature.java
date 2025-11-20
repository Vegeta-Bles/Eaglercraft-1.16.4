package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class StrongholdFeature extends StructureFeature<DefaultFeatureConfig> {
   public StrongholdFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return StrongholdFeature.Start::new;
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, DefaultFeatureConfig _snowman) {
      return _snowman.isStrongholdStartingChunk(new ChunkPos(_snowman, _snowman));
   }

   public static class Start extends StructureStart<DefaultFeatureConfig> {
      private final long seed;

      public Start(StructureFeature<DefaultFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         this.seed = _snowman;
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, DefaultFeatureConfig _snowman) {
         int _snowmanxxxxxxx = 0;

         StrongholdGenerator.Start _snowmanxxxxxxxx;
         do {
            this.children.clear();
            this.boundingBox = BlockBox.empty();
            this.random.setCarverSeed(this.seed + (long)(_snowmanxxxxxxx++), _snowman, _snowman);
            StrongholdGenerator.init();
            _snowmanxxxxxxxx = new StrongholdGenerator.Start(this.random, (_snowman << 4) + 2, (_snowman << 4) + 2);
            this.children.add(_snowmanxxxxxxxx);
            _snowmanxxxxxxxx.fillOpenings(_snowmanxxxxxxxx, this.children, this.random);
            List<StructurePiece> _snowmanxxxxxxxxx = _snowmanxxxxxxxx.pieces;

            while (!_snowmanxxxxxxxxx.isEmpty()) {
               int _snowmanxxxxxxxxxx = this.random.nextInt(_snowmanxxxxxxxxx.size());
               StructurePiece _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.remove(_snowmanxxxxxxxxxx);
               _snowmanxxxxxxxxxxx.fillOpenings(_snowmanxxxxxxxx, this.children, this.random);
            }

            this.setBoundingBoxFromChildren();
            this.randomUpwardTranslation(_snowman.getSeaLevel(), this.random, 10);
         } while (this.children.isEmpty() || _snowmanxxxxxxxx.portalRoom == null);
      }
   }
}
