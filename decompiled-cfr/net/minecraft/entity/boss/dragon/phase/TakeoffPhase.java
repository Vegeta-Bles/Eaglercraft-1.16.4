/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class TakeoffPhase
extends AbstractPhase {
    private boolean field_7056;
    private Path field_7054;
    private Vec3d target;

    public TakeoffPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void serverTick() {
        if (this.field_7056 || this.field_7054 == null) {
            this.field_7056 = false;
            this.method_6858();
        } else {
            BlockPos blockPos = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
            if (!blockPos.isWithinDistance(this.dragon.getPos(), 10.0)) {
                this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
            }
        }
    }

    @Override
    public void beginPhase() {
        this.field_7056 = true;
        this.field_7054 = null;
        this.target = null;
    }

    private void method_6858() {
        int n = this.dragon.getNearestPathNodeIndex();
        Vec3d _snowman2 = this.dragon.method_6834(1.0f);
        _snowman = this.dragon.getNearestPathNodeIndex(-_snowman2.x * 40.0, 105.0, -_snowman2.z * 40.0);
        if (this.dragon.getFight() == null || this.dragon.getFight().getAliveEndCrystals() <= 0) {
            _snowman -= 12;
            _snowman &= 7;
            _snowman += 12;
        } else if ((_snowman %= 12) < 0) {
            _snowman += 12;
        }
        this.field_7054 = this.dragon.findPath(n, _snowman, null);
        this.method_6859();
    }

    private void method_6859() {
        if (this.field_7054 != null) {
            this.field_7054.next();
            if (!this.field_7054.isFinished()) {
                BlockPos blockPos = this.field_7054.method_31032();
                this.field_7054.next();
                while ((_snowman = (double)((float)blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0f)) < (double)blockPos.getY()) {
                }
                this.target = new Vec3d(blockPos.getX(), _snowman, blockPos.getZ());
            }
        }
    }

    @Override
    @Nullable
    public Vec3d getTarget() {
        return this.target;
    }

    public PhaseType<TakeoffPhase> getType() {
        return PhaseType.TAKEOFF;
    }
}

