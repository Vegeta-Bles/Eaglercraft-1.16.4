package net.minecraft.client.gui.screen.options;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SkinOptionsScreen extends GameOptionsScreen {
   public SkinOptionsScreen(Screen parent, GameOptions gameOptions) {
      super(parent, gameOptions, new TranslatableText("options.skinCustomisation.title"));
   }

   @Override
   protected void init() {
      int _snowman = 0;

      for (PlayerModelPart _snowmanx : PlayerModelPart.values()) {
         this.addButton(
            new ButtonWidget(
               this.width / 2 - 155 + _snowman % 2 * 160, this.height / 6 + 24 * (_snowman >> 1), 150, 20, this.getPlayerModelPartDisplayString(_snowmanx), button -> {
                  this.gameOptions.togglePlayerModelPart(_snowman);
                  button.setMessage(this.getPlayerModelPartDisplayString(_snowman));
               }
            )
         );
         _snowman++;
      }

      this.addButton(
         new OptionButtonWidget(
            this.width / 2 - 155 + _snowman % 2 * 160,
            this.height / 6 + 24 * (_snowman >> 1),
            150,
            20,
            Option.MAIN_HAND,
            Option.MAIN_HAND.getMessage(this.gameOptions),
            button -> {
               Option.MAIN_HAND.cycle(this.gameOptions, 1);
               this.gameOptions.write();
               button.setMessage(Option.MAIN_HAND.getMessage(this.gameOptions));
               this.gameOptions.onPlayerModelPartChange();
            }
         )
      );
      if (++_snowman % 2 == 1) {
         _snowman++;
      }

      this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height / 6 + 24 * (_snowman >> 1), 200, 20, ScreenTexts.DONE, button -> this.client.openScreen(this.parent))
      );
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }

   private Text getPlayerModelPartDisplayString(PlayerModelPart part) {
      return ScreenTexts.composeToggleText(part.getOptionName(), this.gameOptions.getEnabledPlayerModelParts().contains(part));
   }
}
