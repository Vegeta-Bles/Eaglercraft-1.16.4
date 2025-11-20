package net.minecraft.structure;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class OceanMonumentGenerator {
   public static class Base extends OceanMonumentGenerator.Piece {
      private OceanMonumentGenerator.PieceSetting field_14464;
      private OceanMonumentGenerator.PieceSetting field_14466;
      private final List<OceanMonumentGenerator.Piece> field_14465 = Lists.newArrayList();

      public Base(Random _snowman, int _snowman, int _snowman, Direction _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_BASE, 0);
         this.setOrientation(_snowman);
         Direction _snowmanxxxx = this.getFacing();
         if (_snowmanxxxx.getAxis() == Direction.Axis.Z) {
            this.boundingBox = new BlockBox(_snowman, 39, _snowman, _snowman + 58 - 1, 61, _snowman + 58 - 1);
         } else {
            this.boundingBox = new BlockBox(_snowman, 39, _snowman, _snowman + 58 - 1, 61, _snowman + 58 - 1);
         }

         List<OceanMonumentGenerator.PieceSetting> _snowmanxxxxx = this.method_14760(_snowman);
         this.field_14464.used = true;
         this.field_14465.add(new OceanMonumentGenerator.Entry(_snowmanxxxx, this.field_14464));
         this.field_14465.add(new OceanMonumentGenerator.CoreRoom(_snowmanxxxx, this.field_14466));
         List<OceanMonumentGenerator.PieceFactory> _snowmanxxxxxx = Lists.newArrayList();
         _snowmanxxxxxx.add(new OceanMonumentGenerator.DoubleXYRoomFactory());
         _snowmanxxxxxx.add(new OceanMonumentGenerator.DoubleYZRoomFactory());
         _snowmanxxxxxx.add(new OceanMonumentGenerator.DoubleZRoomFactory());
         _snowmanxxxxxx.add(new OceanMonumentGenerator.DoubleXRoomFactory());
         _snowmanxxxxxx.add(new OceanMonumentGenerator.DoubleYRoomFactory());
         _snowmanxxxxxx.add(new OceanMonumentGenerator.SimpleRoomTopFactory());
         _snowmanxxxxxx.add(new OceanMonumentGenerator.SimpleRoomFactory());

         for (OceanMonumentGenerator.PieceSetting _snowmanxxxxxxx : _snowmanxxxxx) {
            if (!_snowmanxxxxxxx.used && !_snowmanxxxxxxx.isAboveLevelThree()) {
               for (OceanMonumentGenerator.PieceFactory _snowmanxxxxxxxx : _snowmanxxxxxx) {
                  if (_snowmanxxxxxxxx.canGenerate(_snowmanxxxxxxx)) {
                     this.field_14465.add(_snowmanxxxxxxxx.generate(_snowmanxxxx, _snowmanxxxxxxx, _snowman));
                     break;
                  }
               }
            }
         }

         int _snowmanxxxxxxxxx = this.boundingBox.minY;
         int _snowmanxxxxxxxxxx = this.applyXTransform(9, 22);
         int _snowmanxxxxxxxxxxx = this.applyZTransform(9, 22);

         for (OceanMonumentGenerator.Piece _snowmanxxxxxxxxxxxx : this.field_14465) {
            _snowmanxxxxxxxxxxxx.getBoundingBox().move(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx);
         }

         BlockBox _snowmanxxxxxxxxxxxx = BlockBox.create(
            this.applyXTransform(1, 1),
            this.applyYTransform(1),
            this.applyZTransform(1, 1),
            this.applyXTransform(23, 21),
            this.applyYTransform(8),
            this.applyZTransform(23, 21)
         );
         BlockBox _snowmanxxxxxxxxxxxxx = BlockBox.create(
            this.applyXTransform(34, 1),
            this.applyYTransform(1),
            this.applyZTransform(34, 1),
            this.applyXTransform(56, 21),
            this.applyYTransform(8),
            this.applyZTransform(56, 21)
         );
         BlockBox _snowmanxxxxxxxxxxxxxx = BlockBox.create(
            this.applyXTransform(22, 22),
            this.applyYTransform(13),
            this.applyZTransform(22, 22),
            this.applyXTransform(35, 35),
            this.applyYTransform(17),
            this.applyZTransform(35, 35)
         );
         int _snowmanxxxxxxxxxxxxxxx = _snowman.nextInt();
         this.field_14465.add(new OceanMonumentGenerator.WingRoom(_snowmanxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx++));
         this.field_14465.add(new OceanMonumentGenerator.WingRoom(_snowmanxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx++));
         this.field_14465.add(new OceanMonumentGenerator.Penthouse(_snowmanxxxx, _snowmanxxxxxxxxxxxxxx));
      }

      public Base(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_BASE, _snowman);
      }

      private List<OceanMonumentGenerator.PieceSetting> method_14760(Random _snowman) {
         OceanMonumentGenerator.PieceSetting[] _snowmanx = new OceanMonumentGenerator.PieceSetting[75];

         for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               int _snowmanxxxx = 0;
               int _snowmanxxxxx = getIndex(_snowmanxx, 0, _snowmanxxx);
               _snowmanx[_snowmanxxxxx] = new OceanMonumentGenerator.PieceSetting(_snowmanxxxxx);
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               int _snowmanxxxx = 1;
               int _snowmanxxxxx = getIndex(_snowmanxx, 1, _snowmanxxx);
               _snowmanx[_snowmanxxxxx] = new OceanMonumentGenerator.PieceSetting(_snowmanxxxxx);
            }
         }

         for (int _snowmanxx = 1; _snowmanxx < 4; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
               int _snowmanxxxx = 2;
               int _snowmanxxxxx = getIndex(_snowmanxx, 2, _snowmanxxx);
               _snowmanx[_snowmanxxxxx] = new OceanMonumentGenerator.PieceSetting(_snowmanxxxxx);
            }
         }

         this.field_14464 = _snowmanx[TWO_ZERO_ZERO_INDEX];

         for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
                  int _snowmanxxxxx = getIndex(_snowmanxx, _snowmanxxxx, _snowmanxxx);
                  if (_snowmanx[_snowmanxxxxx] != null) {
                     for (Direction _snowmanxxxxxx : Direction.values()) {
                        int _snowmanxxxxxxx = _snowmanxx + _snowmanxxxxxx.getOffsetX();
                        int _snowmanxxxxxxxx = _snowmanxxxx + _snowmanxxxxxx.getOffsetY();
                        int _snowmanxxxxxxxxx = _snowmanxxx + _snowmanxxxxxx.getOffsetZ();
                        if (_snowmanxxxxxxx >= 0 && _snowmanxxxxxxx < 5 && _snowmanxxxxxxxxx >= 0 && _snowmanxxxxxxxxx < 5 && _snowmanxxxxxxxx >= 0 && _snowmanxxxxxxxx < 3) {
                           int _snowmanxxxxxxxxxx = getIndex(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
                           if (_snowmanx[_snowmanxxxxxxxxxx] != null) {
                              if (_snowmanxxxxxxxxx == _snowmanxxx) {
                                 _snowmanx[_snowmanxxxxx].setNeighbor(_snowmanxxxxxx, _snowmanx[_snowmanxxxxxxxxxx]);
                              } else {
                                 _snowmanx[_snowmanxxxxx].setNeighbor(_snowmanxxxxxx.getOpposite(), _snowmanx[_snowmanxxxxxxxxxx]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         OceanMonumentGenerator.PieceSetting _snowmanxx = new OceanMonumentGenerator.PieceSetting(1003);
         OceanMonumentGenerator.PieceSetting _snowmanxxx = new OceanMonumentGenerator.PieceSetting(1001);
         OceanMonumentGenerator.PieceSetting _snowmanxxxxx = new OceanMonumentGenerator.PieceSetting(1002);
         _snowmanx[TWO_TWO_ZERO_INDEX].setNeighbor(Direction.UP, _snowmanxx);
         _snowmanx[ZERO_ONE_ZERO_INDEX].setNeighbor(Direction.SOUTH, _snowmanxxx);
         _snowmanx[FOUR_ONE_ZERO_INDEX].setNeighbor(Direction.SOUTH, _snowmanxxxxx);
         _snowmanxx.used = true;
         _snowmanxxx.used = true;
         _snowmanxxxxx.used = true;
         this.field_14464.field_14484 = true;
         this.field_14466 = _snowmanx[getIndex(_snowman.nextInt(4), 0, 2)];
         this.field_14466.used = true;
         this.field_14466.neighbors[Direction.EAST.getId()].used = true;
         this.field_14466.neighbors[Direction.NORTH.getId()].used = true;
         this.field_14466.neighbors[Direction.EAST.getId()].neighbors[Direction.NORTH.getId()].used = true;
         this.field_14466.neighbors[Direction.UP.getId()].used = true;
         this.field_14466.neighbors[Direction.EAST.getId()].neighbors[Direction.UP.getId()].used = true;
         this.field_14466.neighbors[Direction.NORTH.getId()].neighbors[Direction.UP.getId()].used = true;
         this.field_14466.neighbors[Direction.EAST.getId()].neighbors[Direction.NORTH.getId()].neighbors[Direction.UP.getId()].used = true;
         List<OceanMonumentGenerator.PieceSetting> _snowmanxxxxxxx = Lists.newArrayList();

         for (OceanMonumentGenerator.PieceSetting _snowmanxxxxxxxx : _snowmanx) {
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxxx.checkNeighborStates();
               _snowmanxxxxxxx.add(_snowmanxxxxxxxx);
            }
         }

         _snowmanxx.checkNeighborStates();
         Collections.shuffle(_snowmanxxxxxxx, _snowman);
         int _snowmanxxxxxxxxx = 1;

         for (OceanMonumentGenerator.PieceSetting _snowmanxxxxxxxxxx : _snowmanxxxxxxx) {
            int _snowmanxxxxxxxxxxx = 0;
            int _snowmanxxxxxxxxxxxx = 0;

            while (_snowmanxxxxxxxxxxx < 2 && _snowmanxxxxxxxxxxxx < 5) {
               _snowmanxxxxxxxxxxxx++;
               int _snowmanxxxxxxxxxxxxx = _snowman.nextInt(6);
               if (_snowmanxxxxxxxxxx.neighborPresences[_snowmanxxxxxxxxxxxxx]) {
                  int _snowmanxxxxxxxxxxxxxx = Direction.byId(_snowmanxxxxxxxxxxxxx).getOpposite().getId();
                  _snowmanxxxxxxxxxx.neighborPresences[_snowmanxxxxxxxxxxxxx] = false;
                  _snowmanxxxxxxxxxx.neighbors[_snowmanxxxxxxxxxxxxx].neighborPresences[_snowmanxxxxxxxxxxxxxx] = false;
                  if (_snowmanxxxxxxxxxx.method_14783(_snowmanxxxxxxxxx++) && _snowmanxxxxxxxxxx.neighbors[_snowmanxxxxxxxxxxxxx].method_14783(_snowmanxxxxxxxxx++)) {
                     _snowmanxxxxxxxxxxx++;
                  } else {
                     _snowmanxxxxxxxxxx.neighborPresences[_snowmanxxxxxxxxxxxxx] = true;
                     _snowmanxxxxxxxxxx.neighbors[_snowmanxxxxxxxxxxxxx].neighborPresences[_snowmanxxxxxxxxxxxxxx] = true;
                  }
               }
            }
         }

         _snowmanxxxxxxx.add(_snowmanxx);
         _snowmanxxxxxxx.add(_snowmanxxx);
         _snowmanxxxxxxx.add(_snowmanxxxxx);
         return _snowmanxxxxxxx;
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
         int _snowmanxxx = Math.max(_snowman.getSeaLevel(), 64) - this.boundingBox.minY;
         this.setAirAndWater(_snowman, boundingBox, 0, 0, 0, 58, _snowmanxxx, 58);
         this.method_14761(false, 0, _snowman, random, boundingBox);
         this.method_14761(true, 33, _snowman, random, boundingBox);
         this.method_14763(_snowman, random, boundingBox);
         this.method_14762(_snowman, random, boundingBox);
         this.method_14765(_snowman, random, boundingBox);
         this.method_14764(_snowman, random, boundingBox);
         this.method_14766(_snowman, random, boundingBox);
         this.method_14767(_snowman, random, boundingBox);

         for (int _snowmanxxxx = 0; _snowmanxxxx < 7; _snowmanxxxx++) {
            int _snowmanxxxxx = 0;

            while (_snowmanxxxxx < 7) {
               if (_snowmanxxxxx == 0 && _snowmanxxxx == 3) {
                  _snowmanxxxxx = 6;
               }

               int _snowmanxxxxxx = _snowmanxxxx * 9;
               int _snowmanxxxxxxx = _snowmanxxxxx * 9;

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 4; _snowmanxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 4; _snowmanxxxxxxxxx++) {
                     this.addBlock(_snowman, PRISMARINE_BRICKS, _snowmanxxxxxx + _snowmanxxxxxxxx, 0, _snowmanxxxxxxx + _snowmanxxxxxxxxx, boundingBox);
                     this.fillDownwards(_snowman, PRISMARINE_BRICKS, _snowmanxxxxxx + _snowmanxxxxxxxx, -1, _snowmanxxxxxxx + _snowmanxxxxxxxxx, boundingBox);
                  }
               }

               if (_snowmanxxxx != 0 && _snowmanxxxx != 6) {
                  _snowmanxxxxx += 6;
               } else {
                  _snowmanxxxxx++;
               }
            }
         }

         for (int _snowmanxxxx = 0; _snowmanxxxx < 5; _snowmanxxxx++) {
            this.setAirAndWater(_snowman, boundingBox, -1 - _snowmanxxxx, 0 + _snowmanxxxx * 2, -1 - _snowmanxxxx, -1 - _snowmanxxxx, 23, 58 + _snowmanxxxx);
            this.setAirAndWater(_snowman, boundingBox, 58 + _snowmanxxxx, 0 + _snowmanxxxx * 2, -1 - _snowmanxxxx, 58 + _snowmanxxxx, 23, 58 + _snowmanxxxx);
            this.setAirAndWater(_snowman, boundingBox, 0 - _snowmanxxxx, 0 + _snowmanxxxx * 2, -1 - _snowmanxxxx, 57 + _snowmanxxxx, 23, -1 - _snowmanxxxx);
            this.setAirAndWater(_snowman, boundingBox, 0 - _snowmanxxxx, 0 + _snowmanxxxx * 2, 58 + _snowmanxxxx, 57 + _snowmanxxxx, 23, 58 + _snowmanxxxx);
         }

         for (OceanMonumentGenerator.Piece _snowmanxxxx : this.field_14465) {
            if (_snowmanxxxx.getBoundingBox().intersects(boundingBox)) {
               _snowmanxxxx.generate(_snowman, structureAccessor, chunkGenerator, random, boundingBox, _snowman, _snowman);
            }
         }

         return true;
      }

      private void method_14761(boolean _snowman, int _snowman, StructureWorldAccess _snowman, Random _snowman, BlockBox _snowman) {
         int _snowmanxxxxx = 24;
         if (this.method_14775(_snowman, _snowman, 0, _snowman + 23, 20)) {
            this.fillWithOutline(_snowman, _snowman, _snowman + 0, 0, 0, _snowman + 24, 0, 20, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, _snowman + 0, 1, 0, _snowman + 24, 10, 20);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
               this.fillWithOutline(_snowman, _snowman, _snowman + _snowmanxxxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxx, _snowman + _snowmanxxxxxx, _snowmanxxxxxx + 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(
                  _snowman, _snowman, _snowman + _snowmanxxxxxx + 7, _snowmanxxxxxx + 5, _snowmanxxxxxx + 7, _snowman + _snowmanxxxxxx + 7, _snowmanxxxxxx + 5, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false
               );
               this.fillWithOutline(
                  _snowman, _snowman, _snowman + 17 - _snowmanxxxxxx, _snowmanxxxxxx + 5, _snowmanxxxxxx + 7, _snowman + 17 - _snowmanxxxxxx, _snowmanxxxxxx + 5, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false
               );
               this.fillWithOutline(
                  _snowman, _snowman, _snowman + 24 - _snowmanxxxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxx, _snowman + 24 - _snowmanxxxxxx, _snowmanxxxxxx + 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false
               );
               this.fillWithOutline(
                  _snowman, _snowman, _snowman + _snowmanxxxxxx + 1, _snowmanxxxxxx + 1, _snowmanxxxxxx, _snowman + 23 - _snowmanxxxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false
               );
               this.fillWithOutline(
                  _snowman, _snowman, _snowman + _snowmanxxxxxx + 8, _snowmanxxxxxx + 5, _snowmanxxxxxx + 7, _snowman + 16 - _snowmanxxxxxx, _snowmanxxxxxx + 5, _snowmanxxxxxx + 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false
               );
            }

            this.fillWithOutline(_snowman, _snowman, _snowman + 4, 4, 4, _snowman + 6, 4, 20, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 7, 4, 4, _snowman + 17, 4, 6, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 18, 4, 4, _snowman + 20, 4, 20, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 11, 8, 11, _snowman + 13, 8, 20, PRISMARINE, PRISMARINE, false);
            this.addBlock(_snowman, field_14470, _snowman + 12, 9, 12, _snowman);
            this.addBlock(_snowman, field_14470, _snowman + 12, 9, 15, _snowman);
            this.addBlock(_snowman, field_14470, _snowman + 12, 9, 18, _snowman);
            int _snowmanxxxxxx = _snowman + (_snowman ? 19 : 5);
            int _snowmanxxxxxxx = _snowman + (_snowman ? 5 : 19);

            for (int _snowmanxxxxxxxx = 20; _snowmanxxxxxxxx >= 5; _snowmanxxxxxxxx -= 3) {
               this.addBlock(_snowman, field_14470, _snowmanxxxxxx, 5, _snowmanxxxxxxxx, _snowman);
            }

            for (int _snowmanxxxxxxxx = 19; _snowmanxxxxxxxx >= 7; _snowmanxxxxxxxx -= 3) {
               this.addBlock(_snowman, field_14470, _snowmanxxxxxxx, 5, _snowmanxxxxxxxx, _snowman);
            }

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 4; _snowmanxxxxxxxx++) {
               int _snowmanxxxxxxxxx = _snowman ? _snowman + 24 - (17 - _snowmanxxxxxxxx * 3) : _snowman + 17 - _snowmanxxxxxxxx * 3;
               this.addBlock(_snowman, field_14470, _snowmanxxxxxxxxx, 5, 5, _snowman);
            }

            this.addBlock(_snowman, field_14470, _snowmanxxxxxxx, 5, 5, _snowman);
            this.fillWithOutline(_snowman, _snowman, _snowman + 11, 1, 12, _snowman + 13, 7, 12, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 12, 1, 11, _snowman + 12, 7, 13, PRISMARINE, PRISMARINE, false);
         }
      }

      private void method_14763(StructureWorldAccess _snowman, Random _snowman, BlockBox _snowman) {
         if (this.method_14775(_snowman, 22, 5, 35, 17)) {
            this.setAirAndWater(_snowman, _snowman, 25, 0, 0, 32, 8, 20);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, 24, 2, 5 + _snowmanxxx * 4, 24, 4, 5 + _snowmanxxx * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, _snowman, 22, 4, 5 + _snowmanxxx * 4, 23, 4, 5 + _snowmanxxx * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.addBlock(_snowman, PRISMARINE_BRICKS, 25, 5, 5 + _snowmanxxx * 4, _snowman);
               this.addBlock(_snowman, PRISMARINE_BRICKS, 26, 6, 5 + _snowmanxxx * 4, _snowman);
               this.addBlock(_snowman, SEA_LANTERN, 26, 5, 5 + _snowmanxxx * 4, _snowman);
               this.fillWithOutline(_snowman, _snowman, 33, 2, 5 + _snowmanxxx * 4, 33, 4, 5 + _snowmanxxx * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, _snowman, 34, 4, 5 + _snowmanxxx * 4, 35, 4, 5 + _snowmanxxx * 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.addBlock(_snowman, PRISMARINE_BRICKS, 32, 5, 5 + _snowmanxxx * 4, _snowman);
               this.addBlock(_snowman, PRISMARINE_BRICKS, 31, 6, 5 + _snowmanxxx * 4, _snowman);
               this.addBlock(_snowman, SEA_LANTERN, 31, 5, 5 + _snowmanxxx * 4, _snowman);
               this.fillWithOutline(_snowman, _snowman, 27, 6, 5 + _snowmanxxx * 4, 30, 6, 5 + _snowmanxxx * 4, PRISMARINE, PRISMARINE, false);
            }
         }
      }

      private void method_14762(StructureWorldAccess _snowman, Random _snowman, BlockBox _snowman) {
         if (this.method_14775(_snowman, 15, 20, 42, 21)) {
            this.fillWithOutline(_snowman, _snowman, 15, 0, 21, 42, 0, 21, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 26, 1, 21, 31, 3, 21);
            this.fillWithOutline(_snowman, _snowman, 21, 12, 21, 36, 12, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 17, 11, 21, 40, 11, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 16, 10, 21, 41, 10, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 15, 7, 21, 42, 9, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 16, 6, 21, 41, 6, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 17, 5, 21, 40, 5, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 21, 4, 21, 36, 4, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 22, 3, 21, 26, 3, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 31, 3, 21, 35, 3, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 23, 2, 21, 25, 2, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 32, 2, 21, 34, 2, 21, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 28, 4, 20, 29, 4, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 27, 3, 21, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 30, 3, 21, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 26, 2, 21, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 31, 2, 21, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 25, 1, 21, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 32, 1, 21, _snowman);

            for (int _snowmanxxx = 0; _snowmanxxx < 7; _snowmanxxx++) {
               this.addBlock(_snowman, DARK_PRISMARINE, 28 - _snowmanxxx, 6 + _snowmanxxx, 21, _snowman);
               this.addBlock(_snowman, DARK_PRISMARINE, 29 + _snowmanxxx, 6 + _snowmanxxx, 21, _snowman);
            }

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.addBlock(_snowman, DARK_PRISMARINE, 28 - _snowmanxxx, 9 + _snowmanxxx, 21, _snowman);
               this.addBlock(_snowman, DARK_PRISMARINE, 29 + _snowmanxxx, 9 + _snowmanxxx, 21, _snowman);
            }

            this.addBlock(_snowman, DARK_PRISMARINE, 28, 12, 21, _snowman);
            this.addBlock(_snowman, DARK_PRISMARINE, 29, 12, 21, _snowman);

            for (int _snowmanxxx = 0; _snowmanxxx < 3; _snowmanxxx++) {
               this.addBlock(_snowman, DARK_PRISMARINE, 22 - _snowmanxxx * 2, 8, 21, _snowman);
               this.addBlock(_snowman, DARK_PRISMARINE, 22 - _snowmanxxx * 2, 9, 21, _snowman);
               this.addBlock(_snowman, DARK_PRISMARINE, 35 + _snowmanxxx * 2, 8, 21, _snowman);
               this.addBlock(_snowman, DARK_PRISMARINE, 35 + _snowmanxxx * 2, 9, 21, _snowman);
            }

            this.setAirAndWater(_snowman, _snowman, 15, 13, 21, 42, 15, 21);
            this.setAirAndWater(_snowman, _snowman, 15, 1, 21, 15, 6, 21);
            this.setAirAndWater(_snowman, _snowman, 16, 1, 21, 16, 5, 21);
            this.setAirAndWater(_snowman, _snowman, 17, 1, 21, 20, 4, 21);
            this.setAirAndWater(_snowman, _snowman, 21, 1, 21, 21, 3, 21);
            this.setAirAndWater(_snowman, _snowman, 22, 1, 21, 22, 2, 21);
            this.setAirAndWater(_snowman, _snowman, 23, 1, 21, 24, 1, 21);
            this.setAirAndWater(_snowman, _snowman, 42, 1, 21, 42, 6, 21);
            this.setAirAndWater(_snowman, _snowman, 41, 1, 21, 41, 5, 21);
            this.setAirAndWater(_snowman, _snowman, 37, 1, 21, 40, 4, 21);
            this.setAirAndWater(_snowman, _snowman, 36, 1, 21, 36, 3, 21);
            this.setAirAndWater(_snowman, _snowman, 33, 1, 21, 34, 1, 21);
            this.setAirAndWater(_snowman, _snowman, 35, 1, 21, 35, 2, 21);
         }
      }

      private void method_14765(StructureWorldAccess _snowman, Random _snowman, BlockBox _snowman) {
         if (this.method_14775(_snowman, 21, 21, 36, 36)) {
            this.fillWithOutline(_snowman, _snowman, 21, 0, 22, 36, 0, 36, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 21, 1, 22, 36, 23, 36);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, 21 + _snowmanxxx, 13 + _snowmanxxx, 21 + _snowmanxxx, 36 - _snowmanxxx, 13 + _snowmanxxx, 21 + _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, _snowman, 21 + _snowmanxxx, 13 + _snowmanxxx, 36 - _snowmanxxx, 36 - _snowmanxxx, 13 + _snowmanxxx, 36 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, _snowman, 21 + _snowmanxxx, 13 + _snowmanxxx, 22 + _snowmanxxx, 21 + _snowmanxxx, 13 + _snowmanxxx, 35 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, _snowman, 36 - _snowmanxxx, 13 + _snowmanxxx, 22 + _snowmanxxx, 36 - _snowmanxxx, 13 + _snowmanxxx, 35 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            this.fillWithOutline(_snowman, _snowman, 25, 16, 25, 32, 16, 32, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 25, 17, 25, 25, 19, 25, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, _snowman, 32, 17, 25, 32, 19, 25, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, _snowman, 25, 17, 32, 25, 19, 32, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, _snowman, 32, 17, 32, 32, 19, 32, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 26, 20, 26, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 27, 21, 27, _snowman);
            this.addBlock(_snowman, SEA_LANTERN, 27, 20, 27, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 26, 20, 31, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 27, 21, 30, _snowman);
            this.addBlock(_snowman, SEA_LANTERN, 27, 20, 30, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 31, 20, 31, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 30, 21, 30, _snowman);
            this.addBlock(_snowman, SEA_LANTERN, 30, 20, 30, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 31, 20, 26, _snowman);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 30, 21, 27, _snowman);
            this.addBlock(_snowman, SEA_LANTERN, 30, 20, 27, _snowman);
            this.fillWithOutline(_snowman, _snowman, 28, 21, 27, 29, 21, 27, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 27, 21, 28, 27, 21, 29, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 28, 21, 30, 29, 21, 30, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 30, 21, 28, 30, 21, 29, PRISMARINE, PRISMARINE, false);
         }
      }

      private void method_14764(StructureWorldAccess _snowman, Random _snowman, BlockBox _snowman) {
         if (this.method_14775(_snowman, 0, 21, 6, 58)) {
            this.fillWithOutline(_snowman, _snowman, 0, 0, 21, 6, 0, 57, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 0, 1, 21, 6, 7, 57);
            this.fillWithOutline(_snowman, _snowman, 4, 4, 21, 6, 4, 53, PRISMARINE, PRISMARINE, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, _snowmanxxx, _snowmanxxx + 1, 21, _snowmanxxx, _snowmanxxx + 1, 57 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            for (int _snowmanxxx = 23; _snowmanxxx < 53; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, 5, 5, _snowmanxxx, _snowman);
            }

            this.addBlock(_snowman, field_14470, 5, 5, 52, _snowman);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, _snowmanxxx, _snowmanxxx + 1, 21, _snowmanxxx, _snowmanxxx + 1, 57 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            this.fillWithOutline(_snowman, _snowman, 4, 1, 52, 6, 3, 52, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 5, 1, 51, 5, 3, 53, PRISMARINE, PRISMARINE, false);
         }

         if (this.method_14775(_snowman, 51, 21, 58, 58)) {
            this.fillWithOutline(_snowman, _snowman, 51, 0, 21, 57, 0, 57, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 51, 1, 21, 57, 7, 57);
            this.fillWithOutline(_snowman, _snowman, 51, 4, 21, 53, 4, 53, PRISMARINE, PRISMARINE, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, 57 - _snowmanxxx, _snowmanxxx + 1, 21, 57 - _snowmanxxx, _snowmanxxx + 1, 57 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            for (int _snowmanxxx = 23; _snowmanxxx < 53; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, 52, 5, _snowmanxxx, _snowman);
            }

            this.addBlock(_snowman, field_14470, 52, 5, 52, _snowman);
            this.fillWithOutline(_snowman, _snowman, 51, 1, 52, 53, 3, 52, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 52, 1, 51, 52, 3, 53, PRISMARINE, PRISMARINE, false);
         }

         if (this.method_14775(_snowman, 0, 51, 57, 57)) {
            this.fillWithOutline(_snowman, _snowman, 7, 0, 51, 50, 0, 57, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 7, 1, 51, 50, 10, 57);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, _snowmanxxx + 1, _snowmanxxx + 1, 57 - _snowmanxxx, 56 - _snowmanxxx, _snowmanxxx + 1, 57 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
         }
      }

      private void method_14766(StructureWorldAccess _snowman, Random _snowman, BlockBox _snowman) {
         if (this.method_14775(_snowman, 7, 21, 13, 50)) {
            this.fillWithOutline(_snowman, _snowman, 7, 0, 21, 13, 0, 50, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 7, 1, 21, 13, 10, 50);
            this.fillWithOutline(_snowman, _snowman, 11, 8, 21, 13, 8, 53, PRISMARINE, PRISMARINE, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, _snowmanxxx + 7, _snowmanxxx + 5, 21, _snowmanxxx + 7, _snowmanxxx + 5, 54, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            for (int _snowmanxxx = 21; _snowmanxxx <= 45; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, 12, 9, _snowmanxxx, _snowman);
            }
         }

         if (this.method_14775(_snowman, 44, 21, 50, 54)) {
            this.fillWithOutline(_snowman, _snowman, 44, 0, 21, 50, 0, 50, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 44, 1, 21, 50, 10, 50);
            this.fillWithOutline(_snowman, _snowman, 44, 8, 21, 46, 8, 53, PRISMARINE, PRISMARINE, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, 50 - _snowmanxxx, _snowmanxxx + 5, 21, 50 - _snowmanxxx, _snowmanxxx + 5, 54, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            for (int _snowmanxxx = 21; _snowmanxxx <= 45; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, 45, 9, _snowmanxxx, _snowman);
            }
         }

         if (this.method_14775(_snowman, 8, 44, 49, 54)) {
            this.fillWithOutline(_snowman, _snowman, 14, 0, 44, 43, 0, 50, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 14, 1, 44, 43, 10, 50);

            for (int _snowmanxxx = 12; _snowmanxxx <= 45; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, _snowmanxxx, 9, 45, _snowman);
               this.addBlock(_snowman, field_14470, _snowmanxxx, 9, 52, _snowman);
               if (_snowmanxxx == 12 || _snowmanxxx == 18 || _snowmanxxx == 24 || _snowmanxxx == 33 || _snowmanxxx == 39 || _snowmanxxx == 45) {
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 9, 47, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 9, 50, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 10, 45, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 10, 46, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 10, 51, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 10, 52, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 11, 47, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 11, 50, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 12, 48, _snowman);
                  this.addBlock(_snowman, field_14470, _snowmanxxx, 12, 49, _snowman);
               }
            }

            for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
               this.fillWithOutline(_snowman, _snowman, 8 + _snowmanxxxx, 5 + _snowmanxxxx, 54, 49 - _snowmanxxxx, 5 + _snowmanxxxx, 54, PRISMARINE, PRISMARINE, false);
            }

            this.fillWithOutline(_snowman, _snowman, 11, 8, 54, 46, 8, 54, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, _snowman, 14, 8, 44, 43, 8, 53, PRISMARINE, PRISMARINE, false);
         }
      }

      private void method_14767(StructureWorldAccess _snowman, Random _snowman, BlockBox _snowman) {
         if (this.method_14775(_snowman, 14, 21, 20, 43)) {
            this.fillWithOutline(_snowman, _snowman, 14, 0, 21, 20, 0, 43, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 14, 1, 22, 20, 14, 43);
            this.fillWithOutline(_snowman, _snowman, 18, 12, 22, 20, 12, 39, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 18, 12, 21, 20, 12, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, _snowmanxxx + 14, _snowmanxxx + 9, 21, _snowmanxxx + 14, _snowmanxxx + 9, 43 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            for (int _snowmanxxx = 23; _snowmanxxx <= 39; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, 19, 13, _snowmanxxx, _snowman);
            }
         }

         if (this.method_14775(_snowman, 37, 21, 43, 43)) {
            this.fillWithOutline(_snowman, _snowman, 37, 0, 21, 43, 0, 43, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 37, 1, 22, 43, 14, 43);
            this.fillWithOutline(_snowman, _snowman, 37, 12, 22, 39, 12, 39, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, 37, 12, 21, 39, 12, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, 43 - _snowmanxxx, _snowmanxxx + 9, 21, 43 - _snowmanxxx, _snowmanxxx + 9, 43 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            for (int _snowmanxxx = 23; _snowmanxxx <= 39; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, 38, 13, _snowmanxxx, _snowman);
            }
         }

         if (this.method_14775(_snowman, 15, 37, 42, 43)) {
            this.fillWithOutline(_snowman, _snowman, 21, 0, 37, 36, 0, 43, PRISMARINE, PRISMARINE, false);
            this.setAirAndWater(_snowman, _snowman, 21, 1, 37, 36, 14, 43);
            this.fillWithOutline(_snowman, _snowman, 21, 12, 37, 36, 12, 39, PRISMARINE, PRISMARINE, false);

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, _snowman, 15 + _snowmanxxx, _snowmanxxx + 9, 43 - _snowmanxxx, 42 - _snowmanxxx, _snowmanxxx + 9, 43 - _snowmanxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            for (int _snowmanxxx = 21; _snowmanxxx <= 36; _snowmanxxx += 3) {
               this.addBlock(_snowman, field_14470, _snowmanxxx, 13, 38, _snowman);
            }
         }
      }
   }

   public static class CoreRoom extends OceanMonumentGenerator.Piece {
      public CoreRoom(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, 1, _snowman, _snowman, 2, 2, 2);
      }

      public CoreRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, _snowman);
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
         this.method_14771(_snowman, boundingBox, 1, 8, 0, 14, 8, 14, PRISMARINE);
         int _snowmanxxx = 7;
         BlockState _snowmanxxxx = PRISMARINE_BRICKS;
         this.fillWithOutline(_snowman, boundingBox, 0, 7, 0, 0, 7, 15, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 15, 7, 0, 15, 7, 15, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 7, 0, 15, 7, 0, _snowmanxxxx, _snowmanxxxx, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 7, 15, 14, 7, 15, _snowmanxxxx, _snowmanxxxx, false);

         for (int _snowmanxxxxx = 1; _snowmanxxxxx <= 6; _snowmanxxxxx++) {
            _snowmanxxxx = PRISMARINE_BRICKS;
            if (_snowmanxxxxx == 2 || _snowmanxxxxx == 6) {
               _snowmanxxxx = PRISMARINE;
            }

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 15; _snowmanxxxxxx += 15) {
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxx, _snowmanxxxxx, 0, _snowmanxxxxxx, _snowmanxxxxx, 1, _snowmanxxxx, _snowmanxxxx, false);
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxx, _snowmanxxxxx, 6, _snowmanxxxxxx, _snowmanxxxxx, 9, _snowmanxxxx, _snowmanxxxx, false);
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxx, _snowmanxxxxx, 14, _snowmanxxxxxx, _snowmanxxxxx, 15, _snowmanxxxx, _snowmanxxxx, false);
            }

            this.fillWithOutline(_snowman, boundingBox, 1, _snowmanxxxxx, 0, 1, _snowmanxxxxx, 0, _snowmanxxxx, _snowmanxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 6, _snowmanxxxxx, 0, 9, _snowmanxxxxx, 0, _snowmanxxxx, _snowmanxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 14, _snowmanxxxxx, 0, 14, _snowmanxxxxx, 0, _snowmanxxxx, _snowmanxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 1, _snowmanxxxxx, 15, 14, _snowmanxxxxx, 15, _snowmanxxxx, _snowmanxxxx, false);
         }

         this.fillWithOutline(_snowman, boundingBox, 6, 3, 6, 9, 6, 9, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), false);

         for (int _snowmanxxxxx = 3; _snowmanxxxxx <= 6; _snowmanxxxxx += 3) {
            for (int _snowmanxxxxxx = 6; _snowmanxxxxxx <= 9; _snowmanxxxxxx += 3) {
               this.addBlock(_snowman, SEA_LANTERN, _snowmanxxxxxx, _snowmanxxxxx, 6, boundingBox);
               this.addBlock(_snowman, SEA_LANTERN, _snowmanxxxxxx, _snowmanxxxxx, 9, boundingBox);
            }
         }

         this.fillWithOutline(_snowman, boundingBox, 5, 1, 6, 5, 2, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 9, 5, 2, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 1, 6, 10, 2, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 1, 9, 10, 2, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 1, 5, 6, 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 9, 1, 5, 9, 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 1, 10, 6, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 9, 1, 10, 9, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 5, 5, 6, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 10, 5, 6, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 2, 5, 10, 6, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 2, 10, 10, 6, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 7, 1, 5, 7, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 7, 1, 10, 7, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 7, 9, 5, 7, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 7, 9, 10, 7, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 7, 5, 6, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 7, 10, 6, 7, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 9, 7, 5, 14, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 9, 7, 10, 14, 7, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 1, 2, 2, 1, 3, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 2, 3, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 13, 1, 2, 13, 1, 3, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 12, 1, 2, 12, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 1, 12, 2, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 13, 3, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 13, 1, 12, 13, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 12, 1, 13, 12, 1, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         return true;
      }
   }

   public static class DoubleXRoom extends OceanMonumentGenerator.Piece {
      public DoubleXRoom(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, 1, _snowman, _snowman, 2, 1, 1);
      }

      public DoubleXRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, _snowman);
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
         OceanMonumentGenerator.PieceSetting _snowmanxxx = this.setting.neighbors[Direction.EAST.getId()];
         OceanMonumentGenerator.PieceSetting _snowmanxxxx = this.setting;
         if (this.setting.roomIndex / 25 > 0) {
            this.method_14774(_snowman, boundingBox, 8, 0, _snowmanxxx.neighborPresences[Direction.DOWN.getId()]);
            this.method_14774(_snowman, boundingBox, 0, 0, _snowmanxxxx.neighborPresences[Direction.DOWN.getId()]);
         }

         if (_snowmanxxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 4, 1, 7, 4, 6, PRISMARINE);
         }

         if (_snowmanxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 8, 4, 1, 14, 4, 6, PRISMARINE);
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 0, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 15, 3, 0, 15, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 0, 15, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 7, 14, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 2, 7, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 15, 2, 0, 15, 2, 7, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 0, 15, 2, 0, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 7, 14, 2, 7, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 15, 1, 0, 15, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 0, 15, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 7, 14, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 0, 10, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 2, 0, 9, 2, 3, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 3, 0, 10, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.addBlock(_snowman, SEA_LANTERN, 6, 2, 3, boundingBox);
         this.addBlock(_snowman, SEA_LANTERN, 9, 2, 3, boundingBox);
         if (_snowmanxxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanxxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 7, 4, 2, 7);
         }

         if (_snowmanxxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 1, 3, 0, 2, 4);
         }

         if (_snowmanxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 11, 1, 0, 12, 2, 0);
         }

         if (_snowmanxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 11, 1, 7, 12, 2, 7);
         }

         if (_snowmanxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 15, 1, 3, 15, 2, 4);
         }

         return true;
      }
   }

   static class DoubleXRoomFactory implements OceanMonumentGenerator.PieceFactory {
      private DoubleXRoomFactory() {
      }

      @Override
      public boolean canGenerate(OceanMonumentGenerator.PieceSetting setting) {
         return setting.neighborPresences[Direction.EAST.getId()] && !setting.neighbors[Direction.EAST.getId()].used;
      }

      @Override
      public OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random) {
         setting.used = true;
         setting.neighbors[Direction.EAST.getId()].used = true;
         return new OceanMonumentGenerator.DoubleXRoom(direction, setting);
      }
   }

   public static class DoubleXYRoom extends OceanMonumentGenerator.Piece {
      public DoubleXYRoom(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_Y_ROOM, 1, _snowman, _snowman, 2, 2, 1);
      }

      public DoubleXYRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_Y_ROOM, _snowman);
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
         OceanMonumentGenerator.PieceSetting _snowmanxxx = this.setting.neighbors[Direction.EAST.getId()];
         OceanMonumentGenerator.PieceSetting _snowmanxxxx = this.setting;
         OceanMonumentGenerator.PieceSetting _snowmanxxxxx = _snowmanxxxx.neighbors[Direction.UP.getId()];
         OceanMonumentGenerator.PieceSetting _snowmanxxxxxx = _snowmanxxx.neighbors[Direction.UP.getId()];
         if (this.setting.roomIndex / 25 > 0) {
            this.method_14774(_snowman, boundingBox, 8, 0, _snowmanxxx.neighborPresences[Direction.DOWN.getId()]);
            this.method_14774(_snowman, boundingBox, 0, 0, _snowmanxxxx.neighborPresences[Direction.DOWN.getId()]);
         }

         if (_snowmanxxxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 8, 1, 7, 8, 6, PRISMARINE);
         }

         if (_snowmanxxxxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 8, 8, 1, 14, 8, 6, PRISMARINE);
         }

         for (int _snowmanxxxxxxx = 1; _snowmanxxxxxxx <= 7; _snowmanxxxxxxx++) {
            BlockState _snowmanxxxxxxxx = PRISMARINE_BRICKS;
            if (_snowmanxxxxxxx == 2 || _snowmanxxxxxxx == 6) {
               _snowmanxxxxxxxx = PRISMARINE;
            }

            this.fillWithOutline(_snowman, boundingBox, 0, _snowmanxxxxxxx, 0, 0, _snowmanxxxxxxx, 7, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 15, _snowmanxxxxxxx, 0, 15, _snowmanxxxxxxx, 7, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 1, _snowmanxxxxxxx, 0, 15, _snowmanxxxxxxx, 0, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 1, _snowmanxxxxxxx, 7, 14, _snowmanxxxxxxx, 7, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
         }

         this.fillWithOutline(_snowman, boundingBox, 2, 1, 3, 2, 7, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 2, 4, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 5, 4, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 13, 1, 3, 13, 7, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 11, 1, 2, 12, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 11, 1, 5, 12, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 3, 5, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 1, 3, 10, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 7, 2, 10, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 5, 2, 5, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 5, 2, 10, 7, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 5, 5, 5, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 5, 5, 10, 7, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 6, 6, 2, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 9, 6, 2, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 6, 6, 5, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 9, 6, 5, boundingBox);
         this.fillWithOutline(_snowman, boundingBox, 5, 4, 3, 6, 4, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 9, 4, 3, 10, 4, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.addBlock(_snowman, SEA_LANTERN, 5, 4, 2, boundingBox);
         this.addBlock(_snowman, SEA_LANTERN, 5, 4, 5, boundingBox);
         this.addBlock(_snowman, SEA_LANTERN, 10, 4, 2, boundingBox);
         this.addBlock(_snowman, SEA_LANTERN, 10, 4, 5, boundingBox);
         if (_snowmanxxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanxxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 7, 4, 2, 7);
         }

         if (_snowmanxxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 1, 3, 0, 2, 4);
         }

         if (_snowmanxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 11, 1, 0, 12, 2, 0);
         }

         if (_snowmanxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 11, 1, 7, 12, 2, 7);
         }

         if (_snowmanxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 15, 1, 3, 15, 2, 4);
         }

         if (_snowmanxxxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 5, 0, 4, 6, 0);
         }

         if (_snowmanxxxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 5, 7, 4, 6, 7);
         }

         if (_snowmanxxxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 5, 3, 0, 6, 4);
         }

         if (_snowmanxxxxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 11, 5, 0, 12, 6, 0);
         }

         if (_snowmanxxxxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 11, 5, 7, 12, 6, 7);
         }

         if (_snowmanxxxxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 15, 5, 3, 15, 6, 4);
         }

         return true;
      }
   }

   static class DoubleXYRoomFactory implements OceanMonumentGenerator.PieceFactory {
      private DoubleXYRoomFactory() {
      }

      @Override
      public boolean canGenerate(OceanMonumentGenerator.PieceSetting setting) {
         if (setting.neighborPresences[Direction.EAST.getId()]
            && !setting.neighbors[Direction.EAST.getId()].used
            && setting.neighborPresences[Direction.UP.getId()]
            && !setting.neighbors[Direction.UP.getId()].used) {
            OceanMonumentGenerator.PieceSetting _snowman = setting.neighbors[Direction.EAST.getId()];
            return _snowman.neighborPresences[Direction.UP.getId()] && !_snowman.neighbors[Direction.UP.getId()].used;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random) {
         setting.used = true;
         setting.neighbors[Direction.EAST.getId()].used = true;
         setting.neighbors[Direction.UP.getId()].used = true;
         setting.neighbors[Direction.EAST.getId()].neighbors[Direction.UP.getId()].used = true;
         return new OceanMonumentGenerator.DoubleXYRoom(direction, setting);
      }
   }

   public static class DoubleYRoom extends OceanMonumentGenerator.Piece {
      public DoubleYRoom(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, 1, _snowman, _snowman, 1, 2, 1);
      }

      public DoubleYRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, _snowman);
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
         if (this.setting.roomIndex / 25 > 0) {
            this.method_14774(_snowman, boundingBox, 0, 0, this.setting.neighborPresences[Direction.DOWN.getId()]);
         }

         OceanMonumentGenerator.PieceSetting _snowmanxxx = this.setting.neighbors[Direction.UP.getId()];
         if (_snowmanxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 8, 1, 6, 8, 6, PRISMARINE);
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 4, 0, 0, 4, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 4, 0, 7, 4, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 0, 6, 4, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 7, 6, 4, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 4, 1, 2, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 2, 1, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 4, 1, 5, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 4, 2, 6, 4, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 4, 5, 2, 4, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 4, 5, 1, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 4, 5, 5, 4, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 4, 5, 6, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         OceanMonumentGenerator.PieceSetting _snowmanxxxx = this.setting;

         for (int _snowmanxxxxx = 1; _snowmanxxxxx <= 5; _snowmanxxxxx += 4) {
            int _snowmanxxxxxx = 0;
            if (_snowmanxxxx.neighborPresences[Direction.SOUTH.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 2, _snowmanxxxxx, _snowmanxxxxxx, 2, _snowmanxxxxx + 2, _snowmanxxxxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 5, _snowmanxxxxx, _snowmanxxxxxx, 5, _snowmanxxxxx + 2, _snowmanxxxxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 3, _snowmanxxxxx + 2, _snowmanxxxxxx, 4, _snowmanxxxxx + 2, _snowmanxxxxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, 0, _snowmanxxxxx, _snowmanxxxxxx, 7, _snowmanxxxxx + 2, _snowmanxxxxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 0, _snowmanxxxxx + 1, _snowmanxxxxxx, 7, _snowmanxxxxx + 1, _snowmanxxxxxx, PRISMARINE, PRISMARINE, false);
            }

            int var13 = 7;
            if (_snowmanxxxx.neighborPresences[Direction.NORTH.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 2, _snowmanxxxxx, var13, 2, _snowmanxxxxx + 2, var13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 5, _snowmanxxxxx, var13, 5, _snowmanxxxxx + 2, var13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 3, _snowmanxxxxx + 2, var13, 4, _snowmanxxxxx + 2, var13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, 0, _snowmanxxxxx, var13, 7, _snowmanxxxxx + 2, var13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 0, _snowmanxxxxx + 1, var13, 7, _snowmanxxxxx + 1, var13, PRISMARINE, PRISMARINE, false);
            }

            int _snowmanxxxxxxx = 0;
            if (_snowmanxxxx.neighborPresences[Direction.WEST.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxxx, _snowmanxxxxx, 2, _snowmanxxxxxxx, _snowmanxxxxx + 2, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxxx, _snowmanxxxxx, 5, _snowmanxxxxxxx, _snowmanxxxxx + 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxxx, _snowmanxxxxx + 2, 3, _snowmanxxxxxxx, _snowmanxxxxx + 2, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxxx, _snowmanxxxxx, 0, _snowmanxxxxxxx, _snowmanxxxxx + 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxxxxxx, _snowmanxxxxx + 1, 0, _snowmanxxxxxxx, _snowmanxxxxx + 1, 7, PRISMARINE, PRISMARINE, false);
            }

            int var14 = 7;
            if (_snowmanxxxx.neighborPresences[Direction.EAST.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, var14, _snowmanxxxxx, 2, var14, _snowmanxxxxx + 2, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, var14, _snowmanxxxxx, 5, var14, _snowmanxxxxx + 2, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, var14, _snowmanxxxxx + 2, 3, var14, _snowmanxxxxx + 2, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, var14, _snowmanxxxxx, 0, var14, _snowmanxxxxx + 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, var14, _snowmanxxxxx + 1, 0, var14, _snowmanxxxxx + 1, 7, PRISMARINE, PRISMARINE, false);
            }

            _snowmanxxxx = _snowmanxxx;
         }

         return true;
      }
   }

   static class DoubleYRoomFactory implements OceanMonumentGenerator.PieceFactory {
      private DoubleYRoomFactory() {
      }

      @Override
      public boolean canGenerate(OceanMonumentGenerator.PieceSetting setting) {
         return setting.neighborPresences[Direction.UP.getId()] && !setting.neighbors[Direction.UP.getId()].used;
      }

      @Override
      public OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random) {
         setting.used = true;
         setting.neighbors[Direction.UP.getId()].used = true;
         return new OceanMonumentGenerator.DoubleYRoom(direction, setting);
      }
   }

   public static class DoubleYZRoom extends OceanMonumentGenerator.Piece {
      public DoubleYZRoom(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_Z_ROOM, 1, _snowman, _snowman, 1, 2, 2);
      }

      public DoubleYZRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_Z_ROOM, _snowman);
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
         OceanMonumentGenerator.PieceSetting _snowmanxxx = this.setting.neighbors[Direction.NORTH.getId()];
         OceanMonumentGenerator.PieceSetting _snowmanxxxx = this.setting;
         OceanMonumentGenerator.PieceSetting _snowmanxxxxx = _snowmanxxx.neighbors[Direction.UP.getId()];
         OceanMonumentGenerator.PieceSetting _snowmanxxxxxx = _snowmanxxxx.neighbors[Direction.UP.getId()];
         if (this.setting.roomIndex / 25 > 0) {
            this.method_14774(_snowman, boundingBox, 0, 8, _snowmanxxx.neighborPresences[Direction.DOWN.getId()]);
            this.method_14774(_snowman, boundingBox, 0, 0, _snowmanxxxx.neighborPresences[Direction.DOWN.getId()]);
         }

         if (_snowmanxxxxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 8, 1, 6, 8, 7, PRISMARINE);
         }

         if (_snowmanxxxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 8, 8, 6, 8, 14, PRISMARINE);
         }

         for (int _snowmanxxxxxxx = 1; _snowmanxxxxxxx <= 7; _snowmanxxxxxxx++) {
            BlockState _snowmanxxxxxxxx = PRISMARINE_BRICKS;
            if (_snowmanxxxxxxx == 2 || _snowmanxxxxxxx == 6) {
               _snowmanxxxxxxxx = PRISMARINE;
            }

            this.fillWithOutline(_snowman, boundingBox, 0, _snowmanxxxxxxx, 0, 0, _snowmanxxxxxxx, 15, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 7, _snowmanxxxxxxx, 0, 7, _snowmanxxxxxxx, 15, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 1, _snowmanxxxxxxx, 0, 6, _snowmanxxxxxxx, 0, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
            this.fillWithOutline(_snowman, boundingBox, 1, _snowmanxxxxxxx, 15, 6, _snowmanxxxxxxx, 15, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
         }

         for (int _snowmanxxxxxxx = 1; _snowmanxxxxxxx <= 7; _snowmanxxxxxxx++) {
            BlockState _snowmanxxxxxxxx = DARK_PRISMARINE;
            if (_snowmanxxxxxxx == 2 || _snowmanxxxxxxx == 6) {
               _snowmanxxxxxxxx = SEA_LANTERN;
            }

            this.fillWithOutline(_snowman, boundingBox, 3, _snowmanxxxxxxx, 7, 4, _snowmanxxxxxxx, 8, _snowmanxxxxxxxx, _snowmanxxxxxxxx, false);
         }

         if (_snowmanxxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanxxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 7, 1, 3, 7, 2, 4);
         }

         if (_snowmanxxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 1, 3, 0, 2, 4);
         }

         if (_snowmanxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 15, 4, 2, 15);
         }

         if (_snowmanxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 1, 11, 0, 2, 12);
         }

         if (_snowmanxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 7, 1, 11, 7, 2, 12);
         }

         if (_snowmanxxxxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 5, 0, 4, 6, 0);
         }

         if (_snowmanxxxxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 7, 5, 3, 7, 6, 4);
            this.fillWithOutline(_snowman, boundingBox, 5, 4, 2, 6, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 6, 1, 2, 6, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 6, 1, 5, 6, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         }

         if (_snowmanxxxxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 5, 3, 0, 6, 4);
            this.fillWithOutline(_snowman, boundingBox, 1, 4, 2, 2, 4, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 1, 2, 1, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 1, 5, 1, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         }

         if (_snowmanxxxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 5, 15, 4, 6, 15);
         }

         if (_snowmanxxxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 5, 11, 0, 6, 12);
            this.fillWithOutline(_snowman, boundingBox, 1, 4, 10, 2, 4, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 1, 10, 1, 3, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 1, 13, 1, 3, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         }

         if (_snowmanxxxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 7, 5, 11, 7, 6, 12);
            this.fillWithOutline(_snowman, boundingBox, 5, 4, 10, 6, 4, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 6, 1, 10, 6, 3, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 6, 1, 13, 6, 3, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         }

         return true;
      }
   }

   static class DoubleYZRoomFactory implements OceanMonumentGenerator.PieceFactory {
      private DoubleYZRoomFactory() {
      }

      @Override
      public boolean canGenerate(OceanMonumentGenerator.PieceSetting setting) {
         if (setting.neighborPresences[Direction.NORTH.getId()]
            && !setting.neighbors[Direction.NORTH.getId()].used
            && setting.neighborPresences[Direction.UP.getId()]
            && !setting.neighbors[Direction.UP.getId()].used) {
            OceanMonumentGenerator.PieceSetting _snowman = setting.neighbors[Direction.NORTH.getId()];
            return _snowman.neighborPresences[Direction.UP.getId()] && !_snowman.neighbors[Direction.UP.getId()].used;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random) {
         setting.used = true;
         setting.neighbors[Direction.NORTH.getId()].used = true;
         setting.neighbors[Direction.UP.getId()].used = true;
         setting.neighbors[Direction.NORTH.getId()].neighbors[Direction.UP.getId()].used = true;
         return new OceanMonumentGenerator.DoubleYZRoom(direction, setting);
      }
   }

   public static class DoubleZRoom extends OceanMonumentGenerator.Piece {
      public DoubleZRoom(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, 1, _snowman, _snowman, 1, 1, 2);
      }

      public DoubleZRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, _snowman);
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
         OceanMonumentGenerator.PieceSetting _snowmanxxx = this.setting.neighbors[Direction.NORTH.getId()];
         OceanMonumentGenerator.PieceSetting _snowmanxxxx = this.setting;
         if (this.setting.roomIndex / 25 > 0) {
            this.method_14774(_snowman, boundingBox, 0, 8, _snowmanxxx.neighborPresences[Direction.DOWN.getId()]);
            this.method_14774(_snowman, boundingBox, 0, 0, _snowmanxxxx.neighborPresences[Direction.DOWN.getId()]);
         }

         if (_snowmanxxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 4, 1, 6, 4, 7, PRISMARINE);
         }

         if (_snowmanxxx.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 4, 8, 6, 4, 14, PRISMARINE);
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 0, 3, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 3, 0, 7, 3, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 0, 7, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 15, 6, 3, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 2, 15, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 2, 0, 7, 2, 15, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 0, 7, 2, 0, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 15, 6, 2, 15, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 0, 0, 1, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 1, 0, 7, 1, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 0, 7, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 15, 6, 1, 15, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 1, 1, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 1, 1, 6, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 1, 1, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 3, 1, 6, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 13, 1, 1, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 1, 13, 6, 1, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 13, 1, 3, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 3, 13, 6, 3, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 1, 6, 2, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 6, 5, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 1, 9, 2, 3, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 9, 5, 3, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 2, 6, 4, 2, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 2, 9, 4, 2, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 2, 2, 7, 2, 2, 8, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 2, 7, 5, 2, 8, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.addBlock(_snowman, SEA_LANTERN, 2, 2, 5, boundingBox);
         this.addBlock(_snowman, SEA_LANTERN, 5, 2, 5, boundingBox);
         this.addBlock(_snowman, SEA_LANTERN, 2, 2, 10, boundingBox);
         this.addBlock(_snowman, SEA_LANTERN, 5, 2, 10, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 2, 3, 5, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 5, 3, 5, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 2, 3, 10, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 5, 3, 10, boundingBox);
         if (_snowmanxxxx.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanxxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 7, 1, 3, 7, 2, 4);
         }

         if (_snowmanxxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 1, 3, 0, 2, 4);
         }

         if (_snowmanxxx.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 15, 4, 2, 15);
         }

         if (_snowmanxxx.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 1, 11, 0, 2, 12);
         }

         if (_snowmanxxx.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 7, 1, 11, 7, 2, 12);
         }

         return true;
      }
   }

   static class DoubleZRoomFactory implements OceanMonumentGenerator.PieceFactory {
      private DoubleZRoomFactory() {
      }

      @Override
      public boolean canGenerate(OceanMonumentGenerator.PieceSetting setting) {
         return setting.neighborPresences[Direction.NORTH.getId()] && !setting.neighbors[Direction.NORTH.getId()].used;
      }

      @Override
      public OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random) {
         OceanMonumentGenerator.PieceSetting _snowman = setting;
         if (!setting.neighborPresences[Direction.NORTH.getId()] || setting.neighbors[Direction.NORTH.getId()].used) {
            _snowman = setting.neighbors[Direction.SOUTH.getId()];
         }

         _snowman.used = true;
         _snowman.neighbors[Direction.NORTH.getId()].used = true;
         return new OceanMonumentGenerator.DoubleZRoom(direction, _snowman);
      }
   }

   public static class Entry extends OceanMonumentGenerator.Piece {
      public Entry(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, 1, _snowman, _snowman, 1, 1, 1);
      }

      public Entry(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, _snowman);
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
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 2, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 3, 0, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 1, 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 2, 0, 7, 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 1, 0, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 7, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 0, 2, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 5, 1, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         if (this.setting.neighborPresences[Direction.NORTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 7, 4, 2, 7);
         }

         if (this.setting.neighborPresences[Direction.WEST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 0, 1, 3, 1, 2, 4);
         }

         if (this.setting.neighborPresences[Direction.EAST.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 6, 1, 3, 7, 2, 4);
         }

         return true;
      }
   }

   public static class Penthouse extends OceanMonumentGenerator.Piece {
      public Penthouse(Direction _snowman, BlockBox _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, _snowman, _snowman);
      }

      public Penthouse(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, _snowman);
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
         this.fillWithOutline(_snowman, boundingBox, 2, -1, 2, 11, -1, 11, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, -1, 0, 1, -1, 11, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 12, -1, 0, 13, -1, 11, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 2, -1, 0, 11, -1, 1, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 2, -1, 12, 11, -1, 13, PRISMARINE, PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 0, 0, 0, 0, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 13, 0, 0, 13, 0, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 0, 0, 12, 0, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 0, 13, 12, 0, 13, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);

         for (int _snowmanxxx = 2; _snowmanxxx <= 11; _snowmanxxx += 3) {
            this.addBlock(_snowman, SEA_LANTERN, 0, 0, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 13, 0, _snowmanxxx, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, _snowmanxxx, 0, 0, boundingBox);
         }

         this.fillWithOutline(_snowman, boundingBox, 2, 0, 3, 4, 0, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 9, 0, 3, 11, 0, 9, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 4, 0, 9, 9, 0, 11, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 5, 0, 8, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 8, 0, 8, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 10, 0, 10, boundingBox);
         this.addBlock(_snowman, PRISMARINE_BRICKS, 3, 0, 10, boundingBox);
         this.fillWithOutline(_snowman, boundingBox, 3, 0, 3, 3, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 10, 0, 3, 10, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 6, 0, 10, 7, 0, 10, DARK_PRISMARINE, DARK_PRISMARINE, false);
         int _snowmanxxx = 3;

         for (int _snowmanxxxx = 0; _snowmanxxxx < 2; _snowmanxxxx++) {
            for (int _snowmanxxxxx = 2; _snowmanxxxxx <= 8; _snowmanxxxxx += 3) {
               this.fillWithOutline(_snowman, boundingBox, _snowmanxxx, 0, _snowmanxxxxx, _snowmanxxx, 2, _snowmanxxxxx, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            _snowmanxxx = 10;
         }

         this.fillWithOutline(_snowman, boundingBox, 5, 0, 10, 5, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 8, 0, 10, 8, 2, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 6, -1, 7, 7, -1, 8, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.setAirAndWater(_snowman, boundingBox, 6, -1, 3, 7, -1, 4);
         this.method_14772(_snowman, boundingBox, 6, 1, 6);
         return true;
      }
   }

   public abstract static class Piece extends StructurePiece {
      protected static final BlockState PRISMARINE = Blocks.PRISMARINE.getDefaultState();
      protected static final BlockState PRISMARINE_BRICKS = Blocks.PRISMARINE_BRICKS.getDefaultState();
      protected static final BlockState DARK_PRISMARINE = Blocks.DARK_PRISMARINE.getDefaultState();
      protected static final BlockState field_14470 = PRISMARINE_BRICKS;
      protected static final BlockState SEA_LANTERN = Blocks.SEA_LANTERN.getDefaultState();
      protected static final BlockState WATER = Blocks.WATER.getDefaultState();
      protected static final Set<Block> ICE_BLOCKS = ImmutableSet.builder()
         .add(Blocks.ICE)
         .add(Blocks.PACKED_ICE)
         .add(Blocks.BLUE_ICE)
         .add(WATER.getBlock())
         .build();
      protected static final int TWO_ZERO_ZERO_INDEX = getIndex(2, 0, 0);
      protected static final int TWO_TWO_ZERO_INDEX = getIndex(2, 2, 0);
      protected static final int ZERO_ONE_ZERO_INDEX = getIndex(0, 1, 0);
      protected static final int FOUR_ONE_ZERO_INDEX = getIndex(4, 1, 0);
      protected OceanMonumentGenerator.PieceSetting setting;

      protected static final int getIndex(int x, int y, int z) {
         return y * 25 + z * 5 + x;
      }

      public Piece(StructurePieceType _snowman, int _snowman) {
         super(_snowman, _snowman);
      }

      public Piece(StructurePieceType _snowman, Direction _snowman, BlockBox _snowman) {
         super(_snowman, 1);
         this.setOrientation(_snowman);
         this.boundingBox = _snowman;
      }

      protected Piece(StructurePieceType _snowman, int _snowman, Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman, int _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman);
         this.setOrientation(_snowman);
         this.setting = _snowman;
         int _snowmanxxxxxxx = _snowman.roomIndex;
         int _snowmanxxxxxxxx = _snowmanxxxxxxx % 5;
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx / 5 % 5;
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxx / 25;
         if (_snowman != Direction.NORTH && _snowman != Direction.SOUTH) {
            this.boundingBox = new BlockBox(0, 0, 0, _snowman * 8 - 1, _snowman * 4 - 1, _snowman * 8 - 1);
         } else {
            this.boundingBox = new BlockBox(0, 0, 0, _snowman * 8 - 1, _snowman * 4 - 1, _snowman * 8 - 1);
         }

         switch (_snowman) {
            case NORTH:
               this.boundingBox.move(_snowmanxxxxxxxx * 8, _snowmanxxxxxxxxxx * 4, -(_snowmanxxxxxxxxx + _snowman) * 8 + 1);
               break;
            case SOUTH:
               this.boundingBox.move(_snowmanxxxxxxxx * 8, _snowmanxxxxxxxxxx * 4, _snowmanxxxxxxxxx * 8);
               break;
            case WEST:
               this.boundingBox.move(-(_snowmanxxxxxxxxx + _snowman) * 8 + 1, _snowmanxxxxxxxxxx * 4, _snowmanxxxxxxxx * 8);
               break;
            default:
               this.boundingBox.move(_snowmanxxxxxxxxx * 8, _snowmanxxxxxxxxxx * 4, _snowmanxxxxxxxx * 8);
         }
      }

      public Piece(StructurePieceType _snowman, CompoundTag _snowman) {
         super(_snowman, _snowman);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
      }

      protected void setAirAndWater(StructureWorldAccess _snowman, BlockBox _snowman, int x, int y, int z, int width, int height, int depth) {
         for (int _snowmanxx = y; _snowmanxx <= height; _snowmanxx++) {
            for (int _snowmanxxx = x; _snowmanxxx <= width; _snowmanxxx++) {
               for (int _snowmanxxxx = z; _snowmanxxxx <= depth; _snowmanxxxx++) {
                  BlockState _snowmanxxxxx = this.getBlockAt(_snowman, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman);
                  if (!ICE_BLOCKS.contains(_snowmanxxxxx.getBlock())) {
                     if (this.applyYTransform(_snowmanxx) >= _snowman.getSeaLevel() && _snowmanxxxxx != WATER) {
                        this.addBlock(_snowman, Blocks.AIR.getDefaultState(), _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman);
                     } else {
                        this.addBlock(_snowman, WATER, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman);
                     }
                  }
               }
            }
         }
      }

      protected void method_14774(StructureWorldAccess _snowman, BlockBox _snowman, int _snowman, int _snowman, boolean _snowman) {
         if (_snowman) {
            this.fillWithOutline(_snowman, _snowman, _snowman + 0, 0, _snowman + 0, _snowman + 2, 0, _snowman + 8 - 1, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 5, 0, _snowman + 0, _snowman + 8 - 1, 0, _snowman + 8 - 1, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 3, 0, _snowman + 0, _snowman + 4, 0, _snowman + 2, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 3, 0, _snowman + 5, _snowman + 4, 0, _snowman + 8 - 1, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 3, 0, _snowman + 2, _snowman + 4, 0, _snowman + 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 3, 0, _snowman + 5, _snowman + 4, 0, _snowman + 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 2, 0, _snowman + 3, _snowman + 2, 0, _snowman + 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, _snowman, _snowman + 5, 0, _snowman + 3, _snowman + 5, 0, _snowman + 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         } else {
            this.fillWithOutline(_snowman, _snowman, _snowman + 0, 0, _snowman + 0, _snowman + 8 - 1, 0, _snowman + 8 - 1, PRISMARINE, PRISMARINE, false);
         }
      }

      protected void method_14771(StructureWorldAccess _snowman, BlockBox _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, BlockState _snowman) {
         for (int _snowmanxxxxxxxxx = _snowman; _snowmanxxxxxxxxx <= _snowman; _snowmanxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxx = _snowman; _snowmanxxxxxxxxxx <= _snowman; _snowmanxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxx = _snowman; _snowmanxxxxxxxxxxx <= _snowman; _snowmanxxxxxxxxxxx++) {
                  if (this.getBlockAt(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman) == WATER) {
                     this.addBlock(_snowman, _snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman);
                  }
               }
            }
         }
      }

      protected boolean method_14775(BlockBox _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         int _snowmanxxxxx = this.applyXTransform(_snowman, _snowman);
         int _snowmanxxxxxx = this.applyZTransform(_snowman, _snowman);
         int _snowmanxxxxxxx = this.applyXTransform(_snowman, _snowman);
         int _snowmanxxxxxxxx = this.applyZTransform(_snowman, _snowman);
         return _snowman.intersectsXZ(Math.min(_snowmanxxxxx, _snowmanxxxxxxx), Math.min(_snowmanxxxxxx, _snowmanxxxxxxxx), Math.max(_snowmanxxxxx, _snowmanxxxxxxx), Math.max(_snowmanxxxxxx, _snowmanxxxxxxxx));
      }

      protected boolean method_14772(StructureWorldAccess _snowman, BlockBox _snowman, int _snowman, int _snowman, int _snowman) {
         int _snowmanxxxxx = this.applyXTransform(_snowman, _snowman);
         int _snowmanxxxxxx = this.applyYTransform(_snowman);
         int _snowmanxxxxxxx = this.applyZTransform(_snowman, _snowman);
         if (_snowman.contains(new BlockPos(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx))) {
            ElderGuardianEntity _snowmanxxxxxxxx = EntityType.ELDER_GUARDIAN.create(_snowman.toServerWorld());
            _snowmanxxxxxxxx.heal(_snowmanxxxxxxxx.getMaxHealth());
            _snowmanxxxxxxxx.refreshPositionAndAngles((double)_snowmanxxxxx + 0.5, (double)_snowmanxxxxxx, (double)_snowmanxxxxxxx + 0.5, 0.0F, 0.0F);
            _snowmanxxxxxxxx.initialize(_snowman, _snowman.getLocalDifficulty(_snowmanxxxxxxxx.getBlockPos()), SpawnReason.STRUCTURE, null, null);
            _snowman.spawnEntityAndPassengers(_snowmanxxxxxxxx);
            return true;
         } else {
            return false;
         }
      }
   }

   interface PieceFactory {
      boolean canGenerate(OceanMonumentGenerator.PieceSetting setting);

      OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random);
   }

   static class PieceSetting {
      private final int roomIndex;
      private final OceanMonumentGenerator.PieceSetting[] neighbors = new OceanMonumentGenerator.PieceSetting[6];
      private final boolean[] neighborPresences = new boolean[6];
      private boolean used;
      private boolean field_14484;
      private int field_14483;

      public PieceSetting(int index) {
         this.roomIndex = index;
      }

      public void setNeighbor(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         this.neighbors[_snowman.getId()] = _snowman;
         _snowman.neighbors[_snowman.getOpposite().getId()] = this;
      }

      public void checkNeighborStates() {
         for (int _snowman = 0; _snowman < 6; _snowman++) {
            this.neighborPresences[_snowman] = this.neighbors[_snowman] != null;
         }
      }

      public boolean method_14783(int _snowman) {
         if (this.field_14484) {
            return true;
         } else {
            this.field_14483 = _snowman;

            for (int _snowmanx = 0; _snowmanx < 6; _snowmanx++) {
               if (this.neighbors[_snowmanx] != null && this.neighborPresences[_snowmanx] && this.neighbors[_snowmanx].field_14483 != _snowman && this.neighbors[_snowmanx].method_14783(_snowman)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean isAboveLevelThree() {
         return this.roomIndex >= 75;
      }

      public int countNeighbors() {
         int _snowman = 0;

         for (int _snowmanx = 0; _snowmanx < 6; _snowmanx++) {
            if (this.neighborPresences[_snowmanx]) {
               _snowman++;
            }
         }

         return _snowman;
      }
   }

   public static class SimpleRoom extends OceanMonumentGenerator.Piece {
      private int field_14480;

      public SimpleRoom(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman, Random _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, 1, _snowman, _snowman, 1, 1, 1);
         this.field_14480 = _snowman.nextInt(3);
      }

      public SimpleRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, _snowman);
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
         if (this.setting.roomIndex / 25 > 0) {
            this.method_14774(_snowman, boundingBox, 0, 0, this.setting.neighborPresences[Direction.DOWN.getId()]);
         }

         if (this.setting.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 4, 1, 6, 4, 6, PRISMARINE);
         }

         boolean _snowmanxxx = this.field_14480 != 0
            && random.nextBoolean()
            && !this.setting.neighborPresences[Direction.DOWN.getId()]
            && !this.setting.neighborPresences[Direction.UP.getId()]
            && this.setting.countNeighbors() > 1;
         if (this.field_14480 == 0) {
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 0, 2, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 2, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 2, 2, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 2, 0, 2, 2, 0, PRISMARINE, PRISMARINE, false);
            this.addBlock(_snowman, SEA_LANTERN, 1, 2, 1, boundingBox);
            this.fillWithOutline(_snowman, boundingBox, 5, 1, 0, 7, 1, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 5, 3, 0, 7, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 2, 0, 7, 2, 2, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 5, 2, 0, 6, 2, 0, PRISMARINE, PRISMARINE, false);
            this.addBlock(_snowman, SEA_LANTERN, 6, 2, 1, boundingBox);
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 5, 2, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 3, 5, 2, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 2, 5, 0, 2, 7, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 2, 7, 2, 2, 7, PRISMARINE, PRISMARINE, false);
            this.addBlock(_snowman, SEA_LANTERN, 1, 2, 6, boundingBox);
            this.fillWithOutline(_snowman, boundingBox, 5, 1, 5, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 5, 3, 5, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 2, 5, 7, 2, 7, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 5, 2, 7, 6, 2, 7, PRISMARINE, PRISMARINE, false);
            this.addBlock(_snowman, SEA_LANTERN, 6, 2, 6, boundingBox);
            if (this.setting.neighborPresences[Direction.SOUTH.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 3, 3, 0, 4, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, 3, 3, 0, 4, 3, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 3, 2, 0, 4, 2, 0, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 3, 1, 0, 4, 1, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            if (this.setting.neighborPresences[Direction.NORTH.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 3, 3, 7, 4, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, 3, 3, 6, 4, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 3, 2, 7, 4, 2, 7, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 3, 1, 6, 4, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            if (this.setting.neighborPresences[Direction.WEST.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 0, 3, 3, 0, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, 0, 3, 3, 1, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 0, 2, 3, 0, 2, 4, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 0, 1, 3, 1, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            if (this.setting.neighborPresences[Direction.EAST.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 7, 3, 3, 7, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            } else {
               this.fillWithOutline(_snowman, boundingBox, 6, 3, 3, 7, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 7, 2, 3, 7, 2, 4, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 6, 1, 3, 7, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
         } else if (this.field_14480 == 1) {
            this.fillWithOutline(_snowman, boundingBox, 2, 1, 2, 2, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 2, 1, 5, 2, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 5, 1, 5, 5, 3, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 5, 1, 2, 5, 3, 2, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(_snowman, SEA_LANTERN, 2, 2, 2, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 2, 2, 5, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 5, 2, 5, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 5, 2, 2, boundingBox);
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 0, 1, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 1, 0, 3, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 7, 1, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 6, 0, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 6, 1, 7, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 1, 6, 7, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 6, 1, 0, 7, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 1, 1, 7, 3, 1, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.addBlock(_snowman, PRISMARINE, 1, 2, 0, boundingBox);
            this.addBlock(_snowman, PRISMARINE, 0, 2, 1, boundingBox);
            this.addBlock(_snowman, PRISMARINE, 1, 2, 7, boundingBox);
            this.addBlock(_snowman, PRISMARINE, 0, 2, 6, boundingBox);
            this.addBlock(_snowman, PRISMARINE, 6, 2, 7, boundingBox);
            this.addBlock(_snowman, PRISMARINE, 7, 2, 6, boundingBox);
            this.addBlock(_snowman, PRISMARINE, 6, 2, 0, boundingBox);
            this.addBlock(_snowman, PRISMARINE, 7, 2, 1, boundingBox);
            if (!this.setting.neighborPresences[Direction.SOUTH.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 1, 3, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 1, 2, 0, 6, 2, 0, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 1, 1, 0, 6, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            if (!this.setting.neighborPresences[Direction.NORTH.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 1, 3, 7, 6, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 1, 2, 7, 6, 2, 7, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 1, 1, 7, 6, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            if (!this.setting.neighborPresences[Direction.WEST.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 0, 3, 1, 0, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 0, 2, 1, 0, 2, 6, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 0, 1, 1, 0, 1, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            if (!this.setting.neighborPresences[Direction.EAST.getId()]) {
               this.fillWithOutline(_snowman, boundingBox, 7, 3, 1, 7, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, 7, 2, 1, 7, 2, 6, PRISMARINE, PRISMARINE, false);
               this.fillWithOutline(_snowman, boundingBox, 7, 1, 1, 7, 1, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }
         } else if (this.field_14480 == 2) {
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 1, 0, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 1, 0, 6, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 1, 7, 6, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 0, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 3, 0, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 3, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 1, 3, 7, 6, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            if (this.setting.neighborPresences[Direction.SOUTH.getId()]) {
               this.setAirAndWater(_snowman, boundingBox, 3, 1, 0, 4, 2, 0);
            }

            if (this.setting.neighborPresences[Direction.NORTH.getId()]) {
               this.setAirAndWater(_snowman, boundingBox, 3, 1, 7, 4, 2, 7);
            }

            if (this.setting.neighborPresences[Direction.WEST.getId()]) {
               this.setAirAndWater(_snowman, boundingBox, 0, 1, 3, 0, 2, 4);
            }

            if (this.setting.neighborPresences[Direction.EAST.getId()]) {
               this.setAirAndWater(_snowman, boundingBox, 7, 1, 3, 7, 2, 4);
            }
         }

         if (_snowmanxxx) {
            this.fillWithOutline(_snowman, boundingBox, 3, 1, 3, 4, 1, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 3, 2, 3, 4, 2, 4, PRISMARINE, PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 3, 3, 3, 4, 3, 4, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         }

         return true;
      }
   }

   static class SimpleRoomFactory implements OceanMonumentGenerator.PieceFactory {
      private SimpleRoomFactory() {
      }

      @Override
      public boolean canGenerate(OceanMonumentGenerator.PieceSetting setting) {
         return true;
      }

      @Override
      public OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random) {
         setting.used = true;
         return new OceanMonumentGenerator.SimpleRoom(direction, setting, random);
      }
   }

   public static class SimpleRoomTop extends OceanMonumentGenerator.Piece {
      public SimpleRoomTop(Direction _snowman, OceanMonumentGenerator.PieceSetting _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, 1, _snowman, _snowman, 1, 1, 1);
      }

      public SimpleRoomTop(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, _snowman);
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
         if (this.setting.roomIndex / 25 > 0) {
            this.method_14774(_snowman, boundingBox, 0, 0, this.setting.neighborPresences[Direction.DOWN.getId()]);
         }

         if (this.setting.neighbors[Direction.UP.getId()] == null) {
            this.method_14771(_snowman, boundingBox, 1, 4, 1, 6, 4, 6, PRISMARINE);
         }

         for (int _snowmanxxx = 1; _snowmanxxx <= 6; _snowmanxxx++) {
            for (int _snowmanxxxx = 1; _snowmanxxxx <= 6; _snowmanxxxx++) {
               if (random.nextInt(3) != 0) {
                  int _snowmanxxxxx = 2 + (random.nextInt(4) == 0 ? 0 : 1);
                  BlockState _snowmanxxxxxx = Blocks.WET_SPONGE.getDefaultState();
                  this.fillWithOutline(_snowman, boundingBox, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowmanxxx, 3, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxx, false);
               }
            }
         }

         this.fillWithOutline(_snowman, boundingBox, 0, 1, 0, 0, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 1, 0, 7, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 0, 6, 1, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 1, 7, 6, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 3, 0, 0, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 3, 0, 7, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 0, 6, 3, 0, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 1, 3, 7, 6, 3, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
         this.fillWithOutline(_snowman, boundingBox, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithOutline(_snowman, boundingBox, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         if (this.setting.neighborPresences[Direction.SOUTH.getId()]) {
            this.setAirAndWater(_snowman, boundingBox, 3, 1, 0, 4, 2, 0);
         }

         return true;
      }
   }

   static class SimpleRoomTopFactory implements OceanMonumentGenerator.PieceFactory {
      private SimpleRoomTopFactory() {
      }

      @Override
      public boolean canGenerate(OceanMonumentGenerator.PieceSetting setting) {
         return !setting.neighborPresences[Direction.WEST.getId()]
            && !setting.neighborPresences[Direction.EAST.getId()]
            && !setting.neighborPresences[Direction.NORTH.getId()]
            && !setting.neighborPresences[Direction.SOUTH.getId()]
            && !setting.neighborPresences[Direction.UP.getId()];
      }

      @Override
      public OceanMonumentGenerator.Piece generate(Direction direction, OceanMonumentGenerator.PieceSetting setting, Random random) {
         setting.used = true;
         return new OceanMonumentGenerator.SimpleRoomTop(direction, setting);
      }
   }

   public static class WingRoom extends OceanMonumentGenerator.Piece {
      private int field_14481;

      public WingRoom(Direction _snowman, BlockBox _snowman, int _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, _snowman, _snowman);
         this.field_14481 = _snowman & 1;
      }

      public WingRoom(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, _snowman);
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
         if (this.field_14481 == 0) {
            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               this.fillWithOutline(_snowman, boundingBox, 10 - _snowmanxxx, 3 - _snowmanxxx, 20 - _snowmanxxx, 12 + _snowmanxxx, 3 - _snowmanxxx, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            }

            this.fillWithOutline(_snowman, boundingBox, 7, 0, 6, 15, 0, 16, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 6, 0, 6, 6, 3, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 16, 0, 6, 16, 3, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 1, 7, 7, 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 15, 1, 7, 15, 1, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 7, 1, 6, 9, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 13, 1, 6, 15, 3, 6, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 8, 1, 7, 9, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 13, 1, 7, 14, 1, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 9, 0, 5, 13, 0, 5, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 10, 0, 7, 12, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 8, 0, 10, 8, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 14, 0, 10, 14, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);

            for (int _snowmanxxx = 18; _snowmanxxx >= 7; _snowmanxxx -= 3) {
               this.addBlock(_snowman, SEA_LANTERN, 6, 3, _snowmanxxx, boundingBox);
               this.addBlock(_snowman, SEA_LANTERN, 16, 3, _snowmanxxx, boundingBox);
            }

            this.addBlock(_snowman, SEA_LANTERN, 10, 0, 10, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 12, 0, 10, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 10, 0, 12, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 12, 0, 12, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 8, 3, 6, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 14, 3, 6, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 4, 2, 4, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 4, 1, 4, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 4, 0, 4, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 18, 2, 4, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 18, 1, 4, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 18, 0, 4, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 4, 2, 18, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 4, 1, 18, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 4, 0, 18, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 18, 2, 18, boundingBox);
            this.addBlock(_snowman, SEA_LANTERN, 18, 1, 18, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 18, 0, 18, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 9, 7, 20, boundingBox);
            this.addBlock(_snowman, PRISMARINE_BRICKS, 13, 7, 20, boundingBox);
            this.fillWithOutline(_snowman, boundingBox, 6, 0, 21, 7, 4, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 15, 0, 21, 16, 4, 21, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.method_14772(_snowman, boundingBox, 11, 2, 16);
         } else if (this.field_14481 == 1) {
            this.fillWithOutline(_snowman, boundingBox, 9, 3, 18, 13, 3, 20, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 9, 0, 18, 9, 2, 18, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            this.fillWithOutline(_snowman, boundingBox, 13, 0, 18, 13, 2, 18, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            int _snowmanxxx = 9;
            int _snowmanxxxx = 20;
            int _snowmanxxxxx = 5;

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 2; _snowmanxxxxxx++) {
               this.addBlock(_snowman, PRISMARINE_BRICKS, _snowmanxxx, 6, 20, boundingBox);
               this.addBlock(_snowman, SEA_LANTERN, _snowmanxxx, 5, 20, boundingBox);
               this.addBlock(_snowman, PRISMARINE_BRICKS, _snowmanxxx, 4, 20, boundingBox);
               _snowmanxxx = 13;
            }

            this.fillWithOutline(_snowman, boundingBox, 7, 3, 7, 15, 3, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
            int var14 = 10;

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 2; _snowmanxxxxxx++) {
               this.fillWithOutline(_snowman, boundingBox, var14, 0, 10, var14, 6, 10, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, var14, 0, 12, var14, 6, 12, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.addBlock(_snowman, SEA_LANTERN, var14, 0, 10, boundingBox);
               this.addBlock(_snowman, SEA_LANTERN, var14, 0, 12, boundingBox);
               this.addBlock(_snowman, SEA_LANTERN, var14, 4, 10, boundingBox);
               this.addBlock(_snowman, SEA_LANTERN, var14, 4, 12, boundingBox);
               var14 = 12;
            }

            var14 = 8;

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 2; _snowmanxxxxxx++) {
               this.fillWithOutline(_snowman, boundingBox, var14, 0, 7, var14, 2, 7, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               this.fillWithOutline(_snowman, boundingBox, var14, 0, 14, var14, 2, 14, PRISMARINE_BRICKS, PRISMARINE_BRICKS, false);
               var14 = 14;
            }

            this.fillWithOutline(_snowman, boundingBox, 8, 3, 8, 8, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithOutline(_snowman, boundingBox, 14, 3, 8, 14, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.method_14772(_snowman, boundingBox, 11, 5, 13);
         }

         return true;
      }
   }
}
