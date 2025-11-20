/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage;

public class GoToPointOfInterestTask
extends Task<VillagerEntity> {
    private final float speed;
    private final int completionRange;

    public GoToPointOfInterestTask(float speed, int completionRange) {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.WALK_TARGET, (Object)((Object)MemoryModuleState.VALUE_ABSENT)));
        this.speed = speed;
        this.completionRange = completionRange;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        return !serverWorld.isNearOccupiedPointOfInterest(villagerEntity.getBlockPos());
    }

    @Override
    protected void run(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        PointOfInterestStorage pointOfInterestStorage = serverWorld.getPointOfInterestStorage();
        int _snowman2 = pointOfInterestStorage.getDistanceFromNearestOccupied(ChunkSectionPos.from(villagerEntity.getBlockPos()));
        Vec3d _snowman3 = null;
        for (int i = 0; i < 5; ++i) {
            Vec3d vec3d = TargetFinder.findGroundTarget(villagerEntity, 15, 7, blockPos -> -serverWorld.getOccupiedPointOfInterestDistance(ChunkSectionPos.from(blockPos)));
            if (vec3d == null) continue;
            int _snowman4 = pointOfInterestStorage.getDistanceFromNearestOccupied(ChunkSectionPos.from(new BlockPos(vec3d)));
            if (_snowman4 < _snowman2) {
                _snowman3 = vec3d;
                break;
            }
            if (_snowman4 != _snowman2) continue;
            _snowman3 = vec3d;
        }
        if (_snowman3 != null) {
            villagerEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowman3, this.speed, this.completionRange));
        }
    }
}

