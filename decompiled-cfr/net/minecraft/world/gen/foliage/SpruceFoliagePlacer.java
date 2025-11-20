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
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class SpruceFoliagePlacer
extends FoliagePlacer {
    public static final Codec<SpruceFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> SpruceFoliagePlacer.fillFoliagePlacerFields(instance).and((App)UniformIntDistribution.createValidatedCodec(0, 16, 8).fieldOf("trunk_height").forGetter(spruceFoliagePlacer -> spruceFoliagePlacer.trunkHeight)).apply((Applicative)instance, SpruceFoliagePlacer::new));
    private final UniformIntDistribution trunkHeight;

    public SpruceFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset, UniformIntDistribution trunkHeight) {
        super(radius, offset);
        this.trunkHeight = trunkHeight;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return FoliagePlacerType.SPRUCE_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, BlockBox box) {
        BlockPos blockPos = treeNode.getCenter();
        int _snowman2 = random.nextInt(2);
        int _snowman3 = 1;
        int _snowman4 = 0;
        for (int i = offset; i >= -foliageHeight; --i) {
            this.generateSquare(world, random, config, blockPos, _snowman2, leaves, i, treeNode.isGiantTrunk(), box);
            if (_snowman2 >= _snowman3) {
                _snowman2 = _snowman4;
                _snowman4 = 1;
                _snowman3 = Math.min(_snowman3 + 1, radius + treeNode.getFoliageRadius());
                continue;
            }
            ++_snowman2;
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return Math.max(4, trunkHeight - this.trunkHeight.getValue(random));
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
        return baseHeight == dz && y == dz && dz > 0;
    }
}

