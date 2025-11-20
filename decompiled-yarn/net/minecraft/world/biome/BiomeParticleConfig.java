package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public class BiomeParticleConfig {
   public static final Codec<BiomeParticleConfig> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(ParticleTypes.TYPE_CODEC.fieldOf("options").forGetter(_snowmanx -> _snowmanx.particle), Codec.FLOAT.fieldOf("probability").forGetter(_snowmanx -> _snowmanx.probability))
            .apply(_snowman, BiomeParticleConfig::new)
   );
   private final ParticleEffect particle;
   private final float probability;

   public BiomeParticleConfig(ParticleEffect particle, float probability) {
      this.particle = particle;
      this.probability = probability;
   }

   public ParticleEffect getParticle() {
      return this.particle;
   }

   public boolean shouldAddParticle(Random random) {
      return random.nextFloat() <= this.probability;
   }
}
