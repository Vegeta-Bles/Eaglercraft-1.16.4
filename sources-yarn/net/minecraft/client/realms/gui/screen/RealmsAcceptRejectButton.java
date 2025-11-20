package net.minecraft.client.realms.gui.screen;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
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

   public void render(MatrixStack matrices, int i, int j, int k, int l) {
      int m = i + this.x;
      int n = j + this.y;
      boolean bl = false;
      if (k >= m && k <= m + this.width && l >= n && l <= n + this.height) {
         bl = true;
      }

      this.render(matrices, m, n, bl);
   }

   protected abstract void render(MatrixStack arg, int y, int j, boolean bl);

   public int getRight() {
      return this.x + this.width;
   }

   public int getBottom() {
      return this.y + this.height;
   }

   public abstract void handleClick(int index);

   public static void render(MatrixStack matrices, List<RealmsAcceptRejectButton> list, RealmsObjectSelectionList<?> arg2, int i, int j, int k, int l) {
      for (RealmsAcceptRejectButton lv : list) {
         if (arg2.getRowWidth() > lv.getRight()) {
            lv.render(matrices, i, j, k, l);
         }
      }
   }

   public static void handleClick(
      RealmsObjectSelectionList<?> selectionList,
      AlwaysSelectedEntryListWidget.Entry<?> arg2,
      List<RealmsAcceptRejectButton> buttons,
      int button,
      double mouseX,
      double mouseY
   ) {
      if (button == 0) {
         int j = selectionList.children().indexOf(arg2);
         if (j > -1) {
            selectionList.setSelected(j);
            int k = selectionList.getRowLeft();
            int l = selectionList.getRowTop(j);
            int m = (int)(mouseX - (double)k);
            int n = (int)(mouseY - (double)l);

            for (RealmsAcceptRejectButton lv : buttons) {
               if (m >= lv.x && m <= lv.getRight() && n >= lv.y && n <= lv.getBottom()) {
                  lv.handleClick(j);
               }
            }
         }
      }
   }
}
