package net.minecraft.client.particle;

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

public class ItemPickupParticle extends Particle {
   private final BufferBuilderStorage bufferStorage;
   private final Entity itemEntity;
   private final Entity interactingEntity;
   private int ticksExisted;
   private final EntityRenderDispatcher dispatcher;

   public ItemPickupParticle(
      EntityRenderDispatcher dispatcher, BufferBuilderStorage bufferStorage, ClientWorld world, Entity itemEntity, Entity interactingEntity
   ) {
      this(dispatcher, bufferStorage, world, itemEntity, interactingEntity, itemEntity.getVelocity());
   }

   private ItemPickupParticle(
      EntityRenderDispatcher dispatcher, BufferBuilderStorage bufferStorage, ClientWorld world, Entity _snowman, Entity interactingEntity, Vec3d velocity
   ) {
      super(world, _snowman.getX(), _snowman.getY(), _snowman.getZ(), velocity.x, velocity.y, velocity.z);
      this.bufferStorage = bufferStorage;
      this.itemEntity = this.method_29358(_snowman);
      this.interactingEntity = interactingEntity;
      this.dispatcher = dispatcher;
   }

   private Entity method_29358(Entity _snowman) {
      return (Entity)(!(_snowman instanceof ItemEntity) ? _snowman : ((ItemEntity)_snowman).method_29271());
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.CUSTOM;
   }

   @Override
   public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
      float _snowman = ((float)this.ticksExisted + tickDelta) / 3.0F;
      _snowman *= _snowman;
      double _snowmanx = MathHelper.lerp((double)tickDelta, this.interactingEntity.lastRenderX, this.interactingEntity.getX());
      double _snowmanxx = MathHelper.lerp((double)tickDelta, this.interactingEntity.lastRenderY, this.interactingEntity.getY()) + 0.5;
      double _snowmanxxx = MathHelper.lerp((double)tickDelta, this.interactingEntity.lastRenderZ, this.interactingEntity.getZ());
      double _snowmanxxxx = MathHelper.lerp((double)_snowman, this.itemEntity.getX(), _snowmanx);
      double _snowmanxxxxx = MathHelper.lerp((double)_snowman, this.itemEntity.getY(), _snowmanxx);
      double _snowmanxxxxxx = MathHelper.lerp((double)_snowman, this.itemEntity.getZ(), _snowmanxxx);
      VertexConsumerProvider.Immediate _snowmanxxxxxxx = this.bufferStorage.getEntityVertexConsumers();
      Vec3d _snowmanxxxxxxxx = camera.getPos();
      this.dispatcher
         .render(
            this.itemEntity,
            _snowmanxxxx - _snowmanxxxxxxxx.getX(),
            _snowmanxxxxx - _snowmanxxxxxxxx.getY(),
            _snowmanxxxxxx - _snowmanxxxxxxxx.getZ(),
            this.itemEntity.yaw,
            tickDelta,
            new MatrixStack(),
            _snowmanxxxxxxx,
            this.dispatcher.getLight(this.itemEntity, tickDelta)
         );
      _snowmanxxxxxxx.draw();
   }

   @Override
   public void tick() {
      this.ticksExisted++;
      if (this.ticksExisted == 3) {
         this.markDead();
      }
   }
}
