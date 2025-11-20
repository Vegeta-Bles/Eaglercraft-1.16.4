package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class WitherSkullBlock extends SkullBlock {
   @Nullable
   private static BlockPattern witherBossPattern;
   @Nullable
   private static BlockPattern witherDispenserPattern;

   protected WitherSkullBlock(AbstractBlock.Settings _snowman) {
      super(SkullBlock.Type.WITHER_SKELETON, _snowman);
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
      super.onPlaced(world, pos, state, placer, itemStack);
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof SkullBlockEntity) {
         onPlaced(world, pos, (SkullBlockEntity)_snowman);
      }
   }

   public static void onPlaced(World world, BlockPos pos, SkullBlockEntity blockEntity) {
      if (!world.isClient) {
         BlockState _snowman = blockEntity.getCachedState();
         boolean _snowmanx = _snowman.isOf(Blocks.WITHER_SKELETON_SKULL) || _snowman.isOf(Blocks.WITHER_SKELETON_WALL_SKULL);
         if (_snowmanx && pos.getY() >= 0 && world.getDifficulty() != Difficulty.PEACEFUL) {
            BlockPattern _snowmanxx = getWitherBossPattern();
            BlockPattern.Result _snowmanxxx = _snowmanxx.searchAround(world, pos);
            if (_snowmanxxx != null) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx.getWidth(); _snowmanxxxx++) {
                  for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxx.getHeight(); _snowmanxxxxx++) {
                     CachedBlockPosition _snowmanxxxxxx = _snowmanxxx.translate(_snowmanxxxx, _snowmanxxxxx, 0);
                     world.setBlockState(_snowmanxxxxxx.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                     world.syncWorldEvent(2001, _snowmanxxxxxx.getBlockPos(), Block.getRawIdFromState(_snowmanxxxxxx.getBlockState()));
                  }
               }

               WitherEntity _snowmanxxxx = EntityType.WITHER.create(world);
               BlockPos _snowmanxxxxx = _snowmanxxx.translate(1, 2, 0).getBlockPos();
               _snowmanxxxx.refreshPositionAndAngles(
                  (double)_snowmanxxxxx.getX() + 0.5,
                  (double)_snowmanxxxxx.getY() + 0.55,
                  (double)_snowmanxxxxx.getZ() + 0.5,
                  _snowmanxxx.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F,
                  0.0F
               );
               _snowmanxxxx.bodyYaw = _snowmanxxx.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
               _snowmanxxxx.method_6885();

               for (ServerPlayerEntity _snowmanxxxxxx : world.getNonSpectatingEntities(ServerPlayerEntity.class, _snowmanxxxx.getBoundingBox().expand(50.0))) {
                  Criteria.SUMMONED_ENTITY.trigger(_snowmanxxxxxx, _snowmanxxxx);
               }

               world.spawnEntity(_snowmanxxxx);

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxx.getWidth(); _snowmanxxxxxx++) {
                  for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxx.getHeight(); _snowmanxxxxxxx++) {
                     world.updateNeighbors(_snowmanxxx.translate(_snowmanxxxxxx, _snowmanxxxxxxx, 0).getBlockPos(), Blocks.AIR);
                  }
               }
            }
         }
      }
   }

   public static boolean canDispense(World world, BlockPos pos, ItemStack stack) {
      return stack.getItem() == Items.WITHER_SKELETON_SKULL && pos.getY() >= 2 && world.getDifficulty() != Difficulty.PEACEFUL && !world.isClient
         ? getWitherDispenserPattern().searchAround(world, pos) != null
         : false;
   }

   private static BlockPattern getWitherBossPattern() {
      if (witherBossPattern == null) {
         witherBossPattern = BlockPatternBuilder.start()
            .aisle("^^^", "###", "~#~")
            .where('#', _snowman -> _snowman.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS))
            .where(
               '^',
               CachedBlockPosition.matchesBlockState(
                  BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_SKULL).or(BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_WALL_SKULL))
               )
            )
            .where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR)))
            .build();
      }

      return witherBossPattern;
   }

   private static BlockPattern getWitherDispenserPattern() {
      if (witherDispenserPattern == null) {
         witherDispenserPattern = BlockPatternBuilder.start()
            .aisle("   ", "###", "~#~")
            .where('#', _snowman -> _snowman.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS))
            .where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR)))
            .build();
      }

      return witherDispenserPattern;
   }
}
