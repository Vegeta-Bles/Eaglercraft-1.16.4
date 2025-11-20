package net.minecraft.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.class_5459;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HoneyBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.ReusableStream;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.AreaHelper;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Entity implements Nameable, CommandOutput {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger MAX_ENTITY_ID = new AtomicInteger();
   private static final List<ItemStack> EMPTY_STACK_LIST = Collections.emptyList();
   private static final Box NULL_BOX = new Box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static double renderDistanceMultiplier = 1.0;
   private final EntityType<?> type;
   private int entityId = MAX_ENTITY_ID.incrementAndGet();
   public boolean inanimate;
   private final List<Entity> passengerList = Lists.newArrayList();
   protected int ridingCooldown;
   @Nullable
   private Entity vehicle;
   public boolean teleporting;
   public World world;
   public double prevX;
   public double prevY;
   public double prevZ;
   private Vec3d pos;
   private BlockPos blockPos;
   private Vec3d velocity = Vec3d.ZERO;
   public float yaw;
   public float pitch;
   public float prevYaw;
   public float prevPitch;
   private Box entityBounds = NULL_BOX;
   protected boolean onGround;
   public boolean horizontalCollision;
   public boolean verticalCollision;
   public boolean velocityModified;
   protected Vec3d movementMultiplier = Vec3d.ZERO;
   public boolean removed;
   public float prevHorizontalSpeed;
   public float horizontalSpeed;
   public float distanceTraveled;
   public float fallDistance;
   private float nextStepSoundDistance = 1.0F;
   private float nextFlySoundDistance = 1.0F;
   public double lastRenderX;
   public double lastRenderY;
   public double lastRenderZ;
   public float stepHeight;
   public boolean noClip;
   public float pushSpeedReduction;
   protected final Random random = new Random();
   public int age;
   private int fireTicks = -this.getBurningDuration();
   protected boolean touchingWater;
   protected Object2DoubleMap<Tag<Fluid>> fluidHeight = new Object2DoubleArrayMap(2);
   protected boolean submergedInWater;
   @Nullable
   protected Tag<Fluid> field_25599;
   public int timeUntilRegen;
   protected boolean firstUpdate = true;
   protected final DataTracker dataTracker;
   protected static final TrackedData<Byte> FLAGS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.BYTE);
   private static final TrackedData<Integer> AIR = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Optional<Text>> CUSTOM_NAME = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.OPTIONAL_TEXT_COMPONENT);
   private static final TrackedData<Boolean> NAME_VISIBLE = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Boolean> SILENT = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Boolean> NO_GRAVITY = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.BOOLEAN);
   protected static final TrackedData<EntityPose> POSE = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.ENTITY_POSE);
   public boolean updateNeeded;
   public int chunkX;
   public int chunkY;
   public int chunkZ;
   private boolean chunkPosUpdateRequested;
   private Vec3d trackedPosition;
   public boolean ignoreCameraFrustum;
   public boolean velocityDirty;
   private int netherPortalCooldown;
   protected boolean inNetherPortal;
   protected int netherPortalTime;
   protected BlockPos lastNetherPortalPosition;
   private boolean invulnerable;
   protected UUID uuid = MathHelper.randomUuid(this.random);
   protected String uuidString = this.uuid.toString();
   protected boolean glowing;
   private final Set<String> scoreboardTags = Sets.newHashSet();
   private boolean teleportRequested;
   private final double[] pistonMovementDelta = new double[]{0.0, 0.0, 0.0};
   private long pistonMovementTick;
   private EntityDimensions dimensions;
   private float standingEyeHeight;

   public Entity(EntityType<?> type, World world) {
      this.type = type;
      this.world = world;
      this.dimensions = type.getDimensions();
      this.pos = Vec3d.ZERO;
      this.blockPos = BlockPos.ORIGIN;
      this.trackedPosition = Vec3d.ZERO;
      this.updatePosition(0.0, 0.0, 0.0);
      this.dataTracker = new DataTracker(this);
      this.dataTracker.startTracking(FLAGS, (byte)0);
      this.dataTracker.startTracking(AIR, this.getMaxAir());
      this.dataTracker.startTracking(NAME_VISIBLE, false);
      this.dataTracker.startTracking(CUSTOM_NAME, Optional.empty());
      this.dataTracker.startTracking(SILENT, false);
      this.dataTracker.startTracking(NO_GRAVITY, false);
      this.dataTracker.startTracking(POSE, EntityPose.STANDING);
      this.initDataTracker();
      this.standingEyeHeight = this.getEyeHeight(EntityPose.STANDING, this.dimensions);
   }

   public boolean method_30632(BlockPos _snowman, BlockState _snowman) {
      VoxelShape _snowmanxx = _snowman.getCollisionShape(this.world, _snowman, ShapeContext.of(this));
      VoxelShape _snowmanxxx = _snowmanxx.offset((double)_snowman.getX(), (double)_snowman.getY(), (double)_snowman.getZ());
      return VoxelShapes.matchesAnywhere(_snowmanxxx, VoxelShapes.cuboid(this.getBoundingBox()), BooleanBiFunction.AND);
   }

   public int getTeamColorValue() {
      AbstractTeam _snowman = this.getScoreboardTeam();
      return _snowman != null && _snowman.getColor().getColorValue() != null ? _snowman.getColor().getColorValue() : 16777215;
   }

   public boolean isSpectator() {
      return false;
   }

   public final void detach() {
      if (this.hasPassengers()) {
         this.removeAllPassengers();
      }

      if (this.hasVehicle()) {
         this.stopRiding();
      }
   }

   public void updateTrackedPosition(double x, double y, double z) {
      this.updateTrackedPosition(new Vec3d(x, y, z));
   }

   public void updateTrackedPosition(Vec3d pos) {
      this.trackedPosition = pos;
   }

   public Vec3d getTrackedPosition() {
      return this.trackedPosition;
   }

   public EntityType<?> getType() {
      return this.type;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public void setEntityId(int id) {
      this.entityId = id;
   }

   public Set<String> getScoreboardTags() {
      return this.scoreboardTags;
   }

   public boolean addScoreboardTag(String tag) {
      return this.scoreboardTags.size() >= 1024 ? false : this.scoreboardTags.add(tag);
   }

   public boolean removeScoreboardTag(String tag) {
      return this.scoreboardTags.remove(tag);
   }

   public void kill() {
      this.remove();
   }

   protected abstract void initDataTracker();

   public DataTracker getDataTracker() {
      return this.dataTracker;
   }

   @Override
   public boolean equals(Object o) {
      return o instanceof Entity ? ((Entity)o).entityId == this.entityId : false;
   }

   @Override
   public int hashCode() {
      return this.entityId;
   }

   protected void afterSpawn() {
      if (this.world != null) {
         for (double _snowman = this.getY(); _snowman > 0.0 && _snowman < 256.0; _snowman++) {
            this.updatePosition(this.getX(), _snowman, this.getZ());
            if (this.world.isSpaceEmpty(this)) {
               break;
            }
         }

         this.setVelocity(Vec3d.ZERO);
         this.pitch = 0.0F;
      }
   }

   public void remove() {
      this.removed = true;
   }

   public void setPose(EntityPose pose) {
      this.dataTracker.set(POSE, pose);
   }

   public EntityPose getPose() {
      return this.dataTracker.get(POSE);
   }

   public boolean isInRange(Entity other, double radius) {
      double _snowman = other.pos.x - this.pos.x;
      double _snowmanx = other.pos.y - this.pos.y;
      double _snowmanxx = other.pos.z - this.pos.z;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx < radius * radius;
   }

   protected void setRotation(float yaw, float pitch) {
      this.yaw = yaw % 360.0F;
      this.pitch = pitch % 360.0F;
   }

   public void updatePosition(double x, double y, double z) {
      this.setPos(x, y, z);
      this.setBoundingBox(this.dimensions.method_30231(x, y, z));
   }

   protected void refreshPosition() {
      this.updatePosition(this.pos.x, this.pos.y, this.pos.z);
   }

   public void changeLookDirection(double cursorDeltaX, double cursorDeltaY) {
      double _snowman = cursorDeltaY * 0.15;
      double _snowmanx = cursorDeltaX * 0.15;
      this.pitch = (float)((double)this.pitch + _snowman);
      this.yaw = (float)((double)this.yaw + _snowmanx);
      this.pitch = MathHelper.clamp(this.pitch, -90.0F, 90.0F);
      this.prevPitch = (float)((double)this.prevPitch + _snowman);
      this.prevYaw = (float)((double)this.prevYaw + _snowmanx);
      this.prevPitch = MathHelper.clamp(this.prevPitch, -90.0F, 90.0F);
      if (this.vehicle != null) {
         this.vehicle.onPassengerLookAround(this);
      }
   }

   public void tick() {
      if (!this.world.isClient) {
         this.setFlag(6, this.isGlowing());
      }

      this.baseTick();
   }

   public void baseTick() {
      this.world.getProfiler().push("entityBaseTick");
      if (this.hasVehicle() && this.getVehicle().removed) {
         this.stopRiding();
      }

      if (this.ridingCooldown > 0) {
         this.ridingCooldown--;
      }

      this.prevHorizontalSpeed = this.horizontalSpeed;
      this.prevPitch = this.pitch;
      this.prevYaw = this.yaw;
      this.tickNetherPortal();
      if (this.shouldSpawnSprintingParticles()) {
         this.spawnSprintingParticles();
      }

      this.updateWaterState();
      this.updateSubmergedInWaterState();
      this.updateSwimming();
      if (this.world.isClient) {
         this.extinguish();
      } else if (this.fireTicks > 0) {
         if (this.isFireImmune()) {
            this.setFireTicks(this.fireTicks - 4);
            if (this.fireTicks < 0) {
               this.extinguish();
            }
         } else {
            if (this.fireTicks % 20 == 0 && !this.isInLava()) {
               this.damage(DamageSource.ON_FIRE, 1.0F);
            }

            this.setFireTicks(this.fireTicks - 1);
         }
      }

      if (this.isInLava()) {
         this.setOnFireFromLava();
         this.fallDistance *= 0.5F;
      }

      if (this.getY() < -64.0) {
         this.destroy();
      }

      if (!this.world.isClient) {
         this.setFlag(0, this.fireTicks > 0);
      }

      this.firstUpdate = false;
      this.world.getProfiler().pop();
   }

   public void resetNetherPortalCooldown() {
      this.netherPortalCooldown = this.getDefaultNetherPortalCooldown();
   }

   public boolean hasNetherPortalCooldown() {
      return this.netherPortalCooldown > 0;
   }

   protected void tickNetherPortalCooldown() {
      if (this.hasNetherPortalCooldown()) {
         this.netherPortalCooldown--;
      }
   }

   public int getMaxNetherPortalTime() {
      return 0;
   }

   protected void setOnFireFromLava() {
      if (!this.isFireImmune()) {
         this.setOnFireFor(15);
         this.damage(DamageSource.LAVA, 4.0F);
      }
   }

   public void setOnFireFor(int seconds) {
      int _snowman = seconds * 20;
      if (this instanceof LivingEntity) {
         _snowman = ProtectionEnchantment.transformFireDuration((LivingEntity)this, _snowman);
      }

      if (this.fireTicks < _snowman) {
         this.setFireTicks(_snowman);
      }
   }

   public void setFireTicks(int ticks) {
      this.fireTicks = ticks;
   }

   public int getFireTicks() {
      return this.fireTicks;
   }

   public void extinguish() {
      this.setFireTicks(0);
   }

   protected void destroy() {
      this.remove();
   }

   public boolean doesNotCollide(double offsetX, double offsetY, double offsetZ) {
      return this.doesNotCollide(this.getBoundingBox().offset(offsetX, offsetY, offsetZ));
   }

   private boolean doesNotCollide(Box box) {
      return this.world.isSpaceEmpty(this, box) && !this.world.containsFluid(box);
   }

   public void setOnGround(boolean onGround) {
      this.onGround = onGround;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void move(MovementType type, Vec3d movement) {
      if (this.noClip) {
         this.setBoundingBox(this.getBoundingBox().offset(movement));
         this.moveToBoundingBoxCenter();
      } else {
         if (type == MovementType.PISTON) {
            movement = this.adjustMovementForPiston(movement);
            if (movement.equals(Vec3d.ZERO)) {
               return;
            }
         }

         this.world.getProfiler().push("move");
         if (this.movementMultiplier.lengthSquared() > 1.0E-7) {
            movement = movement.multiply(this.movementMultiplier);
            this.movementMultiplier = Vec3d.ZERO;
            this.setVelocity(Vec3d.ZERO);
         }

         movement = this.adjustMovementForSneaking(movement, type);
         Vec3d _snowman = this.adjustMovementForCollisions(movement);
         if (_snowman.lengthSquared() > 1.0E-7) {
            this.setBoundingBox(this.getBoundingBox().offset(_snowman));
            this.moveToBoundingBoxCenter();
         }

         this.world.getProfiler().pop();
         this.world.getProfiler().push("rest");
         this.horizontalCollision = !MathHelper.approximatelyEquals(movement.x, _snowman.x) || !MathHelper.approximatelyEquals(movement.z, _snowman.z);
         this.verticalCollision = movement.y != _snowman.y;
         this.onGround = this.verticalCollision && movement.y < 0.0;
         BlockPos _snowmanx = this.getLandingPos();
         BlockState _snowmanxx = this.world.getBlockState(_snowmanx);
         this.fall(_snowman.y, this.onGround, _snowmanxx, _snowmanx);
         Vec3d _snowmanxxx = this.getVelocity();
         if (movement.x != _snowman.x) {
            this.setVelocity(0.0, _snowmanxxx.y, _snowmanxxx.z);
         }

         if (movement.z != _snowman.z) {
            this.setVelocity(_snowmanxxx.x, _snowmanxxx.y, 0.0);
         }

         Block _snowmanxxxx = _snowmanxx.getBlock();
         if (movement.y != _snowman.y) {
            _snowmanxxxx.onEntityLand(this.world, this);
         }

         if (this.onGround && !this.bypassesSteppingEffects()) {
            _snowmanxxxx.onSteppedOn(this.world, _snowmanx, this);
         }

         if (this.canClimb() && !this.hasVehicle()) {
            double _snowmanxxxxx = _snowman.x;
            double _snowmanxxxxxx = _snowman.y;
            double _snowmanxxxxxxx = _snowman.z;
            if (!_snowmanxxxx.isIn(BlockTags.CLIMBABLE)) {
               _snowmanxxxxxx = 0.0;
            }

            this.horizontalSpeed = (float)((double)this.horizontalSpeed + (double)MathHelper.sqrt(squaredHorizontalLength(_snowman)) * 0.6);
            this.distanceTraveled = (float)(
               (double)this.distanceTraveled + (double)MathHelper.sqrt(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx) * 0.6
            );
            if (this.distanceTraveled > this.nextStepSoundDistance && !_snowmanxx.isAir()) {
               this.nextStepSoundDistance = this.calculateNextStepSoundDistance();
               if (this.isTouchingWater()) {
                  Entity _snowmanxxxxxxxx = this.hasPassengers() && this.getPrimaryPassenger() != null ? this.getPrimaryPassenger() : this;
                  float _snowmanxxxxxxxxx = _snowmanxxxxxxxx == this ? 0.35F : 0.4F;
                  Vec3d _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getVelocity();
                  float _snowmanxxxxxxxxxxx = MathHelper.sqrt(
                        _snowmanxxxxxxxxxx.x * _snowmanxxxxxxxxxx.x * 0.2F + _snowmanxxxxxxxxxx.y * _snowmanxxxxxxxxxx.y + _snowmanxxxxxxxxxx.z * _snowmanxxxxxxxxxx.z * 0.2F
                     )
                     * _snowmanxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxx > 1.0F) {
                     _snowmanxxxxxxxxxxx = 1.0F;
                  }

                  this.playSwimSound(_snowmanxxxxxxxxxxx);
               } else {
                  this.playStepSound(_snowmanx, _snowmanxx);
               }
            } else if (this.distanceTraveled > this.nextFlySoundDistance && this.hasWings() && _snowmanxx.isAir()) {
               this.nextFlySoundDistance = this.playFlySound(this.distanceTraveled);
            }
         }

         try {
            this.checkBlockCollision();
         } catch (Throwable var18) {
            CrashReport _snowmanxxxxxxxx = CrashReport.create(var18, "Checking entity block collision");
            CrashReportSection _snowmanxxxxxxxxx = _snowmanxxxxxxxx.addElement("Entity being checked for collision");
            this.populateCrashReport(_snowmanxxxxxxxxx);
            throw new CrashException(_snowmanxxxxxxxx);
         }

         float _snowmanxxxxxxxx = this.getVelocityMultiplier();
         this.setVelocity(this.getVelocity().multiply((double)_snowmanxxxxxxxx, 1.0, (double)_snowmanxxxxxxxx));
         if (this.world
               .method_29556(this.getBoundingBox().contract(0.001))
               .noneMatch(_snowmanxxxxxxxxx -> _snowmanxxxxxxxxx.isIn(BlockTags.FIRE) || _snowmanxxxxxxxxx.isOf(Blocks.LAVA))
            && this.fireTicks <= 0) {
            this.setFireTicks(-this.getBurningDuration());
         }

         if (this.isWet() && this.isOnFire()) {
            this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            this.setFireTicks(-this.getBurningDuration());
         }

         this.world.getProfiler().pop();
      }
   }

   protected BlockPos getLandingPos() {
      int _snowman = MathHelper.floor(this.pos.x);
      int _snowmanx = MathHelper.floor(this.pos.y - 0.2F);
      int _snowmanxx = MathHelper.floor(this.pos.z);
      BlockPos _snowmanxxx = new BlockPos(_snowman, _snowmanx, _snowmanxx);
      if (this.world.getBlockState(_snowmanxxx).isAir()) {
         BlockPos _snowmanxxxx = _snowmanxxx.down();
         BlockState _snowmanxxxxx = this.world.getBlockState(_snowmanxxxx);
         Block _snowmanxxxxxx = _snowmanxxxxx.getBlock();
         if (_snowmanxxxxxx.isIn(BlockTags.FENCES) || _snowmanxxxxxx.isIn(BlockTags.WALLS) || _snowmanxxxxxx instanceof FenceGateBlock) {
            return _snowmanxxxx;
         }
      }

      return _snowmanxxx;
   }

   protected float getJumpVelocityMultiplier() {
      float _snowman = this.world.getBlockState(this.getBlockPos()).getBlock().getJumpVelocityMultiplier();
      float _snowmanx = this.world.getBlockState(this.getVelocityAffectingPos()).getBlock().getJumpVelocityMultiplier();
      return (double)_snowman == 1.0 ? _snowmanx : _snowman;
   }

   protected float getVelocityMultiplier() {
      Block _snowman = this.world.getBlockState(this.getBlockPos()).getBlock();
      float _snowmanx = _snowman.getVelocityMultiplier();
      if (_snowman != Blocks.WATER && _snowman != Blocks.BUBBLE_COLUMN) {
         return (double)_snowmanx == 1.0 ? this.world.getBlockState(this.getVelocityAffectingPos()).getBlock().getVelocityMultiplier() : _snowmanx;
      } else {
         return _snowmanx;
      }
   }

   protected BlockPos getVelocityAffectingPos() {
      return new BlockPos(this.pos.x, this.getBoundingBox().minY - 0.5000001, this.pos.z);
   }

   protected Vec3d adjustMovementForSneaking(Vec3d movement, MovementType type) {
      return movement;
   }

   protected Vec3d adjustMovementForPiston(Vec3d movement) {
      if (movement.lengthSquared() <= 1.0E-7) {
         return movement;
      } else {
         long _snowman = this.world.getTime();
         if (_snowman != this.pistonMovementTick) {
            Arrays.fill(this.pistonMovementDelta, 0.0);
            this.pistonMovementTick = _snowman;
         }

         if (movement.x != 0.0) {
            double _snowmanx = this.calculatePistonMovementFactor(Direction.Axis.X, movement.x);
            return Math.abs(_snowmanx) <= 1.0E-5F ? Vec3d.ZERO : new Vec3d(_snowmanx, 0.0, 0.0);
         } else if (movement.y != 0.0) {
            double _snowmanx = this.calculatePistonMovementFactor(Direction.Axis.Y, movement.y);
            return Math.abs(_snowmanx) <= 1.0E-5F ? Vec3d.ZERO : new Vec3d(0.0, _snowmanx, 0.0);
         } else if (movement.z != 0.0) {
            double _snowmanx = this.calculatePistonMovementFactor(Direction.Axis.Z, movement.z);
            return Math.abs(_snowmanx) <= 1.0E-5F ? Vec3d.ZERO : new Vec3d(0.0, 0.0, _snowmanx);
         } else {
            return Vec3d.ZERO;
         }
      }
   }

   private double calculatePistonMovementFactor(Direction.Axis axis, double offsetFactor) {
      int _snowman = axis.ordinal();
      double _snowmanx = MathHelper.clamp(offsetFactor + this.pistonMovementDelta[_snowman], -0.51, 0.51);
      offsetFactor = _snowmanx - this.pistonMovementDelta[_snowman];
      this.pistonMovementDelta[_snowman] = _snowmanx;
      return offsetFactor;
   }

   private Vec3d adjustMovementForCollisions(Vec3d movement) {
      Box _snowman = this.getBoundingBox();
      ShapeContext _snowmanx = ShapeContext.of(this);
      VoxelShape _snowmanxx = this.world.getWorldBorder().asVoxelShape();
      Stream<VoxelShape> _snowmanxxx = VoxelShapes.matchesAnywhere(_snowmanxx, VoxelShapes.cuboid(_snowman.contract(1.0E-7)), BooleanBiFunction.AND)
         ? Stream.empty()
         : Stream.of(_snowmanxx);
      Stream<VoxelShape> _snowmanxxxx = this.world.getEntityCollisions(this, _snowman.stretch(movement), _snowmanxxxxx -> true);
      ReusableStream<VoxelShape> _snowmanxxxxx = new ReusableStream<>(Stream.concat(_snowmanxxxx, _snowmanxxx));
      Vec3d _snowmanxxxxxx = movement.lengthSquared() == 0.0 ? movement : adjustMovementForCollisions(this, movement, _snowman, this.world, _snowmanx, _snowmanxxxxx);
      boolean _snowmanxxxxxxx = movement.x != _snowmanxxxxxx.x;
      boolean _snowmanxxxxxxxx = movement.y != _snowmanxxxxxx.y;
      boolean _snowmanxxxxxxxxx = movement.z != _snowmanxxxxxx.z;
      boolean _snowmanxxxxxxxxxx = this.onGround || _snowmanxxxxxxxx && movement.y < 0.0;
      if (this.stepHeight > 0.0F && _snowmanxxxxxxxxxx && (_snowmanxxxxxxx || _snowmanxxxxxxxxx)) {
         Vec3d _snowmanxxxxxxxxxxx = adjustMovementForCollisions(this, new Vec3d(movement.x, (double)this.stepHeight, movement.z), _snowman, this.world, _snowmanx, _snowmanxxxxx);
         Vec3d _snowmanxxxxxxxxxxxx = adjustMovementForCollisions(
            this, new Vec3d(0.0, (double)this.stepHeight, 0.0), _snowman.stretch(movement.x, 0.0, movement.z), this.world, _snowmanx, _snowmanxxxxx
         );
         if (_snowmanxxxxxxxxxxxx.y < (double)this.stepHeight) {
            Vec3d _snowmanxxxxxxxxxxxxx = adjustMovementForCollisions(this, new Vec3d(movement.x, 0.0, movement.z), _snowman.offset(_snowmanxxxxxxxxxxxx), this.world, _snowmanx, _snowmanxxxxx)
               .add(_snowmanxxxxxxxxxxxx);
            if (squaredHorizontalLength(_snowmanxxxxxxxxxxxxx) > squaredHorizontalLength(_snowmanxxxxxxxxxxx)) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
            }
         }

         if (squaredHorizontalLength(_snowmanxxxxxxxxxxx) > squaredHorizontalLength(_snowmanxxxxxx)) {
            return _snowmanxxxxxxxxxxx.add(
               adjustMovementForCollisions(this, new Vec3d(0.0, -_snowmanxxxxxxxxxxx.y + movement.y, 0.0), _snowman.offset(_snowmanxxxxxxxxxxx), this.world, _snowmanx, _snowmanxxxxx)
            );
         }
      }

      return _snowmanxxxxxx;
   }

   public static double squaredHorizontalLength(Vec3d vector) {
      return vector.x * vector.x + vector.z * vector.z;
   }

   public static Vec3d adjustMovementForCollisions(
      @Nullable Entity entity, Vec3d movement, Box entityBoundingBox, World world, ShapeContext context, ReusableStream<VoxelShape> collisions
   ) {
      boolean _snowman = movement.x == 0.0;
      boolean _snowmanx = movement.y == 0.0;
      boolean _snowmanxx = movement.z == 0.0;
      if ((!_snowman || !_snowmanx) && (!_snowman || !_snowmanxx) && (!_snowmanx || !_snowmanxx)) {
         ReusableStream<VoxelShape> _snowmanxxx = new ReusableStream<>(
            Stream.concat(collisions.stream(), world.getBlockCollisions(entity, entityBoundingBox.stretch(movement)))
         );
         return adjustMovementForCollisions(movement, entityBoundingBox, _snowmanxxx);
      } else {
         return adjustSingleAxisMovementForCollisions(movement, entityBoundingBox, world, context, collisions);
      }
   }

   public static Vec3d adjustMovementForCollisions(Vec3d movement, Box entityBoundingBox, ReusableStream<VoxelShape> collisions) {
      double _snowman = movement.x;
      double _snowmanx = movement.y;
      double _snowmanxx = movement.z;
      if (_snowmanx != 0.0) {
         _snowmanx = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, entityBoundingBox, collisions.stream(), _snowmanx);
         if (_snowmanx != 0.0) {
            entityBoundingBox = entityBoundingBox.offset(0.0, _snowmanx, 0.0);
         }
      }

      boolean _snowmanxxx = Math.abs(_snowman) < Math.abs(_snowmanxx);
      if (_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions.stream(), _snowmanxx);
         if (_snowmanxx != 0.0) {
            entityBoundingBox = entityBoundingBox.offset(0.0, 0.0, _snowmanxx);
         }
      }

      if (_snowman != 0.0) {
         _snowman = VoxelShapes.calculateMaxOffset(Direction.Axis.X, entityBoundingBox, collisions.stream(), _snowman);
         if (!_snowmanxxx && _snowman != 0.0) {
            entityBoundingBox = entityBoundingBox.offset(_snowman, 0.0, 0.0);
         }
      }

      if (!_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions.stream(), _snowmanxx);
      }

      return new Vec3d(_snowman, _snowmanx, _snowmanxx);
   }

   public static Vec3d adjustSingleAxisMovementForCollisions(
      Vec3d movement, Box entityBoundingBox, WorldView world, ShapeContext context, ReusableStream<VoxelShape> collisions
   ) {
      double _snowman = movement.x;
      double _snowmanx = movement.y;
      double _snowmanxx = movement.z;
      if (_snowmanx != 0.0) {
         _snowmanx = VoxelShapes.calculatePushVelocity(Direction.Axis.Y, entityBoundingBox, world, _snowmanx, context, collisions.stream());
         if (_snowmanx != 0.0) {
            entityBoundingBox = entityBoundingBox.offset(0.0, _snowmanx, 0.0);
         }
      }

      boolean _snowmanxxx = Math.abs(_snowman) < Math.abs(_snowmanxx);
      if (_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = VoxelShapes.calculatePushVelocity(Direction.Axis.Z, entityBoundingBox, world, _snowmanxx, context, collisions.stream());
         if (_snowmanxx != 0.0) {
            entityBoundingBox = entityBoundingBox.offset(0.0, 0.0, _snowmanxx);
         }
      }

      if (_snowman != 0.0) {
         _snowman = VoxelShapes.calculatePushVelocity(Direction.Axis.X, entityBoundingBox, world, _snowman, context, collisions.stream());
         if (!_snowmanxxx && _snowman != 0.0) {
            entityBoundingBox = entityBoundingBox.offset(_snowman, 0.0, 0.0);
         }
      }

      if (!_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = VoxelShapes.calculatePushVelocity(Direction.Axis.Z, entityBoundingBox, world, _snowmanxx, context, collisions.stream());
      }

      return new Vec3d(_snowman, _snowmanx, _snowmanxx);
   }

   protected float calculateNextStepSoundDistance() {
      return (float)((int)this.distanceTraveled + 1);
   }

   public void moveToBoundingBoxCenter() {
      Box _snowman = this.getBoundingBox();
      this.setPos((_snowman.minX + _snowman.maxX) / 2.0, _snowman.minY, (_snowman.minZ + _snowman.maxZ) / 2.0);
   }

   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_GENERIC_SWIM;
   }

   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_GENERIC_SPLASH;
   }

   protected SoundEvent getHighSpeedSplashSound() {
      return SoundEvents.ENTITY_GENERIC_SPLASH;
   }

   protected void checkBlockCollision() {
      Box _snowman = this.getBoundingBox();
      BlockPos _snowmanx = new BlockPos(_snowman.minX + 0.001, _snowman.minY + 0.001, _snowman.minZ + 0.001);
      BlockPos _snowmanxx = new BlockPos(_snowman.maxX - 0.001, _snowman.maxY - 0.001, _snowman.maxZ - 0.001);
      BlockPos.Mutable _snowmanxxx = new BlockPos.Mutable();
      if (this.world.isRegionLoaded(_snowmanx, _snowmanxx)) {
         for (int _snowmanxxxx = _snowmanx.getX(); _snowmanxxxx <= _snowmanxx.getX(); _snowmanxxxx++) {
            for (int _snowmanxxxxx = _snowmanx.getY(); _snowmanxxxxx <= _snowmanxx.getY(); _snowmanxxxxx++) {
               for (int _snowmanxxxxxx = _snowmanx.getZ(); _snowmanxxxxxx <= _snowmanxx.getZ(); _snowmanxxxxxx++) {
                  _snowmanxxx.set(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  BlockState _snowmanxxxxxxx = this.world.getBlockState(_snowmanxxx);

                  try {
                     _snowmanxxxxxxx.onEntityCollision(this.world, _snowmanxxx, this);
                     this.onBlockCollision(_snowmanxxxxxxx);
                  } catch (Throwable var12) {
                     CrashReport _snowmanxxxxxxxx = CrashReport.create(var12, "Colliding entity with block");
                     CrashReportSection _snowmanxxxxxxxxx = _snowmanxxxxxxxx.addElement("Block being collided with");
                     CrashReportSection.addBlockInfo(_snowmanxxxxxxxxx, _snowmanxxx, _snowmanxxxxxxx);
                     throw new CrashException(_snowmanxxxxxxxx);
                  }
               }
            }
         }
      }
   }

   protected void onBlockCollision(BlockState state) {
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      if (!state.getMaterial().isLiquid()) {
         BlockState _snowman = this.world.getBlockState(pos.up());
         BlockSoundGroup _snowmanx = _snowman.isOf(Blocks.SNOW) ? _snowman.getSoundGroup() : state.getSoundGroup();
         this.playSound(_snowmanx.getStepSound(), _snowmanx.getVolume() * 0.15F, _snowmanx.getPitch());
      }
   }

   protected void playSwimSound(float volume) {
      this.playSound(this.getSwimSound(), volume, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
   }

   protected float playFlySound(float distance) {
      return 0.0F;
   }

   protected boolean hasWings() {
      return false;
   }

   public void playSound(SoundEvent sound, float volume, float pitch) {
      if (!this.isSilent()) {
         this.world.playSound(null, this.getX(), this.getY(), this.getZ(), sound, this.getSoundCategory(), volume, pitch);
      }
   }

   public boolean isSilent() {
      return this.dataTracker.get(SILENT);
   }

   public void setSilent(boolean silent) {
      this.dataTracker.set(SILENT, silent);
   }

   public boolean hasNoGravity() {
      return this.dataTracker.get(NO_GRAVITY);
   }

   public void setNoGravity(boolean noGravity) {
      this.dataTracker.set(NO_GRAVITY, noGravity);
   }

   protected boolean canClimb() {
      return true;
   }

   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
      if (onGround) {
         if (this.fallDistance > 0.0F) {
            landedState.getBlock().onLandedUpon(this.world, landedPosition, this, this.fallDistance);
         }

         this.fallDistance = 0.0F;
      } else if (heightDifference < 0.0) {
         this.fallDistance = (float)((double)this.fallDistance - heightDifference);
      }
   }

   public boolean isFireImmune() {
      return this.getType().isFireImmune();
   }

   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      if (this.hasPassengers()) {
         for (Entity _snowman : this.getPassengerList()) {
            _snowman.handleFallDamage(fallDistance, damageMultiplier);
         }
      }

      return false;
   }

   public boolean isTouchingWater() {
      return this.touchingWater;
   }

   private boolean isBeingRainedOn() {
      BlockPos _snowman = this.getBlockPos();
      return this.world.hasRain(_snowman) || this.world.hasRain(new BlockPos((double)_snowman.getX(), this.getBoundingBox().maxY, (double)_snowman.getZ()));
   }

   private boolean isInsideBubbleColumn() {
      return this.world.getBlockState(this.getBlockPos()).isOf(Blocks.BUBBLE_COLUMN);
   }

   public boolean isTouchingWaterOrRain() {
      return this.isTouchingWater() || this.isBeingRainedOn();
   }

   public boolean isWet() {
      return this.isTouchingWater() || this.isBeingRainedOn() || this.isInsideBubbleColumn();
   }

   public boolean isInsideWaterOrBubbleColumn() {
      return this.isTouchingWater() || this.isInsideBubbleColumn();
   }

   public boolean isSubmergedInWater() {
      return this.submergedInWater && this.isTouchingWater();
   }

   public void updateSwimming() {
      if (this.isSwimming()) {
         this.setSwimming(this.isSprinting() && this.isTouchingWater() && !this.hasVehicle());
      } else {
         this.setSwimming(this.isSprinting() && this.isSubmergedInWater() && !this.hasVehicle());
      }
   }

   protected boolean updateWaterState() {
      this.fluidHeight.clear();
      this.checkWaterState();
      double _snowman = this.world.getDimension().isUltrawarm() ? 0.007 : 0.0023333333333333335;
      boolean _snowmanx = this.updateMovementInFluid(FluidTags.LAVA, _snowman);
      return this.isTouchingWater() || _snowmanx;
   }

   void checkWaterState() {
      if (this.getVehicle() instanceof BoatEntity) {
         this.touchingWater = false;
      } else if (this.updateMovementInFluid(FluidTags.WATER, 0.014)) {
         if (!this.touchingWater && !this.firstUpdate) {
            this.onSwimmingStart();
         }

         this.fallDistance = 0.0F;
         this.touchingWater = true;
         this.extinguish();
      } else {
         this.touchingWater = false;
      }
   }

   private void updateSubmergedInWaterState() {
      this.submergedInWater = this.isSubmergedIn(FluidTags.WATER);
      this.field_25599 = null;
      double _snowman = this.getEyeY() - 0.11111111F;
      Entity _snowmanx = this.getVehicle();
      if (_snowmanx instanceof BoatEntity) {
         BoatEntity _snowmanxx = (BoatEntity)_snowmanx;
         if (!_snowmanxx.isSubmergedInWater() && _snowmanxx.getBoundingBox().maxY >= _snowman && _snowmanxx.getBoundingBox().minY <= _snowman) {
            return;
         }
      }

      BlockPos _snowmanxx = new BlockPos(this.getX(), _snowman, this.getZ());
      FluidState _snowmanxxx = this.world.getFluidState(_snowmanxx);

      for (Tag<Fluid> _snowmanxxxx : FluidTags.getRequiredTags()) {
         if (_snowmanxxx.isIn(_snowmanxxxx)) {
            double _snowmanxxxxx = (double)((float)_snowmanxx.getY() + _snowmanxxx.getHeight(this.world, _snowmanxx));
            if (_snowmanxxxxx > _snowman) {
               this.field_25599 = _snowmanxxxx;
            }

            return;
         }
      }
   }

   protected void onSwimmingStart() {
      Entity _snowman = this.hasPassengers() && this.getPrimaryPassenger() != null ? this.getPrimaryPassenger() : this;
      float _snowmanx = _snowman == this ? 0.2F : 0.9F;
      Vec3d _snowmanxx = _snowman.getVelocity();
      float _snowmanxxx = MathHelper.sqrt(_snowmanxx.x * _snowmanxx.x * 0.2F + _snowmanxx.y * _snowmanxx.y + _snowmanxx.z * _snowmanxx.z * 0.2F) * _snowmanx;
      if (_snowmanxxx > 1.0F) {
         _snowmanxxx = 1.0F;
      }

      if ((double)_snowmanxxx < 0.25) {
         this.playSound(this.getSplashSound(), _snowmanxxx, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
      } else {
         this.playSound(this.getHighSpeedSplashSound(), _snowmanxxx, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
      }

      float _snowmanxxxx = (float)MathHelper.floor(this.getY());

      for (int _snowmanxxxxx = 0; (float)_snowmanxxxxx < 1.0F + this.dimensions.width * 20.0F; _snowmanxxxxx++) {
         double _snowmanxxxxxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         double _snowmanxxxxxxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         this.world
            .addParticle(
               ParticleTypes.BUBBLE,
               this.getX() + _snowmanxxxxxx,
               (double)(_snowmanxxxx + 1.0F),
               this.getZ() + _snowmanxxxxxxx,
               _snowmanxx.x,
               _snowmanxx.y - this.random.nextDouble() * 0.2F,
               _snowmanxx.z
            );
      }

      for (int _snowmanxxxxx = 0; (float)_snowmanxxxxx < 1.0F + this.dimensions.width * 20.0F; _snowmanxxxxx++) {
         double _snowmanxxxxxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         double _snowmanxxxxxxx = (this.random.nextDouble() * 2.0 - 1.0) * (double)this.dimensions.width;
         this.world.addParticle(ParticleTypes.SPLASH, this.getX() + _snowmanxxxxxx, (double)(_snowmanxxxx + 1.0F), this.getZ() + _snowmanxxxxxxx, _snowmanxx.x, _snowmanxx.y, _snowmanxx.z);
      }
   }

   protected BlockState getLandingBlockState() {
      return this.world.getBlockState(this.getLandingPos());
   }

   public boolean shouldSpawnSprintingParticles() {
      return this.isSprinting() && !this.isTouchingWater() && !this.isSpectator() && !this.isInSneakingPose() && !this.isInLava() && this.isAlive();
   }

   protected void spawnSprintingParticles() {
      int _snowman = MathHelper.floor(this.getX());
      int _snowmanx = MathHelper.floor(this.getY() - 0.2F);
      int _snowmanxx = MathHelper.floor(this.getZ());
      BlockPos _snowmanxxx = new BlockPos(_snowman, _snowmanx, _snowmanxx);
      BlockState _snowmanxxxx = this.world.getBlockState(_snowmanxxx);
      if (_snowmanxxxx.getRenderType() != BlockRenderType.INVISIBLE) {
         Vec3d _snowmanxxxxx = this.getVelocity();
         this.world
            .addParticle(
               new BlockStateParticleEffect(ParticleTypes.BLOCK, _snowmanxxxx),
               this.getX() + (this.random.nextDouble() - 0.5) * (double)this.dimensions.width,
               this.getY() + 0.1,
               this.getZ() + (this.random.nextDouble() - 0.5) * (double)this.dimensions.width,
               _snowmanxxxxx.x * -4.0,
               1.5,
               _snowmanxxxxx.z * -4.0
            );
      }
   }

   public boolean isSubmergedIn(Tag<Fluid> _snowman) {
      return this.field_25599 == _snowman;
   }

   public boolean isInLava() {
      return !this.firstUpdate && this.fluidHeight.getDouble(FluidTags.LAVA) > 0.0;
   }

   public void updateVelocity(float speed, Vec3d movementInput) {
      Vec3d _snowman = movementInputToVelocity(movementInput, speed, this.yaw);
      this.setVelocity(this.getVelocity().add(_snowman));
   }

   private static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
      double _snowman = movementInput.lengthSquared();
      if (_snowman < 1.0E-7) {
         return Vec3d.ZERO;
      } else {
         Vec3d _snowmanx = (_snowman > 1.0 ? movementInput.normalize() : movementInput).multiply((double)speed);
         float _snowmanxx = MathHelper.sin(yaw * (float) (Math.PI / 180.0));
         float _snowmanxxx = MathHelper.cos(yaw * (float) (Math.PI / 180.0));
         return new Vec3d(_snowmanx.x * (double)_snowmanxxx - _snowmanx.z * (double)_snowmanxx, _snowmanx.y, _snowmanx.z * (double)_snowmanxxx + _snowmanx.x * (double)_snowmanxx);
      }
   }

   public float getBrightnessAtEyes() {
      BlockPos.Mutable _snowman = new BlockPos.Mutable(this.getX(), 0.0, this.getZ());
      if (this.world.isChunkLoaded(_snowman)) {
         _snowman.setY(MathHelper.floor(this.getEyeY()));
         return this.world.getBrightness(_snowman);
      } else {
         return 0.0F;
      }
   }

   public void setWorld(World world) {
      this.world = world;
   }

   public void updatePositionAndAngles(double x, double y, double z, float yaw, float pitch) {
      this.method_30634(x, y, z);
      this.yaw = yaw % 360.0F;
      this.pitch = MathHelper.clamp(pitch, -90.0F, 90.0F) % 360.0F;
      this.prevYaw = this.yaw;
      this.prevPitch = this.pitch;
   }

   public void method_30634(double x, double y, double z) {
      double _snowman = MathHelper.clamp(x, -3.0E7, 3.0E7);
      double _snowmanx = MathHelper.clamp(z, -3.0E7, 3.0E7);
      this.prevX = _snowman;
      this.prevY = y;
      this.prevZ = _snowmanx;
      this.updatePosition(_snowman, y, _snowmanx);
   }

   public void refreshPositionAfterTeleport(Vec3d _snowman) {
      this.refreshPositionAfterTeleport(_snowman.x, _snowman.y, _snowman.z);
   }

   public void refreshPositionAfterTeleport(double x, double y, double z) {
      this.refreshPositionAndAngles(x, y, z, this.yaw, this.pitch);
   }

   public void refreshPositionAndAngles(BlockPos pos, float yaw, float pitch) {
      this.refreshPositionAndAngles((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, yaw, pitch);
   }

   public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
      this.resetPosition(x, y, z);
      this.yaw = yaw;
      this.pitch = pitch;
      this.refreshPosition();
   }

   public void resetPosition(double x, double y, double z) {
      this.setPos(x, y, z);
      this.prevX = x;
      this.prevY = y;
      this.prevZ = z;
      this.lastRenderX = x;
      this.lastRenderY = y;
      this.lastRenderZ = z;
   }

   public float distanceTo(Entity entity) {
      float _snowman = (float)(this.getX() - entity.getX());
      float _snowmanx = (float)(this.getY() - entity.getY());
      float _snowmanxx = (float)(this.getZ() - entity.getZ());
      return MathHelper.sqrt(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx);
   }

   public double squaredDistanceTo(double x, double y, double z) {
      double _snowman = this.getX() - x;
      double _snowmanx = this.getY() - y;
      double _snowmanxx = this.getZ() - z;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public double squaredDistanceTo(Entity entity) {
      return this.squaredDistanceTo(entity.getPos());
   }

   public double squaredDistanceTo(Vec3d vector) {
      double _snowman = this.getX() - vector.x;
      double _snowmanx = this.getY() - vector.y;
      double _snowmanxx = this.getZ() - vector.z;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public void onPlayerCollision(PlayerEntity player) {
   }

   public void pushAwayFrom(Entity entity) {
      if (!this.isConnectedThroughVehicle(entity)) {
         if (!entity.noClip && !this.noClip) {
            double _snowman = entity.getX() - this.getX();
            double _snowmanx = entity.getZ() - this.getZ();
            double _snowmanxx = MathHelper.absMax(_snowman, _snowmanx);
            if (_snowmanxx >= 0.01F) {
               _snowmanxx = (double)MathHelper.sqrt(_snowmanxx);
               _snowman /= _snowmanxx;
               _snowmanx /= _snowmanxx;
               double _snowmanxxx = 1.0 / _snowmanxx;
               if (_snowmanxxx > 1.0) {
                  _snowmanxxx = 1.0;
               }

               _snowman *= _snowmanxxx;
               _snowmanx *= _snowmanxxx;
               _snowman *= 0.05F;
               _snowmanx *= 0.05F;
               _snowman *= (double)(1.0F - this.pushSpeedReduction);
               _snowmanx *= (double)(1.0F - this.pushSpeedReduction);
               if (!this.hasPassengers()) {
                  this.addVelocity(-_snowman, 0.0, -_snowmanx);
               }

               if (!entity.hasPassengers()) {
                  entity.addVelocity(_snowman, 0.0, _snowmanx);
               }
            }
         }
      }
   }

   public void addVelocity(double deltaX, double deltaY, double deltaZ) {
      this.setVelocity(this.getVelocity().add(deltaX, deltaY, deltaZ));
      this.velocityDirty = true;
   }

   protected void scheduleVelocityUpdate() {
      this.velocityModified = true;
   }

   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.scheduleVelocityUpdate();
         return false;
      }
   }

   public final Vec3d getRotationVec(float tickDelta) {
      return this.getRotationVector(this.getPitch(tickDelta), this.getYaw(tickDelta));
   }

   public float getPitch(float tickDelta) {
      return tickDelta == 1.0F ? this.pitch : MathHelper.lerp(tickDelta, this.prevPitch, this.pitch);
   }

   public float getYaw(float tickDelta) {
      return tickDelta == 1.0F ? this.yaw : MathHelper.lerp(tickDelta, this.prevYaw, this.yaw);
   }

   protected final Vec3d getRotationVector(float pitch, float yaw) {
      float _snowman = pitch * (float) (Math.PI / 180.0);
      float _snowmanx = -yaw * (float) (Math.PI / 180.0);
      float _snowmanxx = MathHelper.cos(_snowmanx);
      float _snowmanxxx = MathHelper.sin(_snowmanx);
      float _snowmanxxxx = MathHelper.cos(_snowman);
      float _snowmanxxxxx = MathHelper.sin(_snowman);
      return new Vec3d((double)(_snowmanxxx * _snowmanxxxx), (double)(-_snowmanxxxxx), (double)(_snowmanxx * _snowmanxxxx));
   }

   public final Vec3d getOppositeRotationVector(float tickDelta) {
      return this.getOppositeRotationVector(this.getPitch(tickDelta), this.getYaw(tickDelta));
   }

   protected final Vec3d getOppositeRotationVector(float pitch, float yaw) {
      return this.getRotationVector(pitch - 90.0F, yaw);
   }

   public final Vec3d getCameraPosVec(float tickDelta) {
      if (tickDelta == 1.0F) {
         return new Vec3d(this.getX(), this.getEyeY(), this.getZ());
      } else {
         double _snowman = MathHelper.lerp((double)tickDelta, this.prevX, this.getX());
         double _snowmanx = MathHelper.lerp((double)tickDelta, this.prevY, this.getY()) + (double)this.getStandingEyeHeight();
         double _snowmanxx = MathHelper.lerp((double)tickDelta, this.prevZ, this.getZ());
         return new Vec3d(_snowman, _snowmanx, _snowmanxx);
      }
   }

   public Vec3d method_31166(float tickDelta) {
      return this.getCameraPosVec(tickDelta);
   }

   public final Vec3d method_30950(float _snowman) {
      double _snowmanx = MathHelper.lerp((double)_snowman, this.prevX, this.getX());
      double _snowmanxx = MathHelper.lerp((double)_snowman, this.prevY, this.getY());
      double _snowmanxxx = MathHelper.lerp((double)_snowman, this.prevZ, this.getZ());
      return new Vec3d(_snowmanx, _snowmanxx, _snowmanxxx);
   }

   public HitResult raycast(double maxDistance, float tickDelta, boolean includeFluids) {
      Vec3d _snowman = this.getCameraPosVec(tickDelta);
      Vec3d _snowmanx = this.getRotationVec(tickDelta);
      Vec3d _snowmanxx = _snowman.add(_snowmanx.x * maxDistance, _snowmanx.y * maxDistance, _snowmanx.z * maxDistance);
      return this.world
         .raycast(
            new RaycastContext(
               _snowman, _snowmanxx, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, this
            )
         );
   }

   public boolean collides() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public void updateKilledAdvancementCriterion(Entity killer, int score, DamageSource damageSource) {
      if (killer instanceof ServerPlayerEntity) {
         Criteria.ENTITY_KILLED_PLAYER.trigger((ServerPlayerEntity)killer, this, damageSource);
      }
   }

   public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
      double _snowman = this.getX() - cameraX;
      double _snowmanx = this.getY() - cameraY;
      double _snowmanxx = this.getZ() - cameraZ;
      double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
      return this.shouldRender(_snowmanxxx);
   }

   public boolean shouldRender(double distance) {
      double _snowman = this.getBoundingBox().getAverageSideLength();
      if (Double.isNaN(_snowman)) {
         _snowman = 1.0;
      }

      _snowman *= 64.0 * renderDistanceMultiplier;
      return distance < _snowman * _snowman;
   }

   public boolean saveSelfToTag(CompoundTag tag) {
      String _snowman = this.getSavedEntityId();
      if (!this.removed && _snowman != null) {
         tag.putString("id", _snowman);
         this.toTag(tag);
         return true;
      } else {
         return false;
      }
   }

   public boolean saveToTag(CompoundTag tag) {
      return this.hasVehicle() ? false : this.saveSelfToTag(tag);
   }

   public CompoundTag toTag(CompoundTag tag) {
      try {
         if (this.vehicle != null) {
            tag.put("Pos", this.toListTag(this.vehicle.getX(), this.getY(), this.vehicle.getZ()));
         } else {
            tag.put("Pos", this.toListTag(this.getX(), this.getY(), this.getZ()));
         }

         Vec3d _snowman = this.getVelocity();
         tag.put("Motion", this.toListTag(_snowman.x, _snowman.y, _snowman.z));
         tag.put("Rotation", this.toListTag(this.yaw, this.pitch));
         tag.putFloat("FallDistance", this.fallDistance);
         tag.putShort("Fire", (short)this.fireTicks);
         tag.putShort("Air", (short)this.getAir());
         tag.putBoolean("OnGround", this.onGround);
         tag.putBoolean("Invulnerable", this.invulnerable);
         tag.putInt("PortalCooldown", this.netherPortalCooldown);
         tag.putUuid("UUID", this.getUuid());
         Text _snowmanx = this.getCustomName();
         if (_snowmanx != null) {
            tag.putString("CustomName", Text.Serializer.toJson(_snowmanx));
         }

         if (this.isCustomNameVisible()) {
            tag.putBoolean("CustomNameVisible", this.isCustomNameVisible());
         }

         if (this.isSilent()) {
            tag.putBoolean("Silent", this.isSilent());
         }

         if (this.hasNoGravity()) {
            tag.putBoolean("NoGravity", this.hasNoGravity());
         }

         if (this.glowing) {
            tag.putBoolean("Glowing", this.glowing);
         }

         if (!this.scoreboardTags.isEmpty()) {
            ListTag _snowmanxx = new ListTag();

            for (String _snowmanxxx : this.scoreboardTags) {
               _snowmanxx.add(StringTag.of(_snowmanxxx));
            }

            tag.put("Tags", _snowmanxx);
         }

         this.writeCustomDataToTag(tag);
         if (this.hasPassengers()) {
            ListTag _snowmanxx = new ListTag();

            for (Entity _snowmanxxx : this.getPassengerList()) {
               CompoundTag _snowmanxxxx = new CompoundTag();
               if (_snowmanxxx.saveSelfToTag(_snowmanxxxx)) {
                  _snowmanxx.add(_snowmanxxxx);
               }
            }

            if (!_snowmanxx.isEmpty()) {
               tag.put("Passengers", _snowmanxx);
            }
         }

         return tag;
      } catch (Throwable var8) {
         CrashReport _snowmanxx = CrashReport.create(var8, "Saving entity NBT");
         CrashReportSection _snowmanxxxx = _snowmanxx.addElement("Entity being saved");
         this.populateCrashReport(_snowmanxxxx);
         throw new CrashException(_snowmanxx);
      }
   }

   public void fromTag(CompoundTag tag) {
      try {
         ListTag _snowman = tag.getList("Pos", 6);
         ListTag _snowmanx = tag.getList("Motion", 6);
         ListTag _snowmanxx = tag.getList("Rotation", 5);
         double _snowmanxxx = _snowmanx.getDouble(0);
         double _snowmanxxxx = _snowmanx.getDouble(1);
         double _snowmanxxxxx = _snowmanx.getDouble(2);
         this.setVelocity(Math.abs(_snowmanxxx) > 10.0 ? 0.0 : _snowmanxxx, Math.abs(_snowmanxxxx) > 10.0 ? 0.0 : _snowmanxxxx, Math.abs(_snowmanxxxxx) > 10.0 ? 0.0 : _snowmanxxxxx);
         this.resetPosition(_snowman.getDouble(0), _snowman.getDouble(1), _snowman.getDouble(2));
         this.yaw = _snowmanxx.getFloat(0);
         this.pitch = _snowmanxx.getFloat(1);
         this.prevYaw = this.yaw;
         this.prevPitch = this.pitch;
         this.setHeadYaw(this.yaw);
         this.setYaw(this.yaw);
         this.fallDistance = tag.getFloat("FallDistance");
         this.fireTicks = tag.getShort("Fire");
         this.setAir(tag.getShort("Air"));
         this.onGround = tag.getBoolean("OnGround");
         this.invulnerable = tag.getBoolean("Invulnerable");
         this.netherPortalCooldown = tag.getInt("PortalCooldown");
         if (tag.containsUuid("UUID")) {
            this.uuid = tag.getUuid("UUID");
            this.uuidString = this.uuid.toString();
         }

         if (!Double.isFinite(this.getX()) || !Double.isFinite(this.getY()) || !Double.isFinite(this.getZ())) {
            throw new IllegalStateException("Entity has invalid position");
         } else if (Double.isFinite((double)this.yaw) && Double.isFinite((double)this.pitch)) {
            this.refreshPosition();
            this.setRotation(this.yaw, this.pitch);
            if (tag.contains("CustomName", 8)) {
               String _snowmanxxxxxx = tag.getString("CustomName");

               try {
                  this.setCustomName(Text.Serializer.fromJson(_snowmanxxxxxx));
               } catch (Exception var14) {
                  LOGGER.warn("Failed to parse entity custom name {}", _snowmanxxxxxx, var14);
               }
            }

            this.setCustomNameVisible(tag.getBoolean("CustomNameVisible"));
            this.setSilent(tag.getBoolean("Silent"));
            this.setNoGravity(tag.getBoolean("NoGravity"));
            this.setGlowing(tag.getBoolean("Glowing"));
            if (tag.contains("Tags", 9)) {
               this.scoreboardTags.clear();
               ListTag _snowmanxxxxxx = tag.getList("Tags", 8);
               int _snowmanxxxxxxx = Math.min(_snowmanxxxxxx.size(), 1024);

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxx++) {
                  this.scoreboardTags.add(_snowmanxxxxxx.getString(_snowmanxxxxxxxx));
               }
            }

            this.readCustomDataFromTag(tag);
            if (this.shouldSetPositionOnLoad()) {
               this.refreshPosition();
            }
         } else {
            throw new IllegalStateException("Entity has invalid rotation");
         }
      } catch (Throwable var15) {
         CrashReport _snowmanxxxxxx = CrashReport.create(var15, "Loading entity NBT");
         CrashReportSection _snowmanxxxxxxx = _snowmanxxxxxx.addElement("Entity being loaded");
         this.populateCrashReport(_snowmanxxxxxxx);
         throw new CrashException(_snowmanxxxxxx);
      }
   }

   protected boolean shouldSetPositionOnLoad() {
      return true;
   }

   @Nullable
   protected final String getSavedEntityId() {
      EntityType<?> _snowman = this.getType();
      Identifier _snowmanx = EntityType.getId(_snowman);
      return _snowman.isSaveable() && _snowmanx != null ? _snowmanx.toString() : null;
   }

   protected abstract void readCustomDataFromTag(CompoundTag tag);

   protected abstract void writeCustomDataToTag(CompoundTag tag);

   protected ListTag toListTag(double... values) {
      ListTag _snowman = new ListTag();

      for (double _snowmanx : values) {
         _snowman.add(DoubleTag.of(_snowmanx));
      }

      return _snowman;
   }

   protected ListTag toListTag(float... values) {
      ListTag _snowman = new ListTag();

      for (float _snowmanx : values) {
         _snowman.add(FloatTag.of(_snowmanx));
      }

      return _snowman;
   }

   @Nullable
   public ItemEntity dropItem(ItemConvertible item) {
      return this.dropItem(item, 0);
   }

   @Nullable
   public ItemEntity dropItem(ItemConvertible item, int yOffset) {
      return this.dropStack(new ItemStack(item), (float)yOffset);
   }

   @Nullable
   public ItemEntity dropStack(ItemStack stack) {
      return this.dropStack(stack, 0.0F);
   }

   @Nullable
   public ItemEntity dropStack(ItemStack stack, float yOffset) {
      if (stack.isEmpty()) {
         return null;
      } else if (this.world.isClient) {
         return null;
      } else {
         ItemEntity _snowman = new ItemEntity(this.world, this.getX(), this.getY() + (double)yOffset, this.getZ(), stack);
         _snowman.setToDefaultPickupDelay();
         this.world.spawnEntity(_snowman);
         return _snowman;
      }
   }

   public boolean isAlive() {
      return !this.removed;
   }

   public boolean isInsideWall() {
      if (this.noClip) {
         return false;
      } else {
         float _snowman = 0.1F;
         float _snowmanx = this.dimensions.width * 0.8F;
         Box _snowmanxx = Box.method_30048((double)_snowmanx, 0.1F, (double)_snowmanx).offset(this.getX(), this.getEyeY(), this.getZ());
         return this.world.getBlockCollisions(this, _snowmanxx, (_snowmanxxx, _snowmanxxxx) -> _snowmanxxx.shouldSuffocate(this.world, _snowmanxxxx)).findAny().isPresent();
      }
   }

   public ActionResult interact(PlayerEntity player, Hand hand) {
      return ActionResult.PASS;
   }

   public boolean collidesWith(Entity other) {
      return other.isCollidable() && !this.isConnectedThroughVehicle(other);
   }

   public boolean isCollidable() {
      return false;
   }

   public void tickRiding() {
      this.setVelocity(Vec3d.ZERO);
      this.tick();
      if (this.hasVehicle()) {
         this.getVehicle().updatePassengerPosition(this);
      }
   }

   public void updatePassengerPosition(Entity passenger) {
      this.updatePassengerPosition(passenger, Entity::updatePosition);
   }

   private void updatePassengerPosition(Entity passenger, Entity.PositionUpdater _snowman) {
      if (this.hasPassenger(passenger)) {
         double _snowmanx = this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset();
         _snowman.accept(passenger, this.getX(), _snowmanx, this.getZ());
      }
   }

   public void onPassengerLookAround(Entity passenger) {
   }

   public double getHeightOffset() {
      return 0.0;
   }

   public double getMountedHeightOffset() {
      return (double)this.dimensions.height * 0.75;
   }

   public boolean startRiding(Entity entity) {
      return this.startRiding(entity, false);
   }

   public boolean isLiving() {
      return this instanceof LivingEntity;
   }

   public boolean startRiding(Entity entity, boolean force) {
      for (Entity _snowman = entity; _snowman.vehicle != null; _snowman = _snowman.vehicle) {
         if (_snowman.vehicle == this) {
            return false;
         }
      }

      if (force || this.canStartRiding(entity) && entity.canAddPassenger(this)) {
         if (this.hasVehicle()) {
            this.stopRiding();
         }

         this.setPose(EntityPose.STANDING);
         this.vehicle = entity;
         this.vehicle.addPassenger(this);
         return true;
      } else {
         return false;
      }
   }

   protected boolean canStartRiding(Entity entity) {
      return !this.isSneaking() && this.ridingCooldown <= 0;
   }

   protected boolean wouldPoseNotCollide(EntityPose pose) {
      return this.world.isSpaceEmpty(this, this.calculateBoundsForPose(pose).contract(1.0E-7));
   }

   public void removeAllPassengers() {
      for (int _snowman = this.passengerList.size() - 1; _snowman >= 0; _snowman--) {
         this.passengerList.get(_snowman).stopRiding();
      }
   }

   public void method_29239() {
      if (this.vehicle != null) {
         Entity _snowman = this.vehicle;
         this.vehicle = null;
         _snowman.removePassenger(this);
      }
   }

   public void stopRiding() {
      this.method_29239();
   }

   protected void addPassenger(Entity passenger) {
      if (passenger.getVehicle() != this) {
         throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
      } else {
         if (!this.world.isClient && passenger instanceof PlayerEntity && !(this.getPrimaryPassenger() instanceof PlayerEntity)) {
            this.passengerList.add(0, passenger);
         } else {
            this.passengerList.add(passenger);
         }
      }
   }

   protected void removePassenger(Entity passenger) {
      if (passenger.getVehicle() == this) {
         throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
      } else {
         this.passengerList.remove(passenger);
         passenger.ridingCooldown = 60;
      }
   }

   protected boolean canAddPassenger(Entity passenger) {
      return this.getPassengerList().size() < 1;
   }

   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
      this.updatePosition(x, y, z);
      this.setRotation(yaw, pitch);
   }

   public void updateTrackedHeadRotation(float yaw, int interpolationSteps) {
      this.setHeadYaw(yaw);
   }

   public float getTargetingMargin() {
      return 0.0F;
   }

   public Vec3d getRotationVector() {
      return this.getRotationVector(this.pitch, this.yaw);
   }

   public Vec2f getRotationClient() {
      return new Vec2f(this.pitch, this.yaw);
   }

   public Vec3d getRotationVecClient() {
      return Vec3d.fromPolar(this.getRotationClient());
   }

   public void setInNetherPortal(BlockPos pos) {
      if (this.hasNetherPortalCooldown()) {
         this.resetNetherPortalCooldown();
      } else {
         if (!this.world.isClient && !pos.equals(this.lastNetherPortalPosition)) {
            this.lastNetherPortalPosition = pos.toImmutable();
         }

         this.inNetherPortal = true;
      }
   }

   protected void tickNetherPortal() {
      if (this.world instanceof ServerWorld) {
         int _snowman = this.getMaxNetherPortalTime();
         ServerWorld _snowmanx = (ServerWorld)this.world;
         if (this.inNetherPortal) {
            MinecraftServer _snowmanxx = _snowmanx.getServer();
            RegistryKey<World> _snowmanxxx = this.world.getRegistryKey() == World.NETHER ? World.OVERWORLD : World.NETHER;
            ServerWorld _snowmanxxxx = _snowmanxx.getWorld(_snowmanxxx);
            if (_snowmanxxxx != null && _snowmanxx.isNetherAllowed() && !this.hasVehicle() && this.netherPortalTime++ >= _snowman) {
               this.world.getProfiler().push("portal");
               this.netherPortalTime = _snowman;
               this.resetNetherPortalCooldown();
               this.moveToWorld(_snowmanxxxx);
               this.world.getProfiler().pop();
            }

            this.inNetherPortal = false;
         } else {
            if (this.netherPortalTime > 0) {
               this.netherPortalTime -= 4;
            }

            if (this.netherPortalTime < 0) {
               this.netherPortalTime = 0;
            }
         }

         this.tickNetherPortalCooldown();
      }
   }

   public int getDefaultNetherPortalCooldown() {
      return 300;
   }

   public void setVelocityClient(double x, double y, double z) {
      this.setVelocity(x, y, z);
   }

   public void handleStatus(byte status) {
      switch (status) {
         case 53:
            HoneyBlock.addRegularParticles(this);
      }
   }

   public void animateDamage() {
   }

   public Iterable<ItemStack> getItemsHand() {
      return EMPTY_STACK_LIST;
   }

   public Iterable<ItemStack> getArmorItems() {
      return EMPTY_STACK_LIST;
   }

   public Iterable<ItemStack> getItemsEquipped() {
      return Iterables.concat(this.getItemsHand(), this.getArmorItems());
   }

   public void equipStack(EquipmentSlot slot, ItemStack stack) {
   }

   public boolean isOnFire() {
      boolean _snowman = this.world != null && this.world.isClient;
      return !this.isFireImmune() && (this.fireTicks > 0 || _snowman && this.getFlag(0));
   }

   public boolean hasVehicle() {
      return this.getVehicle() != null;
   }

   public boolean hasPassengers() {
      return !this.getPassengerList().isEmpty();
   }

   public boolean canBeRiddenInWater() {
      return true;
   }

   public void setSneaking(boolean sneaking) {
      this.setFlag(1, sneaking);
   }

   public boolean isSneaking() {
      return this.getFlag(1);
   }

   public boolean bypassesSteppingEffects() {
      return this.isSneaking();
   }

   public boolean bypassesLandingEffects() {
      return this.isSneaking();
   }

   public boolean isSneaky() {
      return this.isSneaking();
   }

   public boolean isDescending() {
      return this.isSneaking();
   }

   public boolean isInSneakingPose() {
      return this.getPose() == EntityPose.CROUCHING;
   }

   public boolean isSprinting() {
      return this.getFlag(3);
   }

   public void setSprinting(boolean sprinting) {
      this.setFlag(3, sprinting);
   }

   public boolean isSwimming() {
      return this.getFlag(4);
   }

   public boolean isInSwimmingPose() {
      return this.getPose() == EntityPose.SWIMMING;
   }

   public boolean shouldLeaveSwimmingPose() {
      return this.isInSwimmingPose() && !this.isTouchingWater();
   }

   public void setSwimming(boolean swimming) {
      this.setFlag(4, swimming);
   }

   public boolean isGlowing() {
      return this.glowing || this.world.isClient && this.getFlag(6);
   }

   public void setGlowing(boolean glowing) {
      this.glowing = glowing;
      if (!this.world.isClient) {
         this.setFlag(6, this.glowing);
      }
   }

   public boolean isInvisible() {
      return this.getFlag(5);
   }

   public boolean isInvisibleTo(PlayerEntity player) {
      if (player.isSpectator()) {
         return false;
      } else {
         AbstractTeam _snowman = this.getScoreboardTeam();
         return _snowman != null && player != null && player.getScoreboardTeam() == _snowman && _snowman.shouldShowFriendlyInvisibles() ? false : this.isInvisible();
      }
   }

   @Nullable
   public AbstractTeam getScoreboardTeam() {
      return this.world.getScoreboard().getPlayerTeam(this.getEntityName());
   }

   public boolean isTeammate(Entity other) {
      return this.isTeamPlayer(other.getScoreboardTeam());
   }

   public boolean isTeamPlayer(AbstractTeam team) {
      return this.getScoreboardTeam() != null ? this.getScoreboardTeam().isEqual(team) : false;
   }

   public void setInvisible(boolean invisible) {
      this.setFlag(5, invisible);
   }

   protected boolean getFlag(int index) {
      return (this.dataTracker.get(FLAGS) & 1 << index) != 0;
   }

   protected void setFlag(int index, boolean value) {
      byte _snowman = this.dataTracker.get(FLAGS);
      if (value) {
         this.dataTracker.set(FLAGS, (byte)(_snowman | 1 << index));
      } else {
         this.dataTracker.set(FLAGS, (byte)(_snowman & ~(1 << index)));
      }
   }

   public int getMaxAir() {
      return 300;
   }

   public int getAir() {
      return this.dataTracker.get(AIR);
   }

   public void setAir(int air) {
      this.dataTracker.set(AIR, air);
   }

   public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
      this.setFireTicks(this.fireTicks + 1);
      if (this.fireTicks == 0) {
         this.setOnFireFor(8);
      }

      this.damage(DamageSource.LIGHTNING_BOLT, 5.0F);
   }

   public void onBubbleColumnSurfaceCollision(boolean drag) {
      Vec3d _snowman = this.getVelocity();
      double _snowmanx;
      if (drag) {
         _snowmanx = Math.max(-0.9, _snowman.y - 0.03);
      } else {
         _snowmanx = Math.min(1.8, _snowman.y + 0.1);
      }

      this.setVelocity(_snowman.x, _snowmanx, _snowman.z);
   }

   public void onBubbleColumnCollision(boolean drag) {
      Vec3d _snowman = this.getVelocity();
      double _snowmanx;
      if (drag) {
         _snowmanx = Math.max(-0.3, _snowman.y - 0.03);
      } else {
         _snowmanx = Math.min(0.7, _snowman.y + 0.06);
      }

      this.setVelocity(_snowman.x, _snowmanx, _snowman.z);
      this.fallDistance = 0.0F;
   }

   public void onKilledOther(ServerWorld _snowman, LivingEntity _snowman) {
   }

   protected void pushOutOfBlocks(double x, double y, double z) {
      BlockPos _snowman = new BlockPos(x, y, z);
      Vec3d _snowmanx = new Vec3d(x - (double)_snowman.getX(), y - (double)_snowman.getY(), z - (double)_snowman.getZ());
      BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();
      Direction _snowmanxxx = Direction.UP;
      double _snowmanxxxx = Double.MAX_VALUE;

      for (Direction _snowmanxxxxx : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP}) {
         _snowmanxx.set(_snowman, _snowmanxxxxx);
         if (!this.world.getBlockState(_snowmanxx).isFullCube(this.world, _snowmanxx)) {
            double _snowmanxxxxxx = _snowmanx.getComponentAlongAxis(_snowmanxxxxx.getAxis());
            double _snowmanxxxxxxx = _snowmanxxxxx.getDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - _snowmanxxxxxx : _snowmanxxxxxx;
            if (_snowmanxxxxxxx < _snowmanxxxx) {
               _snowmanxxxx = _snowmanxxxxxxx;
               _snowmanxxx = _snowmanxxxxx;
            }
         }
      }

      float _snowmanxxxxxx = this.random.nextFloat() * 0.2F + 0.1F;
      float _snowmanxxxxxxx = (float)_snowmanxxx.getDirection().offset();
      Vec3d _snowmanxxxxxxxx = this.getVelocity().multiply(0.75);
      if (_snowmanxxx.getAxis() == Direction.Axis.X) {
         this.setVelocity((double)(_snowmanxxxxxxx * _snowmanxxxxxx), _snowmanxxxxxxxx.y, _snowmanxxxxxxxx.z);
      } else if (_snowmanxxx.getAxis() == Direction.Axis.Y) {
         this.setVelocity(_snowmanxxxxxxxx.x, (double)(_snowmanxxxxxxx * _snowmanxxxxxx), _snowmanxxxxxxxx.z);
      } else if (_snowmanxxx.getAxis() == Direction.Axis.Z) {
         this.setVelocity(_snowmanxxxxxxxx.x, _snowmanxxxxxxxx.y, (double)(_snowmanxxxxxxx * _snowmanxxxxxx));
      }
   }

   public void slowMovement(BlockState state, Vec3d multiplier) {
      this.fallDistance = 0.0F;
      this.movementMultiplier = multiplier;
   }

   private static Text removeClickEvents(Text textComponent) {
      MutableText _snowman = textComponent.copy().setStyle(textComponent.getStyle().withClickEvent(null));

      for (Text _snowmanx : textComponent.getSiblings()) {
         _snowman.append(removeClickEvents(_snowmanx));
      }

      return _snowman;
   }

   @Override
   public Text getName() {
      Text _snowman = this.getCustomName();
      return _snowman != null ? removeClickEvents(_snowman) : this.getDefaultName();
   }

   protected Text getDefaultName() {
      return this.type.getName();
   }

   public boolean isPartOf(Entity entity) {
      return this == entity;
   }

   public float getHeadYaw() {
      return 0.0F;
   }

   public void setHeadYaw(float headYaw) {
   }

   public void setYaw(float yaw) {
   }

   public boolean isAttackable() {
      return true;
   }

   public boolean handleAttack(Entity attacker) {
      return false;
   }

   @Override
   public String toString() {
      return String.format(
         Locale.ROOT,
         "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
         this.getClass().getSimpleName(),
         this.getName().getString(),
         this.entityId,
         this.world == null ? "~NULL~" : this.world.toString(),
         this.getX(),
         this.getY(),
         this.getZ()
      );
   }

   public boolean isInvulnerableTo(DamageSource damageSource) {
      return this.invulnerable && damageSource != DamageSource.OUT_OF_WORLD && !damageSource.isSourceCreativePlayer();
   }

   public boolean isInvulnerable() {
      return this.invulnerable;
   }

   public void setInvulnerable(boolean invulnerable) {
      this.invulnerable = invulnerable;
   }

   public void copyPositionAndRotation(Entity entity) {
      this.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.yaw, entity.pitch);
   }

   public void copyFrom(Entity original) {
      CompoundTag _snowman = original.toTag(new CompoundTag());
      _snowman.remove("Dimension");
      this.fromTag(_snowman);
      this.netherPortalCooldown = original.netherPortalCooldown;
      this.lastNetherPortalPosition = original.lastNetherPortalPosition;
   }

   @Nullable
   public Entity moveToWorld(ServerWorld destination) {
      if (this.world instanceof ServerWorld && !this.removed) {
         this.world.getProfiler().push("changeDimension");
         this.detach();
         this.world.getProfiler().push("reposition");
         TeleportTarget _snowman = this.getTeleportTarget(destination);
         if (_snowman == null) {
            return null;
         } else {
            this.world.getProfiler().swap("reloading");
            Entity _snowmanx = this.getType().create(destination);
            if (_snowmanx != null) {
               _snowmanx.copyFrom(this);
               _snowmanx.refreshPositionAndAngles(_snowman.position.x, _snowman.position.y, _snowman.position.z, _snowman.yaw, _snowmanx.pitch);
               _snowmanx.setVelocity(_snowman.velocity);
               destination.onDimensionChanged(_snowmanx);
               if (destination.getRegistryKey() == World.END) {
                  ServerWorld.createEndSpawnPlatform(destination);
               }
            }

            this.method_30076();
            this.world.getProfiler().pop();
            ((ServerWorld)this.world).resetIdleTimeout();
            destination.resetIdleTimeout();
            this.world.getProfiler().pop();
            return _snowmanx;
         }
      } else {
         return null;
      }
   }

   protected void method_30076() {
      this.removed = true;
   }

   @Nullable
   protected TeleportTarget getTeleportTarget(ServerWorld destination) {
      boolean _snowman = this.world.getRegistryKey() == World.END && destination.getRegistryKey() == World.OVERWORLD;
      boolean _snowmanx = destination.getRegistryKey() == World.END;
      if (!_snowman && !_snowmanx) {
         boolean _snowmanxx = destination.getRegistryKey() == World.NETHER;
         if (this.world.getRegistryKey() != World.NETHER && !_snowmanxx) {
            return null;
         } else {
            WorldBorder _snowmanxxx = destination.getWorldBorder();
            double _snowmanxxxx = Math.max(-2.9999872E7, _snowmanxxx.getBoundWest() + 16.0);
            double _snowmanxxxxx = Math.max(-2.9999872E7, _snowmanxxx.getBoundNorth() + 16.0);
            double _snowmanxxxxxx = Math.min(2.9999872E7, _snowmanxxx.getBoundEast() - 16.0);
            double _snowmanxxxxxxx = Math.min(2.9999872E7, _snowmanxxx.getBoundSouth() - 16.0);
            double _snowmanxxxxxxxx = DimensionType.method_31109(this.world.getDimension(), destination.getDimension());
            BlockPos _snowmanxxxxxxxxx = new BlockPos(
               MathHelper.clamp(this.getX() * _snowmanxxxxxxxx, _snowmanxxxx, _snowmanxxxxxx), this.getY(), MathHelper.clamp(this.getZ() * _snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx)
            );
            return this.method_30330(destination, _snowmanxxxxxxxxx, _snowmanxx)
               .map(
                  _snowmanxxxxxxxxxx -> {
                     BlockState _snowmanxxxxxxxxxxx = this.world.getBlockState(this.lastNetherPortalPosition);
                     Direction.Axis _snowmanxx;
                     Vec3d _snowmanxxx;
                     if (_snowmanxxxxxxxxxxx.contains(Properties.HORIZONTAL_AXIS)) {
                        _snowmanxx = _snowmanxxxxxxxxxxx.get(Properties.HORIZONTAL_AXIS);
                        class_5459.class_5460 _snowmanxxxx = class_5459.method_30574(
                           this.lastNetherPortalPosition, _snowmanxx, 21, Direction.Axis.Y, 21, _snowmanxxxxx -> this.world.getBlockState(_snowmanxxxxx) == _snowman
                        );
                        _snowmanxxx = this.method_30633(_snowmanxx, _snowmanxxxx);
                     } else {
                        _snowmanxx = Direction.Axis.X;
                        _snowmanxxx = new Vec3d(0.5, 0.0, 0.0);
                     }

                     return AreaHelper.method_30484(
                        destination, _snowmanxxxxxxxxxx, _snowmanxx, _snowmanxxx, this.getDimensions(this.getPose()), this.getVelocity(), this.yaw, this.pitch
                     );
                  }
               )
               .orElse(null);
         }
      } else {
         BlockPos _snowmanxx;
         if (_snowmanx) {
            _snowmanxx = ServerWorld.END_SPAWN_POS;
         } else {
            _snowmanxx = destination.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, destination.getSpawnPos());
         }

         return new TeleportTarget(new Vec3d((double)_snowmanxx.getX() + 0.5, (double)_snowmanxx.getY(), (double)_snowmanxx.getZ() + 0.5), this.getVelocity(), this.yaw, this.pitch);
      }
   }

   protected Vec3d method_30633(Direction.Axis _snowman, class_5459.class_5460 _snowman) {
      return AreaHelper.method_30494(_snowman, _snowman, this.getPos(), this.getDimensions(this.getPose()));
   }

   protected Optional<class_5459.class_5460> method_30330(ServerWorld _snowman, BlockPos _snowman, boolean _snowman) {
      return _snowman.getPortalForcer().method_30483(_snowman, _snowman);
   }

   public boolean canUsePortals() {
      return true;
   }

   public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, float max) {
      return max;
   }

   public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
      return true;
   }

   public int getSafeFallDistance() {
      return 3;
   }

   public boolean canAvoidTraps() {
      return false;
   }

   public void populateCrashReport(CrashReportSection section) {
      section.add("Entity Type", () -> EntityType.getId(this.getType()) + " (" + this.getClass().getCanonicalName() + ")");
      section.add("Entity ID", this.entityId);
      section.add("Entity Name", () -> this.getName().getString());
      section.add("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.getX(), this.getY(), this.getZ()));
      section.add(
         "Entity's Block location",
         CrashReportSection.createPositionString(MathHelper.floor(this.getX()), MathHelper.floor(this.getY()), MathHelper.floor(this.getZ()))
      );
      Vec3d _snowman = this.getVelocity();
      section.add("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", _snowman.x, _snowman.y, _snowman.z));
      section.add("Entity's Passengers", () -> this.getPassengerList().toString());
      section.add("Entity's Vehicle", () -> this.getVehicle().toString());
   }

   public boolean doesRenderOnFire() {
      return this.isOnFire() && !this.isSpectator();
   }

   public void setUuid(UUID uuid) {
      this.uuid = uuid;
      this.uuidString = this.uuid.toString();
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public String getUuidAsString() {
      return this.uuidString;
   }

   public String getEntityName() {
      return this.uuidString;
   }

   public boolean canFly() {
      return true;
   }

   public static double getRenderDistanceMultiplier() {
      return renderDistanceMultiplier;
   }

   public static void setRenderDistanceMultiplier(double value) {
      renderDistanceMultiplier = value;
   }

   @Override
   public Text getDisplayName() {
      return Team.modifyText(this.getScoreboardTeam(), this.getName())
         .styled(_snowman -> _snowman.withHoverEvent(this.getHoverEvent()).withInsertion(this.getUuidAsString()));
   }

   public void setCustomName(@Nullable Text name) {
      this.dataTracker.set(CUSTOM_NAME, Optional.ofNullable(name));
   }

   @Nullable
   @Override
   public Text getCustomName() {
      return this.dataTracker.get(CUSTOM_NAME).orElse(null);
   }

   @Override
   public boolean hasCustomName() {
      return this.dataTracker.get(CUSTOM_NAME).isPresent();
   }

   public void setCustomNameVisible(boolean visible) {
      this.dataTracker.set(NAME_VISIBLE, visible);
   }

   public boolean isCustomNameVisible() {
      return this.dataTracker.get(NAME_VISIBLE);
   }

   public final void teleport(double destX, double destY, double destZ) {
      if (this.world instanceof ServerWorld) {
         ChunkPos _snowman = new ChunkPos(new BlockPos(destX, destY, destZ));
         ((ServerWorld)this.world).getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, _snowman, 0, this.getEntityId());
         this.world.getChunk(_snowman.x, _snowman.z);
         this.requestTeleport(destX, destY, destZ);
      }
   }

   public void requestTeleport(double destX, double destY, double destZ) {
      if (this.world instanceof ServerWorld) {
         ServerWorld _snowman = (ServerWorld)this.world;
         this.refreshPositionAndAngles(destX, destY, destZ, this.yaw, this.pitch);
         this.streamPassengersRecursively().forEach(_snowmanx -> {
            _snowman.checkEntityChunkPos(_snowmanx);
            _snowmanx.teleportRequested = true;

            for (Entity _snowmanx : _snowmanx.passengerList) {
               _snowmanx.updatePassengerPosition(_snowmanx, Entity::refreshPositionAfterTeleport);
            }
         });
      }
   }

   public boolean shouldRenderName() {
      return this.isCustomNameVisible();
   }

   public void onTrackedDataSet(TrackedData<?> data) {
      if (POSE.equals(data)) {
         this.calculateDimensions();
      }
   }

   public void calculateDimensions() {
      EntityDimensions _snowman = this.dimensions;
      EntityPose _snowmanx = this.getPose();
      EntityDimensions _snowmanxx = this.getDimensions(_snowmanx);
      this.dimensions = _snowmanxx;
      this.standingEyeHeight = this.getEyeHeight(_snowmanx, _snowmanxx);
      if (_snowmanxx.width < _snowman.width) {
         double _snowmanxxx = (double)_snowmanxx.width / 2.0;
         this.setBoundingBox(
            new Box(this.getX() - _snowmanxxx, this.getY(), this.getZ() - _snowmanxxx, this.getX() + _snowmanxxx, this.getY() + (double)_snowmanxx.height, this.getZ() + _snowmanxxx)
         );
      } else {
         Box _snowmanxxx = this.getBoundingBox();
         this.setBoundingBox(
            new Box(_snowmanxxx.minX, _snowmanxxx.minY, _snowmanxxx.minZ, _snowmanxxx.minX + (double)_snowmanxx.width, _snowmanxxx.minY + (double)_snowmanxx.height, _snowmanxxx.minZ + (double)_snowmanxx.width)
         );
         if (_snowmanxx.width > _snowman.width && !this.firstUpdate && !this.world.isClient) {
            float _snowmanxxxx = _snowman.width - _snowmanxx.width;
            this.move(MovementType.SELF, new Vec3d((double)_snowmanxxxx, 0.0, (double)_snowmanxxxx));
         }
      }
   }

   public Direction getHorizontalFacing() {
      return Direction.fromRotation((double)this.yaw);
   }

   public Direction getMovementDirection() {
      return this.getHorizontalFacing();
   }

   protected HoverEvent getHoverEvent() {
      return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new HoverEvent.EntityContent(this.getType(), this.getUuid(), this.getName()));
   }

   public boolean canBeSpectated(ServerPlayerEntity spectator) {
      return true;
   }

   public Box getBoundingBox() {
      return this.entityBounds;
   }

   public Box getVisibilityBoundingBox() {
      return this.getBoundingBox();
   }

   protected Box calculateBoundsForPose(EntityPose pos) {
      EntityDimensions _snowman = this.getDimensions(pos);
      float _snowmanx = _snowman.width / 2.0F;
      Vec3d _snowmanxx = new Vec3d(this.getX() - (double)_snowmanx, this.getY(), this.getZ() - (double)_snowmanx);
      Vec3d _snowmanxxx = new Vec3d(this.getX() + (double)_snowmanx, this.getY() + (double)_snowman.height, this.getZ() + (double)_snowmanx);
      return new Box(_snowmanxx, _snowmanxxx);
   }

   public void setBoundingBox(Box boundingBox) {
      this.entityBounds = boundingBox;
   }

   protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height * 0.85F;
   }

   public float getEyeHeight(EntityPose pose) {
      return this.getEyeHeight(pose, this.getDimensions(pose));
   }

   public final float getStandingEyeHeight() {
      return this.standingEyeHeight;
   }

   public Vec3d method_29919() {
      return new Vec3d(0.0, (double)this.getStandingEyeHeight(), (double)(this.getWidth() * 0.4F));
   }

   public boolean equip(int slot, ItemStack item) {
      return false;
   }

   @Override
   public void sendSystemMessage(Text message, UUID senderUuid) {
   }

   public World getEntityWorld() {
      return this.world;
   }

   @Nullable
   public MinecraftServer getServer() {
      return this.world.getServer();
   }

   public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
      return ActionResult.PASS;
   }

   public boolean isImmuneToExplosion() {
      return false;
   }

   public void dealDamage(LivingEntity attacker, Entity target) {
      if (target instanceof LivingEntity) {
         EnchantmentHelper.onUserDamaged((LivingEntity)target, attacker);
      }

      EnchantmentHelper.onTargetDamaged(attacker, target);
   }

   public void onStartedTrackingBy(ServerPlayerEntity player) {
   }

   public void onStoppedTrackingBy(ServerPlayerEntity player) {
   }

   public float applyRotation(BlockRotation rotation) {
      float _snowman = MathHelper.wrapDegrees(this.yaw);
      switch (rotation) {
         case CLOCKWISE_180:
            return _snowman + 180.0F;
         case COUNTERCLOCKWISE_90:
            return _snowman + 270.0F;
         case CLOCKWISE_90:
            return _snowman + 90.0F;
         default:
            return _snowman;
      }
   }

   public float applyMirror(BlockMirror mirror) {
      float _snowman = MathHelper.wrapDegrees(this.yaw);
      switch (mirror) {
         case LEFT_RIGHT:
            return -_snowman;
         case FRONT_BACK:
            return 180.0F - _snowman;
         default:
            return _snowman;
      }
   }

   public boolean entityDataRequiresOperator() {
      return false;
   }

   public boolean teleportRequested() {
      boolean _snowman = this.teleportRequested;
      this.teleportRequested = false;
      return _snowman;
   }

   public boolean isChunkPosUpdateRequested() {
      boolean _snowman = this.chunkPosUpdateRequested;
      this.chunkPosUpdateRequested = false;
      return _snowman;
   }

   @Nullable
   public Entity getPrimaryPassenger() {
      return null;
   }

   public List<Entity> getPassengerList() {
      return (List<Entity>)(this.passengerList.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.passengerList));
   }

   public boolean hasPassenger(Entity passenger) {
      for (Entity _snowman : this.getPassengerList()) {
         if (_snowman.equals(passenger)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasPassengerType(Class<? extends Entity> clazz) {
      for (Entity _snowman : this.getPassengerList()) {
         if (clazz.isAssignableFrom(_snowman.getClass())) {
            return true;
         }
      }

      return false;
   }

   public Collection<Entity> getPassengersDeep() {
      Set<Entity> _snowman = Sets.newHashSet();

      for (Entity _snowmanx : this.getPassengerList()) {
         _snowman.add(_snowmanx);
         _snowmanx.collectPassengers(false, _snowman);
      }

      return _snowman;
   }

   public Stream<Entity> streamPassengersRecursively() {
      return Stream.concat(Stream.of(this), this.passengerList.stream().flatMap(Entity::streamPassengersRecursively));
   }

   public boolean hasPlayerRider() {
      Set<Entity> _snowman = Sets.newHashSet();
      this.collectPassengers(true, _snowman);
      return _snowman.size() == 1;
   }

   private void collectPassengers(boolean playersOnly, Set<Entity> output) {
      for (Entity _snowman : this.getPassengerList()) {
         if (!playersOnly || ServerPlayerEntity.class.isAssignableFrom(_snowman.getClass())) {
            output.add(_snowman);
         }

         _snowman.collectPassengers(playersOnly, output);
      }
   }

   public Entity getRootVehicle() {
      Entity _snowman = this;

      while (_snowman.hasVehicle()) {
         _snowman = _snowman.getVehicle();
      }

      return _snowman;
   }

   public boolean isConnectedThroughVehicle(Entity entity) {
      return this.getRootVehicle() == entity.getRootVehicle();
   }

   public boolean hasPassengerDeep(Entity passenger) {
      for (Entity _snowman : this.getPassengerList()) {
         if (_snowman.equals(passenger)) {
            return true;
         }

         if (_snowman.hasPassengerDeep(passenger)) {
            return true;
         }
      }

      return false;
   }

   public boolean isLogicalSideForUpdatingMovement() {
      Entity _snowman = this.getPrimaryPassenger();
      return _snowman instanceof PlayerEntity ? ((PlayerEntity)_snowman).isMainPlayer() : !this.world.isClient;
   }

   protected static Vec3d getPassengerDismountOffset(double vehicleWidth, double passengerWidth, float passengerYaw) {
      double _snowman = (vehicleWidth + passengerWidth + 1.0E-5F) / 2.0;
      float _snowmanx = -MathHelper.sin(passengerYaw * (float) (Math.PI / 180.0));
      float _snowmanxx = MathHelper.cos(passengerYaw * (float) (Math.PI / 180.0));
      float _snowmanxxx = Math.max(Math.abs(_snowmanx), Math.abs(_snowmanxx));
      return new Vec3d((double)_snowmanx * _snowman / (double)_snowmanxxx, 0.0, (double)_snowmanxx * _snowman / (double)_snowmanxxx);
   }

   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      return new Vec3d(this.getX(), this.getBoundingBox().maxY, this.getZ());
   }

   @Nullable
   public Entity getVehicle() {
      return this.vehicle;
   }

   public PistonBehavior getPistonBehavior() {
      return PistonBehavior.NORMAL;
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.NEUTRAL;
   }

   protected int getBurningDuration() {
      return 1;
   }

   public ServerCommandSource getCommandSource() {
      return new ServerCommandSource(
         this,
         this.getPos(),
         this.getRotationClient(),
         this.world instanceof ServerWorld ? (ServerWorld)this.world : null,
         this.getPermissionLevel(),
         this.getName().getString(),
         this.getDisplayName(),
         this.world.getServer(),
         this
      );
   }

   protected int getPermissionLevel() {
      return 0;
   }

   public boolean hasPermissionLevel(int permissionLevel) {
      return this.getPermissionLevel() >= permissionLevel;
   }

   @Override
   public boolean shouldReceiveFeedback() {
      return this.world.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK);
   }

   @Override
   public boolean shouldTrackOutput() {
      return true;
   }

   @Override
   public boolean shouldBroadcastConsoleToOps() {
      return true;
   }

   public void lookAt(EntityAnchorArgumentType.EntityAnchor anchorPoint, Vec3d target) {
      Vec3d _snowman = anchorPoint.positionAt(this);
      double _snowmanx = target.x - _snowman.x;
      double _snowmanxx = target.y - _snowman.y;
      double _snowmanxxx = target.z - _snowman.z;
      double _snowmanxxxx = (double)MathHelper.sqrt(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx);
      this.pitch = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(_snowmanxx, _snowmanxxxx) * 180.0F / (float)Math.PI)));
      this.yaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(_snowmanxxx, _snowmanx) * 180.0F / (float)Math.PI) - 90.0F);
      this.setHeadYaw(this.yaw);
      this.prevPitch = this.pitch;
      this.prevYaw = this.yaw;
   }

   public boolean updateMovementInFluid(Tag<Fluid> tag, double _snowman) {
      Box _snowmanx = this.getBoundingBox().contract(0.001);
      int _snowmanxx = MathHelper.floor(_snowmanx.minX);
      int _snowmanxxx = MathHelper.ceil(_snowmanx.maxX);
      int _snowmanxxxx = MathHelper.floor(_snowmanx.minY);
      int _snowmanxxxxx = MathHelper.ceil(_snowmanx.maxY);
      int _snowmanxxxxxx = MathHelper.floor(_snowmanx.minZ);
      int _snowmanxxxxxxx = MathHelper.ceil(_snowmanx.maxZ);
      if (!this.world.isRegionLoaded(_snowmanxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxxx)) {
         return false;
      } else {
         double _snowmanxxxxxxxx = 0.0;
         boolean _snowmanxxxxxxxxx = this.canFly();
         boolean _snowmanxxxxxxxxxx = false;
         Vec3d _snowmanxxxxxxxxxxx = Vec3d.ZERO;
         int _snowmanxxxxxxxxxxxx = 0;
         BlockPos.Mutable _snowmanxxxxxxxxxxxxx = new BlockPos.Mutable();

         for (int _snowmanxxxxxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
                  _snowmanxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
                  FluidState _snowmanxxxxxxxxxxxxxxxxx = this.world.getFluidState(_snowmanxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxxxx.isIn(tag)) {
                     double _snowmanxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx.getHeight(this.world, _snowmanxxxxxxxxxxxxx));
                     if (_snowmanxxxxxxxxxxxxxxxxxx >= _snowmanx.minY) {
                        _snowmanxxxxxxxxxx = true;
                        _snowmanxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxxxx - _snowmanx.minY, _snowmanxxxxxxxx);
                        if (_snowmanxxxxxxxxx) {
                           Vec3d _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getVelocity(this.world, _snowmanxxxxxxxxxxxxx);
                           if (_snowmanxxxxxxxx < 0.4) {
                              _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.multiply(_snowmanxxxxxxxx);
                           }

                           _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxx);
                           _snowmanxxxxxxxxxxxx++;
                        }
                     }
                  }
               }
            }
         }

         if (_snowmanxxxxxxxxxxx.length() > 0.0) {
            if (_snowmanxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx.multiply(1.0 / (double)_snowmanxxxxxxxxxxxx);
            }

            if (!(this instanceof PlayerEntity)) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx.normalize();
            }

            Vec3d _snowmanxxxxxxxxxxxxxx = this.getVelocity();
            _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx.multiply(_snowman * 1.0);
            double _snowmanxxxxxxxxxxxxxxx = 0.003;
            if (Math.abs(_snowmanxxxxxxxxxxxxxx.x) < 0.003 && Math.abs(_snowmanxxxxxxxxxxxxxx.z) < 0.003 && _snowmanxxxxxxxxxxx.length() < 0.0045000000000000005) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx.normalize().multiply(0.0045000000000000005);
            }

            this.setVelocity(this.getVelocity().add(_snowmanxxxxxxxxxxx));
         }

         this.fluidHeight.put(tag, _snowmanxxxxxxxx);
         return _snowmanxxxxxxxxxx;
      }
   }

   public double getFluidHeight(Tag<Fluid> fluid) {
      return this.fluidHeight.getDouble(fluid);
   }

   public double method_29241() {
      return (double)this.getStandingEyeHeight() < 0.4 ? 0.0 : 0.4;
   }

   public final float getWidth() {
      return this.dimensions.width;
   }

   public final float getHeight() {
      return this.dimensions.height;
   }

   public abstract Packet<?> createSpawnPacket();

   public EntityDimensions getDimensions(EntityPose pose) {
      return this.type.getDimensions();
   }

   public Vec3d getPos() {
      return this.pos;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public Vec3d getVelocity() {
      return this.velocity;
   }

   public void setVelocity(Vec3d velocity) {
      this.velocity = velocity;
   }

   public void setVelocity(double x, double y, double z) {
      this.setVelocity(new Vec3d(x, y, z));
   }

   public final double getX() {
      return this.pos.x;
   }

   public double offsetX(double widthScale) {
      return this.pos.x + (double)this.getWidth() * widthScale;
   }

   public double getParticleX(double widthScale) {
      return this.offsetX((2.0 * this.random.nextDouble() - 1.0) * widthScale);
   }

   public final double getY() {
      return this.pos.y;
   }

   public double getBodyY(double heightScale) {
      return this.pos.y + (double)this.getHeight() * heightScale;
   }

   public double getRandomBodyY() {
      return this.getBodyY(this.random.nextDouble());
   }

   public double getEyeY() {
      return this.pos.y + (double)this.standingEyeHeight;
   }

   public final double getZ() {
      return this.pos.z;
   }

   public double offsetZ(double widthScale) {
      return this.pos.z + (double)this.getWidth() * widthScale;
   }

   public double getParticleZ(double widthScale) {
      return this.offsetZ((2.0 * this.random.nextDouble() - 1.0) * widthScale);
   }

   public void setPos(double x, double y, double z) {
      if (this.pos.x != x || this.pos.y != y || this.pos.z != z) {
         this.pos = new Vec3d(x, y, z);
         int _snowman = MathHelper.floor(x);
         int _snowmanx = MathHelper.floor(y);
         int _snowmanxx = MathHelper.floor(z);
         if (_snowman != this.blockPos.getX() || _snowmanx != this.blockPos.getY() || _snowmanxx != this.blockPos.getZ()) {
            this.blockPos = new BlockPos(_snowman, _snowmanx, _snowmanxx);
         }

         this.chunkPosUpdateRequested = true;
      }
   }

   public void checkDespawn() {
   }

   public Vec3d method_30951(float _snowman) {
      return this.method_30950(_snowman).add(0.0, (double)this.standingEyeHeight * 0.7, 0.0);
   }

   @FunctionalInterface
   public interface PositionUpdater {
      void accept(Entity entity, double x, double y, double z);
   }
}
