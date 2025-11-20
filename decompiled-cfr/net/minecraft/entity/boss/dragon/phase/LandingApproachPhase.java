/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class LandingApproachPhase
extends AbstractPhase {
    private static final TargetPredicate PLAYERS_IN_RANGE_PREDICATE = new TargetPredicate().setBaseMaxDistance(128.0);
    private Path field_7047;
    private Vec3d target;

    public LandingApproachPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    public PhaseType<LandingApproachPhase> getType() {
        return PhaseType.LANDING_APPROACH;
    }

    @Override
    public void beginPhase() {
        this.field_7047 = null;
        this.target = null;
    }

    @Override
    public void serverTick() {
        double d;
        double d2 = d = this.target == null ? 0.0 : this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
        if (d < 100.0 || d > 22500.0 || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
            this.method_6844();
        }
    }

    @Override
    @Nullable
    public Vec3d getTarget() {
        return this.target;
    }

    private void method_6844() {
        if (this.field_7047 == null || this.field_7047.isFinished()) {
            int _snowman4;
            int n = this.dragon.getNearestPathNodeIndex();
            BlockPos _snowman2 = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
            PlayerEntity _snowman3 = this.dragon.world.getClosestPlayer(PLAYERS_IN_RANGE_PREDICATE, _snowman2.getX(), _snowman2.getY(), _snowman2.getZ());
            if (_snowman3 != null) {
                Object object = new Vec3d(_snowman3.getX(), 0.0, _snowman3.getZ()).normalize();
                _snowman4 = this.dragon.getNearestPathNodeIndex(-((Vec3d)object).x * 40.0, 105.0, -((Vec3d)object).z * 40.0);
            } else {
                _snowman4 = this.dragon.getNearestPathNodeIndex(40.0, _snowman2.getY(), 0.0);
            }
            object = new PathNode(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ());
            this.field_7047 = this.dragon.findPath(n, _snowman4, (PathNode)object);
            if (this.field_7047 != null) {
                this.field_7047.next();
            }
        }
        this.method_6845();
        if (this.field_7047 != null && this.field_7047.isFinished()) {
            this.dragon.getPhaseManager().setPhase(PhaseType.LANDING);
        }
    }

    private void method_6845() {
        if (this.field_7047 != null && !this.field_7047.isFinished()) {
            BlockPos blockPos = this.field_7047.method_31032();
            this.field_7047.next();
            double _snowman2 = blockPos.getX();
            double _snowman3 = blockPos.getZ();
            while ((_snowman = (double)((float)blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0f)) < (double)blockPos.getY()) {
            }
            this.target = new Vec3d(_snowman2, _snowman, _snowman3);
        }
    }
}

