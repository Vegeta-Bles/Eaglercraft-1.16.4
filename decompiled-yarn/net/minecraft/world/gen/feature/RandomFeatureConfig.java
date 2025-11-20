package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RandomFeatureConfig implements FeatureConfig {
   public static final Codec<RandomFeatureConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.apply2(
            RandomFeatureConfig::new,
            RandomFeatureEntry.CODEC.listOf().fieldOf("features").forGetter(_snowmanx -> _snowmanx.features),
            ConfiguredFeature.REGISTRY_CODEC.fieldOf("default").forGetter(_snowmanx -> _snowmanx.defaultFeature)
         )
   );
   public final List<RandomFeatureEntry> features;
   public final Supplier<ConfiguredFeature<?, ?>> defaultFeature;

   public RandomFeatureConfig(List<RandomFeatureEntry> features, ConfiguredFeature<?, ?> defaultFeature) {
      this(features, () -> defaultFeature);
   }

   private RandomFeatureConfig(List<RandomFeatureEntry> features, Supplier<ConfiguredFeature<?, ?>> defaultFeature) {
      this.features = features;
      this.defaultFeature = defaultFeature;
   }

   @Override
   public Stream<ConfiguredFeature<?, ?>> method_30649() {
      return Stream.concat(this.features.stream().flatMap(_snowman -> _snowman.feature.get().method_30648()), this.defaultFeature.get().method_30648());
   }
}
