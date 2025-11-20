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
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class PlayerScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {
   public static final Identifier BLOCK_ATLAS_TEXTURE = new Identifier("textures/atlas/blocks.png");
   public static final Identifier EMPTY_HELMET_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_helmet");
   public static final Identifier EMPTY_CHESTPLATE_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_chestplate");
   public static final Identifier EMPTY_LEGGINGS_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_leggings");
   public static final Identifier EMPTY_BOOTS_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_boots");
   public static final Identifier EMPTY_OFFHAND_ARMOR_SLOT = new Identifier("item/empty_armor_slot_shield");
   private static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES = new Identifier[]{
      EMPTY_BOOTS_SLOT_TEXTURE, EMPTY_LEGGINGS_SLOT_TEXTURE, EMPTY_CHESTPLATE_SLOT_TEXTURE, EMPTY_HELMET_SLOT_TEXTURE
   };
   private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{
      EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
   };
   private final CraftingInventory craftingInput = new CraftingInventory(this, 2, 2);
   private final CraftingResultInventory craftingResult = new CraftingResultInventory();
   public final boolean onServer;
   private final PlayerEntity owner;

   public PlayerScreenHandler(PlayerInventory inventory, boolean onServer, PlayerEntity owner) {
      super(null, 0);
      this.onServer = onServer;
      this.owner = owner;
      this.addSlot(new CraftingResultSlot(inventory.player, this.craftingInput, this.craftingResult, 0, 154, 28));

      for (int _snowman = 0; _snowman < 2; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
            this.addSlot(new Slot(this.craftingInput, _snowmanx + _snowman * 2, 98 + _snowmanx * 18, 18 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         final EquipmentSlot _snowmanx = EQUIPMENT_SLOT_ORDER[_snowman];
         this.addSlot(new Slot(inventory, 39 - _snowman, 8, 8 + _snowman * 18) {
            @Override
            public int getMaxItemCount() {
               return 1;
            }

            @Override
            public boolean canInsert(ItemStack stack) {
               return _snowman == MobEntity.getPreferredEquipmentSlot(stack);
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
               ItemStack _snowman = this.getStack();
               return !_snowman.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(_snowman) ? false : super.canTakeItems(playerEntity);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
               return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_ARMOR_SLOT_TEXTURES[_snowman.getEntitySlotId()]);
            }
         });
      }

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.addSlot(new Slot(inventory, _snowmanx + (_snowman + 1) * 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.addSlot(new Slot(inventory, _snowman, 8 + _snowman * 18, 142));
      }

      this.addSlot(new Slot(inventory, 40, 77, 62) {
         @Override
         public Pair<Identifier, Identifier> getBackgroundSprite() {
            return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
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
      if (!player.world.isClient) {
         this.dropInventory(player, player.world, this.craftingInput);
      }
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return true;
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         EquipmentSlot _snowmanxxx = MobEntity.getPreferredEquipmentSlot(_snowman);
         if (index == 0) {
            if (!this.insertItem(_snowmanxx, 9, 45, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index >= 1 && index < 5) {
            if (!this.insertItem(_snowmanxx, 9, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 5 && index < 9) {
            if (!this.insertItem(_snowmanxx, 9, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (_snowmanxxx.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - _snowmanxxx.getEntitySlotId()).hasStack()) {
            int _snowmanxxxx = 8 - _snowmanxxx.getEntitySlotId();
            if (!this.insertItem(_snowmanxx, _snowmanxxxx, _snowmanxxxx + 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (_snowmanxxx == EquipmentSlot.OFFHAND && !this.slots.get(45).hasStack()) {
            if (!this.insertItem(_snowmanxx, 45, 46, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 9 && index < 36) {
            if (!this.insertItem(_snowmanxx, 36, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 36 && index < 45) {
            if (!this.insertItem(_snowmanxx, 9, 36, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 9, 45, false)) {
            return ItemStack.EMPTY;
         }

         if (_snowmanxx.isEmpty()) {
            _snowmanx.setStack(ItemStack.EMPTY);
         } else {
            _snowmanx.markDirty();
         }

         if (_snowmanxx.getCount() == _snowman.getCount()) {
            return ItemStack.EMPTY;
         }

         ItemStack _snowmanxxxx = _snowmanx.onTakeItem(player, _snowmanxx);
         if (index == 0) {
            player.dropItem(_snowmanxxxx, false);
         }
      }

      return _snowman;
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
}
