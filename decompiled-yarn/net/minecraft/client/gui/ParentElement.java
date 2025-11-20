package net.minecraft.client.gui;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public interface ParentElement extends Element {
   List<? extends Element> children();

   default Optional<Element> hoveredElement(double mouseX, double mouseY) {
      for (Element _snowman : this.children()) {
         if (_snowman.isMouseOver(mouseX, mouseY)) {
            return Optional.of(_snowman);
         }
      }

      return Optional.empty();
   }

   @Override
   default boolean mouseClicked(double mouseX, double mouseY, int button) {
      for (Element _snowman : this.children()) {
         if (_snowman.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(_snowman);
            if (button == 0) {
               this.setDragging(true);
            }

            return true;
         }
      }

      return false;
   }

   @Override
   default boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.setDragging(false);
      return this.hoveredElement(mouseX, mouseY).filter(_snowmanxxx -> _snowmanxxx.mouseReleased(mouseX, mouseY, button)).isPresent();
   }

   @Override
   default boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      return this.getFocused() != null && this.isDragging() && button == 0 ? this.getFocused().mouseDragged(mouseX, mouseY, button, deltaX, deltaY) : false;
   }

   boolean isDragging();

   void setDragging(boolean dragging);

   @Override
   default boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      return this.hoveredElement(mouseX, mouseY).filter(_snowmanxxx -> _snowmanxxx.mouseScrolled(mouseX, mouseY, amount)).isPresent();
   }

   @Override
   default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.getFocused() != null && this.getFocused().keyPressed(keyCode, scanCode, modifiers);
   }

   @Override
   default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return this.getFocused() != null && this.getFocused().keyReleased(keyCode, scanCode, modifiers);
   }

   @Override
   default boolean charTyped(char chr, int keyCode) {
      return this.getFocused() != null && this.getFocused().charTyped(chr, keyCode);
   }

   @Nullable
   Element getFocused();

   void setFocused(@Nullable Element focused);

   default void setInitialFocus(@Nullable Element element) {
      this.setFocused(element);
      element.changeFocus(true);
   }

   default void focusOn(@Nullable Element element) {
      this.setFocused(element);
   }

   @Override
   default boolean changeFocus(boolean lookForwards) {
      Element _snowman = this.getFocused();
      boolean _snowmanx = _snowman != null;
      if (_snowmanx && _snowman.changeFocus(lookForwards)) {
         return true;
      } else {
         List<? extends Element> _snowmanxx = this.children();
         int _snowmanxxx = _snowmanxx.indexOf(_snowman);
         int _snowmanxxxx;
         if (_snowmanx && _snowmanxxx >= 0) {
            _snowmanxxxx = _snowmanxxx + (lookForwards ? 1 : 0);
         } else if (lookForwards) {
            _snowmanxxxx = 0;
         } else {
            _snowmanxxxx = _snowmanxx.size();
         }

         ListIterator<? extends Element> _snowmanxxxxx = _snowmanxx.listIterator(_snowmanxxxx);
         BooleanSupplier _snowmanxxxxxx = lookForwards ? _snowmanxxxxx::hasNext : _snowmanxxxxx::hasPrevious;
         Supplier<? extends Element> _snowmanxxxxxxx = lookForwards ? _snowmanxxxxx::next : _snowmanxxxxx::previous;

         while (_snowmanxxxxxx.getAsBoolean()) {
            Element _snowmanxxxxxxxx = _snowmanxxxxxxx.get();
            if (_snowmanxxxxxxxx.changeFocus(lookForwards)) {
               this.setFocused(_snowmanxxxxxxxx);
               return true;
            }
         }

         this.setFocused(null);
         return false;
      }
   }
}
