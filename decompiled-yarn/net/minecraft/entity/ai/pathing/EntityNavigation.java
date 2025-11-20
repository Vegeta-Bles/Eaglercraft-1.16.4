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
      int _snowman = MathHelper.floor(mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE) * 16.0);
      this.pathNodeNavigator = this.createPathNodeNavigator(_snowman);
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
   public Path method_29934(Set<BlockPos> _snowman, int _snowman) {
      return this.findPathToAny(_snowman, 8, false, _snowman);
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
   protected Path findPathToAny(Set<BlockPos> positions, int range, boolean _snowman, int distance) {
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
         float _snowmanx = (float)this.entity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
         BlockPos _snowmanxx = _snowman ? this.entity.getBlockPos().up() : this.entity.getBlockPos();
         int _snowmanxxx = (int)(_snowmanx + (float)range);
         ChunkCache _snowmanxxxx = new ChunkCache(this.world, _snowmanxx.add(-_snowmanxxx, -_snowmanxxx, -_snowmanxxx), _snowmanxx.add(_snowmanxxx, _snowmanxxx, _snowmanxxx));
         Path _snowmanxxxxx = this.pathNodeNavigator.findPathToAny(_snowmanxxxx, this.entity, positions, _snowmanx, distance, this.rangeMultiplier);
         this.world.getProfiler().pop();
         if (_snowmanxxxxx != null && _snowmanxxxxx.getTarget() != null) {
            this.currentTarget = _snowmanxxxxx.getTarget();
            this.currentDistance = distance;
            this.resetNode();
         }

         return _snowmanxxxxx;
      }
   }

   public boolean startMovingTo(double x, double y, double z, double speed) {
      return this.startMovingAlong(this.findPathTo(x, y, z, 1), speed);
   }

   public boolean startMovingTo(Entity entity, double speed) {
      Path _snowman = this.findPathTo(entity, 1);
      return _snowman != null && this.startMovingAlong(_snowman, speed);
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
               Vec3d _snowman = this.getPos();
               this.pathStartTime = this.tickCount;
               this.pathStartPos = _snowman;
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
            Vec3d _snowman = this.getPos();
            Vec3d _snowmanx = this.currentPath.getNodePosition(this.entity);
            if (_snowman.y > _snowmanx.y && !this.entity.isOnGround() && MathHelper.floor(_snowman.x) == MathHelper.floor(_snowmanx.x) && MathHelper.floor(_snowman.z) == MathHelper.floor(_snowmanx.z)) {
               this.currentPath.next();
            }
         }

         DebugInfoSender.sendPathfindingData(this.world, this.entity, this.currentPath, this.nodeReachProximity);
         if (!this.isIdle()) {
            Vec3d _snowman = this.currentPath.getNodePosition(this.entity);
            BlockPos _snowmanx = new BlockPos(_snowman);
            this.entity
               .getMoveControl()
               .moveTo(_snowman.x, this.world.getBlockState(_snowmanx.down()).isAir() ? _snowman.y : LandPathNodeMaker.getFeetY(this.world, _snowmanx), _snowman.z, this.speed);
         }
      }
   }

   protected void continueFollowingPath() {
      Vec3d _snowman = this.getPos();
      this.nodeReachProximity = this.entity.getWidth() > 0.75F ? this.entity.getWidth() / 2.0F : 0.75F - this.entity.getWidth() / 2.0F;
      Vec3i _snowmanx = this.currentPath.method_31032();
      double _snowmanxx = Math.abs(this.entity.getX() - ((double)_snowmanx.getX() + 0.5));
      double _snowmanxxx = Math.abs(this.entity.getY() - (double)_snowmanx.getY());
      double _snowmanxxxx = Math.abs(this.entity.getZ() - ((double)_snowmanx.getZ() + 0.5));
      boolean _snowmanxxxxx = _snowmanxx < (double)this.nodeReachProximity && _snowmanxxxx < (double)this.nodeReachProximity && _snowmanxxx < 1.0;
      if (_snowmanxxxxx || this.entity.method_29244(this.currentPath.method_29301().type) && this.method_27799(_snowman)) {
         this.currentPath.next();
      }

      this.checkTimeouts(_snowman);
   }

   private boolean method_27799(Vec3d _snowman) {
      if (this.currentPath.getCurrentNodeIndex() + 1 >= this.currentPath.getLength()) {
         return false;
      } else {
         Vec3d _snowmanx = Vec3d.ofBottomCenter(this.currentPath.method_31032());
         if (!_snowman.isInRange(_snowmanx, 2.0)) {
            return false;
         } else {
            Vec3d _snowmanxx = Vec3d.ofBottomCenter(this.currentPath.method_31031(this.currentPath.getCurrentNodeIndex() + 1));
            Vec3d _snowmanxxx = _snowmanxx.subtract(_snowmanx);
            Vec3d _snowmanxxxx = _snowman.subtract(_snowmanx);
            return _snowmanxxx.dotProduct(_snowmanxxxx) > 0.0;
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
         Vec3i _snowman = this.currentPath.method_31032();
         if (_snowman.equals(this.lastNodePosition)) {
            this.currentNodeMs = this.currentNodeMs + (Util.getMeasuringTimeMs() - this.lastActiveTickMs);
         } else {
            this.lastNodePosition = _snowman;
            double _snowmanx = currentPos.distanceTo(Vec3d.ofBottomCenter(this.lastNodePosition));
            this.currentNodeTimeout = this.entity.getMovementSpeed() > 0.0F ? _snowmanx / (double)this.entity.getMovementSpeed() * 1000.0 : 0.0;
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
         for (int _snowman = 0; _snowman < this.currentPath.getLength(); _snowman++) {
            PathNode _snowmanx = this.currentPath.getNode(_snowman);
            PathNode _snowmanxx = _snowman + 1 < this.currentPath.getLength() ? this.currentPath.getNode(_snowman + 1) : null;
            BlockState _snowmanxxx = this.world.getBlockState(new BlockPos(_snowmanx.x, _snowmanx.y, _snowmanx.z));
            if (_snowmanxxx.isOf(Blocks.CAULDRON)) {
               this.currentPath.setNode(_snowman, _snowmanx.copyWithNewPosition(_snowmanx.x, _snowmanx.y + 1, _snowmanx.z));
               if (_snowmanxx != null && _snowmanx.y >= _snowmanxx.y) {
                  this.currentPath.setNode(_snowman + 1, _snowmanx.copyWithNewPosition(_snowmanxx.x, _snowmanx.y + 1, _snowmanxx.z));
               }
            }
         }
      }
   }

   protected abstract boolean canPathDirectlyThrough(Vec3d origin, Vec3d target, int sizeX, int sizeY, int sizeZ);

   public boolean isValidPosition(BlockPos pos) {
      BlockPos _snowman = pos.down();
      return this.world.getBlockState(_snowman).isOpaqueFullCube(this.world, _snowman);
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
         PathNode _snowman = this.currentPath.getEnd();
         Vec3d _snowmanx = new Vec3d(((double)_snowman.x + this.entity.getX()) / 2.0, ((double)_snowman.y + this.entity.getY()) / 2.0, ((double)_snowman.z + this.entity.getZ()) / 2.0);
         if (pos.isWithinDistance(_snowmanx, (double)(this.currentPath.getLength() - this.currentPath.getCurrentNodeIndex()))) {
            this.recalculatePath();
         }
      }
   }

   public boolean isNearPathStartPos() {
      return this.nearPathStartPos;
   }
}
