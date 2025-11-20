/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  it.unimi.dsi.fastutil.longs.Long2LongMap
 *  it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class WalkHomeTask
extends Task<LivingEntity> {
    private final float speed;
    private final Long2LongMap positionToExpiry = new Long2LongOpenHashMap();
    private int tries;
    private long expiryTimeLimit;

    public WalkHomeTask(float speed) {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.WALK_TARGET, (Object)((Object)MemoryModuleState.VALUE_ABSENT), MemoryModuleType.HOME, (Object)((Object)MemoryModuleState.VALUE_ABSENT)));
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        if (world.getTime() - this.expiryTimeLimit < 20L) {
            return false;
        }
        PathAwareEntity pathAwareEntity = (PathAwareEntity)entity;
        PointOfInterestStorage _snowman2 = world.getPointOfInterestStorage();
        Optional<BlockPos> _snowman3 = _snowman2.getNearestPosition(PointOfInterestType.HOME.getCompletionCondition(), entity.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.ANY);
        return _snowman3.isPresent() && !(_snowman3.get().getSquaredDistance(pathAwareEntity.getBlockPos()) <= 4.0);
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        this.tries = 0;
        this.expiryTimeLimit = world.getTime() + (long)world.getRandom().nextInt(20);
        PathAwareEntity pathAwareEntity = (PathAwareEntity)entity;
        PointOfInterestStorage _snowman2 = world.getPointOfInterestStorage();
        Predicate<BlockPos> _snowman3 = blockPos -> {
            long l = blockPos.asLong();
            if (this.positionToExpiry.containsKey(l)) {
                return false;
            }
            if (++this.tries >= 5) {
                return false;
            }
            this.positionToExpiry.put(l, this.expiryTimeLimit + 40L);
            return true;
        };
        Stream<BlockPos> _snowman4 = _snowman2.getPositions(PointOfInterestType.HOME.getCompletionCondition(), _snowman3, entity.getBlockPos(), 48, PointOfInterestStorage.OccupationStatus.ANY);
        Path _snowman5 = pathAwareEntity.getNavigation().findPathToAny(_snowman4, PointOfInterestType.HOME.getSearchDistance());
        if (_snowman5 != null && _snowman5.reachesTarget()) {
            BlockPos blockPos2 = _snowman5.getTarget();
            Optional<PointOfInterestType> _snowman6 = _snowman2.getType(blockPos2);
            if (_snowman6.isPresent()) {
                entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos2, this.speed, 1));
                DebugInfoSender.sendPointOfInterest(world, blockPos2);
            }
        } else if (this.tries < 5) {
            this.positionToExpiry.long2LongEntrySet().removeIf(entry -> entry.getLongValue() < this.expiryTimeLimit);
        }
    }
}

