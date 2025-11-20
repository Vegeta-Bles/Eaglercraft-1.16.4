/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandBlock
extends BlockWithEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final DirectionProperty FACING = FacingBlock.FACING;
    public static final BooleanProperty CONDITIONAL = Properties.CONDITIONAL;

    public CommandBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(CONDITIONAL, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        CommandBlockBlockEntity commandBlockBlockEntity = new CommandBlockBlockEntity();
        commandBlockBlockEntity.setAuto(this == Blocks.CHAIN_COMMAND_BLOCK);
        return commandBlockBlockEntity;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof CommandBlockBlockEntity)) {
            return;
        }
        CommandBlockBlockEntity _snowman2 = (CommandBlockBlockEntity)blockEntity;
        boolean _snowman3 = world.isReceivingRedstonePower(pos);
        boolean _snowman4 = _snowman2.isPowered();
        _snowman2.setPowered(_snowman3);
        if (_snowman4 || _snowman2.isAuto() || _snowman2.getCommandBlockType() == CommandBlockBlockEntity.Type.SEQUENCE) {
            return;
        }
        if (_snowman3) {
            _snowman2.updateConditionMet();
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CommandBlockBlockEntity) {
            CommandBlockBlockEntity commandBlockBlockEntity = (CommandBlockBlockEntity)blockEntity;
            CommandBlockExecutor _snowman2 = commandBlockBlockEntity.getCommandExecutor();
            boolean _snowman3 = !ChatUtil.isEmpty(_snowman2.getCommand());
            CommandBlockBlockEntity.Type _snowman4 = commandBlockBlockEntity.getCommandBlockType();
            boolean _snowman5 = commandBlockBlockEntity.isConditionMet();
            if (_snowman4 == CommandBlockBlockEntity.Type.AUTO) {
                commandBlockBlockEntity.updateConditionMet();
                if (_snowman5) {
                    this.execute(state, world, pos, _snowman2, _snowman3);
                } else if (commandBlockBlockEntity.isConditionalCommandBlock()) {
                    _snowman2.setSuccessCount(0);
                }
                if (commandBlockBlockEntity.isPowered() || commandBlockBlockEntity.isAuto()) {
                    world.getBlockTickScheduler().schedule(pos, this, 1);
                }
            } else if (_snowman4 == CommandBlockBlockEntity.Type.REDSTONE) {
                if (_snowman5) {
                    this.execute(state, world, pos, _snowman2, _snowman3);
                } else if (commandBlockBlockEntity.isConditionalCommandBlock()) {
                    _snowman2.setSuccessCount(0);
                }
            }
            world.updateComparators(pos, this);
        }
    }

    private void execute(BlockState state, World world, BlockPos pos, CommandBlockExecutor executor, boolean hasCommand) {
        if (hasCommand) {
            executor.execute(world);
        } else {
            executor.setSuccessCount(0);
        }
        CommandBlock.executeCommandChain(world, pos, state.get(FACING));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CommandBlockBlockEntity && player.isCreativeLevelTwoOp()) {
            player.openCommandBlockScreen((CommandBlockBlockEntity)blockEntity);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CommandBlockBlockEntity) {
            return ((CommandBlockBlockEntity)blockEntity).getCommandExecutor().getSuccessCount();
        }
        return 0;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof CommandBlockBlockEntity)) {
            return;
        }
        CommandBlockBlockEntity _snowman2 = (CommandBlockBlockEntity)blockEntity;
        CommandBlockExecutor _snowman3 = _snowman2.getCommandExecutor();
        if (itemStack.hasCustomName()) {
            _snowman3.setCustomName(itemStack.getName());
        }
        if (!world.isClient) {
            if (itemStack.getSubTag("BlockEntityTag") == null) {
                _snowman3.shouldTrackOutput(world.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK));
                _snowman2.setAuto(this == Blocks.CHAIN_COMMAND_BLOCK);
            }
            if (_snowman2.getCommandBlockType() == CommandBlockBlockEntity.Type.SEQUENCE) {
                boolean bl = world.isReceivingRedstonePower(pos);
                _snowman2.setPowered(bl);
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
        builder.add(FACING, CONDITIONAL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    private static void executeCommandChain(World world, BlockPos pos, Direction facing) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        GameRules _snowman2 = world.getGameRules();
        int _snowman3 = _snowman2.getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH);
        while (_snowman3-- > 0) {
            mutable.move(facing);
            BlockState blockState = world.getBlockState(mutable);
            Block _snowman4 = blockState.getBlock();
            if (!blockState.isOf(Blocks.CHAIN_COMMAND_BLOCK) || !((_snowman = world.getBlockEntity(mutable)) instanceof CommandBlockBlockEntity) || (_snowman = (CommandBlockBlockEntity)_snowman).getCommandBlockType() != CommandBlockBlockEntity.Type.SEQUENCE) break;
            if (_snowman.isPowered() || _snowman.isAuto()) {
                CommandBlockExecutor commandBlockExecutor = _snowman.getCommandExecutor();
                if (_snowman.updateConditionMet()) {
                    if (!commandBlockExecutor.execute(world)) break;
                    world.updateComparators(mutable, _snowman4);
                } else if (_snowman.isConditionalCommandBlock()) {
                    commandBlockExecutor.setSuccessCount(0);
                }
            }
            facing = blockState.get(FACING);
        }
        if (_snowman3 <= 0) {
            int n = Math.max(_snowman2.getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH), 0);
            LOGGER.warn("Command Block chain tried to execute more than {} steps!", (Object)n);
        }
    }
}

