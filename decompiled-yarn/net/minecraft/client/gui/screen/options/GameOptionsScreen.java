package net.minecraft.client.gui.screen.options;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.OrderableTooltip;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public class GameOptionsScreen extends Screen {
   protected final Screen parent;
   protected final GameOptions gameOptions;

   public GameOptionsScreen(Screen parent, GameOptions gameOptions, Text title) {
      super(title);
      this.parent = parent;
      this.gameOptions = gameOptions;
   }

   @Override
   public void removed() {
      this.client.options.write();
   }

   @Override
   public void onClose() {
      this.client.openScreen(this.parent);
   }

   @Nullable
   public static List<OrderedText> getHoveredButtonTooltip(ButtonListWidget buttonList, int mouseX, int mouseY) {
      Optional<AbstractButtonWidget> _snowman = buttonList.getHoveredButton((double)mouseX, (double)mouseY);
      if (_snowman.isPresent() && _snowman.get() instanceof OrderableTooltip) {
         Optional<List<OrderedText>> _snowmanx = ((OrderableTooltip)_snowman.get()).getOrderedTooltip();
         return _snowmanx.orElse(null);
      } else {
         return null;
      }
   }
}
