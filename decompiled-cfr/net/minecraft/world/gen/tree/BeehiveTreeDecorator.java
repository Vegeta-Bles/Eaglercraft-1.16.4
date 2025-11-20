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
import java.util.stream.Collectors;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;

public class BeehiveTreeDecorator
extends TreeDecorator {
    public static final Codec<BeehiveTreeDecorator> CODEC = Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").xmap(BeehiveTreeDecorator::new, decorator -> Float.valueOf(decorator.probability)).codec();
    private final float probability;

    public BeehiveTreeDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorType.BEEHIVE;
    }

    @Override
    public void generate(StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box) {
        if (random.nextFloat() >= this.probability) {
            return;
        }
        Direction direction = BeehiveBlock.getRandomGenerationDirection(random);
        int _snowman2 = !leavesPositions.isEmpty() ? Math.max(leavesPositions.get(0).getY() - 1, logPositions.get(0).getY()) : Math.min(logPositions.get(0).getY() + 1 + random.nextInt(3), logPositions.get(logPositions.size() - 1).getY());
        List _snowman3 = logPositions.stream().filter(pos -> pos.getY() == _snowman2).collect(Collectors.toList());
        if (_snowman3.isEmpty()) {
            return;
        }
        BlockPos _snowman4 = (BlockPos)_snowman3.get(random.nextInt(_snowman3.size()));
        BlockPos _snowman5 = _snowman4.offset(direction);
        if (!Feature.isAir(world, _snowman5) || !Feature.isAir(world, _snowman5.offset(Direction.SOUTH))) {
            return;
        }
        BlockState _snowman6 = (BlockState)Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, Direction.SOUTH);
        this.setBlockStateAndEncompassPosition(world, _snowman5, _snowman6, placedStates, box);
        BlockEntity _snowman7 = world.getBlockEntity(_snowman5);
        if (_snowman7 instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity beehiveBlockEntity = (BeehiveBlockEntity)_snowman7;
            int _snowman8 = 2 + random.nextInt(2);
            for (int i = 0; i < _snowman8; ++i) {
                BeeEntity beeEntity = new BeeEntity((EntityType<? extends BeeEntity>)EntityType.BEE, (World)world.toServerWorld());
                beehiveBlockEntity.tryEnterHive(beeEntity, false, random.nextInt(599));
            }
        }
    }
}

