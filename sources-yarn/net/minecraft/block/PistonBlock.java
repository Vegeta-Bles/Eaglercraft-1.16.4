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
      Direction lv = state.get(FACING);
      boolean bl = this.shouldExtend(world, pos, lv);
      if (bl && !state.get(EXTENDED)) {
         if (new PistonHandler(world, pos, lv, true).calculatePush()) {
            world.addSyncedBlockEvent(pos, this, 0, lv.getId());
         }
      } else if (!bl && state.get(EXTENDED)) {
         BlockPos lv2 = pos.offset(lv, 2);
         BlockState lv3 = world.getBlockState(lv2);
         int i = 1;
         if (lv3.isOf(Blocks.MOVING_PISTON) && lv3.get(FACING) == lv) {
            BlockEntity lv4 = world.getBlockEntity(lv2);
            if (lv4 instanceof PistonBlockEntity) {
               PistonBlockEntity lv5 = (PistonBlockEntity)lv4;
               if (lv5.isExtending() && (lv5.getProgress(0.0F) < 0.5F || world.getTime() == lv5.getSavedWorldTime() || ((ServerWorld)world).isInBlockTick())) {
                  i = 2;
               }
            }
         }

         world.addSyncedBlockEvent(pos, this, i, lv.getId());
      }
   }

   private boolean shouldExtend(World world, BlockPos pos, Direction pistonFace) {
      for (Direction lv : Direction.values()) {
         if (lv != pistonFace && world.isEmittingRedstonePower(pos.offset(lv), lv)) {
            return true;
         }
      }

      if (world.isEmittingRedstonePower(pos, Direction.DOWN)) {
         return true;
      } else {
         BlockPos lv2 = pos.up();

         for (Direction lv3 : Direction.values()) {
            if (lv3 != Direction.DOWN && world.isEmittingRedstonePower(lv2.offset(lv3), lv3)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
      Direction lv = state.get(FACING);
      if (!world.isClient) {
         boolean bl = this.shouldExtend(world, pos, lv);
         if (bl && (type == 1 || type == 2)) {
            world.setBlockState(pos, state.with(EXTENDED, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!bl && type == 0) {
            return false;
         }
      }

      if (type == 0) {
         if (!this.move(world, pos, lv, true)) {
            return false;
         }

         world.setBlockState(pos, state.with(EXTENDED, Boolean.valueOf(true)), 67);
         world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
      } else if (type == 1 || type == 2) {
         BlockEntity lv2 = world.getBlockEntity(pos.offset(lv));
         if (lv2 instanceof PistonBlockEntity) {
            ((PistonBlockEntity)lv2).finish();
         }

         BlockState lv3 = Blocks.MOVING_PISTON
            .getDefaultState()
            .with(PistonExtensionBlock.FACING, lv)
            .with(PistonExtensionBlock.TYPE, this.sticky ? PistonType.STICKY : PistonType.DEFAULT);
         world.setBlockState(pos, lv3, 20);
         world.setBlockEntity(pos, PistonExtensionBlock.createBlockEntityPiston(this.getDefaultState().with(FACING, Direction.byId(data & 7)), lv, false, true));
         world.updateNeighbors(pos, lv3.getBlock());
         lv3.updateNeighbors(world, pos, 2);
         if (this.sticky) {
            BlockPos lv4 = pos.add(lv.getOffsetX() * 2, lv.getOffsetY() * 2, lv.getOffsetZ() * 2);
            BlockState lv5 = world.getBlockState(lv4);
            boolean bl2 = false;
            if (lv5.isOf(Blocks.MOVING_PISTON)) {
               BlockEntity lv6 = world.getBlockEntity(lv4);
               if (lv6 instanceof PistonBlockEntity) {
                  PistonBlockEntity lv7 = (PistonBlockEntity)lv6;
                  if (lv7.getFacing() == lv && lv7.isExtending()) {
                     lv7.finish();
                     bl2 = true;
                  }
               }
            }

            if (!bl2) {
               if (type != 1
                  || lv5.isAir()
                  || !isMovable(lv5, world, lv4, lv.getOpposite(), false, lv)
                  || lv5.getPistonBehavior() != PistonBehavior.NORMAL && !lv5.isOf(Blocks.PISTON) && !lv5.isOf(Blocks.STICKY_PISTON)) {
                  world.removeBlock(pos.offset(lv), false);
               } else {
                  this.move(world, pos, lv, false);
               }
            }
         } else {
            world.removeBlock(pos.offset(lv), false);
         }

         world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
      }

      return true;
   }

   public static boolean isMovable(BlockState arg, World arg2, BlockPos arg3, Direction arg4, boolean canBreak, Direction pistonDir) {
      if (arg3.getY() < 0 || arg3.getY() > arg2.getHeight() - 1 || !arg2.getWorldBorder().contains(arg3)) {
         return false;
      } else if (arg.isAir()) {
         return true;
      } else if (arg.isOf(Blocks.OBSIDIAN) || arg.isOf(Blocks.CRYING_OBSIDIAN) || arg.isOf(Blocks.RESPAWN_ANCHOR)) {
         return false;
      } else if (arg4 == Direction.DOWN && arg3.getY() == 0) {
         return false;
      } else if (arg4 == Direction.UP && arg3.getY() == arg2.getHeight() - 1) {
         return false;
      } else {
         if (!arg.isOf(Blocks.PISTON) && !arg.isOf(Blocks.STICKY_PISTON)) {
            if (arg.getHardness(arg2, arg3) == -1.0F) {
               return false;
            }

            switch (arg.getPistonBehavior()) {
               case BLOCK:
                  return false;
               case DESTROY:
                  return canBreak;
               case PUSH_ONLY:
                  return arg4 == pistonDir;
            }
         } else if (arg.get(EXTENDED)) {
            return false;
         }

         return !arg.getBlock().hasBlockEntity();
      }
   }

   private boolean move(World world, BlockPos pos, Direction dir, boolean retract) {
      BlockPos lv = pos.offset(dir);
      if (!retract && world.getBlockState(lv).isOf(Blocks.PISTON_HEAD)) {
         world.setBlockState(lv, Blocks.AIR.getDefaultState(), 20);
      }

      PistonHandler lv2 = new PistonHandler(world, pos, dir, retract);
      if (!lv2.calculatePush()) {
         return false;
      } else {
         Map<BlockPos, BlockState> map = Maps.newHashMap();
         List<BlockPos> list = lv2.getMovedBlocks();
         List<BlockState> list2 = Lists.newArrayList();

         for (int i = 0; i < list.size(); i++) {
            BlockPos lv3 = list.get(i);
            BlockState lv4 = world.getBlockState(lv3);
            list2.add(lv4);
            map.put(lv3, lv4);
         }

         List<BlockPos> list3 = lv2.getBrokenBlocks();
         BlockState[] lvs = new BlockState[list.size() + list3.size()];
         Direction lv5 = retract ? dir : dir.getOpposite();
         int j = 0;

         for (int k = list3.size() - 1; k >= 0; k--) {
            BlockPos lv6 = list3.get(k);
            BlockState lv7 = world.getBlockState(lv6);
            BlockEntity lv8 = lv7.getBlock().hasBlockEntity() ? world.getBlockEntity(lv6) : null;
            dropStacks(lv7, world, lv6, lv8);
            world.setBlockState(lv6, Blocks.AIR.getDefaultState(), 18);
            lvs[j++] = lv7;
         }

         for (int l = list.size() - 1; l >= 0; l--) {
            BlockPos lv9 = list.get(l);
            BlockState lv10 = world.getBlockState(lv9);
            lv9 = lv9.offset(lv5);
            map.remove(lv9);
            world.setBlockState(lv9, Blocks.MOVING_PISTON.getDefaultState().with(FACING, dir), 68);
            world.setBlockEntity(lv9, PistonExtensionBlock.createBlockEntityPiston(list2.get(l), dir, retract, false));
            lvs[j++] = lv10;
         }

         if (retract) {
            PistonType lv11 = this.sticky ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState lv12 = Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, dir).with(PistonHeadBlock.TYPE, lv11);
            BlockState lv13 = Blocks.MOVING_PISTON
               .getDefaultState()
               .with(PistonExtensionBlock.FACING, dir)
               .with(PistonExtensionBlock.TYPE, this.sticky ? PistonType.STICKY : PistonType.DEFAULT);
            map.remove(lv);
            world.setBlockState(lv, lv13, 68);
            world.setBlockEntity(lv, PistonExtensionBlock.createBlockEntityPiston(lv12, dir, true, true));
         }

         BlockState lv14 = Blocks.AIR.getDefaultState();

         for (BlockPos lv15 : map.keySet()) {
            world.setBlockState(lv15, lv14, 82);
         }

         for (Entry<BlockPos, BlockState> entry : map.entrySet()) {
            BlockPos lv16 = entry.getKey();
            BlockState lv17 = entry.getValue();
            lv17.prepare(world, lv16, 2);
            lv14.updateNeighbors(world, lv16, 2);
            lv14.prepare(world, lv16, 2);
         }

         j = 0;

         for (int m = list3.size() - 1; m >= 0; m--) {
            BlockState lv18 = lvs[j++];
            BlockPos lv19 = list3.get(m);
            lv18.prepare(world, lv19, 2);
            world.updateNeighborsAlways(lv19, lv18.getBlock());
         }

         for (int n = list.size() - 1; n >= 0; n--) {
            world.updateNeighborsAlways(list.get(n), lvs[j++].getBlock());
         }

         if (retract) {
            world.updateNeighborsAlways(lv, Blocks.PISTON_HEAD);
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
