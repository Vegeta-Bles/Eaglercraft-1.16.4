package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.FormCaravanGoal;
import net.minecraft.entity.ai.goal.HorseBondWithPlayerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class LlamaEntity extends AbstractDonkeyEntity implements RangedAttackMob {
   private static final Ingredient TAMING_INGREDIENT = Ingredient.ofItems(Items.WHEAT, Blocks.HAY_BLOCK.asItem());
   private static final TrackedData<Integer> STRENGTH = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> CARPET_COLOR = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> VARIANT = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private boolean spit;
   @Nullable
   private LlamaEntity following;
   @Nullable
   private LlamaEntity follower;

   public LlamaEntity(EntityType<? extends LlamaEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public boolean isTrader() {
      return false;
   }

   private void setStrength(int strength) {
      this.dataTracker.set(STRENGTH, Math.max(1, Math.min(5, strength)));
   }

   private void initializeStrength() {
      int _snowman = this.random.nextFloat() < 0.04F ? 5 : 3;
      this.setStrength(1 + this.random.nextInt(_snowman));
   }

   public int getStrength() {
      return this.dataTracker.get(STRENGTH);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("Variant", this.getVariant());
      tag.putInt("Strength", this.getStrength());
      if (!this.items.getStack(1).isEmpty()) {
         tag.put("DecorItem", this.items.getStack(1).toTag(new CompoundTag()));
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.setStrength(tag.getInt("Strength"));
      super.readCustomDataFromTag(tag);
      this.setVariant(tag.getInt("Variant"));
      if (tag.contains("DecorItem", 10)) {
         this.items.setStack(1, ItemStack.fromTag(tag.getCompound("DecorItem")));
      }

      this.updateSaddle();
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(1, new HorseBondWithPlayerGoal(this, 1.2));
      this.goalSelector.add(2, new FormCaravanGoal(this, 2.1F));
      this.goalSelector.add(3, new ProjectileAttackGoal(this, 1.25, 40, 20.0F));
      this.goalSelector.add(3, new EscapeDangerGoal(this, 1.2));
      this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
      this.goalSelector.add(5, new FollowParentGoal(this, 1.0));
      this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7));
      this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
      this.targetSelector.add(1, new LlamaEntity.SpitRevengeGoal(this));
      this.targetSelector.add(2, new LlamaEntity.ChaseWolvesGoal(this));
   }

   public static DefaultAttributeContainer.Builder createLlamaAttributes() {
      return createAbstractDonkeyAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(STRENGTH, 0);
      this.dataTracker.startTracking(CARPET_COLOR, -1);
      this.dataTracker.startTracking(VARIANT, 0);
   }

   public int getVariant() {
      return MathHelper.clamp(this.dataTracker.get(VARIANT), 0, 3);
   }

   public void setVariant(int variant) {
      this.dataTracker.set(VARIANT, variant);
   }

   @Override
   protected int getInventorySize() {
      return this.hasChest() ? 2 + 3 * this.getInventoryColumns() : super.getInventorySize();
   }

   @Override
   public void updatePassengerPosition(Entity passenger) {
      if (this.hasPassenger(passenger)) {
         float _snowman = MathHelper.cos(this.bodyYaw * (float) (Math.PI / 180.0));
         float _snowmanx = MathHelper.sin(this.bodyYaw * (float) (Math.PI / 180.0));
         float _snowmanxx = 0.3F;
         passenger.updatePosition(
            this.getX() + (double)(0.3F * _snowmanx), this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset(), this.getZ() - (double)(0.3F * _snowman)
         );
      }
   }

   @Override
   public double getMountedHeightOffset() {
      return (double)this.getHeight() * 0.67;
   }

   @Override
   public boolean canBeControlledByRider() {
      return false;
   }

   @Override
   public boolean isBreedingItem(ItemStack stack) {
      return TAMING_INGREDIENT.test(stack);
   }

   @Override
   protected boolean receiveFood(PlayerEntity player, ItemStack item) {
      int _snowman = 0;
      int _snowmanx = 0;
      float _snowmanxx = 0.0F;
      boolean _snowmanxxx = false;
      Item _snowmanxxxx = item.getItem();
      if (_snowmanxxxx == Items.WHEAT) {
         _snowman = 10;
         _snowmanx = 3;
         _snowmanxx = 2.0F;
      } else if (_snowmanxxxx == Blocks.HAY_BLOCK.asItem()) {
         _snowman = 90;
         _snowmanx = 6;
         _snowmanxx = 10.0F;
         if (this.isTame() && this.getBreedingAge() == 0 && this.canEat()) {
            _snowmanxxx = true;
            this.lovePlayer(player);
         }
      }

      if (this.getHealth() < this.getMaxHealth() && _snowmanxx > 0.0F) {
         this.heal(_snowmanxx);
         _snowmanxxx = true;
      }

      if (this.isBaby() && _snowman > 0) {
         this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
         if (!this.world.isClient) {
            this.growUp(_snowman);
         }

         _snowmanxxx = true;
      }

      if (_snowmanx > 0 && (_snowmanxxx || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
         _snowmanxxx = true;
         if (!this.world.isClient) {
            this.addTemper(_snowmanx);
         }
      }

      if (_snowmanxxx && !this.isSilent()) {
         SoundEvent _snowmanxxxxx = this.getEatSound();
         if (_snowmanxxxxx != null) {
            this.world
               .playSound(
                  null,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  this.getEatSound(),
                  this.getSoundCategory(),
                  1.0F,
                  1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
               );
         }
      }

      return _snowmanxxx;
   }

   @Override
   protected boolean isImmobile() {
      return this.isDead() || this.isEatingGrass();
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      this.initializeStrength();
      int _snowman;
      if (entityData instanceof LlamaEntity.LlamaData) {
         _snowman = ((LlamaEntity.LlamaData)entityData).variant;
      } else {
         _snowman = this.random.nextInt(4);
         entityData = new LlamaEntity.LlamaData(_snowman);
      }

      this.setVariant(_snowman);
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   @Override
   protected SoundEvent getAngrySound() {
      return SoundEvents.ENTITY_LLAMA_ANGRY;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_LLAMA_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_LLAMA_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_LLAMA_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getEatSound() {
      return SoundEvents.ENTITY_LLAMA_EAT;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
   }

   @Override
   protected void playAddChestSound() {
      this.playSound(SoundEvents.ENTITY_LLAMA_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
   }

   @Override
   public void playAngrySound() {
      SoundEvent _snowman = this.getAngrySound();
      if (_snowman != null) {
         this.playSound(_snowman, this.getSoundVolume(), this.getSoundPitch());
      }
   }

   @Override
   public int getInventoryColumns() {
      return this.getStrength();
   }

   @Override
   public boolean hasArmorSlot() {
      return true;
   }

   @Override
   public boolean hasArmorInSlot() {
      return !this.items.getStack(1).isEmpty();
   }

   @Override
   public boolean isHorseArmor(ItemStack item) {
      Item _snowman = item.getItem();
      return ItemTags.CARPETS.contains(_snowman);
   }

   @Override
   public boolean canBeSaddled() {
      return false;
   }

   @Override
   public void onInventoryChanged(Inventory sender) {
      DyeColor _snowman = this.getCarpetColor();
      super.onInventoryChanged(sender);
      DyeColor _snowmanx = this.getCarpetColor();
      if (this.age > 20 && _snowmanx != null && _snowmanx != _snowman) {
         this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
      }
   }

   @Override
   protected void updateSaddle() {
      if (!this.world.isClient) {
         super.updateSaddle();
         this.setCarpetColor(getColorFromCarpet(this.items.getStack(1)));
      }
   }

   private void setCarpetColor(@Nullable DyeColor color) {
      this.dataTracker.set(CARPET_COLOR, color == null ? -1 : color.getId());
   }

   @Nullable
   private static DyeColor getColorFromCarpet(ItemStack color) {
      Block _snowman = Block.getBlockFromItem(color.getItem());
      return _snowman instanceof CarpetBlock ? ((CarpetBlock)_snowman).getColor() : null;
   }

   @Nullable
   public DyeColor getCarpetColor() {
      int _snowman = this.dataTracker.get(CARPET_COLOR);
      return _snowman == -1 ? null : DyeColor.byId(_snowman);
   }

   @Override
   public int getMaxTemper() {
      return 30;
   }

   @Override
   public boolean canBreedWith(AnimalEntity other) {
      return other != this && other instanceof LlamaEntity && this.canBreed() && ((LlamaEntity)other).canBreed();
   }

   public LlamaEntity createChild(ServerWorld _snowman, PassiveEntity _snowman) {
      LlamaEntity _snowmanxx = this.createChild();
      this.setChildAttributes(_snowman, _snowmanxx);
      LlamaEntity _snowmanxxx = (LlamaEntity)_snowman;
      int _snowmanxxxx = this.random.nextInt(Math.max(this.getStrength(), _snowmanxxx.getStrength())) + 1;
      if (this.random.nextFloat() < 0.03F) {
         _snowmanxxxx++;
      }

      _snowmanxx.setStrength(_snowmanxxxx);
      _snowmanxx.setVariant(this.random.nextBoolean() ? this.getVariant() : _snowmanxxx.getVariant());
      return _snowmanxx;
   }

   protected LlamaEntity createChild() {
      return EntityType.LLAMA.create(this.world);
   }

   private void spitAt(LivingEntity target) {
      LlamaSpitEntity _snowman = new LlamaSpitEntity(this.world, this);
      double _snowmanx = target.getX() - this.getX();
      double _snowmanxx = target.getBodyY(0.3333333333333333) - _snowman.getY();
      double _snowmanxxx = target.getZ() - this.getZ();
      float _snowmanxxxx = MathHelper.sqrt(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx) * 0.2F;
      _snowman.setVelocity(_snowmanx, _snowmanxx + (double)_snowmanxxxx, _snowmanxxx, 1.5F, 10.0F);
      if (!this.isSilent()) {
         this.world
            .playSound(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               SoundEvents.ENTITY_LLAMA_SPIT,
               this.getSoundCategory(),
               1.0F,
               1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
            );
      }

      this.world.spawnEntity(_snowman);
      this.spit = true;
   }

   private void setSpit(boolean spit) {
      this.spit = spit;
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      int _snowman = this.computeFallDamage(fallDistance, damageMultiplier);
      if (_snowman <= 0) {
         return false;
      } else {
         if (fallDistance >= 6.0F) {
            this.damage(DamageSource.FALL, (float)_snowman);
            if (this.hasPassengers()) {
               for (Entity _snowmanx : this.getPassengersDeep()) {
                  _snowmanx.damage(DamageSource.FALL, (float)_snowman);
               }
            }
         }

         this.playBlockFallSound();
         return true;
      }
   }

   public void stopFollowing() {
      if (this.following != null) {
         this.following.follower = null;
      }

      this.following = null;
   }

   public void follow(LlamaEntity llama) {
      this.following = llama;
      this.following.follower = this;
   }

   public boolean hasFollower() {
      return this.follower != null;
   }

   public boolean isFollowing() {
      return this.following != null;
   }

   @Nullable
   public LlamaEntity getFollowing() {
      return this.following;
   }

   @Override
   protected double getRunFromLeashSpeed() {
      return 2.0;
   }

   @Override
   protected void walkToParent() {
      if (!this.isFollowing() && this.isBaby()) {
         super.walkToParent();
      }
   }

   @Override
   public boolean eatsGrass() {
      return false;
   }

   @Override
   public void attack(LivingEntity target, float pullProgress) {
      this.spitAt(target);
   }

   @Override
   public Vec3d method_29919() {
      return new Vec3d(0.0, 0.75 * (double)this.getStandingEyeHeight(), (double)this.getWidth() * 0.5);
   }

   static class ChaseWolvesGoal extends FollowTargetGoal<WolfEntity> {
      public ChaseWolvesGoal(LlamaEntity llama) {
         super(llama, WolfEntity.class, 16, false, true, _snowman -> !((WolfEntity)_snowman).isTamed());
      }

      @Override
      protected double getFollowRange() {
         return super.getFollowRange() * 0.25;
      }
   }

   static class LlamaData extends PassiveEntity.PassiveData {
      public final int variant;

      private LlamaData(int variant) {
         super(true);
         this.variant = variant;
      }
   }

   static class SpitRevengeGoal extends RevengeGoal {
      public SpitRevengeGoal(LlamaEntity llama) {
         super(llama);
      }

      @Override
      public boolean shouldContinue() {
         if (this.mob instanceof LlamaEntity) {
            LlamaEntity _snowman = (LlamaEntity)this.mob;
            if (_snowman.spit) {
               _snowman.setSpit(false);
               return false;
            }
         }

         return super.shouldContinue();
      }
   }
}
