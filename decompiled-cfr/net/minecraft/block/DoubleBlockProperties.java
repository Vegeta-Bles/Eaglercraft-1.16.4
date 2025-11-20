/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class DoubleBlockProperties {
    public static <S extends BlockEntity> PropertySource<S> toPropertySource(BlockEntityType<S> blockEntityType, Function<BlockState, Type> typeMapper, Function<BlockState, Direction> function, DirectionProperty directionProperty, BlockState state, WorldAccess world, BlockPos pos, BiPredicate<WorldAccess, BlockPos> fallbackTester) {
        S s = blockEntityType.get(world, pos);
        if (s == null) {
            return PropertyRetriever::getFallback;
        }
        if (fallbackTester.test(world, pos)) {
            return PropertyRetriever::getFallback;
        }
        Type _snowman2 = typeMapper.apply(state);
        boolean _snowman3 = _snowman2 == Type.SINGLE;
        boolean bl = _snowman = _snowman2 == Type.FIRST;
        if (_snowman3) {
            return new PropertySource.Single<S>(s);
        }
        BlockPos _snowman4 = pos.offset(function.apply(state));
        BlockState _snowman5 = world.getBlockState(_snowman4);
        if (_snowman5.isOf(state.getBlock()) && (_snowman = typeMapper.apply(_snowman5)) != Type.SINGLE && _snowman2 != _snowman && _snowman5.get(directionProperty) == state.get(directionProperty)) {
            if (fallbackTester.test(world, _snowman4)) {
                return PropertyRetriever::getFallback;
            }
            _snowman = blockEntityType.get(world, _snowman4);
            if (_snowman != null) {
                _snowman = _snowman ? s : _snowman;
                _snowman = _snowman ? _snowman : s;
                return new PropertySource.Pair<S>(_snowman, _snowman);
            }
        }
        return new PropertySource.Single<S>(s);
    }

    public static interface PropertySource<S> {
        public <T> T apply(PropertyRetriever<? super S, T> var1);

        public static final class Single<S>
        implements PropertySource<S> {
            private final S single;

            public Single(S single) {
                this.single = single;
            }

            @Override
            public <T> T apply(PropertyRetriever<? super S, T> propertyRetriever) {
                return propertyRetriever.getFrom(this.single);
            }
        }

        public static final class Pair<S>
        implements PropertySource<S> {
            private final S first;
            private final S second;

            public Pair(S first, S second) {
                this.first = first;
                this.second = second;
            }

            @Override
            public <T> T apply(PropertyRetriever<? super S, T> propertyRetriever) {
                return propertyRetriever.getFromBoth(this.first, this.second);
            }
        }
    }

    public static interface PropertyRetriever<S, T> {
        public T getFromBoth(S var1, S var2);

        public T getFrom(S var1);

        public T getFallback();
    }

    public static enum Type {
        SINGLE,
        FIRST,
        SECOND;

    }
}

