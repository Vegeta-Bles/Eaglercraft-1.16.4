package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_5489;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class DemoScreen extends Screen {
   private static final Identifier DEMO_BG = new Identifier("textures/gui/demo_background.png");
   private class_5489 field_26538 = class_5489.field_26528;
   private class_5489 field_26539 = class_5489.field_26528;

   public DemoScreen() {
      super(new TranslatableText("demo.help.title"));
   }

   @Override
   protected void init() {
      int _snowman = -16;
      this.addButton(new ButtonWidget(this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, new TranslatableText("demo.help.buy"), buttonWidget -> {
         buttonWidget.active = false;
         Util.getOperatingSystem().open("http://www.minecraft.net/store?source=demo");
      }));
      this.addButton(new ButtonWidget(this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, new TranslatableText("demo.help.later"), buttonWidget -> {
         this.client.openScreen(null);
         this.client.mouse.lockCursor();
      }));
      GameOptions _snowmanx = this.client.options;
      this.field_26538 = class_5489.method_30892(
         this.textRenderer,
         new TranslatableText(
            "demo.help.movementShort",
            _snowmanx.keyForward.getBoundKeyLocalizedText(),
            _snowmanx.keyLeft.getBoundKeyLocalizedText(),
            _snowmanx.keyBack.getBoundKeyLocalizedText(),
            _snowmanx.keyRight.getBoundKeyLocalizedText()
         ),
         new TranslatableText("demo.help.movementMouse"),
         new TranslatableText("demo.help.jump", _snowmanx.keyJump.getBoundKeyLocalizedText()),
         new TranslatableText("demo.help.inventory", _snowmanx.keyInventory.getBoundKeyLocalizedText())
      );
      this.field_26539 = class_5489.method_30890(this.textRenderer, new TranslatableText("demo.help.fullWrapped"), 218);
   }

   @Override
   public void renderBackground(MatrixStack matrices) {
      super.renderBackground(matrices);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(DEMO_BG);
      int _snowman = (this.width - 248) / 2;
      int _snowmanx = (this.height - 166) / 2;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, 248, 166);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      int _snowman = (this.width - 248) / 2 + 10;
      int _snowmanx = (this.height - 166) / 2 + 8;
      this.textRenderer.draw(matrices, this.title, (float)_snowman, (float)_snowmanx, 2039583);
      _snowmanx = this.field_26538.method_30896(matrices, _snowman, _snowmanx + 12, 12, 5197647);
      this.field_26539.method_30896(matrices, _snowman, _snowmanx + 20, 9, 2039583);
      super.render(matrices, mouseX, mouseY, delta);
   }
}
