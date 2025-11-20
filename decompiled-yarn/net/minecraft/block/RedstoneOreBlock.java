package net.minecraft.block;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RedstoneOreBlock extends Block {
   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

   public RedstoneOreBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.getDefaultState().with(LIT, Boolean.valueOf(false)));
   }

   @Override
   public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
      light(state, world, pos);
      super.onBlockBreakStart(state, world, pos, player);
   }

   @Override
   public void onSteppedOn(World world, BlockPos pos, Entity entity) {
      light(world.getBlockState(pos), world, pos);
      super.onSteppedOn(world, pos, entity);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {
         spawnParticles(world, pos);
      } else {
         light(state, world, pos);
      }

      ItemStack _snowman = player.getStackInHand(hand);
      return _snowman.getItem() instanceof BlockItem && new ItemPlacementContext(player, hand, _snowman, hit).canPlace() ? ActionResult.PASS : ActionResult.SUCCESS;
   }

   private static void light(BlockState state, World world, BlockPos pos) {
      spawnParticles(world, pos);
      if (!state.get(LIT)) {
         world.setBlockState(pos, state.with(LIT, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public boolean hasRandomTicks(BlockState state) {
      return state.get(LIT);
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (state.get(LIT)) {
         world.setBlockState(pos, state.with(LIT, Boolean.valueOf(false)), 3);
      }
   }

   @Override
   public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
      super.onStacksDropped(state, world, pos, stack);
      if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
         int _snowman = 1 + world.random.nextInt(5);
         this.dropExperience(world, pos, _snowman);
      }
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(LIT)) {
         spawnParticles(world, pos);
      }
   }

   private static void spawnParticles(World world, BlockPos pos) {
      double _snowman = 0.5625;
      Random _snowmanx = world.random;

      for (Direction _snowmanxx : Direction.values()) {
         BlockPos _snowmanxxx = pos.offset(_snowmanxx);
         if (!world.getBlockState(_snowmanxxx).isOpaqueFullCube(world, _snowmanxxx)) {
            Direction.Axis _snowmanxxxx = _snowmanxx.getAxis();
            double _snowmanxxxxx = _snowmanxxxx == Direction.Axis.X ? 0.5 + 0.5625 * (double)_snowmanxx.getOffsetX() : (double)_snowmanx.nextFloat();
            double _snowmanxxxxxx = _snowmanxxxx == Direction.Axis.Y ? 0.5 + 0.5625 * (double)_snowmanxx.getOffsetY() : (double)_snowmanx.nextFloat();
            double _snowmanxxxxxxx = _snowmanxxxx == Direction.Axis.Z ? 0.5 + 0.5625 * (double)_snowmanxx.getOffsetZ() : (double)_snowmanx.nextFloat();
            world.addParticle(DustParticleEffect.RED, (double)pos.getX() + _snowmanxxxxx, (double)pos.getY() + _snowmanxxxxxx, (double)pos.getZ() + _snowmanxxxxxxx, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(LIT);
   }
}
