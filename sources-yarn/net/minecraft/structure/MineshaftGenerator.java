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
      int m = random.nextInt(100);
      if (m >= 80) {
         BlockBox lv = MineshaftGenerator.MineshaftCrossing.getBoundingBox(pieces, random, x, y, z, orientation);
         if (lv != null) {
            return new MineshaftGenerator.MineshaftCrossing(chainLength, lv, orientation, type);
         }
      } else if (m >= 70) {
         BlockBox lv2 = MineshaftGenerator.MineshaftStairs.getBoundingBox(pieces, random, x, y, z, orientation);
         if (lv2 != null) {
            return new MineshaftGenerator.MineshaftStairs(chainLength, lv2, orientation, type);
         }
      } else {
         BlockBox lv3 = MineshaftGenerator.MineshaftCorridor.getBoundingBox(pieces, random, x, y, z, orientation);
         if (lv3 != null) {
            return new MineshaftGenerator.MineshaftCorridor(chainLength, random, lv3, orientation, type);
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
         MineshaftFeature.Type lv = ((MineshaftGenerator.MineshaftPart)start).mineshaftType;
         MineshaftGenerator.MineshaftPart lv2 = pickPiece(pieces, random, x, y, z, orientation, chainLength + 1, lv);
         if (lv2 != null) {
            pieces.add(lv2);
            lv2.fillOpenings(start, pieces, random);
         }

         return lv2;
      } else {
         return null;
      }
   }

   public static class MineshaftCorridor extends MineshaftGenerator.MineshaftPart {
      private final boolean hasRails;
      private final boolean hasCobwebs;
      private boolean hasSpawner;
      private final int length;

      public MineshaftCorridor(StructureManager arg, CompoundTag arg2) {
         super(StructurePieceType.MINESHAFT_CORRIDOR, arg2);
         this.hasRails = arg2.getBoolean("hr");
         this.hasCobwebs = arg2.getBoolean("sc");
         this.hasSpawner = arg2.getBoolean("hps");
         this.length = arg2.getInt("Num");
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
         BlockBox lv = new BlockBox(x, y, z, x, y + 3 - 1, z);

         int l;
         for (l = random.nextInt(3) + 2; l > 0; l--) {
            int m = l * 5;
            switch (orientation) {
               case NORTH:
               default:
                  lv.maxX = x + 3 - 1;
                  lv.minZ = z - (m - 1);
                  break;
               case SOUTH:
                  lv.maxX = x + 3 - 1;
                  lv.maxZ = z + m - 1;
                  break;
               case WEST:
                  lv.minX = x - (m - 1);
                  lv.maxZ = z + 3 - 1;
                  break;
               case EAST:
                  lv.maxX = x + m - 1;
                  lv.maxZ = z + 3 - 1;
            }

            if (StructurePiece.getOverlappingPiece(pieces, lv) == null) {
               break;
            }
         }

         return l > 0 ? lv : null;
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int i = this.getChainLength();
         int j = random.nextInt(4);
         Direction lv = this.getFacing();
         if (lv != null) {
            switch (lv) {
               case NORTH:
               default:
                  if (j <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, lv, i
                     );
                  } else if (j == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ,
                        Direction.WEST,
                        i
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
                        i
                     );
                  }
                  break;
               case SOUTH:
                  if (j <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, lv, i
                     );
                  } else if (j == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ - 3,
                        Direction.WEST,
                        i
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
                        i
                     );
                  }
                  break;
               case WEST:
                  if (j <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, lv, i
                     );
                  } else if (j == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.minX,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ - 1,
                        Direction.NORTH,
                        i
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
                        i
                     );
                  }
                  break;
               case EAST:
                  if (j <= 1) {
                     MineshaftGenerator.pieceGenerator(
                        start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, lv, i
                     );
                  } else if (j == 2) {
                     MineshaftGenerator.pieceGenerator(
                        start,
                        pieces,
                        random,
                        this.boundingBox.maxX - 3,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ - 1,
                        Direction.NORTH,
                        i
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
                        i
                     );
                  }
            }
         }

         if (i < 8) {
            if (lv != Direction.NORTH && lv != Direction.SOUTH) {
               for (int m = this.boundingBox.minX + 3; m + 3 <= this.boundingBox.maxX; m += 5) {
                  int n = random.nextInt(5);
                  if (n == 0) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, m, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, i + 1);
                  } else if (n == 1) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, m, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, i + 1);
                  }
               }
            } else {
               for (int k = this.boundingBox.minZ + 3; k + 3 <= this.boundingBox.maxZ; k += 5) {
                  int l = random.nextInt(5);
                  if (l == 0) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, k, Direction.WEST, i + 1);
                  } else if (l == 1) {
                     MineshaftGenerator.pieceGenerator(start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, k, Direction.EAST, i + 1);
                  }
               }
            }
         }
      }

      @Override
      protected boolean addChest(StructureWorldAccess arg, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId) {
         BlockPos lv = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
         if (boundingBox.contains(lv) && arg.getBlockState(lv).isAir() && !arg.getBlockState(lv.down()).isAir()) {
            BlockState lv2 = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, random.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
            this.addBlock(arg, lv2, x, y, z, boundingBox);
            ChestMinecartEntity lv3 = new ChestMinecartEntity(arg.toServerWorld(), (double)lv.getX() + 0.5, (double)lv.getY() + 0.5, (double)lv.getZ() + 0.5);
            lv3.setLootTable(lootTableId, random.nextLong());
            arg.spawnEntity(lv3);
            return true;
         } else {
            return false;
         }
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
         if (this.isTouchingLiquid(arg, boundingBox)) {
            return false;
         } else {
            int i = 0;
            int j = 2;
            int k = 0;
            int l = 2;
            int m = this.length * 5 - 1;
            BlockState lv = this.getPlanksType();
            this.fillWithOutline(arg, boundingBox, 0, 0, 0, 2, 1, m, AIR, AIR, false);
            this.fillWithOutlineUnderSeaLevel(arg, boundingBox, random, 0.8F, 0, 2, 0, 2, 2, m, AIR, AIR, false, false);
            if (this.hasCobwebs) {
               this.fillWithOutlineUnderSeaLevel(arg, boundingBox, random, 0.6F, 0, 0, 0, 2, 1, m, Blocks.COBWEB.getDefaultState(), AIR, false, true);
            }

            for (int n = 0; n < this.length; n++) {
               int o = 2 + n * 5;
               this.generateSupports(arg, boundingBox, 0, 0, o, 2, 2, random);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.1F, 0, 2, o - 1);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.1F, 2, 2, o - 1);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.1F, 0, 2, o + 1);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.1F, 2, 2, o + 1);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.05F, 0, 2, o - 2);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.05F, 2, 2, o - 2);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.05F, 0, 2, o + 2);
               this.addCobwebsUnderground(arg, boundingBox, random, 0.05F, 2, 2, o + 2);
               if (random.nextInt(100) == 0) {
                  this.addChest(arg, boundingBox, random, 2, 0, o - 1, LootTables.ABANDONED_MINESHAFT_CHEST);
               }

               if (random.nextInt(100) == 0) {
                  this.addChest(arg, boundingBox, random, 0, 0, o + 1, LootTables.ABANDONED_MINESHAFT_CHEST);
               }

               if (this.hasCobwebs && !this.hasSpawner) {
                  int p = this.applyYTransform(0);
                  int q = o - 1 + random.nextInt(3);
                  int r = this.applyXTransform(1, q);
                  int s = this.applyZTransform(1, q);
                  BlockPos lv2 = new BlockPos(r, p, s);
                  if (boundingBox.contains(lv2) && this.isUnderSeaLevel(arg, 1, 0, q, boundingBox)) {
                     this.hasSpawner = true;
                     arg.setBlockState(lv2, Blocks.SPAWNER.getDefaultState(), 2);
                     BlockEntity lv3 = arg.getBlockEntity(lv2);
                     if (lv3 instanceof MobSpawnerBlockEntity) {
                        ((MobSpawnerBlockEntity)lv3).getLogic().setEntityId(EntityType.CAVE_SPIDER);
                     }
                  }
               }
            }

            for (int t = 0; t <= 2; t++) {
               for (int u = 0; u <= m; u++) {
                  int v = -1;
                  BlockState lv4 = this.getBlockAt(arg, t, -1, u, boundingBox);
                  if (lv4.isAir() && this.isUnderSeaLevel(arg, t, -1, u, boundingBox)) {
                     int w = -1;
                     this.addBlock(arg, lv, t, -1, u, boundingBox);
                  }
               }
            }

            if (this.hasRails) {
               BlockState lv5 = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.NORTH_SOUTH);

               for (int x = 0; x <= m; x++) {
                  BlockState lv6 = this.getBlockAt(arg, 1, -1, x, boundingBox);
                  if (!lv6.isAir() && lv6.isOpaqueFullCube(arg, new BlockPos(this.applyXTransform(1, x), this.applyYTransform(-1), this.applyZTransform(1, x)))
                     )
                   {
                     float f = this.isUnderSeaLevel(arg, 1, 0, x, boundingBox) ? 0.7F : 0.9F;
                     this.addBlockWithRandomThreshold(arg, boundingBox, random, f, 1, 0, x, lv5);
                  }
               }
            }

            return true;
         }
      }

      private void generateSupports(StructureWorldAccess arg, BlockBox boundingBox, int minX, int minY, int z, int maxY, int maxX, Random random) {
         if (this.isSolidCeiling(arg, boundingBox, minX, maxX, maxY, z)) {
            BlockState lv = this.getPlanksType();
            BlockState lv2 = this.getFenceType();
            this.fillWithOutline(arg, boundingBox, minX, minY, z, minX, maxY - 1, z, lv2.with(FenceBlock.WEST, Boolean.valueOf(true)), AIR, false);
            this.fillWithOutline(arg, boundingBox, maxX, minY, z, maxX, maxY - 1, z, lv2.with(FenceBlock.EAST, Boolean.valueOf(true)), AIR, false);
            if (random.nextInt(4) == 0) {
               this.fillWithOutline(arg, boundingBox, minX, maxY, z, minX, maxY, z, lv, AIR, false);
               this.fillWithOutline(arg, boundingBox, maxX, maxY, z, maxX, maxY, z, lv, AIR, false);
            } else {
               this.fillWithOutline(arg, boundingBox, minX, maxY, z, maxX, maxY, z, lv, AIR, false);
               this.addBlockWithRandomThreshold(
                  arg, boundingBox, random, 0.05F, minX + 1, maxY, z - 1, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.NORTH)
               );
               this.addBlockWithRandomThreshold(
                  arg, boundingBox, random, 0.05F, minX + 1, maxY, z + 1, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, Direction.SOUTH)
               );
            }
         }
      }

      private void addCobwebsUnderground(StructureWorldAccess arg, BlockBox boundingBox, Random random, float threshold, int x, int y, int z) {
         if (this.isUnderSeaLevel(arg, x, y, z, boundingBox)) {
            this.addBlockWithRandomThreshold(arg, boundingBox, random, threshold, x, y, z, Blocks.COBWEB.getDefaultState());
         }
      }
   }

   public static class MineshaftCrossing extends MineshaftGenerator.MineshaftPart {
      private final Direction direction;
      private final boolean twoFloors;

      public MineshaftCrossing(StructureManager arg, CompoundTag arg2) {
         super(StructurePieceType.MINESHAFT_CROSSING, arg2);
         this.twoFloors = arg2.getBoolean("tf");
         this.direction = Direction.fromHorizontal(arg2.getInt("D"));
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
         BlockBox lv = new BlockBox(x, y, z, x, y + 3 - 1, z);
         if (random.nextInt(4) == 0) {
            lv.maxY += 4;
         }

         switch (orientation) {
            case NORTH:
            default:
               lv.minX = x - 1;
               lv.maxX = x + 3;
               lv.minZ = z - 4;
               break;
            case SOUTH:
               lv.minX = x - 1;
               lv.maxX = x + 3;
               lv.maxZ = z + 3 + 1;
               break;
            case WEST:
               lv.minX = x - 4;
               lv.minZ = z - 1;
               lv.maxZ = z + 3;
               break;
            case EAST:
               lv.maxX = x + 3 + 1;
               lv.minZ = z - 1;
               lv.maxZ = z + 3;
         }

         return StructurePiece.getOverlappingPiece(pieces, lv) != null ? null : lv;
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int i = this.getChainLength();
         switch (this.direction) {
            case NORTH:
            default:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, i
               );
               break;
            case SOUTH:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, i
               );
               break;
            case WEST:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, i
               );
               break;
            case EAST:
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, i
               );
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, i
               );
         }

         if (this.twoFloors) {
            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, Direction.NORTH, i
               );
            }

            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.WEST, i
               );
            }

            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.EAST, i
               );
            }

            if (random.nextBoolean()) {
               MineshaftGenerator.pieceGenerator(
                  start, pieces, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, Direction.SOUTH, i
               );
            }
         }
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
         if (this.isTouchingLiquid(arg, boundingBox)) {
            return false;
         } else {
            BlockState lv = this.getPlanksType();
            if (this.twoFloors) {
               this.fillWithOutline(
                  arg,
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
                  arg,
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
                  arg,
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
                  arg,
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
                  arg,
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
                  arg,
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
                  arg,
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

            this.generateCrossingPilliar(arg, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(arg, boundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(arg, boundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.generateCrossingPilliar(arg, boundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);

            for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
               for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; j++) {
                  if (this.getBlockAt(arg, i, this.boundingBox.minY - 1, j, boundingBox).isAir()
                     && this.isUnderSeaLevel(arg, i, this.boundingBox.minY - 1, j, boundingBox)) {
                     this.addBlock(arg, lv, i, this.boundingBox.minY - 1, j, boundingBox);
                  }
               }
            }

            return true;
         }
      }

      private void generateCrossingPilliar(StructureWorldAccess arg, BlockBox boundingBox, int x, int minY, int z, int maxY) {
         if (!this.getBlockAt(arg, x, maxY + 1, z, boundingBox).isAir()) {
            this.fillWithOutline(arg, boundingBox, x, minY, z, x, maxY, z, this.getPlanksType(), AIR, false);
         }
      }
   }

   abstract static class MineshaftPart extends StructurePiece {
      protected MineshaftFeature.Type mineshaftType;

      public MineshaftPart(StructurePieceType structurePieceType, int chainLength, MineshaftFeature.Type type) {
         super(structurePieceType, chainLength);
         this.mineshaftType = type;
      }

      public MineshaftPart(StructurePieceType arg, CompoundTag arg2) {
         super(arg, arg2);
         this.mineshaftType = MineshaftFeature.Type.byIndex(arg2.getInt("MST"));
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

      protected boolean isSolidCeiling(BlockView arg, BlockBox boundingBox, int minX, int maxX, int y, int z) {
         for (int m = minX; m <= maxX; m++) {
            if (this.getBlockAt(arg, m, y + 1, z, boundingBox).isAir()) {
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

      public MineshaftRoom(StructureManager arg, CompoundTag arg2) {
         super(StructurePieceType.MINESHAFT_ROOM, arg2);
         ListTag lv = arg2.getList("Entrances", 11);

         for (int i = 0; i < lv.size(); i++) {
            this.entrances.add(new BlockBox(lv.getIntArray(i)));
         }
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int i = this.getChainLength();
         int j = this.boundingBox.getBlockCountY() - 3 - 1;
         if (j <= 0) {
            j = 1;
         }

         int k = 0;

         while (k < this.boundingBox.getBlockCountX()) {
            k += random.nextInt(this.boundingBox.getBlockCountX());
            if (k + 3 > this.boundingBox.getBlockCountX()) {
               break;
            }

            MineshaftGenerator.MineshaftPart lv = MineshaftGenerator.pieceGenerator(
               start, pieces, random, this.boundingBox.minX + k, this.boundingBox.minY + random.nextInt(j) + 1, this.boundingBox.minZ - 1, Direction.NORTH, i
            );
            if (lv != null) {
               BlockBox lv2 = lv.getBoundingBox();
               this.entrances.add(new BlockBox(lv2.minX, lv2.minY, this.boundingBox.minZ, lv2.maxX, lv2.maxY, this.boundingBox.minZ + 1));
            }

            k += 4;
         }

         k = 0;

         while (k < this.boundingBox.getBlockCountX()) {
            k += random.nextInt(this.boundingBox.getBlockCountX());
            if (k + 3 > this.boundingBox.getBlockCountX()) {
               break;
            }

            MineshaftGenerator.MineshaftPart lv3 = MineshaftGenerator.pieceGenerator(
               start, pieces, random, this.boundingBox.minX + k, this.boundingBox.minY + random.nextInt(j) + 1, this.boundingBox.maxZ + 1, Direction.SOUTH, i
            );
            if (lv3 != null) {
               BlockBox lv4 = lv3.getBoundingBox();
               this.entrances.add(new BlockBox(lv4.minX, lv4.minY, this.boundingBox.maxZ - 1, lv4.maxX, lv4.maxY, this.boundingBox.maxZ));
            }

            k += 4;
         }

         k = 0;

         while (k < this.boundingBox.getBlockCountZ()) {
            k += random.nextInt(this.boundingBox.getBlockCountZ());
            if (k + 3 > this.boundingBox.getBlockCountZ()) {
               break;
            }

            MineshaftGenerator.MineshaftPart lv5 = MineshaftGenerator.pieceGenerator(
               start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY + random.nextInt(j) + 1, this.boundingBox.minZ + k, Direction.WEST, i
            );
            if (lv5 != null) {
               BlockBox lv6 = lv5.getBoundingBox();
               this.entrances.add(new BlockBox(this.boundingBox.minX, lv6.minY, lv6.minZ, this.boundingBox.minX + 1, lv6.maxY, lv6.maxZ));
            }

            k += 4;
         }

         k = 0;

         while (k < this.boundingBox.getBlockCountZ()) {
            k += random.nextInt(this.boundingBox.getBlockCountZ());
            if (k + 3 > this.boundingBox.getBlockCountZ()) {
               break;
            }

            StructurePiece lv7 = MineshaftGenerator.pieceGenerator(
               start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY + random.nextInt(j) + 1, this.boundingBox.minZ + k, Direction.EAST, i
            );
            if (lv7 != null) {
               BlockBox lv8 = lv7.getBoundingBox();
               this.entrances.add(new BlockBox(this.boundingBox.maxX - 1, lv8.minY, lv8.minZ, this.boundingBox.maxX, lv8.maxY, lv8.maxZ));
            }

            k += 4;
         }
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
         if (this.isTouchingLiquid(arg, boundingBox)) {
            return false;
         } else {
            this.fillWithOutline(
               arg,
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
               arg,
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

            for (BlockBox lv : this.entrances) {
               this.fillWithOutline(arg, boundingBox, lv.minX, lv.maxY - 2, lv.minZ, lv.maxX, lv.maxY, lv.maxZ, AIR, AIR, false);
            }

            this.fillHalfEllipsoid(
               arg,
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

         for (BlockBox lv : this.entrances) {
            lv.move(x, y, z);
         }
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         ListTag lv = new ListTag();

         for (BlockBox lv2 : this.entrances) {
            lv.add(lv2.toNbt());
         }

         tag.put("Entrances", lv);
      }
   }

   public static class MineshaftStairs extends MineshaftGenerator.MineshaftPart {
      public MineshaftStairs(int chainLength, BlockBox boundingBox, Direction orientation, MineshaftFeature.Type type) {
         super(StructurePieceType.MINESHAFT_STAIRS, chainLength, type);
         this.setOrientation(orientation);
         this.boundingBox = boundingBox;
      }

      public MineshaftStairs(StructureManager arg, CompoundTag arg2) {
         super(StructurePieceType.MINESHAFT_STAIRS, arg2);
      }

      public static BlockBox getBoundingBox(List<StructurePiece> pieces, Random random, int x, int y, int z, Direction orientation) {
         BlockBox lv = new BlockBox(x, y - 5, z, x, y + 3 - 1, z);
         switch (orientation) {
            case NORTH:
            default:
               lv.maxX = x + 3 - 1;
               lv.minZ = z - 8;
               break;
            case SOUTH:
               lv.maxX = x + 3 - 1;
               lv.maxZ = z + 8;
               break;
            case WEST:
               lv.minX = x - 8;
               lv.maxZ = z + 3 - 1;
               break;
            case EAST:
               lv.maxX = x + 8;
               lv.maxZ = z + 3 - 1;
         }

         return StructurePiece.getOverlappingPiece(pieces, lv) != null ? null : lv;
      }

      @Override
      public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
         int i = this.getChainLength();
         Direction lv = this.getFacing();
         if (lv != null) {
            switch (lv) {
               case NORTH:
               default:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, i
                  );
                  break;
               case SOUTH:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, i
                  );
                  break;
               case WEST:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.WEST, i
                  );
                  break;
               case EAST:
                  MineshaftGenerator.pieceGenerator(
                     start, pieces, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.EAST, i
                  );
            }
         }
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
         if (this.isTouchingLiquid(arg, boundingBox)) {
            return false;
         } else {
            this.fillWithOutline(arg, boundingBox, 0, 5, 0, 2, 7, 1, AIR, AIR, false);
            this.fillWithOutline(arg, boundingBox, 0, 0, 7, 2, 2, 8, AIR, AIR, false);

            for (int i = 0; i < 5; i++) {
               this.fillWithOutline(arg, boundingBox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, AIR, AIR, false);
            }

            return true;
         }
      }
   }
}
