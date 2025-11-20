package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.structure.OceanRuinGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class OceanRuinFeature extends StructureFeature<OceanRuinFeatureConfig> {
   public OceanRuinFeature(Codec<OceanRuinFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   public StructureFeature.StructureStartFactory<OceanRuinFeatureConfig> getStructureStartFactory() {
      return OceanRuinFeature.Start::new;
   }

   public static enum BiomeType implements StringIdentifiable {
      WARM("warm"),
      COLD("cold");

      public static final Codec<OceanRuinFeature.BiomeType> CODEC = StringIdentifiable.createCodec(
         OceanRuinFeature.BiomeType::values, OceanRuinFeature.BiomeType::byName
      );
      private static final Map<String, OceanRuinFeature.BiomeType> BY_NAME = Arrays.stream(values())
         .collect(Collectors.toMap(OceanRuinFeature.BiomeType::getName, _snowman -> (OceanRuinFeature.BiomeType)_snowman));
      private final String name;

      private BiomeType(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      @Nullable
      public static OceanRuinFeature.BiomeType byName(String name) {
         return BY_NAME.get(name);
      }

      @Override
      public String asString() {
         return this.name;
      }
   }

   public static class Start extends StructureStart<OceanRuinFeatureConfig> {
      public Start(StructureFeature<OceanRuinFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, OceanRuinFeatureConfig _snowman) {
         int _snowmanxxxxxxx = _snowman * 16;
         int _snowmanxxxxxxxx = _snowman * 16;
         BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowmanxxxxxxx, 90, _snowmanxxxxxxxx);
         BlockRotation _snowmanxxxxxxxxxx = BlockRotation.random(this.random);
         OceanRuinGenerator.addPieces(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, this.children, this.random, _snowman);
         this.setBoundingBoxFromChildren();
      }
   }
}
