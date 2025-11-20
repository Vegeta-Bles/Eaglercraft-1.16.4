/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
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
    public static final StructureStart<?> DEFAULT = new StructureStart<MineshaftFeatureConfig>(StructureFeature.MINESHAFT, 0, 0, BlockBox.empty(), 0, 0L){

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, MineshaftFeatureConfig mineshaftFeatureConfig) {
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

    public abstract void init(DynamicRegistryManager var1, ChunkGenerator var2, StructureManager var3, int var4, int var5, Biome var6, C var7);

    public BlockBox getBoundingBox() {
        return this.boundingBox;
    }

    public List<StructurePiece> getChildren() {
        return this.children;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void generateStructure(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos) {
        List<StructurePiece> list = this.children;
        synchronized (list) {
            if (this.children.isEmpty()) {
                return;
            }
            BlockBox blockBox = this.children.get((int)0).boundingBox;
            Vec3i _snowman2 = blockBox.getCenter();
            BlockPos _snowman3 = new BlockPos(_snowman2.getX(), blockBox.minY, _snowman2.getZ());
            Iterator<StructurePiece> _snowman4 = this.children.iterator();
            while (_snowman4.hasNext()) {
                StructurePiece structurePiece = _snowman4.next();
                if (!structurePiece.getBoundingBox().intersects(box) || structurePiece.generate(world, structureAccessor, chunkGenerator, random, box, chunkPos, _snowman3)) continue;
                _snowman4.remove();
            }
            this.setBoundingBoxFromChildren();
        }
    }

    protected void setBoundingBoxFromChildren() {
        this.boundingBox = BlockBox.empty();
        for (StructurePiece structurePiece : this.children) {
            this.boundingBox.encompass(structurePiece.getBoundingBox());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CompoundTag toTag(int chunkX, int chunkZ) {
        CompoundTag compoundTag = new CompoundTag();
        if (!this.hasChildren()) {
            compoundTag.putString("id", "INVALID");
            return compoundTag;
        }
        compoundTag.putString("id", Registry.STRUCTURE_FEATURE.getId(this.getFeature()).toString());
        compoundTag.putInt("ChunkX", chunkX);
        compoundTag.putInt("ChunkZ", chunkZ);
        compoundTag.putInt("references", this.references);
        compoundTag.put("BB", this.boundingBox.toNbt());
        ListTag _snowman2 = new ListTag();
        List<StructurePiece> list = this.children;
        synchronized (list) {
            for (StructurePiece structurePiece : this.children) {
                _snowman2.add(structurePiece.getTag());
            }
        }
        compoundTag.put("Children", _snowman2);
        return compoundTag;
    }

    protected void randomUpwardTranslation(int seaLevel, Random random, int minSeaLevelDistance) {
        int n = seaLevel - minSeaLevelDistance;
        _snowman = this.boundingBox.getBlockCountY() + 1;
        if (_snowman < n) {
            _snowman += random.nextInt(n - _snowman);
        }
        _snowman = _snowman - this.boundingBox.maxY;
        this.boundingBox.move(0, _snowman, 0);
        for (StructurePiece structurePiece : this.children) {
            structurePiece.translate(0, _snowman, 0);
        }
    }

    protected void randomUpwardTranslation(Random random, int minY, int maxY) {
        int n = maxY - minY + 1 - this.boundingBox.getBlockCountY();
        _snowman = n > 1 ? minY + random.nextInt(n) : minY;
        _snowman = _snowman - this.boundingBox.minY;
        this.boundingBox.move(0, _snowman, 0);
        for (StructurePiece structurePiece : this.children) {
            structurePiece.translate(0, _snowman, 0);
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
        ++this.references;
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

