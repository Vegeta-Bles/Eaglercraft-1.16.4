package net.minecraft.structure;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class IglooGenerator {
   private static final Identifier TOP_TEMPLATE = new Identifier("igloo/top");
   private static final Identifier MIDDLE_TEMPLATE = new Identifier("igloo/middle");
   private static final Identifier BOTTOM_TEMPLATE = new Identifier("igloo/bottom");
   private static final Map<Identifier, BlockPos> field_14408 = ImmutableMap.of(
      TOP_TEMPLATE, new BlockPos(3, 5, 5), MIDDLE_TEMPLATE, new BlockPos(1, 3, 1), BOTTOM_TEMPLATE, new BlockPos(3, 6, 7)
   );
   private static final Map<Identifier, BlockPos> field_14406 = ImmutableMap.of(
      TOP_TEMPLATE, BlockPos.ORIGIN, MIDDLE_TEMPLATE, new BlockPos(2, -3, 4), BOTTOM_TEMPLATE, new BlockPos(0, -3, -2)
   );

   public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random) {
      if (random.nextDouble() < 0.5) {
         int _snowman = random.nextInt(8) + 4;
         pieces.add(new IglooGenerator.Piece(manager, BOTTOM_TEMPLATE, pos, rotation, _snowman * 3));

         for (int _snowmanx = 0; _snowmanx < _snowman - 1; _snowmanx++) {
            pieces.add(new IglooGenerator.Piece(manager, MIDDLE_TEMPLATE, pos, rotation, _snowmanx * 3));
         }
      }

      pieces.add(new IglooGenerator.Piece(manager, TOP_TEMPLATE, pos, rotation, 0));
   }

   public static class Piece extends SimpleStructurePiece {
      private final Identifier template;
      private final BlockRotation rotation;

      public Piece(StructureManager manager, Identifier identifier, BlockPos pos, BlockRotation rotation, int yOffset) {
         super(StructurePieceType.IGLOO, 0);
         this.template = identifier;
         BlockPos _snowman = IglooGenerator.field_14406.get(identifier);
         this.pos = pos.add(_snowman.getX(), _snowman.getY() - yOffset, _snowman.getZ());
         this.rotation = rotation;
         this.initializeStructureData(manager);
      }

      public Piece(StructureManager manager, CompoundTag tag) {
         super(StructurePieceType.IGLOO, tag);
         this.template = new Identifier(tag.getString("Template"));
         this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
         this.initializeStructureData(manager);
      }

      private void initializeStructureData(StructureManager manager) {
         Structure _snowman = manager.getStructureOrBlank(this.template);
         StructurePlacementData _snowmanx = new StructurePlacementData()
            .setRotation(this.rotation)
            .setMirror(BlockMirror.NONE)
            .setPosition(IglooGenerator.field_14408.get(this.template))
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
         this.setStructureData(_snowman, this.pos, _snowmanx);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putString("Template", this.template.toString());
         tag.putString("Rot", this.rotation.name());
      }

      @Override
      protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess _snowman, Random random, BlockBox boundingBox) {
         if ("chest".equals(metadata)) {
            _snowman.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            BlockEntity _snowmanx = _snowman.getBlockEntity(pos.down());
            if (_snowmanx instanceof ChestBlockEntity) {
               ((ChestBlockEntity)_snowmanx).setLootTable(LootTables.IGLOO_CHEST_CHEST, random.nextLong());
            }
         }
      }

      @Override
      public boolean generate(
         StructureWorldAccess _snowman,
         StructureAccessor structureAccessor,
         ChunkGenerator chunkGenerator,
         Random random,
         BlockBox boundingBox,
         ChunkPos _snowman,
         BlockPos _snowman
      ) {
         StructurePlacementData _snowmanxxx = new StructurePlacementData()
            .setRotation(this.rotation)
            .setMirror(BlockMirror.NONE)
            .setPosition(IglooGenerator.field_14408.get(this.template))
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
         BlockPos _snowmanxxxx = IglooGenerator.field_14406.get(this.template);
         BlockPos _snowmanxxxxx = this.pos.add(Structure.transform(_snowmanxxx, new BlockPos(3 - _snowmanxxxx.getX(), 0, 0 - _snowmanxxxx.getZ())));
         int _snowmanxxxxxx = _snowman.getTopY(Heightmap.Type.WORLD_SURFACE_WG, _snowmanxxxxx.getX(), _snowmanxxxxx.getZ());
         BlockPos _snowmanxxxxxxx = this.pos;
         this.pos = this.pos.add(0, _snowmanxxxxxx - 90 - 1, 0);
         boolean _snowmanxxxxxxxx = super.generate(_snowman, structureAccessor, chunkGenerator, random, boundingBox, _snowman, _snowman);
         if (this.template.equals(IglooGenerator.TOP_TEMPLATE)) {
            BlockPos _snowmanxxxxxxxxx = this.pos.add(Structure.transform(_snowmanxxx, new BlockPos(3, 0, 5)));
            BlockState _snowmanxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxx.down());
            if (!_snowmanxxxxxxxxxx.isAir() && !_snowmanxxxxxxxxxx.isOf(Blocks.LADDER)) {
               _snowman.setBlockState(_snowmanxxxxxxxxx, Blocks.SNOW_BLOCK.getDefaultState(), 3);
            }
         }

         this.pos = _snowmanxxxxxxx;
         return _snowmanxxxxxxxx;
      }
   }
}
