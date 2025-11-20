package net.minecraft.entity.projectile;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   @Environment(EnvType.CLIENT)
   public FishingBobberEntity(World world, PlayerEntity thrower, double x, double y, double z) {
      this(world, thrower, 0, 0);
      this.updatePosition(x, y, z);
      this.prevX = this.getX();
      this.prevY = this.getY();
      this.prevZ = this.getZ();
   }

   public FishingBobberEntity(PlayerEntity thrower, World world, int lureLevel, int luckOfTheSeaLevel) {
      this(world, thrower, lureLevel, luckOfTheSeaLevel);
      float f = thrower.pitch;
      float g = thrower.yaw;
      float h = MathHelper.cos(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
      float k = MathHelper.sin(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
      float l = -MathHelper.cos(-f * (float) (Math.PI / 180.0));
      float m = MathHelper.sin(-f * (float) (Math.PI / 180.0));
      double d = thrower.getX() - (double)k * 0.3;
      double e = thrower.getEyeY();
      double n = thrower.getZ() - (double)h * 0.3;
      this.refreshPositionAndAngles(d, e, n, g, f);
      Vec3d lv = new Vec3d((double)(-k), (double)MathHelper.clamp(-(m / l), -5.0F, 5.0F), (double)(-h));
      double o = lv.length();
      lv = lv.multiply(
         0.6 / o + 0.5 + this.random.nextGaussian() * 0.0045,
         0.6 / o + 0.5 + this.random.nextGaussian() * 0.0045,
         0.6 / o + 0.5 + this.random.nextGaussian() * 0.0045
      );
      this.setVelocity(lv);
      this.yaw = (float)(MathHelper.atan2(lv.x, lv.z) * 180.0F / (float)Math.PI);
      this.pitch = (float)(MathHelper.atan2(lv.y, (double)MathHelper.sqrt(squaredHorizontalLength(lv))) * 180.0F / (float)Math.PI);
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
         int i = this.getDataTracker().get(HOOK_ENTITY_ID);
         this.hookedEntity = i > 0 ? this.world.getEntityById(i - 1) : null;
      }

      if (CAUGHT_FISH.equals(data)) {
         this.caughtFish = this.getDataTracker().get(CAUGHT_FISH);
         if (this.caughtFish) {
            this.setVelocity(this.getVelocity().x, (double)(-0.4F * MathHelper.nextFloat(this.velocityRandom, 0.6F, 1.0F)), this.getVelocity().z);
         }
      }

      super.onTrackedDataSet(data);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean shouldRender(double distance) {
      double e = 64.0;
      return distance < 4096.0;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
   }

   @Override
   public void tick() {
      this.velocityRandom.setSeed(this.getUuid().getLeastSignificantBits() ^ this.world.getTime());
      super.tick();
      PlayerEntity lv = this.getPlayerOwner();
      if (lv == null) {
         this.remove();
      } else if (this.world.isClient || !this.removeIfInvalid(lv)) {
         if (this.onGround) {
            this.removalTimer++;
            if (this.removalTimer >= 1200) {
               this.remove();
               return;
            }
         } else {
            this.removalTimer = 0;
         }

         float f = 0.0F;
         BlockPos lv2 = this.getBlockPos();
         FluidState lv3 = this.world.getFluidState(lv2);
         if (lv3.isIn(FluidTags.WATER)) {
            f = lv3.getHeight(this.world, lv2);
         }

         boolean bl = f > 0.0F;
         if (this.state == FishingBobberEntity.State.FLYING) {
            if (this.hookedEntity != null) {
               this.setVelocity(Vec3d.ZERO);
               this.state = FishingBobberEntity.State.HOOKED_IN_ENTITY;
               return;
            }

            if (bl) {
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
               Vec3d lv4 = this.getVelocity();
               double d = this.getY() + lv4.y - (double)lv2.getY() - (double)f;
               if (Math.abs(d) < 0.01) {
                  d += Math.signum(d) * 0.1;
               }

               this.setVelocity(lv4.x * 0.9, lv4.y - d * (double)this.random.nextFloat() * 0.2, lv4.z * 0.9);
               if (this.hookCountdown <= 0 && this.fishTravelCountdown <= 0) {
                  this.inOpenWater = true;
               } else {
                  this.inOpenWater = this.inOpenWater && this.outOfOpenWaterTicks < 10 && this.isOpenOrWaterAround(lv2);
               }

               if (bl) {
                  this.outOfOpenWaterTicks = Math.max(0, this.outOfOpenWaterTicks - 1);
                  if (this.caughtFish) {
                     this.setVelocity(
                        this.getVelocity().add(0.0, -0.1 * (double)this.velocityRandom.nextFloat() * (double)this.velocityRandom.nextFloat(), 0.0)
                     );
                  }

                  if (!this.world.isClient) {
                     this.tickFishingLogic(lv2);
                  }
               } else {
                  this.outOfOpenWaterTicks = Math.min(10, this.outOfOpenWaterTicks + 1);
               }
            }
         }

         if (!lv3.isIn(FluidTags.WATER)) {
            this.setVelocity(this.getVelocity().add(0.0, -0.03, 0.0));
         }

         this.move(MovementType.SELF, this.getVelocity());
         this.method_26962();
         if (this.state == FishingBobberEntity.State.FLYING && (this.onGround || this.horizontalCollision)) {
            this.setVelocity(Vec3d.ZERO);
         }

         double e = 0.92;
         this.setVelocity(this.getVelocity().multiply(0.92));
         this.refreshPosition();
      }
   }

   private boolean removeIfInvalid(PlayerEntity arg) {
      ItemStack lv = arg.getMainHandStack();
      ItemStack lv2 = arg.getOffHandStack();
      boolean bl = lv.getItem() == Items.FISHING_ROD;
      boolean bl2 = lv2.getItem() == Items.FISHING_ROD;
      if (!arg.removed && arg.isAlive() && (bl || bl2) && !(this.squaredDistanceTo(arg) > 1024.0)) {
         return false;
      } else {
         this.remove();
         return true;
      }
   }

   private void checkForCollision() {
      HitResult lv = ProjectileUtil.getCollision(this, this::method_26958);
      this.onCollision(lv);
   }

   @Override
   protected boolean method_26958(Entity arg) {
      return super.method_26958(arg) || arg.isAlive() && arg instanceof ItemEntity;
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
      ServerWorld lv = (ServerWorld)this.world;
      int i = 1;
      BlockPos lv2 = pos.up();
      if (this.random.nextFloat() < 0.25F && this.world.hasRain(lv2)) {
         i++;
      }

      if (this.random.nextFloat() < 0.5F && !this.world.isSkyVisible(lv2)) {
         i--;
      }

      if (this.hookCountdown > 0) {
         this.hookCountdown--;
         if (this.hookCountdown <= 0) {
            this.waitCountdown = 0;
            this.fishTravelCountdown = 0;
            this.getDataTracker().set(CAUGHT_FISH, false);
         }
      } else if (this.fishTravelCountdown > 0) {
         this.fishTravelCountdown -= i;
         if (this.fishTravelCountdown > 0) {
            this.fishAngle = (float)((double)this.fishAngle + this.random.nextGaussian() * 4.0);
            float f = this.fishAngle * (float) (Math.PI / 180.0);
            float g = MathHelper.sin(f);
            float h = MathHelper.cos(f);
            double d = this.getX() + (double)(g * (float)this.fishTravelCountdown * 0.1F);
            double e = (double)((float)MathHelper.floor(this.getY()) + 1.0F);
            double j = this.getZ() + (double)(h * (float)this.fishTravelCountdown * 0.1F);
            BlockState lv3 = lv.getBlockState(new BlockPos(d, e - 1.0, j));
            if (lv3.isOf(Blocks.WATER)) {
               if (this.random.nextFloat() < 0.15F) {
                  lv.spawnParticles(ParticleTypes.BUBBLE, d, e - 0.1F, j, 1, (double)g, 0.1, (double)h, 0.0);
               }

               float k = g * 0.04F;
               float l = h * 0.04F;
               lv.spawnParticles(ParticleTypes.FISHING, d, e, j, 0, (double)l, 0.01, (double)(-k), 1.0);
               lv.spawnParticles(ParticleTypes.FISHING, d, e, j, 0, (double)(-l), 0.01, (double)k, 1.0);
            }
         } else {
            this.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            double m = this.getY() + 0.5;
            lv.spawnParticles(
               ParticleTypes.BUBBLE,
               this.getX(),
               m,
               this.getZ(),
               (int)(1.0F + this.getWidth() * 20.0F),
               (double)this.getWidth(),
               0.0,
               (double)this.getWidth(),
               0.2F
            );
            lv.spawnParticles(
               ParticleTypes.FISHING,
               this.getX(),
               m,
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
         this.waitCountdown -= i;
         float n = 0.15F;
         if (this.waitCountdown < 20) {
            n = (float)((double)n + (double)(20 - this.waitCountdown) * 0.05);
         } else if (this.waitCountdown < 40) {
            n = (float)((double)n + (double)(40 - this.waitCountdown) * 0.02);
         } else if (this.waitCountdown < 60) {
            n = (float)((double)n + (double)(60 - this.waitCountdown) * 0.01);
         }

         if (this.random.nextFloat() < n) {
            float o = MathHelper.nextFloat(this.random, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
            float p = MathHelper.nextFloat(this.random, 25.0F, 60.0F);
            double q = this.getX() + (double)(MathHelper.sin(o) * p * 0.1F);
            double r = (double)((float)MathHelper.floor(this.getY()) + 1.0F);
            double s = this.getZ() + (double)(MathHelper.cos(o) * p * 0.1F);
            BlockState lv4 = lv.getBlockState(new BlockPos(q, r - 1.0, s));
            if (lv4.isOf(Blocks.WATER)) {
               lv.spawnParticles(ParticleTypes.SPLASH, q, r, s, 2 + this.random.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
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
      FishingBobberEntity.PositionType lv = FishingBobberEntity.PositionType.INVALID;

      for (int i = -1; i <= 2; i++) {
         FishingBobberEntity.PositionType lv2 = this.getPositionType(pos.add(-2, i, -2), pos.add(2, i, 2));
         switch (lv2) {
            case INVALID:
               return false;
            case ABOVE_WATER:
               if (lv == FishingBobberEntity.PositionType.INVALID) {
                  return false;
               }
               break;
            case INSIDE_WATER:
               if (lv == FishingBobberEntity.PositionType.ABOVE_WATER) {
                  return false;
               }
         }

         lv = lv2;
      }

      return true;
   }

   private FishingBobberEntity.PositionType getPositionType(BlockPos start, BlockPos end) {
      return BlockPos.stream(start, end)
         .map(this::getPositionType)
         .reduce((arg, arg2) -> arg == arg2 ? arg : FishingBobberEntity.PositionType.INVALID)
         .orElse(FishingBobberEntity.PositionType.INVALID);
   }

   private FishingBobberEntity.PositionType getPositionType(BlockPos pos) {
      BlockState lv = this.world.getBlockState(pos);
      if (!lv.isAir() && !lv.isOf(Blocks.LILY_PAD)) {
         FluidState lv2 = lv.getFluidState();
         return lv2.isIn(FluidTags.WATER) && lv2.isStill() && lv.getCollisionShape(this.world, pos).isEmpty()
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
      PlayerEntity lv = this.getPlayerOwner();
      if (!this.world.isClient && lv != null) {
         int i = 0;
         if (this.hookedEntity != null) {
            this.pullHookedEntity();
            Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)lv, usedItem, this, Collections.emptyList());
            this.world.sendEntityStatus(this, (byte)31);
            i = this.hookedEntity instanceof ItemEntity ? 3 : 5;
         } else if (this.hookCountdown > 0) {
            LootContext.Builder lv2 = new LootContext.Builder((ServerWorld)this.world)
               .parameter(LootContextParameters.ORIGIN, this.getPos())
               .parameter(LootContextParameters.TOOL, usedItem)
               .parameter(LootContextParameters.THIS_ENTITY, this)
               .random(this.random)
               .luck((float)this.luckOfTheSeaLevel + lv.getLuck());
            LootTable lv3 = this.world.getServer().getLootManager().getTable(LootTables.FISHING_GAMEPLAY);
            List<ItemStack> list = lv3.generateLoot(lv2.build(LootContextTypes.FISHING));
            Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)lv, usedItem, this, list);

            for (ItemStack lv4 : list) {
               ItemEntity lv5 = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), lv4);
               double d = lv.getX() - this.getX();
               double e = lv.getY() - this.getY();
               double f = lv.getZ() - this.getZ();
               double g = 0.1;
               lv5.setVelocity(d * 0.1, e * 0.1 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08, f * 0.1);
               this.world.spawnEntity(lv5);
               lv.world.spawnEntity(new ExperienceOrbEntity(lv.world, lv.getX(), lv.getY() + 0.5, lv.getZ() + 0.5, this.random.nextInt(6) + 1));
               if (lv4.getItem().isIn(ItemTags.FISHES)) {
                  lv.increaseStat(Stats.FISH_CAUGHT, 1);
               }
            }

            i = 1;
         }

         if (this.onGround) {
            i = 2;
         }

         this.remove();
         return i;
      } else {
         return 0;
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void handleStatus(byte status) {
      if (status == 31 && this.world.isClient && this.hookedEntity instanceof PlayerEntity && ((PlayerEntity)this.hookedEntity).isMainPlayer()) {
         this.pullHookedEntity();
      }

      super.handleStatus(status);
   }

   protected void pullHookedEntity() {
      Entity lv = this.getOwner();
      if (lv != null) {
         Vec3d lv2 = new Vec3d(lv.getX() - this.getX(), lv.getY() - this.getY(), lv.getZ() - this.getZ()).multiply(0.1);
         this.hookedEntity.setVelocity(this.hookedEntity.getVelocity().add(lv2));
      }
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   public void remove() {
      super.remove();
      PlayerEntity lv = this.getPlayerOwner();
      if (lv != null) {
         lv.fishHook = null;
      }
   }

   @Nullable
   public PlayerEntity getPlayerOwner() {
      Entity lv = this.getOwner();
      return lv instanceof PlayerEntity ? (PlayerEntity)lv : null;
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
      Entity lv = this.getOwner();
      return new EntitySpawnS2CPacket(this, lv == null ? this.getEntityId() : lv.getEntityId());
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
