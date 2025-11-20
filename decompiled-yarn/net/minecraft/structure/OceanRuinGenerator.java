package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.OceanRuinFeature;
import net.minecraft.world.gen.feature.OceanRuinFeatureConfig;

public class OceanRuinGenerator {
   private static final Identifier[] WARM_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/warm_1"),
      new Identifier("underwater_ruin/warm_2"),
      new Identifier("underwater_ruin/warm_3"),
      new Identifier("underwater_ruin/warm_4"),
      new Identifier("underwater_ruin/warm_5"),
      new Identifier("underwater_ruin/warm_6"),
      new Identifier("underwater_ruin/warm_7"),
      new Identifier("underwater_ruin/warm_8")
   };
   private static final Identifier[] BRICK_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/brick_1"),
      new Identifier("underwater_ruin/brick_2"),
      new Identifier("underwater_ruin/brick_3"),
      new Identifier("underwater_ruin/brick_4"),
      new Identifier("underwater_ruin/brick_5"),
      new Identifier("underwater_ruin/brick_6"),
      new Identifier("underwater_ruin/brick_7"),
      new Identifier("underwater_ruin/brick_8")
   };
   private static final Identifier[] CRACKED_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/cracked_1"),
      new Identifier("underwater_ruin/cracked_2"),
      new Identifier("underwater_ruin/cracked_3"),
      new Identifier("underwater_ruin/cracked_4"),
      new Identifier("underwater_ruin/cracked_5"),
      new Identifier("underwater_ruin/cracked_6"),
      new Identifier("underwater_ruin/cracked_7"),
      new Identifier("underwater_ruin/cracked_8")
   };
   private static final Identifier[] MOSSY_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/mossy_1"),
      new Identifier("underwater_ruin/mossy_2"),
      new Identifier("underwater_ruin/mossy_3"),
      new Identifier("underwater_ruin/mossy_4"),
      new Identifier("underwater_ruin/mossy_5"),
      new Identifier("underwater_ruin/mossy_6"),
      new Identifier("underwater_ruin/mossy_7"),
      new Identifier("underwater_ruin/mossy_8")
   };
   private static final Identifier[] BIG_BRICK_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/big_brick_1"),
      new Identifier("underwater_ruin/big_brick_2"),
      new Identifier("underwater_ruin/big_brick_3"),
      new Identifier("underwater_ruin/big_brick_8")
   };
   private static final Identifier[] BIG_MOSSY_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/big_mossy_1"),
      new Identifier("underwater_ruin/big_mossy_2"),
      new Identifier("underwater_ruin/big_mossy_3"),
      new Identifier("underwater_ruin/big_mossy_8")
   };
   private static final Identifier[] BIG_CRACKED_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/big_cracked_1"),
      new Identifier("underwater_ruin/big_cracked_2"),
      new Identifier("underwater_ruin/big_cracked_3"),
      new Identifier("underwater_ruin/big_cracked_8")
   };
   private static final Identifier[] BIG_WARM_RUINS = new Identifier[]{
      new Identifier("underwater_ruin/big_warm_4"),
      new Identifier("underwater_ruin/big_warm_5"),
      new Identifier("underwater_ruin/big_warm_6"),
      new Identifier("underwater_ruin/big_warm_7")
   };

   private static Identifier getRandomWarmRuin(Random random) {
      return Util.getRandom(WARM_RUINS, random);
   }

   private static Identifier getRandomBigWarmRuin(Random random) {
      return Util.getRandom(BIG_WARM_RUINS, random);
   }

   public static void addPieces(
      StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random, OceanRuinFeatureConfig config
   ) {
      boolean _snowman = random.nextFloat() <= config.largeProbability;
      float _snowmanx = _snowman ? 0.9F : 0.8F;
      method_14822(manager, pos, rotation, pieces, random, config, _snowman, _snowmanx);
      if (_snowman && random.nextFloat() <= config.clusterProbability) {
         method_14825(manager, random, rotation, pos, config, pieces);
      }
   }

   private static void method_14825(
      StructureManager manager, Random random, BlockRotation rotation, BlockPos pos, OceanRuinFeatureConfig config, List<StructurePiece> pieces
   ) {
      int _snowman = pos.getX();
      int _snowmanx = pos.getZ();
      BlockPos _snowmanxx = Structure.transformAround(new BlockPos(15, 0, 15), BlockMirror.NONE, rotation, BlockPos.ORIGIN).add(_snowman, 0, _snowmanx);
      BlockBox _snowmanxxx = BlockBox.create(_snowman, 0, _snowmanx, _snowmanxx.getX(), 0, _snowmanxx.getZ());
      BlockPos _snowmanxxxx = new BlockPos(Math.min(_snowman, _snowmanxx.getX()), 0, Math.min(_snowmanx, _snowmanxx.getZ()));
      List<BlockPos> _snowmanxxxxx = getRoomPositions(random, _snowmanxxxx.getX(), _snowmanxxxx.getZ());
      int _snowmanxxxxxx = MathHelper.nextInt(random, 4, 8);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
         if (!_snowmanxxxxx.isEmpty()) {
            int _snowmanxxxxxxxx = random.nextInt(_snowmanxxxxx.size());
            BlockPos _snowmanxxxxxxxxx = _snowmanxxxxx.remove(_snowmanxxxxxxxx);
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getX();
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.getZ();
            BlockRotation _snowmanxxxxxxxxxxxx = BlockRotation.random(random);
            BlockPos _snowmanxxxxxxxxxxxxx = Structure.transformAround(new BlockPos(5, 0, 6), BlockMirror.NONE, _snowmanxxxxxxxxxxxx, BlockPos.ORIGIN)
               .add(_snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxxxxx);
            BlockBox _snowmanxxxxxxxxxxxxxx = BlockBox.create(_snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx.getX(), 0, _snowmanxxxxxxxxxxxxx.getZ());
            if (!_snowmanxxxxxxxxxxxxxx.intersects(_snowmanxxx)) {
               method_14822(manager, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx, pieces, random, config, false, 0.8F);
            }
         }
      }
   }

   private static List<BlockPos> getRoomPositions(Random random, int x, int z) {
      List<BlockPos> _snowman = Lists.newArrayList();
      _snowman.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z + 16 + MathHelper.nextInt(random, 1, 7)));
      _snowman.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z + MathHelper.nextInt(random, 1, 7)));
      _snowman.add(new BlockPos(x - 16 + MathHelper.nextInt(random, 1, 8), 90, z - 16 + MathHelper.nextInt(random, 4, 8)));
      _snowman.add(new BlockPos(x + MathHelper.nextInt(random, 1, 7), 90, z + 16 + MathHelper.nextInt(random, 1, 7)));
      _snowman.add(new BlockPos(x + MathHelper.nextInt(random, 1, 7), 90, z - 16 + MathHelper.nextInt(random, 4, 6)));
      _snowman.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z + 16 + MathHelper.nextInt(random, 3, 8)));
      _snowman.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z + MathHelper.nextInt(random, 1, 7)));
      _snowman.add(new BlockPos(x + 16 + MathHelper.nextInt(random, 1, 7), 90, z - 16 + MathHelper.nextInt(random, 4, 8)));
      return _snowman;
   }

   private static void method_14822(
      StructureManager manager,
      BlockPos pos,
      BlockRotation rotation,
      List<StructurePiece> pieces,
      Random random,
      OceanRuinFeatureConfig config,
      boolean large,
      float integrity
   ) {
      if (config.biomeType == OceanRuinFeature.BiomeType.WARM) {
         Identifier _snowman = large ? getRandomBigWarmRuin(random) : getRandomWarmRuin(random);
         pieces.add(new OceanRuinGenerator.Piece(manager, _snowman, pos, rotation, integrity, config.biomeType, large));
      } else if (config.biomeType == OceanRuinFeature.BiomeType.COLD) {
         Identifier[] _snowman = large ? BIG_BRICK_RUINS : BRICK_RUINS;
         Identifier[] _snowmanx = large ? BIG_CRACKED_RUINS : CRACKED_RUINS;
         Identifier[] _snowmanxx = large ? BIG_MOSSY_RUINS : MOSSY_RUINS;
         int _snowmanxxx = random.nextInt(_snowman.length);
         pieces.add(new OceanRuinGenerator.Piece(manager, _snowman[_snowmanxxx], pos, rotation, integrity, config.biomeType, large));
         pieces.add(new OceanRuinGenerator.Piece(manager, _snowmanx[_snowmanxxx], pos, rotation, 0.7F, config.biomeType, large));
         pieces.add(new OceanRuinGenerator.Piece(manager, _snowmanxx[_snowmanxxx], pos, rotation, 0.5F, config.biomeType, large));
      }
   }

   public static class Piece extends SimpleStructurePiece {
      private final OceanRuinFeature.BiomeType biomeType;
      private final float integrity;
      private final Identifier template;
      private final BlockRotation rotation;
      private final boolean large;

      public Piece(
         StructureManager structureManager,
         Identifier template,
         BlockPos pos,
         BlockRotation rotation,
         float integrity,
         OceanRuinFeature.BiomeType biomeType,
         boolean large
      ) {
         super(StructurePieceType.OCEAN_TEMPLE, 0);
         this.template = template;
         this.pos = pos;
         this.rotation = rotation;
         this.integrity = integrity;
         this.biomeType = biomeType;
         this.large = large;
         this.initialize(structureManager);
      }

      public Piece(StructureManager manager, CompoundTag tag) {
         super(StructurePieceType.OCEAN_TEMPLE, tag);
         this.template = new Identifier(tag.getString("Template"));
         this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
         this.integrity = tag.getFloat("Integrity");
         this.biomeType = OceanRuinFeature.BiomeType.valueOf(tag.getString("BiomeType"));
         this.large = tag.getBoolean("IsLarge");
         this.initialize(manager);
      }

      private void initialize(StructureManager structureManager) {
         Structure _snowman = structureManager.getStructureOrBlank(this.template);
         StructurePlacementData _snowmanx = new StructurePlacementData()
            .setRotation(this.rotation)
            .setMirror(BlockMirror.NONE)
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
         this.setStructureData(_snowman, this.pos, _snowmanx);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putString("Template", this.template.toString());
         tag.putString("Rot", this.rotation.name());
         tag.putFloat("Integrity", this.integrity);
         tag.putString("BiomeType", this.biomeType.toString());
         tag.putBoolean("IsLarge", this.large);
      }

      @Override
      protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess _snowman, Random random, BlockBox boundingBox) {
         if ("chest".equals(metadata)) {
            _snowman.setBlockState(pos, Blocks.CHEST.getDefaultState().with(ChestBlock.WATERLOGGED, Boolean.valueOf(_snowman.getFluidState(pos).isIn(FluidTags.WATER))), 2);
            BlockEntity _snowmanx = _snowman.getBlockEntity(pos);
            if (_snowmanx instanceof ChestBlockEntity) {
               ((ChestBlockEntity)_snowmanx)
                  .setLootTable(this.large ? LootTables.UNDERWATER_RUIN_BIG_CHEST : LootTables.UNDERWATER_RUIN_SMALL_CHEST, random.nextLong());
            }
         } else if ("drowned".equals(metadata)) {
            DrownedEntity _snowmanx = EntityType.DROWNED.create(_snowman.toServerWorld());
            _snowmanx.setPersistent();
            _snowmanx.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            _snowmanx.initialize(_snowman, _snowman.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null, null);
            _snowman.spawnEntityAndPassengers(_snowmanx);
            if (pos.getY() > _snowman.getSeaLevel()) {
               _snowman.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            } else {
               _snowman.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
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
         this.placementData
            .clearProcessors()
            .addProcessor(new BlockRotStructureProcessor(this.integrity))
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
         int _snowmanxxx = _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, this.pos.getX(), this.pos.getZ());
         this.pos = new BlockPos(this.pos.getX(), _snowmanxxx, this.pos.getZ());
         BlockPos _snowmanxxxx = Structure.transformAround(
               new BlockPos(this.structure.getSize().getX() - 1, 0, this.structure.getSize().getZ() - 1), BlockMirror.NONE, this.rotation, BlockPos.ORIGIN
            )
            .add(this.pos);
         this.pos = new BlockPos(this.pos.getX(), this.method_14829(this.pos, _snowman, _snowmanxxxx), this.pos.getZ());
         return super.generate(_snowman, structureAccessor, chunkGenerator, random, boundingBox, _snowman, _snowman);
      }

      private int method_14829(BlockPos _snowman, BlockView _snowman, BlockPos _snowman) {
         int _snowmanxxx = _snowman.getY();
         int _snowmanxxxx = 512;
         int _snowmanxxxxx = _snowmanxxx - 1;
         int _snowmanxxxxxx = 0;

         for (BlockPos _snowmanxxxxxxx : BlockPos.iterate(_snowman, _snowman)) {
            int _snowmanxxxxxxxx = _snowmanxxxxxxx.getX();
            int _snowmanxxxxxxxxx = _snowmanxxxxxxx.getZ();
            int _snowmanxxxxxxxxxx = _snowman.getY() - 1;
            BlockPos.Mutable _snowmanxxxxxxxxxxx = new BlockPos.Mutable(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx);
            BlockState _snowmanxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxx);

            for (FluidState _snowmanxxxxxxxxxxxxx = _snowman.getFluidState(_snowmanxxxxxxxxxxx);
               (_snowmanxxxxxxxxxxxx.isAir() || _snowmanxxxxxxxxxxxxx.isIn(FluidTags.WATER) || _snowmanxxxxxxxxxxxx.getBlock().isIn(BlockTags.ICE)) && _snowmanxxxxxxxxxx > 1;
               _snowmanxxxxxxxxxxxxx = _snowman.getFluidState(_snowmanxxxxxxxxxxx)
            ) {
               _snowmanxxxxxxxxxxx.set(_snowmanxxxxxxxx, --_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx);
               _snowmanxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxx);
            }

            _snowmanxxxx = Math.min(_snowmanxxxx, _snowmanxxxxxxxxxx);
            if (_snowmanxxxxxxxxxx < _snowmanxxxxx - 2) {
               _snowmanxxxxxx++;
            }
         }

         int _snowmanxxxxxxx = Math.abs(_snowman.getX() - _snowman.getX());
         if (_snowmanxxxxx - _snowmanxxxx > 2 && _snowmanxxxxxx > _snowmanxxxxxxx - 2) {
            _snowmanxxx = _snowmanxxxx + 1;
         }

         return _snowmanxxx;
      }
   }
}
