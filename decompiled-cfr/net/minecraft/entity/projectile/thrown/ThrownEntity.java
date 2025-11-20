/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.projectile.thrown;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ThrownEntity
extends ProjectileEntity {
    protected ThrownEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    protected ThrownEntity(EntityType<? extends ThrownEntity> type, double x, double y, double z, World world) {
        this(type, world);
        this.updatePosition(x, y, z);
    }

    protected ThrownEntity(EntityType<? extends ThrownEntity> type, LivingEntity owner, World world) {
        this(type, owner.getX(), owner.getEyeY() - (double)0.1f, owner.getZ(), world);
        this.setOwner(owner);
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
        if (Double.isNaN(d)) {
            d = 4.0;
        }
        return distance < (d *= 64.0) * d;
    }

    @Override
    public void tick() {
        float f;
        Object _snowman4;
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::method_26958);
        boolean _snowman2 = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            _snowman4 = ((BlockHitResult)hitResult).getBlockPos();
            BlockState _snowman3 = this.world.getBlockState((BlockPos)_snowman4);
            if (_snowman3.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal((BlockPos)_snowman4);
                _snowman2 = true;
            } else if (_snowman3.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.world.getBlockEntity((BlockPos)_snowman4);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.method_30276(this)) {
                    ((EndGatewayBlockEntity)blockEntity).tryTeleportingEntity(this);
                }
                _snowman2 = true;
            }
        }
        if (hitResult.getType() != HitResult.Type.MISS && !_snowman2) {
            this.onCollision(hitResult);
        }
        this.checkBlockCollision();
        _snowman4 = this.getVelocity();
        double _snowman5 = this.getX() + ((Vec3d)_snowman4).x;
        double _snowman6 = this.getY() + ((Vec3d)_snowman4).y;
        double _snowman7 = this.getZ() + ((Vec3d)_snowman4).z;
        this.method_26962();
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; ++i) {
                float f2 = 0.25f;
                this.world.addParticle(ParticleTypes.BUBBLE, _snowman5 - ((Vec3d)_snowman4).x * 0.25, _snowman6 - ((Vec3d)_snowman4).y * 0.25, _snowman7 - ((Vec3d)_snowman4).z * 0.25, ((Vec3d)_snowman4).x, ((Vec3d)_snowman4).y, ((Vec3d)_snowman4).z);
            }
            f = 0.8f;
        } else {
            f = 0.99f;
        }
        this.setVelocity(((Vec3d)_snowman4).multiply(f));
        if (!this.hasNoGravity()) {
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x, vec3d.y - (double)this.getGravity(), vec3d.z);
        }
        this.updatePosition(_snowman5, _snowman6, _snowman7);
    }

    protected float getGravity() {
        return 0.03f;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}

