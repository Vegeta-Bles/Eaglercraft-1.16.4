/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ProjectileEntity
extends Entity {
    private UUID ownerUuid;
    private int ownerEntityId;
    private boolean leftOwner;

    ProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUuid = entity.getUuid();
            this.ownerEntityId = entity.getEntityId();
        }
    }

    @Nullable
    public Entity getOwner() {
        if (this.ownerUuid != null && this.world instanceof ServerWorld) {
            return ((ServerWorld)this.world).getEntity(this.ownerUuid);
        }
        if (this.ownerEntityId != 0) {
            return this.world.getEntityById(this.ownerEntityId);
        }
        return null;
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        if (this.ownerUuid != null) {
            tag.putUuid("Owner", this.ownerUuid);
        }
        if (this.leftOwner) {
            tag.putBoolean("LeftOwner", true);
        }
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        if (tag.containsUuid("Owner")) {
            this.ownerUuid = tag.getUuid("Owner");
        }
        this.leftOwner = tag.getBoolean("LeftOwner");
    }

    @Override
    public void tick() {
        if (!this.leftOwner) {
            this.leftOwner = this.method_26961();
        }
        super.tick();
    }

    private boolean method_26961() {
        Entity entity2 = this.getOwner();
        if (entity2 != null) {
            for (Entity entity3 : this.world.getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), entity -> !entity.isSpectator() && entity.collides())) {
                if (entity3.getRootVehicle() != entity2.getRootVehicle()) continue;
                return false;
            }
        }
        return true;
    }

    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        Vec3d vec3d = new Vec3d(x, y, z).normalize().add(this.random.nextGaussian() * (double)0.0075f * (double)divergence, this.random.nextGaussian() * (double)0.0075f * (double)divergence, this.random.nextGaussian() * (double)0.0075f * (double)divergence).multiply(speed);
        this.setVelocity(vec3d);
        float _snowman2 = MathHelper.sqrt(ProjectileEntity.squaredHorizontalLength(vec3d));
        this.yaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875);
        this.pitch = (float)(MathHelper.atan2(vec3d.y, _snowman2) * 57.2957763671875);
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
    }

    public void setProperties(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ) {
        float f = -MathHelper.sin(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
        _snowman = -MathHelper.sin((pitch + roll) * ((float)Math.PI / 180));
        _snowman = MathHelper.cos(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
        this.setVelocity(f, _snowman, _snowman, modifierZ, modifierXYZ);
        Vec3d _snowman2 = user.getVelocity();
        this.setVelocity(this.getVelocity().add(_snowman2.x, user.isOnGround() ? 0.0 : _snowman2.y, _snowman2.z));
    }

    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
        } else if (type == HitResult.Type.BLOCK) {
            this.onBlockHit((BlockHitResult)hitResult);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockHitResult blockHitResult2 = blockHitResult;
        BlockState _snowman2 = this.world.getBlockState(blockHitResult2.getBlockPos());
        _snowman2.onProjectileHit(this.world, _snowman2, blockHitResult2, this);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        this.setVelocity(x, y, z);
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.pitch = (float)(MathHelper.atan2(y, f) * 57.2957763671875);
            this.yaw = (float)(MathHelper.atan2(x, z) * 57.2957763671875);
            this.prevPitch = this.pitch;
            this.prevYaw = this.yaw;
            this.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
        }
    }

    protected boolean method_26958(Entity entity) {
        if (entity.isSpectator() || !entity.isAlive() || !entity.collides()) {
            return false;
        }
        _snowman = this.getOwner();
        return _snowman == null || this.leftOwner || !_snowman.isConnectedThroughVehicle(entity);
    }

    protected void method_26962() {
        Vec3d vec3d = this.getVelocity();
        float _snowman2 = MathHelper.sqrt(ProjectileEntity.squaredHorizontalLength(vec3d));
        this.pitch = ProjectileEntity.updateRotation(this.prevPitch, (float)(MathHelper.atan2(vec3d.y, _snowman2) * 57.2957763671875));
        this.yaw = ProjectileEntity.updateRotation(this.prevYaw, (float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
    }

    protected static float updateRotation(float f, float f2) {
        while (f2 - f < -180.0f) {
            f -= 360.0f;
        }
        while (f2 - f >= 180.0f) {
            f += 360.0f;
        }
        return MathHelper.lerp(0.2f, f, f2);
    }
}

