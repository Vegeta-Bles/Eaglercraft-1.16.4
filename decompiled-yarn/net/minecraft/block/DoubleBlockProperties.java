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
      Function<BlockState, Direction> _snowman,
      DirectionProperty _snowman,
      BlockState state,
      WorldAccess world,
      BlockPos pos,
      BiPredicate<WorldAccess, BlockPos> fallbackTester
   ) {
      S _snowmanxx = blockEntityType.get(world, pos);
      if (_snowmanxx == null) {
         return DoubleBlockProperties.PropertyRetriever::getFallback;
      } else if (fallbackTester.test(world, pos)) {
         return DoubleBlockProperties.PropertyRetriever::getFallback;
      } else {
         DoubleBlockProperties.Type _snowmanxxx = typeMapper.apply(state);
         boolean _snowmanxxxx = _snowmanxxx == DoubleBlockProperties.Type.SINGLE;
         boolean _snowmanxxxxx = _snowmanxxx == DoubleBlockProperties.Type.FIRST;
         if (_snowmanxxxx) {
            return new DoubleBlockProperties.PropertySource.Single<>(_snowmanxx);
         } else {
            BlockPos _snowmanxxxxxx = pos.offset(_snowman.apply(state));
            BlockState _snowmanxxxxxxx = world.getBlockState(_snowmanxxxxxx);
            if (_snowmanxxxxxxx.isOf(state.getBlock())) {
               DoubleBlockProperties.Type _snowmanxxxxxxxx = typeMapper.apply(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxx != DoubleBlockProperties.Type.SINGLE && _snowmanxxx != _snowmanxxxxxxxx && _snowmanxxxxxxx.get(_snowman) == state.get(_snowman)) {
                  if (fallbackTester.test(world, _snowmanxxxxxx)) {
                     return DoubleBlockProperties.PropertyRetriever::getFallback;
                  }

                  S _snowmanxxxxxxxxx = blockEntityType.get(world, _snowmanxxxxxx);
                  if (_snowmanxxxxxxxxx != null) {
                     S _snowmanxxxxxxxxxx = _snowmanxxxxx ? _snowmanxx : _snowmanxxxxxxxxx;
                     S _snowmanxxxxxxxxxxx = _snowmanxxxxx ? _snowmanxxxxxxxxx : _snowmanxx;
                     return new DoubleBlockProperties.PropertySource.Pair<>(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                  }
               }
            }

            return new DoubleBlockProperties.PropertySource.Single<>(_snowmanxx);
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
         public <T> T apply(DoubleBlockProperties.PropertyRetriever<? super S, T> _snowman) {
            return _snowman.getFromBoth(this.first, this.second);
         }
      }

      public static final class Single<S> implements DoubleBlockProperties.PropertySource<S> {
         private final S single;

         public Single(S single) {
            this.single = single;
         }

         @Override
         public <T> T apply(DoubleBlockProperties.PropertyRetriever<? super S, T> _snowman) {
            return _snowman.getFrom(this.single);
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
