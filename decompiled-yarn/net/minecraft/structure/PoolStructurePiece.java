package net.minecraft.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.structure.pool.EmptyPoolElement;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoolStructurePiece extends StructurePiece {
   private static final Logger field_24991 = LogManager.getLogger();
   protected final StructurePoolElement poolElement;
   protected BlockPos pos;
   private final int groundLevelDelta;
   protected final BlockRotation rotation;
   private final List<JigsawJunction> junctions = Lists.newArrayList();
   private final StructureManager structureManager;

   public PoolStructurePiece(StructureManager _snowman, StructurePoolElement _snowman, BlockPos _snowman, int _snowman, BlockRotation _snowman, BlockBox _snowman) {
      super(StructurePieceType.JIGSAW, 0);
      this.structureManager = _snowman;
      this.poolElement = _snowman;
      this.pos = _snowman;
      this.groundLevelDelta = _snowman;
      this.rotation = _snowman;
      this.boundingBox = _snowman;
   }

   public PoolStructurePiece(StructureManager manager, CompoundTag tag) {
      super(StructurePieceType.JIGSAW, tag);
      this.structureManager = manager;
      this.pos = new BlockPos(tag.getInt("PosX"), tag.getInt("PosY"), tag.getInt("PosZ"));
      this.groundLevelDelta = tag.getInt("ground_level_delta");
      this.poolElement = StructurePoolElement.CODEC
         .parse(NbtOps.INSTANCE, tag.getCompound("pool_element"))
         .resultOrPartial(field_24991::error)
         .orElse(EmptyPoolElement.INSTANCE);
      this.rotation = BlockRotation.valueOf(tag.getString("rotation"));
      this.boundingBox = this.poolElement.getBoundingBox(manager, this.pos, this.rotation);
      ListTag _snowman = tag.getList("junctions", 10);
      this.junctions.clear();
      _snowman.forEach(_snowmanx -> this.junctions.add(JigsawJunction.method_28873(new Dynamic(NbtOps.INSTANCE, _snowmanx))));
   }

   @Override
   protected void toNbt(CompoundTag tag) {
      tag.putInt("PosX", this.pos.getX());
      tag.putInt("PosY", this.pos.getY());
      tag.putInt("PosZ", this.pos.getZ());
      tag.putInt("ground_level_delta", this.groundLevelDelta);
      StructurePoolElement.CODEC
         .encodeStart(NbtOps.INSTANCE, this.poolElement)
         .resultOrPartial(field_24991::error)
         .ifPresent(_snowmanx -> tag.put("pool_element", _snowmanx));
      tag.putString("rotation", this.rotation.name());
      ListTag _snowman = new ListTag();

      for (JigsawJunction _snowmanx : this.junctions) {
         _snowman.add((Tag)_snowmanx.serialize(NbtOps.INSTANCE).getValue());
      }

      tag.put("junctions", _snowman);
   }

   @Override
   public boolean generate(
      StructureWorldAccess _snowman, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos _snowman, BlockPos _snowman
   ) {
      return this.method_27236(_snowman, structureAccessor, chunkGenerator, random, boundingBox, _snowman, false);
   }

   public boolean method_27236(StructureWorldAccess _snowman, StructureAccessor _snowman, ChunkGenerator _snowman, Random _snowman, BlockBox _snowman, BlockPos _snowman, boolean keepJigsaws) {
      return this.poolElement.generate(this.structureManager, _snowman, _snowman, _snowman, this.pos, _snowman, this.rotation, _snowman, _snowman, keepJigsaws);
   }

   @Override
   public void translate(int x, int y, int z) {
      super.translate(x, y, z);
      this.pos = this.pos.add(x, y, z);
   }

   @Override
   public BlockRotation getRotation() {
      return this.rotation;
   }

   @Override
   public String toString() {
      return String.format("<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.pos, this.rotation, this.poolElement);
   }

   public StructurePoolElement getPoolElement() {
      return this.poolElement;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public int getGroundLevelDelta() {
      return this.groundLevelDelta;
   }

   public void addJunction(JigsawJunction junction) {
      this.junctions.add(junction);
   }

   public List<JigsawJunction> getJunctions() {
      return this.junctions;
   }
}
