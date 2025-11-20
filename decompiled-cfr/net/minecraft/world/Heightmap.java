/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.collection.PackedIntegerArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class Heightmap {
    private static final Predicate<BlockState> ALWAYS_TRUE = blockState -> !blockState.isAir();
    private static final Predicate<BlockState> SUFFOCATES = blockState -> blockState.getMaterial().blocksMovement();
    private final PackedIntegerArray storage = new PackedIntegerArray(9, 256);
    private final Predicate<BlockState> blockPredicate;
    private final Chunk chunk;

    public Heightmap(Chunk chunk, Type type) {
        this.blockPredicate = type.getBlockPredicate();
        this.chunk = chunk;
    }

    public static void populateHeightmaps(Chunk chunk, Set<Type> types) {
        int n = types.size();
        ObjectArrayList _snowman2 = new ObjectArrayList(n);
        ObjectListIterator _snowman3 = _snowman2.iterator();
        int n2 = chunk.getHighestNonEmptySectionYOffset() + 16;
        BlockPos.Mutable _snowman4 = new BlockPos.Mutable();
        for (_snowman = 0; _snowman < 16; ++_snowman) {
            block1: for (_snowman = 0; _snowman < 16; ++_snowman) {
                for (Type type : types) {
                    _snowman2.add((Object)chunk.getHeightmap(type));
                }
                for (int i = n2 - 1; i >= 0; --i) {
                    _snowman4.set(_snowman, i, _snowman);
                    BlockState blockState = chunk.getBlockState(_snowman4);
                    if (blockState.isOf(Blocks.AIR)) continue;
                    while (_snowman3.hasNext()) {
                        Heightmap heightmap = (Heightmap)_snowman3.next();
                        if (!heightmap.blockPredicate.test(blockState)) continue;
                        heightmap.set(_snowman, _snowman, i + 1);
                        _snowman3.remove();
                    }
                    if (_snowman2.isEmpty()) continue block1;
                    _snowman3.back(n);
                }
            }
        }
    }

    public boolean trackUpdate(int x, int y, int z, BlockState state) {
        int n = this.get(x, z);
        if (y <= n - 2) {
            return false;
        }
        if (this.blockPredicate.test(state)) {
            if (y >= n) {
                this.set(x, z, y + 1);
                return true;
            }
        } else if (n - 1 == y) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = y - 1; i >= 0; --i) {
                mutable.set(x, i, z);
                if (!this.blockPredicate.test(this.chunk.getBlockState(mutable))) continue;
                this.set(x, z, i + 1);
                return true;
            }
            this.set(x, z, 0);
            return true;
        }
        return false;
    }

    public int get(int x, int z) {
        return this.get(Heightmap.toIndex(x, z));
    }

    private int get(int index) {
        return this.storage.get(index);
    }

    private void set(int x, int z, int height) {
        this.storage.set(Heightmap.toIndex(x, z), height);
    }

    public void setTo(long[] heightmap) {
        System.arraycopy(heightmap, 0, this.storage.getStorage(), 0, heightmap.length);
    }

    public long[] asLongArray() {
        return this.storage.getStorage();
    }

    private static int toIndex(int x, int z) {
        return x + z * 16;
    }

    static /* synthetic */ Predicate method_16683() {
        return ALWAYS_TRUE;
    }

    static /* synthetic */ Predicate method_16681() {
        return SUFFOCATES;
    }

    public static enum Type implements StringIdentifiable
    {
        WORLD_SURFACE_WG("WORLD_SURFACE_WG", Purpose.WORLDGEN, Heightmap.method_16683()),
        WORLD_SURFACE("WORLD_SURFACE", Purpose.CLIENT, Heightmap.method_16683()),
        OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Purpose.WORLDGEN, Heightmap.method_16681()),
        OCEAN_FLOOR("OCEAN_FLOOR", Purpose.LIVE_WORLD, Heightmap.method_16681()),
        MOTION_BLOCKING("MOTION_BLOCKING", Purpose.CLIENT, blockState -> blockState.getMaterial().blocksMovement() || !blockState.getFluidState().isEmpty()),
        MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES", Purpose.LIVE_WORLD, blockState -> (blockState.getMaterial().blocksMovement() || !blockState.getFluidState().isEmpty()) && !(blockState.getBlock() instanceof LeavesBlock));

        public static final Codec<Type> CODEC;
        private final String name;
        private final Purpose purpose;
        private final Predicate<BlockState> blockPredicate;
        private static final Map<String, Type> BY_NAME;

        private Type(String name, Purpose purpose, Predicate<BlockState> blockPredicate) {
            this.name = name;
            this.purpose = purpose;
            this.blockPredicate = blockPredicate;
        }

        public String getName() {
            return this.name;
        }

        public boolean shouldSendToClient() {
            return this.purpose == Purpose.CLIENT;
        }

        public boolean isStoredServerSide() {
            return this.purpose != Purpose.WORLDGEN;
        }

        @Nullable
        public static Type byName(String name) {
            return BY_NAME.get(name);
        }

        public Predicate<BlockState> getBlockPredicate() {
            return this.blockPredicate;
        }

        @Override
        public String asString() {
            return this.name;
        }

        static {
            CODEC = StringIdentifiable.createCodec(Type::values, Type::byName);
            BY_NAME = Util.make(Maps.newHashMap(), hashMap -> {
                for (Type type : Type.values()) {
                    hashMap.put(type.name, type);
                }
            });
        }
    }

    public static enum Purpose {
        WORLDGEN,
        LIVE_WORLD,
        CLIENT;

    }
}

