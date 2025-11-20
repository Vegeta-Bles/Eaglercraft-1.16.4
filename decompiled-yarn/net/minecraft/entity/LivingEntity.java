package net.minecraft.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.class_5459;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HoneyBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public abstract class LivingEntity extends Entity {
   private static final UUID SPRINTING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final UUID SOUL_SPEED_BOOST_ID = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
   private static final EntityAttributeModifier SPRINTING_SPEED_BOOST = new EntityAttributeModifier(
      SPRINTING_SPEED_BOOST_ID, "Sprinting speed boost", 0.3F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL
   );
   protected static final TrackedData<Byte> LIVING_FLAGS = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BYTE);
   private static final TrackedData<Float> HEALTH = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);
   private static final TrackedData<Integer> POTION_SWIRLS_COLOR = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> POTION_SWIRLS_AMBIENT = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> STUCK_ARROW_COUNT = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> STINGER_COUNT = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Optional<BlockPos>> SLEEPING_POSITION = DataTracker.registerData(
      LivingEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS
   );
   protected static final EntityDimensions SLEEPING_DIMENSIONS = EntityDimensions.fixed(0.2F, 0.2F);
   private final AttributeContainer attributes;
   private final DamageTracker damageTracker = new DamageTracker(this);
   private final Map<StatusEffect, StatusEffectInstance> activeStatusEffects = Maps.newHashMap();
   private final DefaultedList<ItemStack> equippedHand = DefaultedList.ofSize(2, ItemStack.EMPTY);
   private final DefaultedList<ItemStack> equippedArmor = DefaultedList.ofSize(4, ItemStack.EMPTY);
   public boolean handSwinging;
   public Hand preferredHand;
   public int handSwingTicks;
   public int stuckArrowTimer;
   public int stuckStingerTimer;
   public int hurtTime;
   public int maxHurtTime;
   public float knockbackVelocity;
   public int deathTime;
   public float lastHandSwingProgress;
   public float handSwingProgress;
   protected int lastAttackedTicks;
   public float lastLimbDistance;
   public float limbDistance;
   public float limbAngle;
   public final int defaultMaxHealth = 20;
   public final float randomLargeSeed;
   public final float randomSmallSeed;
   public float bodyYaw;
   public float prevBodyYaw;
   public float headYaw;
   public float prevHeadYaw;
   public float flyingSpeed = 0.02F;
   @Nullable
   protected PlayerEntity attackingPlayer;
   protected int playerHitTimer;
   protected boolean dead;
   protected int despawnCounter;
   protected float prevStepBobbingAmount;
   protected float stepBobbingAmount;
   protected float lookDirection;
   protected float prevLookDirection;
   protected float field_6215;
   protected int scoreAmount;
   protected float lastDamageTaken;
   protected boolean jumping;
   public float sidewaysSpeed;
   public float upwardSpeed;
   public float forwardSpeed;
   protected int bodyTrackingIncrements;
   protected double serverX;
   protected double serverY;
   protected double serverZ;
   protected double serverYaw;
   protected double serverPitch;
   protected double serverHeadYaw;
   protected int headTrackingIncrements;
   private boolean effectsChanged = true;
   @Nullable
   private LivingEntity attacker;
   private int lastAttackedTime;
   private LivingEntity attacking;
   private int lastAttackTime;
   private float movementSpeed;
   private int jumpingCooldown;
   private float absorptionAmount;
   protected ItemStack activeItemStack = ItemStack.EMPTY;
   protected int itemUseTimeLeft;
   protected int roll;
   private BlockPos lastBlockPos;
   private Optional<BlockPos> climbingPos = Optional.empty();
   private DamageSource lastDamageSource;
   private long lastDamageTime;
   protected int riptideTicks;
   private float leaningPitch;
   private float lastLeaningPitch;
   protected Brain<?> brain;

   protected LivingEntity(EntityType<? extends LivingEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.attributes = new AttributeContainer(DefaultAttributeRegistry.get(_snowman));
      this.setHealth(this.getMaxHealth());
      this.inanimate = true;
      this.randomSmallSeed = (float)((Math.random() + 1.0) * 0.01F);
      this.refreshPosition();
      this.randomLargeSeed = (float)Math.random() * 12398.0F;
      this.yaw = (float)(Math.random() * (float) (Math.PI * 2));
      this.headYaw = this.yaw;
      this.stepHeight = 0.6F;
      NbtOps _snowmanxx = NbtOps.INSTANCE;
      this.brain = this.deserializeBrain(new Dynamic(_snowmanxx, _snowmanxx.createMap(ImmutableMap.of(_snowmanxx.createString("memories"), _snowmanxx.emptyMap()))));
   }

   public Brain<?> getBrain() {
      return this.brain;
   }

   protected Brain.Profile<?> createBrainProfile() {
      return Brain.createProfile(ImmutableList.of(), ImmutableList.of());
   }

   protected Brain<?> deserializeBrain(Dynamic<?> _snowman) {
      return this.createBrainProfile().deserialize(_snowman);
   }

   @Override
   public void kill() {
      this.damage(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
   }

   public boolean canTarget(EntityType<?> type) {
      return true;
   }

   @Override
   protected void initDataTracker() {
      this.dataTracker.startTracking(LIVING_FLAGS, (byte)0);
      this.dataTracker.startTracking(POTION_SWIRLS_COLOR, 0);
      this.dataTracker.startTracking(POTION_SWIRLS_AMBIENT, false);
      this.dataTracker.startTracking(STUCK_ARROW_COUNT, 0);
      this.dataTracker.startTracking(STINGER_COUNT, 0);
      this.dataTracker.startTracking(HEALTH, 1.0F);
      this.dataTracker.startTracking(SLEEPING_POSITION, Optional.empty());
   }

   public static DefaultAttributeContainer.Builder createLivingAttributes() {
      return DefaultAttributeContainer.builder()
         .add(EntityAttributes.GENERIC_MAX_HEALTH)
         .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED)
         .add(EntityAttributes.GENERIC_ARMOR)
         .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
   }

   @Override
   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
      if (!this.isTouchingWater()) {
         this.checkWaterState();
      }

      if (!this.world.isClient && onGround && this.fallDistance > 0.0F) {
         this.removeSoulSpeedBoost();
         this.addSoulSpeedBoostIfNeeded();
      }

      if (!this.world.isClient && this.fallDistance > 3.0F && onGround) {
         float _snowman = (float)MathHelper.ceil(this.fallDistance - 3.0F);
         if (!landedState.isAir()) {
            double _snowmanx = Math.min((double)(0.2F + _snowman / 15.0F), 2.5);
            int _snowmanxx = (int)(150.0 * _snowmanx);
            ((ServerWorld)this.world)
               .spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, landedState), this.getX(), this.getY(), this.getZ(), _snowmanxx, 0.0, 0.0, 0.0, 0.15F);
         }
      }

      super.fall(heightDifference, onGround, landedState, landedPosition);
   }

   public boolean canBreatheInWater() {
      return this.getGroup() == EntityGroup.UNDEAD;
   }

   public float getLeaningPitch(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastLeaningPitch, this.leaningPitch);
   }

   @Override
   public void baseTick() {
      this.lastHandSwingProgress = this.handSwingProgress;
      if (this.firstUpdate) {
         this.getSleepingPosition().ifPresent(this::setPositionInBed);
      }

      if (this.shouldDisplaySoulSpeedEffects()) {
         this.displaySoulSpeedEffects();
      }

      super.baseTick();
      this.world.getProfiler().push("livingEntityBaseTick");
      boolean _snowman = this instanceof PlayerEntity;
      if (this.isAlive()) {
         if (this.isInsideWall()) {
            this.damage(DamageSource.IN_WALL, 1.0F);
         } else if (_snowman && !this.world.getWorldBorder().contains(this.getBoundingBox())) {
            double _snowmanx = this.world.getWorldBorder().getDistanceInsideBorder(this) + this.world.getWorldBorder().getBuffer();
            if (_snowmanx < 0.0) {
               double _snowmanxx = this.world.getWorldBorder().getDamagePerBlock();
               if (_snowmanxx > 0.0) {
                  this.damage(DamageSource.IN_WALL, (float)Math.max(1, MathHelper.floor(-_snowmanx * _snowmanxx)));
               }
            }
         }
      }

      if (this.isFireImmune() || this.world.isClient) {
         this.extinguish();
      }

      boolean _snowmanx = _snowman && ((PlayerEntity)this).abilities.invulnerable;
      if (this.isAlive()) {
         if (this.isSubmergedIn(FluidTags.WATER)
            && !this.world.getBlockState(new BlockPos(this.getX(), this.getEyeY(), this.getZ())).isOf(Blocks.BUBBLE_COLUMN)) {
            if (!this.canBreatheInWater() && !StatusEffectUtil.hasWaterBreathing(this) && !_snowmanx) {
               this.setAir(this.getNextAirUnderwater(this.getAir()));
               if (this.getAir() == -20) {
                  this.setAir(0);
                  Vec3d _snowmanxx = this.getVelocity();

                  for (int _snowmanxxx = 0; _snowmanxxx < 8; _snowmanxxx++) {
                     double _snowmanxxxx = this.random.nextDouble() - this.random.nextDouble();
                     double _snowmanxxxxx = this.random.nextDouble() - this.random.nextDouble();
                     double _snowmanxxxxxx = this.random.nextDouble() - this.random.nextDouble();
                     this.world.addParticle(ParticleTypes.BUBBLE, this.getX() + _snowmanxxxx, this.getY() + _snowmanxxxxx, this.getZ() + _snowmanxxxxxx, _snowmanxx.x, _snowmanxx.y, _snowmanxx.z);
                  }

                  this.damage(DamageSource.DROWN, 2.0F);
               }
            }

            if (!this.world.isClient && this.hasVehicle() && this.getVehicle() != null && !this.getVehicle().canBeRiddenInWater()) {
               this.stopRiding();
            }
         } else if (this.getAir() < this.getMaxAir()) {
            this.setAir(this.getNextAirOnLand(this.getAir()));
         }

         if (!this.world.isClient) {
            BlockPos _snowmanxx = this.getBlockPos();
            if (!Objects.equal(this.lastBlockPos, _snowmanxx)) {
               this.lastBlockPos = _snowmanxx;
               this.applyMovementEffects(_snowmanxx);
            }
         }
      }

      if (this.isAlive() && this.isWet()) {
         this.extinguish();
      }

      if (this.hurtTime > 0) {
         this.hurtTime--;
      }

      if (this.timeUntilRegen > 0 && !(this instanceof ServerPlayerEntity)) {
         this.timeUntilRegen--;
      }

      if (this.isDead()) {
         this.updatePostDeath();
      }

      if (this.playerHitTimer > 0) {
         this.playerHitTimer--;
      } else {
         this.attackingPlayer = null;
      }

      if (this.attacking != null && !this.attacking.isAlive()) {
         this.attacking = null;
      }

      if (this.attacker != null) {
         if (!this.attacker.isAlive()) {
            this.setAttacker(null);
         } else if (this.age - this.lastAttackedTime > 100) {
            this.setAttacker(null);
         }
      }

      this.tickStatusEffects();
      this.prevLookDirection = this.lookDirection;
      this.prevBodyYaw = this.bodyYaw;
      this.prevHeadYaw = this.headYaw;
      this.prevYaw = this.yaw;
      this.prevPitch = this.pitch;
      this.world.getProfiler().pop();
   }

   public boolean shouldDisplaySoulSpeedEffects() {
      return this.age % 5 == 0
         && this.getVelocity().x != 0.0
         && this.getVelocity().z != 0.0
         && !this.isSpectator()
         && EnchantmentHelper.hasSoulSpeed(this)
         && this.isOnSoulSpeedBlock();
   }

   protected void displaySoulSpeedEffects() {
      Vec3d _snowman = this.getVelocity();
      this.world
         .addParticle(
            ParticleTypes.SOUL,
            this.getX() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(),
            this.getY() + 0.1,
            this.getZ() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(),
            _snowman.x * -0.2,
            0.1,
            _snowman.z * -0.2
         );
      float _snowmanx = this.random.nextFloat() * 0.4F + this.random.nextFloat() > 0.9F ? 0.6F : 0.0F;
      this.playSound(SoundEvents.PARTICLE_SOUL_ESCAPE, _snowmanx, 0.6F + this.random.nextFloat() * 0.4F);
   }

   protected boolean isOnSoulSpeedBlock() {
      return this.world.getBlockState(this.getVelocityAffectingPos()).isIn(BlockTags.SOUL_SPEED_BLOCKS);
   }

   @Override
   protected float getVelocityMultiplier() {
      return this.isOnSoulSpeedBlock() && EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, this) > 0 ? 1.0F : super.getVelocityMultiplier();
   }

   protected boolean method_29500(BlockState _snowman) {
      return !_snowman.isAir() || this.isFallFlying();
   }

   protected void removeSoulSpeedBoost() {
      EntityAttributeInstance _snowman = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
      if (_snowman != null) {
         if (_snowman.getModifier(SOUL_SPEED_BOOST_ID) != null) {
            _snowman.removeModifier(SOUL_SPEED_BOOST_ID);
         }
      }
   }

   protected void addSoulSpeedBoostIfNeeded() {
      if (!this.getLandingBlockState().isAir()) {
         int _snowman = EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, this);
         if (_snowman > 0 && this.isOnSoulSpeedBlock()) {
            EntityAttributeInstance _snowmanx = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (_snowmanx == null) {
               return;
            }

            _snowmanx.addTemporaryModifier(
               new EntityAttributeModifier(
                  SOUL_SPEED_BOOST_ID, "Soul speed boost", (double)(0.03F * (1.0F + (float)_snowman * 0.35F)), EntityAttributeModifier.Operation.ADDITION
               )
            );
            if (this.getRandom().nextFloat() < 0.04F) {
               ItemStack _snowmanxx = this.getEquippedStack(EquipmentSlot.FEET);
               _snowmanxx.damage(1, this, _snowmanxxx -> _snowmanxxx.sendEquipmentBreakStatus(EquipmentSlot.FEET));
            }
         }
      }
   }

   protected void applyMovementEffects(BlockPos pos) {
      int _snowman = EnchantmentHelper.getEquipmentLevel(Enchantments.FROST_WALKER, this);
      if (_snowman > 0) {
         FrostWalkerEnchantment.freezeWater(this, this.world, pos, _snowman);
      }

      if (this.method_29500(this.getLandingBlockState())) {
         this.removeSoulSpeedBoost();
      }

      this.addSoulSpeedBoostIfNeeded();
   }

   public boolean isBaby() {
      return false;
   }

   public float getScaleFactor() {
      return this.isBaby() ? 0.5F : 1.0F;
   }

   protected boolean method_29920() {
      return true;
   }

   @Override
   public boolean canBeRiddenInWater() {
      return false;
   }

   protected void updatePostDeath() {
      this.deathTime++;
      if (this.deathTime == 20) {
         this.remove();

         for (int _snowman = 0; _snowman < 20; _snowman++) {
            double _snowmanx = this.random.nextGaussian() * 0.02;
            double _snowmanxx = this.random.nextGaussian() * 0.02;
            double _snowmanxxx = this.random.nextGaussian() * 0.02;
            this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
         }
      }
   }

   protected boolean canDropLootAndXp() {
      return !this.isBaby();
   }

   protected boolean shouldDropLoot() {
      return !this.isBaby();
   }

   protected int getNextAirUnderwater(int air) {
      int _snowman = EnchantmentHelper.getRespiration(this);
      return _snowman > 0 && this.random.nextInt(_snowman + 1) > 0 ? air : air - 1;
   }

   protected int getNextAirOnLand(int air) {
      return Math.min(air + 4, this.getMaxAir());
   }

   protected int getCurrentExperience(PlayerEntity player) {
      return 0;
   }

   protected boolean shouldAlwaysDropXp() {
      return false;
   }

   public Random getRandom() {
      return this.random;
   }

   @Nullable
   public LivingEntity getAttacker() {
      return this.attacker;
   }

   public int getLastAttackedTime() {
      return this.lastAttackedTime;
   }

   public void setAttacking(@Nullable PlayerEntity attacking) {
      this.attackingPlayer = attacking;
      this.playerHitTimer = this.age;
   }

   public void setAttacker(@Nullable LivingEntity attacker) {
      this.attacker = attacker;
      this.lastAttackedTime = this.age;
   }

   @Nullable
   public LivingEntity getAttacking() {
      return this.attacking;
   }

   public int getLastAttackTime() {
      return this.lastAttackTime;
   }

   public void onAttacking(Entity target) {
      if (target instanceof LivingEntity) {
         this.attacking = (LivingEntity)target;
      } else {
         this.attacking = null;
      }

      this.lastAttackTime = this.age;
   }

   public int getDespawnCounter() {
      return this.despawnCounter;
   }

   public void setDespawnCounter(int despawnCounter) {
      this.despawnCounter = despawnCounter;
   }

   protected void onEquipStack(ItemStack stack) {
      if (!stack.isEmpty()) {
         SoundEvent _snowman = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
         Item _snowmanx = stack.getItem();
         if (_snowmanx instanceof ArmorItem) {
            _snowman = ((ArmorItem)_snowmanx).getMaterial().getEquipSound();
         } else if (_snowmanx == Items.ELYTRA) {
            _snowman = SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
         }

         this.playSound(_snowman, 1.0F, 1.0F);
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      tag.putFloat("Health", this.getHealth());
      tag.putShort("HurtTime", (short)this.hurtTime);
      tag.putInt("HurtByTimestamp", this.lastAttackedTime);
      tag.putShort("DeathTime", (short)this.deathTime);
      tag.putFloat("AbsorptionAmount", this.getAbsorptionAmount());
      tag.put("Attributes", this.getAttributes().toTag());
      if (!this.activeStatusEffects.isEmpty()) {
         ListTag _snowman = new ListTag();

         for (StatusEffectInstance _snowmanx : this.activeStatusEffects.values()) {
            _snowman.add(_snowmanx.toTag(new CompoundTag()));
         }

         tag.put("ActiveEffects", _snowman);
      }

      tag.putBoolean("FallFlying", this.isFallFlying());
      this.getSleepingPosition().ifPresent(_snowmanx -> {
         tag.putInt("SleepingX", _snowmanx.getX());
         tag.putInt("SleepingY", _snowmanx.getY());
         tag.putInt("SleepingZ", _snowmanx.getZ());
      });
      DataResult<Tag> _snowman = this.brain.encode(NbtOps.INSTANCE);
      _snowman.resultOrPartial(LOGGER::error).ifPresent(_snowmanx -> tag.put("Brain", _snowmanx));
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.setAbsorptionAmount(tag.getFloat("AbsorptionAmount"));
      if (tag.contains("Attributes", 9) && this.world != null && !this.world.isClient) {
         this.getAttributes().fromTag(tag.getList("Attributes", 10));
      }

      if (tag.contains("ActiveEffects", 9)) {
         ListTag _snowman = tag.getList("ActiveEffects", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
            StatusEffectInstance _snowmanxxx = StatusEffectInstance.fromTag(_snowmanxx);
            if (_snowmanxxx != null) {
               this.activeStatusEffects.put(_snowmanxxx.getEffectType(), _snowmanxxx);
            }
         }
      }

      if (tag.contains("Health", 99)) {
         this.setHealth(tag.getFloat("Health"));
      }

      this.hurtTime = tag.getShort("HurtTime");
      this.deathTime = tag.getShort("DeathTime");
      this.lastAttackedTime = tag.getInt("HurtByTimestamp");
      if (tag.contains("Team", 8)) {
         String _snowman = tag.getString("Team");
         Team _snowmanxx = this.world.getScoreboard().getTeam(_snowman);
         boolean _snowmanxxx = _snowmanxx != null && this.world.getScoreboard().addPlayerToTeam(this.getUuidAsString(), _snowmanxx);
         if (!_snowmanxxx) {
            LOGGER.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", _snowman);
         }
      }

      if (tag.getBoolean("FallFlying")) {
         this.setFlag(7, true);
      }

      if (tag.contains("SleepingX", 99) && tag.contains("SleepingY", 99) && tag.contains("SleepingZ", 99)) {
         BlockPos _snowman = new BlockPos(tag.getInt("SleepingX"), tag.getInt("SleepingY"), tag.getInt("SleepingZ"));
         this.setSleepingPosition(_snowman);
         this.dataTracker.set(POSE, EntityPose.SLEEPING);
         if (!this.firstUpdate) {
            this.setPositionInBed(_snowman);
         }
      }

      if (tag.contains("Brain", 10)) {
         this.brain = this.deserializeBrain(new Dynamic(NbtOps.INSTANCE, tag.get("Brain")));
      }
   }

   protected void tickStatusEffects() {
      Iterator<StatusEffect> _snowman = this.activeStatusEffects.keySet().iterator();

      try {
         while (_snowman.hasNext()) {
            StatusEffect _snowmanx = _snowman.next();
            StatusEffectInstance _snowmanxx = this.activeStatusEffects.get(_snowmanx);
            if (!_snowmanxx.update(this, () -> this.onStatusEffectUpgraded(_snowman, true))) {
               if (!this.world.isClient) {
                  _snowman.remove();
                  this.onStatusEffectRemoved(_snowmanxx);
               }
            } else if (_snowmanxx.getDuration() % 600 == 0) {
               this.onStatusEffectUpgraded(_snowmanxx, false);
            }
         }
      } catch (ConcurrentModificationException var11) {
      }

      if (this.effectsChanged) {
         if (!this.world.isClient) {
            this.updatePotionVisibility();
         }

         this.effectsChanged = false;
      }

      int _snowmanx = this.dataTracker.get(POTION_SWIRLS_COLOR);
      boolean _snowmanxx = this.dataTracker.get(POTION_SWIRLS_AMBIENT);
      if (_snowmanx > 0) {
         boolean _snowmanxxx;
         if (this.isInvisible()) {
            _snowmanxxx = this.random.nextInt(15) == 0;
         } else {
            _snowmanxxx = this.random.nextBoolean();
         }

         if (_snowmanxx) {
            _snowmanxxx &= this.random.nextInt(5) == 0;
         }

         if (_snowmanxxx && _snowmanx > 0) {
            double _snowmanxxxx = (double)(_snowmanx >> 16 & 0xFF) / 255.0;
            double _snowmanxxxxx = (double)(_snowmanx >> 8 & 0xFF) / 255.0;
            double _snowmanxxxxxx = (double)(_snowmanx >> 0 & 0xFF) / 255.0;
            this.world
               .addParticle(
                  _snowmanxx ? ParticleTypes.AMBIENT_ENTITY_EFFECT : ParticleTypes.ENTITY_EFFECT,
                  this.getParticleX(0.5),
                  this.getRandomBodyY(),
                  this.getParticleZ(0.5),
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx
               );
         }
      }
   }

   protected void updatePotionVisibility() {
      if (this.activeStatusEffects.isEmpty()) {
         this.clearPotionSwirls();
         this.setInvisible(false);
      } else {
         Collection<StatusEffectInstance> _snowman = this.activeStatusEffects.values();
         this.dataTracker.set(POTION_SWIRLS_AMBIENT, containsOnlyAmbientEffects(_snowman));
         this.dataTracker.set(POTION_SWIRLS_COLOR, PotionUtil.getColor(_snowman));
         this.setInvisible(this.hasStatusEffect(StatusEffects.INVISIBILITY));
      }
   }

   public double getAttackDistanceScalingFactor(@Nullable Entity entity) {
      double _snowman = 1.0;
      if (this.isSneaky()) {
         _snowman *= 0.8;
      }

      if (this.isInvisible()) {
         float _snowmanx = this.getArmorVisibility();
         if (_snowmanx < 0.1F) {
            _snowmanx = 0.1F;
         }

         _snowman *= 0.7 * (double)_snowmanx;
      }

      if (entity != null) {
         ItemStack _snowmanx = this.getEquippedStack(EquipmentSlot.HEAD);
         Item _snowmanxx = _snowmanx.getItem();
         EntityType<?> _snowmanxxx = entity.getType();
         if (_snowmanxxx == EntityType.SKELETON && _snowmanxx == Items.SKELETON_SKULL
            || _snowmanxxx == EntityType.ZOMBIE && _snowmanxx == Items.ZOMBIE_HEAD
            || _snowmanxxx == EntityType.CREEPER && _snowmanxx == Items.CREEPER_HEAD) {
            _snowman *= 0.5;
         }
      }

      return _snowman;
   }

   public boolean canTarget(LivingEntity target) {
      return true;
   }

   public boolean isTarget(LivingEntity entity, TargetPredicate predicate) {
      return predicate.test(this, entity);
   }

   public static boolean containsOnlyAmbientEffects(Collection<StatusEffectInstance> effects) {
      for (StatusEffectInstance _snowman : effects) {
         if (!_snowman.isAmbient()) {
            return false;
         }
      }

      return true;
   }

   protected void clearPotionSwirls() {
      this.dataTracker.set(POTION_SWIRLS_AMBIENT, false);
      this.dataTracker.set(POTION_SWIRLS_COLOR, 0);
   }

   public boolean clearStatusEffects() {
      if (this.world.isClient) {
         return false;
      } else {
         Iterator<StatusEffectInstance> _snowman = this.activeStatusEffects.values().iterator();

         boolean _snowmanx;
         for (_snowmanx = false; _snowman.hasNext(); _snowmanx = true) {
            this.onStatusEffectRemoved(_snowman.next());
            _snowman.remove();
         }

         return _snowmanx;
      }
   }

   public Collection<StatusEffectInstance> getStatusEffects() {
      return this.activeStatusEffects.values();
   }

   public Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects() {
      return this.activeStatusEffects;
   }

   public boolean hasStatusEffect(StatusEffect effect) {
      return this.activeStatusEffects.containsKey(effect);
   }

   @Nullable
   public StatusEffectInstance getStatusEffect(StatusEffect effect) {
      return this.activeStatusEffects.get(effect);
   }

   public boolean addStatusEffect(StatusEffectInstance effect) {
      if (!this.canHaveStatusEffect(effect)) {
         return false;
      } else {
         StatusEffectInstance _snowman = this.activeStatusEffects.get(effect.getEffectType());
         if (_snowman == null) {
            this.activeStatusEffects.put(effect.getEffectType(), effect);
            this.onStatusEffectApplied(effect);
            return true;
         } else if (_snowman.upgrade(effect)) {
            this.onStatusEffectUpgraded(_snowman, true);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean canHaveStatusEffect(StatusEffectInstance effect) {
      if (this.getGroup() == EntityGroup.UNDEAD) {
         StatusEffect _snowman = effect.getEffectType();
         if (_snowman == StatusEffects.REGENERATION || _snowman == StatusEffects.POISON) {
            return false;
         }
      }

      return true;
   }

   public void applyStatusEffect(StatusEffectInstance effect) {
      if (this.canHaveStatusEffect(effect)) {
         StatusEffectInstance _snowman = this.activeStatusEffects.put(effect.getEffectType(), effect);
         if (_snowman == null) {
            this.onStatusEffectApplied(effect);
         } else {
            this.onStatusEffectUpgraded(effect, true);
         }
      }
   }

   public boolean isUndead() {
      return this.getGroup() == EntityGroup.UNDEAD;
   }

   @Nullable
   public StatusEffectInstance removeStatusEffectInternal(@Nullable StatusEffect type) {
      return this.activeStatusEffects.remove(type);
   }

   public boolean removeStatusEffect(StatusEffect type) {
      StatusEffectInstance _snowman = this.removeStatusEffectInternal(type);
      if (_snowman != null) {
         this.onStatusEffectRemoved(_snowman);
         return true;
      } else {
         return false;
      }
   }

   protected void onStatusEffectApplied(StatusEffectInstance effect) {
      this.effectsChanged = true;
      if (!this.world.isClient) {
         effect.getEffectType().onApplied(this, this.getAttributes(), effect.getAmplifier());
      }
   }

   protected void onStatusEffectUpgraded(StatusEffectInstance effect, boolean reapplyEffect) {
      this.effectsChanged = true;
      if (reapplyEffect && !this.world.isClient) {
         StatusEffect _snowman = effect.getEffectType();
         _snowman.onRemoved(this, this.getAttributes(), effect.getAmplifier());
         _snowman.onApplied(this, this.getAttributes(), effect.getAmplifier());
      }
   }

   protected void onStatusEffectRemoved(StatusEffectInstance effect) {
      this.effectsChanged = true;
      if (!this.world.isClient) {
         effect.getEffectType().onRemoved(this, this.getAttributes(), effect.getAmplifier());
      }
   }

   public void heal(float amount) {
      float _snowman = this.getHealth();
      if (_snowman > 0.0F) {
         this.setHealth(_snowman + amount);
      }
   }

   public float getHealth() {
      return this.dataTracker.get(HEALTH);
   }

   public void setHealth(float health) {
      this.dataTracker.set(HEALTH, MathHelper.clamp(health, 0.0F, this.getMaxHealth()));
   }

   public boolean isDead() {
      return this.getHealth() <= 0.0F;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (this.world.isClient) {
         return false;
      } else if (this.isDead()) {
         return false;
      } else if (source.isFire() && this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
         return false;
      } else {
         if (this.isSleeping() && !this.world.isClient) {
            this.wakeUp();
         }

         this.despawnCounter = 0;
         float _snowman = amount;
         if ((source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) && !this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            this.getEquippedStack(EquipmentSlot.HEAD)
               .damage((int)(amount * 4.0F + this.random.nextFloat() * amount * 2.0F), this, _snowmanx -> _snowmanx.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
            amount *= 0.75F;
         }

         boolean _snowmanx = false;
         float _snowmanxx = 0.0F;
         if (amount > 0.0F && this.blockedByShield(source)) {
            this.damageShield(amount);
            _snowmanxx = amount;
            amount = 0.0F;
            if (!source.isProjectile()) {
               Entity _snowmanxxx = source.getSource();
               if (_snowmanxxx instanceof LivingEntity) {
                  this.takeShieldHit((LivingEntity)_snowmanxxx);
               }
            }

            _snowmanx = true;
         }

         this.limbDistance = 1.5F;
         boolean _snowmanxxx = true;
         if ((float)this.timeUntilRegen > 10.0F) {
            if (amount <= this.lastDamageTaken) {
               return false;
            }

            this.applyDamage(source, amount - this.lastDamageTaken);
            this.lastDamageTaken = amount;
            _snowmanxxx = false;
         } else {
            this.lastDamageTaken = amount;
            this.timeUntilRegen = 20;
            this.applyDamage(source, amount);
            this.maxHurtTime = 10;
            this.hurtTime = this.maxHurtTime;
         }

         this.knockbackVelocity = 0.0F;
         Entity _snowmanxxxx = source.getAttacker();
         if (_snowmanxxxx != null) {
            if (_snowmanxxxx instanceof LivingEntity) {
               this.setAttacker((LivingEntity)_snowmanxxxx);
            }

            if (_snowmanxxxx instanceof PlayerEntity) {
               this.playerHitTimer = 100;
               this.attackingPlayer = (PlayerEntity)_snowmanxxxx;
            } else if (_snowmanxxxx instanceof WolfEntity) {
               WolfEntity _snowmanxxxxx = (WolfEntity)_snowmanxxxx;
               if (_snowmanxxxxx.isTamed()) {
                  this.playerHitTimer = 100;
                  LivingEntity _snowmanxxxxxx = _snowmanxxxxx.getOwner();
                  if (_snowmanxxxxxx != null && _snowmanxxxxxx.getType() == EntityType.PLAYER) {
                     this.attackingPlayer = (PlayerEntity)_snowmanxxxxxx;
                  } else {
                     this.attackingPlayer = null;
                  }
               }
            }
         }

         if (_snowmanxxx) {
            if (_snowmanx) {
               this.world.sendEntityStatus(this, (byte)29);
            } else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
               this.world.sendEntityStatus(this, (byte)33);
            } else {
               byte _snowmanxxxxx;
               if (source == DamageSource.DROWN) {
                  _snowmanxxxxx = 36;
               } else if (source.isFire()) {
                  _snowmanxxxxx = 37;
               } else if (source == DamageSource.SWEET_BERRY_BUSH) {
                  _snowmanxxxxx = 44;
               } else {
                  _snowmanxxxxx = 2;
               }

               this.world.sendEntityStatus(this, _snowmanxxxxx);
            }

            if (source != DamageSource.DROWN && (!_snowmanx || amount > 0.0F)) {
               this.scheduleVelocityUpdate();
            }

            if (_snowmanxxxx != null) {
               double _snowmanxxxxx = _snowmanxxxx.getX() - this.getX();

               double _snowmanxxxxxx;
               for (_snowmanxxxxxx = _snowmanxxxx.getZ() - this.getZ(); _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx < 1.0E-4; _snowmanxxxxxx = (Math.random() - Math.random()) * 0.01) {
                  _snowmanxxxxx = (Math.random() - Math.random()) * 0.01;
               }

               this.knockbackVelocity = (float)(MathHelper.atan2(_snowmanxxxxxx, _snowmanxxxxx) * 180.0F / (float)Math.PI - (double)this.yaw);
               this.takeKnockback(0.4F, _snowmanxxxxx, _snowmanxxxxxx);
            } else {
               this.knockbackVelocity = (float)((int)(Math.random() * 2.0) * 180);
            }
         }

         if (this.isDead()) {
            if (!this.tryUseTotem(source)) {
               SoundEvent _snowmanxxxxx = this.getDeathSound();
               if (_snowmanxxx && _snowmanxxxxx != null) {
                  this.playSound(_snowmanxxxxx, this.getSoundVolume(), this.getSoundPitch());
               }

               this.onDeath(source);
            }
         } else if (_snowmanxxx) {
            this.playHurtSound(source);
         }

         boolean _snowmanxxxxx = !_snowmanx || amount > 0.0F;
         if (_snowmanxxxxx) {
            this.lastDamageSource = source;
            this.lastDamageTime = this.world.getTime();
         }

         if (this instanceof ServerPlayerEntity) {
            Criteria.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity)this, source, _snowman, amount, _snowmanx);
            if (_snowmanxx > 0.0F && _snowmanxx < 3.4028235E37F) {
               ((ServerPlayerEntity)this).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(_snowmanxx * 10.0F));
            }
         }

         if (_snowmanxxxx instanceof ServerPlayerEntity) {
            Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)_snowmanxxxx, this, source, _snowman, amount, _snowmanx);
         }

         return _snowmanxxxxx;
      }
   }

   protected void takeShieldHit(LivingEntity attacker) {
      attacker.knockback(this);
   }

   protected void knockback(LivingEntity target) {
      target.takeKnockback(0.5F, target.getX() - this.getX(), target.getZ() - this.getZ());
   }

   private boolean tryUseTotem(DamageSource source) {
      if (source.isOutOfWorld()) {
         return false;
      } else {
         ItemStack _snowman = null;

         for (Hand _snowmanx : Hand.values()) {
            ItemStack _snowmanxx = this.getStackInHand(_snowmanx);
            if (_snowmanxx.getItem() == Items.TOTEM_OF_UNDYING) {
               _snowman = _snowmanxx.copy();
               _snowmanxx.decrement(1);
               break;
            }
         }

         if (_snowman != null) {
            if (this instanceof ServerPlayerEntity) {
               ServerPlayerEntity _snowmanxx = (ServerPlayerEntity)this;
               _snowmanxx.incrementStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
               Criteria.USED_TOTEM.trigger(_snowmanxx, _snowman);
            }

            this.setHealth(1.0F);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            this.world.sendEntityStatus(this, (byte)35);
         }

         return _snowman != null;
      }
   }

   @Nullable
   public DamageSource getRecentDamageSource() {
      if (this.world.getTime() - this.lastDamageTime > 40L) {
         this.lastDamageSource = null;
      }

      return this.lastDamageSource;
   }

   protected void playHurtSound(DamageSource source) {
      SoundEvent _snowman = this.getHurtSound(source);
      if (_snowman != null) {
         this.playSound(_snowman, this.getSoundVolume(), this.getSoundPitch());
      }
   }

   private boolean blockedByShield(DamageSource source) {
      Entity _snowman = source.getSource();
      boolean _snowmanx = false;
      if (_snowman instanceof PersistentProjectileEntity) {
         PersistentProjectileEntity _snowmanxx = (PersistentProjectileEntity)_snowman;
         if (_snowmanxx.getPierceLevel() > 0) {
            _snowmanx = true;
         }
      }

      if (!source.bypassesArmor() && this.isBlocking() && !_snowmanx) {
         Vec3d _snowmanxx = source.getPosition();
         if (_snowmanxx != null) {
            Vec3d _snowmanxxx = this.getRotationVec(1.0F);
            Vec3d _snowmanxxxx = _snowmanxx.reverseSubtract(this.getPos()).normalize();
            _snowmanxxxx = new Vec3d(_snowmanxxxx.x, 0.0, _snowmanxxxx.z);
            if (_snowmanxxxx.dotProduct(_snowmanxxx) < 0.0) {
               return true;
            }
         }
      }

      return false;
   }

   private void playEquipmentBreakEffects(ItemStack stack) {
      if (!stack.isEmpty()) {
         if (!this.isSilent()) {
            this.world
               .playSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.ENTITY_ITEM_BREAK,
                  this.getSoundCategory(),
                  0.8F,
                  0.8F + this.world.random.nextFloat() * 0.4F,
                  false
               );
         }

         this.spawnItemParticles(stack, 5);
      }
   }

   public void onDeath(DamageSource source) {
      if (!this.removed && !this.dead) {
         Entity _snowman = source.getAttacker();
         LivingEntity _snowmanx = this.getPrimeAdversary();
         if (this.scoreAmount >= 0 && _snowmanx != null) {
            _snowmanx.updateKilledAdvancementCriterion(this, this.scoreAmount, source);
         }

         if (this.isSleeping()) {
            this.wakeUp();
         }

         this.dead = true;
         this.getDamageTracker().update();
         if (this.world instanceof ServerWorld) {
            if (_snowman != null) {
               _snowman.onKilledOther((ServerWorld)this.world, this);
            }

            this.drop(source);
            this.onKilledBy(_snowmanx);
         }

         this.world.sendEntityStatus(this, (byte)3);
         this.setPose(EntityPose.DYING);
      }
   }

   protected void onKilledBy(@Nullable LivingEntity adversary) {
      if (!this.world.isClient) {
         boolean _snowman = false;
         if (adversary instanceof WitherEntity) {
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
               BlockPos _snowmanx = this.getBlockPos();
               BlockState _snowmanxx = Blocks.WITHER_ROSE.getDefaultState();
               if (this.world.getBlockState(_snowmanx).isAir() && _snowmanxx.canPlaceAt(this.world, _snowmanx)) {
                  this.world.setBlockState(_snowmanx, _snowmanxx, 3);
                  _snowman = true;
               }
            }

            if (!_snowman) {
               ItemEntity _snowmanx = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.WITHER_ROSE));
               this.world.spawnEntity(_snowmanx);
            }
         }
      }
   }

   protected void drop(DamageSource source) {
      Entity _snowman = source.getAttacker();
      int _snowmanx;
      if (_snowman instanceof PlayerEntity) {
         _snowmanx = EnchantmentHelper.getLooting((LivingEntity)_snowman);
      } else {
         _snowmanx = 0;
      }

      boolean _snowmanxx = this.playerHitTimer > 0;
      if (this.shouldDropLoot() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
         this.dropLoot(source, _snowmanxx);
         this.dropEquipment(source, _snowmanx, _snowmanxx);
      }

      this.dropInventory();
      this.dropXp();
   }

   protected void dropInventory() {
   }

   protected void dropXp() {
      if (!this.world.isClient
         && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.canDropLootAndXp() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
         int _snowman = this.getCurrentExperience(this.attackingPlayer);

         while (_snowman > 0) {
            int _snowmanx = ExperienceOrbEntity.roundToOrbSize(_snowman);
            _snowman -= _snowmanx;
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY(), this.getZ(), _snowmanx));
         }
      }
   }

   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
   }

   public Identifier getLootTable() {
      return this.getType().getLootTableId();
   }

   protected void dropLoot(DamageSource source, boolean causedByPlayer) {
      Identifier _snowman = this.getLootTable();
      LootTable _snowmanx = this.world.getServer().getLootManager().getTable(_snowman);
      LootContext.Builder _snowmanxx = this.getLootContextBuilder(causedByPlayer, source);
      _snowmanx.generateLoot(_snowmanxx.build(LootContextTypes.ENTITY), this::dropStack);
   }

   protected LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source) {
      LootContext.Builder _snowman = new LootContext.Builder((ServerWorld)this.world)
         .random(this.random)
         .parameter(LootContextParameters.THIS_ENTITY, this)
         .parameter(LootContextParameters.ORIGIN, this.getPos())
         .parameter(LootContextParameters.DAMAGE_SOURCE, source)
         .optionalParameter(LootContextParameters.KILLER_ENTITY, source.getAttacker())
         .optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, source.getSource());
      if (causedByPlayer && this.attackingPlayer != null) {
         _snowman = _snowman.parameter(LootContextParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).luck(this.attackingPlayer.getLuck());
      }

      return _snowman;
   }

   public void takeKnockback(float _snowman, double _snowman, double _snowman) {
      _snowman = (float)((double)_snowman * (1.0 - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)));
      if (!(_snowman <= 0.0F)) {
         this.velocityDirty = true;
         Vec3d _snowmanxxx = this.getVelocity();
         Vec3d _snowmanxxxx = new Vec3d(_snowman, 0.0, _snowman).normalize().multiply((double)_snowman);
         this.setVelocity(_snowmanxxx.x / 2.0 - _snowmanxxxx.x, this.onGround ? Math.min(0.4, _snowmanxxx.y / 2.0 + (double)_snowman) : _snowmanxxx.y, _snowmanxxx.z / 2.0 - _snowmanxxxx.z);
      }
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_GENERIC_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_GENERIC_DEATH;
   }

   protected SoundEvent getFallSound(int distance) {
      return distance > 4 ? SoundEvents.ENTITY_GENERIC_BIG_FALL : SoundEvents.ENTITY_GENERIC_SMALL_FALL;
   }

   protected SoundEvent getDrinkSound(ItemStack stack) {
      return stack.getDrinkSound();
   }

   public SoundEvent getEatSound(ItemStack stack) {
      return stack.getEatSound();
   }

   @Override
   public void setOnGround(boolean onGround) {
      super.setOnGround(onGround);
      if (onGround) {
         this.climbingPos = Optional.empty();
      }
   }

   public Optional<BlockPos> getClimbingPos() {
      return this.climbingPos;
   }

   public boolean isClimbing() {
      if (this.isSpectator()) {
         return false;
      } else {
         BlockPos _snowman = this.getBlockPos();
         BlockState _snowmanx = this.getBlockState();
         Block _snowmanxx = _snowmanx.getBlock();
         if (_snowmanxx.isIn(BlockTags.CLIMBABLE)) {
            this.climbingPos = Optional.of(_snowman);
            return true;
         } else if (_snowmanxx instanceof TrapdoorBlock && this.canEnterTrapdoor(_snowman, _snowmanx)) {
            this.climbingPos = Optional.of(_snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   public BlockState getBlockState() {
      return this.world.getBlockState(this.getBlockPos());
   }

   private boolean canEnterTrapdoor(BlockPos pos, BlockState state) {
      if (state.get(TrapdoorBlock.OPEN)) {
         BlockState _snowman = this.world.getBlockState(pos.down());
         if (_snowman.isOf(Blocks.LADDER) && _snowman.get(LadderBlock.FACING) == state.get(TrapdoorBlock.FACING)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isAlive() {
      return !this.removed && this.getHealth() > 0.0F;
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      boolean _snowman = super.handleFallDamage(fallDistance, damageMultiplier);
      int _snowmanx = this.computeFallDamage(fallDistance, damageMultiplier);
      if (_snowmanx > 0) {
         this.playSound(this.getFallSound(_snowmanx), 1.0F, 1.0F);
         this.playBlockFallSound();
         this.damage(DamageSource.FALL, (float)_snowmanx);
         return true;
      } else {
         return _snowman;
      }
   }

   protected int computeFallDamage(float fallDistance, float damageMultiplier) {
      StatusEffectInstance _snowman = this.getStatusEffect(StatusEffects.JUMP_BOOST);
      float _snowmanx = _snowman == null ? 0.0F : (float)(_snowman.getAmplifier() + 1);
      return MathHelper.ceil((fallDistance - 3.0F - _snowmanx) * damageMultiplier);
   }

   protected void playBlockFallSound() {
      if (!this.isSilent()) {
         int _snowman = MathHelper.floor(this.getX());
         int _snowmanx = MathHelper.floor(this.getY() - 0.2F);
         int _snowmanxx = MathHelper.floor(this.getZ());
         BlockState _snowmanxxx = this.world.getBlockState(new BlockPos(_snowman, _snowmanx, _snowmanxx));
         if (!_snowmanxxx.isAir()) {
            BlockSoundGroup _snowmanxxxx = _snowmanxxx.getSoundGroup();
            this.playSound(_snowmanxxxx.getFallSound(), _snowmanxxxx.getVolume() * 0.5F, _snowmanxxxx.getPitch() * 0.75F);
         }
      }
   }

   @Override
   public void animateDamage() {
      this.maxHurtTime = 10;
      this.hurtTime = this.maxHurtTime;
      this.knockbackVelocity = 0.0F;
   }

   public int getArmor() {
      return MathHelper.floor(this.getAttributeValue(EntityAttributes.GENERIC_ARMOR));
   }

   protected void damageArmor(DamageSource source, float amount) {
   }

   protected void damageShield(float amount) {
   }

   protected float applyArmorToDamage(DamageSource source, float amount) {
      if (!source.bypassesArmor()) {
         this.damageArmor(source, amount);
         amount = DamageUtil.getDamageLeft(amount, (float)this.getArmor(), (float)this.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
      }

      return amount;
   }

   protected float applyEnchantmentsToDamage(DamageSource source, float amount) {
      if (source.isUnblockable()) {
         return amount;
      } else {
         if (this.hasStatusEffect(StatusEffects.RESISTANCE) && source != DamageSource.OUT_OF_WORLD) {
            int _snowman = (this.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 5;
            int _snowmanx = 25 - _snowman;
            float _snowmanxx = amount * (float)_snowmanx;
            float _snowmanxxx = amount;
            amount = Math.max(_snowmanxx / 25.0F, 0.0F);
            float _snowmanxxxx = _snowmanxxx - amount;
            if (_snowmanxxxx > 0.0F && _snowmanxxxx < 3.4028235E37F) {
               if (this instanceof ServerPlayerEntity) {
                  ((ServerPlayerEntity)this).increaseStat(Stats.DAMAGE_RESISTED, Math.round(_snowmanxxxx * 10.0F));
               } else if (source.getAttacker() instanceof ServerPlayerEntity) {
                  ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(_snowmanxxxx * 10.0F));
               }
            }
         }

         if (amount <= 0.0F) {
            return 0.0F;
         } else {
            int _snowman = EnchantmentHelper.getProtectionAmount(this.getArmorItems(), source);
            if (_snowman > 0) {
               amount = DamageUtil.getInflictedDamage(amount, (float)_snowman);
            }

            return amount;
         }
      }
   }

   protected void applyDamage(DamageSource source, float amount) {
      if (!this.isInvulnerableTo(source)) {
         amount = this.applyArmorToDamage(source, amount);
         amount = this.applyEnchantmentsToDamage(source, amount);
         float var8 = Math.max(amount - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (amount - var8));
         float _snowman = amount - var8;
         if (_snowman > 0.0F && _snowman < 3.4028235E37F && source.getAttacker() instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(_snowman * 10.0F));
         }

         if (var8 != 0.0F) {
            float _snowmanx = this.getHealth();
            this.setHealth(_snowmanx - var8);
            this.getDamageTracker().onDamage(source, _snowmanx, var8);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - var8);
         }
      }
   }

   public DamageTracker getDamageTracker() {
      return this.damageTracker;
   }

   @Nullable
   public LivingEntity getPrimeAdversary() {
      if (this.damageTracker.getBiggestAttacker() != null) {
         return this.damageTracker.getBiggestAttacker();
      } else if (this.attackingPlayer != null) {
         return this.attackingPlayer;
      } else {
         return this.attacker != null ? this.attacker : null;
      }
   }

   public final float getMaxHealth() {
      return (float)this.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
   }

   public final int getStuckArrowCount() {
      return this.dataTracker.get(STUCK_ARROW_COUNT);
   }

   public final void setStuckArrowCount(int stuckArrowCount) {
      this.dataTracker.set(STUCK_ARROW_COUNT, stuckArrowCount);
   }

   public final int getStingerCount() {
      return this.dataTracker.get(STINGER_COUNT);
   }

   public final void setStingerCount(int stingerCount) {
      this.dataTracker.set(STINGER_COUNT, stingerCount);
   }

   private int getHandSwingDuration() {
      if (StatusEffectUtil.hasHaste(this)) {
         return 6 - (1 + StatusEffectUtil.getHasteAmplifier(this));
      } else {
         return this.hasStatusEffect(StatusEffects.MINING_FATIGUE) ? 6 + (1 + this.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) * 2 : 6;
      }
   }

   public void swingHand(Hand hand) {
      this.swingHand(hand, false);
   }

   public void swingHand(Hand hand, boolean _snowman) {
      if (!this.handSwinging || this.handSwingTicks >= this.getHandSwingDuration() / 2 || this.handSwingTicks < 0) {
         this.handSwingTicks = -1;
         this.handSwinging = true;
         this.preferredHand = hand;
         if (this.world instanceof ServerWorld) {
            EntityAnimationS2CPacket _snowmanx = new EntityAnimationS2CPacket(this, hand == Hand.MAIN_HAND ? 0 : 3);
            ServerChunkManager _snowmanxx = ((ServerWorld)this.world).getChunkManager();
            if (_snowman) {
               _snowmanxx.sendToNearbyPlayers(this, _snowmanx);
            } else {
               _snowmanxx.sendToOtherNearbyPlayers(this, _snowmanx);
            }
         }
      }
   }

   @Override
   public void handleStatus(byte status) {
      switch (status) {
         case 2:
         case 33:
         case 36:
         case 37:
         case 44:
            boolean _snowmanx = status == 33;
            boolean _snowmanxx = status == 36;
            boolean _snowmanxxx = status == 37;
            boolean _snowmanxxxx = status == 44;
            this.limbDistance = 1.5F;
            this.timeUntilRegen = 20;
            this.maxHurtTime = 10;
            this.hurtTime = this.maxHurtTime;
            this.knockbackVelocity = 0.0F;
            if (_snowmanx) {
               this.playSound(SoundEvents.ENCHANT_THORNS_HIT, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            DamageSource _snowmanxxxxx;
            if (_snowmanxxx) {
               _snowmanxxxxx = DamageSource.ON_FIRE;
            } else if (_snowmanxx) {
               _snowmanxxxxx = DamageSource.DROWN;
            } else if (_snowmanxxxx) {
               _snowmanxxxxx = DamageSource.SWEET_BERRY_BUSH;
            } else {
               _snowmanxxxxx = DamageSource.GENERIC;
            }

            SoundEvent _snowmanxxxxxx = this.getHurtSound(_snowmanxxxxx);
            if (_snowmanxxxxxx != null) {
               this.playSound(_snowmanxxxxxx, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.damage(DamageSource.GENERIC, 0.0F);
            break;
         case 3:
            SoundEvent _snowman = this.getDeathSound();
            if (_snowman != null) {
               this.playSound(_snowman, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            if (!(this instanceof PlayerEntity)) {
               this.setHealth(0.0F);
               this.onDeath(DamageSource.GENERIC);
            }
            break;
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 31:
         case 32:
         case 34:
         case 35:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 45:
         case 53:
         default:
            super.handleStatus(status);
            break;
         case 29:
            this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + this.world.random.nextFloat() * 0.4F);
            break;
         case 30:
            this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);
            break;
         case 46:
            int _snowmanxxxxxx = 128;

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 128; _snowmanxxxxxxx++) {
               double _snowmanxxxxxxxx = (double)_snowmanxxxxxxx / 127.0;
               float _snowmanxxxxxxxxx = (this.random.nextFloat() - 0.5F) * 0.2F;
               float _snowmanxxxxxxxxxx = (this.random.nextFloat() - 0.5F) * 0.2F;
               float _snowmanxxxxxxxxxxx = (this.random.nextFloat() - 0.5F) * 0.2F;
               double _snowmanxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxx, this.prevX, this.getX()) + (this.random.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
               double _snowmanxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxx, this.prevY, this.getY()) + this.random.nextDouble() * (double)this.getHeight();
               double _snowmanxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxx, this.prevZ, this.getZ()) + (this.random.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
               this.world
                  .addParticle(
                     ParticleTypes.PORTAL, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxx, (double)_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxx
                  );
            }
            break;
         case 47:
            this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.MAINHAND));
            break;
         case 48:
            this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.OFFHAND));
            break;
         case 49:
            this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.HEAD));
            break;
         case 50:
            this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.CHEST));
            break;
         case 51:
            this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.LEGS));
            break;
         case 52:
            this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.FEET));
            break;
         case 54:
            HoneyBlock.addRichParticles(this);
            break;
         case 55:
            this.method_30127();
      }
   }

   private void method_30127() {
      ItemStack _snowman = this.getEquippedStack(EquipmentSlot.OFFHAND);
      this.equipStack(EquipmentSlot.OFFHAND, this.getEquippedStack(EquipmentSlot.MAINHAND));
      this.equipStack(EquipmentSlot.MAINHAND, _snowman);
   }

   @Override
   protected void destroy() {
      this.damage(DamageSource.OUT_OF_WORLD, 4.0F);
   }

   protected void tickHandSwing() {
      int _snowman = this.getHandSwingDuration();
      if (this.handSwinging) {
         this.handSwingTicks++;
         if (this.handSwingTicks >= _snowman) {
            this.handSwingTicks = 0;
            this.handSwinging = false;
         }
      } else {
         this.handSwingTicks = 0;
      }

      this.handSwingProgress = (float)this.handSwingTicks / (float)_snowman;
   }

   @Nullable
   public EntityAttributeInstance getAttributeInstance(EntityAttribute attribute) {
      return this.getAttributes().getCustomInstance(attribute);
   }

   public double getAttributeValue(EntityAttribute attribute) {
      return this.getAttributes().getValue(attribute);
   }

   public double getAttributeBaseValue(EntityAttribute attribute) {
      return this.getAttributes().getBaseValue(attribute);
   }

   public AttributeContainer getAttributes() {
      return this.attributes;
   }

   public EntityGroup getGroup() {
      return EntityGroup.DEFAULT;
   }

   public ItemStack getMainHandStack() {
      return this.getEquippedStack(EquipmentSlot.MAINHAND);
   }

   public ItemStack getOffHandStack() {
      return this.getEquippedStack(EquipmentSlot.OFFHAND);
   }

   public boolean isHolding(Item item) {
      return this.isHolding(_snowmanx -> _snowmanx == item);
   }

   public boolean isHolding(Predicate<Item> _snowman) {
      return _snowman.test(this.getMainHandStack().getItem()) || _snowman.test(this.getOffHandStack().getItem());
   }

   public ItemStack getStackInHand(Hand hand) {
      if (hand == Hand.MAIN_HAND) {
         return this.getEquippedStack(EquipmentSlot.MAINHAND);
      } else if (hand == Hand.OFF_HAND) {
         return this.getEquippedStack(EquipmentSlot.OFFHAND);
      } else {
         throw new IllegalArgumentException("Invalid hand " + hand);
      }
   }

   public void setStackInHand(Hand hand, ItemStack stack) {
      if (hand == Hand.MAIN_HAND) {
         this.equipStack(EquipmentSlot.MAINHAND, stack);
      } else {
         if (hand != Hand.OFF_HAND) {
            throw new IllegalArgumentException("Invalid hand " + hand);
         }

         this.equipStack(EquipmentSlot.OFFHAND, stack);
      }
   }

   public boolean hasStackEquipped(EquipmentSlot slot) {
      return !this.getEquippedStack(slot).isEmpty();
   }

   @Override
   public abstract Iterable<ItemStack> getArmorItems();

   public abstract ItemStack getEquippedStack(EquipmentSlot slot);

   @Override
   public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

   public float getArmorVisibility() {
      Iterable<ItemStack> _snowman = this.getArmorItems();
      int _snowmanx = 0;
      int _snowmanxx = 0;

      for (ItemStack _snowmanxxx : _snowman) {
         if (!_snowmanxxx.isEmpty()) {
            _snowmanxx++;
         }

         _snowmanx++;
      }

      return _snowmanx > 0 ? (float)_snowmanxx / (float)_snowmanx : 0.0F;
   }

   @Override
   public void setSprinting(boolean sprinting) {
      super.setSprinting(sprinting);
      EntityAttributeInstance _snowman = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
      if (_snowman.getModifier(SPRINTING_SPEED_BOOST_ID) != null) {
         _snowman.removeModifier(SPRINTING_SPEED_BOOST);
      }

      if (sprinting) {
         _snowman.addTemporaryModifier(SPRINTING_SPEED_BOOST);
      }
   }

   protected float getSoundVolume() {
      return 1.0F;
   }

   protected float getSoundPitch() {
      return this.isBaby()
         ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F
         : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean isImmobile() {
      return this.isDead();
   }

   @Override
   public void pushAwayFrom(Entity entity) {
      if (!this.isSleeping()) {
         super.pushAwayFrom(entity);
      }
   }

   private void onDismounted(Entity vehicle) {
      Vec3d _snowman;
      if (!vehicle.removed && !this.world.getBlockState(vehicle.getBlockPos()).getBlock().isIn(BlockTags.PORTALS)) {
         _snowman = vehicle.updatePassengerForDismount(this);
      } else {
         _snowman = new Vec3d(vehicle.getX(), vehicle.getY() + (double)vehicle.getHeight(), vehicle.getZ());
      }

      this.requestTeleport(_snowman.x, _snowman.y, _snowman.z);
   }

   @Override
   public boolean shouldRenderName() {
      return this.isCustomNameVisible();
   }

   protected float getJumpVelocity() {
      return 0.42F * this.getJumpVelocityMultiplier();
   }

   protected void jump() {
      float _snowman = this.getJumpVelocity();
      if (this.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
         _snowman += 0.1F * (float)(this.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1);
      }

      Vec3d _snowmanx = this.getVelocity();
      this.setVelocity(_snowmanx.x, (double)_snowman, _snowmanx.z);
      if (this.isSprinting()) {
         float _snowmanxx = this.yaw * (float) (Math.PI / 180.0);
         this.setVelocity(this.getVelocity().add((double)(-MathHelper.sin(_snowmanxx) * 0.2F), 0.0, (double)(MathHelper.cos(_snowmanxx) * 0.2F)));
      }

      this.velocityDirty = true;
   }

   protected void knockDownwards() {
      this.setVelocity(this.getVelocity().add(0.0, -0.04F, 0.0));
   }

   protected void swimUpward(net.minecraft.tag.Tag<Fluid> fluid) {
      this.setVelocity(this.getVelocity().add(0.0, 0.04F, 0.0));
   }

   protected float getBaseMovementSpeedMultiplier() {
      return 0.8F;
   }

   public boolean canWalkOnFluid(Fluid fluid) {
      return false;
   }

   public void travel(Vec3d movementInput) {
      if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
         double _snowman = 0.08;
         boolean _snowmanx = this.getVelocity().y <= 0.0;
         if (_snowmanx && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
            _snowman = 0.01;
            this.fallDistance = 0.0F;
         }

         FluidState _snowmanxx = this.world.getFluidState(this.getBlockPos());
         if (this.isTouchingWater() && this.method_29920() && !this.canWalkOnFluid(_snowmanxx.getFluid())) {
            double _snowmanxxx = this.getY();
            float _snowmanxxxx = this.isSprinting() ? 0.9F : this.getBaseMovementSpeedMultiplier();
            float _snowmanxxxxx = 0.02F;
            float _snowmanxxxxxx = (float)EnchantmentHelper.getDepthStrider(this);
            if (_snowmanxxxxxx > 3.0F) {
               _snowmanxxxxxx = 3.0F;
            }

            if (!this.onGround) {
               _snowmanxxxxxx *= 0.5F;
            }

            if (_snowmanxxxxxx > 0.0F) {
               _snowmanxxxx += (0.54600006F - _snowmanxxxx) * _snowmanxxxxxx / 3.0F;
               _snowmanxxxxx += (this.getMovementSpeed() - _snowmanxxxxx) * _snowmanxxxxxx / 3.0F;
            }

            if (this.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
               _snowmanxxxx = 0.96F;
            }

            this.updateVelocity(_snowmanxxxxx, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            Vec3d _snowmanxxxxxxx = this.getVelocity();
            if (this.horizontalCollision && this.isClimbing()) {
               _snowmanxxxxxxx = new Vec3d(_snowmanxxxxxxx.x, 0.2, _snowmanxxxxxxx.z);
            }

            this.setVelocity(_snowmanxxxxxxx.multiply((double)_snowmanxxxx, 0.8F, (double)_snowmanxxxx));
            Vec3d _snowmanxxxxxxxx = this.method_26317(_snowman, _snowmanx, this.getVelocity());
            this.setVelocity(_snowmanxxxxxxxx);
            if (this.horizontalCollision && this.doesNotCollide(_snowmanxxxxxxxx.x, _snowmanxxxxxxxx.y + 0.6F - this.getY() + _snowmanxxx, _snowmanxxxxxxxx.z)) {
               this.setVelocity(_snowmanxxxxxxxx.x, 0.3F, _snowmanxxxxxxxx.z);
            }
         } else if (this.isInLava() && this.method_29920() && !this.canWalkOnFluid(_snowmanxx.getFluid())) {
            double _snowmanxxxxxxxx = this.getY();
            this.updateVelocity(0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            if (this.getFluidHeight(FluidTags.LAVA) <= this.method_29241()) {
               this.setVelocity(this.getVelocity().multiply(0.5, 0.8F, 0.5));
               Vec3d _snowmanxxxxxxxxx = this.method_26317(_snowman, _snowmanx, this.getVelocity());
               this.setVelocity(_snowmanxxxxxxxxx);
            } else {
               this.setVelocity(this.getVelocity().multiply(0.5));
            }

            if (!this.hasNoGravity()) {
               this.setVelocity(this.getVelocity().add(0.0, -_snowman / 4.0, 0.0));
            }

            Vec3d _snowmanxxxxxxxxx = this.getVelocity();
            if (this.horizontalCollision && this.doesNotCollide(_snowmanxxxxxxxxx.x, _snowmanxxxxxxxxx.y + 0.6F - this.getY() + _snowmanxxxxxxxx, _snowmanxxxxxxxxx.z)) {
               this.setVelocity(_snowmanxxxxxxxxx.x, 0.3F, _snowmanxxxxxxxxx.z);
            }
         } else if (this.isFallFlying()) {
            Vec3d _snowmanxxxxxxxxx = this.getVelocity();
            if (_snowmanxxxxxxxxx.y > -0.5) {
               this.fallDistance = 1.0F;
            }

            Vec3d _snowmanxxxxxxxxxx = this.getRotationVector();
            float _snowmanxxxxxxxxxxx = this.pitch * (float) (Math.PI / 180.0);
            double _snowmanxxxxxxxxxxxx = Math.sqrt(_snowmanxxxxxxxxxx.x * _snowmanxxxxxxxxxx.x + _snowmanxxxxxxxxxx.z * _snowmanxxxxxxxxxx.z);
            double _snowmanxxxxxxxxxxxxx = Math.sqrt(squaredHorizontalLength(_snowmanxxxxxxxxx));
            double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.length();
            float _snowmanxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxxxx * (double)_snowmanxxxxxxxxxxxxxxx * Math.min(1.0, _snowmanxxxxxxxxxxxxxx / 0.4));
            _snowmanxxxxxxxxx = this.getVelocity().add(0.0, _snowman * (-1.0 + (double)_snowmanxxxxxxxxxxxxxxx * 0.75), 0.0);
            if (_snowmanxxxxxxxxx.y < 0.0 && _snowmanxxxxxxxxxxxx > 0.0) {
               double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.y * -0.1 * (double)_snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.add(
                  _snowmanxxxxxxxxxx.x * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx.z * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx
               );
            }

            if (_snowmanxxxxxxxxxxx < 0.0F && _snowmanxxxxxxxxxxxx > 0.0) {
               double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx * (double)(-MathHelper.sin(_snowmanxxxxxxxxxxx)) * 0.04;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.add(
                  -_snowmanxxxxxxxxxx.x * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx * 3.2, -_snowmanxxxxxxxxxx.z * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx
               );
            }

            if (_snowmanxxxxxxxxxxxx > 0.0) {
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.add(
                  (_snowmanxxxxxxxxxx.x / _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxx.x) * 0.1,
                  0.0,
                  (_snowmanxxxxxxxxxx.z / _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxx.z) * 0.1
               );
            }

            this.setVelocity(_snowmanxxxxxxxxx.multiply(0.99F, 0.98F, 0.99F));
            this.move(MovementType.SELF, this.getVelocity());
            if (this.horizontalCollision && !this.world.isClient) {
               double _snowmanxxxxxxxxxxxxxxxx = Math.sqrt(squaredHorizontalLength(this.getVelocity()));
               double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxx;
               float _snowmanxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxx * 10.0 - 3.0);
               if (_snowmanxxxxxxxxxxxxxxxxxx > 0.0F) {
                  this.playSound(this.getFallSound((int)_snowmanxxxxxxxxxxxxxxxxxx), 1.0F, 1.0F);
                  this.damage(DamageSource.FLY_INTO_WALL, _snowmanxxxxxxxxxxxxxxxxxx);
               }
            }

            if (this.onGround && !this.world.isClient) {
               this.setFlag(7, false);
            }
         } else {
            BlockPos _snowmanxxxxxxxxxxxxxxxx = this.getVelocityAffectingPos();
            float _snowmanxxxxxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxxxxxxxx).getBlock().getSlipperiness();
            float _snowmanxxxxxxxxxxxxxxxxxx = this.onGround ? _snowmanxxxxxxxxxxxxxxxxx * 0.91F : 0.91F;
            Vec3d _snowmanxxxxxxxxxxxxxxxxxxx = this.method_26318(movementInput, _snowmanxxxxxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.y;
            if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
               _snowmanxxxxxxxxxxxxxxxxxxxx += (0.05 * (double)(this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - _snowmanxxxxxxxxxxxxxxxxxxx.y) * 0.2;
               this.fallDistance = 0.0F;
            } else if (this.world.isClient && !this.world.isChunkLoaded(_snowmanxxxxxxxxxxxxxxxx)) {
               if (this.getY() > 0.0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxx = -0.1;
               } else {
                  _snowmanxxxxxxxxxxxxxxxxxxxx = 0.0;
               }
            } else if (!this.hasNoGravity()) {
               _snowmanxxxxxxxxxxxxxxxxxxxx -= _snowman;
            }

            this.setVelocity(
               _snowmanxxxxxxxxxxxxxxxxxxx.x * (double)_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx * 0.98F, _snowmanxxxxxxxxxxxxxxxxxxx.z * (double)_snowmanxxxxxxxxxxxxxxxxxx
            );
         }
      }

      this.method_29242(this, this instanceof Flutterer);
   }

   public void method_29242(LivingEntity _snowman, boolean _snowman) {
      _snowman.lastLimbDistance = _snowman.limbDistance;
      double _snowmanxx = _snowman.getX() - _snowman.prevX;
      double _snowmanxxx = _snowman ? _snowman.getY() - _snowman.prevY : 0.0;
      double _snowmanxxxx = _snowman.getZ() - _snowman.prevZ;
      float _snowmanxxxxx = MathHelper.sqrt(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx) * 4.0F;
      if (_snowmanxxxxx > 1.0F) {
         _snowmanxxxxx = 1.0F;
      }

      _snowman.limbDistance = _snowman.limbDistance + (_snowmanxxxxx - _snowman.limbDistance) * 0.4F;
      _snowman.limbAngle = _snowman.limbAngle + _snowman.limbDistance;
   }

   public Vec3d method_26318(Vec3d _snowman, float _snowman) {
      this.updateVelocity(this.getMovementSpeed(_snowman), _snowman);
      this.setVelocity(this.applyClimbingSpeed(this.getVelocity()));
      this.move(MovementType.SELF, this.getVelocity());
      Vec3d _snowmanxx = this.getVelocity();
      if ((this.horizontalCollision || this.jumping) && this.isClimbing()) {
         _snowmanxx = new Vec3d(_snowmanxx.x, 0.2, _snowmanxx.z);
      }

      return _snowmanxx;
   }

   public Vec3d method_26317(double _snowman, boolean _snowman, Vec3d _snowman) {
      if (!this.hasNoGravity() && !this.isSprinting()) {
         double _snowmanxxx;
         if (_snowman && Math.abs(_snowman.y - 0.005) >= 0.003 && Math.abs(_snowman.y - _snowman / 16.0) < 0.003) {
            _snowmanxxx = -0.003;
         } else {
            _snowmanxxx = _snowman.y - _snowman / 16.0;
         }

         return new Vec3d(_snowman.x, _snowmanxxx, _snowman.z);
      } else {
         return _snowman;
      }
   }

   private Vec3d applyClimbingSpeed(Vec3d motion) {
      if (this.isClimbing()) {
         this.fallDistance = 0.0F;
         float _snowman = 0.15F;
         double _snowmanx = MathHelper.clamp(motion.x, -0.15F, 0.15F);
         double _snowmanxx = MathHelper.clamp(motion.z, -0.15F, 0.15F);
         double _snowmanxxx = Math.max(motion.y, -0.15F);
         if (_snowmanxxx < 0.0 && !this.getBlockState().isOf(Blocks.SCAFFOLDING) && this.isHoldingOntoLadder() && this instanceof PlayerEntity) {
            _snowmanxxx = 0.0;
         }

         motion = new Vec3d(_snowmanx, _snowmanxxx, _snowmanxx);
      }

      return motion;
   }

   private float getMovementSpeed(float slipperiness) {
      return this.onGround ? this.getMovementSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness)) : this.flyingSpeed;
   }

   public float getMovementSpeed() {
      return this.movementSpeed;
   }

   public void setMovementSpeed(float movementSpeed) {
      this.movementSpeed = movementSpeed;
   }

   public boolean tryAttack(Entity target) {
      this.onAttacking(target);
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      this.tickActiveItemStack();
      this.updateLeaningPitch();
      if (!this.world.isClient) {
         int _snowman = this.getStuckArrowCount();
         if (_snowman > 0) {
            if (this.stuckArrowTimer <= 0) {
               this.stuckArrowTimer = 20 * (30 - _snowman);
            }

            this.stuckArrowTimer--;
            if (this.stuckArrowTimer <= 0) {
               this.setStuckArrowCount(_snowman - 1);
            }
         }

         int _snowmanx = this.getStingerCount();
         if (_snowmanx > 0) {
            if (this.stuckStingerTimer <= 0) {
               this.stuckStingerTimer = 20 * (30 - _snowmanx);
            }

            this.stuckStingerTimer--;
            if (this.stuckStingerTimer <= 0) {
               this.setStingerCount(_snowmanx - 1);
            }
         }

         this.method_30128();
         if (this.age % 20 == 0) {
            this.getDamageTracker().update();
         }

         if (!this.glowing) {
            boolean _snowmanxx = this.hasStatusEffect(StatusEffects.GLOWING);
            if (this.getFlag(6) != _snowmanxx) {
               this.setFlag(6, _snowmanxx);
            }
         }

         if (this.isSleeping() && !this.isSleepingInBed()) {
            this.wakeUp();
         }
      }

      this.tickMovement();
      double _snowmanxx = this.getX() - this.prevX;
      double _snowmanxxx = this.getZ() - this.prevZ;
      float _snowmanxxxx = (float)(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
      float _snowmanxxxxx = this.bodyYaw;
      float _snowmanxxxxxx = 0.0F;
      this.prevStepBobbingAmount = this.stepBobbingAmount;
      float _snowmanxxxxxxx = 0.0F;
      if (_snowmanxxxx > 0.0025000002F) {
         _snowmanxxxxxxx = 1.0F;
         _snowmanxxxxxx = (float)Math.sqrt((double)_snowmanxxxx) * 3.0F;
         float _snowmanxxxxxxxx = (float)MathHelper.atan2(_snowmanxxx, _snowmanxx) * (180.0F / (float)Math.PI) - 90.0F;
         float _snowmanxxxxxxxxx = MathHelper.abs(MathHelper.wrapDegrees(this.yaw) - _snowmanxxxxxxxx);
         if (95.0F < _snowmanxxxxxxxxx && _snowmanxxxxxxxxx < 265.0F) {
            _snowmanxxxxx = _snowmanxxxxxxxx - 180.0F;
         } else {
            _snowmanxxxxx = _snowmanxxxxxxxx;
         }
      }

      if (this.handSwingProgress > 0.0F) {
         _snowmanxxxxx = this.yaw;
      }

      if (!this.onGround) {
         _snowmanxxxxxxx = 0.0F;
      }

      this.stepBobbingAmount = this.stepBobbingAmount + (_snowmanxxxxxxx - this.stepBobbingAmount) * 0.3F;
      this.world.getProfiler().push("headTurn");
      _snowmanxxxxxx = this.turnHead(_snowmanxxxxx, _snowmanxxxxxx);
      this.world.getProfiler().pop();
      this.world.getProfiler().push("rangeChecks");

      while (this.yaw - this.prevYaw < -180.0F) {
         this.prevYaw -= 360.0F;
      }

      while (this.yaw - this.prevYaw >= 180.0F) {
         this.prevYaw += 360.0F;
      }

      while (this.bodyYaw - this.prevBodyYaw < -180.0F) {
         this.prevBodyYaw -= 360.0F;
      }

      while (this.bodyYaw - this.prevBodyYaw >= 180.0F) {
         this.prevBodyYaw += 360.0F;
      }

      while (this.pitch - this.prevPitch < -180.0F) {
         this.prevPitch -= 360.0F;
      }

      while (this.pitch - this.prevPitch >= 180.0F) {
         this.prevPitch += 360.0F;
      }

      while (this.headYaw - this.prevHeadYaw < -180.0F) {
         this.prevHeadYaw -= 360.0F;
      }

      while (this.headYaw - this.prevHeadYaw >= 180.0F) {
         this.prevHeadYaw += 360.0F;
      }

      this.world.getProfiler().pop();
      this.lookDirection += _snowmanxxxxxx;
      if (this.isFallFlying()) {
         this.roll++;
      } else {
         this.roll = 0;
      }

      if (this.isSleeping()) {
         this.pitch = 0.0F;
      }
   }

   private void method_30128() {
      Map<EquipmentSlot, ItemStack> _snowman = this.method_30129();
      if (_snowman != null) {
         this.method_30121(_snowman);
         if (!_snowman.isEmpty()) {
            this.method_30123(_snowman);
         }
      }
   }

   @Nullable
   private Map<EquipmentSlot, ItemStack> method_30129() {
      Map<EquipmentSlot, ItemStack> _snowman = null;

      for (EquipmentSlot _snowmanx : EquipmentSlot.values()) {
         ItemStack _snowmanxx;
         switch (_snowmanx.getType()) {
            case HAND:
               _snowmanxx = this.method_30126(_snowmanx);
               break;
            case ARMOR:
               _snowmanxx = this.method_30125(_snowmanx);
               break;
            default:
               continue;
         }

         ItemStack _snowmanxx = this.getEquippedStack(_snowmanx);
         if (!ItemStack.areEqual(_snowmanxx, _snowmanxx)) {
            if (_snowman == null) {
               _snowman = Maps.newEnumMap(EquipmentSlot.class);
            }

            _snowman.put(_snowmanx, _snowmanxx);
            if (!_snowmanxx.isEmpty()) {
               this.getAttributes().removeModifiers(_snowmanxx.getAttributeModifiers(_snowmanx));
            }

            if (!_snowmanxx.isEmpty()) {
               this.getAttributes().addTemporaryModifiers(_snowmanxx.getAttributeModifiers(_snowmanx));
            }
         }
      }

      return _snowman;
   }

   private void method_30121(Map<EquipmentSlot, ItemStack> _snowman) {
      ItemStack _snowmanx = _snowman.get(EquipmentSlot.MAINHAND);
      ItemStack _snowmanxx = _snowman.get(EquipmentSlot.OFFHAND);
      if (_snowmanx != null
         && _snowmanxx != null
         && ItemStack.areEqual(_snowmanx, this.method_30126(EquipmentSlot.OFFHAND))
         && ItemStack.areEqual(_snowmanxx, this.method_30126(EquipmentSlot.MAINHAND))) {
         ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityStatusS2CPacket(this, (byte)55));
         _snowman.remove(EquipmentSlot.MAINHAND);
         _snowman.remove(EquipmentSlot.OFFHAND);
         this.method_30124(EquipmentSlot.MAINHAND, _snowmanx.copy());
         this.method_30124(EquipmentSlot.OFFHAND, _snowmanxx.copy());
      }
   }

   private void method_30123(Map<EquipmentSlot, ItemStack> _snowman) {
      List<Pair<EquipmentSlot, ItemStack>> _snowmanx = Lists.newArrayListWithCapacity(_snowman.size());
      _snowman.forEach((_snowmanxxx, _snowmanxx) -> {
         ItemStack _snowmanxx = _snowmanxx.copy();
         _snowman.add(Pair.of(_snowmanxxx, _snowmanxx));
         switch (_snowmanxxx.getType()) {
            case HAND:
               this.method_30124(_snowmanxxx, _snowmanxx);
               break;
            case ARMOR:
               this.method_30122(_snowmanxxx, _snowmanxx);
         }
      });
      ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityEquipmentUpdateS2CPacket(this.getEntityId(), _snowmanx));
   }

   private ItemStack method_30125(EquipmentSlot _snowman) {
      return this.equippedArmor.get(_snowman.getEntitySlotId());
   }

   private void method_30122(EquipmentSlot _snowman, ItemStack _snowman) {
      this.equippedArmor.set(_snowman.getEntitySlotId(), _snowman);
   }

   private ItemStack method_30126(EquipmentSlot _snowman) {
      return this.equippedHand.get(_snowman.getEntitySlotId());
   }

   private void method_30124(EquipmentSlot _snowman, ItemStack _snowman) {
      this.equippedHand.set(_snowman.getEntitySlotId(), _snowman);
   }

   protected float turnHead(float bodyRotation, float headRotation) {
      float _snowman = MathHelper.wrapDegrees(bodyRotation - this.bodyYaw);
      this.bodyYaw += _snowman * 0.3F;
      float _snowmanx = MathHelper.wrapDegrees(this.yaw - this.bodyYaw);
      boolean _snowmanxx = _snowmanx < -90.0F || _snowmanx >= 90.0F;
      if (_snowmanx < -75.0F) {
         _snowmanx = -75.0F;
      }

      if (_snowmanx >= 75.0F) {
         _snowmanx = 75.0F;
      }

      this.bodyYaw = this.yaw - _snowmanx;
      if (_snowmanx * _snowmanx > 2500.0F) {
         this.bodyYaw += _snowmanx * 0.2F;
      }

      if (_snowmanxx) {
         headRotation *= -1.0F;
      }

      return headRotation;
   }

   public void tickMovement() {
      if (this.jumpingCooldown > 0) {
         this.jumpingCooldown--;
      }

      if (this.isLogicalSideForUpdatingMovement()) {
         this.bodyTrackingIncrements = 0;
         this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
      }

      if (this.bodyTrackingIncrements > 0) {
         double _snowman = this.getX() + (this.serverX - this.getX()) / (double)this.bodyTrackingIncrements;
         double _snowmanx = this.getY() + (this.serverY - this.getY()) / (double)this.bodyTrackingIncrements;
         double _snowmanxx = this.getZ() + (this.serverZ - this.getZ()) / (double)this.bodyTrackingIncrements;
         double _snowmanxxx = MathHelper.wrapDegrees(this.serverYaw - (double)this.yaw);
         this.yaw = (float)((double)this.yaw + _snowmanxxx / (double)this.bodyTrackingIncrements);
         this.pitch = (float)((double)this.pitch + (this.serverPitch - (double)this.pitch) / (double)this.bodyTrackingIncrements);
         this.bodyTrackingIncrements--;
         this.updatePosition(_snowman, _snowmanx, _snowmanxx);
         this.setRotation(this.yaw, this.pitch);
      } else if (!this.canMoveVoluntarily()) {
         this.setVelocity(this.getVelocity().multiply(0.98));
      }

      if (this.headTrackingIncrements > 0) {
         this.headYaw = (float)((double)this.headYaw + MathHelper.wrapDegrees(this.serverHeadYaw - (double)this.headYaw) / (double)this.headTrackingIncrements);
         this.headTrackingIncrements--;
      }

      Vec3d _snowman = this.getVelocity();
      double _snowmanx = _snowman.x;
      double _snowmanxx = _snowman.y;
      double _snowmanxxx = _snowman.z;
      if (Math.abs(_snowman.x) < 0.003) {
         _snowmanx = 0.0;
      }

      if (Math.abs(_snowman.y) < 0.003) {
         _snowmanxx = 0.0;
      }

      if (Math.abs(_snowman.z) < 0.003) {
         _snowmanxxx = 0.0;
      }

      this.setVelocity(_snowmanx, _snowmanxx, _snowmanxxx);
      this.world.getProfiler().push("ai");
      if (this.isImmobile()) {
         this.jumping = false;
         this.sidewaysSpeed = 0.0F;
         this.forwardSpeed = 0.0F;
      } else if (this.canMoveVoluntarily()) {
         this.world.getProfiler().push("newAi");
         this.tickNewAi();
         this.world.getProfiler().pop();
      }

      this.world.getProfiler().pop();
      this.world.getProfiler().push("jump");
      if (this.jumping && this.method_29920()) {
         double _snowmanxxxx;
         if (this.isInLava()) {
            _snowmanxxxx = this.getFluidHeight(FluidTags.LAVA);
         } else {
            _snowmanxxxx = this.getFluidHeight(FluidTags.WATER);
         }

         boolean _snowmanxxxxx = this.isTouchingWater() && _snowmanxxxx > 0.0;
         double _snowmanxxxxxx = this.method_29241();
         if (!_snowmanxxxxx || this.onGround && !(_snowmanxxxx > _snowmanxxxxxx)) {
            if (!this.isInLava() || this.onGround && !(_snowmanxxxx > _snowmanxxxxxx)) {
               if ((this.onGround || _snowmanxxxxx && _snowmanxxxx <= _snowmanxxxxxx) && this.jumpingCooldown == 0) {
                  this.jump();
                  this.jumpingCooldown = 10;
               }
            } else {
               this.swimUpward(FluidTags.LAVA);
            }
         } else {
            this.swimUpward(FluidTags.WATER);
         }
      } else {
         this.jumpingCooldown = 0;
      }

      this.world.getProfiler().pop();
      this.world.getProfiler().push("travel");
      this.sidewaysSpeed *= 0.98F;
      this.forwardSpeed *= 0.98F;
      this.initAi();
      Box _snowmanxxxxx = this.getBoundingBox();
      this.travel(new Vec3d((double)this.sidewaysSpeed, (double)this.upwardSpeed, (double)this.forwardSpeed));
      this.world.getProfiler().pop();
      this.world.getProfiler().push("push");
      if (this.riptideTicks > 0) {
         this.riptideTicks--;
         this.tickRiptide(_snowmanxxxxx, this.getBoundingBox());
      }

      this.tickCramming();
      this.world.getProfiler().pop();
      if (!this.world.isClient && this.hurtByWater() && this.isWet()) {
         this.damage(DamageSource.DROWN, 1.0F);
      }
   }

   public boolean hurtByWater() {
      return false;
   }

   private void initAi() {
      boolean _snowman = this.getFlag(7);
      if (_snowman && !this.onGround && !this.hasVehicle() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {
         ItemStack _snowmanx = this.getEquippedStack(EquipmentSlot.CHEST);
         if (_snowmanx.getItem() == Items.ELYTRA && ElytraItem.isUsable(_snowmanx)) {
            _snowman = true;
            if (!this.world.isClient && (this.roll + 1) % 20 == 0) {
               _snowmanx.damage(1, this, _snowmanxx -> _snowmanxx.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
            }
         } else {
            _snowman = false;
         }
      } else {
         _snowman = false;
      }

      if (!this.world.isClient) {
         this.setFlag(7, _snowman);
      }
   }

   protected void tickNewAi() {
   }

   protected void tickCramming() {
      List<Entity> _snowman = this.world.getOtherEntities(this, this.getBoundingBox(), EntityPredicates.canBePushedBy(this));
      if (!_snowman.isEmpty()) {
         int _snowmanx = this.world.getGameRules().getInt(GameRules.MAX_ENTITY_CRAMMING);
         if (_snowmanx > 0 && _snowman.size() > _snowmanx - 1 && this.random.nextInt(4) == 0) {
            int _snowmanxx = 0;

            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
               if (!_snowman.get(_snowmanxxx).hasVehicle()) {
                  _snowmanxx++;
               }
            }

            if (_snowmanxx > _snowmanx - 1) {
               this.damage(DamageSource.CRAMMING, 6.0F);
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
            Entity _snowmanxxxx = _snowman.get(_snowmanxx);
            this.pushAway(_snowmanxxxx);
         }
      }
   }

   protected void tickRiptide(Box a, Box b) {
      Box _snowman = a.union(b);
      List<Entity> _snowmanx = this.world.getOtherEntities(this, _snowman);
      if (!_snowmanx.isEmpty()) {
         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            Entity _snowmanxxx = _snowmanx.get(_snowmanxx);
            if (_snowmanxxx instanceof LivingEntity) {
               this.attackLivingEntity((LivingEntity)_snowmanxxx);
               this.riptideTicks = 0;
               this.setVelocity(this.getVelocity().multiply(-0.2));
               break;
            }
         }
      } else if (this.horizontalCollision) {
         this.riptideTicks = 0;
      }

      if (!this.world.isClient && this.riptideTicks <= 0) {
         this.setLivingFlag(4, false);
      }
   }

   protected void pushAway(Entity entity) {
      entity.pushAwayFrom(this);
   }

   protected void attackLivingEntity(LivingEntity target) {
   }

   public void setRiptideTicks(int _snowman) {
      this.riptideTicks = _snowman;
      if (!this.world.isClient) {
         this.setLivingFlag(4, true);
      }
   }

   public boolean isUsingRiptide() {
      return (this.dataTracker.get(LIVING_FLAGS) & 4) != 0;
   }

   @Override
   public void stopRiding() {
      Entity _snowman = this.getVehicle();
      super.stopRiding();
      if (_snowman != null && _snowman != this.getVehicle() && !this.world.isClient) {
         this.onDismounted(_snowman);
      }
   }

   @Override
   public void tickRiding() {
      super.tickRiding();
      this.prevStepBobbingAmount = this.stepBobbingAmount;
      this.stepBobbingAmount = 0.0F;
      this.fallDistance = 0.0F;
   }

   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
      this.serverX = x;
      this.serverY = y;
      this.serverZ = z;
      this.serverYaw = (double)yaw;
      this.serverPitch = (double)pitch;
      this.bodyTrackingIncrements = interpolationSteps;
   }

   @Override
   public void updateTrackedHeadRotation(float yaw, int interpolationSteps) {
      this.serverHeadYaw = (double)yaw;
      this.headTrackingIncrements = interpolationSteps;
   }

   public void setJumping(boolean jumping) {
      this.jumping = jumping;
   }

   public void method_29499(ItemEntity _snowman) {
      PlayerEntity _snowmanx = _snowman.getThrower() != null ? this.world.getPlayerByUuid(_snowman.getThrower()) : null;
      if (_snowmanx instanceof ServerPlayerEntity) {
         Criteria.THROWN_ITEM_PICKED_UP_BY_ENTITY.trigger((ServerPlayerEntity)_snowmanx, _snowman.getStack(), this);
      }
   }

   public void sendPickup(Entity item, int count) {
      if (!item.removed
         && !this.world.isClient
         && (item instanceof ItemEntity || item instanceof PersistentProjectileEntity || item instanceof ExperienceOrbEntity)) {
         ((ServerWorld)this.world)
            .getChunkManager()
            .sendToOtherNearbyPlayers(item, new ItemPickupAnimationS2CPacket(item.getEntityId(), this.getEntityId(), count));
      }
   }

   public boolean canSee(Entity entity) {
      Vec3d _snowman = new Vec3d(this.getX(), this.getEyeY(), this.getZ());
      Vec3d _snowmanx = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
      return this.world.raycast(new RaycastContext(_snowman, _snowmanx, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)).getType()
         == HitResult.Type.MISS;
   }

   @Override
   public float getYaw(float tickDelta) {
      return tickDelta == 1.0F ? this.headYaw : MathHelper.lerp(tickDelta, this.prevHeadYaw, this.headYaw);
   }

   public float getHandSwingProgress(float tickDelta) {
      float _snowman = this.handSwingProgress - this.lastHandSwingProgress;
      if (_snowman < 0.0F) {
         _snowman++;
      }

      return this.lastHandSwingProgress + _snowman * tickDelta;
   }

   public boolean canMoveVoluntarily() {
      return !this.world.isClient;
   }

   @Override
   public boolean collides() {
      return !this.removed;
   }

   @Override
   public boolean isPushable() {
      return this.isAlive() && !this.isSpectator() && !this.isClimbing();
   }

   @Override
   protected void scheduleVelocityUpdate() {
      this.velocityModified = this.random.nextDouble() >= this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
   }

   @Override
   public float getHeadYaw() {
      return this.headYaw;
   }

   @Override
   public void setHeadYaw(float headYaw) {
      this.headYaw = headYaw;
   }

   @Override
   public void setYaw(float yaw) {
      this.bodyYaw = yaw;
   }

   @Override
   protected Vec3d method_30633(Direction.Axis _snowman, class_5459.class_5460 _snowman) {
      return method_31079(super.method_30633(_snowman, _snowman));
   }

   public static Vec3d method_31079(Vec3d _snowman) {
      return new Vec3d(_snowman.x, _snowman.y, 0.0);
   }

   public float getAbsorptionAmount() {
      return this.absorptionAmount;
   }

   public void setAbsorptionAmount(float amount) {
      if (amount < 0.0F) {
         amount = 0.0F;
      }

      this.absorptionAmount = amount;
   }

   public void enterCombat() {
   }

   public void endCombat() {
   }

   protected void markEffectsDirty() {
      this.effectsChanged = true;
   }

   public abstract Arm getMainArm();

   public boolean isUsingItem() {
      return (this.dataTracker.get(LIVING_FLAGS) & 1) > 0;
   }

   public Hand getActiveHand() {
      return (this.dataTracker.get(LIVING_FLAGS) & 2) > 0 ? Hand.OFF_HAND : Hand.MAIN_HAND;
   }

   private void tickActiveItemStack() {
      if (this.isUsingItem()) {
         if (ItemStack.areItemsEqual(this.getStackInHand(this.getActiveHand()), this.activeItemStack)) {
            this.activeItemStack = this.getStackInHand(this.getActiveHand());
            this.activeItemStack.usageTick(this.world, this, this.getItemUseTimeLeft());
            if (this.shouldSpawnConsumptionEffects()) {
               this.spawnConsumptionEffects(this.activeItemStack, 5);
            }

            if (--this.itemUseTimeLeft == 0 && !this.world.isClient && !this.activeItemStack.isUsedOnRelease()) {
               this.consumeItem();
            }
         } else {
            this.clearActiveItem();
         }
      }
   }

   private boolean shouldSpawnConsumptionEffects() {
      int _snowman = this.getItemUseTimeLeft();
      FoodComponent _snowmanx = this.activeItemStack.getItem().getFoodComponent();
      boolean _snowmanxx = _snowmanx != null && _snowmanx.isSnack();
      _snowmanxx |= _snowman <= this.activeItemStack.getMaxUseTime() - 7;
      return _snowmanxx && _snowman % 4 == 0;
   }

   private void updateLeaningPitch() {
      this.lastLeaningPitch = this.leaningPitch;
      if (this.isInSwimmingPose()) {
         this.leaningPitch = Math.min(1.0F, this.leaningPitch + 0.09F);
      } else {
         this.leaningPitch = Math.max(0.0F, this.leaningPitch - 0.09F);
      }
   }

   protected void setLivingFlag(int mask, boolean value) {
      int _snowman = this.dataTracker.get(LIVING_FLAGS);
      if (value) {
         _snowman |= mask;
      } else {
         _snowman &= ~mask;
      }

      this.dataTracker.set(LIVING_FLAGS, (byte)_snowman);
   }

   public void setCurrentHand(Hand hand) {
      ItemStack _snowman = this.getStackInHand(hand);
      if (!_snowman.isEmpty() && !this.isUsingItem()) {
         this.activeItemStack = _snowman;
         this.itemUseTimeLeft = _snowman.getMaxUseTime();
         if (!this.world.isClient) {
            this.setLivingFlag(1, true);
            this.setLivingFlag(2, hand == Hand.OFF_HAND);
         }
      }
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      super.onTrackedDataSet(data);
      if (SLEEPING_POSITION.equals(data)) {
         if (this.world.isClient) {
            this.getSleepingPosition().ifPresent(this::setPositionInBed);
         }
      } else if (LIVING_FLAGS.equals(data) && this.world.isClient) {
         if (this.isUsingItem() && this.activeItemStack.isEmpty()) {
            this.activeItemStack = this.getStackInHand(this.getActiveHand());
            if (!this.activeItemStack.isEmpty()) {
               this.itemUseTimeLeft = this.activeItemStack.getMaxUseTime();
            }
         } else if (!this.isUsingItem() && !this.activeItemStack.isEmpty()) {
            this.activeItemStack = ItemStack.EMPTY;
            this.itemUseTimeLeft = 0;
         }
      }
   }

   @Override
   public void lookAt(EntityAnchorArgumentType.EntityAnchor anchorPoint, Vec3d target) {
      super.lookAt(anchorPoint, target);
      this.prevHeadYaw = this.headYaw;
      this.bodyYaw = this.headYaw;
      this.prevBodyYaw = this.bodyYaw;
   }

   protected void spawnConsumptionEffects(ItemStack stack, int particleCount) {
      if (!stack.isEmpty() && this.isUsingItem()) {
         if (stack.getUseAction() == UseAction.DRINK) {
            this.playSound(this.getDrinkSound(stack), 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
         }

         if (stack.getUseAction() == UseAction.EAT) {
            this.spawnItemParticles(stack, particleCount);
            this.playSound(
               this.getEatSound(stack), 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F
            );
         }
      }
   }

   private void spawnItemParticles(ItemStack stack, int count) {
      for (int _snowman = 0; _snowman < count; _snowman++) {
         Vec3d _snowmanx = new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         _snowmanx = _snowmanx.rotateX(-this.pitch * (float) (Math.PI / 180.0));
         _snowmanx = _snowmanx.rotateY(-this.yaw * (float) (Math.PI / 180.0));
         double _snowmanxx = (double)(-this.random.nextFloat()) * 0.6 - 0.3;
         Vec3d _snowmanxxx = new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.3, _snowmanxx, 0.6);
         _snowmanxxx = _snowmanxxx.rotateX(-this.pitch * (float) (Math.PI / 180.0));
         _snowmanxxx = _snowmanxxx.rotateY(-this.yaw * (float) (Math.PI / 180.0));
         _snowmanxxx = _snowmanxxx.add(this.getX(), this.getEyeY(), this.getZ());
         this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), _snowmanxxx.x, _snowmanxxx.y, _snowmanxxx.z, _snowmanx.x, _snowmanx.y + 0.05, _snowmanx.z);
      }
   }

   protected void consumeItem() {
      Hand _snowman = this.getActiveHand();
      if (!this.activeItemStack.equals(this.getStackInHand(_snowman))) {
         this.stopUsingItem();
      } else {
         if (!this.activeItemStack.isEmpty() && this.isUsingItem()) {
            this.spawnConsumptionEffects(this.activeItemStack, 16);
            ItemStack _snowmanx = this.activeItemStack.finishUsing(this.world, this);
            if (_snowmanx != this.activeItemStack) {
               this.setStackInHand(_snowman, _snowmanx);
            }

            this.clearActiveItem();
         }
      }
   }

   public ItemStack getActiveItem() {
      return this.activeItemStack;
   }

   public int getItemUseTimeLeft() {
      return this.itemUseTimeLeft;
   }

   public int getItemUseTime() {
      return this.isUsingItem() ? this.activeItemStack.getMaxUseTime() - this.getItemUseTimeLeft() : 0;
   }

   public void stopUsingItem() {
      if (!this.activeItemStack.isEmpty()) {
         this.activeItemStack.onStoppedUsing(this.world, this, this.getItemUseTimeLeft());
         if (this.activeItemStack.isUsedOnRelease()) {
            this.tickActiveItemStack();
         }
      }

      this.clearActiveItem();
   }

   public void clearActiveItem() {
      if (!this.world.isClient) {
         this.setLivingFlag(1, false);
      }

      this.activeItemStack = ItemStack.EMPTY;
      this.itemUseTimeLeft = 0;
   }

   public boolean isBlocking() {
      if (this.isUsingItem() && !this.activeItemStack.isEmpty()) {
         Item _snowman = this.activeItemStack.getItem();
         return _snowman.getUseAction(this.activeItemStack) != UseAction.BLOCK ? false : _snowman.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= 5;
      } else {
         return false;
      }
   }

   public boolean isHoldingOntoLadder() {
      return this.isSneaking();
   }

   public boolean isFallFlying() {
      return this.getFlag(7);
   }

   @Override
   public boolean isInSwimmingPose() {
      return super.isInSwimmingPose() || !this.isFallFlying() && this.getPose() == EntityPose.FALL_FLYING;
   }

   public int getRoll() {
      return this.roll;
   }

   public boolean teleport(double x, double y, double z, boolean particleEffects) {
      double _snowman = this.getX();
      double _snowmanx = this.getY();
      double _snowmanxx = this.getZ();
      double _snowmanxxx = y;
      boolean _snowmanxxxx = false;
      BlockPos _snowmanxxxxx = new BlockPos(x, y, z);
      World _snowmanxxxxxx = this.world;
      if (_snowmanxxxxxx.isChunkLoaded(_snowmanxxxxx)) {
         boolean _snowmanxxxxxxx = false;

         while (!_snowmanxxxxxxx && _snowmanxxxxx.getY() > 0) {
            BlockPos _snowmanxxxxxxxx = _snowmanxxxxx.down();
            BlockState _snowmanxxxxxxxxx = _snowmanxxxxxx.getBlockState(_snowmanxxxxxxxx);
            if (_snowmanxxxxxxxxx.getMaterial().blocksMovement()) {
               _snowmanxxxxxxx = true;
            } else {
               _snowmanxxx--;
               _snowmanxxxxx = _snowmanxxxxxxxx;
            }
         }

         if (_snowmanxxxxxxx) {
            this.requestTeleport(x, _snowmanxxx, z);
            if (_snowmanxxxxxx.isSpaceEmpty(this) && !_snowmanxxxxxx.containsFluid(this.getBoundingBox())) {
               _snowmanxxxx = true;
            }
         }
      }

      if (!_snowmanxxxx) {
         this.requestTeleport(_snowman, _snowmanx, _snowmanxx);
         return false;
      } else {
         if (particleEffects) {
            _snowmanxxxxxx.sendEntityStatus(this, (byte)46);
         }

         if (this instanceof PathAwareEntity) {
            ((PathAwareEntity)this).getNavigation().stop();
         }

         return true;
      }
   }

   public boolean isAffectedBySplashPotions() {
      return true;
   }

   public boolean isMobOrPlayer() {
      return true;
   }

   public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
   }

   public boolean canEquip(ItemStack stack) {
      return false;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new MobSpawnS2CPacket(this);
   }

   @Override
   public EntityDimensions getDimensions(EntityPose pose) {
      return pose == EntityPose.SLEEPING ? SLEEPING_DIMENSIONS : super.getDimensions(pose).scaled(this.getScaleFactor());
   }

   public ImmutableList<EntityPose> getPoses() {
      return ImmutableList.of(EntityPose.STANDING);
   }

   public Box getBoundingBox(EntityPose pose) {
      EntityDimensions _snowman = this.getDimensions(pose);
      return new Box((double)(-_snowman.width / 2.0F), 0.0, (double)(-_snowman.width / 2.0F), (double)(_snowman.width / 2.0F), (double)_snowman.height, (double)(_snowman.width / 2.0F));
   }

   public Optional<BlockPos> getSleepingPosition() {
      return this.dataTracker.get(SLEEPING_POSITION);
   }

   public void setSleepingPosition(BlockPos pos) {
      this.dataTracker.set(SLEEPING_POSITION, Optional.of(pos));
   }

   public void clearSleepingPosition() {
      this.dataTracker.set(SLEEPING_POSITION, Optional.empty());
   }

   public boolean isSleeping() {
      return this.getSleepingPosition().isPresent();
   }

   public void sleep(BlockPos pos) {
      if (this.hasVehicle()) {
         this.stopRiding();
      }

      BlockState _snowman = this.world.getBlockState(pos);
      if (_snowman.getBlock() instanceof BedBlock) {
         this.world.setBlockState(pos, _snowman.with(BedBlock.OCCUPIED, Boolean.valueOf(true)), 3);
      }

      this.setPose(EntityPose.SLEEPING);
      this.setPositionInBed(pos);
      this.setSleepingPosition(pos);
      this.setVelocity(Vec3d.ZERO);
      this.velocityDirty = true;
   }

   private void setPositionInBed(BlockPos pos) {
      this.updatePosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.6875, (double)pos.getZ() + 0.5);
   }

   private boolean isSleepingInBed() {
      return this.getSleepingPosition().map(_snowman -> this.world.getBlockState(_snowman).getBlock() instanceof BedBlock).orElse(false);
   }

   public void wakeUp() {
      this.getSleepingPosition().filter(this.world::isChunkLoaded).ifPresent(_snowman -> {
         BlockState _snowmanx = this.world.getBlockState(_snowman);
         if (_snowmanx.getBlock() instanceof BedBlock) {
            this.world.setBlockState(_snowman, _snowmanx.with(BedBlock.OCCUPIED, Boolean.valueOf(false)), 3);
            Vec3d _snowmanxx = BedBlock.findWakeUpPosition(this.getType(), this.world, _snowman, this.yaw).orElseGet(() -> {
               BlockPos _snowmanxxx = _snowman.up();
               return new Vec3d((double)_snowmanxxx.getX() + 0.5, (double)_snowmanxxx.getY() + 0.1, (double)_snowmanxxx.getZ() + 0.5);
            });
            Vec3d _snowmanxxx = Vec3d.ofBottomCenter(_snowman).subtract(_snowmanxx).normalize();
            float _snowmanxxxx = (float)MathHelper.wrapDegrees(MathHelper.atan2(_snowmanxxx.z, _snowmanxxx.x) * 180.0F / (float)Math.PI - 90.0);
            this.updatePosition(_snowmanxx.x, _snowmanxx.y, _snowmanxx.z);
            this.yaw = _snowmanxxxx;
            this.pitch = 0.0F;
         }
      });
      Vec3d _snowman = this.getPos();
      this.setPose(EntityPose.STANDING);
      this.updatePosition(_snowman.x, _snowman.y, _snowman.z);
      this.clearSleepingPosition();
   }

   @Nullable
   public Direction getSleepingDirection() {
      BlockPos _snowman = this.getSleepingPosition().orElse(null);
      return _snowman != null ? BedBlock.getDirection(this.world, _snowman) : null;
   }

   @Override
   public boolean isInsideWall() {
      return !this.isSleeping() && super.isInsideWall();
   }

   @Override
   protected final float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return pose == EntityPose.SLEEPING ? 0.2F : this.getActiveEyeHeight(pose, dimensions);
   }

   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return super.getEyeHeight(pose, dimensions);
   }

   public ItemStack getArrowType(ItemStack stack) {
      return ItemStack.EMPTY;
   }

   public ItemStack eatFood(World world, ItemStack stack) {
      if (stack.isFood()) {
         world.playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            this.getEatSound(stack),
            SoundCategory.NEUTRAL,
            1.0F,
            1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F
         );
         this.applyFoodEffects(stack, world, this);
         if (!(this instanceof PlayerEntity) || !((PlayerEntity)this).abilities.creativeMode) {
            stack.decrement(1);
         }
      }

      return stack;
   }

   private void applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity) {
      Item _snowman = stack.getItem();
      if (_snowman.isFood()) {
         for (Pair<StatusEffectInstance, Float> _snowmanx : _snowman.getFoodComponent().getStatusEffects()) {
            if (!world.isClient && _snowmanx.getFirst() != null && world.random.nextFloat() < (Float)_snowmanx.getSecond()) {
               targetEntity.addStatusEffect(new StatusEffectInstance((StatusEffectInstance)_snowmanx.getFirst()));
            }
         }
      }
   }

   private static byte getEquipmentBreakStatus(EquipmentSlot slot) {
      switch (slot) {
         case MAINHAND:
            return 47;
         case OFFHAND:
            return 48;
         case HEAD:
            return 49;
         case CHEST:
            return 50;
         case FEET:
            return 52;
         case LEGS:
            return 51;
         default:
            return 47;
      }
   }

   public void sendEquipmentBreakStatus(EquipmentSlot slot) {
      this.world.sendEntityStatus(this, getEquipmentBreakStatus(slot));
   }

   public void sendToolBreakStatus(Hand hand) {
      this.sendEquipmentBreakStatus(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
   }

   @Override
   public Box getVisibilityBoundingBox() {
      if (this.getEquippedStack(EquipmentSlot.HEAD).getItem() == Items.DRAGON_HEAD) {
         float _snowman = 0.5F;
         return this.getBoundingBox().expand(0.5, 0.5, 0.5);
      } else {
         return super.getVisibilityBoundingBox();
      }
   }
}
