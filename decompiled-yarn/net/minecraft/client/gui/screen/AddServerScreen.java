package net.minecraft.client.gui.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.IDN;
import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ChatUtil;

public class AddServerScreen extends Screen {
   private static final Text field_26541 = new TranslatableText("addServer.enterName");
   private static final Text field_26542 = new TranslatableText("addServer.enterIp");
   private ButtonWidget buttonAdd;
   private final BooleanConsumer callback;
   private final ServerInfo server;
   private TextFieldWidget addressField;
   private TextFieldWidget serverNameField;
   private ButtonWidget resourcePackOptionButton;
   private final Screen parent;
   private final Predicate<String> addressTextFilter = _snowman -> {
      if (ChatUtil.isEmpty(_snowman)) {
         return true;
      } else {
         String[] _snowmanx = _snowman.split(":");
         if (_snowmanx.length == 0) {
            return true;
         } else {
            try {
               String _snowmanxx = IDN.toASCII(_snowmanx[0]);
               return true;
            } catch (IllegalArgumentException var3) {
               return false;
            }
         }
      }
   };

   public AddServerScreen(Screen parent, BooleanConsumer callback, ServerInfo server) {
      super(new TranslatableText("addServer.title"));
      this.parent = parent;
      this.callback = callback;
      this.server = server;
   }

   @Override
   public void tick() {
      this.serverNameField.tick();
      this.addressField.tick();
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.serverNameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 66, 200, 20, new TranslatableText("addServer.enterName"));
      this.serverNameField.setSelected(true);
      this.serverNameField.setText(this.server.name);
      this.serverNameField.setChangedListener(this::onClose);
      this.children.add(this.serverNameField);
      this.addressField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 106, 200, 20, new TranslatableText("addServer.enterIp"));
      this.addressField.setMaxLength(128);
      this.addressField.setText(this.server.address);
      this.addressField.setTextPredicate(this.addressTextFilter);
      this.addressField.setChangedListener(this::onClose);
      this.children.add(this.addressField);
      this.resourcePackOptionButton = this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 72,
            200,
            20,
            method_27570(this.server.getResourcePack()),
            _snowman -> {
               this.server
                  .setResourcePackState(
                     ServerInfo.ResourcePackState.values()[(this.server.getResourcePack().ordinal() + 1) % ServerInfo.ResourcePackState.values().length]
                  );
               this.resourcePackOptionButton.setMessage(method_27570(this.server.getResourcePack()));
            }
         )
      );
      this.buttonAdd = this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, new TranslatableText("addServer.add"), _snowman -> this.addAndClose())
      );
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, ScreenTexts.CANCEL, _snowman -> this.callback.accept(false)));
      this.updateButtonActiveState();
   }

   private static Text method_27570(ServerInfo.ResourcePackState _snowman) {
      return new TranslatableText("addServer.resourcePack").append(": ").append(_snowman.getName());
   }

   @Override
   public void resize(MinecraftClient client, int width, int height) {
      String _snowman = this.addressField.getText();
      String _snowmanx = this.serverNameField.getText();
      this.init(client, width, height);
      this.addressField.setText(_snowman);
      this.serverNameField.setText(_snowmanx);
   }

   private void onClose(String text) {
      this.updateButtonActiveState();
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   private void addAndClose() {
      this.server.name = this.serverNameField.getText();
      this.server.address = this.addressField.getText();
      this.callback.accept(true);
   }

   @Override
   public void onClose() {
      this.updateButtonActiveState();
      this.client.openScreen(this.parent);
   }

   private void updateButtonActiveState() {
      String _snowman = this.addressField.getText();
      boolean _snowmanx = !_snowman.isEmpty() && _snowman.split(":").length > 0 && _snowman.indexOf(32) == -1;
      this.buttonAdd.active = _snowmanx && !this.serverNameField.getText().isEmpty();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 17, 16777215);
      drawTextWithShadow(matrices, this.textRenderer, field_26541, this.width / 2 - 100, 53, 10526880);
      drawTextWithShadow(matrices, this.textRenderer, field_26542, this.width / 2 - 100, 94, 10526880);
      this.serverNameField.render(matrices, mouseX, mouseY, delta);
      this.addressField.render(matrices, mouseX, mouseY, delta);
      super.render(matrices, mouseX, mouseY, delta);
   }
}
