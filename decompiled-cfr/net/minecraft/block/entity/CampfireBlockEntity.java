/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Clearable;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CampfireBlockEntity
extends BlockEntity
implements Clearable,
Tickable {
    private final DefaultedList<ItemStack> itemsBeingCooked = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];

    public CampfireBlockEntity() {
        super(BlockEntityType.CAMPFIRE);
    }

    @Override
    public void tick() {
        boolean bl = this.getCachedState().get(CampfireBlock.LIT);
        _snowman = this.world.isClient;
        if (_snowman) {
            if (bl) {
                this.spawnSmokeParticles();
            }
            return;
        }
        if (bl) {
            this.updateItemsBeingCooked();
        } else {
            for (int i = 0; i < this.itemsBeingCooked.size(); ++i) {
                if (this.cookingTimes[i] <= 0) continue;
                this.cookingTimes[i] = MathHelper.clamp(this.cookingTimes[i] - 2, 0, this.cookingTotalTimes[i]);
            }
        }
    }

    private void updateItemsBeingCooked() {
        for (int i = 0; i < this.itemsBeingCooked.size(); ++i) {
            ItemStack itemStack = this.itemsBeingCooked.get(i);
            if (itemStack.isEmpty()) continue;
            int n = i;
            this.cookingTimes[n] = this.cookingTimes[n] + 1;
            if (this.cookingTimes[i] < this.cookingTotalTimes[i]) continue;
            SimpleInventory _snowman2 = new SimpleInventory(itemStack);
            _snowman = this.world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, _snowman2, this.world).map(campfireCookingRecipe -> campfireCookingRecipe.craft(_snowman2)).orElse(itemStack);
            BlockPos _snowman3 = this.getPos();
            ItemScatterer.spawn(this.world, (double)_snowman3.getX(), (double)_snowman3.getY(), (double)_snowman3.getZ(), _snowman);
            this.itemsBeingCooked.set(i, ItemStack.EMPTY);
            this.updateListeners();
        }
    }

    private void spawnSmokeParticles() {
        int n;
        World world = this.getWorld();
        if (world == null) {
            return;
        }
        BlockPos _snowman2 = this.getPos();
        Random _snowman3 = world.random;
        if (_snowman3.nextFloat() < 0.11f) {
            for (n = 0; n < _snowman3.nextInt(2) + 2; ++n) {
                CampfireBlock.spawnSmokeParticle(world, _snowman2, this.getCachedState().get(CampfireBlock.SIGNAL_FIRE), false);
            }
        }
        n = this.getCachedState().get(CampfireBlock.FACING).getHorizontal();
        for (_snowman = 0; _snowman < this.itemsBeingCooked.size(); ++_snowman) {
            if (this.itemsBeingCooked.get(_snowman).isEmpty() || !(_snowman3.nextFloat() < 0.2f)) continue;
            Direction direction = Direction.fromHorizontal(Math.floorMod(_snowman + n, 4));
            float _snowman4 = 0.3125f;
            double _snowman5 = (double)_snowman2.getX() + 0.5 - (double)((float)direction.getOffsetX() * 0.3125f) + (double)((float)direction.rotateYClockwise().getOffsetX() * 0.3125f);
            double _snowman6 = (double)_snowman2.getY() + 0.5;
            double _snowman7 = (double)_snowman2.getZ() + 0.5 - (double)((float)direction.getOffsetZ() * 0.3125f) + (double)((float)direction.rotateYClockwise().getOffsetZ() * 0.3125f);
            for (int i = 0; i < 4; ++i) {
                world.addParticle(ParticleTypes.SMOKE, _snowman5, _snowman6, _snowman7, 0.0, 5.0E-4, 0.0);
            }
        }
    }

    public DefaultedList<ItemStack> getItemsBeingCooked() {
        return this.itemsBeingCooked;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        int[] nArray;
        super.fromTag(state, tag);
        this.itemsBeingCooked.clear();
        Inventories.fromTag(tag, this.itemsBeingCooked);
        if (tag.contains("CookingTimes", 11)) {
            nArray = tag.getIntArray("CookingTimes");
            System.arraycopy(nArray, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, nArray.length));
        }
        if (tag.contains("CookingTotalTimes", 11)) {
            nArray = tag.getIntArray("CookingTotalTimes");
            System.arraycopy(nArray, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, nArray.length));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        this.saveInitialChunkData(tag);
        tag.putIntArray("CookingTimes", this.cookingTimes);
        tag.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        return tag;
    }

    private CompoundTag saveInitialChunkData(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, this.itemsBeingCooked, true);
        return tag;
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 13, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.saveInitialChunkData(new CompoundTag());
    }

    public Optional<CampfireCookingRecipe> getRecipeFor(ItemStack item) {
        if (this.itemsBeingCooked.stream().noneMatch(ItemStack::isEmpty)) {
            return Optional.empty();
        }
        return this.world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SimpleInventory(item), this.world);
    }

    public boolean addItem(ItemStack item, int integer) {
        for (int i = 0; i < this.itemsBeingCooked.size(); ++i) {
            ItemStack itemStack = this.itemsBeingCooked.get(i);
            if (!itemStack.isEmpty()) continue;
            this.cookingTotalTimes[i] = integer;
            this.cookingTimes[i] = 0;
            this.itemsBeingCooked.set(i, item.split(1));
            this.updateListeners();
            return true;
        }
        return false;
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }

    @Override
    public void clear() {
        this.itemsBeingCooked.clear();
    }

    public void spawnItemsBeingCooked() {
        if (this.world != null) {
            if (!this.world.isClient) {
                ItemScatterer.spawn(this.world, this.getPos(), this.getItemsBeingCooked());
            }
            this.updateListeners();
        }
    }
}

