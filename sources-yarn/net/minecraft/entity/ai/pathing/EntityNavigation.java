package net.minecraft.entity.ai.pathing;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCache;

public abstract class EntityNavigation {
   protected final MobEntity entity;
   protected final World world;
   @Nullable
   protected Path currentPath;
   protected double speed;
   protected int tickCount;
   protected int pathStartTime;
   protected Vec3d pathStartPos = Vec3d.ZERO;
   protected Vec3i lastNodePosition = Vec3i.ZERO;
   protected long currentNodeMs;
   protected long lastActiveTickMs;
   protected double currentNodeTimeout;
   protected float nodeReachProximity = 0.5F;
   protected boolean shouldRecalculate;
   protected long lastRecalculateTime;
   protected PathNodeMaker nodeMaker;
   private BlockPos currentTarget;
   private int currentDistance;
   private float rangeMultiplier = 1.0F;
   private final PathNodeNavigator pathNodeNavigator;
   private boolean nearPathStartPos;

   public EntityNavigation(MobEntity mob, World world) {
      this.entity = mob;
      this.world = world;
      int i = MathHelper.floor(mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE) * 16.0);
      this.pathNodeNavigator = this.createPathNodeNavigator(i);
   }

   public void resetRangeMultiplier() {
      this.rangeMultiplier = 1.0F;
   }

   public void setRangeMultiplier(float rangeMultiplier) {
      this.rangeMultiplier = rangeMultiplier;
   }

   public BlockPos getTargetPos() {
      return this.currentTarget;
   }

   protected abstract PathNodeNavigator createPathNodeNavigator(int range);

   public void setSpeed(double speed) {
      this.speed = speed;
   }

   public boolean shouldRecalculatePath() {
      return this.shouldRecalculate;
   }

   public void recalculatePath() {
      if (this.world.getTime() - this.lastRecalculateTime > 20L) {
         if (this.currentTarget != null) {
            this.currentPath = null;
            this.currentPath = this.findPathTo(this.currentTarget, this.currentDistance);
            this.lastRecalculateTime = this.world.getTime();
            this.shouldRecalculate = false;
         }
      } else {
         this.shouldRecalculate = true;
      }
   }

   @Nullable
   public final Path findPathTo(double x, double y, double z, int distance) {
      return this.findPathTo(new BlockPos(x, y, z), distance);
   }

   @Nullable
   public Path findPathToAny(Stream<BlockPos> positions, int distance) {
      return this.findPathToAny(positions.collect(Collectors.toSet()), 8, false, distance);
   }

   @Nullable
   public Path method_29934(Set<BlockPos> set, int i) {
      return this.findPathToAny(set, 8, false, i);
   }

   @Nullable
   public Path findPathTo(BlockPos target, int distance) {
      return this.findPathToAny(ImmutableSet.of(target), 8, false, distance);
   }

   @Nullable
   public Path findPathTo(Entity entity, int distance) {
      return this.findPathToAny(ImmutableSet.of(entity.getBlockPos()), 16, true, distance);
   }

   @Nullable
   protected Path findPathToAny(Set<BlockPos> positions, int range, boolean bl, int distance) {
      if (positions.isEmpty()) {
         return null;
      } else if (this.entity.getY() < 0.0) {
         return null;
      } else if (!this.isAtValidPosition()) {
         return null;
      } else if (this.currentPath != null && !this.currentPath.isFinished() && positions.contains(this.currentTarget)) {
         return this.currentPath;
      } else {
         this.world.getProfiler().push("pathfind");
         float f = (float)this.entity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
         BlockPos lv = bl ? this.entity.getBlockPos().up() : this.entity.getBlockPos();
         int k = (int)(f + (float)range);
         ChunkCache lv2 = new ChunkCache(this.world, lv.add(-k, -k, -k), lv.add(k, k, k));
         Path lv3 = this.pathNodeNavigator.findPathToAny(lv2, this.entity, positions, f, distance, this.rangeMultiplier);
         this.world.getProfiler().pop();
         if (lv3 != null && lv3.getTarget() != null) {
            this.currentTarget = lv3.getTarget();
            this.currentDistance = distance;
            this.resetNode();
         }

         return lv3;
      }
   }

   public boolean startMovingTo(double x, double y, double z, double speed) {
      return this.startMovingAlong(this.findPathTo(x, y, z, 1), speed);
   }

   public boolean startMovingTo(Entity entity, double speed) {
      Path lv = this.findPathTo(entity, 1);
      return lv != null && this.startMovingAlong(lv, speed);
   }

   public boolean startMovingAlong(@Nullable Path path, double speed) {
      if (path == null) {
         this.currentPath = null;
         return false;
      } else {
         if (!path.equalsPath(this.currentPath)) {
            this.currentPath = path;
         }

         if (this.isIdle()) {
            return false;
         } else {
            this.adjustPath();
            if (this.currentPath.getLength() <= 0) {
               return false;
            } else {
               this.speed = speed;
               Vec3d lv = this.getPos();
               this.pathStartTime = this.tickCount;
               this.pathStartPos = lv;
               return true;
            }
         }
      }
   }

   @Nullable
   public Path getCurrentPath() {
      return this.currentPath;
   }

   public void tick() {
      this.tickCount++;
      if (this.shouldRecalculate) {
         this.recalculatePath();
      }

      if (!this.isIdle()) {
         if (this.isAtValidPosition()) {
            this.continueFollowingPath();
         } else if (this.currentPath != null && !this.currentPath.isFinished()) {
            Vec3d lv = this.getPos();
            Vec3d lv2 = this.currentPath.getNodePosition(this.entity);
            if (lv.y > lv2.y
               && !this.entity.isOnGround()
               && MathHelper.floor(lv.x) == MathHelper.floor(lv2.x)
               && MathHelper.floor(lv.z) == MathHelper.floor(lv2.z)) {
               this.currentPath.next();
            }
         }

         DebugInfoSender.sendPathfindingData(this.world, this.entity, this.currentPath, this.nodeReachProximity);
         if (!this.isIdle()) {
            Vec3d lv3 = this.currentPath.getNodePosition(this.entity);
            BlockPos lv4 = new BlockPos(lv3);
            this.entity
               .getMoveControl()
               .moveTo(lv3.x, this.world.getBlockState(lv4.down()).isAir() ? lv3.y : LandPathNodeMaker.getFeetY(this.world, lv4), lv3.z, this.speed);
         }
      }
   }

   protected void continueFollowingPath() {
      Vec3d lv = this.getPos();
      this.nodeReachProximity = this.entity.getWidth() > 0.75F ? this.entity.getWidth() / 2.0F : 0.75F - this.entity.getWidth() / 2.0F;
      Vec3i lv2 = this.currentPath.method_31032();
      double d = Math.abs(this.entity.getX() - ((double)lv2.getX() + 0.5));
      double e = Math.abs(this.entity.getY() - (double)lv2.getY());
      double f = Math.abs(this.entity.getZ() - ((double)lv2.getZ() + 0.5));
      boolean bl = d < (double)this.nodeReachProximity && f < (double)this.nodeReachProximity && e < 1.0;
      if (bl || this.entity.method_29244(this.currentPath.method_29301().type) && this.method_27799(lv)) {
         this.currentPath.next();
      }

      this.checkTimeouts(lv);
   }

   private boolean method_27799(Vec3d arg) {
      if (this.currentPath.getCurrentNodeIndex() + 1 >= this.currentPath.getLength()) {
         return false;
      } else {
         Vec3d lv = Vec3d.ofBottomCenter(this.currentPath.method_31032());
         if (!arg.isInRange(lv, 2.0)) {
            return false;
         } else {
            Vec3d lv2 = Vec3d.ofBottomCenter(this.currentPath.method_31031(this.currentPath.getCurrentNodeIndex() + 1));
            Vec3d lv3 = lv2.subtract(lv);
            Vec3d lv4 = arg.subtract(lv);
            return lv3.dotProduct(lv4) > 0.0;
         }
      }
   }

   protected void checkTimeouts(Vec3d currentPos) {
      if (this.tickCount - this.pathStartTime > 100) {
         if (currentPos.squaredDistanceTo(this.pathStartPos) < 2.25) {
            this.nearPathStartPos = true;
            this.stop();
         } else {
            this.nearPathStartPos = false;
         }

         this.pathStartTime = this.tickCount;
         this.pathStartPos = currentPos;
      }

      if (this.currentPath != null && !this.currentPath.isFinished()) {
         Vec3i lv = this.currentPath.method_31032();
         if (lv.equals(this.lastNodePosition)) {
            this.currentNodeMs = this.currentNodeMs + (Util.getMeasuringTimeMs() - this.lastActiveTickMs);
         } else {
            this.lastNodePosition = lv;
            double d = currentPos.distanceTo(Vec3d.ofBottomCenter(this.lastNodePosition));
            this.currentNodeTimeout = this.entity.getMovementSpeed() > 0.0F ? d / (double)this.entity.getMovementSpeed() * 1000.0 : 0.0;
         }

         if (this.currentNodeTimeout > 0.0 && (double)this.currentNodeMs > this.currentNodeTimeout * 3.0) {
            this.resetNodeAndStop();
         }

         this.lastActiveTickMs = Util.getMeasuringTimeMs();
      }
   }

   private void resetNodeAndStop() {
      this.resetNode();
      this.stop();
   }

   private void resetNode() {
      this.lastNodePosition = Vec3i.ZERO;
      this.currentNodeMs = 0L;
      this.currentNodeTimeout = 0.0;
      this.nearPathStartPos = false;
   }

   public boolean isIdle() {
      return this.currentPath == null || this.currentPath.isFinished();
   }

   public boolean isFollowingPath() {
      return !this.isIdle();
   }

   public void stop() {
      this.currentPath = null;
   }

   protected abstract Vec3d getPos();

   protected abstract boolean isAtValidPosition();

   protected boolean isInLiquid() {
      return this.entity.isInsideWaterOrBubbleColumn() || this.entity.isInLava();
   }

   protected void adjustPath() {
      if (this.currentPath != null) {
         for (int i = 0; i < this.currentPath.getLength(); i++) {
            PathNode lv = this.currentPath.getNode(i);
            PathNode lv2 = i + 1 < this.currentPath.getLength() ? this.currentPath.getNode(i + 1) : null;
            BlockState lv3 = this.world.getBlockState(new BlockPos(lv.x, lv.y, lv.z));
            if (lv3.isOf(Blocks.CAULDRON)) {
               this.currentPath.setNode(i, lv.copyWithNewPosition(lv.x, lv.y + 1, lv.z));
               if (lv2 != null && lv.y >= lv2.y) {
                  this.currentPath.setNode(i + 1, lv.copyWithNewPosition(lv2.x, lv.y + 1, lv2.z));
               }
            }
         }
      }
   }

   protected abstract boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ);

   public boolean isValidPosition(BlockPos pos) {
      BlockPos lv = pos.down();
      return this.world.getBlockState(lv).isOpaqueFullCube(this.world, lv);
   }

   public PathNodeMaker getNodeMaker() {
      return this.nodeMaker;
   }

   public void setCanSwim(boolean canSwim) {
      this.nodeMaker.setCanSwim(canSwim);
   }

   public boolean canSwim() {
      return this.nodeMaker.canSwim();
   }

   public void onBlockChanged(BlockPos pos) {
      if (this.currentPath != null && !this.currentPath.isFinished() && this.currentPath.getLength() != 0) {
         PathNode lv = this.currentPath.getEnd();
         Vec3d lv2 = new Vec3d(((double)lv.x + this.entity.getX()) / 2.0, ((double)lv.y + this.entity.getY()) / 2.0, ((double)lv.z + this.entity.getZ()) / 2.0);
         if (pos.isWithinDistance(lv2, (double)(this.currentPath.getLength() - this.currentPath.getCurrentNodeIndex()))) {
            this.recalculatePath();
         }
      }
   }

   public boolean isNearPathStartPos() {
      return this.nearPathStartPos;
   }
}
