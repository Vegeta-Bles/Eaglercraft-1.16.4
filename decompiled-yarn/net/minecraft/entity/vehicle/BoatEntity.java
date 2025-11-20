package net.minecraft.entity.vehicle;

import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.class_5459;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Dismounting;
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
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BoatEntity extends Entity {
   private static final TrackedData<Integer> DAMAGE_WOBBLE_TICKS = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> DAMAGE_WOBBLE_SIDE = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Float> DAMAGE_WOBBLE_STRENGTH = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.FLOAT);
   private static final TrackedData<Integer> BOAT_TYPE = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> LEFT_PADDLE_MOVING = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Boolean> RIGHT_PADDLE_MOVING = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> BUBBLE_WOBBLE_TICKS = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private final float[] paddlePhases = new float[2];
   private float velocityDecay;
   private float ticksUnderwater;
   private float yawVelocity;
   private int field_7708;
   private double x;
   private double y;
   private double z;
   private double boatYaw;
   private double boatPitch;
   private boolean pressingLeft;
   private boolean pressingRight;
   private boolean pressingForward;
   private boolean pressingBack;
   private double waterLevel;
   private float field_7714;
   private BoatEntity.Location location;
   private BoatEntity.Location lastLocation;
   private double fallVelocity;
   private boolean onBubbleColumnSurface;
   private boolean bubbleColumnIsDrag;
   private float bubbleWobbleStrength;
   private float bubbleWobble;
   private float lastBubbleWobble;

   public BoatEntity(EntityType<? extends BoatEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.inanimate = true;
   }

   public BoatEntity(World world, double x, double y, double z) {
      this(EntityType.BOAT, world);
      this.updatePosition(x, y, z);
      this.setVelocity(Vec3d.ZERO);
      this.prevX = x;
      this.prevY = y;
      this.prevZ = z;
   }

   @Override
   protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height;
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   protected void initDataTracker() {
      this.dataTracker.startTracking(DAMAGE_WOBBLE_TICKS, 0);
      this.dataTracker.startTracking(DAMAGE_WOBBLE_SIDE, 1);
      this.dataTracker.startTracking(DAMAGE_WOBBLE_STRENGTH, 0.0F);
      this.dataTracker.startTracking(BOAT_TYPE, BoatEntity.Type.OAK.ordinal());
      this.dataTracker.startTracking(LEFT_PADDLE_MOVING, false);
      this.dataTracker.startTracking(RIGHT_PADDLE_MOVING, false);
      this.dataTracker.startTracking(BUBBLE_WOBBLE_TICKS, 0);
   }

   @Override
   public boolean collidesWith(Entity other) {
      return method_30959(this, other);
   }

   public static boolean method_30959(Entity _snowman, Entity _snowman) {
      return (_snowman.isCollidable() || _snowman.isPushable()) && !_snowman.isConnectedThroughVehicle(_snowman);
   }

   @Override
   public boolean isCollidable() {
      return true;
   }

   @Override
   public boolean isPushable() {
      return true;
   }

   @Override
   protected Vec3d method_30633(Direction.Axis _snowman, class_5459.class_5460 _snowman) {
      return LivingEntity.method_31079(super.method_30633(_snowman, _snowman));
   }

   @Override
   public double getMountedHeightOffset() {
      return -0.1;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (!this.world.isClient && !this.removed) {
         this.setDamageWobbleSide(-this.getDamageWobbleSide());
         this.setDamageWobbleTicks(10);
         this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0F);
         this.scheduleVelocityUpdate();
         boolean _snowman = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).abilities.creativeMode;
         if (_snowman || this.getDamageWobbleStrength() > 40.0F) {
            if (!_snowman && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
               this.dropItem(this.asItem());
            }

            this.remove();
         }

         return true;
      } else {
         return true;
      }
   }

   @Override
   public void onBubbleColumnSurfaceCollision(boolean drag) {
      if (!this.world.isClient) {
         this.onBubbleColumnSurface = true;
         this.bubbleColumnIsDrag = drag;
         if (this.getBubbleWobbleTicks() == 0) {
            this.setBubbleWobbleTicks(60);
         }
      }

      this.world
         .addParticle(
            ParticleTypes.SPLASH,
            this.getX() + (double)this.random.nextFloat(),
            this.getY() + 0.7,
            this.getZ() + (double)this.random.nextFloat(),
            0.0,
            0.0,
            0.0
         );
      if (this.random.nextInt(20) == 0) {
         this.world
            .playSound(
               this.getX(), this.getY(), this.getZ(), this.getSplashSound(), this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false
            );
      }
   }

   @Override
   public void pushAwayFrom(Entity entity) {
      if (entity instanceof BoatEntity) {
         if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
            super.pushAwayFrom(entity);
         }
      } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
         super.pushAwayFrom(entity);
      }
   }

   public Item asItem() {
      switch (this.getBoatType()) {
         case OAK:
         default:
            return Items.OAK_BOAT;
         case SPRUCE:
            return Items.SPRUCE_BOAT;
         case BIRCH:
            return Items.BIRCH_BOAT;
         case JUNGLE:
            return Items.JUNGLE_BOAT;
         case ACACIA:
            return Items.ACACIA_BOAT;
         case DARK_OAK:
            return Items.DARK_OAK_BOAT;
      }
   }

   @Override
   public void animateDamage() {
      this.setDamageWobbleSide(-this.getDamageWobbleSide());
      this.setDamageWobbleTicks(10);
      this.setDamageWobbleStrength(this.getDamageWobbleStrength() * 11.0F);
   }

   @Override
   public boolean collides() {
      return !this.removed;
   }

   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.boatYaw = (double)yaw;
      this.boatPitch = (double)pitch;
      this.field_7708 = 10;
   }

   @Override
   public Direction getMovementDirection() {
      return this.getHorizontalFacing().rotateYClockwise();
   }

   @Override
   public void tick() {
      this.lastLocation = this.location;
      this.location = this.checkLocation();
      if (this.location != BoatEntity.Location.UNDER_WATER && this.location != BoatEntity.Location.UNDER_FLOWING_WATER) {
         this.ticksUnderwater = 0.0F;
      } else {
         this.ticksUnderwater++;
      }

      if (!this.world.isClient && this.ticksUnderwater >= 60.0F) {
         this.removeAllPassengers();
      }

      if (this.getDamageWobbleTicks() > 0) {
         this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
      }

      if (this.getDamageWobbleStrength() > 0.0F) {
         this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0F);
      }

      super.tick();
      this.method_7555();
      if (this.isLogicalSideForUpdatingMovement()) {
         if (this.getPassengerList().isEmpty() || !(this.getPassengerList().get(0) instanceof PlayerEntity)) {
            this.setPaddleMovings(false, false);
         }

         this.updateVelocity();
         if (this.world.isClient) {
            this.updatePaddles();
            this.world.sendPacket(new BoatPaddleStateC2SPacket(this.isPaddleMoving(0), this.isPaddleMoving(1)));
         }

         this.move(MovementType.SELF, this.getVelocity());
      } else {
         this.setVelocity(Vec3d.ZERO);
      }

      this.handleBubbleColumn();

      for (int _snowman = 0; _snowman <= 1; _snowman++) {
         if (this.isPaddleMoving(_snowman)) {
            if (!this.isSilent()
               && (double)(this.paddlePhases[_snowman] % (float) (Math.PI * 2)) <= (float) (Math.PI / 4)
               && ((double)this.paddlePhases[_snowman] + (float) (Math.PI / 8)) % (float) (Math.PI * 2) >= (float) (Math.PI / 4)) {
               SoundEvent _snowmanx = this.getPaddleSoundEvent();
               if (_snowmanx != null) {
                  Vec3d _snowmanxx = this.getRotationVec(1.0F);
                  double _snowmanxxx = _snowman == 1 ? -_snowmanxx.z : _snowmanxx.z;
                  double _snowmanxxxx = _snowman == 1 ? _snowmanxx.x : -_snowmanxx.x;
                  this.world
                     .playSound(
                        null, this.getX() + _snowmanxxx, this.getY(), this.getZ() + _snowmanxxxx, _snowmanx, this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.random.nextFloat()
                     );
               }
            }

            this.paddlePhases[_snowman] = (float)((double)this.paddlePhases[_snowman] + (float) (Math.PI / 8));
         } else {
            this.paddlePhases[_snowman] = 0.0F;
         }
      }

      this.checkBlockCollision();
      List<Entity> _snowmanx = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2F, -0.01F, 0.2F), EntityPredicates.canBePushedBy(this));
      if (!_snowmanx.isEmpty()) {
         boolean _snowmanxx = !this.world.isClient && !(this.getPrimaryPassenger() instanceof PlayerEntity);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.size(); _snowmanxxx++) {
            Entity _snowmanxxxx = _snowmanx.get(_snowmanxxx);
            if (!_snowmanxxxx.hasPassenger(this)) {
               if (_snowmanxx
                  && this.getPassengerList().size() < 2
                  && !_snowmanxxxx.hasVehicle()
                  && _snowmanxxxx.getWidth() < this.getWidth()
                  && _snowmanxxxx instanceof LivingEntity
                  && !(_snowmanxxxx instanceof WaterCreatureEntity)
                  && !(_snowmanxxxx instanceof PlayerEntity)) {
                  _snowmanxxxx.startRiding(this);
               } else {
                  this.pushAwayFrom(_snowmanxxxx);
               }
            }
         }
      }
   }

   private void handleBubbleColumn() {
      if (this.world.isClient) {
         int _snowman = this.getBubbleWobbleTicks();
         if (_snowman > 0) {
            this.bubbleWobbleStrength += 0.05F;
         } else {
            this.bubbleWobbleStrength -= 0.1F;
         }

         this.bubbleWobbleStrength = MathHelper.clamp(this.bubbleWobbleStrength, 0.0F, 1.0F);
         this.lastBubbleWobble = this.bubbleWobble;
         this.bubbleWobble = 10.0F * (float)Math.sin((double)(0.5F * (float)this.world.getTime())) * this.bubbleWobbleStrength;
      } else {
         if (!this.onBubbleColumnSurface) {
            this.setBubbleWobbleTicks(0);
         }

         int _snowman = this.getBubbleWobbleTicks();
         if (_snowman > 0) {
            this.setBubbleWobbleTicks(--_snowman);
            int _snowmanx = 60 - _snowman - 1;
            if (_snowmanx > 0 && _snowman == 0) {
               this.setBubbleWobbleTicks(0);
               Vec3d _snowmanxx = this.getVelocity();
               if (this.bubbleColumnIsDrag) {
                  this.setVelocity(_snowmanxx.add(0.0, -0.7, 0.0));
                  this.removeAllPassengers();
               } else {
                  this.setVelocity(_snowmanxx.x, this.hasPassengerType(PlayerEntity.class) ? 2.7 : 0.6, _snowmanxx.z);
               }
            }

            this.onBubbleColumnSurface = false;
         }
      }
   }

   @Nullable
   protected SoundEvent getPaddleSoundEvent() {
      switch (this.checkLocation()) {
         case IN_WATER:
         case UNDER_WATER:
         case UNDER_FLOWING_WATER:
            return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
         case ON_LAND:
            return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
         case IN_AIR:
         default:
            return null;
      }
   }

   private void method_7555() {
      if (this.isLogicalSideForUpdatingMovement()) {
         this.field_7708 = 0;
         this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
      }

      if (this.field_7708 > 0) {
         double _snowman = this.getX() + (this.x - this.getX()) / (double)this.field_7708;
         double _snowmanx = this.getY() + (this.y - this.getY()) / (double)this.field_7708;
         double _snowmanxx = this.getZ() + (this.z - this.getZ()) / (double)this.field_7708;
         double _snowmanxxx = MathHelper.wrapDegrees(this.boatYaw - (double)this.yaw);
         this.yaw = (float)((double)this.yaw + _snowmanxxx / (double)this.field_7708);
         this.pitch = (float)((double)this.pitch + (this.boatPitch - (double)this.pitch) / (double)this.field_7708);
         this.field_7708--;
         this.updatePosition(_snowman, _snowmanx, _snowmanxx);
         this.setRotation(this.yaw, this.pitch);
      }
   }

   public void setPaddleMovings(boolean leftMoving, boolean rightMoving) {
      this.dataTracker.set(LEFT_PADDLE_MOVING, leftMoving);
      this.dataTracker.set(RIGHT_PADDLE_MOVING, rightMoving);
   }

   public float interpolatePaddlePhase(int paddle, float tickDelta) {
      return this.isPaddleMoving(paddle)
         ? (float)MathHelper.clampedLerp((double)this.paddlePhases[paddle] - (float) (Math.PI / 8), (double)this.paddlePhases[paddle], (double)tickDelta)
         : 0.0F;
   }

   private BoatEntity.Location checkLocation() {
      BoatEntity.Location _snowman = this.getUnderWaterLocation();
      if (_snowman != null) {
         this.waterLevel = this.getBoundingBox().maxY;
         return _snowman;
      } else if (this.checkBoatInWater()) {
         return BoatEntity.Location.IN_WATER;
      } else {
         float _snowmanx = this.method_7548();
         if (_snowmanx > 0.0F) {
            this.field_7714 = _snowmanx;
            return BoatEntity.Location.ON_LAND;
         } else {
            return BoatEntity.Location.IN_AIR;
         }
      }
   }

   public float method_7544() {
      Box _snowman = this.getBoundingBox();
      int _snowmanx = MathHelper.floor(_snowman.minX);
      int _snowmanxx = MathHelper.ceil(_snowman.maxX);
      int _snowmanxxx = MathHelper.floor(_snowman.maxY);
      int _snowmanxxxx = MathHelper.ceil(_snowman.maxY - this.fallVelocity);
      int _snowmanxxxxx = MathHelper.floor(_snowman.minZ);
      int _snowmanxxxxxx = MathHelper.ceil(_snowman.maxZ);
      BlockPos.Mutable _snowmanxxxxxxx = new BlockPos.Mutable();

      label39:
      for (int _snowmanxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxx++) {
         float _snowmanxxxxxxxxx = 0.0F;

         for (int _snowmanxxxxxxxxxx = _snowmanx; _snowmanxxxxxxxxxx < _snowmanxx; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
               _snowmanxxxxxxx.set(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
               FluidState _snowmanxxxxxxxxxxxx = this.world.getFluidState(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxxxxxx.isIn(FluidTags.WATER)) {
                  _snowmanxxxxxxxxx = Math.max(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx.getHeight(this.world, _snowmanxxxxxxx));
               }

               if (_snowmanxxxxxxxxx >= 1.0F) {
                  continue label39;
               }
            }
         }

         if (_snowmanxxxxxxxxx < 1.0F) {
            return (float)_snowmanxxxxxxx.getY() + _snowmanxxxxxxxxx;
         }
      }

      return (float)(_snowmanxxxx + 1);
   }

   public float method_7548() {
      Box _snowman = this.getBoundingBox();
      Box _snowmanx = new Box(_snowman.minX, _snowman.minY - 0.001, _snowman.minZ, _snowman.maxX, _snowman.minY, _snowman.maxZ);
      int _snowmanxx = MathHelper.floor(_snowmanx.minX) - 1;
      int _snowmanxxx = MathHelper.ceil(_snowmanx.maxX) + 1;
      int _snowmanxxxx = MathHelper.floor(_snowmanx.minY) - 1;
      int _snowmanxxxxx = MathHelper.ceil(_snowmanx.maxY) + 1;
      int _snowmanxxxxxx = MathHelper.floor(_snowmanx.minZ) - 1;
      int _snowmanxxxxxxx = MathHelper.ceil(_snowmanx.maxZ) + 1;
      VoxelShape _snowmanxxxxxxxx = VoxelShapes.cuboid(_snowmanx);
      float _snowmanxxxxxxxxx = 0.0F;
      int _snowmanxxxxxxxxxx = 0;
      BlockPos.Mutable _snowmanxxxxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx != _snowmanxx && _snowmanxxxxxxxxxxxx != _snowmanxxx - 1 ? 0 : 1)
               + (_snowmanxxxxxxxxxxxxx != _snowmanxxxxxx && _snowmanxxxxxxxxxxxxx != _snowmanxxxxxxx - 1 ? 0 : 1);
            if (_snowmanxxxxxxxxxxxxxx != 2) {
               for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxxxxxxx <= 0 || _snowmanxxxxxxxxxxxxxxx != _snowmanxxxx && _snowmanxxxxxxxxxxxxxxx != _snowmanxxxxx - 1) {
                     _snowmanxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                     BlockState _snowmanxxxxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxxx);
                     if (!(_snowmanxxxxxxxxxxxxxxxx.getBlock() instanceof LilyPadBlock)
                        && VoxelShapes.matchesAnywhere(
                           _snowmanxxxxxxxxxxxxxxxx.getCollisionShape(this.world, _snowmanxxxxxxxxxxx)
                              .offset((double)_snowmanxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxx),
                           _snowmanxxxxxxxx,
                           BooleanBiFunction.AND
                        )) {
                        _snowmanxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxx.getBlock().getSlipperiness();
                        _snowmanxxxxxxxxxx++;
                     }
                  }
               }
            }
         }
      }

      return _snowmanxxxxxxxxx / (float)_snowmanxxxxxxxxxx;
   }

   private boolean checkBoatInWater() {
      Box _snowman = this.getBoundingBox();
      int _snowmanx = MathHelper.floor(_snowman.minX);
      int _snowmanxx = MathHelper.ceil(_snowman.maxX);
      int _snowmanxxx = MathHelper.floor(_snowman.minY);
      int _snowmanxxxx = MathHelper.ceil(_snowman.minY + 0.001);
      int _snowmanxxxxx = MathHelper.floor(_snowman.minZ);
      int _snowmanxxxxxx = MathHelper.ceil(_snowman.maxZ);
      boolean _snowmanxxxxxxx = false;
      this.waterLevel = Double.MIN_VALUE;
      BlockPos.Mutable _snowmanxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxx = _snowmanx; _snowmanxxxxxxxxx < _snowmanxx; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
               _snowmanxxxxxxxx.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               FluidState _snowmanxxxxxxxxxxxx = this.world.getFluidState(_snowmanxxxxxxxx);
               if (_snowmanxxxxxxxxxxxx.isIn(FluidTags.WATER)) {
                  float _snowmanxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxx.getHeight(this.world, _snowmanxxxxxxxx);
                  this.waterLevel = Math.max((double)_snowmanxxxxxxxxxxxxx, this.waterLevel);
                  _snowmanxxxxxxx |= _snowman.minY < (double)_snowmanxxxxxxxxxxxxx;
               }
            }
         }
      }

      return _snowmanxxxxxxx;
   }

   @Nullable
   private BoatEntity.Location getUnderWaterLocation() {
      Box _snowman = this.getBoundingBox();
      double _snowmanx = _snowman.maxY + 0.001;
      int _snowmanxx = MathHelper.floor(_snowman.minX);
      int _snowmanxxx = MathHelper.ceil(_snowman.maxX);
      int _snowmanxxxx = MathHelper.floor(_snowman.maxY);
      int _snowmanxxxxx = MathHelper.ceil(_snowmanx);
      int _snowmanxxxxxx = MathHelper.floor(_snowman.minZ);
      int _snowmanxxxxxxx = MathHelper.ceil(_snowman.maxZ);
      boolean _snowmanxxxxxxxx = false;
      BlockPos.Mutable _snowmanxxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx++) {
               _snowmanxxxxxxxxx.set(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               FluidState _snowmanxxxxxxxxxxxxx = this.world.getFluidState(_snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxx.isIn(FluidTags.WATER) && _snowmanx < (double)((float)_snowmanxxxxxxxxx.getY() + _snowmanxxxxxxxxxxxxx.getHeight(this.world, _snowmanxxxxxxxxx))) {
                  if (!_snowmanxxxxxxxxxxxxx.isStill()) {
                     return BoatEntity.Location.UNDER_FLOWING_WATER;
                  }

                  _snowmanxxxxxxxx = true;
               }
            }
         }
      }

      return _snowmanxxxxxxxx ? BoatEntity.Location.UNDER_WATER : null;
   }

   private void updateVelocity() {
      double _snowman = -0.04F;
      double _snowmanx = this.hasNoGravity() ? 0.0 : -0.04F;
      double _snowmanxx = 0.0;
      this.velocityDecay = 0.05F;
      if (this.lastLocation == BoatEntity.Location.IN_AIR && this.location != BoatEntity.Location.IN_AIR && this.location != BoatEntity.Location.ON_LAND) {
         this.waterLevel = this.getBodyY(1.0);
         this.updatePosition(this.getX(), (double)(this.method_7544() - this.getHeight()) + 0.101, this.getZ());
         this.setVelocity(this.getVelocity().multiply(1.0, 0.0, 1.0));
         this.fallVelocity = 0.0;
         this.location = BoatEntity.Location.IN_WATER;
      } else {
         if (this.location == BoatEntity.Location.IN_WATER) {
            _snowmanxx = (this.waterLevel - this.getY()) / (double)this.getHeight();
            this.velocityDecay = 0.9F;
         } else if (this.location == BoatEntity.Location.UNDER_FLOWING_WATER) {
            _snowmanx = -7.0E-4;
            this.velocityDecay = 0.9F;
         } else if (this.location == BoatEntity.Location.UNDER_WATER) {
            _snowmanxx = 0.01F;
            this.velocityDecay = 0.45F;
         } else if (this.location == BoatEntity.Location.IN_AIR) {
            this.velocityDecay = 0.9F;
         } else if (this.location == BoatEntity.Location.ON_LAND) {
            this.velocityDecay = this.field_7714;
            if (this.getPrimaryPassenger() instanceof PlayerEntity) {
               this.field_7714 /= 2.0F;
            }
         }

         Vec3d _snowmanxxx = this.getVelocity();
         this.setVelocity(_snowmanxxx.x * (double)this.velocityDecay, _snowmanxxx.y + _snowmanx, _snowmanxxx.z * (double)this.velocityDecay);
         this.yawVelocity = this.yawVelocity * this.velocityDecay;
         if (_snowmanxx > 0.0) {
            Vec3d _snowmanxxxx = this.getVelocity();
            this.setVelocity(_snowmanxxxx.x, (_snowmanxxxx.y + _snowmanxx * 0.06153846016296973) * 0.75, _snowmanxxxx.z);
         }
      }
   }

   private void updatePaddles() {
      if (this.hasPassengers()) {
         float _snowman = 0.0F;
         if (this.pressingLeft) {
            this.yawVelocity--;
         }

         if (this.pressingRight) {
            this.yawVelocity++;
         }

         if (this.pressingRight != this.pressingLeft && !this.pressingForward && !this.pressingBack) {
            _snowman += 0.005F;
         }

         this.yaw = this.yaw + this.yawVelocity;
         if (this.pressingForward) {
            _snowman += 0.04F;
         }

         if (this.pressingBack) {
            _snowman -= 0.005F;
         }

         this.setVelocity(
            this.getVelocity()
               .add(
                  (double)(MathHelper.sin(-this.yaw * (float) (Math.PI / 180.0)) * _snowman), 0.0, (double)(MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)) * _snowman)
               )
         );
         this.setPaddleMovings(
            this.pressingRight && !this.pressingLeft || this.pressingForward, this.pressingLeft && !this.pressingRight || this.pressingForward
         );
      }
   }

   @Override
   public void updatePassengerPosition(Entity passenger) {
      if (this.hasPassenger(passenger)) {
         float _snowman = 0.0F;
         float _snowmanx = (float)((this.removed ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());
         if (this.getPassengerList().size() > 1) {
            int _snowmanxx = this.getPassengerList().indexOf(passenger);
            if (_snowmanxx == 0) {
               _snowman = 0.2F;
            } else {
               _snowman = -0.6F;
            }

            if (passenger instanceof AnimalEntity) {
               _snowman = (float)((double)_snowman + 0.2);
            }
         }

         Vec3d _snowmanxxx = new Vec3d((double)_snowman, 0.0, 0.0).rotateY(-this.yaw * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
         passenger.updatePosition(this.getX() + _snowmanxxx.x, this.getY() + (double)_snowmanx, this.getZ() + _snowmanxxx.z);
         passenger.yaw = passenger.yaw + this.yawVelocity;
         passenger.setHeadYaw(passenger.getHeadYaw() + this.yawVelocity);
         this.copyEntityData(passenger);
         if (passenger instanceof AnimalEntity && this.getPassengerList().size() > 1) {
            int _snowmanxxxx = passenger.getEntityId() % 2 == 0 ? 90 : 270;
            passenger.setYaw(((AnimalEntity)passenger).bodyYaw + (float)_snowmanxxxx);
            passenger.setHeadYaw(passenger.getHeadYaw() + (float)_snowmanxxxx);
         }
      }
   }

   @Override
   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      Vec3d _snowman = getPassengerDismountOffset((double)(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO), (double)passenger.getWidth(), this.yaw);
      double _snowmanx = this.getX() + _snowman.x;
      double _snowmanxx = this.getZ() + _snowman.z;
      BlockPos _snowmanxxx = new BlockPos(_snowmanx, this.getBoundingBox().maxY, _snowmanxx);
      BlockPos _snowmanxxxx = _snowmanxxx.down();
      if (!this.world.isWater(_snowmanxxxx)) {
         double _snowmanxxxxx = (double)_snowmanxxx.getY() + this.world.getDismountHeight(_snowmanxxx);
         double _snowmanxxxxxx = (double)_snowmanxxx.getY() + this.world.getDismountHeight(_snowmanxxxx);
         UnmodifiableIterator var13 = passenger.getPoses().iterator();

         while (var13.hasNext()) {
            EntityPose _snowmanxxxxxxx = (EntityPose)var13.next();
            Vec3d _snowmanxxxxxxxx = Dismounting.findDismountPos(this.world, _snowmanx, _snowmanxxxxx, _snowmanxx, passenger, _snowmanxxxxxxx);
            if (_snowmanxxxxxxxx != null) {
               passenger.setPose(_snowmanxxxxxxx);
               return _snowmanxxxxxxxx;
            }

            Vec3d _snowmanxxxxxxxxx = Dismounting.findDismountPos(this.world, _snowmanx, _snowmanxxxxxx, _snowmanxx, passenger, _snowmanxxxxxxx);
            if (_snowmanxxxxxxxxx != null) {
               passenger.setPose(_snowmanxxxxxxx);
               return _snowmanxxxxxxxxx;
            }
         }
      }

      return super.updatePassengerForDismount(passenger);
   }

   protected void copyEntityData(Entity entity) {
      entity.setYaw(this.yaw);
      float _snowman = MathHelper.wrapDegrees(entity.yaw - this.yaw);
      float _snowmanx = MathHelper.clamp(_snowman, -105.0F, 105.0F);
      entity.prevYaw += _snowmanx - _snowman;
      entity.yaw += _snowmanx - _snowman;
      entity.setHeadYaw(entity.yaw);
   }

   @Override
   public void onPassengerLookAround(Entity passenger) {
      this.copyEntityData(passenger);
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
      tag.putString("Type", this.getBoatType().getName());
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
      if (tag.contains("Type", 8)) {
         this.setBoatType(BoatEntity.Type.getType(tag.getString("Type")));
      }
   }

   @Override
   public ActionResult interact(PlayerEntity player, Hand hand) {
      if (player.shouldCancelInteraction()) {
         return ActionResult.PASS;
      } else if (this.ticksUnderwater < 60.0F) {
         if (!this.world.isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
         } else {
            return ActionResult.SUCCESS;
         }
      } else {
         return ActionResult.PASS;
      }
   }

   @Override
   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
      this.fallVelocity = this.getVelocity().y;
      if (!this.hasVehicle()) {
         if (onGround) {
            if (this.fallDistance > 3.0F) {
               if (this.location != BoatEntity.Location.ON_LAND) {
                  this.fallDistance = 0.0F;
                  return;
               }

               this.handleFallDamage(this.fallDistance, 1.0F);
               if (!this.world.isClient && !this.removed) {
                  this.remove();
                  if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                     for (int _snowman = 0; _snowman < 3; _snowman++) {
                        this.dropItem(this.getBoatType().getBaseBlock());
                     }

                     for (int _snowman = 0; _snowman < 2; _snowman++) {
                        this.dropItem(Items.STICK);
                     }
                  }
               }
            }

            this.fallDistance = 0.0F;
         } else if (!this.world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER) && heightDifference < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - heightDifference);
         }
      }
   }

   public boolean isPaddleMoving(int paddle) {
      return this.dataTracker.get(paddle == 0 ? LEFT_PADDLE_MOVING : RIGHT_PADDLE_MOVING) && this.getPrimaryPassenger() != null;
   }

   public void setDamageWobbleStrength(float wobbleStrength) {
      this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, wobbleStrength);
   }

   public float getDamageWobbleStrength() {
      return this.dataTracker.get(DAMAGE_WOBBLE_STRENGTH);
   }

   public void setDamageWobbleTicks(int wobbleTicks) {
      this.dataTracker.set(DAMAGE_WOBBLE_TICKS, wobbleTicks);
   }

   public int getDamageWobbleTicks() {
      return this.dataTracker.get(DAMAGE_WOBBLE_TICKS);
   }

   private void setBubbleWobbleTicks(int wobbleTicks) {
      this.dataTracker.set(BUBBLE_WOBBLE_TICKS, wobbleTicks);
   }

   private int getBubbleWobbleTicks() {
      return this.dataTracker.get(BUBBLE_WOBBLE_TICKS);
   }

   public float interpolateBubbleWobble(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastBubbleWobble, this.bubbleWobble);
   }

   public void setDamageWobbleSide(int side) {
      this.dataTracker.set(DAMAGE_WOBBLE_SIDE, side);
   }

   public int getDamageWobbleSide() {
      return this.dataTracker.get(DAMAGE_WOBBLE_SIDE);
   }

   public void setBoatType(BoatEntity.Type type) {
      this.dataTracker.set(BOAT_TYPE, type.ordinal());
   }

   public BoatEntity.Type getBoatType() {
      return BoatEntity.Type.getType(this.dataTracker.get(BOAT_TYPE));
   }

   @Override
   protected boolean canAddPassenger(Entity passenger) {
      return this.getPassengerList().size() < 2 && !this.isSubmergedIn(FluidTags.WATER);
   }

   @Nullable
   @Override
   public Entity getPrimaryPassenger() {
      List<Entity> _snowman = this.getPassengerList();
      return _snowman.isEmpty() ? null : _snowman.get(0);
   }

   public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
      this.pressingLeft = pressingLeft;
      this.pressingRight = pressingRight;
      this.pressingForward = pressingForward;
      this.pressingBack = pressingBack;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this);
   }

   @Override
   public boolean isSubmergedInWater() {
      return this.location == BoatEntity.Location.UNDER_WATER || this.location == BoatEntity.Location.UNDER_FLOWING_WATER;
   }

   public static enum Location {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;

      private Location() {
      }
   }

   public static enum Type {
      OAK(Blocks.OAK_PLANKS, "oak"),
      SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
      BIRCH(Blocks.BIRCH_PLANKS, "birch"),
      JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
      ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
      DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak");

      private final String name;
      private final Block baseBlock;

      private Type(Block baseBlock, String name) {
         this.name = name;
         this.baseBlock = baseBlock;
      }

      public String getName() {
         return this.name;
      }

      public Block getBaseBlock() {
         return this.baseBlock;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BoatEntity.Type getType(int _snowman) {
         BoatEntity.Type[] _snowmanx = values();
         if (_snowman < 0 || _snowman >= _snowmanx.length) {
            _snowman = 0;
         }

         return _snowmanx[_snowman];
      }

      public static BoatEntity.Type getType(String _snowman) {
         BoatEntity.Type[] _snowmanx = values();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
            if (_snowmanx[_snowmanxx].getName().equals(_snowman)) {
               return _snowmanx[_snowmanxx];
            }
         }

         return _snowmanx[0];
      }
   }
}
