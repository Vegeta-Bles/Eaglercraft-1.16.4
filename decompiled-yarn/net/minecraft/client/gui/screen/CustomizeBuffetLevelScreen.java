package net.minecraft.client.gui.screen;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class CustomizeBuffetLevelScreen extends Screen {
   private static final Text field_26535 = new TranslatableText("createWorld.customize.buffet.biome");
   private final Screen field_24562;
   private final Consumer<Biome> field_24563;
   private final MutableRegistry<Biome> field_25888;
   private CustomizeBuffetLevelScreen.BuffetBiomesListWidget biomeSelectionList;
   private Biome field_25040;
   private ButtonWidget confirmButton;

   public CustomizeBuffetLevelScreen(Screen _snowman, DynamicRegistryManager _snowman, Consumer<Biome> _snowman, Biome _snowman) {
      super(new TranslatableText("createWorld.customize.buffet.title"));
      this.field_24562 = _snowman;
      this.field_24563 = _snowman;
      this.field_25040 = _snowman;
      this.field_25888 = _snowman.get(Registry.BIOME_KEY);
   }

   @Override
   public void onClose() {
      this.client.openScreen(this.field_24562);
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.biomeSelectionList = new CustomizeBuffetLevelScreen.BuffetBiomesListWidget();
      this.children.add(this.biomeSelectionList);
      this.confirmButton = this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, ScreenTexts.DONE, _snowman -> {
         this.field_24563.accept(this.field_25040);
         this.client.openScreen(this.field_24562);
      }));
      this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, ScreenTexts.CANCEL, _snowman -> this.client.openScreen(this.field_24562)));
      this.biomeSelectionList
         .setSelected(this.biomeSelectionList.children().stream().filter(_snowman -> Objects.equals(_snowman.field_24564, this.field_25040)).findFirst().orElse(null));
   }

   private void refreshConfirmButton() {
      this.confirmButton.active = this.biomeSelectionList.getSelected() != null;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackgroundTexture(0);
      this.biomeSelectionList.render(matrices, mouseX, mouseY, delta);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
      drawCenteredText(matrices, this.textRenderer, field_26535, this.width / 2, 28, 10526880);
      super.render(matrices, mouseX, mouseY, delta);
   }

   class BuffetBiomesListWidget extends AlwaysSelectedEntryListWidget<CustomizeBuffetLevelScreen.BuffetBiomesListWidget.BuffetBiomeItem> {
      private BuffetBiomesListWidget() {
         super(
            CustomizeBuffetLevelScreen.this.client,
            CustomizeBuffetLevelScreen.this.width,
            CustomizeBuffetLevelScreen.this.height,
            40,
            CustomizeBuffetLevelScreen.this.height - 37,
            16
         );
         CustomizeBuffetLevelScreen.this.field_25888
            .getEntries()
            .stream()
            .sorted(Comparator.comparing(_snowman -> _snowman.getKey().getValue().toString()))
            .forEach(_snowman -> this.addEntry(new CustomizeBuffetLevelScreen.BuffetBiomesListWidget.BuffetBiomeItem(_snowman.getValue())));
      }

      @Override
      protected boolean isFocused() {
         return CustomizeBuffetLevelScreen.this.getFocused() == this;
      }

      public void setSelected(@Nullable CustomizeBuffetLevelScreen.BuffetBiomesListWidget.BuffetBiomeItem _snowman) {
         super.setSelected(_snowman);
         if (_snowman != null) {
            CustomizeBuffetLevelScreen.this.field_25040 = _snowman.field_24564;
            NarratorManager.INSTANCE
               .narrate(new TranslatableText("narrator.select", CustomizeBuffetLevelScreen.this.field_25888.getId(_snowman.field_24564)).getString());
         }

         CustomizeBuffetLevelScreen.this.refreshConfirmButton();
      }

      class BuffetBiomeItem extends AlwaysSelectedEntryListWidget.Entry<CustomizeBuffetLevelScreen.BuffetBiomesListWidget.BuffetBiomeItem> {
         private final Biome field_24564;
         private final Text field_26536;

         public BuffetBiomeItem(Biome var2) {
            this.field_24564 = _snowman;
            Identifier _snowman = CustomizeBuffetLevelScreen.this.field_25888.getId(_snowman);
            String _snowmanx = "biome." + _snowman.getNamespace() + "." + _snowman.getPath();
            if (Language.getInstance().hasTranslation(_snowmanx)) {
               this.field_26536 = new TranslatableText(_snowmanx);
            } else {
               this.field_26536 = new LiteralText(_snowman.toString());
            }
         }

         @Override
         public void render(
            MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
         ) {
            DrawableHelper.drawTextWithShadow(matrices, CustomizeBuffetLevelScreen.this.textRenderer, this.field_26536, x + 5, y + 2, 16777215);
         }

         @Override
         public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
               BuffetBiomesListWidget.this.setSelected(this);
               return true;
            } else {
               return false;
            }
         }
      }
   }
}
