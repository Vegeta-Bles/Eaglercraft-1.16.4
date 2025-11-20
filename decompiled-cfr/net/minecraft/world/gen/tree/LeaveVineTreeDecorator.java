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
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;

public class LeaveVineTreeDecorator
extends TreeDecorator {
    public static final Codec<LeaveVineTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);
    public static final LeaveVineTreeDecorator INSTANCE = new LeaveVineTreeDecorator();

    @Override
    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorType.LEAVE_VINE;
    }

    @Override
    public void generate(StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box) {
        leavesPositions.forEach(pos -> {
            if (random.nextInt(4) == 0 && Feature.isAir(world, _snowman = pos.west())) {
                this.placeVines(world, _snowman, VineBlock.EAST, placedStates, box);
            }
            if (random.nextInt(4) == 0 && Feature.isAir(world, _snowman = pos.east())) {
                this.placeVines(world, _snowman, VineBlock.WEST, placedStates, box);
            }
            if (random.nextInt(4) == 0 && Feature.isAir(world, _snowman = pos.north())) {
                this.placeVines(world, _snowman, VineBlock.SOUTH, placedStates, box);
            }
            if (random.nextInt(4) == 0 && Feature.isAir(world, _snowman = pos.south())) {
                this.placeVines(world, _snowman, VineBlock.NORTH, placedStates, box);
            }
        });
    }

    private void placeVines(ModifiableTestableWorld world, BlockPos pos, BooleanProperty side, Set<BlockPos> placedStates, BlockBox box) {
        this.placeVine(world, pos, side, placedStates, box);
        pos = pos.down();
        for (int i = 4; Feature.isAir(world, pos) && i > 0; --i) {
            this.placeVine(world, pos, side, placedStates, box);
            pos = pos.down();
        }
    }
}

