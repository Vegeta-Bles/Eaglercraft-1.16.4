package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.enums.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RailPlacementHelper {
   private final World world;
   private final BlockPos pos;
   private final AbstractRailBlock block;
   private BlockState state;
   private final boolean allowCurves;
   private final List<BlockPos> neighbors = Lists.newArrayList();

   public RailPlacementHelper(World world, BlockPos pos, BlockState state) {
      this.world = world;
      this.pos = pos;
      this.state = state;
      this.block = (AbstractRailBlock)state.getBlock();
      RailShape _snowman = state.get(this.block.getShapeProperty());
      this.allowCurves = this.block.canMakeCurves();
      this.computeNeighbors(_snowman);
   }

   public List<BlockPos> getNeighbors() {
      return this.neighbors;
   }

   private void computeNeighbors(RailShape shape) {
      this.neighbors.clear();
      switch (shape) {
         case NORTH_SOUTH:
            this.neighbors.add(this.pos.north());
            this.neighbors.add(this.pos.south());
            break;
         case EAST_WEST:
            this.neighbors.add(this.pos.west());
            this.neighbors.add(this.pos.east());
            break;
         case ASCENDING_EAST:
            this.neighbors.add(this.pos.west());
            this.neighbors.add(this.pos.east().up());
            break;
         case ASCENDING_WEST:
            this.neighbors.add(this.pos.west().up());
            this.neighbors.add(this.pos.east());
            break;
         case ASCENDING_NORTH:
            this.neighbors.add(this.pos.north().up());
            this.neighbors.add(this.pos.south());
            break;
         case ASCENDING_SOUTH:
            this.neighbors.add(this.pos.north());
            this.neighbors.add(this.pos.south().up());
            break;
         case SOUTH_EAST:
            this.neighbors.add(this.pos.east());
            this.neighbors.add(this.pos.south());
            break;
         case SOUTH_WEST:
            this.neighbors.add(this.pos.west());
            this.neighbors.add(this.pos.south());
            break;
         case NORTH_WEST:
            this.neighbors.add(this.pos.west());
            this.neighbors.add(this.pos.north());
            break;
         case NORTH_EAST:
            this.neighbors.add(this.pos.east());
            this.neighbors.add(this.pos.north());
      }
   }

   private void updateNeighborPositions() {
      for (int _snowman = 0; _snowman < this.neighbors.size(); _snowman++) {
         RailPlacementHelper _snowmanx = this.getNeighboringRail(this.neighbors.get(_snowman));
         if (_snowmanx != null && _snowmanx.isNeighbor(this)) {
            this.neighbors.set(_snowman, _snowmanx.pos);
         } else {
            this.neighbors.remove(_snowman--);
         }
      }
   }

   private boolean isVerticallyNearRail(BlockPos pos) {
      return AbstractRailBlock.isRail(this.world, pos) || AbstractRailBlock.isRail(this.world, pos.up()) || AbstractRailBlock.isRail(this.world, pos.down());
   }

   @Nullable
   private RailPlacementHelper getNeighboringRail(BlockPos pos) {
      BlockState _snowman = this.world.getBlockState(pos);
      if (AbstractRailBlock.isRail(_snowman)) {
         return new RailPlacementHelper(this.world, pos, _snowman);
      } else {
         BlockPos var2 = pos.up();
         _snowman = this.world.getBlockState(var2);
         if (AbstractRailBlock.isRail(_snowman)) {
            return new RailPlacementHelper(this.world, var2, _snowman);
         } else {
            var2 = pos.down();
            _snowman = this.world.getBlockState(var2);
            return AbstractRailBlock.isRail(_snowman) ? new RailPlacementHelper(this.world, var2, _snowman) : null;
         }
      }
   }

   private boolean isNeighbor(RailPlacementHelper other) {
      return this.isNeighbor(other.pos);
   }

   private boolean isNeighbor(BlockPos pos) {
      for (int _snowman = 0; _snowman < this.neighbors.size(); _snowman++) {
         BlockPos _snowmanx = this.neighbors.get(_snowman);
         if (_snowmanx.getX() == pos.getX() && _snowmanx.getZ() == pos.getZ()) {
            return true;
         }
      }

      return false;
   }

   protected int getNeighborCount() {
      int _snowman = 0;

      for (Direction _snowmanx : Direction.Type.HORIZONTAL) {
         if (this.isVerticallyNearRail(this.pos.offset(_snowmanx))) {
            _snowman++;
         }
      }

      return _snowman;
   }

   private boolean canConnect(RailPlacementHelper placementHelper) {
      return this.isNeighbor(placementHelper) || this.neighbors.size() != 2;
   }

   private void computeRailShape(RailPlacementHelper placementHelper) {
      this.neighbors.add(placementHelper.pos);
      BlockPos _snowman = this.pos.north();
      BlockPos _snowmanx = this.pos.south();
      BlockPos _snowmanxx = this.pos.west();
      BlockPos _snowmanxxx = this.pos.east();
      boolean _snowmanxxxx = this.isNeighbor(_snowman);
      boolean _snowmanxxxxx = this.isNeighbor(_snowmanx);
      boolean _snowmanxxxxxx = this.isNeighbor(_snowmanxx);
      boolean _snowmanxxxxxxx = this.isNeighbor(_snowmanxxx);
      RailShape _snowmanxxxxxxxx = null;
      if (_snowmanxxxx || _snowmanxxxxx) {
         _snowmanxxxxxxxx = RailShape.NORTH_SOUTH;
      }

      if (_snowmanxxxxxx || _snowmanxxxxxxx) {
         _snowmanxxxxxxxx = RailShape.EAST_WEST;
      }

      if (!this.allowCurves) {
         if (_snowmanxxxxx && _snowmanxxxxxxx && !_snowmanxxxx && !_snowmanxxxxxx) {
            _snowmanxxxxxxxx = RailShape.SOUTH_EAST;
         }

         if (_snowmanxxxxx && _snowmanxxxxxx && !_snowmanxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxx = RailShape.SOUTH_WEST;
         }

         if (_snowmanxxxx && _snowmanxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxx = RailShape.NORTH_WEST;
         }

         if (_snowmanxxxx && _snowmanxxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxx) {
            _snowmanxxxxxxxx = RailShape.NORTH_EAST;
         }
      }

      if (_snowmanxxxxxxxx == RailShape.NORTH_SOUTH) {
         if (AbstractRailBlock.isRail(this.world, _snowman.up())) {
            _snowmanxxxxxxxx = RailShape.ASCENDING_NORTH;
         }

         if (AbstractRailBlock.isRail(this.world, _snowmanx.up())) {
            _snowmanxxxxxxxx = RailShape.ASCENDING_SOUTH;
         }
      }

      if (_snowmanxxxxxxxx == RailShape.EAST_WEST) {
         if (AbstractRailBlock.isRail(this.world, _snowmanxxx.up())) {
            _snowmanxxxxxxxx = RailShape.ASCENDING_EAST;
         }

         if (AbstractRailBlock.isRail(this.world, _snowmanxx.up())) {
            _snowmanxxxxxxxx = RailShape.ASCENDING_WEST;
         }
      }

      if (_snowmanxxxxxxxx == null) {
         _snowmanxxxxxxxx = RailShape.NORTH_SOUTH;
      }

      this.state = this.state.with(this.block.getShapeProperty(), _snowmanxxxxxxxx);
      this.world.setBlockState(this.pos, this.state, 3);
   }

   private boolean canConnect(BlockPos pos) {
      RailPlacementHelper _snowman = this.getNeighboringRail(pos);
      if (_snowman == null) {
         return false;
      } else {
         _snowman.updateNeighborPositions();
         return _snowman.canConnect(this);
      }
   }

   public RailPlacementHelper updateBlockState(boolean powered, boolean forceUpdate, RailShape _snowman) {
      BlockPos _snowmanx = this.pos.north();
      BlockPos _snowmanxx = this.pos.south();
      BlockPos _snowmanxxx = this.pos.west();
      BlockPos _snowmanxxxx = this.pos.east();
      boolean _snowmanxxxxx = this.canConnect(_snowmanx);
      boolean _snowmanxxxxxx = this.canConnect(_snowmanxx);
      boolean _snowmanxxxxxxx = this.canConnect(_snowmanxxx);
      boolean _snowmanxxxxxxxx = this.canConnect(_snowmanxxxx);
      RailShape _snowmanxxxxxxxxx = null;
      boolean _snowmanxxxxxxxxxx = _snowmanxxxxx || _snowmanxxxxxx;
      boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxx || _snowmanxxxxxxxx;
      if (_snowmanxxxxxxxxxx && !_snowmanxxxxxxxxxxx) {
         _snowmanxxxxxxxxx = RailShape.NORTH_SOUTH;
      }

      if (_snowmanxxxxxxxxxxx && !_snowmanxxxxxxxxxx) {
         _snowmanxxxxxxxxx = RailShape.EAST_WEST;
      }

      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxx && _snowmanxxxxxxxx;
      boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx && _snowmanxxxxxxx;
      boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx && _snowmanxxxxxxxx;
      boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxx && _snowmanxxxxxxx;
      if (!this.allowCurves) {
         if (_snowmanxxxxxxxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxxx = RailShape.SOUTH_EAST;
         }

         if (_snowmanxxxxxxxxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxxxx) {
            _snowmanxxxxxxxxx = RailShape.SOUTH_WEST;
         }

         if (_snowmanxxxxxxxxxxxxxxx && !_snowmanxxxxxx && !_snowmanxxxxxxxx) {
            _snowmanxxxxxxxxx = RailShape.NORTH_WEST;
         }

         if (_snowmanxxxxxxxxxxxxxx && !_snowmanxxxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxxx = RailShape.NORTH_EAST;
         }
      }

      if (_snowmanxxxxxxxxx == null) {
         if (_snowmanxxxxxxxxxx && _snowmanxxxxxxxxxxx) {
            _snowmanxxxxxxxxx = _snowman;
         } else if (_snowmanxxxxxxxxxx) {
            _snowmanxxxxxxxxx = RailShape.NORTH_SOUTH;
         } else if (_snowmanxxxxxxxxxxx) {
            _snowmanxxxxxxxxx = RailShape.EAST_WEST;
         }

         if (!this.allowCurves) {
            if (powered) {
               if (_snowmanxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.SOUTH_EAST;
               }

               if (_snowmanxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.SOUTH_WEST;
               }

               if (_snowmanxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.NORTH_EAST;
               }

               if (_snowmanxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.NORTH_WEST;
               }
            } else {
               if (_snowmanxxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.NORTH_WEST;
               }

               if (_snowmanxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.NORTH_EAST;
               }

               if (_snowmanxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.SOUTH_WEST;
               }

               if (_snowmanxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = RailShape.SOUTH_EAST;
               }
            }
         }
      }

      if (_snowmanxxxxxxxxx == RailShape.NORTH_SOUTH) {
         if (AbstractRailBlock.isRail(this.world, _snowmanx.up())) {
            _snowmanxxxxxxxxx = RailShape.ASCENDING_NORTH;
         }

         if (AbstractRailBlock.isRail(this.world, _snowmanxx.up())) {
            _snowmanxxxxxxxxx = RailShape.ASCENDING_SOUTH;
         }
      }

      if (_snowmanxxxxxxxxx == RailShape.EAST_WEST) {
         if (AbstractRailBlock.isRail(this.world, _snowmanxxxx.up())) {
            _snowmanxxxxxxxxx = RailShape.ASCENDING_EAST;
         }

         if (AbstractRailBlock.isRail(this.world, _snowmanxxx.up())) {
            _snowmanxxxxxxxxx = RailShape.ASCENDING_WEST;
         }
      }

      if (_snowmanxxxxxxxxx == null) {
         _snowmanxxxxxxxxx = _snowman;
      }

      this.computeNeighbors(_snowmanxxxxxxxxx);
      this.state = this.state.with(this.block.getShapeProperty(), _snowmanxxxxxxxxx);
      if (forceUpdate || this.world.getBlockState(this.pos) != this.state) {
         this.world.setBlockState(this.pos, this.state, 3);

         for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx < this.neighbors.size(); _snowmanxxxxxxxxxxxxxxxx++) {
            RailPlacementHelper _snowmanxxxxxxxxxxxxxxxxx = this.getNeighboringRail(this.neighbors.get(_snowmanxxxxxxxxxxxxxxxx));
            if (_snowmanxxxxxxxxxxxxxxxxx != null) {
               _snowmanxxxxxxxxxxxxxxxxx.updateNeighborPositions();
               if (_snowmanxxxxxxxxxxxxxxxxx.canConnect(this)) {
                  _snowmanxxxxxxxxxxxxxxxxx.computeRailShape(this);
               }
            }
         }
      }

      return this;
   }

   public BlockState getBlockState() {
      return this.state;
   }
}
