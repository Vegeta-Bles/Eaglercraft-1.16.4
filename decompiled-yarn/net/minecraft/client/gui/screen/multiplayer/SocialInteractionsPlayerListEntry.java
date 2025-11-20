package net.minecraft.client.gui.screen.multiplayer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class SocialInteractionsPlayerListEntry extends ElementListWidget.Entry<SocialInteractionsPlayerListEntry> {
   private final MinecraftClient client;
   private final List<Element> buttons;
   private final UUID uuid;
   private final String name;
   private final Supplier<Identifier> field_26904;
   private boolean field_26859;
   @Nullable
   private ButtonWidget hideButton;
   @Nullable
   private ButtonWidget showButton;
   private final List<OrderedText> hideTooltip;
   private final List<OrderedText> showTooltip;
   private float field_26864;
   private static final Text field_26905 = new TranslatableText("gui.socialInteractions.status_hidden").formatted(Formatting.ITALIC);
   private static final Text field_26906 = new TranslatableText("gui.socialInteractions.status_blocked").formatted(Formatting.ITALIC);
   private static final Text field_26907 = new TranslatableText("gui.socialInteractions.status_offline").formatted(Formatting.ITALIC);
   private static final Text field_26908 = new TranslatableText("gui.socialInteractions.status_hidden_offline").formatted(Formatting.ITALIC);
   private static final Text field_26909 = new TranslatableText("gui.socialInteractions.status_blocked_offline").formatted(Formatting.ITALIC);
   public static final int field_26850 = BackgroundHelper.ColorMixer.getArgb(190, 0, 0, 0);
   public static final int field_26851 = BackgroundHelper.ColorMixer.getArgb(255, 74, 74, 74);
   public static final int field_26852 = BackgroundHelper.ColorMixer.getArgb(255, 48, 48, 48);
   public static final int field_26853 = BackgroundHelper.ColorMixer.getArgb(255, 255, 255, 255);
   public static final int field_26903 = BackgroundHelper.ColorMixer.getArgb(140, 255, 255, 255);

   public SocialInteractionsPlayerListEntry(MinecraftClient client, SocialInteractionsScreen parent, UUID uuid, String name, Supplier<Identifier> _snowman) {
      this.client = client;
      this.uuid = uuid;
      this.name = name;
      this.field_26904 = _snowman;
      this.hideTooltip = client.textRenderer.wrapLines(new TranslatableText("gui.socialInteractions.tooltip.hide", name), 150);
      this.showTooltip = client.textRenderer.wrapLines(new TranslatableText("gui.socialInteractions.tooltip.show", name), 150);
      SocialInteractionsManager _snowmanx = client.getSocialInteractionsManager();
      if (!client.player.getGameProfile().getId().equals(uuid) && !_snowmanx.isPlayerBlocked(uuid)) {
         this.hideButton = new TexturedButtonWidget(0, 0, 20, 20, 0, 38, 20, SocialInteractionsScreen.SOCIAL_INTERACTIONS_TEXTURE, 256, 256, _snowmanxx -> {
            _snowman.hidePlayer(uuid);
            this.method_31329(true, new TranslatableText("gui.socialInteractions.hidden_in_chat", name));
         }, (_snowmanxxxx, _snowmanxxx, _snowmanxx, _snowmanxx) -> {
            this.field_26864 = this.field_26864 + client.getLastFrameDuration();
            if (this.field_26864 >= 10.0F) {
               parent.method_31354(() -> method_31328(parent, _snowmanxxx, this.hideTooltip, _snowmanxx, _snowmanx));
            }
         }, new TranslatableText("gui.socialInteractions.hide")) {
            @Override
            protected MutableText getNarrationMessage() {
               return SocialInteractionsPlayerListEntry.this.method_31389(super.getNarrationMessage());
            }
         };
         this.showButton = new TexturedButtonWidget(0, 0, 20, 20, 20, 38, 20, SocialInteractionsScreen.SOCIAL_INTERACTIONS_TEXTURE, 256, 256, _snowmanxx -> {
            _snowman.showPlayer(uuid);
            this.method_31329(false, new TranslatableText("gui.socialInteractions.shown_in_chat", name));
         }, (_snowmanxxxx, _snowmanxxx, _snowmanxx, _snowmanxx) -> {
            this.field_26864 = this.field_26864 + client.getLastFrameDuration();
            if (this.field_26864 >= 10.0F) {
               parent.method_31354(() -> method_31328(parent, _snowmanxxx, this.showTooltip, _snowmanxx, _snowmanx));
            }
         }, new TranslatableText("gui.socialInteractions.show")) {
            @Override
            protected MutableText getNarrationMessage() {
               return SocialInteractionsPlayerListEntry.this.method_31389(super.getNarrationMessage());
            }
         };
         this.showButton.visible = _snowmanx.isPlayerHidden(uuid);
         this.hideButton.visible = !this.showButton.visible;
         this.buttons = ImmutableList.of(this.hideButton, this.showButton);
      } else {
         this.buttons = ImmutableList.of();
      }
   }

   @Override
   public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
      int _snowman = x + 4;
      int _snowmanx = y + (entryHeight - 24) / 2;
      int _snowmanxx = _snowman + 24 + 4;
      Text _snowmanxxx = this.method_31390();
      int _snowmanxxxx;
      if (_snowmanxxx == LiteralText.EMPTY) {
         DrawableHelper.fill(matrices, x, y, x + entryWidth, y + entryHeight, field_26851);
         _snowmanxxxx = y + (entryHeight - 9) / 2;
      } else {
         DrawableHelper.fill(matrices, x, y, x + entryWidth, y + entryHeight, field_26852);
         _snowmanxxxx = y + (entryHeight - (9 + 9)) / 2;
         this.client.textRenderer.draw(matrices, _snowmanxxx, (float)_snowmanxx, (float)(_snowmanxxxx + 12), field_26903);
      }

      this.client.getTextureManager().bindTexture(this.field_26904.get());
      DrawableHelper.drawTexture(matrices, _snowman, _snowmanx, 24, 24, 8.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.enableBlend();
      DrawableHelper.drawTexture(matrices, _snowman, _snowmanx, 24, 24, 40.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.disableBlend();
      this.client.textRenderer.draw(matrices, this.name, (float)_snowmanxx, (float)_snowmanxxxx, field_26853);
      if (this.field_26859) {
         DrawableHelper.fill(matrices, _snowman, _snowmanx, _snowman + 24, _snowmanx + 24, field_26850);
      }

      if (this.hideButton != null && this.showButton != null) {
         float _snowmanxxxxx = this.field_26864;
         this.hideButton.x = x + (entryWidth - this.hideButton.getWidth() - 4);
         this.hideButton.y = y + (entryHeight - this.hideButton.getHeight()) / 2;
         this.hideButton.render(matrices, mouseX, mouseY, tickDelta);
         this.showButton.x = x + (entryWidth - this.showButton.getWidth() - 4);
         this.showButton.y = y + (entryHeight - this.showButton.getHeight()) / 2;
         this.showButton.render(matrices, mouseX, mouseY, tickDelta);
         if (_snowmanxxxxx == this.field_26864) {
            this.field_26864 = 0.0F;
         }
      }
   }

   @Override
   public List<? extends Element> children() {
      return this.buttons;
   }

   public String getName() {
      return this.name;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public void method_31335(boolean _snowman) {
      this.field_26859 = _snowman;
   }

   private void method_31329(boolean _snowman, Text _snowman) {
      this.showButton.visible = _snowman;
      this.hideButton.visible = !_snowman;
      this.client.inGameHud.getChatHud().addMessage(_snowman);
      NarratorManager.INSTANCE.narrate(_snowman.getString());
   }

   private MutableText method_31389(MutableText _snowman) {
      Text _snowmanx = this.method_31390();
      return _snowmanx == LiteralText.EMPTY
         ? new LiteralText(this.name).append(", ").append(_snowman)
         : new LiteralText(this.name).append(", ").append(_snowmanx).append(", ").append(_snowman);
   }

   private Text method_31390() {
      boolean _snowman = this.client.getSocialInteractionsManager().isPlayerHidden(this.uuid);
      boolean _snowmanx = this.client.getSocialInteractionsManager().isPlayerBlocked(this.uuid);
      if (_snowmanx && this.field_26859) {
         return field_26909;
      } else if (_snowman && this.field_26859) {
         return field_26908;
      } else if (_snowmanx) {
         return field_26906;
      } else if (_snowman) {
         return field_26905;
      } else {
         return this.field_26859 ? field_26907 : LiteralText.EMPTY;
      }
   }

   private static void method_31328(SocialInteractionsScreen _snowman, MatrixStack _snowman, List<OrderedText> _snowman, int _snowman, int _snowman) {
      _snowman.renderOrderedTooltip(_snowman, _snowman, _snowman, _snowman);
      _snowman.method_31354(null);
   }
}
