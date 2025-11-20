/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
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

public class CarvedPumpkinBlock
extends HorizontalFacingBlock
implements Wearable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    @Nullable
    private BlockPattern snowGolemDispenserPattern;
    @Nullable
    private BlockPattern snowGolemPattern;
    @Nullable
    private BlockPattern ironGolemDispenserPattern;
    @Nullable
    private BlockPattern ironGolemPattern;
    private static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE = state -> state != null && (state.isOf(Blocks.CARVED_PUMPKIN) || state.isOf(Blocks.JACK_O_LANTERN));

    protected CarvedPumpkinBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        this.trySpawnEntity(world, pos);
    }

    public boolean canDispense(WorldView worldView, BlockPos pos) {
        return this.getSnowGolemDispenserPattern().searchAround(worldView, pos) != null || this.getIronGolemDispenserPattern().searchAround(worldView, pos) != null;
    }

    /*
     * WARNING - void declaration
     */
    private void trySpawnEntity(World world, BlockPos pos) {
        block9: {
            BlockPattern.Result result;
            block8: {
                result = this.getSnowGolemPattern().searchAround(world, pos);
                if (result == null) break block8;
                for (int i = 0; i < this.getSnowGolemPattern().getHeight(); ++i) {
                    CachedBlockPosition _snowman2 = result.translate(0, i, 0);
                    world.setBlockState(_snowman2.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                    world.syncWorldEvent(2001, _snowman2.getBlockPos(), Block.getRawIdFromState(_snowman2.getBlockState()));
                }
                SnowGolemEntity snowGolemEntity = EntityType.SNOW_GOLEM.create(world);
                BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
                snowGolemEntity.refreshPositionAndAngles((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.05, (double)blockPos.getZ() + 0.5, 0.0f, 0.0f);
                world.spawnEntity(snowGolemEntity);
                for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, snowGolemEntity.getBoundingBox().expand(5.0))) {
                    Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, snowGolemEntity);
                }
                for (int i = 0; i < this.getSnowGolemPattern().getHeight(); ++i) {
                    CachedBlockPosition cachedBlockPosition = result.translate(0, i, 0);
                    world.updateNeighbors(cachedBlockPosition.getBlockPos(), Blocks.AIR);
                }
                break block9;
            }
            result = this.getIronGolemPattern().searchAround(world, pos);
            if (result == null) break block9;
            for (int i = 0; i < this.getIronGolemPattern().getWidth(); ++i) {
                for (int j = 0; j < this.getIronGolemPattern().getHeight(); ++j) {
                    CachedBlockPosition cachedBlockPosition = result.translate(i, j, 0);
                    world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                    world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
                }
            }
            BlockPos _snowman3 = result.translate(1, 2, 0).getBlockPos();
            IronGolemEntity _snowman4 = EntityType.IRON_GOLEM.create(world);
            _snowman4.setPlayerCreated(true);
            _snowman4.refreshPositionAndAngles((double)_snowman3.getX() + 0.5, (double)_snowman3.getY() + 0.05, (double)_snowman3.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntity(_snowman4);
            for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, _snowman4.getBoundingBox().expand(5.0))) {
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, _snowman4);
            }
            for (int i = 0; i < this.getIronGolemPattern().getWidth(); ++i) {
                void var7_20;
                boolean bl = false;
                while (var7_20 < this.getIronGolemPattern().getHeight()) {
                    CachedBlockPosition cachedBlockPosition = result.translate(i, (int)var7_20, 0);
                    world.updateNeighbors(cachedBlockPosition.getBlockPos(), Blocks.AIR);
                    ++var7_20;
                }
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private BlockPattern getSnowGolemDispenserPattern() {
        if (this.snowGolemDispenserPattern == null) {
            this.snowGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
        }
        return this.snowGolemDispenserPattern;
    }

    private BlockPattern getSnowGolemPattern() {
        if (this.snowGolemPattern == null) {
            this.snowGolemPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
        }
        return this.snowGolemPattern;
    }

    private BlockPattern getIronGolemDispenserPattern() {
        if (this.ironGolemDispenserPattern == null) {
            this.ironGolemDispenserPattern = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
        }
        return this.ironGolemDispenserPattern;
    }

    private BlockPattern getIronGolemPattern() {
        if (this.ironGolemPattern == null) {
            this.ironGolemPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
        }
        return this.ironGolemPattern;
    }
}

