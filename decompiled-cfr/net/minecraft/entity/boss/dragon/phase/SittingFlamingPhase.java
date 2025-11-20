/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractSittingPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SittingFlamingPhase
extends AbstractSittingPhase {
    private int ticks;
    private int field_7052;
    private AreaEffectCloudEntity field_7051;

    public SittingFlamingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void clientTick() {
        ++this.ticks;
        if (this.ticks % 2 == 0 && this.ticks < 10) {
            Vec3d vec3d = this.dragon.method_6834(1.0f).normalize();
            vec3d.rotateY(-0.7853982f);
            double _snowman2 = this.dragon.partHead.getX();
            double _snowman3 = this.dragon.partHead.getBodyY(0.5);
            double _snowman4 = this.dragon.partHead.getZ();
            for (int i = 0; i < 8; ++i) {
                double d = _snowman2 + this.dragon.getRandom().nextGaussian() / 2.0;
                _snowman = _snowman3 + this.dragon.getRandom().nextGaussian() / 2.0;
                _snowman = _snowman4 + this.dragon.getRandom().nextGaussian() / 2.0;
                for (int j = 0; j < 6; ++j) {
                    this.dragon.world.addParticle(ParticleTypes.DRAGON_BREATH, d, _snowman, _snowman, -vec3d.x * (double)0.08f * (double)j, -vec3d.y * (double)0.6f, -vec3d.z * (double)0.08f * (double)j);
                }
                vec3d.rotateY(0.19634955f);
            }
        }
    }

    @Override
    public void serverTick() {
        ++this.ticks;
        if (this.ticks >= 200) {
            if (this.field_7052 >= 4) {
                this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
            } else {
                this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_SCANNING);
            }
        } else if (this.ticks == 10) {
            Vec3d vec3d = new Vec3d(this.dragon.partHead.getX() - this.dragon.getX(), 0.0, this.dragon.partHead.getZ() - this.dragon.getZ()).normalize();
            float _snowman2 = 5.0f;
            double _snowman3 = this.dragon.partHead.getX() + vec3d.x * 5.0 / 2.0;
            double _snowman4 = this.dragon.partHead.getZ() + vec3d.z * 5.0 / 2.0;
            double _snowman5 = _snowman = this.dragon.partHead.getBodyY(0.5);
            BlockPos.Mutable _snowman6 = new BlockPos.Mutable(_snowman3, _snowman5, _snowman4);
            while (this.dragon.world.isAir(_snowman6)) {
                if ((_snowman5 -= 1.0) < 0.0) {
                    _snowman5 = _snowman;
                    break;
                }
                _snowman6.set(_snowman3, _snowman5, _snowman4);
            }
            _snowman5 = MathHelper.floor(_snowman5) + 1;
            this.field_7051 = new AreaEffectCloudEntity(this.dragon.world, _snowman3, _snowman5, _snowman4);
            this.field_7051.setOwner(this.dragon);
            this.field_7051.setRadius(5.0f);
            this.field_7051.setDuration(200);
            this.field_7051.setParticleType(ParticleTypes.DRAGON_BREATH);
            this.field_7051.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE));
            this.dragon.world.spawnEntity(this.field_7051);
        }
    }

    @Override
    public void beginPhase() {
        this.ticks = 0;
        ++this.field_7052;
    }

    @Override
    public void endPhase() {
        if (this.field_7051 != null) {
            this.field_7051.remove();
            this.field_7051 = null;
        }
    }

    public PhaseType<SittingFlamingPhase> getType() {
        return PhaseType.SITTING_FLAMING;
    }

    public void method_6857() {
        this.field_7052 = 0;
    }
}

