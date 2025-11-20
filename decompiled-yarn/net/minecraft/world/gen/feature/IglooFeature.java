package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.IglooGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class IglooFeature extends StructureFeature<DefaultFeatureConfig> {
   public IglooFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
      return IglooFeature.Start::new;
   }

   public static class Start extends StructureStart<DefaultFeatureConfig> {
      public Start(StructureFeature<DefaultFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, DefaultFeatureConfig _snowman) {
         int _snowmanxxxxxxx = _snowman * 16;
         int _snowmanxxxxxxxx = _snowman * 16;
         BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowmanxxxxxxx, 90, _snowmanxxxxxxxx);
         BlockRotation _snowmanxxxxxxxxxx = BlockRotation.random(this.random);
         IglooGenerator.addPieces(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, this.children, this.random);
         this.setBoundingBoxFromChildren();
      }
   }
}
