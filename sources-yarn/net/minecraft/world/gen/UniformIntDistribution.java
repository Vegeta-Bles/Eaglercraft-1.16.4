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
         RecordCodecBuilder.<UniformIntDistribution>create(
               instance -> instance.group(
                        Codec.INT.fieldOf("base").forGetter((UniformIntDistribution arg) -> arg.base),
                        Codec.INT.fieldOf("spread").forGetter((UniformIntDistribution arg) -> arg.spread)
                     )
                     .apply(instance, UniformIntDistribution::new)
            )
            .comapFlatMap(
               (UniformIntDistribution arg) -> arg.spread < 0 ? DataResult.error("Spread must be non-negative, got: " + arg.spread) : DataResult.success(arg),
               Function.identity()
            )
      )
      .xmap(
         either -> (UniformIntDistribution)either.map(UniformIntDistribution::of, Function.identity()),
         arg -> arg.spread == 0 ? Either.left(arg.base) : Either.right(arg)
      );
   private final int base;
   private final int spread;

   public static Codec<UniformIntDistribution> createValidatedCodec(int minBase, int maxBase, int maxSpread) {
      Function<UniformIntDistribution, DataResult<UniformIntDistribution>> function = arg -> {
         if (arg.base < minBase || arg.base > maxBase) {
            return DataResult.error("Base value out of range: " + arg.base + " [" + minBase + "-" + maxBase + "]");
         } else {
            return arg.spread <= maxSpread ? DataResult.success(arg) : DataResult.error("Spread too big: " + arg.spread + " > " + maxSpread);
         }
      };
      return CODEC.flatXmap(function, function);
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
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         UniformIntDistribution lv = (UniformIntDistribution)object;
         return this.base == lv.base && this.spread == lv.spread;
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
