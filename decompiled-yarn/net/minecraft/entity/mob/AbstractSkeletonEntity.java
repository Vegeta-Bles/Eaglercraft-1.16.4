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

public abstract class AbstractSkeletonEntity extends HostileEntity implements RangedAttackMob {
   private final BowAttackGoal<AbstractSkeletonEntity> bowAttackGoal = new BowAttackGoal<>(this, 1.0, 20, 15.0F);
   private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
      @Override
      public void stop() {
         super.stop();
         AbstractSkeletonEntity.this.setAttacking(false);
      }

      @Override
      public void start() {
         super.start();
         AbstractSkeletonEntity.this.setAttacking(true);
      }
   };

   protected AbstractSkeletonEntity(EntityType<? extends AbstractSkeletonEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.updateAttackType();
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(2, new AvoidSunlightGoal(this));
      this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.0));
      this.goalSelector.add(3, new FleeEntityGoal<>(this, WolfEntity.class, 6.0F, 1.0, 1.2));
      this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
      this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(6, new LookAroundGoal(this));
      this.targetSelector.add(1, new RevengeGoal(this));
      this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
   }

   public static DefaultAttributeContainer.Builder createAbstractSkeletonAttributes() {
      return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(this.getStepSound(), 0.15F, 1.0F);
   }

   abstract SoundEvent getStepSound();

   @Override
   public EntityGroup getGroup() {
      return EntityGroup.UNDEAD;
   }

   @Override
   public void tickMovement() {
      boolean _snowman = this.isAffectedByDaylight();
      if (_snowman) {
         ItemStack _snowmanx = this.getEquippedStack(EquipmentSlot.HEAD);
         if (!_snowmanx.isEmpty()) {
            if (_snowmanx.isDamageable()) {
               _snowmanx.setDamage(_snowmanx.getDamage() + this.random.nextInt(2));
               if (_snowmanx.getDamage() >= _snowmanx.getMaxDamage()) {
                  this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                  this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
               }
            }

            _snowman = false;
         }

         if (_snowman) {
            this.setOnFireFor(8);
         }
      }

      super.tickMovement();
   }

   @Override
   public void tickRiding() {
      super.tickRiding();
      if (this.getVehicle() instanceof PathAwareEntity) {
         PathAwareEntity _snowman = (PathAwareEntity)this.getVehicle();
         this.bodyYaw = _snowman.bodyYaw;
      }
   }

   @Override
   protected void initEquipment(LocalDifficulty difficulty) {
      super.initEquipment(difficulty);
      this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
      this.initEquipment(difficulty);
      this.updateEnchantments(difficulty);
      this.updateAttackType();
      this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficulty.getClampedLocalDifficulty());
      if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
         LocalDate _snowman = LocalDate.now();
         int _snowmanx = _snowman.get(ChronoField.DAY_OF_MONTH);
         int _snowmanxx = _snowman.get(ChronoField.MONTH_OF_YEAR);
         if (_snowmanxx == 10 && _snowmanx == 31 && this.random.nextFloat() < 0.25F) {
            this.equipStack(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
            this.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 0.0F;
         }
      }

      return entityData;
   }

   public void updateAttackType() {
      if (this.world != null && !this.world.isClient) {
         this.goalSelector.remove(this.meleeAttackGoal);
         this.goalSelector.remove(this.bowAttackGoal);
         ItemStack _snowman = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
         if (_snowman.getItem() == Items.BOW) {
            int _snowmanx = 20;
            if (this.world.getDifficulty() != Difficulty.HARD) {
               _snowmanx = 40;
            }

            this.bowAttackGoal.setAttackInterval(_snowmanx);
            this.goalSelector.add(4, this.bowAttackGoal);
         } else {
            this.goalSelector.add(4, this.meleeAttackGoal);
         }
      }
   }

   @Override
   public void attack(LivingEntity target, float pullProgress) {
      ItemStack _snowman = this.getArrowType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
      PersistentProjectileEntity _snowmanx = this.createArrowProjectile(_snowman, pullProgress);
      double _snowmanxx = target.getX() - this.getX();
      double _snowmanxxx = target.getBodyY(0.3333333333333333) - _snowmanx.getY();
      double _snowmanxxxx = target.getZ() - this.getZ();
      double _snowmanxxxxx = (double)MathHelper.sqrt(_snowmanxx * _snowmanxx + _snowmanxxxx * _snowmanxxxx);
      _snowmanx.setVelocity(_snowmanxx, _snowmanxxx + _snowmanxxxxx * 0.2F, _snowmanxxxx, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
      this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(_snowmanx);
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
      return 1.74F;
   }

   @Override
   public double getHeightOffset() {
      return -0.6;
   }
}
