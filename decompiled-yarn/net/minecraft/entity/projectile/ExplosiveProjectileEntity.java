package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ExplosiveProjectileEntity extends ProjectileEntity {
   public double posX;
   public double posY;
   public double posZ;

   protected ExplosiveProjectileEntity(EntityType<? extends ExplosiveProjectileEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public ExplosiveProjectileEntity(
      EntityType<? extends ExplosiveProjectileEntity> type, double x, double y, double z, double directionX, double directionY, double directionZ, World world
   ) {
      this(type, world);
      this.refreshPositionAndAngles(x, y, z, this.yaw, this.pitch);
      this.refreshPosition();
      double _snowman = (double)MathHelper.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
      if (_snowman != 0.0) {
         this.posX = directionX / _snowman * 0.1;
         this.posY = directionY / _snowman * 0.1;
         this.posZ = directionZ / _snowman * 0.1;
      }
   }

   public ExplosiveProjectileEntity(
      EntityType<? extends ExplosiveProjectileEntity> type, LivingEntity owner, double directionX, double directionY, double directionZ, World world
   ) {
      this(type, owner.getX(), owner.getY(), owner.getZ(), directionX, directionY, directionZ, world);
      this.setOwner(owner);
      this.setRotation(owner.yaw, owner.pitch);
   }

   @Override
   protected void initDataTracker() {
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
      Entity _snowman = this.getOwner();
      if (this.world.isClient || (_snowman == null || !_snowman.removed) && this.world.isChunkLoaded(this.getBlockPos())) {
         super.tick();
         if (this.isBurning()) {
            this.setOnFireFor(1);
         }

         HitResult _snowmanx = ProjectileUtil.getCollision(this, this::method_26958);
         if (_snowmanx.getType() != HitResult.Type.MISS) {
            this.onCollision(_snowmanx);
         }

         this.checkBlockCollision();
         Vec3d _snowmanxx = this.getVelocity();
         double _snowmanxxx = this.getX() + _snowmanxx.x;
         double _snowmanxxxx = this.getY() + _snowmanxx.y;
         double _snowmanxxxxx = this.getZ() + _snowmanxx.z;
         ProjectileUtil.method_7484(this, 0.2F);
         float _snowmanxxxxxx = this.getDrag();
         if (this.isTouchingWater()) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
               float _snowmanxxxxxxxx = 0.25F;
               this.world.addParticle(ParticleTypes.BUBBLE, _snowmanxxx - _snowmanxx.x * 0.25, _snowmanxxxx - _snowmanxx.y * 0.25, _snowmanxxxxx - _snowmanxx.z * 0.25, _snowmanxx.x, _snowmanxx.y, _snowmanxx.z);
            }

            _snowmanxxxxxx = 0.8F;
         }

         this.setVelocity(_snowmanxx.add(this.posX, this.posY, this.posZ).multiply((double)_snowmanxxxxxx));
         this.world.addParticle(this.getParticleType(), _snowmanxxx, _snowmanxxxx + 0.5, _snowmanxxxxx, 0.0, 0.0, 0.0);
         this.updatePosition(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      } else {
         this.remove();
      }
   }

   @Override
   protected boolean method_26958(Entity _snowman) {
      return super.method_26958(_snowman) && !_snowman.noClip;
   }

   protected boolean isBurning() {
      return true;
   }

   protected ParticleEffect getParticleType() {
      return ParticleTypes.SMOKE;
   }

   protected float getDrag() {
      return 0.95F;
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.put("power", this.toListTag(new double[]{this.posX, this.posY, this.posZ}));
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("power", 9)) {
         ListTag _snowman = tag.getList("power", 6);
         if (_snowman.size() == 3) {
            this.posX = _snowman.getDouble(0);
            this.posY = _snowman.getDouble(1);
            this.posZ = _snowman.getDouble(2);
         }
      }
   }

   @Override
   public boolean collides() {
      return true;
   }

   @Override
   public float getTargetingMargin() {
      return 1.0F;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.scheduleVelocityUpdate();
         Entity _snowman = source.getAttacker();
         if (_snowman != null) {
            Vec3d _snowmanx = _snowman.getRotationVector();
            this.setVelocity(_snowmanx);
            this.posX = _snowmanx.x * 0.1;
            this.posY = _snowmanx.y * 0.1;
            this.posZ = _snowmanx.z * 0.1;
            this.setOwner(_snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public float getBrightnessAtEyes() {
      return 1.0F;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      Entity _snowman = this.getOwner();
      int _snowmanx = _snowman == null ? 0 : _snowman.getEntityId();
      return new EntitySpawnS2CPacket(
         this.getEntityId(),
         this.getUuid(),
         this.getX(),
         this.getY(),
         this.getZ(),
         this.pitch,
         this.yaw,
         this.getType(),
         _snowmanx,
         new Vec3d(this.posX, this.posY, this.posZ)
      );
   }
}
