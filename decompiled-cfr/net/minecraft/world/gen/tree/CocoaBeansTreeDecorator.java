/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.tree;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;

public class CocoaBeansTreeDecorator
extends TreeDecorator {
    public static final Codec<CocoaBeansTreeDecorator> CODEC = Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").xmap(CocoaBeansTreeDecorator::new, cocoaBeansTreeDecorator -> Float.valueOf(cocoaBeansTreeDecorator.probability)).codec();
    private final float probability;

    public CocoaBeansTreeDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorType.COCOA;
    }

    @Override
    public void generate(StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box) {
        if (random.nextFloat() >= this.probability) {
            return;
        }
        int n = logPositions.get(0).getY();
        logPositions.stream().filter(pos -> pos.getY() - n <= 2).forEach(pos -> {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                if (!(random.nextFloat() <= 0.25f) || !Feature.isAir(world, _snowman = pos.add((_snowman = direction.getOpposite()).getOffsetX(), 0, _snowman.getOffsetZ()))) continue;
                BlockState blockState = (BlockState)((BlockState)Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, random.nextInt(3))).with(CocoaBlock.FACING, direction);
                this.setBlockStateAndEncompassPosition(world, _snowman, blockState, placedStates, box);
            }
        });
    }
}

