/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  javax.annotation.Nullable
 */
package net.minecraft.block.pattern;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldView;

public class BlockPattern {
    private final Predicate<CachedBlockPosition>[][][] pattern;
    private final int depth;
    private final int height;
    private final int width;

    public BlockPattern(Predicate<CachedBlockPosition>[][][] pattern) {
        this.pattern = pattern;
        this.depth = pattern.length;
        if (this.depth > 0) {
            this.height = pattern[0].length;
            this.width = this.height > 0 ? pattern[0][0].length : 0;
        } else {
            this.height = 0;
            this.width = 0;
        }
    }

    public int getDepth() {
        return this.depth;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    @Nullable
    private Result testTransform(BlockPos frontTopLeft, Direction forwards, Direction up, LoadingCache<BlockPos, CachedBlockPosition> cache) {
        for (int i = 0; i < this.width; ++i) {
            for (_snowman = 0; _snowman < this.height; ++_snowman) {
                for (_snowman = 0; _snowman < this.depth; ++_snowman) {
                    if (this.pattern[_snowman][_snowman][i].test((CachedBlockPosition)cache.getUnchecked((Object)BlockPattern.translate(frontTopLeft, forwards, up, i, _snowman, _snowman)))) continue;
                    return null;
                }
            }
        }
        return new Result(frontTopLeft, forwards, up, cache, this.width, this.height, this.depth);
    }

    @Nullable
    public Result searchAround(WorldView world, BlockPos pos) {
        LoadingCache<BlockPos, CachedBlockPosition> loadingCache = BlockPattern.makeCache(world, false);
        int _snowman2 = Math.max(Math.max(this.width, this.height), this.depth);
        for (BlockPos blockPos : BlockPos.iterate(pos, pos.add(_snowman2 - 1, _snowman2 - 1, _snowman2 - 1))) {
            for (Direction direction : Direction.values()) {
                for (Direction direction2 : Direction.values()) {
                    if (direction2 == direction || direction2 == direction.getOpposite() || (_snowman = this.testTransform(blockPos, direction, direction2, loadingCache)) == null) continue;
                    return _snowman;
                }
            }
        }
        return null;
    }

    public static LoadingCache<BlockPos, CachedBlockPosition> makeCache(WorldView world, boolean forceLoad) {
        return CacheBuilder.newBuilder().build((CacheLoader)new BlockStateCacheLoader(world, forceLoad));
    }

    protected static BlockPos translate(BlockPos pos, Direction forwards, Direction up, int offsetLeft, int offsetDown, int offsetForwards) {
        if (forwards == up || forwards == up.getOpposite()) {
            throw new IllegalArgumentException("Invalid forwards & up combination");
        }
        Vec3i vec3i = new Vec3i(forwards.getOffsetX(), forwards.getOffsetY(), forwards.getOffsetZ());
        _snowman = new Vec3i(up.getOffsetX(), up.getOffsetY(), up.getOffsetZ());
        _snowman = vec3i.crossProduct(_snowman);
        return pos.add(_snowman.getX() * -offsetDown + _snowman.getX() * offsetLeft + vec3i.getX() * offsetForwards, _snowman.getY() * -offsetDown + _snowman.getY() * offsetLeft + vec3i.getY() * offsetForwards, _snowman.getZ() * -offsetDown + _snowman.getZ() * offsetLeft + vec3i.getZ() * offsetForwards);
    }

    public static class Result {
        private final BlockPos frontTopLeft;
        private final Direction forwards;
        private final Direction up;
        private final LoadingCache<BlockPos, CachedBlockPosition> cache;
        private final int width;
        private final int height;
        private final int depth;

        public Result(BlockPos frontTopLeft, Direction forwards, Direction up, LoadingCache<BlockPos, CachedBlockPosition> cache, int width, int height, int depth) {
            this.frontTopLeft = frontTopLeft;
            this.forwards = forwards;
            this.up = up;
            this.cache = cache;
            this.width = width;
            this.height = height;
            this.depth = depth;
        }

        public BlockPos getFrontTopLeft() {
            return this.frontTopLeft;
        }

        public Direction getForwards() {
            return this.forwards;
        }

        public Direction getUp() {
            return this.up;
        }

        public CachedBlockPosition translate(int n, int n2, int n3) {
            return (CachedBlockPosition)this.cache.getUnchecked((Object)BlockPattern.translate(this.frontTopLeft, this.getForwards(), this.getUp(), n, n2, n3));
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object)this).add("up", (Object)this.up).add("forwards", (Object)this.forwards).add("frontTopLeft", (Object)this.frontTopLeft).toString();
        }
    }

    static class BlockStateCacheLoader
    extends CacheLoader<BlockPos, CachedBlockPosition> {
        private final WorldView world;
        private final boolean forceLoad;

        public BlockStateCacheLoader(WorldView world, boolean forceLoad) {
            this.world = world;
            this.forceLoad = forceLoad;
        }

        public CachedBlockPosition load(BlockPos blockPos) throws Exception {
            return new CachedBlockPosition(this.world, blockPos, this.forceLoad);
        }

        public /* synthetic */ Object load(Object pos) throws Exception {
            return this.load((BlockPos)pos);
        }
    }
}

