package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.HotbarStorage;
import net.minecraft.client.options.HotbarStorageEntry;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.search.Searchable;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class CreativeInventoryScreen extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/creative_inventory/tabs.png");
   private static final SimpleInventory INVENTORY = new SimpleInventory(45);
   private static final Text field_26563 = new TranslatableText("inventory.binSlot");
   private static int selectedTab = ItemGroup.BUILDING_BLOCKS.getIndex();
   private float scrollPosition;
   private boolean scrolling;
   private TextFieldWidget searchBox;
   @Nullable
   private List<Slot> slots;
   @Nullable
   private Slot deleteItemSlot;
   private CreativeInventoryListener listener;
   private boolean ignoreTypedCharacter;
   private boolean lastClickOutsideBounds;
   private final Map<Identifier, Tag<Item>> searchResultTags = Maps.newTreeMap();

   public CreativeInventoryScreen(PlayerEntity player) {
      super(new CreativeInventoryScreen.CreativeScreenHandler(player), player.inventory, LiteralText.EMPTY);
      player.currentScreenHandler = this.handler;
      this.passEvents = true;
      this.backgroundHeight = 136;
      this.backgroundWidth = 195;
   }

   @Override
   public void tick() {
      if (!this.client.interactionManager.hasCreativeInventory()) {
         this.client.openScreen(new InventoryScreen(this.client.player));
      } else if (this.searchBox != null) {
         this.searchBox.tick();
      }
   }

   @Override
   protected void onMouseClick(@Nullable Slot slot, int invSlot, int clickData, SlotActionType actionType) {
      if (this.isCreativeInventorySlot(slot)) {
         this.searchBox.setCursorToEnd();
         this.searchBox.setSelectionEnd(0);
      }

      boolean _snowman = actionType == SlotActionType.QUICK_MOVE;
      actionType = invSlot == -999 && actionType == SlotActionType.PICKUP ? SlotActionType.THROW : actionType;
      if (slot == null && selectedTab != ItemGroup.INVENTORY.getIndex() && actionType != SlotActionType.QUICK_CRAFT) {
         PlayerInventory _snowmanx = this.client.player.inventory;
         if (!_snowmanx.getCursorStack().isEmpty() && this.lastClickOutsideBounds) {
            if (clickData == 0) {
               this.client.player.dropItem(_snowmanx.getCursorStack(), true);
               this.client.interactionManager.dropCreativeStack(_snowmanx.getCursorStack());
               _snowmanx.setCursorStack(ItemStack.EMPTY);
            }

            if (clickData == 1) {
               ItemStack _snowmanxx = _snowmanx.getCursorStack().split(1);
               this.client.player.dropItem(_snowmanxx, true);
               this.client.interactionManager.dropCreativeStack(_snowmanxx);
            }
         }
      } else {
         if (slot != null && !slot.canTakeItems(this.client.player)) {
            return;
         }

         if (slot == this.deleteItemSlot && _snowman) {
            for (int _snowmanx = 0; _snowmanx < this.client.player.playerScreenHandler.getStacks().size(); _snowmanx++) {
               this.client.interactionManager.clickCreativeStack(ItemStack.EMPTY, _snowmanx);
            }
         } else if (selectedTab == ItemGroup.INVENTORY.getIndex()) {
            if (slot == this.deleteItemSlot) {
               this.client.player.inventory.setCursorStack(ItemStack.EMPTY);
            } else if (actionType == SlotActionType.THROW && slot != null && slot.hasStack()) {
               ItemStack _snowmanx = slot.takeStack(clickData == 0 ? 1 : slot.getStack().getMaxCount());
               ItemStack _snowmanxx = slot.getStack();
               this.client.player.dropItem(_snowmanx, true);
               this.client.interactionManager.dropCreativeStack(_snowmanx);
               this.client.interactionManager.clickCreativeStack(_snowmanxx, ((CreativeInventoryScreen.CreativeSlot)slot).slot.id);
            } else if (actionType == SlotActionType.THROW && !this.client.player.inventory.getCursorStack().isEmpty()) {
               this.client.player.dropItem(this.client.player.inventory.getCursorStack(), true);
               this.client.interactionManager.dropCreativeStack(this.client.player.inventory.getCursorStack());
               this.client.player.inventory.setCursorStack(ItemStack.EMPTY);
            } else {
               this.client
                  .player
                  .playerScreenHandler
                  .onSlotClick(slot == null ? invSlot : ((CreativeInventoryScreen.CreativeSlot)slot).slot.id, clickData, actionType, this.client.player);
               this.client.player.playerScreenHandler.sendContentUpdates();
            }
         } else if (actionType != SlotActionType.QUICK_CRAFT && slot.inventory == INVENTORY) {
            PlayerInventory _snowmanx = this.client.player.inventory;
            ItemStack _snowmanxx = _snowmanx.getCursorStack();
            ItemStack _snowmanxxx = slot.getStack();
            if (actionType == SlotActionType.SWAP) {
               if (!_snowmanxxx.isEmpty()) {
                  ItemStack _snowmanxxxx = _snowmanxxx.copy();
                  _snowmanxxxx.setCount(_snowmanxxxx.getMaxCount());
                  this.client.player.inventory.setStack(clickData, _snowmanxxxx);
                  this.client.player.playerScreenHandler.sendContentUpdates();
               }

               return;
            }

            if (actionType == SlotActionType.CLONE) {
               if (_snowmanx.getCursorStack().isEmpty() && slot.hasStack()) {
                  ItemStack _snowmanxxxx = slot.getStack().copy();
                  _snowmanxxxx.setCount(_snowmanxxxx.getMaxCount());
                  _snowmanx.setCursorStack(_snowmanxxxx);
               }

               return;
            }

            if (actionType == SlotActionType.THROW) {
               if (!_snowmanxxx.isEmpty()) {
                  ItemStack _snowmanxxxx = _snowmanxxx.copy();
                  _snowmanxxxx.setCount(clickData == 0 ? 1 : _snowmanxxxx.getMaxCount());
                  this.client.player.dropItem(_snowmanxxxx, true);
                  this.client.interactionManager.dropCreativeStack(_snowmanxxxx);
               }

               return;
            }

            if (!_snowmanxx.isEmpty() && !_snowmanxxx.isEmpty() && _snowmanxx.isItemEqualIgnoreDamage(_snowmanxxx) && ItemStack.areTagsEqual(_snowmanxx, _snowmanxxx)) {
               if (clickData == 0) {
                  if (_snowman) {
                     _snowmanxx.setCount(_snowmanxx.getMaxCount());
                  } else if (_snowmanxx.getCount() < _snowmanxx.getMaxCount()) {
                     _snowmanxx.increment(1);
                  }
               } else {
                  _snowmanxx.decrement(1);
               }
            } else if (!_snowmanxxx.isEmpty() && _snowmanxx.isEmpty()) {
               _snowmanx.setCursorStack(_snowmanxxx.copy());
               _snowmanxx = _snowmanx.getCursorStack();
               if (_snowman) {
                  _snowmanxx.setCount(_snowmanxx.getMaxCount());
               }
            } else if (clickData == 0) {
               _snowmanx.setCursorStack(ItemStack.EMPTY);
            } else {
               _snowmanx.getCursorStack().decrement(1);
            }
         } else if (this.handler != null) {
            ItemStack _snowmanxxxx = slot == null ? ItemStack.EMPTY : this.handler.getSlot(slot.id).getStack();
            this.handler.onSlotClick(slot == null ? invSlot : slot.id, clickData, actionType, this.client.player);
            if (ScreenHandler.unpackQuickCraftStage(clickData) == 2) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 9; _snowmanxxxxx++) {
                  this.client.interactionManager.clickCreativeStack(this.handler.getSlot(45 + _snowmanxxxxx).getStack(), 36 + _snowmanxxxxx);
               }
            } else if (slot != null) {
               ItemStack _snowmanxxxxx = this.handler.getSlot(slot.id).getStack();
               this.client.interactionManager.clickCreativeStack(_snowmanxxxxx, slot.id - this.handler.slots.size() + 9 + 36);
               int _snowmanxxxxxx = 45 + clickData;
               if (actionType == SlotActionType.SWAP) {
                  this.client.interactionManager.clickCreativeStack(_snowmanxxxx, _snowmanxxxxxx - this.handler.slots.size() + 9 + 36);
               } else if (actionType == SlotActionType.THROW && !_snowmanxxxx.isEmpty()) {
                  ItemStack _snowmanxxxxxxx = _snowmanxxxx.copy();
                  _snowmanxxxxxxx.setCount(clickData == 0 ? 1 : _snowmanxxxxxxx.getMaxCount());
                  this.client.player.dropItem(_snowmanxxxxxxx, true);
                  this.client.interactionManager.dropCreativeStack(_snowmanxxxxxxx);
               }

               this.client.player.playerScreenHandler.sendContentUpdates();
            }
         }
      }
   }

   private boolean isCreativeInventorySlot(@Nullable Slot slot) {
      return slot != null && slot.inventory == INVENTORY;
   }

   @Override
   protected void applyStatusEffectOffset() {
      int _snowman = this.x;
      super.applyStatusEffectOffset();
      if (this.searchBox != null && this.x != _snowman) {
         this.searchBox.setX(this.x + 82);
      }
   }

   @Override
   protected void init() {
      if (this.client.interactionManager.hasCreativeInventory()) {
         super.init();
         this.client.keyboard.setRepeatEvents(true);
         this.searchBox = new TextFieldWidget(this.textRenderer, this.x + 82, this.y + 6, 80, 9, new TranslatableText("itemGroup.search"));
         this.searchBox.setMaxLength(50);
         this.searchBox.setHasBorder(false);
         this.searchBox.setVisible(false);
         this.searchBox.setEditableColor(16777215);
         this.children.add(this.searchBox);
         int _snowman = selectedTab;
         selectedTab = -1;
         this.setSelectedTab(ItemGroup.GROUPS[_snowman]);
         this.client.player.playerScreenHandler.removeListener(this.listener);
         this.listener = new CreativeInventoryListener(this.client);
         this.client.player.playerScreenHandler.addListener(this.listener);
      } else {
         this.client.openScreen(new InventoryScreen(this.client.player));
      }
   }

   @Override
   public void resize(MinecraftClient client, int width, int height) {
      String _snowman = this.searchBox.getText();
      this.init(client, width, height);
      this.searchBox.setText(_snowman);
      if (!this.searchBox.getText().isEmpty()) {
         this.search();
      }
   }

   @Override
   public void removed() {
      super.removed();
      if (this.client.player != null && this.client.player.inventory != null) {
         this.client.player.playerScreenHandler.removeListener(this.listener);
      }

      this.client.keyboard.setRepeatEvents(false);
   }

   @Override
   public boolean charTyped(char chr, int keyCode) {
      if (this.ignoreTypedCharacter) {
         return false;
      } else if (selectedTab != ItemGroup.SEARCH.getIndex()) {
         return false;
      } else {
         String _snowman = this.searchBox.getText();
         if (this.searchBox.charTyped(chr, keyCode)) {
            if (!Objects.equals(_snowman, this.searchBox.getText())) {
               this.search();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      this.ignoreTypedCharacter = false;
      if (selectedTab != ItemGroup.SEARCH.getIndex()) {
         if (this.client.options.keyChat.matchesKey(keyCode, scanCode)) {
            this.ignoreTypedCharacter = true;
            this.setSelectedTab(ItemGroup.SEARCH);
            return true;
         } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
         }
      } else {
         boolean _snowman = !this.isCreativeInventorySlot(this.focusedSlot) || this.focusedSlot.hasStack();
         boolean _snowmanx = InputUtil.fromKeyCode(keyCode, scanCode).method_30103().isPresent();
         if (_snowman && _snowmanx && this.handleHotbarKeyPressed(keyCode, scanCode)) {
            this.ignoreTypedCharacter = true;
            return true;
         } else {
            String _snowmanxx = this.searchBox.getText();
            if (this.searchBox.keyPressed(keyCode, scanCode, modifiers)) {
               if (!Objects.equals(_snowmanxx, this.searchBox.getText())) {
                  this.search();
               }

               return true;
            } else {
               return this.searchBox.isFocused() && this.searchBox.isVisible() && keyCode != 256 ? true : super.keyPressed(keyCode, scanCode, modifiers);
            }
         }
      }
   }

   @Override
   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      this.ignoreTypedCharacter = false;
      return super.keyReleased(keyCode, scanCode, modifiers);
   }

   private void search() {
      this.handler.itemList.clear();
      this.searchResultTags.clear();
      String _snowman = this.searchBox.getText();
      if (_snowman.isEmpty()) {
         for (Item _snowmanx : Registry.ITEM) {
            _snowmanx.appendStacks(ItemGroup.SEARCH, this.handler.itemList);
         }
      } else {
         Searchable<ItemStack> _snowmanx;
         if (_snowman.startsWith("#")) {
            _snowman = _snowman.substring(1);
            _snowmanx = this.client.getSearchableContainer(SearchManager.ITEM_TAG);
            this.searchForTags(_snowman);
         } else {
            _snowmanx = this.client.getSearchableContainer(SearchManager.ITEM_TOOLTIP);
         }

         this.handler.itemList.addAll(_snowmanx.findAll(_snowman.toLowerCase(Locale.ROOT)));
      }

      this.scrollPosition = 0.0F;
      this.handler.scrollItems(0.0F);
   }

   private void searchForTags(String _snowman) {
      int _snowmanx = _snowman.indexOf(58);
      Predicate<Identifier> _snowmanxx;
      if (_snowmanx == -1) {
         _snowmanxx = _snowmanxxx -> _snowmanxxx.getPath().contains(_snowman);
      } else {
         String _snowmanxxx = _snowman.substring(0, _snowmanx).trim();
         String _snowmanxxxx = _snowman.substring(_snowmanx + 1).trim();
         _snowmanxx = _snowmanxxxxx -> _snowmanxxxxx.getNamespace().contains(_snowman) && _snowmanxxxxx.getPath().contains(_snowman);
      }

      TagGroup<Item> _snowmanxxx = ItemTags.getTagGroup();
      _snowmanxxx.getTagIds().stream().filter(_snowmanxx).forEach(_snowmanxxxx -> {
         Tag var10000 = this.searchResultTags.put(_snowmanxxxx, _snowman.getTag(_snowmanxxxx));
      });
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      ItemGroup _snowman = ItemGroup.GROUPS[selectedTab];
      if (_snowman.shouldRenderName()) {
         RenderSystem.disableBlend();
         this.textRenderer.draw(matrices, _snowman.getTranslationKey(), 8.0F, 6.0F, 4210752);
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         double _snowman = mouseX - (double)this.x;
         double _snowmanx = mouseY - (double)this.y;

         for (ItemGroup _snowmanxx : ItemGroup.GROUPS) {
            if (this.isClickInTab(_snowmanxx, _snowman, _snowmanx)) {
               return true;
            }
         }

         if (selectedTab != ItemGroup.INVENTORY.getIndex() && this.isClickInScrollbar(mouseX, mouseY)) {
            this.scrolling = this.hasScrollbar();
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (button == 0) {
         double _snowman = mouseX - (double)this.x;
         double _snowmanx = mouseY - (double)this.y;
         this.scrolling = false;

         for (ItemGroup _snowmanxx : ItemGroup.GROUPS) {
            if (this.isClickInTab(_snowmanxx, _snowman, _snowmanx)) {
               this.setSelectedTab(_snowmanxx);
               return true;
            }
         }
      }

      return super.mouseReleased(mouseX, mouseY, button);
   }

   private boolean hasScrollbar() {
      return selectedTab != ItemGroup.INVENTORY.getIndex() && ItemGroup.GROUPS[selectedTab].hasScrollbar() && this.handler.shouldShowScrollbar();
   }

   private void setSelectedTab(ItemGroup group) {
      int _snowman = selectedTab;
      selectedTab = group.getIndex();
      this.cursorDragSlots.clear();
      this.handler.itemList.clear();
      if (group == ItemGroup.HOTBAR) {
         HotbarStorage _snowmanx = this.client.getCreativeHotbarStorage();

         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            HotbarStorageEntry _snowmanxxx = _snowmanx.getSavedHotbar(_snowmanxx);
            if (_snowmanxxx.isEmpty()) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < 9; _snowmanxxxx++) {
                  if (_snowmanxxxx == _snowmanxx) {
                     ItemStack _snowmanxxxxx = new ItemStack(Items.PAPER);
                     _snowmanxxxxx.getOrCreateSubTag("CustomCreativeLock");
                     Text _snowmanxxxxxx = this.client.options.keysHotbar[_snowmanxx].getBoundKeyLocalizedText();
                     Text _snowmanxxxxxxx = this.client.options.keySaveToolbarActivator.getBoundKeyLocalizedText();
                     _snowmanxxxxx.setCustomName(new TranslatableText("inventory.hotbarInfo", _snowmanxxxxxxx, _snowmanxxxxxx));
                     this.handler.itemList.add(_snowmanxxxxx);
                  } else {
                     this.handler.itemList.add(ItemStack.EMPTY);
                  }
               }
            } else {
               this.handler.itemList.addAll(_snowmanxxx);
            }
         }
      } else if (group != ItemGroup.SEARCH) {
         group.appendStacks(this.handler.itemList);
      }

      if (group == ItemGroup.INVENTORY) {
         ScreenHandler _snowmanx = this.client.player.playerScreenHandler;
         if (this.slots == null) {
            this.slots = ImmutableList.copyOf(this.handler.slots);
         }

         this.handler.slots.clear();

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.slots.size(); _snowmanxxx++) {
            int _snowmanxxxxx;
            int _snowmanxxxxxx;
            if (_snowmanxxx >= 5 && _snowmanxxx < 9) {
               int _snowmanxxxxxxx = _snowmanxxx - 5;
               int _snowmanxxxxxxxx = _snowmanxxxxxxx / 2;
               int _snowmanxxxxxxxxx = _snowmanxxxxxxx % 2;
               _snowmanxxxxx = 54 + _snowmanxxxxxxxx * 54;
               _snowmanxxxxxx = 6 + _snowmanxxxxxxxxx * 27;
            } else if (_snowmanxxx >= 0 && _snowmanxxx < 5) {
               _snowmanxxxxx = -2000;
               _snowmanxxxxxx = -2000;
            } else if (_snowmanxxx == 45) {
               _snowmanxxxxx = 35;
               _snowmanxxxxxx = 20;
            } else {
               int _snowmanxxxxxxx = _snowmanxxx - 9;
               int _snowmanxxxxxxxx = _snowmanxxxxxxx % 9;
               int _snowmanxxxxxxxxx = _snowmanxxxxxxx / 9;
               _snowmanxxxxx = 9 + _snowmanxxxxxxxx * 18;
               if (_snowmanxxx >= 36) {
                  _snowmanxxxxxx = 112;
               } else {
                  _snowmanxxxxxx = 54 + _snowmanxxxxxxxxx * 18;
               }
            }

            Slot _snowmanxxxxxxx = new CreativeInventoryScreen.CreativeSlot(_snowmanx.slots.get(_snowmanxxx), _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx);
            this.handler.slots.add(_snowmanxxxxxxx);
         }

         this.deleteItemSlot = new Slot(INVENTORY, 0, 173, 112);
         this.handler.slots.add(this.deleteItemSlot);
      } else if (_snowman == ItemGroup.INVENTORY.getIndex()) {
         this.handler.slots.clear();
         this.handler.slots.addAll(this.slots);
         this.slots = null;
      }

      if (this.searchBox != null) {
         if (group == ItemGroup.SEARCH) {
            this.searchBox.setVisible(true);
            this.searchBox.setFocusUnlocked(false);
            this.searchBox.setSelected(true);
            if (_snowman != group.getIndex()) {
               this.searchBox.setText("");
            }

            this.search();
         } else {
            this.searchBox.setVisible(false);
            this.searchBox.setFocusUnlocked(true);
            this.searchBox.setSelected(false);
            this.searchBox.setText("");
         }
      }

      this.scrollPosition = 0.0F;
      this.handler.scrollItems(0.0F);
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      if (!this.hasScrollbar()) {
         return false;
      } else {
         int _snowman = (this.handler.itemList.size() + 9 - 1) / 9 - 5;
         this.scrollPosition = (float)((double)this.scrollPosition - amount / (double)_snowman);
         this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0F, 1.0F);
         this.handler.scrollItems(this.scrollPosition);
         return true;
      }
   }

   @Override
   protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
      boolean _snowman = mouseX < (double)left
         || mouseY < (double)top
         || mouseX >= (double)(left + this.backgroundWidth)
         || mouseY >= (double)(top + this.backgroundHeight);
      this.lastClickOutsideBounds = _snowman && !this.isClickInTab(ItemGroup.GROUPS[selectedTab], mouseX, mouseY);
      return this.lastClickOutsideBounds;
   }

   protected boolean isClickInScrollbar(double mouseX, double mouseY) {
      int _snowman = this.x;
      int _snowmanx = this.y;
      int _snowmanxx = _snowman + 175;
      int _snowmanxxx = _snowmanx + 18;
      int _snowmanxxxx = _snowmanxx + 14;
      int _snowmanxxxxx = _snowmanxxx + 112;
      return mouseX >= (double)_snowmanxx && mouseY >= (double)_snowmanxxx && mouseX < (double)_snowmanxxxx && mouseY < (double)_snowmanxxxxx;
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.scrolling) {
         int _snowman = this.y + 18;
         int _snowmanx = _snowman + 112;
         this.scrollPosition = ((float)mouseY - (float)_snowman - 7.5F) / ((float)(_snowmanx - _snowman) - 15.0F);
         this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0F, 1.0F);
         this.handler.scrollItems(this.scrollPosition);
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);

      for (ItemGroup _snowman : ItemGroup.GROUPS) {
         if (this.renderTabTooltipIfHovered(matrices, _snowman, mouseX, mouseY)) {
            break;
         }
      }

      if (this.deleteItemSlot != null
         && selectedTab == ItemGroup.INVENTORY.getIndex()
         && this.isPointWithinBounds(this.deleteItemSlot.x, this.deleteItemSlot.y, 16, 16, (double)mouseX, (double)mouseY)) {
         this.renderTooltip(matrices, field_26563, mouseX, mouseY);
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
   }

   @Override
   protected void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y) {
      if (selectedTab == ItemGroup.SEARCH.getIndex()) {
         List<Text> _snowman = stack.getTooltip(
            this.client.player, this.client.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL
         );
         List<Text> _snowmanx = Lists.newArrayList(_snowman);
         Item _snowmanxx = stack.getItem();
         ItemGroup _snowmanxxx = _snowmanxx.getGroup();
         if (_snowmanxxx == null && _snowmanxx == Items.ENCHANTED_BOOK) {
            Map<Enchantment, Integer> _snowmanxxxx = EnchantmentHelper.get(stack);
            if (_snowmanxxxx.size() == 1) {
               Enchantment _snowmanxxxxx = _snowmanxxxx.keySet().iterator().next();

               for (ItemGroup _snowmanxxxxxx : ItemGroup.GROUPS) {
                  if (_snowmanxxxxxx.containsEnchantments(_snowmanxxxxx.type)) {
                     _snowmanxxx = _snowmanxxxxxx;
                     break;
                  }
               }
            }
         }

         this.searchResultTags.forEach((_snowmanxxxx, _snowmanxxxxx) -> {
            if (_snowmanxxxxx.contains(_snowman)) {
               _snowman.add(1, new LiteralText("#" + _snowmanxxxx).formatted(Formatting.DARK_PURPLE));
            }
         });
         if (_snowmanxxx != null) {
            _snowmanx.add(1, _snowmanxxx.getTranslationKey().shallowCopy().formatted(Formatting.BLUE));
         }

         this.renderTooltip(matrices, _snowmanx, x, y);
      } else {
         super.renderTooltip(matrices, stack, x, y);
      }
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      ItemGroup _snowman = ItemGroup.GROUPS[selectedTab];

      for (ItemGroup _snowmanx : ItemGroup.GROUPS) {
         this.client.getTextureManager().bindTexture(TEXTURE);
         if (_snowmanx.getIndex() != selectedTab) {
            this.renderTabIcon(matrices, _snowmanx);
         }
      }

      this.client.getTextureManager().bindTexture(new Identifier("textures/gui/container/creative_inventory/tab_" + _snowman.getTexture()));
      this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
      this.searchBox.render(matrices, mouseX, mouseY, delta);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      int _snowmanxx = this.x + 175;
      int _snowmanxxx = this.y + 18;
      int _snowmanxxxx = _snowmanxxx + 112;
      this.client.getTextureManager().bindTexture(TEXTURE);
      if (_snowman.hasScrollbar()) {
         this.drawTexture(matrices, _snowmanxx, _snowmanxxx + (int)((float)(_snowmanxxxx - _snowmanxxx - 17) * this.scrollPosition), 232 + (this.hasScrollbar() ? 0 : 12), 0, 12, 15);
      }

      this.renderTabIcon(matrices, _snowman);
      if (_snowman == ItemGroup.INVENTORY) {
         InventoryScreen.drawEntity(this.x + 88, this.y + 45, 20, (float)(this.x + 88 - mouseX), (float)(this.y + 45 - 30 - mouseY), this.client.player);
      }
   }

   protected boolean isClickInTab(ItemGroup group, double mouseX, double mouseY) {
      int _snowman = group.getColumn();
      int _snowmanx = 28 * _snowman;
      int _snowmanxx = 0;
      if (group.isSpecial()) {
         _snowmanx = this.backgroundWidth - 28 * (6 - _snowman) + 2;
      } else if (_snowman > 0) {
         _snowmanx += _snowman;
      }

      if (group.isTopRow()) {
         _snowmanxx -= 32;
      } else {
         _snowmanxx += this.backgroundHeight;
      }

      return mouseX >= (double)_snowmanx && mouseX <= (double)(_snowmanx + 28) && mouseY >= (double)_snowmanxx && mouseY <= (double)(_snowmanxx + 32);
   }

   protected boolean renderTabTooltipIfHovered(MatrixStack _snowman, ItemGroup _snowman, int _snowman, int _snowman) {
      int _snowmanxxxx = _snowman.getColumn();
      int _snowmanxxxxx = 28 * _snowmanxxxx;
      int _snowmanxxxxxx = 0;
      if (_snowman.isSpecial()) {
         _snowmanxxxxx = this.backgroundWidth - 28 * (6 - _snowmanxxxx) + 2;
      } else if (_snowmanxxxx > 0) {
         _snowmanxxxxx += _snowmanxxxx;
      }

      if (_snowman.isTopRow()) {
         _snowmanxxxxxx -= 32;
      } else {
         _snowmanxxxxxx += this.backgroundHeight;
      }

      if (this.isPointWithinBounds(_snowmanxxxxx + 3, _snowmanxxxxxx + 3, 23, 27, (double)_snowman, (double)_snowman)) {
         this.renderTooltip(_snowman, _snowman.getTranslationKey(), _snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   protected void renderTabIcon(MatrixStack _snowman, ItemGroup _snowman) {
      boolean _snowmanxx = _snowman.getIndex() == selectedTab;
      boolean _snowmanxxx = _snowman.isTopRow();
      int _snowmanxxxx = _snowman.getColumn();
      int _snowmanxxxxx = _snowmanxxxx * 28;
      int _snowmanxxxxxx = 0;
      int _snowmanxxxxxxx = this.x + 28 * _snowmanxxxx;
      int _snowmanxxxxxxxx = this.y;
      int _snowmanxxxxxxxxx = 32;
      if (_snowmanxx) {
         _snowmanxxxxxx += 32;
      }

      if (_snowman.isSpecial()) {
         _snowmanxxxxxxx = this.x + this.backgroundWidth - 28 * (6 - _snowmanxxxx);
      } else if (_snowmanxxxx > 0) {
         _snowmanxxxxxxx += _snowmanxxxx;
      }

      if (_snowmanxxx) {
         _snowmanxxxxxxxx -= 28;
      } else {
         _snowmanxxxxxx += 64;
         _snowmanxxxxxxxx += this.backgroundHeight - 4;
      }

      this.drawTexture(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx, 28, 32);
      this.itemRenderer.zOffset = 100.0F;
      _snowmanxxxxxxx += 6;
      _snowmanxxxxxxxx += 8 + (_snowmanxxx ? 1 : -1);
      RenderSystem.enableRescaleNormal();
      ItemStack _snowmanxxxxxxxxxx = _snowman.getIcon();
      this.itemRenderer.renderInGuiWithOverrides(_snowmanxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      this.itemRenderer.renderGuiItemOverlay(this.textRenderer, _snowmanxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      this.itemRenderer.zOffset = 0.0F;
   }

   public int getSelectedTab() {
      return selectedTab;
   }

   public static void onHotbarKeyPress(MinecraftClient client, int index, boolean restore, boolean save) {
      ClientPlayerEntity _snowman = client.player;
      HotbarStorage _snowmanx = client.getCreativeHotbarStorage();
      HotbarStorageEntry _snowmanxx = _snowmanx.getSavedHotbar(index);
      if (restore) {
         for (int _snowmanxxx = 0; _snowmanxxx < PlayerInventory.getHotbarSize(); _snowmanxxx++) {
            ItemStack _snowmanxxxx = ((ItemStack)_snowmanxx.get(_snowmanxxx)).copy();
            _snowman.inventory.setStack(_snowmanxxx, _snowmanxxxx);
            client.interactionManager.clickCreativeStack(_snowmanxxxx, 36 + _snowmanxxx);
         }

         _snowman.playerScreenHandler.sendContentUpdates();
      } else if (save) {
         for (int _snowmanxxx = 0; _snowmanxxx < PlayerInventory.getHotbarSize(); _snowmanxxx++) {
            _snowmanxx.set(_snowmanxxx, _snowman.inventory.getStack(_snowmanxxx).copy());
         }

         Text _snowmanxxx = client.options.keysHotbar[index].getBoundKeyLocalizedText();
         Text _snowmanxxxx = client.options.keyLoadToolbarActivator.getBoundKeyLocalizedText();
         client.inGameHud.setOverlayMessage(new TranslatableText("inventory.hotbarSaved", _snowmanxxxx, _snowmanxxx), false);
         _snowmanx.save();
      }
   }

   public static class CreativeScreenHandler extends ScreenHandler {
      public final DefaultedList<ItemStack> itemList = DefaultedList.of();

      public CreativeScreenHandler(PlayerEntity _snowman) {
         super(null, 0);
         PlayerInventory _snowmanx = _snowman.inventory;

         for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
               this.addSlot(new CreativeInventoryScreen.LockableSlot(CreativeInventoryScreen.INVENTORY, _snowmanxx * 9 + _snowmanxxx, 9 + _snowmanxxx * 18, 18 + _snowmanxx * 18));
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            this.addSlot(new Slot(_snowmanx, _snowmanxx, 9 + _snowmanxx * 18, 112));
         }

         this.scrollItems(0.0F);
      }

      @Override
      public boolean canUse(PlayerEntity player) {
         return true;
      }

      public void scrollItems(float position) {
         int _snowman = (this.itemList.size() + 9 - 1) / 9 - 5;
         int _snowmanx = (int)((double)(position * (float)_snowman) + 0.5);
         if (_snowmanx < 0) {
            _snowmanx = 0;
         }

         for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanxxx + (_snowmanxx + _snowmanx) * 9;
               if (_snowmanxxxx >= 0 && _snowmanxxxx < this.itemList.size()) {
                  CreativeInventoryScreen.INVENTORY.setStack(_snowmanxxx + _snowmanxx * 9, this.itemList.get(_snowmanxxxx));
               } else {
                  CreativeInventoryScreen.INVENTORY.setStack(_snowmanxxx + _snowmanxx * 9, ItemStack.EMPTY);
               }
            }
         }
      }

      public boolean shouldShowScrollbar() {
         return this.itemList.size() > 45;
      }

      @Override
      public ItemStack transferSlot(PlayerEntity player, int index) {
         if (index >= this.slots.size() - 9 && index < this.slots.size()) {
            Slot _snowman = this.slots.get(index);
            if (_snowman != null && _snowman.hasStack()) {
               _snowman.setStack(ItemStack.EMPTY);
            }
         }

         return ItemStack.EMPTY;
      }

      @Override
      public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
         return slot.inventory != CreativeInventoryScreen.INVENTORY;
      }

      @Override
      public boolean canInsertIntoSlot(Slot slot) {
         return slot.inventory != CreativeInventoryScreen.INVENTORY;
      }
   }

   static class CreativeSlot extends Slot {
      private final Slot slot;

      public CreativeSlot(Slot slot, int invSlot, int x, int y) {
         super(slot.inventory, invSlot, x, y);
         this.slot = slot;
      }

      @Override
      public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
         return this.slot.onTakeItem(player, stack);
      }

      @Override
      public boolean canInsert(ItemStack stack) {
         return this.slot.canInsert(stack);
      }

      @Override
      public ItemStack getStack() {
         return this.slot.getStack();
      }

      @Override
      public boolean hasStack() {
         return this.slot.hasStack();
      }

      @Override
      public void setStack(ItemStack stack) {
         this.slot.setStack(stack);
      }

      @Override
      public void markDirty() {
         this.slot.markDirty();
      }

      @Override
      public int getMaxItemCount() {
         return this.slot.getMaxItemCount();
      }

      @Override
      public int getMaxItemCount(ItemStack stack) {
         return this.slot.getMaxItemCount(stack);
      }

      @Nullable
      @Override
      public Pair<Identifier, Identifier> getBackgroundSprite() {
         return this.slot.getBackgroundSprite();
      }

      @Override
      public ItemStack takeStack(int amount) {
         return this.slot.takeStack(amount);
      }

      @Override
      public boolean doDrawHoveringEffect() {
         return this.slot.doDrawHoveringEffect();
      }

      @Override
      public boolean canTakeItems(PlayerEntity playerEntity) {
         return this.slot.canTakeItems(playerEntity);
      }
   }

   static class LockableSlot extends Slot {
      public LockableSlot(Inventory _snowman, int _snowman, int _snowman, int _snowman) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean canTakeItems(PlayerEntity playerEntity) {
         return super.canTakeItems(playerEntity) && this.hasStack() ? this.getStack().getSubTag("CustomCreativeLock") == null : !this.hasStack();
      }
   }
}
