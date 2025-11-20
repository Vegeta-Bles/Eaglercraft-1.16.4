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
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ForkingTrunkPlacer
extends TrunkPlacer {
    public static final Codec<ForkingTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> ForkingTrunkPlacer.method_28904(instance).apply((Applicative)instance, ForkingTrunkPlacer::new));

    public ForkingTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return TrunkPlacerType.FORKING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
        ForkingTrunkPlacer.setToDirt(world, pos.down());
        ArrayList arrayList = Lists.newArrayList();
        Direction _snowman2 = Direction.Type.HORIZONTAL.random(random);
        int _snowman3 = trunkHeight - random.nextInt(4) - 1;
        int _snowman4 = 3 - random.nextInt(3);
        BlockPos.Mutable _snowman5 = new BlockPos.Mutable();
        int _snowman6 = pos.getX();
        int _snowman7 = pos.getZ();
        int _snowman8 = 0;
        for (int i = 0; i < trunkHeight; ++i) {
            n = pos.getY() + i;
            if (i >= _snowman3 && _snowman4 > 0) {
                _snowman6 += _snowman2.getOffsetX();
                _snowman7 += _snowman2.getOffsetZ();
                --_snowman4;
            }
            if (!ForkingTrunkPlacer.getAndSetState(world, random, _snowman5.set(_snowman6, n, _snowman7), placedStates, box, config)) continue;
            _snowman8 = n + 1;
        }
        arrayList.add(new FoliagePlacer.TreeNode(new BlockPos(_snowman6, _snowman8, _snowman7), 1, false));
        _snowman6 = pos.getX();
        _snowman7 = pos.getZ();
        Direction _snowman9 = Direction.Type.HORIZONTAL.random(random);
        if (_snowman9 != _snowman2) {
            int n = _snowman3 - random.nextInt(2) - 1;
            _snowman = 1 + random.nextInt(3);
            _snowman8 = 0;
            for (_snowman = n; _snowman < trunkHeight && _snowman > 0; ++_snowman, --_snowman) {
                if (_snowman < 1) continue;
                _snowman = pos.getY() + _snowman;
                if (!ForkingTrunkPlacer.getAndSetState(world, random, _snowman5.set(_snowman6 += _snowman9.getOffsetX(), _snowman, _snowman7 += _snowman9.getOffsetZ()), placedStates, box, config)) continue;
                _snowman8 = _snowman + 1;
            }
            if (_snowman8 > 1) {
                arrayList.add(new FoliagePlacer.TreeNode(new BlockPos(_snowman6, _snowman8, _snowman7), 0, false));
            }
        }
        return arrayList;
    }
}

