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
      .filter(_snowman -> _snowman.getKey().getAxis().isHorizontal())
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
         BlockState _snowman = (BlockState)var7.next();
         this.getShapeIndex(_snowman);
      }
   }

   protected VoxelShape[] createShapes(float radius1, float radius2, float height1, float offset2, float height2) {
      float _snowman = 8.0F - radius1;
      float _snowmanx = 8.0F + radius1;
      float _snowmanxx = 8.0F - radius2;
      float _snowmanxxx = 8.0F + radius2;
      VoxelShape _snowmanxxxx = Block.createCuboidShape((double)_snowman, 0.0, (double)_snowman, (double)_snowmanx, (double)height1, (double)_snowmanx);
      VoxelShape _snowmanxxxxx = Block.createCuboidShape((double)_snowmanxx, (double)offset2, 0.0, (double)_snowmanxxx, (double)height2, (double)_snowmanxxx);
      VoxelShape _snowmanxxxxxx = Block.createCuboidShape((double)_snowmanxx, (double)offset2, (double)_snowmanxx, (double)_snowmanxxx, (double)height2, 16.0);
      VoxelShape _snowmanxxxxxxx = Block.createCuboidShape(0.0, (double)offset2, (double)_snowmanxx, (double)_snowmanxxx, (double)height2, (double)_snowmanxxx);
      VoxelShape _snowmanxxxxxxxx = Block.createCuboidShape((double)_snowmanxx, (double)offset2, (double)_snowmanxx, 16.0, (double)height2, (double)_snowmanxxx);
      VoxelShape _snowmanxxxxxxxxx = VoxelShapes.union(_snowmanxxxxx, _snowmanxxxxxxxx);
      VoxelShape _snowmanxxxxxxxxxx = VoxelShapes.union(_snowmanxxxxxx, _snowmanxxxxxxx);
      VoxelShape[] _snowmanxxxxxxxxxxx = new VoxelShape[]{
         VoxelShapes.empty(),
         _snowmanxxxxxx,
         _snowmanxxxxxxx,
         _snowmanxxxxxxxxxx,
         _snowmanxxxxx,
         VoxelShapes.union(_snowmanxxxxxx, _snowmanxxxxx),
         VoxelShapes.union(_snowmanxxxxxxx, _snowmanxxxxx),
         VoxelShapes.union(_snowmanxxxxxxxxxx, _snowmanxxxxx),
         _snowmanxxxxxxxx,
         VoxelShapes.union(_snowmanxxxxxx, _snowmanxxxxxxxx),
         VoxelShapes.union(_snowmanxxxxxxx, _snowmanxxxxxxxx),
         VoxelShapes.union(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx),
         _snowmanxxxxxxxxx,
         VoxelShapes.union(_snowmanxxxxxx, _snowmanxxxxxxxxx),
         VoxelShapes.union(_snowmanxxxxxxx, _snowmanxxxxxxxxx),
         VoxelShapes.union(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)
      };

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxx++) {
         _snowmanxxxxxxxxxxx[_snowmanxxxxxxxxxxxx] = VoxelShapes.union(_snowmanxxxx, _snowmanxxxxxxxxxxx[_snowmanxxxxxxxxxxxx]);
      }

      return _snowmanxxxxxxxxxxx;
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
      return this.SHAPE_INDEX_CACHE.computeIntIfAbsent(state, _snowman -> {
         int _snowmanx = 0;
         if (_snowman.get(NORTH)) {
            _snowmanx |= getDirectionMask(Direction.NORTH);
         }

         if (_snowman.get(EAST)) {
            _snowmanx |= getDirectionMask(Direction.EAST);
         }

         if (_snowman.get(SOUTH)) {
            _snowmanx |= getDirectionMask(Direction.SOUTH);
         }

         if (_snowman.get(WEST)) {
            _snowmanx |= getDirectionMask(Direction.WEST);
         }

         return _snowmanx;
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
