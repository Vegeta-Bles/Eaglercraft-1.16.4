package net.minecraft.client.options;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.text.Text;

public class LogarithmicOption extends DoubleOption {
   public LogarithmicOption(
      String _snowman, double _snowman, double _snowman, float _snowman, Function<GameOptions, Double> _snowman, BiConsumer<GameOptions, Double> _snowman, BiFunction<GameOptions, DoubleOption, Text> _snowman
   ) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public double getRatio(double value) {
      return Math.log(value / this.min) / Math.log(this.max / this.min);
   }

   @Override
   public double getValue(double ratio) {
      return this.min * Math.pow(Math.E, Math.log(this.max / this.min) * ratio);
   }
}
