package net.minecraft.block;

import net.minecraft.block.enums.RailShape;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoweredRailBlock extends AbstractRailBlock {
   public static final EnumProperty<RailShape> SHAPE = Properties.STRAIGHT_RAIL_SHAPE;
   public static final BooleanProperty POWERED = Properties.POWERED;

   protected PoweredRailBlock(AbstractBlock.Settings _snowman) {
      super(true, _snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(SHAPE, RailShape.NORTH_SOUTH).with(POWERED, Boolean.valueOf(false)));
   }

   protected boolean isPoweredByOtherRails(World world, BlockPos pos, BlockState state, boolean boolean4, int distance) {
      if (distance >= 8) {
         return false;
      } else {
         int _snowman = pos.getX();
         int _snowmanx = pos.getY();
         int _snowmanxx = pos.getZ();
         boolean _snowmanxxx = true;
         RailShape _snowmanxxxx = state.get(SHAPE);
         switch (_snowmanxxxx) {
            case NORTH_SOUTH:
               if (boolean4) {
                  _snowmanxx++;
               } else {
                  _snowmanxx--;
               }
               break;
            case EAST_WEST:
               if (boolean4) {
                  _snowman--;
               } else {
                  _snowman++;
               }
               break;
            case ASCENDING_EAST:
               if (boolean4) {
                  _snowman--;
               } else {
                  _snowman++;
                  _snowmanx++;
                  _snowmanxxx = false;
               }

               _snowmanxxxx = RailShape.EAST_WEST;
               break;
            case ASCENDING_WEST:
               if (boolean4) {
                  _snowman--;
                  _snowmanx++;
                  _snowmanxxx = false;
               } else {
                  _snowman++;
               }

               _snowmanxxxx = RailShape.EAST_WEST;
               break;
            case ASCENDING_NORTH:
               if (boolean4) {
                  _snowmanxx++;
               } else {
                  _snowmanxx--;
                  _snowmanx++;
                  _snowmanxxx = false;
               }

               _snowmanxxxx = RailShape.NORTH_SOUTH;
               break;
            case ASCENDING_SOUTH:
               if (boolean4) {
                  _snowmanxx++;
                  _snowmanx++;
                  _snowmanxxx = false;
               } else {
                  _snowmanxx--;
               }

               _snowmanxxxx = RailShape.NORTH_SOUTH;
         }

         return this.isPoweredByOtherRails(world, new BlockPos(_snowman, _snowmanx, _snowmanxx), boolean4, distance, _snowmanxxxx)
            ? true
            : _snowmanxxx && this.isPoweredByOtherRails(world, new BlockPos(_snowman, _snowmanx - 1, _snowmanxx), boolean4, distance, _snowmanxxxx);
      }
   }

   protected boolean isPoweredByOtherRails(World world, BlockPos pos, boolean _snowman, int distance, RailShape shape) {
      BlockState _snowmanx = world.getBlockState(pos);
      if (!_snowmanx.isOf(this)) {
         return false;
      } else {
         RailShape _snowmanxx = _snowmanx.get(SHAPE);
         if (shape != RailShape.EAST_WEST || _snowmanxx != RailShape.NORTH_SOUTH && _snowmanxx != RailShape.ASCENDING_NORTH && _snowmanxx != RailShape.ASCENDING_SOUTH) {
            if (shape != RailShape.NORTH_SOUTH || _snowmanxx != RailShape.EAST_WEST && _snowmanxx != RailShape.ASCENDING_EAST && _snowmanxx != RailShape.ASCENDING_WEST) {
               if (!_snowmanx.get(POWERED)) {
                  return false;
               } else {
                  return world.isReceivingRedstonePower(pos) ? true : this.isPoweredByOtherRails(world, pos, _snowmanx, _snowman, distance + 1);
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   protected void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor) {
      boolean _snowman = state.get(POWERED);
      boolean _snowmanx = world.isReceivingRedstonePower(pos)
         || this.isPoweredByOtherRails(world, pos, state, true, 0)
         || this.isPoweredByOtherRails(world, pos, state, false, 0);
      if (_snowmanx != _snowman) {
         world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(_snowmanx)), 3);
         world.updateNeighborsAlways(pos.down(), this);
         if (state.get(SHAPE).isAscending()) {
            world.updateNeighborsAlways(pos.up(), this);
         }
      }
   }

   @Override
   public Property<RailShape> getShapeProperty() {
      return SHAPE;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      switch (rotation) {
         case CLOCKWISE_180:
            switch ((RailShape)state.get(SHAPE)) {
               case ASCENDING_EAST:
                  return state.with(SHAPE, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return state.with(SHAPE, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
                  return state.with(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return state.with(SHAPE, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return state.with(SHAPE, RailShape.NORTH_WEST);
               case SOUTH_WEST:
                  return state.with(SHAPE, RailShape.NORTH_EAST);
               case NORTH_WEST:
                  return state.with(SHAPE, RailShape.SOUTH_EAST);
               case NORTH_EAST:
                  return state.with(SHAPE, RailShape.SOUTH_WEST);
            }
         case COUNTERCLOCKWISE_90:
            switch ((RailShape)state.get(SHAPE)) {
               case NORTH_SOUTH:
                  return state.with(SHAPE, RailShape.EAST_WEST);
               case EAST_WEST:
                  return state.with(SHAPE, RailShape.NORTH_SOUTH);
               case ASCENDING_EAST:
                  return state.with(SHAPE, RailShape.ASCENDING_NORTH);
               case ASCENDING_WEST:
                  return state.with(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_NORTH:
                  return state.with(SHAPE, RailShape.ASCENDING_WEST);
               case ASCENDING_SOUTH:
                  return state.with(SHAPE, RailShape.ASCENDING_EAST);
               case SOUTH_EAST:
                  return state.with(SHAPE, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return state.with(SHAPE, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return state.with(SHAPE, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return state.with(SHAPE, RailShape.NORTH_WEST);
            }
         case CLOCKWISE_90:
            switch ((RailShape)state.get(SHAPE)) {
               case NORTH_SOUTH:
                  return state.with(SHAPE, RailShape.EAST_WEST);
               case EAST_WEST:
                  return state.with(SHAPE, RailShape.NORTH_SOUTH);
               case ASCENDING_EAST:
                  return state.with(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_WEST:
                  return state.with(SHAPE, RailShape.ASCENDING_NORTH);
               case ASCENDING_NORTH:
                  return state.with(SHAPE, RailShape.ASCENDING_EAST);
               case ASCENDING_SOUTH:
                  return state.with(SHAPE, RailShape.ASCENDING_WEST);
               case SOUTH_EAST:
                  return state.with(SHAPE, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return state.with(SHAPE, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return state.with(SHAPE, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return state.with(SHAPE, RailShape.SOUTH_EAST);
            }
         default:
            return state;
      }
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      RailShape _snowman = state.get(SHAPE);
      switch (mirror) {
         case LEFT_RIGHT:
            switch (_snowman) {
               case ASCENDING_NORTH:
                  return state.with(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return state.with(SHAPE, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return state.with(SHAPE, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return state.with(SHAPE, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return state.with(SHAPE, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return state.with(SHAPE, RailShape.SOUTH_EAST);
               default:
                  return super.mirror(state, mirror);
            }
         case FRONT_BACK:
            switch (_snowman) {
               case ASCENDING_EAST:
                  return state.with(SHAPE, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return state.with(SHAPE, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
               case ASCENDING_SOUTH:
               default:
                  break;
               case SOUTH_EAST:
                  return state.with(SHAPE, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return state.with(SHAPE, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return state.with(SHAPE, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return state.with(SHAPE, RailShape.NORTH_WEST);
            }
      }

      return super.mirror(state, mirror);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(SHAPE, POWERED);
   }
}
