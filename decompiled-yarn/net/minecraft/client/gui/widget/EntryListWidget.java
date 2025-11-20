package net.minecraft.client.gui.widget;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public abstract class EntryListWidget<E extends EntryListWidget.Entry<E>> extends AbstractParentElement implements Drawable {
   protected final MinecraftClient client;
   protected final int itemHeight;
   private final List<E> children = new EntryListWidget.Entries();
   protected int width;
   protected int height;
   protected int top;
   protected int bottom;
   protected int right;
   protected int left;
   protected boolean centerListVertically = true;
   private double scrollAmount;
   private boolean renderSelection = true;
   private boolean renderHeader;
   protected int headerHeight;
   private boolean scrolling;
   private E selected;
   private boolean field_26846 = true;
   private boolean field_26847 = true;

   public EntryListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
      this.client = client;
      this.width = width;
      this.height = height;
      this.top = top;
      this.bottom = bottom;
      this.itemHeight = itemHeight;
      this.left = 0;
      this.right = width;
   }

   public void setRenderSelection(boolean renderSelection) {
      this.renderSelection = renderSelection;
   }

   protected void setRenderHeader(boolean renderHeader, int headerHeight) {
      this.renderHeader = renderHeader;
      this.headerHeight = headerHeight;
      if (!renderHeader) {
         this.headerHeight = 0;
      }
   }

   public int getRowWidth() {
      return 220;
   }

   @Nullable
   public E getSelected() {
      return this.selected;
   }

   public void setSelected(@Nullable E entry) {
      this.selected = entry;
   }

   public void method_31322(boolean _snowman) {
      this.field_26846 = _snowman;
   }

   public void method_31323(boolean _snowman) {
      this.field_26847 = _snowman;
   }

   @Nullable
   public E getFocused() {
      return (E)super.getFocused();
   }

   @Override
   public final List<E> children() {
      return this.children;
   }

   protected final void clearEntries() {
      this.children.clear();
   }

   protected void replaceEntries(Collection<E> newEntries) {
      this.children.clear();
      this.children.addAll(newEntries);
   }

   protected E getEntry(int index) {
      return this.children().get(index);
   }

   protected int addEntry(E entry) {
      this.children.add(entry);
      return this.children.size() - 1;
   }

   protected int getItemCount() {
      return this.children().size();
   }

   protected boolean isSelectedItem(int index) {
      return Objects.equals(this.getSelected(), this.children().get(index));
   }

   @Nullable
   protected final E getEntryAtPosition(double x, double y) {
      int _snowman = this.getRowWidth() / 2;
      int _snowmanx = this.left + this.width / 2;
      int _snowmanxx = _snowmanx - _snowman;
      int _snowmanxxx = _snowmanx + _snowman;
      int _snowmanxxxx = MathHelper.floor(y - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
      int _snowmanxxxxx = _snowmanxxxx / this.itemHeight;
      return x < (double)this.getScrollbarPositionX() && x >= (double)_snowmanxx && x <= (double)_snowmanxxx && _snowmanxxxxx >= 0 && _snowmanxxxx >= 0 && _snowmanxxxxx < this.getItemCount()
         ? this.children().get(_snowmanxxxxx)
         : null;
   }

   public void updateSize(int width, int height, int top, int bottom) {
      this.width = width;
      this.height = height;
      this.top = top;
      this.bottom = bottom;
      this.left = 0;
      this.right = width;
   }

   public void setLeftPos(int left) {
      this.left = left;
      this.right = left + this.width;
   }

   protected int getMaxPosition() {
      return this.getItemCount() * this.itemHeight + this.headerHeight;
   }

   protected void clickedHeader(int x, int y) {
   }

   protected void renderHeader(MatrixStack matrices, int x, int y, Tessellator _snowman) {
   }

   protected void renderBackground(MatrixStack matrices) {
   }

   protected void renderDecorations(MatrixStack _snowman, int _snowman, int _snowman) {
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      int _snowman = this.getScrollbarPositionX();
      int _snowmanx = _snowman + 6;
      Tessellator _snowmanxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxx = _snowmanxx.getBuffer();
      if (this.field_26846) {
         this.client.getTextureManager().bindTexture(DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float _snowmanxxxx = 32.0F;
         _snowmanxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
         _snowmanxxx.vertex((double)this.left, (double)this.bottom, 0.0)
            .texture((float)this.left / 32.0F, (float)(this.bottom + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .next();
         _snowmanxxx.vertex((double)this.right, (double)this.bottom, 0.0)
            .texture((float)this.right / 32.0F, (float)(this.bottom + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .next();
         _snowmanxxx.vertex((double)this.right, (double)this.top, 0.0)
            .texture((float)this.right / 32.0F, (float)(this.top + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .next();
         _snowmanxxx.vertex((double)this.left, (double)this.top, 0.0)
            .texture((float)this.left / 32.0F, (float)(this.top + (int)this.getScrollAmount()) / 32.0F)
            .color(32, 32, 32, 255)
            .next();
         _snowmanxx.draw();
      }

      int _snowmanxxxx = this.getRowLeft();
      int _snowmanxxxxx = this.top + 4 - (int)this.getScrollAmount();
      if (this.renderHeader) {
         this.renderHeader(matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxx);
      }

      this.renderList(matrices, _snowmanxxxx, _snowmanxxxxx, mouseX, mouseY, delta);
      if (this.field_26847) {
         this.client.getTextureManager().bindTexture(DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
         RenderSystem.enableDepthTest();
         RenderSystem.depthFunc(519);
         float _snowmanxxxxxx = 32.0F;
         int _snowmanxxxxxxx = -100;
         _snowmanxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
         _snowmanxxx.vertex((double)this.left, (double)this.top, -100.0).texture(0.0F, (float)this.top / 32.0F).color(64, 64, 64, 255).next();
         _snowmanxxx.vertex((double)(this.left + this.width), (double)this.top, -100.0)
            .texture((float)this.width / 32.0F, (float)this.top / 32.0F)
            .color(64, 64, 64, 255)
            .next();
         _snowmanxxx.vertex((double)(this.left + this.width), 0.0, -100.0).texture((float)this.width / 32.0F, 0.0F).color(64, 64, 64, 255).next();
         _snowmanxxx.vertex((double)this.left, 0.0, -100.0).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
         _snowmanxxx.vertex((double)this.left, (double)this.height, -100.0).texture(0.0F, (float)this.height / 32.0F).color(64, 64, 64, 255).next();
         _snowmanxxx.vertex((double)(this.left + this.width), (double)this.height, -100.0)
            .texture((float)this.width / 32.0F, (float)this.height / 32.0F)
            .color(64, 64, 64, 255)
            .next();
         _snowmanxxx.vertex((double)(this.left + this.width), (double)this.bottom, -100.0)
            .texture((float)this.width / 32.0F, (float)this.bottom / 32.0F)
            .color(64, 64, 64, 255)
            .next();
         _snowmanxxx.vertex((double)this.left, (double)this.bottom, -100.0).texture(0.0F, (float)this.bottom / 32.0F).color(64, 64, 64, 255).next();
         _snowmanxx.draw();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE
         );
         RenderSystem.disableAlphaTest();
         RenderSystem.shadeModel(7425);
         RenderSystem.disableTexture();
         int _snowmanxxxxxxxx = 4;
         _snowmanxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
         _snowmanxxx.vertex((double)this.left, (double)(this.top + 4), 0.0).texture(0.0F, 1.0F).color(0, 0, 0, 0).next();
         _snowmanxxx.vertex((double)this.right, (double)(this.top + 4), 0.0).texture(1.0F, 1.0F).color(0, 0, 0, 0).next();
         _snowmanxxx.vertex((double)this.right, (double)this.top, 0.0).texture(1.0F, 0.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)this.left, (double)this.top, 0.0).texture(0.0F, 0.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)this.left, (double)this.bottom, 0.0).texture(0.0F, 1.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)this.right, (double)this.bottom, 0.0).texture(1.0F, 1.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)this.right, (double)(this.bottom - 4), 0.0).texture(1.0F, 0.0F).color(0, 0, 0, 0).next();
         _snowmanxxx.vertex((double)this.left, (double)(this.bottom - 4), 0.0).texture(0.0F, 0.0F).color(0, 0, 0, 0).next();
         _snowmanxx.draw();
      }

      int _snowmanxxxxxx = this.getMaxScroll();
      if (_snowmanxxxxxx > 0) {
         RenderSystem.disableTexture();
         int _snowmanxxxxxxx = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getMaxPosition());
         _snowmanxxxxxxx = MathHelper.clamp(_snowmanxxxxxxx, 32, this.bottom - this.top - 8);
         int _snowmanxxxxxxxx = (int)this.getScrollAmount() * (this.bottom - this.top - _snowmanxxxxxxx) / _snowmanxxxxxx + this.top;
         if (_snowmanxxxxxxxx < this.top) {
            _snowmanxxxxxxxx = this.top;
         }

         _snowmanxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
         _snowmanxxx.vertex((double)_snowman, (double)this.bottom, 0.0).texture(0.0F, 1.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)_snowmanx, (double)this.bottom, 0.0).texture(1.0F, 1.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)_snowmanx, (double)this.top, 0.0).texture(1.0F, 0.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)_snowman, (double)this.top, 0.0).texture(0.0F, 0.0F).color(0, 0, 0, 255).next();
         _snowmanxxx.vertex((double)_snowman, (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx), 0.0).texture(0.0F, 1.0F).color(128, 128, 128, 255).next();
         _snowmanxxx.vertex((double)_snowmanx, (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx), 0.0).texture(1.0F, 1.0F).color(128, 128, 128, 255).next();
         _snowmanxxx.vertex((double)_snowmanx, (double)_snowmanxxxxxxxx, 0.0).texture(1.0F, 0.0F).color(128, 128, 128, 255).next();
         _snowmanxxx.vertex((double)_snowman, (double)_snowmanxxxxxxxx, 0.0).texture(0.0F, 0.0F).color(128, 128, 128, 255).next();
         _snowmanxxx.vertex((double)_snowman, (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx - 1), 0.0).texture(0.0F, 1.0F).color(192, 192, 192, 255).next();
         _snowmanxxx.vertex((double)(_snowmanx - 1), (double)(_snowmanxxxxxxxx + _snowmanxxxxxxx - 1), 0.0).texture(1.0F, 1.0F).color(192, 192, 192, 255).next();
         _snowmanxxx.vertex((double)(_snowmanx - 1), (double)_snowmanxxxxxxxx, 0.0).texture(1.0F, 0.0F).color(192, 192, 192, 255).next();
         _snowmanxxx.vertex((double)_snowman, (double)_snowmanxxxxxxxx, 0.0).texture(0.0F, 0.0F).color(192, 192, 192, 255).next();
         _snowmanxx.draw();
      }

      this.renderDecorations(matrices, mouseX, mouseY);
      RenderSystem.enableTexture();
      RenderSystem.shadeModel(7424);
      RenderSystem.enableAlphaTest();
      RenderSystem.disableBlend();
   }

   protected void centerScrollOn(E entry) {
      this.setScrollAmount((double)(this.children().indexOf(entry) * this.itemHeight + this.itemHeight / 2 - (this.bottom - this.top) / 2));
   }

   protected void ensureVisible(E entry) {
      int _snowman = this.getRowTop(this.children().indexOf(entry));
      int _snowmanx = _snowman - this.top - 4 - this.itemHeight;
      if (_snowmanx < 0) {
         this.scroll(_snowmanx);
      }

      int _snowmanxx = this.bottom - _snowman - this.itemHeight - this.itemHeight;
      if (_snowmanxx < 0) {
         this.scroll(-_snowmanxx);
      }
   }

   private void scroll(int amount) {
      this.setScrollAmount(this.getScrollAmount() + (double)amount);
   }

   public double getScrollAmount() {
      return this.scrollAmount;
   }

   public void setScrollAmount(double amount) {
      this.scrollAmount = MathHelper.clamp(amount, 0.0, (double)this.getMaxScroll());
   }

   public int getMaxScroll() {
      return Math.max(0, this.getMaxPosition() - (this.bottom - this.top - 4));
   }

   protected void updateScrollingState(double mouseX, double mouseY, int button) {
      this.scrolling = button == 0 && mouseX >= (double)this.getScrollbarPositionX() && mouseX < (double)(this.getScrollbarPositionX() + 6);
   }

   protected int getScrollbarPositionX() {
      return this.width / 2 + 124;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.updateScrollingState(mouseX, mouseY, button);
      if (!this.isMouseOver(mouseX, mouseY)) {
         return false;
      } else {
         E _snowman = this.getEntryAtPosition(mouseX, mouseY);
         if (_snowman != null) {
            if (_snowman.mouseClicked(mouseX, mouseY, button)) {
               this.setFocused(_snowman);
               this.setDragging(true);
               return true;
            }
         } else if (button == 0) {
            this.clickedHeader(
               (int)(mouseX - (double)(this.left + this.width / 2 - this.getRowWidth() / 2)),
               (int)(mouseY - (double)this.top) + (int)this.getScrollAmount() - 4
            );
            return true;
         }

         return this.scrolling;
      }
   }

   @Override
   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.getFocused() != null) {
         this.getFocused().mouseReleased(mouseX, mouseY, button);
      }

      return false;
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
         return true;
      } else if (button == 0 && this.scrolling) {
         if (mouseY < (double)this.top) {
            this.setScrollAmount(0.0);
         } else if (mouseY > (double)this.bottom) {
            this.setScrollAmount((double)this.getMaxScroll());
         } else {
            double _snowman = (double)Math.max(1, this.getMaxScroll());
            int _snowmanx = this.bottom - this.top;
            int _snowmanxx = MathHelper.clamp((int)((float)(_snowmanx * _snowmanx) / (float)this.getMaxPosition()), 32, _snowmanx - 8);
            double _snowmanxxx = Math.max(1.0, _snowman / (double)(_snowmanx - _snowmanxx));
            this.setScrollAmount(this.getScrollAmount() + deltaY * _snowmanxxx);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      this.setScrollAmount(this.getScrollAmount() - amount * (double)this.itemHeight / 2.0);
      return true;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (keyCode == 264) {
         this.moveSelection(EntryListWidget.MoveDirection.DOWN);
         return true;
      } else if (keyCode == 265) {
         this.moveSelection(EntryListWidget.MoveDirection.UP);
         return true;
      } else {
         return false;
      }
   }

   protected void moveSelection(EntryListWidget.MoveDirection direction) {
      this.moveSelectionIf(direction, entry -> true);
   }

   protected void method_30015() {
      E _snowman = this.getSelected();
      if (_snowman != null) {
         this.setSelected(_snowman);
         this.ensureVisible(_snowman);
      }
   }

   protected void moveSelectionIf(EntryListWidget.MoveDirection direction, Predicate<E> _snowman) {
      int _snowmanx = direction == EntryListWidget.MoveDirection.UP ? -1 : 1;
      if (!this.children().isEmpty()) {
         int _snowmanxx = this.children().indexOf(this.getSelected());

         while (true) {
            int _snowmanxxx = MathHelper.clamp(_snowmanxx + _snowmanx, 0, this.getItemCount() - 1);
            if (_snowmanxx == _snowmanxxx) {
               break;
            }

            E _snowmanxxxx = this.children().get(_snowmanxxx);
            if (_snowman.test(_snowmanxxxx)) {
               this.setSelected(_snowmanxxxx);
               this.ensureVisible(_snowmanxxxx);
               break;
            }

            _snowmanxx = _snowmanxxx;
         }
      }
   }

   @Override
   public boolean isMouseOver(double mouseX, double mouseY) {
      return mouseY >= (double)this.top && mouseY <= (double)this.bottom && mouseX >= (double)this.left && mouseX <= (double)this.right;
   }

   protected void renderList(MatrixStack matrices, int x, int y, int mouseX, int mouseY, float delta) {
      int _snowman = this.getItemCount();
      Tessellator _snowmanx = Tessellator.getInstance();
      BufferBuilder _snowmanxx = _snowmanx.getBuffer();

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman; _snowmanxxx++) {
         int _snowmanxxxx = this.getRowTop(_snowmanxxx);
         int _snowmanxxxxx = this.getRowBottom(_snowmanxxx);
         if (_snowmanxxxxx >= this.top && _snowmanxxxx <= this.bottom) {
            int _snowmanxxxxxx = y + _snowmanxxx * this.itemHeight + this.headerHeight;
            int _snowmanxxxxxxx = this.itemHeight - 4;
            E _snowmanxxxxxxxx = this.getEntry(_snowmanxxx);
            int _snowmanxxxxxxxxx = this.getRowWidth();
            if (this.renderSelection && this.isSelectedItem(_snowmanxxx)) {
               int _snowmanxxxxxxxxxx = this.left + this.width / 2 - _snowmanxxxxxxxxx / 2;
               int _snowmanxxxxxxxxxxx = this.left + this.width / 2 + _snowmanxxxxxxxxx / 2;
               RenderSystem.disableTexture();
               float _snowmanxxxxxxxxxxxx = this.isFocused() ? 1.0F : 0.5F;
               RenderSystem.color4f(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1.0F);
               _snowmanxx.begin(7, VertexFormats.POSITION);
               _snowmanxx.vertex((double)_snowmanxxxxxxxxxx, (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 2), 0.0).next();
               _snowmanxx.vertex((double)_snowmanxxxxxxxxxxx, (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 2), 0.0).next();
               _snowmanxx.vertex((double)_snowmanxxxxxxxxxxx, (double)(_snowmanxxxxxx - 2), 0.0).next();
               _snowmanxx.vertex((double)_snowmanxxxxxxxxxx, (double)(_snowmanxxxxxx - 2), 0.0).next();
               _snowmanx.draw();
               RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
               _snowmanxx.begin(7, VertexFormats.POSITION);
               _snowmanxx.vertex((double)(_snowmanxxxxxxxxxx + 1), (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 1), 0.0).next();
               _snowmanxx.vertex((double)(_snowmanxxxxxxxxxxx - 1), (double)(_snowmanxxxxxx + _snowmanxxxxxxx + 1), 0.0).next();
               _snowmanxx.vertex((double)(_snowmanxxxxxxxxxxx - 1), (double)(_snowmanxxxxxx - 1), 0.0).next();
               _snowmanxx.vertex((double)(_snowmanxxxxxxxxxx + 1), (double)(_snowmanxxxxxx - 1), 0.0).next();
               _snowmanx.draw();
               RenderSystem.enableTexture();
            }

            int _snowmanxxxxxxxxxx = this.getRowLeft();
            _snowmanxxxxxxxx.render(
               matrices,
               _snowmanxxx,
               _snowmanxxxx,
               _snowmanxxxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowmanxxxxxxx,
               mouseX,
               mouseY,
               this.isMouseOver((double)mouseX, (double)mouseY) && Objects.equals(this.getEntryAtPosition((double)mouseX, (double)mouseY), _snowmanxxxxxxxx),
               delta
            );
         }
      }
   }

   public int getRowLeft() {
      return this.left + this.width / 2 - this.getRowWidth() / 2 + 2;
   }

   public int method_31383() {
      return this.getRowLeft() + this.getRowWidth();
   }

   protected int getRowTop(int index) {
      return this.top + 4 - (int)this.getScrollAmount() + index * this.itemHeight + this.headerHeight;
   }

   private int getRowBottom(int index) {
      return this.getRowTop(index) + this.itemHeight;
   }

   protected boolean isFocused() {
      return false;
   }

   protected E remove(int index) {
      E _snowman = this.children.get(index);
      return this.removeEntry(this.children.get(index)) ? _snowman : null;
   }

   protected boolean removeEntry(E entry) {
      boolean _snowman = this.children.remove(entry);
      if (_snowman && entry == this.getSelected()) {
         this.setSelected(null);
      }

      return _snowman;
   }

   private void method_29621(EntryListWidget.Entry<E> _snowman) {
      _snowman.list = this;
   }

   class Entries extends AbstractList<E> {
      private final List<E> entries = Lists.newArrayList();

      private Entries() {
      }

      public E get(int _snowman) {
         return this.entries.get(_snowman);
      }

      @Override
      public int size() {
         return this.entries.size();
      }

      public E set(int _snowman, E _snowman) {
         E _snowmanxx = this.entries.set(_snowman, _snowman);
         EntryListWidget.this.method_29621(_snowman);
         return _snowmanxx;
      }

      public void add(int _snowman, E _snowman) {
         this.entries.add(_snowman, _snowman);
         EntryListWidget.this.method_29621(_snowman);
      }

      public E remove(int _snowman) {
         return this.entries.remove(_snowman);
      }
   }

   public abstract static class Entry<E extends EntryListWidget.Entry<E>> implements Element {
      @Deprecated
      private EntryListWidget<E> list;

      public Entry() {
      }

      public abstract void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      );

      @Override
      public boolean isMouseOver(double mouseX, double mouseY) {
         return Objects.equals(this.list.getEntryAtPosition(mouseX, mouseY), this);
      }
   }

   public static enum MoveDirection {
      UP,
      DOWN;

      private MoveDirection() {
      }
   }
}
