/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractSittingPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SittingScanningPhase
extends AbstractSittingPhase {
    private static final TargetPredicate PLAYER_WITHIN_RANGE_PREDICATE = new TargetPredicate().setBaseMaxDistance(150.0);
    private final TargetPredicate CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(20.0).setPredicate(livingEntity -> Math.abs(livingEntity.getY() - enderDragonEntity.getY()) <= 10.0);
    private int ticks;

    public SittingScanningPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void serverTick() {
        ++this.ticks;
        PlayerEntity playerEntity = this.dragon.world.getClosestPlayer(this.CLOSE_PLAYER_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
        if (playerEntity != null) {
            if (this.ticks > 25) {
                this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_ATTACKING);
            } else {
                Vec3d vec3d = new Vec3d(playerEntity.getX() - this.dragon.getX(), 0.0, playerEntity.getZ() - this.dragon.getZ()).normalize();
                _snowman = new Vec3d(MathHelper.sin(this.dragon.yaw * ((float)Math.PI / 180)), 0.0, -MathHelper.cos(this.dragon.yaw * ((float)Math.PI / 180))).normalize();
                float _snowman2 = (float)_snowman.dotProduct(vec3d);
                float _snowman3 = (float)(Math.acos(_snowman2) * 57.2957763671875) + 0.5f;
                if (_snowman3 < 0.0f || _snowman3 > 10.0f) {
                    double d;
                    double d2 = playerEntity.getX() - this.dragon.partHead.getX();
                    _snowman = playerEntity.getZ() - this.dragon.partHead.getZ();
                    d = MathHelper.clamp(MathHelper.wrapDegrees(180.0 - MathHelper.atan2(d2, _snowman) * 57.2957763671875 - (double)this.dragon.yaw), -100.0, 100.0);
                    this.dragon.field_20865 *= 0.8f;
                    float _snowman4 = f = MathHelper.sqrt(d2 * d2 + _snowman * _snowman) + 1.0f;
                    if (f > 40.0f) {
                        float f = 40.0f;
                    }
                    this.dragon.field_20865 = (float)((double)this.dragon.field_20865 + d * (double)(0.7f / f / _snowman4));
                    this.dragon.yaw += this.dragon.field_20865;
                }
            }
        } else if (this.ticks >= 100) {
            playerEntity = this.dragon.world.getClosestPlayer(PLAYER_WITHIN_RANGE_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
            this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
            if (playerEntity != null) {
                this.dragon.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
                this.dragon.getPhaseManager().create(PhaseType.CHARGING_PLAYER).setTarget(new Vec3d(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ()));
            }
        }
    }

    @Override
    public void beginPhase() {
        this.ticks = 0;
    }

    public PhaseType<SittingScanningPhase> getType() {
        return PhaseType.SITTING_SCANNING;
    }
}

