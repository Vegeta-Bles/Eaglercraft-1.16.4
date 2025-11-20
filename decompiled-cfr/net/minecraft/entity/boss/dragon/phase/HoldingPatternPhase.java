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
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class HoldingPatternPhase
extends AbstractPhase {
    private static final TargetPredicate PLAYERS_IN_RANGE_PREDICATE = new TargetPredicate().setBaseMaxDistance(64.0);
    private Path field_7043;
    private Vec3d target;
    private boolean field_7044;

    public HoldingPatternPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    public PhaseType<HoldingPatternPhase> getType() {
        return PhaseType.HOLDING_PATTERN;
    }

    @Override
    public void serverTick() {
        double d;
        double d2 = d = this.target == null ? 0.0 : this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
        if (d < 100.0 || d > 22500.0 || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
            this.method_6841();
        }
    }

    @Override
    public void beginPhase() {
        this.field_7043 = null;
        this.target = null;
    }

    @Override
    @Nullable
    public Vec3d getTarget() {
        return this.target;
    }

    private void method_6841() {
        if (this.field_7043 != null && this.field_7043.isFinished()) {
            BlockPos blockPos = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPortalFeature.ORIGIN));
            int n = _snowman4 = this.dragon.getFight() == null ? 0 : this.dragon.getFight().getAliveEndCrystals();
            if (this.dragon.getRandom().nextInt(_snowman4 + 3) == 0) {
                this.dragon.getPhaseManager().setPhase(PhaseType.LANDING_APPROACH);
                return;
            }
            double _snowman2 = 64.0;
            PlayerEntity _snowman3 = this.dragon.world.getClosestPlayer(PLAYERS_IN_RANGE_PREDICATE, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (_snowman3 != null) {
                _snowman2 = blockPos.getSquaredDistance(_snowman3.getPos(), true) / 512.0;
            }
            if (!(_snowman3 == null || _snowman3.abilities.invulnerable || this.dragon.getRandom().nextInt(MathHelper.abs((int)_snowman2) + 2) != 0 && this.dragon.getRandom().nextInt(_snowman4 + 2) != 0)) {
                this.method_6843(_snowman3);
                return;
            }
        }
        if (this.field_7043 == null || this.field_7043.isFinished()) {
            int _snowman4 = _snowman = this.dragon.getNearestPathNodeIndex();
            if (this.dragon.getRandom().nextInt(8) == 0) {
                this.field_7044 = !this.field_7044;
                _snowman4 += 6;
            }
            _snowman4 = this.field_7044 ? ++_snowman4 : --_snowman4;
            if (this.dragon.getFight() == null || this.dragon.getFight().getAliveEndCrystals() < 0) {
                _snowman4 -= 12;
                _snowman4 &= 7;
                _snowman4 += 12;
            } else if ((_snowman4 %= 12) < 0) {
                _snowman4 += 12;
            }
            this.field_7043 = this.dragon.findPath(_snowman, _snowman4, null);
            if (this.field_7043 != null) {
                this.field_7043.next();
            }
        }
        this.method_6842();
    }

    private void method_6843(PlayerEntity playerEntity) {
        this.dragon.getPhaseManager().setPhase(PhaseType.STRAFE_PLAYER);
        this.dragon.getPhaseManager().create(PhaseType.STRAFE_PLAYER).method_6862(playerEntity);
    }

    private void method_6842() {
        if (this.field_7043 != null && !this.field_7043.isFinished()) {
            BlockPos blockPos = this.field_7043.method_31032();
            this.field_7043.next();
            double _snowman2 = blockPos.getX();
            double _snowman3 = blockPos.getZ();
            while ((_snowman = (double)((float)blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0f)) < (double)blockPos.getY()) {
            }
            this.target = new Vec3d(_snowman2, _snowman, _snowman3);
        }
    }

    @Override
    public void crystalDestroyed(EndCrystalEntity crystal, BlockPos pos, DamageSource source, @Nullable PlayerEntity player) {
        if (player != null && !player.abilities.invulnerable) {
            this.method_6843(player);
        }
    }
}

