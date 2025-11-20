package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
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

public abstract class HandledScreen<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
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
      int _snowman = this.x;
      int _snowmanx = this.y;
      this.drawBackground(matrices, delta, mouseX, mouseY);
      RenderSystem.disableRescaleNormal();
      RenderSystem.disableDepthTest();
      super.render(matrices, mouseX, mouseY, delta);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)_snowman, (float)_snowmanx, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableRescaleNormal();
      this.focusedSlot = null;
      int _snowmanxx = 240;
      int _snowmanxxx = 240;
      RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

      for (int _snowmanxxxx = 0; _snowmanxxxx < this.handler.slots.size(); _snowmanxxxx++) {
         Slot _snowmanxxxxx = this.handler.slots.get(_snowmanxxxx);
         if (_snowmanxxxxx.doDrawHoveringEffect()) {
            this.drawSlot(matrices, _snowmanxxxxx);
         }

         if (this.isPointOverSlot(_snowmanxxxxx, (double)mouseX, (double)mouseY) && _snowmanxxxxx.doDrawHoveringEffect()) {
            this.focusedSlot = _snowmanxxxxx;
            RenderSystem.disableDepthTest();
            int _snowmanxxxxxx = _snowmanxxxxx.x;
            int _snowmanxxxxxxx = _snowmanxxxxx.y;
            RenderSystem.colorMask(true, true, true, false);
            this.fillGradient(matrices, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx + 16, _snowmanxxxxxxx + 16, -2130706433, -2130706433);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
         }
      }

      this.drawForeground(matrices, mouseX, mouseY);
      PlayerInventory _snowmanxxxx = this.client.player.inventory;
      ItemStack _snowmanxxxxxx = this.touchDragStack.isEmpty() ? _snowmanxxxx.getCursorStack() : this.touchDragStack;
      if (!_snowmanxxxxxx.isEmpty()) {
         int _snowmanxxxxxxx = 8;
         int _snowmanxxxxxxxx = this.touchDragStack.isEmpty() ? 8 : 16;
         String _snowmanxxxxxxxxx = null;
         if (!this.touchDragStack.isEmpty() && this.touchIsRightClickDrag) {
            _snowmanxxxxxx = _snowmanxxxxxx.copy();
            _snowmanxxxxxx.setCount(MathHelper.ceil((float)_snowmanxxxxxx.getCount() / 2.0F));
         } else if (this.cursorDragging && this.cursorDragSlots.size() > 1) {
            _snowmanxxxxxx = _snowmanxxxxxx.copy();
            _snowmanxxxxxx.setCount(this.draggedStackRemainder);
            if (_snowmanxxxxxx.isEmpty()) {
               _snowmanxxxxxxxxx = "" + Formatting.YELLOW + "0";
            }
         }

         this.drawItem(_snowmanxxxxxx, mouseX - _snowman - 8, mouseY - _snowmanx - _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
      }

      if (!this.touchDropReturningStack.isEmpty()) {
         float _snowmanxxxxxxx = (float)(Util.getMeasuringTimeMs() - this.touchDropTime) / 100.0F;
         if (_snowmanxxxxxxx >= 1.0F) {
            _snowmanxxxxxxx = 1.0F;
            this.touchDropReturningStack = ItemStack.EMPTY;
         }

         int _snowmanxxxxxxxx = this.touchDropOriginSlot.x - this.touchDropX;
         int _snowmanxxxxxxxxx = this.touchDropOriginSlot.y - this.touchDropY;
         int _snowmanxxxxxxxxxx = this.touchDropX + (int)((float)_snowmanxxxxxxxx * _snowmanxxxxxxx);
         int _snowmanxxxxxxxxxxx = this.touchDropY + (int)((float)_snowmanxxxxxxxxx * _snowmanxxxxxxx);
         this.drawItem(this.touchDropReturningStack, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, null);
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
      RenderSystem.translatef(0.0F, 0.0F, 32.0F);
      this.setZOffset(200);
      this.itemRenderer.zOffset = 200.0F;
      this.itemRenderer.renderInGuiWithOverrides(stack, xPosition, yPosition);
      this.itemRenderer.renderGuiItemOverlay(this.textRenderer, stack, xPosition, yPosition - (this.touchDragStack.isEmpty() ? 0 : 8), amountText);
      this.setZOffset(0);
      this.itemRenderer.zOffset = 0.0F;
   }

   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
      this.textRenderer.draw(matrices, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
   }

   protected abstract void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY);

   private void drawSlot(MatrixStack matrices, Slot slot) {
      int _snowman = slot.x;
      int _snowmanx = slot.y;
      ItemStack _snowmanxx = slot.getStack();
      boolean _snowmanxxx = false;
      boolean _snowmanxxxx = slot == this.touchDragSlotStart && !this.touchDragStack.isEmpty() && !this.touchIsRightClickDrag;
      ItemStack _snowmanxxxxx = this.client.player.inventory.getCursorStack();
      String _snowmanxxxxxx = null;
      if (slot == this.touchDragSlotStart && !this.touchDragStack.isEmpty() && this.touchIsRightClickDrag && !_snowmanxx.isEmpty()) {
         _snowmanxx = _snowmanxx.copy();
         _snowmanxx.setCount(_snowmanxx.getCount() / 2);
      } else if (this.cursorDragging && this.cursorDragSlots.contains(slot) && !_snowmanxxxxx.isEmpty()) {
         if (this.cursorDragSlots.size() == 1) {
            return;
         }

         if (ScreenHandler.canInsertItemIntoSlot(slot, _snowmanxxxxx, true) && this.handler.canInsertIntoSlot(slot)) {
            _snowmanxx = _snowmanxxxxx.copy();
            _snowmanxxx = true;
            ScreenHandler.calculateStackSize(this.cursorDragSlots, this.heldButtonType, _snowmanxx, slot.getStack().isEmpty() ? 0 : slot.getStack().getCount());
            int _snowmanxxxxxxx = Math.min(_snowmanxx.getMaxCount(), slot.getMaxItemCount(_snowmanxx));
            if (_snowmanxx.getCount() > _snowmanxxxxxxx) {
               _snowmanxxxxxx = Formatting.YELLOW.toString() + _snowmanxxxxxxx;
               _snowmanxx.setCount(_snowmanxxxxxxx);
            }
         } else {
            this.cursorDragSlots.remove(slot);
            this.calculateOffset();
         }
      }

      this.setZOffset(100);
      this.itemRenderer.zOffset = 100.0F;
      if (_snowmanxx.isEmpty() && slot.doDrawHoveringEffect()) {
         Pair<Identifier, Identifier> _snowmanxxxxxxx = slot.getBackgroundSprite();
         if (_snowmanxxxxxxx != null) {
            Sprite _snowmanxxxxxxxx = this.client.getSpriteAtlas((Identifier)_snowmanxxxxxxx.getFirst()).apply((Identifier)_snowmanxxxxxxx.getSecond());
            this.client.getTextureManager().bindTexture(_snowmanxxxxxxxx.getAtlas().getId());
            drawSprite(matrices, _snowman, _snowmanx, this.getZOffset(), 16, 16, _snowmanxxxxxxxx);
            _snowmanxxxx = true;
         }
      }

      if (!_snowmanxxxx) {
         if (_snowmanxxx) {
            fill(matrices, _snowman, _snowmanx, _snowman + 16, _snowmanx + 16, -2130706433);
         }

         RenderSystem.enableDepthTest();
         this.itemRenderer.renderInGuiWithOverrides(this.client.player, _snowmanxx, _snowman, _snowmanx);
         this.itemRenderer.renderGuiItemOverlay(this.textRenderer, _snowmanxx, _snowman, _snowmanx, _snowmanxxxxxx);
      }

      this.itemRenderer.zOffset = 0.0F;
      this.setZOffset(0);
   }

   private void calculateOffset() {
      ItemStack _snowman = this.client.player.inventory.getCursorStack();
      if (!_snowman.isEmpty() && this.cursorDragging) {
         if (this.heldButtonType == 2) {
            this.draggedStackRemainder = _snowman.getMaxCount();
         } else {
            this.draggedStackRemainder = _snowman.getCount();

            for (Slot _snowmanx : this.cursorDragSlots) {
               ItemStack _snowmanxx = _snowman.copy();
               ItemStack _snowmanxxx = _snowmanx.getStack();
               int _snowmanxxxx = _snowmanxxx.isEmpty() ? 0 : _snowmanxxx.getCount();
               ScreenHandler.calculateStackSize(this.cursorDragSlots, this.heldButtonType, _snowmanxx, _snowmanxxxx);
               int _snowmanxxxxx = Math.min(_snowmanxx.getMaxCount(), _snowmanx.getMaxItemCount(_snowmanxx));
               if (_snowmanxx.getCount() > _snowmanxxxxx) {
                  _snowmanxx.setCount(_snowmanxxxxx);
               }

               this.draggedStackRemainder = this.draggedStackRemainder - (_snowmanxx.getCount() - _snowmanxxxx);
            }
         }
      }
   }

   @Nullable
   private Slot getSlotAt(double xPosition, double yPosition) {
      for (int _snowman = 0; _snowman < this.handler.slots.size(); _snowman++) {
         Slot _snowmanx = this.handler.slots.get(_snowman);
         if (this.isPointOverSlot(_snowmanx, xPosition, yPosition) && _snowmanx.doDrawHoveringEffect()) {
            return _snowmanx;
         }
      }

      return null;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (super.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else {
         boolean _snowman = this.client.options.keyPickItem.matchesMouse(button);
         Slot _snowmanx = this.getSlotAt(mouseX, mouseY);
         long _snowmanxx = Util.getMeasuringTimeMs();
         this.doubleClicking = this.lastClickedSlot == _snowmanx && _snowmanxx - this.lastButtonClickTime < 250L && this.lastClickedButton == button;
         this.cancelNextRelease = false;
         if (button != 0 && button != 1 && !_snowman) {
            this.method_30107(button);
         } else {
            int _snowmanxxx = this.x;
            int _snowmanxxxx = this.y;
            boolean _snowmanxxxxx = this.isClickOutsideBounds(mouseX, mouseY, _snowmanxxx, _snowmanxxxx, button);
            int _snowmanxxxxxx = -1;
            if (_snowmanx != null) {
               _snowmanxxxxxx = _snowmanx.id;
            }

            if (_snowmanxxxxx) {
               _snowmanxxxxxx = -999;
            }

            if (this.client.options.touchscreen && _snowmanxxxxx && this.client.player.inventory.getCursorStack().isEmpty()) {
               this.client.openScreen(null);
               return true;
            }

            if (_snowmanxxxxxx != -1) {
               if (this.client.options.touchscreen) {
                  if (_snowmanx != null && _snowmanx.hasStack()) {
                     this.touchDragSlotStart = _snowmanx;
                     this.touchDragStack = ItemStack.EMPTY;
                     this.touchIsRightClickDrag = button == 1;
                  } else {
                     this.touchDragSlotStart = null;
                  }
               } else if (!this.cursorDragging) {
                  if (this.client.player.inventory.getCursorStack().isEmpty()) {
                     if (this.client.options.keyPickItem.matchesMouse(button)) {
                        this.onMouseClick(_snowmanx, _snowmanxxxxxx, button, SlotActionType.CLONE);
                     } else {
                        boolean _snowmanxxxxxxx = _snowmanxxxxxx != -999
                           && (
                              InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)
                                 || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344)
                           );
                        SlotActionType _snowmanxxxxxxxx = SlotActionType.PICKUP;
                        if (_snowmanxxxxxxx) {
                           this.quickMovingStack = _snowmanx != null && _snowmanx.hasStack() ? _snowmanx.getStack().copy() : ItemStack.EMPTY;
                           _snowmanxxxxxxxx = SlotActionType.QUICK_MOVE;
                        } else if (_snowmanxxxxxx == -999) {
                           _snowmanxxxxxxxx = SlotActionType.THROW;
                        }

                        this.onMouseClick(_snowmanx, _snowmanxxxxxx, button, _snowmanxxxxxxxx);
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
         }

         this.lastClickedSlot = _snowmanx;
         this.lastButtonClickTime = _snowmanxx;
         this.lastClickedButton = button;
         return true;
      }
   }

   private void method_30107(int _snowman) {
      if (this.focusedSlot != null && this.client.player.inventory.getCursorStack().isEmpty()) {
         if (this.client.options.keySwapHands.matchesMouse(_snowman)) {
            this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 40, SlotActionType.SWAP);
            return;
         }

         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            if (this.client.options.keysHotbar[_snowmanx].matchesMouse(_snowman)) {
               this.onMouseClick(this.focusedSlot, this.focusedSlot.id, _snowmanx, SlotActionType.SWAP);
            }
         }
      }
   }

   protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
      return mouseX < (double)left
         || mouseY < (double)top
         || mouseX >= (double)(left + this.backgroundWidth)
         || mouseY >= (double)(top + this.backgroundHeight);
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      Slot _snowman = this.getSlotAt(mouseX, mouseY);
      ItemStack _snowmanx = this.client.player.inventory.getCursorStack();
      if (this.touchDragSlotStart != null && this.client.options.touchscreen) {
         if (button == 0 || button == 1) {
            if (this.touchDragStack.isEmpty()) {
               if (_snowman != this.touchDragSlotStart && !this.touchDragSlotStart.getStack().isEmpty()) {
                  this.touchDragStack = this.touchDragSlotStart.getStack().copy();
               }
            } else if (this.touchDragStack.getCount() > 1 && _snowman != null && ScreenHandler.canInsertItemIntoSlot(_snowman, this.touchDragStack, false)) {
               long _snowmanxx = Util.getMeasuringTimeMs();
               if (this.touchHoveredSlot == _snowman) {
                  if (_snowmanxx - this.touchDropTimer > 500L) {
                     this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, 0, SlotActionType.PICKUP);
                     this.onMouseClick(_snowman, _snowman.id, 1, SlotActionType.PICKUP);
                     this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, 0, SlotActionType.PICKUP);
                     this.touchDropTimer = _snowmanxx + 750L;
                     this.touchDragStack.decrement(1);
                  }
               } else {
                  this.touchHoveredSlot = _snowman;
                  this.touchDropTimer = _snowmanxx;
               }
            }
         }
      } else if (this.cursorDragging
         && _snowman != null
         && !_snowmanx.isEmpty()
         && (_snowmanx.getCount() > this.cursorDragSlots.size() || this.heldButtonType == 2)
         && ScreenHandler.canInsertItemIntoSlot(_snowman, _snowmanx, true)
         && _snowman.canInsert(_snowmanx)
         && this.handler.canInsertIntoSlot(_snowman)) {
         this.cursorDragSlots.add(_snowman);
         this.calculateOffset();
      }

      return true;
   }

   @Override
   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      Slot _snowman = this.getSlotAt(mouseX, mouseY);
      int _snowmanx = this.x;
      int _snowmanxx = this.y;
      boolean _snowmanxxx = this.isClickOutsideBounds(mouseX, mouseY, _snowmanx, _snowmanxx, button);
      int _snowmanxxxx = -1;
      if (_snowman != null) {
         _snowmanxxxx = _snowman.id;
      }

      if (_snowmanxxx) {
         _snowmanxxxx = -999;
      }

      if (this.doubleClicking && _snowman != null && button == 0 && this.handler.canInsertIntoSlot(ItemStack.EMPTY, _snowman)) {
         if (hasShiftDown()) {
            if (!this.quickMovingStack.isEmpty()) {
               for (Slot _snowmanxxxxx : this.handler.slots) {
                  if (_snowmanxxxxx != null
                     && _snowmanxxxxx.canTakeItems(this.client.player)
                     && _snowmanxxxxx.hasStack()
                     && _snowmanxxxxx.inventory == _snowman.inventory
                     && ScreenHandler.canInsertItemIntoSlot(_snowmanxxxxx, this.quickMovingStack, true)) {
                     this.onMouseClick(_snowmanxxxxx, _snowmanxxxxx.id, button, SlotActionType.QUICK_MOVE);
                  }
               }
            }
         } else {
            this.onMouseClick(_snowman, _snowmanxxxx, button, SlotActionType.PICKUP_ALL);
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
               if (this.touchDragStack.isEmpty() && _snowman != this.touchDragSlotStart) {
                  this.touchDragStack = this.touchDragSlotStart.getStack();
               }

               boolean _snowmanxxxxxx = ScreenHandler.canInsertItemIntoSlot(_snowman, this.touchDragStack, false);
               if (_snowmanxxxx != -1 && !this.touchDragStack.isEmpty() && _snowmanxxxxxx) {
                  this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, button, SlotActionType.PICKUP);
                  this.onMouseClick(_snowman, _snowmanxxxx, 0, SlotActionType.PICKUP);
                  if (this.client.player.inventory.getCursorStack().isEmpty()) {
                     this.touchDropReturningStack = ItemStack.EMPTY;
                  } else {
                     this.onMouseClick(this.touchDragSlotStart, this.touchDragSlotStart.id, button, SlotActionType.PICKUP);
                     this.touchDropX = MathHelper.floor(mouseX - (double)_snowmanx);
                     this.touchDropY = MathHelper.floor(mouseY - (double)_snowmanxx);
                     this.touchDropOriginSlot = this.touchDragSlotStart;
                     this.touchDropReturningStack = this.touchDragStack;
                     this.touchDropTime = Util.getMeasuringTimeMs();
                  }
               } else if (!this.touchDragStack.isEmpty()) {
                  this.touchDropX = MathHelper.floor(mouseX - (double)_snowmanx);
                  this.touchDropY = MathHelper.floor(mouseY - (double)_snowmanxx);
                  this.touchDropOriginSlot = this.touchDragSlotStart;
                  this.touchDropReturningStack = this.touchDragStack;
                  this.touchDropTime = Util.getMeasuringTimeMs();
               }

               this.touchDragStack = ItemStack.EMPTY;
               this.touchDragSlotStart = null;
            }
         } else if (this.cursorDragging && !this.cursorDragSlots.isEmpty()) {
            this.onMouseClick(null, -999, ScreenHandler.packQuickCraftData(0, this.heldButtonType), SlotActionType.QUICK_CRAFT);

            for (Slot _snowmanxxxxxx : this.cursorDragSlots) {
               this.onMouseClick(_snowmanxxxxxx, _snowmanxxxxxx.id, ScreenHandler.packQuickCraftData(1, this.heldButtonType), SlotActionType.QUICK_CRAFT);
            }

            this.onMouseClick(null, -999, ScreenHandler.packQuickCraftData(2, this.heldButtonType), SlotActionType.QUICK_CRAFT);
         } else if (!this.client.player.inventory.getCursorStack().isEmpty()) {
            if (this.client.options.keyPickItem.matchesMouse(button)) {
               this.onMouseClick(_snowman, _snowmanxxxx, button, SlotActionType.CLONE);
            } else {
               boolean _snowmanxxxxxx = _snowmanxxxx != -999
                  && (
                     InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)
                        || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344)
                  );
               if (_snowmanxxxxxx) {
                  this.quickMovingStack = _snowman != null && _snowman.hasStack() ? _snowman.getStack().copy() : ItemStack.EMPTY;
               }

               this.onMouseClick(_snowman, _snowmanxxxx, button, _snowmanxxxxxx ? SlotActionType.QUICK_MOVE : SlotActionType.PICKUP);
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
      int _snowman = this.x;
      int _snowmanx = this.y;
      pointX -= (double)_snowman;
      pointY -= (double)_snowmanx;
      return pointX >= (double)(xPosition - 1)
         && pointX < (double)(xPosition + width + 1)
         && pointY >= (double)(yPosition - 1)
         && pointY < (double)(yPosition + height + 1);
   }

   protected void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType) {
      if (slot != null) {
         invSlot = slot.id;
      }

      this.client.interactionManager.clickSlot(this.handler.syncId, invSlot, clickData, actionType, this.client.player);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (this.client.options.keyInventory.matchesKey(keyCode, scanCode)) {
         this.onClose();
         return true;
      } else {
         this.handleHotbarKeyPressed(keyCode, scanCode);
         if (this.focusedSlot != null && this.focusedSlot.hasStack()) {
            if (this.client.options.keyPickItem.matchesKey(keyCode, scanCode)) {
               this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 0, SlotActionType.CLONE);
            } else if (this.client.options.keyDrop.matchesKey(keyCode, scanCode)) {
               this.onMouseClick(this.focusedSlot, this.focusedSlot.id, hasControlDown() ? 1 : 0, SlotActionType.THROW);
            }
         }

         return true;
      }
   }

   protected boolean handleHotbarKeyPressed(int keyCode, int scanCode) {
      if (this.client.player.inventory.getCursorStack().isEmpty() && this.focusedSlot != null) {
         if (this.client.options.keySwapHands.matchesKey(keyCode, scanCode)) {
            this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 40, SlotActionType.SWAP);
            return true;
         }

         for (int _snowman = 0; _snowman < 9; _snowman++) {
            if (this.client.options.keysHotbar[_snowman].matchesKey(keyCode, scanCode)) {
               this.onMouseClick(this.focusedSlot, this.focusedSlot.id, _snowman, SlotActionType.SWAP);
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void removed() {
      if (this.client.player != null) {
         this.handler.close(this.client.player);
      }
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
