/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.gen.foliage;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class AcaciaFoliagePlacer
extends FoliagePlacer {
    public static final Codec<AcaciaFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> AcaciaFoliagePlacer.fillFoliagePlacerFields(instance).apply((Applicative)instance, AcaciaFoliagePlacer::new));

    public AcaciaFoliagePlacer(UniformIntDistribution uniformIntDistribution, UniformIntDistribution uniformIntDistribution2) {
        super(uniformIntDistribution, uniformIntDistribution2);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return FoliagePlacerType.ACACIA_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, BlockBox box) {
        boolean bl = treeNode.isGiantTrunk();
        BlockPos _snowman2 = treeNode.getCenter().up(offset);
        this.generateSquare(world, random, config, _snowman2, radius + treeNode.getFoliageRadius(), leaves, -1 - foliageHeight, bl, box);
        this.generateSquare(world, random, config, _snowman2, radius - 1, leaves, -foliageHeight, bl, box);
        this.generateSquare(world, random, config, _snowman2, radius + treeNode.getFoliageRadius() - 1, leaves, 0, bl, box);
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
        if (dx == 0) {
            return (baseHeight > 1 || y > 1) && baseHeight != 0 && y != 0;
        }
        return baseHeight == dz && y == dz && dz > 0;
    }
}

