package net.minecraft.client.gui.screen.options;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.gui.widget.SoundSliderWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;

public class SoundOptionsScreen extends GameOptionsScreen {
   public SoundOptionsScreen(Screen parent, GameOptions options) {
      super(parent, options, new TranslatableText("options.sounds.title"));
   }

   @Override
   protected void init() {
      int _snowman = 0;
      this.addButton(new SoundSliderWidget(this.client, this.width / 2 - 155 + _snowman % 2 * 160, this.height / 6 - 12 + 24 * (_snowman >> 1), SoundCategory.MASTER, 310));
      _snowman += 2;

      for (SoundCategory _snowmanx : SoundCategory.values()) {
         if (_snowmanx != SoundCategory.MASTER) {
            this.addButton(new SoundSliderWidget(this.client, this.width / 2 - 155 + _snowman % 2 * 160, this.height / 6 - 12 + 24 * (_snowman >> 1), _snowmanx, 150));
            _snowman++;
         }
      }

      this.addButton(
         new OptionButtonWidget(
            this.width / 2 - 75,
            this.height / 6 - 12 + 24 * (++_snowman >> 1),
            150,
            20,
            Option.SUBTITLES,
            Option.SUBTITLES.getDisplayString(this.gameOptions),
            button -> {
               Option.SUBTITLES.toggle(this.client.options);
               button.setMessage(Option.SUBTITLES.getDisplayString(this.client.options));
               this.client.options.write();
            }
         )
      );
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, ScreenTexts.DONE, button -> this.client.openScreen(this.parent)));
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }
}
