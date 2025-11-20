/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.pathing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.BirdPathNodeMaker;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BirdNavigation
extends EntityNavigation {
    public BirdNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new BirdPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range);
    }

    @Override
    protected boolean isAtValidPosition() {
        return this.canSwim() && this.isInLiquid() || !this.entity.hasVehicle();
    }

    @Override
    protected Vec3d getPos() {
        return this.entity.getPos();
    }

    @Override
    public Path findPathTo(Entity entity, int distance) {
        return this.findPathTo(entity.getBlockPos(), distance);
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
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ) {
        int n = MathHelper.floor(origin.x);
        _snowman = MathHelper.floor(origin.y);
        _snowman = MathHelper.floor(origin.z);
        double _snowman2 = target.x - origin.x;
        double _snowman3 = target.y - origin.y;
        double _snowman4 = target.z - origin.z;
        double _snowman5 = _snowman2 * _snowman2 + _snowman3 * _snowman3 + _snowman4 * _snowman4;
        if (_snowman5 < 1.0E-8) {
            return false;
        }
        double _snowman6 = 1.0 / Math.sqrt(_snowman5);
        double _snowman7 = 1.0 / Math.abs(_snowman2 *= _snowman6);
        double _snowman8 = 1.0 / Math.abs(_snowman3 *= _snowman6);
        double _snowman9 = 1.0 / Math.abs(_snowman4 *= _snowman6);
        double _snowman10 = (double)n - origin.x;
        double _snowman11 = (double)_snowman - origin.y;
        double _snowman12 = (double)_snowman - origin.z;
        if (_snowman2 >= 0.0) {
            _snowman10 += 1.0;
        }
        if (_snowman3 >= 0.0) {
            _snowman11 += 1.0;
        }
        if (_snowman4 >= 0.0) {
            _snowman12 += 1.0;
        }
        _snowman10 /= _snowman2;
        _snowman11 /= _snowman3;
        _snowman12 /= _snowman4;
        _snowman = _snowman2 < 0.0 ? -1 : 1;
        _snowman = _snowman3 < 0.0 ? -1 : 1;
        _snowman = _snowman4 < 0.0 ? -1 : 1;
        _snowman = MathHelper.floor(target.x);
        _snowman = MathHelper.floor(target.y);
        _snowman = MathHelper.floor(target.z);
        _snowman = _snowman - n;
        _snowman = _snowman - _snowman;
        _snowman = _snowman - _snowman;
        while (_snowman * _snowman > 0 || _snowman * _snowman > 0 || _snowman * _snowman > 0) {
            if (_snowman10 < _snowman12 && _snowman10 <= _snowman11) {
                _snowman10 += _snowman7;
                _snowman = _snowman - (n += _snowman);
                continue;
            }
            if (_snowman11 < _snowman10 && _snowman11 <= _snowman12) {
                _snowman11 += _snowman8;
                _snowman = _snowman - (_snowman += _snowman);
                continue;
            }
            _snowman12 += _snowman9;
            _snowman = _snowman - (_snowman += _snowman);
        }
        return true;
    }

    public void setCanPathThroughDoors(boolean canPathThroughDoors) {
        this.nodeMaker.setCanOpenDoors(canPathThroughDoors);
    }

    public void setCanEnterOpenDoors(boolean canEnterOpenDoors) {
        this.nodeMaker.setCanEnterOpenDoors(canEnterOpenDoors);
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return this.world.getBlockState(pos).hasSolidTopSurface(this.world, pos, this.entity);
    }
}

