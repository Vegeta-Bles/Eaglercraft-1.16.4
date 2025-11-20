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
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.OpenDoorsTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

public class SleepTask
extends Task<LivingEntity> {
    private long startTime;

    public SleepTask() {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.HOME, (Object)((Object)MemoryModuleState.VALUE_PRESENT), MemoryModuleType.LAST_WOKEN, (Object)((Object)MemoryModuleState.REGISTERED)));
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        if (entity.hasVehicle()) {
            return false;
        }
        Brain<?> brain = entity.getBrain();
        GlobalPos _snowman2 = brain.getOptionalMemory(MemoryModuleType.HOME).get();
        if (world.getRegistryKey() != _snowman2.getDimension()) {
            return false;
        }
        Optional<Long> _snowman3 = brain.getOptionalMemory(MemoryModuleType.LAST_WOKEN);
        if (_snowman3.isPresent() && (_snowman = world.getTime() - _snowman3.get()) > 0L && _snowman < 100L) {
            return false;
        }
        BlockState _snowman4 = world.getBlockState(_snowman2.getPos());
        return _snowman2.getPos().isWithinDistance(entity.getPos(), 2.0) && _snowman4.getBlock().isIn(BlockTags.BEDS) && _snowman4.get(BedBlock.OCCUPIED) == false;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        if (!optional.isPresent()) {
            return false;
        }
        BlockPos _snowman2 = optional.get().getPos();
        return entity.getBrain().hasActivity(Activity.REST) && entity.getY() > (double)_snowman2.getY() + 0.4 && _snowman2.isWithinDistance(entity.getPos(), 1.14);
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        if (time > this.startTime) {
            OpenDoorsTask.method_30760(world, entity, null, null);
            entity.sleep(entity.getBrain().getOptionalMemory(MemoryModuleType.HOME).get().getPos());
        }
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }

    @Override
    protected void finishRunning(ServerWorld world, LivingEntity entity, long time) {
        if (entity.isSleeping()) {
            entity.wakeUp();
            this.startTime = time + 40L;
        }
    }
}

