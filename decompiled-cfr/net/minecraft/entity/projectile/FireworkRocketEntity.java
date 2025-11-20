/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile;

import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class FireworkRocketEntity
extends ProjectileEntity
implements FlyingItemEntity {
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(FireworkRocketEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<OptionalInt> SHOOTER_ENTITY_ID = DataTracker.registerData(FireworkRocketEntity.class, TrackedDataHandlerRegistry.FIREWORK_DATA);
    private static final TrackedData<Boolean> SHOT_AT_ANGLE = DataTracker.registerData(FireworkRocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int life;
    private int lifeTime;
    private LivingEntity shooter;

    public FireworkRocketEntity(EntityType<? extends FireworkRocketEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    public FireworkRocketEntity(World world, double x, double y, double z, ItemStack stack) {
        super((EntityType<? extends ProjectileEntity>)EntityType.FIREWORK_ROCKET, world);
        this.life = 0;
        this.updatePosition(x, y, z);
        int n = 1;
        if (!stack.isEmpty() && stack.hasTag()) {
            this.dataTracker.set(ITEM, stack.copy());
            n += stack.getOrCreateSubTag("Fireworks").getByte("Flight");
        }
        this.setVelocity(this.random.nextGaussian() * 0.001, 0.05, this.random.nextGaussian() * 0.001);
        this.lifeTime = 10 * n + this.random.nextInt(6) + this.random.nextInt(7);
    }

    public FireworkRocketEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
        this(world, x, y, z, stack);
        this.setOwner(entity);
    }

    public FireworkRocketEntity(World world, ItemStack stack, LivingEntity shooter) {
        this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
        this.dataTracker.set(SHOOTER_ENTITY_ID, OptionalInt.of(shooter.getEntityId()));
        this.shooter = shooter;
    }

    public FireworkRocketEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
        this(world, x, y, z, stack);
        this.dataTracker.set(SHOT_AT_ANGLE, shotAtAngle);
    }

    public FireworkRocketEntity(World world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
        this(world, stack, x, y, z, shotAtAngle);
        this.setOwner(entity);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(ITEM, ItemStack.EMPTY);
        this.dataTracker.startTracking(SHOOTER_ENTITY_ID, OptionalInt.empty());
        this.dataTracker.startTracking(SHOT_AT_ANGLE, false);
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 4096.0 && !this.wasShotByEntity();
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return super.shouldRender(cameraX, cameraY, cameraZ) && !this.wasShotByEntity();
    }

    @Override
    public void tick() {
        Object object;
        super.tick();
        if (this.wasShotByEntity()) {
            if (this.shooter == null) {
                this.dataTracker.get(SHOOTER_ENTITY_ID).ifPresent(n -> {
                    Entity entity = this.world.getEntityById(n);
                    if (entity instanceof LivingEntity) {
                        this.shooter = (LivingEntity)entity;
                    }
                });
            }
            if (this.shooter != null) {
                if (this.shooter.isFallFlying()) {
                    object = this.shooter.getRotationVector();
                    double _snowman2 = 1.5;
                    double _snowman3 = 0.1;
                    Vec3d _snowman4 = this.shooter.getVelocity();
                    this.shooter.setVelocity(_snowman4.add(((Vec3d)object).x * 0.1 + (((Vec3d)object).x * 1.5 - _snowman4.x) * 0.5, ((Vec3d)object).y * 0.1 + (((Vec3d)object).y * 1.5 - _snowman4.y) * 0.5, ((Vec3d)object).z * 0.1 + (((Vec3d)object).z * 1.5 - _snowman4.z) * 0.5));
                }
                this.updatePosition(this.shooter.getX(), this.shooter.getY(), this.shooter.getZ());
                this.setVelocity(this.shooter.getVelocity());
            }
        } else {
            if (!this.wasShotAtAngle()) {
                double d = this.horizontalCollision ? 1.0 : 1.15;
                this.setVelocity(this.getVelocity().multiply(d, 1.0, d).add(0.0, 0.04, 0.0));
            }
            object = this.getVelocity();
            this.move(MovementType.SELF, (Vec3d)object);
            this.setVelocity((Vec3d)object);
        }
        object = ProjectileUtil.getCollision(this, this::method_26958);
        if (!this.noClip) {
            this.onCollision((HitResult)object);
            this.velocityDirty = true;
        }
        this.method_26962();
        if (this.life == 0 && !this.isSilent()) {
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 3.0f, 1.0f);
        }
        ++this.life;
        if (this.world.isClient && this.life % 2 < 2) {
            this.world.addParticle(ParticleTypes.FIREWORK, this.getX(), this.getY() - 0.3, this.getZ(), this.random.nextGaussian() * 0.05, -this.getVelocity().y * 0.5, this.random.nextGaussian() * 0.05);
        }
        if (!this.world.isClient && this.life > this.lifeTime) {
            this.explodeAndRemove();
        }
    }

    private void explodeAndRemove() {
        this.world.sendEntityStatus(this, (byte)17);
        this.explode();
        this.remove();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.world.isClient) {
            return;
        }
        this.explodeAndRemove();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos blockPos = new BlockPos(blockHitResult.getBlockPos());
        this.world.getBlockState(blockPos).onEntityCollision(this.world, blockPos, this);
        if (!this.world.isClient() && this.hasExplosionEffects()) {
            this.explodeAndRemove();
        }
        super.onBlockHit(blockHitResult);
    }

    private boolean hasExplosionEffects() {
        ItemStack itemStack = this.dataTracker.get(ITEM);
        CompoundTag _snowman2 = itemStack.isEmpty() ? null : itemStack.getSubTag("Fireworks");
        ListTag _snowman3 = _snowman2 != null ? _snowman2.getList("Explosions", 10) : null;
        return _snowman3 != null && !_snowman3.isEmpty();
    }

    private void explode() {
        float f = 0.0f;
        ItemStack _snowman2 = this.dataTracker.get(ITEM);
        CompoundTag _snowman3 = _snowman2.isEmpty() ? null : _snowman2.getSubTag("Fireworks");
        ListTag listTag = _snowman = _snowman3 != null ? _snowman3.getList("Explosions", 10) : null;
        if (_snowman != null && !_snowman.isEmpty()) {
            f = 5.0f + (float)(_snowman.size() * 2);
        }
        if (f > 0.0f) {
            if (this.shooter != null) {
                this.shooter.damage(DamageSource.firework(this, this.getOwner()), 5.0f + (float)(_snowman.size() * 2));
            }
            double d = 5.0;
            Vec3d _snowman4 = this.getPos();
            List<LivingEntity> _snowman5 = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
            for (LivingEntity livingEntity : _snowman5) {
                if (livingEntity == this.shooter || this.squaredDistanceTo(livingEntity) > 25.0) continue;
                boolean _snowman7 = false;
                for (int i = 0; i < 2; ++i) {
                    Vec3d vec3d = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5 * (double)i), livingEntity.getZ());
                    BlockHitResult _snowman6 = this.world.raycast(new RaycastContext(_snowman4, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
                    if (((HitResult)_snowman6).getType() != HitResult.Type.MISS) continue;
                    _snowman7 = true;
                    break;
                }
                if (!_snowman7) continue;
                float f2 = f * (float)Math.sqrt((5.0 - (double)this.distanceTo(livingEntity)) / 5.0);
                livingEntity.damage(DamageSource.firework(this, this.getOwner()), f2);
            }
        }
    }

    private boolean wasShotByEntity() {
        return this.dataTracker.get(SHOOTER_ENTITY_ID).isPresent();
    }

    public boolean wasShotAtAngle() {
        return this.dataTracker.get(SHOT_AT_ANGLE);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 17 && this.world.isClient) {
            if (!this.hasExplosionEffects()) {
                for (int i = 0; i < this.random.nextInt(3) + 2; ++i) {
                    this.world.addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05, 0.005, this.random.nextGaussian() * 0.05);
                }
            } else {
                ItemStack itemStack = this.dataTracker.get(ITEM);
                CompoundTag _snowman2 = itemStack.isEmpty() ? null : itemStack.getSubTag("Fireworks");
                Vec3d _snowman3 = this.getVelocity();
                this.world.addFireworkParticle(this.getX(), this.getY(), this.getZ(), _snowman3.x, _snowman3.y, _snowman3.z, _snowman2);
            }
        }
        super.handleStatus(status);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Life", this.life);
        tag.putInt("LifeTime", this.lifeTime);
        ItemStack itemStack = this.dataTracker.get(ITEM);
        if (!itemStack.isEmpty()) {
            tag.put("FireworksItem", itemStack.toTag(new CompoundTag()));
        }
        tag.putBoolean("ShotAtAngle", this.dataTracker.get(SHOT_AT_ANGLE));
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.life = tag.getInt("Life");
        this.lifeTime = tag.getInt("LifeTime");
        ItemStack itemStack = ItemStack.fromTag(tag.getCompound("FireworksItem"));
        if (!itemStack.isEmpty()) {
            this.dataTracker.set(ITEM, itemStack);
        }
        if (tag.contains("ShotAtAngle")) {
            this.dataTracker.set(SHOT_AT_ANGLE, tag.getBoolean("ShotAtAngle"));
        }
    }

    @Override
    public ItemStack getStack() {
        ItemStack itemStack = this.dataTracker.get(ITEM);
        return itemStack.isEmpty() ? new ItemStack(Items.FIREWORK_ROCKET) : itemStack;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}

