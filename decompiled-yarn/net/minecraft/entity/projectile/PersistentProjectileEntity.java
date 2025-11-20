package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public abstract class PersistentProjectileEntity extends ProjectileEntity {
   private static final TrackedData<Byte> PROJECTILE_FLAGS = DataTracker.registerData(PersistentProjectileEntity.class, TrackedDataHandlerRegistry.BYTE);
   private static final TrackedData<Byte> PIERCE_LEVEL = DataTracker.registerData(PersistentProjectileEntity.class, TrackedDataHandlerRegistry.BYTE);
   @Nullable
   private BlockState inBlockState;
   protected boolean inGround;
   protected int inGroundTime;
   public PersistentProjectileEntity.PickupPermission pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
   public int shake;
   private int life;
   private double damage = 2.0;
   private int punch;
   private SoundEvent sound = this.getHitSound();
   private IntOpenHashSet piercedEntities;
   private List<Entity> piercingKilledEntities;

   protected PersistentProjectileEntity(EntityType<? extends PersistentProjectileEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   protected PersistentProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) {
      this(type, world);
      this.updatePosition(x, y, z);
   }

   protected PersistentProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
      this(type, owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), world);
      this.setOwner(owner);
      if (owner instanceof PlayerEntity) {
         this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
      }
   }

   public void setSound(SoundEvent sound) {
      this.sound = sound;
   }

   @Override
   public boolean shouldRender(double distance) {
      double _snowman = this.getBoundingBox().getAverageSideLength() * 10.0;
      if (Double.isNaN(_snowman)) {
         _snowman = 1.0;
      }

      _snowman *= 64.0 * getRenderDistanceMultiplier();
      return distance < _snowman * _snowman;
   }

   @Override
   protected void initDataTracker() {
      this.dataTracker.startTracking(PROJECTILE_FLAGS, (byte)0);
      this.dataTracker.startTracking(PIERCE_LEVEL, (byte)0);
   }

   @Override
   public void setVelocity(double x, double y, double z, float speed, float divergence) {
      super.setVelocity(x, y, z, speed, divergence);
      this.life = 0;
   }

   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
      this.updatePosition(x, y, z);
      this.setRotation(yaw, pitch);
   }

   @Override
   public void setVelocityClient(double x, double y, double z) {
      super.setVelocityClient(x, y, z);
      this.life = 0;
   }

   @Override
   public void tick() {
      super.tick();
      boolean _snowman = this.isNoClip();
      Vec3d _snowmanx = this.getVelocity();
      if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
         float _snowmanxx = MathHelper.sqrt(squaredHorizontalLength(_snowmanx));
         this.yaw = (float)(MathHelper.atan2(_snowmanx.x, _snowmanx.z) * 180.0F / (float)Math.PI);
         this.pitch = (float)(MathHelper.atan2(_snowmanx.y, (double)_snowmanxx) * 180.0F / (float)Math.PI);
         this.prevYaw = this.yaw;
         this.prevPitch = this.pitch;
      }

      BlockPos _snowmanxx = this.getBlockPos();
      BlockState _snowmanxxx = this.world.getBlockState(_snowmanxx);
      if (!_snowmanxxx.isAir() && !_snowman) {
         VoxelShape _snowmanxxxx = _snowmanxxx.getCollisionShape(this.world, _snowmanxx);
         if (!_snowmanxxxx.isEmpty()) {
            Vec3d _snowmanxxxxx = this.getPos();

            for (Box _snowmanxxxxxx : _snowmanxxxx.getBoundingBoxes()) {
               if (_snowmanxxxxxx.offset(_snowmanxx).contains(_snowmanxxxxx)) {
                  this.inGround = true;
                  break;
               }
            }
         }
      }

      if (this.shake > 0) {
         this.shake--;
      }

      if (this.isTouchingWaterOrRain()) {
         this.extinguish();
      }

      if (this.inGround && !_snowman) {
         if (this.inBlockState != _snowmanxxx && this.method_26351()) {
            this.method_26352();
         } else if (!this.world.isClient) {
            this.age();
         }

         this.inGroundTime++;
      } else {
         this.inGroundTime = 0;
         Vec3d _snowmanxxxx = this.getPos();
         Vec3d _snowmanxxxxx = _snowmanxxxx.add(_snowmanx);
         HitResult _snowmanxxxxxxx = this.world.raycast(new RaycastContext(_snowmanxxxx, _snowmanxxxxx, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
         if (_snowmanxxxxxxx.getType() != HitResult.Type.MISS) {
            _snowmanxxxxx = _snowmanxxxxxxx.getPos();
         }

         while (!this.removed) {
            EntityHitResult _snowmanxxxxxxxx = this.getEntityCollision(_snowmanxxxx, _snowmanxxxxx);
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxx = _snowmanxxxxxxxx;
            }

            if (_snowmanxxxxxxx != null && _snowmanxxxxxxx.getType() == HitResult.Type.ENTITY) {
               Entity _snowmanxxxxxxxxx = ((EntityHitResult)_snowmanxxxxxxx).getEntity();
               Entity _snowmanxxxxxxxxxx = this.getOwner();
               if (_snowmanxxxxxxxxx instanceof PlayerEntity
                  && _snowmanxxxxxxxxxx instanceof PlayerEntity
                  && !((PlayerEntity)_snowmanxxxxxxxxxx).shouldDamagePlayer((PlayerEntity)_snowmanxxxxxxxxx)) {
                  _snowmanxxxxxxx = null;
                  _snowmanxxxxxxxx = null;
               }
            }

            if (_snowmanxxxxxxx != null && !_snowman) {
               this.onCollision(_snowmanxxxxxxx);
               this.velocityDirty = true;
            }

            if (_snowmanxxxxxxxx == null || this.getPierceLevel() <= 0) {
               break;
            }

            _snowmanxxxxxxx = null;
         }

         _snowmanx = this.getVelocity();
         double _snowmanxxxxxxxxx = _snowmanx.x;
         double _snowmanxxxxxxxxxx = _snowmanx.y;
         double _snowmanxxxxxxxxxxx = _snowmanx.z;
         if (this.isCritical()) {
            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxx++) {
               this.world
                  .addParticle(
                     ParticleTypes.CRIT,
                     this.getX() + _snowmanxxxxxxxxx * (double)_snowmanxxxxxxxxxxxx / 4.0,
                     this.getY() + _snowmanxxxxxxxxxx * (double)_snowmanxxxxxxxxxxxx / 4.0,
                     this.getZ() + _snowmanxxxxxxxxxxx * (double)_snowmanxxxxxxxxxxxx / 4.0,
                     -_snowmanxxxxxxxxx,
                     -_snowmanxxxxxxxxxx + 0.2,
                     -_snowmanxxxxxxxxxxx
                  );
            }
         }

         double _snowmanxxxxxxxxxxxx = this.getX() + _snowmanxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxx = this.getY() + _snowmanxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxx = this.getZ() + _snowmanxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxx = MathHelper.sqrt(squaredHorizontalLength(_snowmanx));
         if (_snowman) {
            this.yaw = (float)(MathHelper.atan2(-_snowmanxxxxxxxxx, -_snowmanxxxxxxxxxxx) * 180.0F / (float)Math.PI);
         } else {
            this.yaw = (float)(MathHelper.atan2(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx) * 180.0F / (float)Math.PI);
         }

         this.pitch = (float)(MathHelper.atan2(_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxx) * 180.0F / (float)Math.PI);
         this.pitch = updateRotation(this.prevPitch, this.pitch);
         this.yaw = updateRotation(this.prevYaw, this.yaw);
         float _snowmanxxxxxxxxxxxxxxxx = 0.99F;
         float _snowmanxxxxxxxxxxxxxxxxx = 0.05F;
         if (this.isTouchingWater()) {
            for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxxxxxxxxxx = 0.25F;
               this.world
                  .addParticle(
                     ParticleTypes.BUBBLE,
                     _snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxx * 0.25,
                     _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxx * 0.25,
                     _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxx * 0.25,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxxxxxx,
                     _snowmanxxxxxxxxxxx
                  );
            }

            _snowmanxxxxxxxxxxxxxxxx = this.getDragInWater();
         }

         this.setVelocity(_snowmanx.multiply((double)_snowmanxxxxxxxxxxxxxxxx));
         if (!this.hasNoGravity() && !_snowman) {
            Vec3d _snowmanxxxxxxxxxxxxxxxxxx = this.getVelocity();
            this.setVelocity(_snowmanxxxxxxxxxxxxxxxxxx.x, _snowmanxxxxxxxxxxxxxxxxxx.y - 0.05F, _snowmanxxxxxxxxxxxxxxxxxx.z);
         }

         this.updatePosition(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
         this.checkBlockCollision();
      }
   }

   private boolean method_26351() {
      return this.inGround && this.world.isSpaceEmpty(new Box(this.getPos(), this.getPos()).expand(0.06));
   }

   private void method_26352() {
      this.inGround = false;
      Vec3d _snowman = this.getVelocity();
      this.setVelocity(_snowman.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
      this.life = 0;
   }

   @Override
   public void move(MovementType type, Vec3d movement) {
      super.move(type, movement);
      if (type != MovementType.SELF && this.method_26351()) {
         this.method_26352();
      }
   }

   protected void age() {
      this.life++;
      if (this.life >= 1200) {
         this.remove();
      }
   }

   private void clearPiercingStatus() {
      if (this.piercingKilledEntities != null) {
         this.piercingKilledEntities.clear();
      }

      if (this.piercedEntities != null) {
         this.piercedEntities.clear();
      }
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      Entity _snowman = entityHitResult.getEntity();
      float _snowmanx = (float)this.getVelocity().length();
      int _snowmanxx = MathHelper.ceil(MathHelper.clamp((double)_snowmanx * this.damage, 0.0, 2.147483647E9));
      if (this.getPierceLevel() > 0) {
         if (this.piercedEntities == null) {
            this.piercedEntities = new IntOpenHashSet(5);
         }

         if (this.piercingKilledEntities == null) {
            this.piercingKilledEntities = Lists.newArrayListWithCapacity(5);
         }

         if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
            this.remove();
            return;
         }

         this.piercedEntities.add(_snowman.getEntityId());
      }

      if (this.isCritical()) {
         long _snowmanxxx = (long)this.random.nextInt(_snowmanxx / 2 + 2);
         _snowmanxx = (int)Math.min(_snowmanxxx + (long)_snowmanxx, 2147483647L);
      }

      Entity _snowmanxxx = this.getOwner();
      DamageSource _snowmanxxxx;
      if (_snowmanxxx == null) {
         _snowmanxxxx = DamageSource.arrow(this, this);
      } else {
         _snowmanxxxx = DamageSource.arrow(this, _snowmanxxx);
         if (_snowmanxxx instanceof LivingEntity) {
            ((LivingEntity)_snowmanxxx).onAttacking(_snowman);
         }
      }

      boolean _snowmanxxxxx = _snowman.getType() == EntityType.ENDERMAN;
      int _snowmanxxxxxx = _snowman.getFireTicks();
      if (this.isOnFire() && !_snowmanxxxxx) {
         _snowman.setOnFireFor(5);
      }

      if (_snowman.damage(_snowmanxxxx, (float)_snowmanxx)) {
         if (_snowmanxxxxx) {
            return;
         }

         if (_snowman instanceof LivingEntity) {
            LivingEntity _snowmanxxxxxxx = (LivingEntity)_snowman;
            if (!this.world.isClient && this.getPierceLevel() <= 0) {
               _snowmanxxxxxxx.setStuckArrowCount(_snowmanxxxxxxx.getStuckArrowCount() + 1);
            }

            if (this.punch > 0) {
               Vec3d _snowmanxxxxxxxx = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply((double)this.punch * 0.6);
               if (_snowmanxxxxxxxx.lengthSquared() > 0.0) {
                  _snowmanxxxxxxx.addVelocity(_snowmanxxxxxxxx.x, 0.1, _snowmanxxxxxxxx.z);
               }
            }

            if (!this.world.isClient && _snowmanxxx instanceof LivingEntity) {
               EnchantmentHelper.onUserDamaged(_snowmanxxxxxxx, _snowmanxxx);
               EnchantmentHelper.onTargetDamaged((LivingEntity)_snowmanxxx, _snowmanxxxxxxx);
            }

            this.onHit(_snowmanxxxxxxx);
            if (_snowmanxxx != null && _snowmanxxxxxxx != _snowmanxxx && _snowmanxxxxxxx instanceof PlayerEntity && _snowmanxxx instanceof ServerPlayerEntity && !this.isSilent()) {
               ((ServerPlayerEntity)_snowmanxxx).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
            }

            if (!_snowman.isAlive() && this.piercingKilledEntities != null) {
               this.piercingKilledEntities.add(_snowmanxxxxxxx);
            }

            if (!this.world.isClient && _snowmanxxx instanceof ServerPlayerEntity) {
               ServerPlayerEntity _snowmanxxxxxxxx = (ServerPlayerEntity)_snowmanxxx;
               if (this.piercingKilledEntities != null && this.isShotFromCrossbow()) {
                  Criteria.KILLED_BY_CROSSBOW.trigger(_snowmanxxxxxxxx, this.piercingKilledEntities);
               } else if (!_snowman.isAlive() && this.isShotFromCrossbow()) {
                  Criteria.KILLED_BY_CROSSBOW.trigger(_snowmanxxxxxxxx, Arrays.asList(_snowman));
               }
            }
         }

         this.playSound(this.sound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
         if (this.getPierceLevel() <= 0) {
            this.remove();
         }
      } else {
         _snowman.setFireTicks(_snowmanxxxxxx);
         this.setVelocity(this.getVelocity().multiply(-0.1));
         this.yaw += 180.0F;
         this.prevYaw += 180.0F;
         if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
            if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
               this.dropStack(this.asItemStack(), 0.1F);
            }

            this.remove();
         }
      }
   }

   @Override
   protected void onBlockHit(BlockHitResult blockHitResult) {
      this.inBlockState = this.world.getBlockState(blockHitResult.getBlockPos());
      super.onBlockHit(blockHitResult);
      Vec3d _snowman = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
      this.setVelocity(_snowman);
      Vec3d _snowmanx = _snowman.normalize().multiply(0.05F);
      this.setPos(this.getX() - _snowmanx.x, this.getY() - _snowmanx.y, this.getZ() - _snowmanx.z);
      this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
      this.inGround = true;
      this.shake = 7;
      this.setCritical(false);
      this.setPierceLevel((byte)0);
      this.setSound(SoundEvents.ENTITY_ARROW_HIT);
      this.setShotFromCrossbow(false);
      this.clearPiercingStatus();
   }

   protected SoundEvent getHitSound() {
      return SoundEvents.ENTITY_ARROW_HIT;
   }

   protected final SoundEvent getSound() {
      return this.sound;
   }

   protected void onHit(LivingEntity target) {
   }

   @Nullable
   protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
      return ProjectileUtil.getEntityCollision(
         this.world, this, currentPosition, nextPosition, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), this::method_26958
      );
   }

   @Override
   protected boolean method_26958(Entity _snowman) {
      return super.method_26958(_snowman) && (this.piercedEntities == null || !this.piercedEntities.contains(_snowman.getEntityId()));
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putShort("life", (short)this.life);
      if (this.inBlockState != null) {
         tag.put("inBlockState", NbtHelper.fromBlockState(this.inBlockState));
      }

      tag.putByte("shake", (byte)this.shake);
      tag.putBoolean("inGround", this.inGround);
      tag.putByte("pickup", (byte)this.pickupType.ordinal());
      tag.putDouble("damage", this.damage);
      tag.putBoolean("crit", this.isCritical());
      tag.putByte("PierceLevel", this.getPierceLevel());
      tag.putString("SoundEvent", Registry.SOUND_EVENT.getId(this.sound).toString());
      tag.putBoolean("ShotFromCrossbow", this.isShotFromCrossbow());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.life = tag.getShort("life");
      if (tag.contains("inBlockState", 10)) {
         this.inBlockState = NbtHelper.toBlockState(tag.getCompound("inBlockState"));
      }

      this.shake = tag.getByte("shake") & 255;
      this.inGround = tag.getBoolean("inGround");
      if (tag.contains("damage", 99)) {
         this.damage = tag.getDouble("damage");
      }

      if (tag.contains("pickup", 99)) {
         this.pickupType = PersistentProjectileEntity.PickupPermission.fromOrdinal(tag.getByte("pickup"));
      } else if (tag.contains("player", 99)) {
         this.pickupType = tag.getBoolean("player")
            ? PersistentProjectileEntity.PickupPermission.ALLOWED
            : PersistentProjectileEntity.PickupPermission.DISALLOWED;
      }

      this.setCritical(tag.getBoolean("crit"));
      this.setPierceLevel(tag.getByte("PierceLevel"));
      if (tag.contains("SoundEvent", 8)) {
         this.sound = Registry.SOUND_EVENT.getOrEmpty(new Identifier(tag.getString("SoundEvent"))).orElse(this.getHitSound());
      }

      this.setShotFromCrossbow(tag.getBoolean("ShotFromCrossbow"));
   }

   @Override
   public void setOwner(@Nullable Entity entity) {
      super.setOwner(entity);
      if (entity instanceof PlayerEntity) {
         this.pickupType = ((PlayerEntity)entity).abilities.creativeMode
            ? PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY
            : PersistentProjectileEntity.PickupPermission.ALLOWED;
      }
   }

   @Override
   public void onPlayerCollision(PlayerEntity player) {
      if (!this.world.isClient && (this.inGround || this.isNoClip()) && this.shake <= 0) {
         boolean _snowman = this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED
            || this.pickupType == PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY && player.abilities.creativeMode
            || this.isNoClip() && this.getOwner().getUuid() == player.getUuid();
         if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED && !player.inventory.insertStack(this.asItemStack())) {
            _snowman = false;
         }

         if (_snowman) {
            player.sendPickup(this, 1);
            this.remove();
         }
      }
   }

   protected abstract ItemStack asItemStack();

   @Override
   protected boolean canClimb() {
      return false;
   }

   public void setDamage(double damage) {
      this.damage = damage;
   }

   public double getDamage() {
      return this.damage;
   }

   public void setPunch(int punch) {
      this.punch = punch;
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   @Override
   protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 0.13F;
   }

   public void setCritical(boolean critical) {
      this.setProjectileFlag(1, critical);
   }

   public void setPierceLevel(byte level) {
      this.dataTracker.set(PIERCE_LEVEL, level);
   }

   private void setProjectileFlag(int index, boolean flag) {
      byte _snowman = this.dataTracker.get(PROJECTILE_FLAGS);
      if (flag) {
         this.dataTracker.set(PROJECTILE_FLAGS, (byte)(_snowman | index));
      } else {
         this.dataTracker.set(PROJECTILE_FLAGS, (byte)(_snowman & ~index));
      }
   }

   public boolean isCritical() {
      byte _snowman = this.dataTracker.get(PROJECTILE_FLAGS);
      return (_snowman & 1) != 0;
   }

   public boolean isShotFromCrossbow() {
      byte _snowman = this.dataTracker.get(PROJECTILE_FLAGS);
      return (_snowman & 4) != 0;
   }

   public byte getPierceLevel() {
      return this.dataTracker.get(PIERCE_LEVEL);
   }

   public void applyEnchantmentEffects(LivingEntity entity, float damageModifier) {
      int _snowman = EnchantmentHelper.getEquipmentLevel(Enchantments.POWER, entity);
      int _snowmanx = EnchantmentHelper.getEquipmentLevel(Enchantments.PUNCH, entity);
      this.setDamage((double)(damageModifier * 2.0F) + this.random.nextGaussian() * 0.25 + (double)((float)this.world.getDifficulty().getId() * 0.11F));
      if (_snowman > 0) {
         this.setDamage(this.getDamage() + (double)_snowman * 0.5 + 0.5);
      }

      if (_snowmanx > 0) {
         this.setPunch(_snowmanx);
      }

      if (EnchantmentHelper.getEquipmentLevel(Enchantments.FLAME, entity) > 0) {
         this.setOnFireFor(100);
      }
   }

   protected float getDragInWater() {
      return 0.6F;
   }

   public void setNoClip(boolean noClip) {
      this.noClip = noClip;
      this.setProjectileFlag(2, noClip);
   }

   public boolean isNoClip() {
      return !this.world.isClient ? this.noClip : (this.dataTracker.get(PROJECTILE_FLAGS) & 2) != 0;
   }

   public void setShotFromCrossbow(boolean shotFromCrossbow) {
      this.setProjectileFlag(4, shotFromCrossbow);
   }

   @Override
   public Packet<?> createSpawnPacket() {
      Entity _snowman = this.getOwner();
      return new EntitySpawnS2CPacket(this, _snowman == null ? 0 : _snowman.getEntityId());
   }

   public static enum PickupPermission {
      DISALLOWED,
      ALLOWED,
      CREATIVE_ONLY;

      private PickupPermission() {
      }

      public static PersistentProjectileEntity.PickupPermission fromOrdinal(int ordinal) {
         if (ordinal < 0 || ordinal > values().length) {
            ordinal = 0;
         }

         return values()[ordinal];
      }
   }
}
