package net.minecraft.block;

import com.google.common.collect.UnmodifiableIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HorizontalConnectingBlock extends Block implements Waterloggable {
   public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
   public static final BooleanProperty EAST = ConnectingBlock.EAST;
   public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
   public static final BooleanProperty WEST = ConnectingBlock.WEST;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey().getAxis().isHorizontal())
      .collect(Util.toMap());
   protected final VoxelShape[] collisionShapes;
   protected final VoxelShape[] boundingShapes;
   private final Object2IntMap<BlockState> SHAPE_INDEX_CACHE = new Object2IntOpenHashMap();

   protected HorizontalConnectingBlock(
      float radius1, float radius2, float boundingHeight1, float boundingHeight2, float collisionHeight, AbstractBlock.Settings settings
   ) {
      super(settings);
      this.collisionShapes = this.createShapes(radius1, radius2, collisionHeight, 0.0F, collisionHeight);
      this.boundingShapes = this.createShapes(radius1, radius2, boundingHeight1, 0.0F, boundingHeight2);
      UnmodifiableIterator var7 = this.stateManager.getStates().iterator();

      while (var7.hasNext()) {
         BlockState lv = (BlockState)var7.next();
         this.getShapeIndex(lv);
      }
   }

   protected VoxelShape[] createShapes(float radius1, float radius2, float height1, float offset2, float height2) {
      float k = 8.0F - radius1;
      float l = 8.0F + radius1;
      float m = 8.0F - radius2;
      float n = 8.0F + radius2;
      VoxelShape lv = Block.createCuboidShape((double)k, 0.0, (double)k, (double)l, (double)height1, (double)l);
      VoxelShape lv2 = Block.createCuboidShape((double)m, (double)offset2, 0.0, (double)n, (double)height2, (double)n);
      VoxelShape lv3 = Block.createCuboidShape((double)m, (double)offset2, (double)m, (double)n, (double)height2, 16.0);
      VoxelShape lv4 = Block.createCuboidShape(0.0, (double)offset2, (double)m, (double)n, (double)height2, (double)n);
      VoxelShape lv5 = Block.createCuboidShape((double)m, (double)offset2, (double)m, 16.0, (double)height2, (double)n);
      VoxelShape lv6 = VoxelShapes.union(lv2, lv5);
      VoxelShape lv7 = VoxelShapes.union(lv3, lv4);
      VoxelShape[] lvs = new VoxelShape[]{
         VoxelShapes.empty(),
         lv3,
         lv4,
         lv7,
         lv2,
         VoxelShapes.union(lv3, lv2),
         VoxelShapes.union(lv4, lv2),
         VoxelShapes.union(lv7, lv2),
         lv5,
         VoxelShapes.union(lv3, lv5),
         VoxelShapes.union(lv4, lv5),
         VoxelShapes.union(lv7, lv5),
         lv6,
         VoxelShapes.union(lv3, lv6),
         VoxelShapes.union(lv4, lv6),
         VoxelShapes.union(lv7, lv6)
      };

      for (int o = 0; o < 16; o++) {
         lvs[o] = VoxelShapes.union(lv, lvs[o]);
      }

      return lvs;
   }

   @Override
   public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
      return !state.get(WATERLOGGED);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.boundingShapes[this.getShapeIndex(state)];
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.collisionShapes[this.getShapeIndex(state)];
   }

   private static int getDirectionMask(Direction dir) {
      return 1 << dir.getHorizontal();
   }

   protected int getShapeIndex(BlockState state) {
      return this.SHAPE_INDEX_CACHE.computeIntIfAbsent(state, arg -> {
         int i = 0;
         if (arg.get(NORTH)) {
            i |= getDirectionMask(Direction.NORTH);
         }

         if (arg.get(EAST)) {
            i |= getDirectionMask(Direction.EAST);
         }

         if (arg.get(SOUTH)) {
            i |= getDirectionMask(Direction.SOUTH);
         }

         if (arg.get(WEST)) {
            i |= getDirectionMask(Direction.WEST);
         }

         return i;
      });
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      switch (rotation) {
         case CLOCKWISE_180:
            return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
         case COUNTERCLOCKWISE_90:
            return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
         case CLOCKWISE_90:
            return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
         default:
            return state;
      }
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      switch (mirror) {
         case LEFT_RIGHT:
            return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
         case FRONT_BACK:
            return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
         default:
            return super.mirror(state, mirror);
      }
   }
}
