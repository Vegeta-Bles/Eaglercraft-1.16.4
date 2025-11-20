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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class MegaJungleTrunkPlacer
extends GiantTrunkPlacer {
    public static final Codec<MegaJungleTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> MegaJungleTrunkPlacer.method_28904(instance).apply((Applicative)instance, MegaJungleTrunkPlacer::new));

    public MegaJungleTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return TrunkPlacerType.MEGA_JUNGLE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
        ArrayList arrayList = Lists.newArrayList();
        arrayList.addAll(super.generate(world, random, trunkHeight, pos, placedStates, box, config));
        for (int i = trunkHeight - 2 - random.nextInt(4); i > trunkHeight / 2; i -= 2 + random.nextInt(4)) {
            float f = random.nextFloat() * ((float)Math.PI * 2);
            int _snowman2 = 0;
            int _snowman3 = 0;
            for (int j = 0; j < 5; ++j) {
                _snowman2 = (int)(1.5f + MathHelper.cos(f) * (float)j);
                _snowman3 = (int)(1.5f + MathHelper.sin(f) * (float)j);
                BlockPos blockPos = pos.add(_snowman2, i - 3 + j / 2, _snowman3);
                MegaJungleTrunkPlacer.getAndSetState(world, random, blockPos, placedStates, box, config);
            }
            arrayList.add(new FoliagePlacer.TreeNode(pos.add(_snowman2, i, _snowman3), -2, false));
        }
        return arrayList;
    }
}

