/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Nameable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EnchantingTableBlock
extends BlockWithEntity {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

    protected EnchantingTableBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        for (int i = -2; i <= 2; ++i) {
            block1: for (_snowman = -2; _snowman <= 2; ++_snowman) {
                if (i > -2 && i < 2 && _snowman == -1) {
                    _snowman = 2;
                }
                if (random.nextInt(16) != 0) continue;
                for (_snowman = 0; _snowman <= 1; ++_snowman) {
                    BlockPos blockPos = pos.add(i, _snowman, _snowman);
                    if (!world.getBlockState(blockPos).isOf(Blocks.BOOKSHELF)) continue;
                    if (!world.isAir(pos.add(i / 2, 0, _snowman / 2))) continue block1;
                    world.addParticle(ParticleTypes.ENCHANT, (double)pos.getX() + 0.5, (double)pos.getY() + 2.0, (double)pos.getZ() + 0.5, (double)((float)i + random.nextFloat()) - 0.5, (float)_snowman - random.nextFloat() - 1.0f, (double)((float)_snowman + random.nextFloat()) - 0.5);
                }
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EnchantingTableBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.CONSUME;
    }

    @Override
    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof EnchantingTableBlockEntity) {
            Text text = ((Nameable)((Object)blockEntity)).getDisplayName();
            return new SimpleNamedScreenHandlerFactory((n, playerInventory, playerEntity) -> new EnchantmentScreenHandler(n, playerInventory, ScreenHandlerContext.create(world, pos)), text);
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof EnchantingTableBlockEntity) {
            ((EnchantingTableBlockEntity)blockEntity).setCustomName(itemStack.getName());
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

