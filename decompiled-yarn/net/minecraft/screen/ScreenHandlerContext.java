package net.minecraft.screen;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ScreenHandlerContext {
   ScreenHandlerContext EMPTY = new ScreenHandlerContext() {
      @Override
      public <T> Optional<T> run(BiFunction<World, BlockPos, T> function) {
         return Optional.empty();
      }
   };

   static ScreenHandlerContext create(World world, BlockPos pos) {
      return new ScreenHandlerContext() {
         @Override
         public <T> Optional<T> run(BiFunction<World, BlockPos, T> function) {
            return Optional.of(function.apply(world, pos));
         }
      };
   }

   <T> Optional<T> run(BiFunction<World, BlockPos, T> function);

   default <T> T run(BiFunction<World, BlockPos, T> function, T defaultValue) {
      return this.run(function).orElse(defaultValue);
   }

   default void run(BiConsumer<World, BlockPos> function) {
      this.run((_snowmanx, _snowmanxx) -> {
         function.accept(_snowmanx, _snowmanxx);
         return Optional.empty();
      });
   }
}
