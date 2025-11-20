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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   protected LivingEntity(EntityType<? extends LivingEntity> arg, World arg2) {
      super(arg, arg2);
      this.attributes = new AttributeContainer(DefaultAttributeRegistry.get(arg));
      this.setHealth(this.getMaxHealth());
      this.inanimate = true;
      this.randomSmallSeed = (float)((Math.random() + 1.0) * 0.01F);
      this.refreshPosition();
      this.randomLargeSeed = (float)Math.random() * 12398.0F;
      this.yaw = (float)(Math.random() * (float) (Math.PI * 2));
      this.headYaw = this.yaw;
      this.stepHeight = 0.6F;
      NbtOps lv = NbtOps.INSTANCE;
      this.brain = this.deserializeBrain(new Dynamic(lv, lv.createMap(ImmutableMap.of(lv.createString("memories"), lv.emptyMap()))));
   }

   public Brain<?> getBrain() {
      return this.brain;
   }

   protected Brain.Profile<?> createBrainProfile() {
      return Brain.createProfile(ImmutableList.of(), ImmutableList.of());
   }

   protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
      return this.createBrainProfile().deserialize(dynamic);
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
         float f = (float)MathHelper.ceil(this.fallDistance - 3.0F);
         if (!landedState.isAir()) {
            double e = Math.min((double)(0.2F + f / 15.0F), 2.5);
            int i = (int)(150.0 * e);
            ((ServerWorld)this.world)
               .spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, landedState), this.getX(), this.getY(), this.getZ(), i, 0.0, 0.0, 0.0, 0.15F);
         }
      }

      super.fall(heightDifference, onGround, landedState, landedPosition);
   }

   public boolean canBreatheInWater() {
      return this.getGroup() == EntityGroup.UNDEAD;
   }

   @Environment(EnvType.CLIENT)
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
      boolean bl = this instanceof PlayerEntity;
      if (this.isAlive()) {
         if (this.isInsideWall()) {
            this.damage(DamageSource.IN_WALL, 1.0F);
         } else if (bl && !this.world.getWorldBorder().contains(this.getBoundingBox())) {
            double d = this.world.getWorldBorder().getDistanceInsideBorder(this) + this.world.getWorldBorder().getBuffer();
            if (d < 0.0) {
               double e = this.world.getWorldBorder().getDamagePerBlock();
               if (e > 0.0) {
                  this.damage(DamageSource.IN_WALL, (float)Math.max(1, MathHelper.floor(-d * e)));
               }
            }
         }
      }

      if (this.isFireImmune() || this.world.isClient) {
         this.extinguish();
      }

      boolean bl2 = bl && ((PlayerEntity)this).abilities.invulnerable;
      if (this.isAlive()) {
         if (this.isSubmergedIn(FluidTags.WATER)
            && !this.world.getBlockState(new BlockPos(this.getX(), this.getEyeY(), this.getZ())).isOf(Blocks.BUBBLE_COLUMN)) {
            if (!this.canBreatheInWater() && !StatusEffectUtil.hasWaterBreathing(this) && !bl2) {
               this.setAir(this.getNextAirUnderwater(this.getAir()));
               if (this.getAir() == -20) {
                  this.setAir(0);
                  Vec3d lv = this.getVelocity();

                  for (int i = 0; i < 8; i++) {
                     double f = this.random.nextDouble() - this.random.nextDouble();
                     double g = this.random.nextDouble() - this.random.nextDouble();
                     double h = this.random.nextDouble() - this.random.nextDouble();
                     this.world.addParticle(ParticleTypes.BUBBLE, this.getX() + f, this.getY() + g, this.getZ() + h, lv.x, lv.y, lv.z);
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
            BlockPos lv2 = this.getBlockPos();
            if (!Objects.equal(this.lastBlockPos, lv2)) {
               this.lastBlockPos = lv2;
               this.applyMovementEffects(lv2);
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
      Vec3d lv = this.getVelocity();
      this.world
         .addParticle(
            ParticleTypes.SOUL,
            this.getX() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(),
            this.getY() + 0.1,
            this.getZ() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(),
            lv.x * -0.2,
            0.1,
            lv.z * -0.2
         );
      float f = this.random.nextFloat() * 0.4F + this.random.nextFloat() > 0.9F ? 0.6F : 0.0F;
      this.playSound(SoundEvents.PARTICLE_SOUL_ESCAPE, f, 0.6F + this.random.nextFloat() * 0.4F);
   }

   protected boolean isOnSoulSpeedBlock() {
      return this.world.getBlockState(this.getVelocityAffectingPos()).isIn(BlockTags.SOUL_SPEED_BLOCKS);
   }

   @Override
   protected float getVelocityMultiplier() {
      return this.isOnSoulSpeedBlock() && EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, this) > 0 ? 1.0F : super.getVelocityMultiplier();
   }

   protected boolean method_29500(BlockState arg) {
      return !arg.isAir() || this.isFallFlying();
   }

   protected void removeSoulSpeedBoost() {
      EntityAttributeInstance lv = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
      if (lv != null) {
         if (lv.getModifier(SOUL_SPEED_BOOST_ID) != null) {
            lv.removeModifier(SOUL_SPEED_BOOST_ID);
         }
      }
   }

   protected void addSoulSpeedBoostIfNeeded() {
      if (!this.getLandingBlockState().isAir()) {
         int i = EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, this);
         if (i > 0 && this.isOnSoulSpeedBlock()) {
            EntityAttributeInstance lv = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (lv == null) {
               return;
            }

            lv.addTemporaryModifier(
               new EntityAttributeModifier(
                  SOUL_SPEED_BOOST_ID, "Soul speed boost", (double)(0.03F * (1.0F + (float)i * 0.35F)), EntityAttributeModifier.Operation.ADDITION
               )
            );
            if (this.getRandom().nextFloat() < 0.04F) {
               ItemStack lv2 = this.getEquippedStack(EquipmentSlot.FEET);
               lv2.damage(1, this, arg -> arg.sendEquipmentBreakStatus(EquipmentSlot.FEET));
            }
         }
      }
   }

   protected void applyMovementEffects(BlockPos pos) {
      int i = EnchantmentHelper.getEquipmentLevel(Enchantments.FROST_WALKER, this);
      if (i > 0) {
         FrostWalkerEnchantment.freezeWater(this, this.world, pos, i);
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

         for (int i = 0; i < 20; i++) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), d, e, f);
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
      int j = EnchantmentHelper.getRespiration(this);
      return j > 0 && this.random.nextInt(j + 1) > 0 ? air : air - 1;
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
         SoundEvent lv = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
         Item lv2 = stack.getItem();
         if (lv2 instanceof ArmorItem) {
            lv = ((ArmorItem)lv2).getMaterial().getEquipSound();
         } else if (lv2 == Items.ELYTRA) {
            lv = SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
         }

         this.playSound(lv, 1.0F, 1.0F);
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
         ListTag lv = new ListTag();

         for (StatusEffectInstance lv2 : this.activeStatusEffects.values()) {
            lv.add(lv2.toTag(new CompoundTag()));
         }

         tag.put("ActiveEffects", lv);
      }

      tag.putBoolean("FallFlying", this.isFallFlying());
      this.getSleepingPosition().ifPresent(arg2 -> {
         tag.putInt("SleepingX", arg2.getX());
         tag.putInt("SleepingY", arg2.getY());
         tag.putInt("SleepingZ", arg2.getZ());
      });
      DataResult<Tag> dataResult = this.brain.encode(NbtOps.INSTANCE);
      dataResult.resultOrPartial(LOGGER::error).ifPresent(arg2 -> tag.put("Brain", arg2));
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.setAbsorptionAmount(tag.getFloat("AbsorptionAmount"));
      if (tag.contains("Attributes", 9) && this.world != null && !this.world.isClient) {
         this.getAttributes().fromTag(tag.getList("Attributes", 10));
      }

      if (tag.contains("ActiveEffects", 9)) {
         ListTag lv = tag.getList("ActiveEffects", 10);

         for (int i = 0; i < lv.size(); i++) {
            CompoundTag lv2 = lv.getCompound(i);
            StatusEffectInstance lv3 = StatusEffectInstance.fromTag(lv2);
            if (lv3 != null) {
               this.activeStatusEffects.put(lv3.getEffectType(), lv3);
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
         String string = tag.getString("Team");
         Team lv4 = this.world.getScoreboard().getTeam(string);
         boolean bl = lv4 != null && this.world.getScoreboard().addPlayerToTeam(this.getUuidAsString(), lv4);
         if (!bl) {
            LOGGER.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", string);
         }
      }

      if (tag.getBoolean("FallFlying")) {
         this.setFlag(7, true);
      }

      if (tag.contains("SleepingX", 99) && tag.contains("SleepingY", 99) && tag.contains("SleepingZ", 99)) {
         BlockPos lv5 = new BlockPos(tag.getInt("SleepingX"), tag.getInt("SleepingY"), tag.getInt("SleepingZ"));
         this.setSleepingPosition(lv5);
         this.dataTracker.set(POSE, EntityPose.SLEEPING);
         if (!this.firstUpdate) {
            this.setPositionInBed(lv5);
         }
      }

      if (tag.contains("Brain", 10)) {
         this.brain = this.deserializeBrain(new Dynamic(NbtOps.INSTANCE, tag.get("Brain")));
      }
   }

   protected void tickStatusEffects() {
      Iterator<StatusEffect> iterator = this.activeStatusEffects.keySet().iterator();

      try {
         while (iterator.hasNext()) {
            StatusEffect lv = iterator.next();
            StatusEffectInstance lv2 = this.activeStatusEffects.get(lv);
            if (!lv2.update(this, () -> this.onStatusEffectUpgraded(lv2, true))) {
               if (!this.world.isClient) {
                  iterator.remove();
                  this.onStatusEffectRemoved(lv2);
               }
            } else if (lv2.getDuration() % 600 == 0) {
               this.onStatusEffectUpgraded(lv2, false);
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

      int i = this.dataTracker.get(POTION_SWIRLS_COLOR);
      boolean bl = this.dataTracker.get(POTION_SWIRLS_AMBIENT);
      if (i > 0) {
         boolean bl2;
         if (this.isInvisible()) {
            bl2 = this.random.nextInt(15) == 0;
         } else {
            bl2 = this.random.nextBoolean();
         }

         if (bl) {
            bl2 &= this.random.nextInt(5) == 0;
         }

         if (bl2 && i > 0) {
            double d = (double)(i >> 16 & 0xFF) / 255.0;
            double e = (double)(i >> 8 & 0xFF) / 255.0;
            double f = (double)(i >> 0 & 0xFF) / 255.0;
            this.world
               .addParticle(
                  bl ? ParticleTypes.AMBIENT_ENTITY_EFFECT : ParticleTypes.ENTITY_EFFECT,
                  this.getParticleX(0.5),
                  this.getRandomBodyY(),
                  this.getParticleZ(0.5),
                  d,
                  e,
                  f
               );
         }
      }
   }

   protected void updatePotionVisibility() {
      if (this.activeStatusEffects.isEmpty()) {
         this.clearPotionSwirls();
         this.setInvisible(false);
      } else {
         Collection<StatusEffectInstance> collection = this.activeStatusEffects.values();
         this.dataTracker.set(POTION_SWIRLS_AMBIENT, containsOnlyAmbientEffects(collection));
         this.dataTracker.set(POTION_SWIRLS_COLOR, PotionUtil.getColor(collection));
         this.setInvisible(this.hasStatusEffect(StatusEffects.INVISIBILITY));
      }
   }

   public double getAttackDistanceScalingFactor(@Nullable Entity entity) {
      double d = 1.0;
      if (this.isSneaky()) {
         d *= 0.8;
      }

      if (this.isInvisible()) {
         float f = this.getArmorVisibility();
         if (f < 0.1F) {
            f = 0.1F;
         }

         d *= 0.7 * (double)f;
      }

      if (entity != null) {
         ItemStack lv = this.getEquippedStack(EquipmentSlot.HEAD);
         Item lv2 = lv.getItem();
         EntityType<?> lv3 = entity.getType();
         if (lv3 == EntityType.SKELETON && lv2 == Items.SKELETON_SKULL
            || lv3 == EntityType.ZOMBIE && lv2 == Items.ZOMBIE_HEAD
            || lv3 == EntityType.CREEPER && lv2 == Items.CREEPER_HEAD) {
            d *= 0.5;
         }
      }

      return d;
   }

   public boolean canTarget(LivingEntity target) {
      return true;
   }

   public boolean isTarget(LivingEntity entity, TargetPredicate predicate) {
      return predicate.test(this, entity);
   }

   public static boolean containsOnlyAmbientEffects(Collection<StatusEffectInstance> effects) {
      for (StatusEffectInstance lv : effects) {
         if (!lv.isAmbient()) {
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
         Iterator<StatusEffectInstance> iterator = this.activeStatusEffects.values().iterator();

         boolean bl;
         for (bl = false; iterator.hasNext(); bl = true) {
            this.onStatusEffectRemoved(iterator.next());
            iterator.remove();
         }

         return bl;
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
         StatusEffectInstance lv = this.activeStatusEffects.get(effect.getEffectType());
         if (lv == null) {
            this.activeStatusEffects.put(effect.getEffectType(), effect);
            this.onStatusEffectApplied(effect);
            return true;
         } else if (lv.upgrade(effect)) {
            this.onStatusEffectUpgraded(lv, true);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean canHaveStatusEffect(StatusEffectInstance effect) {
      if (this.getGroup() == EntityGroup.UNDEAD) {
         StatusEffect lv = effect.getEffectType();
         if (lv == StatusEffects.REGENERATION || lv == StatusEffects.POISON) {
            return false;
         }
      }

      return true;
   }

   @Environment(EnvType.CLIENT)
   public void applyStatusEffect(StatusEffectInstance effect) {
      if (this.canHaveStatusEffect(effect)) {
         StatusEffectInstance lv = this.activeStatusEffects.put(effect.getEffectType(), effect);
         if (lv == null) {
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
      StatusEffectInstance lv = this.removeStatusEffectInternal(type);
      if (lv != null) {
         this.onStatusEffectRemoved(lv);
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
         StatusEffect lv = effect.getEffectType();
         lv.onRemoved(this, this.getAttributes(), effect.getAmplifier());
         lv.onApplied(this, this.getAttributes(), effect.getAmplifier());
      }
   }

   protected void onStatusEffectRemoved(StatusEffectInstance effect) {
      this.effectsChanged = true;
      if (!this.world.isClient) {
         effect.getEffectType().onRemoved(this, this.getAttributes(), effect.getAmplifier());
      }
   }

   public void heal(float amount) {
      float g = this.getHealth();
      if (g > 0.0F) {
         this.setHealth(g + amount);
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
         float g = amount;
         if ((source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) && !this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            this.getEquippedStack(EquipmentSlot.HEAD)
               .damage((int)(amount * 4.0F + this.random.nextFloat() * amount * 2.0F), this, arg -> arg.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
            amount *= 0.75F;
         }

         boolean bl = false;
         float h = 0.0F;
         if (amount > 0.0F && this.blockedByShield(source)) {
            this.damageShield(amount);
            h = amount;
            amount = 0.0F;
            if (!source.isProjectile()) {
               Entity lv = source.getSource();
               if (lv instanceof LivingEntity) {
                  this.takeShieldHit((LivingEntity)lv);
               }
            }

            bl = true;
         }

         this.limbDistance = 1.5F;
         boolean bl2 = true;
         if ((float)this.timeUntilRegen > 10.0F) {
            if (amount <= this.lastDamageTaken) {
               return false;
            }

            this.applyDamage(source, amount - this.lastDamageTaken);
            this.lastDamageTaken = amount;
            bl2 = false;
         } else {
            this.lastDamageTaken = amount;
            this.timeUntilRegen = 20;
            this.applyDamage(source, amount);
            this.maxHurtTime = 10;
            this.hurtTime = this.maxHurtTime;
         }

         this.knockbackVelocity = 0.0F;
         Entity lv2 = source.getAttacker();
         if (lv2 != null) {
            if (lv2 instanceof LivingEntity) {
               this.setAttacker((LivingEntity)lv2);
            }

            if (lv2 instanceof PlayerEntity) {
               this.playerHitTimer = 100;
               this.attackingPlayer = (PlayerEntity)lv2;
            } else if (lv2 instanceof WolfEntity) {
               WolfEntity lv3 = (WolfEntity)lv2;
               if (lv3.isTamed()) {
                  this.playerHitTimer = 100;
                  LivingEntity lv4 = lv3.getOwner();
                  if (lv4 != null && lv4.getType() == EntityType.PLAYER) {
                     this.attackingPlayer = (PlayerEntity)lv4;
                  } else {
                     this.attackingPlayer = null;
                  }
               }
            }
         }

         if (bl2) {
            if (bl) {
               this.world.sendEntityStatus(this, (byte)29);
            } else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
               this.world.sendEntityStatus(this, (byte)33);
            } else {
               byte b;
               if (source == DamageSource.DROWN) {
                  b = 36;
               } else if (source.isFire()) {
                  b = 37;
               } else if (source == DamageSource.SWEET_BERRY_BUSH) {
                  b = 44;
               } else {
                  b = 2;
               }

               this.world.sendEntityStatus(this, b);
            }

            if (source != DamageSource.DROWN && (!bl || amount > 0.0F)) {
               this.scheduleVelocityUpdate();
            }

            if (lv2 != null) {
               double i = lv2.getX() - this.getX();

               double j;
               for (j = lv2.getZ() - this.getZ(); i * i + j * j < 1.0E-4; j = (Math.random() - Math.random()) * 0.01) {
                  i = (Math.random() - Math.random()) * 0.01;
               }

               this.knockbackVelocity = (float)(MathHelper.atan2(j, i) * 180.0F / (float)Math.PI - (double)this.yaw);
               this.takeKnockback(0.4F, i, j);
            } else {
               this.knockbackVelocity = (float)((int)(Math.random() * 2.0) * 180);
            }
         }

         if (this.isDead()) {
            if (!this.tryUseTotem(source)) {
               SoundEvent lv5 = this.getDeathSound();
               if (bl2 && lv5 != null) {
                  this.playSound(lv5, this.getSoundVolume(), this.getSoundPitch());
               }

               this.onDeath(source);
            }
         } else if (bl2) {
            this.playHurtSound(source);
         }

         boolean bl3 = !bl || amount > 0.0F;
         if (bl3) {
            this.lastDamageSource = source;
            this.lastDamageTime = this.world.getTime();
         }

         if (this instanceof ServerPlayerEntity) {
            Criteria.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity)this, source, g, amount, bl);
            if (h > 0.0F && h < 3.4028235E37F) {
               ((ServerPlayerEntity)this).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(h * 10.0F));
            }
         }

         if (lv2 instanceof ServerPlayerEntity) {
            Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)lv2, this, source, g, amount, bl);
         }

         return bl3;
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
         ItemStack lv = null;

         for (Hand lv2 : Hand.values()) {
            ItemStack lv3 = this.getStackInHand(lv2);
            if (lv3.getItem() == Items.TOTEM_OF_UNDYING) {
               lv = lv3.copy();
               lv3.decrement(1);
               break;
            }
         }

         if (lv != null) {
            if (this instanceof ServerPlayerEntity) {
               ServerPlayerEntity lv4 = (ServerPlayerEntity)this;
               lv4.incrementStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
               Criteria.USED_TOTEM.trigger(lv4, lv);
            }

            this.setHealth(1.0F);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            this.world.sendEntityStatus(this, (byte)35);
         }

         return lv != null;
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
      SoundEvent lv = this.getHurtSound(source);
      if (lv != null) {
         this.playSound(lv, this.getSoundVolume(), this.getSoundPitch());
      }
   }

   private boolean blockedByShield(DamageSource source) {
      Entity lv = source.getSource();
      boolean bl = false;
      if (lv instanceof PersistentProjectileEntity) {
         PersistentProjectileEntity lv2 = (PersistentProjectileEntity)lv;
         if (lv2.getPierceLevel() > 0) {
            bl = true;
         }
      }

      if (!source.bypassesArmor() && this.isBlocking() && !bl) {
         Vec3d lv3 = source.getPosition();
         if (lv3 != null) {
            Vec3d lv4 = this.getRotationVec(1.0F);
            Vec3d lv5 = lv3.reverseSubtract(this.getPos()).normalize();
            lv5 = new Vec3d(lv5.x, 0.0, lv5.z);
            if (lv5.dotProduct(lv4) < 0.0) {
               return true;
            }
         }
      }

      return false;
   }

   @Environment(EnvType.CLIENT)
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
         Entity lv = source.getAttacker();
         LivingEntity lv2 = this.getPrimeAdversary();
         if (this.scoreAmount >= 0 && lv2 != null) {
            lv2.updateKilledAdvancementCriterion(this, this.scoreAmount, source);
         }

         if (this.isSleeping()) {
            this.wakeUp();
         }

         this.dead = true;
         this.getDamageTracker().update();
         if (this.world instanceof ServerWorld) {
            if (lv != null) {
               lv.onKilledOther((ServerWorld)this.world, this);
            }

            this.drop(source);
            this.onKilledBy(lv2);
         }

         this.world.sendEntityStatus(this, (byte)3);
         this.setPose(EntityPose.DYING);
      }
   }

   protected void onKilledBy(@Nullable LivingEntity adversary) {
      if (!this.world.isClient) {
         boolean bl = false;
         if (adversary instanceof WitherEntity) {
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
               BlockPos lv = this.getBlockPos();
               BlockState lv2 = Blocks.WITHER_ROSE.getDefaultState();
               if (this.world.getBlockState(lv).isAir() && lv2.canPlaceAt(this.world, lv)) {
                  this.world.setBlockState(lv, lv2, 3);
                  bl = true;
               }
            }

            if (!bl) {
               ItemEntity lv3 = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.WITHER_ROSE));
               this.world.spawnEntity(lv3);
            }
         }
      }
   }

   protected void drop(DamageSource source) {
      Entity lv = source.getAttacker();
      int i;
      if (lv instanceof PlayerEntity) {
         i = EnchantmentHelper.getLooting((LivingEntity)lv);
      } else {
         i = 0;
      }

      boolean bl = this.playerHitTimer > 0;
      if (this.shouldDropLoot() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
         this.dropLoot(source, bl);
         this.dropEquipment(source, i, bl);
      }

      this.dropInventory();
      this.dropXp();
   }

   protected void dropInventory() {
   }

   protected void dropXp() {
      if (!this.world.isClient
         && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.canDropLootAndXp() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
         int i = this.getCurrentExperience(this.attackingPlayer);

         while (i > 0) {
            int j = ExperienceOrbEntity.roundToOrbSize(i);
            i -= j;
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY(), this.getZ(), j));
         }
      }
   }

   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
   }

   public Identifier getLootTable() {
      return this.getType().getLootTableId();
   }

   protected void dropLoot(DamageSource source, boolean causedByPlayer) {
      Identifier lv = this.getLootTable();
      LootTable lv2 = this.world.getServer().getLootManager().getTable(lv);
      LootContext.Builder lv3 = this.getLootContextBuilder(causedByPlayer, source);
      lv2.generateLoot(lv3.build(LootContextTypes.ENTITY), this::dropStack);
   }

   protected LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source) {
      LootContext.Builder lv = new LootContext.Builder((ServerWorld)this.world)
         .random(this.random)
         .parameter(LootContextParameters.THIS_ENTITY, this)
         .parameter(LootContextParameters.ORIGIN, this.getPos())
         .parameter(LootContextParameters.DAMAGE_SOURCE, source)
         .optionalParameter(LootContextParameters.KILLER_ENTITY, source.getAttacker())
         .optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, source.getSource());
      if (causedByPlayer && this.attackingPlayer != null) {
         lv = lv.parameter(LootContextParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).luck(this.attackingPlayer.getLuck());
      }

      return lv;
   }

   public void takeKnockback(float f, double d, double e) {
      f = (float)((double)f * (1.0 - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)));
      if (!(f <= 0.0F)) {
         this.velocityDirty = true;
         Vec3d lv = this.getVelocity();
         Vec3d lv2 = new Vec3d(d, 0.0, e).normalize().multiply((double)f);
         this.setVelocity(lv.x / 2.0 - lv2.x, this.onGround ? Math.min(0.4, lv.y / 2.0 + (double)f) : lv.y, lv.z / 2.0 - lv2.z);
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
         BlockPos lv = this.getBlockPos();
         BlockState lv2 = this.getBlockState();
         Block lv3 = lv2.getBlock();
         if (lv3.isIn(BlockTags.CLIMBABLE)) {
            this.climbingPos = Optional.of(lv);
            return true;
         } else if (lv3 instanceof TrapdoorBlock && this.canEnterTrapdoor(lv, lv2)) {
            this.climbingPos = Optional.of(lv);
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
         BlockState lv = this.world.getBlockState(pos.down());
         if (lv.isOf(Blocks.LADDER) && lv.get(LadderBlock.FACING) == state.get(TrapdoorBlock.FACING)) {
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
      boolean bl = super.handleFallDamage(fallDistance, damageMultiplier);
      int i = this.computeFallDamage(fallDistance, damageMultiplier);
      if (i > 0) {
         this.playSound(this.getFallSound(i), 1.0F, 1.0F);
         this.playBlockFallSound();
         this.damage(DamageSource.FALL, (float)i);
         return true;
      } else {
         return bl;
      }
   }

   protected int computeFallDamage(float fallDistance, float damageMultiplier) {
      StatusEffectInstance lv = this.getStatusEffect(StatusEffects.JUMP_BOOST);
      float h = lv == null ? 0.0F : (float)(lv.getAmplifier() + 1);
      return MathHelper.ceil((fallDistance - 3.0F - h) * damageMultiplier);
   }

   protected void playBlockFallSound() {
      if (!this.isSilent()) {
         int i = MathHelper.floor(this.getX());
         int j = MathHelper.floor(this.getY() - 0.2F);
         int k = MathHelper.floor(this.getZ());
         BlockState lv = this.world.getBlockState(new BlockPos(i, j, k));
         if (!lv.isAir()) {
            BlockSoundGroup lv2 = lv.getSoundGroup();
            this.playSound(lv2.getFallSound(), lv2.getVolume() * 0.5F, lv2.getPitch() * 0.75F);
         }
      }
   }

   @Environment(EnvType.CLIENT)
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
            int i = (this.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 5;
            int j = 25 - i;
            float g = amount * (float)j;
            float h = amount;
            amount = Math.max(g / 25.0F, 0.0F);
            float k = h - amount;
            if (k > 0.0F && k < 3.4028235E37F) {
               if (this instanceof ServerPlayerEntity) {
                  ((ServerPlayerEntity)this).increaseStat(Stats.DAMAGE_RESISTED, Math.round(k * 10.0F));
               } else if (source.getAttacker() instanceof ServerPlayerEntity) {
                  ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(k * 10.0F));
               }
            }
         }

         if (amount <= 0.0F) {
            return 0.0F;
         } else {
            int l = EnchantmentHelper.getProtectionAmount(this.getArmorItems(), source);
            if (l > 0) {
               amount = DamageUtil.getInflictedDamage(amount, (float)l);
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
         float h = amount - var8;
         if (h > 0.0F && h < 3.4028235E37F && source.getAttacker() instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(h * 10.0F));
         }

         if (var8 != 0.0F) {
            float i = this.getHealth();
            this.setHealth(i - var8);
            this.getDamageTracker().onDamage(source, i, var8);
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

   public void swingHand(Hand hand, boolean bl) {
      if (!this.handSwinging || this.handSwingTicks >= this.getHandSwingDuration() / 2 || this.handSwingTicks < 0) {
         this.handSwingTicks = -1;
         this.handSwinging = true;
         this.preferredHand = hand;
         if (this.world instanceof ServerWorld) {
            EntityAnimationS2CPacket lv = new EntityAnimationS2CPacket(this, hand == Hand.MAIN_HAND ? 0 : 3);
            ServerChunkManager lv2 = ((ServerWorld)this.world).getChunkManager();
            if (bl) {
               lv2.sendToNearbyPlayers(this, lv);
            } else {
               lv2.sendToOtherNearbyPlayers(this, lv);
            }
         }
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void handleStatus(byte status) {
      switch (status) {
         case 2:
         case 33:
         case 36:
         case 37:
         case 44:
            boolean bl = status == 33;
            boolean bl2 = status == 36;
            boolean bl3 = status == 37;
            boolean bl4 = status == 44;
            this.limbDistance = 1.5F;
            this.timeUntilRegen = 20;
            this.maxHurtTime = 10;
            this.hurtTime = this.maxHurtTime;
            this.knockbackVelocity = 0.0F;
            if (bl) {
               this.playSound(SoundEvents.ENCHANT_THORNS_HIT, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            DamageSource lv;
            if (bl3) {
               lv = DamageSource.ON_FIRE;
            } else if (bl2) {
               lv = DamageSource.DROWN;
            } else if (bl4) {
               lv = DamageSource.SWEET_BERRY_BUSH;
            } else {
               lv = DamageSource.GENERIC;
            }

            SoundEvent lv5 = this.getHurtSound(lv);
            if (lv5 != null) {
               this.playSound(lv5, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.damage(DamageSource.GENERIC, 0.0F);
            break;
         case 3:
            SoundEvent lv6 = this.getDeathSound();
            if (lv6 != null) {
               this.playSound(lv6, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
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
            int i = 128;

            for (int j = 0; j < 128; j++) {
               double d = (double)j / 127.0;
               float f = (this.random.nextFloat() - 0.5F) * 0.2F;
               float g = (this.random.nextFloat() - 0.5F) * 0.2F;
               float h = (this.random.nextFloat() - 0.5F) * 0.2F;
               double e = MathHelper.lerp(d, this.prevX, this.getX()) + (this.random.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
               double k = MathHelper.lerp(d, this.prevY, this.getY()) + this.random.nextDouble() * (double)this.getHeight();
               double l = MathHelper.lerp(d, this.prevZ, this.getZ()) + (this.random.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
               this.world.addParticle(ParticleTypes.PORTAL, e, k, l, (double)f, (double)g, (double)h);
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

   @Environment(EnvType.CLIENT)
   private void method_30127() {
      ItemStack lv = this.getEquippedStack(EquipmentSlot.OFFHAND);
      this.equipStack(EquipmentSlot.OFFHAND, this.getEquippedStack(EquipmentSlot.MAINHAND));
      this.equipStack(EquipmentSlot.MAINHAND, lv);
   }

   @Override
   protected void destroy() {
      this.damage(DamageSource.OUT_OF_WORLD, 4.0F);
   }

   protected void tickHandSwing() {
      int i = this.getHandSwingDuration();
      if (this.handSwinging) {
         this.handSwingTicks++;
         if (this.handSwingTicks >= i) {
            this.handSwingTicks = 0;
            this.handSwinging = false;
         }
      } else {
         this.handSwingTicks = 0;
      }

      this.handSwingProgress = (float)this.handSwingTicks / (float)i;
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
      return this.isHolding(arg2 -> arg2 == item);
   }

   public boolean isHolding(Predicate<Item> predicate) {
      return predicate.test(this.getMainHandStack().getItem()) || predicate.test(this.getOffHandStack().getItem());
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
      Iterable<ItemStack> iterable = this.getArmorItems();
      int i = 0;
      int j = 0;

      for (ItemStack lv : iterable) {
         if (!lv.isEmpty()) {
            j++;
         }

         i++;
      }

      return i > 0 ? (float)j / (float)i : 0.0F;
   }

   @Override
   public void setSprinting(boolean sprinting) {
      super.setSprinting(sprinting);
      EntityAttributeInstance lv = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
      if (lv.getModifier(SPRINTING_SPEED_BOOST_ID) != null) {
         lv.removeModifier(SPRINTING_SPEED_BOOST);
      }

      if (sprinting) {
         lv.addTemporaryModifier(SPRINTING_SPEED_BOOST);
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
      Vec3d lv2;
      if (!vehicle.removed && !this.world.getBlockState(vehicle.getBlockPos()).getBlock().isIn(BlockTags.PORTALS)) {
         lv2 = vehicle.updatePassengerForDismount(this);
      } else {
         lv2 = new Vec3d(vehicle.getX(), vehicle.getY() + (double)vehicle.getHeight(), vehicle.getZ());
      }

      this.requestTeleport(lv2.x, lv2.y, lv2.z);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean shouldRenderName() {
      return this.isCustomNameVisible();
   }

   protected float getJumpVelocity() {
      return 0.42F * this.getJumpVelocityMultiplier();
   }

   protected void jump() {
      float f = this.getJumpVelocity();
      if (this.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
         f += 0.1F * (float)(this.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1);
      }

      Vec3d lv = this.getVelocity();
      this.setVelocity(lv.x, (double)f, lv.z);
      if (this.isSprinting()) {
         float g = this.yaw * (float) (Math.PI / 180.0);
         this.setVelocity(this.getVelocity().add((double)(-MathHelper.sin(g) * 0.2F), 0.0, (double)(MathHelper.cos(g) * 0.2F)));
      }

      this.velocityDirty = true;
   }

   @Environment(EnvType.CLIENT)
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
         double d = 0.08;
         boolean bl = this.getVelocity().y <= 0.0;
         if (bl && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
            d = 0.01;
            this.fallDistance = 0.0F;
         }

         FluidState lv = this.world.getFluidState(this.getBlockPos());
         if (this.isTouchingWater() && this.method_29920() && !this.canWalkOnFluid(lv.getFluid())) {
            double e = this.getY();
            float f = this.isSprinting() ? 0.9F : this.getBaseMovementSpeedMultiplier();
            float g = 0.02F;
            float h = (float)EnchantmentHelper.getDepthStrider(this);
            if (h > 3.0F) {
               h = 3.0F;
            }

            if (!this.onGround) {
               h *= 0.5F;
            }

            if (h > 0.0F) {
               f += (0.54600006F - f) * h / 3.0F;
               g += (this.getMovementSpeed() - g) * h / 3.0F;
            }

            if (this.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
               f = 0.96F;
            }

            this.updateVelocity(g, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            Vec3d lv2 = this.getVelocity();
            if (this.horizontalCollision && this.isClimbing()) {
               lv2 = new Vec3d(lv2.x, 0.2, lv2.z);
            }

            this.setVelocity(lv2.multiply((double)f, 0.8F, (double)f));
            Vec3d lv3 = this.method_26317(d, bl, this.getVelocity());
            this.setVelocity(lv3);
            if (this.horizontalCollision && this.doesNotCollide(lv3.x, lv3.y + 0.6F - this.getY() + e, lv3.z)) {
               this.setVelocity(lv3.x, 0.3F, lv3.z);
            }
         } else if (this.isInLava() && this.method_29920() && !this.canWalkOnFluid(lv.getFluid())) {
            double i = this.getY();
            this.updateVelocity(0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            if (this.getFluidHeight(FluidTags.LAVA) <= this.method_29241()) {
               this.setVelocity(this.getVelocity().multiply(0.5, 0.8F, 0.5));
               Vec3d lv4 = this.method_26317(d, bl, this.getVelocity());
               this.setVelocity(lv4);
            } else {
               this.setVelocity(this.getVelocity().multiply(0.5));
            }

            if (!this.hasNoGravity()) {
               this.setVelocity(this.getVelocity().add(0.0, -d / 4.0, 0.0));
            }

            Vec3d lv5 = this.getVelocity();
            if (this.horizontalCollision && this.doesNotCollide(lv5.x, lv5.y + 0.6F - this.getY() + i, lv5.z)) {
               this.setVelocity(lv5.x, 0.3F, lv5.z);
            }
         } else if (this.isFallFlying()) {
            Vec3d lv6 = this.getVelocity();
            if (lv6.y > -0.5) {
               this.fallDistance = 1.0F;
            }

            Vec3d lv7 = this.getRotationVector();
            float j = this.pitch * (float) (Math.PI / 180.0);
            double k = Math.sqrt(lv7.x * lv7.x + lv7.z * lv7.z);
            double l = Math.sqrt(squaredHorizontalLength(lv6));
            double m = lv7.length();
            float n = MathHelper.cos(j);
            n = (float)((double)n * (double)n * Math.min(1.0, m / 0.4));
            lv6 = this.getVelocity().add(0.0, d * (-1.0 + (double)n * 0.75), 0.0);
            if (lv6.y < 0.0 && k > 0.0) {
               double o = lv6.y * -0.1 * (double)n;
               lv6 = lv6.add(lv7.x * o / k, o, lv7.z * o / k);
            }

            if (j < 0.0F && k > 0.0) {
               double p = l * (double)(-MathHelper.sin(j)) * 0.04;
               lv6 = lv6.add(-lv7.x * p / k, p * 3.2, -lv7.z * p / k);
            }

            if (k > 0.0) {
               lv6 = lv6.add((lv7.x / k * l - lv6.x) * 0.1, 0.0, (lv7.z / k * l - lv6.z) * 0.1);
            }

            this.setVelocity(lv6.multiply(0.99F, 0.98F, 0.99F));
            this.move(MovementType.SELF, this.getVelocity());
            if (this.horizontalCollision && !this.world.isClient) {
               double q = Math.sqrt(squaredHorizontalLength(this.getVelocity()));
               double r = l - q;
               float s = (float)(r * 10.0 - 3.0);
               if (s > 0.0F) {
                  this.playSound(this.getFallSound((int)s), 1.0F, 1.0F);
                  this.damage(DamageSource.FLY_INTO_WALL, s);
               }
            }

            if (this.onGround && !this.world.isClient) {
               this.setFlag(7, false);
            }
         } else {
            BlockPos lv8 = this.getVelocityAffectingPos();
            float t = this.world.getBlockState(lv8).getBlock().getSlipperiness();
            float u = this.onGround ? t * 0.91F : 0.91F;
            Vec3d lv9 = this.method_26318(movementInput, t);
            double v = lv9.y;
            if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
               v += (0.05 * (double)(this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - lv9.y) * 0.2;
               this.fallDistance = 0.0F;
            } else if (this.world.isClient && !this.world.isChunkLoaded(lv8)) {
               if (this.getY() > 0.0) {
                  v = -0.1;
               } else {
                  v = 0.0;
               }
            } else if (!this.hasNoGravity()) {
               v -= d;
            }

            this.setVelocity(lv9.x * (double)u, v * 0.98F, lv9.z * (double)u);
         }
      }

      this.method_29242(this, this instanceof Flutterer);
   }

   public void method_29242(LivingEntity arg, boolean bl) {
      arg.lastLimbDistance = arg.limbDistance;
      double d = arg.getX() - arg.prevX;
      double e = bl ? arg.getY() - arg.prevY : 0.0;
      double f = arg.getZ() - arg.prevZ;
      float g = MathHelper.sqrt(d * d + e * e + f * f) * 4.0F;
      if (g > 1.0F) {
         g = 1.0F;
      }

      arg.limbDistance = arg.limbDistance + (g - arg.limbDistance) * 0.4F;
      arg.limbAngle = arg.limbAngle + arg.limbDistance;
   }

   public Vec3d method_26318(Vec3d arg, float f) {
      this.updateVelocity(this.getMovementSpeed(f), arg);
      this.setVelocity(this.applyClimbingSpeed(this.getVelocity()));
      this.move(MovementType.SELF, this.getVelocity());
      Vec3d lv = this.getVelocity();
      if ((this.horizontalCollision || this.jumping) && this.isClimbing()) {
         lv = new Vec3d(lv.x, 0.2, lv.z);
      }

      return lv;
   }

   public Vec3d method_26317(double d, boolean bl, Vec3d arg) {
      if (!this.hasNoGravity() && !this.isSprinting()) {
         double e;
         if (bl && Math.abs(arg.y - 0.005) >= 0.003 && Math.abs(arg.y - d / 16.0) < 0.003) {
            e = -0.003;
         } else {
            e = arg.y - d / 16.0;
         }

         return new Vec3d(arg.x, e, arg.z);
      } else {
         return arg;
      }
   }

   private Vec3d applyClimbingSpeed(Vec3d motion) {
      if (this.isClimbing()) {
         this.fallDistance = 0.0F;
         float f = 0.15F;
         double d = MathHelper.clamp(motion.x, -0.15F, 0.15F);
         double e = MathHelper.clamp(motion.z, -0.15F, 0.15F);
         double g = Math.max(motion.y, -0.15F);
         if (g < 0.0 && !this.getBlockState().isOf(Blocks.SCAFFOLDING) && this.isHoldingOntoLadder() && this instanceof PlayerEntity) {
            g = 0.0;
         }

         motion = new Vec3d(d, g, e);
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
         int i = this.getStuckArrowCount();
         if (i > 0) {
            if (this.stuckArrowTimer <= 0) {
               this.stuckArrowTimer = 20 * (30 - i);
            }

            this.stuckArrowTimer--;
            if (this.stuckArrowTimer <= 0) {
               this.setStuckArrowCount(i - 1);
            }
         }

         int j = this.getStingerCount();
         if (j > 0) {
            if (this.stuckStingerTimer <= 0) {
               this.stuckStingerTimer = 20 * (30 - j);
            }

            this.stuckStingerTimer--;
            if (this.stuckStingerTimer <= 0) {
               this.setStingerCount(j - 1);
            }
         }

         this.method_30128();
         if (this.age % 20 == 0) {
            this.getDamageTracker().update();
         }

         if (!this.glowing) {
            boolean bl = this.hasStatusEffect(StatusEffects.GLOWING);
            if (this.getFlag(6) != bl) {
               this.setFlag(6, bl);
            }
         }

         if (this.isSleeping() && !this.isSleepingInBed()) {
            this.wakeUp();
         }
      }

      this.tickMovement();
      double d = this.getX() - this.prevX;
      double e = this.getZ() - this.prevZ;
      float f = (float)(d * d + e * e);
      float g = this.bodyYaw;
      float h = 0.0F;
      this.prevStepBobbingAmount = this.stepBobbingAmount;
      float k = 0.0F;
      if (f > 0.0025000002F) {
         k = 1.0F;
         h = (float)Math.sqrt((double)f) * 3.0F;
         float l = (float)MathHelper.atan2(e, d) * (180.0F / (float)Math.PI) - 90.0F;
         float m = MathHelper.abs(MathHelper.wrapDegrees(this.yaw) - l);
         if (95.0F < m && m < 265.0F) {
            g = l - 180.0F;
         } else {
            g = l;
         }
      }

      if (this.handSwingProgress > 0.0F) {
         g = this.yaw;
      }

      if (!this.onGround) {
         k = 0.0F;
      }

      this.stepBobbingAmount = this.stepBobbingAmount + (k - this.stepBobbingAmount) * 0.3F;
      this.world.getProfiler().push("headTurn");
      h = this.turnHead(g, h);
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
      this.lookDirection += h;
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
      Map<EquipmentSlot, ItemStack> map = this.method_30129();
      if (map != null) {
         this.method_30121(map);
         if (!map.isEmpty()) {
            this.method_30123(map);
         }
      }
   }

   @Nullable
   private Map<EquipmentSlot, ItemStack> method_30129() {
      Map<EquipmentSlot, ItemStack> map = null;

      for (EquipmentSlot lv : EquipmentSlot.values()) {
         ItemStack lv2;
         switch (lv.getType()) {
            case HAND:
               lv2 = this.method_30126(lv);
               break;
            case ARMOR:
               lv2 = this.method_30125(lv);
               break;
            default:
               continue;
         }

         ItemStack lv5 = this.getEquippedStack(lv);
         if (!ItemStack.areEqual(lv5, lv2)) {
            if (map == null) {
               map = Maps.newEnumMap(EquipmentSlot.class);
            }

            map.put(lv, lv5);
            if (!lv2.isEmpty()) {
               this.getAttributes().removeModifiers(lv2.getAttributeModifiers(lv));
            }

            if (!lv5.isEmpty()) {
               this.getAttributes().addTemporaryModifiers(lv5.getAttributeModifiers(lv));
            }
         }
      }

      return map;
   }

   private void method_30121(Map<EquipmentSlot, ItemStack> map) {
      ItemStack lv = map.get(EquipmentSlot.MAINHAND);
      ItemStack lv2 = map.get(EquipmentSlot.OFFHAND);
      if (lv != null
         && lv2 != null
         && ItemStack.areEqual(lv, this.method_30126(EquipmentSlot.OFFHAND))
         && ItemStack.areEqual(lv2, this.method_30126(EquipmentSlot.MAINHAND))) {
         ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityStatusS2CPacket(this, (byte)55));
         map.remove(EquipmentSlot.MAINHAND);
         map.remove(EquipmentSlot.OFFHAND);
         this.method_30124(EquipmentSlot.MAINHAND, lv.copy());
         this.method_30124(EquipmentSlot.OFFHAND, lv2.copy());
      }
   }

   private void method_30123(Map<EquipmentSlot, ItemStack> map) {
      List<Pair<EquipmentSlot, ItemStack>> list = Lists.newArrayListWithCapacity(map.size());
      map.forEach((arg, arg2) -> {
         ItemStack lv = arg2.copy();
         list.add(Pair.of(arg, lv));
         switch (arg.getType()) {
            case HAND:
               this.method_30124(arg, lv);
               break;
            case ARMOR:
               this.method_30122(arg, lv);
         }
      });
      ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityEquipmentUpdateS2CPacket(this.getEntityId(), list));
   }

   private ItemStack method_30125(EquipmentSlot arg) {
      return this.equippedArmor.get(arg.getEntitySlotId());
   }

   private void method_30122(EquipmentSlot arg, ItemStack arg2) {
      this.equippedArmor.set(arg.getEntitySlotId(), arg2);
   }

   private ItemStack method_30126(EquipmentSlot arg) {
      return this.equippedHand.get(arg.getEntitySlotId());
   }

   private void method_30124(EquipmentSlot arg, ItemStack arg2) {
      this.equippedHand.set(arg.getEntitySlotId(), arg2);
   }

   protected float turnHead(float bodyRotation, float headRotation) {
      float h = MathHelper.wrapDegrees(bodyRotation - this.bodyYaw);
      this.bodyYaw += h * 0.3F;
      float i = MathHelper.wrapDegrees(this.yaw - this.bodyYaw);
      boolean bl = i < -90.0F || i >= 90.0F;
      if (i < -75.0F) {
         i = -75.0F;
      }

      if (i >= 75.0F) {
         i = 75.0F;
      }

      this.bodyYaw = this.yaw - i;
      if (i * i > 2500.0F) {
         this.bodyYaw += i * 0.2F;
      }

      if (bl) {
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
         double d = this.getX() + (this.serverX - this.getX()) / (double)this.bodyTrackingIncrements;
         double e = this.getY() + (this.serverY - this.getY()) / (double)this.bodyTrackingIncrements;
         double f = this.getZ() + (this.serverZ - this.getZ()) / (double)this.bodyTrackingIncrements;
         double g = MathHelper.wrapDegrees(this.serverYaw - (double)this.yaw);
         this.yaw = (float)((double)this.yaw + g / (double)this.bodyTrackingIncrements);
         this.pitch = (float)((double)this.pitch + (this.serverPitch - (double)this.pitch) / (double)this.bodyTrackingIncrements);
         this.bodyTrackingIncrements--;
         this.updatePosition(d, e, f);
         this.setRotation(this.yaw, this.pitch);
      } else if (!this.canMoveVoluntarily()) {
         this.setVelocity(this.getVelocity().multiply(0.98));
      }

      if (this.headTrackingIncrements > 0) {
         this.headYaw = (float)((double)this.headYaw + MathHelper.wrapDegrees(this.serverHeadYaw - (double)this.headYaw) / (double)this.headTrackingIncrements);
         this.headTrackingIncrements--;
      }

      Vec3d lv = this.getVelocity();
      double h = lv.x;
      double i = lv.y;
      double j = lv.z;
      if (Math.abs(lv.x) < 0.003) {
         h = 0.0;
      }

      if (Math.abs(lv.y) < 0.003) {
         i = 0.0;
      }

      if (Math.abs(lv.z) < 0.003) {
         j = 0.0;
      }

      this.setVelocity(h, i, j);
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
         double k;
         if (this.isInLava()) {
            k = this.getFluidHeight(FluidTags.LAVA);
         } else {
            k = this.getFluidHeight(FluidTags.WATER);
         }

         boolean bl = this.isTouchingWater() && k > 0.0;
         double m = this.method_29241();
         if (!bl || this.onGround && !(k > m)) {
            if (!this.isInLava() || this.onGround && !(k > m)) {
               if ((this.onGround || bl && k <= m) && this.jumpingCooldown == 0) {
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
      Box lv2 = this.getBoundingBox();
      this.travel(new Vec3d((double)this.sidewaysSpeed, (double)this.upwardSpeed, (double)this.forwardSpeed));
      this.world.getProfiler().pop();
      this.world.getProfiler().push("push");
      if (this.riptideTicks > 0) {
         this.riptideTicks--;
         this.tickRiptide(lv2, this.getBoundingBox());
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
      boolean bl = this.getFlag(7);
      if (bl && !this.onGround && !this.hasVehicle() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {
         ItemStack lv = this.getEquippedStack(EquipmentSlot.CHEST);
         if (lv.getItem() == Items.ELYTRA && ElytraItem.isUsable(lv)) {
            bl = true;
            if (!this.world.isClient && (this.roll + 1) % 20 == 0) {
               lv.damage(1, this, arg -> arg.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
            }
         } else {
            bl = false;
         }
      } else {
         bl = false;
      }

      if (!this.world.isClient) {
         this.setFlag(7, bl);
      }
   }

   protected void tickNewAi() {
   }

   protected void tickCramming() {
      List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox(), EntityPredicates.canBePushedBy(this));
      if (!list.isEmpty()) {
         int i = this.world.getGameRules().getInt(GameRules.MAX_ENTITY_CRAMMING);
         if (i > 0 && list.size() > i - 1 && this.random.nextInt(4) == 0) {
            int j = 0;

            for (int k = 0; k < list.size(); k++) {
               if (!list.get(k).hasVehicle()) {
                  j++;
               }
            }

            if (j > i - 1) {
               this.damage(DamageSource.CRAMMING, 6.0F);
            }
         }

         for (int l = 0; l < list.size(); l++) {
            Entity lv = list.get(l);
            this.pushAway(lv);
         }
      }
   }

   protected void tickRiptide(Box a, Box b) {
      Box lv = a.union(b);
      List<Entity> list = this.world.getOtherEntities(this, lv);
      if (!list.isEmpty()) {
         for (int i = 0; i < list.size(); i++) {
            Entity lv2 = list.get(i);
            if (lv2 instanceof LivingEntity) {
               this.attackLivingEntity((LivingEntity)lv2);
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

   public void setRiptideTicks(int i) {
      this.riptideTicks = i;
      if (!this.world.isClient) {
         this.setLivingFlag(4, true);
      }
   }

   public boolean isUsingRiptide() {
      return (this.dataTracker.get(LIVING_FLAGS) & 4) != 0;
   }

   @Override
   public void stopRiding() {
      Entity lv = this.getVehicle();
      super.stopRiding();
      if (lv != null && lv != this.getVehicle() && !this.world.isClient) {
         this.onDismounted(lv);
      }
   }

   @Override
   public void tickRiding() {
      super.tickRiding();
      this.prevStepBobbingAmount = this.stepBobbingAmount;
      this.stepBobbingAmount = 0.0F;
      this.fallDistance = 0.0F;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
      this.serverX = x;
      this.serverY = y;
      this.serverZ = z;
      this.serverYaw = (double)yaw;
      this.serverPitch = (double)pitch;
      this.bodyTrackingIncrements = interpolationSteps;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void updateTrackedHeadRotation(float yaw, int interpolationSteps) {
      this.serverHeadYaw = (double)yaw;
      this.headTrackingIncrements = interpolationSteps;
   }

   public void setJumping(boolean jumping) {
      this.jumping = jumping;
   }

   public void method_29499(ItemEntity arg) {
      PlayerEntity lv = arg.getThrower() != null ? this.world.getPlayerByUuid(arg.getThrower()) : null;
      if (lv instanceof ServerPlayerEntity) {
         Criteria.THROWN_ITEM_PICKED_UP_BY_ENTITY.trigger((ServerPlayerEntity)lv, arg.getStack(), this);
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
      Vec3d lv = new Vec3d(this.getX(), this.getEyeY(), this.getZ());
      Vec3d lv2 = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
      return this.world.raycast(new RaycastContext(lv, lv2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)).getType()
         == HitResult.Type.MISS;
   }

   @Override
   public float getYaw(float tickDelta) {
      return tickDelta == 1.0F ? this.headYaw : MathHelper.lerp(tickDelta, this.prevHeadYaw, this.headYaw);
   }

   @Environment(EnvType.CLIENT)
   public float getHandSwingProgress(float tickDelta) {
      float g = this.handSwingProgress - this.lastHandSwingProgress;
      if (g < 0.0F) {
         g++;
      }

      return this.lastHandSwingProgress + g * tickDelta;
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
   protected Vec3d method_30633(Direction.Axis arg, class_5459.class_5460 arg2) {
      return method_31079(super.method_30633(arg, arg2));
   }

   public static Vec3d method_31079(Vec3d arg) {
      return new Vec3d(arg.x, arg.y, 0.0);
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
      int i = this.getItemUseTimeLeft();
      FoodComponent lv = this.activeItemStack.getItem().getFoodComponent();
      boolean bl = lv != null && lv.isSnack();
      bl |= i <= this.activeItemStack.getMaxUseTime() - 7;
      return bl && i % 4 == 0;
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
      int j = this.dataTracker.get(LIVING_FLAGS);
      if (value) {
         j |= mask;
      } else {
         j &= ~mask;
      }

      this.dataTracker.set(LIVING_FLAGS, (byte)j);
   }

   public void setCurrentHand(Hand hand) {
      ItemStack lv = this.getStackInHand(hand);
      if (!lv.isEmpty() && !this.isUsingItem()) {
         this.activeItemStack = lv;
         this.itemUseTimeLeft = lv.getMaxUseTime();
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
      for (int j = 0; j < count; j++) {
         Vec3d lv = new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         lv = lv.rotateX(-this.pitch * (float) (Math.PI / 180.0));
         lv = lv.rotateY(-this.yaw * (float) (Math.PI / 180.0));
         double d = (double)(-this.random.nextFloat()) * 0.6 - 0.3;
         Vec3d lv2 = new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.3, d, 0.6);
         lv2 = lv2.rotateX(-this.pitch * (float) (Math.PI / 180.0));
         lv2 = lv2.rotateY(-this.yaw * (float) (Math.PI / 180.0));
         lv2 = lv2.add(this.getX(), this.getEyeY(), this.getZ());
         this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), lv2.x, lv2.y, lv2.z, lv.x, lv.y + 0.05, lv.z);
      }
   }

   protected void consumeItem() {
      Hand lv = this.getActiveHand();
      if (!this.activeItemStack.equals(this.getStackInHand(lv))) {
         this.stopUsingItem();
      } else {
         if (!this.activeItemStack.isEmpty() && this.isUsingItem()) {
            this.spawnConsumptionEffects(this.activeItemStack, 16);
            ItemStack lv2 = this.activeItemStack.finishUsing(this.world, this);
            if (lv2 != this.activeItemStack) {
               this.setStackInHand(lv, lv2);
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
         Item lv = this.activeItemStack.getItem();
         return lv.getUseAction(this.activeItemStack) != UseAction.BLOCK ? false : lv.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= 5;
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

   @Environment(EnvType.CLIENT)
   public int getRoll() {
      return this.roll;
   }

   public boolean teleport(double x, double y, double z, boolean particleEffects) {
      double g = this.getX();
      double h = this.getY();
      double i = this.getZ();
      double j = y;
      boolean bl2 = false;
      BlockPos lv = new BlockPos(x, y, z);
      World lv2 = this.world;
      if (lv2.isChunkLoaded(lv)) {
         boolean bl3 = false;

         while (!bl3 && lv.getY() > 0) {
            BlockPos lv3 = lv.down();
            BlockState lv4 = lv2.getBlockState(lv3);
            if (lv4.getMaterial().blocksMovement()) {
               bl3 = true;
            } else {
               j--;
               lv = lv3;
            }
         }

         if (bl3) {
            this.requestTeleport(x, j, z);
            if (lv2.isSpaceEmpty(this) && !lv2.containsFluid(this.getBoundingBox())) {
               bl2 = true;
            }
         }
      }

      if (!bl2) {
         this.requestTeleport(g, h, i);
         return false;
      } else {
         if (particleEffects) {
            lv2.sendEntityStatus(this, (byte)46);
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

   @Environment(EnvType.CLIENT)
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
      EntityDimensions lv = this.getDimensions(pose);
      return new Box((double)(-lv.width / 2.0F), 0.0, (double)(-lv.width / 2.0F), (double)(lv.width / 2.0F), (double)lv.height, (double)(lv.width / 2.0F));
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

      BlockState lv = this.world.getBlockState(pos);
      if (lv.getBlock() instanceof BedBlock) {
         this.world.setBlockState(pos, lv.with(BedBlock.OCCUPIED, Boolean.valueOf(true)), 3);
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
      return this.getSleepingPosition().map(arg -> this.world.getBlockState(arg).getBlock() instanceof BedBlock).orElse(false);
   }

   public void wakeUp() {
      this.getSleepingPosition().filter(this.world::isChunkLoaded).ifPresent(arg -> {
         BlockState lvx = this.world.getBlockState(arg);
         if (lvx.getBlock() instanceof BedBlock) {
            this.world.setBlockState(arg, lvx.with(BedBlock.OCCUPIED, Boolean.valueOf(false)), 3);
            Vec3d lv2 = BedBlock.findWakeUpPosition(this.getType(), this.world, arg, this.yaw).orElseGet(() -> {
               BlockPos lvxx = arg.up();
               return new Vec3d((double)lvxx.getX() + 0.5, (double)lvxx.getY() + 0.1, (double)lvxx.getZ() + 0.5);
            });
            Vec3d lv3 = Vec3d.ofBottomCenter(arg).subtract(lv2).normalize();
            float f = (float)MathHelper.wrapDegrees(MathHelper.atan2(lv3.z, lv3.x) * 180.0F / (float)Math.PI - 90.0);
            this.updatePosition(lv2.x, lv2.y, lv2.z);
            this.yaw = f;
            this.pitch = 0.0F;
         }
      });
      Vec3d lv = this.getPos();
      this.setPose(EntityPose.STANDING);
      this.updatePosition(lv.x, lv.y, lv.z);
      this.clearSleepingPosition();
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public Direction getSleepingDirection() {
      BlockPos lv = this.getSleepingPosition().orElse(null);
      return lv != null ? BedBlock.getDirection(this.world, lv) : null;
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
      Item lv = stack.getItem();
      if (lv.isFood()) {
         for (Pair<StatusEffectInstance, Float> pair : lv.getFoodComponent().getStatusEffects()) {
            if (!world.isClient && pair.getFirst() != null && world.random.nextFloat() < (Float)pair.getSecond()) {
               targetEntity.addStatusEffect(new StatusEffectInstance((StatusEffectInstance)pair.getFirst()));
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

   @Environment(EnvType.CLIENT)
   @Override
   public Box getVisibilityBoundingBox() {
      if (this.getEquippedStack(EquipmentSlot.HEAD).getItem() == Items.DRAGON_HEAD) {
         float f = 0.5F;
         return this.getBoundingBox().expand(0.5, 0.5, 0.5);
      } else {
         return super.getVisibilityBoundingBox();
      }
   }
}
