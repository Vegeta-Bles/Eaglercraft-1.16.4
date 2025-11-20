/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class EnderChestBlock
extends AbstractChestBlock<EnderChestBlockEntity>
implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    private static final Text CONTAINER_NAME = new TranslatableText("container.enderchest");

    protected EnderChestBlock(AbstractBlock.Settings settings) {
        super(settings, () -> BlockEntityType.ENDER_CHEST);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(WATERLOGGED, false));
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        return DoubleBlockProperties.PropertyRetriever::getFallback;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite())).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();
        BlockEntity _snowman2 = world.getBlockEntity(pos);
        if (enderChestInventory == null || !(_snowman2 instanceof EnderChestBlockEntity)) {
            return ActionResult.success(world.isClient);
        }
        BlockPos _snowman3 = pos.up();
        if (world.getBlockState(_snowman3).isSolidBlock(world, _snowman3)) {
            return ActionResult.success(world.isClient);
        }
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        EnderChestBlockEntity _snowman4 = (EnderChestBlockEntity)_snowman2;
        enderChestInventory.setActiveBlockEntity(_snowman4);
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((n, playerInventory, playerEntity) -> GenericContainerScreenHandler.createGeneric9x3(n, playerInventory, enderChestInventory), CONTAINER_NAME));
        player.incrementStat(Stats.OPEN_ENDERCHEST);
        PiglinBrain.onGuardedBlockInteracted(player, true);
        return ActionResult.CONSUME;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EnderChestBlockEntity();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 3; ++i) {
            _snowman = random.nextInt(2) * 2 - 1;
            _snowman = random.nextInt(2) * 2 - 1;
            double d = (double)pos.getX() + 0.5 + 0.25 * (double)_snowman;
            _snowman = (float)pos.getY() + random.nextFloat();
            _snowman = (double)pos.getZ() + 0.5 + 0.25 * (double)_snowman;
            _snowman = random.nextFloat() * (float)_snowman;
            _snowman = ((double)random.nextFloat() - 0.5) * 0.125;
            _snowman = random.nextFloat() * (float)_snowman;
            world.addParticle(ParticleTypes.PORTAL, d, _snowman, _snowman, _snowman, _snowman, _snowman);
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

