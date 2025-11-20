package net.minecraft.client.gui.widget;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;

public abstract class ElementListWidget<E extends ElementListWidget.Entry<E>> extends EntryListWidget<E> {
   public ElementListWidget(MinecraftClient _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean changeFocus(boolean lookForwards) {
      boolean _snowman = super.changeFocus(lookForwards);
      if (_snowman) {
         this.ensureVisible(this.getFocused());
      }

      return _snowman;
   }

   @Override
   protected boolean isSelectedItem(int index) {
      return false;
   }

   public abstract static class Entry<E extends ElementListWidget.Entry<E>> extends EntryListWidget.Entry<E> implements ParentElement {
      @Nullable
      private Element focused;
      private boolean dragging;

      public Entry() {
      }

      @Override
      public boolean isDragging() {
         return this.dragging;
      }

      @Override
      public void setDragging(boolean dragging) {
         this.dragging = dragging;
      }

      @Override
      public void setFocused(@Nullable Element focused) {
         this.focused = focused;
      }

      @Nullable
      @Override
      public Element getFocused() {
         return this.focused;
      }
   }
}
