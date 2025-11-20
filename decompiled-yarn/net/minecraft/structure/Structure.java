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
         BlockPos _snowman = start.add(size).add(-1, -1, -1);
         List<Structure.StructureBlockInfo> _snowmanx = Lists.newArrayList();
         List<Structure.StructureBlockInfo> _snowmanxx = Lists.newArrayList();
         List<Structure.StructureBlockInfo> _snowmanxxx = Lists.newArrayList();
         BlockPos _snowmanxxxx = new BlockPos(Math.min(start.getX(), _snowman.getX()), Math.min(start.getY(), _snowman.getY()), Math.min(start.getZ(), _snowman.getZ()));
         BlockPos _snowmanxxxxx = new BlockPos(Math.max(start.getX(), _snowman.getX()), Math.max(start.getY(), _snowman.getY()), Math.max(start.getZ(), _snowman.getZ()));
         this.size = size;

         for (BlockPos _snowmanxxxxxx : BlockPos.iterate(_snowmanxxxx, _snowmanxxxxx)) {
            BlockPos _snowmanxxxxxxx = _snowmanxxxxxx.subtract(_snowmanxxxx);
            BlockState _snowmanxxxxxxxx = world.getBlockState(_snowmanxxxxxx);
            if (ignoredBlock == null || ignoredBlock != _snowmanxxxxxxxx.getBlock()) {
               BlockEntity _snowmanxxxxxxxxx = world.getBlockEntity(_snowmanxxxxxx);
               Structure.StructureBlockInfo _snowmanxxxxxxxxxx;
               if (_snowmanxxxxxxxxx != null) {
                  CompoundTag _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.toTag(new CompoundTag());
                  _snowmanxxxxxxxxxxx.remove("x");
                  _snowmanxxxxxxxxxxx.remove("y");
                  _snowmanxxxxxxxxxxx.remove("z");
                  _snowmanxxxxxxxxxx = new Structure.StructureBlockInfo(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx.copy());
               } else {
                  _snowmanxxxxxxxxxx = new Structure.StructureBlockInfo(_snowmanxxxxxxx, _snowmanxxxxxxxx, null);
               }

               method_28054(_snowmanxxxxxxxxxx, _snowmanx, _snowmanxx, _snowmanxxx);
            }
         }

         List<Structure.StructureBlockInfo> _snowmanxxxxxxx = method_28055(_snowmanx, _snowmanxx, _snowmanxxx);
         this.blockInfoLists.clear();
         this.blockInfoLists.add(new Structure.PalettedBlockInfoList(_snowmanxxxxxxx));
         if (includeEntities) {
            this.addEntitiesFromWorld(world, _snowmanxxxx, _snowmanxxxxx.add(1, 1, 1));
         } else {
            this.entities.clear();
         }
      }
   }

   private static void method_28054(
      Structure.StructureBlockInfo blockInfo,
      List<Structure.StructureBlockInfo> jigsawBlocks,
      List<Structure.StructureBlockInfo> fullCubeBlocks,
      List<Structure.StructureBlockInfo> nonFullCubeBlocks
   ) {
      if (blockInfo.tag != null) {
         jigsawBlocks.add(blockInfo);
      } else if (!blockInfo.state.getBlock().hasDynamicBounds() && blockInfo.state.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN)) {
         fullCubeBlocks.add(blockInfo);
      } else {
         nonFullCubeBlocks.add(blockInfo);
      }
   }

   private static List<Structure.StructureBlockInfo> method_28055(
      List<Structure.StructureBlockInfo> jigsawBlocks,
      List<Structure.StructureBlockInfo> fullCubeBlocks,
      List<Structure.StructureBlockInfo> nonFullCubeBlocks
   ) {
      Comparator<Structure.StructureBlockInfo> comparator = Comparator.<Structure.StructureBlockInfo>comparingInt(info -> info.pos.getY())
         .thenComparingInt(info -> info.pos.getX())
         .thenComparingInt(info -> info.pos.getZ());
      jigsawBlocks.sort(comparator);
      fullCubeBlocks.sort(comparator);
      nonFullCubeBlocks.sort(comparator);
      List<Structure.StructureBlockInfo> combined = Lists.newArrayList();
      combined.addAll(jigsawBlocks);
      combined.addAll(fullCubeBlocks);
      combined.addAll(nonFullCubeBlocks);
      return combined;
   }

   private void addEntitiesFromWorld(World world, BlockPos firstCorner, BlockPos secondCorner) {
      List<Entity> _snowman = world.getEntitiesByClass(Entity.class, new Box(firstCorner, secondCorner), _snowmanx -> !(_snowmanx instanceof PlayerEntity));
      this.entities.clear();

      for (Entity _snowmanx : _snowman) {
         Vec3d _snowmanxx = new Vec3d(_snowmanx.getX() - (double)firstCorner.getX(), _snowmanx.getY() - (double)firstCorner.getY(), _snowmanx.getZ() - (double)firstCorner.getZ());
         CompoundTag _snowmanxxx = new CompoundTag();
         _snowmanx.saveToTag(_snowmanxxx);
         BlockPos _snowmanxxxx;
         if (_snowmanx instanceof PaintingEntity) {
            _snowmanxxxx = ((PaintingEntity)_snowmanx).getDecorationBlockPos().subtract(firstCorner);
         } else {
            _snowmanxxxx = new BlockPos(_snowmanxx);
         }

         this.entities.add(new Structure.StructureEntityInfo(_snowmanxx, _snowmanxxxx, _snowmanxxx.copy()));
      }
   }

   public List<Structure.StructureBlockInfo> getInfosForBlock(BlockPos pos, StructurePlacementData placementData, Block block) {
      return this.getInfosForBlock(pos, placementData, block, true);
   }

   public List<Structure.StructureBlockInfo> getInfosForBlock(BlockPos pos, StructurePlacementData placementData, Block block, boolean transformed) {
      List<Structure.StructureBlockInfo> _snowman = Lists.newArrayList();
      BlockBox _snowmanx = placementData.getBoundingBox();
      if (this.blockInfoLists.isEmpty()) {
         return Collections.emptyList();
      } else {
         for (Structure.StructureBlockInfo _snowmanxx : placementData.getRandomBlockInfos(this.blockInfoLists, pos).getAllOf(block)) {
            BlockPos _snowmanxxx = transformed ? transform(placementData, _snowmanxx.pos).add(pos) : _snowmanxx.pos;
            if (_snowmanx == null || _snowmanx.contains(_snowmanxxx)) {
               _snowman.add(new Structure.StructureBlockInfo(_snowmanxxx, _snowmanxx.state.rotate(placementData.getRotation()), _snowmanxx.tag));
            }
         }

         return _snowman;
      }
   }

   public BlockPos transformBox(StructurePlacementData placementData1, BlockPos pos1, StructurePlacementData placementData2, BlockPos pos2) {
      BlockPos _snowman = transform(placementData1, pos1);
      BlockPos _snowmanx = transform(placementData2, pos2);
      return _snowman.subtract(_snowmanx);
   }

   public static BlockPos transform(StructurePlacementData placementData, BlockPos pos) {
      return transformAround(pos, placementData.getMirror(), placementData.getRotation(), placementData.getPosition());
   }

   public void place(ServerWorldAccess world, BlockPos pos, StructurePlacementData placementData, Random random) {
      placementData.calculateBoundingBox();
      this.placeAndNotifyListeners(world, pos, placementData, random);
   }

   public void placeAndNotifyListeners(ServerWorldAccess world, BlockPos pos, StructurePlacementData data, Random random) {
      this.place(world, pos, pos, data, random, 2);
   }

   public boolean place(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, Random random, int flags) {
      if (this.blockInfoLists.isEmpty()) {
         return false;
      } else {
         List<Structure.StructureBlockInfo> blockInfos = placementData.getRandomBlockInfos(this.blockInfoLists, pos).getAll();
         if ((!blockInfos.isEmpty() || !placementData.shouldIgnoreEntities() && !this.entities.isEmpty())
            && this.size.getX() >= 1
            && this.size.getY() >= 1
            && this.size.getZ() >= 1) {
            BlockBox boundingBox = placementData.getBoundingBox();
            List<BlockPos> fluidPositions = Lists.newArrayListWithCapacity(placementData.shouldPlaceFluids() ? blockInfos.size() : 0);
            List<Pair<BlockPos, CompoundTag>> blockEntities = Lists.newArrayListWithCapacity(blockInfos.size());
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            int maxZ = Integer.MIN_VALUE;

            for (Structure.StructureBlockInfo info : process(world, pos, pivot, placementData, blockInfos)) {
               BlockPos absolutePos = info.pos;
               if (_snowmanxxxxx == null || _snowmanxxxxx.contains(_snowmanxxxxxxxxxxxxxxx)) {
                  FluidState _snowmanxxxxxxxxxxxxxxxx = placementData.shouldPlaceFluids() ? _snowman.getFluidState(_snowmanxxxxxxxxxxxxxxx) : null;
                  BlockState _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.state.mirror(placementData.getMirror()).rotate(placementData.getRotation());
                  if (_snowmanxxxxxxxxxxxxxx.tag != null) {
                     BlockEntity _snowmanxxxxxxxxxxxxxxxxxx = _snowman.getBlockEntity(_snowmanxxxxxxxxxxxxxxx);
                     Clearable.clear(_snowmanxxxxxxxxxxxxxxxxxx);
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxx, Blocks.BARRIER.getDefaultState(), 20);
                  }

                  if (_snowman.setBlockState(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowman)) {
                     _snowmanxxxxxxxx = Math.min(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.getX());
                     _snowmanxxxxxxxxx = Math.min(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.getY());
                     _snowmanxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.getZ());
                     _snowmanxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.getX());
                     _snowmanxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.getY());
                     _snowmanxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.getZ());
                     _snowmanxxxxxxx.add(Pair.of(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx.tag));
                     if (_snowmanxxxxxxxxxxxxxx.tag != null) {
                        BlockEntity _snowmanxxxxxxxxxxxxxxxxxx = _snowman.getBlockEntity(_snowmanxxxxxxxxxxxxxxx);
                        if (_snowmanxxxxxxxxxxxxxxxxxx != null) {
                           _snowmanxxxxxxxxxxxxxx.tag.putInt("x", _snowmanxxxxxxxxxxxxxxx.getX());
                           _snowmanxxxxxxxxxxxxxx.tag.putInt("y", _snowmanxxxxxxxxxxxxxxx.getY());
                           _snowmanxxxxxxxxxxxxxx.tag.putInt("z", _snowmanxxxxxxxxxxxxxxx.getZ());
                           if (_snowmanxxxxxxxxxxxxxxxxxx instanceof LootableContainerBlockEntity) {
                              _snowmanxxxxxxxxxxxxxx.tag.putLong("LootTableSeed", _snowman.nextLong());
                           }

                           _snowmanxxxxxxxxxxxxxxxxxx.fromTag(_snowmanxxxxxxxxxxxxxx.state, _snowmanxxxxxxxxxxxxxx.tag);
                           _snowmanxxxxxxxxxxxxxxxxxx.applyMirror(placementData.getMirror());
                           _snowmanxxxxxxxxxxxxxxxxxx.applyRotation(placementData.getRotation());
                        }
                     }

                     if (_snowmanxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxxx.getBlock() instanceof FluidFillable) {
                        ((FluidFillable)_snowmanxxxxxxxxxxxxxxxxx.getBlock()).tryFillWithFluid(_snowman, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
                        if (!_snowmanxxxxxxxxxxxxxxxx.isStill()) {
                           _snowmanxxxxxx.add(_snowmanxxxxxxxxxxxxxxx);
                        }
                     }
                  }
               }
            }

            boolean _snowmanxxxxxxxxxxxxxxx = true;
            Direction[] _snowmanxxxxxxxxxxxxxxxxxx = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

            while (_snowmanxxxxxxxxxxxxxxx && !_snowmanxxxxxx.isEmpty()) {
               _snowmanxxxxxxxxxxxxxxx = false;
               Iterator<BlockPos> _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.iterator();

               while (_snowmanxxxxxxxxxxxxxxxxxxx.hasNext()) {
                  BlockPos _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.next();
                  BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx;
                  FluidState _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.getFluidState(_snowmanxxxxxxxxxxxxxxxxxxxx);

                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxx.length && !_snowmanxxxxxxxxxxxxxxxxxxxxxx.isStill();
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx++
                  ) {
                     BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.offset(_snowmanxxxxxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxxx]);
                     FluidState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getFluidState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.getHeight(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) > _snowmanxxxxxxxxxxxxxxxxxxxxxx.getHeight(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxx)
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.isStill() && !_snowmanxxxxxxxxxxxxxxxxxxxxxx.isStill()) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
                        _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxx.isStill()) {
                     BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxx);
                     Block _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getBlock();
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx instanceof FluidFillable) {
                        ((FluidFillable)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .tryFillWithFluid(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
                        _snowmanxxxxxxxxxxxxxxx = true;
                        _snowmanxxxxxxxxxxxxxxxxxxx.remove();
                     }
                  }
               }
            }

            if (_snowmanxxxxxxxx <= _snowmanxxxxxxxxxxx) {
               if (!placementData.shouldUpdateNeighbors()) {
                  VoxelSet _snowmanxxxxxxxxxxxxxxxxxxx = new BitSetVoxelSet(
                     _snowmanxxxxxxxxxxx - _snowmanxxxxxxxx + 1, _snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxx + 1
                  );
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx;

                  for (Pair<BlockPos, CompoundTag> _snowmanxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxx) {
                     BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (BlockPos)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getFirst();
                     _snowmanxxxxxxxxxxxxxxxxxxx.set(
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.getX() - _snowmanxxxxxxxxxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.getY() - _snowmanxxxxxxxxxxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.getZ() - _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                        true,
                        true
                     );
                  }

                  updateCorner(_snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
               }

               for (Pair<BlockPos, CompoundTag> _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxx) {
                  BlockPos _snowmanxxxxxxxxxxxxxxxxxxxx = (BlockPos)_snowmanxxxxxxxxxxxxxxxxxxx.getFirst();
                  if (!placementData.shouldUpdateNeighbors()) {
                     BlockState _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxx);
                     BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxx = Block.postProcessState(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxxxxxxx) {
                        _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman & -2 | 16);
                     }

                     _snowman.updateNeighbors(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx.getBlock());
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxx.getSecond() != null) {
                     BlockEntity _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.getBlockEntity(_snowmanxxxxxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxx != null) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxx.markDirty();
                     }
                  }
               }
            }

            if (!placementData.shouldIgnoreEntities()) {
               this.spawnEntities(
                  _snowman, pos, placementData.getMirror(), placementData.getRotation(), placementData.getPosition(), _snowmanxxxxx, placementData.method_27265()
               );
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static void updateCorner(WorldAccess world, int flags, VoxelSet _snowman, int startX, int startY, int startZ) {
      _snowman.forEachDirection((_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx) -> {
         BlockPos _snowmanxxxxxxxxx = new BlockPos(startX + _snowmanxxxxxxxxxx, startY + _snowmanxxxxxxxxx, startZ + _snowmanxxxxxxxx);
         BlockPos _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.offset(_snowmanxxxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxxx);
         BlockState _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getStateForNeighborUpdate(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, world, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxx != _snowmanxxxxxxxxxxxxx) {
            world.setBlockState(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxx, flags & -2);
         }

         BlockState _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getStateForNeighborUpdate(_snowmanxxxxxxxxxxx.getOpposite(), _snowmanxxxxxxxxxxxxx, world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxx) {
            world.setBlockState(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, flags & -2);
         }
      });
   }

   public static List<Structure.StructureBlockInfo> process(
      WorldAccess world, BlockPos pos, BlockPos _snowman, StructurePlacementData _snowman, List<Structure.StructureBlockInfo> _snowman
   ) {
      List<Structure.StructureBlockInfo> _snowmanxxx = Lists.newArrayList();

      for (Structure.StructureBlockInfo _snowmanxxxx : _snowman) {
         BlockPos _snowmanxxxxx = transform(_snowman, _snowmanxxxx.pos).add(pos);
         Structure.StructureBlockInfo _snowmanxxxxxx = new Structure.StructureBlockInfo(_snowmanxxxxx, _snowmanxxxx.state, _snowmanxxxx.tag != null ? _snowmanxxxx.tag.copy() : null);
         Iterator<StructureProcessor> _snowmanxxxxxxx = _snowman.getProcessors().iterator();

         while (_snowmanxxxxxx != null && _snowmanxxxxxxx.hasNext()) {
            _snowmanxxxxxx = _snowmanxxxxxxx.next().process(world, pos, _snowman, _snowmanxxxx, _snowmanxxxxxx, _snowman);
         }

         if (_snowmanxxxxxx != null) {
            _snowmanxxx.add(_snowmanxxxxxx);
         }
      }

      return _snowmanxxx;
   }

   private void spawnEntities(ServerWorldAccess _snowman, BlockPos pos, BlockMirror _snowman, BlockRotation _snowman, BlockPos pivot, @Nullable BlockBox area, boolean _snowman) {
      for (Structure.StructureEntityInfo _snowmanxxxx : this.entities) {
         BlockPos _snowmanxxxxx = transformAround(_snowmanxxxx.blockPos, _snowman, _snowman, pivot).add(pos);
         if (area == null || area.contains(_snowmanxxxxx)) {
            CompoundTag _snowmanxxxxxx = _snowmanxxxx.tag.copy();
            Vec3d _snowmanxxxxxxx = transformAround(_snowmanxxxx.pos, _snowman, _snowman, pivot);
            Vec3d _snowmanxxxxxxxx = _snowmanxxxxxxx.add((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            ListTag _snowmanxxxxxxxxx = new ListTag();
            _snowmanxxxxxxxxx.add(DoubleTag.of(_snowmanxxxxxxxx.x));
            _snowmanxxxxxxxxx.add(DoubleTag.of(_snowmanxxxxxxxx.y));
            _snowmanxxxxxxxxx.add(DoubleTag.of(_snowmanxxxxxxxx.z));
            _snowmanxxxxxx.put("Pos", _snowmanxxxxxxxxx);
            _snowmanxxxxxx.remove("UUID");
            getEntity(_snowman, _snowmanxxxxxx).ifPresent(_snowmanxxxxxxxxxx -> {
               float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.applyMirror(_snowman);
               _snowmanxxxxxxxxxxx += _snowmanxxxxxxxxxx.yaw - _snowmanxxxxxxxxxx.applyRotation(_snowman);
               _snowmanxxxxxxxxxx.refreshPositionAndAngles(_snowman.x, _snowman.y, _snowman.z, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx.pitch);
               if (_snowman && _snowmanxxxxxxxxxx instanceof MobEntity) {
                  ((MobEntity)_snowmanxxxxxxxxxx).initialize(_snowman, _snowman.getLocalDifficulty(new BlockPos(_snowman)), SpawnReason.STRUCTURE, null, _snowman);
               }

               _snowman.spawnEntityAndPassengers(_snowmanxxxxxxxxxx);
            });
         }
      }
   }

   private static Optional<Entity> getEntity(ServerWorldAccess _snowman, CompoundTag _snowman) {
      try {
         return EntityType.getEntityFromTag(_snowman, _snowman.toServerWorld());
      } catch (Exception var3) {
         return Optional.empty();
      }
   }

   public BlockPos getRotatedSize(BlockRotation _snowman) {
      switch (_snowman) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            return new BlockPos(this.size.getZ(), this.size.getY(), this.size.getX());
         default:
            return this.size;
      }
   }

   public static BlockPos transformAround(BlockPos pos, BlockMirror _snowman, BlockRotation _snowman, BlockPos pivot) {
      int _snowmanxx = pos.getX();
      int _snowmanxxx = pos.getY();
      int _snowmanxxxx = pos.getZ();
      boolean _snowmanxxxxx = true;
      switch (_snowman) {
         case LEFT_RIGHT:
            _snowmanxxxx = -_snowmanxxxx;
            break;
         case FRONT_BACK:
            _snowmanxx = -_snowmanxx;
            break;
         default:
            _snowmanxxxxx = false;
      }

      int _snowmanxx = pivot.getX();
      int _snowmanxxx = pivot.getZ();
      switch (_snowman) {
         case COUNTERCLOCKWISE_90:
            return new BlockPos(_snowmanxx - _snowmanxxx + _snowmanxxxx, _snowmanxxx, _snowmanxx + _snowmanxxx - _snowmanxx);
         case CLOCKWISE_90:
            return new BlockPos(_snowmanxx + _snowmanxxx - _snowmanxxxx, _snowmanxxx, _snowmanxxx - _snowmanxx + _snowmanxx);
         case CLOCKWISE_180:
            return new BlockPos(_snowmanxx + _snowmanxx - _snowmanxx, _snowmanxxx, _snowmanxxx + _snowmanxxx - _snowmanxxxx);
         default:
            return _snowmanxxxxx ? new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx) : pos;
      }
   }

   public static Vec3d transformAround(Vec3d point, BlockMirror _snowman, BlockRotation _snowman, BlockPos pivot) {
      double _snowmanxx = point.x;
      double _snowmanxxx = point.y;
      double _snowmanxxxx = point.z;
      boolean _snowmanxxxxx = true;
      switch (_snowman) {
         case LEFT_RIGHT:
            _snowmanxxxx = 1.0 - _snowmanxxxx;
            break;
         case FRONT_BACK:
            _snowmanxx = 1.0 - _snowmanxx;
            break;
         default:
            _snowmanxxxxx = false;
      }

      int _snowmanxx = pivot.getX();
      int _snowmanxxx = pivot.getZ();
      switch (_snowman) {
         case COUNTERCLOCKWISE_90:
            return new Vec3d((double)(_snowmanxx - _snowmanxxx) + _snowmanxxxx, _snowmanxxx, (double)(_snowmanxx + _snowmanxxx + 1) - _snowmanxx);
         case CLOCKWISE_90:
            return new Vec3d((double)(_snowmanxx + _snowmanxxx + 1) - _snowmanxxxx, _snowmanxxx, (double)(_snowmanxxx - _snowmanxx) + _snowmanxx);
         case CLOCKWISE_180:
            return new Vec3d((double)(_snowmanxx + _snowmanxx + 1) - _snowmanxx, _snowmanxxx, (double)(_snowmanxxx + _snowmanxxx + 1) - _snowmanxxxx);
         default:
            return _snowmanxxxxx ? new Vec3d(_snowmanxx, _snowmanxxx, _snowmanxxxx) : point;
      }
   }

   public BlockPos offsetByTransformedSize(BlockPos _snowman, BlockMirror _snowman, BlockRotation _snowman) {
      return applyTransformedOffset(_snowman, _snowman, _snowman, this.getSize().getX(), this.getSize().getZ());
   }

   public static BlockPos applyTransformedOffset(BlockPos _snowman, BlockMirror _snowman, BlockRotation _snowman, int offsetX, int offsetZ) {
      offsetX--;
      offsetZ--;
      int _snowmanxxx = _snowman == BlockMirror.FRONT_BACK ? offsetX : 0;
      int _snowmanxxxx = _snowman == BlockMirror.LEFT_RIGHT ? offsetZ : 0;
      BlockPos _snowmanxxxxx = _snowman;
      switch (_snowman) {
         case COUNTERCLOCKWISE_90:
            _snowmanxxxxx = _snowman.add(_snowmanxxxx, 0, offsetX - _snowmanxxx);
            break;
         case CLOCKWISE_90:
            _snowmanxxxxx = _snowman.add(offsetZ - _snowmanxxxx, 0, _snowmanxxx);
            break;
         case CLOCKWISE_180:
            _snowmanxxxxx = _snowman.add(offsetX - _snowmanxxx, 0, offsetZ - _snowmanxxxx);
            break;
         case NONE:
            _snowmanxxxxx = _snowman.add(_snowmanxxx, 0, _snowmanxxxx);
      }

      return _snowmanxxxxx;
   }

   public BlockBox calculateBoundingBox(StructurePlacementData _snowman, BlockPos pos) {
      return this.method_27267(pos, _snowman.getRotation(), _snowman.getPosition(), _snowman.getMirror());
   }

   public BlockBox method_27267(BlockPos _snowman, BlockRotation _snowman, BlockPos _snowman, BlockMirror _snowman) {
      BlockPos _snowmanxxxx = this.getRotatedSize(_snowman);
      int _snowmanxxxxx = _snowman.getX();
      int _snowmanxxxxxx = _snowman.getZ();
      int _snowmanxxxxxxx = _snowmanxxxx.getX() - 1;
      int _snowmanxxxxxxxx = _snowmanxxxx.getY() - 1;
      int _snowmanxxxxxxxxx = _snowmanxxxx.getZ() - 1;
      BlockBox _snowmanxxxxxxxxxx = new BlockBox(0, 0, 0, 0, 0, 0);
      switch (_snowman) {
         case COUNTERCLOCKWISE_90:
            _snowmanxxxxxxxxxx = new BlockBox(_snowmanxxxxx - _snowmanxxxxxx, 0, _snowmanxxxxx + _snowmanxxxxxx - _snowmanxxxxxxxxx, _snowmanxxxxx - _snowmanxxxxxx + _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx + _snowmanxxxxxx);
            break;
         case CLOCKWISE_90:
            _snowmanxxxxxxxxxx = new BlockBox(_snowmanxxxxx + _snowmanxxxxxx - _snowmanxxxxxxx, 0, _snowmanxxxxxx - _snowmanxxxxx, _snowmanxxxxx + _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx - _snowmanxxxxx + _snowmanxxxxxxxxx);
            break;
         case CLOCKWISE_180:
            _snowmanxxxxxxxxxx = new BlockBox(_snowmanxxxxx + _snowmanxxxxx - _snowmanxxxxxxx, 0, _snowmanxxxxxx + _snowmanxxxxxx - _snowmanxxxxxxxxx, _snowmanxxxxx + _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx + _snowmanxxxxxx);
            break;
         case NONE:
            _snowmanxxxxxxxxxx = new BlockBox(0, 0, 0, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
      }

      switch (_snowman) {
         case LEFT_RIGHT:
            this.mirrorBoundingBox(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxx, Direction.NORTH, Direction.SOUTH);
            break;
         case FRONT_BACK:
            this.mirrorBoundingBox(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, Direction.WEST, Direction.EAST);
         case NONE:
      }

      _snowmanxxxxxxxxxx.move(_snowman.getX(), _snowman.getY(), _snowman.getZ());
      return _snowmanxxxxxxxxxx;
   }

   private void mirrorBoundingBox(BlockRotation rotation, int offsetX, int offsetZ, BlockBox boundingBox, Direction _snowman, Direction _snowman) {
      BlockPos _snowmanxx = BlockPos.ORIGIN;
      if (rotation == BlockRotation.CLOCKWISE_90 || rotation == BlockRotation.COUNTERCLOCKWISE_90) {
         _snowmanxx = _snowmanxx.offset(rotation.rotate(_snowman), offsetZ);
      } else if (rotation == BlockRotation.CLOCKWISE_180) {
         _snowmanxx = _snowmanxx.offset(_snowman, offsetX);
      } else {
         _snowmanxx = _snowmanxx.offset(_snowman, offsetX);
      }

      boundingBox.move(_snowmanxx.getX(), 0, _snowmanxx.getZ());
   }

   public CompoundTag toTag(CompoundTag tag) {
      if (this.blockInfoLists.isEmpty()) {
         tag.put("blocks", new ListTag());
         tag.put("palette", new ListTag());
      } else {
         List<Structure.Palette> _snowman = Lists.newArrayList();
         Structure.Palette _snowmanx = new Structure.Palette();
         _snowman.add(_snowmanx);

         for (int _snowmanxx = 1; _snowmanxx < this.blockInfoLists.size(); _snowmanxx++) {
            _snowman.add(new Structure.Palette());
         }

         ListTag _snowmanxx = new ListTag();
         List<Structure.StructureBlockInfo> _snowmanxxx = this.blockInfoLists.get(0).getAll();

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
            Structure.StructureBlockInfo _snowmanxxxxx = _snowmanxxx.get(_snowmanxxxx);
            CompoundTag _snowmanxxxxxx = new CompoundTag();
            _snowmanxxxxxx.put("pos", this.createIntListTag(_snowmanxxxxx.pos.getX(), _snowmanxxxxx.pos.getY(), _snowmanxxxxx.pos.getZ()));
            int _snowmanxxxxxxx = _snowmanx.getId(_snowmanxxxxx.state);
            _snowmanxxxxxx.putInt("state", _snowmanxxxxxxx);
            if (_snowmanxxxxx.tag != null) {
               _snowmanxxxxxx.put("nbt", _snowmanxxxxx.tag);
            }

            _snowmanxx.add(_snowmanxxxxxx);

            for (int _snowmanxxxxxxxx = 1; _snowmanxxxxxxxx < this.blockInfoLists.size(); _snowmanxxxxxxxx++) {
               Structure.Palette _snowmanxxxxxxxxx = _snowman.get(_snowmanxxxxxxxx);
               _snowmanxxxxxxxxx.set(this.blockInfoLists.get(_snowmanxxxxxxxx).getAll().get(_snowmanxxxx).state, _snowmanxxxxxxx);
            }
         }

         tag.put("blocks", _snowmanxx);
         if (_snowman.size() == 1) {
            ListTag _snowmanxxxx = new ListTag();

            for (BlockState _snowmanxxxxx : _snowmanx) {
               _snowmanxxxx.add(NbtHelper.fromBlockState(_snowmanxxxxx));
            }

            tag.put("palette", _snowmanxxxx);
         } else {
            ListTag _snowmanxxxx = new ListTag();

            for (Structure.Palette _snowmanxxxxx : _snowman) {
               ListTag _snowmanxxxxxx = new ListTag();

               for (BlockState _snowmanxxxxxxx : _snowmanxxxxx) {
                  _snowmanxxxxxx.add(NbtHelper.fromBlockState(_snowmanxxxxxxx));
               }

               _snowmanxxxx.add(_snowmanxxxxxx);
            }

            tag.put("palettes", _snowmanxxxx);
         }
      }

      ListTag _snowman = new ListTag();

      for (Structure.StructureEntityInfo _snowmanx : this.entities) {
         CompoundTag _snowmanxx = new CompoundTag();
         _snowmanxx.put("pos", this.createDoubleListTag(_snowmanx.pos.x, _snowmanx.pos.y, _snowmanx.pos.z));
         _snowmanxx.put("blockPos", this.createIntListTag(_snowmanx.blockPos.getX(), _snowmanx.blockPos.getY(), _snowmanx.blockPos.getZ()));
         if (_snowmanx.tag != null) {
            _snowmanxx.put("nbt", _snowmanx.tag);
         }

         _snowman.add(_snowmanxx);
      }

      tag.put("entities", _snowman);
      tag.put("size", this.createIntListTag(this.size.getX(), this.size.getY(), this.size.getZ()));
      tag.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      return tag;
   }

   public void fromTag(CompoundTag tag) {
      this.blockInfoLists.clear();
      this.entities.clear();
      ListTag _snowman = tag.getList("size", 3);
      this.size = new BlockPos(_snowman.getInt(0), _snowman.getInt(1), _snowman.getInt(2));
      ListTag _snowmanx = tag.getList("blocks", 10);
      if (tag.contains("palettes", 9)) {
         ListTag _snowmanxx = tag.getList("palettes", 9);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            this.loadPalettedBlockInfo(_snowmanxx.getList(_snowmanxxx), _snowmanx);
         }
      } else {
         this.loadPalettedBlockInfo(tag.getList("palette", 10), _snowmanx);
      }

      ListTag _snowmanxx = tag.getList("entities", 10);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
         CompoundTag _snowmanxxxx = _snowmanxx.getCompound(_snowmanxxx);
         ListTag _snowmanxxxxx = _snowmanxxxx.getList("pos", 6);
         Vec3d _snowmanxxxxxx = new Vec3d(_snowmanxxxxx.getDouble(0), _snowmanxxxxx.getDouble(1), _snowmanxxxxx.getDouble(2));
         ListTag _snowmanxxxxxxx = _snowmanxxxx.getList("blockPos", 3);
         BlockPos _snowmanxxxxxxxx = new BlockPos(_snowmanxxxxxxx.getInt(0), _snowmanxxxxxxx.getInt(1), _snowmanxxxxxxx.getInt(2));
         if (_snowmanxxxx.contains("nbt")) {
            CompoundTag _snowmanxxxxxxxxx = _snowmanxxxx.getCompound("nbt");
            this.entities.add(new Structure.StructureEntityInfo(_snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx));
         }
      }
   }

   private void loadPalettedBlockInfo(ListTag paletteTag, ListTag blocksTag) {
      Structure.Palette _snowman = new Structure.Palette();

      for (int _snowmanx = 0; _snowmanx < paletteTag.size(); _snowmanx++) {
         _snowman.set(NbtHelper.toBlockState(paletteTag.getCompound(_snowmanx)), _snowmanx);
      }

      List<Structure.StructureBlockInfo> _snowmanx = Lists.newArrayList();
      List<Structure.StructureBlockInfo> _snowmanxx = Lists.newArrayList();
      List<Structure.StructureBlockInfo> _snowmanxxx = Lists.newArrayList();

      for (int _snowmanxxxx = 0; _snowmanxxxx < blocksTag.size(); _snowmanxxxx++) {
         CompoundTag _snowmanxxxxx = blocksTag.getCompound(_snowmanxxxx);
         ListTag _snowmanxxxxxx = _snowmanxxxxx.getList("pos", 3);
         BlockPos _snowmanxxxxxxx = new BlockPos(_snowmanxxxxxx.getInt(0), _snowmanxxxxxx.getInt(1), _snowmanxxxxxx.getInt(2));
         BlockState _snowmanxxxxxxxx = _snowman.getState(_snowmanxxxxx.getInt("state"));
         CompoundTag _snowmanxxxxxxxxx;
         if (_snowmanxxxxx.contains("nbt")) {
            _snowmanxxxxxxxxx = _snowmanxxxxx.getCompound("nbt");
         } else {
            _snowmanxxxxxxxxx = null;
         }

         Structure.StructureBlockInfo _snowmanxxxxxxxxxx = new Structure.StructureBlockInfo(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         method_28054(_snowmanxxxxxxxxxx, _snowmanx, _snowmanxx, _snowmanxxx);
      }

      List<Structure.StructureBlockInfo> _snowmanxxxx = method_28055(_snowmanx, _snowmanxx, _snowmanxxx);
      this.blockInfoLists.add(new Structure.PalettedBlockInfoList(_snowmanxxxx));
   }

   private ListTag createIntListTag(int... _snowman) {
      ListTag _snowmanx = new ListTag();

      for (int _snowmanxx : _snowman) {
         _snowmanx.add(IntTag.of(_snowmanxx));
      }

      return _snowmanx;
   }

   private ListTag createDoubleListTag(double... _snowman) {
      ListTag _snowmanx = new ListTag();

      for (double _snowmanxx : _snowman) {
         _snowmanx.add(DoubleTag.of(_snowmanxx));
      }

      return _snowmanx;
   }

   static class Palette implements Iterable<BlockState> {
      public static final BlockState AIR = Blocks.AIR.getDefaultState();
      private final IdList<BlockState> ids = new IdList<>(16);
      private int currentIndex;

      private Palette() {
      }

      public int getId(BlockState state) {
         int _snowman = this.ids.getRawId(state);
         if (_snowman == -1) {
            _snowman = this.currentIndex++;
            this.ids.set(state, _snowman);
         }

         return _snowman;
      }

      @Nullable
      public BlockState getState(int id) {
         BlockState _snowman = this.ids.get(id);
         return _snowman == null ? AIR : _snowman;
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
         return this.blockToInfos.computeIfAbsent(block, _snowman -> this.infos.stream().filter(_snowmanxx -> _snowmanxx.state.isOf(_snowman)).collect(Collectors.toList()));
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
