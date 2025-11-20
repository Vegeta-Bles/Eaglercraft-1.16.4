/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestType;

public class ForgetCompletedPointOfInterestTask
extends Task<LivingEntity> {
    private final MemoryModuleType<GlobalPos> memoryModule;
    private final Predicate<PointOfInterestType> condition;

    public ForgetCompletedPointOfInterestTask(PointOfInterestType poiType, MemoryModuleType<GlobalPos> memoryModule) {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(memoryModule, (Object)((Object)MemoryModuleState.VALUE_PRESENT)));
        this.condition = poiType.getCompletionCondition();
        this.memoryModule = memoryModule;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        GlobalPos globalPos = entity.getBrain().getOptionalMemory(this.memoryModule).get();
        return world.getRegistryKey() == globalPos.getDimension() && globalPos.getPos().isWithinDistance(entity.getPos(), 16.0);
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        Brain<?> brain = entity.getBrain();
        GlobalPos _snowman2 = brain.getOptionalMemory(this.memoryModule).get();
        BlockPos _snowman3 = _snowman2.getPos();
        ServerWorld _snowman4 = world.getServer().getWorld(_snowman2.getDimension());
        if (_snowman4 == null || this.hasCompletedPointOfInterest(_snowman4, _snowman3)) {
            brain.forget(this.memoryModule);
        } else if (this.isBedOccupiedByOthers(_snowman4, _snowman3, entity)) {
            brain.forget(this.memoryModule);
            world.getPointOfInterestStorage().releaseTicket(_snowman3);
            DebugInfoSender.sendPointOfInterest(world, _snowman3);
        }
    }

    private boolean isBedOccupiedByOthers(ServerWorld world, BlockPos pos, LivingEntity entity) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.getBlock().isIn(BlockTags.BEDS) && blockState.get(BedBlock.OCCUPIED) != false && !entity.isSleeping();
    }

    private boolean hasCompletedPointOfInterest(ServerWorld world, BlockPos pos) {
        return !world.getPointOfInterestStorage().test(pos, this.condition);
    }
}

