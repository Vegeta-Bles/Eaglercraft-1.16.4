/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StrafePlayerPhase
extends AbstractPhase {
    private static final Logger LOGGER = LogManager.getLogger();
    private int field_7060;
    private Path field_7059;
    private Vec3d target;
    private LivingEntity field_7062;
    private boolean field_7058;

    public StrafePlayerPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void serverTick() {
        double d;
        if (this.field_7062 == null) {
            LOGGER.warn("Skipping player strafe phase because no player was found");
            this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
            return;
        }
        if (this.field_7059 != null && this.field_7059.isFinished()) {
            d = this.field_7062.getX();
            _snowman = this.field_7062.getZ();
            _snowman = d - this.dragon.getX();
            _snowman = _snowman - this.dragon.getZ();
            d3 = MathHelper.sqrt(_snowman * _snowman + _snowman * _snowman);
            _snowman = Math.min((double)0.4f + d3 / 80.0 - 1.0, 10.0);
            this.target = new Vec3d(d, this.field_7062.getY() + _snowman, _snowman);
        }
        double d2 = d = this.target == null ? 0.0 : this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
        if (d < 100.0 || d > 22500.0) {
            this.method_6860();
        }
        _snowman = 64.0;
        if (this.field_7062.squaredDistanceTo(this.dragon) < 4096.0) {
            if (this.dragon.canSee(this.field_7062)) {
                ++this.field_7060;
                Vec3d vec3d = new Vec3d(this.field_7062.getX() - this.dragon.getX(), 0.0, this.field_7062.getZ() - this.dragon.getZ()).normalize();
                _snowman = new Vec3d(MathHelper.sin(this.dragon.yaw * ((float)Math.PI / 180)), 0.0, -MathHelper.cos(this.dragon.yaw * ((float)Math.PI / 180))).normalize();
                float _snowman2 = (float)_snowman.dotProduct(vec3d);
                float _snowman3 = (float)(Math.acos(_snowman2) * 57.2957763671875);
                _snowman3 += 0.5f;
                if (this.field_7060 >= 5 && _snowman3 >= 0.0f && _snowman3 < 10.0f) {
                    double d3 = 1.0;
                    Vec3d _snowman4 = this.dragon.getRotationVec(1.0f);
                    _snowman = this.dragon.partHead.getX() - _snowman4.x * 1.0;
                    _snowman = this.dragon.partHead.getBodyY(0.5) + 0.5;
                    _snowman = this.dragon.partHead.getZ() - _snowman4.z * 1.0;
                    _snowman = this.field_7062.getX() - _snowman;
                    _snowman = this.field_7062.getBodyY(0.5) - _snowman;
                    _snowman = this.field_7062.getZ() - _snowman;
                    if (!this.dragon.isSilent()) {
                        this.dragon.world.syncWorldEvent(null, 1017, this.dragon.getBlockPos(), 0);
                    }
                    DragonFireballEntity _snowman5 = new DragonFireballEntity(this.dragon.world, this.dragon, _snowman, _snowman, _snowman);
                    _snowman5.refreshPositionAndAngles(_snowman, _snowman, _snowman, 0.0f, 0.0f);
                    this.dragon.world.spawnEntity(_snowman5);
                    this.field_7060 = 0;
                    if (this.field_7059 != null) {
                        while (!this.field_7059.isFinished()) {
                            this.field_7059.next();
                        }
                    }
                    this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
                }
            } else if (this.field_7060 > 0) {
                --this.field_7060;
            }
        } else if (this.field_7060 > 0) {
            --this.field_7060;
        }
    }

    private void method_6860() {
        if (this.field_7059 == null || this.field_7059.isFinished()) {
            int n;
            _snowman = n = this.dragon.getNearestPathNodeIndex();
            if (this.dragon.getRandom().nextInt(8) == 0) {
                this.field_7058 = !this.field_7058;
                _snowman += 6;
            }
            _snowman = this.field_7058 ? ++_snowman : --_snowman;
            if (this.dragon.getFight() == null || this.dragon.getFight().getAliveEndCrystals() <= 0) {
                _snowman -= 12;
                _snowman &= 7;
                _snowman += 12;
            } else if ((_snowman %= 12) < 0) {
                _snowman += 12;
            }
            this.field_7059 = this.dragon.findPath(n, _snowman, null);
            if (this.field_7059 != null) {
                this.field_7059.next();
            }
        }
        this.method_6861();
    }

    private void method_6861() {
        if (this.field_7059 != null && !this.field_7059.isFinished()) {
            BlockPos blockPos = this.field_7059.method_31032();
            this.field_7059.next();
            double _snowman2 = blockPos.getX();
            double _snowman3 = blockPos.getZ();
            while ((_snowman = (double)((float)blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0f)) < (double)blockPos.getY()) {
            }
            this.target = new Vec3d(_snowman2, _snowman, _snowman3);
        }
    }

    @Override
    public void beginPhase() {
        this.field_7060 = 0;
        this.target = null;
        this.field_7059 = null;
        this.field_7062 = null;
    }

    public void method_6862(LivingEntity livingEntity) {
        this.field_7062 = livingEntity;
        int n = this.dragon.getNearestPathNodeIndex();
        _snowman = this.dragon.getNearestPathNodeIndex(this.field_7062.getX(), this.field_7062.getY(), this.field_7062.getZ());
        _snowman = MathHelper.floor(this.field_7062.getX());
        _snowman = MathHelper.floor(this.field_7062.getZ());
        double _snowman2 = (double)_snowman - this.dragon.getX();
        double _snowman3 = (double)_snowman - this.dragon.getZ();
        double _snowman4 = MathHelper.sqrt(_snowman2 * _snowman2 + _snowman3 * _snowman3);
        double _snowman5 = Math.min((double)0.4f + _snowman4 / 80.0 - 1.0, 10.0);
        _snowman = MathHelper.floor(this.field_7062.getY() + _snowman5);
        PathNode _snowman6 = new PathNode(_snowman, _snowman, _snowman);
        this.field_7059 = this.dragon.findPath(n, _snowman, _snowman6);
        if (this.field_7059 != null) {
            this.field_7059.next();
            this.method_6861();
        }
    }

    @Override
    @Nullable
    public Vec3d getTarget() {
        return this.target;
    }

    public PhaseType<StrafePlayerPhase> getType() {
        return PhaseType.STRAFE_PLAYER;
    }
}

