/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HoneyBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.class_5459;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
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
import net.minecraft.entity.passive.TameableEntity;
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
import org.apache.logging.log4j.Logger;

public abstract class LivingEntity
extends Entity {
    private static final UUID SPRINTING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
    private static final UUID SOUL_SPEED_BOOST_ID = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
    private static final EntityAttributeModifier SPRINTING_SPEED_BOOST = new EntityAttributeModifier(SPRINTING_SPEED_BOOST_ID, "Sprinting speed boost", (double)0.3f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    protected static final TrackedData<Byte> LIVING_FLAGS = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Float> HEALTH = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> POTION_SWIRLS_COLOR = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> POTION_SWIRLS_AMBIENT = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> STUCK_ARROW_COUNT = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> STINGER_COUNT = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<BlockPos>> SLEEPING_POSITION = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
    protected static final EntityDimensions SLEEPING_DIMENSIONS = EntityDimensions.fixed(0.2f, 0.2f);
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
    public float flyingSpeed = 0.02f;
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

    protected LivingEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.attributes = new AttributeContainer(DefaultAttributeRegistry.get(entityType));
        this.setHealth(this.getMaxHealth());
        this.inanimate = true;
        this.randomSmallSeed = (float)((Math.random() + 1.0) * (double)0.01f);
        this.refreshPosition();
        this.randomLargeSeed = (float)Math.random() * 12398.0f;
        this.headYaw = this.yaw = (float)(Math.random() * 6.2831854820251465);
        this.stepHeight = 0.6f;
        NbtOps nbtOps = NbtOps.INSTANCE;
        this.brain = this.deserializeBrain(new Dynamic((DynamicOps)nbtOps, nbtOps.createMap((Map)ImmutableMap.of((Object)nbtOps.createString("memories"), (Object)nbtOps.emptyMap()))));
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
        this.dataTracker.startTracking(HEALTH, Float.valueOf(1.0f));
        this.dataTracker.startTracking(SLEEPING_POSITION, Optional.empty());
    }

    public static DefaultAttributeContainer.Builder createLivingAttributes() {
        return DefaultAttributeContainer.builder().add(EntityAttributes.GENERIC_MAX_HEALTH).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).add(EntityAttributes.GENERIC_MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ARMOR).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        if (!this.isTouchingWater()) {
            this.checkWaterState();
        }
        if (!this.world.isClient && onGround && this.fallDistance > 0.0f) {
            this.removeSoulSpeedBoost();
            this.addSoulSpeedBoostIfNeeded();
        }
        if (!this.world.isClient && this.fallDistance > 3.0f && onGround) {
            float f = MathHelper.ceil(this.fallDistance - 3.0f);
            if (!landedState.isAir()) {
                double d = Math.min((double)(0.2f + f / 15.0f), 2.5);
                int _snowman2 = (int)(150.0 * d);
                ((ServerWorld)this.world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, landedState), this.getX(), this.getY(), this.getZ(), _snowman2, 0.0, 0.0, 0.0, 0.15f);
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
        boolean bl = this instanceof PlayerEntity;
        if (this.isAlive()) {
            if (this.isInsideWall()) {
                this.damage(DamageSource.IN_WALL, 1.0f);
            } else if (bl && !this.world.getWorldBorder().contains(this.getBoundingBox()) && (_snowman = this.world.getWorldBorder().getDistanceInsideBorder(this) + this.world.getWorldBorder().getBuffer()) < 0.0 && (_snowman = this.world.getWorldBorder().getDamagePerBlock()) > 0.0) {
                this.damage(DamageSource.IN_WALL, Math.max(1, MathHelper.floor(-_snowman * _snowman)));
            }
        }
        if (this.isFireImmune() || this.world.isClient) {
            this.extinguish();
        }
        boolean bl2 = _snowman = bl && ((PlayerEntity)this).abilities.invulnerable;
        if (this.isAlive()) {
            Object object;
            if (this.isSubmergedIn(FluidTags.WATER) && !this.world.getBlockState(new BlockPos(this.getX(), this.getEyeY(), this.getZ())).isOf(Blocks.BUBBLE_COLUMN)) {
                if (!(this.canBreatheInWater() || StatusEffectUtil.hasWaterBreathing(this) || _snowman)) {
                    this.setAir(this.getNextAirUnderwater(this.getAir()));
                    if (this.getAir() == -20) {
                        this.setAir(0);
                        object = this.getVelocity();
                        for (int i = 0; i < 8; ++i) {
                            double d = this.random.nextDouble() - this.random.nextDouble();
                            _snowman = this.random.nextDouble() - this.random.nextDouble();
                            _snowman = this.random.nextDouble() - this.random.nextDouble();
                            this.world.addParticle(ParticleTypes.BUBBLE, this.getX() + d, this.getY() + _snowman, this.getZ() + _snowman, ((Vec3d)object).x, ((Vec3d)object).y, ((Vec3d)object).z);
                        }
                        this.damage(DamageSource.DROWN, 2.0f);
                    }
                }
                if (!this.world.isClient && this.hasVehicle() && this.getVehicle() != null && !this.getVehicle().canBeRiddenInWater()) {
                    this.stopRiding();
                }
            } else if (this.getAir() < this.getMaxAir()) {
                this.setAir(this.getNextAirOnLand(this.getAir()));
            }
            if (!this.world.isClient && !Objects.equal((Object)this.lastBlockPos, (Object)(object = this.getBlockPos()))) {
                this.lastBlockPos = object;
                this.applyMovementEffects((BlockPos)object);
            }
        }
        if (this.isAlive() && this.isWet()) {
            this.extinguish();
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.timeUntilRegen > 0 && !(this instanceof ServerPlayerEntity)) {
            --this.timeUntilRegen;
        }
        if (this.isDead()) {
            this.updatePostDeath();
        }
        if (this.playerHitTimer > 0) {
            --this.playerHitTimer;
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
        return this.age % 5 == 0 && this.getVelocity().x != 0.0 && this.getVelocity().z != 0.0 && !this.isSpectator() && EnchantmentHelper.hasSoulSpeed(this) && this.isOnSoulSpeedBlock();
    }

    protected void displaySoulSpeedEffects() {
        Vec3d vec3d = this.getVelocity();
        this.world.addParticle(ParticleTypes.SOUL, this.getX() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(), this.getY() + 0.1, this.getZ() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(), vec3d.x * -0.2, 0.1, vec3d.z * -0.2);
        float _snowman2 = this.random.nextFloat() * 0.4f + this.random.nextFloat() > 0.9f ? 0.6f : 0.0f;
        this.playSound(SoundEvents.PARTICLE_SOUL_ESCAPE, _snowman2, 0.6f + this.random.nextFloat() * 0.4f);
    }

    protected boolean isOnSoulSpeedBlock() {
        return this.world.getBlockState(this.getVelocityAffectingPos()).isIn(BlockTags.SOUL_SPEED_BLOCKS);
    }

    @Override
    protected float getVelocityMultiplier() {
        if (this.isOnSoulSpeedBlock() && EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, this) > 0) {
            return 1.0f;
        }
        return super.getVelocityMultiplier();
    }

    protected boolean method_29500(BlockState blockState) {
        return !blockState.isAir() || this.isFallFlying();
    }

    protected void removeSoulSpeedBoost() {
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance == null) {
            return;
        }
        if (entityAttributeInstance.getModifier(SOUL_SPEED_BOOST_ID) != null) {
            entityAttributeInstance.removeModifier(SOUL_SPEED_BOOST_ID);
        }
    }

    protected void addSoulSpeedBoostIfNeeded() {
        int n;
        if (!this.getLandingBlockState().isAir() && (n = EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, this)) > 0 && this.isOnSoulSpeedBlock()) {
            EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (entityAttributeInstance == null) {
                return;
            }
            entityAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(SOUL_SPEED_BOOST_ID, "Soul speed boost", (double)(0.03f * (1.0f + (float)n * 0.35f)), EntityAttributeModifier.Operation.ADDITION));
            if (this.getRandom().nextFloat() < 0.04f) {
                ItemStack itemStack = this.getEquippedStack(EquipmentSlot.FEET);
                itemStack.damage(1, this, livingEntity -> livingEntity.sendEquipmentBreakStatus(EquipmentSlot.FEET));
            }
        }
    }

    protected void applyMovementEffects(BlockPos pos) {
        int n = EnchantmentHelper.getEquipmentLevel(Enchantments.FROST_WALKER, this);
        if (n > 0) {
            FrostWalkerEnchantment.freezeWater(this, this.world, pos, n);
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
        return this.isBaby() ? 0.5f : 1.0f;
    }

    protected boolean method_29920() {
        return true;
    }

    @Override
    public boolean canBeRiddenInWater() {
        return false;
    }

    protected void updatePostDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove();
            for (int i = 0; i < 20; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                _snowman = this.random.nextGaussian() * 0.02;
                _snowman = this.random.nextGaussian() * 0.02;
                this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), d, _snowman, _snowman);
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
        int n = EnchantmentHelper.getRespiration(this);
        if (n > 0 && this.random.nextInt(n + 1) > 0) {
            return air;
        }
        return air - 1;
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
        this.attacking = target instanceof LivingEntity ? (LivingEntity)target : null;
        this.lastAttackTime = this.age;
    }

    public int getDespawnCounter() {
        return this.despawnCounter;
    }

    public void setDespawnCounter(int despawnCounter) {
        this.despawnCounter = despawnCounter;
    }

    protected void onEquipStack(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        SoundEvent soundEvent = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        Item _snowman2 = stack.getItem();
        if (_snowman2 instanceof ArmorItem) {
            soundEvent = ((ArmorItem)_snowman2).getMaterial().getEquipSound();
        } else if (_snowman2 == Items.ELYTRA) {
            soundEvent = SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
        }
        this.playSound(soundEvent, 1.0f, 1.0f);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag2) {
        DataResult<Tag> dataResult;
        tag2.putFloat("Health", this.getHealth());
        tag2.putShort("HurtTime", (short)this.hurtTime);
        tag2.putInt("HurtByTimestamp", this.lastAttackedTime);
        tag2.putShort("DeathTime", (short)this.deathTime);
        tag2.putFloat("AbsorptionAmount", this.getAbsorptionAmount());
        tag2.put("Attributes", this.getAttributes().toTag());
        if (!this.activeStatusEffects.isEmpty()) {
            dataResult = new DataResult<Tag>();
            for (StatusEffectInstance statusEffectInstance : this.activeStatusEffects.values()) {
                dataResult.add(statusEffectInstance.toTag(new CompoundTag()));
            }
            tag2.put("ActiveEffects", (Tag)dataResult);
        }
        tag2.putBoolean("FallFlying", this.isFallFlying());
        this.getSleepingPosition().ifPresent(blockPos -> {
            tag2.putInt("SleepingX", blockPos.getX());
            tag2.putInt("SleepingY", blockPos.getY());
            tag2.putInt("SleepingZ", blockPos.getZ());
        });
        dataResult = this.brain.encode(NbtOps.INSTANCE);
        dataResult.resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).ifPresent(tag -> tag2.put("Brain", (Tag)tag));
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        Object object;
        this.setAbsorptionAmount(tag.getFloat("AbsorptionAmount"));
        if (tag.contains("Attributes", 9) && this.world != null && !this.world.isClient) {
            this.getAttributes().fromTag(tag.getList("Attributes", 10));
        }
        if (tag.contains("ActiveEffects", 9)) {
            object = tag.getList("ActiveEffects", 10);
            for (int i = 0; i < ((ListTag)object).size(); ++i) {
                CompoundTag compoundTag = ((ListTag)object).getCompound(i);
                StatusEffectInstance _snowman2 = StatusEffectInstance.fromTag(compoundTag);
                if (_snowman2 == null) continue;
                this.activeStatusEffects.put(_snowman2.getEffectType(), _snowman2);
            }
        }
        if (tag.contains("Health", 99)) {
            this.setHealth(tag.getFloat("Health"));
        }
        this.hurtTime = tag.getShort("HurtTime");
        this.deathTime = tag.getShort("DeathTime");
        this.lastAttackedTime = tag.getInt("HurtByTimestamp");
        if (tag.contains("Team", 8)) {
            object = tag.getString("Team");
            Team _snowman3 = this.world.getScoreboard().getTeam((String)object);
            boolean bl = _snowman = _snowman3 != null && this.world.getScoreboard().addPlayerToTeam(this.getUuidAsString(), _snowman3);
            if (!_snowman) {
                LOGGER.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", object);
            }
        }
        if (tag.getBoolean("FallFlying")) {
            this.setFlag(7, true);
        }
        if (tag.contains("SleepingX", 99) && tag.contains("SleepingY", 99) && tag.contains("SleepingZ", 99)) {
            object = new BlockPos(tag.getInt("SleepingX"), tag.getInt("SleepingY"), tag.getInt("SleepingZ"));
            this.setSleepingPosition((BlockPos)object);
            this.dataTracker.set(POSE, EntityPose.SLEEPING);
            if (!this.firstUpdate) {
                this.setPositionInBed((BlockPos)object);
            }
        }
        if (tag.contains("Brain", 10)) {
            this.brain = this.deserializeBrain(new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)tag.get("Brain")));
        }
    }

    protected void tickStatusEffects() {
        Iterator<StatusEffect> iterator = this.activeStatusEffects.keySet().iterator();
        try {
            while (iterator.hasNext()) {
                StatusEffect statusEffect = iterator.next();
                StatusEffectInstance _snowman2 = this.activeStatusEffects.get(statusEffect);
                if (!_snowman2.update(this, () -> this.onStatusEffectUpgraded(_snowman2, true))) {
                    if (this.world.isClient) continue;
                    iterator.remove();
                    this.onStatusEffectRemoved(_snowman2);
                    continue;
                }
                if (_snowman2.getDuration() % 600 != 0) continue;
                this.onStatusEffectUpgraded(_snowman2, false);
            }
        }
        catch (ConcurrentModificationException statusEffect) {
            // empty catch block
        }
        if (this.effectsChanged) {
            if (!this.world.isClient) {
                this.updatePotionVisibility();
            }
            this.effectsChanged = false;
        }
        int n = this.dataTracker.get(POTION_SWIRLS_COLOR);
        boolean _snowman3 = this.dataTracker.get(POTION_SWIRLS_AMBIENT);
        if (n > 0) {
            boolean bl = this.isInvisible() ? this.random.nextInt(15) == 0 : this.random.nextBoolean();
            if (_snowman3) {
                bl &= this.random.nextInt(5) == 0;
            }
            if (bl && n > 0) {
                double d = (double)(n >> 16 & 0xFF) / 255.0;
                _snowman = (double)(n >> 8 & 0xFF) / 255.0;
                _snowman = (double)(n >> 0 & 0xFF) / 255.0;
                this.world.addParticle(_snowman3 ? ParticleTypes.AMBIENT_ENTITY_EFFECT : ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, _snowman, _snowman);
            }
        }
    }

    protected void updatePotionVisibility() {
        if (this.activeStatusEffects.isEmpty()) {
            this.clearPotionSwirls();
            this.setInvisible(false);
        } else {
            Collection<StatusEffectInstance> collection = this.activeStatusEffects.values();
            this.dataTracker.set(POTION_SWIRLS_AMBIENT, LivingEntity.containsOnlyAmbientEffects(collection));
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
            if (f < 0.1f) {
                f = 0.1f;
            }
            d *= 0.7 * (double)f;
        }
        if (entity != null) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
            Item _snowman2 = itemStack.getItem();
            EntityType<?> _snowman3 = entity.getType();
            if (_snowman3 == EntityType.SKELETON && _snowman2 == Items.SKELETON_SKULL || _snowman3 == EntityType.ZOMBIE && _snowman2 == Items.ZOMBIE_HEAD || _snowman3 == EntityType.CREEPER && _snowman2 == Items.CREEPER_HEAD) {
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
        for (StatusEffectInstance statusEffectInstance : effects) {
            if (statusEffectInstance.isAmbient()) continue;
            return false;
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
        }
        Iterator<StatusEffectInstance> iterator = this.activeStatusEffects.values().iterator();
        boolean _snowman2 = false;
        while (iterator.hasNext()) {
            this.onStatusEffectRemoved(iterator.next());
            iterator.remove();
            _snowman2 = true;
        }
        return _snowman2;
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
        }
        StatusEffectInstance statusEffectInstance = this.activeStatusEffects.get(effect.getEffectType());
        if (statusEffectInstance == null) {
            this.activeStatusEffects.put(effect.getEffectType(), effect);
            this.onStatusEffectApplied(effect);
            return true;
        }
        if (statusEffectInstance.upgrade(effect)) {
            this.onStatusEffectUpgraded(statusEffectInstance, true);
            return true;
        }
        return false;
    }

    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        StatusEffect statusEffect;
        return this.getGroup() != EntityGroup.UNDEAD || (statusEffect = effect.getEffectType()) != StatusEffects.REGENERATION && statusEffect != StatusEffects.POISON;
    }

    public void applyStatusEffect(StatusEffectInstance effect) {
        if (!this.canHaveStatusEffect(effect)) {
            return;
        }
        StatusEffectInstance statusEffectInstance = this.activeStatusEffects.put(effect.getEffectType(), effect);
        if (statusEffectInstance == null) {
            this.onStatusEffectApplied(effect);
        } else {
            this.onStatusEffectUpgraded(effect, true);
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
        StatusEffectInstance statusEffectInstance = this.removeStatusEffectInternal(type);
        if (statusEffectInstance != null) {
            this.onStatusEffectRemoved(statusEffectInstance);
            return true;
        }
        return false;
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
            StatusEffect statusEffect = effect.getEffectType();
            statusEffect.onRemoved(this, this.getAttributes(), effect.getAmplifier());
            statusEffect.onApplied(this, this.getAttributes(), effect.getAmplifier());
        }
    }

    protected void onStatusEffectRemoved(StatusEffectInstance effect) {
        this.effectsChanged = true;
        if (!this.world.isClient) {
            effect.getEffectType().onRemoved(this, this.getAttributes(), effect.getAmplifier());
        }
    }

    public void heal(float amount) {
        float f = this.getHealth();
        if (f > 0.0f) {
            this.setHealth(f + amount);
        }
    }

    public float getHealth() {
        return this.dataTracker.get(HEALTH).floatValue();
    }

    public void setHealth(float health) {
        this.dataTracker.set(HEALTH, Float.valueOf(MathHelper.clamp(health, 0.0f, this.getMaxHealth())));
    }

    public boolean isDead() {
        return this.getHealth() <= 0.0f;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.world.isClient) {
            return false;
        }
        if (this.isDead()) {
            return false;
        }
        if (source.isFire() && this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            return false;
        }
        if (this.isSleeping() && !this.world.isClient) {
            this.wakeUp();
        }
        this.despawnCounter = 0;
        float f = amount;
        if (!(source != DamageSource.ANVIL && source != DamageSource.FALLING_BLOCK || this.getEquippedStack(EquipmentSlot.HEAD).isEmpty())) {
            this.getEquippedStack(EquipmentSlot.HEAD).damage((int)(amount * 4.0f + this.random.nextFloat() * amount * 2.0f), this, livingEntity -> livingEntity.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
            amount *= 0.75f;
        }
        boolean _snowman2 = false;
        _snowman = 0.0f;
        if (amount > 0.0f && this.blockedByShield(source)) {
            this.damageShield(amount);
            _snowman = amount;
            amount = 0.0f;
            if (!source.isProjectile() && (_snowman = source.getSource()) instanceof LivingEntity) {
                this.takeShieldHit((LivingEntity)_snowman);
            }
            _snowman2 = true;
        }
        this.limbDistance = 1.5f;
        boolean _snowman3 = true;
        if ((float)this.timeUntilRegen > 10.0f) {
            if (amount <= this.lastDamageTaken) {
                return false;
            }
            this.applyDamage(source, amount - this.lastDamageTaken);
            this.lastDamageTaken = amount;
            _snowman3 = false;
        } else {
            this.lastDamageTaken = amount;
            this.timeUntilRegen = 20;
            this.applyDamage(source, amount);
            this.hurtTime = this.maxHurtTime = 10;
        }
        this.knockbackVelocity = 0.0f;
        Entity _snowman4 = source.getAttacker();
        if (_snowman4 != null) {
            if (_snowman4 instanceof LivingEntity) {
                this.setAttacker((LivingEntity)_snowman4);
            }
            if (_snowman4 instanceof PlayerEntity) {
                this.playerHitTimer = 100;
                this.attackingPlayer = (PlayerEntity)_snowman4;
            } else if (_snowman4 instanceof WolfEntity && ((TameableEntity)(object = (WolfEntity)_snowman4)).isTamed()) {
                this.playerHitTimer = 100;
                LivingEntity livingEntity2 = ((TameableEntity)object).getOwner();
                this.attackingPlayer = livingEntity2 != null && livingEntity2.getType() == EntityType.PLAYER ? (PlayerEntity)livingEntity2 : null;
            }
        }
        if (_snowman3) {
            if (_snowman2) {
                this.world.sendEntityStatus(this, (byte)29);
            } else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
                this.world.sendEntityStatus(this, (byte)33);
            } else {
                int n = source == DamageSource.DROWN ? 36 : (source.isFire() ? 37 : (source == DamageSource.SWEET_BERRY_BUSH ? 44 : 2));
                this.world.sendEntityStatus(this, (byte)n);
            }
            if (source != DamageSource.DROWN && (!_snowman2 || amount > 0.0f)) {
                this.scheduleVelocityUpdate();
            }
            if (_snowman4 != null) {
                double d = _snowman4.getX() - this.getX();
                _snowman = _snowman4.getZ() - this.getZ();
                while (d * d + _snowman * _snowman < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    _snowman = (Math.random() - Math.random()) * 0.01;
                }
                this.knockbackVelocity = (float)(MathHelper.atan2(_snowman, d) * 57.2957763671875 - (double)this.yaw);
                this.takeKnockback(0.4f, d, _snowman);
            } else {
                this.knockbackVelocity = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.isDead()) {
            if (!this.tryUseTotem(source)) {
                Object object = this.getDeathSound();
                if (_snowman3 && object != null) {
                    this.playSound((SoundEvent)object, this.getSoundVolume(), this.getSoundPitch());
                }
                this.onDeath(source);
            }
        } else if (_snowman3) {
            this.playHurtSound(source);
        }
        boolean bl2 = bl = !_snowman2 || amount > 0.0f;
        if (bl) {
            this.lastDamageSource = source;
            this.lastDamageTime = this.world.getTime();
        }
        if (this instanceof ServerPlayerEntity) {
            Criteria.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity)this, source, f, amount, _snowman2);
            if (_snowman > 0.0f && _snowman < 3.4028235E37f) {
                ((ServerPlayerEntity)this).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(_snowman * 10.0f));
            }
        }
        if (_snowman4 instanceof ServerPlayerEntity) {
            Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)_snowman4, this, source, f, amount, _snowman2);
        }
        return bl;
    }

    protected void takeShieldHit(LivingEntity attacker) {
        attacker.knockback(this);
    }

    protected void knockback(LivingEntity target) {
        target.takeKnockback(0.5f, target.getX() - this.getX(), target.getZ() - this.getZ());
    }

    private boolean tryUseTotem(DamageSource source) {
        if (source.isOutOfWorld()) {
            return false;
        }
        ItemStack itemStack = null;
        for (Hand hand : Hand.values()) {
            ItemStack itemStack2 = this.getStackInHand(hand);
            if (itemStack2.getItem() != Items.TOTEM_OF_UNDYING) continue;
            itemStack = itemStack2.copy();
            itemStack2.decrement(1);
            break;
        }
        if (itemStack != null) {
            if (this instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)this;
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
                Criteria.USED_TOTEM.trigger(serverPlayerEntity, itemStack);
            }
            this.setHealth(1.0f);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            this.world.sendEntityStatus(this, (byte)35);
        }
        return itemStack != null;
    }

    @Nullable
    public DamageSource getRecentDamageSource() {
        if (this.world.getTime() - this.lastDamageTime > 40L) {
            this.lastDamageSource = null;
        }
        return this.lastDamageSource;
    }

    protected void playHurtSound(DamageSource source) {
        SoundEvent soundEvent = this.getHurtSound(source);
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    private boolean blockedByShield(DamageSource source) {
        Entity entity = source.getSource();
        boolean _snowman2 = false;
        if (entity instanceof PersistentProjectileEntity && ((PersistentProjectileEntity)(_snowman = (PersistentProjectileEntity)entity)).getPierceLevel() > 0) {
            _snowman2 = true;
        }
        if (!source.bypassesArmor() && this.isBlocking() && !_snowman2 && (_snowman = source.getPosition()) != null) {
            Vec3d vec3d = this.getRotationVec(1.0f);
            _snowman = ((Vec3d)_snowman).reverseSubtract(this.getPos()).normalize();
            _snowman = new Vec3d(_snowman.x, 0.0, _snowman.z);
            if (_snowman.dotProduct(vec3d) < 0.0) {
                return true;
            }
        }
        return false;
    }

    private void playEquipmentBreakEffects(ItemStack stack) {
        if (!stack.isEmpty()) {
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ITEM_BREAK, this.getSoundCategory(), 0.8f, 0.8f + this.world.random.nextFloat() * 0.4f, false);
            }
            this.spawnItemParticles(stack, 5);
        }
    }

    public void onDeath(DamageSource source) {
        if (this.removed || this.dead) {
            return;
        }
        Entity entity = source.getAttacker();
        LivingEntity _snowman2 = this.getPrimeAdversary();
        if (this.scoreAmount >= 0 && _snowman2 != null) {
            _snowman2.updateKilledAdvancementCriterion(this, this.scoreAmount, source);
        }
        if (this.isSleeping()) {
            this.wakeUp();
        }
        this.dead = true;
        this.getDamageTracker().update();
        if (this.world instanceof ServerWorld) {
            if (entity != null) {
                entity.onKilledOther((ServerWorld)this.world, this);
            }
            this.drop(source);
            this.onKilledBy(_snowman2);
        }
        this.world.sendEntityStatus(this, (byte)3);
        this.setPose(EntityPose.DYING);
    }

    protected void onKilledBy(@Nullable LivingEntity adversary) {
        if (this.world.isClient) {
            return;
        }
        boolean bl = false;
        if (adversary instanceof WitherEntity) {
            Object object;
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                object = this.getBlockPos();
                BlockState _snowman2 = Blocks.WITHER_ROSE.getDefaultState();
                if (this.world.getBlockState((BlockPos)object).isAir() && _snowman2.canPlaceAt(this.world, (BlockPos)object)) {
                    this.world.setBlockState((BlockPos)object, _snowman2, 3);
                    bl = true;
                }
            }
            if (!bl) {
                object = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.WITHER_ROSE));
                this.world.spawnEntity((Entity)object);
            }
        }
    }

    protected void drop(DamageSource source) {
        Entity entity = source.getAttacker();
        int _snowman2 = entity instanceof PlayerEntity ? EnchantmentHelper.getLooting((LivingEntity)entity) : 0;
        boolean bl = _snowman = this.playerHitTimer > 0;
        if (this.shouldDropLoot() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropLoot(source, _snowman);
            this.dropEquipment(source, _snowman2, _snowman);
        }
        this.dropInventory();
        this.dropXp();
    }

    protected void dropInventory() {
    }

    protected void dropXp() {
        if (!this.world.isClient && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.canDropLootAndXp() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
            int n;
            for (_snowman = this.getCurrentExperience(this.attackingPlayer); _snowman > 0; _snowman -= n) {
                n = ExperienceOrbEntity.roundToOrbSize(_snowman);
                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY(), this.getZ(), n));
            }
        }
    }

    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
    }

    public Identifier getLootTable() {
        return this.getType().getLootTableId();
    }

    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        Identifier identifier = this.getLootTable();
        LootTable _snowman2 = this.world.getServer().getLootManager().getTable(identifier);
        LootContext.Builder _snowman3 = this.getLootContextBuilder(causedByPlayer, source);
        _snowman2.generateLoot(_snowman3.build(LootContextTypes.ENTITY), this::dropStack);
    }

    protected LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source) {
        LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).random(this.random).parameter(LootContextParameters.THIS_ENTITY, this).parameter(LootContextParameters.ORIGIN, this.getPos()).parameter(LootContextParameters.DAMAGE_SOURCE, source).optionalParameter(LootContextParameters.KILLER_ENTITY, source.getAttacker()).optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, source.getSource());
        if (causedByPlayer && this.attackingPlayer != null) {
            builder = builder.parameter(LootContextParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).luck(this.attackingPlayer.getLuck());
        }
        return builder;
    }

    public void takeKnockback(float f, double d, double d2) {
        if ((f = (float)((double)f * (1.0 - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)))) <= 0.0f) {
            return;
        }
        this.velocityDirty = true;
        Vec3d vec3d = this.getVelocity();
        _snowman = new Vec3d(d, 0.0, d2).normalize().multiply(f);
        this.setVelocity(vec3d.x / 2.0 - _snowman.x, this.onGround ? Math.min(0.4, vec3d.y / 2.0 + (double)f) : vec3d.y, vec3d.z / 2.0 - _snowman.z);
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
        if (distance > 4) {
            return SoundEvents.ENTITY_GENERIC_BIG_FALL;
        }
        return SoundEvents.ENTITY_GENERIC_SMALL_FALL;
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
        }
        BlockPos blockPos = this.getBlockPos();
        BlockState _snowman2 = this.getBlockState();
        Block _snowman3 = _snowman2.getBlock();
        if (_snowman3.isIn(BlockTags.CLIMBABLE)) {
            this.climbingPos = Optional.of(blockPos);
            return true;
        }
        if (_snowman3 instanceof TrapdoorBlock && this.canEnterTrapdoor(blockPos, _snowman2)) {
            this.climbingPos = Optional.of(blockPos);
            return true;
        }
        return false;
    }

    public BlockState getBlockState() {
        return this.world.getBlockState(this.getBlockPos());
    }

    private boolean canEnterTrapdoor(BlockPos pos, BlockState state) {
        BlockState blockState;
        return state.get(TrapdoorBlock.OPEN) != false && (blockState = this.world.getBlockState(pos.down())).isOf(Blocks.LADDER) && blockState.get(LadderBlock.FACING) == state.get(TrapdoorBlock.FACING);
    }

    @Override
    public boolean isAlive() {
        return !this.removed && this.getHealth() > 0.0f;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        boolean bl = super.handleFallDamage(fallDistance, damageMultiplier);
        int _snowman2 = this.computeFallDamage(fallDistance, damageMultiplier);
        if (_snowman2 > 0) {
            this.playSound(this.getFallSound(_snowman2), 1.0f, 1.0f);
            this.playBlockFallSound();
            this.damage(DamageSource.FALL, _snowman2);
            return true;
        }
        return bl;
    }

    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffects.JUMP_BOOST);
        float _snowman2 = statusEffectInstance == null ? 0.0f : (float)(statusEffectInstance.getAmplifier() + 1);
        return MathHelper.ceil((fallDistance - 3.0f - _snowman2) * damageMultiplier);
    }

    protected void playBlockFallSound() {
        if (this.isSilent()) {
            return;
        }
        int n = MathHelper.floor(this.getX());
        BlockState _snowman2 = this.world.getBlockState(new BlockPos(n, _snowman = MathHelper.floor(this.getY() - (double)0.2f), _snowman = MathHelper.floor(this.getZ())));
        if (!_snowman2.isAir()) {
            BlockSoundGroup blockSoundGroup = _snowman2.getSoundGroup();
            this.playSound(blockSoundGroup.getFallSound(), blockSoundGroup.getVolume() * 0.5f, blockSoundGroup.getPitch() * 0.75f);
        }
    }

    @Override
    public void animateDamage() {
        this.hurtTime = this.maxHurtTime = 10;
        this.knockbackVelocity = 0.0f;
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
            amount = DamageUtil.getDamageLeft(amount, this.getArmor(), (float)this.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
        }
        return amount;
    }

    protected float applyEnchantmentsToDamage(DamageSource source, float amount) {
        float f;
        if (source.isUnblockable()) {
            return amount;
        }
        if (this.hasStatusEffect(StatusEffects.RESISTANCE) && source != DamageSource.OUT_OF_WORLD && (f = (_snowman = amount) - (amount = Math.max((_snowman = amount * (float)(_snowman = 25 - (_snowman2 = (this.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 5))) / 25.0f, 0.0f))) > 0.0f && f < 3.4028235E37f) {
            if (this instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)this).increaseStat(Stats.DAMAGE_RESISTED, Math.round(f * 10.0f));
            } else if (source.getAttacker() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(f * 10.0f));
            }
        }
        if (amount <= 0.0f) {
            return 0.0f;
        }
        int _snowman2 = EnchantmentHelper.getProtectionAmount(this.getArmorItems(), source);
        if (_snowman2 > 0) {
            amount = DamageUtil.getInflictedDamage(amount, _snowman2);
        }
        return amount;
    }

    protected void applyDamage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return;
        }
        amount = this.applyArmorToDamage(source, amount);
        float f = amount = this.applyEnchantmentsToDamage(source, amount);
        amount = Math.max(amount - this.getAbsorptionAmount(), 0.0f);
        this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - amount));
        _snowman = f - amount;
        if (_snowman > 0.0f && _snowman < 3.4028235E37f && source.getAttacker() instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(_snowman * 10.0f));
        }
        if (amount == 0.0f) {
            return;
        }
        _snowman = this.getHealth();
        this.setHealth(_snowman - amount);
        this.getDamageTracker().onDamage(source, _snowman, amount);
        this.setAbsorptionAmount(this.getAbsorptionAmount() - amount);
    }

    public DamageTracker getDamageTracker() {
        return this.damageTracker;
    }

    @Nullable
    public LivingEntity getPrimeAdversary() {
        if (this.damageTracker.getBiggestAttacker() != null) {
            return this.damageTracker.getBiggestAttacker();
        }
        if (this.attackingPlayer != null) {
            return this.attackingPlayer;
        }
        if (this.attacker != null) {
            return this.attacker;
        }
        return null;
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
        }
        if (this.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            return 6 + (1 + this.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) * 2;
        }
        return 6;
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
                EntityAnimationS2CPacket entityAnimationS2CPacket = new EntityAnimationS2CPacket(this, hand == Hand.MAIN_HAND ? 0 : 3);
                ServerChunkManager _snowman2 = ((ServerWorld)this.world).getChunkManager();
                if (bl) {
                    _snowman2.sendToNearbyPlayers(this, entityAnimationS2CPacket);
                } else {
                    _snowman2.sendToOtherNearbyPlayers(this, entityAnimationS2CPacket);
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
            case 44: {
                boolean bl = status == 33;
                _snowman = status == 36;
                _snowman = status == 37;
                _snowman = status == 44;
                this.limbDistance = 1.5f;
                this.timeUntilRegen = 20;
                this.hurtTime = this.maxHurtTime = 10;
                this.knockbackVelocity = 0.0f;
                if (bl) {
                    this.playSound(SoundEvents.ENCHANT_THORNS_HIT, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                }
                if ((_snowman = this.getHurtSound(_snowman = _snowman ? DamageSource.ON_FIRE : (_snowman ? DamageSource.DROWN : (_snowman ? DamageSource.SWEET_BERRY_BUSH : DamageSource.GENERIC)))) != null) {
                    this.playSound(_snowman, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                }
                this.damage(DamageSource.GENERIC, 0.0f);
                break;
            }
            case 3: {
                SoundEvent soundEvent = this.getDeathSound();
                if (soundEvent != null) {
                    this.playSound(soundEvent, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                }
                if (this instanceof PlayerEntity) break;
                this.setHealth(0.0f);
                this.onDeath(DamageSource.GENERIC);
                break;
            }
            case 30: {
                this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + this.world.random.nextFloat() * 0.4f);
                break;
            }
            case 29: {
                this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0f, 0.8f + this.world.random.nextFloat() * 0.4f);
                break;
            }
            case 46: {
                int n = 128;
                for (_snowman = 0; _snowman < 128; ++_snowman) {
                    double d = (double)_snowman / 127.0;
                    float _snowman2 = (this.random.nextFloat() - 0.5f) * 0.2f;
                    float _snowman3 = (this.random.nextFloat() - 0.5f) * 0.2f;
                    float _snowman4 = (this.random.nextFloat() - 0.5f) * 0.2f;
                    _snowman = MathHelper.lerp(d, this.prevX, this.getX()) + (this.random.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
                    _snowman = MathHelper.lerp(d, this.prevY, this.getY()) + this.random.nextDouble() * (double)this.getHeight();
                    _snowman = MathHelper.lerp(d, this.prevZ, this.getZ()) + (this.random.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
                    this.world.addParticle(ParticleTypes.PORTAL, _snowman, _snowman, _snowman, _snowman2, _snowman3, _snowman4);
                }
                break;
            }
            case 47: {
                this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.MAINHAND));
                break;
            }
            case 48: {
                this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.OFFHAND));
                break;
            }
            case 49: {
                this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.HEAD));
                break;
            }
            case 50: {
                this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.CHEST));
                break;
            }
            case 51: {
                this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.LEGS));
                break;
            }
            case 52: {
                this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.FEET));
                break;
            }
            case 54: {
                HoneyBlock.addRichParticles(this);
                break;
            }
            case 55: {
                this.method_30127();
                break;
            }
            default: {
                super.handleStatus(status);
            }
        }
    }

    private void method_30127() {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.OFFHAND);
        this.equipStack(EquipmentSlot.OFFHAND, this.getEquippedStack(EquipmentSlot.MAINHAND));
        this.equipStack(EquipmentSlot.MAINHAND, itemStack);
    }

    @Override
    protected void destroy() {
        this.damage(DamageSource.OUT_OF_WORLD, 4.0f);
    }

    protected void tickHandSwing() {
        int n = this.getHandSwingDuration();
        if (this.handSwinging) {
            ++this.handSwingTicks;
            if (this.handSwingTicks >= n) {
                this.handSwingTicks = 0;
                this.handSwinging = false;
            }
        } else {
            this.handSwingTicks = 0;
        }
        this.handSwingProgress = (float)this.handSwingTicks / (float)n;
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
        return this.isHolding((Item item2) -> item2 == item);
    }

    public boolean isHolding(Predicate<Item> predicate) {
        return predicate.test(this.getMainHandStack().getItem()) || predicate.test(this.getOffHandStack().getItem());
    }

    public ItemStack getStackInHand(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            return this.getEquippedStack(EquipmentSlot.MAINHAND);
        }
        if (hand == Hand.OFF_HAND) {
            return this.getEquippedStack(EquipmentSlot.OFFHAND);
        }
        throw new IllegalArgumentException("Invalid hand " + (Object)((Object)hand));
    }

    public void setStackInHand(Hand hand, ItemStack stack) {
        if (hand == Hand.MAIN_HAND) {
            this.equipStack(EquipmentSlot.MAINHAND, stack);
        } else if (hand == Hand.OFF_HAND) {
            this.equipStack(EquipmentSlot.OFFHAND, stack);
        } else {
            throw new IllegalArgumentException("Invalid hand " + (Object)((Object)hand));
        }
    }

    public boolean hasStackEquipped(EquipmentSlot slot) {
        return !this.getEquippedStack(slot).isEmpty();
    }

    @Override
    public abstract Iterable<ItemStack> getArmorItems();

    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Override
    public abstract void equipStack(EquipmentSlot var1, ItemStack var2);

    public float getArmorVisibility() {
        Iterable<ItemStack> iterable = this.getArmorItems();
        int _snowman2 = 0;
        int _snowman3 = 0;
        for (ItemStack itemStack : iterable) {
            if (!itemStack.isEmpty()) {
                ++_snowman3;
            }
            ++_snowman2;
        }
        return _snowman2 > 0 ? (float)_snowman3 / (float)_snowman2 : 0.0f;
    }

    @Override
    public void setSprinting(boolean sprinting) {
        super.setSprinting(sprinting);
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance.getModifier(SPRINTING_SPEED_BOOST_ID) != null) {
            entityAttributeInstance.removeModifier(SPRINTING_SPEED_BOOST);
        }
        if (sprinting) {
            entityAttributeInstance.addTemporaryModifier(SPRINTING_SPEED_BOOST);
        }
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected float getSoundPitch() {
        if (this.isBaby()) {
            return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.5f;
        }
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f;
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
        Vec3d vec3d = vehicle.removed || this.world.getBlockState(vehicle.getBlockPos()).getBlock().isIn(BlockTags.PORTALS) ? new Vec3d(vehicle.getX(), vehicle.getY() + (double)vehicle.getHeight(), vehicle.getZ()) : vehicle.updatePassengerForDismount(this);
        this.requestTeleport(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public boolean shouldRenderName() {
        return this.isCustomNameVisible();
    }

    protected float getJumpVelocity() {
        return 0.42f * this.getJumpVelocityMultiplier();
    }

    protected void jump() {
        float f = this.getJumpVelocity();
        if (this.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            f += 0.1f * (float)(this.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1);
        }
        Vec3d _snowman2 = this.getVelocity();
        this.setVelocity(_snowman2.x, f, _snowman2.z);
        if (this.isSprinting()) {
            _snowman = this.yaw * ((float)Math.PI / 180);
            this.setVelocity(this.getVelocity().add(-MathHelper.sin(_snowman) * 0.2f, 0.0, MathHelper.cos(_snowman) * 0.2f));
        }
        this.velocityDirty = true;
    }

    protected void knockDownwards() {
        this.setVelocity(this.getVelocity().add(0.0, -0.04f, 0.0));
    }

    protected void swimUpward(net.minecraft.tag.Tag<Fluid> fluid) {
        this.setVelocity(this.getVelocity().add(0.0, 0.04f, 0.0));
    }

    protected float getBaseMovementSpeedMultiplier() {
        return 0.8f;
    }

    public boolean canWalkOnFluid(Fluid fluid) {
        return false;
    }

    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
            double d = 0.08;
            boolean bl = _snowman = this.getVelocity().y <= 0.0;
            if (_snowman && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
                d = 0.01;
                this.fallDistance = 0.0f;
            }
            FluidState _snowman2 = this.world.getFluidState(this.getBlockPos());
            if (this.isTouchingWater() && this.method_29920() && !this.canWalkOnFluid(_snowman2.getFluid())) {
                _snowman = this.getY();
                float f = this.isSprinting() ? 0.9f : this.getBaseMovementSpeedMultiplier();
                _snowman = 0.02f;
                _snowman = EnchantmentHelper.getDepthStrider(this);
                if (_snowman > 3.0f) {
                    _snowman = 3.0f;
                }
                if (!this.onGround) {
                    _snowman *= 0.5f;
                }
                if (_snowman > 0.0f) {
                    f += (0.54600006f - f) * _snowman / 3.0f;
                    _snowman += (this.getMovementSpeed() - _snowman) * _snowman / 3.0f;
                }
                if (this.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
                    f = 0.96f;
                }
                this.updateVelocity(_snowman, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                Vec3d _snowman3 = this.getVelocity();
                if (this.horizontalCollision && this.isClimbing()) {
                    _snowman3 = new Vec3d(_snowman3.x, 0.2, _snowman3.z);
                }
                this.setVelocity(_snowman3.multiply(f, 0.8f, f));
                Vec3d _snowman4 = this.method_26317(d, _snowman, this.getVelocity());
                this.setVelocity(_snowman4);
                if (this.horizontalCollision && this.doesNotCollide(_snowman4.x, _snowman4.y + (double)0.6f - this.getY() + _snowman, _snowman4.z)) {
                    this.setVelocity(_snowman4.x, 0.3f, _snowman4.z);
                }
            } else if (this.isInLava() && this.method_29920() && !this.canWalkOnFluid(_snowman2.getFluid())) {
                Vec3d vec3d;
                double _snowman5 = this.getY();
                this.updateVelocity(0.02f, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                if (this.getFluidHeight(FluidTags.LAVA) <= this.method_29241()) {
                    this.setVelocity(this.getVelocity().multiply(0.5, 0.8f, 0.5));
                    vec3d = this.method_26317(d, _snowman, this.getVelocity());
                    this.setVelocity(vec3d);
                } else {
                    this.setVelocity(this.getVelocity().multiply(0.5));
                }
                if (!this.hasNoGravity()) {
                    this.setVelocity(this.getVelocity().add(0.0, -d / 4.0, 0.0));
                }
                vec3d = this.getVelocity();
                if (this.horizontalCollision && this.doesNotCollide(vec3d.x, vec3d.y + (double)0.6f - this.getY() + _snowman5, vec3d.z)) {
                    this.setVelocity(vec3d.x, 0.3f, vec3d.z);
                }
            } else if (this.isFallFlying()) {
                float f;
                double d2;
                Vec3d _snowman11 = this.getVelocity();
                if (_snowman11.y > -0.5) {
                    this.fallDistance = 1.0f;
                }
                vec3d = this.getRotationVector();
                float _snowman6 = this.pitch * ((float)Math.PI / 180);
                double _snowman7 = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
                double _snowman8 = Math.sqrt(LivingEntity.squaredHorizontalLength(_snowman11));
                double _snowman9 = vec3d.length();
                float _snowman10 = MathHelper.cos(_snowman6);
                _snowman10 = (float)((double)_snowman10 * ((double)_snowman10 * Math.min(1.0, _snowman9 / 0.4)));
                _snowman11 = this.getVelocity().add(0.0, d * (-1.0 + (double)_snowman10 * 0.75), 0.0);
                if (_snowman11.y < 0.0 && _snowman7 > 0.0) {
                    d2 = _snowman11.y * -0.1 * (double)_snowman10;
                    _snowman11 = _snowman11.add(vec3d.x * d2 / _snowman7, d2, vec3d.z * d2 / _snowman7);
                }
                if (_snowman6 < 0.0f && _snowman7 > 0.0) {
                    d2 = _snowman8 * (double)(-MathHelper.sin(_snowman6)) * 0.04;
                    _snowman11 = _snowman11.add(-vec3d.x * d2 / _snowman7, d2 * 3.2, -vec3d.z * d2 / _snowman7);
                }
                if (_snowman7 > 0.0) {
                    Vec3d vec3d;
                    _snowman11 = _snowman11.add((vec3d.x / _snowman7 * _snowman8 - _snowman11.x) * 0.1, 0.0, (vec3d.z / _snowman7 * _snowman8 - _snowman11.z) * 0.1);
                }
                this.setVelocity(_snowman11.multiply(0.99f, 0.98f, 0.99f));
                this.move(MovementType.SELF, this.getVelocity());
                if (this.horizontalCollision && !this.world.isClient && (f = (float)((_snowman = _snowman8 - (d2 = Math.sqrt(LivingEntity.squaredHorizontalLength(this.getVelocity())))) * 10.0 - 3.0)) > 0.0f) {
                    this.playSound(this.getFallSound((int)f), 1.0f, 1.0f);
                    this.damage(DamageSource.FLY_INTO_WALL, f);
                }
                if (this.onGround && !this.world.isClient) {
                    this.setFlag(7, false);
                }
            } else {
                BlockPos blockPos = this.getVelocityAffectingPos();
                float _snowman12 = this.world.getBlockState(blockPos).getBlock().getSlipperiness();
                float _snowman13 = this.onGround ? _snowman12 * 0.91f : 0.91f;
                Vec3d _snowman14 = this.method_26318(movementInput, _snowman12);
                double _snowman15 = _snowman14.y;
                if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
                    _snowman15 += (0.05 * (double)(this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - _snowman14.y) * 0.2;
                    this.fallDistance = 0.0f;
                } else if (!this.world.isClient || this.world.isChunkLoaded(blockPos)) {
                    if (!this.hasNoGravity()) {
                        _snowman15 -= d;
                    }
                } else {
                    _snowman15 = this.getY() > 0.0 ? -0.1 : 0.0;
                }
                this.setVelocity(_snowman14.x * (double)_snowman13, _snowman15 * (double)0.98f, _snowman14.z * (double)_snowman13);
            }
        }
        this.method_29242(this, this instanceof Flutterer);
    }

    public void method_29242(LivingEntity livingEntity, boolean bl) {
        livingEntity.lastLimbDistance = livingEntity.limbDistance;
        double d = livingEntity.getX() - livingEntity.prevX;
        float _snowman2 = MathHelper.sqrt(d * d + (_snowman = bl ? livingEntity.getY() - livingEntity.prevY : 0.0) * _snowman + (_snowman = livingEntity.getZ() - livingEntity.prevZ) * _snowman) * 4.0f;
        if (_snowman2 > 1.0f) {
            _snowman2 = 1.0f;
        }
        livingEntity.limbDistance += (_snowman2 - livingEntity.limbDistance) * 0.4f;
        livingEntity.limbAngle += livingEntity.limbDistance;
    }

    public Vec3d method_26318(Vec3d vec3d, float f) {
        this.updateVelocity(this.getMovementSpeed(f), vec3d);
        this.setVelocity(this.applyClimbingSpeed(this.getVelocity()));
        this.move(MovementType.SELF, this.getVelocity());
        Vec3d vec3d2 = this.getVelocity();
        if ((this.horizontalCollision || this.jumping) && this.isClimbing()) {
            vec3d2 = new Vec3d(vec3d2.x, 0.2, vec3d2.z);
        }
        return vec3d2;
    }

    public Vec3d method_26317(double d, boolean bl, Vec3d vec3d2) {
        Vec3d vec3d2;
        if (!this.hasNoGravity() && !this.isSprinting()) {
            double d2 = bl && Math.abs(vec3d2.y - 0.005) >= 0.003 && Math.abs(vec3d2.y - d / 16.0) < 0.003 ? -0.003 : vec3d2.y - d / 16.0;
            return new Vec3d(vec3d2.x, d2, vec3d2.z);
        }
        return vec3d2;
    }

    private Vec3d applyClimbingSpeed(Vec3d motion) {
        if (this.isClimbing()) {
            this.fallDistance = 0.0f;
            float f = 0.15f;
            double _snowman2 = MathHelper.clamp(motion.x, (double)-0.15f, (double)0.15f);
            double _snowman3 = MathHelper.clamp(motion.z, (double)-0.15f, (double)0.15f);
            double _snowman4 = Math.max(motion.y, (double)-0.15f);
            if (_snowman4 < 0.0 && !this.getBlockState().isOf(Blocks.SCAFFOLDING) && this.isHoldingOntoLadder() && this instanceof PlayerEntity) {
                _snowman4 = 0.0;
            }
            motion = new Vec3d(_snowman2, _snowman4, _snowman3);
        }
        return motion;
    }

    private float getMovementSpeed(float slipperiness) {
        if (this.onGround) {
            return this.getMovementSpeed() * (0.21600002f / (slipperiness * slipperiness * slipperiness));
        }
        return this.flyingSpeed;
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
            int n = this.getStuckArrowCount();
            if (n > 0) {
                if (this.stuckArrowTimer <= 0) {
                    this.stuckArrowTimer = 20 * (30 - n);
                }
                --this.stuckArrowTimer;
                if (this.stuckArrowTimer <= 0) {
                    this.setStuckArrowCount(n - 1);
                }
            }
            if ((_snowman = this.getStingerCount()) > 0) {
                if (this.stuckStingerTimer <= 0) {
                    this.stuckStingerTimer = 20 * (30 - _snowman);
                }
                --this.stuckStingerTimer;
                if (this.stuckStingerTimer <= 0) {
                    this.setStingerCount(_snowman - 1);
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
        _snowman = this.getZ() - this.prevZ;
        float _snowman2 = (float)(d * d + _snowman * _snowman);
        float _snowman3 = this.bodyYaw;
        float _snowman4 = 0.0f;
        this.prevStepBobbingAmount = this.stepBobbingAmount;
        float _snowman5 = 0.0f;
        if (_snowman2 > 0.0025000002f) {
            _snowman5 = 1.0f;
            _snowman4 = (float)Math.sqrt(_snowman2) * 3.0f;
            float f = (float)MathHelper.atan2(_snowman, d) * 57.295776f - 90.0f;
            _snowman = MathHelper.abs(MathHelper.wrapDegrees(this.yaw) - f);
            _snowman3 = 95.0f < _snowman && _snowman < 265.0f ? f - 180.0f : f;
        }
        if (this.handSwingProgress > 0.0f) {
            _snowman3 = this.yaw;
        }
        if (!this.onGround) {
            _snowman5 = 0.0f;
        }
        this.stepBobbingAmount += (_snowman5 - this.stepBobbingAmount) * 0.3f;
        this.world.getProfiler().push("headTurn");
        _snowman4 = this.turnHead(_snowman3, _snowman4);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("rangeChecks");
        while (this.yaw - this.prevYaw < -180.0f) {
            this.prevYaw -= 360.0f;
        }
        while (this.yaw - this.prevYaw >= 180.0f) {
            this.prevYaw += 360.0f;
        }
        while (this.bodyYaw - this.prevBodyYaw < -180.0f) {
            this.prevBodyYaw -= 360.0f;
        }
        while (this.bodyYaw - this.prevBodyYaw >= 180.0f) {
            this.prevBodyYaw += 360.0f;
        }
        while (this.pitch - this.prevPitch < -180.0f) {
            this.prevPitch -= 360.0f;
        }
        while (this.pitch - this.prevPitch >= 180.0f) {
            this.prevPitch += 360.0f;
        }
        while (this.headYaw - this.prevHeadYaw < -180.0f) {
            this.prevHeadYaw -= 360.0f;
        }
        while (this.headYaw - this.prevHeadYaw >= 180.0f) {
            this.prevHeadYaw += 360.0f;
        }
        this.world.getProfiler().pop();
        this.lookDirection += _snowman4;
        this.roll = this.isFallFlying() ? ++this.roll : 0;
        if (this.isSleeping()) {
            this.pitch = 0.0f;
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
        EnumMap enumMap = null;
        block4: for (EquipmentSlot equipmentSlot2 : EquipmentSlot.values()) {
            ItemStack _snowman2;
            switch (equipmentSlot2.getType()) {
                case HAND: {
                    _snowman2 = this.method_30126(equipmentSlot2);
                    break;
                }
                case ARMOR: {
                    EquipmentSlot equipmentSlot2;
                    _snowman2 = this.method_30125(equipmentSlot2);
                    break;
                }
                default: {
                    continue block4;
                }
            }
            ItemStack itemStack = this.getEquippedStack(equipmentSlot2);
            if (ItemStack.areEqual(itemStack, _snowman2)) continue;
            if (enumMap == null) {
                enumMap = Maps.newEnumMap(EquipmentSlot.class);
            }
            enumMap.put(equipmentSlot2, itemStack);
            if (!_snowman2.isEmpty()) {
                this.getAttributes().removeModifiers(_snowman2.getAttributeModifiers(equipmentSlot2));
            }
            if (itemStack.isEmpty()) continue;
            this.getAttributes().addTemporaryModifiers(itemStack.getAttributeModifiers(equipmentSlot2));
        }
        return enumMap;
    }

    private void method_30121(Map<EquipmentSlot, ItemStack> map) {
        ItemStack itemStack = map.get((Object)EquipmentSlot.MAINHAND);
        _snowman = map.get((Object)EquipmentSlot.OFFHAND);
        if (itemStack != null && _snowman != null && ItemStack.areEqual(itemStack, this.method_30126(EquipmentSlot.OFFHAND)) && ItemStack.areEqual(_snowman, this.method_30126(EquipmentSlot.MAINHAND))) {
            ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityStatusS2CPacket(this, 55));
            map.remove((Object)EquipmentSlot.MAINHAND);
            map.remove((Object)EquipmentSlot.OFFHAND);
            this.method_30124(EquipmentSlot.MAINHAND, itemStack.copy());
            this.method_30124(EquipmentSlot.OFFHAND, _snowman.copy());
        }
    }

    private void method_30123(Map<EquipmentSlot, ItemStack> map) {
        ArrayList arrayList = Lists.newArrayListWithCapacity((int)map.size());
        map.forEach((equipmentSlot, itemStack) -> {
            _snowman = itemStack.copy();
            arrayList.add(Pair.of((Object)equipmentSlot, (Object)_snowman));
            switch (equipmentSlot.getType()) {
                case HAND: {
                    this.method_30124((EquipmentSlot)((Object)equipmentSlot), _snowman);
                    break;
                }
                case ARMOR: {
                    this.method_30122((EquipmentSlot)((Object)equipmentSlot), _snowman);
                }
            }
        });
        ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityEquipmentUpdateS2CPacket(this.getEntityId(), arrayList));
    }

    private ItemStack method_30125(EquipmentSlot equipmentSlot) {
        return this.equippedArmor.get(equipmentSlot.getEntitySlotId());
    }

    private void method_30122(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        this.equippedArmor.set(equipmentSlot.getEntitySlotId(), itemStack);
    }

    private ItemStack method_30126(EquipmentSlot equipmentSlot) {
        return this.equippedHand.get(equipmentSlot.getEntitySlotId());
    }

    private void method_30124(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        this.equippedHand.set(equipmentSlot.getEntitySlotId(), itemStack);
    }

    protected float turnHead(float bodyRotation, float headRotation) {
        float f = MathHelper.wrapDegrees(bodyRotation - this.bodyYaw);
        this.bodyYaw += f * 0.3f;
        _snowman = MathHelper.wrapDegrees(this.yaw - this.bodyYaw);
        boolean bl = _snowman = _snowman < -90.0f || _snowman >= 90.0f;
        if (_snowman < -75.0f) {
            _snowman = -75.0f;
        }
        if (_snowman >= 75.0f) {
            _snowman = 75.0f;
        }
        this.bodyYaw = this.yaw - _snowman;
        if (_snowman * _snowman > 2500.0f) {
            this.bodyYaw += _snowman * 0.2f;
        }
        if (_snowman) {
            headRotation *= -1.0f;
        }
        return headRotation;
    }

    public void tickMovement() {
        if (this.jumpingCooldown > 0) {
            --this.jumpingCooldown;
        }
        if (this.isLogicalSideForUpdatingMovement()) {
            this.bodyTrackingIncrements = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }
        if (this.bodyTrackingIncrements > 0) {
            double d = this.getX() + (this.serverX - this.getX()) / (double)this.bodyTrackingIncrements;
            _snowman = this.getY() + (this.serverY - this.getY()) / (double)this.bodyTrackingIncrements;
            _snowman = this.getZ() + (this.serverZ - this.getZ()) / (double)this.bodyTrackingIncrements;
            _snowman = MathHelper.wrapDegrees(this.serverYaw - (double)this.yaw);
            this.yaw = (float)((double)this.yaw + _snowman / (double)this.bodyTrackingIncrements);
            this.pitch = (float)((double)this.pitch + (this.serverPitch - (double)this.pitch) / (double)this.bodyTrackingIncrements);
            --this.bodyTrackingIncrements;
            this.updatePosition(d, _snowman, _snowman);
            this.setRotation(this.yaw, this.pitch);
        } else if (!this.canMoveVoluntarily()) {
            this.setVelocity(this.getVelocity().multiply(0.98));
        }
        if (this.headTrackingIncrements > 0) {
            this.headYaw = (float)((double)this.headYaw + MathHelper.wrapDegrees(this.serverHeadYaw - (double)this.headYaw) / (double)this.headTrackingIncrements);
            --this.headTrackingIncrements;
        }
        Vec3d vec3d = this.getVelocity();
        double _snowman2 = vec3d.x;
        double _snowman3 = vec3d.y;
        double _snowman4 = vec3d.z;
        if (Math.abs(vec3d.x) < 0.003) {
            _snowman2 = 0.0;
        }
        if (Math.abs(vec3d.y) < 0.003) {
            _snowman3 = 0.0;
        }
        if (Math.abs(vec3d.z) < 0.003) {
            _snowman4 = 0.0;
        }
        this.setVelocity(_snowman2, _snowman3, _snowman4);
        this.world.getProfiler().push("ai");
        if (this.isImmobile()) {
            this.jumping = false;
            this.sidewaysSpeed = 0.0f;
            this.forwardSpeed = 0.0f;
        } else if (this.canMoveVoluntarily()) {
            this.world.getProfiler().push("newAi");
            this.tickNewAi();
            this.world.getProfiler().pop();
        }
        this.world.getProfiler().pop();
        this.world.getProfiler().push("jump");
        if (this.jumping && this.method_29920()) {
            double d = this.isInLava() ? this.getFluidHeight(FluidTags.LAVA) : this.getFluidHeight(FluidTags.WATER);
            boolean _snowman5 = this.isTouchingWater() && d > 0.0;
            _snowman = this.method_29241();
            if (_snowman5 && (!this.onGround || d > _snowman)) {
                this.swimUpward(FluidTags.WATER);
            } else if (this.isInLava() && (!this.onGround || d > _snowman)) {
                this.swimUpward(FluidTags.LAVA);
            } else if ((this.onGround || _snowman5 && d <= _snowman) && this.jumpingCooldown == 0) {
                this.jump();
                this.jumpingCooldown = 10;
            }
        } else {
            this.jumpingCooldown = 0;
        }
        this.world.getProfiler().pop();
        this.world.getProfiler().push("travel");
        this.sidewaysSpeed *= 0.98f;
        this.forwardSpeed *= 0.98f;
        this.initAi();
        Box box = this.getBoundingBox();
        this.travel(new Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed));
        this.world.getProfiler().pop();
        this.world.getProfiler().push("push");
        if (this.riptideTicks > 0) {
            --this.riptideTicks;
            this.tickRiptide(box, this.getBoundingBox());
        }
        this.tickCramming();
        this.world.getProfiler().pop();
        if (!this.world.isClient && this.hurtByWater() && this.isWet()) {
            this.damage(DamageSource.DROWN, 1.0f);
        }
    }

    public boolean hurtByWater() {
        return false;
    }

    private void initAi() {
        boolean bl = this.getFlag(7);
        if (bl && !this.onGround && !this.hasVehicle() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.CHEST);
            if (itemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(itemStack)) {
                bl = true;
                if (!this.world.isClient && (this.roll + 1) % 20 == 0) {
                    itemStack.damage(1, this, livingEntity -> livingEntity.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
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
            int n = this.world.getGameRules().getInt(GameRules.MAX_ENTITY_CRAMMING);
            if (n > 0 && list.size() > n - 1 && this.random.nextInt(4) == 0) {
                _snowman = 0;
                for (_snowman = 0; _snowman < list.size(); ++_snowman) {
                    if (list.get(_snowman).hasVehicle()) continue;
                    ++_snowman;
                }
                if (_snowman > n - 1) {
                    this.damage(DamageSource.CRAMMING, 6.0f);
                }
            }
            for (_snowman = 0; _snowman < list.size(); ++_snowman) {
                Entity entity = list.get(_snowman);
                this.pushAway(entity);
            }
        }
    }

    protected void tickRiptide(Box a, Box b) {
        Box box = a.union(b);
        List<Entity> _snowman2 = this.world.getOtherEntities(this, box);
        if (!_snowman2.isEmpty()) {
            for (int i = 0; i < _snowman2.size(); ++i) {
                Entity entity = _snowman2.get(i);
                if (!(entity instanceof LivingEntity)) continue;
                this.attackLivingEntity((LivingEntity)entity);
                this.riptideTicks = 0;
                this.setVelocity(this.getVelocity().multiply(-0.2));
                break;
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

    public void setRiptideTicks(int n) {
        this.riptideTicks = n;
        if (!this.world.isClient) {
            this.setLivingFlag(4, true);
        }
    }

    public boolean isUsingRiptide() {
        return (this.dataTracker.get(LIVING_FLAGS) & 4) != 0;
    }

    @Override
    public void stopRiding() {
        Entity entity = this.getVehicle();
        super.stopRiding();
        if (entity != null && entity != this.getVehicle() && !this.world.isClient) {
            this.onDismounted(entity);
        }
    }

    @Override
    public void tickRiding() {
        super.tickRiding();
        this.prevStepBobbingAmount = this.stepBobbingAmount;
        this.stepBobbingAmount = 0.0f;
        this.fallDistance = 0.0f;
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.serverX = x;
        this.serverY = y;
        this.serverZ = z;
        this.serverYaw = yaw;
        this.serverPitch = pitch;
        this.bodyTrackingIncrements = interpolationSteps;
    }

    @Override
    public void updateTrackedHeadRotation(float yaw, int interpolationSteps) {
        this.serverHeadYaw = yaw;
        this.headTrackingIncrements = interpolationSteps;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void method_29499(ItemEntity itemEntity) {
        PlayerEntity playerEntity = _snowman = itemEntity.getThrower() != null ? this.world.getPlayerByUuid(itemEntity.getThrower()) : null;
        if (_snowman instanceof ServerPlayerEntity) {
            Criteria.THROWN_ITEM_PICKED_UP_BY_ENTITY.trigger((ServerPlayerEntity)_snowman, itemEntity.getStack(), this);
        }
    }

    public void sendPickup(Entity item, int count) {
        if (!item.removed && !this.world.isClient && (item instanceof ItemEntity || item instanceof PersistentProjectileEntity || item instanceof ExperienceOrbEntity)) {
            ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(item, new ItemPickupAnimationS2CPacket(item.getEntityId(), this.getEntityId(), count));
        }
    }

    public boolean canSee(Entity entity) {
        Vec3d vec3d = new Vec3d(this.getX(), this.getEyeY(), this.getZ());
        return this.world.raycast(new RaycastContext(vec3d, _snowman = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ()), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)).getType() == HitResult.Type.MISS;
    }

    @Override
    public float getYaw(float tickDelta) {
        if (tickDelta == 1.0f) {
            return this.headYaw;
        }
        return MathHelper.lerp(tickDelta, this.prevHeadYaw, this.headYaw);
    }

    public float getHandSwingProgress(float tickDelta) {
        float f = this.handSwingProgress - this.lastHandSwingProgress;
        if (f < 0.0f) {
            f += 1.0f;
        }
        return this.lastHandSwingProgress + f * tickDelta;
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
    protected Vec3d method_30633(Direction.Axis axis, class_5459.class_5460 class_54602) {
        return LivingEntity.method_31079(super.method_30633(axis, class_54602));
    }

    public static Vec3d method_31079(Vec3d vec3d) {
        return new Vec3d(vec3d.x, vec3d.y, 0.0);
    }

    public float getAbsorptionAmount() {
        return this.absorptionAmount;
    }

    public void setAbsorptionAmount(float amount) {
        if (amount < 0.0f) {
            amount = 0.0f;
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
        int n = this.getItemUseTimeLeft();
        FoodComponent _snowman2 = this.activeItemStack.getItem().getFoodComponent();
        boolean _snowman3 = _snowman2 != null && _snowman2.isSnack();
        return (_snowman3 |= n <= this.activeItemStack.getMaxUseTime() - 7) && n % 4 == 0;
    }

    private void updateLeaningPitch() {
        this.lastLeaningPitch = this.leaningPitch;
        this.leaningPitch = this.isInSwimmingPose() ? Math.min(1.0f, this.leaningPitch + 0.09f) : Math.max(0.0f, this.leaningPitch - 0.09f);
    }

    protected void setLivingFlag(int mask, boolean value) {
        int n = this.dataTracker.get(LIVING_FLAGS).byteValue();
        n = value ? (n |= mask) : (n &= ~mask);
        this.dataTracker.set(LIVING_FLAGS, (byte)n);
    }

    public void setCurrentHand(Hand hand) {
        ItemStack itemStack = this.getStackInHand(hand);
        if (itemStack.isEmpty() || this.isUsingItem()) {
            return;
        }
        this.activeItemStack = itemStack;
        this.itemUseTimeLeft = itemStack.getMaxUseTime();
        if (!this.world.isClient) {
            this.setLivingFlag(1, true);
            this.setLivingFlag(2, hand == Hand.OFF_HAND);
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
        this.prevBodyYaw = this.bodyYaw = this.headYaw;
    }

    protected void spawnConsumptionEffects(ItemStack stack, int particleCount) {
        if (stack.isEmpty() || !this.isUsingItem()) {
            return;
        }
        if (stack.getUseAction() == UseAction.DRINK) {
            this.playSound(this.getDrinkSound(stack), 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
        }
        if (stack.getUseAction() == UseAction.EAT) {
            this.spawnItemParticles(stack, particleCount);
            this.playSound(this.getEatSound(stack), 0.5f + 0.5f * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
        }
    }

    private void spawnItemParticles(ItemStack stack, int count) {
        for (int i = 0; i < count; ++i) {
            Vec3d vec3d = new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            vec3d = vec3d.rotateX(-this.pitch * ((float)Math.PI / 180));
            vec3d = vec3d.rotateY(-this.yaw * ((float)Math.PI / 180));
            double _snowman2 = (double)(-this.random.nextFloat()) * 0.6 - 0.3;
            _snowman = new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.3, _snowman2, 0.6);
            _snowman = _snowman.rotateX(-this.pitch * ((float)Math.PI / 180));
            _snowman = _snowman.rotateY(-this.yaw * ((float)Math.PI / 180));
            _snowman = _snowman.add(this.getX(), this.getEyeY(), this.getZ());
            this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), _snowman.x, _snowman.y, _snowman.z, vec3d.x, vec3d.y + 0.05, vec3d.z);
        }
    }

    protected void consumeItem() {
        Hand hand = this.getActiveHand();
        if (!this.activeItemStack.equals(this.getStackInHand(hand))) {
            this.stopUsingItem();
            return;
        }
        if (!this.activeItemStack.isEmpty() && this.isUsingItem()) {
            this.spawnConsumptionEffects(this.activeItemStack, 16);
            ItemStack itemStack = this.activeItemStack.finishUsing(this.world, this);
            if (itemStack != this.activeItemStack) {
                this.setStackInHand(hand, itemStack);
            }
            this.clearActiveItem();
        }
    }

    public ItemStack getActiveItem() {
        return this.activeItemStack;
    }

    public int getItemUseTimeLeft() {
        return this.itemUseTimeLeft;
    }

    public int getItemUseTime() {
        if (this.isUsingItem()) {
            return this.activeItemStack.getMaxUseTime() - this.getItemUseTimeLeft();
        }
        return 0;
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
        if (!this.isUsingItem() || this.activeItemStack.isEmpty()) {
            return false;
        }
        Item item = this.activeItemStack.getItem();
        if (item.getUseAction(this.activeItemStack) != UseAction.BLOCK) {
            return false;
        }
        return item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= 5;
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
        double d = this.getX();
        _snowman = this.getY();
        _snowman = this.getZ();
        _snowman = y;
        boolean _snowman2 = false;
        World _snowman3 = this.world;
        BlockPos _snowman4 = new BlockPos(x, _snowman, z);
        if (_snowman3.isChunkLoaded(_snowman4)) {
            boolean bl = false;
            while (!bl && _snowman4.getY() > 0) {
                BlockPos blockPos = _snowman4.down();
                BlockState _snowman5 = _snowman3.getBlockState(blockPos);
                if (_snowman5.getMaterial().blocksMovement()) {
                    bl = true;
                    continue;
                }
                _snowman -= 1.0;
                _snowman4 = blockPos;
            }
            if (bl) {
                this.requestTeleport(x, _snowman, z);
                if (_snowman3.isSpaceEmpty(this) && !_snowman3.containsFluid(this.getBoundingBox())) {
                    _snowman2 = true;
                }
            }
        }
        if (!_snowman2) {
            this.requestTeleport(d, _snowman, _snowman);
            return false;
        }
        if (particleEffects) {
            _snowman3.sendEntityStatus(this, (byte)46);
        }
        if (this instanceof PathAwareEntity) {
            ((PathAwareEntity)this).getNavigation().stop();
        }
        return true;
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
        return ImmutableList.of((Object)((Object)EntityPose.STANDING));
    }

    public Box getBoundingBox(EntityPose pose) {
        EntityDimensions entityDimensions = this.getDimensions(pose);
        return new Box(-entityDimensions.width / 2.0f, 0.0, -entityDimensions.width / 2.0f, entityDimensions.width / 2.0f, entityDimensions.height, entityDimensions.width / 2.0f);
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
        BlockState blockState;
        if (this.hasVehicle()) {
            this.stopRiding();
        }
        if ((blockState = this.world.getBlockState(pos)).getBlock() instanceof BedBlock) {
            this.world.setBlockState(pos, (BlockState)blockState.with(BedBlock.OCCUPIED, true), 3);
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
        return this.getSleepingPosition().map(blockPos -> this.world.getBlockState((BlockPos)blockPos).getBlock() instanceof BedBlock).orElse(false);
    }

    public void wakeUp() {
        this.getSleepingPosition().filter(this.world::isChunkLoaded).ifPresent(blockPos -> {
            BlockState blockState = this.world.getBlockState((BlockPos)blockPos);
            if (blockState.getBlock() instanceof BedBlock) {
                this.world.setBlockState((BlockPos)blockPos, (BlockState)blockState.with(BedBlock.OCCUPIED, false), 3);
                Vec3d vec3d = BedBlock.findWakeUpPosition(this.getType(), this.world, blockPos, this.yaw).orElseGet(() -> {
                    _snowman = blockPos.up();
                    return new Vec3d((double)_snowman.getX() + 0.5, (double)_snowman.getY() + 0.1, (double)_snowman.getZ() + 0.5);
                });
                _snowman = Vec3d.ofBottomCenter(blockPos).subtract(vec3d).normalize();
                float _snowman2 = (float)MathHelper.wrapDegrees(MathHelper.atan2(_snowman.z, _snowman.x) * 57.2957763671875 - 90.0);
                this.updatePosition(vec3d.x, vec3d.y, vec3d.z);
                this.yaw = _snowman2;
                this.pitch = 0.0f;
            }
        });
        Vec3d vec3d = this.getPos();
        this.setPose(EntityPose.STANDING);
        this.updatePosition(vec3d.x, vec3d.y, vec3d.z);
        this.clearSleepingPosition();
    }

    @Nullable
    public Direction getSleepingDirection() {
        BlockPos blockPos = this.getSleepingPosition().orElse(null);
        return blockPos != null ? BedBlock.getDirection(this.world, blockPos) : null;
    }

    @Override
    public boolean isInsideWall() {
        return !this.isSleeping() && super.isInsideWall();
    }

    @Override
    protected final float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return pose == EntityPose.SLEEPING ? 0.2f : this.getActiveEyeHeight(pose, dimensions);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return super.getEyeHeight(pose, dimensions);
    }

    public ItemStack getArrowType(ItemStack stack) {
        return ItemStack.EMPTY;
    }

    public ItemStack eatFood(World world, ItemStack stack) {
        if (stack.isFood()) {
            world.playSound(null, this.getX(), this.getY(), this.getZ(), this.getEatSound(stack), SoundCategory.NEUTRAL, 1.0f, 1.0f + (world.random.nextFloat() - world.random.nextFloat()) * 0.4f);
            this.applyFoodEffects(stack, world, this);
            if (!(this instanceof PlayerEntity) || !((PlayerEntity)this).abilities.creativeMode) {
                stack.decrement(1);
            }
        }
        return stack;
    }

    private void applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity) {
        Item item = stack.getItem();
        if (item.isFood()) {
            List<Pair<StatusEffectInstance, Float>> list = item.getFoodComponent().getStatusEffects();
            for (Pair<StatusEffectInstance, Float> pair : list) {
                if (world.isClient || pair.getFirst() == null || !(world.random.nextFloat() < ((Float)pair.getSecond()).floatValue())) continue;
                targetEntity.addStatusEffect(new StatusEffectInstance((StatusEffectInstance)pair.getFirst()));
            }
        }
    }

    private static byte getEquipmentBreakStatus(EquipmentSlot slot) {
        switch (slot) {
            case MAINHAND: {
                return 47;
            }
            case OFFHAND: {
                return 48;
            }
            case HEAD: {
                return 49;
            }
            case CHEST: {
                return 50;
            }
            case FEET: {
                return 52;
            }
            case LEGS: {
                return 51;
            }
        }
        return 47;
    }

    public void sendEquipmentBreakStatus(EquipmentSlot slot) {
        this.world.sendEntityStatus(this, LivingEntity.getEquipmentBreakStatus(slot));
    }

    public void sendToolBreakStatus(Hand hand) {
        this.sendEquipmentBreakStatus(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }

    @Override
    public Box getVisibilityBoundingBox() {
        if (this.getEquippedStack(EquipmentSlot.HEAD).getItem() == Items.DRAGON_HEAD) {
            float f = 0.5f;
            return this.getBoundingBox().expand(0.5, 0.5, 0.5);
        }
        return super.getVisibilityBoundingBox();
    }
}

