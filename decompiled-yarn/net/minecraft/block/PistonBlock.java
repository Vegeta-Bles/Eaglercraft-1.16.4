package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.enums.PistonType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PistonBlock extends FacingBlock {
   public static final BooleanProperty EXTENDED = Properties.EXTENDED;
   protected static final VoxelShape EXTENDED_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   protected static final VoxelShape EXTENDED_WEST_SHAPE = Block.createCuboidShape(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape EXTENDED_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
   protected static final VoxelShape EXTENDED_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape EXTENDED_UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   protected static final VoxelShape EXTENDED_DOWN_SHAPE = Block.createCuboidShape(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
   private final boolean sticky;

   public PistonBlock(boolean sticky, AbstractBlock.Settings settings) {
      super(settings);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(EXTENDED, Boolean.valueOf(false)));
      this.sticky = sticky;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      if (state.get(EXTENDED)) {
         switch ((Direction)state.get(FACING)) {
            case DOWN:
               return EXTENDED_DOWN_SHAPE;
            case UP:
            default:
               return EXTENDED_UP_SHAPE;
            case NORTH:
               return EXTENDED_NORTH_SHAPE;
            case SOUTH:
               return EXTENDED_SOUTH_SHAPE;
            case WEST:
               return EXTENDED_WEST_SHAPE;
            case EAST:
               return EXTENDED_EAST_SHAPE;
         }
      } else {
         return VoxelShapes.fullCube();
      }
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      if (!world.isClient) {
         this.tryMove(world, pos, state);
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (!world.isClient) {
         this.tryMove(world, pos, state);
      }
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!oldState.isOf(state.getBlock())) {
         if (!world.isClient && world.getBlockEntity(pos) == null) {
            this.tryMove(world, pos, state);
         }
      }
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite()).with(EXTENDED, Boolean.valueOf(false));
   }

   private void tryMove(World world, BlockPos pos, BlockState state) {
      Direction _snowman = state.get(FACING);
      boolean _snowmanx = this.shouldExtend(world, pos, _snowman);
      if (_snowmanx && !state.get(EXTENDED)) {
         if (new PistonHandler(world, pos, _snowman, true).calculatePush()) {
            world.addSyncedBlockEvent(pos, this, 0, _snowman.getId());
         }
      } else if (!_snowmanx && state.get(EXTENDED)) {
         BlockPos _snowmanxx = pos.offset(_snowman, 2);
         BlockState _snowmanxxx = world.getBlockState(_snowmanxx);
         int _snowmanxxxx = 1;
         if (_snowmanxxx.isOf(Blocks.MOVING_PISTON) && _snowmanxxx.get(FACING) == _snowman) {
            BlockEntity _snowmanxxxxx = world.getBlockEntity(_snowmanxx);
            if (_snowmanxxxxx instanceof PistonBlockEntity) {
               PistonBlockEntity _snowmanxxxxxx = (PistonBlockEntity)_snowmanxxxxx;
               if (_snowmanxxxxxx.isExtending()
                  && (_snowmanxxxxxx.getProgress(0.0F) < 0.5F || world.getTime() == _snowmanxxxxxx.getSavedWorldTime() || ((ServerWorld)world).isInBlockTick())) {
                  _snowmanxxxx = 2;
               }
            }
         }

         world.addSyncedBlockEvent(pos, this, _snowmanxxxx, _snowman.getId());
      }
   }

   private boolean shouldExtend(World world, BlockPos pos, Direction pistonFace) {
      for (Direction _snowman : Direction.values()) {
         if (_snowman != pistonFace && world.isEmittingRedstonePower(pos.offset(_snowman), _snowman)) {
            return true;
         }
      }

      if (world.isEmittingRedstonePower(pos, Direction.DOWN)) {
         return true;
      } else {
         BlockPos _snowmanx = pos.up();

         for (Direction _snowmanxx : Direction.values()) {
            if (_snowmanxx != Direction.DOWN && world.isEmittingRedstonePower(_snowmanx.offset(_snowmanxx), _snowmanxx)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
      Direction _snowman = state.get(FACING);
      if (!world.isClient) {
         boolean _snowmanx = this.shouldExtend(world, pos, _snowman);
         if (_snowmanx && (type == 1 || type == 2)) {
            world.setBlockState(pos, state.with(EXTENDED, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!_snowmanx && type == 0) {
            return false;
         }
      }

      if (type == 0) {
         if (!this.move(world, pos, _snowman, true)) {
            return false;
         }

         world.setBlockState(pos, state.with(EXTENDED, Boolean.valueOf(true)), 67);
         world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
      } else if (type == 1 || type == 2) {
         BlockEntity _snowmanxx = world.getBlockEntity(pos.offset(_snowman));
         if (_snowmanxx instanceof PistonBlockEntity) {
            ((PistonBlockEntity)_snowmanxx).finish();
         }

         BlockState _snowmanxxx = Blocks.MOVING_PISTON
            .getDefaultState()
            .with(PistonExtensionBlock.FACING, _snowman)
            .with(PistonExtensionBlock.TYPE, this.sticky ? PistonType.STICKY : PistonType.DEFAULT);
         world.setBlockState(pos, _snowmanxxx, 20);
         world.setBlockEntity(pos, PistonExtensionBlock.createBlockEntityPiston(this.getDefaultState().with(FACING, Direction.byId(data & 7)), _snowman, false, true));
         world.updateNeighbors(pos, _snowmanxxx.getBlock());
         _snowmanxxx.updateNeighbors(world, pos, 2);
         if (this.sticky) {
            BlockPos _snowmanxxxx = pos.add(_snowman.getOffsetX() * 2, _snowman.getOffsetY() * 2, _snowman.getOffsetZ() * 2);
            BlockState _snowmanxxxxx = world.getBlockState(_snowmanxxxx);
            boolean _snowmanxxxxxx = false;
            if (_snowmanxxxxx.isOf(Blocks.MOVING_PISTON)) {
               BlockEntity _snowmanxxxxxxx = world.getBlockEntity(_snowmanxxxx);
               if (_snowmanxxxxxxx instanceof PistonBlockEntity) {
                  PistonBlockEntity _snowmanxxxxxxxx = (PistonBlockEntity)_snowmanxxxxxxx;
                  if (_snowmanxxxxxxxx.getFacing() == _snowman && _snowmanxxxxxxxx.isExtending()) {
                     _snowmanxxxxxxxx.finish();
                     _snowmanxxxxxx = true;
                  }
               }
            }

            if (!_snowmanxxxxxx) {
               if (type != 1
                  || _snowmanxxxxx.isAir()
                  || !isMovable(_snowmanxxxxx, world, _snowmanxxxx, _snowman.getOpposite(), false, _snowman)
                  || _snowmanxxxxx.getPistonBehavior() != PistonBehavior.NORMAL && !_snowmanxxxxx.isOf(Blocks.PISTON) && !_snowmanxxxxx.isOf(Blocks.STICKY_PISTON)) {
                  world.removeBlock(pos.offset(_snowman), false);
               } else {
                  this.move(world, pos, _snowman, false);
               }
            }
         } else {
            world.removeBlock(pos.offset(_snowman), false);
         }

         world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
      }

      return true;
   }

   public static boolean isMovable(BlockState _snowman, World _snowman, BlockPos _snowman, Direction _snowman, boolean canBreak, Direction pistonDir) {
      if (_snowman.getY() < 0 || _snowman.getY() > _snowman.getHeight() - 1 || !_snowman.getWorldBorder().contains(_snowman)) {
         return false;
      } else if (_snowman.isAir()) {
         return true;
      } else if (_snowman.isOf(Blocks.OBSIDIAN) || _snowman.isOf(Blocks.CRYING_OBSIDIAN) || _snowman.isOf(Blocks.RESPAWN_ANCHOR)) {
         return false;
      } else if (_snowman == Direction.DOWN && _snowman.getY() == 0) {
         return false;
      } else if (_snowman == Direction.UP && _snowman.getY() == _snowman.getHeight() - 1) {
         return false;
      } else {
         if (!_snowman.isOf(Blocks.PISTON) && !_snowman.isOf(Blocks.STICKY_PISTON)) {
            if (_snowman.getHardness(_snowman, _snowman) == -1.0F) {
               return false;
            }

            switch (_snowman.getPistonBehavior()) {
               case BLOCK:
                  return false;
               case DESTROY:
                  return canBreak;
               case PUSH_ONLY:
                  return _snowman == pistonDir;
            }
         } else if (_snowman.get(EXTENDED)) {
            return false;
         }

         return !_snowman.getBlock().hasBlockEntity();
      }
   }

   private boolean move(World world, BlockPos pos, Direction dir, boolean retract) {
      BlockPos _snowman = pos.offset(dir);
      if (!retract && world.getBlockState(_snowman).isOf(Blocks.PISTON_HEAD)) {
         world.setBlockState(_snowman, Blocks.AIR.getDefaultState(), 20);
      }

      PistonHandler _snowmanx = new PistonHandler(world, pos, dir, retract);
      if (!_snowmanx.calculatePush()) {
         return false;
      } else {
         Map<BlockPos, BlockState> _snowmanxx = Maps.newHashMap();
         List<BlockPos> _snowmanxxx = _snowmanx.getMovedBlocks();
         List<BlockState> _snowmanxxxx = Lists.newArrayList();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx.size(); _snowmanxxxxx++) {
            BlockPos _snowmanxxxxxx = _snowmanxxx.get(_snowmanxxxxx);
            BlockState _snowmanxxxxxxx = world.getBlockState(_snowmanxxxxxx);
            _snowmanxxxx.add(_snowmanxxxxxxx);
            _snowmanxx.put(_snowmanxxxxxx, _snowmanxxxxxxx);
         }

         List<BlockPos> _snowmanxxxxx = _snowmanx.getBrokenBlocks();
         BlockState[] _snowmanxxxxxx = new BlockState[_snowmanxxx.size() + _snowmanxxxxx.size()];
         Direction _snowmanxxxxxxx = retract ? dir : dir.getOpposite();
         int _snowmanxxxxxxxx = 0;

         for (int _snowmanxxxxxxxxx = _snowmanxxxxx.size() - 1; _snowmanxxxxxxxxx >= 0; _snowmanxxxxxxxxx--) {
            BlockPos _snowmanxxxxxxxxxx = _snowmanxxxxx.get(_snowmanxxxxxxxxx);
            BlockState _snowmanxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxxx);
            BlockEntity _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getBlock().hasBlockEntity() ? world.getBlockEntity(_snowmanxxxxxxxxxx) : null;
            dropStacks(_snowmanxxxxxxxxxxx, world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            world.setBlockState(_snowmanxxxxxxxxxx, Blocks.AIR.getDefaultState(), 18);
            _snowmanxxxxxx[_snowmanxxxxxxxx++] = _snowmanxxxxxxxxxxx;
         }

         for (int _snowmanxxxxxxxxx = _snowmanxxx.size() - 1; _snowmanxxxxxxxxx >= 0; _snowmanxxxxxxxxx--) {
            BlockPos _snowmanxxxxxxxxxx = _snowmanxxx.get(_snowmanxxxxxxxxx);
            BlockState _snowmanxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxxx);
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.offset(_snowmanxxxxxxx);
            _snowmanxx.remove(_snowmanxxxxxxxxxx);
            world.setBlockState(_snowmanxxxxxxxxxx, Blocks.MOVING_PISTON.getDefaultState().with(FACING, dir), 68);
            world.setBlockEntity(_snowmanxxxxxxxxxx, PistonExtensionBlock.createBlockEntityPiston(_snowmanxxxx.get(_snowmanxxxxxxxxx), dir, retract, false));
            _snowmanxxxxxx[_snowmanxxxxxxxx++] = _snowmanxxxxxxxxxxx;
         }

         if (retract) {
            PistonType _snowmanxxxxxxxxx = this.sticky ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState _snowmanxxxxxxxxxx = Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, dir).with(PistonHeadBlock.TYPE, _snowmanxxxxxxxxx);
            BlockState _snowmanxxxxxxxxxxx = Blocks.MOVING_PISTON
               .getDefaultState()
               .with(PistonExtensionBlock.FACING, dir)
               .with(PistonExtensionBlock.TYPE, this.sticky ? PistonType.STICKY : PistonType.DEFAULT);
            _snowmanxx.remove(_snowman);
            world.setBlockState(_snowman, _snowmanxxxxxxxxxxx, 68);
            world.setBlockEntity(_snowman, PistonExtensionBlock.createBlockEntityPiston(_snowmanxxxxxxxxxx, dir, true, true));
         }

         BlockState _snowmanxxxxxxxxx = Blocks.AIR.getDefaultState();

         for (BlockPos _snowmanxxxxxxxxxx : _snowmanxx.keySet()) {
            world.setBlockState(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, 82);
         }

         for (Entry<BlockPos, BlockState> _snowmanxxxxxxxxxx : _snowmanxx.entrySet()) {
            BlockPos _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getKey();
            BlockState _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.getValue();
            _snowmanxxxxxxxxxxxx.prepare(world, _snowmanxxxxxxxxxxx, 2);
            _snowmanxxxxxxxxx.updateNeighbors(world, _snowmanxxxxxxxxxxx, 2);
            _snowmanxxxxxxxxx.prepare(world, _snowmanxxxxxxxxxxx, 2);
         }

         _snowmanxxxxxxxx = 0;

         for (int _snowmanxxxxxxxxxx = _snowmanxxxxx.size() - 1; _snowmanxxxxxxxxxx >= 0; _snowmanxxxxxxxxxx--) {
            BlockState _snowmanxxxxxxxxxxx = _snowmanxxxxxx[_snowmanxxxxxxxx++];
            BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxxxx.get(_snowmanxxxxxxxxxx);
            _snowmanxxxxxxxxxxx.prepare(world, _snowmanxxxxxxxxxxxx, 2);
            world.updateNeighborsAlways(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx.getBlock());
         }

         for (int _snowmanxxxxxxxxxx = _snowmanxxx.size() - 1; _snowmanxxxxxxxxxx >= 0; _snowmanxxxxxxxxxx--) {
            world.updateNeighborsAlways(_snowmanxxx.get(_snowmanxxxxxxxxxx), _snowmanxxxxxx[_snowmanxxxxxxxx++].getBlock());
         }

         if (retract) {
            world.updateNeighborsAlways(_snowman, Blocks.PISTON_HEAD);
         }

         return true;
      }
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
      builder.add(FACING, EXTENDED);
   }

   @Override
   public boolean hasSidedTransparency(BlockState state) {
      return state.get(EXTENDED);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
