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
   public MineshaftFeature(Codec<MineshaftFeatureConfig> _snowman) {
      super(_snowman);
   }

   protected boolean shouldStartAt(ChunkGenerator _snowman, BiomeSource _snowman, long _snowman, ChunkRandom _snowman, int _snowman, int _snowman, Biome _snowman, ChunkPos _snowman, MineshaftFeatureConfig _snowman) {
      _snowman.setCarverSeed(_snowman, _snowman, _snowman);
      double _snowmanxxxxxxxxx = (double)_snowman.probability;
      return _snowman.nextDouble() < _snowmanxxxxxxxxx;
   }

   @Override
   public StructureFeature.StructureStartFactory<MineshaftFeatureConfig> getStructureStartFactory() {
      return MineshaftFeature.Start::new;
   }

   public static class Start extends StructureStart<MineshaftFeatureConfig> {
      public Start(StructureFeature<MineshaftFeatureConfig> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, MineshaftFeatureConfig _snowman) {
         MineshaftGenerator.MineshaftRoom _snowmanxxxxxxx = new MineshaftGenerator.MineshaftRoom(0, this.random, (_snowman << 4) + 2, (_snowman << 4) + 2, _snowman.type);
         this.children.add(_snowmanxxxxxxx);
         _snowmanxxxxxxx.fillOpenings(_snowmanxxxxxxx, this.children, this.random);
         this.setBoundingBoxFromChildren();
         if (_snowman.type == MineshaftFeature.Type.MESA) {
            int _snowmanxxxxxxxx = -5;
            int _snowmanxxxxxxxxx = _snowman.getSeaLevel() - this.boundingBox.maxY + this.boundingBox.getBlockCountY() / 2 - -5;
            this.boundingBox.move(0, _snowmanxxxxxxxxx, 0);

            for (StructurePiece _snowmanxxxxxxxxxx : this.children) {
               _snowmanxxxxxxxxxx.translate(0, _snowmanxxxxxxxxx, 0);
            }
         } else {
            this.randomUpwardTranslation(_snowman.getSeaLevel(), this.random, 10);
         }
      }
   }

   public static enum Type implements StringIdentifiable {
      NORMAL("normal"),
      MESA("mesa");

      public static final Codec<MineshaftFeature.Type> CODEC = StringIdentifiable.createCodec(MineshaftFeature.Type::values, MineshaftFeature.Type::byName);
      private static final Map<String, MineshaftFeature.Type> BY_NAME = Arrays.stream(values())
         .collect(Collectors.toMap(MineshaftFeature.Type::getName, _snowman -> (MineshaftFeature.Type)_snowman));
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
