package net.minecraft.entity.ai.pathing;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;

public class LandPathNodeMaker extends PathNodeMaker {
   protected float waterPathNodeTypeWeight;
   private final Long2ObjectMap<PathNodeType> field_25190 = new Long2ObjectOpenHashMap();
   private final Object2BooleanMap<Box> field_25191 = new Object2BooleanOpenHashMap();

   public LandPathNodeMaker() {
   }

   @Override
   public void init(ChunkCache cachedWorld, MobEntity entity) {
      super.init(cachedWorld, entity);
      this.waterPathNodeTypeWeight = entity.getPathfindingPenalty(PathNodeType.WATER);
   }

   @Override
   public void clear() {
      this.entity.setPathfindingPenalty(PathNodeType.WATER, this.waterPathNodeTypeWeight);
      this.field_25190.clear();
      this.field_25191.clear();
      super.clear();
   }

   @Override
   public PathNode getStart() {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      int _snowmanx = MathHelper.floor(this.entity.getY());
      BlockState _snowmanxx = this.cachedWorld.getBlockState(_snowman.set(this.entity.getX(), (double)_snowmanx, this.entity.getZ()));
      if (!this.entity.canWalkOnFluid(_snowmanxx.getFluidState().getFluid())) {
         if (this.canSwim() && this.entity.isTouchingWater()) {
            while (true) {
               if (_snowmanxx.getBlock() != Blocks.WATER && _snowmanxx.getFluidState() != Fluids.WATER.getStill(false)) {
                  _snowmanx--;
                  break;
               }

               _snowmanxx = this.cachedWorld.getBlockState(_snowman.set(this.entity.getX(), (double)(++_snowmanx), this.entity.getZ()));
            }
         } else if (this.entity.isOnGround()) {
            _snowmanx = MathHelper.floor(this.entity.getY() + 0.5);
         } else {
            BlockPos _snowmanxxx = this.entity.getBlockPos();

            while (
               (
                     this.cachedWorld.getBlockState(_snowmanxxx).isAir()
                        || this.cachedWorld.getBlockState(_snowmanxxx).canPathfindThrough(this.cachedWorld, _snowmanxxx, NavigationType.LAND)
                  )
                  && _snowmanxxx.getY() > 0
            ) {
               _snowmanxxx = _snowmanxxx.down();
            }

            _snowmanx = _snowmanxxx.up().getY();
         }
      } else {
         while (this.entity.canWalkOnFluid(_snowmanxx.getFluidState().getFluid())) {
            _snowmanxx = this.cachedWorld.getBlockState(_snowman.set(this.entity.getX(), (double)(++_snowmanx), this.entity.getZ()));
         }

         _snowmanx--;
      }

      BlockPos _snowmanxxx = this.entity.getBlockPos();
      PathNodeType _snowmanxxxx = this.method_29303(this.entity, _snowmanxxx.getX(), _snowmanx, _snowmanxxx.getZ());
      if (this.entity.getPathfindingPenalty(_snowmanxxxx) < 0.0F) {
         Box _snowmanxxxxx = this.entity.getBoundingBox();
         if (this.method_27139(_snowman.set(_snowmanxxxxx.minX, (double)_snowmanx, _snowmanxxxxx.minZ))
            || this.method_27139(_snowman.set(_snowmanxxxxx.minX, (double)_snowmanx, _snowmanxxxxx.maxZ))
            || this.method_27139(_snowman.set(_snowmanxxxxx.maxX, (double)_snowmanx, _snowmanxxxxx.minZ))
            || this.method_27139(_snowman.set(_snowmanxxxxx.maxX, (double)_snowmanx, _snowmanxxxxx.maxZ))) {
            PathNode _snowmanxxxxxx = this.method_27137(_snowman);
            _snowmanxxxxxx.type = this.getNodeType(this.entity, _snowmanxxxxxx.getPos());
            _snowmanxxxxxx.penalty = this.entity.getPathfindingPenalty(_snowmanxxxxxx.type);
            return _snowmanxxxxxx;
         }
      }

      PathNode _snowmanxxxxx = this.getNode(_snowmanxxx.getX(), _snowmanx, _snowmanxxx.getZ());
      _snowmanxxxxx.type = this.getNodeType(this.entity, _snowmanxxxxx.getPos());
      _snowmanxxxxx.penalty = this.entity.getPathfindingPenalty(_snowmanxxxxx.type);
      return _snowmanxxxxx;
   }

   private boolean method_27139(BlockPos _snowman) {
      PathNodeType _snowmanx = this.getNodeType(this.entity, _snowman);
      return this.entity.getPathfindingPenalty(_snowmanx) >= 0.0F;
   }

   @Override
   public TargetPathNode getNode(double x, double y, double z) {
      return new TargetPathNode(this.getNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
   }

   @Override
   public int getSuccessors(PathNode[] successors, PathNode node) {
      int _snowman = 0;
      int _snowmanx = 0;
      PathNodeType _snowmanxx = this.method_29303(this.entity, node.x, node.y + 1, node.z);
      PathNodeType _snowmanxxx = this.method_29303(this.entity, node.x, node.y, node.z);
      if (this.entity.getPathfindingPenalty(_snowmanxx) >= 0.0F && _snowmanxxx != PathNodeType.STICKY_HONEY) {
         _snowmanx = MathHelper.floor(Math.max(1.0F, this.entity.stepHeight));
      }

      double _snowmanxxxx = getFeetY(this.cachedWorld, new BlockPos(node.x, node.y, node.z));
      PathNode _snowmanxxxxx = this.getPathNode(node.x, node.y, node.z + 1, _snowmanx, _snowmanxxxx, Direction.SOUTH, _snowmanxxx);
      if (this.isValidDiagonalSuccessor(_snowmanxxxxx, node)) {
         successors[_snowman++] = _snowmanxxxxx;
      }

      PathNode _snowmanxxxxxx = this.getPathNode(node.x - 1, node.y, node.z, _snowmanx, _snowmanxxxx, Direction.WEST, _snowmanxxx);
      if (this.isValidDiagonalSuccessor(_snowmanxxxxxx, node)) {
         successors[_snowman++] = _snowmanxxxxxx;
      }

      PathNode _snowmanxxxxxxx = this.getPathNode(node.x + 1, node.y, node.z, _snowmanx, _snowmanxxxx, Direction.EAST, _snowmanxxx);
      if (this.isValidDiagonalSuccessor(_snowmanxxxxxxx, node)) {
         successors[_snowman++] = _snowmanxxxxxxx;
      }

      PathNode _snowmanxxxxxxxx = this.getPathNode(node.x, node.y, node.z - 1, _snowmanx, _snowmanxxxx, Direction.NORTH, _snowmanxxx);
      if (this.isValidDiagonalSuccessor(_snowmanxxxxxxxx, node)) {
         successors[_snowman++] = _snowmanxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxx = this.getPathNode(node.x - 1, node.y, node.z - 1, _snowmanx, _snowmanxxxx, Direction.NORTH, _snowmanxxx);
      if (this.method_29579(node, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxx = this.getPathNode(node.x + 1, node.y, node.z - 1, _snowmanx, _snowmanxxxx, Direction.NORTH, _snowmanxxx);
      if (this.method_29579(node, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxx = this.getPathNode(node.x - 1, node.y, node.z + 1, _snowmanx, _snowmanxxxx, Direction.SOUTH, _snowmanxxx);
      if (this.method_29579(node, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxx;
      }

      PathNode _snowmanxxxxxxxxxxxx = this.getPathNode(node.x + 1, node.y, node.z + 1, _snowmanx, _snowmanxxxx, Direction.SOUTH, _snowmanxxx);
      if (this.method_29579(node, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxx)) {
         successors[_snowman++] = _snowmanxxxxxxxxxxxx;
      }

      return _snowman;
   }

   private boolean isValidDiagonalSuccessor(PathNode node, PathNode successor1) {
      return node != null && !node.visited && (node.penalty >= 0.0F || successor1.penalty < 0.0F);
   }

   private boolean method_29579(PathNode _snowman, @Nullable PathNode _snowman, @Nullable PathNode _snowman, @Nullable PathNode _snowman) {
      if (_snowman == null || _snowman == null || _snowman == null) {
         return false;
      } else if (_snowman.visited) {
         return false;
      } else if (_snowman.y > _snowman.y || _snowman.y > _snowman.y) {
         return false;
      } else if (_snowman.type != PathNodeType.WALKABLE_DOOR && _snowman.type != PathNodeType.WALKABLE_DOOR && _snowman.type != PathNodeType.WALKABLE_DOOR) {
         boolean _snowmanxxxx = _snowman.type == PathNodeType.FENCE && _snowman.type == PathNodeType.FENCE && (double)this.entity.getWidth() < 0.5;
         return _snowman.penalty >= 0.0F && (_snowman.y < _snowman.y || _snowman.penalty >= 0.0F || _snowmanxxxx) && (_snowman.y < _snowman.y || _snowman.penalty >= 0.0F || _snowmanxxxx);
      } else {
         return false;
      }
   }

   private boolean method_29578(PathNode _snowman) {
      Vec3d _snowmanx = new Vec3d((double)_snowman.x - this.entity.getX(), (double)_snowman.y - this.entity.getY(), (double)_snowman.z - this.entity.getZ());
      Box _snowmanxx = this.entity.getBoundingBox();
      int _snowmanxxx = MathHelper.ceil(_snowmanx.length() / _snowmanxx.getAverageSideLength());
      _snowmanx = _snowmanx.multiply((double)(1.0F / (float)_snowmanxxx));

      for (int _snowmanxxxx = 1; _snowmanxxxx <= _snowmanxxx; _snowmanxxxx++) {
         _snowmanxx = _snowmanxx.offset(_snowmanx);
         if (this.method_29304(_snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   public static double getFeetY(BlockView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      VoxelShape _snowmanx = world.getBlockState(_snowman).getCollisionShape(world, _snowman);
      return (double)_snowman.getY() + (_snowmanx.isEmpty() ? 0.0 : _snowmanx.getMax(Direction.Axis.Y));
   }

   @Nullable
   private PathNode getPathNode(int x, int y, int z, int maxYStep, double prevFeetY, Direction direction, PathNodeType _snowman) {
      PathNode _snowmanx = null;
      BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();
      double _snowmanxxx = getFeetY(this.cachedWorld, _snowmanxx.set(x, y, z));
      if (_snowmanxxx - prevFeetY > 1.125) {
         return null;
      } else {
         PathNodeType _snowmanxxxx = this.method_29303(this.entity, x, y, z);
         float _snowmanxxxxx = this.entity.getPathfindingPenalty(_snowmanxxxx);
         double _snowmanxxxxxx = (double)this.entity.getWidth() / 2.0;
         if (_snowmanxxxxx >= 0.0F) {
            _snowmanx = this.getNode(x, y, z);
            _snowmanx.type = _snowmanxxxx;
            _snowmanx.penalty = Math.max(_snowmanx.penalty, _snowmanxxxxx);
         }

         if (_snowman == PathNodeType.FENCE && _snowmanx != null && _snowmanx.penalty >= 0.0F && !this.method_29578(_snowmanx)) {
            _snowmanx = null;
         }

         if (_snowmanxxxx == PathNodeType.WALKABLE) {
            return _snowmanx;
         } else {
            if ((_snowmanx == null || _snowmanx.penalty < 0.0F)
               && maxYStep > 0
               && _snowmanxxxx != PathNodeType.FENCE
               && _snowmanxxxx != PathNodeType.UNPASSABLE_RAIL
               && _snowmanxxxx != PathNodeType.TRAPDOOR) {
               _snowmanx = this.getPathNode(x, y + 1, z, maxYStep - 1, prevFeetY, direction, _snowman);
               if (_snowmanx != null && (_snowmanx.type == PathNodeType.OPEN || _snowmanx.type == PathNodeType.WALKABLE) && this.entity.getWidth() < 1.0F) {
                  double _snowmanxxxxxxx = (double)(x - direction.getOffsetX()) + 0.5;
                  double _snowmanxxxxxxxx = (double)(z - direction.getOffsetZ()) + 0.5;
                  Box _snowmanxxxxxxxxx = new Box(
                     _snowmanxxxxxxx - _snowmanxxxxxx,
                     getFeetY(this.cachedWorld, _snowmanxx.set(_snowmanxxxxxxx, (double)(y + 1), _snowmanxxxxxxxx)) + 0.001,
                     _snowmanxxxxxxxx - _snowmanxxxxxx,
                     _snowmanxxxxxxx + _snowmanxxxxxx,
                     (double)this.entity.getHeight() + getFeetY(this.cachedWorld, _snowmanxx.set((double)_snowmanx.x, (double)_snowmanx.y, (double)_snowmanx.z)) - 0.002,
                     _snowmanxxxxxxxx + _snowmanxxxxxx
                  );
                  if (this.method_29304(_snowmanxxxxxxxxx)) {
                     _snowmanx = null;
                  }
               }
            }

            if (_snowmanxxxx == PathNodeType.WATER && !this.canSwim()) {
               if (this.method_29303(this.entity, x, y - 1, z) != PathNodeType.WATER) {
                  return _snowmanx;
               }

               while (y > 0) {
                  _snowmanxxxx = this.method_29303(this.entity, x, --y, z);
                  if (_snowmanxxxx != PathNodeType.WATER) {
                     return _snowmanx;
                  }

                  _snowmanx = this.getNode(x, y, z);
                  _snowmanx.type = _snowmanxxxx;
                  _snowmanx.penalty = Math.max(_snowmanx.penalty, this.entity.getPathfindingPenalty(_snowmanxxxx));
               }
            }

            if (_snowmanxxxx == PathNodeType.OPEN) {
               int _snowmanxxxxxxx = 0;
               int _snowmanxxxxxxxx = y;

               while (_snowmanxxxx == PathNodeType.OPEN) {
                  if (--y < 0) {
                     PathNode _snowmanxxxxxxxxx = this.getNode(x, _snowmanxxxxxxxx, z);
                     _snowmanxxxxxxxxx.type = PathNodeType.BLOCKED;
                     _snowmanxxxxxxxxx.penalty = -1.0F;
                     return _snowmanxxxxxxxxx;
                  }

                  if (_snowmanxxxxxxx++ >= this.entity.getSafeFallDistance()) {
                     PathNode _snowmanxxxxxxxxx = this.getNode(x, y, z);
                     _snowmanxxxxxxxxx.type = PathNodeType.BLOCKED;
                     _snowmanxxxxxxxxx.penalty = -1.0F;
                     return _snowmanxxxxxxxxx;
                  }

                  _snowmanxxxx = this.method_29303(this.entity, x, y, z);
                  _snowmanxxxxx = this.entity.getPathfindingPenalty(_snowmanxxxx);
                  if (_snowmanxxxx != PathNodeType.OPEN && _snowmanxxxxx >= 0.0F) {
                     _snowmanx = this.getNode(x, y, z);
                     _snowmanx.type = _snowmanxxxx;
                     _snowmanx.penalty = Math.max(_snowmanx.penalty, _snowmanxxxxx);
                     break;
                  }

                  if (_snowmanxxxxx < 0.0F) {
                     PathNode _snowmanxxxxxxxxx = this.getNode(x, y, z);
                     _snowmanxxxxxxxxx.type = PathNodeType.BLOCKED;
                     _snowmanxxxxxxxxx.penalty = -1.0F;
                     return _snowmanxxxxxxxxx;
                  }
               }
            }

            if (_snowmanxxxx == PathNodeType.FENCE) {
               _snowmanx = this.getNode(x, y, z);
               _snowmanx.visited = true;
               _snowmanx.type = _snowmanxxxx;
               _snowmanx.penalty = _snowmanxxxx.getDefaultPenalty();
            }

            return _snowmanx;
         }
      }
   }

   private boolean method_29304(Box _snowman) {
      return (Boolean)this.field_25191.computeIfAbsent(_snowman, _snowmanx -> !this.cachedWorld.isSpaceEmpty(this.entity, _snowman));
   }

   @Override
   public PathNodeType getNodeType(
      BlockView world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors
   ) {
      EnumSet<PathNodeType> _snowman = EnumSet.noneOf(PathNodeType.class);
      PathNodeType _snowmanx = PathNodeType.BLOCKED;
      BlockPos _snowmanxx = mob.getBlockPos();
      _snowmanx = this.findNearbyNodeTypes(world, x, y, z, sizeX, sizeY, sizeZ, canOpenDoors, canEnterOpenDoors, _snowman, _snowmanx, _snowmanxx);
      if (_snowman.contains(PathNodeType.FENCE)) {
         return PathNodeType.FENCE;
      } else if (_snowman.contains(PathNodeType.UNPASSABLE_RAIL)) {
         return PathNodeType.UNPASSABLE_RAIL;
      } else {
         PathNodeType _snowmanxxx = PathNodeType.BLOCKED;

         for (PathNodeType _snowmanxxxx : _snowman) {
            if (mob.getPathfindingPenalty(_snowmanxxxx) < 0.0F) {
               return _snowmanxxxx;
            }

            if (mob.getPathfindingPenalty(_snowmanxxxx) >= mob.getPathfindingPenalty(_snowmanxxx)) {
               _snowmanxxx = _snowmanxxxx;
            }
         }

         return _snowmanx == PathNodeType.OPEN && mob.getPathfindingPenalty(_snowmanxxx) == 0.0F && sizeX <= 1 ? PathNodeType.OPEN : _snowmanxxx;
      }
   }

   public PathNodeType findNearbyNodeTypes(
      BlockView world,
      int x,
      int y,
      int z,
      int sizeX,
      int sizeY,
      int sizeZ,
      boolean canOpenDoors,
      boolean canEnterOpenDoors,
      EnumSet<PathNodeType> nearbyTypes,
      PathNodeType type,
      BlockPos pos
   ) {
      for (int _snowman = 0; _snowman < sizeX; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < sizeY; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < sizeZ; _snowmanxx++) {
               int _snowmanxxx = _snowman + x;
               int _snowmanxxxx = _snowmanx + y;
               int _snowmanxxxxx = _snowmanxx + z;
               PathNodeType _snowmanxxxxxx = this.getDefaultNodeType(world, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
               _snowmanxxxxxx = this.adjustNodeType(world, canOpenDoors, canEnterOpenDoors, pos, _snowmanxxxxxx);
               if (_snowman == 0 && _snowmanx == 0 && _snowmanxx == 0) {
                  type = _snowmanxxxxxx;
               }

               nearbyTypes.add(_snowmanxxxxxx);
            }
         }
      }

      return type;
   }

   protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors, BlockPos pos, PathNodeType type) {
      if (type == PathNodeType.DOOR_WOOD_CLOSED && canOpenDoors && canEnterOpenDoors) {
         type = PathNodeType.WALKABLE_DOOR;
      }

      if (type == PathNodeType.DOOR_OPEN && !canEnterOpenDoors) {
         type = PathNodeType.BLOCKED;
      }

      if (type == PathNodeType.RAIL
         && !(world.getBlockState(pos).getBlock() instanceof AbstractRailBlock)
         && !(world.getBlockState(pos.down()).getBlock() instanceof AbstractRailBlock)) {
         type = PathNodeType.UNPASSABLE_RAIL;
      }

      if (type == PathNodeType.LEAVES) {
         type = PathNodeType.BLOCKED;
      }

      return type;
   }

   private PathNodeType getNodeType(MobEntity entity, BlockPos pos) {
      return this.method_29303(entity, pos.getX(), pos.getY(), pos.getZ());
   }

   private PathNodeType method_29303(MobEntity _snowman, int _snowman, int _snowman, int _snowman) {
      return (PathNodeType)this.field_25190
         .computeIfAbsent(
            BlockPos.asLong(_snowman, _snowman, _snowman),
            _snowmanxxxx -> this.getNodeType(
                  this.cachedWorld,
                  _snowman,
                  _snowman,
                  _snowman,
                  _snowman,
                  this.entityBlockXSize,
                  this.entityBlockYSize,
                  this.entityBlockZSize,
                  this.canOpenDoors(),
                  this.canEnterOpenDoors()
               )
         );
   }

   @Override
   public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
      return getLandNodeType(world, new BlockPos.Mutable(x, y, z));
   }

   public static PathNodeType getLandNodeType(BlockView _snowman, BlockPos.Mutable _snowman) {
      int _snowmanxx = _snowman.getX();
      int _snowmanxxx = _snowman.getY();
      int _snowmanxxxx = _snowman.getZ();
      PathNodeType _snowmanxxxxx = getCommonNodeType(_snowman, _snowman);
      if (_snowmanxxxxx == PathNodeType.OPEN && _snowmanxxx >= 1) {
         PathNodeType _snowmanxxxxxx = getCommonNodeType(_snowman, _snowman.set(_snowmanxx, _snowmanxxx - 1, _snowmanxxxx));
         _snowmanxxxxx = _snowmanxxxxxx != PathNodeType.WALKABLE && _snowmanxxxxxx != PathNodeType.OPEN && _snowmanxxxxxx != PathNodeType.WATER && _snowmanxxxxxx != PathNodeType.LAVA
            ? PathNodeType.WALKABLE
            : PathNodeType.OPEN;
         if (_snowmanxxxxxx == PathNodeType.DAMAGE_FIRE) {
            _snowmanxxxxx = PathNodeType.DAMAGE_FIRE;
         }

         if (_snowmanxxxxxx == PathNodeType.DAMAGE_CACTUS) {
            _snowmanxxxxx = PathNodeType.DAMAGE_CACTUS;
         }

         if (_snowmanxxxxxx == PathNodeType.DAMAGE_OTHER) {
            _snowmanxxxxx = PathNodeType.DAMAGE_OTHER;
         }

         if (_snowmanxxxxxx == PathNodeType.STICKY_HONEY) {
            _snowmanxxxxx = PathNodeType.STICKY_HONEY;
         }
      }

      if (_snowmanxxxxx == PathNodeType.WALKABLE) {
         _snowmanxxxxx = getNodeTypeFromNeighbors(_snowman, _snowman.set(_snowmanxx, _snowmanxxx, _snowmanxxxx), _snowmanxxxxx);
      }

      return _snowmanxxxxx;
   }

   public static PathNodeType getNodeTypeFromNeighbors(BlockView _snowman, BlockPos.Mutable _snowman, PathNodeType _snowman) {
      int _snowmanxxx = _snowman.getX();
      int _snowmanxxxx = _snowman.getY();
      int _snowmanxxxxx = _snowman.getZ();

      for (int _snowmanxxxxxx = -1; _snowmanxxxxxx <= 1; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = -1; _snowmanxxxxxxx <= 1; _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = -1; _snowmanxxxxxxxx <= 1; _snowmanxxxxxxxx++) {
               if (_snowmanxxxxxx != 0 || _snowmanxxxxxxxx != 0) {
                  _snowman.set(_snowmanxxx + _snowmanxxxxxx, _snowmanxxxx + _snowmanxxxxxxx, _snowmanxxxxx + _snowmanxxxxxxxx);
                  BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(_snowman);
                  if (_snowmanxxxxxxxxx.isOf(Blocks.CACTUS)) {
                     return PathNodeType.DANGER_CACTUS;
                  }

                  if (_snowmanxxxxxxxxx.isOf(Blocks.SWEET_BERRY_BUSH)) {
                     return PathNodeType.DANGER_OTHER;
                  }

                  if (method_27138(_snowmanxxxxxxxxx)) {
                     return PathNodeType.DANGER_FIRE;
                  }

                  if (_snowman.getFluidState(_snowman).isIn(FluidTags.WATER)) {
                     return PathNodeType.WATER_BORDER;
                  }
               }
            }
         }
      }

      return _snowman;
   }

   protected static PathNodeType getCommonNodeType(BlockView _snowman, BlockPos _snowman) {
      BlockState _snowmanxx = _snowman.getBlockState(_snowman);
      Block _snowmanxxx = _snowmanxx.getBlock();
      Material _snowmanxxxx = _snowmanxx.getMaterial();
      if (_snowmanxx.isAir()) {
         return PathNodeType.OPEN;
      } else if (_snowmanxx.isIn(BlockTags.TRAPDOORS) || _snowmanxx.isOf(Blocks.LILY_PAD)) {
         return PathNodeType.TRAPDOOR;
      } else if (_snowmanxx.isOf(Blocks.CACTUS)) {
         return PathNodeType.DAMAGE_CACTUS;
      } else if (_snowmanxx.isOf(Blocks.SWEET_BERRY_BUSH)) {
         return PathNodeType.DAMAGE_OTHER;
      } else if (_snowmanxx.isOf(Blocks.HONEY_BLOCK)) {
         return PathNodeType.STICKY_HONEY;
      } else if (_snowmanxx.isOf(Blocks.COCOA)) {
         return PathNodeType.COCOA;
      } else {
         FluidState _snowmanxxxxx = _snowman.getFluidState(_snowman);
         if (_snowmanxxxxx.isIn(FluidTags.WATER)) {
            return PathNodeType.WATER;
         } else if (_snowmanxxxxx.isIn(FluidTags.LAVA)) {
            return PathNodeType.LAVA;
         } else if (method_27138(_snowmanxx)) {
            return PathNodeType.DAMAGE_FIRE;
         } else if (DoorBlock.isWoodenDoor(_snowmanxx) && !_snowmanxx.get(DoorBlock.OPEN)) {
            return PathNodeType.DOOR_WOOD_CLOSED;
         } else if (_snowmanxxx instanceof DoorBlock && _snowmanxxxx == Material.METAL && !_snowmanxx.get(DoorBlock.OPEN)) {
            return PathNodeType.DOOR_IRON_CLOSED;
         } else if (_snowmanxxx instanceof DoorBlock && _snowmanxx.get(DoorBlock.OPEN)) {
            return PathNodeType.DOOR_OPEN;
         } else if (_snowmanxxx instanceof AbstractRailBlock) {
            return PathNodeType.RAIL;
         } else if (_snowmanxxx instanceof LeavesBlock) {
            return PathNodeType.LEAVES;
         } else if (!_snowmanxxx.isIn(BlockTags.FENCES) && !_snowmanxxx.isIn(BlockTags.WALLS) && (!(_snowmanxxx instanceof FenceGateBlock) || _snowmanxx.get(FenceGateBlock.OPEN))) {
            return !_snowmanxx.canPathfindThrough(_snowman, _snowman, NavigationType.LAND) ? PathNodeType.BLOCKED : PathNodeType.OPEN;
         } else {
            return PathNodeType.FENCE;
         }
      }
   }

   private static boolean method_27138(BlockState _snowman) {
      return _snowman.isIn(BlockTags.FIRE) || _snowman.isOf(Blocks.LAVA) || _snowman.isOf(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(_snowman);
   }
}
