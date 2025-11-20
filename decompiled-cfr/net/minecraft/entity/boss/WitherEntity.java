/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.boss;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class WitherEntity
extends HostileEntity
implements SkinOverlayOwner,
RangedAttackMob {
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_1 = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_2 = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_3 = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final List<TrackedData<Integer>> TRACKED_ENTITY_IDS = ImmutableList.of(TRACKED_ENTITY_ID_1, TRACKED_ENTITY_ID_2, TRACKED_ENTITY_ID_3);
    private static final TrackedData<Integer> INVUL_TIMER = DataTracker.registerData(WitherEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final float[] sideHeadPitches = new float[2];
    private final float[] sideHeadYaws = new float[2];
    private final float[] prevSideHeadPitches = new float[2];
    private final float[] prevSideHeadYaws = new float[2];
    private final int[] field_7091 = new int[2];
    private final int[] field_7092 = new int[2];
    private int field_7082;
    private final ServerBossBar bossBar = (ServerBossBar)new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS).setDarkenSky(true);
    private static final Predicate<LivingEntity> CAN_ATTACK_PREDICATE = livingEntity -> livingEntity.getGroup() != EntityGroup.UNDEAD && livingEntity.isMobOrPlayer();
    private static final TargetPredicate HEAD_TARGET_PREDICATE = new TargetPredicate().setBaseMaxDistance(20.0).setPredicate(CAN_ATTACK_PREDICATE);

    public WitherEntity(EntityType<? extends WitherEntity> entityType, World world) {
        super((EntityType<? extends HostileEntity>)entityType, world);
        this.setHealth(this.getMaxHealth());
        this.getNavigation().setCanSwim(true);
        this.experiencePoints = 50;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new DescendAtHalfHealthGoal());
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 40, 20.0f));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal<MobEntity>(this, MobEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TRACKED_ENTITY_ID_1, 0);
        this.dataTracker.startTracking(TRACKED_ENTITY_ID_2, 0);
        this.dataTracker.startTracking(TRACKED_ENTITY_ID_3, 0);
        this.dataTracker.startTracking(INVUL_TIMER, 0);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Invul", this.getInvulnerableTimer());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setInvulTimer(tag.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_DEATH;
    }

    @Override
    public void tickMovement() {
        int n;
        Vec3d _snowman2 = this.getVelocity().multiply(1.0, 0.6, 1.0);
        if (!this.world.isClient && this.getTrackedEntityId(0) > 0 && (entity = this.world.getEntityById(this.getTrackedEntityId(0))) != null) {
            Entity entity;
            double d = _snowman2.y;
            if (this.getY() < entity.getY() || !this.shouldRenderOverlay() && this.getY() < entity.getY() + 5.0) {
                d = Math.max(0.0, d);
                d += 0.3 - d * (double)0.6f;
            }
            _snowman2 = new Vec3d(_snowman2.x, d, _snowman2.z);
            Vec3d _snowman3 = new Vec3d(entity.getX() - this.getX(), 0.0, entity.getZ() - this.getZ());
            if (WitherEntity.squaredHorizontalLength(_snowman3) > 9.0) {
                Vec3d vec3d = _snowman3.normalize();
                _snowman2 = _snowman2.add(vec3d.x * 0.3 - _snowman2.x * 0.6, 0.0, vec3d.z * 0.3 - _snowman2.z * 0.6);
            }
        }
        this.setVelocity(_snowman2);
        if (WitherEntity.squaredHorizontalLength(_snowman2) > 0.05) {
            this.yaw = (float)MathHelper.atan2(_snowman2.z, _snowman2.x) * 57.295776f - 90.0f;
        }
        super.tickMovement();
        for (n = 0; n < 2; ++n) {
            this.prevSideHeadYaws[n] = this.sideHeadYaws[n];
            this.prevSideHeadPitches[n] = this.sideHeadPitches[n];
        }
        for (n = 0; n < 2; ++n) {
            _snowman = this.getTrackedEntityId(n + 1);
            Entity entity = null;
            if (_snowman > 0) {
                entity = this.world.getEntityById(_snowman);
            }
            if (entity != null) {
                double d = this.getHeadX(n + 1);
                _snowman = this.getHeadY(n + 1);
                _snowman = this.getHeadZ(n + 1);
                _snowman = entity.getX() - d;
                _snowman = entity.getEyeY() - _snowman;
                _snowman = entity.getZ() - _snowman;
                _snowman = MathHelper.sqrt(_snowman * _snowman + _snowman * _snowman);
                float _snowman4 = (float)(MathHelper.atan2(_snowman, _snowman) * 57.2957763671875) - 90.0f;
                float _snowman5 = (float)(-(MathHelper.atan2(_snowman, _snowman) * 57.2957763671875));
                this.sideHeadPitches[n] = this.getNextAngle(this.sideHeadPitches[n], _snowman5, 40.0f);
                this.sideHeadYaws[n] = this.getNextAngle(this.sideHeadYaws[n], _snowman4, 10.0f);
                continue;
            }
            this.sideHeadYaws[n] = this.getNextAngle(this.sideHeadYaws[n], this.bodyYaw, 10.0f);
        }
        n = this.shouldRenderOverlay() ? 1 : 0;
        for (i = 0; i < 3; ++i) {
            double d = this.getHeadX(i);
            _snowman = this.getHeadY(i);
            _snowman = this.getHeadZ(i);
            this.world.addParticle(ParticleTypes.SMOKE, d + this.random.nextGaussian() * (double)0.3f, _snowman + this.random.nextGaussian() * (double)0.3f, _snowman + this.random.nextGaussian() * (double)0.3f, 0.0, 0.0, 0.0);
            if (n == 0 || this.world.random.nextInt(4) != 0) continue;
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, d + this.random.nextGaussian() * (double)0.3f, _snowman + this.random.nextGaussian() * (double)0.3f, _snowman + this.random.nextGaussian() * (double)0.3f, 0.7f, 0.7f, 0.5);
        }
        if (this.getInvulnerableTimer() > 0) {
            for (int i = 0; i < 3; ++i) {
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + this.random.nextGaussian(), this.getY() + (double)(this.random.nextFloat() * 3.3f), this.getZ() + this.random.nextGaussian(), 0.7f, 0.7f, 0.9f);
            }
        }
    }

    @Override
    protected void mobTick() {
        int n;
        if (this.getInvulnerableTimer() > 0) {
            int n2 = this.getInvulnerableTimer() - 1;
            if (n2 <= 0) {
                Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
                this.world.createExplosion(this, this.getX(), this.getEyeY(), this.getZ(), 7.0f, false, destructionType);
                if (!this.isSilent()) {
                    this.world.syncGlobalEvent(1023, this.getBlockPos(), 0);
                }
            }
            this.setInvulTimer(n2);
            if (this.age % 10 == 0) {
                this.heal(10.0f);
            }
            return;
        }
        super.mobTick();
        block0: for (n = 1; n < 3; ++n) {
            Object object;
            if (this.age < this.field_7091[n - 1]) continue;
            this.field_7091[n - 1] = this.age + 10 + this.random.nextInt(10);
            if (this.world.getDifficulty() == Difficulty.NORMAL || this.world.getDifficulty() == Difficulty.HARD) {
                int n3 = n - 1;
                int n4 = this.field_7092[n3];
                this.field_7092[n3] = n4 + 1;
                if (n4 > 15) {
                    float f = 10.0f;
                    _snowman = 5.0f;
                    double _snowman2 = MathHelper.nextDouble(this.random, this.getX() - 10.0, this.getX() + 10.0);
                    double _snowman3 = MathHelper.nextDouble(this.random, this.getY() - 5.0, this.getY() + 5.0);
                    double _snowman4 = MathHelper.nextDouble(this.random, this.getZ() - 10.0, this.getZ() + 10.0);
                    this.method_6877(n + 1, _snowman2, _snowman3, _snowman4, true);
                    this.field_7092[n - 1] = 0;
                }
            }
            if ((_snowman = this.getTrackedEntityId(n)) > 0) {
                object = this.world.getEntityById(_snowman);
                if (object == null || !((Entity)object).isAlive() || this.squaredDistanceTo((Entity)object) > 900.0 || !this.canSee((Entity)object)) {
                    this.setTrackedEntityId(n, 0);
                    continue;
                }
                if (object instanceof PlayerEntity && ((PlayerEntity)object).abilities.invulnerable) {
                    this.setTrackedEntityId(n, 0);
                    continue;
                }
                this.method_6878(n + 1, (LivingEntity)object);
                this.field_7091[n - 1] = this.age + 40 + this.random.nextInt(20);
                this.field_7092[n - 1] = 0;
                continue;
            }
            object = this.world.getTargets(LivingEntity.class, HEAD_TARGET_PREDICATE, this, this.getBoundingBox().expand(20.0, 8.0, 20.0));
            for (int i = 0; i < 10 && !object.isEmpty(); ++i) {
                LivingEntity livingEntity = (LivingEntity)object.get(this.random.nextInt(object.size()));
                if (livingEntity != this && livingEntity.isAlive() && this.canSee(livingEntity)) {
                    if (livingEntity instanceof PlayerEntity) {
                        if (((PlayerEntity)livingEntity).abilities.invulnerable) continue block0;
                        this.setTrackedEntityId(n, livingEntity.getEntityId());
                        continue block0;
                    }
                    this.setTrackedEntityId(n, livingEntity.getEntityId());
                    continue block0;
                }
                object.remove(livingEntity);
            }
        }
        if (this.getTarget() != null) {
            this.setTrackedEntityId(0, this.getTarget().getEntityId());
        } else {
            this.setTrackedEntityId(0, 0);
        }
        if (this.field_7082 > 0) {
            --this.field_7082;
            if (this.field_7082 == 0 && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                n = MathHelper.floor(this.getY());
                _snowman = MathHelper.floor(this.getX());
                _snowman = MathHelper.floor(this.getZ());
                boolean _snowman5 = false;
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    for (_snowman = -1; _snowman <= 1; ++_snowman) {
                        for (_snowman = 0; _snowman <= 3; ++_snowman) {
                            _snowman = _snowman + _snowman;
                            _snowman = n + _snowman;
                            _snowman = _snowman + _snowman;
                            BlockPos blockPos = new BlockPos(_snowman, _snowman, _snowman);
                            BlockState _snowman6 = this.world.getBlockState(blockPos);
                            if (!WitherEntity.canDestroy(_snowman6)) continue;
                            _snowman5 = this.world.breakBlock(blockPos, true, this) || _snowman5;
                        }
                    }
                }
                if (_snowman5) {
                    this.world.syncWorldEvent(null, 1022, this.getBlockPos(), 0);
                }
            }
        }
        if (this.age % 20 == 0) {
            this.heal(1.0f);
        }
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    public static boolean canDestroy(BlockState block) {
        return !block.isAir() && !BlockTags.WITHER_IMMUNE.contains(block.getBlock());
    }

    public void method_6885() {
        this.setInvulTimer(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }

    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    private double getHeadX(int headIndex) {
        if (headIndex <= 0) {
            return this.getX();
        }
        float f = (this.bodyYaw + (float)(180 * (headIndex - 1))) * ((float)Math.PI / 180);
        _snowman = MathHelper.cos(f);
        return this.getX() + (double)_snowman * 1.3;
    }

    private double getHeadY(int headIndex) {
        if (headIndex <= 0) {
            return this.getY() + 3.0;
        }
        return this.getY() + 2.2;
    }

    private double getHeadZ(int headIndex) {
        if (headIndex <= 0) {
            return this.getZ();
        }
        float f = (this.bodyYaw + (float)(180 * (headIndex - 1))) * ((float)Math.PI / 180);
        _snowman = MathHelper.sin(f);
        return this.getZ() + (double)_snowman * 1.3;
    }

    private float getNextAngle(float prevAngle, float desiredAngle, float maxDifference) {
        float f = MathHelper.wrapDegrees(desiredAngle - prevAngle);
        if (f > maxDifference) {
            f = maxDifference;
        }
        if (f < -maxDifference) {
            f = -maxDifference;
        }
        return prevAngle + f;
    }

    private void method_6878(int n, LivingEntity livingEntity) {
        this.method_6877(n, livingEntity.getX(), livingEntity.getY() + (double)livingEntity.getStandingEyeHeight() * 0.5, livingEntity.getZ(), n == 0 && this.random.nextFloat() < 0.001f);
    }

    private void method_6877(int headIndex, double d, double d2, double d3, boolean bl) {
        if (!this.isSilent()) {
            this.world.syncWorldEvent(null, 1024, this.getBlockPos(), 0);
        }
        double d4 = this.getHeadX(headIndex);
        _snowman = this.getHeadY(headIndex);
        _snowman = this.getHeadZ(headIndex);
        _snowman = d - d4;
        _snowman = d2 - _snowman;
        _snowman = d3 - _snowman;
        WitherSkullEntity _snowman2 = new WitherSkullEntity(this.world, this, _snowman, _snowman, _snowman);
        _snowman2.setOwner(this);
        if (bl) {
            _snowman2.setCharged(true);
        }
        _snowman2.setPos(d4, _snowman, _snowman);
        this.world.spawnEntity(_snowman2);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        this.method_6878(0, target);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity entity;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (source == DamageSource.DROWN || source.getAttacker() instanceof WitherEntity) {
            return false;
        }
        if (this.getInvulnerableTimer() > 0 && source != DamageSource.OUT_OF_WORLD) {
            return false;
        }
        if (this.shouldRenderOverlay() && (entity = source.getSource()) instanceof PersistentProjectileEntity) {
            return false;
        }
        entity = source.getAttacker();
        if (entity != null && !(entity instanceof PlayerEntity) && entity instanceof LivingEntity && ((LivingEntity)entity).getGroup() == this.getGroup()) {
            return false;
        }
        if (this.field_7082 <= 0) {
            this.field_7082 = 20;
        }
        int _snowman2 = 0;
        while (_snowman2 < this.field_7092.length) {
            int n = _snowman2++;
            this.field_7092[n] = this.field_7092[n] + 3;
        }
        return super.damage(source, amount);
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        ItemEntity itemEntity = this.dropItem(Items.NETHER_STAR);
        if (itemEntity != null) {
            itemEntity.setCovetedItem();
        }
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.remove();
            return;
        }
        this.despawnCounter = 0;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    public static DefaultAttributeContainer.Builder createWitherAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6f).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0).add(EntityAttributes.GENERIC_ARMOR, 4.0);
    }

    public float getHeadYaw(int headIndex) {
        return this.sideHeadYaws[headIndex];
    }

    public float getHeadPitch(int headIndex) {
        return this.sideHeadPitches[headIndex];
    }

    public int getInvulnerableTimer() {
        return this.dataTracker.get(INVUL_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.dataTracker.set(INVUL_TIMER, ticks);
    }

    public int getTrackedEntityId(int headIndex) {
        return this.dataTracker.get(TRACKED_ENTITY_IDS.get(headIndex));
    }

    public void setTrackedEntityId(int headIndex, int id) {
        this.dataTracker.set(TRACKED_ENTITY_IDS.get(headIndex), id);
    }

    @Override
    public boolean shouldRenderOverlay() {
        return this.getHealth() <= this.getMaxHealth() / 2.0f;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == StatusEffects.WITHER) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    class DescendAtHalfHealthGoal
    extends Goal {
        public DescendAtHalfHealthGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return WitherEntity.this.getInvulnerableTimer() > 0;
        }
    }
}

