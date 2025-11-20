/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ShulkerBulletEntity
extends ProjectileEntity {
    private Entity target;
    @Nullable
    private Direction direction;
    private int stepCount;
    private double targetX;
    private double targetY;
    private double targetZ;
    @Nullable
    private UUID targetUuid;

    public ShulkerBulletEntity(EntityType<? extends ShulkerBulletEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
        this.noClip = true;
    }

    public ShulkerBulletEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this((EntityType<? extends ShulkerBulletEntity>)EntityType.SHULKER_BULLET, world);
        this.refreshPositionAndAngles(x, y, z, this.yaw, this.pitch);
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    public ShulkerBulletEntity(World world, LivingEntity owner, Entity target, Direction.Axis axis) {
        this((EntityType<? extends ShulkerBulletEntity>)EntityType.SHULKER_BULLET, world);
        this.setOwner(owner);
        BlockPos blockPos = owner.getBlockPos();
        double _snowman2 = (double)blockPos.getX() + 0.5;
        double _snowman3 = (double)blockPos.getY() + 0.5;
        double _snowman4 = (double)blockPos.getZ() + 0.5;
        this.refreshPositionAndAngles(_snowman2, _snowman3, _snowman4, this.yaw, this.pitch);
        this.target = target;
        this.direction = Direction.UP;
        this.method_7486(axis);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        if (this.target != null) {
            tag.putUuid("Target", this.target.getUuid());
        }
        if (this.direction != null) {
            tag.putInt("Dir", this.direction.getId());
        }
        tag.putInt("Steps", this.stepCount);
        tag.putDouble("TXD", this.targetX);
        tag.putDouble("TYD", this.targetY);
        tag.putDouble("TZD", this.targetZ);
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.stepCount = tag.getInt("Steps");
        this.targetX = tag.getDouble("TXD");
        this.targetY = tag.getDouble("TYD");
        this.targetZ = tag.getDouble("TZD");
        if (tag.contains("Dir", 99)) {
            this.direction = Direction.byId(tag.getInt("Dir"));
        }
        if (tag.containsUuid("Target")) {
            this.targetUuid = tag.getUuid("Target");
        }
    }

    @Override
    protected void initDataTracker() {
    }

    private void setDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    private void method_7486(@Nullable Direction.Axis axis) {
        double d;
        BlockPos _snowman2;
        double d2 = 0.5;
        if (this.target == null) {
            _snowman2 = this.getBlockPos().down();
        } else {
            d2 = (double)this.target.getHeight() * 0.5;
            _snowman2 = new BlockPos(this.target.getX(), this.target.getY() + d2, this.target.getZ());
        }
        d = (double)_snowman2.getX() + 0.5;
        _snowman = (double)_snowman2.getY() + d2;
        _snowman = (double)_snowman2.getZ() + 0.5;
        Direction _snowman4 = null;
        if (!_snowman2.isWithinDistance(this.getPos(), 2.0)) {
            BlockPos blockPos = this.getBlockPos();
            ArrayList _snowman3 = Lists.newArrayList();
            if (axis != Direction.Axis.X) {
                if (blockPos.getX() < _snowman2.getX() && this.world.isAir(blockPos.east())) {
                    _snowman3.add(Direction.EAST);
                } else if (blockPos.getX() > _snowman2.getX() && this.world.isAir(blockPos.west())) {
                    _snowman3.add(Direction.WEST);
                }
            }
            if (axis != Direction.Axis.Y) {
                if (blockPos.getY() < _snowman2.getY() && this.world.isAir(blockPos.up())) {
                    _snowman3.add(Direction.UP);
                } else if (blockPos.getY() > _snowman2.getY() && this.world.isAir(blockPos.down())) {
                    _snowman3.add(Direction.DOWN);
                }
            }
            if (axis != Direction.Axis.Z) {
                if (blockPos.getZ() < _snowman2.getZ() && this.world.isAir(blockPos.south())) {
                    _snowman3.add(Direction.SOUTH);
                } else if (blockPos.getZ() > _snowman2.getZ() && this.world.isAir(blockPos.north())) {
                    _snowman3.add(Direction.NORTH);
                }
            }
            _snowman4 = Direction.random(this.random);
            if (_snowman3.isEmpty()) {
                for (int i = 5; !this.world.isAir(blockPos.offset(_snowman4)) && i > 0; --i) {
                    _snowman4 = Direction.random(this.random);
                }
            } else {
                _snowman4 = (Direction)_snowman3.get(this.random.nextInt(_snowman3.size()));
            }
            d = this.getX() + (double)_snowman4.getOffsetX();
            _snowman = this.getY() + (double)_snowman4.getOffsetY();
            _snowman = this.getZ() + (double)_snowman4.getOffsetZ();
        }
        this.setDirection(_snowman4);
        _snowman = d - this.getX();
        _snowman = _snowman - this.getY();
        _snowman = _snowman - this.getZ();
        _snowman = MathHelper.sqrt(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman);
        if (_snowman == 0.0) {
            this.targetX = 0.0;
            this.targetY = 0.0;
            this.targetZ = 0.0;
        } else {
            this.targetX = _snowman / _snowman * 0.15;
            this.targetY = _snowman / _snowman * 0.15;
            this.targetZ = _snowman / _snowman * 0.15;
        }
        this.velocityDirty = true;
        this.stepCount = 10 + this.random.nextInt(5) * 10;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
    }

    @Override
    public void tick() {
        Object object;
        super.tick();
        if (!this.world.isClient) {
            if (this.target == null && this.targetUuid != null) {
                this.target = ((ServerWorld)this.world).getEntity(this.targetUuid);
                if (this.target == null) {
                    this.targetUuid = null;
                }
            }
            if (!(this.target == null || !this.target.isAlive() || this.target instanceof PlayerEntity && ((PlayerEntity)this.target).isSpectator())) {
                this.targetX = MathHelper.clamp(this.targetX * 1.025, -1.0, 1.0);
                this.targetY = MathHelper.clamp(this.targetY * 1.025, -1.0, 1.0);
                this.targetZ = MathHelper.clamp(this.targetZ * 1.025, -1.0, 1.0);
                object = this.getVelocity();
                this.setVelocity(((Vec3d)object).add((this.targetX - ((Vec3d)object).x) * 0.2, (this.targetY - ((Vec3d)object).y) * 0.2, (this.targetZ - ((Vec3d)object).z) * 0.2));
            } else if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
            }
            object = ProjectileUtil.getCollision(this, this::method_26958);
            if (((HitResult)object).getType() != HitResult.Type.MISS) {
                this.onCollision((HitResult)object);
            }
        }
        this.checkBlockCollision();
        object = this.getVelocity();
        this.updatePosition(this.getX() + ((Vec3d)object).x, this.getY() + ((Vec3d)object).y, this.getZ() + ((Vec3d)object).z);
        ProjectileUtil.method_7484(this, 0.5f);
        if (this.world.isClient) {
            this.world.addParticle(ParticleTypes.END_ROD, this.getX() - ((Vec3d)object).x, this.getY() - ((Vec3d)object).y + 0.15, this.getZ() - ((Vec3d)object).z, 0.0, 0.0, 0.0);
        } else if (this.target != null && !this.target.removed) {
            if (this.stepCount > 0) {
                --this.stepCount;
                if (this.stepCount == 0) {
                    this.method_7486(this.direction == null ? null : this.direction.getAxis());
                }
            }
            if (this.direction != null) {
                BlockPos blockPos = this.getBlockPos();
                Direction.Axis _snowman2 = this.direction.getAxis();
                if (this.world.isTopSolid(blockPos.offset(this.direction), this)) {
                    this.method_7486(_snowman2);
                } else {
                    _snowman = this.target.getBlockPos();
                    if (_snowman2 == Direction.Axis.X && blockPos.getX() == _snowman.getX() || _snowman2 == Direction.Axis.Z && blockPos.getZ() == _snowman.getZ() || _snowman2 == Direction.Axis.Y && blockPos.getY() == _snowman.getY()) {
                        this.method_7486(_snowman2);
                    }
                }
            }
        }
    }

    @Override
    protected boolean method_26958(Entity entity) {
        return super.method_26958(entity) && !entity.noClip;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 16384.0;
    }

    @Override
    public float getBrightnessAtEyes() {
        return 1.0f;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        _snowman = this.getOwner();
        LivingEntity _snowman2 = _snowman instanceof LivingEntity ? (LivingEntity)_snowman : null;
        boolean _snowman3 = entity.damage(DamageSource.mobProjectile(this, _snowman2).setProjectile(), 4.0f);
        if (_snowman3) {
            this.dealDamage(_snowman2, entity);
            if (entity instanceof LivingEntity) {
                ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200));
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        ((ServerWorld)this.world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
        this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0f, 1.0f);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.remove();
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.world.isClient) {
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HURT, 1.0f, 1.0f);
            ((ServerWorld)this.world).spawnParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2, 0.2, 0.2, 0.0);
            this.remove();
        }
        return true;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}

