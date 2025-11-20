/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.mob;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class IllusionerEntity
extends SpellcastingIllagerEntity
implements RangedAttackMob {
    private int field_7296;
    private final Vec3d[][] field_7297;

    public IllusionerEntity(EntityType<? extends IllusionerEntity> entityType, World world) {
        super((EntityType<? extends SpellcastingIllagerEntity>)entityType, world);
        this.experiencePoints = 5;
        this.field_7297 = new Vec3d[2][4];
        for (int i = 0; i < 4; ++i) {
            this.field_7297[0][i] = Vec3d.ZERO;
            this.field_7297[1][i] = Vec3d.ZERO;
        }
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SpellcastingIllagerEntity.LookAtTargetGoal(this));
        this.goalSelector.add(4, new GiveInvisibilityGoal());
        this.goalSelector.add(5, new BlindTargetGoal());
        this.goalSelector.add(6, new BowAttackGoal<IllusionerEntity>(this, 0.5, 20, 15.0f));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new FollowTargetGoal<MerchantEntity>((MobEntity)this, MerchantEntity.class, false).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new FollowTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, false).setMaxTimeWithoutVisibility(300));
    }

    public static DefaultAttributeContainer.Builder createIllusionerAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 18.0).add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public Box getVisibilityBoundingBox() {
        return this.getBoundingBox().expand(3.0, 0.0, 3.0);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.world.isClient && this.isInvisible()) {
            --this.field_7296;
            if (this.field_7296 < 0) {
                this.field_7296 = 0;
            }
            if (this.hurtTime == 1 || this.age % 1200 == 0) {
                int n;
                this.field_7296 = 3;
                float f = -6.0f;
                int _snowman2 = 13;
                for (n = 0; n < 4; ++n) {
                    this.field_7297[0][n] = this.field_7297[1][n];
                    this.field_7297[1][n] = new Vec3d((double)(-6.0f + (float)this.random.nextInt(13)) * 0.5, Math.max(0, this.random.nextInt(6) - 4), (double)(-6.0f + (float)this.random.nextInt(13)) * 0.5);
                }
                for (n = 0; n < 16; ++n) {
                    this.world.addParticle(ParticleTypes.CLOUD, this.getParticleX(0.5), this.getRandomBodyY(), this.offsetZ(0.5), 0.0, 0.0, 0.0);
                }
                this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE, this.getSoundCategory(), 1.0f, 1.0f, false);
            } else if (this.hurtTime == this.maxHurtTime - 1) {
                this.field_7296 = 3;
                for (int i = 0; i < 4; ++i) {
                    this.field_7297[0][i] = this.field_7297[1][i];
                    this.field_7297[1][i] = new Vec3d(0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    public Vec3d[] method_7065(float f) {
        if (this.field_7296 <= 0) {
            return this.field_7297[1];
        }
        double d = ((float)this.field_7296 - f) / 3.0f;
        d = Math.pow(d, 0.25);
        Vec3d[] _snowman2 = new Vec3d[4];
        for (int i = 0; i < 4; ++i) {
            _snowman2[i] = this.field_7297[1][i].multiply(1.0 - d).add(this.field_7297[0][i].multiply(d));
        }
        return _snowman2;
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == EntityGroup.ILLAGER) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ILLUSIONER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ILLUSIONER_HURT;
    }

    @Override
    protected SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL;
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getArrowType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity _snowman2 = ProjectileUtil.createArrowProjectile(this, itemStack, pullProgress);
        double _snowman3 = target.getX() - this.getX();
        double _snowman4 = target.getBodyY(0.3333333333333333) - _snowman2.getY();
        double _snowman5 = target.getZ() - this.getZ();
        double _snowman6 = MathHelper.sqrt(_snowman3 * _snowman3 + _snowman5 * _snowman5);
        _snowman2.setVelocity(_snowman3, _snowman4 + _snowman6 * (double)0.2f, _snowman5, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.world.spawnEntity(_snowman2);
    }

    @Override
    public IllagerEntity.State getState() {
        if (this.isSpellcasting()) {
            return IllagerEntity.State.SPELLCASTING;
        }
        if (this.isAttacking()) {
            return IllagerEntity.State.BOW_AND_ARROW;
        }
        return IllagerEntity.State.CROSSED;
    }

    class BlindTargetGoal
    extends SpellcastingIllagerEntity.CastSpellGoal {
        private int targetId;

        private BlindTargetGoal() {
            super(IllusionerEntity.this);
        }

        @Override
        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            }
            if (IllusionerEntity.this.getTarget() == null) {
                return false;
            }
            if (IllusionerEntity.this.getTarget().getEntityId() == this.targetId) {
                return false;
            }
            return IllusionerEntity.this.world.getLocalDifficulty(IllusionerEntity.this.getBlockPos()).isHarderThan(Difficulty.NORMAL.ordinal());
        }

        @Override
        public void start() {
            super.start();
            this.targetId = IllusionerEntity.this.getTarget().getEntityId();
        }

        @Override
        protected int getSpellTicks() {
            return 20;
        }

        @Override
        protected int startTimeDelay() {
            return 180;
        }

        @Override
        protected void castSpell() {
            IllusionerEntity.this.getTarget().addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 400));
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.BLINDNESS;
        }
    }

    class GiveInvisibilityGoal
    extends SpellcastingIllagerEntity.CastSpellGoal {
        private GiveInvisibilityGoal() {
            super(IllusionerEntity.this);
        }

        @Override
        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            }
            return !IllusionerEntity.this.hasStatusEffect(StatusEffects.INVISIBILITY);
        }

        @Override
        protected int getSpellTicks() {
            return 20;
        }

        @Override
        protected int startTimeDelay() {
            return 340;
        }

        @Override
        protected void castSpell() {
            IllusionerEntity.this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 1200));
        }

        @Override
        @Nullable
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.DISAPPEAR;
        }
    }
}

