package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.stream.IntStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class SignEditScreen extends Screen {
   private final SignBlockEntityRenderer.SignModel model = new SignBlockEntityRenderer.SignModel();
   private final SignBlockEntity sign;
   private int ticksSinceOpened;
   private int currentRow;
   private SelectionManager selectionManager;
   private final String[] text;

   public SignEditScreen(SignBlockEntity sign) {
      super(new TranslatableText("sign.edit"));
      this.text = IntStream.range(0, 4).mapToObj(sign::getTextOnRow).map(Text::getString).toArray(String[]::new);
      this.sign = sign;
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, ScreenTexts.DONE, arg -> this.finishEditing()));
      this.sign.setEditable(false);
      this.selectionManager = new SelectionManager(
         () -> this.text[this.currentRow],
         string -> {
            this.text[this.currentRow] = string;
            this.sign.setTextOnRow(this.currentRow, new LiteralText(string));
         },
         SelectionManager.makeClipboardGetter(this.client),
         SelectionManager.makeClipboardSetter(this.client),
         string -> this.client.textRenderer.getWidth(string) <= 90
      );
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
      ClientPlayNetworkHandler lv = this.client.getNetworkHandler();
      if (lv != null) {
         lv.sendPacket(new UpdateSignC2SPacket(this.sign.getPos(), this.text[0], this.text[1], this.text[2], this.text[3]));
      }

      this.sign.setEditable(true);
   }

   @Override
   public void tick() {
      this.ticksSinceOpened++;
      if (!this.sign.getType().supports(this.sign.getCachedState().getBlock())) {
         this.finishEditing();
      }
   }

   private void finishEditing() {
      this.sign.markDirty();
      this.client.openScreen(null);
   }

   @Override
   public boolean charTyped(char chr, int keyCode) {
      this.selectionManager.insert(chr);
      return true;
   }

   @Override
   public void onClose() {
      this.finishEditing();
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 265) {
         this.currentRow = this.currentRow - 1 & 3;
         this.selectionManager.moveCaretToEnd();
         return true;
      } else if (keyCode == 264 || keyCode == 257 || keyCode == 335) {
         this.currentRow = this.currentRow + 1 & 3;
         this.selectionManager.moveCaretToEnd();
         return true;
      } else {
         return this.selectionManager.handleSpecialKey(keyCode) ? true : super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      DiffuseLighting.disableGuiDepthLighting();
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 40, 16777215);
      matrices.push();
      matrices.translate((double)(this.width / 2), 0.0, 50.0);
      float g = 93.75F;
      matrices.scale(93.75F, -93.75F, 93.75F);
      matrices.translate(0.0, -1.3125, 0.0);
      BlockState lv = this.sign.getCachedState();
      boolean bl = lv.getBlock() instanceof SignBlock;
      if (!bl) {
         matrices.translate(0.0, -0.3125, 0.0);
      }

      boolean bl2 = this.ticksSinceOpened / 6 % 2 == 0;
      float h = 0.6666667F;
      matrices.push();
      matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
      VertexConsumerProvider.Immediate lv2 = this.client.getBufferBuilders().getEntityVertexConsumers();
      SpriteIdentifier lv3 = SignBlockEntityRenderer.getModelTexture(lv.getBlock());
      VertexConsumer lv4 = lv3.getVertexConsumer(lv2, this.model::getLayer);
      this.model.field.render(matrices, lv4, 15728880, OverlayTexture.DEFAULT_UV);
      if (bl) {
         this.model.foot.render(matrices, lv4, 15728880, OverlayTexture.DEFAULT_UV);
      }

      matrices.pop();
      float k = 0.010416667F;
      matrices.translate(0.0, 0.33333334F, 0.046666667F);
      matrices.scale(0.010416667F, -0.010416667F, 0.010416667F);
      int l = this.sign.getTextColor().getSignColor();
      int m = this.selectionManager.getSelectionStart();
      int n = this.selectionManager.getSelectionEnd();
      int o = this.currentRow * 10 - this.text.length * 5;
      Matrix4f lv5 = matrices.peek().getModel();

      for (int p = 0; p < this.text.length; p++) {
         String string = this.text[p];
         if (string != null) {
            if (this.textRenderer.isRightToLeft()) {
               string = this.textRenderer.mirror(string);
            }

            float q = (float)(-this.client.textRenderer.getWidth(string) / 2);
            this.client.textRenderer.draw(string, q, (float)(p * 10 - this.text.length * 5), l, false, lv5, lv2, false, 0, 15728880, false);
            if (p == this.currentRow && m >= 0 && bl2) {
               int r = this.client.textRenderer.getWidth(string.substring(0, Math.max(Math.min(m, string.length()), 0)));
               int s = r - this.client.textRenderer.getWidth(string) / 2;
               if (m >= string.length()) {
                  this.client.textRenderer.draw("_", (float)s, (float)o, l, false, lv5, lv2, false, 0, 15728880, false);
               }
            }
         }
      }

      lv2.draw();

      for (int t = 0; t < this.text.length; t++) {
         String string2 = this.text[t];
         if (string2 != null && t == this.currentRow && m >= 0) {
            int u = this.client.textRenderer.getWidth(string2.substring(0, Math.max(Math.min(m, string2.length()), 0)));
            int v = u - this.client.textRenderer.getWidth(string2) / 2;
            if (bl2 && m < string2.length()) {
               fill(matrices, v, o - 1, v + 1, o + 9, 0xFF000000 | l);
            }

            if (n != m) {
               int w = Math.min(m, n);
               int x = Math.max(m, n);
               int y = this.client.textRenderer.getWidth(string2.substring(0, w)) - this.client.textRenderer.getWidth(string2) / 2;
               int z = this.client.textRenderer.getWidth(string2.substring(0, x)) - this.client.textRenderer.getWidth(string2) / 2;
               int aa = Math.min(y, z);
               int ab = Math.max(y, z);
               Tessellator lv6 = Tessellator.getInstance();
               BufferBuilder lv7 = lv6.getBuffer();
               RenderSystem.disableTexture();
               RenderSystem.enableColorLogicOp();
               RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
               lv7.begin(7, VertexFormats.POSITION_COLOR);
               lv7.vertex(lv5, (float)aa, (float)(o + 9), 0.0F).color(0, 0, 255, 255).next();
               lv7.vertex(lv5, (float)ab, (float)(o + 9), 0.0F).color(0, 0, 255, 255).next();
               lv7.vertex(lv5, (float)ab, (float)o, 0.0F).color(0, 0, 255, 255).next();
               lv7.vertex(lv5, (float)aa, (float)o, 0.0F).color(0, 0, 255, 255).next();
               lv7.end();
               BufferRenderer.draw(lv7);
               RenderSystem.disableColorLogicOp();
               RenderSystem.enableTexture();
            }
         }
      }

      matrices.pop();
      DiffuseLighting.enableGuiDepthLighting();
      super.render(matrices, mouseX, mouseY, delta);
   }
}
