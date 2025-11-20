/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class LargeOakTrunkPlacer
extends TrunkPlacer {
    public static final Codec<LargeOakTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> LargeOakTrunkPlacer.method_28904(instance).apply((Applicative)instance, LargeOakTrunkPlacer::new));

    public LargeOakTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return TrunkPlacerType.FANCY_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
        int n = 5;
        _snowman = trunkHeight + 2;
        _snowman = MathHelper.floor((double)_snowman * 0.618);
        if (!config.skipFluidCheck) {
            LargeOakTrunkPlacer.setToDirt(world, pos.down());
        }
        double _snowman2 = 1.0;
        _snowman = Math.min(1, MathHelper.floor(1.382 + Math.pow(1.0 * (double)_snowman / 13.0, 2.0)));
        _snowman = pos.getY() + _snowman;
        ArrayList _snowman3 = Lists.newArrayList();
        _snowman3.add(new BranchPosition(pos.up(_snowman), _snowman));
        for (_snowman = _snowman - 5; _snowman >= 0; --_snowman) {
            float f = this.shouldGenerateBranch(_snowman, _snowman);
            if (f < 0.0f) continue;
            for (int i = 0; i < _snowman; ++i) {
                double d = 1.0;
                _snowman = 1.0 * (double)f * ((double)random.nextFloat() + 0.328);
                _snowman = _snowman * Math.sin(_snowman = (double)(random.nextFloat() * 2.0f) * Math.PI) + 0.5;
                BlockPos _snowman4 = pos.add(_snowman, (double)(_snowman - 1), _snowman = _snowman * Math.cos(_snowman) + 0.5);
                if (!this.makeOrCheckBranch(world, random, _snowman4, _snowman = _snowman4.up(5), false, placedStates, box, config)) continue;
                int _snowman5 = pos.getX() - _snowman4.getX();
                int _snowman6 = pos.getZ() - _snowman4.getZ();
                _snowman = (double)_snowman4.getY() - Math.sqrt(_snowman5 * _snowman5 + _snowman6 * _snowman6) * 0.381;
                int _snowman7 = _snowman > (double)_snowman ? _snowman : (int)_snowman;
                BlockPos _snowman8 = new BlockPos(pos.getX(), _snowman7, pos.getZ());
                if (!this.makeOrCheckBranch(world, random, _snowman8, _snowman4, false, placedStates, box, config)) continue;
                _snowman3.add(new BranchPosition(_snowman4, _snowman8.getY()));
            }
        }
        this.makeOrCheckBranch(world, random, pos, pos.up(_snowman), true, placedStates, box, config);
        this.makeBranches(world, random, _snowman, pos, _snowman3, placedStates, box, config);
        ArrayList arrayList = Lists.newArrayList();
        for (BranchPosition branchPosition : _snowman3) {
            if (!this.isHighEnough(_snowman, branchPosition.getEndY() - pos.getY())) continue;
            arrayList.add(branchPosition.node);
        }
        return arrayList;
    }

    private boolean makeOrCheckBranch(ModifiableTestableWorld world, Random random, BlockPos start, BlockPos end, boolean make, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
        if (!make && Objects.equals(start, end)) {
            return true;
        }
        BlockPos blockPos = end.add(-start.getX(), -start.getY(), -start.getZ());
        int _snowman2 = this.getLongestSide(blockPos);
        float _snowman3 = (float)blockPos.getX() / (float)_snowman2;
        float _snowman4 = (float)blockPos.getY() / (float)_snowman2;
        float _snowman5 = (float)blockPos.getZ() / (float)_snowman2;
        for (int i = 0; i <= _snowman2; ++i) {
            BlockPos blockPos2 = start.add(0.5f + (float)i * _snowman3, 0.5f + (float)i * _snowman4, 0.5f + (float)i * _snowman5);
            if (make) {
                LargeOakTrunkPlacer.setBlockState(world, blockPos2, (BlockState)config.trunkProvider.getBlockState(random, blockPos2).with(PillarBlock.AXIS, this.getLogAxis(start, blockPos2)), box);
                placedStates.add(blockPos2.toImmutable());
                continue;
            }
            if (TreeFeature.canTreeReplace(world, blockPos2)) continue;
            return false;
        }
        return true;
    }

    private int getLongestSide(BlockPos offset) {
        int n = MathHelper.abs(offset.getX());
        _snowman = MathHelper.abs(offset.getY());
        _snowman = MathHelper.abs(offset.getZ());
        return Math.max(n, Math.max(_snowman, _snowman));
    }

    private Direction.Axis getLogAxis(BlockPos branchStart, BlockPos branchEnd) {
        Direction.Axis axis = Direction.Axis.Y;
        int _snowman2 = Math.abs(branchEnd.getX() - branchStart.getX());
        int _snowman3 = Math.max(_snowman2, _snowman = Math.abs(branchEnd.getZ() - branchStart.getZ()));
        if (_snowman3 > 0) {
            axis = _snowman2 == _snowman3 ? Direction.Axis.X : Direction.Axis.Z;
        }
        return axis;
    }

    private boolean isHighEnough(int treeHeight, int height) {
        return (double)height >= (double)treeHeight * 0.2;
    }

    private void makeBranches(ModifiableTestableWorld world, Random random, int treeHeight, BlockPos treePos, List<BranchPosition> branches, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
        for (BranchPosition branchPosition : branches) {
            int n = branchPosition.getEndY();
            BlockPos _snowman2 = new BlockPos(treePos.getX(), n, treePos.getZ());
            if (_snowman2.equals(branchPosition.node.getCenter()) || !this.isHighEnough(treeHeight, n - treePos.getY())) continue;
            this.makeOrCheckBranch(world, random, _snowman2, branchPosition.node.getCenter(), true, placedStates, box, config);
        }
    }

    private float shouldGenerateBranch(int trunkHeight, int y) {
        if ((float)y < (float)trunkHeight * 0.3f) {
            return -1.0f;
        }
        float f = (float)trunkHeight / 2.0f;
        _snowman = f - (float)y;
        _snowman = MathHelper.sqrt(f * f - _snowman * _snowman);
        if (_snowman == 0.0f) {
            _snowman = f;
        } else if (Math.abs(_snowman) >= f) {
            return 0.0f;
        }
        return _snowman * 0.5f;
    }

    static class BranchPosition {
        private final FoliagePlacer.TreeNode node;
        private final int endY;

        public BranchPosition(BlockPos pos, int width) {
            this.node = new FoliagePlacer.TreeNode(pos, 0, false);
            this.endY = width;
        }

        public int getEndY() {
            return this.endY;
        }
    }
}

