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

   protected PoweredRailBlock(AbstractBlock.Settings arg) {
      super(true, arg);
      this.setDefaultState(this.stateManager.getDefaultState().with(SHAPE, RailShape.NORTH_SOUTH).with(POWERED, Boolean.valueOf(false)));
   }

   protected boolean isPoweredByOtherRails(World world, BlockPos pos, BlockState state, boolean boolean4, int distance) {
      if (distance >= 8) {
         return false;
      } else {
         int j = pos.getX();
         int k = pos.getY();
         int l = pos.getZ();
         boolean bl2 = true;
         RailShape lv = state.get(SHAPE);
         switch (lv) {
            case NORTH_SOUTH:
               if (boolean4) {
                  l++;
               } else {
                  l--;
               }
               break;
            case EAST_WEST:
               if (boolean4) {
                  j--;
               } else {
                  j++;
               }
               break;
            case ASCENDING_EAST:
               if (boolean4) {
                  j--;
               } else {
                  j++;
                  k++;
                  bl2 = false;
               }

               lv = RailShape.EAST_WEST;
               break;
            case ASCENDING_WEST:
               if (boolean4) {
                  j--;
                  k++;
                  bl2 = false;
               } else {
                  j++;
               }

               lv = RailShape.EAST_WEST;
               break;
            case ASCENDING_NORTH:
               if (boolean4) {
                  l++;
               } else {
                  l--;
                  k++;
                  bl2 = false;
               }

               lv = RailShape.NORTH_SOUTH;
               break;
            case ASCENDING_SOUTH:
               if (boolean4) {
                  l++;
                  k++;
                  bl2 = false;
               } else {
                  l--;
               }

               lv = RailShape.NORTH_SOUTH;
         }

         return this.isPoweredByOtherRails(world, new BlockPos(j, k, l), boolean4, distance, lv)
            ? true
            : bl2 && this.isPoweredByOtherRails(world, new BlockPos(j, k - 1, l), boolean4, distance, lv);
      }
   }

   protected boolean isPoweredByOtherRails(World world, BlockPos pos, boolean bl, int distance, RailShape shape) {
      BlockState lv = world.getBlockState(pos);
      if (!lv.isOf(this)) {
         return false;
      } else {
         RailShape lv2 = lv.get(SHAPE);
         if (shape != RailShape.EAST_WEST || lv2 != RailShape.NORTH_SOUTH && lv2 != RailShape.ASCENDING_NORTH && lv2 != RailShape.ASCENDING_SOUTH) {
            if (shape != RailShape.NORTH_SOUTH || lv2 != RailShape.EAST_WEST && lv2 != RailShape.ASCENDING_EAST && lv2 != RailShape.ASCENDING_WEST) {
               if (!lv.get(POWERED)) {
                  return false;
               } else {
                  return world.isReceivingRedstonePower(pos) ? true : this.isPoweredByOtherRails(world, pos, lv, bl, distance + 1);
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
      boolean bl = state.get(POWERED);
      boolean bl2 = world.isReceivingRedstonePower(pos)
         || this.isPoweredByOtherRails(world, pos, state, true, 0)
         || this.isPoweredByOtherRails(world, pos, state, false, 0);
      if (bl2 != bl) {
         world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(bl2)), 3);
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
      RailShape lv = state.get(SHAPE);
      switch (mirror) {
         case LEFT_RIGHT:
            switch (lv) {
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
            switch (lv) {
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
