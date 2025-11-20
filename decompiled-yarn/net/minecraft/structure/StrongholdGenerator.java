package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class StrongholdGenerator {
   private static final StrongholdGenerator.PieceData[] ALL_PIECES = new StrongholdGenerator.PieceData[]{
      new StrongholdGenerator.PieceData(StrongholdGenerator.Corridor.class, 40, 0),
      new StrongholdGenerator.PieceData(StrongholdGenerator.PrisonHall.class, 5, 5),
      new StrongholdGenerator.PieceData(StrongholdGenerator.LeftTurn.class, 20, 0),
      new StrongholdGenerator.PieceData(StrongholdGenerator.RightTurn.class, 20, 0),
      new StrongholdGenerator.PieceData(StrongholdGenerator.SquareRoom.class, 10, 6),
      new StrongholdGenerator.PieceData(StrongholdGenerator.Stairs.class, 5, 5),
      new StrongholdGenerator.PieceData(StrongholdGenerator.SpiralStaircase.class, 5, 5),
      new StrongholdGenerator.PieceData(StrongholdGenerator.FiveWayCrossing.class, 5, 4),
      new StrongholdGenerator.PieceData(StrongholdGenerator.ChestCorridor.class, 5, 4),
      new StrongholdGenerator.PieceData(StrongholdGenerator.Library.class, 10, 2) {
         @Override
         public boolean canGenerate(int chainLength) {
            return super.canGenerate(chainLength) && chainLength > 4;
         }
      },
      new StrongholdGenerator.PieceData(StrongholdGenerator.PortalRoom.class, 20, 1) {
         @Override
         public boolean canGenerate(int chainLength) {
            return super.canGenerate(chainLength) && chainLength > 5;
         }
      }
   };
   private static List<StrongholdGenerator.PieceData> possiblePieces;
   private static Class<? extends StrongholdGenerator.Piece> activePieceType;
   private static int totalWeight;
   private static final StrongholdGenerator.StoneBrickRandomizer STONE_BRICK_RANDOMIZER = new StrongholdGenerator.StoneBrickRandomizer();

   public static void init() {
      possiblePieces = Lists.newArrayList();

      for (StrongholdGenerator.PieceData _snowman : ALL_PIECES) {
         _snowman.generatedCount = 0;
         possiblePieces.add(_snowman);
      }

      activePieceType = null;
   }

   private static boolean checkRemainingPieces() {
      boolean _snowman = false;
      totalWeight = 0;

      for (StrongholdGenerator.PieceData _snowmanx : possiblePieces) {
         if (_snowmanx.limit > 0 && _snowmanx.generatedCount < _snowmanx.limit) {
            _snowman = true;
         }

         totalWeight = totalWeight + _snowmanx.weight;
      }

      return _snowman;
   }

   private static StrongholdGenerator.Piece createPiece(
      Class<? extends StrongholdGenerator.Piece> pieceType,
      List<StructurePiece> pieces,
      Random random,
      int x,
      int y,
      int z,
      @Nullable Direction orientation,
      int chainLength
   ) {
      StrongholdGenerator.Piece _snowman = null;
      if (pieceType == StrongholdGenerator.Corridor.class) {
         _snowman = StrongholdGenerator.Corridor.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.PrisonHall.class) {
         _snowman = StrongholdGenerator.PrisonHall.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.LeftTurn.class) {
         _snowman = StrongholdGenerator.LeftTurn.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.RightTurn.class) {
         _snowman = StrongholdGenerator.RightTurn.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.SquareRoom.class) {
         _snowman = StrongholdGenerator.SquareRoom.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.Stairs.class) {
         _snowman = StrongholdGenerator.Stairs.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.SpiralStaircase.class) {
         _snowman = StrongholdGenerator.SpiralStaircase.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.FiveWayCrossing.class) {
         _snowman = StrongholdGenerator.FiveWayCrossing.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.ChestCorridor.class) {
         _snowman = StrongholdGenerator.ChestCorridor.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.Library.class) {
         _snowman = StrongholdGenerator.Library.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (pieceType == StrongholdGenerator.PortalRoom.class) {
         _snowman = StrongholdGenerator.PortalRoom.create(pieces, x, y, z, orientation, chainLength);
      }

      return _snowman;
   }

   private static StrongholdGenerator.Piece pickPiece(
      StrongholdGenerator.Start start, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
   ) {
      if (!checkRemainingPieces()) {
         return null;
      } else {
         if (activePieceType != null) {
            StrongholdGenerator.Piece _snowman = createPiece(activePieceType, pieces, random, x, y, z, orientation, chainLength);
            activePieceType = null;
            if (_snowman != null) {
               return _snowman;
            }
         }

         int _snowman = 0;

         while (_snowman < 5) {
            _snowman++;
            int _snowmanx = random.nextInt(totalWeight);

            for (StrongholdGenerator.PieceData _snowmanxx : possiblePieces) {
               _snowmanx -= _snowmanxx.weight;
               if (_snowmanx < 0) {
                  if (!_snowmanxx.canGenerate(chainLength) || _snowmanxx == start.lastPiece) {
                     break;
                  }

                  StrongholdGenerator.Piece _snowmanxxx = createPiece(_snowmanxx.pieceType, pieces, random, x, y, z, orientation, chainLength);
                  if (_snowmanxxx != null) {
                     _snowmanxx.generatedCount++;
                     start.lastPiece = _snowmanxx;
                     if (!_snowmanxx.canGenerate()) {
                        possiblePieces.remove(_snowmanxx);
                     }

                     return _snowmanxxx;
                  }
               }
            }
         }

         BlockBox _snowmanx = StrongholdGenerator.SmallCorridor.create(pieces, random, x, y, z, orientation);
         return _snowmanx != null && _snowmanx.minY > 1 ? new StrongholdGenerator.SmallCorridor(chainLength, _snowmanx, orientation) : null;
      }
   }

   private static StructurePiece pieceGenerator(
      StrongholdGenerator.Start start, List<StructurePiece> pieces, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength
   ) {
      if (chainLength > 50) {
         return null;
      } else if (Math.abs(x - start.getBoundingBox().minX) <= 112 && Math.abs(z - start.getBoundingBox().minZ) <= 112) {
         StructurePiece _snowman = pickPiece(start, pieces, random, x, y, z, orientation, chainLength + 1);
         if (_snowman != null) {
            pieces.add(_snowman);
            start.pieces.add(_snowman);
         }

         return _snowman;
      } else {
         return null;
      }
   }

   public static class ChestCorridor extends StrongholdGenerator.Piece {
      private boolean chestGenerated;

      public ChestCorridor(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
      }

      public ChestCorridor(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, _snowman);
         this.chestGenerated = _snowman.getBoolean("Chest");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Chest", this.chestGenerated);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
      }

      public static StrongholdGenerator.ChestCorridor create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainlength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.ChestCorridor(chainlength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 4, 6, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 1, 1, 0);
         this.generateEntrance(_snowman, random, boundingBox, StrongholdGenerator.Piece.EntranceType.OPENING, 1, 1, 6);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 2, 3, 1, 4, Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICKS.getDefaultState(), false);
         this.addBlock(_snowman, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 1, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 5, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 2, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 4, boundingBox);

         for (int _snowmanxxx = 2; _snowmanxxx <= 4; _snowmanxxx++) {
            this.addBlock(_snowman, Blocks.STONE_BRICK_SLAB.getDefaultState(), 2, 1, _snowmanxxx, boundingBox);
         }

         if (!this.chestGenerated && boundingBox.contains(new BlockPos(this.applyXTransform(3, 3), this.applyYTransform(2), this.applyZTransform(3, 3)))) {
            this.chestGenerated = true;
            this.addChest(_snowman, boundingBox, random, 3, 2, 3, LootTables.STRONGHOLD_CORRIDOR_CHEST);
         }

         return true;
      }
   }

   public static class Corridor extends StrongholdGenerator.Piece {
      private final boolean leftExitExists;
      private final boolean rightExitExists;

      public Corridor(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_CORRIDOR, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
         this.leftExitExists = random.nextInt(2) == 0;
         this.rightExitExists = random.nextInt(2) == 0;
      }

      public Corridor(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_CORRIDOR, _snowman);
         this.leftExitExists = _snowman.getBoolean("Left");
         this.rightExitExists = _snowman.getBoolean("Right");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Left", this.leftExitExists);
         tag.putBoolean("Right", this.rightExitExists);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
         if (this.leftExitExists) {
            this.fillNWOpening((StrongholdGenerator.Start)start, pieces, random, 1, 2);
         }

         if (this.rightExitExists) {
            this.fillSEOpening((StrongholdGenerator.Start)start, pieces, random, 1, 2);
         }
      }

      public static StrongholdGenerator.Corridor create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.Corridor(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 4, 6, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 1, 1, 0);
         this.generateEntrance(_snowman, random, boundingBox, StrongholdGenerator.Piece.EntranceType.OPENING, 1, 1, 6);
         BlockState _snowmanxxx = Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.EAST);
         BlockState _snowmanxxxx = Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.WEST);
         this.addBlockWithRandomThreshold(_snowman, boundingBox, random, 0.1F, 1, 2, 1, _snowmanxxx);
         this.addBlockWithRandomThreshold(_snowman, boundingBox, random, 0.1F, 3, 2, 1, _snowmanxxxx);
         this.addBlockWithRandomThreshold(_snowman, boundingBox, random, 0.1F, 1, 2, 5, _snowmanxxx);
         this.addBlockWithRandomThreshold(_snowman, boundingBox, random, 0.1F, 3, 2, 5, _snowmanxxxx);
         if (this.leftExitExists) {
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 2, 0, 3, 4, AIR, AIR, false);
         }

         if (this.rightExitExists) {
            this.fillWithOutline(_snowman, boundingBox, 4, 1, 2, 4, 3, 4, AIR, AIR, false);
         }

         return true;
      }
   }

   public static class FiveWayCrossing extends StrongholdGenerator.Piece {
      private final boolean lowerLeftExists;
      private final boolean upperLeftExists;
      private final boolean lowerRightExists;
      private final boolean upperRightExists;

      public FiveWayCrossing(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_FIVE_WAY_CROSSING, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
         this.lowerLeftExists = random.nextBoolean();
         this.upperLeftExists = random.nextBoolean();
         this.lowerRightExists = random.nextBoolean();
         this.upperRightExists = random.nextInt(3) > 0;
      }

      public FiveWayCrossing(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_FIVE_WAY_CROSSING, _snowman);
         this.lowerLeftExists = _snowman.getBoolean("leftLow");
         this.upperLeftExists = _snowman.getBoolean("leftHigh");
         this.lowerRightExists = _snowman.getBoolean("rightLow");
         this.upperRightExists = _snowman.getBoolean("rightHigh");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("leftLow", this.lowerLeftExists);
         tag.putBoolean("leftHigh", this.upperLeftExists);
         tag.putBoolean("rightLow", this.lowerRightExists);
         tag.putBoolean("rightHigh", this.upperRightExists);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int _snowman = 3;
         int _snowmanx = 5;
         Direction _snowmanxx = this.getFacing();
         if (_snowmanxx == Direction.WEST || _snowmanxx == Direction.NORTH) {
            _snowman = 8 - _snowman;
            _snowmanx = 8 - _snowmanx;
         }

         this.fillForwardOpening((StrongholdGenerator.Start)start, pieces, random, 5, 1);
         if (this.lowerLeftExists) {
            this.fillNWOpening((StrongholdGenerator.Start)start, pieces, random, _snowman, 1);
         }

         if (this.upperLeftExists) {
            this.fillNWOpening((StrongholdGenerator.Start)start, pieces, random, _snowmanx, 7);
         }

         if (this.lowerRightExists) {
            this.fillSEOpening((StrongholdGenerator.Start)start, pieces, random, _snowman, 1);
         }

         if (this.upperRightExists) {
            this.fillSEOpening((StrongholdGenerator.Start)start, pieces, random, _snowmanx, 7);
         }
      }

      public static StrongholdGenerator.FiveWayCrossing create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -4, -3, 0, 10, 9, 11, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.FiveWayCrossing(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 9, 8, 10, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 4, 3, 0);
         if (this.lowerLeftExists) {
            this.fillWithOutline(_snowman, boundingBox, 0, 3, 1, 0, 5, 3, AIR, AIR, false);
         }

         if (this.lowerRightExists) {
            this.fillWithOutline(_snowman, boundingBox, 9, 3, 1, 9, 5, 3, AIR, AIR, false);
         }

         if (this.upperLeftExists) {
            this.fillWithOutline(_snowman, boundingBox, 0, 5, 7, 0, 7, 9, AIR, AIR, false);
         }

         if (this.upperRightExists) {
            this.fillWithOutline(_snowman, boundingBox, 9, 5, 7, 9, 7, 9, AIR, AIR, false);
         }

         this.fillWithOutline(_snowman, boundingBox, 5, 1, 10, 7, 3, 10, AIR, AIR, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 1, 8, 2, 6, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 5, 4, 4, 9, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 8, 1, 5, 8, 4, 9, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 7, 3, 4, 9, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 5, 3, 3, 6, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 4, 3, 3, 4, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 6, 3, 4, 6, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 7, 7, 1, 8, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 9, 7, 1, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 7, 7, 2, 7, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 5, 7, 4, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 5, 7, 8, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
         this.fillWithOutline(
            _snowman,
            boundingBox,
            5,
            5,
            7,
            7,
            5,
            9,
            Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE),
            Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE),
            false
         );
         this.addBlock(_snowman, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.SOUTH), 6, 5, 6, boundingBox);
         return true;
      }
   }

   public static class LeftTurn extends StrongholdGenerator.Turn {
      public LeftTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_LEFT_TURN, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
      }

      public LeftTurn(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_LEFT_TURN, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         Direction _snowman = this.getFacing();
         if (_snowman != Direction.NORTH && _snowman != Direction.EAST) {
            this.fillSEOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
         } else {
            this.fillNWOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
         }
      }

      public static StrongholdGenerator.LeftTurn create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 5, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.LeftTurn(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 4, 4, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 1, 1, 0);
         Direction _snowmanxxx = this.getFacing();
         if (_snowmanxxx != Direction.NORTH && _snowmanxxx != Direction.EAST) {
            this.fillWithOutline(_snowman, boundingBox, 4, 1, 1, 4, 3, 3, AIR, AIR, false);
         } else {
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 1, 0, 3, 3, AIR, AIR, false);
         }

         return true;
      }
   }

   public static class Library extends StrongholdGenerator.Piece {
      private final boolean tall;

      public Library(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_LIBRARY, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
         this.tall = boundingBox.getBlockCountY() > 6;
      }

      public Library(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_LIBRARY, _snowman);
         this.tall = _snowman.getBoolean("Tall");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Tall", this.tall);
      }

      public static StrongholdGenerator.Library create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 11, 15, orientation);
         if (!isInbounds(_snowman) || StructurePiece.getOverlappingPiece(pieces, _snowman) != null) {
            _snowman = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 6, 15, orientation);
            if (!isInbounds(_snowman) || StructurePiece.getOverlappingPiece(pieces, _snowman) != null) {
               return null;
            }
         }

         return new StrongholdGenerator.Library(chainLength, random, _snowman, orientation);
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
         int _snowmanxxx = 11;
         if (!this.tall) {
            _snowmanxxx = 6;
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 13, _snowmanxxx - 1, 14, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 4, 1, 0);
         this.fillWithOutlineUnderSeaLevel(
            _snowman, boundingBox, random, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.COBWEB.getDefaultState(), Blocks.COBWEB.getDefaultState(), false, false
         );
         int _snowmanxxxx = 1;
         int _snowmanxxxxx = 12;

         for (int _snowmanxxxxxx = 1; _snowmanxxxxxx <= 13; _snowmanxxxxxx++) {
            if ((_snowmanxxxxxx - 1) % 4 == 0) {
               this.fillWithOutline(
                  _snowman, boundingBox, 1, 1, _snowmanxxxxxx, 1, 4, _snowmanxxxxxx, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false
               );
               this.fillWithOutline(
                  _snowman, boundingBox, 12, 1, _snowmanxxxxxx, 12, 4, _snowmanxxxxxx, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false
               );
               this.addBlock(_snowman, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.EAST), 2, 3, _snowmanxxxxxx, boundingBox);
               this.addBlock(_snowman, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.WEST), 11, 3, _snowmanxxxxxx, boundingBox);
               if (this.tall) {
                  this.fillWithOutline(
                     _snowman, boundingBox, 1, 6, _snowmanxxxxxx, 1, 9, _snowmanxxxxxx, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false
                  );
                  this.fillWithOutline(
                     _snowman, boundingBox, 12, 6, _snowmanxxxxxx, 12, 9, _snowmanxxxxxx, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false
                  );
               }
            } else {
               this.fillWithOutline(_snowman, boundingBox, 1, 1, _snowmanxxxxxx, 1, 4, _snowmanxxxxxx, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
               this.fillWithOutline(
                  _snowman, boundingBox, 12, 1, _snowmanxxxxxx, 12, 4, _snowmanxxxxxx, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false
               );
               if (this.tall) {
                  this.fillWithOutline(
                     _snowman, boundingBox, 1, 6, _snowmanxxxxxx, 1, 9, _snowmanxxxxxx, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false
                  );
                  this.fillWithOutline(
                     _snowman, boundingBox, 12, 6, _snowmanxxxxxx, 12, 9, _snowmanxxxxxx, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false
                  );
               }
            }
         }

         for (int _snowmanxxxxxxx = 3; _snowmanxxxxxxx < 12; _snowmanxxxxxxx += 2) {
            this.fillWithOutline(_snowman, boundingBox, 3, 1, _snowmanxxxxxxx, 4, 3, _snowmanxxxxxxx, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
            this.fillWithOutline(_snowman, boundingBox, 6, 1, _snowmanxxxxxxx, 7, 3, _snowmanxxxxxxx, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
            this.fillWithOutline(_snowman, boundingBox, 9, 1, _snowmanxxxxxxx, 10, 3, _snowmanxxxxxxx, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
         }

         if (this.tall) {
            this.fillWithOutline(_snowman, boundingBox, 1, 5, 1, 3, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
            this.fillWithOutline(_snowman, boundingBox, 10, 5, 1, 12, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
            this.fillWithOutline(_snowman, boundingBox, 4, 5, 1, 9, 5, 2, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
            this.fillWithOutline(_snowman, boundingBox, 4, 5, 12, 9, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
            this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 11, boundingBox);
            this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 8, 5, 11, boundingBox);
            this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 10, boundingBox);
            BlockState _snowmanxxxxxxx = Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true));
            BlockState _snowmanxxxxxxxx = Blocks.OAK_FENCE
               .getDefaultState()
               .with(FenceBlock.NORTH, Boolean.valueOf(true))
               .with(FenceBlock.SOUTH, Boolean.valueOf(true));
            this.fillWithOutline(_snowman, boundingBox, 3, 6, 3, 3, 6, 11, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 10, 6, 3, 10, 6, 9, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 4, 6, 2, 9, 6, 2, _snowmanxxxxxxx, _snowmanxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 4, 6, 12, 7, 6, 12, _snowmanxxxxxxx, _snowmanxxxxxxx, false);
            this.addBlock(
               _snowman,
               Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)),
               3,
               6,
               2,
               boundingBox
            );
            this.addBlock(
               _snowman,
               Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)),
               3,
               6,
               12,
               boundingBox
            );
            this.addBlock(
               _snowman,
               Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.WEST, Boolean.valueOf(true)),
               10,
               6,
               2,
               boundingBox
            );

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= 2; _snowmanxxxxxxxxx++) {
               this.addBlock(
                  _snowman,
                  Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.WEST, Boolean.valueOf(true)),
                  8 + _snowmanxxxxxxxxx,
                  6,
                  12 - _snowmanxxxxxxxxx,
                  boundingBox
               );
               if (_snowmanxxxxxxxxx != 2) {
                  this.addBlock(
                     _snowman,
                     Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)),
                     8 + _snowmanxxxxxxxxx,
                     6,
                     11 - _snowmanxxxxxxxxx,
                     boundingBox
                  );
               }
            }

            BlockState _snowmanxxxxxxxxxx = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH);
            this.addBlock(_snowman, _snowmanxxxxxxxxxx, 10, 1, 13, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxx, 10, 2, 13, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxx, 10, 3, 13, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxx, 10, 4, 13, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxx, 10, 5, 13, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxx, 10, 6, 13, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxx, 10, 7, 13, boundingBox);
            int _snowmanxxxxxxxxxxx = 7;
            int _snowmanxxxxxxxxxxxx = 7;
            BlockState _snowmanxxxxxxxxxxxxx = Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.EAST, Boolean.valueOf(true));
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 6, 9, 7, boundingBox);
            BlockState _snowmanxxxxxxxxxxxxxx = Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true));
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxx, 7, 9, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 6, 8, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxx, 7, 8, 7, boundingBox);
            BlockState _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.with(FenceBlock.WEST, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true));
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxx, 6, 7, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxx, 7, 7, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 5, 7, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxx, 8, 7, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx.with(FenceBlock.NORTH, Boolean.valueOf(true)), 6, 7, 6, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx.with(FenceBlock.SOUTH, Boolean.valueOf(true)), 6, 7, 8, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxx.with(FenceBlock.NORTH, Boolean.valueOf(true)), 7, 7, 6, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxx.with(FenceBlock.SOUTH, Boolean.valueOf(true)), 7, 7, 8, boundingBox);
            BlockState _snowmanxxxxxxxxxxxxxxxx = Blocks.TORCH.getDefaultState();
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxxx, 5, 8, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxxx, 8, 8, 7, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxxx, 6, 8, 6, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxxx, 6, 8, 8, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxxx, 7, 8, 6, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxxxxx, 7, 8, 8, boundingBox);
         }

         this.addChest(_snowman, boundingBox, random, 3, 3, 5, LootTables.STRONGHOLD_LIBRARY_CHEST);
         if (this.tall) {
            this.addBlock(_snowman, AIR, 12, 9, 1, boundingBox);
            this.addChest(_snowman, boundingBox, random, 12, 8, 1, LootTables.STRONGHOLD_LIBRARY_CHEST);
         }

         return true;
      }
   }

   abstract static class Piece extends StructurePiece {
      protected StrongholdGenerator.Piece.EntranceType entryDoor = StrongholdGenerator.Piece.EntranceType.OPENING;

      protected Piece(StructurePieceType _snowman, int _snowman) {
         super(_snowman, _snowman);
      }

      public Piece(StructurePieceType _snowman, CompoundTag _snowman) {
         super(_snowman, _snowman);
         this.entryDoor = StrongholdGenerator.Piece.EntranceType.valueOf(_snowman.getString("EntryDoor"));
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         tag.putString("EntryDoor", this.entryDoor.name());
      }

      protected void generateEntrance(
         StructureWorldAccess _snowman, Random random, BlockBox boundingBox, StrongholdGenerator.Piece.EntranceType type, int x, int y, int z
      ) {
         switch (type) {
            case OPENING:
               this.fillWithOutline(_snowman, boundingBox, x, y, z, x + 3 - 1, y + 3 - 1, z, AIR, AIR, false);
               break;
            case WOOD_DOOR:
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x, y + 1, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x, y + 2, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 1, y + 2, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 2, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 1, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.OAK_DOOR.getDefaultState(), x + 1, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), x + 1, y + 1, z, boundingBox);
               break;
            case GRATES:
               this.addBlock(_snowman, Blocks.CAVE_AIR.getDefaultState(), x + 1, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.CAVE_AIR.getDefaultState(), x + 1, y + 1, z, boundingBox);
               this.addBlock(_snowman, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, Boolean.valueOf(true)), x, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, Boolean.valueOf(true)), x, y + 1, z, boundingBox);
               this.addBlock(
                  _snowman,
                  Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)),
                  x,
                  y + 2,
                  z,
                  boundingBox
               );
               this.addBlock(
                  _snowman,
                  Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)),
                  x + 1,
                  y + 2,
                  z,
                  boundingBox
               );
               this.addBlock(
                  _snowman,
                  Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)).with(PaneBlock.WEST, Boolean.valueOf(true)),
                  x + 2,
                  y + 2,
                  z,
                  boundingBox
               );
               this.addBlock(_snowman, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)), x + 2, y + 1, z, boundingBox);
               this.addBlock(_snowman, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, Boolean.valueOf(true)), x + 2, y, z, boundingBox);
               break;
            case IRON_DOOR:
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x, y + 1, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x, y + 2, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 1, y + 2, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 2, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y + 1, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), x + 2, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.IRON_DOOR.getDefaultState(), x + 1, y, z, boundingBox);
               this.addBlock(_snowman, Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), x + 1, y + 1, z, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.FACING, Direction.NORTH), x + 2, y + 1, z + 1, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.FACING, Direction.SOUTH), x + 2, y + 1, z - 1, boundingBox);
         }
      }

      protected StrongholdGenerator.Piece.EntranceType getRandomEntrance(Random random) {
         int _snowman = random.nextInt(5);
         switch (_snowman) {
            case 0:
            case 1:
            default:
               return StrongholdGenerator.Piece.EntranceType.OPENING;
            case 2:
               return StrongholdGenerator.Piece.EntranceType.WOOD_DOOR;
            case 3:
               return StrongholdGenerator.Piece.EntranceType.GRATES;
            case 4:
               return StrongholdGenerator.Piece.EntranceType.IRON_DOOR;
         }
      }

      @Nullable
      protected StructurePiece fillForwardOpening(
         StrongholdGenerator.Start start, List<StructurePiece> pieces, Random random, int leftRightOffset, int heightOffset
      ) {
         Direction _snowman = this.getFacing();
         if (_snowman != null) {
            switch (_snowman) {
               case NORTH:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ - 1,
                     _snowman,
                     this.getChainLength()
                  );
               case SOUTH:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.maxZ + 1,
                     _snowman,
                     this.getChainLength()
                  );
               case WEST:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     _snowman,
                     this.getChainLength()
                  );
               case EAST:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     _snowman,
                     this.getChainLength()
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece fillNWOpening(StrongholdGenerator.Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset) {
         Direction _snowman = this.getFacing();
         if (_snowman != null) {
            switch (_snowman) {
               case NORTH:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.WEST,
                     this.getChainLength()
                  );
               case SOUTH:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.WEST,
                     this.getChainLength()
                  );
               case WEST:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ - 1,
                     Direction.NORTH,
                     this.getChainLength()
                  );
               case EAST:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ - 1,
                     Direction.NORTH,
                     this.getChainLength()
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece fillSEOpening(StrongholdGenerator.Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset) {
         Direction _snowman = this.getFacing();
         if (_snowman != null) {
            switch (_snowman) {
               case NORTH:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.EAST,
                     this.getChainLength()
                  );
               case SOUTH:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.EAST,
                     this.getChainLength()
                  );
               case WEST:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.maxZ + 1,
                     Direction.SOUTH,
                     this.getChainLength()
                  );
               case EAST:
                  return StrongholdGenerator.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.maxZ + 1,
                     Direction.SOUTH,
                     this.getChainLength()
                  );
            }
         }

         return null;
      }

      protected static boolean isInbounds(BlockBox boundingBox) {
         return boundingBox != null && boundingBox.minY > 10;
      }

      public static enum EntranceType {
         OPENING,
         WOOD_DOOR,
         GRATES,
         IRON_DOOR;

         private EntranceType() {
         }
      }
   }

   static class PieceData {
      public final Class<? extends StrongholdGenerator.Piece> pieceType;
      public final int weight;
      public int generatedCount;
      public final int limit;

      public PieceData(Class<? extends StrongholdGenerator.Piece> pieceType, int weight, int limit) {
         this.pieceType = pieceType;
         this.weight = weight;
         this.limit = limit;
      }

      public boolean canGenerate(int chainLength) {
         return this.limit == 0 || this.generatedCount < this.limit;
      }

      public boolean canGenerate() {
         return this.limit == 0 || this.generatedCount < this.limit;
      }
   }

   public static class PortalRoom extends StrongholdGenerator.Piece {
      private boolean spawnerPlaced;

      public PortalRoom(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public PortalRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, _snowman);
         this.spawnerPlaced = _snowman.getBoolean("Mob");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Mob", this.spawnerPlaced);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         if (start != null) {
            ((StrongholdGenerator.Start)start).portalRoom = this;
         }
      }

      public static StrongholdGenerator.PortalRoom create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 8, 16, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null ? new StrongholdGenerator.PortalRoom(chainLength, _snowman, orientation) : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 10, 7, 15, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, StrongholdGenerator.Piece.EntranceType.GRATES, 4, 1, 0);
         int _snowmanxxx = 6;
         this.fillWithOutline(_snowman, boundingBox, 1, _snowmanxxx, 1, 1, _snowmanxxx, 14, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 9, _snowmanxxx, 1, 9, _snowmanxxx, 14, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 2, _snowmanxxx, 1, 8, _snowmanxxx, 2, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 2, _snowmanxxx, 14, 8, _snowmanxxx, 14, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 1, 2, 1, 4, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 8, 1, 1, 9, 1, 4, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 1, 1, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 9, 1, 1, 9, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 8, 7, 1, 12, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 9, 6, 1, 11, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
         BlockState _snowmanxxxx = Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(true)).with(PaneBlock.SOUTH, Boolean.valueOf(true));
         BlockState _snowmanxxxxx = Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, Boolean.valueOf(true)).with(PaneBlock.EAST, Boolean.valueOf(true));

         for (int _snowmanxxxxxx = 3; _snowmanxxxxxx < 14; _snowmanxxxxxx += 2) {
            this.fillWithOutline(_snowman, boundingBox, 0, 3, _snowmanxxxxxx, 0, 4, _snowmanxxxxxx, _snowmanxxxx, _snowmanxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 10, 3, _snowmanxxxxxx, 10, 4, _snowmanxxxxxx, _snowmanxxxx, _snowmanxxxx, false);
         }

         for (int _snowmanxxxxxx = 2; _snowmanxxxxxx < 9; _snowmanxxxxxx += 2) {
            this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxx, 3, 15, _snowmanxxxxxx, 4, 15, _snowmanxxxxx, _snowmanxxxxx, false);
         }

         BlockState _snowmanxxxxxx = Blocks.STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 5, 6, 1, 7, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 6, 6, 2, 7, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 4, 3, 7, 6, 3, 7, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);

         for (int _snowmanxxxxxxx = 4; _snowmanxxxxxxx <= 6; _snowmanxxxxxxx++) {
            this.addBlock(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, 1, 4, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, 2, 5, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, 3, 6, boundingBox);
         }

         BlockState _snowmanxxxxxxx = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.NORTH);
         BlockState _snowmanxxxxxxxx = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.SOUTH);
         BlockState _snowmanxxxxxxxxx = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.EAST);
         BlockState _snowmanxxxxxxxxxx = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.WEST);
         boolean _snowmanxxxxxxxxxxx = true;
         boolean[] _snowmanxxxxxxxxxxxx = new boolean[12];

         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx.length; _snowmanxxxxxxxxxxxxx++) {
            _snowmanxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxx] = random.nextFloat() > 0.9F;
            _snowmanxxxxxxxxxxx &= _snowmanxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxx];
         }

         this.addBlock(_snowman, _snowmanxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[0])), 4, 3, 8, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[1])), 5, 3, 8, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[2])), 6, 3, 8, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[3])), 4, 3, 12, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[4])), 5, 3, 12, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[5])), 6, 3, 12, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[6])), 3, 3, 9, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[7])), 3, 3, 10, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[8])), 3, 3, 11, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[9])), 7, 3, 9, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[10])), 7, 3, 10, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(_snowmanxxxxxxxxxxxx[11])), 7, 3, 11, boundingBox);
         if (_snowmanxxxxxxxxxxx) {
            BlockState _snowmanxxxxxxxxxxxxx = Blocks.END_PORTAL.getDefaultState();
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 4, 3, 9, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 5, 3, 9, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 6, 3, 9, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 4, 3, 10, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 5, 3, 10, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 6, 3, 10, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 4, 3, 11, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 5, 3, 11, boundingBox);
            this.addBlock(_snowman, _snowmanxxxxxxxxxxxxx, 6, 3, 11, boundingBox);
         }

         if (!this.spawnerPlaced) {
            _snowmanxxx = this.applyYTransform(3);
            BlockPos _snowmanxxxxxxxxxxxxx = new BlockPos(this.applyXTransform(5, 6), _snowmanxxx, this.applyZTransform(5, 6));
            if (boundingBox.contains(_snowmanxxxxxxxxxxxxx)) {
               this.spawnerPlaced = true;
               _snowman.setBlockState(_snowmanxxxxxxxxxxxxx, Blocks.SPAWNER.getDefaultState(), 2);
               BlockEntity _snowmanxxxxxxxxxxxxxx = _snowman.getBlockEntity(_snowmanxxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxx instanceof MobSpawnerBlockEntity) {
                  ((MobSpawnerBlockEntity)_snowmanxxxxxxxxxxxxxx).getLogic().setEntityId(EntityType.SILVERFISH);
               }
            }
         }

         return true;
      }
   }

   public static class PrisonHall extends StrongholdGenerator.Piece {
      public PrisonHall(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_PRISON_HALL, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
      }

      public PrisonHall(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_PRISON_HALL, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
      }

      public static StrongholdGenerator.PrisonHall create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -1, 0, 9, 5, 11, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.PrisonHall(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 8, 4, 10, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 1, 1, 0);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 10, 3, 3, 10, AIR, AIR, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 1, 4, 3, 1, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 3, 4, 3, 3, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 7, 4, 3, 7, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 9, 4, 3, 9, false, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);

         for (int _snowmanxxx = 1; _snowmanxxx <= 3; _snowmanxxx++) {
            this.addBlock(
               _snowman,
               Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(true)).with(PaneBlock.SOUTH, Boolean.valueOf(true)),
               4,
               _snowmanxxx,
               4,
               boundingBox
            );
            this.addBlock(
               _snowman,
               Blocks.IRON_BARS
                  .getDefaultState()
                  .with(PaneBlock.NORTH, Boolean.valueOf(true))
                  .with(PaneBlock.SOUTH, Boolean.valueOf(true))
                  .with(PaneBlock.EAST, Boolean.valueOf(true)),
               4,
               _snowmanxxx,
               5,
               boundingBox
            );
            this.addBlock(
               _snowman,
               Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(true)).with(PaneBlock.SOUTH, Boolean.valueOf(true)),
               4,
               _snowmanxxx,
               6,
               boundingBox
            );
            this.addBlock(
               _snowman,
               Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, Boolean.valueOf(true)).with(PaneBlock.EAST, Boolean.valueOf(true)),
               5,
               _snowmanxxx,
               5,
               boundingBox
            );
            this.addBlock(
               _snowman,
               Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, Boolean.valueOf(true)).with(PaneBlock.EAST, Boolean.valueOf(true)),
               6,
               _snowmanxxx,
               5,
               boundingBox
            );
            this.addBlock(
               _snowman,
               Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, Boolean.valueOf(true)).with(PaneBlock.EAST, Boolean.valueOf(true)),
               7,
               _snowmanxxx,
               5,
               boundingBox
            );
         }

         this.addBlock(
            _snowman,
            Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(true)).with(PaneBlock.SOUTH, Boolean.valueOf(true)),
            4,
            3,
            2,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(true)).with(PaneBlock.SOUTH, Boolean.valueOf(true)),
            4,
            3,
            8,
            boundingBox
         );
         BlockState _snowmanxxx = Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST);
         BlockState _snowmanxxxx = Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST).with(DoorBlock.HALF, DoubleBlockHalf.UPPER);
         this.addBlock(_snowman, _snowmanxxx, 4, 1, 2, boundingBox);
         this.addBlock(_snowman, _snowmanxxxx, 4, 2, 2, boundingBox);
         this.addBlock(_snowman, _snowmanxxx, 4, 1, 8, boundingBox);
         this.addBlock(_snowman, _snowmanxxxx, 4, 2, 8, boundingBox);
         return true;
      }
   }

   public static class RightTurn extends StrongholdGenerator.Turn {
      public RightTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_RIGHT_TURN, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
      }

      public RightTurn(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_RIGHT_TURN, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         Direction _snowman = this.getFacing();
         if (_snowman != Direction.NORTH && _snowman != Direction.EAST) {
            this.fillNWOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
         } else {
            this.fillSEOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
         }
      }

      public static StrongholdGenerator.RightTurn create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 5, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.RightTurn(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 4, 4, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 1, 1, 0);
         Direction _snowmanxxx = this.getFacing();
         if (_snowmanxxx != Direction.NORTH && _snowmanxxx != Direction.EAST) {
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 1, 0, 3, 3, AIR, AIR, false);
         } else {
            this.fillWithOutline(_snowman, boundingBox, 4, 1, 1, 4, 3, 3, AIR, AIR, false);
         }

         return true;
      }
   }

   public static class SmallCorridor extends StrongholdGenerator.Piece {
      private final int length;

      public SmallCorridor(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_SMALL_CORRIDOR, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
         this.length = orientation != Direction.NORTH && orientation != Direction.SOUTH ? boundingBox.getBlockCountX() : boundingBox.getBlockCountZ();
      }

      public SmallCorridor(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_SMALL_CORRIDOR, _snowman);
         this.length = _snowman.getInt("Steps");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putInt("Steps", this.length);
      }

      public static BlockBox create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
         int _snowman = 3;
         BlockBox _snowmanx = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, 4, orientation);
         StructurePiece _snowmanxx = StructurePiece.getOverlappingPiece(pieces, _snowmanx);
         if (_snowmanxx == null) {
            return null;
         } else {
            if (_snowmanxx.getBoundingBox().minY == _snowmanx.minY) {
               for (int _snowmanxxx = 3; _snowmanxxx >= 1; _snowmanxxx--) {
                  _snowmanx = BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, _snowmanxxx - 1, orientation);
                  if (!_snowmanxx.getBoundingBox().intersects(_snowmanx)) {
                     return BlockBox.rotated(x, y, z, -1, -1, 0, 5, 5, _snowmanxxx, orientation);
                  }
               }
            }

            return null;
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
         for (int _snowmanxxx = 0; _snowmanxxx < this.length; _snowmanxxx++) {
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 0, 0, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 0, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 2, 0, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3, 0, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 4, 0, _snowmanxxx, boundingBox);

            for (int _snowmanxxxx = 1; _snowmanxxxx <= 3; _snowmanxxxx++) {
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 0, _snowmanxxxx, _snowmanxxx, boundingBox);
               this.addBlock(_snowman, Blocks.CAVE_AIR.getDefaultState(), 1, _snowmanxxxx, _snowmanxxx, boundingBox);
               this.addBlock(_snowman, Blocks.CAVE_AIR.getDefaultState(), 2, _snowmanxxxx, _snowmanxxx, boundingBox);
               this.addBlock(_snowman, Blocks.CAVE_AIR.getDefaultState(), 3, _snowmanxxxx, _snowmanxxx, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 4, _snowmanxxxx, _snowmanxxx, boundingBox);
            }

            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 0, 4, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3, 4, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 4, 4, _snowmanxxx, boundingBox);
         }

         return true;
      }
   }

   public static class SpiralStaircase extends StrongholdGenerator.Piece {
      private final boolean isStructureStart;

      public SpiralStaircase(StructurePieceType structurePieceType, int chainLength, Random random, int x, int z) {
         super(structurePieceType, chainLength);
         this.isStructureStart = true;
         this.setOrientation(Direction.Type.HORIZONTAL.random(random));
         this.entryDoor = StrongholdGenerator.Piece.EntranceType.OPENING;
         if (this.getFacing().getAxis() == Direction.Axis.Z) {
            this.boundingBox = new BlockBox(x, 64, z, x + 5 - 1, 74, z + 5 - 1);
         } else {
            this.boundingBox = new BlockBox(x, 64, z, x + 5 - 1, 74, z + 5 - 1);
         }
      }

      public SpiralStaircase(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_SPIRAL_STAIRCASE, chainLength);
         this.isStructureStart = false;
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
      }

      public SpiralStaircase(StructurePieceType _snowman, CompoundTag _snowman) {
         super(_snowman, _snowman);
         this.isStructureStart = _snowman.getBoolean("Source");
      }

      public SpiralStaircase(StructureManager _snowman, CompoundTag _snowman) {
         this(StructurePieceType.STRONGHOLD_SPIRAL_STAIRCASE, _snowman);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Source", this.isStructureStart);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         if (this.isStructureStart) {
            StrongholdGenerator.activePieceType = StrongholdGenerator.FiveWayCrossing.class;
         }

         this.fillForwardOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
      }

      public static StrongholdGenerator.SpiralStaircase create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 11, 5, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.SpiralStaircase(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 10, 4, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 1, 7, 0);
         this.generateEntrance(_snowman, random, boundingBox, StrongholdGenerator.Piece.EntranceType.OPENING, 1, 1, 4);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 2, 6, 1, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 1, boundingBox);
         this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 6, 1, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 2, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, 3, boundingBox);
         this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 5, 3, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, 3, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 3, boundingBox);
         this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 4, 3, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 2, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3, 2, 1, boundingBox);
         this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 3, 1, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 2, 2, 1, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 1, boundingBox);
         this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 2, 1, boundingBox);
         this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 2, boundingBox);
         this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 1, 3, boundingBox);
         return true;
      }
   }

   public static class SquareRoom extends StrongholdGenerator.Piece {
      protected final int roomType;

      public SquareRoom(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_SQUARE_ROOM, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
         this.roomType = random.nextInt(5);
      }

      public SquareRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_SQUARE_ROOM, _snowman);
         this.roomType = _snowman.getInt("Type");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putInt("Type", this.roomType);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((StrongholdGenerator.Start)start, pieces, random, 4, 1);
         this.fillNWOpening((StrongholdGenerator.Start)start, pieces, random, 1, 4);
         this.fillSEOpening((StrongholdGenerator.Start)start, pieces, random, 1, 4);
      }

      public static StrongholdGenerator.SquareRoom create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 7, 11, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.SquareRoom(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 10, 6, 10, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 4, 1, 0);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 10, 6, 3, 10, AIR, AIR, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 4, 0, 3, 6, AIR, AIR, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 1, 4, 10, 3, 6, AIR, AIR, false);
         switch (this.roomType) {
            case 0:
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, boundingBox);
               this.addBlock(_snowman, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.WEST), 4, 3, 5, boundingBox);
               this.addBlock(_snowman, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.EAST), 6, 3, 5, boundingBox);
               this.addBlock(_snowman, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.SOUTH), 5, 3, 4, boundingBox);
               this.addBlock(_snowman, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.NORTH), 5, 3, 6, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 4, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 5, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 6, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 4, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 5, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 6, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 4, boundingBox);
               this.addBlock(_snowman, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 6, boundingBox);
               break;
            case 1:
               for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
                  this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3, 1, 3 + _snowmanxxx, boundingBox);
                  this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 7, 1, 3 + _snowmanxxx, boundingBox);
                  this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3 + _snowmanxxx, 1, 3, boundingBox);
                  this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3 + _snowmanxxx, 1, 7, boundingBox);
               }

               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, boundingBox);
               this.addBlock(_snowman, Blocks.WATER.getDefaultState(), 5, 4, 5, boundingBox);
               break;
            case 2:
               for (int _snowmanxxx = 1; _snowmanxxx <= 9; _snowmanxxx++) {
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 1, 3, _snowmanxxx, boundingBox);
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 9, 3, _snowmanxxx, boundingBox);
               }

               for (int _snowmanxxx = 1; _snowmanxxx <= 9; _snowmanxxx++) {
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), _snowmanxxx, 3, 1, boundingBox);
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), _snowmanxxx, 3, 9, boundingBox);
               }

               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 4, boundingBox);
               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 6, boundingBox);
               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 4, boundingBox);
               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 6, boundingBox);
               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, boundingBox);
               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, boundingBox);
               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 4, 3, 5, boundingBox);
               this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 6, 3, 5, boundingBox);

               for (int _snowmanxxx = 1; _snowmanxxx <= 3; _snowmanxxx++) {
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 4, _snowmanxxx, 4, boundingBox);
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 6, _snowmanxxx, 4, boundingBox);
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 4, _snowmanxxx, 6, boundingBox);
                  this.addBlock(_snowman, Blocks.COBBLESTONE.getDefaultState(), 6, _snowmanxxx, 6, boundingBox);
               }

               this.addBlock(_snowman, Blocks.TORCH.getDefaultState(), 5, 3, 5, boundingBox);

               for (int _snowmanxxx = 2; _snowmanxxx <= 8; _snowmanxxx++) {
                  this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 2, 3, _snowmanxxx, boundingBox);
                  this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 3, 3, _snowmanxxx, boundingBox);
                  if (_snowmanxxx <= 3 || _snowmanxxx >= 7) {
                     this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 4, 3, _snowmanxxx, boundingBox);
                     this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 5, 3, _snowmanxxx, boundingBox);
                     this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 6, 3, _snowmanxxx, boundingBox);
                  }

                  this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 7, 3, _snowmanxxx, boundingBox);
                  this.addBlock(_snowman, Blocks.OAK_PLANKS.getDefaultState(), 8, 3, _snowmanxxx, boundingBox);
               }

               BlockState _snowmanxxx = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST);
               this.addBlock(_snowman, _snowmanxxx, 9, 1, 3, boundingBox);
               this.addBlock(_snowman, _snowmanxxx, 9, 2, 3, boundingBox);
               this.addBlock(_snowman, _snowmanxxx, 9, 3, 3, boundingBox);
               this.addChest(_snowman, boundingBox, random, 3, 4, 8, LootTables.STRONGHOLD_CROSSING_CHEST);
         }

         return true;
      }
   }

   public static class Stairs extends StrongholdGenerator.Piece {
      public Stairs(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.STRONGHOLD_STAIRS, chainLength);
         this.setOrientation(orientation);
         this.entryDoor = this.getRandomEntrance(random);
         this.boundingBox = boundingBox;
      }

      public Stairs(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_STAIRS, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((StrongholdGenerator.Start)start, pieces, random, 1, 1);
      }

      public static StrongholdGenerator.Stairs create(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 11, 8, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new StrongholdGenerator.Stairs(chainLength, random, _snowman, orientation)
            : null;
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 10, 7, true, random, StrongholdGenerator.STONE_BRICK_RANDOMIZER);
         this.generateEntrance(_snowman, random, boundingBox, this.entryDoor, 1, 7, 0);
         this.generateEntrance(_snowman, random, boundingBox, StrongholdGenerator.Piece.EntranceType.OPENING, 1, 1, 7);
         BlockState _snowmanxxx = Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);

         for (int _snowmanxxxx = 0; _snowmanxxxx < 6; _snowmanxxxx++) {
            this.addBlock(_snowman, _snowmanxxx, 1, 6 - _snowmanxxxx, 1 + _snowmanxxxx, boundingBox);
            this.addBlock(_snowman, _snowmanxxx, 2, 6 - _snowmanxxxx, 1 + _snowmanxxxx, boundingBox);
            this.addBlock(_snowman, _snowmanxxx, 3, 6 - _snowmanxxxx, 1 + _snowmanxxxx, boundingBox);
            if (_snowmanxxxx < 5) {
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 1, 5 - _snowmanxxxx, 1 + _snowmanxxxx, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 2, 5 - _snowmanxxxx, 1 + _snowmanxxxx, boundingBox);
               this.addBlock(_snowman, Blocks.STONE_BRICKS.getDefaultState(), 3, 5 - _snowmanxxxx, 1 + _snowmanxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class Start extends StrongholdGenerator.SpiralStaircase {
      public StrongholdGenerator.PieceData lastPiece;
      @Nullable
      public StrongholdGenerator.PortalRoom portalRoom;
      public final List<StructurePiece> pieces = Lists.newArrayList();

      public Start(Random random, int _snowman, int _snowman) {
         super(StructurePieceType.STRONGHOLD_START, 0, random, _snowman, _snowman);
      }

      public Start(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.STRONGHOLD_START, _snowman);
      }
   }

   static class StoneBrickRandomizer extends StructurePiece.BlockRandomizer {
      private StoneBrickRandomizer() {
      }

      @Override
      public void setBlock(Random random, int x, int y, int z, boolean placeBlock) {
         if (placeBlock) {
            float _snowman = random.nextFloat();
            if (_snowman < 0.2F) {
               this.block = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
            } else if (_snowman < 0.5F) {
               this.block = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
            } else if (_snowman < 0.55F) {
               this.block = Blocks.INFESTED_STONE_BRICKS.getDefaultState();
            } else {
               this.block = Blocks.STONE_BRICKS.getDefaultState();
            }
         } else {
            this.block = Blocks.CAVE_AIR.getDefaultState();
         }
      }
   }

   public abstract static class Turn extends StrongholdGenerator.Piece {
      protected Turn(StructurePieceType _snowman, int _snowman) {
         super(_snowman, _snowman);
      }

      public Turn(StructurePieceType _snowman, CompoundTag _snowman) {
         super(_snowman, _snowman);
      }
   }
}
