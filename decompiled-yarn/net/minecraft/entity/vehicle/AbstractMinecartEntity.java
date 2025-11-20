package net.minecraft.entity.vehicle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.class_5459;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.enums.RailShape;
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
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class AbstractMinecartEntity extends Entity {
   private static final TrackedData<Integer> DAMAGE_WOBBLE_TICKS = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> DAMAGE_WOBBLE_SIDE = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Float> DAMAGE_WOBBLE_STRENGTH = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.FLOAT);
   private static final TrackedData<Integer> CUSTOM_BLOCK_ID = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> CUSTOM_BLOCK_OFFSET = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> CUSTOM_BLOCK_PRESENT = DataTracker.registerData(AbstractMinecartEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final ImmutableMap<EntityPose, ImmutableList<Integer>> DISMOUNT_FREE_Y_SPACES_NEEDED = ImmutableMap.of(
      EntityPose.STANDING, ImmutableList.of(0, 1, -1), EntityPose.CROUCHING, ImmutableList.of(0, 1, -1), EntityPose.SWIMMING, ImmutableList.of(0, 1)
   );
   private boolean yawFlipped;
   private static final Map<RailShape, Pair<Vec3i, Vec3i>> ADJACENT_RAIL_POSITIONS_BY_SHAPE = Util.make(Maps.newEnumMap(RailShape.class), _snowman -> {
      Vec3i _snowmanx = Direction.WEST.getVector();
      Vec3i _snowmanxx = Direction.EAST.getVector();
      Vec3i _snowmanxxx = Direction.NORTH.getVector();
      Vec3i _snowmanxxxx = Direction.SOUTH.getVector();
      Vec3i _snowmanxxxxx = _snowmanx.down();
      Vec3i _snowmanxxxxxx = _snowmanxx.down();
      Vec3i _snowmanxxxxxxx = _snowmanxxx.down();
      Vec3i _snowmanxxxxxxxx = _snowmanxxxx.down();
      _snowman.put(RailShape.NORTH_SOUTH, Pair.of(_snowmanxxx, _snowmanxxxx));
      _snowman.put(RailShape.EAST_WEST, Pair.of(_snowmanx, _snowmanxx));
      _snowman.put(RailShape.ASCENDING_EAST, Pair.of(_snowmanxxxxx, _snowmanxx));
      _snowman.put(RailShape.ASCENDING_WEST, Pair.of(_snowmanx, _snowmanxxxxxx));
      _snowman.put(RailShape.ASCENDING_NORTH, Pair.of(_snowmanxxx, _snowmanxxxxxxxx));
      _snowman.put(RailShape.ASCENDING_SOUTH, Pair.of(_snowmanxxxxxxx, _snowmanxxxx));
      _snowman.put(RailShape.SOUTH_EAST, Pair.of(_snowmanxxxx, _snowmanxx));
      _snowman.put(RailShape.SOUTH_WEST, Pair.of(_snowmanxxxx, _snowmanx));
      _snowman.put(RailShape.NORTH_WEST, Pair.of(_snowmanxxx, _snowmanx));
      _snowman.put(RailShape.NORTH_EAST, Pair.of(_snowmanxxx, _snowmanxx));
   });
   private int clientInterpolationSteps;
   private double clientX;
   private double clientY;
   private double clientZ;
   private double clientYaw;
   private double clientPitch;
   private double clientXVelocity;
   private double clientYVelocity;
   private double clientZVelocity;

   protected AbstractMinecartEntity(EntityType<?> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.inanimate = true;
   }

   protected AbstractMinecartEntity(EntityType<?> type, World world, double x, double y, double z) {
      this(type, world);
      this.updatePosition(x, y, z);
      this.setVelocity(Vec3d.ZERO);
      this.prevX = x;
      this.prevY = y;
      this.prevZ = z;
   }

   public static AbstractMinecartEntity create(World world, double x, double y, double z, AbstractMinecartEntity.Type type) {
      if (type == AbstractMinecartEntity.Type.CHEST) {
         return new ChestMinecartEntity(world, x, y, z);
      } else if (type == AbstractMinecartEntity.Type.FURNACE) {
         return new FurnaceMinecartEntity(world, x, y, z);
      } else if (type == AbstractMinecartEntity.Type.TNT) {
         return new TntMinecartEntity(world, x, y, z);
      } else if (type == AbstractMinecartEntity.Type.SPAWNER) {
         return new SpawnerMinecartEntity(world, x, y, z);
      } else if (type == AbstractMinecartEntity.Type.HOPPER) {
         return new HopperMinecartEntity(world, x, y, z);
      } else {
         return (AbstractMinecartEntity)(type == AbstractMinecartEntity.Type.COMMAND_BLOCK
            ? new CommandBlockMinecartEntity(world, x, y, z)
            : new MinecartEntity(world, x, y, z));
      }
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
      this.dataTracker.startTracking(CUSTOM_BLOCK_ID, Block.getRawIdFromState(Blocks.AIR.getDefaultState()));
      this.dataTracker.startTracking(CUSTOM_BLOCK_OFFSET, 6);
      this.dataTracker.startTracking(CUSTOM_BLOCK_PRESENT, false);
   }

   @Override
   public boolean collidesWith(Entity other) {
      return BoatEntity.method_30959(this, other);
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
      return 0.0;
   }

   @Override
   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      Direction _snowman = this.getMovementDirection();
      if (_snowman.getAxis() == Direction.Axis.Y) {
         return super.updatePassengerForDismount(passenger);
      } else {
         int[][] _snowmanx = Dismounting.getDismountOffsets(_snowman);
         BlockPos _snowmanxx = this.getBlockPos();
         BlockPos.Mutable _snowmanxxx = new BlockPos.Mutable();
         ImmutableList<EntityPose> _snowmanxxxx = passenger.getPoses();
         UnmodifiableIterator var7 = _snowmanxxxx.iterator();

         while (var7.hasNext()) {
            EntityPose _snowmanxxxxx = (EntityPose)var7.next();
            EntityDimensions _snowmanxxxxxx = passenger.getDimensions(_snowmanxxxxx);
            float _snowmanxxxxxxx = Math.min(_snowmanxxxxxx.width, 1.0F) / 2.0F;
            UnmodifiableIterator var11 = ((ImmutableList)DISMOUNT_FREE_Y_SPACES_NEEDED.get(_snowmanxxxxx)).iterator();

            while (var11.hasNext()) {
               int _snowmanxxxxxxxx = (Integer)var11.next();

               for (int[] _snowmanxxxxxxxxx : _snowmanx) {
                  _snowmanxxx.set(_snowmanxx.getX() + _snowmanxxxxxxxxx[0], _snowmanxx.getY() + _snowmanxxxxxxxx, _snowmanxx.getZ() + _snowmanxxxxxxxxx[1]);
                  double _snowmanxxxxxxxxxx = this.world
                     .getDismountHeight(Dismounting.getCollisionShape(this.world, _snowmanxxx), () -> Dismounting.getCollisionShape(this.world, _snowman.down()));
                  if (Dismounting.canDismountInBlock(_snowmanxxxxxxxxxx)) {
                     Box _snowmanxxxxxxxxxxx = new Box((double)(-_snowmanxxxxxxx), 0.0, (double)(-_snowmanxxxxxxx), (double)_snowmanxxxxxxx, (double)_snowmanxxxxxx.height, (double)_snowmanxxxxxxx);
                     Vec3d _snowmanxxxxxxxxxxxx = Vec3d.ofCenter(_snowmanxxx, _snowmanxxxxxxxxxx);
                     if (Dismounting.canPlaceEntityAt(this.world, passenger, _snowmanxxxxxxxxxxx.offset(_snowmanxxxxxxxxxxxx))) {
                        passenger.setPose(_snowmanxxxxx);
                        return _snowmanxxxxxxxxxxxx;
                     }
                  }
               }
            }
         }

         double _snowmanxxxxx = this.getBoundingBox().maxY;
         _snowmanxxx.set((double)_snowmanxx.getX(), _snowmanxxxxx, (double)_snowmanxx.getZ());
         UnmodifiableIterator var22 = _snowmanxxxx.iterator();

         while (var22.hasNext()) {
            EntityPose _snowmanxxxxxx = (EntityPose)var22.next();
            double _snowmanxxxxxxx = (double)passenger.getDimensions(_snowmanxxxxxx).height;
            int _snowmanxxxxxxxx = MathHelper.ceil(_snowmanxxxxx - (double)_snowmanxxx.getY() + _snowmanxxxxxxx);
            double _snowmanxxxxxxxxxx = Dismounting.getCeilingHeight(
               _snowmanxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx -> this.world.getBlockState(_snowmanxxxxxxxxxxx).getCollisionShape(this.world, _snowmanxxxxxxxxxxx)
            );
            if (_snowmanxxxxx + _snowmanxxxxxxx <= _snowmanxxxxxxxxxx) {
               passenger.setPose(_snowmanxxxxxx);
               break;
            }
         }

         return super.updatePassengerForDismount(passenger);
      }
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.world.isClient || this.removed) {
         return true;
      } else if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.setDamageWobbleSide(-this.getDamageWobbleSide());
         this.setDamageWobbleTicks(10);
         this.scheduleVelocityUpdate();
         this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0F);
         boolean _snowman = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).abilities.creativeMode;
         if (_snowman || this.getDamageWobbleStrength() > 40.0F) {
            this.removeAllPassengers();
            if (_snowman && !this.hasCustomName()) {
               this.remove();
            } else {
               this.dropItems(source);
            }
         }

         return true;
      }
   }

   @Override
   protected float getVelocityMultiplier() {
      BlockState _snowman = this.world.getBlockState(this.getBlockPos());
      return _snowman.isIn(BlockTags.RAILS) ? 1.0F : super.getVelocityMultiplier();
   }

   public void dropItems(DamageSource damageSource) {
      this.remove();
      if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
         ItemStack _snowman = new ItemStack(Items.MINECART);
         if (this.hasCustomName()) {
            _snowman.setCustomName(this.getCustomName());
         }

         this.dropStack(_snowman);
      }
   }

   @Override
   public void animateDamage() {
      this.setDamageWobbleSide(-this.getDamageWobbleSide());
      this.setDamageWobbleTicks(10);
      this.setDamageWobbleStrength(this.getDamageWobbleStrength() + this.getDamageWobbleStrength() * 10.0F);
   }

   @Override
   public boolean collides() {
      return !this.removed;
   }

   private static Pair<Vec3i, Vec3i> getAdjacentRailPositionsByShape(RailShape shape) {
      return ADJACENT_RAIL_POSITIONS_BY_SHAPE.get(shape);
   }

   @Override
   public Direction getMovementDirection() {
      return this.yawFlipped ? this.getHorizontalFacing().getOpposite().rotateYClockwise() : this.getHorizontalFacing().rotateYClockwise();
   }

   @Override
   public void tick() {
      if (this.getDamageWobbleTicks() > 0) {
         this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
      }

      if (this.getDamageWobbleStrength() > 0.0F) {
         this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0F);
      }

      if (this.getY() < -64.0) {
         this.destroy();
      }

      this.tickNetherPortal();
      if (this.world.isClient) {
         if (this.clientInterpolationSteps > 0) {
            double _snowman = this.getX() + (this.clientX - this.getX()) / (double)this.clientInterpolationSteps;
            double _snowmanx = this.getY() + (this.clientY - this.getY()) / (double)this.clientInterpolationSteps;
            double _snowmanxx = this.getZ() + (this.clientZ - this.getZ()) / (double)this.clientInterpolationSteps;
            double _snowmanxxx = MathHelper.wrapDegrees(this.clientYaw - (double)this.yaw);
            this.yaw = (float)((double)this.yaw + _snowmanxxx / (double)this.clientInterpolationSteps);
            this.pitch = (float)((double)this.pitch + (this.clientPitch - (double)this.pitch) / (double)this.clientInterpolationSteps);
            this.clientInterpolationSteps--;
            this.updatePosition(_snowman, _snowmanx, _snowmanxx);
            this.setRotation(this.yaw, this.pitch);
         } else {
            this.refreshPosition();
            this.setRotation(this.yaw, this.pitch);
         }
      } else {
         if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
         }

         int _snowman = MathHelper.floor(this.getX());
         int _snowmanx = MathHelper.floor(this.getY());
         int _snowmanxx = MathHelper.floor(this.getZ());
         if (this.world.getBlockState(new BlockPos(_snowman, _snowmanx - 1, _snowmanxx)).isIn(BlockTags.RAILS)) {
            _snowmanx--;
         }

         BlockPos _snowmanxxx = new BlockPos(_snowman, _snowmanx, _snowmanxx);
         BlockState _snowmanxxxx = this.world.getBlockState(_snowmanxxx);
         if (AbstractRailBlock.isRail(_snowmanxxxx)) {
            this.moveOnRail(_snowmanxxx, _snowmanxxxx);
            if (_snowmanxxxx.isOf(Blocks.ACTIVATOR_RAIL)) {
               this.onActivatorRail(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx.get(PoweredRailBlock.POWERED));
            }
         } else {
            this.moveOffRail();
         }

         this.checkBlockCollision();
         this.pitch = 0.0F;
         double _snowmanxxxxx = this.prevX - this.getX();
         double _snowmanxxxxxx = this.prevZ - this.getZ();
         if (_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx > 0.001) {
            this.yaw = (float)(MathHelper.atan2(_snowmanxxxxxx, _snowmanxxxxx) * 180.0 / Math.PI);
            if (this.yawFlipped) {
               this.yaw += 180.0F;
            }
         }

         double _snowmanxxxxxxx = (double)MathHelper.wrapDegrees(this.yaw - this.prevYaw);
         if (_snowmanxxxxxxx < -170.0 || _snowmanxxxxxxx >= 170.0) {
            this.yaw += 180.0F;
            this.yawFlipped = !this.yawFlipped;
         }

         this.setRotation(this.yaw, this.pitch);
         if (this.getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE && squaredHorizontalLength(this.getVelocity()) > 0.01) {
            List<Entity> _snowmanxxxxxxxx = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2F, 0.0, 0.2F), EntityPredicates.canBePushedBy(this));
            if (!_snowmanxxxxxxxx.isEmpty()) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx.size(); _snowmanxxxxxxxxx++) {
                  Entity _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.get(_snowmanxxxxxxxxx);
                  if (!(_snowmanxxxxxxxxxx instanceof PlayerEntity)
                     && !(_snowmanxxxxxxxxxx instanceof IronGolemEntity)
                     && !(_snowmanxxxxxxxxxx instanceof AbstractMinecartEntity)
                     && !this.hasPassengers()
                     && !_snowmanxxxxxxxxxx.hasVehicle()) {
                     _snowmanxxxxxxxxxx.startRiding(this);
                  } else {
                     _snowmanxxxxxxxxxx.pushAwayFrom(this);
                  }
               }
            }
         } else {
            for (Entity _snowmanxxxxxxxx : this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2F, 0.0, 0.2F))) {
               if (!this.hasPassenger(_snowmanxxxxxxxx) && _snowmanxxxxxxxx.isPushable() && _snowmanxxxxxxxx instanceof AbstractMinecartEntity) {
                  _snowmanxxxxxxxx.pushAwayFrom(this);
               }
            }
         }

         this.updateWaterState();
         if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5F;
         }

         this.firstUpdate = false;
      }
   }

   protected double getMaxOffRailSpeed() {
      return 0.4;
   }

   public void onActivatorRail(int x, int y, int z, boolean powered) {
   }

   protected void moveOffRail() {
      double _snowman = this.getMaxOffRailSpeed();
      Vec3d _snowmanx = this.getVelocity();
      this.setVelocity(MathHelper.clamp(_snowmanx.x, -_snowman, _snowman), _snowmanx.y, MathHelper.clamp(_snowmanx.z, -_snowman, _snowman));
      if (this.onGround) {
         this.setVelocity(this.getVelocity().multiply(0.5));
      }

      this.move(MovementType.SELF, this.getVelocity());
      if (!this.onGround) {
         this.setVelocity(this.getVelocity().multiply(0.95));
      }
   }

   protected void moveOnRail(BlockPos pos, BlockState state) {
      this.fallDistance = 0.0F;
      double _snowman = this.getX();
      double _snowmanx = this.getY();
      double _snowmanxx = this.getZ();
      Vec3d _snowmanxxx = this.snapPositionToRail(_snowman, _snowmanx, _snowmanxx);
      _snowmanx = (double)pos.getY();
      boolean _snowmanxxxx = false;
      boolean _snowmanxxxxx = false;
      AbstractRailBlock _snowmanxxxxxx = (AbstractRailBlock)state.getBlock();
      if (_snowmanxxxxxx == Blocks.POWERED_RAIL) {
         _snowmanxxxx = state.get(PoweredRailBlock.POWERED);
         _snowmanxxxxx = !_snowmanxxxx;
      }

      double _snowmanxxxxxxx = 0.0078125;
      Vec3d _snowmanxxxxxxxx = this.getVelocity();
      RailShape _snowmanxxxxxxxxx = state.get(_snowmanxxxxxx.getShapeProperty());
      switch (_snowmanxxxxxxxxx) {
         case ASCENDING_EAST:
            this.setVelocity(_snowmanxxxxxxxx.add(-0.0078125, 0.0, 0.0));
            _snowmanx++;
            break;
         case ASCENDING_WEST:
            this.setVelocity(_snowmanxxxxxxxx.add(0.0078125, 0.0, 0.0));
            _snowmanx++;
            break;
         case ASCENDING_NORTH:
            this.setVelocity(_snowmanxxxxxxxx.add(0.0, 0.0, 0.0078125));
            _snowmanx++;
            break;
         case ASCENDING_SOUTH:
            this.setVelocity(_snowmanxxxxxxxx.add(0.0, 0.0, -0.0078125));
            _snowmanx++;
      }

      _snowmanxxxxxxxx = this.getVelocity();
      Pair<Vec3i, Vec3i> _snowmanxxxxxxx = getAdjacentRailPositionsByShape(_snowmanxxxxxxxxx);
      Vec3i _snowmanxxxxxxxx = (Vec3i)_snowmanxxxxxxx.getFirst();
      Vec3i _snowmanxxxxxxxxx = (Vec3i)_snowmanxxxxxxx.getSecond();
      double _snowmanxxxxxxxxxx = (double)(_snowmanxxxxxxxxx.getX() - _snowmanxxxxxxxx.getX());
      double _snowmanxxxxxxxxxxx = (double)(_snowmanxxxxxxxxx.getZ() - _snowmanxxxxxxxx.getZ());
      double _snowmanxxxxxxxxxxxx = Math.sqrt(_snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx);
      double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.x * _snowmanxxxxxxxxxx + _snowmanxxxxxxxx.z * _snowmanxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxx < 0.0) {
         _snowmanxxxxxxxxxx = -_snowmanxxxxxxxxxx;
         _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxxx;
      }

      double _snowmanxxxxxxxxxxxxxx = Math.min(2.0, Math.sqrt(squaredHorizontalLength(_snowmanxxxxxxxx)));
      _snowmanxxxxxxxx = new Vec3d(_snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxx / _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx.y, _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx / _snowmanxxxxxxxxxxxx);
      this.setVelocity(_snowmanxxxxxxxx);
      Entity _snowmanxxxxxxxxxxxxxxx = this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
      if (_snowmanxxxxxxxxxxxxxxx instanceof PlayerEntity) {
         Vec3d _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getVelocity();
         double _snowmanxxxxxxxxxxxxxxxxx = squaredHorizontalLength(_snowmanxxxxxxxxxxxxxxxx);
         double _snowmanxxxxxxxxxxxxxxxxxx = squaredHorizontalLength(this.getVelocity());
         if (_snowmanxxxxxxxxxxxxxxxxx > 1.0E-4 && _snowmanxxxxxxxxxxxxxxxxxx < 0.01) {
            this.setVelocity(this.getVelocity().add(_snowmanxxxxxxxxxxxxxxxx.x * 0.1, 0.0, _snowmanxxxxxxxxxxxxxxxx.z * 0.1));
            _snowmanxxxxx = false;
         }
      }

      if (_snowmanxxxxx) {
         double _snowmanxxxxxxxxxxxxxxxx = Math.sqrt(squaredHorizontalLength(this.getVelocity()));
         if (_snowmanxxxxxxxxxxxxxxxx < 0.03) {
            this.setVelocity(Vec3d.ZERO);
         } else {
            this.setVelocity(this.getVelocity().multiply(0.5, 0.0, 0.5));
         }
      }

      double _snowmanxxxxxxxxxxxxxxxx = (double)pos.getX() + 0.5 + (double)_snowmanxxxxxxxx.getX() * 0.5;
      double _snowmanxxxxxxxxxxxxxxxxx = (double)pos.getZ() + 0.5 + (double)_snowmanxxxxxxxx.getZ() * 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxx = (double)pos.getX() + 0.5 + (double)_snowmanxxxxxxxxx.getX() * 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxxx = (double)pos.getZ() + 0.5 + (double)_snowmanxxxxxxxxx.getZ() * 0.5;
      _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxx;
      double _snowmanxxxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxx == 0.0) {
         _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxx - (double)pos.getZ();
      } else if (_snowmanxxxxxxxxxxx == 0.0) {
         _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman - (double)pos.getX();
      } else {
         double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx - _snowmanxxxxxxxxxxxxxxxxx;
         _snowmanxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx) * 2.0;
      }

      _snowman = _snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      _snowmanxx = _snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      this.updatePosition(_snowman, _snowmanx, _snowmanxx);
      double _snowmanxxxxxxxxxxxxxxxxxxxxx = this.hasPassengers() ? 0.75 : 1.0;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.getMaxOffRailSpeed();
      _snowmanxxxxxxxx = this.getVelocity();
      this.move(
         MovementType.SELF,
         new Vec3d(
            MathHelper.clamp(_snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxx.x, -_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx),
            0.0,
            MathHelper.clamp(_snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxx.z, -_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx)
         )
      );
      if (_snowmanxxxxxxxx.getY() != 0
         && MathHelper.floor(this.getX()) - pos.getX() == _snowmanxxxxxxxx.getX()
         && MathHelper.floor(this.getZ()) - pos.getZ() == _snowmanxxxxxxxx.getZ()) {
         this.updatePosition(this.getX(), this.getY() + (double)_snowmanxxxxxxxx.getY(), this.getZ());
      } else if (_snowmanxxxxxxxxx.getY() != 0
         && MathHelper.floor(this.getX()) - pos.getX() == _snowmanxxxxxxxxx.getX()
         && MathHelper.floor(this.getZ()) - pos.getZ() == _snowmanxxxxxxxxx.getZ()) {
         this.updatePosition(this.getX(), this.getY() + (double)_snowmanxxxxxxxxx.getY(), this.getZ());
      }

      this.applySlowdown();
      Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.snapPositionToRail(this.getX(), this.getY(), this.getZ());
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx != null && _snowmanxxx != null) {
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxx.y - _snowmanxxxxxxxxxxxxxxxxxxxxxxx.y) * 0.05;
         Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.getVelocity();
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.sqrt(squaredHorizontalLength(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx));
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx > 0.0) {
            this.setVelocity(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.multiply(
                  (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  1.0,
                  (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
               )
            );
         }

         this.updatePosition(this.getX(), _snowmanxxxxxxxxxxxxxxxxxxxxxxx.y, this.getZ());
      }

      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(this.getX());
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.floor(this.getZ());
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx != pos.getX() || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx != pos.getZ()) {
         Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getVelocity();
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.sqrt(squaredHorizontalLength(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
         this.setVelocity(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx - pos.getX()),
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.y,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx - pos.getZ())
         );
      }

      if (_snowmanxxxx) {
         Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getVelocity();
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.sqrt(squaredHorizontalLength(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0.01) {
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.06;
            this.setVelocity(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.add(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.x / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * 0.06, 0.0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.z / _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * 0.06
               )
            );
         } else {
            Vec3d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getVelocity();
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.x;
            double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.z;
            if (_snowmanxxxxxxxxx == RailShape.EAST_WEST) {
               if (this.willHitBlockAt(pos.west())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.02;
               } else if (this.willHitBlockAt(pos.east())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -0.02;
               }
            } else {
               if (_snowmanxxxxxxxxx != RailShape.NORTH_SOUTH) {
                  return;
               }

               if (this.willHitBlockAt(pos.north())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.02;
               } else if (this.willHitBlockAt(pos.south())) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -0.02;
               }
            }

            this.setVelocity(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.y, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         }
      }
   }

   private boolean willHitBlockAt(BlockPos pos) {
      return this.world.getBlockState(pos).isSolidBlock(this.world, pos);
   }

   protected void applySlowdown() {
      double _snowman = this.hasPassengers() ? 0.997 : 0.96;
      this.setVelocity(this.getVelocity().multiply(_snowman, 0.0, _snowman));
   }

   @Nullable
   public Vec3d snapPositionToRailWithOffset(double x, double y, double z, double offset) {
      int _snowman = MathHelper.floor(x);
      int _snowmanx = MathHelper.floor(y);
      int _snowmanxx = MathHelper.floor(z);
      if (this.world.getBlockState(new BlockPos(_snowman, _snowmanx - 1, _snowmanxx)).isIn(BlockTags.RAILS)) {
         _snowmanx--;
      }

      BlockState _snowmanxxx = this.world.getBlockState(new BlockPos(_snowman, _snowmanx, _snowmanxx));
      if (AbstractRailBlock.isRail(_snowmanxxx)) {
         RailShape _snowmanxxxx = _snowmanxxx.get(((AbstractRailBlock)_snowmanxxx.getBlock()).getShapeProperty());
         y = (double)_snowmanx;
         if (_snowmanxxxx.isAscending()) {
            y = (double)(_snowmanx + 1);
         }

         Pair<Vec3i, Vec3i> _snowmanxxxxx = getAdjacentRailPositionsByShape(_snowmanxxxx);
         Vec3i _snowmanxxxxxx = (Vec3i)_snowmanxxxxx.getFirst();
         Vec3i _snowmanxxxxxxx = (Vec3i)_snowmanxxxxx.getSecond();
         double _snowmanxxxxxxxx = (double)(_snowmanxxxxxxx.getX() - _snowmanxxxxxx.getX());
         double _snowmanxxxxxxxxx = (double)(_snowmanxxxxxxx.getZ() - _snowmanxxxxxx.getZ());
         double _snowmanxxxxxxxxxx = Math.sqrt(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx);
         _snowmanxxxxxxxx /= _snowmanxxxxxxxxxx;
         _snowmanxxxxxxxxx /= _snowmanxxxxxxxxxx;
         x += _snowmanxxxxxxxx * offset;
         z += _snowmanxxxxxxxxx * offset;
         if (_snowmanxxxxxx.getY() != 0 && MathHelper.floor(x) - _snowman == _snowmanxxxxxx.getX() && MathHelper.floor(z) - _snowmanxx == _snowmanxxxxxx.getZ()) {
            y += (double)_snowmanxxxxxx.getY();
         } else if (_snowmanxxxxxxx.getY() != 0 && MathHelper.floor(x) - _snowman == _snowmanxxxxxxx.getX() && MathHelper.floor(z) - _snowmanxx == _snowmanxxxxxxx.getZ()) {
            y += (double)_snowmanxxxxxxx.getY();
         }

         return this.snapPositionToRail(x, y, z);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3d snapPositionToRail(double x, double y, double z) {
      int _snowman = MathHelper.floor(x);
      int _snowmanx = MathHelper.floor(y);
      int _snowmanxx = MathHelper.floor(z);
      if (this.world.getBlockState(new BlockPos(_snowman, _snowmanx - 1, _snowmanxx)).isIn(BlockTags.RAILS)) {
         _snowmanx--;
      }

      BlockState _snowmanxxx = this.world.getBlockState(new BlockPos(_snowman, _snowmanx, _snowmanxx));
      if (AbstractRailBlock.isRail(_snowmanxxx)) {
         RailShape _snowmanxxxx = _snowmanxxx.get(((AbstractRailBlock)_snowmanxxx.getBlock()).getShapeProperty());
         Pair<Vec3i, Vec3i> _snowmanxxxxx = getAdjacentRailPositionsByShape(_snowmanxxxx);
         Vec3i _snowmanxxxxxx = (Vec3i)_snowmanxxxxx.getFirst();
         Vec3i _snowmanxxxxxxx = (Vec3i)_snowmanxxxxx.getSecond();
         double _snowmanxxxxxxxx = (double)_snowman + 0.5 + (double)_snowmanxxxxxx.getX() * 0.5;
         double _snowmanxxxxxxxxx = (double)_snowmanx + 0.0625 + (double)_snowmanxxxxxx.getY() * 0.5;
         double _snowmanxxxxxxxxxx = (double)_snowmanxx + 0.5 + (double)_snowmanxxxxxx.getZ() * 0.5;
         double _snowmanxxxxxxxxxxx = (double)_snowman + 0.5 + (double)_snowmanxxxxxxx.getX() * 0.5;
         double _snowmanxxxxxxxxxxxx = (double)_snowmanx + 0.0625 + (double)_snowmanxxxxxxx.getY() * 0.5;
         double _snowmanxxxxxxxxxxxxx = (double)_snowmanxx + 0.5 + (double)_snowmanxxxxxxx.getZ() * 0.5;
         double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx - _snowmanxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxx) * 2.0;
         double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxx;
         if (_snowmanxxxxxxxxxxxxxx == 0.0) {
            _snowmanxxxxxxxxxxxxxxxxx = z - (double)_snowmanxx;
         } else if (_snowmanxxxxxxxxxxxxxxxx == 0.0) {
            _snowmanxxxxxxxxxxxxxxxxx = x - (double)_snowman;
         } else {
            double _snowmanxxxxxxxxxxxxxxxxxx = x - _snowmanxxxxxxxx;
            double _snowmanxxxxxxxxxxxxxxxxxxx = z - _snowmanxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx) * 2.0;
         }

         x = _snowmanxxxxxxxx + _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         y = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         z = _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
         if (_snowmanxxxxxxxxxxxxxxx < 0.0) {
            y++;
         } else if (_snowmanxxxxxxxxxxxxxxx > 0.0) {
            y += 0.5;
         }

         return new Vec3d(x, y, z);
      } else {
         return null;
      }
   }

   @Override
   public Box getVisibilityBoundingBox() {
      Box _snowman = this.getBoundingBox();
      return this.hasCustomBlock() ? _snowman.expand((double)Math.abs(this.getBlockOffset()) / 16.0) : _snowman;
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
      if (tag.getBoolean("CustomDisplayTile")) {
         this.setCustomBlock(NbtHelper.toBlockState(tag.getCompound("DisplayState")));
         this.setCustomBlockOffset(tag.getInt("DisplayOffset"));
      }
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
      if (this.hasCustomBlock()) {
         tag.putBoolean("CustomDisplayTile", true);
         tag.put("DisplayState", NbtHelper.fromBlockState(this.getContainedBlock()));
         tag.putInt("DisplayOffset", this.getBlockOffset());
      }
   }

   @Override
   public void pushAwayFrom(Entity entity) {
      if (!this.world.isClient) {
         if (!entity.noClip && !this.noClip) {
            if (!this.hasPassenger(entity)) {
               double _snowman = entity.getX() - this.getX();
               double _snowmanx = entity.getZ() - this.getZ();
               double _snowmanxx = _snowman * _snowman + _snowmanx * _snowmanx;
               if (_snowmanxx >= 1.0E-4F) {
                  _snowmanxx = (double)MathHelper.sqrt(_snowmanxx);
                  _snowman /= _snowmanxx;
                  _snowmanx /= _snowmanxx;
                  double _snowmanxxx = 1.0 / _snowmanxx;
                  if (_snowmanxxx > 1.0) {
                     _snowmanxxx = 1.0;
                  }

                  _snowman *= _snowmanxxx;
                  _snowmanx *= _snowmanxxx;
                  _snowman *= 0.1F;
                  _snowmanx *= 0.1F;
                  _snowman *= (double)(1.0F - this.pushSpeedReduction);
                  _snowmanx *= (double)(1.0F - this.pushSpeedReduction);
                  _snowman *= 0.5;
                  _snowmanx *= 0.5;
                  if (entity instanceof AbstractMinecartEntity) {
                     double _snowmanxxxx = entity.getX() - this.getX();
                     double _snowmanxxxxx = entity.getZ() - this.getZ();
                     Vec3d _snowmanxxxxxx = new Vec3d(_snowmanxxxx, 0.0, _snowmanxxxxx).normalize();
                     Vec3d _snowmanxxxxxxx = new Vec3d(
                           (double)MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)), 0.0, (double)MathHelper.sin(this.yaw * (float) (Math.PI / 180.0))
                        )
                        .normalize();
                     double _snowmanxxxxxxxx = Math.abs(_snowmanxxxxxx.dotProduct(_snowmanxxxxxxx));
                     if (_snowmanxxxxxxxx < 0.8F) {
                        return;
                     }

                     Vec3d _snowmanxxxxxxxxx = this.getVelocity();
                     Vec3d _snowmanxxxxxxxxxx = entity.getVelocity();
                     if (((AbstractMinecartEntity)entity).getMinecartType() == AbstractMinecartEntity.Type.FURNACE
                        && this.getMinecartType() != AbstractMinecartEntity.Type.FURNACE) {
                        this.setVelocity(_snowmanxxxxxxxxx.multiply(0.2, 1.0, 0.2));
                        this.addVelocity(_snowmanxxxxxxxxxx.x - _snowman, 0.0, _snowmanxxxxxxxxxx.z - _snowmanx);
                        entity.setVelocity(_snowmanxxxxxxxxxx.multiply(0.95, 1.0, 0.95));
                     } else if (((AbstractMinecartEntity)entity).getMinecartType() != AbstractMinecartEntity.Type.FURNACE
                        && this.getMinecartType() == AbstractMinecartEntity.Type.FURNACE) {
                        entity.setVelocity(_snowmanxxxxxxxxxx.multiply(0.2, 1.0, 0.2));
                        entity.addVelocity(_snowmanxxxxxxxxx.x + _snowman, 0.0, _snowmanxxxxxxxxx.z + _snowmanx);
                        this.setVelocity(_snowmanxxxxxxxxx.multiply(0.95, 1.0, 0.95));
                     } else {
                        double _snowmanxxxxxxxxxxx = (_snowmanxxxxxxxxxx.x + _snowmanxxxxxxxxx.x) / 2.0;
                        double _snowmanxxxxxxxxxxxx = (_snowmanxxxxxxxxxx.z + _snowmanxxxxxxxxx.z) / 2.0;
                        this.setVelocity(_snowmanxxxxxxxxx.multiply(0.2, 1.0, 0.2));
                        this.addVelocity(_snowmanxxxxxxxxxxx - _snowman, 0.0, _snowmanxxxxxxxxxxxx - _snowmanx);
                        entity.setVelocity(_snowmanxxxxxxxxxx.multiply(0.2, 1.0, 0.2));
                        entity.addVelocity(_snowmanxxxxxxxxxxx + _snowman, 0.0, _snowmanxxxxxxxxxxxx + _snowmanx);
                     }
                  } else {
                     this.addVelocity(-_snowman, 0.0, -_snowmanx);
                     entity.addVelocity(_snowman / 4.0, 0.0, _snowmanx / 4.0);
                  }
               }
            }
         }
      }
   }

   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
      this.clientX = x;
      this.clientY = y;
      this.clientZ = z;
      this.clientYaw = (double)yaw;
      this.clientPitch = (double)pitch;
      this.clientInterpolationSteps = interpolationSteps + 2;
      this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
   }

   @Override
   public void setVelocityClient(double x, double y, double z) {
      this.clientXVelocity = x;
      this.clientYVelocity = y;
      this.clientZVelocity = z;
      this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
   }

   public void setDamageWobbleStrength(float _snowman) {
      this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, _snowman);
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

   public void setDamageWobbleSide(int wobbleSide) {
      this.dataTracker.set(DAMAGE_WOBBLE_SIDE, wobbleSide);
   }

   public int getDamageWobbleSide() {
      return this.dataTracker.get(DAMAGE_WOBBLE_SIDE);
   }

   public abstract AbstractMinecartEntity.Type getMinecartType();

   public BlockState getContainedBlock() {
      return !this.hasCustomBlock() ? this.getDefaultContainedBlock() : Block.getStateFromRawId(this.getDataTracker().get(CUSTOM_BLOCK_ID));
   }

   public BlockState getDefaultContainedBlock() {
      return Blocks.AIR.getDefaultState();
   }

   public int getBlockOffset() {
      return !this.hasCustomBlock() ? this.getDefaultBlockOffset() : this.getDataTracker().get(CUSTOM_BLOCK_OFFSET);
   }

   public int getDefaultBlockOffset() {
      return 6;
   }

   public void setCustomBlock(BlockState state) {
      this.getDataTracker().set(CUSTOM_BLOCK_ID, Block.getRawIdFromState(state));
      this.setCustomBlockPresent(true);
   }

   public void setCustomBlockOffset(int offset) {
      this.getDataTracker().set(CUSTOM_BLOCK_OFFSET, offset);
      this.setCustomBlockPresent(true);
   }

   public boolean hasCustomBlock() {
      return this.getDataTracker().get(CUSTOM_BLOCK_PRESENT);
   }

   public void setCustomBlockPresent(boolean present) {
      this.getDataTracker().set(CUSTOM_BLOCK_PRESENT, present);
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this);
   }

   public static enum Type {
      RIDEABLE,
      CHEST,
      FURNACE,
      TNT,
      SPAWNER,
      HOPPER,
      COMMAND_BLOCK;

      private Type() {
      }
   }
}
