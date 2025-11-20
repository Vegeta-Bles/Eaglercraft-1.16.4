package net.minecraft.entity.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Recipe;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class PlayerEntity extends LivingEntity {
   public static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.changing(0.6F, 1.8F);
   private static final Map<EntityPose, EntityDimensions> POSE_DIMENSIONS = ImmutableMap.builder()
      .put(EntityPose.STANDING, STANDING_DIMENSIONS)
      .put(EntityPose.SLEEPING, SLEEPING_DIMENSIONS)
      .put(EntityPose.FALL_FLYING, EntityDimensions.changing(0.6F, 0.6F))
      .put(EntityPose.SWIMMING, EntityDimensions.changing(0.6F, 0.6F))
      .put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(0.6F, 0.6F))
      .put(EntityPose.CROUCHING, EntityDimensions.changing(0.6F, 1.5F))
      .put(EntityPose.DYING, EntityDimensions.fixed(0.2F, 0.2F))
      .build();
   private static final TrackedData<Float> ABSORPTION_AMOUNT = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
   private static final TrackedData<Integer> SCORE = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
   protected static final TrackedData<Byte> PLAYER_MODEL_PARTS = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BYTE);
   protected static final TrackedData<Byte> MAIN_ARM = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BYTE);
   protected static final TrackedData<CompoundTag> LEFT_SHOULDER_ENTITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
   protected static final TrackedData<CompoundTag> RIGHT_SHOULDER_ENTITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
   private long shoulderEntityAddedTime;
   public final PlayerInventory inventory = new PlayerInventory(this);
   protected EnderChestInventory enderChestInventory = new EnderChestInventory();
   public final PlayerScreenHandler playerScreenHandler;
   public ScreenHandler currentScreenHandler;
   protected HungerManager hungerManager = new HungerManager();
   protected int abilityResyncCountdown;
   public float prevStrideDistance;
   public float strideDistance;
   public int experiencePickUpDelay;
   public double prevCapeX;
   public double prevCapeY;
   public double prevCapeZ;
   public double capeX;
   public double capeY;
   public double capeZ;
   private int sleepTimer;
   protected boolean isSubmergedInWater;
   public final PlayerAbilities abilities = new PlayerAbilities();
   public int experienceLevel;
   public int totalExperience;
   public float experienceProgress;
   protected int enchantmentTableSeed;
   protected final float field_7509 = 0.02F;
   private int lastPlayedLevelUpSoundTime;
   private final GameProfile gameProfile;
   private boolean reducedDebugInfo;
   private ItemStack selectedItem = ItemStack.EMPTY;
   private final ItemCooldownManager itemCooldownManager = this.createCooldownManager();
   @Nullable
   public FishingBobberEntity fishHook;

   public PlayerEntity(World world, BlockPos pos, float yaw, GameProfile profile) {
      super(EntityType.PLAYER, world);
      this.setUuid(getUuidFromProfile(profile));
      this.gameProfile = profile;
      this.playerScreenHandler = new PlayerScreenHandler(this.inventory, !world.isClient, this);
      this.currentScreenHandler = this.playerScreenHandler;
      this.refreshPositionAndAngles((double)pos.getX() + 0.5, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5, yaw, 0.0F);
      this.field_6215 = 180.0F;
   }

   public boolean isBlockBreakingRestricted(World world, BlockPos pos, GameMode gameMode) {
      if (!gameMode.isBlockBreakingRestricted()) {
         return false;
      } else if (gameMode == GameMode.SPECTATOR) {
         return true;
      } else if (this.canModifyBlocks()) {
         return false;
      } else {
         ItemStack _snowman = this.getMainHandStack();
         return _snowman.isEmpty() || !_snowman.canDestroy(world.getTagManager(), new CachedBlockPosition(world, pos, false));
      }
   }

   public static DefaultAttributeContainer.Builder createPlayerAttributes() {
      return LivingEntity.createLivingAttributes()
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1F)
         .add(EntityAttributes.GENERIC_ATTACK_SPEED)
         .add(EntityAttributes.GENERIC_LUCK);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(ABSORPTION_AMOUNT, 0.0F);
      this.dataTracker.startTracking(SCORE, 0);
      this.dataTracker.startTracking(PLAYER_MODEL_PARTS, (byte)0);
      this.dataTracker.startTracking(MAIN_ARM, (byte)1);
      this.dataTracker.startTracking(LEFT_SHOULDER_ENTITY, new CompoundTag());
      this.dataTracker.startTracking(RIGHT_SHOULDER_ENTITY, new CompoundTag());
   }

   @Override
   public void tick() {
      this.noClip = this.isSpectator();
      if (this.isSpectator()) {
         this.onGround = false;
      }

      if (this.experiencePickUpDelay > 0) {
         this.experiencePickUpDelay--;
      }

      if (this.isSleeping()) {
         this.sleepTimer++;
         if (this.sleepTimer > 100) {
            this.sleepTimer = 100;
         }

         if (!this.world.isClient && this.world.isDay()) {
            this.wakeUp(false, true);
         }
      } else if (this.sleepTimer > 0) {
         this.sleepTimer++;
         if (this.sleepTimer >= 110) {
            this.sleepTimer = 0;
         }
      }

      this.updateWaterSubmersionState();
      super.tick();
      if (!this.world.isClient && this.currentScreenHandler != null && !this.currentScreenHandler.canUse(this)) {
         this.closeHandledScreen();
         this.currentScreenHandler = this.playerScreenHandler;
      }

      this.updateCapeAngles();
      if (!this.world.isClient) {
         this.hungerManager.update(this);
         this.incrementStat(Stats.PLAY_ONE_MINUTE);
         if (this.isAlive()) {
            this.incrementStat(Stats.TIME_SINCE_DEATH);
         }

         if (this.isSneaky()) {
            this.incrementStat(Stats.SNEAK_TIME);
         }

         if (!this.isSleeping()) {
            this.incrementStat(Stats.TIME_SINCE_REST);
         }
      }

      int _snowman = 29999999;
      double _snowmanx = MathHelper.clamp(this.getX(), -2.9999999E7, 2.9999999E7);
      double _snowmanxx = MathHelper.clamp(this.getZ(), -2.9999999E7, 2.9999999E7);
      if (_snowmanx != this.getX() || _snowmanxx != this.getZ()) {
         this.updatePosition(_snowmanx, this.getY(), _snowmanxx);
      }

      this.lastAttackedTicks++;
      ItemStack _snowmanxxx = this.getMainHandStack();
      if (!ItemStack.areEqual(this.selectedItem, _snowmanxxx)) {
         if (!ItemStack.areItemsEqual(this.selectedItem, _snowmanxxx)) {
            this.resetLastAttackedTicks();
         }

         this.selectedItem = _snowmanxxx.copy();
      }

      this.updateTurtleHelmet();
      this.itemCooldownManager.update();
      this.updateSize();
   }

   public boolean shouldCancelInteraction() {
      return this.isSneaking();
   }

   protected boolean shouldDismount() {
      return this.isSneaking();
   }

   protected boolean clipAtLedge() {
      return this.isSneaking();
   }

   protected boolean updateWaterSubmersionState() {
      this.isSubmergedInWater = this.isSubmergedIn(FluidTags.WATER);
      return this.isSubmergedInWater;
   }

   private void updateTurtleHelmet() {
      ItemStack _snowman = this.getEquippedStack(EquipmentSlot.HEAD);
      if (_snowman.getItem() == Items.TURTLE_HELMET && !this.isSubmergedIn(FluidTags.WATER)) {
         this.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, 0, false, false, true));
      }
   }

   protected ItemCooldownManager createCooldownManager() {
      return new ItemCooldownManager();
   }

   private void updateCapeAngles() {
      this.prevCapeX = this.capeX;
      this.prevCapeY = this.capeY;
      this.prevCapeZ = this.capeZ;
      double _snowman = this.getX() - this.capeX;
      double _snowmanx = this.getY() - this.capeY;
      double _snowmanxx = this.getZ() - this.capeZ;
      double _snowmanxxx = 10.0;
      if (_snowman > 10.0) {
         this.capeX = this.getX();
         this.prevCapeX = this.capeX;
      }

      if (_snowmanxx > 10.0) {
         this.capeZ = this.getZ();
         this.prevCapeZ = this.capeZ;
      }

      if (_snowmanx > 10.0) {
         this.capeY = this.getY();
         this.prevCapeY = this.capeY;
      }

      if (_snowman < -10.0) {
         this.capeX = this.getX();
         this.prevCapeX = this.capeX;
      }

      if (_snowmanxx < -10.0) {
         this.capeZ = this.getZ();
         this.prevCapeZ = this.capeZ;
      }

      if (_snowmanx < -10.0) {
         this.capeY = this.getY();
         this.prevCapeY = this.capeY;
      }

      this.capeX += _snowman * 0.25;
      this.capeZ += _snowmanxx * 0.25;
      this.capeY += _snowmanx * 0.25;
   }

   protected void updateSize() {
      if (this.wouldPoseNotCollide(EntityPose.SWIMMING)) {
         EntityPose _snowman;
         if (this.isFallFlying()) {
            _snowman = EntityPose.FALL_FLYING;
         } else if (this.isSleeping()) {
            _snowman = EntityPose.SLEEPING;
         } else if (this.isSwimming()) {
            _snowman = EntityPose.SWIMMING;
         } else if (this.isUsingRiptide()) {
            _snowman = EntityPose.SPIN_ATTACK;
         } else if (this.isSneaking() && !this.abilities.flying) {
            _snowman = EntityPose.CROUCHING;
         } else {
            _snowman = EntityPose.STANDING;
         }

         EntityPose _snowmanx;
         if (this.isSpectator() || this.hasVehicle() || this.wouldPoseNotCollide(_snowman)) {
            _snowmanx = _snowman;
         } else if (this.wouldPoseNotCollide(EntityPose.CROUCHING)) {
            _snowmanx = EntityPose.CROUCHING;
         } else {
            _snowmanx = EntityPose.SWIMMING;
         }

         this.setPose(_snowmanx);
      }
   }

   @Override
   public int getMaxNetherPortalTime() {
      return this.abilities.invulnerable ? 1 : 80;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_PLAYER_SWIM;
   }

   @Override
   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_PLAYER_SPLASH;
   }

   @Override
   protected SoundEvent getHighSpeedSplashSound() {
      return SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
   }

   @Override
   public int getDefaultNetherPortalCooldown() {
      return 10;
   }

   @Override
   public void playSound(SoundEvent sound, float volume, float pitch) {
      this.world.playSound(this, this.getX(), this.getY(), this.getZ(), sound, this.getSoundCategory(), volume, pitch);
   }

   public void playSound(SoundEvent event, SoundCategory category, float volume, float pitch) {
   }

   @Override
   public SoundCategory getSoundCategory() {
      return SoundCategory.PLAYERS;
   }

   @Override
   protected int getBurningDuration() {
      return 20;
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 9) {
         this.consumeItem();
      } else if (status == 23) {
         this.reducedDebugInfo = false;
      } else if (status == 22) {
         this.reducedDebugInfo = true;
      } else if (status == 43) {
         this.spawnParticles(ParticleTypes.CLOUD);
      } else {
         super.handleStatus(status);
      }
   }

   private void spawnParticles(ParticleEffect parameters) {
      for (int _snowman = 0; _snowman < 5; _snowman++) {
         double _snowmanx = this.random.nextGaussian() * 0.02;
         double _snowmanxx = this.random.nextGaussian() * 0.02;
         double _snowmanxxx = this.random.nextGaussian() * 0.02;
         this.world.addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   protected void closeHandledScreen() {
      this.currentScreenHandler = this.playerScreenHandler;
   }

   @Override
   public void tickRiding() {
      if (this.shouldDismount() && this.hasVehicle()) {
         this.stopRiding();
         this.setSneaking(false);
      } else {
         double _snowman = this.getX();
         double _snowmanx = this.getY();
         double _snowmanxx = this.getZ();
         super.tickRiding();
         this.prevStrideDistance = this.strideDistance;
         this.strideDistance = 0.0F;
         this.increaseRidingMotionStats(this.getX() - _snowman, this.getY() - _snowmanx, this.getZ() - _snowmanxx);
      }
   }

   @Override
   public void afterSpawn() {
      this.setPose(EntityPose.STANDING);
      super.afterSpawn();
      this.setHealth(this.getMaxHealth());
      this.deathTime = 0;
   }

   @Override
   protected void tickNewAi() {
      super.tickNewAi();
      this.tickHandSwing();
      this.headYaw = this.yaw;
   }

   @Override
   public void tickMovement() {
      if (this.abilityResyncCountdown > 0) {
         this.abilityResyncCountdown--;
      }

      if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) {
         if (this.getHealth() < this.getMaxHealth() && this.age % 20 == 0) {
            this.heal(1.0F);
         }

         if (this.hungerManager.isNotFull() && this.age % 10 == 0) {
            this.hungerManager.setFoodLevel(this.hungerManager.getFoodLevel() + 1);
         }
      }

      this.inventory.updateItems();
      this.prevStrideDistance = this.strideDistance;
      super.tickMovement();
      this.flyingSpeed = 0.02F;
      if (this.isSprinting()) {
         this.flyingSpeed = (float)((double)this.flyingSpeed + 0.005999999865889549);
      }

      this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
      float _snowman;
      if (this.onGround && !this.isDead() && !this.isSwimming()) {
         _snowman = Math.min(0.1F, MathHelper.sqrt(squaredHorizontalLength(this.getVelocity())));
      } else {
         _snowman = 0.0F;
      }

      this.strideDistance = this.strideDistance + (_snowman - this.strideDistance) * 0.4F;
      if (this.getHealth() > 0.0F && !this.isSpectator()) {
         Box _snowmanx;
         if (this.hasVehicle() && !this.getVehicle().removed) {
            _snowmanx = this.getBoundingBox().union(this.getVehicle().getBoundingBox()).expand(1.0, 0.0, 1.0);
         } else {
            _snowmanx = this.getBoundingBox().expand(1.0, 0.5, 1.0);
         }

         List<Entity> _snowmanxx = this.world.getOtherEntities(this, _snowmanx);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            Entity _snowmanxxxx = _snowmanxx.get(_snowmanxxx);
            if (!_snowmanxxxx.removed) {
               this.collideWithEntity(_snowmanxxxx);
            }
         }
      }

      this.updateShoulderEntity(this.getShoulderEntityLeft());
      this.updateShoulderEntity(this.getShoulderEntityRight());
      if (!this.world.isClient && (this.fallDistance > 0.5F || this.isTouchingWater()) || this.abilities.flying || this.isSleeping()) {
         this.dropShoulderEntities();
      }
   }

   private void updateShoulderEntity(@Nullable CompoundTag _snowman) {
      if (_snowman != null && (!_snowman.contains("Silent") || !_snowman.getBoolean("Silent")) && this.world.random.nextInt(200) == 0) {
         String _snowmanx = _snowman.getString("id");
         EntityType.get(_snowmanx)
            .filter(_snowmanxx -> _snowmanxx == EntityType.PARROT)
            .ifPresent(
               _snowmanxx -> {
                  if (!ParrotEntity.imitateNearbyMob(this.world, this)) {
                     this.world
                        .playSound(
                           null,
                           this.getX(),
                           this.getY(),
                           this.getZ(),
                           ParrotEntity.getRandomSound(this.world, this.world.random),
                           this.getSoundCategory(),
                           1.0F,
                           ParrotEntity.getSoundPitch(this.world.random)
                        );
                  }
               }
            );
      }
   }

   private void collideWithEntity(Entity entity) {
      entity.onPlayerCollision(this);
   }

   public int getScore() {
      return this.dataTracker.get(SCORE);
   }

   public void setScore(int score) {
      this.dataTracker.set(SCORE, score);
   }

   public void addScore(int score) {
      int _snowman = this.getScore();
      this.dataTracker.set(SCORE, _snowman + score);
   }

   @Override
   public void onDeath(DamageSource source) {
      super.onDeath(source);
      this.refreshPosition();
      if (!this.isSpectator()) {
         this.drop(source);
      }

      if (source != null) {
         this.setVelocity(
            (double)(-MathHelper.cos((this.knockbackVelocity + this.yaw) * (float) (Math.PI / 180.0)) * 0.1F),
            0.1F,
            (double)(-MathHelper.sin((this.knockbackVelocity + this.yaw) * (float) (Math.PI / 180.0)) * 0.1F)
         );
      } else {
         this.setVelocity(0.0, 0.1, 0.0);
      }

      this.incrementStat(Stats.DEATHS);
      this.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_DEATH));
      this.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
      this.extinguish();
      this.setFlag(0, false);
   }

   @Override
   protected void dropInventory() {
      super.dropInventory();
      if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
         this.vanishCursedItems();
         this.inventory.dropAll();
      }
   }

   protected void vanishCursedItems() {
      for (int _snowman = 0; _snowman < this.inventory.size(); _snowman++) {
         ItemStack _snowmanx = this.inventory.getStack(_snowman);
         if (!_snowmanx.isEmpty() && EnchantmentHelper.hasVanishingCurse(_snowmanx)) {
            this.inventory.removeStack(_snowman);
         }
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      if (source == DamageSource.ON_FIRE) {
         return SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE;
      } else if (source == DamageSource.DROWN) {
         return SoundEvents.ENTITY_PLAYER_HURT_DROWN;
      } else {
         return source == DamageSource.SWEET_BERRY_BUSH ? SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH : SoundEvents.ENTITY_PLAYER_HURT;
      }
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_PLAYER_DEATH;
   }

   public boolean dropSelectedItem(boolean dropEntireStack) {
      return this.dropItem(
            this.inventory
               .removeStack(
                  this.inventory.selectedSlot,
                  dropEntireStack && !this.inventory.getMainHandStack().isEmpty() ? this.inventory.getMainHandStack().getCount() : 1
               ),
            false,
            true
         )
         != null;
   }

   @Nullable
   public ItemEntity dropItem(ItemStack stack, boolean retainOwnership) {
      return this.dropItem(stack, false, retainOwnership);
   }

   @Nullable
   public ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
      if (stack.isEmpty()) {
         return null;
      } else {
         if (this.world.isClient) {
            this.swingHand(Hand.MAIN_HAND);
         }

         double _snowman = this.getEyeY() - 0.3F;
         ItemEntity _snowmanx = new ItemEntity(this.world, this.getX(), _snowman, this.getZ(), stack);
         _snowmanx.setPickupDelay(40);
         if (retainOwnership) {
            _snowmanx.setThrower(this.getUuid());
         }

         if (throwRandomly) {
            float _snowmanxx = this.random.nextFloat() * 0.5F;
            float _snowmanxxx = this.random.nextFloat() * (float) (Math.PI * 2);
            _snowmanx.setVelocity((double)(-MathHelper.sin(_snowmanxxx) * _snowmanxx), 0.2F, (double)(MathHelper.cos(_snowmanxxx) * _snowmanxx));
         } else {
            float _snowmanxx = 0.3F;
            float _snowmanxxx = MathHelper.sin(this.pitch * (float) (Math.PI / 180.0));
            float _snowmanxxxx = MathHelper.cos(this.pitch * (float) (Math.PI / 180.0));
            float _snowmanxxxxx = MathHelper.sin(this.yaw * (float) (Math.PI / 180.0));
            float _snowmanxxxxxx = MathHelper.cos(this.yaw * (float) (Math.PI / 180.0));
            float _snowmanxxxxxxx = this.random.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxxxxxxx = 0.02F * this.random.nextFloat();
            _snowmanx.setVelocity(
               (double)(-_snowmanxxxxx * _snowmanxxxx * 0.3F) + Math.cos((double)_snowmanxxxxxxx) * (double)_snowmanxxxxxxxx,
               (double)(-_snowmanxxx * 0.3F + 0.1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F),
               (double)(_snowmanxxxxxx * _snowmanxxxx * 0.3F) + Math.sin((double)_snowmanxxxxxxx) * (double)_snowmanxxxxxxxx
            );
         }

         return _snowmanx;
      }
   }

   public float getBlockBreakingSpeed(BlockState block) {
      float _snowman = this.inventory.getBlockBreakingSpeed(block);
      if (_snowman > 1.0F) {
         int _snowmanx = EnchantmentHelper.getEfficiency(this);
         ItemStack _snowmanxx = this.getMainHandStack();
         if (_snowmanx > 0 && !_snowmanxx.isEmpty()) {
            _snowman += (float)(_snowmanx * _snowmanx + 1);
         }
      }

      if (StatusEffectUtil.hasHaste(this)) {
         _snowman *= 1.0F + (float)(StatusEffectUtil.getHasteAmplifier(this) + 1) * 0.2F;
      }

      if (this.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
         float _snowmanx;
         switch (this.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) {
            case 0:
               _snowmanx = 0.3F;
               break;
            case 1:
               _snowmanx = 0.09F;
               break;
            case 2:
               _snowmanx = 0.0027F;
               break;
            case 3:
            default:
               _snowmanx = 8.1E-4F;
         }

         _snowman *= _snowmanx;
      }

      if (this.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
         _snowman /= 5.0F;
      }

      if (!this.onGround) {
         _snowman /= 5.0F;
      }

      return _snowman;
   }

   public boolean isUsingEffectiveTool(BlockState block) {
      return !block.isToolRequired() || this.inventory.getMainHandStack().isEffectiveOn(block);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setUuid(getUuidFromProfile(this.gameProfile));
      ListTag _snowman = tag.getList("Inventory", 10);
      this.inventory.deserialize(_snowman);
      this.inventory.selectedSlot = tag.getInt("SelectedItemSlot");
      this.sleepTimer = tag.getShort("SleepTimer");
      this.experienceProgress = tag.getFloat("XpP");
      this.experienceLevel = tag.getInt("XpLevel");
      this.totalExperience = tag.getInt("XpTotal");
      this.enchantmentTableSeed = tag.getInt("XpSeed");
      if (this.enchantmentTableSeed == 0) {
         this.enchantmentTableSeed = this.random.nextInt();
      }

      this.setScore(tag.getInt("Score"));
      this.hungerManager.fromTag(tag);
      this.abilities.deserialize(tag);
      this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue((double)this.abilities.getWalkSpeed());
      if (tag.contains("EnderItems", 9)) {
         this.enderChestInventory.readTags(tag.getList("EnderItems", 10));
      }

      if (tag.contains("ShoulderEntityLeft", 10)) {
         this.setShoulderEntityLeft(tag.getCompound("ShoulderEntityLeft"));
      }

      if (tag.contains("ShoulderEntityRight", 10)) {
         this.setShoulderEntityRight(tag.getCompound("ShoulderEntityRight"));
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      tag.put("Inventory", this.inventory.serialize(new ListTag()));
      tag.putInt("SelectedItemSlot", this.inventory.selectedSlot);
      tag.putShort("SleepTimer", (short)this.sleepTimer);
      tag.putFloat("XpP", this.experienceProgress);
      tag.putInt("XpLevel", this.experienceLevel);
      tag.putInt("XpTotal", this.totalExperience);
      tag.putInt("XpSeed", this.enchantmentTableSeed);
      tag.putInt("Score", this.getScore());
      this.hungerManager.toTag(tag);
      this.abilities.serialize(tag);
      tag.put("EnderItems", this.enderChestInventory.getTags());
      if (!this.getShoulderEntityLeft().isEmpty()) {
         tag.put("ShoulderEntityLeft", this.getShoulderEntityLeft());
      }

      if (!this.getShoulderEntityRight().isEmpty()) {
         tag.put("ShoulderEntityRight", this.getShoulderEntityRight());
      }
   }

   @Override
   public boolean isInvulnerableTo(DamageSource damageSource) {
      if (super.isInvulnerableTo(damageSource)) {
         return true;
      } else if (damageSource == DamageSource.DROWN) {
         return !this.world.getGameRules().getBoolean(GameRules.DROWNING_DAMAGE);
      } else if (damageSource == DamageSource.FALL) {
         return !this.world.getGameRules().getBoolean(GameRules.FALL_DAMAGE);
      } else {
         return damageSource.isFire() ? !this.world.getGameRules().getBoolean(GameRules.FIRE_DAMAGE) : false;
      }
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (this.abilities.invulnerable && !source.isOutOfWorld()) {
         return false;
      } else {
         this.despawnCounter = 0;
         if (this.isDead()) {
            return false;
         } else {
            this.dropShoulderEntities();
            if (source.isScaledWithDifficulty()) {
               if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                  amount = 0.0F;
               }

               if (this.world.getDifficulty() == Difficulty.EASY) {
                  amount = Math.min(amount / 2.0F + 1.0F, amount);
               }

               if (this.world.getDifficulty() == Difficulty.HARD) {
                  amount = amount * 3.0F / 2.0F;
               }
            }

            return amount == 0.0F ? false : super.damage(source, amount);
         }
      }
   }

   @Override
   protected void takeShieldHit(LivingEntity attacker) {
      super.takeShieldHit(attacker);
      if (attacker.getMainHandStack().getItem() instanceof AxeItem) {
         this.disableShield(true);
      }
   }

   public boolean shouldDamagePlayer(PlayerEntity player) {
      AbstractTeam _snowman = this.getScoreboardTeam();
      AbstractTeam _snowmanx = player.getScoreboardTeam();
      if (_snowman == null) {
         return true;
      } else {
         return !_snowman.isEqual(_snowmanx) ? true : _snowman.isFriendlyFireAllowed();
      }
   }

   @Override
   protected void damageArmor(DamageSource source, float amount) {
      this.inventory.damageArmor(source, amount);
   }

   @Override
   protected void damageShield(float amount) {
      if (this.activeItemStack.getItem() == Items.SHIELD) {
         if (!this.world.isClient) {
            this.incrementStat(Stats.USED.getOrCreateStat(this.activeItemStack.getItem()));
         }

         if (amount >= 3.0F) {
            int _snowman = 1 + MathHelper.floor(amount);
            Hand _snowmanx = this.getActiveHand();
            this.activeItemStack.damage(_snowman, this, _snowmanxx -> _snowmanxx.sendToolBreakStatus(_snowman));
            if (this.activeItemStack.isEmpty()) {
               if (_snowmanx == Hand.MAIN_HAND) {
                  this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
               } else {
                  this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
               }

               this.activeItemStack = ItemStack.EMPTY;
               this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);
            }
         }
      }
   }

   @Override
   protected void applyDamage(DamageSource source, float amount) {
      if (!this.isInvulnerableTo(source)) {
         amount = this.applyArmorToDamage(source, amount);
         amount = this.applyEnchantmentsToDamage(source, amount);
         float var8 = Math.max(amount - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (amount - var8));
         float _snowman = amount - var8;
         if (_snowman > 0.0F && _snowman < 3.4028235E37F) {
            this.increaseStat(Stats.DAMAGE_ABSORBED, Math.round(_snowman * 10.0F));
         }

         if (var8 != 0.0F) {
            this.addExhaustion(source.getExhaustion());
            float _snowmanx = this.getHealth();
            this.setHealth(this.getHealth() - var8);
            this.getDamageTracker().onDamage(source, _snowmanx, var8);
            if (var8 < 3.4028235E37F) {
               this.increaseStat(Stats.DAMAGE_TAKEN, Math.round(var8 * 10.0F));
            }
         }
      }
   }

   @Override
   protected boolean isOnSoulSpeedBlock() {
      return !this.abilities.flying && super.isOnSoulSpeedBlock();
   }

   public void openEditSignScreen(SignBlockEntity sign) {
   }

   public void openCommandBlockMinecartScreen(CommandBlockExecutor commandBlockExecutor) {
   }

   public void openCommandBlockScreen(CommandBlockBlockEntity commandBlock) {
   }

   public void openStructureBlockScreen(StructureBlockBlockEntity structureBlock) {
   }

   public void openJigsawScreen(JigsawBlockEntity jigsaw) {
   }

   public void openHorseInventory(HorseBaseEntity horse, Inventory inventory) {
   }

   public OptionalInt openHandledScreen(@Nullable NamedScreenHandlerFactory factory) {
      return OptionalInt.empty();
   }

   public void sendTradeOffers(int syncId, TradeOfferList offers, int levelProgress, int experience, boolean leveled, boolean refreshable) {
   }

   public void openEditBookScreen(ItemStack book, Hand hand) {
   }

   public ActionResult interact(Entity entity, Hand hand) {
      if (this.isSpectator()) {
         if (entity instanceof NamedScreenHandlerFactory) {
            this.openHandledScreen((NamedScreenHandlerFactory)entity);
         }

         return ActionResult.PASS;
      } else {
         ItemStack _snowman = this.getStackInHand(hand);
         ItemStack _snowmanx = _snowman.copy();
         ActionResult _snowmanxx = entity.interact(this, hand);
         if (_snowmanxx.isAccepted()) {
            if (this.abilities.creativeMode && _snowman == this.getStackInHand(hand) && _snowman.getCount() < _snowmanx.getCount()) {
               _snowman.setCount(_snowmanx.getCount());
            }

            return _snowmanxx;
         } else {
            if (!_snowman.isEmpty() && entity instanceof LivingEntity) {
               if (this.abilities.creativeMode) {
                  _snowman = _snowmanx;
               }

               ActionResult _snowmanxxx = _snowman.useOnEntity(this, (LivingEntity)entity, hand);
               if (_snowmanxxx.isAccepted()) {
                  if (_snowman.isEmpty() && !this.abilities.creativeMode) {
                     this.setStackInHand(hand, ItemStack.EMPTY);
                  }

                  return _snowmanxxx;
               }
            }

            return ActionResult.PASS;
         }
      }
   }

   @Override
   public double getHeightOffset() {
      return -0.35;
   }

   @Override
   public void method_29239() {
      super.method_29239();
      this.ridingCooldown = 0;
   }

   @Override
   protected boolean isImmobile() {
      return super.isImmobile() || this.isSleeping();
   }

   @Override
   public boolean method_29920() {
      return !this.abilities.flying;
   }

   @Override
   protected Vec3d adjustMovementForSneaking(Vec3d movement, MovementType type) {
      if (!this.abilities.flying && (type == MovementType.SELF || type == MovementType.PLAYER) && this.clipAtLedge() && this.method_30263()) {
         double _snowman = movement.x;
         double _snowmanx = movement.z;
         double _snowmanxx = 0.05;

         while (_snowman != 0.0 && this.world.isSpaceEmpty(this, this.getBoundingBox().offset(_snowman, (double)(-this.stepHeight), 0.0))) {
            if (_snowman < 0.05 && _snowman >= -0.05) {
               _snowman = 0.0;
            } else if (_snowman > 0.0) {
               _snowman -= 0.05;
            } else {
               _snowman += 0.05;
            }
         }

         while (_snowmanx != 0.0 && this.world.isSpaceEmpty(this, this.getBoundingBox().offset(0.0, (double)(-this.stepHeight), _snowmanx))) {
            if (_snowmanx < 0.05 && _snowmanx >= -0.05) {
               _snowmanx = 0.0;
            } else if (_snowmanx > 0.0) {
               _snowmanx -= 0.05;
            } else {
               _snowmanx += 0.05;
            }
         }

         while (_snowman != 0.0 && _snowmanx != 0.0 && this.world.isSpaceEmpty(this, this.getBoundingBox().offset(_snowman, (double)(-this.stepHeight), _snowmanx))) {
            if (_snowman < 0.05 && _snowman >= -0.05) {
               _snowman = 0.0;
            } else if (_snowman > 0.0) {
               _snowman -= 0.05;
            } else {
               _snowman += 0.05;
            }

            if (_snowmanx < 0.05 && _snowmanx >= -0.05) {
               _snowmanx = 0.0;
            } else if (_snowmanx > 0.0) {
               _snowmanx -= 0.05;
            } else {
               _snowmanx += 0.05;
            }
         }

         movement = new Vec3d(_snowman, movement.y, _snowmanx);
      }

      return movement;
   }

   private boolean method_30263() {
      return this.onGround
         || this.fallDistance < this.stepHeight
            && !this.world.isSpaceEmpty(this, this.getBoundingBox().offset(0.0, (double)(this.fallDistance - this.stepHeight), 0.0));
   }

   public void attack(Entity target) {
      if (target.isAttackable()) {
         if (!target.handleAttack(this)) {
            float _snowman = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            float _snowmanx;
            if (target instanceof LivingEntity) {
               _snowmanx = EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
            } else {
               _snowmanx = EnchantmentHelper.getAttackDamage(this.getMainHandStack(), EntityGroup.DEFAULT);
            }

            float _snowmanxx = this.getAttackCooldownProgress(0.5F);
            _snowman *= 0.2F + _snowmanxx * _snowmanxx * 0.8F;
            _snowmanx *= _snowmanxx;
            this.resetLastAttackedTicks();
            if (_snowman > 0.0F || _snowmanx > 0.0F) {
               boolean _snowmanxxx = _snowmanxx > 0.9F;
               boolean _snowmanxxxx = false;
               int _snowmanxxxxx = 0;
               _snowmanxxxxx += EnchantmentHelper.getKnockback(this);
               if (this.isSprinting() && _snowmanxxx) {
                  this.world
                     .playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, this.getSoundCategory(), 1.0F, 1.0F);
                  _snowmanxxxxx++;
                  _snowmanxxxx = true;
               }

               boolean _snowmanxxxxxx = _snowmanxxx
                  && this.fallDistance > 0.0F
                  && !this.onGround
                  && !this.isClimbing()
                  && !this.isTouchingWater()
                  && !this.hasStatusEffect(StatusEffects.BLINDNESS)
                  && !this.hasVehicle()
                  && target instanceof LivingEntity;
               _snowmanxxxxxx = _snowmanxxxxxx && !this.isSprinting();
               if (_snowmanxxxxxx) {
                  _snowman *= 1.5F;
               }

               _snowman += _snowmanx;
               boolean _snowmanxxxxxxx = false;
               double _snowmanxxxxxxxx = (double)(this.horizontalSpeed - this.prevHorizontalSpeed);
               if (_snowmanxxx && !_snowmanxxxxxx && !_snowmanxxxx && this.onGround && _snowmanxxxxxxxx < (double)this.getMovementSpeed()) {
                  ItemStack _snowmanxxxxxxxxx = this.getStackInHand(Hand.MAIN_HAND);
                  if (_snowmanxxxxxxxxx.getItem() instanceof SwordItem) {
                     _snowmanxxxxxxx = true;
                  }
               }

               float _snowmanxxxxxxxxx = 0.0F;
               boolean _snowmanxxxxxxxxxx = false;
               int _snowmanxxxxxxxxxxx = EnchantmentHelper.getFireAspect(this);
               if (target instanceof LivingEntity) {
                  _snowmanxxxxxxxxx = ((LivingEntity)target).getHealth();
                  if (_snowmanxxxxxxxxxxx > 0 && !target.isOnFire()) {
                     _snowmanxxxxxxxxxx = true;
                     target.setOnFireFor(1);
                  }
               }

               Vec3d _snowmanxxxxxxxxxxxx = target.getVelocity();
               boolean _snowmanxxxxxxxxxxxxx = target.damage(DamageSource.player(this), _snowman);
               if (_snowmanxxxxxxxxxxxxx) {
                  if (_snowmanxxxxx > 0) {
                     if (target instanceof LivingEntity) {
                        ((LivingEntity)target)
                           .takeKnockback(
                              (float)_snowmanxxxxx * 0.5F,
                              (double)MathHelper.sin(this.yaw * (float) (Math.PI / 180.0)),
                              (double)(-MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)))
                           );
                     } else {
                        target.addVelocity(
                           (double)(-MathHelper.sin(this.yaw * (float) (Math.PI / 180.0)) * (float)_snowmanxxxxx * 0.5F),
                           0.1,
                           (double)(MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)) * (float)_snowmanxxxxx * 0.5F)
                        );
                     }

                     this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
                     this.setSprinting(false);
                  }

                  if (_snowmanxxxxxxx) {
                     float _snowmanxxxxxxxxxxxxxx = 1.0F + EnchantmentHelper.getSweepingMultiplier(this) * _snowman;

                     for (LivingEntity _snowmanxxxxxxxxxxxxxxx : this.world
                        .getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
                        if (_snowmanxxxxxxxxxxxxxxx != this
                           && _snowmanxxxxxxxxxxxxxxx != target
                           && !this.isTeammate(_snowmanxxxxxxxxxxxxxxx)
                           && (!(_snowmanxxxxxxxxxxxxxxx instanceof ArmorStandEntity) || !((ArmorStandEntity)_snowmanxxxxxxxxxxxxxxx).isMarker())
                           && this.squaredDistanceTo(_snowmanxxxxxxxxxxxxxxx) < 9.0) {
                           _snowmanxxxxxxxxxxxxxxx.takeKnockback(
                              0.4F,
                              (double)MathHelper.sin(this.yaw * (float) (Math.PI / 180.0)),
                              (double)(-MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)))
                           );
                           _snowmanxxxxxxxxxxxxxxx.damage(DamageSource.player(this), _snowmanxxxxxxxxxxxxxx);
                        }
                     }

                     this.world
                        .playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                     this.spawnSweepAttackParticles();
                  }

                  if (target instanceof ServerPlayerEntity && target.velocityModified) {
                     ((ServerPlayerEntity)target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
                     target.velocityModified = false;
                     target.setVelocity(_snowmanxxxxxxxxxxxx);
                  }

                  if (_snowmanxxxxxx) {
                     this.world
                        .playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0F, 1.0F);
                     this.addCritParticles(target);
                  }

                  if (!_snowmanxxxxxx && !_snowmanxxxxxxx) {
                     if (_snowmanxxx) {
                        this.world
                           .playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0F, 1.0F);
                     } else {
                        this.world
                           .playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0F, 1.0F);
                     }
                  }

                  if (_snowmanx > 0.0F) {
                     this.addEnchantedHitParticles(target);
                  }

                  this.onAttacking(target);
                  if (target instanceof LivingEntity) {
                     EnchantmentHelper.onUserDamaged((LivingEntity)target, this);
                  }

                  EnchantmentHelper.onTargetDamaged(this, target);
                  ItemStack _snowmanxxxxxxxxxxxxxx = this.getMainHandStack();
                  Entity _snowmanxxxxxxxxxxxxxxxx = target;
                  if (target instanceof EnderDragonPart) {
                     _snowmanxxxxxxxxxxxxxxxx = ((EnderDragonPart)target).owner;
                  }

                  if (!this.world.isClient && !_snowmanxxxxxxxxxxxxxx.isEmpty() && _snowmanxxxxxxxxxxxxxxxx instanceof LivingEntity) {
                     _snowmanxxxxxxxxxxxxxx.postHit((LivingEntity)_snowmanxxxxxxxxxxxxxxxx, this);
                     if (_snowmanxxxxxxxxxxxxxx.isEmpty()) {
                        this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                     }
                  }

                  if (target instanceof LivingEntity) {
                     float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - ((LivingEntity)target).getHealth();
                     this.increaseStat(Stats.DAMAGE_DEALT, Math.round(_snowmanxxxxxxxxxxxxxxxxx * 10.0F));
                     if (_snowmanxxxxxxxxxxx > 0) {
                        target.setOnFireFor(_snowmanxxxxxxxxxxx * 4);
                     }

                     if (this.world instanceof ServerWorld && _snowmanxxxxxxxxxxxxxxxxx > 2.0F) {
                        int _snowmanxxxxxxxxxxxxxxxxxx = (int)((double)_snowmanxxxxxxxxxxxxxxxxx * 0.5);
                        ((ServerWorld)this.world)
                           .spawnParticles(
                              ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5), target.getZ(), _snowmanxxxxxxxxxxxxxxxxxx, 0.1, 0.0, 0.1, 0.2
                           );
                     }
                  }

                  this.addExhaustion(0.1F);
               } else {
                  this.world
                     .playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, this.getSoundCategory(), 1.0F, 1.0F);
                  if (_snowmanxxxxxxxxxx) {
                     target.extinguish();
                  }
               }
            }
         }
      }
   }

   @Override
   protected void attackLivingEntity(LivingEntity target) {
      this.attack(target);
   }

   public void disableShield(boolean sprinting) {
      float _snowman = 0.25F + (float)EnchantmentHelper.getEfficiency(this) * 0.05F;
      if (sprinting) {
         _snowman += 0.75F;
      }

      if (this.random.nextFloat() < _snowman) {
         this.getItemCooldownManager().set(Items.SHIELD, 100);
         this.clearActiveItem();
         this.world.sendEntityStatus(this, (byte)30);
      }
   }

   public void addCritParticles(Entity target) {
   }

   public void addEnchantedHitParticles(Entity target) {
   }

   public void spawnSweepAttackParticles() {
      double _snowman = (double)(-MathHelper.sin(this.yaw * (float) (Math.PI / 180.0)));
      double _snowmanx = (double)MathHelper.cos(this.yaw * (float) (Math.PI / 180.0));
      if (this.world instanceof ServerWorld) {
         ((ServerWorld)this.world).spawnParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + _snowman, this.getBodyY(0.5), this.getZ() + _snowmanx, 0, _snowman, 0.0, _snowmanx, 0.0);
      }
   }

   public void requestRespawn() {
   }

   @Override
   public void remove() {
      super.remove();
      this.playerScreenHandler.close(this);
      if (this.currentScreenHandler != null) {
         this.currentScreenHandler.close(this);
      }
   }

   public boolean isMainPlayer() {
      return false;
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public Either<PlayerEntity.SleepFailureReason, Unit> trySleep(BlockPos pos) {
      this.sleep(pos);
      this.sleepTimer = 0;
      return Either.right(Unit.INSTANCE);
   }

   public void wakeUp(boolean _snowman, boolean updateSleepingPlayers) {
      super.wakeUp();
      if (this.world instanceof ServerWorld && updateSleepingPlayers) {
         ((ServerWorld)this.world).updateSleepingPlayers();
      }

      this.sleepTimer = _snowman ? 0 : 100;
   }

   @Override
   public void wakeUp() {
      this.wakeUp(true, true);
   }

   public static Optional<Vec3d> findRespawnPosition(ServerWorld world, BlockPos pos, float _snowman, boolean _snowman, boolean _snowman) {
      BlockState _snowmanxxx = world.getBlockState(pos);
      Block _snowmanxxxx = _snowmanxxx.getBlock();
      if (_snowmanxxxx instanceof RespawnAnchorBlock && _snowmanxxx.get(RespawnAnchorBlock.CHARGES) > 0 && RespawnAnchorBlock.isNether(world)) {
         Optional<Vec3d> _snowmanxxxxx = RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
         if (!_snowman && _snowmanxxxxx.isPresent()) {
            world.setBlockState(pos, _snowmanxxx.with(RespawnAnchorBlock.CHARGES, Integer.valueOf(_snowmanxxx.get(RespawnAnchorBlock.CHARGES) - 1)), 3);
         }

         return _snowmanxxxxx;
      } else if (_snowmanxxxx instanceof BedBlock && BedBlock.isOverworld(world)) {
         return BedBlock.findWakeUpPosition(EntityType.PLAYER, world, pos, _snowman);
      } else if (!_snowman) {
         return Optional.empty();
      } else {
         boolean _snowmanxxxxx = _snowmanxxxx.canMobSpawnInside();
         boolean _snowmanxxxxxx = world.getBlockState(pos.up()).getBlock().canMobSpawnInside();
         return _snowmanxxxxx && _snowmanxxxxxx ? Optional.of(new Vec3d((double)pos.getX() + 0.5, (double)pos.getY() + 0.1, (double)pos.getZ() + 0.5)) : Optional.empty();
      }
   }

   public boolean isSleepingLongEnough() {
      return this.isSleeping() && this.sleepTimer >= 100;
   }

   public int getSleepTimer() {
      return this.sleepTimer;
   }

   public void sendMessage(Text message, boolean actionBar) {
   }

   public void incrementStat(Identifier stat) {
      this.incrementStat(Stats.CUSTOM.getOrCreateStat(stat));
   }

   public void increaseStat(Identifier stat, int amount) {
      this.increaseStat(Stats.CUSTOM.getOrCreateStat(stat), amount);
   }

   public void incrementStat(Stat<?> stat) {
      this.increaseStat(stat, 1);
   }

   public void increaseStat(Stat<?> stat, int amount) {
   }

   public void resetStat(Stat<?> stat) {
   }

   public int unlockRecipes(Collection<Recipe<?>> recipes) {
      return 0;
   }

   public void unlockRecipes(Identifier[] ids) {
   }

   public int lockRecipes(Collection<Recipe<?>> recipes) {
      return 0;
   }

   @Override
   public void jump() {
      super.jump();
      this.incrementStat(Stats.JUMP);
      if (this.isSprinting()) {
         this.addExhaustion(0.2F);
      } else {
         this.addExhaustion(0.05F);
      }
   }

   @Override
   public void travel(Vec3d movementInput) {
      double _snowman = this.getX();
      double _snowmanx = this.getY();
      double _snowmanxx = this.getZ();
      if (this.isSwimming() && !this.hasVehicle()) {
         double _snowmanxxx = this.getRotationVector().y;
         double _snowmanxxxx = _snowmanxxx < -0.2 ? 0.085 : 0.06;
         if (_snowmanxxx <= 0.0
            || this.jumping
            || !this.world.getBlockState(new BlockPos(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())).getFluidState().isEmpty()) {
            Vec3d _snowmanxxxxx = this.getVelocity();
            this.setVelocity(_snowmanxxxxx.add(0.0, (_snowmanxxx - _snowmanxxxxx.y) * _snowmanxxxx, 0.0));
         }
      }

      if (this.abilities.flying && !this.hasVehicle()) {
         double _snowmanxxx = this.getVelocity().y;
         float _snowmanxxxx = this.flyingSpeed;
         this.flyingSpeed = this.abilities.getFlySpeed() * (float)(this.isSprinting() ? 2 : 1);
         super.travel(movementInput);
         Vec3d _snowmanxxxxx = this.getVelocity();
         this.setVelocity(_snowmanxxxxx.x, _snowmanxxx * 0.6, _snowmanxxxxx.z);
         this.flyingSpeed = _snowmanxxxx;
         this.fallDistance = 0.0F;
         this.setFlag(7, false);
      } else {
         super.travel(movementInput);
      }

      this.increaseTravelMotionStats(this.getX() - _snowman, this.getY() - _snowmanx, this.getZ() - _snowmanxx);
   }

   @Override
   public void updateSwimming() {
      if (this.abilities.flying) {
         this.setSwimming(false);
      } else {
         super.updateSwimming();
      }
   }

   protected boolean doesNotSuffocate(BlockPos pos) {
      return !this.world.getBlockState(pos).shouldSuffocate(this.world, pos);
   }

   @Override
   public float getMovementSpeed() {
      return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
   }

   public void increaseTravelMotionStats(double dx, double dy, double dz) {
      if (!this.hasVehicle()) {
         if (this.isSwimming()) {
            int _snowman = Math.round(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) * 100.0F);
            if (_snowman > 0) {
               this.increaseStat(Stats.SWIM_ONE_CM, _snowman);
               this.addExhaustion(0.01F * (float)_snowman * 0.01F);
            }
         } else if (this.isSubmergedIn(FluidTags.WATER)) {
            int _snowman = Math.round(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) * 100.0F);
            if (_snowman > 0) {
               this.increaseStat(Stats.WALK_UNDER_WATER_ONE_CM, _snowman);
               this.addExhaustion(0.01F * (float)_snowman * 0.01F);
            }
         } else if (this.isTouchingWater()) {
            int _snowman = Math.round(MathHelper.sqrt(dx * dx + dz * dz) * 100.0F);
            if (_snowman > 0) {
               this.increaseStat(Stats.WALK_ON_WATER_ONE_CM, _snowman);
               this.addExhaustion(0.01F * (float)_snowman * 0.01F);
            }
         } else if (this.isClimbing()) {
            if (dy > 0.0) {
               this.increaseStat(Stats.CLIMB_ONE_CM, (int)Math.round(dy * 100.0));
            }
         } else if (this.onGround) {
            int _snowman = Math.round(MathHelper.sqrt(dx * dx + dz * dz) * 100.0F);
            if (_snowman > 0) {
               if (this.isSprinting()) {
                  this.increaseStat(Stats.SPRINT_ONE_CM, _snowman);
                  this.addExhaustion(0.1F * (float)_snowman * 0.01F);
               } else if (this.isInSneakingPose()) {
                  this.increaseStat(Stats.CROUCH_ONE_CM, _snowman);
                  this.addExhaustion(0.0F * (float)_snowman * 0.01F);
               } else {
                  this.increaseStat(Stats.WALK_ONE_CM, _snowman);
                  this.addExhaustion(0.0F * (float)_snowman * 0.01F);
               }
            }
         } else if (this.isFallFlying()) {
            int _snowman = Math.round(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) * 100.0F);
            this.increaseStat(Stats.AVIATE_ONE_CM, _snowman);
         } else {
            int _snowman = Math.round(MathHelper.sqrt(dx * dx + dz * dz) * 100.0F);
            if (_snowman > 25) {
               this.increaseStat(Stats.FLY_ONE_CM, _snowman);
            }
         }
      }
   }

   private void increaseRidingMotionStats(double dx, double dy, double dz) {
      if (this.hasVehicle()) {
         int _snowman = Math.round(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) * 100.0F);
         if (_snowman > 0) {
            Entity _snowmanx = this.getVehicle();
            if (_snowmanx instanceof AbstractMinecartEntity) {
               this.increaseStat(Stats.MINECART_ONE_CM, _snowman);
            } else if (_snowmanx instanceof BoatEntity) {
               this.increaseStat(Stats.BOAT_ONE_CM, _snowman);
            } else if (_snowmanx instanceof PigEntity) {
               this.increaseStat(Stats.PIG_ONE_CM, _snowman);
            } else if (_snowmanx instanceof HorseBaseEntity) {
               this.increaseStat(Stats.HORSE_ONE_CM, _snowman);
            } else if (_snowmanx instanceof StriderEntity) {
               this.increaseStat(Stats.STRIDER_ONE_CM, _snowman);
            }
         }
      }
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      if (this.abilities.allowFlying) {
         return false;
      } else {
         if (fallDistance >= 2.0F) {
            this.increaseStat(Stats.FALL_ONE_CM, (int)Math.round((double)fallDistance * 100.0));
         }

         return super.handleFallDamage(fallDistance, damageMultiplier);
      }
   }

   public boolean checkFallFlying() {
      if (!this.onGround && !this.isFallFlying() && !this.isTouchingWater() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {
         ItemStack _snowman = this.getEquippedStack(EquipmentSlot.CHEST);
         if (_snowman.getItem() == Items.ELYTRA && ElytraItem.isUsable(_snowman)) {
            this.startFallFlying();
            return true;
         }
      }

      return false;
   }

   public void startFallFlying() {
      this.setFlag(7, true);
   }

   public void stopFallFlying() {
      this.setFlag(7, true);
      this.setFlag(7, false);
   }

   @Override
   protected void onSwimmingStart() {
      if (!this.isSpectator()) {
         super.onSwimmingStart();
      }
   }

   @Override
   protected SoundEvent getFallSound(int distance) {
      return distance > 4 ? SoundEvents.ENTITY_PLAYER_BIG_FALL : SoundEvents.ENTITY_PLAYER_SMALL_FALL;
   }

   @Override
   public void onKilledOther(ServerWorld _snowman, LivingEntity _snowman) {
      this.incrementStat(Stats.KILLED.getOrCreateStat(_snowman.getType()));
   }

   @Override
   public void slowMovement(BlockState state, Vec3d multiplier) {
      if (!this.abilities.flying) {
         super.slowMovement(state, multiplier);
      }
   }

   public void addExperience(int experience) {
      this.addScore(experience);
      this.experienceProgress = this.experienceProgress + (float)experience / (float)this.getNextLevelExperience();
      this.totalExperience = MathHelper.clamp(this.totalExperience + experience, 0, Integer.MAX_VALUE);

      while (this.experienceProgress < 0.0F) {
         float _snowman = this.experienceProgress * (float)this.getNextLevelExperience();
         if (this.experienceLevel > 0) {
            this.addExperienceLevels(-1);
            this.experienceProgress = 1.0F + _snowman / (float)this.getNextLevelExperience();
         } else {
            this.addExperienceLevels(-1);
            this.experienceProgress = 0.0F;
         }
      }

      while (this.experienceProgress >= 1.0F) {
         this.experienceProgress = (this.experienceProgress - 1.0F) * (float)this.getNextLevelExperience();
         this.addExperienceLevels(1);
         this.experienceProgress = this.experienceProgress / (float)this.getNextLevelExperience();
      }
   }

   public int getEnchantmentTableSeed() {
      return this.enchantmentTableSeed;
   }

   public void applyEnchantmentCosts(ItemStack enchantedItem, int experienceLevels) {
      this.experienceLevel -= experienceLevels;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experienceProgress = 0.0F;
         this.totalExperience = 0;
      }

      this.enchantmentTableSeed = this.random.nextInt();
   }

   public void addExperienceLevels(int levels) {
      this.experienceLevel += levels;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experienceProgress = 0.0F;
         this.totalExperience = 0;
      }

      if (levels > 0 && this.experienceLevel % 5 == 0 && (float)this.lastPlayedLevelUpSoundTime < (float)this.age - 100.0F) {
         float _snowman = this.experienceLevel > 30 ? 1.0F : (float)this.experienceLevel / 30.0F;
         this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), _snowman * 0.75F, 1.0F);
         this.lastPlayedLevelUpSoundTime = this.age;
      }
   }

   public int getNextLevelExperience() {
      if (this.experienceLevel >= 30) {
         return 112 + (this.experienceLevel - 30) * 9;
      } else {
         return this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2;
      }
   }

   public void addExhaustion(float exhaustion) {
      if (!this.abilities.invulnerable) {
         if (!this.world.isClient) {
            this.hungerManager.addExhaustion(exhaustion);
         }
      }
   }

   public HungerManager getHungerManager() {
      return this.hungerManager;
   }

   public boolean canConsume(boolean ignoreHunger) {
      return this.abilities.invulnerable || ignoreHunger || this.hungerManager.isNotFull();
   }

   public boolean canFoodHeal() {
      return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
   }

   public boolean canModifyBlocks() {
      return this.abilities.allowModifyWorld;
   }

   public boolean canPlaceOn(BlockPos pos, Direction facing, ItemStack stack) {
      if (this.abilities.allowModifyWorld) {
         return true;
      } else {
         BlockPos _snowman = pos.offset(facing.getOpposite());
         CachedBlockPosition _snowmanx = new CachedBlockPosition(this.world, _snowman, false);
         return stack.canPlaceOn(this.world.getTagManager(), _snowmanx);
      }
   }

   @Override
   protected int getCurrentExperience(PlayerEntity player) {
      if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && !this.isSpectator()) {
         int _snowman = this.experienceLevel * 7;
         return _snowman > 100 ? 100 : _snowman;
      } else {
         return 0;
      }
   }

   @Override
   protected boolean shouldAlwaysDropXp() {
      return true;
   }

   @Override
   public boolean shouldRenderName() {
      return true;
   }

   @Override
   protected boolean canClimb() {
      return !this.abilities.flying && (!this.onGround || !this.isSneaky());
   }

   public void sendAbilitiesUpdate() {
   }

   public void setGameMode(GameMode gameMode) {
   }

   @Override
   public Text getName() {
      return new LiteralText(this.gameProfile.getName());
   }

   public EnderChestInventory getEnderChestInventory() {
      return this.enderChestInventory;
   }

   @Override
   public ItemStack getEquippedStack(EquipmentSlot slot) {
      if (slot == EquipmentSlot.MAINHAND) {
         return this.inventory.getMainHandStack();
      } else if (slot == EquipmentSlot.OFFHAND) {
         return this.inventory.offHand.get(0);
      } else {
         return slot.getType() == EquipmentSlot.Type.ARMOR ? this.inventory.armor.get(slot.getEntitySlotId()) : ItemStack.EMPTY;
      }
   }

   @Override
   public void equipStack(EquipmentSlot slot, ItemStack stack) {
      if (slot == EquipmentSlot.MAINHAND) {
         this.onEquipStack(stack);
         this.inventory.main.set(this.inventory.selectedSlot, stack);
      } else if (slot == EquipmentSlot.OFFHAND) {
         this.onEquipStack(stack);
         this.inventory.offHand.set(0, stack);
      } else if (slot.getType() == EquipmentSlot.Type.ARMOR) {
         this.onEquipStack(stack);
         this.inventory.armor.set(slot.getEntitySlotId(), stack);
      }
   }

   public boolean giveItemStack(ItemStack stack) {
      this.onEquipStack(stack);
      return this.inventory.insertStack(stack);
   }

   @Override
   public Iterable<ItemStack> getItemsHand() {
      return Lists.newArrayList(new ItemStack[]{this.getMainHandStack(), this.getOffHandStack()});
   }

   @Override
   public Iterable<ItemStack> getArmorItems() {
      return this.inventory.armor;
   }

   public boolean addShoulderEntity(CompoundTag tag) {
      if (this.hasVehicle() || !this.onGround || this.isTouchingWater()) {
         return false;
      } else if (this.getShoulderEntityLeft().isEmpty()) {
         this.setShoulderEntityLeft(tag);
         this.shoulderEntityAddedTime = this.world.getTime();
         return true;
      } else if (this.getShoulderEntityRight().isEmpty()) {
         this.setShoulderEntityRight(tag);
         this.shoulderEntityAddedTime = this.world.getTime();
         return true;
      } else {
         return false;
      }
   }

   protected void dropShoulderEntities() {
      if (this.shoulderEntityAddedTime + 20L < this.world.getTime()) {
         this.dropShoulderEntity(this.getShoulderEntityLeft());
         this.setShoulderEntityLeft(new CompoundTag());
         this.dropShoulderEntity(this.getShoulderEntityRight());
         this.setShoulderEntityRight(new CompoundTag());
      }
   }

   private void dropShoulderEntity(CompoundTag entityNbt) {
      if (!this.world.isClient && !entityNbt.isEmpty()) {
         EntityType.getEntityFromTag(entityNbt, this.world).ifPresent(_snowman -> {
            if (_snowman instanceof TameableEntity) {
               ((TameableEntity)_snowman).setOwnerUuid(this.uuid);
            }

            _snowman.updatePosition(this.getX(), this.getY() + 0.7F, this.getZ());
            ((ServerWorld)this.world).tryLoadEntity(_snowman);
         });
      }
   }

   @Override
   public abstract boolean isSpectator();

   @Override
   public boolean isSwimming() {
      return !this.abilities.flying && !this.isSpectator() && super.isSwimming();
   }

   public abstract boolean isCreative();

   @Override
   public boolean canFly() {
      return !this.abilities.flying;
   }

   public Scoreboard getScoreboard() {
      return this.world.getScoreboard();
   }

   @Override
   public Text getDisplayName() {
      MutableText _snowman = Team.modifyText(this.getScoreboardTeam(), this.getName());
      return this.addTellClickEvent(_snowman);
   }

   private MutableText addTellClickEvent(MutableText component) {
      String _snowman = this.getGameProfile().getName();
      return component.styled(
         _snowmanx -> _snowmanx.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + _snowman + " ")).withHoverEvent(this.getHoverEvent()).withInsertion(_snowman)
      );
   }

   @Override
   public String getEntityName() {
      return this.getGameProfile().getName();
   }

   @Override
   public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      switch (pose) {
         case SWIMMING:
         case FALL_FLYING:
         case SPIN_ATTACK:
            return 0.4F;
         case CROUCHING:
            return 1.27F;
         default:
            return 1.62F;
      }
   }

   @Override
   public void setAbsorptionAmount(float amount) {
      if (amount < 0.0F) {
         amount = 0.0F;
      }

      this.getDataTracker().set(ABSORPTION_AMOUNT, amount);
   }

   @Override
   public float getAbsorptionAmount() {
      return this.getDataTracker().get(ABSORPTION_AMOUNT);
   }

   public static UUID getUuidFromProfile(GameProfile profile) {
      UUID _snowman = profile.getId();
      if (_snowman == null) {
         _snowman = getOfflinePlayerUuid(profile.getName());
      }

      return _snowman;
   }

   public static UUID getOfflinePlayerUuid(String nickname) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + nickname).getBytes(StandardCharsets.UTF_8));
   }

   public boolean isPartVisible(PlayerModelPart modelPart) {
      return (this.getDataTracker().get(PLAYER_MODEL_PARTS) & modelPart.getBitFlag()) == modelPart.getBitFlag();
   }

   @Override
   public boolean equip(int slot, ItemStack item) {
      if (slot >= 0 && slot < this.inventory.main.size()) {
         this.inventory.setStack(slot, item);
         return true;
      } else {
         EquipmentSlot _snowman;
         if (slot == 100 + EquipmentSlot.HEAD.getEntitySlotId()) {
            _snowman = EquipmentSlot.HEAD;
         } else if (slot == 100 + EquipmentSlot.CHEST.getEntitySlotId()) {
            _snowman = EquipmentSlot.CHEST;
         } else if (slot == 100 + EquipmentSlot.LEGS.getEntitySlotId()) {
            _snowman = EquipmentSlot.LEGS;
         } else if (slot == 100 + EquipmentSlot.FEET.getEntitySlotId()) {
            _snowman = EquipmentSlot.FEET;
         } else {
            _snowman = null;
         }

         if (slot == 98) {
            this.equipStack(EquipmentSlot.MAINHAND, item);
            return true;
         } else if (slot == 99) {
            this.equipStack(EquipmentSlot.OFFHAND, item);
            return true;
         } else if (_snowman == null) {
            int _snowmanx = slot - 200;
            if (_snowmanx >= 0 && _snowmanx < this.enderChestInventory.size()) {
               this.enderChestInventory.setStack(_snowmanx, item);
               return true;
            } else {
               return false;
            }
         } else {
            if (!item.isEmpty()) {
               if (!(item.getItem() instanceof ArmorItem) && !(item.getItem() instanceof ElytraItem)) {
                  if (_snowman != EquipmentSlot.HEAD) {
                     return false;
                  }
               } else if (MobEntity.getPreferredEquipmentSlot(item) != _snowman) {
                  return false;
               }
            }

            this.inventory.setStack(_snowman.getEntitySlotId() + this.inventory.main.size(), item);
            return true;
         }
      }
   }

   public boolean getReducedDebugInfo() {
      return this.reducedDebugInfo;
   }

   public void setReducedDebugInfo(boolean reducedDebugInfo) {
      this.reducedDebugInfo = reducedDebugInfo;
   }

   @Override
   public void setFireTicks(int ticks) {
      super.setFireTicks(this.abilities.invulnerable ? Math.min(ticks, 1) : ticks);
   }

   @Override
   public Arm getMainArm() {
      return this.dataTracker.get(MAIN_ARM) == 0 ? Arm.LEFT : Arm.RIGHT;
   }

   public void setMainArm(Arm arm) {
      this.dataTracker.set(MAIN_ARM, (byte)(arm == Arm.LEFT ? 0 : 1));
   }

   public CompoundTag getShoulderEntityLeft() {
      return this.dataTracker.get(LEFT_SHOULDER_ENTITY);
   }

   protected void setShoulderEntityLeft(CompoundTag entityTag) {
      this.dataTracker.set(LEFT_SHOULDER_ENTITY, entityTag);
   }

   public CompoundTag getShoulderEntityRight() {
      return this.dataTracker.get(RIGHT_SHOULDER_ENTITY);
   }

   protected void setShoulderEntityRight(CompoundTag entityTag) {
      this.dataTracker.set(RIGHT_SHOULDER_ENTITY, entityTag);
   }

   public float getAttackCooldownProgressPerTick() {
      return (float)(1.0 / this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) * 20.0);
   }

   public float getAttackCooldownProgress(float baseTime) {
      return MathHelper.clamp(((float)this.lastAttackedTicks + baseTime) / this.getAttackCooldownProgressPerTick(), 0.0F, 1.0F);
   }

   public void resetLastAttackedTicks() {
      this.lastAttackedTicks = 0;
   }

   public ItemCooldownManager getItemCooldownManager() {
      return this.itemCooldownManager;
   }

   @Override
   protected float getVelocityMultiplier() {
      return !this.abilities.flying && !this.isFallFlying() ? super.getVelocityMultiplier() : 1.0F;
   }

   public float getLuck() {
      return (float)this.getAttributeValue(EntityAttributes.GENERIC_LUCK);
   }

   public boolean isCreativeLevelTwoOp() {
      return this.abilities.creativeMode && this.getPermissionLevel() >= 2;
   }

   @Override
   public boolean canEquip(ItemStack stack) {
      EquipmentSlot _snowman = MobEntity.getPreferredEquipmentSlot(stack);
      return this.getEquippedStack(_snowman).isEmpty();
   }

   @Override
   public EntityDimensions getDimensions(EntityPose pose) {
      return POSE_DIMENSIONS.getOrDefault(pose, STANDING_DIMENSIONS);
   }

   @Override
   public ImmutableList<EntityPose> getPoses() {
      return ImmutableList.of(EntityPose.STANDING, EntityPose.CROUCHING, EntityPose.SWIMMING);
   }

   @Override
   public ItemStack getArrowType(ItemStack stack) {
      if (!(stack.getItem() instanceof RangedWeaponItem)) {
         return ItemStack.EMPTY;
      } else {
         Predicate<ItemStack> _snowman = ((RangedWeaponItem)stack.getItem()).getHeldProjectiles();
         ItemStack _snowmanx = RangedWeaponItem.getHeldProjectile(this, _snowman);
         if (!_snowmanx.isEmpty()) {
            return _snowmanx;
         } else {
            _snowman = ((RangedWeaponItem)stack.getItem()).getProjectiles();

            for (int _snowmanxx = 0; _snowmanxx < this.inventory.size(); _snowmanxx++) {
               ItemStack _snowmanxxx = this.inventory.getStack(_snowmanxx);
               if (_snowman.test(_snowmanxxx)) {
                  return _snowmanxxx;
               }
            }

            return this.abilities.creativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
         }
      }
   }

   @Override
   public ItemStack eatFood(World world, ItemStack stack) {
      this.getHungerManager().eat(stack.getItem(), stack);
      this.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
      world.playSound(
         null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F
      );
      if (this instanceof ServerPlayerEntity) {
         Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)this, stack);
      }

      return super.eatFood(world, stack);
   }

   @Override
   protected boolean method_29500(BlockState _snowman) {
      return this.abilities.flying || super.method_29500(_snowman);
   }

   @Override
   public Vec3d method_30951(float _snowman) {
      double _snowmanx = 0.22 * (this.getMainArm() == Arm.RIGHT ? -1.0 : 1.0);
      float _snowmanxx = MathHelper.lerp(_snowman * 0.5F, this.pitch, this.prevPitch) * (float) (Math.PI / 180.0);
      float _snowmanxxx = MathHelper.lerp(_snowman, this.prevBodyYaw, this.bodyYaw) * (float) (Math.PI / 180.0);
      if (this.isFallFlying() || this.isUsingRiptide()) {
         Vec3d _snowmanxxxx = this.getRotationVec(_snowman);
         Vec3d _snowmanxxxxx = this.getVelocity();
         double _snowmanxxxxxx = Entity.squaredHorizontalLength(_snowmanxxxxx);
         double _snowmanxxxxxxx = Entity.squaredHorizontalLength(_snowmanxxxx);
         float _snowmanxxxxxxxx;
         if (_snowmanxxxxxx > 0.0 && _snowmanxxxxxxx > 0.0) {
            double _snowmanxxxxxxxxx = (_snowmanxxxxx.x * _snowmanxxxx.x + _snowmanxxxxx.z * _snowmanxxxx.z) / Math.sqrt(_snowmanxxxxxx * _snowmanxxxxxxx);
            double _snowmanxxxxxxxxxx = _snowmanxxxxx.x * _snowmanxxxx.z - _snowmanxxxxx.z * _snowmanxxxx.x;
            _snowmanxxxxxxxx = (float)(Math.signum(_snowmanxxxxxxxxxx) * Math.acos(_snowmanxxxxxxxxx));
         } else {
            _snowmanxxxxxxxx = 0.0F;
         }

         return this.method_30950(_snowman).add(new Vec3d(_snowmanx, -0.11, 0.85).rotateZ(-_snowmanxxxxxxxx).rotateX(-_snowmanxx).rotateY(-_snowmanxxx));
      } else if (this.isInSwimmingPose()) {
         return this.method_30950(_snowman).add(new Vec3d(_snowmanx, 0.2, -0.15).rotateX(-_snowmanxx).rotateY(-_snowmanxxx));
      } else {
         double _snowmanxxxx = this.getBoundingBox().getYLength() - 1.0;
         double _snowmanxxxxx = this.isInSneakingPose() ? -0.2 : 0.07;
         return this.method_30950(_snowman).add(new Vec3d(_snowmanx, _snowmanxxxx, _snowmanxxxxx).rotateY(-_snowmanxxx));
      }
   }

   public static enum SleepFailureReason {
      NOT_POSSIBLE_HERE,
      NOT_POSSIBLE_NOW(new TranslatableText("block.minecraft.bed.no_sleep")),
      TOO_FAR_AWAY(new TranslatableText("block.minecraft.bed.too_far_away")),
      OBSTRUCTED(new TranslatableText("block.minecraft.bed.obstructed")),
      OTHER_PROBLEM,
      NOT_SAFE(new TranslatableText("block.minecraft.bed.not_safe"));

      @Nullable
      private final Text text;

      private SleepFailureReason() {
         this.text = null;
      }

      private SleepFailureReason(Text text) {
         this.text = text;
      }

      @Nullable
      public Text toText() {
         return this.text;
      }
   }
}
