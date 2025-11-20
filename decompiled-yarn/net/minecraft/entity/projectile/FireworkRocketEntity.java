package net.minecraft.entity.projectile;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class FireworkRocketEntity extends ProjectileEntity implements FlyingItemEntity {
   private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(FireworkRocketEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
   private static final TrackedData<OptionalInt> SHOOTER_ENTITY_ID = DataTracker.registerData(
      FireworkRocketEntity.class, TrackedDataHandlerRegistry.FIREWORK_DATA
   );
   private static final TrackedData<Boolean> SHOT_AT_ANGLE = DataTracker.registerData(FireworkRocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private int life;
   private int lifeTime;
   private LivingEntity shooter;

   public FireworkRocketEntity(EntityType<? extends FireworkRocketEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public FireworkRocketEntity(World world, double x, double y, double z, ItemStack stack) {
      super(EntityType.FIREWORK_ROCKET, world);
      this.life = 0;
      this.updatePosition(x, y, z);
      int _snowman = 1;
      if (!stack.isEmpty() && stack.hasTag()) {
         this.dataTracker.set(ITEM, stack.copy());
         _snowman += stack.getOrCreateSubTag("Fireworks").getByte("Flight");
      }

      this.setVelocity(this.random.nextGaussian() * 0.001, 0.05, this.random.nextGaussian() * 0.001);
      this.lifeTime = 10 * _snowman + this.random.nextInt(6) + this.random.nextInt(7);
   }

   public FireworkRocketEntity(World world, @Nullable Entity entity, double x, double y, double z, ItemStack stack) {
      this(world, x, y, z, stack);
      this.setOwner(entity);
   }

   public FireworkRocketEntity(World world, ItemStack stack, LivingEntity shooter) {
      this(world, shooter, shooter.getX(), shooter.getY(), shooter.getZ(), stack);
      this.dataTracker.set(SHOOTER_ENTITY_ID, OptionalInt.of(shooter.getEntityId()));
      this.shooter = shooter;
   }

   public FireworkRocketEntity(World world, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
      this(world, x, y, z, stack);
      this.dataTracker.set(SHOT_AT_ANGLE, shotAtAngle);
   }

   public FireworkRocketEntity(World world, ItemStack stack, Entity entity, double x, double y, double z, boolean shotAtAngle) {
      this(world, stack, x, y, z, shotAtAngle);
      this.setOwner(entity);
   }

   @Override
   protected void initDataTracker() {
      this.dataTracker.startTracking(ITEM, ItemStack.EMPTY);
      this.dataTracker.startTracking(SHOOTER_ENTITY_ID, OptionalInt.empty());
      this.dataTracker.startTracking(SHOT_AT_ANGLE, false);
   }

   @Override
   public boolean shouldRender(double distance) {
      return distance < 4096.0 && !this.wasShotByEntity();
   }

   @Override
   public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
      return super.shouldRender(cameraX, cameraY, cameraZ) && !this.wasShotByEntity();
   }

   @Override
   public void tick() {
      super.tick();
      if (this.wasShotByEntity()) {
         if (this.shooter == null) {
            this.dataTracker.get(SHOOTER_ENTITY_ID).ifPresent(_snowman -> {
               Entity _snowmanx = this.world.getEntityById(_snowman);
               if (_snowmanx instanceof LivingEntity) {
                  this.shooter = (LivingEntity)_snowmanx;
               }
            });
         }

         if (this.shooter != null) {
            if (this.shooter.isFallFlying()) {
               Vec3d _snowman = this.shooter.getRotationVector();
               double _snowmanx = 1.5;
               double _snowmanxx = 0.1;
               Vec3d _snowmanxxx = this.shooter.getVelocity();
               this.shooter
                  .setVelocity(_snowmanxxx.add(_snowman.x * 0.1 + (_snowman.x * 1.5 - _snowmanxxx.x) * 0.5, _snowman.y * 0.1 + (_snowman.y * 1.5 - _snowmanxxx.y) * 0.5, _snowman.z * 0.1 + (_snowman.z * 1.5 - _snowmanxxx.z) * 0.5));
            }

            this.updatePosition(this.shooter.getX(), this.shooter.getY(), this.shooter.getZ());
            this.setVelocity(this.shooter.getVelocity());
         }
      } else {
         if (!this.wasShotAtAngle()) {
            double _snowman = this.horizontalCollision ? 1.0 : 1.15;
            this.setVelocity(this.getVelocity().multiply(_snowman, 1.0, _snowman).add(0.0, 0.04, 0.0));
         }

         Vec3d _snowman = this.getVelocity();
         this.move(MovementType.SELF, _snowman);
         this.setVelocity(_snowman);
      }

      HitResult _snowman = ProjectileUtil.getCollision(this, this::method_26958);
      if (!this.noClip) {
         this.onCollision(_snowman);
         this.velocityDirty = true;
      }

      this.method_26962();
      if (this.life == 0 && !this.isSilent()) {
         this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 3.0F, 1.0F);
      }

      this.life++;
      if (this.world.isClient && this.life % 2 < 2) {
         this.world
            .addParticle(
               ParticleTypes.FIREWORK,
               this.getX(),
               this.getY() - 0.3,
               this.getZ(),
               this.random.nextGaussian() * 0.05,
               -this.getVelocity().y * 0.5,
               this.random.nextGaussian() * 0.05
            );
      }

      if (!this.world.isClient && this.life > this.lifeTime) {
         this.explodeAndRemove();
      }
   }

   private void explodeAndRemove() {
      this.world.sendEntityStatus(this, (byte)17);
      this.explode();
      this.remove();
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      if (!this.world.isClient) {
         this.explodeAndRemove();
      }
   }

   @Override
   protected void onBlockHit(BlockHitResult blockHitResult) {
      BlockPos _snowman = new BlockPos(blockHitResult.getBlockPos());
      this.world.getBlockState(_snowman).onEntityCollision(this.world, _snowman, this);
      if (!this.world.isClient() && this.hasExplosionEffects()) {
         this.explodeAndRemove();
      }

      super.onBlockHit(blockHitResult);
   }

   private boolean hasExplosionEffects() {
      ItemStack _snowman = this.dataTracker.get(ITEM);
      CompoundTag _snowmanx = _snowman.isEmpty() ? null : _snowman.getSubTag("Fireworks");
      ListTag _snowmanxx = _snowmanx != null ? _snowmanx.getList("Explosions", 10) : null;
      return _snowmanxx != null && !_snowmanxx.isEmpty();
   }

   private void explode() {
      float _snowman = 0.0F;
      ItemStack _snowmanx = this.dataTracker.get(ITEM);
      CompoundTag _snowmanxx = _snowmanx.isEmpty() ? null : _snowmanx.getSubTag("Fireworks");
      ListTag _snowmanxxx = _snowmanxx != null ? _snowmanxx.getList("Explosions", 10) : null;
      if (_snowmanxxx != null && !_snowmanxxx.isEmpty()) {
         _snowman = 5.0F + (float)(_snowmanxxx.size() * 2);
      }

      if (_snowman > 0.0F) {
         if (this.shooter != null) {
            this.shooter.damage(DamageSource.firework(this, this.getOwner()), 5.0F + (float)(_snowmanxxx.size() * 2));
         }

         double _snowmanxxxx = 5.0;
         Vec3d _snowmanxxxxx = this.getPos();

         for (LivingEntity _snowmanxxxxxx : this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0))) {
            if (_snowmanxxxxxx != this.shooter && !(this.squaredDistanceTo(_snowmanxxxxxx) > 25.0)) {
               boolean _snowmanxxxxxxx = false;

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 2; _snowmanxxxxxxxx++) {
                  Vec3d _snowmanxxxxxxxxx = new Vec3d(_snowmanxxxxxx.getX(), _snowmanxxxxxx.getBodyY(0.5 * (double)_snowmanxxxxxxxx), _snowmanxxxxxx.getZ());
                  HitResult _snowmanxxxxxxxxxx = this.world
                     .raycast(new RaycastContext(_snowmanxxxxx, _snowmanxxxxxxxxx, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
                  if (_snowmanxxxxxxxxxx.getType() == HitResult.Type.MISS) {
                     _snowmanxxxxxxx = true;
                     break;
                  }
               }

               if (_snowmanxxxxxxx) {
                  float _snowmanxxxxxxxxx = _snowman * (float)Math.sqrt((5.0 - (double)this.distanceTo(_snowmanxxxxxx)) / 5.0);
                  _snowmanxxxxxx.damage(DamageSource.firework(this, this.getOwner()), _snowmanxxxxxxxxx);
               }
            }
         }
      }
   }

   private boolean wasShotByEntity() {
      return this.dataTracker.get(SHOOTER_ENTITY_ID).isPresent();
   }

   public boolean wasShotAtAngle() {
      return this.dataTracker.get(SHOT_AT_ANGLE);
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 17 && this.world.isClient) {
         if (!this.hasExplosionEffects()) {
            for (int _snowman = 0; _snowman < this.random.nextInt(3) + 2; _snowman++) {
               this.world
                  .addParticle(
                     ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05, 0.005, this.random.nextGaussian() * 0.05
                  );
            }
         } else {
            ItemStack _snowman = this.dataTracker.get(ITEM);
            CompoundTag _snowmanx = _snowman.isEmpty() ? null : _snowman.getSubTag("Fireworks");
            Vec3d _snowmanxx = this.getVelocity();
            this.world.addFireworkParticle(this.getX(), this.getY(), this.getZ(), _snowmanxx.x, _snowmanxx.y, _snowmanxx.z, _snowmanx);
         }
      }

      super.handleStatus(status);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("Life", this.life);
      tag.putInt("LifeTime", this.lifeTime);
      ItemStack _snowman = this.dataTracker.get(ITEM);
      if (!_snowman.isEmpty()) {
         tag.put("FireworksItem", _snowman.toTag(new CompoundTag()));
      }

      tag.putBoolean("ShotAtAngle", this.dataTracker.get(SHOT_AT_ANGLE));
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.life = tag.getInt("Life");
      this.lifeTime = tag.getInt("LifeTime");
      ItemStack _snowman = ItemStack.fromTag(tag.getCompound("FireworksItem"));
      if (!_snowman.isEmpty()) {
         this.dataTracker.set(ITEM, _snowman);
      }

      if (tag.contains("ShotAtAngle")) {
         this.dataTracker.set(SHOT_AT_ANGLE, tag.getBoolean("ShotAtAngle"));
      }
   }

   @Override
   public ItemStack getStack() {
      ItemStack _snowman = this.dataTracker.get(ITEM);
      return _snowman.isEmpty() ? new ItemStack(Items.FIREWORK_ROCKET) : _snowman;
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
