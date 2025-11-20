package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.MineshaftFeature;

public class MineshaftGenerator {
   private static MineshaftGenerator.MineshaftPart pickPiece(
      List<StructurePiece> pieces, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength, MineshaftFeature.Type type
   ) {
      int _snowman = random.nextInt(100);
      if (_snowman >= 80) {
         BlockBox _snowmanx = MineshaftGenerator.MineshaftCrossing.getBoundingBox(pieces, random, x, y, z, orientation);
         if (_snowmanx != null) {
            return new MineshaftGenerator.MineshaftCrossing(chainLength, _snowmanx, orientation, type);
         }
      } else if (_snowman >= 70) {
         BlockBox _snowmanx = MineshaftGenerator.MineshaftStairs.getBoundingBox(pieces, random, x, y, z, orientation);
         if (_snowmanx != null) {
            return new MineshaftGenerator.MineshaftStairs(chainLength, _snowmanx, orientation, type);
         }
      } else {
         BlockBox _snowmanx = MineshaftGenerator.MineshaftCorridor.getBoundingBox(pieces, random, x, y, z, orientation);
         if (_snowmanx != null) {
            return new MineshaftGenerator.MineshaftCorridor(chainLength, random, _snowmanx, orientation, type);
         }
      }

      return null;
   }

   private static MineshaftGenerator.MineshaftPart pieceGenerator(
      StructurePiece start, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation, int chainLength
   ) {
      if (chainLength > 8) {
         return null;
      } else if (Math.abs(x - start.getBoundingBox().minX) <= 80 && Math.abs(z - start.getBoundingBox().minZ) <= 80) {
         MineshaftFeature.Type _snowman = ((MineshaftGenerator.MineshaftPart)start).mineshaftType;
         MineshaftGenerator.MineshaftPart _snowmanx = pickPiece(pieces, random, x, y, z, orientation, chainLength + 1, _snowman);
         if (_snowmanx != null) {
            pieces.add(_snowmanx);
            _snowmanx.fillOpenings(start, pieces, random);
         }

         return _snowmanx;
      } else {
         return null;
      }
   }

   public static class MineshaftCorridor extends MineshaftGenerator.MineshaftPart {
      private final boolean hasRails;
      private final boolean hasCobwebs;
      private boolean hasSpawner;
      private final int length;

      public MineshaftCorridor(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.MINESHAFT_CORRIDOR, _snowman);
         this.hasRails = _snowman.getBoolean("hr");
         this.hasCobwebs = _snowman.getBoolean("sc");
         this.hasSpawner = _snowman.getBoolean("hps");
         this.length = _snowman.getInt("Num");
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("hr", this.hasRails);
         tag.putBoolean("sc", this.hasCobwebs);
         tag.putBoolean("hps", this.hasSpawner);
         tag.putInt("Num", this.length);
      }

      public MineshaftCorridor(int chainLength, Random random, BlockBox boundingBox, Direction orientation, MineshaftFeature.Type type) {
         super(StructurePieceType.MINESHAFT_CORRIDOR, chainLength, type);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
         this.hasRails = random.nextInt(3) == 0;
         this.hasCobwebs = !this.hasRails && random.nextInt(23) == 0;
         if (this.getFacing().getAxis() == Direction.Axis.Z) {
            this.length = boundingBox.getBlockCountZ() / 5;
         } else {
            this.length = boundingBox.getBlockCountX() / 5;
         }
      }

      public static BlockBox getBoundingBox(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
         BlockBox _snowman = new BlockBox(x, y, z, x, y + 3 - 1, z);

         int _snowmanx;
         for (_snowmanx = random.nextInt(3) + 2; _snowmanx > 0; _snowmanx--) {
            int _snowmanxx = _snowmanx * 5;
            switch (orientation) {
               case NORTH:
               default:
                  _snowman.maxX = x + 3 - 1;
                  _snowman.minZ = z - (_snowmanxx - 1);
                  break;
               case SOUTH:
                  _snowman.maxX = x + 3 - 1;
                  _snowman.maxZ = z + _snowmanxx - 1;
                  break;
               case WEST:
                  _snowman.minX = x - (_snowmanxx - 1);
                  _snowman.maxZ = z + 3 - 1;
                  break;
               case EAST:
                  _snowman.maxX = x + _snowmanxx - 1;
                  _snowman.maxZ = z + 3 - 1;
            }

            if (StructurePiece.getOverlappingPiece(pieces, _snowman) == null) {
               break;
            }
         }

         return _snowmanx > 0 ? _snowman : null;
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int _snowman = this.getChainLength();
         int _snowmanx = random.nextInt(4);
         Direction _snowmanxx = this.getFacing();
         if (_snowmanxx != null) {
            switch (_snowmanxx) {
               case NORTH:
               default:
                  if (_snowmanx <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, _snowmanxx, _snowman
                     );
                  } else if (_snowmanx == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ,
                        Direction.WEST,
                        _snowman
                     );
                  } else {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ,
                        Direction.EAST,
                        _snowman
                     );
                  }
                  break;
               case SOUTH:
                  if (_snowmanx <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, _snowmanxx, _snowman
                     );
                  } else if (_snowmanx == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ - 3,
                        Direction.WEST,
                        _snowman
                     );
                  } else {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ - 3,
                        Direction.EAST,
                        _snowman
                     );
                  }
                  break;
               case WEST:
                  if (_snowmanx <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, _snowmanxx, _snowman
                     );
                  } else if (_snowmanx == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.minX,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ - 1,
                        Direction.NORTH,
                        _snowman
                     );
                  } else {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.minX,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ + 1,
                        Direction.SOUTH,
                        _snowman
                     );
                  }
                  break;
               case EAST:
                  if (_snowmanx <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, _snowmanxx, _snowman
                     );
                  } else if (_snowmanx == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.maxX - 3,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ - 1,
                        Direction.NORTH,
                        _snowman
                     );
                  } else {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.maxX - 3,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ + 1,
                        Direction.SOUTH,
                        _snowman
                     );
                  }
            }
         }

         if (_snowman < 8) {
            if (_snowmanxx != Direction.NORTH && _snowmanxx != Direction.SOUTH) {
               for (int _snowmanxxx = this.boundingBox.minX + 3; _snowmanxxx + 3 <= this.boundingBox.maxX; _snowmanxxx += 5) {
                  int _snowmanxxxx = random.nextInt(5);
                  if (_snowmanxxxx == 0) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, _snowmanxxx, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, _snowman + 1);
                  } else if (_snowmanxxxx == 1) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, _snowmanxxx, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, _snowman + 1);
                  }
               }
            } else {
               for (int _snowmanxxxx = this.boundingBox.minZ + 3; _snowmanxxxx + 3 <= this.boundingBox.maxZ; _snowmanxxxx += 5) {
                  int _snowmanxxxxx = random.nextInt(5);
                  if (_snowmanxxxxx == 0) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, _snowmanxxxx, Direction.WEST, _snowman + 1);
                  } else if (_snowmanxxxxx == 1) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, _snowmanxxxx, Direction.EAST, _snowman + 1);
                  }
               }
            }
         }
      }

      @Override
      protected boolean addChest(StructureWorldAccess _snowman, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId) {
         BlockPos _snowmanx = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
         if (boundingBox.contains(_snowmanx) && _snowman.getBlockState(_snowmanx).isAir() && !_snowman.getBlockState(_snowmanx.down()).isAir()) {
            BlockState _snowmanxx = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, random.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
            this.addBlock(_snowman, _snowmanxx, x, y, z, boundingBox);
            ChestMinecartEntity _snowmanxxx = new ChestMinecartEntity(_snowman.toServerWorld(), (double)_snowmanx.getX() + 0.5, (double)_snowmanx.getY() + 0.5, (double)_snowmanx.getZ() + 0.5);
            _snowmanxxx.setLootTable(lootTableId, random.nextLong());
            _snowman.spawnEntity(_snowmanxxx);
            return true;
         } else {
            return false;
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
         if (this.isTouchingLiquid(_snowman, boundingBox)) {
            return false;
         } else {
            int _snowmanxxx = 0;
            int _snowmanxxxx = 2;
            int _snowmanxxxxx = 0;
            int _snowmanxxxxxx = 2;
            int _snowmanxxxxxxx = this.length * 5 - 1;
            BlockState _snowmanxxxxxxxx = this.getPlanksType();
            this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 2, 1, _snowmanxxxxxxx, AIR, AIR, false);
            this.fillWithOutlineUnderSeaLevel(_snowman, boundingBox, random, 0.8F, 0, 2, 0, 2, 2, _snowmanxxxxxxx, AIR, AIR, false, false);
            if (this.hasCobwebs) {
               this.fillWithOutlineUnderSeaLevel(_snowman, boundingBox, random, 0.6F, 0, 0, 0, 2, 1, _snowmanxxxxxxx, Blocks.COBWEB.getDefaultState(), AIR, false, true);
            }

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < this.length; _snowmanxxxxxxxxx++) {
               int _snowmanxxxxxxxxxx = 2 + _snowmanxxxxxxxxx * 5;
               this.generateSupports(_snowman, boundingBox, 0, 0, _snowmanxxxxxxxxxx, 2, 2, random);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.1F, 0, 2, _snowmanxxxxxxxxxx - 1);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.1F, 2, 2, _snowmanxxxxxxxxxx - 1);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.1F, 0, 2, _snowmanxxxxxxxxxx + 1);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.1F, 2, 2, _snowmanxxxxxxxxxx + 1);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.05F, 0, 2, _snowmanxxxxxxxxxx - 2);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.05F, 2, 2, _snowmanxxxxxxxxxx - 2);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.05F, 0, 2, _snowmanxxxxxxxxxx + 2);
               this.addCobwebsUnderground(_snowman, boundingBox, random, 0.05F, 2, 2, _snowmanxxxxxxxxxx + 2);
               if (random.nextInt(100) == 0) {
                  this.addChest(_snowman, boundingBox, random, 2, 0, _snowmanxxxxxxxxxx - 1, LootTables.ABANDONED_MINESHAFT_CHEST);
               }

               if (random.nextInt(100) == 0) {
                  this.addChest(_snowman, boundingBox, random, 0, 0, _snowmanxxxxxxxxxx + 1, LootTables.ABANDONED_MINESHAFT_CHEST);
               }

               if (this.hasCobwebs && !this.hasSpawner) {
                  int _snowmanxxxxxxxxxxx = this.applyYTransform(0);
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx - 1 + random.nextInt(3);
                  int _snowmanxxxxxxxxxxxxx = this.applyXTransform(1, _snowmanxxxxxxxxxxxx);
                  int _snowmanxxxxxxxxxxxxxx = this.applyZTransform(1, _snowmanxxxxxxxxxxxx);
                  BlockPos _snowmanxxxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
                  if (boundingBox.contains(_snowmanxxxxxxxxxxxxxxx) && this.isUnderSeaLevel(_snowman, 1, 0, _snowmanxxxxxxxxxxxx, boundingBox)) {
                     this.hasSpawner = true;
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxx, Blocks.SPAWNER.getDefaultState(), 2);
                     BlockEntity _snowmanxxxxxxxxxxxxxxxx = _snowman.getBlockEntity(_snowmanxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxx instanceof MobSpawnerBlockEntity) {
                        ((MobSpawnerBlockEntity)_snowmanxxxxxxxxxxxxxxxx).getLogic().setEntityId(EntityType.CAVE_SPIDER);
                     }
                  }
               }
            }

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= 2; _snowmanxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxx = -1;
                  BlockState _snowmanxxxxxxxxxxxxx = this.getBlockAt(_snowman, _snowmanxxxxxxxxx, -1, _snowmanxxxxxxxxxxx, boundingBox);
                  if (_snowmanxxxxxxxxxxxxx.isAir() && this.isUnderSeaLevel(_snowman, _snowmanxxxxxxxxx, -1, _snowmanxxxxxxxxxxx, boundingBox)) {
                     int _snowmanxxxxxxxxxxxxxx = -1;
                     this.addBlock(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, -1, _snowmanxxxxxxxxxxx, boundingBox);
                  }
               }
            }

            if (this.hasRails) {
               BlockState _snowmanxxxxxxxxx = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.NORTH_SOUTH);

               for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx++) {
                  BlockState _snowmanxxxxxxxxxxxxx = this.getBlockAt(_snowman, 1, -1, _snowmanxxxxxxxxxxxx, boundingBox);
                  if (!_snowmanxxxxxxxxxxxxx.isAir()
                     && _snowmanxxxxxxxxxxxxx.isOpaqueFullCube(
                        _snowman, new BlockPos(this.applyXTransform(1, _snowmanxxxxxxxxxxxx), this.applyYTransform(-1), this.applyZTransform(1, _snowmanxxxxxxxxxxxx))
                     )) {
                     float _snowmanxxxxxxxxxxxxxx = this.isUnderSeaLevel(_snowman, 1, 0, _snowmanxxxxxxxxxxxx, boundingBox) ? 0.7F : 0.9F;
                     this.addBlockWithRandomThreshold(_snowman, boundingBox, random, _snowmanxxxxxxxxxxxxxx, 1, 0, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxx);
                  }
               }
            }

            return true;
         }
      }

      private void generateSupports(StructureWorldAccess _snowman, BlockBox boundingBox, int minX, int minY, int z, int maxY, int maxX, Random random) {
         if (this.isSolidCeiling(_snowman, boundingBox, minX, maxX, maxY, z)) {
            BlockState _snowmanx = this.getPlanksType();
            BlockState _snowmanxx = this.getFenceType();
            this.fillWithOutline(_snowman, boundingBox, minX, minY, z, minX, maxY - 1, z, _snowmanxx.with(FenceBlock.WEST, Boolean.valueOf(true)), AIR, false);
            this.fillWithOutline(_snowman, boundingBox, maxX, minY, z, maxX, maxY - 1, z, _snowmanxx.with(FenceBlock.EAST, Boolean.valueOf(true)), AIR, false);
            if (random.nextInt(4) == 0) {
               this.fillWithOutline(_snowman, boundingBox, minX, maxY, z, minX, maxY, z, _snowmanx, AIR, false);
               this.fillWithOutline(_snowman, boundingBox, maxX, maxY, z, maxX, maxY, z, _snowmanx, AIR, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, minX, maxY, z, maxX, maxY, z, _snowmanx, AIR, false);
               this.addBlockWithRandomThreshold(
                  _snowman, boundingBox, random, 0.05F, minX + 1, maxY, z - 1, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.NORTH)
               );
               this.addBlockWithRandomThreshold(
                  _snowman, boundingBox, random, 0.05F, minX + 1, maxY, z + 1, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.SOUTH)
               );
            }
         }
      }

      private void addCobwebsUnderground(StructureWorldAccess _snowman, BlockBox boundingBox, Random random, float threshold, int x, int y, int z) {
         if (this.isUnderSeaLevel(_snowman, x, y, z, boundingBox)) {
            this.addBlockWithRandomThreshold(_snowman, boundingBox, random, threshold, x, y, z, Blocks.COBWEB.getDefaultState());
         }
      }
   }

   public static class MineshaftCrossing extends MineshaftGenerator.MineshaftPart {
      private final Direction direction;
      private final boolean twoFloors;

      public MineshaftCrossing(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.MINESHAFT_CROSSING, _snowman);
         this.twoFloors = _snowman.getBoolean("tf");
         this.direction = Direction.fromHorizontal(_snowman.getInt("D"));
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putBoolean("tf", this.twoFloors);
         tag.putInt("D", this.direction.getHorizontal());
      }

      public MineshaftCrossing(int chainLength, BlockBox boundingBox, @Nullable Direction orientation, MineshaftFeature.Type type) {
         super(StructurePieceType.MINESHAFT_CROSSING, chainLength, type);
         this.direction = orientation;
         this.boundingBox = boundingBox;
         this.twoFloors = boundingBox.getBlockCountY() > 3;
      }

      public static BlockBox getBoundingBox(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
         BlockBox _snowman = new BlockBox(x, y, z, x, y + 3 - 1, z);
         if (random.nextInt(4) == 0) {
            _snowman.maxY += 4;
         }

         switch (orientation) {
            case NORTH:
            default:
               _snowman.minX = x - 1;
               _snowman.maxX = x + 3;
               _snowman.minZ = z - 4;
               break;
            case SOUTH:
               _snowman.minX = x - 1;
               _snowman.maxX = x + 3;
               _snowman.maxZ = z + 3 + 1;
               break;
            case WEST:
               _snowman.minX = x - 4;
               _snowman.minZ = z - 1;
               _snowman.maxZ = z + 3;
               break;
            case EAST:
               _snowman.maxX = x + 3 + 1;
               _snowman.minZ = z - 1;
               _snowman.maxZ = z + 3;
         }

         return StructurePiece.getOverlappingPiece(pieces, _snowman) != null ? null : _snowman;
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int _snowman = this.getChainLength();
         switch (this.direction) {
            case NORTH:
            default:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, _snowman
               );
               break;
            case SOUTH:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, _snowman
               );
               break;
            case WEST:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, _snowman
               );
               break;
            case EAST:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, _snowman
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, _snowman
               );
         }

         if (this.twoFloors) {
            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, Direction.NORTH, _snowman
               );
            }

            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.WEST, _snowman
               );
            }

            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.EAST, _snowman
               );
            }

            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, Direction.SOUTH, _snowman
               );
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
         if (this.isTouchingLiquid(_snowman, boundingBox)) {
            return false;
         } else {
            BlockState _snowmanxxx = this.getPlanksType();
            if (this.twoFloors) {
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  this.boundingBox.minX + 1,
                  this.boundingBox.minY,
                  this.boundingBox.minZ,
                  this.boundingBox.maxX - 1,
                  this.boundingBox.minY + 3 - 1,
                  this.boundingBox.maxZ,
                  AIR,
                  AIR,
                  false
               );
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  this.boundingBox.minX,
                  this.boundingBox.minY,
                  this.boundingBox.minZ + 1,
                  this.boundingBox.maxX,
                  this.boundingBox.minY + 3 - 1,
                  this.boundingBox.maxZ - 1,
                  AIR,
                  AIR,
                  false
               );
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  this.boundingBox.minX + 1,
                  this.boundingBox.maxY - 2,
                  this.boundingBox.minZ,
                  this.boundingBox.maxX - 1,
                  this.boundingBox.maxY,
                  this.boundingBox.maxZ,
                  AIR,
                  AIR,
                  false
               );
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  this.boundingBox.minX,
                  this.boundingBox.maxY - 2,
                  this.boundingBox.minZ + 1,
                  this.boundingBox.maxX,
                  this.boundingBox.maxY,
                  this.boundingBox.maxZ - 1,
                  AIR,
                  AIR,
                  false
               );
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  this.boundingBox.minX + 1,
                  this.boundingBox.minY + 3,
                  this.boundingBox.minZ + 1,
                  this.boundingBox.maxX - 1,
                  this.boundingBox.minY + 3,
                  this.boundingBox.maxZ - 1,
                  AIR,
                  AIR,
                  false
               );
            } else {
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  this.boundingBox.minX + 1,
                  this.boundingBox.minY,
                  this.boundingBox.minZ,
                  this.boundingBox.maxX - 1,
                  this.boundingBox.maxY,
                  this.boundingBox.maxZ,
                  AIR,
                  AIR,
                  false
               );
               this.fillWithOutline(
                  _snowman,
                  boundingBox,
                  this.boundingBox.minX,
                  this.boundingBox.minY,
                  this.boundingBox.minZ + 1,
                  this.boundingBox.maxX,
                  this.boundingBox.maxY,
                  this.boundingBox.maxZ - 1,
                  AIR,
                  AIR,
                  false
               );
            }

            this.generateCrossingPilliar(_snowman, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(_snowman, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(_snowman, boundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(_snowman, boundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);

            for (int _snowmanxxxx = this.boundingBox.minX; _snowmanxxxx <= this.boundingBox.maxX; _snowmanxxxx++) {
               for (int _snowmanxxxxx = this.boundingBox.minZ; _snowmanxxxxx <= this.boundingBox.maxZ; _snowmanxxxxx++) {
                  if (this.getBlockAt(_snowman, _snowmanxxxx, this.boundingBox.minY - 1, _snowmanxxxxx, boundingBox).isAir()
                     && this.isUnderSeaLevel(_snowman, _snowmanxxxx, this.boundingBox.minY - 1, _snowmanxxxxx, boundingBox)) {
                     this.addBlock(_snowman, _snowmanxxx, _snowmanxxxx, this.boundingBox.minY - 1, _snowmanxxxxx, boundingBox);
                  }
               }
            }

            return true;
         }
      }

      private void generateCrossingPilliar(StructureWorldAccess _snowman, BlockBox boundingBox, int x, int minY, int z, int maxY) {
         if (!this.getBlockAt(_snowman, x, maxY + 1, z, boundingBox).isAir()) {
            this.fillWithOutline(_snowman, boundingBox, x, minY, z, x, maxY, z, this.getPlanksType(), AIR, false);
         }
      }
   }

   abstract static class MineshaftPart extends StructurePiece {
      protected MineshaftFeature.Type mineshaftType;

      public MineshaftPart(StructurePieceType structurePieceType, int chainLength, MineshaftFeature.Type type) {
         super(structurePieceType, chainLength);
         this.mineshaftType = type;
      }

      public MineshaftPart(StructurePieceType _snowman, CompoundTag _snowman) {
         super(_snowman, _snowman);
         this.mineshaftType = MineshaftFeature.Type.byIndex(_snowman.getInt("MST"));
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         tag.putInt("MST", this.mineshaftType.ordinal());
      }

      protected BlockState getPlanksType() {
         switch (this.mineshaftType) {
            case NORMAL:
            default:
               return Blocks.OAK_PLANKS.getDefaultState();
            case MESA:
               return Blocks.DARK_OAK_PLANKS.getDefaultState();
         }
      }

      protected BlockState getFenceType() {
         switch (this.mineshaftType) {
            case NORMAL:
            default:
               return Blocks.OAK_FENCE.getDefaultState();
            case MESA:
               return Blocks.DARK_OAK_FENCE.getDefaultState();
         }
      }

      protected boolean isSolidCeiling(BlockView _snowman, BlockBox boundingBox, int minX, int maxX, int y, int z) {
         for (int _snowmanx = minX; _snowmanx <= maxX; _snowmanx++) {
            if (this.getBlockAt(_snowman, _snowmanx, y + 1, z, boundingBox).isAir()) {
               return false;
            }
         }

         return true;
      }
   }

   public static class MineshaftRoom extends MineshaftGenerator.MineshaftPart {
      private final List<BlockBox> entrances = Lists.newLinkedList();

      public MineshaftRoom(int chainLength, Random random, int x, int z, MineshaftFeature.Type type) {
         super(StructurePieceType.MINESHAFT_ROOM, chainLength, type);
         this.mineshaftType = type;
         this.boundingBox = new BlockBox(x, 50, z, x + 7 + random.nextInt(6), 54 + random.nextInt(6), z + 7 + random.nextInt(6));
      }

      public MineshaftRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.MINESHAFT_ROOM, _snowman);
         ListTag _snowmanxx = _snowman.getList("Entrances", 11);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            this.entrances.add(new BlockBox(_snowmanxx.getIntArray(_snowmanxxx)));
         }
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int _snowman = this.getChainLength();
         int _snowmanx = this.boundingBox.getBlockCountY() - 3 - 1;
         if (_snowmanx <= 0) {
            _snowmanx = 1;
         }

         int _snowmanxx = 0;

         while (_snowmanxx < this.boundingBox.getBlockCountX()) {
            _snowmanxx += random.nextInt(this.boundingBox.getBlockCountX());
            if (_snowmanxx + 3 > this.boundingBox.getBlockCountX()) {
               break;
            }

            MineshaftGenerator.MineshaftPart _snowmanxxx = MineshaftGenerator.pieceGenerator(
               start,
               pieces,
               random,
               this.boundingBox.minX + _snowmanxx,
               this.boundingBox.minY + random.nextInt(_snowmanx) + 1,
               this.boundingBox.minZ - 1,
               Direction.NORTH,
               _snowman
            );
            if (_snowmanxxx != null) {
               BlockBox _snowmanxxxx = _snowmanxxx.getBoundingBox();
               this.entrances.add(new BlockBox(_snowmanxxxx.minX, _snowmanxxxx.minY, this.boundingBox.minZ, _snowmanxxxx.maxX, _snowmanxxxx.maxY, this.boundingBox.minZ + 1));
            }

            _snowmanxx += 4;
         }

         _snowmanxx = 0;

         while (_snowmanxx < this.boundingBox.getBlockCountX()) {
            _snowmanxx += random.nextInt(this.boundingBox.getBlockCountX());
            if (_snowmanxx + 3 > this.boundingBox.getBlockCountX()) {
               break;
            }

            MineshaftGenerator.MineshaftPart _snowmanxxx = MineshaftGenerator.pieceGenerator(
               start,
               pieces,
               random,
               this.boundingBox.minX + _snowmanxx,
               this.boundingBox.minY + random.nextInt(_snowmanx) + 1,
               this.boundingBox.maxZ + 1,
               Direction.SOUTH,
               _snowman
            );
            if (_snowmanxxx != null) {
               BlockBox _snowmanxxxx = _snowmanxxx.getBoundingBox();
               this.entrances.add(new BlockBox(_snowmanxxxx.minX, _snowmanxxxx.minY, this.boundingBox.maxZ - 1, _snowmanxxxx.maxX, _snowmanxxxx.maxY, this.boundingBox.maxZ));
            }

            _snowmanxx += 4;
         }

         _snowmanxx = 0;

         while (_snowmanxx < this.boundingBox.getBlockCountZ()) {
            _snowmanxx += random.nextInt(this.boundingBox.getBlockCountZ());
            if (_snowmanxx + 3 > this.boundingBox.getBlockCountZ()) {
               break;
            }

            MineshaftGenerator.MineshaftPart _snowmanxxx = MineshaftGenerator.pieceGenerator(
               start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + random.nextInt(_snowmanx) + 1, this.boundingBox.minZ + _snowmanxx, Direction.WEST, _snowman
            );
            if (_snowmanxxx != null) {
               BlockBox _snowmanxxxx = _snowmanxxx.getBoundingBox();
               this.entrances.add(new BlockBox(this.boundingBox.minX, _snowmanxxxx.minY, _snowmanxxxx.minZ, this.boundingBox.minX + 1, _snowmanxxxx.maxY, _snowmanxxxx.maxZ));
            }

            _snowmanxx += 4;
         }

         _snowmanxx = 0;

         while (_snowmanxx < this.boundingBox.getBlockCountZ()) {
            _snowmanxx += random.nextInt(this.boundingBox.getBlockCountZ());
            if (_snowmanxx + 3 > this.boundingBox.getBlockCountZ()) {
               break;
            }

            StructurePiece _snowmanxxx = MineshaftGenerator.pieceGenerator(
               start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + random.nextInt(_snowmanx) + 1, this.boundingBox.minZ + _snowmanxx, Direction.EAST, _snowman
            );
            if (_snowmanxxx != null) {
               BlockBox _snowmanxxxx = _snowmanxxx.getBoundingBox();
               this.entrances.add(new BlockBox(this.boundingBox.maxX - 1, _snowmanxxxx.minY, _snowmanxxxx.minZ, this.boundingBox.maxX, _snowmanxxxx.maxY, _snowmanxxxx.maxZ));
            }

            _snowmanxx += 4;
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
         if (this.isTouchingLiquid(_snowman, boundingBox)) {
            return false;
         } else {
            this.fillWithOutline(
               _snowman,
               boundingBox,
               this.boundingBox.minX,
               this.boundingBox.minY,
               this.boundingBox.minZ,
               this.boundingBox.maxX,
               this.boundingBox.minY,
               this.boundingBox.maxZ,
               Blocks.DIRT.getDefaultState(),
               AIR,
               true
            );
            this.fillWithOutline(
               _snowman,
               boundingBox,
               this.boundingBox.minX,
               this.boundingBox.minY + 1,
               this.boundingBox.minZ,
               this.boundingBox.maxX,
               Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY),
               this.boundingBox.maxZ,
               AIR,
               AIR,
               false
            );

            for (BlockBox _snowmanxxx : this.entrances) {
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxx.minX, _snowmanxxx.maxY - 2, _snowmanxxx.minZ, _snowmanxxx.maxX, _snowmanxxx.maxY, _snowmanxxx.maxZ, AIR, AIR, false);
            }

            this.fillHalfEllipsoid(
               _snowman,
               boundingBox,
               this.boundingBox.minX,
               this.boundingBox.minY + 4,
               this.boundingBox.minZ,
               this.boundingBox.maxX,
               this.boundingBox.maxY,
               this.boundingBox.maxZ,
               AIR,
               false
            );
            return true;
         }
      }

      @Override
      public void translate(int x, int y, int z) {
         super.translate(x, y, z);

         for (BlockBox _snowman : this.entrances) {
            _snowman.move(x, y, z);
         }
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         ListTag _snowman = new ListTag();

         for (BlockBox _snowmanx : this.entrances) {
            _snowman.add(_snowmanx.toNbt());
         }

         tag.put("Entrances", _snowman);
      }
   }

   public static class MineshaftStairs extends MineshaftGenerator.MineshaftPart {
      public MineshaftStairs(int chainLength, BlockBox boundingBox, Direction orientation, MineshaftFeature.Type type) {
         super(StructurePieceType.MINESHAFT_STAIRS, chainLength, type);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public MineshaftStairs(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.MINESHAFT_STAIRS, _snowman);
      }

      public static BlockBox getBoundingBox(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
         BlockBox _snowman = new BlockBox(x, y - 5, z, x, y + 3 - 1, z);
         switch (orientation) {
            case NORTH:
            default:
               _snowman.maxX = x + 3 - 1;
               _snowman.minZ = z - 8;
               break;
            case SOUTH:
               _snowman.maxX = x + 3 - 1;
               _snowman.maxZ = z + 8;
               break;
            case WEST:
               _snowman.minX = x - 8;
               _snowman.maxZ = z + 3 - 1;
               break;
            case EAST:
               _snowman.maxX = x + 8;
               _snowman.maxZ = z + 3 - 1;
         }

         return StructurePiece.getOverlappingPiece(pieces, _snowman) != null ? null : _snowman;
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int _snowman = this.getChainLength();
         Direction _snowmanx = this.getFacing();
         if (_snowmanx != null) {
            switch (_snowmanx) {
               case NORTH:
               default:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, _snowman
                  );
                  break;
               case SOUTH:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, _snowman
                  );
                  break;
               case WEST:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.WEST, _snowman
                  );
                  break;
               case EAST:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.EAST, _snowman
                  );
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
         if (this.isTouchingLiquid(_snowman, boundingBox)) {
            return false;
         } else {
            this.fillWithOutline(_snowman, boundingBox, 0, 5, 0, 2, 7, 1, AIR, AIR, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 0, 7, 2, 2, 8, AIR, AIR, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
               this.fillWithOutline(_snowman, boundingBox, 0, 5 - _snowmanxxx - (_snowmanxxx < 4 ? 1 : 0), 2 + _snowmanxxx, 2, 7 - _snowmanxxx, 2 + _snowmanxxx, AIR, AIR, false);
            }

            return true;
         }
      }
   }
}
