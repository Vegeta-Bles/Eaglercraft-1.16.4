package net.minecraft.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
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

@EnvironmentInterfaces({@EnvironmentInterface(
      value = EnvType.CLIENT,
      itf = FlyingItemEntity.class
   )})
public class EyeOfEnderEntity extends Entity implements FlyingItemEntity {
   private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(EyeOfEnderEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
   private double targetX;
   private double targetY;
   private double targetZ;
   private int lifespan;
   private boolean dropsItem;

   public EyeOfEnderEntity(EntityType<? extends EyeOfEnderEntity> arg, World arg2) {
      super(arg, arg2);
   }

   public EyeOfEnderEntity(World world, double x, double y, double z) {
      this(EntityType.EYE_OF_ENDER, world);
      this.lifespan = 0;
      this.updatePosition(x, y, z);
   }

   public void setItem(ItemStack stack) {
      if (stack.getItem() != Items.ENDER_EYE || stack.hasTag()) {
         this.getDataTracker().set(ITEM, Util.make(stack.copy(), stackx -> stackx.setCount(1)));
      }
   }

   private ItemStack getTrackedItem() {
      return this.getDataTracker().get(ITEM);
   }

   @Override
   public ItemStack getStack() {
      ItemStack lv = this.getTrackedItem();
      return lv.isEmpty() ? new ItemStack(Items.ENDER_EYE) : lv;
   }

   @Override
   protected void initDataTracker() {
      this.getDataTracker().startTracking(ITEM, ItemStack.EMPTY);
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

   public void initTargetPos(BlockPos pos) {
      double d = (double)pos.getX();
      int i = pos.getY();
      double e = (double)pos.getZ();
      double f = d - this.getX();
      double g = e - this.getZ();
      float h = MathHelper.sqrt(f * f + g * g);
      if (h > 12.0F) {
         this.targetX = this.getX() + f / (double)h * 12.0;
         this.targetZ = this.getZ() + g / (double)h * 12.0;
         this.targetY = this.getY() + 8.0;
      } else {
         this.targetX = d;
         this.targetY = (double)i;
         this.targetZ = e;
      }

      this.lifespan = 0;
      this.dropsItem = this.random.nextInt(5) > 0;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void setVelocityClient(double x, double y, double z) {
      this.setVelocity(x, y, z);
      if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
         float g = MathHelper.sqrt(x * x + z * z);
         this.yaw = (float)(MathHelper.atan2(x, z) * 180.0F / (float)Math.PI);
         this.pitch = (float)(MathHelper.atan2(y, (double)g) * 180.0F / (float)Math.PI);
         this.prevYaw = this.yaw;
         this.prevPitch = this.pitch;
      }
   }

   @Override
   public void tick() {
      super.tick();
      Vec3d lv = this.getVelocity();
      double d = this.getX() + lv.x;
      double e = this.getY() + lv.y;
      double f = this.getZ() + lv.z;
      float g = MathHelper.sqrt(squaredHorizontalLength(lv));
      this.pitch = ProjectileEntity.updateRotation(this.prevPitch, (float)(MathHelper.atan2(lv.y, (double)g) * 180.0F / (float)Math.PI));
      this.yaw = ProjectileEntity.updateRotation(this.prevYaw, (float)(MathHelper.atan2(lv.x, lv.z) * 180.0F / (float)Math.PI));
      if (!this.world.isClient) {
         double h = this.targetX - d;
         double i = this.targetZ - f;
         float j = (float)Math.sqrt(h * h + i * i);
         float k = (float)MathHelper.atan2(i, h);
         double l = MathHelper.lerp(0.0025, (double)g, (double)j);
         double m = lv.y;
         if (j < 1.0F) {
            l *= 0.8;
            m *= 0.8;
         }

         int n = this.getY() < this.targetY ? 1 : -1;
         lv = new Vec3d(Math.cos((double)k) * l, m + ((double)n - m) * 0.015F, Math.sin((double)k) * l);
         this.setVelocity(lv);
      }

      float o = 0.25F;
      if (this.isTouchingWater()) {
         for (int p = 0; p < 4; p++) {
            this.world.addParticle(ParticleTypes.BUBBLE, d - lv.x * 0.25, e - lv.y * 0.25, f - lv.z * 0.25, lv.x, lv.y, lv.z);
         }
      } else {
         this.world
            .addParticle(
               ParticleTypes.PORTAL,
               d - lv.x * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               e - lv.y * 0.25 - 0.5,
               f - lv.z * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               lv.x,
               lv.y,
               lv.z
            );
      }

      if (!this.world.isClient) {
         this.updatePosition(d, e, f);
         this.lifespan++;
         if (this.lifespan > 80 && !this.world.isClient) {
            this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0F, 1.0F);
            this.remove();
            if (this.dropsItem) {
               this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), this.getStack()));
            } else {
               this.world.syncWorldEvent(2003, this.getBlockPos(), 0);
            }
         }
      } else {
         this.setPos(d, e, f);
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      ItemStack lv = this.getTrackedItem();
      if (!lv.isEmpty()) {
         tag.put("Item", lv.toTag(new CompoundTag()));
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      ItemStack lv = ItemStack.fromTag(tag.getCompound("Item"));
      this.setItem(lv);
   }

   @Override
   public float getBrightnessAtEyes() {
      return 1.0F;
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
