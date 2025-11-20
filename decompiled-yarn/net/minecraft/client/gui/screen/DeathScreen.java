package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class DeathScreen extends Screen {
   private int ticksSinceDeath;
   private final Text message;
   private final boolean isHardcore;
   private Text field_26537;

   public DeathScreen(@Nullable Text message, boolean isHardcore) {
      super(new TranslatableText(isHardcore ? "deathScreen.title.hardcore" : "deathScreen.title"));
      this.message = message;
      this.isHardcore = isHardcore;
   }

   @Override
   protected void init() {
      this.ticksSinceDeath = 0;
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 72,
            200,
            20,
            this.isHardcore ? new TranslatableText("deathScreen.spectate") : new TranslatableText("deathScreen.respawn"),
            _snowman -> {
               this.client.player.requestRespawn();
               this.client.openScreen(null);
            }
         )
      );
      ButtonWidget _snowman = this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 96,
            200,
            20,
            new TranslatableText("deathScreen.titleScreen"),
            _snowmanx -> {
               if (this.isHardcore) {
                  this.quitLevel();
               } else {
                  ConfirmScreen _snowmanx = new ConfirmScreen(
                     this::onConfirmQuit,
                     new TranslatableText("deathScreen.quit.confirm"),
                     LiteralText.EMPTY,
                     new TranslatableText("deathScreen.titleScreen"),
                     new TranslatableText("deathScreen.respawn")
                  );
                  this.client.openScreen(_snowmanx);
                  _snowmanx.disableButtons(20);
               }
            }
         )
      );
      if (!this.isHardcore && this.client.getSession() == null) {
         _snowman.active = false;
      }

      for (AbstractButtonWidget _snowmanx : this.buttons) {
         _snowmanx.active = false;
      }

      this.field_26537 = new TranslatableText("deathScreen.score")
         .append(": ")
         .append(new LiteralText(Integer.toString(this.client.player.getScore())).formatted(Formatting.YELLOW));
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   private void onConfirmQuit(boolean quit) {
      if (quit) {
         this.quitLevel();
      } else {
         this.client.player.requestRespawn();
         this.client.openScreen(null);
      }
   }

   private void quitLevel() {
      if (this.client.world != null) {
         this.client.world.disconnect();
      }

      this.client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
      this.client.openScreen(new TitleScreen());
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.fillGradient(matrices, 0, 0, this.width, this.height, 1615855616, -1602211792);
      RenderSystem.pushMatrix();
      RenderSystem.scalef(2.0F, 2.0F, 2.0F);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2 / 2, 30, 16777215);
      RenderSystem.popMatrix();
      if (this.message != null) {
         drawCenteredText(matrices, this.textRenderer, this.message, this.width / 2, 85, 16777215);
      }

      drawCenteredText(matrices, this.textRenderer, this.field_26537, this.width / 2, 100, 16777215);
      if (this.message != null && mouseY > 85 && mouseY < 85 + 9) {
         Style _snowman = this.getTextComponentUnderMouse(mouseX);
         this.renderTextHoverEffect(matrices, _snowman, mouseX, mouseY);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   @Nullable
   private Style getTextComponentUnderMouse(int mouseX) {
      if (this.message == null) {
         return null;
      } else {
         int _snowman = this.client.textRenderer.getWidth(this.message);
         int _snowmanx = this.width / 2 - _snowman / 2;
         int _snowmanxx = this.width / 2 + _snowman / 2;
         return mouseX >= _snowmanx && mouseX <= _snowmanxx ? this.client.textRenderer.getTextHandler().getStyleAt(this.message, mouseX - _snowmanx) : null;
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.message != null && mouseY > 85.0 && mouseY < (double)(85 + 9)) {
         Style _snowman = this.getTextComponentUnderMouse((int)mouseX);
         if (_snowman != null && _snowman.getClickEvent() != null && _snowman.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
            this.handleTextClick(_snowman);
            return false;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      this.ticksSinceDeath++;
      if (this.ticksSinceDeath == 20) {
         for (AbstractButtonWidget _snowman : this.buttons) {
            _snowman.active = true;
         }
      }
   }
}
