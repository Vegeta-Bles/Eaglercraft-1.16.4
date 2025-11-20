package net.minecraft.client.realms.gui.screen;

import net.minecraft.class_5489;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class RealmsParentalConsentScreen extends RealmsScreen {
   private static final Text field_26491 = new TranslatableText("mco.account.privacyinfo");
   private final Screen parent;
   private class_5489 field_26492 = class_5489.field_26528;

   public RealmsParentalConsentScreen(Screen _snowman) {
      this.parent = _snowman;
   }

   @Override
   public void init() {
      Realms.narrateNow(field_26491.getString());
      Text _snowman = new TranslatableText("mco.account.update");
      Text _snowmanx = ScreenTexts.BACK;
      int _snowmanxx = Math.max(this.textRenderer.getWidth(_snowman), this.textRenderer.getWidth(_snowmanx)) + 30;
      Text _snowmanxxx = new TranslatableText("mco.account.privacy.info");
      int _snowmanxxxx = (int)((double)this.textRenderer.getWidth(_snowmanxxx) * 1.2);
      this.addButton(
         new ButtonWidget(this.width / 2 - _snowmanxxxx / 2, row(11), _snowmanxxxx, 20, _snowmanxxx, _snowmanxxxxx -> Util.getOperatingSystem().open("https://aka.ms/MinecraftGDPR"))
      );
      this.addButton(
         new ButtonWidget(this.width / 2 - (_snowmanxx + 5), row(13), _snowmanxx, 20, _snowman, _snowmanxxxxx -> Util.getOperatingSystem().open("https://aka.ms/UpdateMojangAccount"))
      );
      this.addButton(new ButtonWidget(this.width / 2 + 5, row(13), _snowmanxx, 20, _snowmanx, _snowmanxxxxx -> this.client.openScreen(this.parent)));
      this.field_26492 = class_5489.method_30890(this.textRenderer, field_26491, (int)Math.round((double)this.width * 0.9));
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      this.field_26492.method_30889(matrices, this.width / 2, 15, 15, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }
}
