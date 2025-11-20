/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.tree.TreeDecorator;

public class TreeFeature
extends Feature<TreeFeatureConfig> {
    public TreeFeature(Codec<TreeFeatureConfig> codec) {
        super(codec);
    }

    public static boolean canTreeReplace(TestableWorld world, BlockPos pos) {
        return TreeFeature.canReplace(world, pos) || world.testBlockState(pos, state -> state.isIn(BlockTags.LOGS));
    }

    private static boolean isVine(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, state -> state.isOf(Blocks.VINE));
    }

    private static boolean isWater(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, state -> state.isOf(Blocks.WATER));
    }

    public static boolean isAirOrLeaves(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, state -> state.isAir() || state.isIn(BlockTags.LEAVES));
    }

    private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, state -> {
            Block block = state.getBlock();
            return TreeFeature.isSoil(block) || block == Blocks.FARMLAND;
        });
    }

    private static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, state -> {
            Material material = state.getMaterial();
            return material == Material.REPLACEABLE_PLANT;
        });
    }

    public static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, 19);
    }

    public static boolean canReplace(TestableWorld world, BlockPos pos) {
        return TreeFeature.isAirOrLeaves(world, pos) || TreeFeature.isReplaceablePlant(world, pos) || TreeFeature.isWater(world, pos);
    }

    private boolean generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config) {
        BlockPos blockPos;
        int n = config.trunkPlacer.getHeight(random);
        _snowman = config.foliagePlacer.getRandomHeight(random, n, config);
        _snowman = n - _snowman;
        _snowman = config.foliagePlacer.getRandomRadius(random, _snowman);
        if (!config.skipFluidCheck) {
            _snowman = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos).getY();
            _snowman3 = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY();
            if (_snowman3 - _snowman > config.maxWaterDepth) {
                return false;
            }
            _snowman = config.heightmap == Heightmap.Type.OCEAN_FLOOR ? _snowman : (config.heightmap == Heightmap.Type.WORLD_SURFACE ? _snowman3 : world.getTopPosition(config.heightmap, pos).getY());
            blockPos = new BlockPos(pos.getX(), _snowman, pos.getZ());
        } else {
            blockPos = pos;
        }
        if (blockPos.getY() < 1 || blockPos.getY() + n + 1 > 256) {
            return false;
        }
        if (!TreeFeature.isDirtOrGrass(world, blockPos.down())) {
            return false;
        }
        OptionalInt _snowman2 = config.minimumSize.getMinClippedHeight();
        int _snowman3 = this.method_29963(world, n, blockPos, config);
        if (!(_snowman3 >= n || _snowman2.isPresent() && _snowman3 >= _snowman2.getAsInt())) {
            return false;
        }
        List<FoliagePlacer.TreeNode> _snowman4 = config.trunkPlacer.generate(world, random, _snowman3, blockPos, logPositions, box, config);
        _snowman4.forEach(treeNode -> treeFeatureConfig.foliagePlacer.generate(world, random, config, _snowman3, (FoliagePlacer.TreeNode)treeNode, _snowman, _snowman, leavesPositions, box));
        return true;
    }

    private int method_29963(TestableWorld testableWorld, int n2, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
        int n2;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i <= n2 + 1; ++i) {
            _snowman = treeFeatureConfig.minimumSize.method_27378(n2, i);
            for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                for (_snowman = -_snowman; _snowman <= _snowman; ++_snowman) {
                    mutable.set(blockPos, _snowman, i, _snowman);
                    if (TreeFeature.canTreeReplace(testableWorld, mutable) && (treeFeatureConfig.ignoreVines || !TreeFeature.isVine(testableWorld, mutable))) continue;
                    return i - 2;
                }
            }
        }
        return n2;
    }

    @Override
    protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
        TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, pos, state);
    }

    @Override
    public final boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
        HashSet hashSet = Sets.newHashSet();
        _snowman = Sets.newHashSet();
        _snowman = Sets.newHashSet();
        BlockBox _snowman2 = BlockBox.empty();
        boolean _snowman3 = this.generate(structureWorldAccess, random, blockPos, hashSet, _snowman, _snowman2, treeFeatureConfig);
        if (_snowman2.minX > _snowman2.maxX || !_snowman3 || hashSet.isEmpty()) {
            return false;
        }
        if (!treeFeatureConfig.decorators.isEmpty()) {
            Object object = Lists.newArrayList((Iterable)hashSet);
            ArrayList _snowman4 = Lists.newArrayList((Iterable)_snowman);
            object.sort(Comparator.comparingInt(Vec3i::getY));
            _snowman4.sort(Comparator.comparingInt(Vec3i::getY));
            treeFeatureConfig.decorators.forEach(arg_0 -> TreeFeature.method_23381(structureWorldAccess, random, (List)object, _snowman4, _snowman, _snowman2, arg_0));
        }
        object = this.placeLogsAndLeaves(structureWorldAccess, _snowman2, hashSet, _snowman);
        Structure.updateCorner(structureWorldAccess, 3, (VoxelSet)object, _snowman2.minX, _snowman2.minY, _snowman2.minZ);
        return true;
    }

    private VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
        ArrayList arrayList = Lists.newArrayList();
        BitSetVoxelSet _snowman2 = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
        int _snowman3 = 6;
        for (int i = 0; i < 6; ++i) {
            arrayList.add(Sets.newHashSet());
        }
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Object object : Lists.newArrayList(leaves)) {
            if (!box.contains((Vec3i)object)) continue;
            ((VoxelSet)_snowman2).set(((Vec3i)object).getX() - box.minX, ((Vec3i)object).getY() - box.minY, ((Vec3i)object).getZ() - box.minZ, true, true);
        }
        for (Object object : Lists.newArrayList(logs)) {
            if (box.contains((Vec3i)object)) {
                ((VoxelSet)_snowman2).set(((Vec3i)object).getX() - box.minX, ((Vec3i)object).getY() - box.minY, ((Vec3i)object).getZ() - box.minZ, true, true);
            }
            for (Direction direction : Direction.values()) {
                mutable.set((Vec3i)object, direction);
                if (logs.contains(mutable) || !(_snowman = world.getBlockState(mutable)).contains(Properties.DISTANCE_1_7)) continue;
                ((Set)arrayList.get(0)).add(mutable.toImmutable());
                TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, mutable, (BlockState)_snowman.with(Properties.DISTANCE_1_7, 1));
                if (!box.contains(mutable)) continue;
                ((VoxelSet)_snowman2).set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
            }
        }
        for (int i = 1; i < 6; ++i) {
            Object object;
            object = (Set)arrayList.get(i - 1);
            Set _snowman4 = (Set)arrayList.get(i);
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                BlockPos blockPos = (BlockPos)iterator.next();
                if (box.contains(blockPos)) {
                    ((VoxelSet)_snowman2).set(blockPos.getX() - box.minX, blockPos.getY() - box.minY, blockPos.getZ() - box.minZ, true, true);
                }
                for (Direction direction : Direction.values()) {
                    mutable.set(blockPos, direction);
                    if (object.contains(mutable) || _snowman4.contains(mutable) || !(_snowman = world.getBlockState(mutable)).contains(Properties.DISTANCE_1_7) || (_snowman = _snowman.get(Properties.DISTANCE_1_7).intValue()) <= i + 1) continue;
                    BlockState blockState = (BlockState)_snowman.with(Properties.DISTANCE_1_7, i + 1);
                    TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState);
                    if (box.contains(mutable)) {
                        ((VoxelSet)_snowman2).set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
                    }
                    _snowman4.add(mutable.toImmutable());
                }
            }
        }
        return _snowman2;
    }

    private static /* synthetic */ void method_23381(StructureWorldAccess structureWorldAccess, Random random, List list, List list2, Set set, BlockBox blockBox, TreeDecorator decorator) {
        decorator.generate(structureWorldAccess, random, list, list2, set, blockBox);
    }
}

