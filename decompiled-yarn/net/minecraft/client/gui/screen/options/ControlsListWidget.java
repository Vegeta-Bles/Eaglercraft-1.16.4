package net.minecraft.client.gui.screen.options;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.ArrayUtils;

public class ControlsListWidget extends ElementListWidget<ControlsListWidget.Entry> {
   private final ControlsOptionsScreen parent;
   private int maxKeyNameLength;

   public ControlsListWidget(ControlsOptionsScreen parent, MinecraftClient client) {
      super(client, parent.width + 45, parent.height, 43, parent.height - 32, 20);
      this.parent = parent;
      KeyBinding[] _snowman = (KeyBinding[])ArrayUtils.clone(client.options.keysAll);
      Arrays.sort((Object[])_snowman);
      String _snowmanx = null;

      for (KeyBinding _snowmanxx : _snowman) {
         String _snowmanxxx = _snowmanxx.getCategory();
         if (!_snowmanxxx.equals(_snowmanx)) {
            _snowmanx = _snowmanxxx;
            this.addEntry(new ControlsListWidget.CategoryEntry(new TranslatableText(_snowmanxxx)));
         }

         Text _snowmanxxxx = new TranslatableText(_snowmanxx.getTranslationKey());
         int _snowmanxxxxx = client.textRenderer.getWidth(_snowmanxxxx);
         if (_snowmanxxxxx > this.maxKeyNameLength) {
            this.maxKeyNameLength = _snowmanxxxxx;
         }

         this.addEntry(new ControlsListWidget.KeyBindingEntry(_snowmanxx, _snowmanxxxx));
      }
   }

   @Override
   protected int getScrollbarPositionX() {
      return super.getScrollbarPositionX() + 15;
   }

   @Override
   public int getRowWidth() {
      return super.getRowWidth() + 32;
   }

   public class CategoryEntry extends ControlsListWidget.Entry {
      private final Text text;
      private final int textWidth;

      public CategoryEntry(Text text) {
         this.text = text;
         this.textWidth = ControlsListWidget.this.client.textRenderer.getWidth(this.text);
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         ControlsListWidget.this.client
            .textRenderer
            .draw(
               matrices,
               this.text,
               (float)(ControlsListWidget.this.client.currentScreen.width / 2 - this.textWidth / 2),
               (float)(y + entryHeight - 9 - 1),
               16777215
            );
      }

      @Override
      public boolean changeFocus(boolean lookForwards) {
         return false;
      }

      @Override
      public List<? extends Element> children() {
         return Collections.emptyList();
      }
   }

   public abstract static class Entry extends ElementListWidget.Entry<ControlsListWidget.Entry> {
      public Entry() {
      }
   }

   public class KeyBindingEntry extends ControlsListWidget.Entry {
      private final KeyBinding binding;
      private final Text bindingName;
      private final ButtonWidget editButton;
      private final ButtonWidget resetButton;

      private KeyBindingEntry(KeyBinding binding, Text text) {
         this.binding = binding;
         this.bindingName = text;
         this.editButton = new ButtonWidget(0, 0, 75, 20, text, button -> ControlsListWidget.this.parent.focusedBinding = binding) {
            @Override
            protected MutableText getNarrationMessage() {
               return binding.isUnbound()
                  ? new TranslatableText("narrator.controls.unbound", text)
                  : new TranslatableText("narrator.controls.bound", text, super.getNarrationMessage());
            }
         };
         this.resetButton = new ButtonWidget(0, 0, 50, 20, new TranslatableText("controls.reset"), button -> {
            ControlsListWidget.this.client.options.setKeyCode(binding, binding.getDefaultKey());
            KeyBinding.updateKeysByCode();
         }) {
            @Override
            protected MutableText getNarrationMessage() {
               return new TranslatableText("narrator.controls.reset", text);
            }
         };
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         boolean _snowman = ControlsListWidget.this.parent.focusedBinding == this.binding;
         ControlsListWidget.this.client
            .textRenderer
            .draw(matrices, this.bindingName, (float)(x + 90 - ControlsListWidget.this.maxKeyNameLength), (float)(y + entryHeight / 2 - 9 / 2), 16777215);
         this.resetButton.x = x + 190;
         this.resetButton.y = y;
         this.resetButton.active = !this.binding.isDefault();
         this.resetButton.render(matrices, mouseX, mouseY, tickDelta);
         this.editButton.x = x + 105;
         this.editButton.y = y;
         this.editButton.setMessage(this.binding.getBoundKeyLocalizedText());
         boolean _snowmanx = false;
         if (!this.binding.isUnbound()) {
            for (KeyBinding _snowmanxx : ControlsListWidget.this.client.options.keysAll) {
               if (_snowmanxx != this.binding && this.binding.equals(_snowmanxx)) {
                  _snowmanx = true;
                  break;
               }
            }
         }

         if (_snowman) {
            this.editButton
               .setMessage(
                  new LiteralText("> ")
                     .append(this.editButton.getMessage().shallowCopy().formatted(Formatting.YELLOW))
                     .append(" <")
                     .formatted(Formatting.YELLOW)
               );
         } else if (_snowmanx) {
            this.editButton.setMessage(this.editButton.getMessage().shallowCopy().formatted(Formatting.RED));
         }

         this.editButton.render(matrices, mouseX, mouseY, tickDelta);
      }

      @Override
      public List<? extends Element> children() {
         return ImmutableList.of(this.editButton, this.resetButton);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         return this.editButton.mouseClicked(mouseX, mouseY, button) ? true : this.resetButton.mouseClicked(mouseX, mouseY, button);
      }

      @Override
      public boolean mouseReleased(double mouseX, double mouseY, int button) {
         return this.editButton.mouseReleased(mouseX, mouseY, button) || this.resetButton.mouseReleased(mouseX, mouseY, button);
      }
   }
}
