/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class BoneMealTask
extends Task<VillagerEntity> {
    private long startTime;
    private long lastEndEntityAge;
    private int duration;
    private Optional<BlockPos> pos = Optional.empty();

    public BoneMealTask() {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, (Object)((Object)MemoryModuleState.VALUE_ABSENT), MemoryModuleType.WALK_TARGET, (Object)((Object)MemoryModuleState.VALUE_ABSENT)));
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        if (villagerEntity.age % 10 != 0 || this.lastEndEntityAge != 0L && this.lastEndEntityAge + 160L > (long)villagerEntity.age) {
            return false;
        }
        if (villagerEntity.getInventory().count(Items.BONE_MEAL) <= 0) {
            return false;
        }
        this.pos = this.findBoneMealPos(serverWorld, villagerEntity);
        return this.pos.isPresent();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.duration < 80 && this.pos.isPresent();
    }

    private Optional<BlockPos> findBoneMealPos(ServerWorld world, VillagerEntity entity) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Optional<BlockPos> _snowman2 = Optional.empty();
        int _snowman3 = 0;
        for (int i = -1; i <= 1; ++i) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    mutable.set(entity.getBlockPos(), i, _snowman, _snowman);
                    if (!this.canBoneMeal(mutable, world) || world.random.nextInt(++_snowman3) != 0) continue;
                    _snowman2 = Optional.of(mutable.toImmutable());
                }
            }
        }
        return _snowman2;
    }

    private boolean canBoneMeal(BlockPos pos, ServerWorld world) {
        BlockState blockState = world.getBlockState(pos);
        Block _snowman2 = blockState.getBlock();
        return _snowman2 instanceof CropBlock && !((CropBlock)_snowman2).isMature(blockState);
    }

    @Override
    protected void run(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.addLookWalkTargets(villagerEntity);
        villagerEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BONE_MEAL));
        this.startTime = l;
        this.duration = 0;
    }

    private void addLookWalkTargets(VillagerEntity villager) {
        this.pos.ifPresent(blockPos -> {
            BlockPosLookTarget blockPosLookTarget = new BlockPosLookTarget((BlockPos)blockPos);
            villager.getBrain().remember(MemoryModuleType.LOOK_TARGET, blockPosLookTarget);
            villager.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPosLookTarget, 0.5f, 1));
        });
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        villagerEntity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.lastEndEntityAge = villagerEntity.age;
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld2, VillagerEntity villagerEntity, long l) {
        ServerWorld serverWorld2;
        BlockPos blockPos = this.pos.get();
        if (l < this.startTime || !blockPos.isWithinDistance(villagerEntity.getPos(), 1.0)) {
            return;
        }
        ItemStack _snowman2 = ItemStack.EMPTY;
        SimpleInventory _snowman3 = villagerEntity.getInventory();
        int _snowman4 = _snowman3.size();
        for (int i = 0; i < _snowman4; ++i) {
            ItemStack itemStack = _snowman3.getStack(i);
            if (itemStack.getItem() != Items.BONE_MEAL) continue;
            _snowman2 = itemStack;
            break;
        }
        if (!_snowman2.isEmpty() && BoneMealItem.useOnFertilizable(_snowman2, serverWorld2, blockPos)) {
            serverWorld2.syncWorldEvent(2005, blockPos, 0);
            this.pos = this.findBoneMealPos(serverWorld2, villagerEntity);
            this.addLookWalkTargets(villagerEntity);
            this.startTime = l + 40L;
        }
        ++this.duration;
    }

    @Override
    protected /* synthetic */ boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        return this.shouldKeepRunning(world, (VillagerEntity)entity, time);
    }

    @Override
    protected /* synthetic */ void run(ServerWorld world, LivingEntity entity, long time) {
        this.run(world, (VillagerEntity)entity, time);
    }
}

