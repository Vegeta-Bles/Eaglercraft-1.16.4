package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class NetherFortressGenerator {
   private static final NetherFortressGenerator.PieceData[] ALL_BRIDGE_PIECES = new NetherFortressGenerator.PieceData[]{
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.Bridge.class, 30, 0, true),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.BridgeCrossing.class, 10, 4),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.BridgeSmallCrossing.class, 10, 4),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.BridgeStairs.class, 10, 3),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.BridgePlatform.class, 5, 2),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.CorridorExit.class, 5, 1)
   };
   private static final NetherFortressGenerator.PieceData[] ALL_CORRIDOR_PIECES = new NetherFortressGenerator.PieceData[]{
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.SmallCorridor.class, 25, 0, true),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.CorridorCrossing.class, 15, 5),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.CorridorRightTurn.class, 5, 10),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.CorridorLeftTurn.class, 5, 10),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.CorridorStairs.class, 10, 3, true),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.CorridorBalcony.class, 7, 2),
      new NetherFortressGenerator.PieceData(NetherFortressGenerator.CorridorNetherWartsRoom.class, 5, 2)
   };

   private static NetherFortressGenerator.Piece createPiece(
      NetherFortressGenerator.PieceData pieceData, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
   ) {
      Class<? extends NetherFortressGenerator.Piece> _snowman = pieceData.pieceType;
      NetherFortressGenerator.Piece _snowmanx = null;
      if (_snowman == NetherFortressGenerator.Bridge.class) {
         _snowmanx = NetherFortressGenerator.Bridge.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.BridgeCrossing.class) {
         _snowmanx = NetherFortressGenerator.BridgeCrossing.create(pieces, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.BridgeSmallCrossing.class) {
         _snowmanx = NetherFortressGenerator.BridgeSmallCrossing.create(pieces, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.BridgeStairs.class) {
         _snowmanx = NetherFortressGenerator.BridgeStairs.create(pieces, x, y, z, chainLength, orientation);
      } else if (_snowman == NetherFortressGenerator.BridgePlatform.class) {
         _snowmanx = NetherFortressGenerator.BridgePlatform.create(pieces, x, y, z, chainLength, orientation);
      } else if (_snowman == NetherFortressGenerator.CorridorExit.class) {
         _snowmanx = NetherFortressGenerator.CorridorExit.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.SmallCorridor.class) {
         _snowmanx = NetherFortressGenerator.SmallCorridor.create(pieces, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.CorridorRightTurn.class) {
         _snowmanx = NetherFortressGenerator.CorridorRightTurn.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.CorridorLeftTurn.class) {
         _snowmanx = NetherFortressGenerator.CorridorLeftTurn.create(pieces, random, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.CorridorStairs.class) {
         _snowmanx = NetherFortressGenerator.CorridorStairs.create(pieces, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.CorridorBalcony.class) {
         _snowmanx = NetherFortressGenerator.CorridorBalcony.create(pieces, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.CorridorCrossing.class) {
         _snowmanx = NetherFortressGenerator.CorridorCrossing.create(pieces, x, y, z, orientation, chainLength);
      } else if (_snowman == NetherFortressGenerator.CorridorNetherWartsRoom.class) {
         _snowmanx = NetherFortressGenerator.CorridorNetherWartsRoom.create(pieces, x, y, z, orientation, chainLength);
      }

      return _snowmanx;
   }

   public static class Bridge extends NetherFortressGenerator.Piece {
      public Bridge(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public Bridge(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 1, 3, false);
      }

      public static NetherFortressGenerator.Bridge create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -3, 0, 5, 10, 19, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.Bridge(chainLength, random, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 5, 0, 3, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxx = 0; _snowmanxxx <= 4; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx <= 2; _snowmanxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxx, -1, _snowmanxxxx, boundingBox);
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxx, -1, 18 - _snowmanxxxx, boundingBox);
            }
         }

         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         BlockState _snowmanxxxx = _snowmanxxx.with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxxx = _snowmanxxx.with(FenceBlock.WEST, Boolean.valueOf(true));
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 1, 0, 4, 1, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 4, 0, 4, 4, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 14, 0, 4, 14, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 17, 0, 4, 17, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 1, 4, 4, 1, _snowmanxxxxx, _snowmanxxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 3, 4, 4, 4, 4, _snowmanxxxxx, _snowmanxxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 3, 14, 4, 4, 14, _snowmanxxxxx, _snowmanxxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 1, 17, 4, 4, 17, _snowmanxxxxx, _snowmanxxxxx, false);
         return true;
      }
   }

   public static class BridgeCrossing extends NetherFortressGenerator.Piece {
      public BridgeCrossing(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      protected BridgeCrossing(Random random, int x, int z) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, 0);
         this.setOrientation(Direction.Type.HORIZONTAL.random(random));
         if (this.getFacing().getAxis() == Direction.Axis.Z) {
            this.boundingBox = new BlockBox(x, 64, z, x + 19 - 1, 73, z + 19 - 1);
         } else {
            this.boundingBox = new BlockBox(x, 64, z, x + 19 - 1, 73, z + 19 - 1);
         }
      }

      protected BridgeCrossing(StructurePieceType _snowman, CompoundTag _snowman) {
         super(_snowman, _snowman);
      }

      public BridgeCrossing(StructureManager _snowman, CompoundTag _snowman) {
         this(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 8, 3, false);
         this.fillNWOpening((NetherFortressGenerator.Start)start, pieces, random, 3, 8, false);
         this.fillSEOpening((NetherFortressGenerator.Start)start, pieces, random, 3, 8, false);
      }

      public static NetherFortressGenerator.BridgeCrossing create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -8, -3, 0, 19, 10, 19, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.BridgeCrossing(chainLength, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 5, 0, 10, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 8, 18, 7, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxx = 7; _snowmanxxx <= 11; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx <= 2; _snowmanxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxx, -1, _snowmanxxxx, boundingBox);
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxx, -1, 18 - _snowmanxxxx, boundingBox);
            }
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxx = 0; _snowmanxxx <= 2; _snowmanxxx++) {
            for (int _snowmanxxxx = 7; _snowmanxxxx <= 11; _snowmanxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxx, -1, _snowmanxxxx, boundingBox);
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 18 - _snowmanxxx, -1, _snowmanxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class BridgeEnd extends NetherFortressGenerator.Piece {
      private final int seed;

      public BridgeEnd(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
         this.seed = random.nextInt();
      }

      public BridgeEnd(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END, _snowman);
         this.seed = _snowman.getInt("Seed");
      }

      public static NetherFortressGenerator.BridgeEnd create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -3, 0, 5, 10, 8, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.BridgeEnd(chainLength, random, _snowman, orientation)
            : null;
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putInt("Seed", this.seed);
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
         Random _snowmanxxx = new Random((long)this.seed);

         for (int _snowmanxxxx = 0; _snowmanxxxx <= 4; _snowmanxxxx++) {
            for (int _snowmanxxxxx = 3; _snowmanxxxxx <= 4; _snowmanxxxxx++) {
               int _snowmanxxxxxx = _snowmanxxx.nextInt(8);
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  0,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  false
               );
            }
         }

         int _snowmanxxxx = _snowmanxxx.nextInt(8);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 0, 0, 5, _snowmanxxxx, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         _snowmanxxxx = _snowmanxxx.nextInt(8);
         this.fillWithOutline(_snowman, boundingBox, 4, 5, 0, 4, 5, _snowmanxxxx, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 4; _snowmanxxxxx++) {
            int _snowmanxxxxxx = _snowmanxxx.nextInt(5);
            this.fillWithOutline(
               _snowman, boundingBox, _snowmanxxxxx, 2, 0, _snowmanxxxxx, 2, _snowmanxxxxxx, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false
            );
         }

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 4; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 1; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = _snowmanxxx.nextInt(3);
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  0,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxx,
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  false
               );
            }
         }

         return true;
      }
   }

   public static class BridgePlatform extends NetherFortressGenerator.Piece {
      private boolean hasBlazeSpawner;

      public BridgePlatform(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_PLATFORM, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public BridgePlatform(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_PLATFORM, _snowman);
         this.hasBlazeSpawner = _snowman.getBoolean("Mob");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Mob", this.hasBlazeSpawner);
      }

      public static NetherFortressGenerator.BridgePlatform create(List<StructurePiece> pieces, int x, int y, int z, int chainLength, Direction orientation) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -2, 0, 0, 7, 8, 9, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.BridgePlatform(chainLength, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 6, 7, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.addBlock(_snowman, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true)), 1, 6, 3, boundingBox);
         this.addBlock(_snowman, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, Boolean.valueOf(true)), 5, 6, 3, boundingBox);
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, Boolean.valueOf(true)).with(FenceBlock.NORTH, Boolean.valueOf(true)),
            0,
            6,
            3,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true)).with(FenceBlock.NORTH, Boolean.valueOf(true)),
            6,
            6,
            3,
            boundingBox
         );
         this.fillWithOutline(_snowman, boundingBox, 0, 6, 4, 0, 6, 7, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 6, 4, 6, 6, 7, _snowmanxxxx, _snowmanxxxx, false);
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, Boolean.valueOf(true)).with(FenceBlock.SOUTH, Boolean.valueOf(true)),
            0,
            6,
            8,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true)).with(FenceBlock.SOUTH, Boolean.valueOf(true)),
            6,
            6,
            8,
            boundingBox
         );
         this.fillWithOutline(_snowman, boundingBox, 1, 6, 8, 5, 6, 8, _snowmanxxx, _snowmanxxx, false);
         this.addBlock(_snowman, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, Boolean.valueOf(true)), 1, 7, 8, boundingBox);
         this.fillWithOutline(_snowman, boundingBox, 2, 7, 8, 4, 7, 8, _snowmanxxx, _snowmanxxx, false);
         this.addBlock(_snowman, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true)), 5, 7, 8, boundingBox);
         this.addBlock(_snowman, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, Boolean.valueOf(true)), 2, 8, 8, boundingBox);
         this.addBlock(_snowman, _snowmanxxx, 3, 8, 8, boundingBox);
         this.addBlock(_snowman, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true)), 4, 8, 8, boundingBox);
         if (!this.hasBlazeSpawner) {
            BlockPos _snowmanxxxxx = new BlockPos(this.applyXTransform(3, 5), this.applyYTransform(5), this.applyZTransform(3, 5));
            if (boundingBox.contains(_snowmanxxxxx)) {
               this.hasBlazeSpawner = true;
               _snowman.setBlockState(_snowmanxxxxx, Blocks.SPAWNER.getDefaultState(), 2);
               BlockEntity _snowmanxxxxxx = _snowman.getBlockEntity(_snowmanxxxxx);
               if (_snowmanxxxxxx instanceof MobSpawnerBlockEntity) {
                  ((MobSpawnerBlockEntity)_snowmanxxxxxx).getLogic().setEntityId(EntityType.BLAZE);
               }
            }
         }

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 6; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 6; _snowmanxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxx, -1, _snowmanxxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class BridgeSmallCrossing extends NetherFortressGenerator.Piece {
      public BridgeSmallCrossing(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_SMALL_CROSSING, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public BridgeSmallCrossing(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_SMALL_CROSSING, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 2, 0, false);
         this.fillNWOpening((NetherFortressGenerator.Start)start, pieces, random, 0, 2, false);
         this.fillSEOpening((NetherFortressGenerator.Start)start, pieces, random, 0, 2, false);
      }

      public static NetherFortressGenerator.BridgeSmallCrossing create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -2, 0, 0, 7, 9, 7, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.BridgeSmallCrossing(chainLength, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 6, 7, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.fillWithOutline(_snowman, boundingBox, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 0, 4, 5, 0, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 6, 4, 5, 6, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 2, 0, 5, 4, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 5, 2, 6, 5, 4, _snowmanxxxx, _snowmanxxxx, false);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 6; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 6; _snowmanxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxx, -1, _snowmanxxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class BridgeStairs extends NetherFortressGenerator.Piece {
      public BridgeStairs(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STAIRS, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public BridgeStairs(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STAIRS, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillSEOpening((NetherFortressGenerator.Start)start, pieces, random, 6, 2, false);
      }

      public static NetherFortressGenerator.BridgeStairs create(List<StructurePiece> pieces, int x, int y, int z, int chainlength, Direction orientation) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -2, 0, 0, 7, 11, 7, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.BridgeStairs(chainlength, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 6, 10, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 2, 0, 5, 4, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 3, 2, 6, 5, 2, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 3, 4, 6, 5, 4, _snowmanxxxx, _snowmanxxxx, false);
         this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 5, 2, 5, boundingBox);
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 8, 2, 6, 8, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 0, 4, 5, 0, _snowmanxxx, _snowmanxxx, false);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 6; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 6; _snowmanxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxx, -1, _snowmanxxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class CorridorBalcony extends NetherFortressGenerator.Piece {
      public CorridorBalcony(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_BALCONY, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public CorridorBalcony(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_BALCONY, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int _snowman = 1;
         Direction _snowmanx = this.getFacing();
         if (_snowmanx == Direction.WEST || _snowmanx == Direction.NORTH) {
            _snowman = 5;
         }

         this.fillNWOpening((NetherFortressGenerator.Start)start, pieces, random, 0, _snowman, random.nextInt(8) > 0);
         this.fillSEOpening((NetherFortressGenerator.Start)start, pieces, random, 0, _snowman, random.nextInt(8) > 0);
      }

      public static NetherFortressGenerator.CorridorBalcony create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -3, 0, 0, 9, 7, 9, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.CorridorBalcony(chainLength, _snowman, orientation)
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
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 8, 5, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 0, 1, 4, 0, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 3, 0, 7, 4, 0, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 4, 2, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 1, 4, 7, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 8, 7, 3, 8, _snowmanxxxx, _snowmanxxxx, false);
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, Boolean.valueOf(true)).with(FenceBlock.SOUTH, Boolean.valueOf(true)),
            0,
            3,
            8,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, Boolean.valueOf(true)).with(FenceBlock.SOUTH, Boolean.valueOf(true)),
            8,
            3,
            8,
            boundingBox
         );
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 6, 0, 3, 7, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 8, 3, 6, 8, 3, 7, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 5, 1, 5, 5, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 4, 5, 7, 5, 5, _snowmanxxxx, _snowmanxxxx, false);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 5; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 8; _snowmanxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxx, -1, _snowmanxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class CorridorCrossing extends NetherFortressGenerator.Piece {
      public CorridorCrossing(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_CROSSING, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public CorridorCrossing(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_CROSSING, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 1, 0, true);
         this.fillNWOpening((NetherFortressGenerator.Start)start, pieces, random, 0, 1, true);
         this.fillSEOpening((NetherFortressGenerator.Start)start, pieces, random, 0, 1, true);
      }

      public static NetherFortressGenerator.CorridorCrossing create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.CorridorCrossing(chainLength, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxx = 0; _snowmanxxx <= 4; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx <= 4; _snowmanxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxx, -1, _snowmanxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class CorridorExit extends NetherFortressGenerator.Piece {
      public CorridorExit(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_EXIT, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public CorridorExit(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_EXIT, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 5, 3, true);
      }

      public static NetherFortressGenerator.CorridorExit create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -5, -3, 0, 13, 14, 13, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.CorridorExit(chainLength, random, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));

         for (int _snowmanxxxxx = 1; _snowmanxxxxx <= 11; _snowmanxxxxx += 2) {
            this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxx, 10, 0, _snowmanxxxxx, 11, 0, _snowmanxxx, _snowmanxxx, false);
            this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxx, 10, 12, _snowmanxxxxx, 11, 12, _snowmanxxx, _snowmanxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 10, _snowmanxxxxx, 0, 11, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 12, 10, _snowmanxxxxx, 12, 11, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxx, false);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxx, 13, 0, boundingBox);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxx, 13, 12, boundingBox);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 0, 13, _snowmanxxxxx, boundingBox);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 12, 13, _snowmanxxxxx, boundingBox);
            if (_snowmanxxxxx != 11) {
               this.addBlock(_snowman, _snowmanxxx, _snowmanxxxxx + 1, 13, 0, boundingBox);
               this.addBlock(_snowman, _snowmanxxx, _snowmanxxxxx + 1, 13, 12, boundingBox);
               this.addBlock(_snowman, _snowmanxxxx, 0, 13, _snowmanxxxxx + 1, boundingBox);
               this.addBlock(_snowman, _snowmanxxxx, 12, 13, _snowmanxxxxx + 1, boundingBox);
            }
         }

         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            0,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            12,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            12,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            0,
            boundingBox
         );

         for (int _snowmanxxxxxx = 3; _snowmanxxxxxx <= 9; _snowmanxxxxxx += 2) {
            this.fillWithOutline(
               _snowman,
               boundingBox,
               1,
               7,
               _snowmanxxxxxx,
               1,
               8,
               _snowmanxxxxxx,
               _snowmanxxxx.with(FenceBlock.WEST, Boolean.valueOf(true)),
               _snowmanxxxx.with(FenceBlock.WEST, Boolean.valueOf(true)),
               false
            );
            this.fillWithOutline(
               _snowman,
               boundingBox,
               11,
               7,
               _snowmanxxxxxx,
               11,
               8,
               _snowmanxxxxxx,
               _snowmanxxxx.with(FenceBlock.EAST, Boolean.valueOf(true)),
               _snowmanxxxx.with(FenceBlock.EAST, Boolean.valueOf(true)),
               false
            );
         }

         this.fillWithOutline(_snowman, boundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxxxxx = 4; _snowmanxxxxxx <= 8; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= 2; _snowmanxxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxx, -1, _snowmanxxxxxxx, boundingBox);
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxx, -1, 12 - _snowmanxxxxxxx, boundingBox);
            }
         }

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 2; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = 4; _snowmanxxxxxxx <= 8; _snowmanxxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxx, -1, _snowmanxxxxxxx, boundingBox);
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 12 - _snowmanxxxxxx, -1, _snowmanxxxxxxx, boundingBox);
            }
         }

         this.fillWithOutline(_snowman, boundingBox, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 6, 1, 6, 6, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 6, 0, 6, boundingBox);
         this.addBlock(_snowman, Blocks.LAVA.getDefaultState(), 6, 5, 6, boundingBox);
         BlockPos _snowmanxxxxxx = new BlockPos(this.applyXTransform(6, 6), this.applyYTransform(5), this.applyZTransform(6, 6));
         if (boundingBox.contains(_snowmanxxxxxx)) {
            _snowman.getFluidTickScheduler().schedule(_snowmanxxxxxx, Fluids.LAVA, 0);
         }

         return true;
      }
   }

   public static class CorridorLeftTurn extends NetherFortressGenerator.Piece {
      private boolean containsChest;

      public CorridorLeftTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_LEFT_TURN, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
         this.containsChest = random.nextInt(3) == 0;
      }

      public CorridorLeftTurn(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_LEFT_TURN, _snowman);
         this.containsChest = _snowman.getBoolean("Chest");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Chest", this.containsChest);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillNWOpening((NetherFortressGenerator.Start)start, pieces, random, 0, 1, true);
      }

      public static NetherFortressGenerator.CorridorLeftTurn create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.CorridorLeftTurn(chainLength, random, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 3, 1, 4, 4, 1, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 3, 3, 4, 4, 3, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 4, 1, 4, 4, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 3, 4, 3, 4, 4, _snowmanxxx, _snowmanxxx, false);
         if (this.containsChest && boundingBox.contains(new BlockPos(this.applyXTransform(3, 3), this.applyYTransform(2), this.applyZTransform(3, 3)))) {
            this.containsChest = false;
            this.addChest(_snowman, boundingBox, random, 3, 2, 3, LootTables.NETHER_BRIDGE_CHEST);
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 4; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 4; _snowmanxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxx, -1, _snowmanxxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class CorridorNetherWartsRoom extends NetherFortressGenerator.Piece {
      public CorridorNetherWartsRoom(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_NETHER_WARTS_ROOM, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public CorridorNetherWartsRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_NETHER_WARTS_ROOM, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 5, 3, true);
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 5, 11, true);
      }

      public static NetherFortressGenerator.CorridorNetherWartsRoom create(
         List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainlength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -5, -3, 0, 13, 14, 13, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.CorridorNetherWartsRoom(chainlength, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         BlockState _snowmanxxxxx = _snowmanxxxx.with(FenceBlock.WEST, Boolean.valueOf(true));
         BlockState _snowmanxxxxxx = _snowmanxxxx.with(FenceBlock.EAST, Boolean.valueOf(true));

         for (int _snowmanxxxxxxx = 1; _snowmanxxxxxxx <= 11; _snowmanxxxxxxx += 2) {
            this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxxx, 10, 0, _snowmanxxxxxxx, 11, 0, _snowmanxxx, _snowmanxxx, false);
            this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxxx, 10, 12, _snowmanxxxxxxx, 11, 12, _snowmanxxx, _snowmanxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 10, _snowmanxxxxxxx, 0, 11, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 12, 10, _snowmanxxxxxxx, 12, 11, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxx, false);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxxx, 13, 0, boundingBox);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxxx, 13, 12, boundingBox);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 0, 13, _snowmanxxxxxxx, boundingBox);
            this.addBlock(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 12, 13, _snowmanxxxxxxx, boundingBox);
            if (_snowmanxxxxxxx != 11) {
               this.addBlock(_snowman, _snowmanxxx, _snowmanxxxxxxx + 1, 13, 0, boundingBox);
               this.addBlock(_snowman, _snowmanxxx, _snowmanxxxxxxx + 1, 13, 12, boundingBox);
               this.addBlock(_snowman, _snowmanxxxx, 0, 13, _snowmanxxxxxxx + 1, boundingBox);
               this.addBlock(_snowman, _snowmanxxxx, 12, 13, _snowmanxxxxxxx + 1, boundingBox);
            }
         }

         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            0,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.EAST, Boolean.valueOf(true)),
            0,
            13,
            12,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, Boolean.valueOf(true)).with(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            12,
            boundingBox
         );
         this.addBlock(
            _snowman,
            Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, Boolean.valueOf(true)).with(FenceBlock.WEST, Boolean.valueOf(true)),
            12,
            13,
            0,
            boundingBox
         );

         for (int _snowmanxxxxxxxx = 3; _snowmanxxxxxxxx <= 9; _snowmanxxxxxxxx += 2) {
            this.fillWithOutline(_snowman, boundingBox, 1, 7, _snowmanxxxxxxxx, 1, 8, _snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 11, 7, _snowmanxxxxxxxx, 11, 8, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxx, false);
         }

         BlockState _snowmanxxxxxxxx = Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= 6; _snowmanxxxxxxxxx++) {
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx + 4;

            for (int _snowmanxxxxxxxxxxx = 5; _snowmanxxxxxxxxxxx <= 7; _snowmanxxxxxxxxxxx++) {
               this.addBlock(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, 5 + _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, boundingBox);
            }

            if (_snowmanxxxxxxxxxx >= 5 && _snowmanxxxxxxxxxx <= 8) {
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  5,
                  5,
                  _snowmanxxxxxxxxxx,
                  7,
                  _snowmanxxxxxxxxx + 4,
                  _snowmanxxxxxxxxxx,
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  false
               );
            } else if (_snowmanxxxxxxxxxx >= 9 && _snowmanxxxxxxxxxx <= 10) {
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  5,
                  8,
                  _snowmanxxxxxxxxxx,
                  7,
                  _snowmanxxxxxxxxx + 4,
                  _snowmanxxxxxxxxxx,
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  Blocks.NETHER_BRICKS.getDefaultState(),
                  false
               );
            }

            if (_snowmanxxxxxxxxx >= 1) {
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  5,
                  6 + _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxx,
                  7,
                  9 + _snowmanxxxxxxxxx,
                  _snowmanxxxxxxxxxx,
                  Blocks.AIR.getDefaultState(),
                  Blocks.AIR.getDefaultState(),
                  false
               );
            }
         }

         for (int _snowmanxxxxxxxxx = 5; _snowmanxxxxxxxxx <= 7; _snowmanxxxxxxxxx++) {
            this.addBlock(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 12, 11, boundingBox);
         }

         this.fillWithOutline(_snowman, boundingBox, 5, 6, 7, 5, 7, 7, _snowmanxxxxxx, _snowmanxxxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 6, 7, 7, 7, 7, _snowmanxxxxx, _snowmanxxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 13, 12, 7, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         BlockState _snowmanxxxxxxxxx = _snowmanxxxxxxxx.with(StairsBlock.FACING, Direction.EAST);
         BlockState _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.with(StairsBlock.FACING, Direction.WEST);
         this.addBlock(_snowman, _snowmanxxxxxxxxxx, 4, 5, 2, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxxx, 4, 5, 3, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxxx, 4, 5, 9, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxxx, 4, 5, 10, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxx, 8, 5, 2, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxx, 8, 5, 3, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxx, 8, 5, 9, boundingBox);
         this.addBlock(_snowman, _snowmanxxxxxxxxx, 8, 5, 10, boundingBox);
         this.fillWithOutline(_snowman, boundingBox, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxxxxxxxxxx = 4; _snowmanxxxxxxxxxxx <= 8; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx <= 2; _snowmanxxxxxxxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxxxxxxx, -1, _snowmanxxxxxxxxxxxx, boundingBox);
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxxxxxxx, -1, 12 - _snowmanxxxxxxxxxxxx, boundingBox);
            }
         }

         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx <= 2; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = 4; _snowmanxxxxxxxxxxxx <= 8; _snowmanxxxxxxxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxxxxxxx, -1, _snowmanxxxxxxxxxxxx, boundingBox);
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), 12 - _snowmanxxxxxxxxxxx, -1, _snowmanxxxxxxxxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class CorridorRightTurn extends NetherFortressGenerator.Piece {
      private boolean containsChest;

      public CorridorRightTurn(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_RIGHT_TURN, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
         this.containsChest = random.nextInt(3) == 0;
      }

      public CorridorRightTurn(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_RIGHT_TURN, _snowman);
         this.containsChest = _snowman.getBoolean("Chest");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("Chest", this.containsChest);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillSEOpening((NetherFortressGenerator.Start)start, pieces, random, 0, 1, true);
      }

      public static NetherFortressGenerator.CorridorRightTurn create(
         List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
      ) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.CorridorRightTurn(chainLength, random, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.WEST, Boolean.valueOf(true))
            .with(FenceBlock.EAST, Boolean.valueOf(true));
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 1, 0, 4, 1, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 3, 0, 4, 3, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 4, 1, 4, 4, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 3, 4, 3, 4, 4, _snowmanxxx, _snowmanxxx, false);
         if (this.containsChest && boundingBox.contains(new BlockPos(this.applyXTransform(1, 3), this.applyYTransform(2), this.applyZTransform(1, 3)))) {
            this.containsChest = false;
            this.addChest(_snowman, boundingBox, random, 1, 2, 3, LootTables.NETHER_BRIDGE_CHEST);
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 4; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 4; _snowmanxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxx, -1, _snowmanxxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class CorridorStairs extends NetherFortressGenerator.Piece {
      public CorridorStairs(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_STAIRS, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public CorridorStairs(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_CORRIDOR_STAIRS, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 1, 0, true);
      }

      public static NetherFortressGenerator.CorridorStairs create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, -7, 0, 5, 14, 10, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.CorridorStairs(chainLength, _snowman, orientation)
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
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
         BlockState _snowmanxxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));

         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 9; _snowmanxxxxx++) {
            int _snowmanxxxxxx = Math.max(1, 7 - _snowmanxxxxx);
            int _snowmanxxxxxxx = Math.min(Math.max(_snowmanxxxxxx + 5, 14 - _snowmanxxxxx), 13);
            int _snowmanxxxxxxxx = _snowmanxxxxx;
            this.fillWithOutline(
               _snowman, boundingBox, 0, 0, _snowmanxxxxx, 4, _snowmanxxxxxx, _snowmanxxxxx, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false
            );
            this.fillWithOutline(
               _snowman, boundingBox, 1, _snowmanxxxxxx + 1, _snowmanxxxxx, 3, _snowmanxxxxxxx - 1, _snowmanxxxxx, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false
            );
            if (_snowmanxxxxx <= 6) {
               this.addBlock(_snowman, _snowmanxxx, 1, _snowmanxxxxxx + 1, _snowmanxxxxx, boundingBox);
               this.addBlock(_snowman, _snowmanxxx, 2, _snowmanxxxxxx + 1, _snowmanxxxxx, boundingBox);
               this.addBlock(_snowman, _snowmanxxx, 3, _snowmanxxxxxx + 1, _snowmanxxxxx, boundingBox);
            }

            this.fillWithOutline(
               _snowman, boundingBox, 0, _snowmanxxxxxxx, _snowmanxxxxx, 4, _snowmanxxxxxxx, _snowmanxxxxx, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false
            );
            this.fillWithOutline(
               _snowman,
               boundingBox,
               0,
               _snowmanxxxxxx + 1,
               _snowmanxxxxx,
               0,
               _snowmanxxxxxxx - 1,
               _snowmanxxxxx,
               Blocks.NETHER_BRICKS.getDefaultState(),
               Blocks.NETHER_BRICKS.getDefaultState(),
               false
            );
            this.fillWithOutline(
               _snowman,
               boundingBox,
               4,
               _snowmanxxxxxx + 1,
               _snowmanxxxxx,
               4,
               _snowmanxxxxxxx - 1,
               _snowmanxxxxx,
               Blocks.NETHER_BRICKS.getDefaultState(),
               Blocks.NETHER_BRICKS.getDefaultState(),
               false
            );
            if ((_snowmanxxxxx & 1) == 0) {
               this.fillWithOutline(_snowman, boundingBox, 0, _snowmanxxxxxx + 2, _snowmanxxxxx, 0, _snowmanxxxxxx + 3, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxx, false);
               this.fillWithOutline(_snowman, boundingBox, 4, _snowmanxxxxxx + 2, _snowmanxxxxx, 4, _snowmanxxxxxx + 3, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxx, false);
            }

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= 4; _snowmanxxxxxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxxxxxxx, -1, _snowmanxxxxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   abstract static class Piece extends StructurePiece {
      protected Piece(StructurePieceType _snowman, int _snowman) {
         super(_snowman, _snowman);
      }

      public Piece(StructurePieceType _snowman, CompoundTag _snowman) {
         super(_snowman, _snowman);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
      }

      private int checkRemainingPieces(List<NetherFortressGenerator.PieceData> possiblePieces) {
         boolean _snowman = false;
         int _snowmanx = 0;

         for (NetherFortressGenerator.PieceData _snowmanxx : possiblePieces) {
            if (_snowmanxx.limit > 0 && _snowmanxx.generatedCount < _snowmanxx.limit) {
               _snowman = true;
            }

            _snowmanx += _snowmanxx.weight;
         }

         return _snowman ? _snowmanx : -1;
      }

      private NetherFortressGenerator.Piece pickPiece(
         NetherFortressGenerator.Start start,
         List<NetherFortressGenerator.PieceData> possiblePieces,
         List<StructurePiece> pieces,
         Random random,
         int x,
         int y,
         int z,
         Direction orientation,
         int chainLength
      ) {
         int _snowman = this.checkRemainingPieces(possiblePieces);
         boolean _snowmanx = _snowman > 0 && chainLength <= 30;
         int _snowmanxx = 0;

         while (_snowmanxx < 5 && _snowmanx) {
            _snowmanxx++;
            int _snowmanxxx = random.nextInt(_snowman);

            for (NetherFortressGenerator.PieceData _snowmanxxxx : possiblePieces) {
               _snowmanxxx -= _snowmanxxxx.weight;
               if (_snowmanxxx < 0) {
                  if (!_snowmanxxxx.canGenerate(chainLength) || _snowmanxxxx == start.lastPiece && !_snowmanxxxx.repeatable) {
                     break;
                  }

                  NetherFortressGenerator.Piece _snowmanxxxxx = NetherFortressGenerator.createPiece(_snowmanxxxx, pieces, random, x, y, z, orientation, chainLength);
                  if (_snowmanxxxxx != null) {
                     _snowmanxxxx.generatedCount++;
                     start.lastPiece = _snowmanxxxx;
                     if (!_snowmanxxxx.canGenerate()) {
                        possiblePieces.remove(_snowmanxxxx);
                     }

                     return _snowmanxxxxx;
                  }
               }
            }
         }

         return NetherFortressGenerator.BridgeEnd.create(pieces, random, x, y, z, orientation, chainLength);
      }

      private StructurePiece pieceGenerator(
         NetherFortressGenerator.Start start,
         List<StructurePiece> pieces,
         Random random,
         int x,
         int y,
         int z,
         @Nullable Direction orientation,
         int chainLength,
         boolean inside
      ) {
         if (Math.abs(x - start.getBoundingBox().minX) <= 112 && Math.abs(z - start.getBoundingBox().minZ) <= 112) {
            List<NetherFortressGenerator.PieceData> _snowman = start.bridgePieces;
            if (inside) {
               _snowman = start.corridorPieces;
            }

            StructurePiece _snowmanx = this.pickPiece(start, _snowman, pieces, random, x, y, z, orientation, chainLength + 1);
            if (_snowmanx != null) {
               pieces.add(_snowmanx);
               start.pieces.add(_snowmanx);
            }

            return _snowmanx;
         } else {
            return NetherFortressGenerator.BridgeEnd.create(pieces, random, x, y, z, orientation, chainLength);
         }
      }

      @Nullable
      protected StructurePiece fillForwardOpening(
         NetherFortressGenerator.Start start, List<StructurePiece> pieces, Random random, int leftRightOffset, int heightOffset, boolean inside
      ) {
         Direction _snowman = this.getFacing();
         if (_snowman != null) {
            switch (_snowman) {
               case NORTH:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ - 1,
                     _snowman,
                     this.getChainLength(),
                     inside
                  );
               case SOUTH:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.maxZ + 1,
                     _snowman,
                     this.getChainLength(),
                     inside
                  );
               case WEST:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     _snowman,
                     this.getChainLength(),
                     inside
                  );
               case EAST:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     _snowman,
                     this.getChainLength(),
                     inside
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece fillNWOpening(
         NetherFortressGenerator.Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset, boolean inside
      ) {
         Direction _snowman = this.getFacing();
         if (_snowman != null) {
            switch (_snowman) {
               case NORTH:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.WEST,
                     this.getChainLength(),
                     inside
                  );
               case SOUTH:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.WEST,
                     this.getChainLength(),
                     inside
                  );
               case WEST:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ - 1,
                     Direction.NORTH,
                     this.getChainLength(),
                     inside
                  );
               case EAST:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ - 1,
                     Direction.NORTH,
                     this.getChainLength(),
                     inside
                  );
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece fillSEOpening(
         NetherFortressGenerator.Start start, List<StructurePiece> pieces, Random random, int heightOffset, int leftRightOffset, boolean inside
      ) {
         Direction _snowman = this.getFacing();
         if (_snowman != null) {
            switch (_snowman) {
               case NORTH:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.EAST,
                     this.getChainLength(),
                     inside
                  );
               case SOUTH:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.minZ + leftRightOffset,
                     Direction.EAST,
                     this.getChainLength(),
                     inside
                  );
               case WEST:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.maxZ + 1,
                     Direction.SOUTH,
                     this.getChainLength(),
                     inside
                  );
               case EAST:
                  return this.pieceGenerator(
                     start,
                     pieces,
                     random,
                     this.boundingBox.minX + leftRightOffset,
                     this.boundingBox.minY + heightOffset,
                     this.boundingBox.maxZ + 1,
                     Direction.SOUTH,
                     this.getChainLength(),
                     inside
                  );
            }
         }

         return null;
      }

      protected static boolean isInbounds(BlockBox boundingBox) {
         return boundingBox != null && boundingBox.minY > 10;
      }
   }

   static class PieceData {
      public final Class<? extends NetherFortressGenerator.Piece> pieceType;
      public final int weight;
      public int generatedCount;
      public final int limit;
      public final boolean repeatable;

      public PieceData(Class<? extends NetherFortressGenerator.Piece> pieceType, int weight, int limit, boolean repeatable) {
         this.pieceType = pieceType;
         this.weight = weight;
         this.limit = limit;
         this.repeatable = repeatable;
      }

      public PieceData(Class<? extends NetherFortressGenerator.Piece> pieceType, int weight, int limit) {
         this(pieceType, weight, limit, false);
      }

      public boolean canGenerate(int chainLength) {
         return this.limit == 0 || this.generatedCount < this.limit;
      }

      public boolean canGenerate() {
         return this.limit == 0 || this.generatedCount < this.limit;
      }
   }

   public static class SmallCorridor extends NetherFortressGenerator.Piece {
      public SmallCorridor(int chainLength, BlockBox boundingBox, Direction orientation) {
         super(StructurePieceType.NETHER_FORTRESS_SMALL_CORRIDOR, chainLength);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public SmallCorridor(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_SMALL_CORRIDOR, _snowman);
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         this.fillForwardOpening((NetherFortressGenerator.Start)start, pieces, random, 1, 0, true);
      }

      public static NetherFortressGenerator.SmallCorridor create(List<StructurePiece> pieces, int x, int y, int z, Direction orientation, int chainLength) {
         BlockBox _snowman = BlockBox.rotated(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
         return isInbounds(_snowman) && StructurePiece.getOverlappingPiece(pieces, _snowman) == null
            ? new NetherFortressGenerator.SmallCorridor(chainLength, _snowman, orientation)
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
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
         BlockState _snowmanxxx = Blocks.NETHER_BRICK_FENCE
            .getDefaultState()
            .with(FenceBlock.NORTH, Boolean.valueOf(true))
            .with(FenceBlock.SOUTH, Boolean.valueOf(true));
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 1, 0, 4, 1, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 3, 0, 4, 3, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 3, 1, 4, 4, 1, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 3, 3, 4, 4, 3, _snowmanxxx, _snowmanxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);

         for (int _snowmanxxxx = 0; _snowmanxxxx <= 4; _snowmanxxxx++) {
            for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 4; _snowmanxxxxx++) {
               this.fillDownwards(_snowman, Blocks.NETHER_BRICKS.getDefaultState(), _snowmanxxxx, -1, _snowmanxxxxx, boundingBox);
            }
         }

         return true;
      }
   }

   public static class Start extends NetherFortressGenerator.BridgeCrossing {
      public NetherFortressGenerator.PieceData lastPiece;
      public List<NetherFortressGenerator.PieceData> bridgePieces;
      public List<NetherFortressGenerator.PieceData> corridorPieces;
      public final List<StructurePiece> pieces = Lists.newArrayList();

      public Start(Random _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman, _snowman);
         this.bridgePieces = Lists.newArrayList();

         for (NetherFortressGenerator.PieceData _snowmanxxx : NetherFortressGenerator.ALL_BRIDGE_PIECES) {
            _snowmanxxx.generatedCount = 0;
            this.bridgePieces.add(_snowmanxxx);
         }

         this.corridorPieces = Lists.newArrayList();

         for (NetherFortressGenerator.PieceData _snowmanxxx : NetherFortressGenerator.ALL_CORRIDOR_PIECES) {
            _snowmanxxx.generatedCount = 0;
            this.corridorPieces.add(_snowmanxxx);
         }
      }

      public Start(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.NETHER_FORTRESS_START, _snowman);
      }
   }
}
