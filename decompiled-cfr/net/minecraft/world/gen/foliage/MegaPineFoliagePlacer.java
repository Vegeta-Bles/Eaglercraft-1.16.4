/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.gen.foliage;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class MegaPineFoliagePlacer
extends FoliagePlacer {
    public static final Codec<MegaPineFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> MegaPineFoliagePlacer.fillFoliagePlacerFields(instance).and((App)UniformIntDistribution.createValidatedCodec(0, 16, 8).fieldOf("crown_height").forGetter(megaPineFoliagePlacer -> megaPineFoliagePlacer.crownHeight)).apply((Applicative)instance, MegaPineFoliagePlacer::new));
    private final UniformIntDistribution crownHeight;

    public MegaPineFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, UniformIntDistribution crownHeight) {
        super(radius, offset);
        this.crownHeight = crownHeight;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return FoliagePlacerType.MEGA_PINE_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, BlockBox box) {
        BlockPos blockPos = treeNode.getCenter();
        int _snowman2 = 0;
        for (int i = blockPos.getY() - foliageHeight + offset; i <= blockPos.getY() + offset; ++i) {
            _snowman = blockPos.getY() - i;
            _snowman = radius + treeNode.getFoliageRadius() + MathHelper.floor((float)_snowman / (float)foliageHeight * 3.5f);
            _snowman = _snowman > 0 && _snowman == _snowman2 && (i & 1) == 0 ? _snowman + 1 : _snowman;
            this.generateSquare(world, random, config, new BlockPos(blockPos.getX(), i, blockPos.getZ()), _snowman, leaves, 0, treeNode.isGiantTrunk(), box);
            _snowman2 = _snowman;
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.crownHeight.getValue(random);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
        if (baseHeight + y >= 7) {
            return true;
        }
        return baseHeight * baseHeight + y * y > dz * dz;
    }
}

