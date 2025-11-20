package net.minecraft.client.gui.widget;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;

public class ButtonListWidget extends ElementListWidget<ButtonListWidget.ButtonEntry> {
   public ButtonListWidget(MinecraftClient _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.centerListVertically = false;
   }

   public int addSingleOptionEntry(Option option) {
      return this.addEntry(ButtonListWidget.ButtonEntry.create(this.client.options, this.width, option));
   }

   public void addOptionEntry(Option firstOption, @Nullable Option secondOption) {
      this.addEntry(ButtonListWidget.ButtonEntry.create(this.client.options, this.width, firstOption, secondOption));
   }

   public void addAll(Option[] options) {
      for (int _snowman = 0; _snowman < options.length; _snowman += 2) {
         this.addOptionEntry(options[_snowman], _snowman < options.length - 1 ? options[_snowman + 1] : null);
      }
   }

   @Override
   public int getRowWidth() {
      return 400;
   }

   @Override
   protected int getScrollbarPositionX() {
      return super.getScrollbarPositionX() + 32;
   }

   @Nullable
   public AbstractButtonWidget getButtonFor(Option _snowman) {
      for (ButtonListWidget.ButtonEntry _snowmanx : this.children()) {
         for (AbstractButtonWidget _snowmanxx : _snowmanx.buttons) {
            if (_snowmanxx instanceof OptionButtonWidget && ((OptionButtonWidget)_snowmanxx).getOption() == _snowman) {
               return _snowmanxx;
            }
         }
      }

      return null;
   }

   public Optional<AbstractButtonWidget> getHoveredButton(double mouseX, double mouseY) {
      for (ButtonListWidget.ButtonEntry _snowman : this.children()) {
         for (AbstractButtonWidget _snowmanx : _snowman.buttons) {
            if (_snowmanx.isMouseOver(mouseX, mouseY)) {
               return Optional.of(_snowmanx);
            }
         }
      }

      return Optional.empty();
   }

   public static class ButtonEntry extends ElementListWidget.Entry<ButtonListWidget.ButtonEntry> {
      private final List<AbstractButtonWidget> buttons;

      private ButtonEntry(List<AbstractButtonWidget> buttons) {
         this.buttons = buttons;
      }

      public static ButtonListWidget.ButtonEntry create(GameOptions options, int width, Option option) {
         return new ButtonListWidget.ButtonEntry(ImmutableList.of(option.createButton(options, width / 2 - 155, 0, 310)));
      }

      public static ButtonListWidget.ButtonEntry create(GameOptions options, int width, Option firstOption, @Nullable Option secondOption) {
         AbstractButtonWidget _snowman = firstOption.createButton(options, width / 2 - 155, 0, 150);
         return secondOption == null
            ? new ButtonListWidget.ButtonEntry(ImmutableList.of(_snowman))
            : new ButtonListWidget.ButtonEntry(ImmutableList.of(_snowman, secondOption.createButton(options, width / 2 - 155 + 160, 0, 150)));
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.buttons.forEach(button -> {
            button.y = y;
            button.render(matrices, mouseX, mouseY, tickDelta);
         });
      }

      @Override
      public List<? extends Element> children() {
         return this.buttons;
      }
   }
}
