/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.boss.dragon;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathMinHeap;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonEntity
extends MobEntity
implements Monster {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final TrackedData<Integer> PHASE_TYPE = DataTracker.registerData(EnderDragonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(64.0);
    public final double[][] segmentCircularBuffer = new double[64][3];
    public int latestSegment = -1;
    private final EnderDragonPart[] parts;
    public final EnderDragonPart partHead;
    private final EnderDragonPart partNeck;
    private final EnderDragonPart partBody;
    private final EnderDragonPart partTail1;
    private final EnderDragonPart partTail2;
    private final EnderDragonPart partTail3;
    private final EnderDragonPart partWingRight;
    private final EnderDragonPart partWingLeft;
    public float prevWingPosition;
    public float wingPosition;
    public boolean slowedDownByBlock;
    public int ticksSinceDeath;
    public float field_20865;
    @Nullable
    public EndCrystalEntity connectedCrystal;
    @Nullable
    private final EnderDragonFight fight;
    private final PhaseManager phaseManager;
    private int ticksUntilNextGrowl = 100;
    private int field_7029;
    private final PathNode[] pathNodes = new PathNode[24];
    private final int[] pathNodeConnections = new int[24];
    private final PathMinHeap pathHeap = new PathMinHeap();

    public EnderDragonEntity(EntityType<? extends EnderDragonEntity> entityType, World world) {
        super((EntityType<? extends MobEntity>)EntityType.ENDER_DRAGON, world);
        this.partHead = new EnderDragonPart(this, "head", 1.0f, 1.0f);
        this.partNeck = new EnderDragonPart(this, "neck", 3.0f, 3.0f);
        this.partBody = new EnderDragonPart(this, "body", 5.0f, 3.0f);
        this.partTail1 = new EnderDragonPart(this, "tail", 2.0f, 2.0f);
        this.partTail2 = new EnderDragonPart(this, "tail", 2.0f, 2.0f);
        this.partTail3 = new EnderDragonPart(this, "tail", 2.0f, 2.0f);
        this.partWingRight = new EnderDragonPart(this, "wing", 4.0f, 2.0f);
        this.partWingLeft = new EnderDragonPart(this, "wing", 4.0f, 2.0f);
        this.parts = new EnderDragonPart[]{this.partHead, this.partNeck, this.partBody, this.partTail1, this.partTail2, this.partTail3, this.partWingRight, this.partWingLeft};
        this.setHealth(this.getMaxHealth());
        this.noClip = true;
        this.ignoreCameraFrustum = true;
        this.fight = world instanceof ServerWorld ? ((ServerWorld)world).getEnderDragonFight() : null;
        this.phaseManager = new PhaseManager(this);
    }

    public static DefaultAttributeContainer.Builder createEnderDragonAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(PHASE_TYPE, PhaseType.HOVER.getTypeId());
    }

    public double[] getSegmentProperties(int segmentNumber, float tickDelta) {
        if (this.isDead()) {
            tickDelta = 0.0f;
        }
        tickDelta = 1.0f - tickDelta;
        int n = this.latestSegment - segmentNumber & 0x3F;
        _snowman = this.latestSegment - segmentNumber - 1 & 0x3F;
        double[] _snowman2 = new double[3];
        double _snowman3 = this.segmentCircularBuffer[n][0];
        double _snowman4 = MathHelper.wrapDegrees(this.segmentCircularBuffer[_snowman][0] - _snowman3);
        _snowman2[0] = _snowman3 + _snowman4 * (double)tickDelta;
        _snowman3 = this.segmentCircularBuffer[n][1];
        _snowman4 = this.segmentCircularBuffer[_snowman][1] - _snowman3;
        _snowman2[1] = _snowman3 + _snowman4 * (double)tickDelta;
        _snowman2[2] = MathHelper.lerp((double)tickDelta, this.segmentCircularBuffer[n][2], this.segmentCircularBuffer[_snowman][2]);
        return _snowman2;
    }

    @Override
    public void tickMovement() {
        int n;
        float _snowman7;
        float _snowman6;
        float f;
        if (this.world.isClient) {
            this.setHealth(this.getHealth());
            if (!this.isSilent()) {
                f = MathHelper.cos(this.wingPosition * ((float)Math.PI * 2));
                _snowman2 = MathHelper.cos(this.prevWingPosition * ((float)Math.PI * 2));
                if (_snowman2 <= -0.3f && f >= -0.3f) {
                    this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, this.getSoundCategory(), 5.0f, 0.8f + this.random.nextFloat() * 0.3f, false);
                }
                if (!this.phaseManager.getCurrent().isSittingOrHovering() && --this.ticksUntilNextGrowl < 0) {
                    this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, this.getSoundCategory(), 2.5f, 0.8f + this.random.nextFloat() * 0.3f, false);
                    this.ticksUntilNextGrowl = 200 + this.random.nextInt(200);
                }
            }
        }
        this.prevWingPosition = this.wingPosition;
        if (this.isDead()) {
            f = (this.random.nextFloat() - 0.5f) * 8.0f;
            _snowman2 = (this.random.nextFloat() - 0.5f) * 4.0f;
            _snowman = (this.random.nextFloat() - 0.5f) * 8.0f;
            this.world.addParticle(ParticleTypes.EXPLOSION, this.getX() + (double)f, this.getY() + 2.0 + (double)_snowman2, this.getZ() + (double)_snowman, 0.0, 0.0, 0.0);
            return;
        }
        this.tickWithEndCrystals();
        Vec3d vec3d = this.getVelocity();
        float _snowman2 = 0.2f / (MathHelper.sqrt(EnderDragonEntity.squaredHorizontalLength(vec3d)) * 10.0f + 1.0f);
        this.wingPosition = this.phaseManager.getCurrent().isSittingOrHovering() ? (this.wingPosition += 0.1f) : (this.slowedDownByBlock ? (this.wingPosition += _snowman2 * 0.5f) : (this.wingPosition += (_snowman2 *= (float)Math.pow(2.0, vec3d.y))));
        this.yaw = MathHelper.wrapDegrees(this.yaw);
        if (this.isAiDisabled()) {
            this.wingPosition = 0.5f;
            return;
        }
        if (this.latestSegment < 0) {
            for (int i = 0; i < this.segmentCircularBuffer.length; ++i) {
                this.segmentCircularBuffer[i][0] = this.yaw;
                this.segmentCircularBuffer[i][1] = this.getY();
            }
        }
        if (++this.latestSegment == this.segmentCircularBuffer.length) {
            this.latestSegment = 0;
        }
        this.segmentCircularBuffer[this.latestSegment][0] = this.yaw;
        this.segmentCircularBuffer[this.latestSegment][1] = this.getY();
        if (this.world.isClient) {
            if (this.bodyTrackingIncrements > 0) {
                double d = this.getX() + (this.serverX - this.getX()) / (double)this.bodyTrackingIncrements;
                d = this.getY() + (this.serverY - this.getY()) / (double)this.bodyTrackingIncrements;
                _snowman = this.getZ() + (this.serverZ - this.getZ()) / (double)this.bodyTrackingIncrements;
                _snowman = MathHelper.wrapDegrees(this.serverYaw - (double)this.yaw);
                this.yaw = (float)((double)this.yaw + _snowman / (double)this.bodyTrackingIncrements);
                this.pitch = (float)((double)this.pitch + (this.serverPitch - (double)this.pitch) / (double)this.bodyTrackingIncrements);
                --this.bodyTrackingIncrements;
                this.updatePosition(d, d, _snowman);
                this.setRotation(this.yaw, this.pitch);
            }
            this.phaseManager.getCurrent().clientTick();
        } else {
            Phase phase = this.phaseManager.getCurrent();
            phase.serverTick();
            if (this.phaseManager.getCurrent() != phase) {
                phase = this.phaseManager.getCurrent();
                phase.serverTick();
            }
            if ((_snowman = phase.getTarget()) != null) {
                double d = _snowman.x - this.getX();
                _snowman = _snowman.y - this.getY();
                _snowman = _snowman.z - this.getZ();
                _snowman = d * d + _snowman * _snowman + _snowman * _snowman;
                float _snowman3 = phase.getMaxYAcceleration();
                _snowman = MathHelper.sqrt(d * d + _snowman * _snowman);
                if (_snowman > 0.0) {
                    _snowman = MathHelper.clamp(_snowman / _snowman, (double)(-_snowman3), (double)_snowman3);
                }
                this.setVelocity(this.getVelocity().add(0.0, _snowman * 0.01, 0.0));
                this.yaw = MathHelper.wrapDegrees(this.yaw);
                _snowman = MathHelper.clamp(MathHelper.wrapDegrees(180.0 - MathHelper.atan2(d, _snowman) * 57.2957763671875 - (double)this.yaw), -50.0, 50.0);
                Vec3d _snowman4 = _snowman.subtract(this.getX(), this.getY(), this.getZ()).normalize();
                Vec3d _snowman5 = new Vec3d(MathHelper.sin(this.yaw * ((float)Math.PI / 180)), this.getVelocity().y, -MathHelper.cos(this.yaw * ((float)Math.PI / 180))).normalize();
                _snowman6 = Math.max(((float)_snowman5.dotProduct(_snowman4) + 0.5f) / 1.5f, 0.0f);
                this.field_20865 *= 0.8f;
                this.field_20865 = (float)((double)this.field_20865 + _snowman * (double)phase.method_6847());
                this.yaw += this.field_20865 * 0.1f;
                _snowman7 = (float)(2.0 / (_snowman + 1.0));
                float _snowman8 = 0.06f;
                this.updateVelocity(0.06f * (_snowman6 * _snowman7 + (1.0f - _snowman7)), new Vec3d(0.0, 0.0, -1.0));
                if (this.slowedDownByBlock) {
                    this.move(MovementType.SELF, this.getVelocity().multiply(0.8f));
                } else {
                    this.move(MovementType.SELF, this.getVelocity());
                }
                Vec3d _snowman9 = this.getVelocity().normalize();
                _snowman = 0.8 + 0.15 * (_snowman9.dotProduct(_snowman5) + 1.0) / 2.0;
                this.setVelocity(this.getVelocity().multiply(_snowman, 0.91f, _snowman));
            }
        }
        this.bodyYaw = this.yaw;
        Vec3d[] vec3dArray = new Vec3d[this.parts.length];
        for (int i = 0; i < this.parts.length; ++i) {
            vec3dArray[i] = new Vec3d(this.parts[i].getX(), this.parts[i].getY(), this.parts[i].getZ());
        }
        float f2 = (float)(this.getSegmentProperties(5, 1.0f)[1] - this.getSegmentProperties(10, 1.0f)[1]) * 10.0f * ((float)Math.PI / 180);
        _snowman = MathHelper.cos(f2);
        _snowman = MathHelper.sin(f2);
        _snowman = this.yaw * ((float)Math.PI / 180);
        _snowman = MathHelper.sin(_snowman);
        _snowman = MathHelper.cos(_snowman);
        this.movePart(this.partBody, _snowman * 0.5f, 0.0, -_snowman * 0.5f);
        this.movePart(this.partWingRight, _snowman * 4.5f, 2.0, _snowman * 4.5f);
        this.movePart(this.partWingLeft, _snowman * -4.5f, 2.0, _snowman * -4.5f);
        if (!this.world.isClient && this.hurtTime == 0) {
            this.launchLivingEntities(this.world.getOtherEntities(this, this.partWingRight.getBoundingBox().expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
            this.launchLivingEntities(this.world.getOtherEntities(this, this.partWingLeft.getBoundingBox().expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
            this.damageLivingEntities(this.world.getOtherEntities(this, this.partHead.getBoundingBox().expand(1.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
            this.damageLivingEntities(this.world.getOtherEntities(this, this.partNeck.getBoundingBox().expand(1.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
        }
        _snowman = MathHelper.sin(this.yaw * ((float)Math.PI / 180) - this.field_20865 * 0.01f);
        _snowman = MathHelper.cos(this.yaw * ((float)Math.PI / 180) - this.field_20865 * 0.01f);
        _snowman = this.method_6820();
        this.movePart(this.partHead, _snowman * 6.5f * _snowman, _snowman + _snowman * 6.5f, -_snowman * 6.5f * _snowman);
        this.movePart(this.partNeck, _snowman * 5.5f * _snowman, _snowman + _snowman * 5.5f, -_snowman * 5.5f * _snowman);
        double[] _snowman10 = this.getSegmentProperties(5, 1.0f);
        for (n = 0; n < 3; ++n) {
            EnderDragonPart enderDragonPart = null;
            if (n == 0) {
                enderDragonPart = this.partTail1;
            }
            if (n == 1) {
                enderDragonPart = this.partTail2;
            }
            if (n == 2) {
                enderDragonPart = this.partTail3;
            }
            double[] _snowman11 = this.getSegmentProperties(12 + n * 2, 1.0f);
            float _snowman12 = this.yaw * ((float)Math.PI / 180) + this.wrapYawChange(_snowman11[0] - _snowman10[0]) * ((float)Math.PI / 180);
            float _snowman13 = MathHelper.sin(_snowman12);
            float _snowman14 = MathHelper.cos(_snowman12);
            _snowman6 = 1.5f;
            _snowman7 = (float)(n + 1) * 2.0f;
            this.movePart(enderDragonPart, -(_snowman * 1.5f + _snowman13 * _snowman7) * _snowman, _snowman11[1] - _snowman10[1] - (double)((_snowman7 + 1.5f) * _snowman) + 1.5, (_snowman * 1.5f + _snowman14 * _snowman7) * _snowman);
        }
        if (!this.world.isClient) {
            this.slowedDownByBlock = this.destroyBlocks(this.partHead.getBoundingBox()) | this.destroyBlocks(this.partNeck.getBoundingBox()) | this.destroyBlocks(this.partBody.getBoundingBox());
            if (this.fight != null) {
                this.fight.updateFight(this);
            }
        }
        for (n = 0; n < this.parts.length; ++n) {
            this.parts[n].prevX = vec3dArray[n].x;
            this.parts[n].prevY = vec3dArray[n].y;
            this.parts[n].prevZ = vec3dArray[n].z;
            this.parts[n].lastRenderX = vec3dArray[n].x;
            this.parts[n].lastRenderY = vec3dArray[n].y;
            this.parts[n].lastRenderZ = vec3dArray[n].z;
        }
    }

    private void movePart(EnderDragonPart enderDragonPart, double dx, double dy, double dz) {
        enderDragonPart.updatePosition(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
    }

    private float method_6820() {
        if (this.phaseManager.getCurrent().isSittingOrHovering()) {
            return -1.0f;
        }
        double[] dArray = this.getSegmentProperties(5, 1.0f);
        _snowman = this.getSegmentProperties(0, 1.0f);
        return (float)(dArray[1] - _snowman[1]);
    }

    private void tickWithEndCrystals() {
        if (this.connectedCrystal != null) {
            if (this.connectedCrystal.removed) {
                this.connectedCrystal = null;
            } else if (this.age % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0f);
            }
        }
        if (this.random.nextInt(10) == 0) {
            List<EndCrystalEntity> list = this.world.getNonSpectatingEntities(EndCrystalEntity.class, this.getBoundingBox().expand(32.0));
            EndCrystalEntity _snowman2 = null;
            double _snowman3 = Double.MAX_VALUE;
            for (EndCrystalEntity endCrystalEntity : list) {
                double d = endCrystalEntity.squaredDistanceTo(this);
                if (!(d < _snowman3)) continue;
                _snowman3 = d;
                _snowman2 = endCrystalEntity;
            }
            this.connectedCrystal = _snowman2;
        }
    }

    private void launchLivingEntities(List<Entity> entities) {
        double d = (this.partBody.getBoundingBox().minX + this.partBody.getBoundingBox().maxX) / 2.0;
        _snowman = (this.partBody.getBoundingBox().minZ + this.partBody.getBoundingBox().maxZ) / 2.0;
        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) continue;
            double d2 = entity.getX() - d;
            _snowman = entity.getZ() - _snowman;
            _snowman = Math.max(d2 * d2 + _snowman * _snowman, 0.1);
            entity.addVelocity(d2 / _snowman * 4.0, 0.2f, _snowman / _snowman * 4.0);
            if (this.phaseManager.getCurrent().isSittingOrHovering() || ((LivingEntity)entity).getLastAttackedTime() >= entity.age - 2) continue;
            entity.damage(DamageSource.mob(this), 5.0f);
            this.dealDamage(this, entity);
        }
    }

    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) continue;
            entity.damage(DamageSource.mob(this), 10.0f);
            this.dealDamage(this, entity);
        }
    }

    private float wrapYawChange(double yawDegrees) {
        return (float)MathHelper.wrapDegrees(yawDegrees);
    }

    private boolean destroyBlocks(Box box) {
        int n = MathHelper.floor(box.minX);
        _snowman = MathHelper.floor(box.minY);
        _snowman = MathHelper.floor(box.minZ);
        _snowman = MathHelper.floor(box.maxX);
        _snowman = MathHelper.floor(box.maxY);
        _snowman = MathHelper.floor(box.maxZ);
        boolean _snowman2 = false;
        boolean _snowman3 = false;
        for (_snowman = n; _snowman <= _snowman; ++_snowman) {
            for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                    BlockPos blockPos = new BlockPos(_snowman, _snowman, _snowman);
                    BlockState _snowman4 = this.world.getBlockState(blockPos);
                    Block _snowman5 = _snowman4.getBlock();
                    if (_snowman4.isAir() || _snowman4.getMaterial() == Material.FIRE) continue;
                    if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) || BlockTags.DRAGON_IMMUNE.contains(_snowman5)) {
                        _snowman2 = true;
                        continue;
                    }
                    _snowman3 = this.world.removeBlock(blockPos, false) || _snowman3;
                }
            }
        }
        if (_snowman3) {
            _snowman = new BlockPos(n + this.random.nextInt(_snowman - n + 1), _snowman + this.random.nextInt(_snowman - _snowman + 1), _snowman + this.random.nextInt(_snowman - _snowman + 1));
            this.world.syncWorldEvent(2008, _snowman, 0);
        }
        return _snowman2;
    }

    public boolean damagePart(EnderDragonPart part, DamageSource source, float amount) {
        if (this.phaseManager.getCurrent().getType() == PhaseType.DYING) {
            return false;
        }
        amount = this.phaseManager.getCurrent().modifyDamageTaken(source, amount);
        if (part != this.partHead) {
            amount = amount / 4.0f + Math.min(amount, 1.0f);
        }
        if (amount < 0.01f) {
            return false;
        }
        if (source.getAttacker() instanceof PlayerEntity || source.isExplosive()) {
            float f = this.getHealth();
            this.parentDamage(source, amount);
            if (this.isDead() && !this.phaseManager.getCurrent().isSittingOrHovering()) {
                this.setHealth(1.0f);
                this.phaseManager.setPhase(PhaseType.DYING);
            }
            if (this.phaseManager.getCurrent().isSittingOrHovering()) {
                this.field_7029 = (int)((float)this.field_7029 + (f - this.getHealth()));
                if ((float)this.field_7029 > 0.25f * this.getMaxHealth()) {
                    this.field_7029 = 0;
                    this.phaseManager.setPhase(PhaseType.TAKEOFF);
                }
            }
        }
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
            this.damagePart(this.partBody, source, amount);
        }
        return false;
    }

    protected boolean parentDamage(DamageSource source, float amount) {
        return super.damage(source, amount);
    }

    @Override
    public void kill() {
        this.remove();
        if (this.fight != null) {
            this.fight.updateFight(this);
            this.fight.dragonKilled(this);
        }
    }

    @Override
    protected void updatePostDeath() {
        if (this.fight != null) {
            this.fight.updateFight(this);
        }
        ++this.ticksSinceDeath;
        if (this.ticksSinceDeath >= 180 && this.ticksSinceDeath <= 200) {
            float f = (this.random.nextFloat() - 0.5f) * 8.0f;
            _snowman = (this.random.nextFloat() - 0.5f) * 4.0f;
            _snowman = (this.random.nextFloat() - 0.5f) * 8.0f;
            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + (double)f, this.getY() + 2.0 + (double)_snowman, this.getZ() + (double)_snowman, 0.0, 0.0, 0.0);
        }
        boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT);
        int _snowman2 = 500;
        if (this.fight != null && !this.fight.hasPreviouslyKilled()) {
            _snowman2 = 12000;
        }
        if (!this.world.isClient) {
            if (this.ticksSinceDeath > 150 && this.ticksSinceDeath % 5 == 0 && bl) {
                this.awardExperience(MathHelper.floor((float)_snowman2 * 0.08f));
            }
            if (this.ticksSinceDeath == 1 && !this.isSilent()) {
                this.world.syncGlobalEvent(1028, this.getBlockPos(), 0);
            }
        }
        this.move(MovementType.SELF, new Vec3d(0.0, 0.1f, 0.0));
        this.yaw += 20.0f;
        this.bodyYaw = this.yaw;
        if (this.ticksSinceDeath == 200 && !this.world.isClient) {
            if (bl) {
                this.awardExperience(MathHelper.floor((float)_snowman2 * 0.2f));
            }
            if (this.fight != null) {
                this.fight.dragonKilled(this);
            }
            this.remove();
        }
    }

    private void awardExperience(int amount) {
        while (amount > 0) {
            int n = ExperienceOrbEntity.roundToOrbSize(amount);
            amount -= n;
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY(), this.getZ(), n));
        }
    }

    public int getNearestPathNodeIndex() {
        if (this.pathNodes[0] == null) {
            for (int i = 0; i < 24; ++i) {
                _snowman = 5;
                _snowman = i;
                if (i < 12) {
                    _snowman = MathHelper.floor(60.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)_snowman)));
                    _snowman = MathHelper.floor(60.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)_snowman)));
                } else if (i < 20) {
                    _snowman = MathHelper.floor(40.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)(_snowman -= 12))));
                    _snowman = MathHelper.floor(40.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)_snowman)));
                    _snowman += 10;
                } else {
                    _snowman = MathHelper.floor(20.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)(_snowman -= 20))));
                    _snowman = MathHelper.floor(20.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)_snowman)));
                }
                _snowman = Math.max(this.world.getSeaLevel() + 10, this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(_snowman, 0, _snowman)).getY() + _snowman);
                this.pathNodes[i] = new PathNode(_snowman, _snowman, _snowman);
            }
            this.pathNodeConnections[0] = 6146;
            this.pathNodeConnections[1] = 8197;
            this.pathNodeConnections[2] = 8202;
            this.pathNodeConnections[3] = 16404;
            this.pathNodeConnections[4] = 32808;
            this.pathNodeConnections[5] = 32848;
            this.pathNodeConnections[6] = 65696;
            this.pathNodeConnections[7] = 131392;
            this.pathNodeConnections[8] = 131712;
            this.pathNodeConnections[9] = 263424;
            this.pathNodeConnections[10] = 526848;
            this.pathNodeConnections[11] = 525313;
            this.pathNodeConnections[12] = 1581057;
            this.pathNodeConnections[13] = 3166214;
            this.pathNodeConnections[14] = 2138120;
            this.pathNodeConnections[15] = 6373424;
            this.pathNodeConnections[16] = 4358208;
            this.pathNodeConnections[17] = 12910976;
            this.pathNodeConnections[18] = 9044480;
            this.pathNodeConnections[19] = 9706496;
            this.pathNodeConnections[20] = 15216640;
            this.pathNodeConnections[21] = 0xD0E000;
            this.pathNodeConnections[22] = 11763712;
            this.pathNodeConnections[23] = 0x7E0000;
        }
        return this.getNearestPathNodeIndex(this.getX(), this.getY(), this.getZ());
    }

    public int getNearestPathNodeIndex(double x, double y, double z) {
        float f = 10000.0f;
        int _snowman2 = 0;
        PathNode _snowman3 = new PathNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
        int _snowman4 = 0;
        if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
            _snowman4 = 12;
        }
        for (int i = _snowman4; i < 24; ++i) {
            if (this.pathNodes[i] == null || !((_snowman = this.pathNodes[i].getSquaredDistance(_snowman3)) < f)) continue;
            f = _snowman;
            _snowman2 = i;
        }
        return _snowman2;
    }

    @Nullable
    public Path findPath(int from, int to, @Nullable PathNode pathNode) {
        for (int i = 0; i < 24; ++i) {
            PathNode pathNode2 = this.pathNodes[i];
            pathNode2.visited = false;
            pathNode2.heapWeight = 0.0f;
            pathNode2.penalizedPathLength = 0.0f;
            pathNode2.distanceToNearestTarget = 0.0f;
            pathNode2.previous = null;
            pathNode2.heapIndex = -1;
        }
        PathNode pathNode3 = this.pathNodes[from];
        pathNode2 = this.pathNodes[to];
        pathNode3.penalizedPathLength = 0.0f;
        pathNode3.heapWeight = pathNode3.distanceToNearestTarget = pathNode3.getDistance(pathNode2);
        this.pathHeap.clear();
        this.pathHeap.push(pathNode3);
        _snowman = pathNode3;
        int _snowman2 = 0;
        if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
            _snowman2 = 12;
        }
        while (!this.pathHeap.isEmpty()) {
            _snowman = this.pathHeap.pop();
            if (_snowman.equals(pathNode2)) {
                if (pathNode != null) {
                    pathNode.previous = pathNode2;
                    pathNode2 = pathNode;
                }
                return this.getPathOfAllPredecessors(pathNode3, pathNode2);
            }
            if (_snowman.getDistance(pathNode2) < _snowman.getDistance(pathNode2)) {
                _snowman = _snowman;
            }
            _snowman.visited = true;
            int n = 0;
            for (_snowman = 0; _snowman < 24; ++_snowman) {
                if (this.pathNodes[_snowman] != _snowman) continue;
                n = _snowman;
                break;
            }
            for (_snowman = _snowman2; _snowman < 24; ++_snowman) {
                if ((this.pathNodeConnections[n] & 1 << _snowman) <= 0) continue;
                PathNode pathNode4 = this.pathNodes[_snowman];
                if (pathNode4.visited) continue;
                float _snowman3 = _snowman.penalizedPathLength + _snowman.getDistance(pathNode4);
                if (pathNode4.isInHeap() && !(_snowman3 < pathNode4.penalizedPathLength)) continue;
                pathNode4.previous = _snowman;
                pathNode4.penalizedPathLength = _snowman3;
                pathNode4.distanceToNearestTarget = pathNode4.getDistance(pathNode2);
                if (pathNode4.isInHeap()) {
                    this.pathHeap.setNodeWeight(pathNode4, pathNode4.penalizedPathLength + pathNode4.distanceToNearestTarget);
                    continue;
                }
                pathNode4.heapWeight = pathNode4.penalizedPathLength + pathNode4.distanceToNearestTarget;
                this.pathHeap.push(pathNode4);
            }
        }
        if (_snowman == pathNode3) {
            return null;
        }
        LOGGER.debug("Failed to find path from {} to {}", (Object)from, (Object)to);
        if (pathNode != null) {
            pathNode.previous = _snowman;
            _snowman = pathNode;
        }
        return this.getPathOfAllPredecessors(pathNode3, _snowman);
    }

    private Path getPathOfAllPredecessors(PathNode unused, PathNode node) {
        ArrayList arrayList = Lists.newArrayList();
        PathNode _snowman2 = node;
        arrayList.add(0, _snowman2);
        while (_snowman2.previous != null) {
            _snowman2 = _snowman2.previous;
            arrayList.add(0, _snowman2);
        }
        return new Path(arrayList, new BlockPos(node.x, node.y, node.z), true);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("DragonPhase", this.phaseManager.getCurrent().getType().getTypeId());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("DragonPhase")) {
            this.phaseManager.setPhase(PhaseType.getFromId(tag.getInt("DragonPhase")));
        }
    }

    @Override
    public void checkDespawn() {
    }

    public EnderDragonPart[] getBodyParts() {
        return this.parts;
    }

    @Override
    public boolean collides() {
        return false;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }

    public float method_6823(int segmentOffset, double[] segment1, double[] segment2) {
        double _snowman4;
        Phase phase = this.phaseManager.getCurrent();
        PhaseType<? extends Phase> _snowman2 = phase.getType();
        if (_snowman2 == PhaseType.LANDING || _snowman2 == PhaseType.TAKEOFF) {
            BlockPos blockPos = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
            float _snowman3 = Math.max(MathHelper.sqrt(blockPos.getSquaredDistance(this.getPos(), true)) / 4.0f, 1.0f);
            _snowman4 = (float)segmentOffset / _snowman3;
        } else {
            _snowman4 = phase.isSittingOrHovering() ? (double)segmentOffset : (segmentOffset == 6 ? 0.0 : segment2[1] - segment1[1]);
        }
        return (float)_snowman4;
    }

    public Vec3d method_6834(float tickDelta) {
        Vec3d _snowman7;
        Phase phase = this.phaseManager.getCurrent();
        PhaseType<? extends Phase> _snowman2 = phase.getType();
        if (_snowman2 == PhaseType.LANDING || _snowman2 == PhaseType.TAKEOFF) {
            BlockPos blockPos = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
            float _snowman3 = Math.max(MathHelper.sqrt(blockPos.getSquaredDistance(this.getPos(), true)) / 4.0f, 1.0f);
            float _snowman4 = 6.0f / _snowman3;
            float _snowman5 = this.pitch;
            float _snowman6 = 1.5f;
            this.pitch = -_snowman4 * 1.5f * 5.0f;
            _snowman7 = this.getRotationVec(tickDelta);
            this.pitch = _snowman5;
        } else if (phase.isSittingOrHovering()) {
            float _snowman8 = this.pitch;
            float _snowman9 = 1.5f;
            this.pitch = -45.0f;
            _snowman7 = this.getRotationVec(tickDelta);
            this.pitch = _snowman8;
        } else {
            _snowman7 = this.getRotationVec(tickDelta);
        }
        return _snowman7;
    }

    public void crystalDestroyed(EndCrystalEntity crystal, BlockPos pos, DamageSource source) {
        PlayerEntity playerEntity = source.getAttacker() instanceof PlayerEntity ? (PlayerEntity)source.getAttacker() : this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, pos.getX(), pos.getY(), pos.getZ());
        if (crystal == this.connectedCrystal) {
            this.damagePart(this.partHead, DamageSource.explosion(playerEntity), 10.0f);
        }
        this.phaseManager.getCurrent().crystalDestroyed(crystal, pos, source, playerEntity);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (PHASE_TYPE.equals(data) && this.world.isClient) {
            this.phaseManager.setPhase(PhaseType.getFromId(this.getDataTracker().get(PHASE_TYPE)));
        }
        super.onTrackedDataSet(data);
    }

    public PhaseManager getPhaseManager() {
        return this.phaseManager;
    }

    @Nullable
    public EnderDragonFight getFight() {
        return this.fight;
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }
}

