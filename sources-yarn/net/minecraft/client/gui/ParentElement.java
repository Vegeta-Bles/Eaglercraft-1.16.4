package net.minecraft.client.gui;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ParentElement extends Element {
   List<? extends Element> children();

   default Optional<Element> hoveredElement(double mouseX, double mouseY) {
      for (Element lv : this.children()) {
         if (lv.isMouseOver(mouseX, mouseY)) {
            return Optional.of(lv);
         }
      }

      return Optional.empty();
   }

   @Override
   default boolean mouseClicked(double mouseX, double mouseY, int button) {
      for (Element lv : this.children()) {
         if (lv.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(lv);
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
      return this.hoveredElement(mouseX, mouseY).filter(arg -> arg.mouseReleased(mouseX, mouseY, button)).isPresent();
   }

   @Override
   default boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      return this.getFocused() != null && this.isDragging() && button == 0 ? this.getFocused().mouseDragged(mouseX, mouseY, button, deltaX, deltaY) : false;
   }

   boolean isDragging();

   void setDragging(boolean dragging);

   @Override
   default boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      return this.hoveredElement(mouseX, mouseY).filter(arg -> arg.mouseScrolled(mouseX, mouseY, amount)).isPresent();
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
      Element lv = this.getFocused();
      boolean bl2 = lv != null;
      if (bl2 && lv.changeFocus(lookForwards)) {
         return true;
      } else {
         List<? extends Element> list = this.children();
         int i = list.indexOf(lv);
         int j;
         if (bl2 && i >= 0) {
            j = i + (lookForwards ? 1 : 0);
         } else if (lookForwards) {
            j = 0;
         } else {
            j = list.size();
         }

         ListIterator<? extends Element> listIterator = list.listIterator(j);
         BooleanSupplier booleanSupplier = lookForwards ? listIterator::hasNext : listIterator::hasPrevious;
         Supplier<? extends Element> supplier = lookForwards ? listIterator::next : listIterator::previous;

         while (booleanSupplier.getAsBoolean()) {
            Element lv2 = supplier.get();
            if (lv2.changeFocus(lookForwards)) {
               this.setFocused(lv2);
               return true;
            }
         }

         this.setFocused(null);
         return false;
      }
   }
}
