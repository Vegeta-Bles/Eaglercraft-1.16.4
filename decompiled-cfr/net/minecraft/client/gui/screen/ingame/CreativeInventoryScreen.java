/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryListener;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.HotbarStorage;
import net.minecraft.client.options.HotbarStorageEntry;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.search.SearchableContainer;
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

public class CreativeInventoryScreen
extends AbstractInventoryScreen<CreativeScreenHandler> {
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
        super(new CreativeScreenHandler(player), player.inventory, LiteralText.EMPTY);
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
        boolean bl = actionType == SlotActionType.QUICK_MOVE;
        SlotActionType slotActionType = actionType = invSlot == -999 && actionType == SlotActionType.PICKUP ? SlotActionType.THROW : actionType;
        if (slot != null || selectedTab == ItemGroup.INVENTORY.getIndex() || actionType == SlotActionType.QUICK_CRAFT) {
            if (slot != null && !slot.canTakeItems(this.client.player)) {
                return;
            }
            if (slot == this.deleteItemSlot && bl) {
                for (int i = 0; i < this.client.player.playerScreenHandler.getStacks().size(); ++i) {
                    this.client.interactionManager.clickCreativeStack(ItemStack.EMPTY, i);
                }
            } else if (selectedTab == ItemGroup.INVENTORY.getIndex()) {
                if (slot == this.deleteItemSlot) {
                    this.client.player.inventory.setCursorStack(ItemStack.EMPTY);
                } else if (actionType == SlotActionType.THROW && slot != null && slot.hasStack()) {
                    ItemStack itemStack = slot.takeStack(clickData == 0 ? 1 : slot.getStack().getMaxCount());
                    _snowman = slot.getStack();
                    this.client.player.dropItem(itemStack, true);
                    this.client.interactionManager.dropCreativeStack(itemStack);
                    this.client.interactionManager.clickCreativeStack(_snowman, ((CreativeSlot)((CreativeSlot)slot)).slot.id);
                } else if (actionType == SlotActionType.THROW && !this.client.player.inventory.getCursorStack().isEmpty()) {
                    this.client.player.dropItem(this.client.player.inventory.getCursorStack(), true);
                    this.client.interactionManager.dropCreativeStack(this.client.player.inventory.getCursorStack());
                    this.client.player.inventory.setCursorStack(ItemStack.EMPTY);
                } else {
                    this.client.player.playerScreenHandler.onSlotClick(slot == null ? invSlot : ((CreativeSlot)((CreativeSlot)slot)).slot.id, clickData, actionType, this.client.player);
                    this.client.player.playerScreenHandler.sendContentUpdates();
                }
            } else if (actionType != SlotActionType.QUICK_CRAFT && slot.inventory == INVENTORY) {
                PlayerInventory playerInventory = this.client.player.inventory;
                ItemStack _snowman2 = playerInventory.getCursorStack();
                ItemStack _snowman3 = slot.getStack();
                if (actionType == SlotActionType.SWAP) {
                    if (!_snowman3.isEmpty()) {
                        ItemStack itemStack = _snowman3.copy();
                        itemStack.setCount(itemStack.getMaxCount());
                        this.client.player.inventory.setStack(clickData, itemStack);
                        this.client.player.playerScreenHandler.sendContentUpdates();
                    }
                    return;
                }
                if (actionType == SlotActionType.CLONE) {
                    if (playerInventory.getCursorStack().isEmpty() && slot.hasStack()) {
                        ItemStack itemStack = slot.getStack().copy();
                        itemStack.setCount(itemStack.getMaxCount());
                        playerInventory.setCursorStack(itemStack);
                    }
                    return;
                }
                if (actionType == SlotActionType.THROW) {
                    if (!_snowman3.isEmpty()) {
                        ItemStack itemStack = _snowman3.copy();
                        itemStack.setCount(clickData == 0 ? 1 : itemStack.getMaxCount());
                        this.client.player.dropItem(itemStack, true);
                        this.client.interactionManager.dropCreativeStack(itemStack);
                    }
                    return;
                }
                if (!_snowman2.isEmpty() && !_snowman3.isEmpty() && _snowman2.isItemEqualIgnoreDamage(_snowman3) && ItemStack.areTagsEqual(_snowman2, _snowman3)) {
                    if (clickData == 0) {
                        if (bl) {
                            _snowman2.setCount(_snowman2.getMaxCount());
                        } else if (_snowman2.getCount() < _snowman2.getMaxCount()) {
                            _snowman2.increment(1);
                        }
                    } else {
                        _snowman2.decrement(1);
                    }
                } else if (_snowman3.isEmpty() || !_snowman2.isEmpty()) {
                    if (clickData == 0) {
                        playerInventory.setCursorStack(ItemStack.EMPTY);
                    } else {
                        playerInventory.getCursorStack().decrement(1);
                    }
                } else {
                    playerInventory.setCursorStack(_snowman3.copy());
                    _snowman2 = playerInventory.getCursorStack();
                    if (bl) {
                        _snowman2.setCount(_snowman2.getMaxCount());
                    }
                }
            } else if (this.handler != null) {
                ItemStack itemStack = slot == null ? ItemStack.EMPTY : ((CreativeScreenHandler)this.handler).getSlot(slot.id).getStack();
                ((CreativeScreenHandler)this.handler).onSlotClick(slot == null ? invSlot : slot.id, clickData, actionType, this.client.player);
                if (ScreenHandler.unpackQuickCraftStage(clickData) == 2) {
                    for (int i = 0; i < 9; ++i) {
                        this.client.interactionManager.clickCreativeStack(((CreativeScreenHandler)this.handler).getSlot(45 + i).getStack(), 36 + i);
                    }
                } else if (slot != null) {
                    ItemStack itemStack2 = ((CreativeScreenHandler)this.handler).getSlot(slot.id).getStack();
                    this.client.interactionManager.clickCreativeStack(itemStack2, slot.id - ((CreativeScreenHandler)this.handler).slots.size() + 9 + 36);
                    int _snowman4 = 45 + clickData;
                    if (actionType == SlotActionType.SWAP) {
                        this.client.interactionManager.clickCreativeStack(itemStack, _snowman4 - ((CreativeScreenHandler)this.handler).slots.size() + 9 + 36);
                    } else if (actionType == SlotActionType.THROW && !itemStack.isEmpty()) {
                        _snowman = itemStack.copy();
                        _snowman.setCount(clickData == 0 ? 1 : _snowman.getMaxCount());
                        this.client.player.dropItem(_snowman, true);
                        this.client.interactionManager.dropCreativeStack(_snowman);
                    }
                    this.client.player.playerScreenHandler.sendContentUpdates();
                }
            }
        } else {
            PlayerInventory playerInventory = this.client.player.inventory;
            if (!playerInventory.getCursorStack().isEmpty() && this.lastClickOutsideBounds) {
                if (clickData == 0) {
                    this.client.player.dropItem(playerInventory.getCursorStack(), true);
                    this.client.interactionManager.dropCreativeStack(playerInventory.getCursorStack());
                    playerInventory.setCursorStack(ItemStack.EMPTY);
                }
                if (clickData == 1) {
                    ItemStack itemStack = playerInventory.getCursorStack().split(1);
                    this.client.player.dropItem(itemStack, true);
                    this.client.interactionManager.dropCreativeStack(itemStack);
                }
            }
        }
    }

    private boolean isCreativeInventorySlot(@Nullable Slot slot) {
        return slot != null && slot.inventory == INVENTORY;
    }

    @Override
    protected void applyStatusEffectOffset() {
        int n = this.x;
        super.applyStatusEffectOffset();
        if (this.searchBox != null && this.x != n) {
            this.searchBox.setX(this.x + 82);
        }
    }

    @Override
    protected void init() {
        if (this.client.interactionManager.hasCreativeInventory()) {
            super.init();
            this.client.keyboard.setRepeatEvents(true);
            this.searchBox = new TextFieldWidget(this.textRenderer, this.x + 82, this.y + 6, 80, this.textRenderer.fontHeight, new TranslatableText("itemGroup.search"));
            this.searchBox.setMaxLength(50);
            this.searchBox.setHasBorder(false);
            this.searchBox.setVisible(false);
            this.searchBox.setEditableColor(0xFFFFFF);
            this.children.add(this.searchBox);
            int n = selectedTab;
            selectedTab = -1;
            this.setSelectedTab(ItemGroup.GROUPS[n]);
            this.client.player.playerScreenHandler.removeListener(this.listener);
            this.listener = new CreativeInventoryListener(this.client);
            this.client.player.playerScreenHandler.addListener(this.listener);
        } else {
            this.client.openScreen(new InventoryScreen(this.client.player));
        }
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.searchBox.getText();
        this.init(client, width, height);
        this.searchBox.setText(string);
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
        }
        if (selectedTab != ItemGroup.SEARCH.getIndex()) {
            return false;
        }
        String string = this.searchBox.getText();
        if (this.searchBox.charTyped(chr, keyCode)) {
            if (!Objects.equals(string, this.searchBox.getText())) {
                this.search();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.ignoreTypedCharacter = false;
        if (selectedTab != ItemGroup.SEARCH.getIndex()) {
            if (this.client.options.keyChat.matchesKey(keyCode, scanCode)) {
                this.ignoreTypedCharacter = true;
                this.setSelectedTab(ItemGroup.SEARCH);
                return true;
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
        boolean bl = !this.isCreativeInventorySlot(this.focusedSlot) || this.focusedSlot.hasStack();
        _snowman = InputUtil.fromKeyCode(keyCode, scanCode).method_30103().isPresent();
        if (bl && _snowman && this.handleHotbarKeyPressed(keyCode, scanCode)) {
            this.ignoreTypedCharacter = true;
            return true;
        }
        String _snowman2 = this.searchBox.getText();
        if (this.searchBox.keyPressed(keyCode, scanCode, modifiers)) {
            if (!Objects.equals(_snowman2, this.searchBox.getText())) {
                this.search();
            }
            return true;
        }
        if (this.searchBox.isFocused() && this.searchBox.isVisible() && keyCode != 256) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.ignoreTypedCharacter = false;
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    private void search() {
        ((CreativeScreenHandler)this.handler).itemList.clear();
        this.searchResultTags.clear();
        String string = this.searchBox.getText();
        if (string.isEmpty()) {
            for (Item item : Registry.ITEM) {
                item.appendStacks(ItemGroup.SEARCH, ((CreativeScreenHandler)this.handler).itemList);
            }
        } else {
            SearchableContainer<ItemStack> searchableContainer;
            if (string.startsWith("#")) {
                string = string.substring(1);
                searchableContainer = this.client.getSearchableContainer(SearchManager.ITEM_TAG);
                this.searchForTags(string);
            } else {
                searchableContainer = this.client.getSearchableContainer(SearchManager.ITEM_TOOLTIP);
            }
            ((CreativeScreenHandler)this.handler).itemList.addAll(searchableContainer.findAll(string.toLowerCase(Locale.ROOT)));
        }
        this.scrollPosition = 0.0f;
        ((CreativeScreenHandler)this.handler).scrollItems(0.0f);
    }

    private void searchForTags(String string2) {
        Object _snowman2;
        Predicate<Identifier> _snowman3;
        int n = string2.indexOf(58);
        if (n == -1) {
            _snowman3 = identifier -> identifier.getPath().contains(string2);
        } else {
            String string2;
            _snowman2 = string2.substring(0, n).trim();
            _snowman = string2.substring(n + 1).trim();
            _snowman3 = arg_0 -> CreativeInventoryScreen.method_15874((String)_snowman2, _snowman, arg_0);
        }
        _snowman2 = ItemTags.getTagGroup();
        _snowman2.getTagIds().stream().filter(_snowman3).forEach(arg_0 -> this.method_15873((TagGroup)_snowman2, arg_0));
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        ItemGroup itemGroup = ItemGroup.GROUPS[selectedTab];
        if (itemGroup.shouldRenderName()) {
            RenderSystem.disableBlend();
            this.textRenderer.draw(matrices, itemGroup.getTranslationKey(), 8.0f, 6.0f, 0x404040);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            double d = mouseX - (double)this.x;
            _snowman = mouseY - (double)this.y;
            for (ItemGroup itemGroup : ItemGroup.GROUPS) {
                if (!this.isClickInTab(itemGroup, d, _snowman)) continue;
                return true;
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
            double d = mouseX - (double)this.x;
            _snowman = mouseY - (double)this.y;
            this.scrolling = false;
            for (ItemGroup itemGroup : ItemGroup.GROUPS) {
                if (!this.isClickInTab(itemGroup, d, _snowman)) continue;
                this.setSelectedTab(itemGroup);
                return true;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private boolean hasScrollbar() {
        return selectedTab != ItemGroup.INVENTORY.getIndex() && ItemGroup.GROUPS[selectedTab].hasScrollbar() && ((CreativeScreenHandler)this.handler).shouldShowScrollbar();
    }

    private void setSelectedTab(ItemGroup group) {
        Object object;
        int n;
        int n2 = selectedTab;
        selectedTab = group.getIndex();
        this.cursorDragSlots.clear();
        ((CreativeScreenHandler)this.handler).itemList.clear();
        if (group == ItemGroup.HOTBAR) {
            Object object2 = this.client.getCreativeHotbarStorage();
            for (n = 0; n < 9; ++n) {
                HotbarStorageEntry hotbarStorageEntry = ((HotbarStorage)object2).getSavedHotbar(n);
                if (hotbarStorageEntry.isEmpty()) {
                    for (int i = 0; i < 9; ++i) {
                        if (i == n) {
                            object = new ItemStack(Items.PAPER);
                            ((ItemStack)object).getOrCreateSubTag("CustomCreativeLock");
                            Text _snowman2 = this.client.options.keysHotbar[n].getBoundKeyLocalizedText();
                            Text _snowman3 = this.client.options.keySaveToolbarActivator.getBoundKeyLocalizedText();
                            ((ItemStack)object).setCustomName(new TranslatableText("inventory.hotbarInfo", _snowman3, _snowman2));
                            ((CreativeScreenHandler)this.handler).itemList.add((ItemStack)object);
                            continue;
                        }
                        ((CreativeScreenHandler)this.handler).itemList.add(ItemStack.EMPTY);
                    }
                    continue;
                }
                ((CreativeScreenHandler)this.handler).itemList.addAll((Collection<ItemStack>)((Object)hotbarStorageEntry));
            }
        } else if (group != ItemGroup.SEARCH) {
            group.appendStacks(((CreativeScreenHandler)this.handler).itemList);
        }
        if (group == ItemGroup.INVENTORY) {
            object2 = this.client.player.playerScreenHandler;
            if (this.slots == null) {
                this.slots = ImmutableList.copyOf((Collection)((CreativeScreenHandler)this.handler).slots);
            }
            ((CreativeScreenHandler)this.handler).slots.clear();
            for (n = 0; n < ((ScreenHandler)object2).slots.size(); ++n) {
                if (n >= 5 && n < 9) {
                    _snowman = n - 5;
                    _snowman = _snowman / 2;
                    _snowman = _snowman % 2;
                    _snowman = 54 + _snowman * 54;
                    i = 6 + _snowman * 27;
                } else if (n >= 0 && n < 5) {
                    _snowman = -2000;
                    i = -2000;
                } else if (n == 45) {
                    _snowman = 35;
                    i = 20;
                } else {
                    _snowman = n - 9;
                    _snowman = _snowman % 9;
                    _snowman = _snowman / 9;
                    _snowman = 9 + _snowman * 18;
                    i = n >= 36 ? 112 : 54 + _snowman * 18;
                }
                object = new CreativeSlot(((ScreenHandler)object2).slots.get(n), n, _snowman, i);
                ((CreativeScreenHandler)this.handler).slots.add(object);
            }
            this.deleteItemSlot = new Slot(INVENTORY, 0, 173, 112);
            ((CreativeScreenHandler)this.handler).slots.add(this.deleteItemSlot);
        } else if (n2 == ItemGroup.INVENTORY.getIndex()) {
            ((CreativeScreenHandler)this.handler).slots.clear();
            ((CreativeScreenHandler)this.handler).slots.addAll(this.slots);
            this.slots = null;
        }
        if (this.searchBox != null) {
            if (group == ItemGroup.SEARCH) {
                this.searchBox.setVisible(true);
                this.searchBox.setFocusUnlocked(false);
                this.searchBox.setSelected(true);
                if (n2 != group.getIndex()) {
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
        this.scrollPosition = 0.0f;
        ((CreativeScreenHandler)this.handler).scrollItems(0.0f);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (!this.hasScrollbar()) {
            return false;
        }
        int n = (((CreativeScreenHandler)this.handler).itemList.size() + 9 - 1) / 9 - 5;
        this.scrollPosition = (float)((double)this.scrollPosition - amount / (double)n);
        this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0f, 1.0f);
        ((CreativeScreenHandler)this.handler).scrollItems(this.scrollPosition);
        return true;
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
        this.lastClickOutsideBounds = bl && !this.isClickInTab(ItemGroup.GROUPS[selectedTab], mouseX, mouseY);
        return this.lastClickOutsideBounds;
    }

    protected boolean isClickInScrollbar(double mouseX, double mouseY) {
        int n = this.x;
        _snowman = this.y;
        _snowman = n + 175;
        _snowman = _snowman + 18;
        _snowman = _snowman + 14;
        _snowman = _snowman + 112;
        return mouseX >= (double)_snowman && mouseY >= (double)_snowman && mouseX < (double)_snowman && mouseY < (double)_snowman;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrolling) {
            int n = this.y + 18;
            _snowman = n + 112;
            this.scrollPosition = ((float)mouseY - (float)n - 7.5f) / ((float)(_snowman - n) - 15.0f);
            this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0f, 1.0f);
            ((CreativeScreenHandler)this.handler).scrollItems(this.scrollPosition);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        for (ItemGroup itemGroup : ItemGroup.GROUPS) {
            if (this.renderTabTooltipIfHovered(matrices, itemGroup, mouseX, mouseY)) break;
        }
        if (this.deleteItemSlot != null && selectedTab == ItemGroup.INVENTORY.getIndex() && this.isPointWithinBounds(this.deleteItemSlot.x, this.deleteItemSlot.y, 16, 16, mouseX, mouseY)) {
            this.renderTooltip(matrices, field_26563, mouseX, mouseY);
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y) {
        if (selectedTab == ItemGroup.SEARCH.getIndex()) {
            List<Text> list = stack.getTooltip(this.client.player, this.client.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL);
            ArrayList _snowman2 = Lists.newArrayList(list);
            Item _snowman3 = stack.getItem();
            ItemGroup _snowman4 = _snowman3.getGroup();
            if (_snowman4 == null && _snowman3 == Items.ENCHANTED_BOOK && (_snowman = EnchantmentHelper.get(stack)).size() == 1) {
                Enchantment enchantment = _snowman.keySet().iterator().next();
                for (ItemGroup itemGroup : ItemGroup.GROUPS) {
                    if (!itemGroup.containsEnchantments(enchantment.type)) continue;
                    _snowman4 = itemGroup;
                    break;
                }
            }
            this.searchResultTags.forEach((identifier, tag) -> {
                if (tag.contains(_snowman3)) {
                    _snowman2.add(1, new LiteralText("#" + identifier).formatted(Formatting.DARK_PURPLE));
                }
            });
            if (_snowman4 != null) {
                _snowman2.add(1, _snowman4.getTranslationKey().shallowCopy().formatted(Formatting.BLUE));
            }
            this.renderTooltip(matrices, _snowman2, x, y);
        } else {
            super.renderTooltip(matrices, stack, x, y);
        }
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        ItemGroup itemGroup = ItemGroup.GROUPS[selectedTab];
        for (ItemGroup itemGroup2 : ItemGroup.GROUPS) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            if (itemGroup2.getIndex() == selectedTab) continue;
            this.renderTabIcon(matrices, itemGroup2);
        }
        this.client.getTextureManager().bindTexture(new Identifier("textures/gui/container/creative_inventory/tab_" + itemGroup.getTexture()));
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.searchBox.render(matrices, mouseX, mouseY, delta);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int _snowman2 = this.x + 175;
        int _snowman3 = this.y + 18;
        int _snowman4 = _snowman3 + 112;
        this.client.getTextureManager().bindTexture(TEXTURE);
        if (itemGroup.hasScrollbar()) {
            this.drawTexture(matrices, _snowman2, _snowman3 + (int)((float)(_snowman4 - _snowman3 - 17) * this.scrollPosition), 232 + (this.hasScrollbar() ? 0 : 12), 0, 12, 15);
        }
        this.renderTabIcon(matrices, itemGroup);
        if (itemGroup == ItemGroup.INVENTORY) {
            InventoryScreen.drawEntity(this.x + 88, this.y + 45, 20, this.x + 88 - mouseX, this.y + 45 - 30 - mouseY, this.client.player);
        }
    }

    protected boolean isClickInTab(ItemGroup group, double mouseX, double mouseY) {
        int n = group.getColumn();
        _snowman = 28 * n;
        _snowman = 0;
        if (group.isSpecial()) {
            _snowman = this.backgroundWidth - 28 * (6 - n) + 2;
        } else if (n > 0) {
            _snowman += n;
        }
        _snowman = group.isTopRow() ? (_snowman -= 32) : (_snowman += this.backgroundHeight);
        return mouseX >= (double)_snowman && mouseX <= (double)(_snowman + 28) && mouseY >= (double)_snowman && mouseY <= (double)(_snowman + 32);
    }

    protected boolean renderTabTooltipIfHovered(MatrixStack matrixStack, ItemGroup itemGroup, int n, int n2) {
        _snowman = itemGroup.getColumn();
        _snowman = 28 * _snowman;
        _snowman = 0;
        if (itemGroup.isSpecial()) {
            _snowman = this.backgroundWidth - 28 * (6 - _snowman) + 2;
        } else if (_snowman > 0) {
            _snowman += _snowman;
        }
        _snowman = itemGroup.isTopRow() ? (_snowman -= 32) : (_snowman += this.backgroundHeight);
        if (this.isPointWithinBounds(_snowman + 3, _snowman + 3, 23, 27, n, n2)) {
            this.renderTooltip(matrixStack, itemGroup.getTranslationKey(), n, n2);
            return true;
        }
        return false;
    }

    protected void renderTabIcon(MatrixStack matrixStack, ItemGroup itemGroup) {
        boolean bl = itemGroup.getIndex() == selectedTab;
        _snowman = itemGroup.isTopRow();
        int _snowman2 = itemGroup.getColumn();
        int _snowman3 = _snowman2 * 28;
        int _snowman4 = 0;
        int _snowman5 = this.x + 28 * _snowman2;
        int _snowman6 = this.y;
        int _snowman7 = 32;
        if (bl) {
            _snowman4 += 32;
        }
        if (itemGroup.isSpecial()) {
            _snowman5 = this.x + this.backgroundWidth - 28 * (6 - _snowman2);
        } else if (_snowman2 > 0) {
            _snowman5 += _snowman2;
        }
        if (_snowman) {
            _snowman6 -= 28;
        } else {
            _snowman4 += 64;
            _snowman6 += this.backgroundHeight - 4;
        }
        this.drawTexture(matrixStack, _snowman5, _snowman6, _snowman3, _snowman4, 28, 32);
        this.itemRenderer.zOffset = 100.0f;
        int n = _snowman ? 1 : -1;
        RenderSystem.enableRescaleNormal();
        ItemStack _snowman8 = itemGroup.getIcon();
        this.itemRenderer.renderInGuiWithOverrides(_snowman8, _snowman5 += 6, _snowman6 += 8 + n);
        this.itemRenderer.renderGuiItemOverlay(this.textRenderer, _snowman8, _snowman5, _snowman6);
        this.itemRenderer.zOffset = 0.0f;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public static void onHotbarKeyPress(MinecraftClient client, int index, boolean restore, boolean save) {
        ClientPlayerEntity clientPlayerEntity = client.player;
        HotbarStorage _snowman2 = client.getCreativeHotbarStorage();
        HotbarStorageEntry _snowman3 = _snowman2.getSavedHotbar(index);
        if (restore) {
            for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
                ItemStack itemStack = ((ItemStack)_snowman3.get(i)).copy();
                clientPlayerEntity.inventory.setStack(i, itemStack);
                client.interactionManager.clickCreativeStack(itemStack, 36 + i);
            }
            clientPlayerEntity.playerScreenHandler.sendContentUpdates();
        } else if (save) {
            for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
                _snowman3.set(i, clientPlayerEntity.inventory.getStack(i).copy());
            }
            Text text = client.options.keysHotbar[index].getBoundKeyLocalizedText();
            _snowman = client.options.keyLoadToolbarActivator.getBoundKeyLocalizedText();
            client.inGameHud.setOverlayMessage(new TranslatableText("inventory.hotbarSaved", _snowman, text), false);
            _snowman2.save();
        }
    }

    private /* synthetic */ void method_15873(TagGroup tagGroup, Identifier identifier) {
        this.searchResultTags.put(identifier, tagGroup.getTag(identifier));
    }

    private static /* synthetic */ boolean method_15874(String string, String string2, Identifier identifier) {
        return identifier.getNamespace().contains(string) && identifier.getPath().contains(string2);
    }

    static class LockableSlot
    extends Slot {
        public LockableSlot(Inventory inventory, int n, int n2, int n3) {
            super(inventory, n, n2, n3);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            if (super.canTakeItems(playerEntity) && this.hasStack()) {
                return this.getStack().getSubTag("CustomCreativeLock") == null;
            }
            return !this.hasStack();
        }
    }

    static class CreativeSlot
    extends Slot {
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

        @Override
        @Nullable
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

    public static class CreativeScreenHandler
    extends ScreenHandler {
        public final DefaultedList<ItemStack> itemList = DefaultedList.of();

        public CreativeScreenHandler(PlayerEntity playerEntity) {
            super(null, 0);
            int n;
            PlayerInventory playerInventory = playerEntity.inventory;
            for (n = 0; n < 5; ++n) {
                for (_snowman = 0; _snowman < 9; ++_snowman) {
                    this.addSlot(new LockableSlot(INVENTORY, n * 9 + _snowman, 9 + _snowman * 18, 18 + n * 18));
                }
            }
            for (n = 0; n < 9; ++n) {
                this.addSlot(new Slot(playerInventory, n, 9 + n * 18, 112));
            }
            this.scrollItems(0.0f);
        }

        @Override
        public boolean canUse(PlayerEntity player) {
            return true;
        }

        public void scrollItems(float position) {
            int n = (this.itemList.size() + 9 - 1) / 9 - 5;
            _snowman = (int)((double)(position * (float)n) + 0.5);
            if (_snowman < 0) {
                _snowman = 0;
            }
            for (_snowman = 0; _snowman < 5; ++_snowman) {
                for (_snowman = 0; _snowman < 9; ++_snowman) {
                    _snowman = _snowman + (_snowman + _snowman) * 9;
                    if (_snowman >= 0 && _snowman < this.itemList.size()) {
                        INVENTORY.setStack(_snowman + _snowman * 9, this.itemList.get(_snowman));
                        continue;
                    }
                    INVENTORY.setStack(_snowman + _snowman * 9, ItemStack.EMPTY);
                }
            }
        }

        public boolean shouldShowScrollbar() {
            return this.itemList.size() > 45;
        }

        @Override
        public ItemStack transferSlot(PlayerEntity player, int index) {
            Slot slot;
            if (index >= this.slots.size() - 9 && index < this.slots.size() && (slot = (Slot)this.slots.get(index)) != null && slot.hasStack()) {
                slot.setStack(ItemStack.EMPTY);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
            return slot.inventory != INVENTORY;
        }

        @Override
        public boolean canInsertIntoSlot(Slot slot) {
            return slot.inventory != INVENTORY;
        }
    }
}

