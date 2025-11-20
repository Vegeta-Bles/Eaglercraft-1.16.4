/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LlamaSpitEntity
extends ProjectileEntity {
    public LlamaSpitEntity(EntityType<? extends LlamaSpitEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    public LlamaSpitEntity(World world, LlamaEntity owner) {
        this((EntityType<? extends LlamaSpitEntity>)EntityType.LLAMA_SPIT, world);
        super.setOwner(owner);
        this.updatePosition(owner.getX() - (double)(owner.getWidth() + 1.0f) * 0.5 * (double)MathHelper.sin(owner.bodyYaw * ((float)Math.PI / 180)), owner.getEyeY() - (double)0.1f, owner.getZ() + (double)(owner.getWidth() + 1.0f) * 0.5 * (double)MathHelper.cos(owner.bodyYaw * ((float)Math.PI / 180)));
    }

    public LlamaSpitEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this((EntityType<? extends LlamaSpitEntity>)EntityType.LLAMA_SPIT, world);
        this.updatePosition(x, y, z);
        for (int i = 0; i < 7; ++i) {
            double d = 0.4 + 0.1 * (double)i;
            world.addParticle(ParticleTypes.SPIT, x, y, z, velocityX * d, velocityY, velocityZ * d);
        }
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        HitResult _snowman2 = ProjectileUtil.getCollision(this, this::method_26958);
        if (_snowman2 != null) {
            this.onCollision(_snowman2);
        }
        double _snowman3 = this.getX() + vec3d.x;
        double _snowman4 = this.getY() + vec3d.y;
        double _snowman5 = this.getZ() + vec3d.z;
        this.method_26962();
        float _snowman6 = 0.99f;
        float _snowman7 = 0.06f;
        if (this.world.method_29546(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.remove();
            return;
        }
        if (this.isInsideWaterOrBubbleColumn()) {
            this.remove();
            return;
        }
        this.setVelocity(vec3d.multiply(0.99f));
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.06f, 0.0));
        }
        this.updatePosition(_snowman3, _snowman4, _snowman5);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            entityHitResult.getEntity().damage(DamageSource.mobProjectile(this, (LivingEntity)entity).setProjectile(), 1.0f);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            this.remove();
        }
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}

