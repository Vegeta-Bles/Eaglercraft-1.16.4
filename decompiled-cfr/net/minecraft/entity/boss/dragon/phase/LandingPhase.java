/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.boss.dragon.phase;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

public class LandingPhase
extends AbstractPhase {
    private Vec3d target;

    public LandingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void clientTick() {
        Vec3d vec3d = this.dragon.method_6834(1.0f).normalize();
        vec3d.rotateY(-0.7853982f);
        double _snowman2 = this.dragon.partHead.getX();
        double _snowman3 = this.dragon.partHead.getBodyY(0.5);
        double _snowman4 = this.dragon.partHead.getZ();
        for (int i = 0; i < 8; ++i) {
            Random random = this.dragon.getRandom();
            double _snowman5 = _snowman2 + random.nextGaussian() / 2.0;
            double _snowman6 = _snowman3 + random.nextGaussian() / 2.0;
            double _snowman7 = _snowman4 + random.nextGaussian() / 2.0;
            Vec3d _snowman8 = this.dragon.getVelocity();
            this.dragon.world.addParticle(ParticleTypes.DRAGON_BREATH, _snowman5, _snowman6, _snowman7, -vec3d.x * (double)0.08f + _snowman8.x, -vec3d.y * (double)0.3f + _snowman8.y, -vec3d.z * (double)0.08f + _snowman8.z);
            vec3d.rotateY(0.19634955f);
        }
    }

    @Override
    public void serverTick() {
        if (this.target == null) {
            this.target = Vec3d.ofBottomCenter(this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN));
        }
        if (this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ()) < 1.0) {
            this.dragon.getPhaseManager().create(PhaseType.SITTING_FLAMING).method_6857();
            this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_SCANNING);
        }
    }

    @Override
    public float getMaxYAcceleration() {
        return 1.5f;
    }

    @Override
    public float method_6847() {
        float f = MathHelper.sqrt(Entity.squaredHorizontalLength(this.dragon.getVelocity())) + 1.0f;
        _snowman = Math.min(f, 40.0f);
        return _snowman / f;
    }

    @Override
    public void beginPhase() {
        this.target = null;
    }

    @Override
    @Nullable
    public Vec3d getTarget() {
        return this.target;
    }

    public PhaseType<LandingPhase> getType() {
        return PhaseType.LANDING;
    }
}

