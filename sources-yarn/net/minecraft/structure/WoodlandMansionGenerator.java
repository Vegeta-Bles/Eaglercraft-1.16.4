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
      WoodlandMansionGenerator.MansionParameters lv = new WoodlandMansionGenerator.MansionParameters(random);
      WoodlandMansionGenerator.LayoutGenerator lv2 = new WoodlandMansionGenerator.LayoutGenerator(manager, random);
      lv2.generate(pos, rotation, pieces, lv);
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
         for (int n = j0; n <= j1; n++) {
            for (int o = i0; o <= i1; o++) {
               this.set(o, n, value);
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

      public void generate(BlockPos pos, BlockRotation rotation, List<WoodlandMansionGenerator.Piece> pieces, WoodlandMansionGenerator.MansionParameters arg3) {
         WoodlandMansionGenerator.GenerationPiece lv = new WoodlandMansionGenerator.GenerationPiece();
         lv.position = pos;
         lv.rotation = rotation;
         lv.template = "wall_flat";
         WoodlandMansionGenerator.GenerationPiece lv2 = new WoodlandMansionGenerator.GenerationPiece();
         this.addEntrance(pieces, lv);
         lv2.position = lv.position.up(8);
         lv2.rotation = lv.rotation;
         lv2.template = "wall_window";
         if (!pieces.isEmpty()) {
         }

         WoodlandMansionGenerator.FlagMatrix lv3 = arg3.field_15440;
         WoodlandMansionGenerator.FlagMatrix lv4 = arg3.field_15439;
         this.field_15446 = arg3.field_15442 + 1;
         this.field_15445 = arg3.field_15441 + 1;
         int i = arg3.field_15442 + 1;
         int j = arg3.field_15441;
         this.addRoof(pieces, lv, lv3, Direction.SOUTH, this.field_15446, this.field_15445, i, j);
         this.addRoof(pieces, lv2, lv3, Direction.SOUTH, this.field_15446, this.field_15445, i, j);
         WoodlandMansionGenerator.GenerationPiece lv5 = new WoodlandMansionGenerator.GenerationPiece();
         lv5.position = lv.position.up(19);
         lv5.rotation = lv.rotation;
         lv5.template = "wall_window";
         boolean bl = false;

         for (int k = 0; k < lv4.m && !bl; k++) {
            for (int l = lv4.n - 1; l >= 0 && !bl; l--) {
               if (WoodlandMansionGenerator.MansionParameters.method_15047(lv4, l, k)) {
                  lv5.position = lv5.position.offset(rotation.rotate(Direction.SOUTH), 8 + (k - this.field_15445) * 8);
                  lv5.position = lv5.position.offset(rotation.rotate(Direction.EAST), (l - this.field_15446) * 8);
                  this.method_15052(pieces, lv5);
                  this.addRoof(pieces, lv5, lv4, Direction.SOUTH, l, k, l, k);
                  bl = true;
               }
            }
         }

         this.method_15055(pieces, pos.up(16), rotation, lv3, lv4);
         this.method_15055(pieces, pos.up(27), rotation, lv4, null);
         if (!pieces.isEmpty()) {
         }

         WoodlandMansionGenerator.RoomPool[] lvs = new WoodlandMansionGenerator.RoomPool[]{
            new WoodlandMansionGenerator.FirstFloorRoomPool(),
            new WoodlandMansionGenerator.SecondFloorRoomPool(),
            new WoodlandMansionGenerator.ThirdFloorRoomPool()
         };

         for (int m = 0; m < 3; m++) {
            BlockPos lv6 = pos.up(8 * m + (m == 2 ? 3 : 0));
            WoodlandMansionGenerator.FlagMatrix lv7 = arg3.field_15443[m];
            WoodlandMansionGenerator.FlagMatrix lv8 = m == 2 ? lv4 : lv3;
            String string = m == 0 ? "carpet_south_1" : "carpet_south_2";
            String string2 = m == 0 ? "carpet_west_1" : "carpet_west_2";

            for (int n = 0; n < lv8.m; n++) {
               for (int o = 0; o < lv8.n; o++) {
                  if (lv8.get(o, n) == 1) {
                     BlockPos lv9 = lv6.offset(rotation.rotate(Direction.SOUTH), 8 + (n - this.field_15445) * 8);
                     lv9 = lv9.offset(rotation.rotate(Direction.EAST), (o - this.field_15446) * 8);
                     pieces.add(new WoodlandMansionGenerator.Piece(this.manager, "corridor_floor", lv9, rotation));
                     if (lv8.get(o, n - 1) == 1 || (lv7.get(o, n - 1) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(this.manager, "carpet_north", lv9.offset(rotation.rotate(Direction.EAST), 1).up(), rotation)
                        );
                     }

                     if (lv8.get(o + 1, n) == 1 || (lv7.get(o + 1, n) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager,
                              "carpet_east",
                              lv9.offset(rotation.rotate(Direction.SOUTH), 1).offset(rotation.rotate(Direction.EAST), 5).up(),
                              rotation
                           )
                        );
                     }

                     if (lv8.get(o, n + 1) == 1 || (lv7.get(o, n + 1) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager, string, lv9.offset(rotation.rotate(Direction.SOUTH), 5).offset(rotation.rotate(Direction.WEST), 1), rotation
                           )
                        );
                     }

                     if (lv8.get(o - 1, n) == 1 || (lv7.get(o - 1, n) & 8388608) == 8388608) {
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager, string2, lv9.offset(rotation.rotate(Direction.WEST), 1).offset(rotation.rotate(Direction.NORTH), 1), rotation
                           )
                        );
                     }
                  }
               }
            }

            String string3 = m == 0 ? "indoors_wall_1" : "indoors_wall_2";
            String string4 = m == 0 ? "indoors_door_1" : "indoors_door_2";
            List<Direction> list2 = Lists.newArrayList();

            for (int p = 0; p < lv8.m; p++) {
               for (int q = 0; q < lv8.n; q++) {
                  boolean bl2 = m == 2 && lv8.get(q, p) == 3;
                  if (lv8.get(q, p) == 2 || bl2) {
                     int r = lv7.get(q, p);
                     int s = r & 983040;
                     int t = r & 65535;
                     bl2 = bl2 && (r & 8388608) == 8388608;
                     list2.clear();
                     if ((r & 2097152) == 2097152) {
                        for (Direction lv10 : Direction.Type.HORIZONTAL) {
                           if (lv8.get(q + lv10.getOffsetX(), p + lv10.getOffsetZ()) == 1) {
                              list2.add(lv10);
                           }
                        }
                     }

                     Direction lv11 = null;
                     if (!list2.isEmpty()) {
                        lv11 = list2.get(this.random.nextInt(list2.size()));
                     } else if ((r & 1048576) == 1048576) {
                        lv11 = Direction.UP;
                     }

                     BlockPos lv12 = lv6.offset(rotation.rotate(Direction.SOUTH), 8 + (p - this.field_15445) * 8);
                     lv12 = lv12.offset(rotation.rotate(Direction.EAST), -1 + (q - this.field_15446) * 8);
                     if (WoodlandMansionGenerator.MansionParameters.method_15047(lv8, q - 1, p) && !arg3.method_15039(lv8, q - 1, p, m, t)) {
                        pieces.add(new WoodlandMansionGenerator.Piece(this.manager, lv11 == Direction.WEST ? string4 : string3, lv12, rotation));
                     }

                     if (lv8.get(q + 1, p) == 1 && !bl2) {
                        BlockPos lv13 = lv12.offset(rotation.rotate(Direction.EAST), 8);
                        pieces.add(new WoodlandMansionGenerator.Piece(this.manager, lv11 == Direction.EAST ? string4 : string3, lv13, rotation));
                     }

                     if (WoodlandMansionGenerator.MansionParameters.method_15047(lv8, q, p + 1) && !arg3.method_15039(lv8, q, p + 1, m, t)) {
                        BlockPos lv14 = lv12.offset(rotation.rotate(Direction.SOUTH), 7);
                        lv14 = lv14.offset(rotation.rotate(Direction.EAST), 7);
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager, lv11 == Direction.SOUTH ? string4 : string3, lv14, rotation.rotate(BlockRotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (lv8.get(q, p - 1) == 1 && !bl2) {
                        BlockPos lv15 = lv12.offset(rotation.rotate(Direction.NORTH), 1);
                        lv15 = lv15.offset(rotation.rotate(Direction.EAST), 7);
                        pieces.add(
                           new WoodlandMansionGenerator.Piece(
                              this.manager, lv11 == Direction.NORTH ? string4 : string3, lv15, rotation.rotate(BlockRotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (s == 65536) {
                        this.addSmallRoom(pieces, lv12, rotation, lv11, lvs[m]);
                     } else if (s == 131072 && lv11 != null) {
                        Direction lv16 = arg3.method_15040(lv8, q, p, m, t);
                        boolean bl3 = (r & 4194304) == 4194304;
                        this.addMediumRoom(pieces, lv12, rotation, lv16, lv11, lvs[m], bl3);
                     } else if (s == 262144 && lv11 != null && lv11 != Direction.UP) {
                        Direction lv17 = lv11.rotateYClockwise();
                        if (!arg3.method_15039(lv8, q + lv17.getOffsetX(), p + lv17.getOffsetZ(), m, t)) {
                           lv17 = lv17.getOpposite();
                        }

                        this.addBigRoom(pieces, lv12, rotation, lv17, lv11, lvs[m]);
                     } else if (s == 262144 && lv11 == Direction.UP) {
                        this.addBigSecretRoom(pieces, lv12, rotation, lvs[m]);
                     }
                  }
               }
            }
         }
      }

      private void addRoof(
         List<WoodlandMansionGenerator.Piece> list,
         WoodlandMansionGenerator.GenerationPiece arg,
         WoodlandMansionGenerator.FlagMatrix arg2,
         Direction arg3,
         int i,
         int j,
         int k,
         int l
      ) {
         int m = i;
         int n = j;
         Direction lv = arg3;

         do {
            if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg2, m + arg3.getOffsetX(), n + arg3.getOffsetZ())) {
               this.method_15058(list, arg);
               arg3 = arg3.rotateYClockwise();
               if (m != k || n != l || lv != arg3) {
                  this.method_15052(list, arg);
               }
            } else if (WoodlandMansionGenerator.MansionParameters.method_15047(arg2, m + arg3.getOffsetX(), n + arg3.getOffsetZ())
               && WoodlandMansionGenerator.MansionParameters.method_15047(
                  arg2,
                  m + arg3.getOffsetX() + arg3.rotateYCounterclockwise().getOffsetX(),
                  n + arg3.getOffsetZ() + arg3.rotateYCounterclockwise().getOffsetZ()
               )) {
               this.method_15060(list, arg);
               m += arg3.getOffsetX();
               n += arg3.getOffsetZ();
               arg3 = arg3.rotateYCounterclockwise();
            } else {
               m += arg3.getOffsetX();
               n += arg3.getOffsetZ();
               if (m != k || n != l || lv != arg3) {
                  this.method_15052(list, arg);
               }
            }
         } while (m != k || n != l || lv != arg3);
      }

      private void method_15055(
         List<WoodlandMansionGenerator.Piece> list,
         BlockPos arg,
         BlockRotation arg2,
         WoodlandMansionGenerator.FlagMatrix arg3,
         @Nullable WoodlandMansionGenerator.FlagMatrix arg4
      ) {
         for (int i = 0; i < arg3.m; i++) {
            for (int j = 0; j < arg3.n; j++) {
               BlockPos lv15 = arg.offset(arg2.rotate(Direction.SOUTH), 8 + (i - this.field_15445) * 8);
               lv15 = lv15.offset(arg2.rotate(Direction.EAST), (j - this.field_15446) * 8);
               boolean bl = arg4 != null && WoodlandMansionGenerator.MansionParameters.method_15047(arg4, j, i);
               if (WoodlandMansionGenerator.MansionParameters.method_15047(arg3, j, i) && !bl) {
                  list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof", lv15.up(3), arg2));
                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, j + 1, i)) {
                     BlockPos lv2 = lv15.offset(arg2.rotate(Direction.EAST), 6);
                     list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", lv2, arg2));
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, j - 1, i)) {
                     BlockPos lv3 = lv15.offset(arg2.rotate(Direction.EAST), 0);
                     lv3 = lv3.offset(arg2.rotate(Direction.SOUTH), 7);
                     list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", lv3, arg2.rotate(BlockRotation.CLOCKWISE_180)));
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, j, i - 1)) {
                     BlockPos lv4 = lv15.offset(arg2.rotate(Direction.WEST), 1);
                     list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", lv4, arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, j, i + 1)) {
                     BlockPos lv5 = lv15.offset(arg2.rotate(Direction.EAST), 6);
                     lv5 = lv5.offset(arg2.rotate(Direction.SOUTH), 6);
                     list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_front", lv5, arg2.rotate(BlockRotation.CLOCKWISE_90)));
                  }
               }
            }
         }

         if (arg4 != null) {
            for (int k = 0; k < arg3.m; k++) {
               for (int l = 0; l < arg3.n; l++) {
                  BlockPos var17 = arg.offset(arg2.rotate(Direction.SOUTH), 8 + (k - this.field_15445) * 8);
                  var17 = var17.offset(arg2.rotate(Direction.EAST), (l - this.field_15446) * 8);
                  boolean bl2 = WoodlandMansionGenerator.MansionParameters.method_15047(arg4, l, k);
                  if (WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l, k) && bl2) {
                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l + 1, k)) {
                        BlockPos lv7 = var17.offset(arg2.rotate(Direction.EAST), 7);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", lv7, arg2));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l - 1, k)) {
                        BlockPos lv8 = var17.offset(arg2.rotate(Direction.WEST), 1);
                        lv8 = lv8.offset(arg2.rotate(Direction.SOUTH), 6);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", lv8, arg2.rotate(BlockRotation.CLOCKWISE_180)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l, k - 1)) {
                        BlockPos lv9 = var17.offset(arg2.rotate(Direction.WEST), 0);
                        lv9 = lv9.offset(arg2.rotate(Direction.NORTH), 1);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", lv9, arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l, k + 1)) {
                        BlockPos lv10 = var17.offset(arg2.rotate(Direction.EAST), 6);
                        lv10 = lv10.offset(arg2.rotate(Direction.SOUTH), 7);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall", lv10, arg2.rotate(BlockRotation.CLOCKWISE_90)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l + 1, k)) {
                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l, k - 1)) {
                           BlockPos lv11 = var17.offset(arg2.rotate(Direction.EAST), 7);
                           lv11 = lv11.offset(arg2.rotate(Direction.NORTH), 2);
                           list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", lv11, arg2));
                        }

                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l, k + 1)) {
                           BlockPos lv12 = var17.offset(arg2.rotate(Direction.EAST), 8);
                           lv12 = lv12.offset(arg2.rotate(Direction.SOUTH), 7);
                           list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", lv12, arg2.rotate(BlockRotation.CLOCKWISE_90)));
                        }
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l - 1, k)) {
                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l, k - 1)) {
                           BlockPos lv13 = var17.offset(arg2.rotate(Direction.WEST), 2);
                           lv13 = lv13.offset(arg2.rotate(Direction.NORTH), 1);
                           list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", lv13, arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                        }

                        if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, l, k + 1)) {
                           BlockPos lv14 = var17.offset(arg2.rotate(Direction.WEST), 1);
                           lv14 = lv14.offset(arg2.rotate(Direction.SOUTH), 8);
                           list.add(new WoodlandMansionGenerator.Piece(this.manager, "small_wall_corner", lv14, arg2.rotate(BlockRotation.CLOCKWISE_180)));
                        }
                     }
                  }
               }
            }
         }

         for (int m = 0; m < arg3.m; m++) {
            for (int n = 0; n < arg3.n; n++) {
               BlockPos var19 = arg.offset(arg2.rotate(Direction.SOUTH), 8 + (m - this.field_15445) * 8);
               var19 = var19.offset(arg2.rotate(Direction.EAST), (n - this.field_15446) * 8);
               boolean bl3 = arg4 != null && WoodlandMansionGenerator.MansionParameters.method_15047(arg4, n, m);
               if (WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n, m) && !bl3) {
                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n + 1, m)) {
                     BlockPos lv16 = var19.offset(arg2.rotate(Direction.EAST), 6);
                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n, m + 1)) {
                        BlockPos lv17 = lv16.offset(arg2.rotate(Direction.SOUTH), 6);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", lv17, arg2));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n + 1, m + 1)) {
                        BlockPos lv18 = lv16.offset(arg2.rotate(Direction.SOUTH), 5);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", lv18, arg2));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n, m - 1)) {
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", lv16, arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n + 1, m - 1)) {
                        BlockPos lv19 = var19.offset(arg2.rotate(Direction.EAST), 9);
                        lv19 = lv19.offset(arg2.rotate(Direction.NORTH), 2);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", lv19, arg2.rotate(BlockRotation.CLOCKWISE_90)));
                     }
                  }

                  if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n - 1, m)) {
                     BlockPos lv20 = var19.offset(arg2.rotate(Direction.EAST), 0);
                     lv20 = lv20.offset(arg2.rotate(Direction.SOUTH), 0);
                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n, m + 1)) {
                        BlockPos lv21 = lv20.offset(arg2.rotate(Direction.SOUTH), 6);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", lv21, arg2.rotate(BlockRotation.CLOCKWISE_90)));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n - 1, m + 1)) {
                        BlockPos lv22 = lv20.offset(arg2.rotate(Direction.SOUTH), 8);
                        lv22 = lv22.offset(arg2.rotate(Direction.WEST), 3);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", lv22, arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n, m - 1)) {
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_corner", lv20, arg2.rotate(BlockRotation.CLOCKWISE_180)));
                     } else if (WoodlandMansionGenerator.MansionParameters.method_15047(arg3, n - 1, m - 1)) {
                        BlockPos lv23 = lv20.offset(arg2.rotate(Direction.SOUTH), 1);
                        list.add(new WoodlandMansionGenerator.Piece(this.manager, "roof_inner_corner", lv23, arg2.rotate(BlockRotation.CLOCKWISE_180)));
                     }
                  }
               }
            }
         }
      }

      private void addEntrance(List<WoodlandMansionGenerator.Piece> list, WoodlandMansionGenerator.GenerationPiece arg) {
         Direction lv = arg.rotation.rotate(Direction.WEST);
         list.add(new WoodlandMansionGenerator.Piece(this.manager, "entrance", arg.position.offset(lv, 9), arg.rotation));
         arg.position = arg.position.offset(arg.rotation.rotate(Direction.SOUTH), 16);
      }

      private void method_15052(List<WoodlandMansionGenerator.Piece> list, WoodlandMansionGenerator.GenerationPiece arg) {
         list.add(new WoodlandMansionGenerator.Piece(this.manager, arg.template, arg.position.offset(arg.rotation.rotate(Direction.EAST), 7), arg.rotation));
         arg.position = arg.position.offset(arg.rotation.rotate(Direction.SOUTH), 8);
      }

      private void method_15058(List<WoodlandMansionGenerator.Piece> list, WoodlandMansionGenerator.GenerationPiece arg) {
         arg.position = arg.position.offset(arg.rotation.rotate(Direction.SOUTH), -1);
         list.add(new WoodlandMansionGenerator.Piece(this.manager, "wall_corner", arg.position, arg.rotation));
         arg.position = arg.position.offset(arg.rotation.rotate(Direction.SOUTH), -7);
         arg.position = arg.position.offset(arg.rotation.rotate(Direction.WEST), -6);
         arg.rotation = arg.rotation.rotate(BlockRotation.CLOCKWISE_90);
      }

      private void method_15060(List<WoodlandMansionGenerator.Piece> list, WoodlandMansionGenerator.GenerationPiece arg) {
         arg.position = arg.position.offset(arg.rotation.rotate(Direction.SOUTH), 6);
         arg.position = arg.position.offset(arg.rotation.rotate(Direction.EAST), 8);
         arg.rotation = arg.rotation.rotate(BlockRotation.COUNTERCLOCKWISE_90);
      }

      private void addSmallRoom(
         List<WoodlandMansionGenerator.Piece> list, BlockPos arg, BlockRotation arg2, Direction arg3, WoodlandMansionGenerator.RoomPool arg4
      ) {
         BlockRotation lv = BlockRotation.NONE;
         String string = arg4.getSmallRoom(this.random);
         if (arg3 != Direction.EAST) {
            if (arg3 == Direction.NORTH) {
               lv = lv.rotate(BlockRotation.COUNTERCLOCKWISE_90);
            } else if (arg3 == Direction.WEST) {
               lv = lv.rotate(BlockRotation.CLOCKWISE_180);
            } else if (arg3 == Direction.SOUTH) {
               lv = lv.rotate(BlockRotation.CLOCKWISE_90);
            } else {
               string = arg4.getSmallSecretRoom(this.random);
            }
         }

         BlockPos lv2 = Structure.applyTransformedOffset(new BlockPos(1, 0, 0), BlockMirror.NONE, lv, 7, 7);
         lv = lv.rotate(arg2);
         lv2 = lv2.rotate(arg2);
         BlockPos lv3 = arg.add(lv2.getX(), 0, lv2.getZ());
         list.add(new WoodlandMansionGenerator.Piece(this.manager, string, lv3, lv));
      }

      private void addMediumRoom(
         List<WoodlandMansionGenerator.Piece> list,
         BlockPos arg,
         BlockRotation arg2,
         Direction arg3,
         Direction arg4,
         WoodlandMansionGenerator.RoomPool arg5,
         boolean staircase
      ) {
         if (arg4 == Direction.EAST && arg3 == Direction.SOUTH) {
            BlockPos lv = arg.offset(arg2.rotate(Direction.EAST), 1);
            list.add(new WoodlandMansionGenerator.Piece(this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv, arg2));
         } else if (arg4 == Direction.EAST && arg3 == Direction.NORTH) {
            BlockPos lv2 = arg.offset(arg2.rotate(Direction.EAST), 1);
            lv2 = lv2.offset(arg2.rotate(Direction.SOUTH), 6);
            list.add(new WoodlandMansionGenerator.Piece(this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv2, arg2, BlockMirror.LEFT_RIGHT));
         } else if (arg4 == Direction.WEST && arg3 == Direction.NORTH) {
            BlockPos lv3 = arg.offset(arg2.rotate(Direction.EAST), 7);
            lv3 = lv3.offset(arg2.rotate(Direction.SOUTH), 6);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv3, arg2.rotate(BlockRotation.CLOCKWISE_180)
               )
            );
         } else if (arg4 == Direction.WEST && arg3 == Direction.SOUTH) {
            BlockPos lv4 = arg.offset(arg2.rotate(Direction.EAST), 7);
            list.add(new WoodlandMansionGenerator.Piece(this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv4, arg2, BlockMirror.FRONT_BACK));
         } else if (arg4 == Direction.SOUTH && arg3 == Direction.EAST) {
            BlockPos lv5 = arg.offset(arg2.rotate(Direction.EAST), 1);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv5, arg2.rotate(BlockRotation.CLOCKWISE_90), BlockMirror.LEFT_RIGHT
               )
            );
         } else if (arg4 == Direction.SOUTH && arg3 == Direction.WEST) {
            BlockPos lv6 = arg.offset(arg2.rotate(Direction.EAST), 7);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv6, arg2.rotate(BlockRotation.CLOCKWISE_90)
               )
            );
         } else if (arg4 == Direction.NORTH && arg3 == Direction.WEST) {
            BlockPos lv7 = arg.offset(arg2.rotate(Direction.EAST), 7);
            lv7 = lv7.offset(arg2.rotate(Direction.SOUTH), 6);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv7, arg2.rotate(BlockRotation.CLOCKWISE_90), BlockMirror.FRONT_BACK
               )
            );
         } else if (arg4 == Direction.NORTH && arg3 == Direction.EAST) {
            BlockPos lv8 = arg.offset(arg2.rotate(Direction.EAST), 1);
            lv8 = lv8.offset(arg2.rotate(Direction.SOUTH), 6);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumFunctionalRoom(this.random, staircase), lv8, arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (arg4 == Direction.SOUTH && arg3 == Direction.NORTH) {
            BlockPos lv9 = arg.offset(arg2.rotate(Direction.EAST), 1);
            lv9 = lv9.offset(arg2.rotate(Direction.NORTH), 8);
            list.add(new WoodlandMansionGenerator.Piece(this.manager, arg5.getMediumGenericRoom(this.random, staircase), lv9, arg2));
         } else if (arg4 == Direction.NORTH && arg3 == Direction.SOUTH) {
            BlockPos lv10 = arg.offset(arg2.rotate(Direction.EAST), 7);
            lv10 = lv10.offset(arg2.rotate(Direction.SOUTH), 14);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumGenericRoom(this.random, staircase), lv10, arg2.rotate(BlockRotation.CLOCKWISE_180)
               )
            );
         } else if (arg4 == Direction.WEST && arg3 == Direction.EAST) {
            BlockPos lv11 = arg.offset(arg2.rotate(Direction.EAST), 15);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumGenericRoom(this.random, staircase), lv11, arg2.rotate(BlockRotation.CLOCKWISE_90)
               )
            );
         } else if (arg4 == Direction.EAST && arg3 == Direction.WEST) {
            BlockPos lv12 = arg.offset(arg2.rotate(Direction.WEST), 7);
            lv12 = lv12.offset(arg2.rotate(Direction.SOUTH), 6);
            list.add(
               new WoodlandMansionGenerator.Piece(
                  this.manager, arg5.getMediumGenericRoom(this.random, staircase), lv12, arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (arg4 == Direction.UP && arg3 == Direction.EAST) {
            BlockPos lv13 = arg.offset(arg2.rotate(Direction.EAST), 15);
            list.add(new WoodlandMansionGenerator.Piece(this.manager, arg5.getMediumSecretRoom(this.random), lv13, arg2.rotate(BlockRotation.CLOCKWISE_90)));
         } else if (arg4 == Direction.UP && arg3 == Direction.SOUTH) {
            BlockPos lv14 = arg.offset(arg2.rotate(Direction.EAST), 1);
            lv14 = lv14.offset(arg2.rotate(Direction.NORTH), 0);
            list.add(new WoodlandMansionGenerator.Piece(this.manager, arg5.getMediumSecretRoom(this.random), lv14, arg2));
         }
      }

      private void addBigRoom(
         List<WoodlandMansionGenerator.Piece> list, BlockPos arg, BlockRotation arg2, Direction arg3, Direction arg4, WoodlandMansionGenerator.RoomPool arg5
      ) {
         int i = 0;
         int j = 0;
         BlockRotation lv = arg2;
         BlockMirror lv2 = BlockMirror.NONE;
         if (arg4 == Direction.EAST && arg3 == Direction.SOUTH) {
            i = -7;
         } else if (arg4 == Direction.EAST && arg3 == Direction.NORTH) {
            i = -7;
            j = 6;
            lv2 = BlockMirror.LEFT_RIGHT;
         } else if (arg4 == Direction.NORTH && arg3 == Direction.EAST) {
            i = 1;
            j = 14;
            lv = arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90);
         } else if (arg4 == Direction.NORTH && arg3 == Direction.WEST) {
            i = 7;
            j = 14;
            lv = arg2.rotate(BlockRotation.COUNTERCLOCKWISE_90);
            lv2 = BlockMirror.LEFT_RIGHT;
         } else if (arg4 == Direction.SOUTH && arg3 == Direction.WEST) {
            i = 7;
            j = -8;
            lv = arg2.rotate(BlockRotation.CLOCKWISE_90);
         } else if (arg4 == Direction.SOUTH && arg3 == Direction.EAST) {
            i = 1;
            j = -8;
            lv = arg2.rotate(BlockRotation.CLOCKWISE_90);
            lv2 = BlockMirror.LEFT_RIGHT;
         } else if (arg4 == Direction.WEST && arg3 == Direction.NORTH) {
            i = 15;
            j = 6;
            lv = arg2.rotate(BlockRotation.CLOCKWISE_180);
         } else if (arg4 == Direction.WEST && arg3 == Direction.SOUTH) {
            i = 15;
            lv2 = BlockMirror.FRONT_BACK;
         }

         BlockPos lv3 = arg.offset(arg2.rotate(Direction.EAST), i);
         lv3 = lv3.offset(arg2.rotate(Direction.SOUTH), j);
         list.add(new WoodlandMansionGenerator.Piece(this.manager, arg5.getBigRoom(this.random), lv3, lv, lv2));
      }

      private void addBigSecretRoom(List<WoodlandMansionGenerator.Piece> list, BlockPos arg, BlockRotation arg2, WoodlandMansionGenerator.RoomPool arg3) {
         BlockPos lv = arg.offset(arg2.rotate(Direction.EAST), 1);
         list.add(new WoodlandMansionGenerator.Piece(this.manager, arg3.getBigSecretRoom(this.random), lv, arg2, BlockMirror.NONE));
      }
   }

   static class MansionParameters {
      private final Random random;
      private final WoodlandMansionGenerator.FlagMatrix field_15440;
      private final WoodlandMansionGenerator.FlagMatrix field_15439;
      private final WoodlandMansionGenerator.FlagMatrix[] field_15443;
      private final int field_15442;
      private final int field_15441;

      public MansionParameters(Random random) {
         this.random = random;
         int i = 11;
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

      public static boolean method_15047(WoodlandMansionGenerator.FlagMatrix arg, int i, int j) {
         int k = arg.get(i, j);
         return k == 1 || k == 2 || k == 3 || k == 4;
      }

      public boolean method_15039(WoodlandMansionGenerator.FlagMatrix arg, int i, int j, int k, int l) {
         return (this.field_15443[k].get(i, j) & 65535) == l;
      }

      @Nullable
      public Direction method_15040(WoodlandMansionGenerator.FlagMatrix arg, int i, int j, int k, int l) {
         for (Direction lv : Direction.Type.HORIZONTAL) {
            if (this.method_15039(arg, i + lv.getOffsetX(), j + lv.getOffsetZ(), k, l)) {
               return lv;
            }
         }

         return null;
      }

      private void method_15045(WoodlandMansionGenerator.FlagMatrix arg, int i, int j, Direction arg2, int k) {
         if (k > 0) {
            arg.set(i, j, 1);
            arg.update(i + arg2.getOffsetX(), j + arg2.getOffsetZ(), 0, 1);

            for (int l = 0; l < 8; l++) {
               Direction lv = Direction.fromHorizontal(this.random.nextInt(4));
               if (lv != arg2.getOpposite() && (lv != Direction.EAST || !this.random.nextBoolean())) {
                  int m = i + arg2.getOffsetX();
                  int n = j + arg2.getOffsetZ();
                  if (arg.get(m + lv.getOffsetX(), n + lv.getOffsetZ()) == 0 && arg.get(m + lv.getOffsetX() * 2, n + lv.getOffsetZ() * 2) == 0) {
                     this.method_15045(arg, i + arg2.getOffsetX() + lv.getOffsetX(), j + arg2.getOffsetZ() + lv.getOffsetZ(), lv, k - 1);
                     break;
                  }
               }
            }

            Direction lv2 = arg2.rotateYClockwise();
            Direction lv3 = arg2.rotateYCounterclockwise();
            arg.update(i + lv2.getOffsetX(), j + lv2.getOffsetZ(), 0, 2);
            arg.update(i + lv3.getOffsetX(), j + lv3.getOffsetZ(), 0, 2);
            arg.update(i + arg2.getOffsetX() + lv2.getOffsetX(), j + arg2.getOffsetZ() + lv2.getOffsetZ(), 0, 2);
            arg.update(i + arg2.getOffsetX() + lv3.getOffsetX(), j + arg2.getOffsetZ() + lv3.getOffsetZ(), 0, 2);
            arg.update(i + arg2.getOffsetX() * 2, j + arg2.getOffsetZ() * 2, 0, 2);
            arg.update(i + lv2.getOffsetX() * 2, j + lv2.getOffsetZ() * 2, 0, 2);
            arg.update(i + lv3.getOffsetX() * 2, j + lv3.getOffsetZ() * 2, 0, 2);
         }
      }

      private boolean method_15046(WoodlandMansionGenerator.FlagMatrix arg) {
         boolean bl = false;

         for (int i = 0; i < arg.m; i++) {
            for (int j = 0; j < arg.n; j++) {
               if (arg.get(j, i) == 0) {
                  int k = 0;
                  k += method_15047(arg, j + 1, i) ? 1 : 0;
                  k += method_15047(arg, j - 1, i) ? 1 : 0;
                  k += method_15047(arg, j, i + 1) ? 1 : 0;
                  k += method_15047(arg, j, i - 1) ? 1 : 0;
                  if (k >= 3) {
                     arg.set(j, i, 2);
                     bl = true;
                  } else if (k == 2) {
                     int l = 0;
                     l += method_15047(arg, j + 1, i + 1) ? 1 : 0;
                     l += method_15047(arg, j - 1, i + 1) ? 1 : 0;
                     l += method_15047(arg, j + 1, i - 1) ? 1 : 0;
                     l += method_15047(arg, j - 1, i - 1) ? 1 : 0;
                     if (l <= 1) {
                        arg.set(j, i, 2);
                        bl = true;
                     }
                  }
               }
            }
         }

         return bl;
      }

      private void method_15048() {
         List<Pair<Integer, Integer>> list = Lists.newArrayList();
         WoodlandMansionGenerator.FlagMatrix lv = this.field_15443[1];

         for (int i = 0; i < this.field_15439.m; i++) {
            for (int j = 0; j < this.field_15439.n; j++) {
               int k = lv.get(j, i);
               int l = k & 983040;
               if (l == 131072 && (k & 2097152) == 2097152) {
                  list.add(new Pair<>(j, i));
               }
            }
         }

         if (list.isEmpty()) {
            this.field_15439.fill(0, 0, this.field_15439.n, this.field_15439.m, 5);
         } else {
            Pair<Integer, Integer> lv2 = list.get(this.random.nextInt(list.size()));
            int m = lv.get(lv2.getLeft(), lv2.getRight());
            lv.set(lv2.getLeft(), lv2.getRight(), m | 4194304);
            Direction lv3 = this.method_15040(this.field_15440, lv2.getLeft(), lv2.getRight(), 1, m & 65535);
            int n = lv2.getLeft() + lv3.getOffsetX();
            int o = lv2.getRight() + lv3.getOffsetZ();

            for (int p = 0; p < this.field_15439.m; p++) {
               for (int q = 0; q < this.field_15439.n; q++) {
                  if (!method_15047(this.field_15440, q, p)) {
                     this.field_15439.set(q, p, 5);
                  } else if (q == lv2.getLeft() && p == lv2.getRight()) {
                     this.field_15439.set(q, p, 3);
                  } else if (q == n && p == o) {
                     this.field_15439.set(q, p, 3);
                     this.field_15443[2].set(q, p, 8388608);
                  }
               }
            }

            List<Direction> list2 = Lists.newArrayList();

            for (Direction lv4 : Direction.Type.HORIZONTAL) {
               if (this.field_15439.get(n + lv4.getOffsetX(), o + lv4.getOffsetZ()) == 0) {
                  list2.add(lv4);
               }
            }

            if (list2.isEmpty()) {
               this.field_15439.fill(0, 0, this.field_15439.n, this.field_15439.m, 5);
               lv.set(lv2.getLeft(), lv2.getRight(), m);
            } else {
               Direction lv5 = list2.get(this.random.nextInt(list2.size()));
               this.method_15045(this.field_15439, n + lv5.getOffsetX(), o + lv5.getOffsetZ(), lv5, 4);

               while (this.method_15046(this.field_15439)) {
               }
            }
         }
      }

      private void method_15042(WoodlandMansionGenerator.FlagMatrix arg, WoodlandMansionGenerator.FlagMatrix arg2) {
         List<Pair<Integer, Integer>> list = Lists.newArrayList();

         for (int i = 0; i < arg.m; i++) {
            for (int j = 0; j < arg.n; j++) {
               if (arg.get(j, i) == 2) {
                  list.add(new Pair<>(j, i));
               }
            }
         }

         Collections.shuffle(list, this.random);
         int k = 10;

         for (Pair<Integer, Integer> lv : list) {
            int l = lv.getLeft();
            int m = lv.getRight();
            if (arg2.get(l, m) == 0) {
               int n = l;
               int o = l;
               int p = m;
               int q = m;
               int r = 65536;
               if (arg2.get(l + 1, m) == 0
                  && arg2.get(l, m + 1) == 0
                  && arg2.get(l + 1, m + 1) == 0
                  && arg.get(l + 1, m) == 2
                  && arg.get(l, m + 1) == 2
                  && arg.get(l + 1, m + 1) == 2) {
                  o = l + 1;
                  q = m + 1;
                  r = 262144;
               } else if (arg2.get(l - 1, m) == 0
                  && arg2.get(l, m + 1) == 0
                  && arg2.get(l - 1, m + 1) == 0
                  && arg.get(l - 1, m) == 2
                  && arg.get(l, m + 1) == 2
                  && arg.get(l - 1, m + 1) == 2) {
                  n = l - 1;
                  q = m + 1;
                  r = 262144;
               } else if (arg2.get(l - 1, m) == 0
                  && arg2.get(l, m - 1) == 0
                  && arg2.get(l - 1, m - 1) == 0
                  && arg.get(l - 1, m) == 2
                  && arg.get(l, m - 1) == 2
                  && arg.get(l - 1, m - 1) == 2) {
                  n = l - 1;
                  p = m - 1;
                  r = 262144;
               } else if (arg2.get(l + 1, m) == 0 && arg.get(l + 1, m) == 2) {
                  o = l + 1;
                  r = 131072;
               } else if (arg2.get(l, m + 1) == 0 && arg.get(l, m + 1) == 2) {
                  q = m + 1;
                  r = 131072;
               } else if (arg2.get(l - 1, m) == 0 && arg.get(l - 1, m) == 2) {
                  n = l - 1;
                  r = 131072;
               } else if (arg2.get(l, m - 1) == 0 && arg.get(l, m - 1) == 2) {
                  p = m - 1;
                  r = 131072;
               }

               int s = this.random.nextBoolean() ? n : o;
               int t = this.random.nextBoolean() ? p : q;
               int u = 2097152;
               if (!arg.anyMatchAround(s, t, 1)) {
                  s = s == n ? o : n;
                  t = t == p ? q : p;
                  if (!arg.anyMatchAround(s, t, 1)) {
                     t = t == p ? q : p;
                     if (!arg.anyMatchAround(s, t, 1)) {
                        s = s == n ? o : n;
                        t = t == p ? q : p;
                        if (!arg.anyMatchAround(s, t, 1)) {
                           u = 0;
                           s = n;
                           t = p;
                        }
                     }
                  }
               }

               for (int v = p; v <= q; v++) {
                  for (int w = n; w <= o; w++) {
                     if (w == s && v == t) {
                        arg2.set(w, v, 1048576 | u | r | k);
                     } else {
                        arg2.set(w, v, r | k);
                     }
                  }
               }

               k++;
            }
         }
      }
   }

   public static class Piece extends SimpleStructurePiece {
      private final String template;
      private final BlockRotation rotation;
      private final BlockMirror mirror;

      public Piece(StructureManager arg, String string, BlockPos arg2, BlockRotation arg3) {
         this(arg, string, arg2, arg3, BlockMirror.NONE);
      }

      public Piece(StructureManager arg, String string, BlockPos arg2, BlockRotation arg3, BlockMirror arg4) {
         super(StructurePieceType.WOODLAND_MANSION, 0);
         this.template = string;
         this.pos = arg2;
         this.rotation = arg3;
         this.mirror = arg4;
         this.setupPlacement(arg);
      }

      public Piece(StructureManager arg, CompoundTag arg2) {
         super(StructurePieceType.WOODLAND_MANSION, arg2);
         this.template = arg2.getString("Template");
         this.rotation = BlockRotation.valueOf(arg2.getString("Rot"));
         this.mirror = BlockMirror.valueOf(arg2.getString("Mi"));
         this.setupPlacement(arg);
      }

      private void setupPlacement(StructureManager arg) {
         Structure lv = arg.getStructureOrBlank(new Identifier("woodland_mansion/" + this.template));
         StructurePlacementData lv2 = new StructurePlacementData()
            .setIgnoreEntities(true)
            .setRotation(this.rotation)
            .setMirror(this.mirror)
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
         this.setStructureData(lv, this.pos, lv2);
      }

      @Override
      protected void toNbt(CompoundTag tag) {
         super.toNbt(tag);
         tag.putString("Template", this.template);
         tag.putString("Rot", this.placementData.getRotation().name());
         tag.putString("Mi", this.placementData.getMirror().name());
      }

      @Override
      protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess arg2, Random random, BlockBox boundingBox) {
         if (metadata.startsWith("Chest")) {
            BlockRotation lv = this.placementData.getRotation();
            BlockState lv2 = Blocks.CHEST.getDefaultState();
            if ("ChestWest".equals(metadata)) {
               lv2 = lv2.with(ChestBlock.FACING, lv.rotate(Direction.WEST));
            } else if ("ChestEast".equals(metadata)) {
               lv2 = lv2.with(ChestBlock.FACING, lv.rotate(Direction.EAST));
            } else if ("ChestSouth".equals(metadata)) {
               lv2 = lv2.with(ChestBlock.FACING, lv.rotate(Direction.SOUTH));
            } else if ("ChestNorth".equals(metadata)) {
               lv2 = lv2.with(ChestBlock.FACING, lv.rotate(Direction.NORTH));
            }

            this.addChest(arg2, boundingBox, random, pos, LootTables.WOODLAND_MANSION_CHEST, lv2);
         } else {
            IllagerEntity lv3;
            switch (metadata) {
               case "Mage":
                  lv3 = EntityType.EVOKER.create(arg2.toServerWorld());
                  break;
               case "Warrior":
                  lv3 = EntityType.VINDICATOR.create(arg2.toServerWorld());
                  break;
               default:
                  return;
            }

            lv3.setPersistent();
            lv3.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            lv3.initialize(arg2, arg2.getLocalDifficulty(lv3.getBlockPos()), SpawnReason.STRUCTURE, null, null);
            arg2.spawnEntityAndPassengers(lv3);
            arg2.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
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
