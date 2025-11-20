/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.VillagerWorkTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

public class FarmerWorkTask
extends VillagerWorkTask {
    private static final List<Item> COMPOSTABLES = ImmutableList.of((Object)Items.WHEAT_SEEDS, (Object)Items.BEETROOT_SEEDS);

    @Override
    protected void performAdditionalWork(ServerWorld world, VillagerEntity entity) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
        if (!optional.isPresent()) {
            return;
        }
        GlobalPos _snowman2 = optional.get();
        BlockState _snowman3 = world.getBlockState(_snowman2.getPos());
        if (_snowman3.isOf(Blocks.COMPOSTER)) {
            this.craftAndDropBread(entity);
            this.compostSeeds(world, entity, _snowman2, _snowman3);
        }
    }

    private void compostSeeds(ServerWorld world, VillagerEntity entity, GlobalPos pos, BlockState composterState) {
        BlockPos blockPos = pos.getPos();
        if (composterState.get(ComposterBlock.LEVEL) == 8) {
            composterState = ComposterBlock.emptyFullComposter(composterState, world, blockPos);
        }
        int _snowman2 = 20;
        int _snowman3 = 10;
        int[] _snowman4 = new int[COMPOSTABLES.size()];
        SimpleInventory _snowman5 = entity.getInventory();
        int _snowman6 = _snowman5.size();
        BlockState _snowman7 = composterState;
        for (int i = _snowman6 - 1; i >= 0 && _snowman2 > 0; --i) {
            ItemStack itemStack = _snowman5.getStack(i);
            int _snowman8 = COMPOSTABLES.indexOf(itemStack.getItem());
            if (_snowman8 == -1) continue;
            int _snowman9 = itemStack.getCount();
            _snowman4[_snowman8] = _snowman = _snowman4[_snowman8] + _snowman9;
            int _snowman10 = Math.min(Math.min(_snowman - 10, _snowman2), _snowman9);
            if (_snowman10 <= 0) continue;
            _snowman2 -= _snowman10;
            for (int j = 0; j < _snowman10; ++j) {
                if ((_snowman7 = ComposterBlock.compost(_snowman7, world, itemStack, blockPos)).get(ComposterBlock.LEVEL) != 7) continue;
                this.method_30232(world, composterState, blockPos, _snowman7);
                return;
            }
        }
        this.method_30232(world, composterState, blockPos, _snowman7);
    }

    private void method_30232(ServerWorld serverWorld, BlockState blockState, BlockPos blockPos, BlockState blockState2) {
        serverWorld.syncWorldEvent(1500, blockPos, blockState2 != blockState ? 1 : 0);
    }

    private void craftAndDropBread(VillagerEntity entity) {
        SimpleInventory simpleInventory = entity.getInventory();
        if (simpleInventory.count(Items.BREAD) > 36) {
            return;
        }
        int _snowman2 = simpleInventory.count(Items.WHEAT);
        int _snowman3 = 3;
        int _snowman4 = 3;
        int _snowman5 = Math.min(3, _snowman2 / 3);
        if (_snowman5 == 0) {
            return;
        }
        int _snowman6 = _snowman5 * 3;
        simpleInventory.removeItem(Items.WHEAT, _snowman6);
        ItemStack _snowman7 = simpleInventory.addStack(new ItemStack(Items.BREAD, _snowman5));
        if (!_snowman7.isEmpty()) {
            entity.dropStack(_snowman7, 0.5f);
        }
    }
}

