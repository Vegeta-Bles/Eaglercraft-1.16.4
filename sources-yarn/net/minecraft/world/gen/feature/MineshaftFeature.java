package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.structure.MineshaftGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class MineshaftFeature extends StructureFeature<MineshaftFeatureConfig> {
   public MineshaftFeature(Codec<MineshaftFeatureConfig> codec) {
      super(codec);
   }

   protected boolean shouldStartAt(
      ChunkGenerator arg, BiomeSource arg2, long l, ChunkRandom arg3, int i, int j, Biome arg4, ChunkPos arg5, MineshaftFeatureConfig arg6
   ) {
      arg3.setCarverSeed(l, i, j);
      double d = (double)arg6.probability;
      return arg3.nextDouble() < d;
   }

   @Override
   public StructureFeature.StructureStartFactory<MineshaftFeatureConfig> getStructureStartFactory() {
      return MineshaftFeature.Start::new;
   }

   public static class Start extends StructureStart<MineshaftFeatureConfig> {
      public Start(StructureFeature<MineshaftFeatureConfig> arg, int i, int j, BlockBox arg2, int k, long l) {
         super(arg, i, j, arg2, k, l);
      }

      public void init(DynamicRegistryManager arg, ChunkGenerator arg2, StructureManager arg3, int i, int j, Biome arg4, MineshaftFeatureConfig arg5) {
         MineshaftGenerator.MineshaftRoom lv = new MineshaftGenerator.MineshaftRoom(0, this.random, (i << 4) + 2, (j << 4) + 2, arg5.type);
         this.children.add(lv);
         lv.fillOpenings(lv, this.children, this.random);
         this.setBoundingBoxFromChildren();
         if (arg5.type == MineshaftFeature.Type.MESA) {
            int k = -5;
            int l = arg2.getSeaLevel() - this.boundingBox.maxY + this.boundingBox.getBlockCountY() / 2 - -5;
            this.boundingBox.move(0, l, 0);

            for (StructurePiece lv2 : this.children) {
               lv2.translate(0, l, 0);
            }
         } else {
            this.randomUpwardTranslation(arg2.getSeaLevel(), this.random, 10);
         }
      }
   }

   public static enum Type implements StringIdentifiable {
      NORMAL("normal"),
      MESA("mesa");

      public static final Codec<MineshaftFeature.Type> CODEC = StringIdentifiable.createCodec(MineshaftFeature.Type::values, MineshaftFeature.Type::byName);
      private static final Map<String, MineshaftFeature.Type> BY_NAME = Arrays.stream(values())
         .collect(Collectors.toMap(MineshaftFeature.Type::getName, arg -> (MineshaftFeature.Type)arg));
      private final String name;

      private Type(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      private static MineshaftFeature.Type byName(String name) {
         return BY_NAME.get(name);
      }

      public static MineshaftFeature.Type byIndex(int index) {
         return index >= 0 && index < values().length ? values()[index] : NORMAL;
      }

      @Override
      public String asString() {
         return this.name;
      }
   }
}
