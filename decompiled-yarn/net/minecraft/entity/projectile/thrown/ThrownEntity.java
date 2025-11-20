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

public abstract class ThrownEntity extends ProjectileEntity {
   protected ThrownEntity(EntityType<? extends ThrownEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   protected ThrownEntity(EntityType<? extends ThrownEntity> type, double x, double y, double z, World world) {
      this(type, world);
      this.updatePosition(x, y, z);
   }

   protected ThrownEntity(EntityType<? extends ThrownEntity> type, LivingEntity owner, World world) {
      this(type, owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), world);
      this.setOwner(owner);
   }

   @Override
   public boolean shouldRender(double distance) {
      double _snowman = this.getBoundingBox().getAverageSideLength() * 4.0;
      if (Double.isNaN(_snowman)) {
         _snowman = 4.0;
      }

      _snowman *= 64.0;
      return distance < _snowman * _snowman;
   }

   @Override
   public void tick() {
      super.tick();
      HitResult _snowman = ProjectileUtil.getCollision(this, this::method_26958);
      boolean _snowmanx = false;
      if (_snowman.getType() == HitResult.Type.BLOCK) {
         BlockPos _snowmanxx = ((BlockHitResult)_snowman).getBlockPos();
         BlockState _snowmanxxx = this.world.getBlockState(_snowmanxx);
         if (_snowmanxxx.isOf(Blocks.NETHER_PORTAL)) {
            this.setInNetherPortal(_snowmanxx);
            _snowmanx = true;
         } else if (_snowmanxxx.isOf(Blocks.END_GATEWAY)) {
            BlockEntity _snowmanxxxx = this.world.getBlockEntity(_snowmanxx);
            if (_snowmanxxxx instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.method_30276(this)) {
               ((EndGatewayBlockEntity)_snowmanxxxx).tryTeleportingEntity(this);
            }

            _snowmanx = true;
         }
      }

      if (_snowman.getType() != HitResult.Type.MISS && !_snowmanx) {
         this.onCollision(_snowman);
      }

      this.checkBlockCollision();
      Vec3d _snowmanxx = this.getVelocity();
      double _snowmanxxx = this.getX() + _snowmanxx.x;
      double _snowmanxxxx = this.getY() + _snowmanxx.y;
      double _snowmanxxxxx = this.getZ() + _snowmanxx.z;
      this.method_26962();
      float _snowmanxxxxxx;
      if (this.isTouchingWater()) {
         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
            float _snowmanxxxxxxxx = 0.25F;
            this.world.addParticle(ParticleTypes.BUBBLE, _snowmanxxx - _snowmanxx.x * 0.25, _snowmanxxxx - _snowmanxx.y * 0.25, _snowmanxxxxx - _snowmanxx.z * 0.25, _snowmanxx.x, _snowmanxx.y, _snowmanxx.z);
         }

         _snowmanxxxxxx = 0.8F;
      } else {
         _snowmanxxxxxx = 0.99F;
      }

      this.setVelocity(_snowmanxx.multiply((double)_snowmanxxxxxx));
      if (!this.hasNoGravity()) {
         Vec3d _snowmanxxxxxxx = this.getVelocity();
         this.setVelocity(_snowmanxxxxxxx.x, _snowmanxxxxxxx.y - (double)this.getGravity(), _snowmanxxxxxxx.z);
      }

      this.updatePosition(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   protected float getGravity() {
      return 0.03F;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this);
   }
}
