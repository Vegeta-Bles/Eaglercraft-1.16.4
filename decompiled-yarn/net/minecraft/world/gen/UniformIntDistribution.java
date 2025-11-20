package net.minecraft.world.gen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class UniformIntDistribution {
   public static final Codec<UniformIntDistribution> CODEC = Codec.either(
         Codec.INT,
         RecordCodecBuilder.create(
               _snowman -> _snowman.group(Codec.INT.fieldOf("base").forGetter(_snowmanx -> _snowmanx.base), Codec.INT.fieldOf("spread").forGetter(_snowmanx -> _snowmanx.spread))
                     .apply(_snowman, UniformIntDistribution::new)
            )
            .comapFlatMap(_snowman -> _snowman.spread < 0 ? DataResult.error("Spread must be non-negative, got: " + _snowman.spread) : DataResult.success(_snowman), Function.identity())
      )
      .xmap(_snowman -> (UniformIntDistribution)_snowman.map(UniformIntDistribution::of, _snowmanx -> _snowmanx), _snowman -> _snowman.spread == 0 ? Either.left(_snowman.base) : Either.right(_snowman));
   private final int base;
   private final int spread;

   public static Codec<UniformIntDistribution> createValidatedCodec(int minBase, int maxBase, int maxSpread) {
      Function<UniformIntDistribution, DataResult<UniformIntDistribution>> _snowman = _snowmanxxx -> {
         if (_snowmanxxx.base < minBase || _snowmanxxx.base > maxBase) {
            return DataResult.error("Base value out of range: " + _snowmanxxx.base + " [" + minBase + "-" + maxBase + "]");
         } else {
            return _snowmanxxx.spread <= maxSpread ? DataResult.success(_snowmanxxx) : DataResult.error("Spread too big: " + _snowmanxxx.spread + " > " + maxSpread);
         }
      };
      return CODEC.flatXmap(_snowman, _snowman);
   }

   private UniformIntDistribution(int base, int spread) {
      this.base = base;
      this.spread = spread;
   }

   public static UniformIntDistribution of(int value) {
      return new UniformIntDistribution(value, 0);
   }

   public static UniformIntDistribution of(int base, int spread) {
      return new UniformIntDistribution(base, spread);
   }

   public int getValue(Random random) {
      return this.spread == 0 ? this.base : this.base + random.nextInt(this.spread + 1);
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         UniformIntDistribution _snowmanx = (UniformIntDistribution)_snowman;
         return this.base == _snowmanx.base && this.spread == _snowmanx.spread;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.base, this.spread);
   }

   @Override
   public String toString() {
      return "[" + this.base + '-' + (this.base + this.spread) + ']';
   }
}
