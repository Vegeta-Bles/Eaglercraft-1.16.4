package net.minecraft.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.ShulkerLidCollisions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ShulkerBoxBlock extends BlockWithEntity {
   public static final EnumProperty<Direction> FACING = FacingBlock.FACING;
   public static final Identifier CONTENTS = new Identifier("contents");
   @Nullable
   private final DyeColor color;

   public ShulkerBoxBlock(@Nullable DyeColor color, AbstractBlock.Settings settings) {
      super(settings);
      this.color = color;
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new ShulkerBoxBlockEntity(this.color);
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {
         return ActionResult.SUCCESS;
      } else if (player.isSpectator()) {
         return ActionResult.CONSUME;
      } else {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof ShulkerBoxBlockEntity) {
            ShulkerBoxBlockEntity _snowmanx = (ShulkerBoxBlockEntity)_snowman;
            boolean _snowmanxx;
            if (_snowmanx.getAnimationStage() == ShulkerBoxBlockEntity.AnimationStage.CLOSED) {
               Direction _snowmanxxx = state.get(FACING);
               _snowmanxx = world.isSpaceEmpty(ShulkerLidCollisions.getLidCollisionBox(pos, _snowmanxxx));
            } else {
               _snowmanxx = true;
            }

            if (_snowmanxx) {
               player.openHandledScreen(_snowmanx);
               player.incrementStat(Stats.OPEN_SHULKER_BOX);
               PiglinBrain.onGuardedBlockInteracted(player, true);
            }

            return ActionResult.CONSUME;
         } else {
            return ActionResult.PASS;
         }
      }
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(FACING, ctx.getSide());
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof ShulkerBoxBlockEntity) {
         ShulkerBoxBlockEntity _snowmanx = (ShulkerBoxBlockEntity)_snowman;
         if (!world.isClient && player.isCreative() && !_snowmanx.isEmpty()) {
            ItemStack _snowmanxx = getItemStack(this.getColor());
            CompoundTag _snowmanxxx = _snowmanx.serializeInventory(new CompoundTag());
            if (!_snowmanxxx.isEmpty()) {
               _snowmanxx.putSubTag("BlockEntityTag", _snowmanxxx);
            }

            if (_snowmanx.hasCustomName()) {
               _snowmanxx.setCustomName(_snowmanx.getCustomName());
            }

            ItemEntity _snowmanxxxx = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, _snowmanxx);
            _snowmanxxxx.setToDefaultPickupDelay();
            world.spawnEntity(_snowmanxxxx);
         } else {
            _snowmanx.checkLootInteraction(player);
         }
      }

      super.onBreak(world, pos, state, player);
   }

   @Override
   public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
      BlockEntity _snowman = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
      if (_snowman instanceof ShulkerBoxBlockEntity) {
         ShulkerBoxBlockEntity _snowmanx = (ShulkerBoxBlockEntity)_snowman;
         builder = builder.putDrop(CONTENTS, (_snowmanxx, _snowmanxxx) -> {
            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
               _snowmanxxx.accept(_snowman.getStack(_snowmanxxxx));
            }
         });
      }

      return super.getDroppedStacks(state, builder);
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      if (itemStack.hasCustomName()) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof ShulkerBoxBlockEntity) {
            ((ShulkerBoxBlockEntity)_snowman).setCustomName(itemStack.getName());
         }
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!state.isOf(newState.getBlock())) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof ShulkerBoxBlockEntity) {
            world.updateComparators(pos, state.getBlock());
         }

         super.onStateReplaced(state, world, pos, newState, moved);
      }
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
      super.appendTooltip(stack, world, tooltip, options);
      CompoundTag _snowman = stack.getSubTag("BlockEntityTag");
      if (_snowman != null) {
         if (_snowman.contains("LootTable", 8)) {
            tooltip.add(new LiteralText("???????"));
         }

         if (_snowman.contains("Items", 9)) {
            DefaultedList<ItemStack> _snowmanx = DefaultedList.ofSize(27, ItemStack.EMPTY);
            Inventories.fromTag(_snowman, _snowmanx);
            int _snowmanxx = 0;
            int _snowmanxxx = 0;

            for (ItemStack _snowmanxxxx : _snowmanx) {
               if (!_snowmanxxxx.isEmpty()) {
                  _snowmanxxx++;
                  if (_snowmanxx <= 4) {
                     _snowmanxx++;
                     MutableText _snowmanxxxxx = _snowmanxxxx.getName().shallowCopy();
                     _snowmanxxxxx.append(" x").append(String.valueOf(_snowmanxxxx.getCount()));
                     tooltip.add(_snowmanxxxxx);
                  }
               }
            }

            if (_snowmanxxx - _snowmanxx > 0) {
               tooltip.add(new TranslatableText("container.shulkerBox.more", _snowmanxxx - _snowmanxx).formatted(Formatting.ITALIC));
            }
         }
      }
   }

   @Override
   public PistonBehavior getPistonBehavior(BlockState state) {
      return PistonBehavior.DESTROY;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      return _snowman instanceof ShulkerBoxBlockEntity ? VoxelShapes.cuboid(((ShulkerBoxBlockEntity)_snowman).getBoundingBox(state)) : VoxelShapes.fullCube();
   }

   @Override
   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   @Override
   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      return ScreenHandler.calculateComparatorOutput((Inventory)world.getBlockEntity(pos));
   }

   @Override
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      ItemStack _snowman = super.getPickStack(world, pos, state);
      ShulkerBoxBlockEntity _snowmanx = (ShulkerBoxBlockEntity)world.getBlockEntity(pos);
      CompoundTag _snowmanxx = _snowmanx.serializeInventory(new CompoundTag());
      if (!_snowmanxx.isEmpty()) {
         _snowman.putSubTag("BlockEntityTag", _snowmanxx);
      }

      return _snowman;
   }

   @Nullable
   public static DyeColor getColor(Item item) {
      return getColor(Block.getBlockFromItem(item));
   }

   @Nullable
   public static DyeColor getColor(Block block) {
      return block instanceof ShulkerBoxBlock ? ((ShulkerBoxBlock)block).getColor() : null;
   }

   public static Block get(@Nullable DyeColor dyeColor) {
      if (dyeColor == null) {
         return Blocks.SHULKER_BOX;
      } else {
         switch (dyeColor) {
            case WHITE:
               return Blocks.WHITE_SHULKER_BOX;
            case ORANGE:
               return Blocks.ORANGE_SHULKER_BOX;
            case MAGENTA:
               return Blocks.MAGENTA_SHULKER_BOX;
            case LIGHT_BLUE:
               return Blocks.LIGHT_BLUE_SHULKER_BOX;
            case YELLOW:
               return Blocks.YELLOW_SHULKER_BOX;
            case LIME:
               return Blocks.LIME_SHULKER_BOX;
            case PINK:
               return Blocks.PINK_SHULKER_BOX;
            case GRAY:
               return Blocks.GRAY_SHULKER_BOX;
            case LIGHT_GRAY:
               return Blocks.LIGHT_GRAY_SHULKER_BOX;
            case CYAN:
               return Blocks.CYAN_SHULKER_BOX;
            case PURPLE:
            default:
               return Blocks.PURPLE_SHULKER_BOX;
            case BLUE:
               return Blocks.BLUE_SHULKER_BOX;
            case BROWN:
               return Blocks.BROWN_SHULKER_BOX;
            case GREEN:
               return Blocks.GREEN_SHULKER_BOX;
            case RED:
               return Blocks.RED_SHULKER_BOX;
            case BLACK:
               return Blocks.BLACK_SHULKER_BOX;
         }
      }
   }

   @Nullable
   public DyeColor getColor() {
      return this.color;
   }

   public static ItemStack getItemStack(@Nullable DyeColor color) {
      return new ItemStack(get(color));
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }
}
