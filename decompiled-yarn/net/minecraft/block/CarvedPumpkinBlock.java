package net.minecraft.block;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Wearable;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CarvedPumpkinBlock extends HorizontalFacingBlock implements Wearable {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   @Nullable
   private BlockPattern snowGolemDispenserPattern;
   @Nullable
   private BlockPattern snowGolemPattern;
   @Nullable
   private BlockPattern ironGolemDispenserPattern;
   @Nullable
   private BlockPattern ironGolemPattern;
   private static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE = state -> state != null
         && (state.isOf(Blocks.CARVED_PUMPKIN) || state.isOf(Blocks.JACK_O_LANTERN));

   protected CarvedPumpkinBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!oldState.isOf(state.getBlock())) {
         this.trySpawnEntity(world, pos);
      }
   }

   public boolean canDispense(WorldView _snowman, BlockPos pos) {
      return this.getSnowGolemDispenserPattern().searchAround(_snowman, pos) != null || this.getIronGolemDispenserPattern().searchAround(_snowman, pos) != null;
   }

   private void trySpawnEntity(World world, BlockPos pos) {
      BlockPattern.Result _snowman = this.getSnowGolemPattern().searchAround(world, pos);
      if (_snowman != null) {
         for (int _snowmanx = 0; _snowmanx < this.getSnowGolemPattern().getHeight(); _snowmanx++) {
            CachedBlockPosition _snowmanxx = _snowman.translate(0, _snowmanx, 0);
            world.setBlockState(_snowmanxx.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
            world.syncWorldEvent(2001, _snowmanxx.getBlockPos(), Block.getRawIdFromState(_snowmanxx.getBlockState()));
         }

         SnowGolemEntity _snowmanx = EntityType.SNOW_GOLEM.create(world);
         BlockPos _snowmanxx = _snowman.translate(0, 2, 0).getBlockPos();
         _snowmanx.refreshPositionAndAngles((double)_snowmanxx.getX() + 0.5, (double)_snowmanxx.getY() + 0.05, (double)_snowmanxx.getZ() + 0.5, 0.0F, 0.0F);
         world.spawnEntity(_snowmanx);

         for (ServerPlayerEntity _snowmanxxx : world.getNonSpectatingEntities(ServerPlayerEntity.class, _snowmanx.getBoundingBox().expand(5.0))) {
            Criteria.SUMMONED_ENTITY.trigger(_snowmanxxx, _snowmanx);
         }

         for (int _snowmanxxx = 0; _snowmanxxx < this.getSnowGolemPattern().getHeight(); _snowmanxxx++) {
            CachedBlockPosition _snowmanxxxx = _snowman.translate(0, _snowmanxxx, 0);
            world.updateNeighbors(_snowmanxxxx.getBlockPos(), Blocks.AIR);
         }
      } else {
         _snowman = this.getIronGolemPattern().searchAround(world, pos);
         if (_snowman != null) {
            for (int _snowmanx = 0; _snowmanx < this.getIronGolemPattern().getWidth(); _snowmanx++) {
               for (int _snowmanxx = 0; _snowmanxx < this.getIronGolemPattern().getHeight(); _snowmanxx++) {
                  CachedBlockPosition _snowmanxxx = _snowman.translate(_snowmanx, _snowmanxx, 0);
                  world.setBlockState(_snowmanxxx.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                  world.syncWorldEvent(2001, _snowmanxxx.getBlockPos(), Block.getRawIdFromState(_snowmanxxx.getBlockState()));
               }
            }

            BlockPos _snowmanx = _snowman.translate(1, 2, 0).getBlockPos();
            IronGolemEntity _snowmanxx = EntityType.IRON_GOLEM.create(world);
            _snowmanxx.setPlayerCreated(true);
            _snowmanxx.refreshPositionAndAngles((double)_snowmanx.getX() + 0.5, (double)_snowmanx.getY() + 0.05, (double)_snowmanx.getZ() + 0.5, 0.0F, 0.0F);
            world.spawnEntity(_snowmanxx);

            for (ServerPlayerEntity _snowmanxxx : world.getNonSpectatingEntities(ServerPlayerEntity.class, _snowmanxx.getBoundingBox().expand(5.0))) {
               Criteria.SUMMONED_ENTITY.trigger(_snowmanxxx, _snowmanxx);
            }

            for (int _snowmanxxx = 0; _snowmanxxx < this.getIronGolemPattern().getWidth(); _snowmanxxx++) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < this.getIronGolemPattern().getHeight(); _snowmanxxxx++) {
                  CachedBlockPosition _snowmanxxxxx = _snowman.translate(_snowmanxxx, _snowmanxxxx, 0);
                  world.updateNeighbors(_snowmanxxxxx.getBlockPos(), Blocks.AIR);
               }
            }
         }
      }
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }

   private BlockPattern getSnowGolemDispenserPattern() {
      if (this.snowGolemDispenserPattern == null) {
         this.snowGolemDispenserPattern = BlockPatternBuilder.start()
            .aisle(" ", "#", "#")
            .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
            .build();
      }

      return this.snowGolemDispenserPattern;
   }

   private BlockPattern getSnowGolemPattern() {
      if (this.snowGolemPattern == null) {
         this.snowGolemPattern = BlockPatternBuilder.start()
            .aisle("^", "#", "#")
            .where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE))
            .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
            .build();
      }

      return this.snowGolemPattern;
   }

   private BlockPattern getIronGolemDispenserPattern() {
      if (this.ironGolemDispenserPattern == null) {
         this.ironGolemDispenserPattern = BlockPatternBuilder.start()
            .aisle("~ ~", "###", "~#~")
            .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
            .where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR)))
            .build();
      }

      return this.ironGolemDispenserPattern;
   }

   private BlockPattern getIronGolemPattern() {
      if (this.ironGolemPattern == null) {
         this.ironGolemPattern = BlockPatternBuilder.start()
            .aisle("~^~", "###", "~#~")
            .where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE))
            .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
            .where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR)))
            .build();
      }

      return this.ironGolemPattern;
   }
}
