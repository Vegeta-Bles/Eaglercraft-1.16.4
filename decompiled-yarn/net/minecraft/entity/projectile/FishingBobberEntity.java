package net.minecraft.entity.projectile;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FishingBobberEntity extends ProjectileEntity {
   private final Random velocityRandom = new Random();
   private boolean caughtFish;
   private int outOfOpenWaterTicks;
   private static final TrackedData<Integer> HOOK_ENTITY_ID = DataTracker.registerData(FishingBobberEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> CAUGHT_FISH = DataTracker.registerData(FishingBobberEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private int removalTimer;
   private int hookCountdown;
   private int waitCountdown;
   private int fishTravelCountdown;
   private float fishAngle;
   private boolean inOpenWater = true;
   private Entity hookedEntity;
   private FishingBobberEntity.State state = FishingBobberEntity.State.FLYING;
   private final int luckOfTheSeaLevel;
   private final int lureLevel;

   private FishingBobberEntity(World world, PlayerEntity owner, int lureLevel, int luckOfTheSeaLevel) {
      super(EntityType.FISHING_BOBBER, world);
      this.ignoreCameraFrustum = true;
      this.setOwner(owner);
      owner.fishHook = this;
      this.luckOfTheSeaLevel = Math.max(0, lureLevel);
      this.lureLevel = Math.max(0, luckOfTheSeaLevel);
   }

   public FishingBobberEntity(World world, PlayerEntity thrower, double x, double y, double z) {
      this(world, thrower, 0, 0);
      this.updatePosition(x, y, z);
      this.prevX = this.getX();
      this.prevY = this.getY();
      this.prevZ = this.getZ();
   }

   public FishingBobberEntity(PlayerEntity thrower, World world, int lureLevel, int luckOfTheSeaLevel) {
      this(world, thrower, lureLevel, luckOfTheSeaLevel);
      float _snowman = thrower.pitch;
      float _snowmanx = thrower.yaw;
      float _snowmanxx = MathHelper.cos(-_snowmanx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxxx = MathHelper.sin(-_snowmanx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxxxx = -MathHelper.cos(-_snowman * (float) (Math.PI / 180.0));
      float _snowmanxxxxx = MathHelper.sin(-_snowman * (float) (Math.PI / 180.0));
      double _snowmanxxxxxx = thrower.getX() - (double)_snowmanxxx * 0.3;
      double _snowmanxxxxxxx = thrower.getEyeY();
      double _snowmanxxxxxxxx = thrower.getZ() - (double)_snowmanxx * 0.3;
      this.refreshPositionAndAngles(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanx, _snowman);
      Vec3d _snowmanxxxxxxxxx = new Vec3d((double)(-_snowmanxxx), (double)MathHelper.clamp(-(_snowmanxxxxx / _snowmanxxxx), -5.0F, 5.0F), (double)(-_snowmanxx));
      double _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.length();
      _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.multiply(
         0.6 / _snowmanxxxxxxxxxx + 0.5 + this.random.nextGaussian() * 0.0045,
         0.6 / _snowmanxxxxxxxxxx + 0.5 + this.random.nextGaussian() * 0.0045,
         0.6 / _snowmanxxxxxxxxxx + 0.5 + this.random.nextGaussian() * 0.0045
      );
      this.setVelocity(_snowmanxxxxxxxxx);
      this.yaw = (float)(MathHelper.atan2(_snowmanxxxxxxxxx.x, _snowmanxxxxxxxxx.z) * 180.0F / (float)Math.PI);
      this.pitch = (float)(MathHelper.atan2(_snowmanxxxxxxxxx.y, (double)MathHelper.sqrt(squaredHorizontalLength(_snowmanxxxxxxxxx))) * 180.0F / (float)Math.PI);
      this.prevYaw = this.yaw;
      this.prevPitch = this.pitch;
   }

   @Override
   protected void initDataTracker() {
      this.getDataTracker().startTracking(HOOK_ENTITY_ID, 0);
      this.getDataTracker().startTracking(CAUGHT_FISH, false);
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (HOOK_ENTITY_ID.equals(data)) {
         int _snowman = this.getDataTracker().get(HOOK_ENTITY_ID);
         this.hookedEntity = _snowman > 0 ? this.world.getEntityById(_snowman - 1) : null;
      }

      if (CAUGHT_FISH.equals(data)) {
         this.caughtFish = this.getDataTracker().get(CAUGHT_FISH);
         if (this.caughtFish) {
            this.setVelocity(this.getVelocity().x, (double)(-0.4F * MathHelper.nextFloat(this.velocityRandom, 0.6F, 1.0F)), this.getVelocity().z);
         }
      }

      super.onTrackedDataSet(data);
   }

   @Override
   public boolean shouldRender(double distance) {
      double _snowman = 64.0;
      return distance < 4096.0;
   }

   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
   }

   @Override
   public void tick() {
      this.velocityRandom.setSeed(this.getUuid().getLeastSignificantBits() ^ this.world.getTime());
      super.tick();
      PlayerEntity _snowman = this.getPlayerOwner();
      if (_snowman == null) {
         this.remove();
      } else if (this.world.isClient || !this.removeIfInvalid(_snowman)) {
         if (this.onGround) {
            this.removalTimer++;
            if (this.removalTimer >= 1200) {
               this.remove();
               return;
            }
         } else {
            this.removalTimer = 0;
         }

         float _snowmanx = 0.0F;
         BlockPos _snowmanxx = this.getBlockPos();
         FluidState _snowmanxxx = this.world.getFluidState(_snowmanxx);
         if (_snowmanxxx.isIn(FluidTags.WATER)) {
            _snowmanx = _snowmanxxx.getHeight(this.world, _snowmanxx);
         }

         boolean _snowmanxxxx = _snowmanx > 0.0F;
         if (this.state == FishingBobberEntity.State.FLYING) {
            if (this.hookedEntity != null) {
               this.setVelocity(Vec3d.ZERO);
               this.state = FishingBobberEntity.State.HOOKED_IN_ENTITY;
               return;
            }

            if (_snowmanxxxx) {
               this.setVelocity(this.getVelocity().multiply(0.3, 0.2, 0.3));
               this.state = FishingBobberEntity.State.BOBBING;
               return;
            }

            this.checkForCollision();
         } else {
            if (this.state == FishingBobberEntity.State.HOOKED_IN_ENTITY) {
               if (this.hookedEntity != null) {
                  if (this.hookedEntity.removed) {
                     this.hookedEntity = null;
                     this.state = FishingBobberEntity.State.FLYING;
                  } else {
                     this.updatePosition(this.hookedEntity.getX(), this.hookedEntity.getBodyY(0.8), this.hookedEntity.getZ());
                  }
               }

               return;
            }

            if (this.state == FishingBobberEntity.State.BOBBING) {
               Vec3d _snowmanxxxxx = this.getVelocity();
               double _snowmanxxxxxx = this.getY() + _snowmanxxxxx.y - (double)_snowmanxx.getY() - (double)_snowmanx;
               if (Math.abs(_snowmanxxxxxx) < 0.01) {
                  _snowmanxxxxxx += Math.signum(_snowmanxxxxxx) * 0.1;
               }

               this.setVelocity(_snowmanxxxxx.x * 0.9, _snowmanxxxxx.y - _snowmanxxxxxx * (double)this.random.nextFloat() * 0.2, _snowmanxxxxx.z * 0.9);
               if (this.hookCountdown <= 0 && this.fishTravelCountdown <= 0) {
                  this.inOpenWater = true;
               } else {
                  this.inOpenWater = this.inOpenWater && this.outOfOpenWaterTicks < 10 && this.isOpenOrWaterAround(_snowmanxx);
               }

               if (_snowmanxxxx) {
                  this.outOfOpenWaterTicks = Math.max(0, this.outOfOpenWaterTicks - 1);
                  if (this.caughtFish) {
                     this.setVelocity(
                        this.getVelocity().add(0.0, -0.1 * (double)this.velocityRandom.nextFloat() * (double)this.velocityRandom.nextFloat(), 0.0)
                     );
                  }

                  if (!this.world.isClient) {
                     this.tickFishingLogic(_snowmanxx);
                  }
               } else {
                  this.outOfOpenWaterTicks = Math.min(10, this.outOfOpenWaterTicks + 1);
               }
            }
         }

         if (!_snowmanxxx.isIn(FluidTags.WATER)) {
            this.setVelocity(this.getVelocity().add(0.0, -0.03, 0.0));
         }

         this.move(MovementType.SELF, this.getVelocity());
         this.method_26962();
         if (this.state == FishingBobberEntity.State.FLYING && (this.onGround || this.horizontalCollision)) {
            this.setVelocity(Vec3d.ZERO);
         }

         double _snowmanxxxxxxx = 0.92;
         this.setVelocity(this.getVelocity().multiply(0.92));
         this.refreshPosition();
      }
   }

   private boolean removeIfInvalid(PlayerEntity _snowman) {
      ItemStack _snowmanx = _snowman.getMainHandStack();
      ItemStack _snowmanxx = _snowman.getOffHandStack();
      boolean _snowmanxxx = _snowmanx.getItem() == Items.FISHING_ROD;
      boolean _snowmanxxxx = _snowmanxx.getItem() == Items.FISHING_ROD;
      if (!_snowman.removed && _snowman.isAlive() && (_snowmanxxx || _snowmanxxxx) && !(this.squaredDistanceTo(_snowman) > 1024.0)) {
         return false;
      } else {
         this.remove();
         return true;
      }
   }

   private void checkForCollision() {
      HitResult _snowman = ProjectileUtil.getCollision(this, this::method_26958);
      this.onCollision(_snowman);
   }

   @Override
   protected boolean method_26958(Entity _snowman) {
      return super.method_26958(_snowman) || _snowman.isAlive() && _snowman instanceof ItemEntity;
   }

   @Override
   protected void onEntityHit(EntityHitResult entityHitResult) {
      super.onEntityHit(entityHitResult);
      if (!this.world.isClient) {
         this.hookedEntity = entityHitResult.getEntity();
         this.updateHookedEntityId();
      }
   }

   @Override
   protected void onBlockHit(BlockHitResult blockHitResult) {
      super.onBlockHit(blockHitResult);
      this.setVelocity(this.getVelocity().normalize().multiply(blockHitResult.squaredDistanceTo(this)));
   }

   private void updateHookedEntityId() {
      this.getDataTracker().set(HOOK_ENTITY_ID, this.hookedEntity.getEntityId() + 1);
   }

   private void tickFishingLogic(BlockPos pos) {
      ServerWorld _snowman = (ServerWorld)this.world;
      int _snowmanx = 1;
      BlockPos _snowmanxx = pos.up();
      if (this.random.nextFloat() < 0.25F && this.world.hasRain(_snowmanxx)) {
         _snowmanx++;
      }

      if (this.random.nextFloat() < 0.5F && !this.world.isSkyVisible(_snowmanxx)) {
         _snowmanx--;
      }

      if (this.hookCountdown > 0) {
         this.hookCountdown--;
         if (this.hookCountdown <= 0) {
            this.waitCountdown = 0;
            this.fishTravelCountdown = 0;
            this.getDataTracker().set(CAUGHT_FISH, false);
         }
      } else if (this.fishTravelCountdown > 0) {
         this.fishTravelCountdown -= _snowmanx;
         if (this.fishTravelCountdown > 0) {
            this.fishAngle = (float)((double)this.fishAngle + this.random.nextGaussian() * 4.0);
            float _snowmanxxx = this.fishAngle * (float) (Math.PI / 180.0);
            float _snowmanxxxx = MathHelper.sin(_snowmanxxx);
            float _snowmanxxxxx = MathHelper.cos(_snowmanxxx);
            double _snowmanxxxxxx = this.getX() + (double)(_snowmanxxxx * (float)this.fishTravelCountdown * 0.1F);
            double _snowmanxxxxxxx = (double)((float)MathHelper.floor(this.getY()) + 1.0F);
            double _snowmanxxxxxxxx = this.getZ() + (double)(_snowmanxxxxx * (float)this.fishTravelCountdown * 0.1F);
            BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(new BlockPos(_snowmanxxxxxx, _snowmanxxxxxxx - 1.0, _snowmanxxxxxxxx));
            if (_snowmanxxxxxxxxx.isOf(Blocks.WATER)) {
               if (this.random.nextFloat() < 0.15F) {
                  _snowman.spawnParticles(ParticleTypes.BUBBLE, _snowmanxxxxxx, _snowmanxxxxxxx - 0.1F, _snowmanxxxxxxxx, 1, (double)_snowmanxxxx, 0.1, (double)_snowmanxxxxx, 0.0);
               }

               float _snowmanxxxxxxxxxx = _snowmanxxxx * 0.04F;
               float _snowmanxxxxxxxxxxx = _snowmanxxxxx * 0.04F;
               _snowman.spawnParticles(ParticleTypes.FISHING, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0, (double)_snowmanxxxxxxxxxxx, 0.01, (double)(-_snowmanxxxxxxxxxx), 1.0);
               _snowman.spawnParticles(ParticleTypes.FISHING, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0, (double)(-_snowmanxxxxxxxxxxx), 0.01, (double)_snowmanxxxxxxxxxx, 1.0);
            }
         } else {
            this.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            double _snowmanxxx = this.getY() + 0.5;
            _snowman.spawnParticles(
               ParticleTypes.BUBBLE,
               this.getX(),
               _snowmanxxx,
               this.getZ(),
               (int)(1.0F + this.getWidth() * 20.0F),
               (double)this.getWidth(),
               0.0,
               (double)this.getWidth(),
               0.2F
            );
            _snowman.spawnParticles(
               ParticleTypes.FISHING,
               this.getX(),
               _snowmanxxx,
               this.getZ(),
               (int)(1.0F + this.getWidth() * 20.0F),
               (double)this.getWidth(),
               0.0,
               (double)this.getWidth(),
               0.2F
            );
            this.hookCountdown = MathHelper.nextInt(this.random, 20, 40);
            this.getDataTracker().set(CAUGHT_FISH, true);
         }
      } else if (this.waitCountdown > 0) {
         this.waitCountdown -= _snowmanx;
         float _snowmanxxx = 0.15F;
         if (this.waitCountdown < 20) {
            _snowmanxxx = (float)((double)_snowmanxxx + (double)(20 - this.waitCountdown) * 0.05);
         } else if (this.waitCountdown < 40) {
            _snowmanxxx = (float)((double)_snowmanxxx + (double)(40 - this.waitCountdown) * 0.02);
         } else if (this.waitCountdown < 60) {
            _snowmanxxx = (float)((double)_snowmanxxx + (double)(60 - this.waitCountdown) * 0.01);
         }

         if (this.random.nextFloat() < _snowmanxxx) {
            float _snowmanxxxx = MathHelper.nextFloat(this.random, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
            float _snowmanxxxxx = MathHelper.nextFloat(this.random, 25.0F, 60.0F);
            double _snowmanxxxxxx = this.getX() + (double)(MathHelper.sin(_snowmanxxxx) * _snowmanxxxxx * 0.1F);
            double _snowmanxxxxxxx = (double)((float)MathHelper.floor(this.getY()) + 1.0F);
            double _snowmanxxxxxxxx = this.getZ() + (double)(MathHelper.cos(_snowmanxxxx) * _snowmanxxxxx * 0.1F);
            BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(new BlockPos(_snowmanxxxxxx, _snowmanxxxxxxx - 1.0, _snowmanxxxxxxxx));
            if (_snowmanxxxxxxxxx.isOf(Blocks.WATER)) {
               _snowman.spawnParticles(ParticleTypes.SPLASH, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 2 + this.random.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
            }
         }

         if (this.waitCountdown <= 0) {
            this.fishAngle = MathHelper.nextFloat(this.random, 0.0F, 360.0F);
            this.fishTravelCountdown = MathHelper.nextInt(this.random, 20, 80);
         }
      } else {
         this.waitCountdown = MathHelper.nextInt(this.random, 100, 600);
         this.waitCountdown = this.waitCountdown - this.lureLevel * 20 * 5;
      }
   }

   private boolean isOpenOrWaterAround(BlockPos pos) {
      FishingBobberEntity.PositionType _snowman = FishingBobberEntity.PositionType.INVALID;

      for (int _snowmanx = -1; _snowmanx <= 2; _snowmanx++) {
         FishingBobberEntity.PositionType _snowmanxx = this.getPositionType(pos.add(-2, _snowmanx, -2), pos.add(2, _snowmanx, 2));
         switch (_snowmanxx) {
            case INVALID:
               return false;
            case ABOVE_WATER:
               if (_snowman == FishingBobberEntity.PositionType.INVALID) {
                  return false;
               }
               break;
            case INSIDE_WATER:
               if (_snowman == FishingBobberEntity.PositionType.ABOVE_WATER) {
                  return false;
               }
         }

         _snowman = _snowmanxx;
      }

      return true;
   }

   private FishingBobberEntity.PositionType getPositionType(BlockPos start, BlockPos end) {
      return BlockPos.stream(start, end)
         .map(this::getPositionType)
         .reduce((_snowman, _snowmanx) -> _snowman == _snowmanx ? _snowman : FishingBobberEntity.PositionType.INVALID)
         .orElse(FishingBobberEntity.PositionType.INVALID);
   }

   private FishingBobberEntity.PositionType getPositionType(BlockPos pos) {
      BlockState _snowman = this.world.getBlockState(pos);
      if (!_snowman.isAir() && !_snowman.isOf(Blocks.LILY_PAD)) {
         FluidState _snowmanx = _snowman.getFluidState();
         return _snowmanx.isIn(FluidTags.WATER) && _snowmanx.isStill() && _snowman.getCollisionShape(this.world, pos).isEmpty()
            ? FishingBobberEntity.PositionType.INSIDE_WATER
            : FishingBobberEntity.PositionType.INVALID;
      } else {
         return FishingBobberEntity.PositionType.ABOVE_WATER;
      }
   }

   public boolean isInOpenWater() {
      return this.inOpenWater;
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
   }

   public int use(ItemStack usedItem) {
      PlayerEntity _snowman = this.getPlayerOwner();
      if (!this.world.isClient && _snowman != null) {
         int _snowmanx = 0;
         if (this.hookedEntity != null) {
            this.pullHookedEntity();
            Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)_snowman, usedItem, this, Collections.emptyList());
            this.world.sendEntityStatus(this, (byte)31);
            _snowmanx = this.hookedEntity instanceof ItemEntity ? 3 : 5;
         } else if (this.hookCountdown > 0) {
            LootContext.Builder _snowmanxx = new LootContext.Builder((ServerWorld)this.world)
               .parameter(LootContextParameters.ORIGIN, this.getPos())
               .parameter(LootContextParameters.TOOL, usedItem)
               .parameter(LootContextParameters.THIS_ENTITY, this)
               .random(this.random)
               .luck((float)this.luckOfTheSeaLevel + _snowman.getLuck());
            LootTable _snowmanxxx = this.world.getServer().getLootManager().getTable(LootTables.FISHING_GAMEPLAY);
            List<ItemStack> _snowmanxxxx = _snowmanxxx.generateLoot(_snowmanxx.build(LootContextTypes.FISHING));
            Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)_snowman, usedItem, this, _snowmanxxxx);

            for (ItemStack _snowmanxxxxx : _snowmanxxxx) {
               ItemEntity _snowmanxxxxxx = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), _snowmanxxxxx);
               double _snowmanxxxxxxx = _snowman.getX() - this.getX();
               double _snowmanxxxxxxxx = _snowman.getY() - this.getY();
               double _snowmanxxxxxxxxx = _snowman.getZ() - this.getZ();
               double _snowmanxxxxxxxxxx = 0.1;
               _snowmanxxxxxx.setVelocity(
                  _snowmanxxxxxxx * 0.1,
                  _snowmanxxxxxxxx * 0.1 + Math.sqrt(Math.sqrt(_snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx)) * 0.08,
                  _snowmanxxxxxxxxx * 0.1
               );
               this.world.spawnEntity(_snowmanxxxxxx);
               _snowman.world.spawnEntity(new ExperienceOrbEntity(_snowman.world, _snowman.getX(), _snowman.getY() + 0.5, _snowman.getZ() + 0.5, this.random.nextInt(6) + 1));
               if (_snowmanxxxxx.getItem().isIn(ItemTags.FISHES)) {
                  _snowman.increaseStat(Stats.FISH_CAUGHT, 1);
               }
            }

            _snowmanx = 1;
         }

         if (this.onGround) {
            _snowmanx = 2;
         }

         this.remove();
         return _snowmanx;
      } else {
         return 0;
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 31 && this.world.isClient && this.hookedEntity instanceof PlayerEntity && ((PlayerEntity)this.hookedEntity).isMainPlayer()) {
         this.pullHookedEntity();
      }

      super.handleStatus(status);
   }

   protected void pullHookedEntity() {
      Entity _snowman = this.getOwner();
      if (_snowman != null) {
         Vec3d _snowmanx = new Vec3d(_snowman.getX() - this.getX(), _snowman.getY() - this.getY(), _snowman.getZ() - this.getZ()).multiply(0.1);
         this.hookedEntity.setVelocity(this.hookedEntity.getVelocity().add(_snowmanx));
      }
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   public void remove() {
      super.remove();
      PlayerEntity _snowman = this.getPlayerOwner();
      if (_snowman != null) {
         _snowman.fishHook = null;
      }
   }

   @Nullable
   public PlayerEntity getPlayerOwner() {
      Entity _snowman = this.getOwner();
      return _snowman instanceof PlayerEntity ? (PlayerEntity)_snowman : null;
   }

   @Nullable
   public Entity getHookedEntity() {
      return this.hookedEntity;
   }

   @Override
   public boolean canUsePortals() {
      return false;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      Entity _snowman = this.getOwner();
      return new EntitySpawnS2CPacket(this, _snowman == null ? this.getEntityId() : _snowman.getEntityId());
   }

   static enum PositionType {
      ABOVE_WATER,
      INSIDE_WATER,
      INVALID;

      private PositionType() {
      }
   }

   static enum State {
      FLYING,
      HOOKED_IN_ENTITY,
      BOBBING;

      private State() {
      }
   }
}
