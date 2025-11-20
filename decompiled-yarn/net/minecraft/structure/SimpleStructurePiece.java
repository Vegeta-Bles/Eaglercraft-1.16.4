package net.minecraft.structure;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SimpleStructurePiece extends StructurePiece {
   private static final Logger LOGGER = LogManager.getLogger();
   protected Structure structure;
   protected StructurePlacementData placementData;
   protected BlockPos pos;

   public SimpleStructurePiece(StructurePieceType _snowman, int _snowman) {
      super(_snowman, _snowman);
   }

   public SimpleStructurePiece(StructurePieceType _snowman, CompoundTag _snowman) {
      super(_snowman, _snowman);
      this.pos = new BlockPos(_snowman.getInt("TPX"), _snowman.getInt("TPY"), _snowman.getInt("TPZ"));
   }

   protected void setStructureData(Structure structure, BlockPos pos, StructurePlacementData placementData) {
      this.structure = structure;
      this.setOrientation(Direction.NORTH);
      this.pos = pos;
      this.placementData = placementData;
      this.boundingBox = structure.calculateBoundingBox(placementData, pos);
   }

   @Override
   protected void toNbt(CompoundTag tag) {
      tag.putInt("TPX", this.pos.getX());
      tag.putInt("TPY", this.pos.getY());
      tag.putInt("TPZ", this.pos.getZ());
   }

   @Override
   public boolean generate(
      StructureWorldAccess _snowman, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos _snowman, BlockPos _snowman
   ) {
      this.placementData.setBoundingBox(boundingBox);
      this.boundingBox = this.structure.calculateBoundingBox(this.placementData, this.pos);
      if (this.structure.place(_snowman, this.pos, _snowman, this.placementData, random, 2)) {
         for (Structure.StructureBlockInfo _snowmanxxx : this.structure.getInfosForBlock(this.pos, this.placementData, Blocks.STRUCTURE_BLOCK)) {
            if (_snowmanxxx.tag != null) {
               StructureBlockMode _snowmanxxxx = StructureBlockMode.valueOf(_snowmanxxx.tag.getString("mode"));
               if (_snowmanxxxx == StructureBlockMode.DATA) {
                  this.handleMetadata(_snowmanxxx.tag.getString("metadata"), _snowmanxxx.pos, _snowman, random, boundingBox);
               }
            }
         }

         for (Structure.StructureBlockInfo _snowmanxxxx : this.structure.getInfosForBlock(this.pos, this.placementData, Blocks.JIGSAW)) {
            if (_snowmanxxxx.tag != null) {
               String _snowmanxxxxx = _snowmanxxxx.tag.getString("final_state");
               BlockArgumentParser _snowmanxxxxxx = new BlockArgumentParser(new StringReader(_snowmanxxxxx), false);
               BlockState _snowmanxxxxxxx = Blocks.AIR.getDefaultState();

               try {
                  _snowmanxxxxxx.parse(true);
                  BlockState _snowmanxxxxxxxx = _snowmanxxxxxx.getBlockState();
                  if (_snowmanxxxxxxxx != null) {
                     _snowmanxxxxxxx = _snowmanxxxxxxxx;
                  } else {
                     LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", _snowmanxxxxx, _snowmanxxxx.pos);
                  }
               } catch (CommandSyntaxException var16) {
                  LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", _snowmanxxxxx, _snowmanxxxx.pos);
               }

               _snowman.setBlockState(_snowmanxxxx.pos, _snowmanxxxxxxx, 3);
            }
         }
      }

      return true;
   }

   protected abstract void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess var3, Random random, BlockBox boundingBox);

   @Override
   public void translate(int x, int y, int z) {
      super.translate(x, y, z);
      this.pos = this.pos.add(x, y, z);
   }

   @Override
   public BlockRotation getRotation() {
      return this.placementData.getRotation();
   }
}
