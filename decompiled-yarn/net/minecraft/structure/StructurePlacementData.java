package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

public class StructurePlacementData {
   private BlockMirror mirror = BlockMirror.NONE;
   private BlockRotation rotation = BlockRotation.NONE;
   private BlockPos position = BlockPos.ORIGIN;
   private boolean ignoreEntities;
   @Nullable
   private ChunkPos chunkPosition;
   @Nullable
   private BlockBox boundingBox;
   private boolean placeFluids = true;
   @Nullable
   private Random random;
   @Nullable
   private int field_15575;
   private final List<StructureProcessor> processors = Lists.newArrayList();
   private boolean updateNeighbors;
   private boolean field_24043;

   public StructurePlacementData() {
   }

   public StructurePlacementData copy() {
      StructurePlacementData _snowman = new StructurePlacementData();
      _snowman.mirror = this.mirror;
      _snowman.rotation = this.rotation;
      _snowman.position = this.position;
      _snowman.ignoreEntities = this.ignoreEntities;
      _snowman.chunkPosition = this.chunkPosition;
      _snowman.boundingBox = this.boundingBox;
      _snowman.placeFluids = this.placeFluids;
      _snowman.random = this.random;
      _snowman.field_15575 = this.field_15575;
      _snowman.processors.addAll(this.processors);
      _snowman.updateNeighbors = this.updateNeighbors;
      _snowman.field_24043 = this.field_24043;
      return _snowman;
   }

   public StructurePlacementData setMirror(BlockMirror mirror) {
      this.mirror = mirror;
      return this;
   }

   public StructurePlacementData setRotation(BlockRotation rotation) {
      this.rotation = rotation;
      return this;
   }

   public StructurePlacementData setPosition(BlockPos position) {
      this.position = position;
      return this;
   }

   public StructurePlacementData setIgnoreEntities(boolean ignoreEntities) {
      this.ignoreEntities = ignoreEntities;
      return this;
   }

   public StructurePlacementData setChunkPosition(ChunkPos chunkPosition) {
      this.chunkPosition = chunkPosition;
      return this;
   }

   public StructurePlacementData setBoundingBox(BlockBox boundingBox) {
      this.boundingBox = boundingBox;
      return this;
   }

   public StructurePlacementData setRandom(@Nullable Random random) {
      this.random = random;
      return this;
   }

   public StructurePlacementData setUpdateNeighbors(boolean updateNeighbors) {
      this.updateNeighbors = updateNeighbors;
      return this;
   }

   public StructurePlacementData clearProcessors() {
      this.processors.clear();
      return this;
   }

   public StructurePlacementData addProcessor(StructureProcessor processor) {
      this.processors.add(processor);
      return this;
   }

   public StructurePlacementData removeProcessor(StructureProcessor processor) {
      this.processors.remove(processor);
      return this;
   }

   public BlockMirror getMirror() {
      return this.mirror;
   }

   public BlockRotation getRotation() {
      return this.rotation;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public Random getRandom(@Nullable BlockPos pos) {
      if (this.random != null) {
         return this.random;
      } else {
         return pos == null ? new Random(Util.getMeasuringTimeMs()) : new Random(MathHelper.hashCode(pos));
      }
   }

   public boolean shouldIgnoreEntities() {
      return this.ignoreEntities;
   }

   @Nullable
   public BlockBox getBoundingBox() {
      if (this.boundingBox == null && this.chunkPosition != null) {
         this.calculateBoundingBox();
      }

      return this.boundingBox;
   }

   public boolean shouldUpdateNeighbors() {
      return this.updateNeighbors;
   }

   public List<StructureProcessor> getProcessors() {
      return this.processors;
   }

   void calculateBoundingBox() {
      if (this.chunkPosition != null) {
         this.boundingBox = this.getChunkBlockBox(this.chunkPosition);
      }
   }

   public boolean shouldPlaceFluids() {
      return this.placeFluids;
   }

   public Structure.PalettedBlockInfoList getRandomBlockInfos(List<Structure.PalettedBlockInfoList> _snowman, @Nullable BlockPos pos) {
      int _snowmanx = _snowman.size();
      if (_snowmanx == 0) {
         throw new IllegalStateException("No palettes");
      } else {
         return _snowman.get(this.getRandom(pos).nextInt(_snowmanx));
      }
   }

   @Nullable
   private BlockBox getChunkBlockBox(@Nullable ChunkPos pos) {
      if (pos == null) {
         return this.boundingBox;
      } else {
         int _snowman = pos.x * 16;
         int _snowmanx = pos.z * 16;
         return new BlockBox(_snowman, 0, _snowmanx, _snowman + 16 - 1, 255, _snowmanx + 16 - 1);
      }
   }

   public StructurePlacementData method_27264(boolean _snowman) {
      this.field_24043 = _snowman;
      return this;
   }

   public boolean method_27265() {
      return this.field_24043;
   }
}
