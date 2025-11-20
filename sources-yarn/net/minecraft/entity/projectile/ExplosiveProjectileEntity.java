package net.minecraft.entity.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   protected ExplosiveProjectileEntity(EntityType<? extends ExplosiveProjectileEntity> arg, World arg2) {
      super(arg, arg2);
   }

   public ExplosiveProjectileEntity(
      EntityType<? extends ExplosiveProjectileEntity> type, double x, double y, double z, double directionX, double directionY, double directionZ, World world
   ) {
      this(type, world);
      this.refreshPositionAndAngles(x, y, z, this.yaw, this.pitch);
      this.refreshPosition();
      double j = (double)MathHelper.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
      if (j != 0.0) {
         this.posX = directionX / j * 0.1;
         this.posY = directionY / j * 0.1;
         this.posZ = directionZ / j * 0.1;
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

   @Environment(EnvType.CLIENT)
   @Override
   public boolean shouldRender(double distance) {
      double e = this.getBoundingBox().getAverageSideLength() * 4.0;
      if (Double.isNaN(e)) {
         e = 4.0;
      }

      e *= 64.0;
      return distance < e * e;
   }

   @Override
   public void tick() {
      Entity lv = this.getOwner();
      if (this.world.isClient || (lv == null || !lv.removed) && this.world.isChunkLoaded(this.getBlockPos())) {
         super.tick();
         if (this.isBurning()) {
            this.setOnFireFor(1);
         }

         HitResult lv2 = ProjectileUtil.getCollision(this, this::method_26958);
         if (lv2.getType() != HitResult.Type.MISS) {
            this.onCollision(lv2);
         }

         this.checkBlockCollision();
         Vec3d lv3 = this.getVelocity();
         double d = this.getX() + lv3.x;
         double e = this.getY() + lv3.y;
         double f = this.getZ() + lv3.z;
         ProjectileUtil.method_7484(this, 0.2F);
         float g = this.getDrag();
         if (this.isTouchingWater()) {
            for (int i = 0; i < 4; i++) {
               float h = 0.25F;
               this.world.addParticle(ParticleTypes.BUBBLE, d - lv3.x * 0.25, e - lv3.y * 0.25, f - lv3.z * 0.25, lv3.x, lv3.y, lv3.z);
            }

            g = 0.8F;
         }

         this.setVelocity(lv3.add(this.posX, this.posY, this.posZ).multiply((double)g));
         this.world.addParticle(this.getParticleType(), d, e + 0.5, f, 0.0, 0.0, 0.0);
         this.updatePosition(d, e, f);
      } else {
         this.remove();
      }
   }

   @Override
   protected boolean method_26958(Entity arg) {
      return super.method_26958(arg) && !arg.noClip;
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
         ListTag lv = tag.getList("power", 6);
         if (lv.size() == 3) {
            this.posX = lv.getDouble(0);
            this.posY = lv.getDouble(1);
            this.posZ = lv.getDouble(2);
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
         Entity lv = source.getAttacker();
         if (lv != null) {
            Vec3d lv2 = lv.getRotationVector();
            this.setVelocity(lv2);
            this.posX = lv2.x * 0.1;
            this.posY = lv2.y * 0.1;
            this.posZ = lv2.z * 0.1;
            this.setOwner(lv);
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
      Entity lv = this.getOwner();
      int i = lv == null ? 0 : lv.getEntityId();
      return new EntitySpawnS2CPacket(
         this.getEntityId(),
         this.getUuid(),
         this.getX(),
         this.getY(),
         this.getZ(),
         this.pitch,
         this.yaw,
         this.getType(),
         i,
         new Vec3d(this.posX, this.posY, this.posZ)
      );
   }
}
