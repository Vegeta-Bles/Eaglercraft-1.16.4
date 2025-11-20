package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;

public class DecoratedFeatureConfig implements FeatureConfig {
   public static final Codec<DecoratedFeatureConfig> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               ConfiguredFeature.REGISTRY_CODEC.fieldOf("feature").forGetter(arg -> arg.feature),
               ConfiguredDecorator.CODEC.fieldOf("decorator").forGetter(arg -> arg.decorator)
            )
            .apply(instance, DecoratedFeatureConfig::new)
   );
   public final Supplier<ConfiguredFeature<?, ?>> feature;
   public final ConfiguredDecorator<?> decorator;

   public DecoratedFeatureConfig(Supplier<ConfiguredFeature<?, ?>> feature, ConfiguredDecorator<?> decorator) {
      this.feature = feature;
      this.decorator = decorator;
   }

   @Override
   public String toString() {
      return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), Registry.FEATURE.getId(this.feature.get().getFeature()), this.decorator);
   }

   @Override
   public Stream<ConfiguredFeature<?, ?>> method_30649() {
      return this.feature.get().method_30648();
   }
}
