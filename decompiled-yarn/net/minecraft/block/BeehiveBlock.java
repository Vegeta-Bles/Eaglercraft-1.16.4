package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BeehiveBlock extends BlockWithEntity {
   private static final Direction[] GENERATE_DIRECTIONS = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH};
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final IntProperty HONEY_LEVEL = Properties.HONEY_LEVEL;

   public BeehiveBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(HONEY_LEVEL, Integer.valueOf(0)).with(FACING, Direction.NORTH));
   }

   @Override
   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   @Override
   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      return state.get(HONEY_LEVEL);
   }

   @Override
   public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
      super.afterBreak(world, player, pos, state, blockEntity, stack);
      if (!world.isClient && blockEntity instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity _snowman = (BeehiveBlockEntity)blockEntity;
         if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            _snowman.angerBees(player, state, BeehiveBlockEntity.BeeState.EMERGENCY);
            world.updateComparators(pos, this);
            this.angerNearbyBees(world, pos);
         }

         Criteria.BEE_NEST_DESTROYED.test((ServerPlayerEntity)player, state.getBlock(), stack, _snowman.getBeeCount());
      }
   }

   private void angerNearbyBees(World world, BlockPos pos) {
      List<BeeEntity> _snowman = world.getNonSpectatingEntities(BeeEntity.class, new Box(pos).expand(8.0, 6.0, 8.0));
      if (!_snowman.isEmpty()) {
         List<PlayerEntity> _snowmanx = world.getNonSpectatingEntities(PlayerEntity.class, new Box(pos).expand(8.0, 6.0, 8.0));
         int _snowmanxx = _snowmanx.size();

         for (BeeEntity _snowmanxxx : _snowman) {
            if (_snowmanxxx.getTarget() == null) {
               _snowmanxxx.setTarget(_snowmanx.get(world.random.nextInt(_snowmanxx)));
            }
         }
      }
   }

   public static void dropHoneycomb(World world, BlockPos pos) {
      dropStack(world, pos, new ItemStack(Items.HONEYCOMB, 3));
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      ItemStack _snowman = player.getStackInHand(hand);
      int _snowmanx = state.get(HONEY_LEVEL);
      boolean _snowmanxx = false;
      if (_snowmanx >= 5) {
         if (_snowman.getItem() == Items.SHEARS) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            dropHoneycomb(world, pos);
            _snowman.damage(1, player, playerx -> playerx.sendToolBreakStatus(hand));
            _snowmanxx = true;
         } else if (_snowman.getItem() == Items.GLASS_BOTTLE) {
            _snowman.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            if (_snowman.isEmpty()) {
               player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));
            } else if (!player.inventory.insertStack(new ItemStack(Items.HONEY_BOTTLE))) {
               player.dropItem(new ItemStack(Items.HONEY_BOTTLE), false);
            }

            _snowmanxx = true;
         }
      }

      if (_snowmanxx) {
         if (!CampfireBlock.isLitCampfireInRange(world, pos)) {
            if (this.hasBees(world, pos)) {
               this.angerNearbyBees(world, pos);
            }

            this.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
         } else {
            this.takeHoney(world, state, pos);
         }

         return ActionResult.success(world.isClient);
      } else {
         return super.onUse(state, world, pos, player, hand, hit);
      }
   }

   private boolean hasBees(World world, BlockPos pos) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity _snowmanx = (BeehiveBlockEntity)_snowman;
         return !_snowmanx.hasNoBees();
      } else {
         return false;
      }
   }

   public void takeHoney(World world, BlockState state, BlockPos pos, @Nullable PlayerEntity player, BeehiveBlockEntity.BeeState _snowman) {
      this.takeHoney(world, state, pos);
      BlockEntity _snowmanx = world.getBlockEntity(pos);
      if (_snowmanx instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity _snowmanxx = (BeehiveBlockEntity)_snowmanx;
         _snowmanxx.angerBees(player, state, _snowman);
      }
   }

   public void takeHoney(World world, BlockState state, BlockPos pos) {
      world.setBlockState(pos, state.with(HONEY_LEVEL, Integer.valueOf(0)), 3);
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(HONEY_LEVEL) >= 5) {
         for (int _snowman = 0; _snowman < random.nextInt(1) + 1; _snowman++) {
            this.spawnHoneyParticles(world, pos, state);
         }
      }
   }

   private void spawnHoneyParticles(World world, BlockPos pos, BlockState state) {
      if (state.getFluidState().isEmpty() && !(world.random.nextFloat() < 0.3F)) {
         VoxelShape _snowman = state.getCollisionShape(world, pos);
         double _snowmanx = _snowman.getMax(Direction.Axis.Y);
         if (_snowmanx >= 1.0 && !state.isIn(BlockTags.IMPERMEABLE)) {
            double _snowmanxx = _snowman.getMin(Direction.Axis.Y);
            if (_snowmanxx > 0.0) {
               this.addHoneyParticle(world, pos, _snowman, (double)pos.getY() + _snowmanxx - 0.05);
            } else {
               BlockPos _snowmanxxx = pos.down();
               BlockState _snowmanxxxx = world.getBlockState(_snowmanxxx);
               VoxelShape _snowmanxxxxx = _snowmanxxxx.getCollisionShape(world, _snowmanxxx);
               double _snowmanxxxxxx = _snowmanxxxxx.getMax(Direction.Axis.Y);
               if ((_snowmanxxxxxx < 1.0 || !_snowmanxxxx.isFullCube(world, _snowmanxxx)) && _snowmanxxxx.getFluidState().isEmpty()) {
                  this.addHoneyParticle(world, pos, _snowman, (double)pos.getY() - 0.05);
               }
            }
         }
      }
   }

   private void addHoneyParticle(World world, BlockPos pos, VoxelShape shape, double height) {
      this.addHoneyParticle(
         world,
         (double)pos.getX() + shape.getMin(Direction.Axis.X),
         (double)pos.getX() + shape.getMax(Direction.Axis.X),
         (double)pos.getZ() + shape.getMin(Direction.Axis.Z),
         (double)pos.getZ() + shape.getMax(Direction.Axis.Z),
         height
      );
   }

   private void addHoneyParticle(World world, double minX, double maxX, double minZ, double maxZ, double height) {
      world.addParticle(
         ParticleTypes.DRIPPING_HONEY,
         MathHelper.lerp(world.random.nextDouble(), minX, maxX),
         height,
         MathHelper.lerp(world.random.nextDouble(), minZ, maxZ),
         0.0,
         0.0,
         0.0
      );
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(HONEY_LEVEL, FACING);
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Nullable
   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new BeehiveBlockEntity();
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient && player.isCreative() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity _snowmanx = (BeehiveBlockEntity)_snowman;
            ItemStack _snowmanxx = new ItemStack(this);
            int _snowmanxxx = state.get(HONEY_LEVEL);
            boolean _snowmanxxxx = !_snowmanx.hasNoBees();
            if (!_snowmanxxxx && _snowmanxxx == 0) {
               return;
            }

            if (_snowmanxxxx) {
               CompoundTag _snowmanxxxxx = new CompoundTag();
               _snowmanxxxxx.put("Bees", _snowmanx.getBees());
               _snowmanxx.putSubTag("BlockEntityTag", _snowmanxxxxx);
            }

            CompoundTag _snowmanxxxxx = new CompoundTag();
            _snowmanxxxxx.putInt("honey_level", _snowmanxxx);
            _snowmanxx.putSubTag("BlockStateTag", _snowmanxxxxx);
            ItemEntity _snowmanxxxxxx = new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), _snowmanxx);
            _snowmanxxxxxx.setToDefaultPickupDelay();
            world.spawnEntity(_snowmanxxxxxx);
         }
      }

      super.onBreak(world, pos, state, player);
   }

   @Override
   public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
      Entity _snowman = builder.getNullable(LootContextParameters.THIS_ENTITY);
      if (_snowman instanceof TntEntity || _snowman instanceof CreeperEntity || _snowman instanceof WitherSkullEntity || _snowman instanceof WitherEntity || _snowman instanceof TntMinecartEntity
         )
       {
         BlockEntity _snowmanx = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
         if (_snowmanx instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity _snowmanxx = (BeehiveBlockEntity)_snowmanx;
            _snowmanxx.angerBees(null, state, BeehiveBlockEntity.BeeState.EMERGENCY);
         }
      }

      return super.getDroppedStacks(state, builder);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (world.getBlockState(posFrom).getBlock() instanceof FireBlock) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity _snowmanx = (BeehiveBlockEntity)_snowman;
            _snowmanx.angerBees(null, state, BeehiveBlockEntity.BeeState.EMERGENCY);
         }
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   public static Direction getRandomGenerationDirection(Random _snowman) {
      return Util.getRandom(GENERATE_DIRECTIONS, _snowman);
   }
}
