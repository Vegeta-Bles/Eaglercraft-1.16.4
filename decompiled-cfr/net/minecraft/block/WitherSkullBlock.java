/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.SkullBlock;
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

public class WitherSkullBlock
extends SkullBlock {
    @Nullable
    private static BlockPattern witherBossPattern;
    @Nullable
    private static BlockPattern witherDispenserPattern;

    protected WitherSkullBlock(AbstractBlock.Settings settings) {
        super(SkullBlock.Type.WITHER_SKELETON, settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SkullBlockEntity) {
            WitherSkullBlock.onPlaced(world, pos, (SkullBlockEntity)blockEntity);
        }
    }

    public static void onPlaced(World world, BlockPos pos, SkullBlockEntity blockEntity) {
        if (world.isClient) {
            return;
        }
        BlockState blockState = blockEntity.getCachedState();
        boolean bl = _snowman = blockState.isOf(Blocks.WITHER_SKELETON_SKULL) || blockState.isOf(Blocks.WITHER_SKELETON_WALL_SKULL);
        if (!_snowman || pos.getY() < 0 || world.getDifficulty() == Difficulty.PEACEFUL) {
            return;
        }
        BlockPattern _snowman2 = WitherSkullBlock.getWitherBossPattern();
        BlockPattern.Result _snowman3 = _snowman2.searchAround(world, pos);
        if (_snowman3 == null) {
            return;
        }
        for (int i = 0; i < _snowman2.getWidth(); ++i) {
            for (_snowman = 0; _snowman < _snowman2.getHeight(); ++_snowman) {
                CachedBlockPosition cachedBlockPosition = _snowman3.translate(i, _snowman, 0);
                world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
            }
        }
        WitherEntity witherEntity = EntityType.WITHER.create(world);
        BlockPos _snowman4 = _snowman3.translate(1, 2, 0).getBlockPos();
        witherEntity.refreshPositionAndAngles((double)_snowman4.getX() + 0.5, (double)_snowman4.getY() + 0.55, (double)_snowman4.getZ() + 0.5, _snowman3.getForwards().getAxis() == Direction.Axis.X ? 0.0f : 90.0f, 0.0f);
        witherEntity.bodyYaw = _snowman3.getForwards().getAxis() == Direction.Axis.X ? 0.0f : 90.0f;
        witherEntity.method_6885();
        for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, witherEntity.getBoundingBox().expand(50.0))) {
            Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, witherEntity);
        }
        world.spawnEntity(witherEntity);
        for (int i = 0; i < _snowman2.getWidth(); ++i) {
            for (_snowman = 0; _snowman < _snowman2.getHeight(); ++_snowman) {
                world.updateNeighbors(_snowman3.translate(i, _snowman, 0).getBlockPos(), Blocks.AIR);
            }
        }
    }

    public static boolean canDispense(World world, BlockPos pos, ItemStack stack) {
        if (stack.getItem() == Items.WITHER_SKELETON_SKULL && pos.getY() >= 2 && world.getDifficulty() != Difficulty.PEACEFUL && !world.isClient) {
            return WitherSkullBlock.getWitherDispenserPattern().searchAround(world, pos) != null;
        }
        return false;
    }

    private static BlockPattern getWitherBossPattern() {
        if (witherBossPattern == null) {
            witherBossPattern = BlockPatternBuilder.start().aisle("^^^", "###", "~#~").where('#', cachedBlockPosition -> cachedBlockPosition.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS)).where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_SKULL).or(BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_WALL_SKULL)))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
        }
        return witherBossPattern;
    }

    private static BlockPattern getWitherDispenserPattern() {
        if (witherDispenserPattern == null) {
            witherDispenserPattern = BlockPatternBuilder.start().aisle("   ", "###", "~#~").where('#', cachedBlockPosition -> cachedBlockPosition.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS)).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
        }
        return witherDispenserPattern;
    }
}

