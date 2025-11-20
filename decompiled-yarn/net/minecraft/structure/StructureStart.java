package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class StructureStart<C extends FeatureConfig> {
   public static final StructureStart<?> DEFAULT = new StructureStart<MineshaftFeatureConfig>(StructureFeature.MINESHAFT, 0, 0, BlockBox.empty(), 0, 0L) {
      public void init(DynamicRegistryManager _snowman, ChunkGenerator _snowman, StructureManager _snowman, int _snowman, int _snowman, Biome _snowman, MineshaftFeatureConfig _snowman) {
      }
   };
   private final StructureFeature<C> feature;
   protected final List<StructurePiece> children = Lists.newArrayList();
   protected BlockBox boundingBox;
   private final int chunkX;
   private final int chunkZ;
   private int references;
   protected final ChunkRandom random;

   public StructureStart(StructureFeature<C> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
      this.feature = feature;
      this.chunkX = chunkX;
      this.chunkZ = chunkZ;
      this.references = references;
      this.random = new ChunkRandom();
      this.random.setCarverSeed(seed, chunkX, chunkZ);
      this.boundingBox = box;
   }

   public abstract void init(
      DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, C config
   );

   public BlockBox getBoundingBox() {
      return this.boundingBox;
   }

   public List<StructurePiece> getChildren() {
      return this.children;
   }

   public void generateStructure(
      StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos
   ) {
      synchronized (this.children) {
         if (!this.children.isEmpty()) {
            BlockBox _snowman = this.children.get(0).boundingBox;
            Vec3i _snowmanx = _snowman.getCenter();
            BlockPos _snowmanxx = new BlockPos(_snowmanx.getX(), _snowman.minY, _snowmanx.getZ());
            Iterator<StructurePiece> _snowmanxxx = this.children.iterator();

            while (_snowmanxxx.hasNext()) {
               StructurePiece _snowmanxxxx = _snowmanxxx.next();
               if (_snowmanxxxx.getBoundingBox().intersects(box) && !_snowmanxxxx.generate(world, structureAccessor, chunkGenerator, random, box, chunkPos, _snowmanxx)) {
                  _snowmanxxx.remove();
               }
            }

            this.setBoundingBoxFromChildren();
         }
      }
   }

   protected void setBoundingBoxFromChildren() {
      this.boundingBox = BlockBox.empty();

      for (StructurePiece _snowman : this.children) {
         this.boundingBox.encompass(_snowman.getBoundingBox());
      }
   }

   public CompoundTag toTag(int chunkX, int chunkZ) {
      CompoundTag _snowman = new CompoundTag();
      if (this.hasChildren()) {
         _snowman.putString("id", Registry.STRUCTURE_FEATURE.getId(this.getFeature()).toString());
         _snowman.putInt("ChunkX", chunkX);
         _snowman.putInt("ChunkZ", chunkZ);
         _snowman.putInt("references", this.references);
         _snowman.put("BB", this.boundingBox.toNbt());
         ListTag var4 = new ListTag();
         synchronized (this.children) {
            for (StructurePiece _snowmanx : this.children) {
               var4.add(_snowmanx.getTag());
            }
         }

         _snowman.put("Children", var4);
         return _snowman;
      } else {
         _snowman.putString("id", "INVALID");
         return _snowman;
      }
   }

   protected void randomUpwardTranslation(int seaLevel, Random random, int minSeaLevelDistance) {
      int _snowman = seaLevel - minSeaLevelDistance;
      int _snowmanx = this.boundingBox.getBlockCountY() + 1;
      if (_snowmanx < _snowman) {
         _snowmanx += random.nextInt(_snowman - _snowmanx);
      }

      int _snowmanxx = _snowmanx - this.boundingBox.maxY;
      this.boundingBox.move(0, _snowmanxx, 0);

      for (StructurePiece _snowmanxxx : this.children) {
         _snowmanxxx.translate(0, _snowmanxx, 0);
      }
   }

   protected void randomUpwardTranslation(Random random, int minY, int maxY) {
      int _snowman = maxY - minY + 1 - this.boundingBox.getBlockCountY();
      int _snowmanx;
      if (_snowman > 1) {
         _snowmanx = minY + random.nextInt(_snowman);
      } else {
         _snowmanx = minY;
      }

      int _snowmanxx = _snowmanx - this.boundingBox.minY;
      this.boundingBox.move(0, _snowmanxx, 0);

      for (StructurePiece _snowmanxxx : this.children) {
         _snowmanxxx.translate(0, _snowmanxx, 0);
      }
   }

   public boolean hasChildren() {
      return !this.children.isEmpty();
   }

   public int getChunkX() {
      return this.chunkX;
   }

   public int getChunkZ() {
      return this.chunkZ;
   }

   public BlockPos getPos() {
      return new BlockPos(this.chunkX << 4, 0, this.chunkZ << 4);
   }

   public boolean isInExistingChunk() {
      return this.references < this.getReferenceCountToBeInExistingChunk();
   }

   public void incrementReferences() {
      this.references++;
   }

   public int getReferences() {
      return this.references;
   }

   protected int getReferenceCountToBeInExistingChunk() {
      return 1;
   }

   public StructureFeature<?> getFeature() {
      return this.feature;
   }
}
