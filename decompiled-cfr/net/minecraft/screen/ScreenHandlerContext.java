/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ScreenHandlerContext {
    public static final ScreenHandlerContext EMPTY = new ScreenHandlerContext(){

        public <T> Optional<T> run(BiFunction<World, BlockPos, T> function) {
            return Optional.empty();
        }
    };

    public static ScreenHandlerContext create(World world, BlockPos pos) {
        return new ScreenHandlerContext(world, pos){
            final /* synthetic */ World field_17305;
            final /* synthetic */ BlockPos field_17306;
            {
                this.field_17305 = world;
                this.field_17306 = blockPos;
            }

            public <T> Optional<T> run(BiFunction<World, BlockPos, T> function) {
                return Optional.of(function.apply(this.field_17305, this.field_17306));
            }
        };
    }

    public <T> Optional<T> run(BiFunction<World, BlockPos, T> var1);

    default public <T> T run(BiFunction<World, BlockPos, T> function, T defaultValue) {
        return this.run(function).orElse(defaultValue);
    }

    default public void run(BiConsumer<World, BlockPos> function) {
        this.run((World world, BlockPos blockPos) -> {
            function.accept((World)world, (BlockPos)blockPos);
            return Optional.empty();
        });
    }
}

