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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ForgetBellRingTask
extends Task<LivingEntity> {
    private final int distance;
    private final int maxHiddenTicks;
    private int hiddenTicks;

    public ForgetBellRingTask(int maxHiddenSeconds, int distance) {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.HIDING_PLACE, (Object)((Object)MemoryModuleState.VALUE_PRESENT), MemoryModuleType.HEARD_BELL_TIME, (Object)((Object)MemoryModuleState.VALUE_PRESENT)));
        this.maxHiddenTicks = maxHiddenSeconds * 20;
        this.hiddenTicks = 0;
        this.distance = distance;
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        Brain<?> brain = entity.getBrain();
        Optional<Long> _snowman2 = brain.getOptionalMemory(MemoryModuleType.HEARD_BELL_TIME);
        boolean bl = _snowman = _snowman2.get() + 300L <= time;
        if (this.hiddenTicks > this.maxHiddenTicks || _snowman) {
            brain.forget(MemoryModuleType.HEARD_BELL_TIME);
            brain.forget(MemoryModuleType.HIDING_PLACE);
            brain.refreshActivities(world.getTimeOfDay(), world.getTime());
            this.hiddenTicks = 0;
            return;
        }
        BlockPos _snowman3 = brain.getOptionalMemory(MemoryModuleType.HIDING_PLACE).get().getPos();
        if (_snowman3.isWithinDistance(entity.getBlockPos(), (double)this.distance)) {
            ++this.hiddenTicks;
        }
    }
}

