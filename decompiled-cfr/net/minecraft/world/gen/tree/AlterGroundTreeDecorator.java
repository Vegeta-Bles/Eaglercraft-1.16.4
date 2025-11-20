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
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.tree.TreeDecoratorType;

public class AlterGroundTreeDecorator
extends TreeDecorator {
    public static final Codec<AlterGroundTreeDecorator> CODEC = BlockStateProvider.TYPE_CODEC.fieldOf("provider").xmap(AlterGroundTreeDecorator::new, alterGroundTreeDecorator -> alterGroundTreeDecorator.provider).codec();
    private final BlockStateProvider provider;

    public AlterGroundTreeDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorType.ALTER_GROUND;
    }

    @Override
    public void generate(StructureWorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> placedStates, BlockBox box) {
        int n = logPositions.get(0).getY();
        logPositions.stream().filter(blockPos -> blockPos.getY() == n).forEach(blockPos -> {
            this.method_23462(world, random, blockPos.west().north());
            this.method_23462(world, random, blockPos.east(2).north());
            this.method_23462(world, random, blockPos.west().south(2));
            this.method_23462(world, random, blockPos.east(2).south(2));
            for (int i = 0; i < 5; ++i) {
                _snowman = random.nextInt(64);
                _snowman = _snowman % 8;
                _snowman = _snowman / 8;
                if (_snowman != 0 && _snowman != 7 && _snowman != 0 && _snowman != 7) continue;
                this.method_23462(world, random, blockPos.add(-3 + _snowman, 0, -3 + _snowman));
            }
        });
    }

    private void method_23462(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos) {
        for (int i = -2; i <= 2; ++i) {
            for (_snowman = -2; _snowman <= 2; ++_snowman) {
                if (Math.abs(i) == 2 && Math.abs(_snowman) == 2) continue;
                this.method_23463(modifiableTestableWorld, random, blockPos.add(i, 0, _snowman));
            }
        }
    }

    private void method_23463(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockPos2 = blockPos.up(i);
            if (Feature.isSoil(modifiableTestableWorld, blockPos2)) {
                modifiableTestableWorld.setBlockState(blockPos2, this.provider.getBlockState(random, blockPos), 19);
                break;
            }
            if (!Feature.isAir(modifiableTestableWorld, blockPos2) && i < 0) break;
        }
    }
}

