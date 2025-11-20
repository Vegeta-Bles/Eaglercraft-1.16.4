package net.minecraft.entity.projectile;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LlamaSpitEntity extends ProjectileEntity {
   public LlamaSpitEntity(EntityType<? extends LlamaSpitEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public LlamaSpitEntity(World world, LlamaEntity owner) {
      this(EntityType.LLAMA_SPIT, world);
      super.setOwner(owner);
      this.updatePosition(
         owner.getX() - (double)(owner.getWidth() + 1.0F) * 0.5 * (double)MathHelper.sin(owner.bodyYaw * (float) (Math.PI / 180.0)),
         owner.getEyeY() - 0.1F,
         owner.getZ() + (double)(owner.getWidth() + 1.0F) * 0.5 * (double)MathHelper.cos(owner.bodyYaw * (float) (Math.PI / 180.0))
      );
   }

   public LlamaSpitEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this(EntityType.LLAMA_SPIT, world);
      this.updatePosition(x, y, z);

      for (int _snowman = 0; _snowman < 7; _snowman++) {
         double _snowmanx = 0.4 + 0.1 * (double)_snowman;
         world.addParticle(ParticleTypes.SPIT, x, y, z, velocityX * _snowmanx, velocityY, velocityZ * _snowmanx);
      }

      this.setVelocity(velocityX, velocityY, velocityZ);
   }

   @Override
   public void tick() {
      super.tick();
      Vec3d _snowman = this.getVelocity();
      HitResult _snowmanx = ProjectileUtil.getCollision(this, this::method_26958);
      if (_snowmanx != null) {
         this.onCollision(_snowmanx);
      }

      double _snowmanxx = this.getX() + _snowman.x;
      double _snowmanxxx = this.getY() + _snowman.y;
      double _snowmanxxxx = this.getZ() + _snowman.z;
      this.method_26962();
      float _snowmanxxxxx = 0.99F;
      float _snowmanxxxxxx = 0.06F;
      if (this.world.method_29546(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
         this.remove();
      } else if (this.isInsideWaterOrBubbleColumn()) {
         this.remove();
      } else {
         this.setVelocity(_snowman.multiply(0.99F));
         if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.06F, 0.0));
         }

         this.updatePosition(_snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      Entity _snowman = this.getOwner();
      if (_snowman instanceof LivingEntity) {
         entityHitResult.getEntity().damage(DamageSource.mobProjectile(this, (LivingEntity)_snowman).setProjectile(), 1.0F);
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
