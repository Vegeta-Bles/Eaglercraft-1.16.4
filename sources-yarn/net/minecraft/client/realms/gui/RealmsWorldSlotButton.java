package net.minecraft.client.realms.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      RealmsServer lv = this.serverDataProvider.get();
      if (lv != null) {
         RealmsWorldOptions lv2 = lv.slots.get(this.slotIndex);
         boolean bl = this.slotIndex == 4;
         boolean bl2;
         String string;
         long l;
         String string2;
         boolean bl3;
         if (bl) {
            bl2 = lv.worldType == RealmsServer.WorldType.MINIGAME;
            string = "Minigame";
            l = (long)lv.minigameId;
            string2 = lv.minigameImage;
            bl3 = lv.minigameId == -1;
         } else {
            bl2 = lv.activeSlot == this.slotIndex && lv.worldType != RealmsServer.WorldType.MINIGAME;
            string = lv2.getSlotName(this.slotIndex);
            l = lv2.templateId;
            string2 = lv2.templateImage;
            bl3 = lv2.empty;
         }

         RealmsWorldSlotButton.Action lv3 = method_27455(lv, bl2, bl);
         Pair<Text, Text> pair = this.method_27454(lv, string, bl3, bl, lv3);
         this.state = new RealmsWorldSlotButton.State(bl2, string, l, string2, bl3, bl, lv3, (Text)pair.getFirst());
         this.setMessage((Text)pair.getSecond());
      }
   }

   private static RealmsWorldSlotButton.Action method_27455(RealmsServer arg, boolean bl, boolean bl2) {
      if (bl) {
         if (!arg.expired && arg.state != RealmsServer.State.UNINITIALIZED) {
            return RealmsWorldSlotButton.Action.JOIN;
         }
      } else {
         if (!bl2) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }

         if (!arg.expired) {
            return RealmsWorldSlotButton.Action.SWITCH_SLOT;
         }
      }

      return RealmsWorldSlotButton.Action.NOTHING;
   }

   private Pair<Text, Text> method_27454(RealmsServer arg, String string, boolean bl, boolean bl2, RealmsWorldSlotButton.Action arg2) {
      if (arg2 == RealmsWorldSlotButton.Action.NOTHING) {
         return Pair.of(null, new LiteralText(string));
      } else {
         Text lv;
         if (bl2) {
            if (bl) {
               lv = LiteralText.EMPTY;
            } else {
               lv = new LiteralText(" ").append(string).append(" ").append(arg.minigameName);
            }
         } else {
            lv = new LiteralText(" ").append(string);
         }

         Text lv4;
         if (arg2 == RealmsWorldSlotButton.Action.JOIN) {
            lv4 = field_26468;
         } else {
            lv4 = bl2 ? field_26469 : field_26470;
         }

         Text lv6 = lv4.shallowCopy().append(lv);
         return Pair.of(lv4, lv6);
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
      boolean bl,
      String text,
      int m,
      long n,
      @Nullable String string2,
      boolean bl2,
      boolean bl3,
      RealmsWorldSlotButton.Action arg2,
      @Nullable Text arg3
   ) {
      boolean bl4 = this.isHovered();
      if (this.isMouseOver((double)mouseX, (double)mouseY) && arg3 != null) {
         this.toolTipSetter.accept(arg3);
      }

      MinecraftClient lv = MinecraftClient.getInstance();
      TextureManager lv2 = lv.getTextureManager();
      if (bl3) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(n), string2);
      } else if (bl2) {
         lv2.bindTexture(EMPTY_FRAME);
      } else if (string2 != null && n != -1L) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(n), string2);
      } else if (m == 1) {
         lv2.bindTexture(PANORAMA_0);
      } else if (m == 2) {
         lv2.bindTexture(PANORAMA_2);
      } else if (m == 3) {
         lv2.bindTexture(PANORAMA_3);
      }

      if (bl) {
         float f = 0.85F + 0.15F * MathHelper.cos((float)this.animTick * 0.2F);
         RenderSystem.color4f(f, f, f, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      drawTexture(matrices, x + 3, y + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      lv2.bindTexture(SLOT_FRAME);
      boolean bl5 = bl4 && arg2 != RealmsWorldSlotButton.Action.NOTHING;
      if (bl5) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else if (bl) {
         RenderSystem.color4f(0.8F, 0.8F, 0.8F, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      drawTexture(matrices, x, y, 0.0F, 0.0F, 80, 80, 80, 80);
      drawCenteredString(matrices, lv.textRenderer, text, x + 40, y + 66, 16777215);
   }

   @Environment(EnvType.CLIENT)
   public static enum Action {
      NOTHING,
      SWITCH_SLOT,
      JOIN;

      private Action() {
      }
   }

   @Environment(EnvType.CLIENT)
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
