/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BucketItem
extends Item {
    private final Fluid fluid;

    public BucketItem(Fluid fluid, Item.Settings settings) {
        super(settings);
        this.fluid = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult _snowman2 = BucketItem.raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (((HitResult)_snowman2).getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        if (((HitResult)_snowman2).getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = _snowman2;
            BlockPos _snowman3 = blockHitResult.getBlockPos();
            Direction _snowman4 = blockHitResult.getSide();
            BlockPos _snowman5 = _snowman3.offset(_snowman4);
            if (!world.canPlayerModifyAt(user, _snowman3) || !user.canPlaceOn(_snowman5, _snowman4, itemStack)) {
                return TypedActionResult.fail(itemStack);
            }
            if (this.fluid == Fluids.EMPTY) {
                BlockState blockState = world.getBlockState(_snowman3);
                if (blockState.getBlock() instanceof FluidDrainable && (_snowman = ((FluidDrainable)((Object)blockState.getBlock())).tryDrainFluid(world, _snowman3, blockState)) != Fluids.EMPTY) {
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    user.playSound(_snowman.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL, 1.0f, 1.0f);
                    ItemStack itemStack2 = ItemUsage.method_30012(itemStack, user, new ItemStack(_snowman.getBucketItem()));
                    if (!world.isClient) {
                        Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, new ItemStack(_snowman.getBucketItem()));
                    }
                    return TypedActionResult.success(itemStack2, world.isClient());
                }
                return TypedActionResult.fail(itemStack);
            }
            BlockState blockState = world.getBlockState(_snowman3);
            BlockPos blockPos = _snowman = blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER ? _snowman3 : _snowman5;
            if (this.placeFluid(user, world, _snowman, blockHitResult)) {
                this.onEmptied(world, itemStack, _snowman);
                if (user instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, _snowman, itemStack);
                }
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                return TypedActionResult.success(this.getEmptiedStack(itemStack, user), world.isClient());
            }
            return TypedActionResult.fail(itemStack);
        }
        return TypedActionResult.pass(itemStack);
    }

    protected ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        if (!player.abilities.creativeMode) {
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }

    public void onEmptied(World world, ItemStack stack, BlockPos pos) {
    }

    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult blockHitResult) {
        if (!(this.fluid instanceof FlowableFluid)) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        Block _snowman2 = blockState.getBlock();
        Material _snowman3 = blockState.getMaterial();
        boolean _snowman4 = blockState.canBucketPlace(this.fluid);
        boolean bl = _snowman = blockState.isAir() || _snowman4 || _snowman2 instanceof FluidFillable && ((FluidFillable)((Object)_snowman2)).canFillWithFluid(world, pos, blockState, this.fluid);
        if (!_snowman) {
            return blockHitResult != null && this.placeFluid(player, world, blockHitResult.getBlockPos().offset(blockHitResult.getSide()), null);
        }
        if (world.getDimension().isUltrawarm() && this.fluid.isIn(FluidTags.WATER)) {
            int n = pos.getX();
            _snowman = pos.getY();
            _snowman = pos.getZ();
            world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
            for (_snowman = 0; _snowman < 8; ++_snowman) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)n + Math.random(), (double)_snowman + Math.random(), (double)_snowman + Math.random(), 0.0, 0.0, 0.0);
            }
            return true;
        }
        if (_snowman2 instanceof FluidFillable && this.fluid == Fluids.WATER) {
            ((FluidFillable)((Object)_snowman2)).tryFillWithFluid(world, pos, blockState, ((FlowableFluid)this.fluid).getStill(false));
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        if (!world.isClient && _snowman4 && !_snowman3.isLiquid()) {
            world.breakBlock(pos, true);
        }
        if (world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) || blockState.getFluidState().isStill()) {
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        return false;
    }

    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        SoundEvent soundEvent = this.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}

