/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class RingBellTask
extends Task<LivingEntity> {
    public RingBellTask() {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.MEETING_POINT, (Object)((Object)MemoryModuleState.VALUE_PRESENT)));
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        return world.random.nextFloat() > 0.95f;
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        Brain<?> brain = entity.getBrain();
        BlockPos _snowman2 = brain.getOptionalMemory(MemoryModuleType.MEETING_POINT).get().getPos();
        if (_snowman2.isWithinDistance(entity.getBlockPos(), 3.0) && (_snowman = world.getBlockState(_snowman2)).isOf(Blocks.BELL)) {
            BellBlock bellBlock = (BellBlock)_snowman.getBlock();
            bellBlock.ring(world, _snowman2, null);
        }
    }
}

