package net.minecraft.world.border;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class WorldBorder {
   private final List<WorldBorderListener> listeners = Lists.newArrayList();
   private double damagePerBlock = 0.2;
   private double buffer = 5.0;
   private int warningTime = 15;
   private int warningBlocks = 5;
   private double centerX;
   private double centerZ;
   private int maxWorldBorderRadius = 29999984;
   private WorldBorder.Area area = new WorldBorder.StaticArea(6.0E7);
   public static final WorldBorder.Properties DEFAULT_BORDER = new WorldBorder.Properties(0.0, 0.0, 0.2, 5.0, 5, 15, 6.0E7, 0L, 0.0);

   public WorldBorder() {
   }

   public boolean contains(BlockPos pos) {
      return (double)(pos.getX() + 1) > this.getBoundWest()
         && (double)pos.getX() < this.getBoundEast()
         && (double)(pos.getZ() + 1) > this.getBoundNorth()
         && (double)pos.getZ() < this.getBoundSouth();
   }

   public boolean contains(ChunkPos pos) {
      return (double)pos.getEndX() > this.getBoundWest()
         && (double)pos.getStartX() < this.getBoundEast()
         && (double)pos.getEndZ() > this.getBoundNorth()
         && (double)pos.getStartZ() < this.getBoundSouth();
   }

   public boolean contains(Box box) {
      return box.maxX > this.getBoundWest() && box.minX < this.getBoundEast() && box.maxZ > this.getBoundNorth() && box.minZ < this.getBoundSouth();
   }

   public double getDistanceInsideBorder(Entity entity) {
      return this.getDistanceInsideBorder(entity.getX(), entity.getZ());
   }

   public VoxelShape asVoxelShape() {
      return this.area.asVoxelShape();
   }

   public double getDistanceInsideBorder(double x, double z) {
      double _snowman = z - this.getBoundNorth();
      double _snowmanx = this.getBoundSouth() - z;
      double _snowmanxx = x - this.getBoundWest();
      double _snowmanxxx = this.getBoundEast() - x;
      double _snowmanxxxx = Math.min(_snowmanxx, _snowmanxxx);
      _snowmanxxxx = Math.min(_snowmanxxxx, _snowman);
      return Math.min(_snowmanxxxx, _snowmanx);
   }

   public WorldBorderStage getStage() {
      return this.area.getStage();
   }

   public double getBoundWest() {
      return this.area.getBoundWest();
   }

   public double getBoundNorth() {
      return this.area.getBoundNorth();
   }

   public double getBoundEast() {
      return this.area.getBoundEast();
   }

   public double getBoundSouth() {
      return this.area.getBoundSouth();
   }

   public double getCenterX() {
      return this.centerX;
   }

   public double getCenterZ() {
      return this.centerZ;
   }

   public void setCenter(double x, double z) {
      this.centerX = x;
      this.centerZ = z;
      this.area.onCenterChanged();

      for (WorldBorderListener _snowman : this.getListeners()) {
         _snowman.onCenterChanged(this, x, z);
      }
   }

   public double getSize() {
      return this.area.getSize();
   }

   public long getTargetRemainingTime() {
      return this.area.getTargetRemainingTime();
   }

   public double getTargetSize() {
      return this.area.getTargetSize();
   }

   public void setSize(double size) {
      this.area = new WorldBorder.StaticArea(size);

      for (WorldBorderListener _snowman : this.getListeners()) {
         _snowman.onSizeChange(this, size);
      }
   }

   public void interpolateSize(double fromSize, double toSize, long time) {
      this.area = (WorldBorder.Area)(fromSize == toSize ? new WorldBorder.StaticArea(toSize) : new WorldBorder.MovingArea(fromSize, toSize, time));

      for (WorldBorderListener _snowman : this.getListeners()) {
         _snowman.onInterpolateSize(this, fromSize, toSize, time);
      }
   }

   protected List<WorldBorderListener> getListeners() {
      return Lists.newArrayList(this.listeners);
   }

   public void addListener(WorldBorderListener listener) {
      this.listeners.add(listener);
   }

   public void setMaxWorldBorderRadius(int radius) {
      this.maxWorldBorderRadius = radius;
      this.area.onMaxWorldBorderRadiusChanged();
   }

   public int getMaxWorldBorderRadius() {
      return this.maxWorldBorderRadius;
   }

   public double getBuffer() {
      return this.buffer;
   }

   public void setBuffer(double buffer) {
      this.buffer = buffer;

      for (WorldBorderListener _snowman : this.getListeners()) {
         _snowman.onSafeZoneChanged(this, buffer);
      }
   }

   public double getDamagePerBlock() {
      return this.damagePerBlock;
   }

   public void setDamagePerBlock(double damagePerBlock) {
      this.damagePerBlock = damagePerBlock;

      for (WorldBorderListener _snowman : this.getListeners()) {
         _snowman.onDamagePerBlockChanged(this, damagePerBlock);
      }
   }

   public double getShrinkingSpeed() {
      return this.area.getShrinkingSpeed();
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public void setWarningTime(int warningTime) {
      this.warningTime = warningTime;

      for (WorldBorderListener _snowman : this.getListeners()) {
         _snowman.onWarningTimeChanged(this, warningTime);
      }
   }

   public int getWarningBlocks() {
      return this.warningBlocks;
   }

   public void setWarningBlocks(int warningBlocks) {
      this.warningBlocks = warningBlocks;

      for (WorldBorderListener _snowman : this.getListeners()) {
         _snowman.onWarningBlocksChanged(this, warningBlocks);
      }
   }

   public void tick() {
      this.area = this.area.getAreaInstance();
   }

   public WorldBorder.Properties write() {
      return new WorldBorder.Properties(this);
   }

   public void load(WorldBorder.Properties properties) {
      this.setCenter(properties.getCenterX(), properties.getCenterZ());
      this.setDamagePerBlock(properties.getDamagePerBlock());
      this.setBuffer(properties.getBuffer());
      this.setWarningBlocks(properties.getWarningBlocks());
      this.setWarningTime(properties.getWarningTime());
      if (properties.getTargetRemainingTime() > 0L) {
         this.interpolateSize(properties.getSize(), properties.getTargetSize(), properties.getTargetRemainingTime());
      } else {
         this.setSize(properties.getSize());
      }
   }

   interface Area {
      double getBoundWest();

      double getBoundEast();

      double getBoundNorth();

      double getBoundSouth();

      double getSize();

      double getShrinkingSpeed();

      long getTargetRemainingTime();

      double getTargetSize();

      WorldBorderStage getStage();

      void onMaxWorldBorderRadiusChanged();

      void onCenterChanged();

      WorldBorder.Area getAreaInstance();

      VoxelShape asVoxelShape();
   }

   class MovingArea implements WorldBorder.Area {
      private final double oldSize;
      private final double newSize;
      private final long timeEnd;
      private final long timeStart;
      private final double timeDuration;

      private MovingArea(double oldSize, double newSize, long duration) {
         this.oldSize = oldSize;
         this.newSize = newSize;
         this.timeDuration = (double)duration;
         this.timeStart = Util.getMeasuringTimeMs();
         this.timeEnd = this.timeStart + duration;
      }

      @Override
      public double getBoundWest() {
         return Math.max(WorldBorder.this.getCenterX() - this.getSize() / 2.0, (double)(-WorldBorder.this.maxWorldBorderRadius));
      }

      @Override
      public double getBoundNorth() {
         return Math.max(WorldBorder.this.getCenterZ() - this.getSize() / 2.0, (double)(-WorldBorder.this.maxWorldBorderRadius));
      }

      @Override
      public double getBoundEast() {
         return Math.min(WorldBorder.this.getCenterX() + this.getSize() / 2.0, (double)WorldBorder.this.maxWorldBorderRadius);
      }

      @Override
      public double getBoundSouth() {
         return Math.min(WorldBorder.this.getCenterZ() + this.getSize() / 2.0, (double)WorldBorder.this.maxWorldBorderRadius);
      }

      @Override
      public double getSize() {
         double _snowman = (double)(Util.getMeasuringTimeMs() - this.timeStart) / this.timeDuration;
         return _snowman < 1.0 ? MathHelper.lerp(_snowman, this.oldSize, this.newSize) : this.newSize;
      }

      @Override
      public double getShrinkingSpeed() {
         return Math.abs(this.oldSize - this.newSize) / (double)(this.timeEnd - this.timeStart);
      }

      @Override
      public long getTargetRemainingTime() {
         return this.timeEnd - Util.getMeasuringTimeMs();
      }

      @Override
      public double getTargetSize() {
         return this.newSize;
      }

      @Override
      public WorldBorderStage getStage() {
         return this.newSize < this.oldSize ? WorldBorderStage.SHRINKING : WorldBorderStage.GROWING;
      }

      @Override
      public void onCenterChanged() {
      }

      @Override
      public void onMaxWorldBorderRadiusChanged() {
      }

      @Override
      public WorldBorder.Area getAreaInstance() {
         return (WorldBorder.Area)(this.getTargetRemainingTime() <= 0L ? WorldBorder.this.new StaticArea(this.newSize) : this);
      }

      @Override
      public VoxelShape asVoxelShape() {
         return VoxelShapes.combineAndSimplify(
            VoxelShapes.UNBOUNDED,
            VoxelShapes.cuboid(
               Math.floor(this.getBoundWest()),
               Double.NEGATIVE_INFINITY,
               Math.floor(this.getBoundNorth()),
               Math.ceil(this.getBoundEast()),
               Double.POSITIVE_INFINITY,
               Math.ceil(this.getBoundSouth())
            ),
            BooleanBiFunction.ONLY_FIRST
         );
      }
   }

   public static class Properties {
      private final double centerX;
      private final double centerZ;
      private final double damagePerBlock;
      private final double buffer;
      private final int warningBlocks;
      private final int warningTime;
      private final double size;
      private final long targetRemainingTime;
      private final double targetSize;

      private Properties(
         double centerX,
         double centerZ,
         double damagePerBlock,
         double buffer,
         int warningBlocks,
         int warningTime,
         double size,
         long targetRemainingTime,
         double targetSize
      ) {
         this.centerX = centerX;
         this.centerZ = centerZ;
         this.damagePerBlock = damagePerBlock;
         this.buffer = buffer;
         this.warningBlocks = warningBlocks;
         this.warningTime = warningTime;
         this.size = size;
         this.targetRemainingTime = targetRemainingTime;
         this.targetSize = targetSize;
      }

      private Properties(WorldBorder worldBorder) {
         this.centerX = worldBorder.getCenterX();
         this.centerZ = worldBorder.getCenterZ();
         this.damagePerBlock = worldBorder.getDamagePerBlock();
         this.buffer = worldBorder.getBuffer();
         this.warningBlocks = worldBorder.getWarningBlocks();
         this.warningTime = worldBorder.getWarningTime();
         this.size = worldBorder.getSize();
         this.targetRemainingTime = worldBorder.getTargetRemainingTime();
         this.targetSize = worldBorder.getTargetSize();
      }

      public double getCenterX() {
         return this.centerX;
      }

      public double getCenterZ() {
         return this.centerZ;
      }

      public double getDamagePerBlock() {
         return this.damagePerBlock;
      }

      public double getBuffer() {
         return this.buffer;
      }

      public int getWarningBlocks() {
         return this.warningBlocks;
      }

      public int getWarningTime() {
         return this.warningTime;
      }

      public double getSize() {
         return this.size;
      }

      public long getTargetRemainingTime() {
         return this.targetRemainingTime;
      }

      public double getTargetSize() {
         return this.targetSize;
      }

      public static WorldBorder.Properties fromDynamic(DynamicLike<?> _snowman, WorldBorder.Properties _snowman) {
         double _snowmanxx = _snowman.get("BorderCenterX").asDouble(_snowman.centerX);
         double _snowmanxxx = _snowman.get("BorderCenterZ").asDouble(_snowman.centerZ);
         double _snowmanxxxx = _snowman.get("BorderSize").asDouble(_snowman.size);
         long _snowmanxxxxx = _snowman.get("BorderSizeLerpTime").asLong(_snowman.targetRemainingTime);
         double _snowmanxxxxxx = _snowman.get("BorderSizeLerpTarget").asDouble(_snowman.targetSize);
         double _snowmanxxxxxxx = _snowman.get("BorderSafeZone").asDouble(_snowman.buffer);
         double _snowmanxxxxxxxx = _snowman.get("BorderDamagePerBlock").asDouble(_snowman.damagePerBlock);
         int _snowmanxxxxxxxxx = _snowman.get("BorderWarningBlocks").asInt(_snowman.warningBlocks);
         int _snowmanxxxxxxxxxx = _snowman.get("BorderWarningTime").asInt(_snowman.warningTime);
         return new WorldBorder.Properties(_snowmanxx, _snowmanxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      }

      public void toTag(CompoundTag tag) {
         tag.putDouble("BorderCenterX", this.centerX);
         tag.putDouble("BorderCenterZ", this.centerZ);
         tag.putDouble("BorderSize", this.size);
         tag.putLong("BorderSizeLerpTime", this.targetRemainingTime);
         tag.putDouble("BorderSafeZone", this.buffer);
         tag.putDouble("BorderDamagePerBlock", this.damagePerBlock);
         tag.putDouble("BorderSizeLerpTarget", this.targetSize);
         tag.putDouble("BorderWarningBlocks", (double)this.warningBlocks);
         tag.putDouble("BorderWarningTime", (double)this.warningTime);
      }
   }

   class StaticArea implements WorldBorder.Area {
      private final double size;
      private double boundWest;
      private double boundNorth;
      private double boundEast;
      private double boundSouth;
      private VoxelShape shape;

      public StaticArea(double _snowman) {
         this.size = _snowman;
         this.recalculateBounds();
      }

      @Override
      public double getBoundWest() {
         return this.boundWest;
      }

      @Override
      public double getBoundEast() {
         return this.boundEast;
      }

      @Override
      public double getBoundNorth() {
         return this.boundNorth;
      }

      @Override
      public double getBoundSouth() {
         return this.boundSouth;
      }

      @Override
      public double getSize() {
         return this.size;
      }

      @Override
      public WorldBorderStage getStage() {
         return WorldBorderStage.STATIONARY;
      }

      @Override
      public double getShrinkingSpeed() {
         return 0.0;
      }

      @Override
      public long getTargetRemainingTime() {
         return 0L;
      }

      @Override
      public double getTargetSize() {
         return this.size;
      }

      private void recalculateBounds() {
         this.boundWest = Math.max(WorldBorder.this.getCenterX() - this.size / 2.0, (double)(-WorldBorder.this.maxWorldBorderRadius));
         this.boundNorth = Math.max(WorldBorder.this.getCenterZ() - this.size / 2.0, (double)(-WorldBorder.this.maxWorldBorderRadius));
         this.boundEast = Math.min(WorldBorder.this.getCenterX() + this.size / 2.0, (double)WorldBorder.this.maxWorldBorderRadius);
         this.boundSouth = Math.min(WorldBorder.this.getCenterZ() + this.size / 2.0, (double)WorldBorder.this.maxWorldBorderRadius);
         this.shape = VoxelShapes.combineAndSimplify(
            VoxelShapes.UNBOUNDED,
            VoxelShapes.cuboid(
               Math.floor(this.getBoundWest()),
               Double.NEGATIVE_INFINITY,
               Math.floor(this.getBoundNorth()),
               Math.ceil(this.getBoundEast()),
               Double.POSITIVE_INFINITY,
               Math.ceil(this.getBoundSouth())
            ),
            BooleanBiFunction.ONLY_FIRST
         );
      }

      @Override
      public void onMaxWorldBorderRadiusChanged() {
         this.recalculateBounds();
      }

      @Override
      public void onCenterChanged() {
         this.recalculateBounds();
      }

      @Override
      public WorldBorder.Area getAreaInstance() {
         return this;
      }

      @Override
      public VoxelShape asVoxelShape() {
         return this.shape;
      }
   }
}
