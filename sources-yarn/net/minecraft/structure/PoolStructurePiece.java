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

   public PoolStructurePiece(StructureManager arg, StructurePoolElement arg2, BlockPos arg3, int i, BlockRotation arg4, BlockBox arg5) {
      super(StructurePieceType.JIGSAW, 0);
      this.structureManager = arg;
      this.poolElement = arg2;
      this.pos = arg3;
      this.groundLevelDelta = i;
      this.rotation = arg4;
      this.boundingBox = arg5;
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
      ListTag lv = tag.getList("junctions", 10);
      this.junctions.clear();
      lv.forEach(arg -> this.junctions.add(JigsawJunction.method_28873(new Dynamic(NbtOps.INSTANCE, arg))));
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
         .ifPresent(arg2 -> tag.put("pool_element", arg2));
      tag.putString("rotation", this.rotation.name());
      ListTag lv = new ListTag();

      for (JigsawJunction lv2 : this.junctions) {
         lv.add((Tag)lv2.serialize(NbtOps.INSTANCE).getValue());
      }

      tag.put("junctions", lv);
   }

   @Override
   public boolean generate(
      StructureWorldAccess arg,
      StructureAccessor structureAccessor,
      ChunkGenerator chunkGenerator,
      Random random,
      BlockBox boundingBox,
      ChunkPos arg5,
      BlockPos arg6
   ) {
      return this.method_27236(arg, structureAccessor, chunkGenerator, random, boundingBox, arg6, false);
   }

   public boolean method_27236(
      StructureWorldAccess arg, StructureAccessor arg2, ChunkGenerator arg3, Random random, BlockBox arg4, BlockPos arg5, boolean keepJigsaws
   ) {
      return this.poolElement.generate(this.structureManager, arg, arg2, arg3, this.pos, arg5, this.rotation, arg4, random, keepJigsaws);
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
