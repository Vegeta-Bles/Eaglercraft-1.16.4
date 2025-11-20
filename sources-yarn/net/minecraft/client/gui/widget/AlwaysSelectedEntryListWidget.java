package net.minecraft.client.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public abstract class AlwaysSelectedEntryListWidget<E extends EntryListWidget.Entry<E>> extends EntryListWidget<E> {
   private boolean inFocus;

   public AlwaysSelectedEntryListWidget(MinecraftClient arg, int i, int j, int k, int l, int m) {
      super(arg, i, j, k, l, m);
   }

   @Override
   public boolean changeFocus(boolean lookForwards) {
      if (!this.inFocus && this.getItemCount() == 0) {
         return false;
      } else {
         this.inFocus = !this.inFocus;
         if (this.inFocus && this.getSelected() == null && this.getItemCount() > 0) {
            this.moveSelection(EntryListWidget.MoveDirection.DOWN);
         } else if (this.inFocus && this.getSelected() != null) {
            this.method_30015();
         }

         return this.inFocus;
      }
   }

   @Environment(EnvType.CLIENT)
   public abstract static class Entry<E extends AlwaysSelectedEntryListWidget.Entry<E>> extends EntryListWidget.Entry<E> {
      public Entry() {
      }

      @Override
      public boolean changeFocus(boolean lookForwards) {
         return false;
      }
   }
}
