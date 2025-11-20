/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.screen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class PlayerScreenHandler
extends AbstractRecipeScreenHandler<CraftingInventory> {
    public static final Identifier BLOCK_ATLAS_TEXTURE = new Identifier("textures/atlas/blocks.png");
    public static final Identifier EMPTY_HELMET_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_helmet");
    public static final Identifier EMPTY_CHESTPLATE_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_chestplate");
    public static final Identifier EMPTY_LEGGINGS_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_leggings");
    public static final Identifier EMPTY_BOOTS_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_boots");
    public static final Identifier EMPTY_OFFHAND_ARMOR_SLOT = new Identifier("item/empty_armor_slot_shield");
    private static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES = new Identifier[]{EMPTY_BOOTS_SLOT_TEXTURE, EMPTY_LEGGINGS_SLOT_TEXTURE, EMPTY_CHESTPLATE_SLOT_TEXTURE, EMPTY_HELMET_SLOT_TEXTURE};
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private final CraftingInventory craftingInput = new CraftingInventory(this, 2, 2);
    private final CraftingResultInventory craftingResult = new CraftingResultInventory();
    public final boolean onServer;
    private final PlayerEntity owner;

    public PlayerScreenHandler(PlayerInventory inventory, boolean onServer, PlayerEntity owner) {
        super(null, 0);
        int n;
        this.onServer = onServer;
        this.owner = owner;
        this.addSlot(new CraftingResultSlot(inventory.player, this.craftingInput, this.craftingResult, 0, 154, 28));
        for (n = 0; n < 2; ++n) {
            for (_snowman = 0; _snowman < 2; ++_snowman) {
                this.addSlot(new Slot(this.craftingInput, _snowman + n * 2, 98 + _snowman * 18, 18 + n * 18));
            }
        }
        for (n = 0; n < 4; ++n) {
            EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[n];
            this.addSlot(new Slot(this, inventory, 39 - n, 8, 8 + n * 18, equipmentSlot){
                final /* synthetic */ EquipmentSlot field_7834;
                final /* synthetic */ PlayerScreenHandler field_7833;
                {
                    this.field_7833 = playerScreenHandler;
                    this.field_7834 = equipmentSlot;
                    super(inventory, n, n2, n3);
                }

                public int getMaxItemCount() {
                    return 1;
                }

                public boolean canInsert(ItemStack stack) {
                    return this.field_7834 == MobEntity.getPreferredEquipmentSlot(stack);
                }

                public boolean canTakeItems(PlayerEntity playerEntity) {
                    ItemStack itemStack = this.getStack();
                    if (!itemStack.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(itemStack)) {
                        return false;
                    }
                    return super.canTakeItems(playerEntity);
                }

                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of((Object)PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, (Object)PlayerScreenHandler.method_7641()[this.field_7834.getEntitySlotId()]);
                }
            });
        }
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(inventory, _snowman + (n + 1) * 9, 8 + _snowman * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(inventory, n, 8 + n * 18, 142));
        }
        this.addSlot(new Slot(this, inventory, 40, 77, 62){
            final /* synthetic */ PlayerScreenHandler field_21674;
            {
                this.field_21674 = playerScreenHandler;
                super(inventory, n, n2, n3);
            }

            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of((Object)PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, (Object)PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        });
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        this.craftingInput.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.craftingResult.clear();
        this.craftingInput.clear();
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.craftingInput, this.owner.world);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        CraftingScreenHandler.updateResult(this.syncId, this.owner.world, this.owner, this.craftingInput, this.craftingResult);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.craftingResult.clear();
        if (player.world.isClient) {
            return;
        }
        this.dropInventory(player, player.world, this.craftingInput);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(itemStack);
            if (index == 0) {
                if (!this.insertItem(_snowman, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(_snowman, itemStack);
            } else if (index >= 1 && index < 5 ? !this.insertItem(_snowman, 9, 45, false) : (index >= 5 && index < 9 ? !this.insertItem(_snowman, 9, 45, false) : (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !((Slot)this.slots.get(8 - equipmentSlot.getEntitySlotId())).hasStack() ? !this.insertItem(_snowman, _snowman = 8 - equipmentSlot.getEntitySlotId(), _snowman + 1, false) : (equipmentSlot == EquipmentSlot.OFFHAND && !((Slot)this.slots.get(45)).hasStack() ? !this.insertItem(_snowman, 45, 46, false) : (index >= 9 && index < 36 ? !this.insertItem(_snowman, 36, 45, false) : (index >= 36 && index < 45 ? !this.insertItem(_snowman, 9, 36, false) : !this.insertItem(_snowman, 9, 45, false))))))) {
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
            ItemStack _snowman3 = _snowman2.onTakeItem(player, _snowman);
            if (index == 0) {
                player.dropItem(_snowman3, false);
            }
        }
        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.craftingResult && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.craftingInput.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.craftingInput.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 5;
    }

    public CraftingInventory method_29281() {
        return this.craftingInput;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    static /* synthetic */ Identifier[] method_7641() {
        return EMPTY_ARMOR_SLOT_TEXTURES;
    }
}

