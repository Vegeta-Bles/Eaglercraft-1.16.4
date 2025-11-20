/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EyeOfEnderEntity
extends Entity
implements FlyingItemEntity {
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(EyeOfEnderEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private double targetX;
    private double targetY;
    private double targetZ;
    private int lifespan;
    private boolean dropsItem;

    public EyeOfEnderEntity(EntityType<? extends EyeOfEnderEntity> entityType, World world) {
        super(entityType, world);
    }

    public EyeOfEnderEntity(World world, double x, double y, double z) {
        this((EntityType<? extends EyeOfEnderEntity>)EntityType.EYE_OF_ENDER, world);
        this.lifespan = 0;
        this.updatePosition(x, y, z);
    }

    public void setItem(ItemStack stack2) {
        if (stack2.getItem() != Items.ENDER_EYE || stack2.hasTag()) {
            this.getDataTracker().set(ITEM, Util.make(stack2.copy(), stack -> stack.setCount(1)));
        }
    }

    private ItemStack getTrackedItem() {
        return this.getDataTracker().get(ITEM);
    }

    @Override
    public ItemStack getStack() {
        ItemStack itemStack = this.getTrackedItem();
        return itemStack.isEmpty() ? new ItemStack(Items.ENDER_EYE) : itemStack;
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(ITEM, ItemStack.EMPTY);
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
        if (Double.isNaN(d)) {
            d = 4.0;
        }
        return distance < (d *= 64.0) * d;
    }

    public void initTargetPos(BlockPos pos) {
        double d = pos.getX();
        int _snowman2 = pos.getY();
        _snowman = pos.getZ();
        _snowman = d - this.getX();
        float _snowman3 = MathHelper.sqrt(_snowman * _snowman + (_snowman = _snowman - this.getZ()) * _snowman);
        if (_snowman3 > 12.0f) {
            this.targetX = this.getX() + _snowman / (double)_snowman3 * 12.0;
            this.targetZ = this.getZ() + _snowman / (double)_snowman3 * 12.0;
            this.targetY = this.getY() + 8.0;
        } else {
            this.targetX = d;
            this.targetY = _snowman2;
            this.targetZ = _snowman;
        }
        this.lifespan = 0;
        this.dropsItem = this.random.nextInt(5) > 0;
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        this.setVelocity(x, y, z);
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.yaw = (float)(MathHelper.atan2(x, z) * 57.2957763671875);
            this.pitch = (float)(MathHelper.atan2(y, f) * 57.2957763671875);
            this.prevYaw = this.yaw;
            this.prevPitch = this.pitch;
        }
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d _snowman9 = this.getVelocity();
        double _snowman2 = this.getX() + _snowman9.x;
        double _snowman3 = this.getY() + _snowman9.y;
        double _snowman4 = this.getZ() + _snowman9.z;
        float _snowman5 = MathHelper.sqrt(EyeOfEnderEntity.squaredHorizontalLength(_snowman9));
        this.pitch = ProjectileEntity.updateRotation(this.prevPitch, (float)(MathHelper.atan2(_snowman9.y, _snowman5) * 57.2957763671875));
        this.yaw = ProjectileEntity.updateRotation(this.prevYaw, (float)(MathHelper.atan2(_snowman9.x, _snowman9.z) * 57.2957763671875));
        if (!this.world.isClient) {
            double d = this.targetX - _snowman2;
            _snowman = this.targetZ - _snowman4;
            float _snowman6 = (float)Math.sqrt(d * d + _snowman * _snowman);
            float _snowman7 = (float)MathHelper.atan2(_snowman, d);
            _snowman = MathHelper.lerp(0.0025, (double)_snowman5, (double)_snowman6);
            _snowman = _snowman9.y;
            if (_snowman6 < 1.0f) {
                _snowman *= 0.8;
                _snowman *= 0.8;
            }
            int _snowman8 = this.getY() < this.targetY ? 1 : -1;
            _snowman9 = new Vec3d(Math.cos(_snowman7) * _snowman, _snowman + ((double)_snowman8 - _snowman) * (double)0.015f, Math.sin(_snowman7) * _snowman);
            this.setVelocity(_snowman9);
        }
        float f = 0.25f;
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; ++i) {
                this.world.addParticle(ParticleTypes.BUBBLE, _snowman2 - _snowman9.x * 0.25, _snowman3 - _snowman9.y * 0.25, _snowman4 - _snowman9.z * 0.25, _snowman9.x, _snowman9.y, _snowman9.z);
            }
        } else {
            this.world.addParticle(ParticleTypes.PORTAL, _snowman2 - _snowman9.x * 0.25 + this.random.nextDouble() * 0.6 - 0.3, _snowman3 - _snowman9.y * 0.25 - 0.5, _snowman4 - _snowman9.z * 0.25 + this.random.nextDouble() * 0.6 - 0.3, _snowman9.x, _snowman9.y, _snowman9.z);
        }
        if (!this.world.isClient) {
            this.updatePosition(_snowman2, _snowman3, _snowman4);
            ++this.lifespan;
            if (this.lifespan > 80 && !this.world.isClient) {
                this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0f, 1.0f);
                this.remove();
                if (this.dropsItem) {
                    this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), this.getStack()));
                } else {
                    this.world.syncWorldEvent(2003, this.getBlockPos(), 0);
                }
            }
        } else {
            this.setPos(_snowman2, _snowman3, _snowman4);
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        ItemStack itemStack = this.getTrackedItem();
        if (!itemStack.isEmpty()) {
            tag.put("Item", itemStack.toTag(new CompoundTag()));
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        ItemStack itemStack = ItemStack.fromTag(tag.getCompound("Item"));
        this.setItem(itemStack);
    }

    @Override
    public float getBrightnessAtEyes() {
        return 1.0f;
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

