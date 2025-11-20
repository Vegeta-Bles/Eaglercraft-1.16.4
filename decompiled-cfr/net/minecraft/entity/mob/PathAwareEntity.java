/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class PathAwareEntity
extends MobEntity {
    protected PathAwareEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super((EntityType<? extends MobEntity>)entityType, world);
    }

    public float getPathfindingFavor(BlockPos pos) {
        return this.getPathfindingFavor(pos, this.world);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0f;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return this.getPathfindingFavor(this.getBlockPos(), world) >= 0.0f;
    }

    public boolean isNavigating() {
        return !this.getNavigation().isIdle();
    }

    @Override
    protected void updateLeash() {
        super.updateLeash();
        Entity entity = this.getHoldingEntity();
        if (entity != null && entity.world == this.world) {
            this.setPositionTarget(entity.getBlockPos(), 5);
            float f = this.distanceTo(entity);
            if (this instanceof TameableEntity && ((TameableEntity)this).isInSittingPose()) {
                if (f > 10.0f) {
                    this.detachLeash(true, true);
                }
                return;
            }
            this.updateForLeashLength(f);
            if (f > 10.0f) {
                this.detachLeash(true, true);
                this.goalSelector.disableControl(Goal.Control.MOVE);
            } else if (f > 6.0f) {
                double d = (entity.getX() - this.getX()) / (double)f;
                _snowman = (entity.getY() - this.getY()) / (double)f;
                _snowman = (entity.getZ() - this.getZ()) / (double)f;
                this.setVelocity(this.getVelocity().add(Math.copySign(d * d * 0.4, d), Math.copySign(_snowman * _snowman * 0.4, _snowman), Math.copySign(_snowman * _snowman * 0.4, _snowman)));
            } else {
                this.goalSelector.enableControl(Goal.Control.MOVE);
                float f2 = 2.0f;
                Vec3d _snowman2 = new Vec3d(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ()).normalize().multiply(Math.max(f - 2.0f, 0.0f));
                this.getNavigation().startMovingTo(this.getX() + _snowman2.x, this.getY() + _snowman2.y, this.getZ() + _snowman2.z, this.getRunFromLeashSpeed());
            }
        }
    }

    protected double getRunFromLeashSpeed() {
        return 1.0;
    }

    protected void updateForLeashLength(float leashLength) {
    }
}

