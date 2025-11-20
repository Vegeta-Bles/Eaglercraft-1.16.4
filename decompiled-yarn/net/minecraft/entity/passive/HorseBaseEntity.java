package net.minecraft.entity.passive;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HorseBondWithPlayerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public abstract class HorseBaseEntity extends AnimalEntity implements InventoryChangedListener, JumpingMount, Saddleable {
   private static final Predicate<LivingEntity> IS_BRED_HORSE = _snowman -> _snowman instanceof HorseBaseEntity && ((HorseBaseEntity)_snowman).isBred();
   private static final TargetPredicate PARENT_HORSE_PREDICATE = new TargetPredicate()
      .setBaseMaxDistance(16.0)
      .includeInvulnerable()
      .includeTeammates()
      .includeHidden()
      .setPredicate(IS_BRED_HORSE);
   private static final Ingredient field_25374 = Ingredient.ofItems(
      Items.WHEAT, Items.SUGAR, Blocks.HAY_BLOCK.asItem(), Items.APPLE, Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE
   );
   private static final TrackedData<Byte> HORSE_FLAGS = DataTracker.registerData(HorseBaseEntity.class, TrackedDataHandlerRegistry.BYTE);
   private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(HorseBaseEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
   private int eatingGrassTicks;
   private int eatingTicks;
   private int angryTicks;
   public int tailWagTicks;
   public int field_6958;
   protected boolean inAir;
   protected SimpleInventory items;
   protected int temper;
   protected float jumpStrength;
   private boolean jumping;
   private float eatingGrassAnimationProgress;
   private float lastEatingGrassAnimationProgress;
   private float angryAnimationProgress;
   private float lastAngryAnimationProgress;
   private float eatingAnimationProgress;
   private float lastEatingAnimationProgress;
   protected boolean playExtraHorseSounds = true;
   protected int soundTicks;

   protected HorseBaseEntity(EntityType<? extends HorseBaseEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.stepHeight = 1.0F;
      this.onChestedStatusChanged();
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2));
      this.goalSelector.add(1, new HorseBondWithPlayerGoal(this, 1.2));
      this.goalSelector.add(2, new AnimalMateGoal(this, 1.0, HorseBaseEntity.class));
      this.goalSelector.add(4, new FollowParentGoal(this, 1.0));
      this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7));
      this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
      this.initCustomGoals();
   }

   protected void initCustomGoals() {
      this.goalSelector.add(0, new SwimGoal(this));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(HORSE_FLAGS, (byte)0);
      this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
   }

   protected boolean getHorseFlag(int bitmask) {
      return (this.dataTracker.get(HORSE_FLAGS) & bitmask) != 0;
   }

   protected void setHorseFlag(int bitmask, boolean flag) {
      byte _snowman = this.dataTracker.get(HORSE_FLAGS);
      if (flag) {
         this.dataTracker.set(HORSE_FLAGS, (byte)(_snowman | bitmask));
      } else {
         this.dataTracker.set(HORSE_FLAGS, (byte)(_snowman & ~bitmask));
      }
   }

   public boolean isTame() {
      return this.getHorseFlag(2);
   }

   @Nullable
   public UUID getOwnerUuid() {
      return this.dataTracker.get(OWNER_UUID).orElse(null);
   }

   public void setOwnerUuid(@Nullable UUID uuid) {
      this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
   }

   public boolean isInAir() {
      return this.inAir;
   }

   public void setTame(boolean tame) {
      this.setHorseFlag(2, tame);
   }

   public void setInAir(boolean inAir) {
      this.inAir = inAir;
   }

   @Override
   protected void updateForLeashLength(float leashLength) {
      if (leashLength > 6.0F && this.isEatingGrass()) {
         this.setEatingGrass(false);
      }
   }

   public boolean isEatingGrass() {
      return this.getHorseFlag(16);
   }

   public boolean isAngry() {
      return this.getHorseFlag(32);
   }

   public boolean isBred() {
      return this.getHorseFlag(8);
   }

   public void setBred(boolean bred) {
      this.setHorseFlag(8, bred);
   }

   @Override
   public boolean canBeSaddled() {
      return this.isAlive() && !this.isBaby() && this.isTame();
   }

   @Override
   public void saddle(@Nullable SoundCategory sound) {
      this.items.setStack(0, new ItemStack(Items.SADDLE));
      if (sound != null) {
         this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_HORSE_SADDLE, sound, 0.5F, 1.0F);
      }
   }

   @Override
   public boolean isSaddled() {
      return this.getHorseFlag(4);
   }

   public int getTemper() {
      return this.temper;
   }

   public void setTemper(int temper) {
      this.temper = temper;
   }

   public int addTemper(int difference) {
      int _snowman = MathHelper.clamp(this.getTemper() + difference, 0, this.getMaxTemper());
      this.setTemper(_snowman);
      return _snowman;
   }

   @Override
   public boolean isPushable() {
      return !this.hasPassengers();
   }

   private void playEatingAnimation() {
      this.setEating();
      if (!this.isSilent()) {
         SoundEvent _snowman = this.getEatSound();
         if (_snowman != null) {
            this.world
               .playSound(
                  null,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  _snowman,
                  this.getSoundCategory(),
                  1.0F,
                  1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
               );
         }
      }
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      if (fallDistance > 1.0F) {
         this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4F, 1.0F);
      }

      int _snowman = this.computeFallDamage(fallDistance, damageMultiplier);
      if (_snowman <= 0) {
         return false;
      } else {
         this.damage(DamageSource.FALL, (float)_snowman);
         if (this.hasPassengers()) {
            for (Entity _snowmanx : this.getPassengersDeep()) {
               _snowmanx.damage(DamageSource.FALL, (float)_snowman);
            }
         }

         this.playBlockFallSound();
         return true;
      }
   }

   @Override
   protected int computeFallDamage(float fallDistance, float damageMultiplier) {
      return MathHelper.ceil((fallDistance * 0.5F - 3.0F) * damageMultiplier);
   }

   protected int getInventorySize() {
      return 2;
   }

   protected void onChestedStatusChanged() {
      SimpleInventory _snowman = this.items;
      this.items = new SimpleInventory(this.getInventorySize());
      if (_snowman != null) {
         _snowman.removeListener(this);
         int _snowmanx = Math.min(_snowman.size(), this.items.size());

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
            ItemStack _snowmanxxx = _snowman.getStack(_snowmanxx);
            if (!_snowmanxxx.isEmpty()) {
               this.items.setStack(_snowmanxx, _snowmanxxx.copy());
            }
         }
      }

      this.items.addListener(this);
      this.updateSaddle();
   }

   protected void updateSaddle() {
      if (!this.world.isClient) {
         this.setHorseFlag(4, !this.items.getStack(0).isEmpty());
      }
   }

   @Override
   public void onInventoryChanged(Inventory sender) {
      boolean _snowman = this.isSaddled();
      this.updateSaddle();
      if (this.age > 20 && !_snowman && this.isSaddled()) {
         this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
      }
   }

   public double getJumpStrength() {
      return this.getAttributeValue(EntityAttributes.HORSE_JUMP_STRENGTH);
   }

   @Nullable
   protected SoundEvent getEatSound() {
      return null;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return null;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      if (this.random.nextInt(3) == 0) {
         this.updateAnger();
      }

      return null;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.random.nextInt(10) == 0 && !this.isImmobile()) {
         this.updateAnger();
      }

      return null;
   }

   @Nullable
   protected SoundEvent getAngrySound() {
      this.updateAnger();
      return null;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      if (!state.getMaterial().isLiquid()) {
         BlockState _snowman = this.world.getBlockState(pos.up());
         BlockSoundGroup _snowmanx = state.getSoundGroup();
         if (_snowman.isOf(Blocks.SNOW)) {
            _snowmanx = _snowman.getSoundGroup();
         }

         if (this.hasPassengers() && this.playExtraHorseSounds) {
            this.soundTicks++;
            if (this.soundTicks > 5 && this.soundTicks % 3 == 0) {
               this.playWalkSound(_snowmanx);
            } else if (this.soundTicks <= 5) {
               this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, _snowmanx.getVolume() * 0.15F, _snowmanx.getPitch());
            }
         } else if (_snowmanx == BlockSoundGroup.WOOD) {
            this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, _snowmanx.getVolume() * 0.15F, _snowmanx.getPitch());
         } else {
            this.playSound(SoundEvents.ENTITY_HORSE_STEP, _snowmanx.getVolume() * 0.15F, _snowmanx.getPitch());
         }
      }
   }

   protected void playWalkSound(BlockSoundGroup group) {
      this.playSound(SoundEvents.ENTITY_HORSE_GALLOP, group.getVolume() * 0.15F, group.getPitch());
   }

   public static DefaultAttributeContainer.Builder createBaseHorseAttributes() {
      return MobEntity.createMobAttributes()
         .add(EntityAttributes.HORSE_JUMP_STRENGTH)
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 53.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225F);
   }

   @Override
   public int getLimitPerChunk() {
      return 6;
   }

   public int getMaxTemper() {
      return 100;
   }

   @Override
   protected float getSoundVolume() {
      return 0.8F;
   }

   @Override
   public int getMinAmbientSoundDelay() {
      return 400;
   }

   public void openInventory(PlayerEntity player) {
      if (!this.world.isClient && (!this.hasPassengers() || this.hasPassenger(player)) && this.isTame()) {
         player.openHorseInventory(this, this.items);
      }
   }

   public ActionResult method_30009(PlayerEntity _snowman, ItemStack _snowman) {
      boolean _snowmanxx = this.receiveFood(_snowman, _snowman);
      if (!_snowman.abilities.creativeMode) {
         _snowman.decrement(1);
      }

      if (this.world.isClient) {
         return ActionResult.CONSUME;
      } else {
         return _snowmanxx ? ActionResult.SUCCESS : ActionResult.PASS;
      }
   }

   protected boolean receiveFood(PlayerEntity player, ItemStack item) {
      boolean _snowman = false;
      float _snowmanx = 0.0F;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;
      Item _snowmanxxxx = item.getItem();
      if (_snowmanxxxx == Items.WHEAT) {
         _snowmanx = 2.0F;
         _snowmanxx = 20;
         _snowmanxxx = 3;
      } else if (_snowmanxxxx == Items.SUGAR) {
         _snowmanx = 1.0F;
         _snowmanxx = 30;
         _snowmanxxx = 3;
      } else if (_snowmanxxxx == Blocks.HAY_BLOCK.asItem()) {
         _snowmanx = 20.0F;
         _snowmanxx = 180;
      } else if (_snowmanxxxx == Items.APPLE) {
         _snowmanx = 3.0F;
         _snowmanxx = 60;
         _snowmanxxx = 3;
      } else if (_snowmanxxxx == Items.GOLDEN_CARROT) {
         _snowmanx = 4.0F;
         _snowmanxx = 60;
         _snowmanxxx = 5;
         if (!this.world.isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
            _snowman = true;
            this.lovePlayer(player);
         }
      } else if (_snowmanxxxx == Items.GOLDEN_APPLE || _snowmanxxxx == Items.ENCHANTED_GOLDEN_APPLE) {
         _snowmanx = 10.0F;
         _snowmanxx = 240;
         _snowmanxxx = 10;
         if (!this.world.isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
            _snowman = true;
            this.lovePlayer(player);
         }
      }

      if (this.getHealth() < this.getMaxHealth() && _snowmanx > 0.0F) {
         this.heal(_snowmanx);
         _snowman = true;
      }

      if (this.isBaby() && _snowmanxx > 0) {
         this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
         if (!this.world.isClient) {
            this.growUp(_snowmanxx);
         }

         _snowman = true;
      }

      if (_snowmanxxx > 0 && (_snowman || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
         _snowman = true;
         if (!this.world.isClient) {
            this.addTemper(_snowmanxxx);
         }
      }

      if (_snowman) {
         this.playEatingAnimation();
      }

      return _snowman;
   }

   protected void putPlayerOnBack(PlayerEntity player) {
      this.setEatingGrass(false);
      this.setAngry(false);
      if (!this.world.isClient) {
         player.yaw = this.yaw;
         player.pitch = this.pitch;
         player.startRiding(this);
      }
   }

   @Override
   protected boolean isImmobile() {
      return super.isImmobile() && this.hasPassengers() && this.isSaddled() || this.isEatingGrass() || this.isAngry();
   }

   @Override
   public boolean isBreedingItem(ItemStack stack) {
      return field_25374.test(stack);
   }

   private void wagTail() {
      this.tailWagTicks = 1;
   }

   @Override
   protected void dropInventory() {
      super.dropInventory();
      if (this.items != null) {
         for (int _snowman = 0; _snowman < this.items.size(); _snowman++) {
            ItemStack _snowmanx = this.items.getStack(_snowman);
            if (!_snowmanx.isEmpty() && !EnchantmentHelper.hasVanishingCurse(_snowmanx)) {
               this.dropStack(_snowmanx);
            }
         }
      }
   }

   @Override
   public void tickMovement() {
      if (this.random.nextInt(200) == 0) {
         this.wagTail();
      }

      super.tickMovement();
      if (!this.world.isClient && this.isAlive()) {
         if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
            this.heal(1.0F);
         }

         if (this.eatsGrass()) {
            if (!this.isEatingGrass()
               && !this.hasPassengers()
               && this.random.nextInt(300) == 0
               && this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)) {
               this.setEatingGrass(true);
            }

            if (this.isEatingGrass() && ++this.eatingGrassTicks > 50) {
               this.eatingGrassTicks = 0;
               this.setEatingGrass(false);
            }
         }

         this.walkToParent();
      }
   }

   protected void walkToParent() {
      if (this.isBred() && this.isBaby() && !this.isEatingGrass()) {
         LivingEntity _snowman = this.world
            .getClosestEntity(HorseBaseEntity.class, PARENT_HORSE_PREDICATE, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().expand(16.0));
         if (_snowman != null && this.squaredDistanceTo(_snowman) > 4.0) {
            this.navigation.findPathTo(_snowman, 0);
         }
      }
   }

   public boolean eatsGrass() {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.eatingTicks > 0 && ++this.eatingTicks > 30) {
         this.eatingTicks = 0;
         this.setHorseFlag(64, false);
      }

      if ((this.isLogicalSideForUpdatingMovement() || this.canMoveVoluntarily()) && this.angryTicks > 0 && ++this.angryTicks > 20) {
         this.angryTicks = 0;
         this.setAngry(false);
      }

      if (this.tailWagTicks > 0 && ++this.tailWagTicks > 8) {
         this.tailWagTicks = 0;
      }

      if (this.field_6958 > 0) {
         this.field_6958++;
         if (this.field_6958 > 300) {
            this.field_6958 = 0;
         }
      }

      this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress;
      if (this.isEatingGrass()) {
         this.eatingGrassAnimationProgress = this.eatingGrassAnimationProgress + (1.0F - this.eatingGrassAnimationProgress) * 0.4F + 0.05F;
         if (this.eatingGrassAnimationProgress > 1.0F) {
            this.eatingGrassAnimationProgress = 1.0F;
         }
      } else {
         this.eatingGrassAnimationProgress = this.eatingGrassAnimationProgress + ((0.0F - this.eatingGrassAnimationProgress) * 0.4F - 0.05F);
         if (this.eatingGrassAnimationProgress < 0.0F) {
            this.eatingGrassAnimationProgress = 0.0F;
         }
      }

      this.lastAngryAnimationProgress = this.angryAnimationProgress;
      if (this.isAngry()) {
         this.eatingGrassAnimationProgress = 0.0F;
         this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress;
         this.angryAnimationProgress = this.angryAnimationProgress + (1.0F - this.angryAnimationProgress) * 0.4F + 0.05F;
         if (this.angryAnimationProgress > 1.0F) {
            this.angryAnimationProgress = 1.0F;
         }
      } else {
         this.jumping = false;
         this.angryAnimationProgress = this.angryAnimationProgress
            + ((0.8F * this.angryAnimationProgress * this.angryAnimationProgress * this.angryAnimationProgress - this.angryAnimationProgress) * 0.6F - 0.05F);
         if (this.angryAnimationProgress < 0.0F) {
            this.angryAnimationProgress = 0.0F;
         }
      }

      this.lastEatingAnimationProgress = this.eatingAnimationProgress;
      if (this.getHorseFlag(64)) {
         this.eatingAnimationProgress = this.eatingAnimationProgress + (1.0F - this.eatingAnimationProgress) * 0.7F + 0.05F;
         if (this.eatingAnimationProgress > 1.0F) {
            this.eatingAnimationProgress = 1.0F;
         }
      } else {
         this.eatingAnimationProgress = this.eatingAnimationProgress + ((0.0F - this.eatingAnimationProgress) * 0.7F - 0.05F);
         if (this.eatingAnimationProgress < 0.0F) {
            this.eatingAnimationProgress = 0.0F;
         }
      }
   }

   private void setEating() {
      if (!this.world.isClient) {
         this.eatingTicks = 1;
         this.setHorseFlag(64, true);
      }
   }

   public void setEatingGrass(boolean eatingGrass) {
      this.setHorseFlag(16, eatingGrass);
   }

   public void setAngry(boolean angry) {
      if (angry) {
         this.setEatingGrass(false);
      }

      this.setHorseFlag(32, angry);
   }

   private void updateAnger() {
      if (this.isLogicalSideForUpdatingMovement() || this.canMoveVoluntarily()) {
         this.angryTicks = 1;
         this.setAngry(true);
      }
   }

   public void playAngrySound() {
      if (!this.isAngry()) {
         this.updateAnger();
         SoundEvent _snowman = this.getAngrySound();
         if (_snowman != null) {
            this.playSound(_snowman, this.getSoundVolume(), this.getSoundPitch());
         }
      }
   }

   public boolean bondWithPlayer(PlayerEntity player) {
      this.setOwnerUuid(player.getUuid());
      this.setTame(true);
      if (player instanceof ServerPlayerEntity) {
         Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
      }

      this.world.sendEntityStatus(this, (byte)7);
      return true;
   }

   @Override
   public void travel(Vec3d movementInput) {
      if (this.isAlive()) {
         if (this.hasPassengers() && this.canBeControlledByRider() && this.isSaddled()) {
            LivingEntity _snowman = (LivingEntity)this.getPrimaryPassenger();
            this.yaw = _snowman.yaw;
            this.prevYaw = this.yaw;
            this.pitch = _snowman.pitch * 0.5F;
            this.setRotation(this.yaw, this.pitch);
            this.bodyYaw = this.yaw;
            this.headYaw = this.bodyYaw;
            float _snowmanx = _snowman.sidewaysSpeed * 0.5F;
            float _snowmanxx = _snowman.forwardSpeed;
            if (_snowmanxx <= 0.0F) {
               _snowmanxx *= 0.25F;
               this.soundTicks = 0;
            }

            if (this.onGround && this.jumpStrength == 0.0F && this.isAngry() && !this.jumping) {
               _snowmanx = 0.0F;
               _snowmanxx = 0.0F;
            }

            if (this.jumpStrength > 0.0F && !this.isInAir() && this.onGround) {
               double _snowmanxxx = this.getJumpStrength() * (double)this.jumpStrength * (double)this.getJumpVelocityMultiplier();
               double _snowmanxxxx;
               if (this.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
                  _snowmanxxxx = _snowmanxxx + (double)((float)(this.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
               } else {
                  _snowmanxxxx = _snowmanxxx;
               }

               Vec3d _snowmanxxxxx = this.getVelocity();
               this.setVelocity(_snowmanxxxxx.x, _snowmanxxxx, _snowmanxxxxx.z);
               this.setInAir(true);
               this.velocityDirty = true;
               if (_snowmanxx > 0.0F) {
                  float _snowmanxxxxxx = MathHelper.sin(this.yaw * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxx = MathHelper.cos(this.yaw * (float) (Math.PI / 180.0));
                  this.setVelocity(this.getVelocity().add((double)(-0.4F * _snowmanxxxxxx * this.jumpStrength), 0.0, (double)(0.4F * _snowmanxxxxxxx * this.jumpStrength)));
               }

               this.jumpStrength = 0.0F;
            }

            this.flyingSpeed = this.getMovementSpeed() * 0.1F;
            if (this.isLogicalSideForUpdatingMovement()) {
               this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
               super.travel(new Vec3d((double)_snowmanx, movementInput.y, (double)_snowmanxx));
            } else if (_snowman instanceof PlayerEntity) {
               this.setVelocity(Vec3d.ZERO);
            }

            if (this.onGround) {
               this.jumpStrength = 0.0F;
               this.setInAir(false);
            }

            this.method_29242(this, false);
         } else {
            this.flyingSpeed = 0.02F;
            super.travel(movementInput);
         }
      }
   }

   protected void playJumpSound() {
      this.playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4F, 1.0F);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putBoolean("EatingHaystack", this.isEatingGrass());
      tag.putBoolean("Bred", this.isBred());
      tag.putInt("Temper", this.getTemper());
      tag.putBoolean("Tame", this.isTame());
      if (this.getOwnerUuid() != null) {
         tag.putUuid("Owner", this.getOwnerUuid());
      }

      if (!this.items.getStack(0).isEmpty()) {
         tag.put("SaddleItem", this.items.getStack(0).toTag(new CompoundTag()));
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setEatingGrass(tag.getBoolean("EatingHaystack"));
      this.setBred(tag.getBoolean("Bred"));
      this.setTemper(tag.getInt("Temper"));
      this.setTame(tag.getBoolean("Tame"));
      UUID _snowman;
      if (tag.containsUuid("Owner")) {
         _snowman = tag.getUuid("Owner");
      } else {
         String _snowmanx = tag.getString("Owner");
         _snowman = ServerConfigHandler.getPlayerUuidByName(this.getServer(), _snowmanx);
      }

      if (_snowman != null) {
         this.setOwnerUuid(_snowman);
      }

      if (tag.contains("SaddleItem", 10)) {
         ItemStack _snowmanx = ItemStack.fromTag(tag.getCompound("SaddleItem"));
         if (_snowmanx.getItem() == Items.SADDLE) {
            this.items.setStack(0, _snowmanx);
         }
      }

      this.updateSaddle();
   }

   @Override
   public boolean canBreedWith(AnimalEntity other) {
      return false;
   }

   protected boolean canBreed() {
      return !this.hasPassengers() && !this.hasVehicle() && this.isTame() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
   }

   @Nullable
   @Override
   public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
      return null;
   }

   protected void setChildAttributes(PassiveEntity mate, HorseBaseEntity child) {
      double _snowman = this.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)
         + mate.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)
         + (double)this.getChildHealthBonus();
      child.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(_snowman / 3.0);
      double _snowmanx = this.getAttributeBaseValue(EntityAttributes.HORSE_JUMP_STRENGTH)
         + mate.getAttributeBaseValue(EntityAttributes.HORSE_JUMP_STRENGTH)
         + this.getChildJumpStrengthBonus();
      child.getAttributeInstance(EntityAttributes.HORSE_JUMP_STRENGTH).setBaseValue(_snowmanx / 3.0);
      double _snowmanxx = this.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)
         + mate.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)
         + this.getChildMovementSpeedBonus();
      child.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(_snowmanxx / 3.0);
   }

   @Override
   public boolean canBeControlledByRider() {
      return this.getPrimaryPassenger() instanceof LivingEntity;
   }

   public float getEatingGrassAnimationProgress(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastEatingGrassAnimationProgress, this.eatingGrassAnimationProgress);
   }

   public float getAngryAnimationProgress(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastAngryAnimationProgress, this.angryAnimationProgress);
   }

   public float getEatingAnimationProgress(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastEatingAnimationProgress, this.eatingAnimationProgress);
   }

   @Override
   public void setJumpStrength(int strength) {
      if (this.isSaddled()) {
         if (strength < 0) {
            strength = 0;
         } else {
            this.jumping = true;
            this.updateAnger();
         }

         if (strength >= 90) {
            this.jumpStrength = 1.0F;
         } else {
            this.jumpStrength = 0.4F + 0.4F * (float)strength / 90.0F;
         }
      }
   }

   @Override
   public boolean canJump() {
      return this.isSaddled();
   }

   @Override
   public void startJumping(int height) {
      this.jumping = true;
      this.updateAnger();
      this.playJumpSound();
   }

   @Override
   public void stopJumping() {
   }

   protected void spawnPlayerReactionParticles(boolean positive) {
      ParticleEffect _snowman = positive ? ParticleTypes.HEART : ParticleTypes.SMOKE;

      for (int _snowmanx = 0; _snowmanx < 7; _snowmanx++) {
         double _snowmanxx = this.random.nextGaussian() * 0.02;
         double _snowmanxxx = this.random.nextGaussian() * 0.02;
         double _snowmanxxxx = this.random.nextGaussian() * 0.02;
         this.world.addParticle(_snowman, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 7) {
         this.spawnPlayerReactionParticles(true);
      } else if (status == 6) {
         this.spawnPlayerReactionParticles(false);
      } else {
         super.handleStatus(status);
      }
   }

   @Override
   public void updatePassengerPosition(Entity passenger) {
      super.updatePassengerPosition(passenger);
      if (passenger instanceof MobEntity) {
         MobEntity _snowman = (MobEntity)passenger;
         this.bodyYaw = _snowman.bodyYaw;
      }

      if (this.lastAngryAnimationProgress > 0.0F) {
         float _snowman = MathHelper.sin(this.bodyYaw * (float) (Math.PI / 180.0));
         float _snowmanx = MathHelper.cos(this.bodyYaw * (float) (Math.PI / 180.0));
         float _snowmanxx = 0.7F * this.lastAngryAnimationProgress;
         float _snowmanxxx = 0.15F * this.lastAngryAnimationProgress;
         passenger.updatePosition(
            this.getX() + (double)(_snowmanxx * _snowman),
            this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset() + (double)_snowmanxxx,
            this.getZ() - (double)(_snowmanxx * _snowmanx)
         );
         if (passenger instanceof LivingEntity) {
            ((LivingEntity)passenger).bodyYaw = this.bodyYaw;
         }
      }
   }

   protected float getChildHealthBonus() {
      return 15.0F + (float)this.random.nextInt(8) + (float)this.random.nextInt(9);
   }

   protected double getChildJumpStrengthBonus() {
      return 0.4F + this.random.nextDouble() * 0.2 + this.random.nextDouble() * 0.2 + this.random.nextDouble() * 0.2;
   }

   protected double getChildMovementSpeedBonus() {
      return (0.45F + this.random.nextDouble() * 0.3 + this.random.nextDouble() * 0.3 + this.random.nextDouble() * 0.3) * 0.25;
   }

   @Override
   public boolean isClimbing() {
      return false;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height * 0.95F;
   }

   public boolean hasArmorSlot() {
      return false;
   }

   public boolean hasArmorInSlot() {
      return !this.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
   }

   public boolean isHorseArmor(ItemStack item) {
      return false;
   }

   @Override
   public boolean equip(int slot, ItemStack item) {
      int _snowman = slot - 400;
      if (_snowman >= 0 && _snowman < 2 && _snowman < this.items.size()) {
         if (_snowman == 0 && item.getItem() != Items.SADDLE) {
            return false;
         } else if (_snowman != 1 || this.hasArmorSlot() && this.isHorseArmor(item)) {
            this.items.setStack(_snowman, item);
            this.updateSaddle();
            return true;
         } else {
            return false;
         }
      } else {
         int _snowmanx = slot - 500 + 2;
         if (_snowmanx >= 2 && _snowmanx < this.items.size()) {
            this.items.setStack(_snowmanx, item);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   @Override
   public Entity getPrimaryPassenger() {
      return this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
   }

   @Nullable
   private Vec3d method_27930(Vec3d _snowman, LivingEntity _snowman) {
      double _snowmanxx = this.getX() + _snowman.x;
      double _snowmanxxx = this.getBoundingBox().minY;
      double _snowmanxxxx = this.getZ() + _snowman.z;
      BlockPos.Mutable _snowmanxxxxx = new BlockPos.Mutable();
      UnmodifiableIterator var10 = _snowman.getPoses().iterator();

      while (var10.hasNext()) {
         EntityPose _snowmanxxxxxx = (EntityPose)var10.next();
         _snowmanxxxxx.set(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         double _snowmanxxxxxxx = this.getBoundingBox().maxY + 0.75;

         do {
            double _snowmanxxxxxxxx = this.world.getDismountHeight(_snowmanxxxxx);
            if ((double)_snowmanxxxxx.getY() + _snowmanxxxxxxxx > _snowmanxxxxxxx) {
               break;
            }

            if (Dismounting.canDismountInBlock(_snowmanxxxxxxxx)) {
               Box _snowmanxxxxxxxxx = _snowman.getBoundingBox(_snowmanxxxxxx);
               Vec3d _snowmanxxxxxxxxxx = new Vec3d(_snowmanxx, (double)_snowmanxxxxx.getY() + _snowmanxxxxxxxx, _snowmanxxxx);
               if (Dismounting.canPlaceEntityAt(this.world, _snowman, _snowmanxxxxxxxxx.offset(_snowmanxxxxxxxxxx))) {
                  _snowman.setPose(_snowmanxxxxxx);
                  return _snowmanxxxxxxxxxx;
               }
            }

            _snowmanxxxxx.move(Direction.UP);
         } while (!((double)_snowmanxxxxx.getY() < _snowmanxxxxxxx));
      }

      return null;
   }

   @Override
   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      Vec3d _snowman = getPassengerDismountOffset(
         (double)this.getWidth(), (double)passenger.getWidth(), this.yaw + (passenger.getMainArm() == Arm.RIGHT ? 90.0F : -90.0F)
      );
      Vec3d _snowmanx = this.method_27930(_snowman, passenger);
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         Vec3d _snowmanxx = getPassengerDismountOffset(
            (double)this.getWidth(), (double)passenger.getWidth(), this.yaw + (passenger.getMainArm() == Arm.LEFT ? 90.0F : -90.0F)
         );
         Vec3d _snowmanxxx = this.method_27930(_snowmanxx, passenger);
         return _snowmanxxx != null ? _snowmanxxx : this.getPos();
      }
   }

   protected void initAttributes() {
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      if (entityData == null) {
         entityData = new PassiveEntity.PassiveData(0.2F);
      }

      this.initAttributes();
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }
}
