package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.stream.IntStream;
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
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, ScreenTexts.DONE, _snowman -> this.finishEditing()));
      this.sign.setEditable(false);
      this.selectionManager = new SelectionManager(() -> this.text[this.currentRow], _snowman -> {
         this.text[this.currentRow] = _snowman;
         this.sign.setTextOnRow(this.currentRow, new LiteralText(_snowman));
      }, SelectionManager.makeClipboardGetter(this.client), SelectionManager.makeClipboardSetter(this.client), _snowman -> this.client.textRenderer.getWidth(_snowman) <= 90);
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
      ClientPlayNetworkHandler _snowman = this.client.getNetworkHandler();
      if (_snowman != null) {
         _snowman.sendPacket(new UpdateSignC2SPacket(this.sign.getPos(), this.text[0], this.text[1], this.text[2], this.text[3]));
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
      float _snowman = 93.75F;
      matrices.scale(93.75F, -93.75F, 93.75F);
      matrices.translate(0.0, -1.3125, 0.0);
      BlockState _snowmanx = this.sign.getCachedState();
      boolean _snowmanxx = _snowmanx.getBlock() instanceof SignBlock;
      if (!_snowmanxx) {
         matrices.translate(0.0, -0.3125, 0.0);
      }

      boolean _snowmanxxx = this.ticksSinceOpened / 6 % 2 == 0;
      float _snowmanxxxx = 0.6666667F;
      matrices.push();
      matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
      VertexConsumerProvider.Immediate _snowmanxxxxx = this.client.getBufferBuilders().getEntityVertexConsumers();
      SpriteIdentifier _snowmanxxxxxx = SignBlockEntityRenderer.getModelTexture(_snowmanx.getBlock());
      VertexConsumer _snowmanxxxxxxx = _snowmanxxxxxx.getVertexConsumer(_snowmanxxxxx, this.model::getLayer);
      this.model.field.render(matrices, _snowmanxxxxxxx, 15728880, OverlayTexture.DEFAULT_UV);
      if (_snowmanxx) {
         this.model.foot.render(matrices, _snowmanxxxxxxx, 15728880, OverlayTexture.DEFAULT_UV);
      }

      matrices.pop();
      float _snowmanxxxxxxxx = 0.010416667F;
      matrices.translate(0.0, 0.33333334F, 0.046666667F);
      matrices.scale(0.010416667F, -0.010416667F, 0.010416667F);
      int _snowmanxxxxxxxxx = this.sign.getTextColor().getSignColor();
      int _snowmanxxxxxxxxxx = this.selectionManager.getSelectionStart();
      int _snowmanxxxxxxxxxxx = this.selectionManager.getSelectionEnd();
      int _snowmanxxxxxxxxxxxx = this.currentRow * 10 - this.text.length * 5;
      Matrix4f _snowmanxxxxxxxxxxxxx = matrices.peek().getModel();

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < this.text.length; _snowmanxxxxxxxxxxxxxx++) {
         String _snowmanxxxxxxxxxxxxxxx = this.text[_snowmanxxxxxxxxxxxxxx];
         if (_snowmanxxxxxxxxxxxxxxx != null) {
            if (this.textRenderer.isRightToLeft()) {
               _snowmanxxxxxxxxxxxxxxx = this.textRenderer.mirror(_snowmanxxxxxxxxxxxxxxx);
            }

            float _snowmanxxxxxxxxxxxxxxxx = (float)(-this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxx) / 2);
            this.client
               .textRenderer
               .draw(
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  (float)(_snowmanxxxxxxxxxxxxxx * 10 - this.text.length * 5),
                  _snowmanxxxxxxxxx,
                  false,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxx,
                  false,
                  0,
                  15728880,
                  false
               );
            if (_snowmanxxxxxxxxxxxxxx == this.currentRow && _snowmanxxxxxxxxxx >= 0 && _snowmanxxx) {
               int _snowmanxxxxxxxxxxxxxxxxx = this.client
                  .textRenderer
                  .getWidth(_snowmanxxxxxxxxxxxxxxx.substring(0, Math.max(Math.min(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.length()), 0)));
               int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxx) / 2;
               if (_snowmanxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxxx.length()) {
                  this.client
                     .textRenderer
                     .draw("_", (float)_snowmanxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxx, false, _snowmanxxxxxxxxxxxxx, _snowmanxxxxx, false, 0, 15728880, false);
               }
            }
         }
      }

      _snowmanxxxxx.draw();

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.text.length; _snowmanxxxxxxxxxxxxxxx++) {
         String _snowmanxxxxxxxxxxxxxxxx = this.text[_snowmanxxxxxxxxxxxxxxx];
         if (_snowmanxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxx == this.currentRow && _snowmanxxxxxxxxxx >= 0) {
            int _snowmanxxxxxxxxxxxxxxxxx = this.client
               .textRenderer
               .getWidth(_snowmanxxxxxxxxxxxxxxxx.substring(0, Math.max(Math.min(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx.length()), 0)));
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx) / 2;
            if (_snowmanxxx && _snowmanxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx.length()) {
               fill(matrices, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxx + 9, 0xFF000000 | _snowmanxxxxxxxxx);
            }

            if (_snowmanxxxxxxxxxxx != _snowmanxxxxxxxxxx) {
               int _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxxxxxxxxxx = this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx.substring(0, _snowmanxxxxxxxxxxxxxxxxxxx))
                  - this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx) / 2;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx.substring(0, _snowmanxxxxxxxxxxxxxxxxxxxx))
                  - this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx) / 2;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
               Tessellator _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = Tessellator.getInstance();
               BufferBuilder _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.getBuffer();
               RenderSystem.disableTexture();
               RenderSystem.enableColorLogicOp();
               RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.begin(7, VertexFormats.POSITION_COLOR);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxxxxxxxxx + 9), 0.0F)
                  .color(0, 0, 255, 255)
                  .next();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxxxxxxxxx + 9), 0.0F)
                  .color(0, 0, 255, 255)
                  .next();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxx, 0.0F).color(0, 0, 255, 255).next();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxx, 0.0F).color(0, 0, 255, 255).next();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.end();
               BufferRenderer.draw(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx);
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
