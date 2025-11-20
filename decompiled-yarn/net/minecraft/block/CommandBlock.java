package net.minecraft.block;

import java.util.Random;
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

public class CommandBlock extends BlockWithEntity {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final DirectionProperty FACING = FacingBlock.FACING;
   public static final BooleanProperty CONDITIONAL = Properties.CONDITIONAL;

   public CommandBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(CONDITIONAL, Boolean.valueOf(false)));
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      CommandBlockBlockEntity _snowman = new CommandBlockBlockEntity();
      _snowman.setAuto(this == Blocks.CHAIN_COMMAND_BLOCK);
      return _snowman;
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (!world.isClient) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof CommandBlockBlockEntity) {
            CommandBlockBlockEntity _snowmanx = (CommandBlockBlockEntity)_snowman;
            boolean _snowmanxx = world.isReceivingRedstonePower(pos);
            boolean _snowmanxxx = _snowmanx.isPowered();
            _snowmanx.setPowered(_snowmanxx);
            if (!_snowmanxxx && !_snowmanx.isAuto() && _snowmanx.getCommandBlockType() != CommandBlockBlockEntity.Type.SEQUENCE) {
               if (_snowmanxx) {
                  _snowmanx.updateConditionMet();
                  world.getBlockTickScheduler().schedule(pos, this, 1);
               }
            }
         }
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof CommandBlockBlockEntity) {
         CommandBlockBlockEntity _snowmanx = (CommandBlockBlockEntity)_snowman;
         CommandBlockExecutor _snowmanxx = _snowmanx.getCommandExecutor();
         boolean _snowmanxxx = !ChatUtil.isEmpty(_snowmanxx.getCommand());
         CommandBlockBlockEntity.Type _snowmanxxxx = _snowmanx.getCommandBlockType();
         boolean _snowmanxxxxx = _snowmanx.isConditionMet();
         if (_snowmanxxxx == CommandBlockBlockEntity.Type.AUTO) {
            _snowmanx.updateConditionMet();
            if (_snowmanxxxxx) {
               this.execute(state, world, pos, _snowmanxx, _snowmanxxx);
            } else if (_snowmanx.isConditionalCommandBlock()) {
               _snowmanxx.setSuccessCount(0);
            }

            if (_snowmanx.isPowered() || _snowmanx.isAuto()) {
               world.getBlockTickScheduler().schedule(pos, this, 1);
            }
         } else if (_snowmanxxxx == CommandBlockBlockEntity.Type.REDSTONE) {
            if (_snowmanxxxxx) {
               this.execute(state, world, pos, _snowmanxx, _snowmanxxx);
            } else if (_snowmanx.isConditionalCommandBlock()) {
               _snowmanxx.setSuccessCount(0);
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

      executeCommandChain(world, pos, state.get(FACING));
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof CommandBlockBlockEntity && player.isCreativeLevelTwoOp()) {
         player.openCommandBlockScreen((CommandBlockBlockEntity)_snowman);
         return ActionResult.success(world.isClient);
      } else {
         return ActionResult.PASS;
      }
   }

   @Override
   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   @Override
   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      return _snowman instanceof CommandBlockBlockEntity ? ((CommandBlockBlockEntity)_snowman).getCommandExecutor().getSuccessCount() : 0;
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof CommandBlockBlockEntity) {
         CommandBlockBlockEntity _snowmanx = (CommandBlockBlockEntity)_snowman;
         CommandBlockExecutor _snowmanxx = _snowmanx.getCommandExecutor();
         if (itemStack.hasCustomName()) {
            _snowmanxx.setCustomName(itemStack.getName());
         }

         if (!world.isClient) {
            if (itemStack.getSubTag("BlockEntityTag") == null) {
               _snowmanxx.shouldTrackOutput(world.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK));
               _snowmanx.setAuto(this == Blocks.CHAIN_COMMAND_BLOCK);
            }

            if (_snowmanx.getCommandBlockType() == CommandBlockBlockEntity.Type.SEQUENCE) {
               boolean _snowmanxxx = world.isReceivingRedstonePower(pos);
               _snowmanx.setPowered(_snowmanxxx);
            }
         }
      }
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
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
      return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
   }

   private static void executeCommandChain(World world, BlockPos pos, Direction facing) {
      BlockPos.Mutable _snowman = pos.mutableCopy();
      GameRules _snowmanx = world.getGameRules();
      int _snowmanxx = _snowmanx.getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH);

      while (_snowmanxx-- > 0) {
         _snowman.move(facing);
         BlockState _snowmanxxx = world.getBlockState(_snowman);
         Block _snowmanxxxx = _snowmanxxx.getBlock();
         if (!_snowmanxxx.isOf(Blocks.CHAIN_COMMAND_BLOCK)) {
            break;
         }

         BlockEntity _snowmanxxxxx = world.getBlockEntity(_snowman);
         if (!(_snowmanxxxxx instanceof CommandBlockBlockEntity)) {
            break;
         }

         CommandBlockBlockEntity _snowmanxxxxxx = (CommandBlockBlockEntity)_snowmanxxxxx;
         if (_snowmanxxxxxx.getCommandBlockType() != CommandBlockBlockEntity.Type.SEQUENCE) {
            break;
         }

         if (_snowmanxxxxxx.isPowered() || _snowmanxxxxxx.isAuto()) {
            CommandBlockExecutor _snowmanxxxxxxx = _snowmanxxxxxx.getCommandExecutor();
            if (_snowmanxxxxxx.updateConditionMet()) {
               if (!_snowmanxxxxxxx.execute(world)) {
                  break;
               }

               world.updateComparators(_snowman, _snowmanxxxx);
            } else if (_snowmanxxxxxx.isConditionalCommandBlock()) {
               _snowmanxxxxxxx.setSuccessCount(0);
            }
         }

         facing = _snowmanxxx.get(FACING);
      }

      if (_snowmanxx <= 0) {
         int _snowmanxxxxxxx = Math.max(_snowmanx.getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH), 0);
         LOGGER.warn("Command Block chain tried to execute more than {} steps!", _snowmanxxxxxxx);
      }
   }
}
