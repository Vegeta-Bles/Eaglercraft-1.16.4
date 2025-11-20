package net.minecraft.entity.mob;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.class_5493;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ZombieEntity extends HostileEntity {
   private static final UUID BABY_SPEED_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final EntityAttributeModifier BABY_SPEED_BONUS = new EntityAttributeModifier(
      BABY_SPEED_ID, "Baby speed boost", 0.5, EntityAttributeModifier.Operation.MULTIPLY_BASE
   );
   private static final TrackedData<Boolean> BABY = DataTracker.registerData(ZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> ZOMBIE_TYPE = DataTracker.registerData(ZombieEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> CONVERTING_IN_WATER = DataTracker.registerData(ZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = difficulty -> difficulty == Difficulty.HARD;
   private final BreakDoorGoal breakDoorsGoal = new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER);
   private boolean canBreakDoors;
   private int inWaterTime;
   private int ticksUntilWaterConversion;

   public ZombieEntity(EntityType<? extends ZombieEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public ZombieEntity(World world) {
      this(EntityType.ZOMBIE, world);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(4, new ZombieEntity.DestroyEggGoal(this, 1.0, 3));
      this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
      this.initCustomGoals();
   }

   protected void initCustomGoals() {
      this.goalSelector.add(2, new ZombieAttackGoal(this, 1.0, false));
      this.goalSelector.add(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
      this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
      this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(ZombifiedPiglinEntity.class));
      this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, false));
      this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
      this.targetSelector.add(5, new FollowTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
   }

   public static DefaultAttributeContainer.Builder createZombieAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23F)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
         .add(EntityAttributes.GENERIC_ARMOR, 2.0)
         .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.getDataTracker().startTracking(BABY, false);
      this.getDataTracker().startTracking(ZOMBIE_TYPE, 0);
      this.getDataTracker().startTracking(CONVERTING_IN_WATER, false);
   }

   public boolean isConvertingInWater() {
      return this.getDataTracker().get(CONVERTING_IN_WATER);
   }

   public boolean canBreakDoors() {
      return this.canBreakDoors;
   }

   public void setCanBreakDoors(boolean canBreakDoors) {
      if (this.shouldBreakDoors() && class_5493.method_30955(this)) {
         if (this.canBreakDoors != canBreakDoors) {
            this.canBreakDoors = canBreakDoors;
            ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(canBreakDoors);
            if (canBreakDoors) {
               this.goalSelector.add(1, this.breakDoorsGoal);
            } else {
               this.goalSelector.remove(this.breakDoorsGoal);
            }
         }
      } else if (this.canBreakDoors) {
         this.goalSelector.remove(this.breakDoorsGoal);
         this.canBreakDoors = false;
      }
   }

   protected boolean shouldBreakDoors() {
      return true;
   }

   @Override
   public boolean isBaby() {
      return this.getDataTracker().get(BABY);
   }

   @Override
   protected int getCurrentExperience(PlayerEntity player) {
      if (this.isBaby()) {
         this.experiencePoints = (int)((float)this.experiencePoints * 2.5F);
      }

      return super.getCurrentExperience(player);
   }

   @Override
   public void setBaby(boolean baby) {
      this.getDataTracker().set(BABY, baby);
      if (this.world != null && !this.world.isClient) {
         EntityAttributeInstance _snowman = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
         _snowman.removeModifier(BABY_SPEED_BONUS);
         if (baby) {
            _snowman.addTemporaryModifier(BABY_SPEED_BONUS);
         }
      }
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (BABY.equals(data)) {
         this.calculateDimensions();
      }

      super.onTrackedDataSet(data);
   }

   protected boolean canConvertInWater() {
      return true;
   }

   @Override
   public void tick() {
      if (!this.world.isClient && this.isAlive() && !this.isAiDisabled()) {
         if (this.isConvertingInWater()) {
            this.ticksUntilWaterConversion--;
            if (this.ticksUntilWaterConversion < 0) {
               this.convertInWater();
            }
         } else if (this.canConvertInWater()) {
            if (this.isSubmergedIn(FluidTags.WATER)) {
               this.inWaterTime++;
               if (this.inWaterTime >= 600) {
                  this.setTicksUntilWaterConversion(300);
               }
            } else {
               this.inWaterTime = -1;
            }
         }
      }

      super.tick();
   }

   @Override
   public void tickMovement() {
      if (this.isAlive()) {
         boolean _snowman = this.burnsInDaylight() && this.isAffectedByDaylight();
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
      }

      super.tickMovement();
   }

   private void setTicksUntilWaterConversion(int ticksUntilWaterConversion) {
      this.ticksUntilWaterConversion = ticksUntilWaterConversion;
      this.getDataTracker().set(CONVERTING_IN_WATER, true);
   }

   protected void convertInWater() {
      this.convertTo(EntityType.DROWNED);
      if (!this.isSilent()) {
         this.world.syncWorldEvent(null, 1040, this.getBlockPos(), 0);
      }
   }

   protected void convertTo(EntityType<? extends ZombieEntity> entityType) {
      ZombieEntity _snowman = this.method_29243(entityType, true);
      if (_snowman != null) {
         _snowman.applyAttributeModifiers(_snowman.world.getLocalDifficulty(_snowman.getBlockPos()).getClampedLocalDifficulty());
         _snowman.setCanBreakDoors(_snowman.shouldBreakDoors() && this.canBreakDoors());
      }
   }

   protected boolean burnsInDaylight() {
      return true;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (!super.damage(source, amount)) {
         return false;
      } else if (!(this.world instanceof ServerWorld)) {
         return false;
      } else {
         ServerWorld _snowman = (ServerWorld)this.world;
         LivingEntity _snowmanx = this.getTarget();
         if (_snowmanx == null && source.getAttacker() instanceof LivingEntity) {
            _snowmanx = (LivingEntity)source.getAttacker();
         }

         if (_snowmanx != null
            && this.world.getDifficulty() == Difficulty.HARD
            && (double)this.random.nextFloat() < this.getAttributeValue(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
            && this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            int _snowmanxx = MathHelper.floor(this.getX());
            int _snowmanxxx = MathHelper.floor(this.getY());
            int _snowmanxxxx = MathHelper.floor(this.getZ());
            ZombieEntity _snowmanxxxxx = new ZombieEntity(this.world);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 50; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = _snowmanxx + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
               int _snowmanxxxxxxxx = _snowmanxxx + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
               int _snowmanxxxxxxxxx = _snowmanxxxx + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
               BlockPos _snowmanxxxxxxxxxx = new BlockPos(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               EntityType<?> _snowmanxxxxxxxxxxx = _snowmanxxxxx.getType();
               SpawnRestriction.Location _snowmanxxxxxxxxxxxx = SpawnRestriction.getLocation(_snowmanxxxxxxxxxxx);
               if (SpawnHelper.canSpawn(_snowmanxxxxxxxxxxxx, this.world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx)
                  && SpawnRestriction.canSpawn(_snowmanxxxxxxxxxxx, _snowman, SpawnReason.REINFORCEMENT, _snowmanxxxxxxxxxx, this.world.random)) {
                  _snowmanxxxxx.updatePosition((double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx);
                  if (!this.world.isPlayerInRange((double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx, 7.0)
                     && this.world.intersectsEntities(_snowmanxxxxx)
                     && this.world.isSpaceEmpty(_snowmanxxxxx)
                     && !this.world.containsFluid(_snowmanxxxxx.getBoundingBox())) {
                     _snowmanxxxxx.setTarget(_snowmanx);
                     _snowmanxxxxx.initialize(_snowman, this.world.getLocalDifficulty(_snowmanxxxxx.getBlockPos()), SpawnReason.REINFORCEMENT, null, null);
                     _snowman.spawnEntityAndPassengers(_snowmanxxxxx);
                     this.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
                        .addPersistentModifier(
                           new EntityAttributeModifier("Zombie reinforcement caller charge", -0.05F, EntityAttributeModifier.Operation.ADDITION)
                        );
                     _snowmanxxxxx.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
                        .addPersistentModifier(
                           new EntityAttributeModifier("Zombie reinforcement callee charge", -0.05F, EntityAttributeModifier.Operation.ADDITION)
                        );
                     break;
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean tryAttack(Entity target) {
      boolean _snowman = super.tryAttack(target);
      if (_snowman) {
         float _snowmanx = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
         if (this.getMainHandStack().isEmpty() && this.isOnFire() && this.random.nextFloat() < _snowmanx * 0.3F) {
            target.setOnFireFor(2 * (int)_snowmanx);
         }
      }

      return _snowman;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_ZOMBIE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_ZOMBIE_DEATH;
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.ENTITY_ZOMBIE_STEP;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(this.getStepSound(), 0.15F, 1.0F);
   }

   @Override
   public EntityGroup getGroup() {
      return EntityGroup.UNDEAD;
   }

   @Override
   protected void initEquipment(LocalDifficulty difficulty) {
      super.initEquipment(difficulty);
      if (this.random.nextFloat() < (this.world.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
         int _snowman = this.random.nextInt(3);
         if (_snowman == 0) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
         } else {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
         }
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putBoolean("IsBaby", this.isBaby());
      tag.putBoolean("CanBreakDoors", this.canBreakDoors());
      tag.putInt("InWaterTime", this.isTouchingWater() ? this.inWaterTime : -1);
      tag.putInt("DrownedConversionTime", this.isConvertingInWater() ? this.ticksUntilWaterConversion : -1);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setBaby(tag.getBoolean("IsBaby"));
      this.setCanBreakDoors(tag.getBoolean("CanBreakDoors"));
      this.inWaterTime = tag.getInt("InWaterTime");
      if (tag.contains("DrownedConversionTime", 99) && tag.getInt("DrownedConversionTime") > -1) {
         this.setTicksUntilWaterConversion(tag.getInt("DrownedConversionTime"));
      }
   }

   @Override
   public void onKilledOther(ServerWorld _snowman, LivingEntity _snowman) {
      super.onKilledOther(_snowman, _snowman);
      if ((_snowman.getDifficulty() == Difficulty.NORMAL || _snowman.getDifficulty() == Difficulty.HARD) && _snowman instanceof VillagerEntity) {
         if (_snowman.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
            return;
         }

         VillagerEntity _snowmanxx = (VillagerEntity)_snowman;
         ZombieVillagerEntity _snowmanxxx = _snowmanxx.method_29243(EntityType.ZOMBIE_VILLAGER, false);
         _snowmanxxx.initialize(_snowman, _snowman.getLocalDifficulty(_snowmanxxx.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
         _snowmanxxx.setVillagerData(_snowmanxx.getVillagerData());
         _snowmanxxx.setGossipData((Tag)_snowmanxx.getGossip().serialize(NbtOps.INSTANCE).getValue());
         _snowmanxxx.setOfferData(_snowmanxx.getOffers().toTag());
         _snowmanxxx.setXp(_snowmanxx.getExperience());
         if (!this.isSilent()) {
            _snowman.syncWorldEvent(null, 1026, this.getBlockPos(), 0);
         }
      }
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return this.isBaby() ? 0.93F : 1.74F;
   }

   @Override
   public boolean canPickupItem(ItemStack stack) {
      return stack.getItem() == Items.EGG && this.isBaby() && this.hasVehicle() ? false : super.canPickupItem(stack);
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
      float _snowman = difficulty.getClampedLocalDifficulty();
      this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * _snowman);
      if (entityData == null) {
         entityData = new ZombieEntity.ZombieData(method_29936(world.getRandom()), true);
      }

      if (entityData instanceof ZombieEntity.ZombieData) {
         ZombieEntity.ZombieData _snowmanx = (ZombieEntity.ZombieData)entityData;
         if (_snowmanx.baby) {
            this.setBaby(true);
            if (_snowmanx.field_25607) {
               if ((double)world.getRandom().nextFloat() < 0.05) {
                  List<ChickenEntity> _snowmanxx = world.getEntitiesByClass(
                     ChickenEntity.class, this.getBoundingBox().expand(5.0, 3.0, 5.0), EntityPredicates.NOT_MOUNTED
                  );
                  if (!_snowmanxx.isEmpty()) {
                     ChickenEntity _snowmanxxx = _snowmanxx.get(0);
                     _snowmanxxx.setHasJockey(true);
                     this.startRiding(_snowmanxxx);
                  }
               } else if ((double)world.getRandom().nextFloat() < 0.05) {
                  ChickenEntity _snowmanxx = EntityType.CHICKEN.create(this.world);
                  _snowmanxx.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, 0.0F);
                  _snowmanxx.initialize(world, difficulty, SpawnReason.JOCKEY, null, null);
                  _snowmanxx.setHasJockey(true);
                  this.startRiding(_snowmanxx);
                  world.spawnEntity(_snowmanxx);
               }
            }
         }

         this.setCanBreakDoors(this.shouldBreakDoors() && this.random.nextFloat() < _snowman * 0.1F);
         this.initEquipment(difficulty);
         this.updateEnchantments(difficulty);
      }

      if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
         LocalDate _snowmanx = LocalDate.now();
         int _snowmanxx = _snowmanx.get(ChronoField.DAY_OF_MONTH);
         int _snowmanxxx = _snowmanx.get(ChronoField.MONTH_OF_YEAR);
         if (_snowmanxxx == 10 && _snowmanxx == 31 && this.random.nextFloat() < 0.25F) {
            this.equipStack(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
            this.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 0.0F;
         }
      }

      this.applyAttributeModifiers(_snowman);
      return entityData;
   }

   public static boolean method_29936(Random _snowman) {
      return _snowman.nextFloat() < 0.05F;
   }

   protected void applyAttributeModifiers(float chanceMultiplier) {
      this.initAttributes();
      this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
         .addPersistentModifier(new EntityAttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05F, EntityAttributeModifier.Operation.ADDITION));
      double _snowman = this.random.nextDouble() * 1.5 * (double)chanceMultiplier;
      if (_snowman > 1.0) {
         this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE)
            .addPersistentModifier(new EntityAttributeModifier("Random zombie-spawn bonus", _snowman, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
      }

      if (this.random.nextFloat() < chanceMultiplier * 0.05F) {
         this.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
            .addPersistentModifier(
               new EntityAttributeModifier("Leader zombie bonus", this.random.nextDouble() * 0.25 + 0.5, EntityAttributeModifier.Operation.ADDITION)
            );
         this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
            .addPersistentModifier(
               new EntityAttributeModifier("Leader zombie bonus", this.random.nextDouble() * 3.0 + 1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            );
         this.setCanBreakDoors(this.shouldBreakDoors());
      }
   }

   protected void initAttributes() {
      this.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(this.random.nextDouble() * 0.1F);
   }

   @Override
   public double getHeightOffset() {
      return this.isBaby() ? 0.0 : -0.45;
   }

   @Override
   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
      super.dropEquipment(source, lootingMultiplier, allowDrops);
      Entity _snowman = source.getAttacker();
      if (_snowman instanceof CreeperEntity) {
         CreeperEntity _snowmanx = (CreeperEntity)_snowman;
         if (_snowmanx.shouldDropHead()) {
            ItemStack _snowmanxx = this.getSkull();
            if (!_snowmanxx.isEmpty()) {
               _snowmanx.onHeadDropped();
               this.dropStack(_snowmanxx);
            }
         }
      }
   }

   protected ItemStack getSkull() {
      return new ItemStack(Items.ZOMBIE_HEAD);
   }

   class DestroyEggGoal extends StepAndDestroyBlockGoal {
      DestroyEggGoal(PathAwareEntity mob, double speed, int maxYDifference) {
         super(Blocks.TURTLE_EGG, mob, speed, maxYDifference);
      }

      @Override
      public void tickStepping(WorldAccess world, BlockPos pos) {
         world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_DESTROY_EGG, SoundCategory.HOSTILE, 0.5F, 0.9F + ZombieEntity.this.random.nextFloat() * 0.2F);
      }

      @Override
      public void onDestroyBlock(World world, BlockPos pos) {
         world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
      }

      @Override
      public double getDesiredSquaredDistanceToTarget() {
         return 1.14;
      }
   }

   public static class ZombieData implements EntityData {
      public final boolean baby;
      public final boolean field_25607;

      public ZombieData(boolean baby, boolean _snowman) {
         this.baby = baby;
         this.field_25607 = _snowman;
      }
   }
}
