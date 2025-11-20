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
   public OceanRuinFeature(Codec<OceanRuinFeatureConfig> codec) {
      super(codec);
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
         .collect(Collectors.toMap(OceanRuinFeature.BiomeType::getName, arg -> (OceanRuinFeature.BiomeType)arg));
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
      public Start(StructureFeature<OceanRuinFeatureConfig> arg, int i, int j, BlockBox arg2, int k, long l) {
         super(arg, i, j, arg2, k, l);
      }

      public void init(DynamicRegistryManager arg, ChunkGenerator arg2, StructureManager arg3, int i, int j, Biome arg4, OceanRuinFeatureConfig arg5) {
         int k = i * 16;
         int l = j * 16;
         BlockPos lv = new BlockPos(k, 90, l);
         BlockRotation lv2 = BlockRotation.random(this.random);
         OceanRuinGenerator.addPieces(arg3, lv, lv2, this.children, this.random, arg5);
         this.setBoundingBoxFromChildren();
      }
   }
}
