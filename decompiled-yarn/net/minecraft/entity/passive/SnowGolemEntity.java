package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SnowGolemEntity extends GolemEntity implements Shearable, RangedAttackMob {
   private static final TrackedData<Byte> SNOW_GOLEM_FLAGS = DataTracker.registerData(SnowGolemEntity.class, TrackedDataHandlerRegistry.BYTE);

   public SnowGolemEntity(EntityType<? extends SnowGolemEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.25, 20, 10.0F));
      this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0, 1.0000001E-5F));
      this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(4, new LookAroundGoal(this));
      this.targetSelector.add(1, new FollowTargetGoal<>(this, MobEntity.class, 10, true, false, _snowman -> _snowman instanceof Monster));
   }

   public static DefaultAttributeContainer.Builder createSnowGolemAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(SNOW_GOLEM_FLAGS, (byte)16);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putBoolean("Pumpkin", this.hasPumpkin());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("Pumpkin")) {
         this.setHasPumpkin(tag.getBoolean("Pumpkin"));
      }
   }

   @Override
   public boolean hurtByWater() {
      return true;
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (!this.world.isClient) {
         int _snowman = MathHelper.floor(this.getX());
         int _snowmanx = MathHelper.floor(this.getY());
         int _snowmanxx = MathHelper.floor(this.getZ());
         if (this.world.getBiome(new BlockPos(_snowman, 0, _snowmanxx)).getTemperature(new BlockPos(_snowman, _snowmanx, _snowmanxx)) > 1.0F) {
            this.damage(DamageSource.ON_FIRE, 1.0F);
         }

         if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return;
         }

         BlockState _snowmanxxx = Blocks.SNOW.getDefaultState();

         for (int _snowmanxxxx = 0; _snowmanxxxx < 4; _snowmanxxxx++) {
            _snowman = MathHelper.floor(this.getX() + (double)((float)(_snowmanxxxx % 2 * 2 - 1) * 0.25F));
            _snowmanx = MathHelper.floor(this.getY());
            _snowmanxx = MathHelper.floor(this.getZ() + (double)((float)(_snowmanxxxx / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos _snowmanxxxxx = new BlockPos(_snowman, _snowmanx, _snowmanxx);
            if (this.world.getBlockState(_snowmanxxxxx).isAir() && this.world.getBiome(_snowmanxxxxx).getTemperature(_snowmanxxxxx) < 0.8F && _snowmanxxx.canPlaceAt(this.world, _snowmanxxxxx)) {
               this.world.setBlockState(_snowmanxxxxx, _snowmanxxx);
            }
         }
      }
   }

   @Override
   public void attack(LivingEntity target, float pullProgress) {
      SnowballEntity _snowman = new SnowballEntity(this.world, this);
      double _snowmanx = target.getEyeY() - 1.1F;
      double _snowmanxx = target.getX() - this.getX();
      double _snowmanxxx = _snowmanx - _snowman.getY();
      double _snowmanxxxx = target.getZ() - this.getZ();
      float _snowmanxxxxx = MathHelper.sqrt(_snowmanxx * _snowmanxx + _snowmanxxxx * _snowmanxxxx) * 0.2F;
      _snowman.setVelocity(_snowmanxx, _snowmanxxx + (double)_snowmanxxxxx, _snowmanxxxx, 1.6F, 12.0F);
      this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(_snowman);
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 1.7F;
   }

   @Override
   protected ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() == Items.SHEARS && this.isShearable()) {
         this.sheared(SoundCategory.PLAYERS);
         if (!this.world.isClient) {
            _snowman.damage(1, player, _snowmanx -> _snowmanx.sendToolBreakStatus(hand));
         }

         return ActionResult.success(this.world.isClient);
      } else {
         return ActionResult.PASS;
      }
   }

   @Override
   public void sheared(SoundCategory shearedSoundCategory) {
      this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
      if (!this.world.isClient()) {
         this.setHasPumpkin(false);
         this.dropStack(new ItemStack(Items.CARVED_PUMPKIN), 1.7F);
      }
   }

   @Override
   public boolean isShearable() {
      return this.isAlive() && this.hasPumpkin();
   }

   public boolean hasPumpkin() {
      return (this.dataTracker.get(SNOW_GOLEM_FLAGS) & 16) != 0;
   }

   public void setHasPumpkin(boolean hasPumpkin) {
      byte _snowman = this.dataTracker.get(SNOW_GOLEM_FLAGS);
      if (hasPumpkin) {
         this.dataTracker.set(SNOW_GOLEM_FLAGS, (byte)(_snowman | 16));
      } else {
         this.dataTracker.set(SNOW_GOLEM_FLAGS, (byte)(_snowman & -17));
      }
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
   }

   @Override
   public Vec3d method_29919() {
      return new Vec3d(0.0, (double)(0.75F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
   }
}
