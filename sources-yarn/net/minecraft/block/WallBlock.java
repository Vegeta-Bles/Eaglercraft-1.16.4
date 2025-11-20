package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WallBlock extends Block implements Waterloggable {
   public static final BooleanProperty UP = Properties.UP;
   public static final EnumProperty<WallShape> EAST_SHAPE = Properties.EAST_WALL_SHAPE;
   public static final EnumProperty<WallShape> NORTH_SHAPE = Properties.NORTH_WALL_SHAPE;
   public static final EnumProperty<WallShape> SOUTH_SHAPE = Properties.SOUTH_WALL_SHAPE;
   public static final EnumProperty<WallShape> WEST_SHAPE = Properties.WEST_WALL_SHAPE;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   private final Map<BlockState, VoxelShape> shapeMap;
   private final Map<BlockState, VoxelShape> collisionShapeMap;
   private static final VoxelShape TALL_POST_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape TALL_NORTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
   private static final VoxelShape TALL_SOUTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
   private static final VoxelShape TALL_WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape TALL_EAST_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

   public WallBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(UP, Boolean.valueOf(true))
            .with(NORTH_SHAPE, WallShape.NONE)
            .with(EAST_SHAPE, WallShape.NONE)
            .with(SOUTH_SHAPE, WallShape.NONE)
            .with(WEST_SHAPE, WallShape.NONE)
            .with(WATERLOGGED, Boolean.valueOf(false))
      );
      this.shapeMap = this.getShapeMap(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
      this.collisionShapeMap = this.getShapeMap(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   private static VoxelShape method_24426(VoxelShape arg, WallShape arg2, VoxelShape arg3, VoxelShape arg4) {
      if (arg2 == WallShape.TALL) {
         return VoxelShapes.union(arg, arg4);
      } else {
         return arg2 == WallShape.LOW ? VoxelShapes.union(arg, arg3) : arg;
      }
   }

   private Map<BlockState, VoxelShape> getShapeMap(float f, float g, float h, float i, float j, float k) {
      float l = 8.0F - f;
      float m = 8.0F + f;
      float n = 8.0F - g;
      float o = 8.0F + g;
      VoxelShape lv = Block.createCuboidShape((double)l, 0.0, (double)l, (double)m, (double)h, (double)m);
      VoxelShape lv2 = Block.createCuboidShape((double)n, (double)i, 0.0, (double)o, (double)j, (double)o);
      VoxelShape lv3 = Block.createCuboidShape((double)n, (double)i, (double)n, (double)o, (double)j, 16.0);
      VoxelShape lv4 = Block.createCuboidShape(0.0, (double)i, (double)n, (double)o, (double)j, (double)o);
      VoxelShape lv5 = Block.createCuboidShape((double)n, (double)i, (double)n, 16.0, (double)j, (double)o);
      VoxelShape lv6 = Block.createCuboidShape((double)n, (double)i, 0.0, (double)o, (double)k, (double)o);
      VoxelShape lv7 = Block.createCuboidShape((double)n, (double)i, (double)n, (double)o, (double)k, 16.0);
      VoxelShape lv8 = Block.createCuboidShape(0.0, (double)i, (double)n, (double)o, (double)k, (double)o);
      VoxelShape lv9 = Block.createCuboidShape((double)n, (double)i, (double)n, 16.0, (double)k, (double)o);
      Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

      for (Boolean boolean_ : UP.getValues()) {
         for (WallShape lv10 : EAST_SHAPE.getValues()) {
            for (WallShape lv11 : NORTH_SHAPE.getValues()) {
               for (WallShape lv12 : WEST_SHAPE.getValues()) {
                  for (WallShape lv13 : SOUTH_SHAPE.getValues()) {
                     VoxelShape lv14 = VoxelShapes.empty();
                     lv14 = method_24426(lv14, lv10, lv5, lv9);
                     lv14 = method_24426(lv14, lv12, lv4, lv8);
                     lv14 = method_24426(lv14, lv11, lv2, lv6);
                     lv14 = method_24426(lv14, lv13, lv3, lv7);
                     if (boolean_) {
                        lv14 = VoxelShapes.union(lv14, lv);
                     }

                     BlockState lv15 = this.getDefaultState()
                        .with(UP, boolean_)
                        .with(EAST_SHAPE, lv10)
                        .with(WEST_SHAPE, lv12)
                        .with(NORTH_SHAPE, lv11)
                        .with(SOUTH_SHAPE, lv13);
                     builder.put(lv15.with(WATERLOGGED, Boolean.valueOf(false)), lv14);
                     builder.put(lv15.with(WATERLOGGED, Boolean.valueOf(true)), lv14);
                  }
               }
            }
         }
      }

      return builder.build();
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.shapeMap.get(state);
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.collisionShapeMap.get(state);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   private boolean shouldConnectTo(BlockState state, boolean faceFullSquare, Direction side) {
      Block lv = state.getBlock();
      boolean bl2 = lv instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, side);
      return state.isIn(BlockTags.WALLS) || !cannotConnect(lv) && faceFullSquare || lv instanceof PaneBlock || bl2;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      WorldView lv = ctx.getWorld();
      BlockPos lv2 = ctx.getBlockPos();
      FluidState lv3 = ctx.getWorld().getFluidState(ctx.getBlockPos());
      BlockPos lv4 = lv2.north();
      BlockPos lv5 = lv2.east();
      BlockPos lv6 = lv2.south();
      BlockPos lv7 = lv2.west();
      BlockPos lv8 = lv2.up();
      BlockState lv9 = lv.getBlockState(lv4);
      BlockState lv10 = lv.getBlockState(lv5);
      BlockState lv11 = lv.getBlockState(lv6);
      BlockState lv12 = lv.getBlockState(lv7);
      BlockState lv13 = lv.getBlockState(lv8);
      boolean bl = this.shouldConnectTo(lv9, lv9.isSideSolidFullSquare(lv, lv4, Direction.SOUTH), Direction.SOUTH);
      boolean bl2 = this.shouldConnectTo(lv10, lv10.isSideSolidFullSquare(lv, lv5, Direction.WEST), Direction.WEST);
      boolean bl3 = this.shouldConnectTo(lv11, lv11.isSideSolidFullSquare(lv, lv6, Direction.NORTH), Direction.NORTH);
      boolean bl4 = this.shouldConnectTo(lv12, lv12.isSideSolidFullSquare(lv, lv7, Direction.EAST), Direction.EAST);
      BlockState lv14 = this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(lv3.getFluid() == Fluids.WATER));
      return this.method_24422(lv, lv14, lv8, lv13, bl, bl2, bl3, bl4);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      if (direction == Direction.DOWN) {
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         return direction == Direction.UP
            ? this.method_24421(world, state, posFrom, newState)
            : this.method_24423(world, pos, state, posFrom, newState, direction);
      }
   }

   private static boolean method_24424(BlockState arg, Property<WallShape> arg2) {
      return arg.get(arg2) != WallShape.NONE;
   }

   private static boolean method_24427(VoxelShape arg, VoxelShape arg2) {
      return !VoxelShapes.matchesAnywhere(arg2, arg, BooleanBiFunction.ONLY_FIRST);
   }

   private BlockState method_24421(WorldView arg, BlockState arg2, BlockPos arg3, BlockState arg4) {
      boolean bl = method_24424(arg2, NORTH_SHAPE);
      boolean bl2 = method_24424(arg2, EAST_SHAPE);
      boolean bl3 = method_24424(arg2, SOUTH_SHAPE);
      boolean bl4 = method_24424(arg2, WEST_SHAPE);
      return this.method_24422(arg, arg2, arg3, arg4, bl, bl2, bl3, bl4);
   }

   private BlockState method_24423(WorldView arg, BlockPos arg2, BlockState arg3, BlockPos arg4, BlockState arg5, Direction arg6) {
      Direction lv = arg6.getOpposite();
      boolean bl = arg6 == Direction.NORTH ? this.shouldConnectTo(arg5, arg5.isSideSolidFullSquare(arg, arg4, lv), lv) : method_24424(arg3, NORTH_SHAPE);
      boolean bl2 = arg6 == Direction.EAST ? this.shouldConnectTo(arg5, arg5.isSideSolidFullSquare(arg, arg4, lv), lv) : method_24424(arg3, EAST_SHAPE);
      boolean bl3 = arg6 == Direction.SOUTH ? this.shouldConnectTo(arg5, arg5.isSideSolidFullSquare(arg, arg4, lv), lv) : method_24424(arg3, SOUTH_SHAPE);
      boolean bl4 = arg6 == Direction.WEST ? this.shouldConnectTo(arg5, arg5.isSideSolidFullSquare(arg, arg4, lv), lv) : method_24424(arg3, WEST_SHAPE);
      BlockPos lv2 = arg2.up();
      BlockState lv3 = arg.getBlockState(lv2);
      return this.method_24422(arg, arg3, lv2, lv3, bl, bl2, bl3, bl4);
   }

   private BlockState method_24422(WorldView arg, BlockState arg2, BlockPos arg3, BlockState arg4, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
      VoxelShape lv = arg4.getCollisionShape(arg, arg3).getFace(Direction.DOWN);
      BlockState lv2 = this.method_24425(arg2, bl, bl2, bl3, bl4, lv);
      return lv2.with(UP, Boolean.valueOf(this.method_27092(lv2, arg4, lv)));
   }

   private boolean method_27092(BlockState arg, BlockState arg2, VoxelShape arg3) {
      boolean bl = arg2.getBlock() instanceof WallBlock && arg2.get(UP);
      if (bl) {
         return true;
      } else {
         WallShape lv = arg.get(NORTH_SHAPE);
         WallShape lv2 = arg.get(SOUTH_SHAPE);
         WallShape lv3 = arg.get(EAST_SHAPE);
         WallShape lv4 = arg.get(WEST_SHAPE);
         boolean bl2 = lv2 == WallShape.NONE;
         boolean bl3 = lv4 == WallShape.NONE;
         boolean bl4 = lv3 == WallShape.NONE;
         boolean bl5 = lv == WallShape.NONE;
         boolean bl6 = bl5 && bl2 && bl3 && bl4 || bl5 != bl2 || bl3 != bl4;
         if (bl6) {
            return true;
         } else {
            boolean bl7 = lv == WallShape.TALL && lv2 == WallShape.TALL || lv3 == WallShape.TALL && lv4 == WallShape.TALL;
            return bl7 ? false : arg2.getBlock().isIn(BlockTags.WALL_POST_OVERRIDE) || method_24427(arg3, TALL_POST_SHAPE);
         }
      }
   }

   private BlockState method_24425(BlockState arg, boolean bl, boolean bl2, boolean bl3, boolean bl4, VoxelShape arg2) {
      return arg.with(NORTH_SHAPE, this.method_24428(bl, arg2, TALL_NORTH_SHAPE))
         .with(EAST_SHAPE, this.method_24428(bl2, arg2, TALL_EAST_SHAPE))
         .with(SOUTH_SHAPE, this.method_24428(bl3, arg2, TALL_SOUTH_SHAPE))
         .with(WEST_SHAPE, this.method_24428(bl4, arg2, TALL_WEST_SHAPE));
   }

   private WallShape method_24428(boolean bl, VoxelShape arg, VoxelShape arg2) {
      if (bl) {
         return method_24427(arg, arg2) ? WallShape.TALL : WallShape.LOW;
      } else {
         return WallShape.NONE;
      }
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
      return !state.get(WATERLOGGED);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(UP, NORTH_SHAPE, EAST_SHAPE, WEST_SHAPE, SOUTH_SHAPE, WATERLOGGED);
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      switch (rotation) {
         case CLOCKWISE_180:
            return state.with(NORTH_SHAPE, state.get(SOUTH_SHAPE))
               .with(EAST_SHAPE, state.get(WEST_SHAPE))
               .with(SOUTH_SHAPE, state.get(NORTH_SHAPE))
               .with(WEST_SHAPE, state.get(EAST_SHAPE));
         case COUNTERCLOCKWISE_90:
            return state.with(NORTH_SHAPE, state.get(EAST_SHAPE))
               .with(EAST_SHAPE, state.get(SOUTH_SHAPE))
               .with(SOUTH_SHAPE, state.get(WEST_SHAPE))
               .with(WEST_SHAPE, state.get(NORTH_SHAPE));
         case CLOCKWISE_90:
            return state.with(NORTH_SHAPE, state.get(WEST_SHAPE))
               .with(EAST_SHAPE, state.get(NORTH_SHAPE))
               .with(SOUTH_SHAPE, state.get(EAST_SHAPE))
               .with(WEST_SHAPE, state.get(SOUTH_SHAPE));
         default:
            return state;
      }
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      switch (mirror) {
         case LEFT_RIGHT:
            return state.with(NORTH_SHAPE, state.get(SOUTH_SHAPE)).with(SOUTH_SHAPE, state.get(NORTH_SHAPE));
         case FRONT_BACK:
            return state.with(EAST_SHAPE, state.get(WEST_SHAPE)).with(WEST_SHAPE, state.get(EAST_SHAPE));
         default:
            return super.mirror(state, mirror);
      }
   }
}
