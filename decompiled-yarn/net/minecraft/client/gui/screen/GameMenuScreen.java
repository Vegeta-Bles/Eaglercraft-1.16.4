package net.minecraft.client.gui.screen;

import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsBridgeScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class GameMenuScreen extends Screen {
   private final boolean showMenu;

   public GameMenuScreen(boolean showMenu) {
      super(showMenu ? new TranslatableText("menu.game") : new TranslatableText("menu.paused"));
      this.showMenu = showMenu;
   }

   @Override
   protected void init() {
      if (this.showMenu) {
         this.initWidgets();
      }
   }

   private void initWidgets() {
      int _snowman = -16;
      int _snowmanx = 98;
      this.addButton(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, new TranslatableText("menu.returnToGame"), _snowmanxx -> {
         this.client.openScreen(null);
         this.client.mouse.lockCursor();
      }));
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 102,
            this.height / 4 + 48 + -16,
            98,
            20,
            new TranslatableText("gui.advancements"),
            _snowmanxx -> this.client.openScreen(new AdvancementsScreen(this.client.player.networkHandler.getAdvancementHandler()))
         )
      );
      this.addButton(
         new ButtonWidget(
            this.width / 2 + 4,
            this.height / 4 + 48 + -16,
            98,
            20,
            new TranslatableText("gui.stats"),
            _snowmanxx -> this.client.openScreen(new StatsScreen(this, this.client.player.getStatHandler()))
         )
      );
      String _snowmanxx = SharedConstants.getGameVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 102,
            this.height / 4 + 72 + -16,
            98,
            20,
            new TranslatableText("menu.sendFeedback"),
            _snowmanxxx -> this.client.openScreen(new ConfirmChatLinkScreen(_snowmanxxxx -> {
                  if (_snowmanxxxx) {
                     Util.getOperatingSystem().open(_snowman);
                  }

                  this.client.openScreen(this);
               }, _snowman, true))
         )
      );
      this.addButton(
         new ButtonWidget(
            this.width / 2 + 4,
            this.height / 4 + 72 + -16,
            98,
            20,
            new TranslatableText("menu.reportBugs"),
            _snowmanxxx -> this.client.openScreen(new ConfirmChatLinkScreen(_snowmanxxxx -> {
                  if (_snowmanxxxx) {
                     Util.getOperatingSystem().open("https://aka.ms/snapshotbugs?ref=game");
                  }

                  this.client.openScreen(this);
               }, "https://aka.ms/snapshotbugs?ref=game", true))
         )
      );
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 102,
            this.height / 4 + 96 + -16,
            98,
            20,
            new TranslatableText("menu.options"),
            _snowmanxxx -> this.client.openScreen(new OptionsScreen(this, this.client.options))
         )
      );
      ButtonWidget _snowmanxxx = this.addButton(
         new ButtonWidget(
            this.width / 2 + 4,
            this.height / 4 + 96 + -16,
            98,
            20,
            new TranslatableText("menu.shareToLan"),
            _snowmanxxxx -> this.client.openScreen(new OpenToLanScreen(this))
         )
      );
      _snowmanxxx.active = this.client.isIntegratedServerRunning() && !this.client.getServer().isRemote();
      ButtonWidget _snowmanxxxx = this.addButton(
         new ButtonWidget(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, new TranslatableText("menu.returnToMenu"), _snowmanxxxxx -> {
            boolean _snowmanx = this.client.isInSingleplayer();
            boolean _snowmanxx = this.client.isConnectedToRealms();
            _snowmanxxxxx.active = false;
            this.client.world.disconnect();
            if (_snowmanx) {
               this.client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
            } else {
               this.client.disconnect();
            }

            if (_snowmanx) {
               this.client.openScreen(new TitleScreen());
            } else if (_snowmanxx) {
               RealmsBridgeScreen _snowmanxxx = new RealmsBridgeScreen();
               _snowmanxxx.switchToRealms(new TitleScreen());
            } else {
               this.client.openScreen(new MultiplayerScreen(new TitleScreen()));
            }
         })
      );
      if (!this.client.isInSingleplayer()) {
         _snowmanxxxx.setMessage(new TranslatableText("menu.disconnect"));
      }
   }

   @Override
   public void tick() {
      super.tick();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.showMenu) {
         this.renderBackground(matrices);
         drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 40, 16777215);
      } else {
         drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 16777215);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }
}
