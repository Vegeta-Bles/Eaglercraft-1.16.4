package net.minecraft.structure;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;

public class WoodlandMansionGenerator {
   public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<WoodlandMansionGenerator.Piece> pieces, Random random) {
      WoodlandMansionGenerator.MansionParameters _snowman = new WoodlandMansionGenerator.MansionParameters(random);
      WoodlandMansionGenerator.LayoutGenerator _snowmanx = new WoodlandMansionGenerator.LayoutGenerator(manager, random);
      _snowmanx.generate(pos, rotation, pieces, _snowman);
   }

   static class FirstFloorRoomPool extends WoodlandMansionGenerator.RoomPool {
      private FirstFloorRoomPool() {
      }

      @Override
      public String getSmallRoom(Random random) {
         return "1x1_a" + (random.nextInt(5) + 1);
      }

      @Override
      public String getSmallSecretRoom(Random random) {
         return "1x1_as" + (random.nextInt(4) + 1);
      }

      @Override
      public String getMediumFunctionalRoom(Random random, boolean staircase) {
         return "1x2_a" + (random.nextInt(9) + 1);
      }

      @Override
      public String getMediumGenericRoom(Random random, boolean staircase) {
         return "1x2_b" + (random.nextInt(5) + 1);
      }

      @Override
      public String getMediumSecretRoom(Random random) {
         return "1x2_s" + (random.nextInt(2) + 1);
      }

      @Override
      public String getBigRoom(Random random) {
         return "2x2_a" + (random.nextInt(4) + 1);
      }

      @Override
      public String getBigSecretRoom(Random random) {
         return "2x2_s1";
      }
   }

   static class FlagMatrix {
      private final int[][] array;
      private final int n;
      private final int m;
      private final int fallback;

      public FlagMatrix(int n, int m, int fallback) {
         this.n = n;
         this.m = m;
         this.fallback = fallback;
         this.array = new int[n][m];
      }

      public void set(int i, int j, int value) {
         if (i >= 0 && i < this.n && j >= 0 && j < this.m) {
            this.array[i][j] = value;
         }
      }

      public void fill(int i0, int j0, int i1, int j1, int value) {
         for (int _snowman = j0; _snowman <= j1; _snowman++) {
            for (int _snowmanx = i0; _snowmanx <= i1; _snowmanx++) {
               this.set(_snowmanx, _snowman, value);
            }
         }
      }

      public int get(int i, int j) {
         return i >= 0 && i < this.n && j >= 0 && j < this.m ? this.array[i][j] : this.fallback;
      }

      public void update(int i, int j, int expected, int newValue) {
         if (this.get(i, j) == expected) {
            this.set(i, j, newValue);
         }
      }

      public boolean anyMatchAround(int i, int j, int value) {
         return this.get(i - 1, j) == value || this.get(i + 1, j) == value || this.get(i, j + 1) == value || this.get(i, j - 1) == value;
      }
   }

   static class GenerationPiece {
      public BlockRotation rotation;
      public BlockPos position;
      public String template;

      private GenerationPiece() {
      }
   }

   static class LayoutGenerator {
      private final StructureManager manager;
      private final Random random;
      private int field_15446;
      private int field_15445;

      public LayoutGenerator(StructureManager manager, Random random) {
         this.manager = manager;
         this.random = random;
      }

      public void generate(BlockPos pos, BlockRotation rotation, List<WoodlandMansionGenerator.Piece> pieces, WoodlandMansionGenerator.MansionParameters _snowman) {
         WoodlandMansionGenerator.GenerationPiece _snowmanx = new WoodlandMansionGenerator.GenerationPiece();
         _snowmanx.position = pos;
         _snowmanx.rotation = rotation;
         _snowmanx.template = "wall_flat";
         WoodlandMansionGenerator.GenerationPiece _snowmanxx = new WoodlandMansionGenerator.GenerationPiece();
         this.addEntrance(pieces, _snowmanx);
         _snowmanxx.position = _snowmanx.position.up(8);
         _snowmanxx.rotation = _snowmanx.rotation;
         _snowmanxx.template = "wall_window";
         if (!pieces.isEmpty()) {
         }

         WoodlandMansionGenerator.FlagMatrix _snowmanxxx = _snowman.field_15440;
         WoodlandMansionGenerator.FlagMatrix _snowmanxxxx = _snowman.field_15439;
         this.field_15446 = _snowman.field_15442 + 1;
         this.field_15445 = _snowman.field_15441 + 1;
         int _snowmanxxxxx = _snowman.field_15442 + 1;
         int _snowmanxxxxxx = _snowman.field_15441;
         this.addRoof(pieces, _snowmanx, _snowmanxxx, Direction.SOUTH, this.field_15446, this.field_15445, _snowmanxxxxx, _snowmanxxxxxx);
         this.addRoof(pieces, _snowmanxx, _snowmanxxx, Direction.SOUTH, this.field_15446, this.field_15445, _snowmanxxxxx, _snowmanxxxxxx);
         WoodlandMansionGenerator.GenerationPiece _snowmanxxxxxxx = new WoodlandMansionGenerator.GenerationPiece();
         _snowmanxxxxxxx.position = _snowmanx.position.up(19);
         _snowmanxxxxxxx.rotation = _snowmanx.rotation;
         _snowmanxxxxxxx.template = "wall_window";
         boolean _snowmanxxxxxxxx = false;

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxx.m && !_snowmanxxxxxxxx; _snowmanxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxx = _snowmanxxxx.n - 1; _snowmanxxxxxxxxxx >= 0 && !_snowmanxxxxxxxx; _snowmanxxxxxxxxxx--) {
               if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowmanxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)) {
                  _snowmanxxxxxxx.position = _snowmanxxxxxxx.position.offset(rotation.rotate(Direction.SOUTH), 8 + (_snowmanxxxxxxxxx - this.field_15445) * 8);
                  _snowmanxxxxxxx.position = _snowmanxxxxxxx.position.offset(rotation.rotate(Direction.EAST), (_snowmanxxxxxxxxxx - this.field_15446) * 8);
                  this.method_15052(pieces, _snowmanxxxxxxx);
                  this.addRoof(pieces, _snowmanxxxxxxx, _snowmanxxxx, Direction.SOUTH, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx);
                  _snowmanxxxxxxxx = true;
               }
            }
         }

         this.method_15055(pieces, pos.up(16), rotation, _snowmanxxx, _snowmanxxxx);
         this.method_15055(pieces, pos.up(27), rotation, _snowmanxxxx, null);
         if (!pieces.isEmpty()) {
         }

         WoodlandMansionGenerator.RoomPool[] _snowmanxxxxxxxxx = new WoodlandMansionGenerator.RoomPool[]{
            new WoodlandMansionGenerator.FirstFloorRoomPool(),
            new WoodlandMansionGenerator.SecondFloorRoomPool(),
            new WoodlandMansionGenerator.ThirdFloorRoomPool()
         };

         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxx++) {
            BlockPos _snowmanxxxxxxxxxxxx = pos.up(8 * _snowmanxxxxxxxxxxx + (_snowmanxxxxxxxxxxx == 2 ? 3 : 0));
            WoodlandMansionGenerator.FlagMatrix _snowmanxxxxxxxxxxxxx = _snowman.field_15443[_snowmanxxxxxxxxxxx];
            WoodlandMansionGenerator.FlagMatrix _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx == 2 ? _snowmanxxxx : _snowmanxxx;
            String _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx == 0 ? "carpet_south_1" : "carpet_south_2";
            String _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx == 0 ? "carpet_west_1" : "carpet_west_2";

            for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.m; _snowmanxxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.n; _snowmanxxxxxxxxxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx) == 1) {
                     BlockPos _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.offset(rotation.rotate(Direction.SOUTH), 8 + (_snowmanxxxxxxxxxxxxxxxxx - this.field_15445) * 8);
                     _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.EAST), (_snowmanxxxxxxxxxxxxxxxxxx - this.field_15446) * 8);
                     pieces.add(new WoodlandMansionGenerator.Piece(this.manager, "corridor_floor", _snowmanxxxxxxxxxxxxxxxxxxx, rotation));
                     if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx - 1) == 1
                        || (_snowmanxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx - 1) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager, "carpet_north", _snowmanxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.EAST), 1).up(), rotation
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxxx) == 1
                        || (_snowmanxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxxx) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              "carpet_east",
                              _snowmanxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.SOUTH), 1).offset(rotation.rotate(Direction.EAST), 5).up(),
                              rotation
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx + 1) == 1
                        || (_snowmanxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx + 1) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              _snowmanxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.SOUTH), 5).offset(rotation.rotate(Direction.WEST), 1),
                              rotation
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxx) == 1
                        || (_snowmanxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxx) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              _snowmanxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.WEST), 1).offset(rotation.rotate(Direction.NORTH), 1),
                              rotation
                           )
                        );
                     }
                  }
               }
            }

            String _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx == 0 ? "indoors_wall_1" : "indoors_wall_2";
            String _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx == 0 ? "indoors_door_1" : "indoors_door_2";
            List<Direction> _snowmanxxxxxxxxxxxxxxxxxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.m; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.n; _snowmanxxxxxxxxxxxxxxxxxxxxxxx++) {
                  boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx == 2 && _snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx) == 3;
                  if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx) == 2 || _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx & 983040;
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx & 65535;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx && (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx & 8388608) == 8388608;
                     _snowmanxxxxxxxxxxxxxxxxxxxxx.clear();
                     if ((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx & 2097152) == 2097152) {
                        for (Direction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx : Direction.Type.HORIZONTAL) {
                           if (_snowmanxxxxxxxxxxxxxx.get(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getOffsetX(),
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getOffsetZ()
                              )
                              == 1) {
                              _snowmanxxxxxxxxxxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           }
                        }
                     }

                     Direction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = null;
                     if (!_snowmanxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.get(this.random.nextInt(_snowmanxxxxxxxxxxxxxxxxxxxxx.size()));
                     } else if ((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx & 1048576) == 1048576) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Direction.UP;
                     }

                     BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.offset(
                        rotation.rotate(Direction.SOUTH), 8 + (_snowmanxxxxxxxxxxxxxxxxxxxxxx - this.field_15445) * 8
                     );
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.offset(
                        rotation.rotate(Direction.EAST), -1 + (_snowmanxxxxxxxxxxxxxxxxxxxxxxx - this.field_15446) * 8
                     );
                     if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxxxxxx)
                        && !_snowman.method_15039(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.WEST ? _snowmanxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              rotation
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxxxxxxxx) == 1 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                        BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.EAST), 8);
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.EAST ? _snowmanxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              rotation
                           )
                        );
                     }

                     if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1)
                        && !_snowman.method_15039(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                        BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.SOUTH), 7);
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.EAST), 7);
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.SOUTH ? _snowmanxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              rotation.rotate(BlockRotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx - 1) == 1 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                        BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.NORTH), 1);
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.offset(rotation.rotate(Direction.EAST), 7);
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.NORTH ? _snowmanxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              rotation.rotate(BlockRotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx == 65536) {
                        this.addSmallRoom(pieces, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, rotation, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxx]);
                     } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx == 131072 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
                        Direction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.method_15040(
                           _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx & 4194304) == 4194304;
                        this.addMediumRoom(
                           pieces,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           rotation,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxx],
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                     } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx == 262144
                        && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null
                        && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != Direction.UP) {
                        Direction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.rotateYClockwise();
                        if (!_snowman.method_15039(
                           _snowmanxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getOffsetX(),
                           _snowmanxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getOffsetZ(),
                           _snowmanxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        )) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getOpposite();
                        }

                        this.addBigRoom(
                           pieces,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           rotation,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxx]
                        );
                     } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx == 262144 && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.UP) {
                        this.addBigSecretRoom(pieces, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, rotation, _snowmanxxxxxxxxx[_snowmanxxxxxxxxxxx]);
                     }
                  }
               }
            }
         }
      }

      private void addRoof(
         List<WoodlandMansionGenerator.Piece> _snowman,
         WoodlandMansionGenerator.GenerationPiece _snowman,
         WoodlandMansionGenerator.FlagMatrix _snowman,
         Direction _snowman,
         int _snowman,
         int _snowman,
         int _snowman,
         int _snowman
      ) {
         int _snowmanxxxxxxxx = _snowman;
         int _snowmanxxxxxxxxx = _snowman;
         Direction _snowmanxxxxxxxxxx = _snowman;

         do {
            if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx + _snowman.getOffsetX(), _snowmanxxxxxxxxx + _snowman.getOffsetZ())) {
               this.method_15058(_snowman, _snowman);
               _snowman = _snowman.rotateYClockwise();
               if (_snowmanxxxxxxxx != _snowman || _snowmanxxxxxxxxx != _snowman || _snowmanxxxxxxxxxx != _snowman) {
                  this.method_15052(_snowman, _snowman);
               }
            } else if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx + _snowman.getOffsetX(), _snowmanxxxxxxxxx + _snowman.getOffsetZ())
               && WoodlandMansionGenerator.MansionParameters.method_15047(
                  _snowman,
                  _snowmanxxxxxxxx + _snowman.getOffsetX() + _snowman.rotateYCounterclockwise().getOffsetX(),
                  _snowmanxxxxxxxxx + _snowman.getOffsetZ() + _snowman.rotateYCounterclockwise().getOffsetZ()
               )) {
               this.method_15060(_snowman, _snowman);
               _snowmanxxxxxxxx += _snowman.getOffsetX();
               _snowmanxxxxxxxxx += _snowman.getOffsetZ();
               _snowman = _snowman.rotateYCounterclockwise();
            } else {
               _snowmanxxxxxxxx += _snowman.getOffsetX();
               _snowmanxxxxxxxxx += _snowman.getOffsetZ();
               if (_snowmanxxxxxxxx != _snowman || _snowmanxxxxxxxxx != _snowman || _snowmanxxxxxxxxxx != _snowman) {
                  this.method_15052(_snowman, _snowman);
               }
            }
         } while (_snowmanxxxxxxxx != _snowman || _snowmanxxxxxxxxx != _snowman || _snowmanxxxxxxxxxx != _snowman);
      }

      private void method_15055(
         List<WoodlandMansionGenerator.Piece> _snowman,
         BlockPos _snowman,
         BlockRotation _snowman,
         WoodlandMansionGenerator.FlagMatrix _snowman,
         @Nullable WoodlandMansionGenerator.FlagMatrix _snowman
      ) {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.m; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowman.n; _snowmanxxxxxx++) {
               BlockPos var8 = _snowman.offset(_snowman.rotate(Direction.SOUTH), 8 + (_snowmanxxxxx - this.field_15445) * 8);
               var8 = var8.offset(_snowman.rotate(Direction.EAST), (_snowmanxxxxxx - this.field_15446) * 8);
               boolean _snowmanxxxxxxx = _snowman != null && WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxx, _snowmanxxxxx);
               if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxx, _snowmanxxxxx) && !_snowmanxxxxxxx) {
                  _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof", var8.up(3), _snowman));
                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxx + 1, _snowmanxxxxx)) {
                     BlockPos _snowmanxxxxxxxx = var8.offset(_snowman.rotate(Direction.EAST), 6);
                     _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", _snowmanxxxxxxxx, _snowman));
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxx - 1, _snowmanxxxxx)) {
                     BlockPos _snowmanxxxxxxxx = var8.offset(_snowman.rotate(Direction.EAST), 0);
                     _snowmanxxxxxxxx = _snowmanxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 7);
                     _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", _snowmanxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_180)));
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxx, _snowmanxxxxx - 1)) {
                     BlockPos _snowmanxxxxxxxx = var8.offset(_snowman.rotate(Direction.WEST), 1);
                     _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", _snowmanxxxxxxxx, _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxx, _snowmanxxxxx + 1)) {
                     BlockPos _snowmanxxxxxxxx = var8.offset(_snowman.rotate(Direction.EAST), 6);
                     _snowmanxxxxxxxx = _snowmanxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
                     _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", _snowmanxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90)));
                  }
               }
            }
         }

         if (_snowman != null) {
            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.m; _snowmanxxxxx++) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman.n; _snowmanxxxxxxx++) {
                  BlockPos var17 = _snowman.offset(_snowman.rotate(Direction.SOUTH), 8 + (_snowmanxxxxx - this.field_15445) * 8);
                  var17 = var17.offset(_snowman.rotate(Direction.EAST), (_snowmanxxxxxxx - this.field_15446) * 8);
                  boolean _snowmanxxxxxxxx = WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx);
                  if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx) && _snowmanxxxxxxxx) {
                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx + 1, _snowmanxxxxx)) {
                        BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.EAST), 7);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", _snowmanxxxxxxxxx, _snowman));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx - 1, _snowmanxxxxx)) {
                        BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.WEST), 1);
                        _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", _snowmanxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_180)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx - 1)) {
                        BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.WEST), 0);
                        _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowman.rotate(Direction.NORTH), 1);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", _snowmanxxxxxxxxx, _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx + 1)) {
                        BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.EAST), 6);
                        _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 7);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", _snowmanxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx + 1, _snowmanxxxxx)) {
                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx - 1)) {
                           BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.EAST), 7);
                           _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowman.rotate(Direction.NORTH), 2);
                           _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", _snowmanxxxxxxxxx, _snowman));
                        }

                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx + 1)) {
                           BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.EAST), 8);
                           _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 7);
                           _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", _snowmanxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90)));
                        }
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx - 1, _snowmanxxxxx)) {
                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx - 1)) {
                           BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.WEST), 2);
                           _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowman.rotate(Direction.NORTH), 1);
                           _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", _snowmanxxxxxxxxx, _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                        }

                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxx, _snowmanxxxxx + 1)) {
                           BlockPos _snowmanxxxxxxxxx = var17.offset(_snowman.rotate(Direction.WEST), 1);
                           _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 8);
                           _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", _snowmanxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_180)));
                        }
                     }
                  }
               }
            }
         }

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.m; _snowmanxxxxx++) {
            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowman.n; _snowmanxxxxxxxx++) {
               BlockPos var19 = _snowman.offset(_snowman.rotate(Direction.SOUTH), 8 + (_snowmanxxxxx - this.field_15445) * 8);
               var19 = var19.offset(_snowman.rotate(Direction.EAST), (_snowmanxxxxxxxx - this.field_15446) * 8);
               boolean _snowmanxxxxxxxxx = _snowman != null && WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx);
               if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx) && !_snowmanxxxxxxxxx) {
                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx + 1, _snowmanxxxxx)) {
                     BlockPos _snowmanxxxxxxxxxx = var19.offset(_snowman.rotate(Direction.EAST), 6);
                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx + 1)) {
                        BlockPos _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", _snowmanxxxxxxxxxxx, _snowman));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx + 1, _snowmanxxxxx + 1)) {
                        BlockPos _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 5);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", _snowmanxxxxxxxxxxx, _snowman));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx - 1)) {
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", _snowmanxxxxxxxxxx, _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx + 1, _snowmanxxxxx - 1)) {
                        BlockPos _snowmanxxxxxxxxxxx = var19.offset(_snowman.rotate(Direction.EAST), 9);
                        _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx.offset(_snowman.rotate(Direction.NORTH), 2);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", _snowmanxxxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90)));
                     }
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx - 1, _snowmanxxxxx)) {
                     BlockPos _snowmanxxxxxxxxxxx = var19.offset(_snowman.rotate(Direction.EAST), 0);
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 0);
                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx + 1)) {
                        BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", _snowmanxxxxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90)));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx - 1, _snowmanxxxxx + 1)) {
                        BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 8);
                        _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.offset(_snowman.rotate(Direction.WEST), 3);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", _snowmanxxxxxxxxxxxx, _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx, _snowmanxxxxx - 1)) {
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", _snowmanxxxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_180)));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(_snowman, _snowmanxxxxxxxx - 1, _snowmanxxxxx - 1)) {
                        BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 1);
                        _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", _snowmanxxxxxxxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_180)));
                     }
                  }
               }
            }
         }
      }

      private void addEntrance(List<WoodlandMansionGenerator.Piece> _snowman, WoodlandMansionGenerator.GenerationPiece _snowman) {
         Direction _snowmanxx = _snowman.rotation.rotate(Direction.WEST);
         _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "entrance", _snowman.position.offset(_snowmanxx, 9), _snowman.rotation));
         _snowman.position = _snowman.position.offset(_snowman.rotation.rotate(Direction.SOUTH), 16);
      }

      private void method_15052(List<WoodlandMansionGenerator.Piece> _snowman, WoodlandMansionGenerator.GenerationPiece _snowman) {
         _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.template, _snowman.position.offset(_snowman.rotation.rotate(Direction.EAST), 7), _snowman.rotation));
         _snowman.position = _snowman.position.offset(_snowman.rotation.rotate(Direction.SOUTH), 8);
      }

      private void method_15058(List<WoodlandMansionGenerator.Piece> _snowman, WoodlandMansionGenerator.GenerationPiece _snowman) {
         _snowman.position = _snowman.position.offset(_snowman.rotation.rotate(Direction.SOUTH), -1);
         _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, "wall_corner", _snowman.position, _snowman.rotation));
         _snowman.position = _snowman.position.offset(_snowman.rotation.rotate(Direction.SOUTH), -7);
         _snowman.position = _snowman.position.offset(_snowman.rotation.rotate(Direction.WEST), -6);
         _snowman.rotation = _snowman.rotation.rotate(BlockRotation.CLOCKWISE_90);
      }

      private void method_15060(List<WoodlandMansionGenerator.Piece> _snowman, WoodlandMansionGenerator.GenerationPiece _snowman) {
         _snowman.position = _snowman.position.offset(_snowman.rotation.rotate(Direction.SOUTH), 6);
         _snowman.position = _snowman.position.offset(_snowman.rotation.rotate(Direction.EAST), 8);
         _snowman.rotation = _snowman.rotation.rotate(BlockRotation.COUNTERCLOCKWISE_90);
      }

      private void addSmallRoom(List<WoodlandMansionGenerator.Piece> _snowman, BlockPos _snowman, BlockRotation _snowman, Direction _snowman, WoodlandMansionGenerator.RoomPool _snowman) {
         BlockRotation _snowmanxxxxx = BlockRotation.NONE;
         String _snowmanxxxxxx = _snowman.getSmallRoom(this.random);
         if (_snowman != Direction.EAST) {
            if (_snowman == Direction.NORTH) {
               _snowmanxxxxx = _snowmanxxxxx.rotate(BlockRotation.COUNTERCLOCKWISE_90);
            } else if (_snowman == Direction.WEST) {
               _snowmanxxxxx = _snowmanxxxxx.rotate(BlockRotation.CLOCKWISE_180);
            } else if (_snowman == Direction.SOUTH) {
               _snowmanxxxxx = _snowmanxxxxx.rotate(BlockRotation.CLOCKWISE_90);
            } else {
               _snowmanxxxxxx = _snowman.getSmallSecretRoom(this.random);
            }
         }

         BlockPos _snowmanxxxxxxx = Structure.applyTransformedOffset(new BlockPos(1, 0, 0), BlockMirror.NONE, _snowmanxxxxx, 7, 7);
         _snowmanxxxxx = _snowmanxxxxx.rotate(_snowman);
         _snowmanxxxxxxx = _snowmanxxxxxxx.rotate(_snowman);
         BlockPos _snowmanxxxxxxxx = _snowman.add(_snowmanxxxxxxx.getX(), 0, _snowmanxxxxxxx.getZ());
         _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx));
      }

      private void addMediumRoom(
         List<WoodlandMansionGenerator.Piece> _snowman, BlockPos _snowman, BlockRotation _snowman, Direction _snowman, Direction _snowman, WoodlandMansionGenerator.RoomPool _snowman, boolean staircase
      ) {
         if (_snowman == Direction.EAST && _snowman == Direction.SOUTH) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 1);
            _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman));
         } else if (_snowman == Direction.EAST && _snowman == Direction.NORTH) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 1);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
            _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman, BlockMirror.LEFT_RIGHT));
         } else if (_snowman == Direction.WEST && _snowman == Direction.NORTH) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 7);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_180)
               )
            );
         } else if (_snowman == Direction.WEST && _snowman == Direction.SOUTH) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 7);
            _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman, BlockMirror.FRONT_BACK));
         } else if (_snowman == Direction.SOUTH && _snowman == Direction.EAST) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 1);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90), BlockMirror.LEFT_RIGHT
               )
            );
         } else if (_snowman == Direction.SOUTH && _snowman == Direction.WEST) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 7);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90)
               )
            );
         } else if (_snowman == Direction.NORTH && _snowman == Direction.WEST) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 7);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90), BlockMirror.FRONT_BACK
               )
            );
         } else if (_snowman == Direction.NORTH && _snowman == Direction.EAST) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 1);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, _snowman.getMediumFunctionalRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (_snowman == Direction.SOUTH && _snowman == Direction.NORTH) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 1);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.NORTH), 8);
            _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumGenericRoom(this.random, staircase), _snowmanxxxxxx, _snowman));
         } else if (_snowman == Direction.NORTH && _snowman == Direction.SOUTH) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 7);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 14);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumGenericRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_180))
            );
         } else if (_snowman == Direction.WEST && _snowman == Direction.EAST) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 15);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumGenericRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90))
            );
         } else if (_snowman == Direction.EAST && _snowman == Direction.WEST) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.WEST), 7);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.SOUTH), 6);
            _snowman.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, _snowman.getMediumGenericRoom(this.random, staircase), _snowmanxxxxxx, _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (_snowman == Direction.UP && _snowman == Direction.EAST) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 15);
            _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumSecretRoom(this.random), _snowmanxxxxxx, _snowman.rotate(BlockRotation.CLOCKWISE_90)));
         } else if (_snowman == Direction.UP && _snowman == Direction.SOUTH) {
            BlockPos _snowmanxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 1);
            _snowmanxxxxxx = _snowmanxxxxxx.offset(_snowman.rotate(Direction.NORTH), 0);
            _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getMediumSecretRoom(this.random), _snowmanxxxxxx, _snowman));
         }
      }

      private void addBigRoom(
         List<WoodlandMansionGenerator.Piece> _snowman, BlockPos _snowman, BlockRotation _snowman, Direction _snowman, Direction _snowman, WoodlandMansionGenerator.RoomPool _snowman
      ) {
         int _snowmanxxxxxx = 0;
         int _snowmanxxxxxxx = 0;
         BlockRotation _snowmanxxxxxxxx = _snowman;
         BlockMirror _snowmanxxxxxxxxx = BlockMirror.NONE;
         if (_snowman == Direction.EAST && _snowman == Direction.SOUTH) {
            _snowmanxxxxxx = -7;
         } else if (_snowman == Direction.EAST && _snowman == Direction.NORTH) {
            _snowmanxxxxxx = -7;
            _snowmanxxxxxxx = 6;
            _snowmanxxxxxxxxx = BlockMirror.LEFT_RIGHT;
         } else if (_snowman == Direction.NORTH && _snowman == Direction.EAST) {
            _snowmanxxxxxx = 1;
            _snowmanxxxxxxx = 14;
            _snowmanxxxxxxxx = _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90);
         } else if (_snowman == Direction.NORTH && _snowman == Direction.WEST) {
            _snowmanxxxxxx = 7;
            _snowmanxxxxxxx = 14;
            _snowmanxxxxxxxx = _snowman.rotate(BlockRotation.COUNTERCLOCKWISE_90);
            _snowmanxxxxxxxxx = BlockMirror.LEFT_RIGHT;
         } else if (_snowman == Direction.SOUTH && _snowman == Direction.WEST) {
            _snowmanxxxxxx = 7;
            _snowmanxxxxxxx = -8;
            _snowmanxxxxxxxx = _snowman.rotate(BlockRotation.CLOCKWISE_90);
         } else if (_snowman == Direction.SOUTH && _snowman == Direction.EAST) {
            _snowmanxxxxxx = 1;
            _snowmanxxxxxxx = -8;
            _snowmanxxxxxxxx = _snowman.rotate(BlockRotation.CLOCKWISE_90);
            _snowmanxxxxxxxxx = BlockMirror.LEFT_RIGHT;
         } else if (_snowman == Direction.WEST && _snowman == Direction.NORTH) {
            _snowmanxxxxxx = 15;
            _snowmanxxxxxxx = 6;
            _snowmanxxxxxxxx = _snowman.rotate(BlockRotation.CLOCKWISE_180);
         } else if (_snowman == Direction.WEST && _snowman == Direction.SOUTH) {
            _snowmanxxxxxx = 15;
            _snowmanxxxxxxxxx = BlockMirror.FRONT_BACK;
         }

         BlockPos _snowmanxxxxxxxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), _snowmanxxxxxx);
         _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.offset(_snowman.rotate(Direction.SOUTH), _snowmanxxxxxxx);
         _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getBigRoom(this.random), _snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx));
      }

      private void addBigSecretRoom(List<WoodlandMansionGenerator.Piece> _snowman, BlockPos _snowman, BlockRotation _snowman, WoodlandMansionGenerator.RoomPool _snowman) {
         BlockPos _snowmanxxxx = _snowman.offset(_snowman.rotate(Direction.EAST), 1);
         _snowman.add(new WoodlandMansionGenerator.Piece(this.manager, _snowman.getBigSecretRoom(this.random), _snowmanxxxx, _snowman, BlockMirror.NONE));
      }
   }

   static class MansionParameters {
      private final Random random;
      private final WoodlandMansionGenerator.FlagMatrix field_15440;
      private final WoodlandMansionGenerator.FlagMatrix field_15439;
      private final WoodlandMansionGenerator.FlagMatrix[] field_15443;
      private final int field_15442;
      private final int field_15441;

      public MansionParameters(Random _snowman) {
         this.random = _snowman;
         int _snowmanx = 11;
         this.field_15442 = 7;
         this.field_15441 = 4;
         this.field_15440 = new WoodlandMansionGenerator.FlagMatrix(11, 11, 5);
         this.field_15440.fill(this.field_15442, this.field_15441, this.field_15442 + 1, this.field_15441 + 1, 3);
         this.field_15440.fill(this.field_15442 - 1, this.field_15441, this.field_15442 - 1, this.field_15441 + 1, 2);
         this.field_15440.fill(this.field_15442 + 2, this.field_15441 - 2, this.field_15442 + 3, this.field_15441 + 3, 5);
         this.field_15440.fill(this.field_15442 + 1, this.field_15441 - 2, this.field_15442 + 1, this.field_15441 - 1, 1);
         this.field_15440.fill(this.field_15442 + 1, this.field_15441 + 2, this.field_15442 + 1, this.field_15441 + 3, 1);
         this.field_15440.set(this.field_15442 - 1, this.field_15441 - 1, 1);
         this.field_15440.set(this.field_15442 - 1, this.field_15441 + 2, 1);
         this.field_15440.fill(0, 0, 11, 1, 5);
         this.field_15440.fill(0, 9, 11, 11, 5);
         this.method_15045(this.field_15440, this.field_15442, this.field_15441 - 2, Direction.WEST, 6);
         this.method_15045(this.field_15440, this.field_15442, this.field_15441 + 3, Direction.WEST, 6);
         this.method_15045(this.field_15440, this.field_15442 - 2, this.field_15441 - 1, Direction.WEST, 3);
         this.method_15045(this.field_15440, this.field_15442 - 2, this.field_15441 + 2, Direction.WEST, 3);

         while (this.method_15046(this.field_15440)) {
         }

         this.field_15443 = new WoodlandMansionGenerator.FlagMatrix[3];
         this.field_15443[0] = new WoodlandMansionGenerator.FlagMatrix(11, 11, 5);
         this.field_15443[1] = new WoodlandMansionGenerator.FlagMatrix(11, 11, 5);
         this.field_15443[2] = new WoodlandMansionGenerator.FlagMatrix(11, 11, 5);
         this.method_15042(this.field_15440, this.field_15443[0]);
         this.method_15042(this.field_15440, this.field_15443[1]);
         this.field_15443[0].fill(this.field_15442 + 1, this.field_15441, this.field_15442 + 1, this.field_15441 + 1, 8388608);
         this.field_15443[1].fill(this.field_15442 + 1, this.field_15441, this.field_15442 + 1, this.field_15441 + 1, 8388608);
         this.field_15439 = new WoodlandMansionGenerator.FlagMatrix(this.field_15440.n, this.field_15440.m, 5);
         this.method_15048();
         this.method_15042(this.field_15439, this.field_15443[2]);
      }

      public static boolean method_15047(WoodlandMansionGenerator.FlagMatrix _snowman, int _snowman, int _snowman) {
         int _snowmanxxx = _snowman.get(_snowman, _snowman);
         return _snowmanxxx == 1 || _snowmanxxx == 2 || _snowmanxxx == 3 || _snowmanxxx == 4;
      }

      public boolean method_15039(WoodlandMansionGenerator.FlagMatrix _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         return (this.field_15443[_snowman].get(_snowman, _snowman) & 65535) == _snowman;
      }

      @Nullable
      public Direction method_15040(WoodlandMansionGenerator.FlagMatrix _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         for (Direction _snowmanxxxxx : Direction.Type.HORIZONTAL) {
            if (this.method_15039(_snowman, _snowman + _snowmanxxxxx.getOffsetX(), _snowman + _snowmanxxxxx.getOffsetZ(), _snowman, _snowman)) {
               return _snowmanxxxxx;
            }
         }

         return null;
      }

      private void method_15045(WoodlandMansionGenerator.FlagMatrix _snowman, int _snowman, int _snowman, Direction _snowman, int _snowman) {
         if (_snowman > 0) {
            _snowman.set(_snowman, _snowman, 1);
            _snowman.update(_snowman + _snowman.getOffsetX(), _snowman + _snowman.getOffsetZ(), 0, 1);

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 8; _snowmanxxxxx++) {
               Direction _snowmanxxxxxx = Direction.fromHorizontal(this.random.nextInt(4));
               if (_snowmanxxxxxx != _snowman.getOpposite() && (_snowmanxxxxxx != Direction.EAST || !this.random.nextBoolean())) {
                  int _snowmanxxxxxxx = _snowman + _snowman.getOffsetX();
                  int _snowmanxxxxxxxx = _snowman + _snowman.getOffsetZ();
                  if (_snowman.get(_snowmanxxxxxxx + _snowmanxxxxxx.getOffsetX(), _snowmanxxxxxxxx + _snowmanxxxxxx.getOffsetZ()) == 0
                     && _snowman.get(_snowmanxxxxxxx + _snowmanxxxxxx.getOffsetX() * 2, _snowmanxxxxxxxx + _snowmanxxxxxx.getOffsetZ() * 2) == 0) {
                     this.method_15045(_snowman, _snowman + _snowman.getOffsetX() + _snowmanxxxxxx.getOffsetX(), _snowman + _snowman.getOffsetZ() + _snowmanxxxxxx.getOffsetZ(), _snowmanxxxxxx, _snowman - 1);
                     break;
                  }
               }
            }

            Direction _snowmanxxxxxx = _snowman.rotateYClockwise();
            Direction _snowmanxxxxxxx = _snowman.rotateYCounterclockwise();
            _snowman.update(_snowman + _snowmanxxxxxx.getOffsetX(), _snowman + _snowmanxxxxxx.getOffsetZ(), 0, 2);
            _snowman.update(_snowman + _snowmanxxxxxxx.getOffsetX(), _snowman + _snowmanxxxxxxx.getOffsetZ(), 0, 2);
            _snowman.update(_snowman + _snowman.getOffsetX() + _snowmanxxxxxx.getOffsetX(), _snowman + _snowman.getOffsetZ() + _snowmanxxxxxx.getOffsetZ(), 0, 2);
            _snowman.update(_snowman + _snowman.getOffsetX() + _snowmanxxxxxxx.getOffsetX(), _snowman + _snowman.getOffsetZ() + _snowmanxxxxxxx.getOffsetZ(), 0, 2);
            _snowman.update(_snowman + _snowman.getOffsetX() * 2, _snowman + _snowman.getOffsetZ() * 2, 0, 2);
            _snowman.update(_snowman + _snowmanxxxxxx.getOffsetX() * 2, _snowman + _snowmanxxxxxx.getOffsetZ() * 2, 0, 2);
            _snowman.update(_snowman + _snowmanxxxxxxx.getOffsetX() * 2, _snowman + _snowmanxxxxxxx.getOffsetZ() * 2, 0, 2);
         }
      }

      private boolean method_15046(WoodlandMansionGenerator.FlagMatrix _snowman) {
         boolean _snowmanx = false;

         for (int _snowmanxx = 0; _snowmanxx < _snowman.m; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.n; _snowmanxxx++) {
               if (_snowman.get(_snowmanxxx, _snowmanxx) == 0) {
                  int _snowmanxxxx = 0;
                  _snowmanxxxx += method_15047(_snowman, _snowmanxxx + 1, _snowmanxx) ? 1 : 0;
                  _snowmanxxxx += method_15047(_snowman, _snowmanxxx - 1, _snowmanxx) ? 1 : 0;
                  _snowmanxxxx += method_15047(_snowman, _snowmanxxx, _snowmanxx + 1) ? 1 : 0;
                  _snowmanxxxx += method_15047(_snowman, _snowmanxxx, _snowmanxx - 1) ? 1 : 0;
                  if (_snowmanxxxx >= 3) {
                     _snowman.set(_snowmanxxx, _snowmanxx, 2);
                     _snowmanx = true;
                  } else if (_snowmanxxxx == 2) {
                     int _snowmanxxxxx = 0;
                     _snowmanxxxxx += method_15047(_snowman, _snowmanxxx + 1, _snowmanxx + 1) ? 1 : 0;
                     _snowmanxxxxx += method_15047(_snowman, _snowmanxxx - 1, _snowmanxx + 1) ? 1 : 0;
                     _snowmanxxxxx += method_15047(_snowman, _snowmanxxx + 1, _snowmanxx - 1) ? 1 : 0;
                     _snowmanxxxxx += method_15047(_snowman, _snowmanxxx - 1, _snowmanxx - 1) ? 1 : 0;
                     if (_snowmanxxxxx <= 1) {
                        _snowman.set(_snowmanxxx, _snowmanxx, 2);
                        _snowmanx = true;
                     }
                  }
               }
            }
         }

         return _snowmanx;
      }

      private void method_15048() {
         List<Pair<Integer, Integer>> _snowman = Lists.newArrayList();
         WoodlandMansionGenerator.FlagMatrix _snowmanx = this.field_15443[1];

         for (int _snowmanxx = 0; _snowmanxx < this.field_15439.m; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < this.field_15439.n; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanx.get(_snowmanxxx, _snowmanxx);
               int _snowmanxxxxx = _snowmanxxxx & 983040;
               if (_snowmanxxxxx == 131072 && (_snowmanxxxx & 2097152) == 2097152) {
                  _snowman.add(new Pair<>(_snowmanxxx, _snowmanxx));
               }
            }
         }

         if (_snowman.isEmpty()) {
            this.field_15439.fill(0, 0, this.field_15439.n, this.field_15439.m, 5);
         } else {
            Pair<Integer, Integer> _snowmanxx = _snowman.get(this.random.nextInt(_snowman.size()));
            int _snowmanxxxx = _snowmanx.get(_snowmanxx.getLeft(), _snowmanxx.getRight());
            _snowmanx.set(_snowmanxx.getLeft(), _snowmanxx.getRight(), _snowmanxxxx | 4194304);
            Direction _snowmanxxxxx = this.method_15040(this.field_15440, _snowmanxx.getLeft(), _snowmanxx.getRight(), 1, _snowmanxxxx & 65535);
            int _snowmanxxxxxx = _snowmanxx.getLeft() + _snowmanxxxxx.getOffsetX();
            int _snowmanxxxxxxx = _snowmanxx.getRight() + _snowmanxxxxx.getOffsetZ();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < this.field_15439.m; _snowmanxxxxxxxx++) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < this.field_15439.n; _snowmanxxxxxxxxx++) {
                  if (!method_15047(this.field_15440, _snowmanxxxxxxxxx, _snowmanxxxxxxxx)) {
                     this.field_15439.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 5);
                  } else if (_snowmanxxxxxxxxx == _snowmanxx.getLeft() && _snowmanxxxxxxxx == _snowmanxx.getRight()) {
                     this.field_15439.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 3);
                  } else if (_snowmanxxxxxxxxx == _snowmanxxxxxx && _snowmanxxxxxxxx == _snowmanxxxxxxx) {
                     this.field_15439.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 3);
                     this.field_15443[2].set(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 8388608);
                  }
               }
            }

            List<Direction> _snowmanxxxxxxxx = Lists.newArrayList();

            for (Direction _snowmanxxxxxxxxxx : Direction.Type.HORIZONTAL) {
               if (this.field_15439.get(_snowmanxxxxxx + _snowmanxxxxxxxxxx.getOffsetX(), _snowmanxxxxxxx + _snowmanxxxxxxxxxx.getOffsetZ()) == 0) {
                  _snowmanxxxxxxxx.add(_snowmanxxxxxxxxxx);
               }
            }

            if (_snowmanxxxxxxxx.isEmpty()) {
               this.field_15439.fill(0, 0, this.field_15439.n, this.field_15439.m, 5);
               _snowmanx.set(_snowmanxx.getLeft(), _snowmanxx.getRight(), _snowmanxxxx);
            } else {
               Direction _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.get(this.random.nextInt(_snowmanxxxxxxxx.size()));
               this.method_15045(this.field_15439, _snowmanxxxxxx + _snowmanxxxxxxxxxxx.getOffsetX(), _snowmanxxxxxxx + _snowmanxxxxxxxxxxx.getOffsetZ(), _snowmanxxxxxxxxxxx, 4);

               while (this.method_15046(this.field_15439)) {
               }
            }
         }
      }

      private void method_15042(WoodlandMansionGenerator.FlagMatrix _snowman, WoodlandMansionGenerator.FlagMatrix _snowman) {
         List<Pair<Integer, Integer>> _snowmanxx = Lists.newArrayList();

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman.m; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.n; _snowmanxxxx++) {
               if (_snowman.get(_snowmanxxxx, _snowmanxxx) == 2) {
                  _snowmanxx.add(new Pair<>(_snowmanxxxx, _snowmanxxx));
               }
            }
         }

         Collections.shuffle(_snowmanxx, this.random);
         int _snowmanxxx = 10;

         for (Pair<Integer, Integer> _snowmanxxxxx : _snowmanxx) {
            int _snowmanxxxxxx = _snowmanxxxxx.getLeft();
            int _snowmanxxxxxxx = _snowmanxxxxx.getRight();
            if (_snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx) == 0) {
               int _snowmanxxxxxxxx = _snowmanxxxxxx;
               int _snowmanxxxxxxxxx = _snowmanxxxxxx;
               int _snowmanxxxxxxxxxx = _snowmanxxxxxxx;
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxx;
               int _snowmanxxxxxxxxxxxx = 65536;
               if (_snowman.get(_snowmanxxxxxx + 1, _snowmanxxxxxxx) == 0
                  && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx + 1) == 0
                  && _snowman.get(_snowmanxxxxxx + 1, _snowmanxxxxxxx + 1) == 0
                  && _snowman.get(_snowmanxxxxxx + 1, _snowmanxxxxxxx) == 2
                  && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx + 1) == 2
                  && _snowman.get(_snowmanxxxxxx + 1, _snowmanxxxxxxx + 1) == 2) {
                  _snowmanxxxxxxxxx = _snowmanxxxxxx + 1;
                  _snowmanxxxxxxxxxxx = _snowmanxxxxxxx + 1;
                  _snowmanxxxxxxxxxxxx = 262144;
               } else if (_snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx) == 0
                  && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx + 1) == 0
                  && _snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx + 1) == 0
                  && _snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx) == 2
                  && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx + 1) == 2
                  && _snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx + 1) == 2) {
                  _snowmanxxxxxxxx = _snowmanxxxxxx - 1;
                  _snowmanxxxxxxxxxxx = _snowmanxxxxxxx + 1;
                  _snowmanxxxxxxxxxxxx = 262144;
               } else if (_snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx) == 0
                  && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx - 1) == 0
                  && _snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx - 1) == 0
                  && _snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx) == 2
                  && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx - 1) == 2
                  && _snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx - 1) == 2) {
                  _snowmanxxxxxxxx = _snowmanxxxxxx - 1;
                  _snowmanxxxxxxxxxx = _snowmanxxxxxxx - 1;
                  _snowmanxxxxxxxxxxxx = 262144;
               } else if (_snowman.get(_snowmanxxxxxx + 1, _snowmanxxxxxxx) == 0 && _snowman.get(_snowmanxxxxxx + 1, _snowmanxxxxxxx) == 2) {
                  _snowmanxxxxxxxxx = _snowmanxxxxxx + 1;
                  _snowmanxxxxxxxxxxxx = 131072;
               } else if (_snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx + 1) == 0 && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx + 1) == 2) {
                  _snowmanxxxxxxxxxxx = _snowmanxxxxxxx + 1;
                  _snowmanxxxxxxxxxxxx = 131072;
               } else if (_snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx) == 0 && _snowman.get(_snowmanxxxxxx - 1, _snowmanxxxxxxx) == 2) {
                  _snowmanxxxxxxxx = _snowmanxxxxxx - 1;
                  _snowmanxxxxxxxxxxxx = 131072;
               } else if (_snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx - 1) == 0 && _snowman.get(_snowmanxxxxxx, _snowmanxxxxxxx - 1) == 2) {
                  _snowmanxxxxxxxxxx = _snowmanxxxxxxx - 1;
                  _snowmanxxxxxxxxxxxx = 131072;
               }

               int _snowmanxxxxxxxxxxxxx = this.random.nextBoolean() ? _snowmanxxxxxxxx : _snowmanxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxx = this.random.nextBoolean() ? _snowmanxxxxxxxxxx : _snowmanxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxx = 2097152;
               if (!_snowman.anyMatchAround(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 1)) {
                  _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx == _snowmanxxxxxxxx ? _snowmanxxxxxxxxx : _snowmanxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx == _snowmanxxxxxxxxxx ? _snowmanxxxxxxxxxxx : _snowmanxxxxxxxxxx;
                  if (!_snowman.anyMatchAround(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 1)) {
                     _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx == _snowmanxxxxxxxxxx ? _snowmanxxxxxxxxxxx : _snowmanxxxxxxxxxx;
                     if (!_snowman.anyMatchAround(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 1)) {
                        _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx == _snowmanxxxxxxxx ? _snowmanxxxxxxxxx : _snowmanxxxxxxxx;
                        _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx == _snowmanxxxxxxxxxx ? _snowmanxxxxxxxxxxx : _snowmanxxxxxxxxxx;
                        if (!_snowman.anyMatchAround(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 1)) {
                           _snowmanxxxxxxxxxxxxxxx = 0;
                           _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx;
                           _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx;
                        }
                     }
                  }
               }

               for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
                     if (_snowmanxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxx) {
                        _snowman.set(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, 1048576 | _snowmanxxxxxxxxxxxxxxx | _snowmanxxxxxxxxxxxx | _snowmanxxx);
                     } else {
                        _snowman.set(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx | _snowmanxxx);
                     }
                  }
               }

               _snowmanxxx++;
            }
         }
      }
   }

   public static class Piece extends SimpleStructurePiece {
      private final String template;
      private final BlockRotation rotation;
      private final BlockMirror mirror;

      public Piece(StructureManager _snowman, String _snowman, BlockPos _snowman, BlockRotation _snowman) {
         this(_snowman, _snowman, _snowman, _snowman, BlockMirror.NONE);
      }

      public Piece(StructureManager _snowman, String _snowman, BlockPos _snowman, BlockRotation _snowman, BlockMirror _snowman) {
         super(StructurePieceType.WOODLAND_MANSION, 0);
         this.template = _snowman;
         this.pos = _snowman;
         this.rotation = _snowman;
         this.mirror = _snowman;
         this.setupPlacement(_snowman);
      }

      public Piece(StructureManager _snowman, CompoundTag _snowman) {
         super(StructurePieceType.WOODLAND_MANSION, _snowman);
         this.template = _snowman.getString("Template");
         this.rotation = BlockRotation.valueOf(_snowman.getString("Rot"));
         this.mirror = BlockMirror.valueOf(_snowman.getString("Mi"));
         this.setupPlacement(_snowman);
      }

      private void setupPlacement(StructureManager _snowman) {
         Structure _snowmanx = _snowman.getStructureOrBlank(new Identifier("woodland_mansion/" + this.template));
         StructurePlacementData _snowmanxx = new StructurePlacementData()
            .setIgnoreEntities(true)
            .setRotation(this.rotation)
            .setMirror(this.mirror)
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
         this.setStructureData(_snowmanx, this.pos, _snowmanxx);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putString("Template", this.template);
         tag.putString("Rot", this.placementData.getRotation().name());
         tag.putString("Mi", this.placementData.getMirror().name());
      }

      @Override
      protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess _snowman, Random random, BlockBox boundingBox) {
         if (metadata.startsWith("Chest")) {
            BlockRotation _snowmanx = this.placementData.getRotation();
            BlockState _snowmanxx = Blocks.CHEST.getDefaultState();
            if ("ChestWest".equals(metadata)) {
               _snowmanxx = _snowmanxx.with(ChestBlock.FACING, _snowmanx.rotate(Direction.WEST));
            } else if ("ChestEast".equals(metadata)) {
               _snowmanxx = _snowmanxx.with(ChestBlock.FACING, _snowmanx.rotate(Direction.EAST));
            } else if ("ChestSouth".equals(metadata)) {
               _snowmanxx = _snowmanxx.with(ChestBlock.FACING, _snowmanx.rotate(Direction.SOUTH));
            } else if ("ChestNorth".equals(metadata)) {
               _snowmanxx = _snowmanxx.with(ChestBlock.FACING, _snowmanx.rotate(Direction.NORTH));
            }

            this.addChest(_snowman, boundingBox, random, pos, LootTables.WOODLAND_MANSION_CHEST, _snowmanxx);
         } else {
            IllagerEntity _snowmanx;
            switch (metadata) {
               case "Mage":
                  _snowmanx = EntityType.EVOKER.create(_snowman.toServerWorld());
                  break;
               case "Warrior":
                  _snowmanx = EntityType.VINDICATOR.create(_snowman.toServerWorld());
                  break;
               default:
                  return;
            }

            _snowmanx.setPersistent();
            _snowmanx.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            _snowmanx.initialize(_snowman, _snowman.getLocalDifficulty(_snowmanx.getBlockPos()), SpawnReason.STRUCTURE, null, null);
            _snowman.spawnEntityAndPassengers(_snowmanx);
            _snowman.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
         }
      }
   }

   abstract static class RoomPool {
      private RoomPool() {
      }

      public abstract String getSmallRoom(Random random);

      public abstract String getSmallSecretRoom(Random random);

      public abstract String getMediumFunctionalRoom(Random random, boolean staircase);

      public abstract String getMediumGenericRoom(Random random, boolean staircase);

      public abstract String getMediumSecretRoom(Random random);

      public abstract String getBigRoom(Random random);

      public abstract String getBigSecretRoom(Random random);
   }

   static class SecondFloorRoomPool extends WoodlandMansionGenerator.RoomPool {
      private SecondFloorRoomPool() {
      }

      @Override
      public String getSmallRoom(Random random) {
         return "1x1_b" + (random.nextInt(4) + 1);
      }

      @Override
      public String getSmallSecretRoom(Random random) {
         return "1x1_as" + (random.nextInt(4) + 1);
      }

      @Override
      public String getMediumFunctionalRoom(Random random, boolean staircase) {
         return staircase ? "1x2_c_stairs" : "1x2_c" + (random.nextInt(4) + 1);
      }

      @Override
      public String getMediumGenericRoom(Random random, boolean staircase) {
         return staircase ? "1x2_d_stairs" : "1x2_d" + (random.nextInt(5) + 1);
      }

      @Override
      public String getMediumSecretRoom(Random random) {
         return "1x2_se" + (random.nextInt(1) + 1);
      }

      @Override
      public String getBigRoom(Random random) {
         return "2x2_b" + (random.nextInt(5) + 1);
      }

      @Override
      public String getBigSecretRoom(Random random) {
         return "2x2_s1";
      }
   }

   static class ThirdFloorRoomPool extends WoodlandMansionGenerator.SecondFloorRoomPool {
      private ThirdFloorRoomPool() {
      }
   }
}
