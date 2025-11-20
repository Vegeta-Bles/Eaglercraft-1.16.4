package net.minecraft.client.realms.gui.screen;

import java.util.List;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.util.math.MatrixStack;

public abstract class RealmsAcceptRejectButton {
   public final int width;
   public final int height;
   public final int x;
   public final int y;

   public RealmsAcceptRejectButton(int width, int height, int x, int y) {
      this.width = width;
      this.height = height;
      this.x = x;
      this.y = y;
   }

   public void render(MatrixStack matrices, int _snowman, int _snowman, int _snowman, int _snowman) {
      int _snowmanxxxx = _snowman + this.x;
      int _snowmanxxxxx = _snowman + this.y;
      boolean _snowmanxxxxxx = false;
      if (_snowman >= _snowmanxxxx && _snowman <= _snowmanxxxx + this.width && _snowman >= _snowmanxxxxx && _snowman <= _snowmanxxxxx + this.height) {
         _snowmanxxxxxx = true;
      }

      this.render(matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
   }

   protected abstract void render(MatrixStack var1, int y, int var3, boolean var4);

   public int getRight() {
      return this.x + this.width;
   }

   public int getBottom() {
      return this.y + this.height;
   }

   public abstract void handleClick(int index);

   public static void render(MatrixStack matrices, List<RealmsAcceptRejectButton> _snowman, RealmsObjectSelectionList<?> _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      for (RealmsAcceptRejectButton _snowmanxxxxxx : _snowman) {
         if (_snowman.getRowWidth() > _snowmanxxxxxx.getRight()) {
            _snowmanxxxxxx.render(matrices, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   public static void handleClick(
      RealmsObjectSelectionList<?> selectionList,
      AlwaysSelectedEntryListWidget.Entry<?> _snowman,
      List<RealmsAcceptRejectButton> buttons,
      int button,
      double mouseX,
      double mouseY
   ) {
      if (button == 0) {
         int _snowmanx = selectionList.children().indexOf(_snowman);
         if (_snowmanx > -1) {
            selectionList.setSelected(_snowmanx);
            int _snowmanxx = selectionList.getRowLeft();
            int _snowmanxxx = selectionList.getRowTop(_snowmanx);
            int _snowmanxxxx = (int)(mouseX - (double)_snowmanxx);
            int _snowmanxxxxx = (int)(mouseY - (double)_snowmanxxx);

            for (RealmsAcceptRejectButton _snowmanxxxxxx : buttons) {
               if (_snowmanxxxx >= _snowmanxxxxxx.x && _snowmanxxxx <= _snowmanxxxxxx.getRight() && _snowmanxxxxx >= _snowmanxxxxxx.y && _snowmanxxxxx <= _snowmanxxxxxx.getBottom()) {
                  _snowmanxxxxxx.handleClick(_snowmanx);
               }
            }
         }
      }
   }
}
