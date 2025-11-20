package net.minecraft.entity.vehicle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   private static final Map<RailShape, Pair<Vec3i, Vec3i>> ADJACENT_RAIL_POSITIONS_BY_SHAPE = Util.make(Maps.newEnumMap(RailShape.class), enumMap -> {
      Vec3i lv = Direction.WEST.getVector();
      Vec3i lv2 = Direction.EAST.getVector();
      Vec3i lv3 = Direction.NORTH.getVector();
      Vec3i lv4 = Direction.SOUTH.getVector();
      Vec3i lv5 = lv.down();
      Vec3i lv6 = lv2.down();
      Vec3i lv7 = lv3.down();
      Vec3i lv8 = lv4.down();
      enumMap.put(RailShape.NORTH_SOUTH, Pair.of(lv3, lv4));
      enumMap.put(RailShape.EAST_WEST, Pair.of(lv, lv2));
      enumMap.put(RailShape.ASCENDING_EAST, Pair.of(lv5, lv2));
      enumMap.put(RailShape.ASCENDING_WEST, Pair.of(lv, lv6));
      enumMap.put(RailShape.ASCENDING_NORTH, Pair.of(lv3, lv8));
      enumMap.put(RailShape.ASCENDING_SOUTH, Pair.of(lv7, lv4));
      enumMap.put(RailShape.SOUTH_EAST, Pair.of(lv4, lv2));
      enumMap.put(RailShape.SOUTH_WEST, Pair.of(lv4, lv));
      enumMap.put(RailShape.NORTH_WEST, Pair.of(lv3, lv));
      enumMap.put(RailShape.NORTH_EAST, Pair.of(lv3, lv2));
   });
   private int clientInterpolationSteps;
   private double clientX;
   private double clientY;
   private double clientZ;
   private double clientYaw;
   private double clientPitch;
   @Environment(EnvType.CLIENT)
   private double clientXVelocity;
   @Environment(EnvType.CLIENT)
   private double clientYVelocity;
   @Environment(EnvType.CLIENT)
   private double clientZVelocity;

   protected AbstractMinecartEntity(EntityType<?> arg, World arg2) {
      super(arg, arg2);
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
   protected Vec3d method_30633(Direction.Axis arg, class_5459.class_5460 arg2) {
      return LivingEntity.method_31079(super.method_30633(arg, arg2));
   }

   @Override
   public double getMountedHeightOffset() {
      return 0.0;
   }

   @Override
   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      Direction lv = this.getMovementDirection();
      if (lv.getAxis() == Direction.Axis.Y) {
         return super.updatePassengerForDismount(passenger);
      } else {
         int[][] is = Dismounting.getDismountOffsets(lv);
         BlockPos lv2 = this.getBlockPos();
         BlockPos.Mutable lv3 = new BlockPos.Mutable();
         ImmutableList<EntityPose> immutableList = passenger.getPoses();
         UnmodifiableIterator e = immutableList.iterator();

         while (e.hasNext()) {
            EntityPose lv4 = (EntityPose)e.next();
            EntityDimensions lv5 = passenger.getDimensions(lv4);
            float f = Math.min(lv5.width, 1.0F) / 2.0F;
            UnmodifiableIterator g = ((ImmutableList)DISMOUNT_FREE_Y_SPACES_NEEDED.get(lv4)).iterator();

            while (g.hasNext()) {
               int i = (Integer)g.next();

               for (int[] js : is) {
                  lv3.set(lv2.getX() + js[0], lv2.getY() + i, lv2.getZ() + js[1]);
                  double d = this.world
                     .getDismountHeight(Dismounting.getCollisionShape(this.world, lv3), () -> Dismounting.getCollisionShape(this.world, lv3.down()));
                  if (Dismounting.canDismountInBlock(d)) {
                     Box lv6 = new Box((double)(-f), 0.0, (double)(-f), (double)f, (double)lv5.height, (double)f);
                     Vec3d lv7 = Vec3d.ofCenter(lv3, d);
                     if (Dismounting.canPlaceEntityAt(this.world, passenger, lv6.offset(lv7))) {
                        passenger.setPose(lv4);
                        return lv7;
                     }
                  }
               }
            }
         }

         double ex = this.getBoundingBox().maxY;
         lv3.set((double)lv2.getX(), ex, (double)lv2.getZ());
         UnmodifiableIterator var22 = immutableList.iterator();

         while (var22.hasNext()) {
            EntityPose lv8 = (EntityPose)var22.next();
            double g = (double)passenger.getDimensions(lv8).height;
            int j = MathHelper.ceil(ex - (double)lv3.getY() + g);
            double h = Dismounting.getCeilingHeight(lv3, j, arg -> this.world.getBlockState(arg).getCollisionShape(this.world, arg));
            if (ex + g <= h) {
               passenger.setPose(lv8);
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
         boolean bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).abilities.creativeMode;
         if (bl || this.getDamageWobbleStrength() > 40.0F) {
            this.removeAllPassengers();
            if (bl && !this.hasCustomName()) {
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
      BlockState lv = this.world.getBlockState(this.getBlockPos());
      return lv.isIn(BlockTags.RAILS) ? 1.0F : super.getVelocityMultiplier();
   }

   public void dropItems(DamageSource damageSource) {
      this.remove();
      if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
         ItemStack lv = new ItemStack(Items.MINECART);
         if (this.hasCustomName()) {
            lv.setCustomName(this.getCustomName());
         }

         this.dropStack(lv);
      }
   }

   @Environment(EnvType.CLIENT)
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
            double d = this.getX() + (this.clientX - this.getX()) / (double)this.clientInterpolationSteps;
            double e = this.getY() + (this.clientY - this.getY()) / (double)this.clientInterpolationSteps;
            double f = this.getZ() + (this.clientZ - this.getZ()) / (double)this.clientInterpolationSteps;
            double g = MathHelper.wrapDegrees(this.clientYaw - (double)this.yaw);
            this.yaw = (float)((double)this.yaw + g / (double)this.clientInterpolationSteps);
            this.pitch = (float)((double)this.pitch + (this.clientPitch - (double)this.pitch) / (double)this.clientInterpolationSteps);
            this.clientInterpolationSteps--;
            this.updatePosition(d, e, f);
            this.setRotation(this.yaw, this.pitch);
         } else {
            this.refreshPosition();
            this.setRotation(this.yaw, this.pitch);
         }
      } else {
         if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
         }

         int i = MathHelper.floor(this.getX());
         int j = MathHelper.floor(this.getY());
         int k = MathHelper.floor(this.getZ());
         if (this.world.getBlockState(new BlockPos(i, j - 1, k)).isIn(BlockTags.RAILS)) {
            j--;
         }

         BlockPos lv = new BlockPos(i, j, k);
         BlockState lv2 = this.world.getBlockState(lv);
         if (AbstractRailBlock.isRail(lv2)) {
            this.moveOnRail(lv, lv2);
            if (lv2.isOf(Blocks.ACTIVATOR_RAIL)) {
               this.onActivatorRail(i, j, k, lv2.get(PoweredRailBlock.POWERED));
            }
         } else {
            this.moveOffRail();
         }

         this.checkBlockCollision();
         this.pitch = 0.0F;
         double h = this.prevX - this.getX();
         double l = this.prevZ - this.getZ();
         if (h * h + l * l > 0.001) {
            this.yaw = (float)(MathHelper.atan2(l, h) * 180.0 / Math.PI);
            if (this.yawFlipped) {
               this.yaw += 180.0F;
            }
         }

         double m = (double)MathHelper.wrapDegrees(this.yaw - this.prevYaw);
         if (m < -170.0 || m >= 170.0) {
            this.yaw += 180.0F;
            this.yawFlipped = !this.yawFlipped;
         }

         this.setRotation(this.yaw, this.pitch);
         if (this.getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE && squaredHorizontalLength(this.getVelocity()) > 0.01) {
            List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2F, 0.0, 0.2F), EntityPredicates.canBePushedBy(this));
            if (!list.isEmpty()) {
               for (int n = 0; n < list.size(); n++) {
                  Entity lv3 = list.get(n);
                  if (!(lv3 instanceof PlayerEntity)
                     && !(lv3 instanceof IronGolemEntity)
                     && !(lv3 instanceof AbstractMinecartEntity)
                     && !this.hasPassengers()
                     && !lv3.hasVehicle()) {
                     lv3.startRiding(this);
                  } else {
                     lv3.pushAwayFrom(this);
                  }
               }
            }
         } else {
            for (Entity lv4 : this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2F, 0.0, 0.2F))) {
               if (!this.hasPassenger(lv4) && lv4.isPushable() && lv4 instanceof AbstractMinecartEntity) {
                  lv4.pushAwayFrom(this);
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
      double d = this.getMaxOffRailSpeed();
      Vec3d lv = this.getVelocity();
      this.setVelocity(MathHelper.clamp(lv.x, -d, d), lv.y, MathHelper.clamp(lv.z, -d, d));
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
      double d = this.getX();
      double e = this.getY();
      double f = this.getZ();
      Vec3d lv = this.snapPositionToRail(d, e, f);
      e = (double)pos.getY();
      boolean bl = false;
      boolean bl2 = false;
      AbstractRailBlock lv2 = (AbstractRailBlock)state.getBlock();
      if (lv2 == Blocks.POWERED_RAIL) {
         bl = state.get(PoweredRailBlock.POWERED);
         bl2 = !bl;
      }

      double g = 0.0078125;
      Vec3d lv3 = this.getVelocity();
      RailShape lv4 = state.get(lv2.getShapeProperty());
      switch (lv4) {
         case ASCENDING_EAST:
            this.setVelocity(lv3.add(-0.0078125, 0.0, 0.0));
            e++;
            break;
         case ASCENDING_WEST:
            this.setVelocity(lv3.add(0.0078125, 0.0, 0.0));
            e++;
            break;
         case ASCENDING_NORTH:
            this.setVelocity(lv3.add(0.0, 0.0, 0.0078125));
            e++;
            break;
         case ASCENDING_SOUTH:
            this.setVelocity(lv3.add(0.0, 0.0, -0.0078125));
            e++;
      }

      lv3 = this.getVelocity();
      Pair<Vec3i, Vec3i> pair = getAdjacentRailPositionsByShape(lv4);
      Vec3i lv5 = (Vec3i)pair.getFirst();
      Vec3i lv6 = (Vec3i)pair.getSecond();
      double h = (double)(lv6.getX() - lv5.getX());
      double i = (double)(lv6.getZ() - lv5.getZ());
      double j = Math.sqrt(h * h + i * i);
      double k = lv3.x * h + lv3.z * i;
      if (k < 0.0) {
         h = -h;
         i = -i;
      }

      double l = Math.min(2.0, Math.sqrt(squaredHorizontalLength(lv3)));
      lv3 = new Vec3d(l * h / j, lv3.y, l * i / j);
      this.setVelocity(lv3);
      Entity lv7 = this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
      if (lv7 instanceof PlayerEntity) {
         Vec3d lv8 = lv7.getVelocity();
         double m = squaredHorizontalLength(lv8);
         double n = squaredHorizontalLength(this.getVelocity());
         if (m > 1.0E-4 && n < 0.01) {
            this.setVelocity(this.getVelocity().add(lv8.x * 0.1, 0.0, lv8.z * 0.1));
            bl2 = false;
         }
      }

      if (bl2) {
         double o = Math.sqrt(squaredHorizontalLength(this.getVelocity()));
         if (o < 0.03) {
            this.setVelocity(Vec3d.ZERO);
         } else {
            this.setVelocity(this.getVelocity().multiply(0.5, 0.0, 0.5));
         }
      }

      double p = (double)pos.getX() + 0.5 + (double)lv5.getX() * 0.5;
      double q = (double)pos.getZ() + 0.5 + (double)lv5.getZ() * 0.5;
      double r = (double)pos.getX() + 0.5 + (double)lv6.getX() * 0.5;
      double s = (double)pos.getZ() + 0.5 + (double)lv6.getZ() * 0.5;
      h = r - p;
      i = s - q;
      double t;
      if (h == 0.0) {
         t = f - (double)pos.getZ();
      } else if (i == 0.0) {
         t = d - (double)pos.getX();
      } else {
         double v = d - p;
         double w = f - q;
         t = (v * h + w * i) * 2.0;
      }

      d = p + h * t;
      f = q + i * t;
      this.updatePosition(d, e, f);
      double y = this.hasPassengers() ? 0.75 : 1.0;
      double z = this.getMaxOffRailSpeed();
      lv3 = this.getVelocity();
      this.move(MovementType.SELF, new Vec3d(MathHelper.clamp(y * lv3.x, -z, z), 0.0, MathHelper.clamp(y * lv3.z, -z, z)));
      if (lv5.getY() != 0 && MathHelper.floor(this.getX()) - pos.getX() == lv5.getX() && MathHelper.floor(this.getZ()) - pos.getZ() == lv5.getZ()) {
         this.updatePosition(this.getX(), this.getY() + (double)lv5.getY(), this.getZ());
      } else if (lv6.getY() != 0 && MathHelper.floor(this.getX()) - pos.getX() == lv6.getX() && MathHelper.floor(this.getZ()) - pos.getZ() == lv6.getZ()) {
         this.updatePosition(this.getX(), this.getY() + (double)lv6.getY(), this.getZ());
      }

      this.applySlowdown();
      Vec3d lv9 = this.snapPositionToRail(this.getX(), this.getY(), this.getZ());
      if (lv9 != null && lv != null) {
         double aa = (lv.y - lv9.y) * 0.05;
         Vec3d lv10 = this.getVelocity();
         double ab = Math.sqrt(squaredHorizontalLength(lv10));
         if (ab > 0.0) {
            this.setVelocity(lv10.multiply((ab + aa) / ab, 1.0, (ab + aa) / ab));
         }

         this.updatePosition(this.getX(), lv9.y, this.getZ());
      }

      int ac = MathHelper.floor(this.getX());
      int ad = MathHelper.floor(this.getZ());
      if (ac != pos.getX() || ad != pos.getZ()) {
         Vec3d lv11 = this.getVelocity();
         double ae = Math.sqrt(squaredHorizontalLength(lv11));
         this.setVelocity(ae * (double)(ac - pos.getX()), lv11.y, ae * (double)(ad - pos.getZ()));
      }

      if (bl) {
         Vec3d lv12 = this.getVelocity();
         double af = Math.sqrt(squaredHorizontalLength(lv12));
         if (af > 0.01) {
            double ag = 0.06;
            this.setVelocity(lv12.add(lv12.x / af * 0.06, 0.0, lv12.z / af * 0.06));
         } else {
            Vec3d lv13 = this.getVelocity();
            double ah = lv13.x;
            double ai = lv13.z;
            if (lv4 == RailShape.EAST_WEST) {
               if (this.willHitBlockAt(pos.west())) {
                  ah = 0.02;
               } else if (this.willHitBlockAt(pos.east())) {
                  ah = -0.02;
               }
            } else {
               if (lv4 != RailShape.NORTH_SOUTH) {
                  return;
               }

               if (this.willHitBlockAt(pos.north())) {
                  ai = 0.02;
               } else if (this.willHitBlockAt(pos.south())) {
                  ai = -0.02;
               }
            }

            this.setVelocity(ah, lv13.y, ai);
         }
      }
   }

   private boolean willHitBlockAt(BlockPos pos) {
      return this.world.getBlockState(pos).isSolidBlock(this.world, pos);
   }

   protected void applySlowdown() {
      double d = this.hasPassengers() ? 0.997 : 0.96;
      this.setVelocity(this.getVelocity().multiply(d, 0.0, d));
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public Vec3d snapPositionToRailWithOffset(double x, double y, double z, double offset) {
      int i = MathHelper.floor(x);
      int j = MathHelper.floor(y);
      int k = MathHelper.floor(z);
      if (this.world.getBlockState(new BlockPos(i, j - 1, k)).isIn(BlockTags.RAILS)) {
         j--;
      }

      BlockState lv = this.world.getBlockState(new BlockPos(i, j, k));
      if (AbstractRailBlock.isRail(lv)) {
         RailShape lv2 = lv.get(((AbstractRailBlock)lv.getBlock()).getShapeProperty());
         y = (double)j;
         if (lv2.isAscending()) {
            y = (double)(j + 1);
         }

         Pair<Vec3i, Vec3i> pair = getAdjacentRailPositionsByShape(lv2);
         Vec3i lv3 = (Vec3i)pair.getFirst();
         Vec3i lv4 = (Vec3i)pair.getSecond();
         double h = (double)(lv4.getX() - lv3.getX());
         double l = (double)(lv4.getZ() - lv3.getZ());
         double m = Math.sqrt(h * h + l * l);
         h /= m;
         l /= m;
         x += h * offset;
         z += l * offset;
         if (lv3.getY() != 0 && MathHelper.floor(x) - i == lv3.getX() && MathHelper.floor(z) - k == lv3.getZ()) {
            y += (double)lv3.getY();
         } else if (lv4.getY() != 0 && MathHelper.floor(x) - i == lv4.getX() && MathHelper.floor(z) - k == lv4.getZ()) {
            y += (double)lv4.getY();
         }

         return this.snapPositionToRail(x, y, z);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3d snapPositionToRail(double x, double y, double z) {
      int i = MathHelper.floor(x);
      int j = MathHelper.floor(y);
      int k = MathHelper.floor(z);
      if (this.world.getBlockState(new BlockPos(i, j - 1, k)).isIn(BlockTags.RAILS)) {
         j--;
      }

      BlockState lv = this.world.getBlockState(new BlockPos(i, j, k));
      if (AbstractRailBlock.isRail(lv)) {
         RailShape lv2 = lv.get(((AbstractRailBlock)lv.getBlock()).getShapeProperty());
         Pair<Vec3i, Vec3i> pair = getAdjacentRailPositionsByShape(lv2);
         Vec3i lv3 = (Vec3i)pair.getFirst();
         Vec3i lv4 = (Vec3i)pair.getSecond();
         double g = (double)i + 0.5 + (double)lv3.getX() * 0.5;
         double h = (double)j + 0.0625 + (double)lv3.getY() * 0.5;
         double l = (double)k + 0.5 + (double)lv3.getZ() * 0.5;
         double m = (double)i + 0.5 + (double)lv4.getX() * 0.5;
         double n = (double)j + 0.0625 + (double)lv4.getY() * 0.5;
         double o = (double)k + 0.5 + (double)lv4.getZ() * 0.5;
         double p = m - g;
         double q = (n - h) * 2.0;
         double r = o - l;
         double s;
         if (p == 0.0) {
            s = z - (double)k;
         } else if (r == 0.0) {
            s = x - (double)i;
         } else {
            double u = x - g;
            double v = z - l;
            s = (u * p + v * r) * 2.0;
         }

         x = g + p * s;
         y = h + q * s;
         z = l + r * s;
         if (q < 0.0) {
            y++;
         } else if (q > 0.0) {
            y += 0.5;
         }

         return new Vec3d(x, y, z);
      } else {
         return null;
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public Box getVisibilityBoundingBox() {
      Box lv = this.getBoundingBox();
      return this.hasCustomBlock() ? lv.expand((double)Math.abs(this.getBlockOffset()) / 16.0) : lv;
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
               double d = entity.getX() - this.getX();
               double e = entity.getZ() - this.getZ();
               double f = d * d + e * e;
               if (f >= 1.0E-4F) {
                  f = (double)MathHelper.sqrt(f);
                  d /= f;
                  e /= f;
                  double g = 1.0 / f;
                  if (g > 1.0) {
                     g = 1.0;
                  }

                  d *= g;
                  e *= g;
                  d *= 0.1F;
                  e *= 0.1F;
                  d *= (double)(1.0F - this.pushSpeedReduction);
                  e *= (double)(1.0F - this.pushSpeedReduction);
                  d *= 0.5;
                  e *= 0.5;
                  if (entity instanceof AbstractMinecartEntity) {
                     double h = entity.getX() - this.getX();
                     double i = entity.getZ() - this.getZ();
                     Vec3d lv = new Vec3d(h, 0.0, i).normalize();
                     Vec3d lv2 = new Vec3d(
                           (double)MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)), 0.0, (double)MathHelper.sin(this.yaw * (float) (Math.PI / 180.0))
                        )
                        .normalize();
                     double j = Math.abs(lv.dotProduct(lv2));
                     if (j < 0.8F) {
                        return;
                     }

                     Vec3d lv3 = this.getVelocity();
                     Vec3d lv4 = entity.getVelocity();
                     if (((AbstractMinecartEntity)entity).getMinecartType() == AbstractMinecartEntity.Type.FURNACE
                        && this.getMinecartType() != AbstractMinecartEntity.Type.FURNACE) {
                        this.setVelocity(lv3.multiply(0.2, 1.0, 0.2));
                        this.addVelocity(lv4.x - d, 0.0, lv4.z - e);
                        entity.setVelocity(lv4.multiply(0.95, 1.0, 0.95));
                     } else if (((AbstractMinecartEntity)entity).getMinecartType() != AbstractMinecartEntity.Type.FURNACE
                        && this.getMinecartType() == AbstractMinecartEntity.Type.FURNACE) {
                        entity.setVelocity(lv4.multiply(0.2, 1.0, 0.2));
                        entity.addVelocity(lv3.x + d, 0.0, lv3.z + e);
                        this.setVelocity(lv3.multiply(0.95, 1.0, 0.95));
                     } else {
                        double k = (lv4.x + lv3.x) / 2.0;
                        double l = (lv4.z + lv3.z) / 2.0;
                        this.setVelocity(lv3.multiply(0.2, 1.0, 0.2));
                        this.addVelocity(k - d, 0.0, l - e);
                        entity.setVelocity(lv4.multiply(0.2, 1.0, 0.2));
                        entity.addVelocity(k + d, 0.0, l + e);
                     }
                  } else {
                     this.addVelocity(-d, 0.0, -e);
                     entity.addVelocity(d / 4.0, 0.0, e / 4.0);
                  }
               }
            }
         }
      }
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
   @Override
   public void setVelocityClient(double x, double y, double z) {
      this.clientXVelocity = x;
      this.clientYVelocity = y;
      this.clientZVelocity = z;
      this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
   }

   public void setDamageWobbleStrength(float f) {
      this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, f);
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
