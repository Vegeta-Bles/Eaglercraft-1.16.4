package net.minecraft.block;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class DoubleBlockProperties {
   public static <S extends BlockEntity> DoubleBlockProperties.PropertySource<S> toPropertySource(
      BlockEntityType<S> blockEntityType,
      Function<BlockState, DoubleBlockProperties.Type> typeMapper,
      Function<BlockState, Direction> function2,
      DirectionProperty arg2,
      BlockState state,
      WorldAccess world,
      BlockPos pos,
      BiPredicate<WorldAccess, BlockPos> fallbackTester
   ) {
      S lv = blockEntityType.get(world, pos);
      if (lv == null) {
         return DoubleBlockProperties.PropertyRetriever::getFallback;
      } else if (fallbackTester.test(world, pos)) {
         return DoubleBlockProperties.PropertyRetriever::getFallback;
      } else {
         DoubleBlockProperties.Type lv2 = typeMapper.apply(state);
         boolean bl = lv2 == DoubleBlockProperties.Type.SINGLE;
         boolean bl2 = lv2 == DoubleBlockProperties.Type.FIRST;
         if (bl) {
            return new DoubleBlockProperties.PropertySource.Single<>(lv);
         } else {
            BlockPos lv3 = pos.offset(function2.apply(state));
            BlockState lv4 = world.getBlockState(lv3);
            if (lv4.isOf(state.getBlock())) {
               DoubleBlockProperties.Type lv5 = typeMapper.apply(lv4);
               if (lv5 != DoubleBlockProperties.Type.SINGLE && lv2 != lv5 && lv4.get(arg2) == state.get(arg2)) {
                  if (fallbackTester.test(world, lv3)) {
                     return DoubleBlockProperties.PropertyRetriever::getFallback;
                  }

                  S lv6 = blockEntityType.get(world, lv3);
                  if (lv6 != null) {
                     S lv7 = bl2 ? lv : lv6;
                     S lv8 = bl2 ? lv6 : lv;
                     return new DoubleBlockProperties.PropertySource.Pair<>(lv7, lv8);
                  }
               }
            }

            return new DoubleBlockProperties.PropertySource.Single<>(lv);
         }
      }
   }

   public interface PropertyRetriever<S, T> {
      T getFromBoth(S first, S second);

      T getFrom(S single);

      T getFallback();
   }

   public interface PropertySource<S> {
      <T> T apply(DoubleBlockProperties.PropertyRetriever<? super S, T> retriever);

      public static final class Pair<S> implements DoubleBlockProperties.PropertySource<S> {
         private final S first;
         private final S second;

         public Pair(S first, S second) {
            this.first = first;
            this.second = second;
         }

         @Override
         public <T> T apply(DoubleBlockProperties.PropertyRetriever<? super S, T> arg) {
            return arg.getFromBoth(this.first, this.second);
         }
      }

      public static final class Single<S> implements DoubleBlockProperties.PropertySource<S> {
         private final S single;

         public Single(S single) {
            this.single = single;
         }

         @Override
         public <T> T apply(DoubleBlockProperties.PropertyRetriever<? super S, T> arg) {
            return arg.getFrom(this.single);
         }
      }
   }

   public static enum Type {
      SINGLE,
      FIRST,
      SECOND;

      private Type() {
      }
   }
}
