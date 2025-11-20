/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.mob;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public abstract class AbstractSkeletonEntity
extends HostileEntity
implements RangedAttackMob {
    private final BowAttackGoal<AbstractSkeletonEntity> bowAttackGoal = new BowAttackGoal<AbstractSkeletonEntity>(this, 1.0, 20, 15.0f);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, this, 1.2, false){
        final /* synthetic */ AbstractSkeletonEntity field_7223;
        {
            this.field_7223 = abstractSkeletonEntity;
            super(pathAwareEntity, d, bl);
        }

        public void stop() {
            super.stop();
            this.field_7223.setAttacking(false);
        }

        public void start() {
            super.start();
            this.field_7223.setAttacking(true);
        }
    };

    protected AbstractSkeletonEntity(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super((EntityType<? extends HostileEntity>)entityType, world);
        this.updateAttackType();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.0));
        this.goalSelector.add(3, new FleeEntityGoal<WolfEntity>(this, WolfEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal<TurtleEntity>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    }

    public static DefaultAttributeContainer.Builder createAbstractSkeletonAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15f, 1.0f);
    }

    abstract SoundEvent getStepSound();

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public void tickMovement() {
        boolean bl = this.isAffectedByDaylight();
        if (bl) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
            if (!itemStack.isEmpty()) {
                if (itemStack.isDamageable()) {
                    itemStack.setDamage(itemStack.getDamage() + this.random.nextInt(2));
                    if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                        this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                        this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }
                bl = false;
            }
            if (bl) {
                this.setOnFireFor(8);
            }
        }
        super.tickMovement();
    }

    @Override
    public void tickRiding() {
        super.tickRiding();
        if (this.getVehicle() instanceof PathAwareEntity) {
            PathAwareEntity pathAwareEntity = (PathAwareEntity)this.getVehicle();
            this.bodyYaw = pathAwareEntity.bodyYaw;
        }
    }

    @Override
    protected void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
        this.initEquipment(difficulty);
        this.updateEnchantments(difficulty);
        this.updateAttackType();
        this.setCanPickUpLoot(this.random.nextFloat() < 0.55f * difficulty.getClampedLocalDifficulty());
        if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            LocalDate localDate = LocalDate.now();
            int _snowman2 = localDate.get(ChronoField.DAY_OF_MONTH);
            int _snowman3 = localDate.get(ChronoField.MONTH_OF_YEAR);
            if (_snowman3 == 10 && _snowman2 == 31 && this.random.nextFloat() < 0.25f) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1f ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 0.0f;
            }
        }
        return entityData;
    }

    public void updateAttackType() {
        if (this.world == null || this.world.isClient) {
            return;
        }
        this.goalSelector.remove(this.meleeAttackGoal);
        this.goalSelector.remove(this.bowAttackGoal);
        ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
        if (itemStack.getItem() == Items.BOW) {
            int n = 20;
            if (this.world.getDifficulty() != Difficulty.HARD) {
                n = 40;
            }
            this.bowAttackGoal.setAttackInterval(n);
            this.goalSelector.add(4, this.bowAttackGoal);
        } else {
            this.goalSelector.add(4, this.meleeAttackGoal);
        }
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getArrowType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity _snowman2 = this.createArrowProjectile(itemStack, pullProgress);
        double _snowman3 = target.getX() - this.getX();
        double _snowman4 = target.getBodyY(0.3333333333333333) - _snowman2.getY();
        double _snowman5 = target.getZ() - this.getZ();
        double _snowman6 = MathHelper.sqrt(_snowman3 * _snowman3 + _snowman5 * _snowman5);
        _snowman2.setVelocity(_snowman3, _snowman4 + _snowman6 * (double)0.2f, _snowman5, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.world.spawnEntity(_snowman2);
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
    }

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return weapon == Items.BOW;
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.updateAttackType();
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
        if (!this.world.isClient) {
            this.updateAttackType();
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.74f;
    }

    @Override
    public double getHeightOffset() {
        return -0.6;
    }
}

