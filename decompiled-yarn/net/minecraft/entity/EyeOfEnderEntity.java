package net.minecraft.entity;

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

public class EyeOfEnderEntity extends Entity implements FlyingItemEntity {
   private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(EyeOfEnderEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
   private double targetX;
   private double targetY;
   private double targetZ;
   private int lifespan;
   private boolean dropsItem;

   public EyeOfEnderEntity(EntityType<? extends EyeOfEnderEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
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
      ItemStack _snowman = this.getTrackedItem();
      return _snowman.isEmpty() ? new ItemStack(Items.ENDER_EYE) : _snowman;
   }

   @Override
   protected void initDataTracker() {
      this.getDataTracker().startTracking(ITEM, ItemStack.EMPTY);
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

   public void initTargetPos(BlockPos pos) {
      double _snowman = (double)pos.getX();
      int _snowmanx = pos.getY();
      double _snowmanxx = (double)pos.getZ();
      double _snowmanxxx = _snowman - this.getX();
      double _snowmanxxxx = _snowmanxx - this.getZ();
      float _snowmanxxxxx = MathHelper.sqrt(_snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx);
      if (_snowmanxxxxx > 12.0F) {
         this.targetX = this.getX() + _snowmanxxx / (double)_snowmanxxxxx * 12.0;
         this.targetZ = this.getZ() + _snowmanxxxx / (double)_snowmanxxxxx * 12.0;
         this.targetY = this.getY() + 8.0;
      } else {
         this.targetX = _snowman;
         this.targetY = (double)_snowmanx;
         this.targetZ = _snowmanxx;
      }

      this.lifespan = 0;
      this.dropsItem = this.random.nextInt(5) > 0;
   }

   @Override
   public void setVelocityClient(double x, double y, double z) {
      this.setVelocity(x, y, z);
      if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
         float _snowman = MathHelper.sqrt(x * x + z * z);
         this.yaw = (float)(MathHelper.atan2(x, z) * 180.0F / (float)Math.PI);
         this.pitch = (float)(MathHelper.atan2(y, (double)_snowman) * 180.0F / (float)Math.PI);
         this.prevYaw = this.yaw;
         this.prevPitch = this.pitch;
      }
   }

   @Override
   public void tick() {
      super.tick();
      Vec3d _snowman = this.getVelocity();
      double _snowmanx = this.getX() + _snowman.x;
      double _snowmanxx = this.getY() + _snowman.y;
      double _snowmanxxx = this.getZ() + _snowman.z;
      float _snowmanxxxx = MathHelper.sqrt(squaredHorizontalLength(_snowman));
      this.pitch = ProjectileEntity.updateRotation(this.prevPitch, (float)(MathHelper.atan2(_snowman.y, (double)_snowmanxxxx) * 180.0F / (float)Math.PI));
      this.yaw = ProjectileEntity.updateRotation(this.prevYaw, (float)(MathHelper.atan2(_snowman.x, _snowman.z) * 180.0F / (float)Math.PI));
      if (!this.world.isClient) {
         double _snowmanxxxxx = this.targetX - _snowmanx;
         double _snowmanxxxxxx = this.targetZ - _snowmanxxx;
         float _snowmanxxxxxxx = (float)Math.sqrt(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx);
         float _snowmanxxxxxxxx = (float)MathHelper.atan2(_snowmanxxxxxx, _snowmanxxxxx);
         double _snowmanxxxxxxxxx = MathHelper.lerp(0.0025, (double)_snowmanxxxx, (double)_snowmanxxxxxxx);
         double _snowmanxxxxxxxxxx = _snowman.y;
         if (_snowmanxxxxxxx < 1.0F) {
            _snowmanxxxxxxxxx *= 0.8;
            _snowmanxxxxxxxxxx *= 0.8;
         }

         int _snowmanxxxxxxxxxxx = this.getY() < this.targetY ? 1 : -1;
         _snowman = new Vec3d(
            Math.cos((double)_snowmanxxxxxxxx) * _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx + ((double)_snowmanxxxxxxxxxxx - _snowmanxxxxxxxxxx) * 0.015F, Math.sin((double)_snowmanxxxxxxxx) * _snowmanxxxxxxxxx
         );
         this.setVelocity(_snowman);
      }

      float _snowmanxxxxx = 0.25F;
      if (this.isTouchingWater()) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
            this.world.addParticle(ParticleTypes.BUBBLE, _snowmanx - _snowman.x * 0.25, _snowmanxx - _snowman.y * 0.25, _snowmanxxx - _snowman.z * 0.25, _snowman.x, _snowman.y, _snowman.z);
         }
      } else {
         this.world
            .addParticle(
               ParticleTypes.PORTAL,
               _snowmanx - _snowman.x * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               _snowmanxx - _snowman.y * 0.25 - 0.5,
               _snowmanxxx - _snowman.z * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               _snowman.x,
               _snowman.y,
               _snowman.z
            );
      }

      if (!this.world.isClient) {
         this.updatePosition(_snowmanx, _snowmanxx, _snowmanxxx);
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
         this.setPos(_snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      ItemStack _snowman = this.getTrackedItem();
      if (!_snowman.isEmpty()) {
         tag.put("Item", _snowman.toTag(new CompoundTag()));
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      ItemStack _snowman = ItemStack.fromTag(tag.getCompound("Item"));
      this.setItem(_snowman);
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
