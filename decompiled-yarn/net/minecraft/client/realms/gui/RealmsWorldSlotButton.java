package net.minecraft.client.realms.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsWorldOptions;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RealmsWorldSlotButton extends ButtonWidget implements TickableElement {
   public static final Identifier SLOT_FRAME = new Identifier("realms", "textures/gui/realms/slot_frame.png");
   public static final Identifier EMPTY_FRAME = new Identifier("realms", "textures/gui/realms/empty_frame.png");
   public static final Identifier PANORAMA_0 = new Identifier("minecraft", "textures/gui/title/background/panorama_0.png");
   public static final Identifier PANORAMA_2 = new Identifier("minecraft", "textures/gui/title/background/panorama_2.png");
   public static final Identifier PANORAMA_3 = new Identifier("minecraft", "textures/gui/title/background/panorama_3.png");
   private static final Text field_26468 = new TranslatableText("mco.configure.world.slot.tooltip.active");
   private static final Text field_26469 = new TranslatableText("mco.configure.world.slot.tooltip.minigame");
   private static final Text field_26470 = new TranslatableText("mco.configure.world.slot.tooltip");
   private final Supplier<RealmsServer> serverDataProvider;
   private final Consumer<Text> toolTipSetter;
   private final int slotIndex;
   private int animTick;
   @Nullable
   private RealmsWorldSlotButton.State state;

   public RealmsWorldSlotButton(
      int x, int y, int width, int height, Supplier<RealmsServer> serverDataProvider, Consumer<Text> toolTipSetter, int id, ButtonWidget.PressAction action
   ) {
      super(x, y, width, height, LiteralText.EMPTY, action);
      this.serverDataProvider = serverDataProvider;
      this.slotIndex = id;
      this.toolTipSetter = toolTipSetter;
   }

   @Nullable
   public RealmsWorldSlotButton.State getState() {
      return this.state;
   }

   @Override
   public void tick() {
      this.animTick++;
      RealmsServer _snowman = this.serverDataProvider.get();
      if (_snowman != null) {
         RealmsWorldOptions _snowmanx = _snowman.slots.get(this.slotIndex);
         boolean _snowmanxx = this.slotIndex == 4;
         boolean _snowmanxxx;
         String _snowmanxxxx;
         long _snowmanxxxxx;
         String _snowmanxxxxxx;
         boolean _snowmanxxxxxxx;
         if (_snowmanxx) {
            _snowmanxxx = _snowman.worldType == RealmsServer.WorldType.MINIGAME;
            _snowmanxxxx = "Minigame";
            _snowmanxxxxx = (long)_snowman.minigameId;
            _snowmanxxxxxx = _snowman.minigameImage;
            _snowmanxxxxxxx = _snowman.minigameId == -1;
         } else {
            _snowmanxxx = _snowman.activeSlot == this.slotIndex && _snowman.worldType != RealmsServer.WorldType.MINIGAME;
            _snowmanxxxx = _snowmanx.getSlotName(this.slotIndex);
            _snowmanxxxxx = _snowmanx.templateId;
            _snowmanxxxxxx = _snowmanx.templateImage;
            _snowmanxxxxxxx = _snowmanx.empty;
         }

         RealmsWorldSlotButton.Action _snowmanxxxxxxxx = method_27455(_snowman, _snowmanxxx, _snowmanxx);
         Pair<Text, Text> _snowmanxxxxxxxxx = this.method_27454(_snowman, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxx, _snowmanxxxxxxxx);
         this.state = new RealmsWorldSlotButton.State(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxx, _snowmanxxxxxxxx, (Text)_snowmanxxxxxxxxx.getFirst());
         this.setMessage((Text)_snowmanxxxxxxxxx.getSecond());
      }
   }

   private static RealmsWorldSlotButton.Action method_27455(RealmsServer _snowman, boolean _snowman, boolean _snowman) {
      if (_snowman) {
         if (!_snowman.expired && _snowman.state != RealmsServer.State.UNINITIALIZED) {
            return RealmsWorldSlotButton.Action.JOIN;
         }
      } else {
         if (!_snowman) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }

         if (!_snowman.expired) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }
      }

      return RealmsWorldSlotButton.Action.NOTHING;
   }

   private Pair<Text, Text> method_27454(RealmsServer _snowman, String _snowman, boolean _snowman, boolean _snowman, RealmsWorldSlotButton.Action _snowman) {
      if (_snowman == RealmsWorldSlotButton.Action.NOTHING) {
         return Pair.of(null, new LiteralText(_snowman));
      } else {
         Text _snowmanxxxxx;
         if (_snowman) {
            if (_snowman) {
               _snowmanxxxxx = LiteralText.EMPTY;
            } else {
               _snowmanxxxxx = new LiteralText(" ").append(_snowman).append(" ").append(_snowman.minigameName);
            }
         } else {
            _snowmanxxxxx = new LiteralText(" ").append(_snowman);
         }

         Text _snowmanxxxxxx;
         if (_snowman == RealmsWorldSlotButton.Action.JOIN) {
            _snowmanxxxxxx = field_26468;
         } else {
            _snowmanxxxxxx = _snowman ? field_26469 : field_26470;
         }

         Text _snowmanxxxxxxx = _snowmanxxxxxx.shallowCopy().append(_snowmanxxxxx);
         return Pair.of(_snowmanxxxxxx, _snowmanxxxxxxx);
      }
   }

   @Override
   public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.state != null) {
         this.drawSlotFrame(
            matrices,
            this.x,
            this.y,
            mouseX,
            mouseY,
            this.state.isCurrentlyActiveSlot,
            this.state.slotName,
            this.slotIndex,
            this.state.imageId,
            this.state.image,
            this.state.empty,
            this.state.minigame,
            this.state.action,
            this.state.actionPrompt
         );
      }
   }

   private void drawSlotFrame(
      MatrixStack matrices,
      int x,
      int y,
      int mouseX,
      int mouseY,
      boolean _snowman,
      String text,
      int _snowman,
      long _snowman,
      @Nullable String _snowman,
      boolean _snowman,
      boolean _snowman,
      RealmsWorldSlotButton.Action _snowman,
      @Nullable Text _snowman
   ) {
      boolean _snowmanxxxxxxxx = this.isHovered();
      if (this.isMouseOver((double)mouseX, (double)mouseY) && _snowman != null) {
         this.toolTipSetter.accept(_snowman);
      }

      MinecraftClient _snowmanxxxxxxxxx = MinecraftClient.getInstance();
      TextureManager _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getTextureManager();
      if (_snowman) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(_snowman), _snowman);
      } else if (_snowman) {
         _snowmanxxxxxxxxxx.bindTexture(EMPTY_FRAME);
      } else if (_snowman != null && _snowman != -1L) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(_snowman), _snowman);
      } else if (_snowman == 1) {
         _snowmanxxxxxxxxxx.bindTexture(PANORAMA_0);
      } else if (_snowman == 2) {
         _snowmanxxxxxxxxxx.bindTexture(PANORAMA_2);
      } else if (_snowman == 3) {
         _snowmanxxxxxxxxxx.bindTexture(PANORAMA_3);
      }

      if (_snowman) {
         float _snowmanxxxxxxxxxxx = 0.85F + 0.15F * MathHelper.cos((float)this.animTick * 0.2F);
         RenderSystem.color4f(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      drawTexture(matrices, x + 3, y + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      _snowmanxxxxxxxxxx.bindTexture(SLOT_FRAME);
      boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx && _snowman != RealmsWorldSlotButton.Action.NOTHING;
      if (_snowmanxxxxxxxxxxx) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else if (_snowman) {
         RenderSystem.color4f(0.8F, 0.8F, 0.8F, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      drawTexture(matrices, x, y, 0.0F, 0.0F, 80, 80, 80, 80);
      drawCenteredString(matrices, _snowmanxxxxxxxxx.textRenderer, text, x + 40, y + 66, 16777215);
   }

   public static enum Action {
      NOTHING,
      SWITCH_SLOT,
      JOIN;

      private Action() {
      }
   }

   public static class State {
      private final boolean isCurrentlyActiveSlot;
      private final String slotName;
      private final long imageId;
      private final String image;
      public final boolean empty;
      public final boolean minigame;
      public final RealmsWorldSlotButton.Action action;
      @Nullable
      private final Text actionPrompt;

      State(
         boolean isCurrentlyActiveSlot,
         String slotName,
         long imageId,
         @Nullable String image,
         boolean empty,
         boolean minigame,
         RealmsWorldSlotButton.Action action,
         @Nullable Text actionPrompt
      ) {
         this.isCurrentlyActiveSlot = isCurrentlyActiveSlot;
         this.slotName = slotName;
         this.imageId = imageId;
         this.image = image;
         this.empty = empty;
         this.minigame = minigame;
         this.action = action;
         this.actionPrompt = actionPrompt;
      }
   }
}
