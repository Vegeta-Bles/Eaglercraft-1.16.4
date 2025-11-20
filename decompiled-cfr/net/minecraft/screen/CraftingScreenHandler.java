/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class CraftingScreenHandler
extends AbstractRecipeScreenHandler<CraftingInventory> {
    private final CraftingInventory input = new CraftingInventory(this, 3, 3);
    private final CraftingResultInventory result = new CraftingResultInventory();
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public CraftingScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public CraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.CRAFTING, syncId);
        int n;
        this.context = context;
        this.player = playerInventory.player;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 124, 35));
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 3; ++_snowman) {
                this.addSlot(new Slot(this.input, _snowman + n * 3, 30 + _snowman * 18, 17 + n * 18));
            }
        }
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + n * 9 + 9, 8 + _snowman * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 142));
        }
    }

    protected static void updateResult(int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (world.isClient) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
        ItemStack _snowman2 = ItemStack.EMPTY;
        Optional<CraftingRecipe> _snowman3 = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
        if (_snowman3.isPresent() && resultInventory.shouldCraftRecipe(world, serverPlayerEntity, _snowman = _snowman3.get())) {
            _snowman2 = _snowman.craft(craftingInventory);
        }
        resultInventory.setStack(0, _snowman2);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, 0, _snowman2));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, blockPos) -> CraftingScreenHandler.updateResult(this.syncId, world, this.player, this.input, this.result));
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.input, this.player.world);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> this.dropInventory(player, (World)world, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return CraftingScreenHandler.canUse(this.context, player, Blocks.CRAFTING_TABLE);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            if (index == 0) {
                this.context.run((world, blockPos) -> _snowman.getItem().onCraft(_snowman, (World)world, player));
                if (!this.insertItem(_snowman, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(_snowman, itemStack);
            } else if (index >= 10 && index < 46 ? !this.insertItem(_snowman, 1, 10, false) && (index < 37 ? !this.insertItem(_snowman, 37, 46, false) : !this.insertItem(_snowman, 10, 37, false)) : !this.insertItem(_snowman, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            if (_snowman.isEmpty()) {
                _snowman2.setStack(ItemStack.EMPTY);
            } else {
                _snowman2.markDirty();
            }
            if (_snowman.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            _snowman = _snowman2.onTakeItem(player, _snowman);
            if (index == 0) {
                player.dropItem(_snowman, false);
            }
        }
        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 10;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }
}

