/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.ShapeContext;
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

public class RedstoneWireBlock
extends Block {
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_NORTH = Properties.NORTH_WIRE_CONNECTION;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_EAST = Properties.EAST_WIRE_CONNECTION;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_SOUTH = Properties.SOUTH_WIRE_CONNECTION;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_WEST = Properties.WEST_WIRE_CONNECTION;
    public static final IntProperty POWER = Properties.POWER;
    public static final Map<Direction, EnumProperty<WireConnection>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, WIRE_CONNECTION_NORTH, (Object)Direction.EAST, WIRE_CONNECTION_EAST, (Object)Direction.SOUTH, WIRE_CONNECTION_SOUTH, (Object)Direction.WEST, WIRE_CONNECTION_WEST));
    private static final VoxelShape DOT_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    private static final Map<Direction, VoxelShape> field_24414 = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Block.createCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0), (Object)Direction.SOUTH, (Object)Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 16.0), (Object)Direction.EAST, (Object)Block.createCuboidShape(3.0, 0.0, 3.0, 16.0, 1.0, 13.0), (Object)Direction.WEST, (Object)Block.createCuboidShape(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)));
    private static final Map<Direction, VoxelShape> field_24415 = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)VoxelShapes.union(field_24414.get(Direction.NORTH), Block.createCuboidShape(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)), (Object)Direction.SOUTH, (Object)VoxelShapes.union(field_24414.get(Direction.SOUTH), Block.createCuboidShape(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)), (Object)Direction.EAST, (Object)VoxelShapes.union(field_24414.get(Direction.EAST), Block.createCuboidShape(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)), (Object)Direction.WEST, (Object)VoxelShapes.union(field_24414.get(Direction.WEST), Block.createCuboidShape(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))));
    private final Map<BlockState, VoxelShape> field_24416 = Maps.newHashMap();
    private static final Vector3f[] field_24466 = new Vector3f[16];
    private final BlockState dotState;
    private boolean wiresGivePower = true;

    public RedstoneWireBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(WIRE_CONNECTION_NORTH, WireConnection.NONE)).with(WIRE_CONNECTION_EAST, WireConnection.NONE)).with(WIRE_CONNECTION_SOUTH, WireConnection.NONE)).with(WIRE_CONNECTION_WEST, WireConnection.NONE)).with(POWER, 0));
        this.dotState = (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.SIDE)).with(WIRE_CONNECTION_EAST, WireConnection.SIDE)).with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE)).with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
        for (BlockState blockState : this.getStateManager().getStates()) {
            if (blockState.get(POWER) != 0) continue;
            this.field_24416.put(blockState, this.method_27845(blockState));
        }
    }

    private VoxelShape method_27845(BlockState state) {
        VoxelShape _snowman2 = DOT_SHAPE;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            WireConnection wireConnection = (WireConnection)state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == WireConnection.SIDE) {
                _snowman2 = VoxelShapes.union(_snowman2, field_24414.get(direction));
                continue;
            }
            if (wireConnection != WireConnection.UP) continue;
            _snowman2 = VoxelShapes.union(_snowman2, field_24415.get(direction));
        }
        return _snowman2;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.field_24416.get(state.with(POWER, 0));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.method_27840(ctx.getWorld(), this.dotState, ctx.getBlockPos());
    }

    private BlockState method_27840(BlockView world, BlockState state, BlockPos pos) {
        boolean bl = RedstoneWireBlock.isNotConnected(state);
        state = this.method_27843(world, (BlockState)this.getDefaultState().with(POWER, state.get(POWER)), pos);
        if (bl && RedstoneWireBlock.isNotConnected(state)) {
            return state;
        }
        _snowman = state.get(WIRE_CONNECTION_NORTH).isConnected();
        _snowman = state.get(WIRE_CONNECTION_SOUTH).isConnected();
        _snowman = state.get(WIRE_CONNECTION_EAST).isConnected();
        _snowman = state.get(WIRE_CONNECTION_WEST).isConnected();
        _snowman = !_snowman && !_snowman;
        boolean bl2 = _snowman = !_snowman && !_snowman;
        if (!_snowman && _snowman) {
            state = (BlockState)state.with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
        }
        if (!_snowman && _snowman) {
            state = (BlockState)state.with(WIRE_CONNECTION_EAST, WireConnection.SIDE);
        }
        if (!_snowman && _snowman) {
            state = (BlockState)state.with(WIRE_CONNECTION_NORTH, WireConnection.SIDE);
        }
        if (!_snowman && _snowman) {
            state = (BlockState)state.with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE);
        }
        return state;
    }

    private BlockState method_27843(BlockView world, BlockState state, BlockPos pos) {
        boolean bl = !world.getBlockState(pos.up()).isSolidBlock(world, pos);
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (((WireConnection)state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected()) continue;
            WireConnection wireConnection = this.method_27841(world, pos, direction, bl);
            state = (BlockState)state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
        }
        return state;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction == Direction.DOWN) {
            return state;
        }
        if (direction == Direction.UP) {
            return this.method_27840(world, state, pos);
        }
        WireConnection wireConnection = this.getRenderConnectionType(world, pos, direction);
        if (wireConnection.isConnected() == ((WireConnection)state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() && !RedstoneWireBlock.isFullyConnected(state)) {
            return (BlockState)state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
        }
        return this.method_27840(world, (BlockState)((BlockState)this.dotState.with(POWER, state.get(POWER))).with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection), pos);
    }

    private static boolean isFullyConnected(BlockState state) {
        return state.get(WIRE_CONNECTION_NORTH).isConnected() && state.get(WIRE_CONNECTION_SOUTH).isConnected() && state.get(WIRE_CONNECTION_EAST).isConnected() && state.get(WIRE_CONNECTION_WEST).isConnected();
    }

    private static boolean isNotConnected(BlockState state) {
        return !state.get(WIRE_CONNECTION_NORTH).isConnected() && !state.get(WIRE_CONNECTION_SOUTH).isConnected() && !state.get(WIRE_CONNECTION_EAST).isConnected() && !state.get(WIRE_CONNECTION_WEST).isConnected();
    }

    @Override
    public void prepare(BlockState state, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            Object _snowman3;
            WireConnection wireConnection = (WireConnection)state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == WireConnection.NONE || world.getBlockState(mutable.set(pos, direction)).isOf(this)) continue;
            mutable.move(Direction.DOWN);
            BlockState _snowman2 = world.getBlockState(mutable);
            if (!_snowman2.isOf(Blocks.OBSERVER)) {
                _snowman3 = mutable.offset(direction.getOpposite());
                _snowman4 = _snowman2.getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState((BlockPos)_snowman3), world, mutable, (BlockPos)_snowman3);
                RedstoneWireBlock.replace(_snowman2, (BlockState)_snowman4, world, mutable, flags, maxUpdateDepth);
            }
            mutable.set(pos, direction).move(Direction.UP);
            _snowman3 = world.getBlockState(mutable);
            if (((AbstractBlock.AbstractBlockState)_snowman3).isOf(Blocks.OBSERVER)) continue;
            Object _snowman4 = mutable.offset(direction.getOpposite());
            BlockState _snowman5 = ((AbstractBlock.AbstractBlockState)_snowman3).getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState((BlockPos)_snowman4), world, mutable, (BlockPos)_snowman4);
            RedstoneWireBlock.replace((BlockState)_snowman3, _snowman5, world, mutable, flags, maxUpdateDepth);
        }
    }

    private WireConnection getRenderConnectionType(BlockView blockView, BlockPos blockPos, Direction direction) {
        return this.method_27841(blockView, blockPos, direction, !blockView.getBlockState(blockPos.up()).isSolidBlock(blockView, blockPos));
    }

    private WireConnection method_27841(BlockView blockView, BlockPos blockPos, Direction direction, boolean bl) {
        BlockPos blockPos2 = blockPos.offset(direction);
        BlockState _snowman2 = blockView.getBlockState(blockPos2);
        if (bl && (_snowman = this.canRunOnTop(blockView, blockPos2, _snowman2)) && RedstoneWireBlock.connectsTo(blockView.getBlockState(blockPos2.up()))) {
            if (_snowman2.isSideSolidFullSquare(blockView, blockPos2, direction.getOpposite())) {
                return WireConnection.UP;
            }
            return WireConnection.SIDE;
        }
        if (RedstoneWireBlock.connectsTo(_snowman2, direction) || !_snowman2.isSolidBlock(blockView, blockPos2) && RedstoneWireBlock.connectsTo(blockView.getBlockState(blockPos2.down()))) {
            return WireConnection.SIDE;
        }
        return WireConnection.NONE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState _snowman2 = world.getBlockState(blockPos);
        return this.canRunOnTop(world, blockPos, _snowman2);
    }

    private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
    }

    private void update(World world, BlockPos pos, BlockState state) {
        int n = this.getReceivedRedstonePower(world, pos);
        if (state.get(POWER) != n) {
            if (world.getBlockState(pos) == state) {
                world.setBlockState(pos, (BlockState)state.with(POWER, n), 2);
            }
            HashSet hashSet = Sets.newHashSet();
            hashSet.add(pos);
            for (Direction direction : Direction.values()) {
                hashSet.add(pos.offset(direction));
            }
            for (BlockPos _snowman2 : hashSet) {
                world.updateNeighborsAlways(_snowman2, this);
            }
        }
    }

    private int getReceivedRedstonePower(World world, BlockPos pos) {
        int _snowman3;
        this.wiresGivePower = false;
        int n = world.getReceivedRedstonePower(pos);
        this.wiresGivePower = true;
        _snowman3 = 0;
        if (n < 15) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState _snowman2 = world.getBlockState(blockPos);
                _snowman3 = Math.max(_snowman3, this.increasePower(_snowman2));
                _snowman = pos.up();
                if (_snowman2.isSolidBlock(world, blockPos) && !world.getBlockState(_snowman).isSolidBlock(world, _snowman)) {
                    _snowman3 = Math.max(_snowman3, this.increasePower(world.getBlockState(blockPos.up())));
                    continue;
                }
                if (_snowman2.isSolidBlock(world, blockPos)) continue;
                _snowman3 = Math.max(_snowman3, this.increasePower(world.getBlockState(blockPos.down())));
            }
        }
        return Math.max(n, _snowman3 - 1);
    }

    private int increasePower(BlockState state) {
        return state.isOf(this) ? state.get(POWER) : 0;
    }

    private void updateNeighbors(World world, BlockPos pos) {
        if (!world.getBlockState(pos).isOf(this)) {
            return;
        }
        world.updateNeighborsAlways(pos, this);
        for (Direction direction : Direction.values()) {
            world.updateNeighborsAlways(pos.offset(direction), this);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock()) || world.isClient) {
            return;
        }
        this.update(world, pos, state);
        for (Direction direction : Direction.Type.VERTICAL) {
            world.updateNeighborsAlways(pos.offset(direction), this);
        }
        this.method_27844(world, pos);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved || state.isOf(newState.getBlock())) {
            return;
        }
        super.onStateReplaced(state, world, pos, newState, moved);
        if (world.isClient) {
            return;
        }
        for (Direction direction : Direction.values()) {
            world.updateNeighborsAlways(pos.offset(direction), this);
        }
        this.update(world, pos, state);
        this.method_27844(world, pos);
    }

    private void method_27844(World world, BlockPos pos) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            this.updateNeighbors(world, pos.offset(direction));
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                this.updateNeighbors(world, blockPos.up());
                continue;
            }
            this.updateNeighbors(world, blockPos.down());
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient) {
            return;
        }
        if (state.canPlaceAt(world, pos)) {
            this.update(world, pos, state);
        } else {
            RedstoneWireBlock.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (!this.wiresGivePower) {
            return 0;
        }
        return state.getWeakRedstonePower(world, pos, direction);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (!this.wiresGivePower || direction == Direction.DOWN) {
            return 0;
        }
        int n = state.get(POWER);
        if (n == 0) {
            return 0;
        }
        if (direction == Direction.UP || ((WireConnection)this.method_27840(world, state, pos).get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite()))).isConnected()) {
            return n;
        }
        return 0;
    }

    protected static boolean connectsTo(BlockState state) {
        return RedstoneWireBlock.connectsTo(state, null);
    }

    protected static boolean connectsTo(BlockState state, @Nullable Direction dir) {
        if (state.isOf(Blocks.REDSTONE_WIRE)) {
            return true;
        }
        if (state.isOf(Blocks.REPEATER)) {
            Direction direction = state.get(RepeaterBlock.FACING);
            return direction == dir || direction.getOpposite() == dir;
        }
        if (state.isOf(Blocks.OBSERVER)) {
            return dir == state.get(ObserverBlock.FACING);
        }
        return state.emitsRedstonePower() && dir != null;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return this.wiresGivePower;
    }

    public static int getWireColor(int powerLevel) {
        Vector3f vector3f = field_24466[powerLevel];
        return MathHelper.packRgb(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    private void method_27936(World world, Random random, BlockPos pos, Vector3f vector3f, Direction direction, Direction direction2, float f, float f2) {
        _snowman = f2 - f;
        if (random.nextFloat() >= 0.2f * _snowman) {
            return;
        }
        _snowman = 0.4375f;
        _snowman = f + _snowman * random.nextFloat();
        double d = 0.5 + (double)(0.4375f * (float)direction.getOffsetX()) + (double)(_snowman * (float)direction2.getOffsetX());
        _snowman = 0.5 + (double)(0.4375f * (float)direction.getOffsetY()) + (double)(_snowman * (float)direction2.getOffsetY());
        _snowman = 0.5 + (double)(0.4375f * (float)direction.getOffsetZ()) + (double)(_snowman * (float)direction2.getOffsetZ());
        world.addParticle(new DustParticleEffect(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.0f), (double)pos.getX() + d, (double)pos.getY() + _snowman, (double)pos.getZ() + _snowman, 0.0, 0.0, 0.0);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int n = state.get(POWER);
        if (n == 0) {
            return;
        }
        block4: for (Direction direction : Direction.Type.HORIZONTAL) {
            WireConnection wireConnection = (WireConnection)state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            switch (wireConnection) {
                case UP: {
                    this.method_27936(world, random, pos, field_24466[n], direction, Direction.UP, -0.5f, 0.5f);
                }
                case SIDE: {
                    this.method_27936(world, random, pos, field_24466[n], Direction.DOWN, direction, 0.0f, 0.5f);
                    continue block4;
                }
            }
            this.method_27936(world, random, pos, field_24466[n], Direction.DOWN, direction, 0.0f, 0.3f);
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH))).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH))).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_EAST))).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_SOUTH))).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_NORTH));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_NORTH))).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_EAST))).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_SOUTH));
            }
        }
        return state;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH))).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)state.with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
            }
        }
        return super.mirror(state, mirror);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.abilities.allowModifyWorld) {
            return ActionResult.PASS;
        }
        if (RedstoneWireBlock.isFullyConnected(state) || RedstoneWireBlock.isNotConnected(state)) {
            BlockState blockState = RedstoneWireBlock.isFullyConnected(state) ? this.getDefaultState() : this.dotState;
            blockState = (BlockState)blockState.with(POWER, state.get(POWER));
            if ((blockState = this.method_27840(world, blockState, pos)) != state) {
                world.setBlockState(pos, blockState, 3);
                this.method_28482(world, pos, state, blockState);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    private void method_28482(World world, BlockPos pos, BlockState blockState, BlockState blockState2) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            if (((WireConnection)blockState.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() == ((WireConnection)blockState2.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() || !world.getBlockState(blockPos).isSolidBlock(world, blockPos)) continue;
            world.updateNeighborsExcept(blockPos, blockState2.getBlock(), direction.getOpposite());
        }
    }

    static {
        for (int i = 0; i <= 15; ++i) {
            float f = _snowman * 0.6f + ((_snowman = (float)i / 15.0f) > 0.0f ? 0.4f : 0.3f);
            _snowman = MathHelper.clamp(_snowman * _snowman * 0.7f - 0.5f, 0.0f, 1.0f);
            _snowman = MathHelper.clamp(_snowman * _snowman * 0.6f - 0.7f, 0.0f, 1.0f);
            RedstoneWireBlock.field_24466[i] = new Vector3f(f, _snowman, _snowman);
        }
    }
}

