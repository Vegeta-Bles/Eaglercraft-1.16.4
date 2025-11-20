package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class RedstoneWireBlock extends Block {
   public static final EnumProperty<WireConnection> WIRE_CONNECTION_NORTH = Properties.NORTH_WIRE_CONNECTION;
   public static final EnumProperty<WireConnection> WIRE_CONNECTION_EAST = Properties.EAST_WIRE_CONNECTION;
   public static final EnumProperty<WireConnection> WIRE_CONNECTION_SOUTH = Properties.SOUTH_WIRE_CONNECTION;
   public static final EnumProperty<WireConnection> WIRE_CONNECTION_WEST = Properties.WEST_WIRE_CONNECTION;
   public static final IntProperty POWER = Properties.POWER;
   public static final Map<Direction, EnumProperty<WireConnection>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         WIRE_CONNECTION_NORTH,
         Direction.EAST,
         WIRE_CONNECTION_EAST,
         Direction.SOUTH,
         WIRE_CONNECTION_SOUTH,
         Direction.WEST,
         WIRE_CONNECTION_WEST
      )
   );
   private static final VoxelShape DOT_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
   private static final Map<Direction, VoxelShape> field_24414 = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.createCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
         Direction.SOUTH,
         Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
         Direction.EAST,
         Block.createCuboidShape(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
         Direction.WEST,
         Block.createCuboidShape(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)
      )
   );
   private static final Map<Direction, VoxelShape> field_24415 = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         VoxelShapes.union(field_24414.get(Direction.NORTH), Block.createCuboidShape(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)),
         Direction.SOUTH,
         VoxelShapes.union(field_24414.get(Direction.SOUTH), Block.createCuboidShape(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)),
         Direction.EAST,
         VoxelShapes.union(field_24414.get(Direction.EAST), Block.createCuboidShape(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)),
         Direction.WEST,
         VoxelShapes.union(field_24414.get(Direction.WEST), Block.createCuboidShape(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))
      )
   );
   private final Map<BlockState, VoxelShape> field_24416 = Maps.newHashMap();
   private static final Vector3f[] field_24466 = new Vector3f[16];
   private final BlockState dotState;
   private boolean wiresGivePower = true;

   public RedstoneWireBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(WIRE_CONNECTION_NORTH, WireConnection.NONE)
            .with(WIRE_CONNECTION_EAST, WireConnection.NONE)
            .with(WIRE_CONNECTION_SOUTH, WireConnection.NONE)
            .with(WIRE_CONNECTION_WEST, WireConnection.NONE)
            .with(POWER, Integer.valueOf(0))
      );
      this.dotState = this.getDefaultState()
         .with(WIRE_CONNECTION_NORTH, WireConnection.SIDE)
         .with(WIRE_CONNECTION_EAST, WireConnection.SIDE)
         .with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE)
         .with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
      UnmodifiableIterator var2 = this.getStateManager().getStates().iterator();

      while (var2.hasNext()) {
         BlockState _snowmanx = (BlockState)var2.next();
         if (_snowmanx.get(POWER) == 0) {
            this.field_24416.put(_snowmanx, this.method_27845(_snowmanx));
         }
      }
   }

   private VoxelShape method_27845(BlockState state) {
      VoxelShape _snowman = DOT_SHAPE;

      for (Direction _snowmanx : Direction.Type.HORIZONTAL) {
         WireConnection _snowmanxx = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(_snowmanx));
         if (_snowmanxx == WireConnection.SIDE) {
            _snowman = VoxelShapes.union(_snowman, field_24414.get(_snowmanx));
         } else if (_snowmanxx == WireConnection.UP) {
            _snowman = VoxelShapes.union(_snowman, field_24415.get(_snowmanx));
         }
      }

      return _snowman;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.field_24416.get(state.with(POWER, Integer.valueOf(0)));
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.method_27840(ctx.getWorld(), this.dotState, ctx.getBlockPos());
   }

   private BlockState method_27840(BlockView world, BlockState state, BlockPos pos) {
      boolean _snowman = isNotConnected(state);
      state = this.method_27843(world, this.getDefaultState().with(POWER, state.get(POWER)), pos);
      if (_snowman && isNotConnected(state)) {
         return state;
      } else {
         boolean _snowmanx = state.get(WIRE_CONNECTION_NORTH).isConnected();
         boolean _snowmanxx = state.get(WIRE_CONNECTION_SOUTH).isConnected();
         boolean _snowmanxxx = state.get(WIRE_CONNECTION_EAST).isConnected();
         boolean _snowmanxxxx = state.get(WIRE_CONNECTION_WEST).isConnected();
         boolean _snowmanxxxxx = !_snowmanx && !_snowmanxx;
         boolean _snowmanxxxxxx = !_snowmanxxx && !_snowmanxxxx;
         if (!_snowmanxxxx && _snowmanxxxxx) {
            state = state.with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
         }

         if (!_snowmanxxx && _snowmanxxxxx) {
            state = state.with(WIRE_CONNECTION_EAST, WireConnection.SIDE);
         }

         if (!_snowmanx && _snowmanxxxxxx) {
            state = state.with(WIRE_CONNECTION_NORTH, WireConnection.SIDE);
         }

         if (!_snowmanxx && _snowmanxxxxxx) {
            state = state.with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE);
         }

         return state;
      }
   }

   private BlockState method_27843(BlockView world, BlockState state, BlockPos pos) {
      boolean _snowman = !world.getBlockState(pos.up()).isSolidBlock(world, pos);

      for (Direction _snowmanx : Direction.Type.HORIZONTAL) {
         if (!state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(_snowmanx)).isConnected()) {
            WireConnection _snowmanxx = this.method_27841(world, pos, _snowmanx, _snowman);
            state = state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(_snowmanx), _snowmanxx);
         }
      }

      return state;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction == Direction.DOWN) {
         return state;
      } else if (direction == Direction.UP) {
         return this.method_27840(world, state, pos);
      } else {
         WireConnection _snowman = this.getRenderConnectionType(world, pos, direction);
         return _snowman.isConnected() == state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() && !isFullyConnected(state)
            ? state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), _snowman)
            : this.method_27840(world, this.dotState.with(POWER, state.get(POWER)).with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), _snowman), pos);
      }
   }

   private static boolean isFullyConnected(BlockState state) {
      return state.get(WIRE_CONNECTION_NORTH).isConnected()
         && state.get(WIRE_CONNECTION_SOUTH).isConnected()
         && state.get(WIRE_CONNECTION_EAST).isConnected()
         && state.get(WIRE_CONNECTION_WEST).isConnected();
   }

   private static boolean isNotConnected(BlockState state) {
      return !state.get(WIRE_CONNECTION_NORTH).isConnected()
         && !state.get(WIRE_CONNECTION_SOUTH).isConnected()
         && !state.get(WIRE_CONNECTION_EAST).isConnected()
         && !state.get(WIRE_CONNECTION_WEST).isConnected();
   }

   @Override
   public void prepare(BlockState state, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();

      for (Direction _snowmanx : Direction.Type.HORIZONTAL) {
         WireConnection _snowmanxx = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(_snowmanx));
         if (_snowmanxx != WireConnection.NONE && !world.getBlockState(_snowman.set(pos, _snowmanx)).isOf(this)) {
            _snowman.move(Direction.DOWN);
            BlockState _snowmanxxx = world.getBlockState(_snowman);
            if (!_snowmanxxx.isOf(Blocks.OBSERVER)) {
               BlockPos _snowmanxxxx = _snowman.offset(_snowmanx.getOpposite());
               BlockState _snowmanxxxxx = _snowmanxxx.getStateForNeighborUpdate(_snowmanx.getOpposite(), world.getBlockState(_snowmanxxxx), world, _snowman, _snowmanxxxx);
               replace(_snowmanxxx, _snowmanxxxxx, world, _snowman, flags, maxUpdateDepth);
            }

            _snowman.set(pos, _snowmanx).move(Direction.UP);
            BlockState _snowmanxxxx = world.getBlockState(_snowman);
            if (!_snowmanxxxx.isOf(Blocks.OBSERVER)) {
               BlockPos _snowmanxxxxx = _snowman.offset(_snowmanx.getOpposite());
               BlockState _snowmanxxxxxx = _snowmanxxxx.getStateForNeighborUpdate(_snowmanx.getOpposite(), world.getBlockState(_snowmanxxxxx), world, _snowman, _snowmanxxxxx);
               replace(_snowmanxxxx, _snowmanxxxxxx, world, _snowman, flags, maxUpdateDepth);
            }
         }
      }
   }

   private WireConnection getRenderConnectionType(BlockView _snowman, BlockPos _snowman, Direction _snowman) {
      return this.method_27841(_snowman, _snowman, _snowman, !_snowman.getBlockState(_snowman.up()).isSolidBlock(_snowman, _snowman));
   }

   private WireConnection method_27841(BlockView _snowman, BlockPos _snowman, Direction _snowman, boolean _snowman) {
      BlockPos _snowmanxxxx = _snowman.offset(_snowman);
      BlockState _snowmanxxxxx = _snowman.getBlockState(_snowmanxxxx);
      if (_snowman) {
         boolean _snowmanxxxxxx = this.canRunOnTop(_snowman, _snowmanxxxx, _snowmanxxxxx);
         if (_snowmanxxxxxx && connectsTo(_snowman.getBlockState(_snowmanxxxx.up()))) {
            if (_snowmanxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxx, _snowman.getOpposite())) {
               return WireConnection.UP;
            }

            return WireConnection.SIDE;
         }
      }

      return !connectsTo(_snowmanxxxxx, _snowman) && (_snowmanxxxxx.isSolidBlock(_snowman, _snowmanxxxx) || !connectsTo(_snowman.getBlockState(_snowmanxxxx.down())))
         ? WireConnection.NONE
         : WireConnection.SIDE;
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      return this.canRunOnTop(world, _snowman, _snowmanx);
   }

   private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
      return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
   }

   private void update(World world, BlockPos pos, BlockState state) {
      int _snowman = this.getReceivedRedstonePower(world, pos);
      if (state.get(POWER) != _snowman) {
         if (world.getBlockState(pos) == state) {
            world.setBlockState(pos, state.with(POWER, Integer.valueOf(_snowman)), 2);
         }

         Set<BlockPos> _snowmanx = Sets.newHashSet();
         _snowmanx.add(pos);

         for (Direction _snowmanxx : Direction.values()) {
            _snowmanx.add(pos.offset(_snowmanxx));
         }

         for (BlockPos _snowmanxx : _snowmanx) {
            world.updateNeighborsAlways(_snowmanxx, this);
         }
      }
   }

   private int getReceivedRedstonePower(World world, BlockPos pos) {
      this.wiresGivePower = false;
      int _snowman = world.getReceivedRedstonePower(pos);
      this.wiresGivePower = true;
      int _snowmanx = 0;
      if (_snowman < 15) {
         for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
            BlockPos _snowmanxxx = pos.offset(_snowmanxx);
            BlockState _snowmanxxxx = world.getBlockState(_snowmanxxx);
            _snowmanx = Math.max(_snowmanx, this.increasePower(_snowmanxxxx));
            BlockPos _snowmanxxxxx = pos.up();
            if (_snowmanxxxx.isSolidBlock(world, _snowmanxxx) && !world.getBlockState(_snowmanxxxxx).isSolidBlock(world, _snowmanxxxxx)) {
               _snowmanx = Math.max(_snowmanx, this.increasePower(world.getBlockState(_snowmanxxx.up())));
            } else if (!_snowmanxxxx.isSolidBlock(world, _snowmanxxx)) {
               _snowmanx = Math.max(_snowmanx, this.increasePower(world.getBlockState(_snowmanxxx.down())));
            }
         }
      }

      return Math.max(_snowman, _snowmanx - 1);
   }

   private int increasePower(BlockState state) {
      return state.isOf(this) ? state.get(POWER) : 0;
   }

   private void updateNeighbors(World world, BlockPos pos) {
      if (world.getBlockState(pos).isOf(this)) {
         world.updateNeighborsAlways(pos, this);

         for (Direction _snowman : Direction.values()) {
            world.updateNeighborsAlways(pos.offset(_snowman), this);
         }
      }
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!oldState.isOf(state.getBlock()) && !world.isClient) {
         this.update(world, pos, state);

         for (Direction _snowman : Direction.Type.VERTICAL) {
            world.updateNeighborsAlways(pos.offset(_snowman), this);
         }

         this.method_27844(world, pos);
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!moved && !state.isOf(newState.getBlock())) {
         super.onStateReplaced(state, world, pos, newState, moved);
         if (!world.isClient) {
            for (Direction _snowman : Direction.values()) {
               world.updateNeighborsAlways(pos.offset(_snowman), this);
            }

            this.update(world, pos, state);
            this.method_27844(world, pos);
         }
      }
   }

   private void method_27844(World world, BlockPos pos) {
      for (Direction _snowman : Direction.Type.HORIZONTAL) {
         this.updateNeighbors(world, pos.offset(_snowman));
      }

      for (Direction _snowman : Direction.Type.HORIZONTAL) {
         BlockPos _snowmanx = pos.offset(_snowman);
         if (world.getBlockState(_snowmanx).isSolidBlock(world, _snowmanx)) {
            this.updateNeighbors(world, _snowmanx.up());
         } else {
            this.updateNeighbors(world, _snowmanx.down());
         }
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (!world.isClient) {
         if (state.canPlaceAt(world, pos)) {
            this.update(world, pos, state);
         } else {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
         }
      }
   }

   @Override
   public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return !this.wiresGivePower ? 0 : state.getWeakRedstonePower(world, pos, direction);
   }

   @Override
   public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      if (this.wiresGivePower && direction != Direction.DOWN) {
         int _snowman = state.get(POWER);
         if (_snowman == 0) {
            return 0;
         } else {
            return direction != Direction.UP
                  && !this.method_27840(world, state, pos).get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite())).isConnected()
               ? 0
               : _snowman;
         }
      } else {
         return 0;
      }
   }

   protected static boolean connectsTo(BlockState state) {
      return connectsTo(state, null);
   }

   protected static boolean connectsTo(BlockState state, @Nullable Direction dir) {
      if (state.isOf(Blocks.REDSTONE_WIRE)) {
         return true;
      } else if (state.isOf(Blocks.REPEATER)) {
         Direction _snowman = state.get(RepeaterBlock.FACING);
         return _snowman == dir || _snowman.getOpposite() == dir;
      } else {
         return state.isOf(Blocks.OBSERVER) ? dir == state.get(ObserverBlock.FACING) : state.emitsRedstonePower() && dir != null;
      }
   }

   @Override
   public boolean emitsRedstonePower(BlockState state) {
      return this.wiresGivePower;
   }

   public static int getWireColor(int powerLevel) {
      Vector3f _snowman = field_24466[powerLevel];
      return MathHelper.packRgb(_snowman.getX(), _snowman.getY(), _snowman.getZ());
   }

   private void method_27936(World world, Random random, BlockPos pos, Vector3f _snowman, Direction _snowman, Direction _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxx = _snowman - _snowman;
      if (!(random.nextFloat() >= 0.2F * _snowmanxxxxx)) {
         float _snowmanxxxxxx = 0.4375F;
         float _snowmanxxxxxxx = _snowman + _snowmanxxxxx * random.nextFloat();
         double _snowmanxxxxxxxx = 0.5 + (double)(0.4375F * (float)_snowman.getOffsetX()) + (double)(_snowmanxxxxxxx * (float)_snowman.getOffsetX());
         double _snowmanxxxxxxxxx = 0.5 + (double)(0.4375F * (float)_snowman.getOffsetY()) + (double)(_snowmanxxxxxxx * (float)_snowman.getOffsetY());
         double _snowmanxxxxxxxxxx = 0.5 + (double)(0.4375F * (float)_snowman.getOffsetZ()) + (double)(_snowmanxxxxxxx * (float)_snowman.getOffsetZ());
         world.addParticle(
            new DustParticleEffect(_snowman.getX(), _snowman.getY(), _snowman.getZ(), 1.0F),
            (double)pos.getX() + _snowmanxxxxxxxx,
            (double)pos.getY() + _snowmanxxxxxxxxx,
            (double)pos.getZ() + _snowmanxxxxxxxxxx,
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      int _snowman = state.get(POWER);
      if (_snowman != 0) {
         for (Direction _snowmanx : Direction.Type.HORIZONTAL) {
            WireConnection _snowmanxx = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(_snowmanx));
            switch (_snowmanxx) {
               case UP:
                  this.method_27936(world, random, pos, field_24466[_snowman], _snowmanx, Direction.UP, -0.5F, 0.5F);
               case SIDE:
                  this.method_27936(world, random, pos, field_24466[_snowman], Direction.DOWN, _snowmanx, 0.0F, 0.5F);
                  break;
               case NONE:
               default:
                  this.method_27936(world, random, pos, field_24466[_snowman], Direction.DOWN, _snowmanx, 0.0F, 0.3F);
            }
         }
      }
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      switch (rotation) {
         case CLOCKWISE_180:
            return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH))
               .with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST))
               .with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH))
               .with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
         case COUNTERCLOCKWISE_90:
            return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_EAST))
               .with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_SOUTH))
               .with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_WEST))
               .with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_NORTH));
         case CLOCKWISE_90:
            return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_WEST))
               .with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_NORTH))
               .with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_EAST))
               .with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_SOUTH));
         default:
            return state;
      }
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      switch (mirror) {
         case LEFT_RIGHT:
            return state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH));
         case FRONT_BACK:
            return state.with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
         default:
            return super.mirror(state, mirror);
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (!player.abilities.allowModifyWorld) {
         return ActionResult.PASS;
      } else {
         if (isFullyConnected(state) || isNotConnected(state)) {
            BlockState _snowman = isFullyConnected(state) ? this.getDefaultState() : this.dotState;
            _snowman = _snowman.with(POWER, state.get(POWER));
            _snowman = this.method_27840(world, _snowman, pos);
            if (_snowman != state) {
               world.setBlockState(pos, _snowman, 3);
               this.method_28482(world, pos, state, _snowman);
               return ActionResult.SUCCESS;
            }
         }

         return ActionResult.PASS;
      }
   }

   private void method_28482(World world, BlockPos pos, BlockState _snowman, BlockState _snowman) {
      for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
         BlockPos _snowmanxxx = pos.offset(_snowmanxx);
         if (_snowman.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(_snowmanxx)).isConnected() != _snowman.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(_snowmanxx)).isConnected()
            && world.getBlockState(_snowmanxxx).isSolidBlock(world, _snowmanxxx)) {
            world.updateNeighborsExcept(_snowmanxxx, _snowman.getBlock(), _snowmanxx.getOpposite());
         }
      }
   }

   static {
      for (int _snowman = 0; _snowman <= 15; _snowman++) {
         float _snowmanx = (float)_snowman / 15.0F;
         float _snowmanxx = _snowmanx * 0.6F + (_snowmanx > 0.0F ? 0.4F : 0.3F);
         float _snowmanxxx = MathHelper.clamp(_snowmanx * _snowmanx * 0.7F - 0.5F, 0.0F, 1.0F);
         float _snowmanxxxx = MathHelper.clamp(_snowmanx * _snowmanx * 0.6F - 0.7F, 0.0F, 1.0F);
         field_24466[_snowman] = new Vector3f(_snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }
}
