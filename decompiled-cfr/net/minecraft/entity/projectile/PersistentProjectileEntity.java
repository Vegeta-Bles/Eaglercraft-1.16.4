/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public abstract class PersistentProjectileEntity
extends ProjectileEntity {
    private static final TrackedData<Byte> PROJECTILE_FLAGS = DataTracker.registerData(PersistentProjectileEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Byte> PIERCE_LEVEL = DataTracker.registerData(PersistentProjectileEntity.class, TrackedDataHandlerRegistry.BYTE);
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int inGroundTime;
    public PickupPermission pickupType = PickupPermission.DISALLOWED;
    public int shake;
    private int life;
    private double damage = 2.0;
    private int punch;
    private SoundEvent sound = this.getHitSound();
    private IntOpenHashSet piercedEntities;
    private List<Entity> piercingKilledEntities;

    protected PersistentProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    protected PersistentProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) {
        this(type, world);
        this.updatePosition(x, y, z);
    }

    protected PersistentProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
        this(type, owner.getX(), owner.getEyeY() - (double)0.1f, owner.getZ(), world);
        this.setOwner(owner);
        if (owner instanceof PlayerEntity) {
            this.pickupType = PickupPermission.ALLOWED;
        }
    }

    public void setSound(SoundEvent sound) {
        this.sound = sound;
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 10.0;
        if (Double.isNaN(d)) {
            d = 1.0;
        }
        return distance < (d *= 64.0 * PersistentProjectileEntity.getRenderDistanceMultiplier()) * d;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(PROJECTILE_FLAGS, (byte)0);
        this.dataTracker.startTracking(PIERCE_LEVEL, (byte)0);
    }

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        super.setVelocity(x, y, z, speed, divergence);
        this.life = 0;
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.updatePosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        super.setVelocityClient(x, y, z);
        this.life = 0;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void tick() {
        Vec3d vec3d;
        VoxelShape _snowman3;
        BlockPos blockPos;
        BlockState blockState;
        super.tick();
        boolean bl = this.isNoClip();
        Vec3d _snowman2 = this.getVelocity();
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            float f = MathHelper.sqrt(PersistentProjectileEntity.squaredHorizontalLength(_snowman2));
            this.yaw = (float)(MathHelper.atan2(_snowman2.x, _snowman2.z) * 57.2957763671875);
            this.pitch = (float)(MathHelper.atan2(_snowman2.y, f) * 57.2957763671875);
            this.prevYaw = this.yaw;
            this.prevPitch = this.pitch;
        }
        if (!((blockState = this.world.getBlockState(blockPos = this.getBlockPos())).isAir() || bl || (_snowman3 = blockState.getCollisionShape(this.world, blockPos)).isEmpty())) {
            Vec3d vec3d2 = this.getPos();
            for (Box box : _snowman3.getBoundingBoxes()) {
                if (!box.offset(blockPos).contains(vec3d2)) continue;
                this.inGround = true;
                break;
            }
        }
        if (this.shake > 0) {
            --this.shake;
        }
        if (this.isTouchingWaterOrRain()) {
            this.extinguish();
        }
        if (this.inGround && !bl) {
            if (this.inBlockState != blockState && this.method_26351()) {
                this.method_26352();
            } else if (!this.world.isClient) {
                this.age();
            }
            ++this.inGroundTime;
            return;
        }
        this.inGroundTime = 0;
        Vec3d _snowman9 = this.getPos();
        HitResult _snowman4 = this.world.raycast(new RaycastContext(_snowman9, vec3d = _snowman9.add(_snowman2), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (_snowman4.getType() != HitResult.Type.MISS) {
            vec3d = _snowman4.getPos();
        }
        while (!this.removed) {
            void var8_13;
            EntityHitResult entityHitResult = this.getEntityCollision(_snowman9, vec3d);
            if (entityHitResult != null) {
                _snowman4 = entityHitResult;
            }
            if (_snowman4 != null && _snowman4.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult)_snowman4).getEntity();
                Entity entity2 = this.getOwner();
                if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity)entity2).shouldDamagePlayer((PlayerEntity)entity)) {
                    _snowman4 = null;
                    Object var8_12 = null;
                }
            }
            if (_snowman4 != null && !bl) {
                this.onCollision(_snowman4);
                this.velocityDirty = true;
            }
            if (var8_13 == null || this.getPierceLevel() <= 0) break;
            _snowman4 = null;
        }
        _snowman2 = this.getVelocity();
        double d = _snowman2.x;
        double d2 = _snowman2.y;
        _snowman = _snowman2.z;
        if (this.isCritical()) {
            for (int i = 0; i < 4; ++i) {
                this.world.addParticle(ParticleTypes.CRIT, this.getX() + d * (double)i / 4.0, this.getY() + d2 * (double)i / 4.0, this.getZ() + _snowman * (double)i / 4.0, -d, -d2 + 0.2, -_snowman);
            }
        }
        double d3 = this.getX() + d;
        _snowman = this.getY() + d2;
        _snowman = this.getZ() + _snowman;
        float _snowman6 = MathHelper.sqrt(PersistentProjectileEntity.squaredHorizontalLength(_snowman2));
        this.yaw = bl ? (float)(MathHelper.atan2(-d, -_snowman) * 57.2957763671875) : (float)(MathHelper.atan2(d, _snowman) * 57.2957763671875);
        this.pitch = (float)(MathHelper.atan2(d2, _snowman6) * 57.2957763671875);
        this.pitch = PersistentProjectileEntity.updateRotation(this.prevPitch, this.pitch);
        this.yaw = PersistentProjectileEntity.updateRotation(this.prevYaw, this.yaw);
        float _snowman7 = 0.99f;
        float _snowman8 = 0.05f;
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; ++i) {
                float f = 0.25f;
                this.world.addParticle(ParticleTypes.BUBBLE, d3 - d * 0.25, _snowman - d2 * 0.25, _snowman - _snowman * 0.25, d, d2, _snowman);
            }
            _snowman7 = this.getDragInWater();
        }
        this.setVelocity(_snowman2.multiply(_snowman7));
        if (!this.hasNoGravity() && !bl) {
            Vec3d vec3d2 = this.getVelocity();
            this.setVelocity(vec3d2.x, vec3d2.y - (double)0.05f, vec3d2.z);
        }
        this.updatePosition(d3, _snowman, _snowman);
        this.checkBlockCollision();
    }

    private boolean method_26351() {
        return this.inGround && this.world.isSpaceEmpty(new Box(this.getPos(), this.getPos()).expand(0.06));
    }

    private void method_26352() {
        this.inGround = false;
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.multiply(this.random.nextFloat() * 0.2f, this.random.nextFloat() * 0.2f, this.random.nextFloat() * 0.2f));
        this.life = 0;
    }

    @Override
    public void move(MovementType type, Vec3d movement) {
        super.move(type, movement);
        if (type != MovementType.SELF && this.method_26351()) {
            this.method_26352();
        }
    }

    protected void age() {
        ++this.life;
        if (this.life >= 1200) {
            this.remove();
        }
    }

    private void clearPiercingStatus() {
        if (this.piercingKilledEntities != null) {
            this.piercingKilledEntities.clear();
        }
        if (this.piercedEntities != null) {
            this.piercedEntities.clear();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        DamageSource _snowman4;
        Entity entity;
        super.onEntityHit(entityHitResult);
        Entity entity2 = entityHitResult.getEntity();
        float _snowman2 = (float)this.getVelocity().length();
        int _snowman3 = MathHelper.ceil(MathHelper.clamp((double)_snowman2 * this.damage, 0.0, 2.147483647E9));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }
            if (this.piercingKilledEntities == null) {
                this.piercingKilledEntities = Lists.newArrayListWithCapacity((int)5);
            }
            if (this.piercedEntities.size() < this.getPierceLevel() + 1) {
                this.piercedEntities.add(entity2.getEntityId());
            } else {
                this.remove();
                return;
            }
        }
        if (this.isCritical()) {
            long l = this.random.nextInt(_snowman3 / 2 + 2);
            _snowman3 = (int)Math.min(l + (long)_snowman3, Integer.MAX_VALUE);
        }
        if ((entity = this.getOwner()) == null) {
            _snowman4 = DamageSource.arrow(this, this);
        } else {
            _snowman4 = DamageSource.arrow(this, entity);
            if (entity instanceof LivingEntity) {
                ((LivingEntity)entity).onAttacking(entity2);
            }
        }
        boolean bl = entity2.getType() == EntityType.ENDERMAN;
        int _snowman5 = entity2.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity2.setOnFireFor(5);
        }
        if (entity2.damage(_snowman4, _snowman3)) {
            if (bl) {
                return;
            }
            if (entity2 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity2;
                if (!this.world.isClient && this.getPierceLevel() <= 0) {
                    livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
                }
                if (this.punch > 0 && ((Vec3d)(object = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply((double)this.punch * 0.6))).lengthSquared() > 0.0) {
                    livingEntity.addVelocity(((Vec3d)object).x, 0.1, ((Vec3d)object).z);
                }
                if (!this.world.isClient && entity instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)entity, livingEntity);
                }
                this.onHit(livingEntity);
                if (entity != null && livingEntity != entity && livingEntity instanceof PlayerEntity && entity instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0f));
                }
                if (!entity2.isAlive() && this.piercingKilledEntities != null) {
                    this.piercingKilledEntities.add(livingEntity);
                }
                if (!this.world.isClient && entity instanceof ServerPlayerEntity) {
                    Object object = (ServerPlayerEntity)entity;
                    if (this.piercingKilledEntities != null && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger((ServerPlayerEntity)object, this.piercingKilledEntities);
                    } else if (!entity2.isAlive() && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger((ServerPlayerEntity)object, Arrays.asList(entity2));
                    }
                }
            }
            this.playSound(this.sound, 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity2.setFireTicks(_snowman5);
            this.setVelocity(this.getVelocity().multiply(-0.1));
            this.yaw += 180.0f;
            this.prevYaw += 180.0f;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
                if (this.pickupType == PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1f);
                }
                this.remove();
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.inBlockState = this.world.getBlockState(blockHitResult.getBlockPos());
        super.onBlockHit(blockHitResult);
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        this.setVelocity(vec3d);
        _snowman = vec3d.normalize().multiply(0.05f);
        this.setPos(this.getX() - _snowman.x, this.getY() - _snowman.y, this.getZ() - _snowman.z);
        this.playSound(this.getSound(), 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
        this.inGround = true;
        this.shake = 7;
        this.setCritical(false);
        this.setPierceLevel((byte)0);
        this.setSound(SoundEvents.ENTITY_ARROW_HIT);
        this.setShotFromCrossbow(false);
        this.clearPiercingStatus();
    }

    protected SoundEvent getHitSound() {
        return SoundEvents.ENTITY_ARROW_HIT;
    }

    protected final SoundEvent getSound() {
        return this.sound;
    }

    protected void onHit(LivingEntity target) {
    }

    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return ProjectileUtil.getEntityCollision(this.world, this, currentPosition, nextPosition, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), this::method_26958);
    }

    @Override
    protected boolean method_26958(Entity entity) {
        return super.method_26958(entity) && (this.piercedEntities == null || !this.piercedEntities.contains(entity.getEntityId()));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putShort("life", (short)this.life);
        if (this.inBlockState != null) {
            tag.put("inBlockState", NbtHelper.fromBlockState(this.inBlockState));
        }
        tag.putByte("shake", (byte)this.shake);
        tag.putBoolean("inGround", this.inGround);
        tag.putByte("pickup", (byte)this.pickupType.ordinal());
        tag.putDouble("damage", this.damage);
        tag.putBoolean("crit", this.isCritical());
        tag.putByte("PierceLevel", this.getPierceLevel());
        tag.putString("SoundEvent", Registry.SOUND_EVENT.getId(this.sound).toString());
        tag.putBoolean("ShotFromCrossbow", this.isShotFromCrossbow());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.life = tag.getShort("life");
        if (tag.contains("inBlockState", 10)) {
            this.inBlockState = NbtHelper.toBlockState(tag.getCompound("inBlockState"));
        }
        this.shake = tag.getByte("shake") & 0xFF;
        this.inGround = tag.getBoolean("inGround");
        if (tag.contains("damage", 99)) {
            this.damage = tag.getDouble("damage");
        }
        if (tag.contains("pickup", 99)) {
            this.pickupType = PickupPermission.fromOrdinal(tag.getByte("pickup"));
        } else if (tag.contains("player", 99)) {
            this.pickupType = tag.getBoolean("player") ? PickupPermission.ALLOWED : PickupPermission.DISALLOWED;
        }
        this.setCritical(tag.getBoolean("crit"));
        this.setPierceLevel(tag.getByte("PierceLevel"));
        if (tag.contains("SoundEvent", 8)) {
            this.sound = Registry.SOUND_EVENT.getOrEmpty(new Identifier(tag.getString("SoundEvent"))).orElse(this.getHitSound());
        }
        this.setShotFromCrossbow(tag.getBoolean("ShotFromCrossbow"));
    }

    @Override
    public void setOwner(@Nullable Entity entity) {
        super.setOwner(entity);
        if (entity instanceof PlayerEntity) {
            this.pickupType = ((PlayerEntity)entity).abilities.creativeMode ? PickupPermission.CREATIVE_ONLY : PickupPermission.ALLOWED;
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        boolean bl;
        if (this.world.isClient || !this.inGround && !this.isNoClip() || this.shake > 0) {
            return;
        }
        boolean bl2 = bl = this.pickupType == PickupPermission.ALLOWED || this.pickupType == PickupPermission.CREATIVE_ONLY && player.abilities.creativeMode || this.isNoClip() && this.getOwner().getUuid() == player.getUuid();
        if (this.pickupType == PickupPermission.ALLOWED && !player.inventory.insertStack(this.asItemStack())) {
            bl = false;
        }
        if (bl) {
            player.sendPickup(this, 1);
            this.remove();
        }
    }

    protected abstract ItemStack asItemStack();

    @Override
    protected boolean canClimb() {
        return false;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setPunch(int punch) {
        this.punch = punch;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.13f;
    }

    public void setCritical(boolean critical) {
        this.setProjectileFlag(1, critical);
    }

    public void setPierceLevel(byte level) {
        this.dataTracker.set(PIERCE_LEVEL, level);
    }

    private void setProjectileFlag(int index, boolean flag) {
        byte by = this.dataTracker.get(PROJECTILE_FLAGS);
        if (flag) {
            this.dataTracker.set(PROJECTILE_FLAGS, (byte)(by | index));
        } else {
            this.dataTracker.set(PROJECTILE_FLAGS, (byte)(by & ~index));
        }
    }

    public boolean isCritical() {
        byte by = this.dataTracker.get(PROJECTILE_FLAGS);
        return (by & 1) != 0;
    }

    public boolean isShotFromCrossbow() {
        byte by = this.dataTracker.get(PROJECTILE_FLAGS);
        return (by & 4) != 0;
    }

    public byte getPierceLevel() {
        return this.dataTracker.get(PIERCE_LEVEL);
    }

    public void applyEnchantmentEffects(LivingEntity entity, float damageModifier) {
        int n = EnchantmentHelper.getEquipmentLevel(Enchantments.POWER, entity);
        _snowman = EnchantmentHelper.getEquipmentLevel(Enchantments.PUNCH, entity);
        this.setDamage((double)(damageModifier * 2.0f) + (this.random.nextGaussian() * 0.25 + (double)((float)this.world.getDifficulty().getId() * 0.11f)));
        if (n > 0) {
            this.setDamage(this.getDamage() + (double)n * 0.5 + 0.5);
        }
        if (_snowman > 0) {
            this.setPunch(_snowman);
        }
        if (EnchantmentHelper.getEquipmentLevel(Enchantments.FLAME, entity) > 0) {
            this.setOnFireFor(100);
        }
    }

    protected float getDragInWater() {
        return 0.6f;
    }

    public void setNoClip(boolean noClip) {
        this.noClip = noClip;
        this.setProjectileFlag(2, noClip);
    }

    public boolean isNoClip() {
        if (!this.world.isClient) {
            return this.noClip;
        }
        return (this.dataTracker.get(PROJECTILE_FLAGS) & 2) != 0;
    }

    public void setShotFromCrossbow(boolean shotFromCrossbow) {
        this.setProjectileFlag(4, shotFromCrossbow);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getEntityId());
    }

    public static enum PickupPermission {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY;


        public static PickupPermission fromOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal > PickupPermission.values().length) {
                ordinal = 0;
            }
            return PickupPermission.values()[ordinal];
        }
    }
}

