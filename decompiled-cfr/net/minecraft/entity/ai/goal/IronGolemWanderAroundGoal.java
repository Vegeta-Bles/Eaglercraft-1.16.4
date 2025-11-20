/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.goal;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;

public class IronGolemWanderAroundGoal
extends WanderAroundGoal {
    public IronGolemWanderAroundGoal(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d, 240, false);
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d vec3d;
        float f = this.mob.world.random.nextFloat();
        if (this.mob.world.random.nextFloat() < 0.3f) {
            return this.method_27925();
        }
        if (f < 0.7f) {
            vec3d = this.method_27926();
            if (vec3d == null) {
                vec3d = this.method_27927();
            }
        } else {
            vec3d = this.method_27927();
            if (vec3d == null) {
                vec3d = this.method_27926();
            }
        }
        return vec3d == null ? this.method_27925() : vec3d;
    }

    @Nullable
    private Vec3d method_27925() {
        return TargetFinder.findGroundTarget(this.mob, 10, 7);
    }

    @Nullable
    private Vec3d method_27926() {
        ServerWorld serverWorld = (ServerWorld)this.mob.world;
        List<VillagerEntity> _snowman2 = serverWorld.getEntitiesByType(EntityType.VILLAGER, this.mob.getBoundingBox().expand(32.0), this::method_27922);
        if (_snowman2.isEmpty()) {
            return null;
        }
        VillagerEntity _snowman3 = _snowman2.get(this.mob.world.random.nextInt(_snowman2.size()));
        Vec3d _snowman4 = _snowman3.getPos();
        return TargetFinder.method_27929(this.mob, 10, 7, _snowman4);
    }

    @Nullable
    private Vec3d method_27927() {
        ChunkSectionPos chunkSectionPos = this.method_27928();
        if (chunkSectionPos == null) {
            return null;
        }
        BlockPos _snowman2 = this.method_27923(chunkSectionPos);
        if (_snowman2 == null) {
            return null;
        }
        return TargetFinder.method_27929(this.mob, 10, 7, Vec3d.ofBottomCenter(_snowman2));
    }

    @Nullable
    private ChunkSectionPos method_27928() {
        ServerWorld serverWorld = (ServerWorld)this.mob.world;
        List _snowman2 = ChunkSectionPos.stream(ChunkSectionPos.from(this.mob), 2).filter(chunkSectionPos -> serverWorld.getOccupiedPointOfInterestDistance((ChunkSectionPos)chunkSectionPos) == 0).collect(Collectors.toList());
        if (_snowman2.isEmpty()) {
            return null;
        }
        return (ChunkSectionPos)_snowman2.get(serverWorld.random.nextInt(_snowman2.size()));
    }

    @Nullable
    private BlockPos method_27923(ChunkSectionPos chunkSectionPos) {
        ServerWorld serverWorld = (ServerWorld)this.mob.world;
        PointOfInterestStorage _snowman2 = serverWorld.getPointOfInterestStorage();
        List _snowman3 = _snowman2.getInCircle(pointOfInterestType -> true, chunkSectionPos.getCenterPos(), 8, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED).map(PointOfInterest::getPos).collect(Collectors.toList());
        if (_snowman3.isEmpty()) {
            return null;
        }
        return (BlockPos)_snowman3.get(serverWorld.random.nextInt(_snowman3.size()));
    }

    private boolean method_27922(VillagerEntity villagerEntity) {
        return villagerEntity.canSummonGolem(this.mob.world.getTime());
    }
}

