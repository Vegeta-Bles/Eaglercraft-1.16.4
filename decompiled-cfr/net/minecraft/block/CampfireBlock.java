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
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CampfireBlock
extends BlockWithEntity
implements Waterloggable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty SIGNAL_FIRE = Properties.SIGNAL_FIRE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SMOKEY_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    private final boolean emitsParticles;
    private final int fireDamage;

    public CampfireBlock(boolean emitsParticles, int fireDamage, AbstractBlock.Settings settings) {
        super(settings);
        this.emitsParticles = emitsParticles;
        this.fireDamage = fireDamage;
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LIT, true)).with(SIGNAL_FIRE, false)).with(WATERLOGGED, false)).with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CampfireBlockEntity && (_snowman = (_snowman = (CampfireBlockEntity)blockEntity).getRecipeFor(_snowman = player.getStackInHand(hand))).isPresent()) {
            if (!world.isClient && _snowman.addItem(player.abilities.creativeMode ? _snowman.copy() : _snowman, _snowman.get().getCookTime())) {
                player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune() && state.get(LIT).booleanValue() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.IN_FIRE, this.fireDamage);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CampfireBlockEntity) {
            ItemScatterer.spawn(world, pos, ((CampfireBlockEntity)blockEntity).getItemsBeingCooked());
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        boolean _snowman2 = world.getFluidState(_snowman = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, _snowman2)).with(SIGNAL_FIRE, this.doesBlockCauseSignalFire(world.getBlockState(_snowman.down())))).with(LIT, !_snowman2)).with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == Direction.DOWN) {
            return (BlockState)state.with(SIGNAL_FIRE, this.doesBlockCauseSignalFire(newState));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    private boolean doesBlockCauseSignalFire(BlockState state) {
        return state.isOf(Blocks.HAY_BLOCK);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT).booleanValue()) {
            return;
        }
        if (random.nextInt(10) == 0) {
            world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5f + random.nextFloat(), random.nextFloat() * 0.7f + 0.6f, false);
        }
        if (this.emitsParticles && random.nextInt(5) == 0) {
            for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                world.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, random.nextFloat() / 2.0f, 5.0E-5, random.nextFloat() / 2.0f);
            }
        }
    }

    public static void extinguish(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity;
        if (world.isClient()) {
            for (int i = 0; i < 20; ++i) {
                CampfireBlock.spawnSmokeParticle((World)world, pos, state.get(SIGNAL_FIRE), true);
            }
        }
        if ((blockEntity = world.getBlockEntity(pos)) instanceof CampfireBlockEntity) {
            ((CampfireBlockEntity)blockEntity).spawnItemsBeingCooked();
        }
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED).booleanValue() && fluidState.getFluid() == Fluids.WATER) {
            boolean bl = state.get(LIT);
            if (bl) {
                if (!world.isClient()) {
                    world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                }
                CampfireBlock.extinguish(world, pos, state);
            }
            world.setBlockState(pos, (BlockState)((BlockState)state.with(WATERLOGGED, true)).with(LIT, false), 3);
            world.getFluidTickScheduler().schedule(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire()) {
            Entity entity = projectile.getOwner();
            boolean bl = _snowman = entity == null || entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            if (_snowman && !state.get(LIT).booleanValue() && !state.get(WATERLOGGED).booleanValue()) {
                BlockPos blockPos = hit.getBlockPos();
                world.setBlockState(blockPos, (BlockState)state.with(Properties.LIT, true), 11);
            }
        }
    }

    public static void spawnSmokeParticle(World world, BlockPos pos, boolean isSignal, boolean lotsOfSmoke) {
        Random random = world.getRandom();
        DefaultParticleType _snowman2 = isSignal ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addImportantParticle(_snowman2, true, (double)pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        if (lotsOfSmoke) {
            world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.25 + random.nextDouble() / 2.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4, (double)pos.getZ() + 0.25 + random.nextDouble() / 2.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.005, 0.0);
        }
    }

    public static boolean isLitCampfireInRange(World world, BlockPos pos) {
        for (int i = 1; i <= 5; ++i) {
            BlockPos blockPos = pos.down(i);
            BlockState _snowman2 = world.getBlockState(blockPos);
            if (CampfireBlock.isLitCampfire(_snowman2)) {
                return true;
            }
            boolean _snowman3 = VoxelShapes.matchesAnywhere(SMOKEY_SHAPE, _snowman2.getCollisionShape(world, pos, ShapeContext.absent()), BooleanBiFunction.AND);
            if (!_snowman3) continue;
            BlockState _snowman4 = world.getBlockState(blockPos.down());
            return CampfireBlock.isLitCampfire(_snowman4);
        }
        return false;
    }

    public static boolean isLitCampfire(BlockState state) {
        return state.contains(LIT) && state.isIn(BlockTags.CAMPFIRES) && state.get(LIT) != false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new CampfireBlockEntity();
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public static boolean method_30035(BlockState blockState) {
        return blockState.method_27851(BlockTags.CAMPFIRES, abstractBlockState -> abstractBlockState.contains(Properties.WATERLOGGED) && abstractBlockState.contains(Properties.LIT)) && blockState.get(Properties.WATERLOGGED) == false && blockState.get(Properties.LIT) == false;
    }
}

