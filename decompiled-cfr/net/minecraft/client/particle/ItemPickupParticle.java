/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ItemPickupParticle
extends Particle {
    private final BufferBuilderStorage bufferStorage;
    private final Entity itemEntity;
    private final Entity interactingEntity;
    private int ticksExisted;
    private final EntityRenderDispatcher dispatcher;

    public ItemPickupParticle(EntityRenderDispatcher dispatcher, BufferBuilderStorage bufferStorage, ClientWorld world, Entity itemEntity, Entity interactingEntity) {
        this(dispatcher, bufferStorage, world, itemEntity, interactingEntity, itemEntity.getVelocity());
    }

    private ItemPickupParticle(EntityRenderDispatcher dispatcher, BufferBuilderStorage bufferStorage, ClientWorld world, Entity entity, Entity interactingEntity, Vec3d velocity) {
        super(world, entity.getX(), entity.getY(), entity.getZ(), velocity.x, velocity.y, velocity.z);
        this.bufferStorage = bufferStorage;
        this.itemEntity = this.method_29358(entity);
        this.interactingEntity = interactingEntity;
        this.dispatcher = dispatcher;
    }

    private Entity method_29358(Entity entity) {
        if (!(entity instanceof ItemEntity)) {
            return entity;
        }
        return ((ItemEntity)entity).method_29271();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        float f = ((float)this.ticksExisted + tickDelta) / 3.0f;
        f *= f;
        double _snowman2 = MathHelper.lerp((double)tickDelta, this.interactingEntity.lastRenderX, this.interactingEntity.getX());
        double _snowman3 = MathHelper.lerp((double)tickDelta, this.interactingEntity.lastRenderY, this.interactingEntity.getY()) + 0.5;
        double _snowman4 = MathHelper.lerp((double)tickDelta, this.interactingEntity.lastRenderZ, this.interactingEntity.getZ());
        double _snowman5 = MathHelper.lerp((double)f, this.itemEntity.getX(), _snowman2);
        double _snowman6 = MathHelper.lerp((double)f, this.itemEntity.getY(), _snowman3);
        double _snowman7 = MathHelper.lerp((double)f, this.itemEntity.getZ(), _snowman4);
        VertexConsumerProvider.Immediate _snowman8 = this.bufferStorage.getEntityVertexConsumers();
        Vec3d _snowman9 = camera.getPos();
        this.dispatcher.render(this.itemEntity, _snowman5 - _snowman9.getX(), _snowman6 - _snowman9.getY(), _snowman7 - _snowman9.getZ(), this.itemEntity.yaw, tickDelta, new MatrixStack(), _snowman8, this.dispatcher.getLight(this.itemEntity, tickDelta));
        _snowman8.draw();
    }

    @Override
    public void tick() {
        ++this.ticksExisted;
        if (this.ticksExisted == 3) {
            this.markDead();
        }
    }
}

