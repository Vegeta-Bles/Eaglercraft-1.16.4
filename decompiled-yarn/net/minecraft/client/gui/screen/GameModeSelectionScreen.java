package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class GameModeSelectionScreen extends Screen {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/gamemode_switcher.png");
   private static final int UI_WIDTH = GameModeSelectionScreen.GameMode.values().length * 30 - 5;
   private static final Text field_25454 = new TranslatableText(
      "debug.gamemodes.select_next", new TranslatableText("debug.gamemodes.press_f4").formatted(Formatting.AQUA)
   );
   private final Optional<GameModeSelectionScreen.GameMode> currentGameMode;
   private Optional<GameModeSelectionScreen.GameMode> gameMode = Optional.empty();
   private int lastMouseX;
   private int lastMouseY;
   private boolean mouseUsedForSelection;
   private final List<GameModeSelectionScreen.ButtonWidget> gameModeButtons = Lists.newArrayList();

   public GameModeSelectionScreen() {
      super(NarratorManager.EMPTY);
      this.currentGameMode = GameModeSelectionScreen.GameMode.of(this.method_30106());
   }

   private net.minecraft.world.GameMode method_30106() {
      net.minecraft.world.GameMode _snowman = MinecraftClient.getInstance().interactionManager.getCurrentGameMode();
      net.minecraft.world.GameMode _snowmanx = MinecraftClient.getInstance().interactionManager.getPreviousGameMode();
      if (_snowmanx == net.minecraft.world.GameMode.NOT_SET) {
         if (_snowman == net.minecraft.world.GameMode.CREATIVE) {
            _snowmanx = net.minecraft.world.GameMode.SURVIVAL;
         } else {
            _snowmanx = net.minecraft.world.GameMode.CREATIVE;
         }
      }

      return _snowmanx;
   }

   @Override
   protected void init() {
      super.init();
      this.gameMode = this.currentGameMode.isPresent()
         ? this.currentGameMode
         : GameModeSelectionScreen.GameMode.of(this.client.interactionManager.getCurrentGameMode());

      for (int _snowman = 0; _snowman < GameModeSelectionScreen.GameMode.VALUES.length; _snowman++) {
         GameModeSelectionScreen.GameMode _snowmanx = GameModeSelectionScreen.GameMode.VALUES[_snowman];
         this.gameModeButtons.add(new GameModeSelectionScreen.ButtonWidget(_snowmanx, this.width / 2 - UI_WIDTH / 2 + _snowman * 30, this.height / 2 - 30));
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (!this.checkForClose()) {
         matrices.push();
         RenderSystem.enableBlend();
         this.client.getTextureManager().bindTexture(TEXTURE);
         int _snowman = this.width / 2 - 62;
         int _snowmanx = this.height / 2 - 30 - 27;
         drawTexture(matrices, _snowman, _snowmanx, 0.0F, 0.0F, 125, 75, 128, 128);
         matrices.pop();
         super.render(matrices, mouseX, mouseY, delta);
         this.gameMode.ifPresent(_snowmanxx -> drawCenteredText(matrices, this.textRenderer, _snowmanxx.getText(), this.width / 2, this.height / 2 - 30 - 20, -1));
         drawCenteredText(matrices, this.textRenderer, field_25454, this.width / 2, this.height / 2 + 5, 16777215);
         if (!this.mouseUsedForSelection) {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.mouseUsedForSelection = true;
         }

         boolean _snowmanxx = this.lastMouseX == mouseX && this.lastMouseY == mouseY;

         for (GameModeSelectionScreen.ButtonWidget _snowmanxxx : this.gameModeButtons) {
            _snowmanxxx.render(matrices, mouseX, mouseY, delta);
            this.gameMode.ifPresent(_snowmanxxxx -> _snowman.setSelected(_snowmanxxxx == _snowman.gameMode));
            if (!_snowmanxx && _snowmanxxx.isHovered()) {
               this.gameMode = Optional.of(_snowmanxxx.gameMode);
            }
         }
      }
   }

   private void apply() {
      apply(this.client, this.gameMode);
   }

   private static void apply(MinecraftClient client, Optional<GameModeSelectionScreen.GameMode> gameMode) {
      if (client.interactionManager != null && client.player != null && gameMode.isPresent()) {
         Optional<GameModeSelectionScreen.GameMode> _snowman = GameModeSelectionScreen.GameMode.of(client.interactionManager.getCurrentGameMode());
         GameModeSelectionScreen.GameMode _snowmanx = gameMode.get();
         if (_snowman.isPresent() && client.player.hasPermissionLevel(2) && _snowmanx != _snowman.get()) {
            client.player.sendChatMessage(_snowmanx.getCommand());
         }
      }
   }

   private boolean checkForClose() {
      if (!InputUtil.isKeyPressed(this.client.getWindow().getHandle(), 292)) {
         this.apply();
         this.client.openScreen(null);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 293 && this.gameMode.isPresent()) {
         this.mouseUsedForSelection = false;
         this.gameMode = this.gameMode.get().next();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   public class ButtonWidget extends AbstractButtonWidget {
      private final GameModeSelectionScreen.GameMode gameMode;
      private boolean selected;

      public ButtonWidget(GameModeSelectionScreen.GameMode _snowman, int x, int y) {
         super(x, y, 25, 25, _snowman.getText());
         this.gameMode = _snowman;
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         MinecraftClient _snowman = MinecraftClient.getInstance();
         this.drawBackground(matrices, _snowman.getTextureManager());
         this.gameMode.renderIcon(GameModeSelectionScreen.this.itemRenderer, this.x + 5, this.y + 5);
         if (this.selected) {
            this.drawSelectionBox(matrices, _snowman.getTextureManager());
         }
      }

      @Override
      public boolean isHovered() {
         return super.isHovered() || this.selected;
      }

      public void setSelected(boolean selected) {
         this.selected = selected;
         this.narrate();
      }

      private void drawBackground(MatrixStack matrices, TextureManager _snowman) {
         _snowman.bindTexture(GameModeSelectionScreen.TEXTURE);
         matrices.push();
         matrices.translate((double)this.x, (double)this.y, 0.0);
         drawTexture(matrices, 0, 0, 0.0F, 75.0F, 25, 25, 128, 128);
         matrices.pop();
      }

      private void drawSelectionBox(MatrixStack matrices, TextureManager _snowman) {
         _snowman.bindTexture(GameModeSelectionScreen.TEXTURE);
         matrices.push();
         matrices.translate((double)this.x, (double)this.y, 0.0);
         drawTexture(matrices, 0, 0, 25.0F, 75.0F, 25, 25, 128, 128);
         matrices.pop();
      }
   }

   static enum GameMode {
      CREATIVE(new TranslatableText("gameMode.creative"), "/gamemode creative", new ItemStack(Blocks.GRASS_BLOCK)),
      SURVIVAL(new TranslatableText("gameMode.survival"), "/gamemode survival", new ItemStack(Items.IRON_SWORD)),
      ADVENTURE(new TranslatableText("gameMode.adventure"), "/gamemode adventure", new ItemStack(Items.MAP)),
      SPECTATOR(new TranslatableText("gameMode.spectator"), "/gamemode spectator", new ItemStack(Items.ENDER_EYE));

      protected static final GameModeSelectionScreen.GameMode[] VALUES = values();
      final Text text;
      final String command;
      final ItemStack icon;

      private GameMode(Text var3, String command, ItemStack icon) {
         this.text = _snowman;
         this.command = command;
         this.icon = icon;
      }

      private void renderIcon(ItemRenderer _snowman, int x, int y) {
         _snowman.renderInGuiWithOverrides(this.icon, x, y);
      }

      private Text getText() {
         return this.text;
      }

      private String getCommand() {
         return this.command;
      }

      private Optional<GameModeSelectionScreen.GameMode> next() {
         switch (this) {
            case CREATIVE:
               return Optional.of(SURVIVAL);
            case SURVIVAL:
               return Optional.of(ADVENTURE);
            case ADVENTURE:
               return Optional.of(SPECTATOR);
            default:
               return Optional.of(CREATIVE);
         }
      }

      private static Optional<GameModeSelectionScreen.GameMode> of(net.minecraft.world.GameMode _snowman) {
         switch (_snowman) {
            case SPECTATOR:
               return Optional.of(SPECTATOR);
            case SURVIVAL:
               return Optional.of(SURVIVAL);
            case CREATIVE:
               return Optional.of(CREATIVE);
            case ADVENTURE:
               return Optional.of(ADVENTURE);
            default:
               return Optional.empty();
         }
      }
   }
}
