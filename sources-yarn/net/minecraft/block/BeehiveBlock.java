package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   public BeehiveBlock(AbstractBlock.Settings arg) {
      super(arg);
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
         BeehiveBlockEntity lv = (BeehiveBlockEntity)blockEntity;
         if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            lv.angerBees(player, state, BeehiveBlockEntity.BeeState.EMERGENCY);
            world.updateComparators(pos, this);
            this.angerNearbyBees(world, pos);
         }

         Criteria.BEE_NEST_DESTROYED.test((ServerPlayerEntity)player, state.getBlock(), stack, lv.getBeeCount());
      }
   }

   private void angerNearbyBees(World world, BlockPos pos) {
      List<BeeEntity> list = world.getNonSpectatingEntities(BeeEntity.class, new Box(pos).expand(8.0, 6.0, 8.0));
      if (!list.isEmpty()) {
         List<PlayerEntity> list2 = world.getNonSpectatingEntities(PlayerEntity.class, new Box(pos).expand(8.0, 6.0, 8.0));
         int i = list2.size();

         for (BeeEntity lv : list) {
            if (lv.getTarget() == null) {
               lv.setTarget(list2.get(world.random.nextInt(i)));
            }
         }
      }
   }

   public static void dropHoneycomb(World world, BlockPos pos) {
      dropStack(world, pos, new ItemStack(Items.HONEYCOMB, 3));
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      ItemStack lv = player.getStackInHand(hand);
      int i = state.get(HONEY_LEVEL);
      boolean bl = false;
      if (i >= 5) {
         if (lv.getItem() == Items.SHEARS) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            dropHoneycomb(world, pos);
            lv.damage(1, player, playerx -> playerx.sendToolBreakStatus(hand));
            bl = true;
         } else if (lv.getItem() == Items.GLASS_BOTTLE) {
            lv.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            if (lv.isEmpty()) {
               player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));
            } else if (!player.inventory.insertStack(new ItemStack(Items.HONEY_BOTTLE))) {
               player.dropItem(new ItemStack(Items.HONEY_BOTTLE), false);
            }

            bl = true;
         }
      }

      if (bl) {
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
      BlockEntity lv = world.getBlockEntity(pos);
      if (lv instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity lv2 = (BeehiveBlockEntity)lv;
         return !lv2.hasNoBees();
      } else {
         return false;
      }
   }

   public void takeHoney(World world, BlockState state, BlockPos pos, @Nullable PlayerEntity player, BeehiveBlockEntity.BeeState arg5) {
      this.takeHoney(world, state, pos);
      BlockEntity lv = world.getBlockEntity(pos);
      if (lv instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity lv2 = (BeehiveBlockEntity)lv;
         lv2.angerBees(player, state, arg5);
      }
   }

   public void takeHoney(World world, BlockState state, BlockPos pos) {
      world.setBlockState(pos, state.with(HONEY_LEVEL, Integer.valueOf(0)), 3);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(HONEY_LEVEL) >= 5) {
         for (int i = 0; i < random.nextInt(1) + 1; i++) {
            this.spawnHoneyParticles(world, pos, state);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   private void spawnHoneyParticles(World world, BlockPos pos, BlockState state) {
      if (state.getFluidState().isEmpty() && !(world.random.nextFloat() < 0.3F)) {
         VoxelShape lv = state.getCollisionShape(world, pos);
         double d = lv.getMax(Direction.Axis.Y);
         if (d >= 1.0 && !state.isIn(BlockTags.IMPERMEABLE)) {
            double e = lv.getMin(Direction.Axis.Y);
            if (e > 0.0) {
               this.addHoneyParticle(world, pos, lv, (double)pos.getY() + e - 0.05);
            } else {
               BlockPos lv2 = pos.down();
               BlockState lv3 = world.getBlockState(lv2);
               VoxelShape lv4 = lv3.getCollisionShape(world, lv2);
               double f = lv4.getMax(Direction.Axis.Y);
               if ((f < 1.0 || !lv3.isFullCube(world, lv2)) && lv3.getFluidState().isEmpty()) {
                  this.addHoneyParticle(world, pos, lv, (double)pos.getY() - 0.05);
               }
            }
         }
      }
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
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
         BlockEntity lv = world.getBlockEntity(pos);
         if (lv instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity lv2 = (BeehiveBlockEntity)lv;
            ItemStack lv3 = new ItemStack(this);
            int i = state.get(HONEY_LEVEL);
            boolean bl = !lv2.hasNoBees();
            if (!bl && i == 0) {
               return;
            }

            if (bl) {
               CompoundTag lv4 = new CompoundTag();
               lv4.put("Bees", lv2.getBees());
               lv3.putSubTag("BlockEntityTag", lv4);
            }

            CompoundTag lv5 = new CompoundTag();
            lv5.putInt("honey_level", i);
            lv3.putSubTag("BlockStateTag", lv5);
            ItemEntity lv6 = new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), lv3);
            lv6.setToDefaultPickupDelay();
            world.spawnEntity(lv6);
         }
      }

      super.onBreak(world, pos, state, player);
   }

   @Override
   public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
      Entity lv = builder.getNullable(LootContextParameters.THIS_ENTITY);
      if (lv instanceof TntEntity
         || lv instanceof CreeperEntity
         || lv instanceof WitherSkullEntity
         || lv instanceof WitherEntity
         || lv instanceof TntMinecartEntity) {
         BlockEntity lv2 = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
         if (lv2 instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity lv3 = (BeehiveBlockEntity)lv2;
            lv3.angerBees(null, state, BeehiveBlockEntity.BeeState.EMERGENCY);
         }
      }

      return super.getDroppedStacks(state, builder);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (world.getBlockState(posFrom).getBlock() instanceof FireBlock) {
         BlockEntity lv = world.getBlockEntity(pos);
         if (lv instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity lv2 = (BeehiveBlockEntity)lv;
            lv2.angerBees(null, state, BeehiveBlockEntity.BeeState.EMERGENCY);
         }
      }

      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   public static Direction getRandomGenerationDirection(Random random) {
      return Util.getRandom(GENERATE_DIRECTIONS, random);
   }
}
