/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public abstract class HandledScreen<T extends ScreenHandler>
extends Screen
implements ScreenHandlerProvider<T> {
    public static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/container/inventory.png");
    protected int backgroundWidth = 176;
    protected int backgroundHeight = 166;
    protected int titleX;
    protected int titleY;
    protected int playerInventoryTitleX;
    protected int playerInventoryTitleY;
    protected final T handler;
    protected final PlayerInventory playerInventory;
    @Nullable
    protected Slot focusedSlot;
    @Nullable
    private Slot touchDragSlotStart;
    @Nullable
    private Slot touchDropOriginSlot;
    @Nullable
    private Slot touchHoveredSlot;
    @Nullable
    private Slot lastClickedSlot;
    protected int x;
    protected int y;
    private boolean touchIsRightClickDrag;
    private ItemStack touchDragStack = ItemStack.EMPTY;
    private int touchDropX;
    private int touchDropY;
    private long touchDropTime;
    private ItemStack touchDropReturningStack = ItemStack.EMPTY;
    private long touchDropTimer;
    protected final Set<Slot> cursorDragSlots = Sets.newHashSet();
    protected boolean cursorDragging;
    private int heldButtonType;
    private int heldButtonCode;
    private boolean cancelNextRelease;
    private int draggedStackRemainder;
    private long lastButtonClickTime;
    private int lastClickedButton;
    private boolean doubleClicking;
    private ItemStack quickMovingStack = ItemStack.EMPTY;

    public HandledScreen(T handler, PlayerInventory inventory, Text title) {
        super(title);
        this.handler = handler;
        this.playerInventory = inventory;
        this.cancelNextRelease = true;
        this.titleX = 8;
        this.titleY = 6;
        this.playerInventoryTitleX = 8;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int _snowman3;
        int _snowman2;
        Object object;
        int n = this.x;
        _snowman = this.y;
        this.drawBackground(matrices, delta, mouseX, mouseY);
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();
        super.render(matrices, mouseX, mouseY, delta);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(n, _snowman, 0.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableRescaleNormal();
        this.focusedSlot = null;
        _snowman = 240;
        _snowman = 240;
        RenderSystem.glMultiTexCoord2f(33986, 240.0f, 240.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        for (_snowman = 0; _snowman < ((ScreenHandler)this.handler).slots.size(); ++_snowman) {
            object = ((ScreenHandler)this.handler).slots.get(_snowman);
            if (((Slot)object).doDrawHoveringEffect()) {
                this.drawSlot(matrices, (Slot)object);
            }
            if (!this.isPointOverSlot((Slot)object, mouseX, mouseY) || !((Slot)object).doDrawHoveringEffect()) continue;
            this.focusedSlot = object;
            RenderSystem.disableDepthTest();
            _snowman2 = ((Slot)object).x;
            _snowman3 = ((Slot)object).y;
            RenderSystem.colorMask(true, true, true, false);
            this.fillGradient(matrices, _snowman2, _snowman3, _snowman2 + 16, _snowman3 + 16, -2130706433, -2130706433);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
        }
        this.drawForeground(matrices, mouseX, mouseY);
        PlayerInventory playerInventory = this.client.player.inventory;
        Object object2 = object = this.touchDragStack.isEmpty() ? playerInventory.getCursorStack() : this.touchDragStack;
        if (!((ItemStack)object).isEmpty()) {
            _snowman2 = 8;
            _snowman3 = this.touchDragStack.isEmpty() ? 8 : 16;
            String string = null;
            if (!this.touchDragStack.isEmpty() && this.touchIsRightClickDrag) {
                object = ((ItemStack)object).copy();
                ((ItemStack)object).setCount(MathHelper.ceil((float)((ItemStack)object).getCount() / 2.0f));
            } else if (this.cursorDragging && this.cursorDragSlots.size() > 1) {
                object = ((ItemStack)object).copy();
                ((ItemStack)object).setCount(this.draggedStackRemainder);
                if (((ItemStack)object).isEmpty()) {
                    string = "" + (Object)((Object)Formatting.YELLOW) + "0";
                }
            }
            this.drawItem((ItemStack)object, mouseX - n - 8, mouseY - _snowman - _snowman3, string);
        }
        if (!this.touchDropReturningStack.isEmpty()) {
            float f = (float)(Util.getMeasuringTimeMs() - this.touchDropTime) / 100.0f;
            if (f >= 1.0f) {
                f = 1.0f;
                this.touchDropReturningStack = ItemStack.EMPTY;
            }
            _snowman3 = this.touchDropOriginSlot.x - this.touchDropX;
            int _snowman4 = this.touchDropOriginSlot.y - this.touchDropY;
            int _snowman5 = this.touchDropX + (int)((float)_snowman3 * f);
            int _snowman6 = this.touchDropY + (int)((float)_snowman4 * f);
            this.drawItem(this.touchDropReturningStack, _snowman5, _snowman6, null);
        }
        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        if (this.client.player.inventory.getCursorStack().isEmpty() && this.focusedSlot != null && this.focusedSlot.hasStack()) {
            this.renderTooltip(matrices, this.focusedSlot.getStack(), x, y);
        }
    }

    private void drawItem(ItemStack stack, int xPosition, int yPosition, String amountText) {
        RenderSystem.translatef(0.0f, 0.0f, 32.0f);
        this.setZOffset(200);
        this.itemRenderer.zOffset = 200.0f;
        this.itemRenderer.renderInGuiWithOverrides(stack, xPosition, yPosition);
        this.itemRenderer.renderGuiItemOverlay(this.textRenderer, stack, xPosition, yPosition - (this.touchDragStack.isEmpty() ? 0 : 8), amountText);
        this.setZOffset(0);
        this.itemRenderer.zOffset = 0.0f;
    }

    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 0x404040);
        this.textRenderer.draw(matrices, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 0x404040);
    }

    protected abstract void drawBackground(MatrixStack var1, float var2, int var3, int var4);

    private void drawSlot(MatrixStack matrices, Slot slot) {
        int n = slot.x;
        _snowman = slot.y;
        ItemStack _snowman2 = slot.getStack();
        boolean _snowman3 = false;
        boolean _snowman4 = slot == this.touchDragSlotStart && !this.touchDragStack.isEmpty() && !this.touchIsRightClickDrag;
        ItemStack _snowman5 = this.client.player.inventory.getCursorStack();
        String _snowman6 = null;
        if (slot == this.touchDragSlotStart && !this.touchDragStack.isEmpty() && this.touchIsRightClickDrag && !_snowman2.isEmpty()) {
            _snowman2 = _snowman2.copy();
            _snowman2.setCount(_snowman2.getCount() / 2);
        } else if (this.cursorDragging && this.cursorDragSlots.contains(slot) && !_snowman5.isEmpty()) {
            if (this.cursorDragSlots.size() == 1) {
                return;
            }
            if (ScreenHandler.canInsertItemIntoSlot(slot, _snowman5, true) && ((ScreenHandler)this.handler).canInsertIntoSlot(slot)) {
                _snowman2 = _snowman5.copy();
                _snowman3 = true;
                ScreenHandler.calculateStackSize(this.cursorDragSlots, this.heldButtonType, _snowman2, slot.getStack().isEmpty() ? 0 : slot.getStack().getCount());
                _snowman = Math.min(_snowman2.getMaxCount(), slot.getMaxItemCount(_snowman2));
                if (_snowman2.getCount() > _snowman) {
                    _snowman6 = Formatting.YELLOW.toString() + _snowman;
                    _snowman2.setCount(_snowman);
                }
            } else {
                this.cursorDragSlots.remove(slot);
                this.calculateOffset();
            }
        }
        this.setZOffset(100);
        this.itemRenderer.zOffset = 100.0f;
        if (_snowman2.isEmpty() && slot.doDrawHoveringEffect() && (_snowman = slot.getBackgroundSprite()) != null) {
            Sprite sprite = this.client.getSpriteAtlas((Identifier)_snowman.getFirst()).apply((Identifier)_snowman.getSecond());
            this.client.getTextureManager().bindTexture(sprite.getAtlas().getId());
            HandledScreen.drawSprite(matrices, n, _snowman, this.getZOffset(), 16, 16, sprite);
            _snowman4 = true;
        }
        if (!_snowman4) {
            if (_snowman3) {
                HandledScreen.fill(matrices, n, _snowman, n + 16, _snowman + 16, -2130706433);
            }
            RenderSystem.enableDepthTest();
            this.itemRenderer.renderInGuiWithOverrides(this.client.player, _snowman2, n, _snowman);
            this.itemRenderer.renderGuiItemOverlay(this.textRenderer, _snowman2, n, _snowman, _snowman6);
        }
        this.itemRenderer.zOffset = 0.0f;
        this.setZOffset(0);
    }

    private void calculateOffset() {
        ItemStack itemStack = this.client.player.inventory.getCursorStack();
        if (itemStack.isEmpty() || !this.cursorDragging) {
            return;
        }
        if (this.heldButtonType == 2) {
            this.draggedStackRemainder = itemStack.getMaxCount();
            return;
        }
        this.draggedStackRemainder = itemStack.getCount();
        for (Slot slot : this.cursorDragSlots) {
            ItemStack itemStack2 = itemStack.copy();
            _snowman = slot.getStack();
            int _snowman2 = _snowman.isEmpty() ? 0 : _snowman.getCount();
            ScreenHandler.calculateStackSize(this.cursorDragSlots, this.heldButtonType, itemStack2, _snowman2);
            int _snowman3 = Math.min(itemStack2.getMaxCount(), slot.getMaxItemCount(itemStack2));
            if (itemStack2.getCount() > _snowman3) {
                itemStack2.setCount(_snowman3);
            }
            this.draggedStackRemainder -= itemStack2.getCount() - _snowman2;
        }
    }

    @Nullable
    private Slot getSlotAt(double xPosition, double yPosition) {
        for (int i = 0; i < ((ScreenHandler)this.handler).slots.size(); ++i) {
            Slot slot = ((ScreenHandler)this.handler).slots.get(i);
            if (!this.isPointOverSlot(slot, xPosition, yPosition) || !slot.doDrawHoveringEffect()) continue;
            return slot;
        }
        return null;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        boolean bl = this.client.options.keyPickItem.matchesMouse(button);
        Slot _snowman2 = this.getSlotAt(mouseX, mouseY);
        long _snowman3 = Util.getMeasuringTimeMs();
        this.doubleClicking = this.lastClickedSlot == _snowman2 && _snowman3 - this.lastButtonClickTime < 250L && this.lastClickedButton == button;
        this.cancelNextRelease = false;
        if (button == 0 || button == 1 || bl) {
            int n = this.x;
            _snowman = this.y;
            boolean _snowman4 = this.isClickOutsideBounds(mouseX, mouseY, n, _snowman, button);
            _snowman = -1;
            if (_snowman2 != null) {
                _snowman = _snowman2.id;
            }
            if (_snowman4) {
                _snowman = -999;
            }
            if (this.client.options.touchscreen && _snowman4 && this.client.player.inventory.getCursorStack().isEmpty()) {
                this.client.openScreen(null);
                return true;
            }
            if (_snowman != -1) {
                if (this.client.options.touchscreen) {
                    if (_snowman2 != null && _snowman2.hasStack()) {
                        this.touchDragSlotStart = _snowman2;
                        this.touchDragStack = ItemStack.EMPTY;
                        this.touchIsRightClickDrag = button == 1;
                    } else {
                        this.touchDragSlotStart = null;
                    }
                } else if (!this.cursorDragging) {
                    if (this.client.player.inventory.getCursorStack().isEmpty()) {
                        if (this.client.options.keyPickItem.matchesMouse(button)) {
                            this.onMouseClick(_snowman2, _snowman, button, SlotActionType.CLONE);
                        } else {
                            boolean bl2 = _snowman != -999 && (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344));
                            SlotActionType _snowman5 = SlotActionType.PICKUP;
                            if (bl2) {
                                this.quickMovingStack = _snowman2 != null && _snowman2.hasStack() ? _snowman2.getStack().copy() : ItemStack.EMPTY;
                                _snowman5 = SlotActionType.QUICK_MOVE;
                            } else if (_snowman == -999) {
                                _snowman5 = SlotActionType.THROW;
                            }
                            this.onMouseClick(_snowman2, _snowman, button, _snowman5);
                        }
                        this.cancelNextRelease = true;
                    } else {
                        this.cursorDragging = true;
                        this.heldButtonCode = button;
                        this.cursorDragSlots.clear();
                        if (button == 0) {
                            this.heldButtonType = 0;
                        } else if (button == 1) {
                            this.heldButtonType = 1;
                        } else if (this.client.options.keyPickItem.matchesMouse(button)) {
                            this.heldButtonType = 2;
                        }
                    }
                }
            }
        } else {
            this.method_30107(button);
        }
        this.lastClickedSlot = _snowman2;
        this.lastButtonClickTime = _snowman3;
        this.lastClickedButton = button;
        return true;
    }

    private void method_30107(int n) {
        if (this.focusedSlot != null && this.client.player.inventory.getCursorStack().isEmpty()) {
            if (this.client.options.keySwapHands.matchesMouse(n)) {
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 40, SlotActionType.SWAP);
                return;
            }
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                if (!this.client.options.keysHotbar[_snowman].matchesMouse(n)) continue;
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, _snowman, SlotActionType.SWAP);
            }
        }
    }

    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        Slot slot = this.getSlotAt(mouseX, mouseY);
        ItemStack _snowman2 = this.client.player.inventory.getCursorStack();
        if (this.touchDragSlotStart != null && this.client.options.touchscreen) {
            if (button == 0 || button == 1) {
                if (this.touchDragStack.isEmpty()) {
                    if (slot != this.touchDragSlotStart && !this.touchDragSlotStart.getStack().isEmpty()) {
                        this.touchDragStack = this.touchDragSlotStart.getStack().copy();
                    }
                } else if (this.touchDragStack.getCount() > 1 && slot != null && ScreenHandler.canInsertItemIntoSlot(slot, this.touchDragStack, false)) {
                    long l = Util.getMeasuringTimeMs();
                    if (this.touchHoveredSlot == slot) {
                        if (l - this.touchDropTimer > 500L) {
                            this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, 0, SlotActionType.PICKUP);
                            this.onMouseClick(slot, slot.id, 1, SlotActionType.PICKUP);
                            this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, 0, SlotActionType.PICKUP);
                            this.touchDropTimer = l + 750L;
                            this.touchDragStack.decrement(1);
                        }
                    } else {
                        this.touchHoveredSlot = slot;
                        this.touchDropTimer = l;
                    }
                }
            }
        } else if (this.cursorDragging && slot != null && !_snowman2.isEmpty() && (_snowman2.getCount() > this.cursorDragSlots.size() || this.heldButtonType == 2) && ScreenHandler.canInsertItemIntoSlot(slot, _snowman2, true) && slot.canInsert(_snowman2) && ((ScreenHandler)this.handler).canInsertIntoSlot(slot)) {
            this.cursorDragSlots.add(slot);
            this.calculateOffset();
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Slot slot = this.getSlotAt(mouseX, mouseY);
        int _snowman2 = this.x;
        int _snowman3 = this.y;
        boolean _snowman4 = this.isClickOutsideBounds(mouseX, mouseY, _snowman2, _snowman3, button);
        int _snowman5 = -1;
        if (slot != null) {
            _snowman5 = slot.id;
        }
        if (_snowman4) {
            _snowman5 = -999;
        }
        if (this.doubleClicking && slot != null && button == 0 && ((ScreenHandler)this.handler).canInsertIntoSlot(ItemStack.EMPTY, slot)) {
            if (HandledScreen.hasShiftDown()) {
                if (!this.quickMovingStack.isEmpty()) {
                    for (Slot slot2 : ((ScreenHandler)this.handler).slots) {
                        if (slot2 == null || !slot2.canTakeItems(this.client.player) || !slot2.hasStack() || slot2.inventory != slot.inventory || !ScreenHandler.canInsertItemIntoSlot(slot2, this.quickMovingStack, true)) continue;
                        this.onMouseClick(slot2, slot2.id, button, SlotActionType.QUICK_MOVE);
                    }
                }
            } else {
                this.onMouseClick(slot, _snowman5, button, SlotActionType.PICKUP_ALL);
            }
            this.doubleClicking = false;
            this.lastButtonClickTime = 0L;
        } else {
            if (this.cursorDragging && this.heldButtonCode != button) {
                this.cursorDragging = false;
                this.cursorDragSlots.clear();
                this.cancelNextRelease = true;
                return true;
            }
            if (this.cancelNextRelease) {
                this.cancelNextRelease = false;
                return true;
            }
            if (this.touchDragSlotStart != null && this.client.options.touchscreen) {
                if (button == 0 || button == 1) {
                    if (this.touchDragStack.isEmpty() && slot != this.touchDragSlotStart) {
                        this.touchDragStack = this.touchDragSlotStart.getStack();
                    }
                    boolean bl = ScreenHandler.canInsertItemIntoSlot(slot, this.touchDragStack, false);
                    if (_snowman5 != -1 && !this.touchDragStack.isEmpty() && bl) {
                        this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, button, SlotActionType.PICKUP);
                        this.onMouseClick(slot, _snowman5, 0, SlotActionType.PICKUP);
                        if (this.client.player.inventory.getCursorStack().isEmpty()) {
                            this.touchDropReturningStack = ItemStack.EMPTY;
                        } else {
                            this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, button, SlotActionType.PICKUP);
                            this.touchDropX = MathHelper.floor(mouseX - (double)_snowman2);
                            this.touchDropY = MathHelper.floor(mouseY - (double)_snowman3);
                            this.touchDropOriginSlot = this.touchDragSlotStart;
                            this.touchDropReturningStack = this.touchDragStack;
                            this.touchDropTime = Util.getMeasuringTimeMs();
                        }
                    } else if (!this.touchDragStack.isEmpty()) {
                        this.touchDropX = MathHelper.floor(mouseX - (double)_snowman2);
                        this.touchDropY = MathHelper.floor(mouseY - (double)_snowman3);
                        this.touchDropOriginSlot = this.touchDragSlotStart;
                        this.touchDropReturningStack = this.touchDragStack;
                        this.touchDropTime = Util.getMeasuringTimeMs();
                    }
                    this.touchDragStack = ItemStack.EMPTY;
                    this.touchDragSlotStart = null;
                }
            } else if (this.cursorDragging && !this.cursorDragSlots.isEmpty()) {
                this.onMouseClick(null, -999, ScreenHandler.packQuickCraftData(0, this.heldButtonType), SlotActionType.QUICK_CRAFT);
                for (Slot slot3 : this.cursorDragSlots) {
                    this.onMouseClick(slot3, slot3.id, ScreenHandler.packQuickCraftData(1, this.heldButtonType), SlotActionType.QUICK_CRAFT);
                }
                this.onMouseClick(null, -999, ScreenHandler.packQuickCraftData(2, this.heldButtonType), SlotActionType.QUICK_CRAFT);
            } else if (!this.client.player.inventory.getCursorStack().isEmpty()) {
                if (this.client.options.keyPickItem.matchesMouse(button)) {
                    this.onMouseClick(slot, _snowman5, button, SlotActionType.CLONE);
                } else {
                    boolean bl;
                    boolean bl2 = bl = _snowman5 != -999 && (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344));
                    if (bl) {
                        this.quickMovingStack = slot != null && slot.hasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
                    }
                    this.onMouseClick(slot, _snowman5, button, bl ? SlotActionType.QUICK_MOVE : SlotActionType.PICKUP);
                }
            }
        }
        if (this.client.player.inventory.getCursorStack().isEmpty()) {
            this.lastButtonClickTime = 0L;
        }
        this.cursorDragging = false;
        return true;
    }

    private boolean isPointOverSlot(Slot slot, double pointX, double pointY) {
        return this.isPointWithinBounds(slot.x, slot.y, 16, 16, pointX, pointY);
    }

    protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX, double pointY) {
        int n = this.x;
        _snowman = this.y;
        return (pointX -= (double)n) >= (double)(xPosition - 1) && pointX < (double)(xPosition + width + 1) && (pointY -= (double)_snowman) >= (double)(yPosition - 1) && pointY < (double)(yPosition + height + 1);
    }

    protected void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType) {
        if (slot != null) {
            invSlot = slot.id;
        }
        this.client.interactionManager.clickSlot(((ScreenHandler)this.handler).syncId, invSlot, clickData, actionType, this.client.player);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (this.client.options.keyInventory.matchesKey(keyCode, scanCode)) {
            this.onClose();
            return true;
        }
        this.handleHotbarKeyPressed(keyCode, scanCode);
        if (this.focusedSlot != null && this.focusedSlot.hasStack()) {
            if (this.client.options.keyPickItem.matchesKey(keyCode, scanCode)) {
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 0, SlotActionType.CLONE);
            } else if (this.client.options.keyDrop.matchesKey(keyCode, scanCode)) {
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, HandledScreen.hasControlDown() ? 1 : 0, SlotActionType.THROW);
            }
        }
        return true;
    }

    protected boolean handleHotbarKeyPressed(int keyCode, int scanCode) {
        if (this.client.player.inventory.getCursorStack().isEmpty() && this.focusedSlot != null) {
            if (this.client.options.keySwapHands.matchesKey(keyCode, scanCode)) {
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 40, SlotActionType.SWAP);
                return true;
            }
            for (int i = 0; i < 9; ++i) {
                if (!this.client.options.keysHotbar[i].matchesKey(keyCode, scanCode)) continue;
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, i, SlotActionType.SWAP);
                return true;
            }
        }
        return false;
    }

    @Override
    public void removed() {
        if (this.client.player == null) {
            return;
        }
        ((ScreenHandler)this.handler).close(this.client.player);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.client.player.isAlive() || this.client.player.removed) {
            this.client.player.closeHandledScreen();
        }
    }

    @Override
    public T getScreenHandler() {
        return this.handler;
    }

    @Override
    public void onClose() {
        this.client.player.closeHandledScreen();
        super.onClose();
    }
}

