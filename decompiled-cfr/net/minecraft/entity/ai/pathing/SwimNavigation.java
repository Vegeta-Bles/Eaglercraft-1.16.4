/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.pathing;

import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.WaterPathNodeMaker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class SwimNavigation
extends EntityNavigation {
    private boolean canJumpOutOfWater;

    public SwimNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.canJumpOutOfWater = this.entity instanceof DolphinEntity;
        this.nodeMaker = new WaterPathNodeMaker(this.canJumpOutOfWater);
        return new PathNodeNavigator(this.nodeMaker, range);
    }

    @Override
    protected boolean isAtValidPosition() {
        return this.canJumpOutOfWater || this.isInLiquid();
    }

    @Override
    protected Vec3d getPos() {
        return new Vec3d(this.entity.getX(), this.entity.getBodyY(0.5), this.entity.getZ());
    }

    @Override
    public void tick() {
        Vec3d vec3d;
        ++this.tickCount;
        if (this.shouldRecalculate) {
            this.recalculatePath();
        }
        if (this.isIdle()) {
            return;
        }
        if (this.isAtValidPosition()) {
            this.continueFollowingPath();
        } else if (this.currentPath != null && !this.currentPath.isFinished()) {
            vec3d = this.currentPath.getNodePosition(this.entity);
            if (MathHelper.floor(this.entity.getX()) == MathHelper.floor(vec3d.x) && MathHelper.floor(this.entity.getY()) == MathHelper.floor(vec3d.y) && MathHelper.floor(this.entity.getZ()) == MathHelper.floor(vec3d.z)) {
                this.currentPath.next();
            }
        }
        DebugInfoSender.sendPathfindingData(this.world, this.entity, this.currentPath, this.nodeReachProximity);
        if (this.isIdle()) {
            return;
        }
        vec3d = this.currentPath.getNodePosition(this.entity);
        this.entity.getMoveControl().moveTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
    }

    @Override
    protected void continueFollowingPath() {
        if (this.currentPath == null) {
            return;
        }
        Vec3d vec3d = this.getPos();
        float _snowman2 = this.entity.getWidth();
        float _snowman3 = _snowman2 > 0.75f ? _snowman2 / 2.0f : 0.75f - _snowman2 / 2.0f;
        _snowman = this.entity.getVelocity();
        if (Math.abs(_snowman.x) > 0.2 || Math.abs(_snowman.z) > 0.2) {
            _snowman3 = (float)((double)_snowman3 * (_snowman.length() * 6.0));
        }
        int _snowman4 = 6;
        vec3d2 = Vec3d.ofBottomCenter(this.currentPath.method_31032());
        if (Math.abs(this.entity.getX() - vec3d2.x) < (double)_snowman3 && Math.abs(this.entity.getZ() - vec3d2.z) < (double)_snowman3 && Math.abs(this.entity.getY() - vec3d2.y) < (double)(_snowman3 * 2.0f)) {
            this.currentPath.next();
        }
        for (int i = Math.min(this.currentPath.getCurrentNodeIndex() + 6, this.currentPath.getLength() - 1); i > this.currentPath.getCurrentNodeIndex(); --i) {
            Vec3d vec3d2 = this.currentPath.getNodePosition(this.entity, i);
            if (vec3d2.squaredDistanceTo(vec3d) > 36.0 || !this.canPathDirectlyThrough(vec3d, vec3d2, 0, 0, 0)) continue;
            this.currentPath.setCurrentNodeIndex(i);
            break;
        }
        this.checkTimeouts(vec3d);
    }

    @Override
    protected void checkTimeouts(Vec3d currentPos) {
        if (this.tickCount - this.pathStartTime > 100) {
            if (currentPos.squaredDistanceTo(this.pathStartPos) < 2.25) {
                this.stop();
            }
            this.pathStartTime = this.tickCount;
            this.pathStartPos = currentPos;
        }
        if (this.currentPath != null && !this.currentPath.isFinished()) {
            BlockPos blockPos = this.currentPath.method_31032();
            if (blockPos.equals(this.lastNodePosition)) {
                this.currentNodeMs += Util.getMeasuringTimeMs() - this.lastActiveTickMs;
            } else {
                this.lastNodePosition = blockPos;
                double d = currentPos.distanceTo(Vec3d.ofCenter(this.lastNodePosition));
                double d2 = this.currentNodeTimeout = this.entity.getMovementSpeed() > 0.0f ? d / (double)this.entity.getMovementSpeed() * 100.0 : 0.0;
            }
            if (this.currentNodeTimeout > 0.0 && (double)this.currentNodeMs > this.currentNodeTimeout * 2.0) {
                this.lastNodePosition = Vec3i.ZERO;
                this.currentNodeMs = 0L;
                this.currentNodeTimeout = 0.0;
                this.stop();
            }
            this.lastActiveTickMs = Util.getMeasuringTimeMs();
        }
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ) {
        Vec3d vec3d = new Vec3d(target.x, target.y + (double)this.entity.getHeight() * 0.5, target.z);
        return this.world.raycast(new RaycastContext(origin, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this.entity)).getType() == HitResult.Type.MISS;
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return !this.world.getBlockState(pos).isOpaqueFullCube(this.world, pos);
    }

    @Override
    public void setCanSwim(boolean canSwim) {
    }
}

