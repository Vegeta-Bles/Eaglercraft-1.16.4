package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.ShipwreckGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ShipwreckFeature extends StructureFeature<ShipwreckFeatureConfig> {
   public ShipwreckFeature(Codec<ShipwreckFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public StructureFeature.StructureStartFactory<ShipwreckFeatureConfig> getStructureStartFactory() {
      return ShipwreckFeature.Start::new;
   }

   public static class Start extends StructureStart<ShipwreckFeatureConfig> {
      public Start(StructureFeature<ShipwreckFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, ShipwreckFeatureConfig _snowman) {
         BlockRotation _snowmanxxxxxxx = BlockRotation.random(this.random);
         BlockPos _snowmanxxxxxxxx = new BlockPos(_snowman * 16, 90, _snowman * 16);
         ShipwreckGenerator.addParts(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx, this.children, this.random, _snowman);
         this.setBoundingBoxFromChildren();
      }
   }
}
