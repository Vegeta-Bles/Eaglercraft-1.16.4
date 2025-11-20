package net.minecraft.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.EmptyBlockView;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class Structure {
   private final List<Structure.PalettedBlockInfoList> blockInfoLists = Lists.newArrayList();
   private final List<Structure.StructureEntityInfo> entities = Lists.newArrayList();
   private BlockPos size = BlockPos.ORIGIN;
   private String author = "?";

   public Structure() {
   }

   public BlockPos getSize() {
      return this.size;
   }

   public void setAuthor(String name) {
      this.author = name;
   }

   public String getAuthor() {
      return this.author;
   }

   public void saveFromWorld(World world, BlockPos start, BlockPos size, boolean includeEntities, @Nullable Block ignoredBlock) {
      if (size.getX() >= 1 && size.getY() >= 1 && size.getZ() >= 1) {
         BlockPos lv = start.add(size).add(-1, -1, -1);
         List<Structure.StructureBlockInfo> list = Lists.newArrayList();
         List<Structure.StructureBlockInfo> list2 = Lists.newArrayList();
         List<Structure.StructureBlockInfo> list3 = Lists.newArrayList();
         BlockPos lv2 = new BlockPos(Math.min(start.getX(), lv.getX()), Math.min(start.getY(), lv.getY()), Math.min(start.getZ(), lv.getZ()));
         BlockPos lv3 = new BlockPos(Math.max(start.getX(), lv.getX()), Math.max(start.getY(), lv.getY()), Math.max(start.getZ(), lv.getZ()));
         this.size = size;

         for (BlockPos lv4 : BlockPos.iterate(lv2, lv3)) {
            BlockPos lv5 = lv4.subtract(lv2);
            BlockState lv6 = world.getBlockState(lv4);
            if (ignoredBlock == null || ignoredBlock != lv6.getBlock()) {
               BlockEntity lv7 = world.getBlockEntity(lv4);
               Structure.StructureBlockInfo lv9;
               if (lv7 != null) {
                  CompoundTag lv8 = lv7.toTag(new CompoundTag());
                  lv8.remove("x");
                  lv8.remove("y");
                  lv8.remove("z");
                  lv9 = new Structure.StructureBlockInfo(lv5, lv6, lv8.copy());
               } else {
                  lv9 = new Structure.StructureBlockInfo(lv5, lv6, null);
               }

               method_28054(lv9, list, list2, list3);
            }
         }

         List<Structure.StructureBlockInfo> list4 = method_28055(list, list2, list3);
         this.blockInfoLists.clear();
         this.blockInfoLists.add(new Structure.PalettedBlockInfoList(list4));
         if (includeEntities) {
            this.addEntitiesFromWorld(world, lv2, lv3.add(1, 1, 1));
         } else {
            this.entities.clear();
         }
      }
   }

   private static void method_28054(
      Structure.StructureBlockInfo arg,
      List<Structure.StructureBlockInfo> list,
      List<Structure.StructureBlockInfo> list2,
      List<Structure.StructureBlockInfo> list3
   ) {
      if (arg.tag != null) {
         list2.add(arg);
      } else if (!arg.state.getBlock().hasDynamicBounds() && arg.state.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN)) {
         list.add(arg);
      } else {
         list3.add(arg);
      }
   }

   private static List<Structure.StructureBlockInfo> method_28055(
      List<Structure.StructureBlockInfo> list, List<Structure.StructureBlockInfo> list2, List<Structure.StructureBlockInfo> list3
   ) {
      Comparator<Structure.StructureBlockInfo> comparator = Comparator.<Structure.StructureBlockInfo>comparingInt(arg -> arg.pos.getY())
         .thenComparingInt(arg -> arg.pos.getX())
         .thenComparingInt(arg -> arg.pos.getZ());
      list.sort(comparator);
      list3.sort(comparator);
      list2.sort(comparator);
      List<Structure.StructureBlockInfo> list4 = Lists.newArrayList();
      list4.addAll(list);
      list4.addAll(list3);
      list4.addAll(list2);
      return list4;
   }

   private void addEntitiesFromWorld(World world, BlockPos firstCorner, BlockPos secondCorner) {
      List<Entity> list = world.getEntitiesByClass(Entity.class, new Box(firstCorner, secondCorner), arg -> !(arg instanceof PlayerEntity));
      this.entities.clear();

      for (Entity lv : list) {
         Vec3d lv2 = new Vec3d(lv.getX() - (double)firstCorner.getX(), lv.getY() - (double)firstCorner.getY(), lv.getZ() - (double)firstCorner.getZ());
         CompoundTag lv3 = new CompoundTag();
         lv.saveToTag(lv3);
         BlockPos lv4;
         if (lv instanceof PaintingEntity) {
            lv4 = ((PaintingEntity)lv).getDecorationBlockPos().subtract(firstCorner);
         } else {
            lv4 = new BlockPos(lv2);
         }

         this.entities.add(new Structure.StructureEntityInfo(lv2, lv4, lv3.copy()));
      }
   }

   public List<Structure.StructureBlockInfo> getInfosForBlock(BlockPos pos, StructurePlacementData placementData, Block block) {
      return this.getInfosForBlock(pos, placementData, block, true);
   }

   public List<Structure.StructureBlockInfo> getInfosForBlock(BlockPos pos, StructurePlacementData placementData, Block block, boolean transformed) {
      List<Structure.StructureBlockInfo> list = Lists.newArrayList();
      BlockBox lv = placementData.getBoundingBox();
      if (this.blockInfoLists.isEmpty()) {
         return Collections.emptyList();
      } else {
         for (Structure.StructureBlockInfo lv2 : placementData.getRandomBlockInfos(this.blockInfoLists, pos).getAllOf(block)) {
            BlockPos lv3 = transformed ? transform(placementData, lv2.pos).add(pos) : lv2.pos;
            if (lv == null || lv.contains(lv3)) {
               list.add(new Structure.StructureBlockInfo(lv3, lv2.state.rotate(placementData.getRotation()), lv2.tag));
            }
         }

         return list;
      }
   }

   public BlockPos transformBox(StructurePlacementData placementData1, BlockPos pos1, StructurePlacementData placementData2, BlockPos pos2) {
      BlockPos lv = transform(placementData1, pos1);
      BlockPos lv2 = transform(placementData2, pos2);
      return lv.subtract(lv2);
   }

   public static BlockPos transform(StructurePlacementData placementData, BlockPos pos) {
      return transformAround(pos, placementData.getMirror(), placementData.getRotation(), placementData.getPosition());
   }

   public void place(ServerWorldAccess arg, BlockPos pos, StructurePlacementData placementData, Random random) {
      placementData.calculateBoundingBox();
      this.placeAndNotifyListeners(arg, pos, placementData, random);
   }

   public void placeAndNotifyListeners(ServerWorldAccess arg, BlockPos pos, StructurePlacementData data, Random random) {
      this.place(arg, pos, pos, data, random, 2);
   }

   public boolean place(ServerWorldAccess arg, BlockPos pos, BlockPos arg3, StructurePlacementData placementData, Random random, int i) {
      if (this.blockInfoLists.isEmpty()) {
         return false;
      } else {
         List<Structure.StructureBlockInfo> list = placementData.getRandomBlockInfos(this.blockInfoLists, pos).getAll();
         if ((!list.isEmpty() || !placementData.shouldIgnoreEntities() && !this.entities.isEmpty())
            && this.size.getX() >= 1
            && this.size.getY() >= 1
            && this.size.getZ() >= 1) {
            BlockBox lv = placementData.getBoundingBox();
            List<BlockPos> list2 = Lists.newArrayListWithCapacity(placementData.shouldPlaceFluids() ? list.size() : 0);
            List<Pair<BlockPos, CompoundTag>> list3 = Lists.newArrayListWithCapacity(list.size());
            int j = Integer.MAX_VALUE;
            int k = Integer.MAX_VALUE;
            int l = Integer.MAX_VALUE;
            int m = Integer.MIN_VALUE;
            int n = Integer.MIN_VALUE;
            int o = Integer.MIN_VALUE;

            for (Structure.StructureBlockInfo lv2 : process(arg, pos, arg3, placementData, list)) {
               BlockPos lv3 = lv2.pos;
               if (lv == null || lv.contains(lv3)) {
                  FluidState lv4 = placementData.shouldPlaceFluids() ? arg.getFluidState(lv3) : null;
                  BlockState lv5 = lv2.state.mirror(placementData.getMirror()).rotate(placementData.getRotation());
                  if (lv2.tag != null) {
                     BlockEntity lv6 = arg.getBlockEntity(lv3);
                     Clearable.clear(lv6);
                     arg.setBlockState(lv3, Blocks.BARRIER.getDefaultState(), 20);
                  }

                  if (arg.setBlockState(lv3, lv5, i)) {
                     j = Math.min(j, lv3.getX());
                     k = Math.min(k, lv3.getY());
                     l = Math.min(l, lv3.getZ());
                     m = Math.max(m, lv3.getX());
                     n = Math.max(n, lv3.getY());
                     o = Math.max(o, lv3.getZ());
                     list3.add(Pair.of(lv3, lv2.tag));
                     if (lv2.tag != null) {
                        BlockEntity lv7 = arg.getBlockEntity(lv3);
                        if (lv7 != null) {
                           lv2.tag.putInt("x", lv3.getX());
                           lv2.tag.putInt("y", lv3.getY());
                           lv2.tag.putInt("z", lv3.getZ());
                           if (lv7 instanceof LootableContainerBlockEntity) {
                              lv2.tag.putLong("LootTableSeed", random.nextLong());
                           }

                           lv7.fromTag(lv2.state, lv2.tag);
                           lv7.applyMirror(placementData.getMirror());
                           lv7.applyRotation(placementData.getRotation());
                        }
                     }

                     if (lv4 != null && lv5.getBlock() instanceof FluidFillable) {
                        ((FluidFillable)lv5.getBlock()).tryFillWithFluid(arg, lv3, lv5, lv4);
                        if (!lv4.isStill()) {
                           list2.add(lv3);
                        }
                     }
                  }
               }
            }

            boolean bl = true;
            Direction[] lvs = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

            while (bl && !list2.isEmpty()) {
               bl = false;
               Iterator<BlockPos> iterator = list2.iterator();

               while (iterator.hasNext()) {
                  BlockPos lv8 = iterator.next();
                  BlockPos lv9 = lv8;
                  FluidState lv10 = arg.getFluidState(lv8);

                  for (int p = 0; p < lvs.length && !lv10.isStill(); p++) {
                     BlockPos lv11 = lv9.offset(lvs[p]);
                     FluidState lv12 = arg.getFluidState(lv11);
                     if (lv12.getHeight(arg, lv11) > lv10.getHeight(arg, lv9) || lv12.isStill() && !lv10.isStill()) {
                        lv10 = lv12;
                        lv9 = lv11;
                     }
                  }

                  if (lv10.isStill()) {
                     BlockState lv13 = arg.getBlockState(lv8);
                     Block lv14 = lv13.getBlock();
                     if (lv14 instanceof FluidFillable) {
                        ((FluidFillable)lv14).tryFillWithFluid(arg, lv8, lv13, lv10);
                        bl = true;
                        iterator.remove();
                     }
                  }
               }
            }

            if (j <= m) {
               if (!placementData.shouldUpdateNeighbors()) {
                  VoxelSet lv15 = new BitSetVoxelSet(m - j + 1, n - k + 1, o - l + 1);
                  int q = j;
                  int r = k;
                  int s = l;

                  for (Pair<BlockPos, CompoundTag> pair : list3) {
                     BlockPos lv16 = (BlockPos)pair.getFirst();
                     lv15.set(lv16.getX() - q, lv16.getY() - r, lv16.getZ() - s, true, true);
                  }

                  updateCorner(arg, i, lv15, q, r, s);
               }

               for (Pair<BlockPos, CompoundTag> pair2 : list3) {
                  BlockPos lv17 = (BlockPos)pair2.getFirst();
                  if (!placementData.shouldUpdateNeighbors()) {
                     BlockState lv18 = arg.getBlockState(lv17);
                     BlockState lv19 = Block.postProcessState(lv18, arg, lv17);
                     if (lv18 != lv19) {
                        arg.setBlockState(lv17, lv19, i & -2 | 16);
                     }

                     arg.updateNeighbors(lv17, lv19.getBlock());
                  }

                  if (pair2.getSecond() != null) {
                     BlockEntity lv20 = arg.getBlockEntity(lv17);
                     if (lv20 != null) {
                        lv20.markDirty();
                     }
                  }
               }
            }

            if (!placementData.shouldIgnoreEntities()) {
               this.spawnEntities(
                  arg, pos, placementData.getMirror(), placementData.getRotation(), placementData.getPosition(), lv, placementData.method_27265()
               );
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static void updateCorner(WorldAccess world, int flags, VoxelSet arg2, int startX, int startY, int startZ) {
      arg2.forEachDirection((arg2x, m, n, o) -> {
         BlockPos lv = new BlockPos(startX + m, startY + n, startZ + o);
         BlockPos lv2 = lv.offset(arg2x);
         BlockState lv3 = world.getBlockState(lv);
         BlockState lv4 = world.getBlockState(lv2);
         BlockState lv5 = lv3.getStateForNeighborUpdate(arg2x, lv4, world, lv, lv2);
         if (lv3 != lv5) {
            world.setBlockState(lv, lv5, flags & -2);
         }

         BlockState lv6 = lv4.getStateForNeighborUpdate(arg2x.getOpposite(), lv5, world, lv2, lv);
         if (lv4 != lv6) {
            world.setBlockState(lv2, lv6, flags & -2);
         }
      });
   }

   public static List<Structure.StructureBlockInfo> process(
      WorldAccess world, BlockPos pos, BlockPos arg3, StructurePlacementData arg4, List<Structure.StructureBlockInfo> list
   ) {
      List<Structure.StructureBlockInfo> list2 = Lists.newArrayList();

      for (Structure.StructureBlockInfo lv : list) {
         BlockPos lv2 = transform(arg4, lv.pos).add(pos);
         Structure.StructureBlockInfo lv3 = new Structure.StructureBlockInfo(lv2, lv.state, lv.tag != null ? lv.tag.copy() : null);
         Iterator<StructureProcessor> iterator = arg4.getProcessors().iterator();

         while (lv3 != null && iterator.hasNext()) {
            lv3 = iterator.next().process(world, pos, arg3, lv, lv3, arg4);
         }

         if (lv3 != null) {
            list2.add(lv3);
         }
      }

      return list2;
   }

   private void spawnEntities(ServerWorldAccess arg, BlockPos pos, BlockMirror arg3, BlockRotation arg4, BlockPos pivot, @Nullable BlockBox area, boolean bl) {
      for (Structure.StructureEntityInfo lv : this.entities) {
         BlockPos lv2 = transformAround(lv.blockPos, arg3, arg4, pivot).add(pos);
         if (area == null || area.contains(lv2)) {
            CompoundTag lv3 = lv.tag.copy();
            Vec3d lv4 = transformAround(lv.pos, arg3, arg4, pivot);
            Vec3d lv5 = lv4.add((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            ListTag lv6 = new ListTag();
            lv6.add(DoubleTag.of(lv5.x));
            lv6.add(DoubleTag.of(lv5.y));
            lv6.add(DoubleTag.of(lv5.z));
            lv3.put("Pos", lv6);
            lv3.remove("UUID");
            getEntity(arg, lv3).ifPresent(arg6 -> {
               float f = arg6.applyMirror(arg3);
               f += arg6.yaw - arg6.applyRotation(arg4);
               arg6.refreshPositionAndAngles(lv5.x, lv5.y, lv5.z, f, arg6.pitch);
               if (bl && arg6 instanceof MobEntity) {
                  ((MobEntity)arg6).initialize(arg, arg.getLocalDifficulty(new BlockPos(lv5)), SpawnReason.STRUCTURE, null, lv3);
               }

               arg.spawnEntityAndPassengers(arg6);
            });
         }
      }
   }

   private static Optional<Entity> getEntity(ServerWorldAccess arg, CompoundTag arg2) {
      try {
         return EntityType.getEntityFromTag(arg2, arg.toServerWorld());
      } catch (Exception var3) {
         return Optional.empty();
      }
   }

   public BlockPos getRotatedSize(BlockRotation arg) {
      switch (arg) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            return new BlockPos(this.size.getZ(), this.size.getY(), this.size.getX());
         default:
            return this.size;
      }
   }

   public static BlockPos transformAround(BlockPos pos, BlockMirror arg2, BlockRotation arg3, BlockPos pivot) {
      int i = pos.getX();
      int j = pos.getY();
      int k = pos.getZ();
      boolean bl = true;
      switch (arg2) {
         case LEFT_RIGHT:
            k = -k;
            break;
         case FRONT_BACK:
            i = -i;
            break;
         default:
            bl = false;
      }

      int l = pivot.getX();
      int m = pivot.getZ();
      switch (arg3) {
         case COUNTERCLOCKWISE_90:
            return new BlockPos(l - m + k, j, l + m - i);
         case CLOCKWISE_90:
            return new BlockPos(l + m - k, j, m - l + i);
         case CLOCKWISE_180:
            return new BlockPos(l + l - i, j, m + m - k);
         default:
            return bl ? new BlockPos(i, j, k) : pos;
      }
   }

   public static Vec3d transformAround(Vec3d point, BlockMirror arg2, BlockRotation arg3, BlockPos pivot) {
      double d = point.x;
      double e = point.y;
      double f = point.z;
      boolean bl = true;
      switch (arg2) {
         case LEFT_RIGHT:
            f = 1.0 - f;
            break;
         case FRONT_BACK:
            d = 1.0 - d;
            break;
         default:
            bl = false;
      }

      int i = pivot.getX();
      int j = pivot.getZ();
      switch (arg3) {
         case COUNTERCLOCKWISE_90:
            return new Vec3d((double)(i - j) + f, e, (double)(i + j + 1) - d);
         case CLOCKWISE_90:
            return new Vec3d((double)(i + j + 1) - f, e, (double)(j - i) + d);
         case CLOCKWISE_180:
            return new Vec3d((double)(i + i + 1) - d, e, (double)(j + j + 1) - f);
         default:
            return bl ? new Vec3d(d, e, f) : point;
      }
   }

   public BlockPos offsetByTransformedSize(BlockPos arg, BlockMirror arg2, BlockRotation arg3) {
      return applyTransformedOffset(arg, arg2, arg3, this.getSize().getX(), this.getSize().getZ());
   }

   public static BlockPos applyTransformedOffset(BlockPos arg, BlockMirror arg2, BlockRotation arg3, int offsetX, int offsetZ) {
      offsetX--;
      offsetZ--;
      int k = arg2 == BlockMirror.FRONT_BACK ? offsetX : 0;
      int l = arg2 == BlockMirror.LEFT_RIGHT ? offsetZ : 0;
      BlockPos lv = arg;
      switch (arg3) {
         case COUNTERCLOCKWISE_90:
            lv = arg.add(l, 0, offsetX - k);
            break;
         case CLOCKWISE_90:
            lv = arg.add(offsetZ - l, 0, k);
            break;
         case CLOCKWISE_180:
            lv = arg.add(offsetX - k, 0, offsetZ - l);
            break;
         case NONE:
            lv = arg.add(k, 0, l);
      }

      return lv;
   }

   public BlockBox calculateBoundingBox(StructurePlacementData arg, BlockPos pos) {
      return this.method_27267(pos, arg.getRotation(), arg.getPosition(), arg.getMirror());
   }

   public BlockBox method_27267(BlockPos arg, BlockRotation arg2, BlockPos arg3, BlockMirror arg4) {
      BlockPos lv = this.getRotatedSize(arg2);
      int i = arg3.getX();
      int j = arg3.getZ();
      int k = lv.getX() - 1;
      int l = lv.getY() - 1;
      int m = lv.getZ() - 1;
      BlockBox lv2 = new BlockBox(0, 0, 0, 0, 0, 0);
      switch (arg2) {
         case COUNTERCLOCKWISE_90:
            lv2 = new BlockBox(i - j, 0, i + j - m, i - j + k, l, i + j);
            break;
         case CLOCKWISE_90:
            lv2 = new BlockBox(i + j - k, 0, j - i, i + j, l, j - i + m);
            break;
         case CLOCKWISE_180:
            lv2 = new BlockBox(i + i - k, 0, j + j - m, i + i, l, j + j);
            break;
         case NONE:
            lv2 = new BlockBox(0, 0, 0, k, l, m);
      }

      switch (arg4) {
         case LEFT_RIGHT:
            this.mirrorBoundingBox(arg2, m, k, lv2, Direction.NORTH, Direction.SOUTH);
            break;
         case FRONT_BACK:
            this.mirrorBoundingBox(arg2, k, m, lv2, Direction.WEST, Direction.EAST);
         case NONE:
      }

      lv2.move(arg.getX(), arg.getY(), arg.getZ());
      return lv2;
   }

   private void mirrorBoundingBox(BlockRotation rotation, int offsetX, int offsetZ, BlockBox boundingBox, Direction arg3, Direction arg4) {
      BlockPos lv = BlockPos.ORIGIN;
      if (rotation == BlockRotation.CLOCKWISE_90 || rotation == BlockRotation.COUNTERCLOCKWISE_90) {
         lv = lv.offset(rotation.rotate(arg3), offsetZ);
      } else if (rotation == BlockRotation.CLOCKWISE_180) {
         lv = lv.offset(arg4, offsetX);
      } else {
         lv = lv.offset(arg3, offsetX);
      }

      boundingBox.move(lv.getX(), 0, lv.getZ());
   }

   public CompoundTag toTag(CompoundTag tag) {
      if (this.blockInfoLists.isEmpty()) {
         tag.put("blocks", new ListTag());
         tag.put("palette", new ListTag());
      } else {
         List<Structure.Palette> list = Lists.newArrayList();
         Structure.Palette lv = new Structure.Palette();
         list.add(lv);

         for (int i = 1; i < this.blockInfoLists.size(); i++) {
            list.add(new Structure.Palette());
         }

         ListTag lv2 = new ListTag();
         List<Structure.StructureBlockInfo> list2 = this.blockInfoLists.get(0).getAll();

         for (int j = 0; j < list2.size(); j++) {
            Structure.StructureBlockInfo lv3 = list2.get(j);
            CompoundTag lv4 = new CompoundTag();
            lv4.put("pos", this.createIntListTag(lv3.pos.getX(), lv3.pos.getY(), lv3.pos.getZ()));
            int k = lv.getId(lv3.state);
            lv4.putInt("state", k);
            if (lv3.tag != null) {
               lv4.put("nbt", lv3.tag);
            }

            lv2.add(lv4);

            for (int l = 1; l < this.blockInfoLists.size(); l++) {
               Structure.Palette lv5 = list.get(l);
               lv5.set(this.blockInfoLists.get(l).getAll().get(j).state, k);
            }
         }

         tag.put("blocks", lv2);
         if (list.size() == 1) {
            ListTag lv6 = new ListTag();

            for (BlockState lv7 : lv) {
               lv6.add(NbtHelper.fromBlockState(lv7));
            }

            tag.put("palette", lv6);
         } else {
            ListTag lv8 = new ListTag();

            for (Structure.Palette lv9 : list) {
               ListTag lv10 = new ListTag();

               for (BlockState lv11 : lv9) {
                  lv10.add(NbtHelper.fromBlockState(lv11));
               }

               lv8.add(lv10);
            }

            tag.put("palettes", lv8);
         }
      }

      ListTag lv12 = new ListTag();

      for (Structure.StructureEntityInfo lv13 : this.entities) {
         CompoundTag lv14 = new CompoundTag();
         lv14.put("pos", this.createDoubleListTag(lv13.pos.x, lv13.pos.y, lv13.pos.z));
         lv14.put("blockPos", this.createIntListTag(lv13.blockPos.getX(), lv13.blockPos.getY(), lv13.blockPos.getZ()));
         if (lv13.tag != null) {
            lv14.put("nbt", lv13.tag);
         }

         lv12.add(lv14);
      }

      tag.put("entities", lv12);
      tag.put("size", this.createIntListTag(this.size.getX(), this.size.getY(), this.size.getZ()));
      tag.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      return tag;
   }

   public void fromTag(CompoundTag tag) {
      this.blockInfoLists.clear();
      this.entities.clear();
      ListTag lv = tag.getList("size", 3);
      this.size = new BlockPos(lv.getInt(0), lv.getInt(1), lv.getInt(2));
      ListTag lv2 = tag.getList("blocks", 10);
      if (tag.contains("palettes", 9)) {
         ListTag lv3 = tag.getList("palettes", 9);

         for (int i = 0; i < lv3.size(); i++) {
            this.loadPalettedBlockInfo(lv3.getList(i), lv2);
         }
      } else {
         this.loadPalettedBlockInfo(tag.getList("palette", 10), lv2);
      }

      ListTag lv4 = tag.getList("entities", 10);

      for (int j = 0; j < lv4.size(); j++) {
         CompoundTag lv5 = lv4.getCompound(j);
         ListTag lv6 = lv5.getList("pos", 6);
         Vec3d lv7 = new Vec3d(lv6.getDouble(0), lv6.getDouble(1), lv6.getDouble(2));
         ListTag lv8 = lv5.getList("blockPos", 3);
         BlockPos lv9 = new BlockPos(lv8.getInt(0), lv8.getInt(1), lv8.getInt(2));
         if (lv5.contains("nbt")) {
            CompoundTag lv10 = lv5.getCompound("nbt");
            this.entities.add(new Structure.StructureEntityInfo(lv7, lv9, lv10));
         }
      }
   }

   private void loadPalettedBlockInfo(ListTag paletteTag, ListTag blocksTag) {
      Structure.Palette lv = new Structure.Palette();

      for (int i = 0; i < paletteTag.size(); i++) {
         lv.set(NbtHelper.toBlockState(paletteTag.getCompound(i)), i);
      }

      List<Structure.StructureBlockInfo> list = Lists.newArrayList();
      List<Structure.StructureBlockInfo> list2 = Lists.newArrayList();
      List<Structure.StructureBlockInfo> list3 = Lists.newArrayList();

      for (int j = 0; j < blocksTag.size(); j++) {
         CompoundTag lv2 = blocksTag.getCompound(j);
         ListTag lv3 = lv2.getList("pos", 3);
         BlockPos lv4 = new BlockPos(lv3.getInt(0), lv3.getInt(1), lv3.getInt(2));
         BlockState lv5 = lv.getState(lv2.getInt("state"));
         CompoundTag lv6;
         if (lv2.contains("nbt")) {
            lv6 = lv2.getCompound("nbt");
         } else {
            lv6 = null;
         }

         Structure.StructureBlockInfo lv8 = new Structure.StructureBlockInfo(lv4, lv5, lv6);
         method_28054(lv8, list, list2, list3);
      }

      List<Structure.StructureBlockInfo> list4 = method_28055(list, list2, list3);
      this.blockInfoLists.add(new Structure.PalettedBlockInfoList(list4));
   }

   private ListTag createIntListTag(int... is) {
      ListTag lv = new ListTag();

      for (int i : is) {
         lv.add(IntTag.of(i));
      }

      return lv;
   }

   private ListTag createDoubleListTag(double... ds) {
      ListTag lv = new ListTag();

      for (double d : ds) {
         lv.add(DoubleTag.of(d));
      }

      return lv;
   }

   static class Palette implements Iterable<BlockState> {
      public static final BlockState AIR = Blocks.AIR.getDefaultState();
      private final IdList<BlockState> ids = new IdList<>(16);
      private int currentIndex;

      private Palette() {
      }

      public int getId(BlockState state) {
         int i = this.ids.getRawId(state);
         if (i == -1) {
            i = this.currentIndex++;
            this.ids.set(state, i);
         }

         return i;
      }

      @Nullable
      public BlockState getState(int id) {
         BlockState lv = this.ids.get(id);
         return lv == null ? AIR : lv;
      }

      @Override
      public Iterator<BlockState> iterator() {
         return this.ids.iterator();
      }

      public void set(BlockState state, int id) {
         this.ids.set(state, id);
      }
   }

   public static final class PalettedBlockInfoList {
      private final List<Structure.StructureBlockInfo> infos;
      private final Map<Block, List<Structure.StructureBlockInfo>> blockToInfos = Maps.newHashMap();

      private PalettedBlockInfoList(List<Structure.StructureBlockInfo> infos) {
         this.infos = infos;
      }

      public List<Structure.StructureBlockInfo> getAll() {
         return this.infos;
      }

      public List<Structure.StructureBlockInfo> getAllOf(Block block) {
         return this.blockToInfos.computeIfAbsent(block, arg -> this.infos.stream().filter(arg2 -> arg2.state.isOf(arg)).collect(Collectors.toList()));
      }
   }

   public static class StructureBlockInfo {
      public final BlockPos pos;
      public final BlockState state;
      public final CompoundTag tag;

      public StructureBlockInfo(BlockPos pos, BlockState state, @Nullable CompoundTag tag) {
         this.pos = pos;
         this.state = state;
         this.tag = tag;
      }

      @Override
      public String toString() {
         return String.format("<StructureBlockInfo | %s | %s | %s>", this.pos, this.state, this.tag);
      }
   }

   public static class StructureEntityInfo {
      public final Vec3d pos;
      public final BlockPos blockPos;
      public final CompoundTag tag;

      public StructureEntityInfo(Vec3d pos, BlockPos blockPos, CompoundTag tag) {
         this.pos = pos;
         this.blockPos = blockPos;
         this.tag = tag;
      }
   }
}
