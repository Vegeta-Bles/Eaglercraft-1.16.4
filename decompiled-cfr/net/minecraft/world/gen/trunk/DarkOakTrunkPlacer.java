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
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class DarkOakTrunkPlacer
extends TrunkPlacer {
    public static final Codec<DarkOakTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> DarkOakTrunkPlacer.method_28904(instance).apply((Applicative)instance, DarkOakTrunkPlacer::new));

    public DarkOakTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return TrunkPlacerType.DARK_OAK_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
        int n;
        ArrayList arrayList = Lists.newArrayList();
        BlockPos _snowman2 = pos.down();
        DarkOakTrunkPlacer.setToDirt(world, _snowman2);
        DarkOakTrunkPlacer.setToDirt(world, _snowman2.east());
        DarkOakTrunkPlacer.setToDirt(world, _snowman2.south());
        DarkOakTrunkPlacer.setToDirt(world, _snowman2.south().east());
        Direction _snowman3 = Direction.Type.HORIZONTAL.random(random);
        int _snowman4 = trunkHeight - random.nextInt(4);
        int _snowman5 = 2 - random.nextInt(3);
        int _snowman6 = pos.getX();
        int _snowman7 = pos.getY();
        int _snowman8 = pos.getZ();
        int _snowman9 = _snowman6;
        int _snowman10 = _snowman8;
        int _snowman11 = _snowman7 + trunkHeight - 1;
        for (n = 0; n < trunkHeight; ++n) {
            if (n >= _snowman4 && _snowman5 > 0) {
                _snowman9 += _snowman3.getOffsetX();
                _snowman10 += _snowman3.getOffsetZ();
                --_snowman5;
            }
            if (!TreeFeature.isAirOrLeaves(world, _snowman = new BlockPos(_snowman9, _snowman = _snowman7 + n, _snowman10))) continue;
            DarkOakTrunkPlacer.getAndSetState(world, random, _snowman, placedStates, box, config);
            DarkOakTrunkPlacer.getAndSetState(world, random, _snowman.east(), placedStates, box, config);
            DarkOakTrunkPlacer.getAndSetState(world, random, _snowman.south(), placedStates, box, config);
            DarkOakTrunkPlacer.getAndSetState(world, random, _snowman.east().south(), placedStates, box, config);
        }
        arrayList.add(new FoliagePlacer.TreeNode(new BlockPos(_snowman9, _snowman11, _snowman10), 0, true));
        for (n = -1; n <= 2; ++n) {
            for (_snowman = -1; _snowman <= 2; ++_snowman) {
                if (n >= 0 && n <= 1 && _snowman >= 0 && _snowman <= 1 || random.nextInt(3) > 0) continue;
                _snowman = random.nextInt(3) + 2;
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    DarkOakTrunkPlacer.getAndSetState(world, random, new BlockPos(_snowman6 + n, _snowman11 - _snowman - 1, _snowman8 + _snowman), placedStates, box, config);
                }
                arrayList.add(new FoliagePlacer.TreeNode(new BlockPos(_snowman9 + n, _snowman11, _snowman10 + _snowman), 0, false));
            }
        }
        return arrayList;
    }
}

