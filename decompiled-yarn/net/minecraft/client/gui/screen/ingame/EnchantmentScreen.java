package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

public class EnchantmentScreen extends HandledScreen<EnchantmentScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/enchanting_table.png");
   private static final Identifier BOOK_TEXTURE = new Identifier("textures/entity/enchanting_table_book.png");
   private static final BookModel BOOK_MODEL = new BookModel();
   private final Random random = new Random();
   public int ticks;
   public float nextPageAngle;
   public float pageAngle;
   public float approximatePageAngle;
   public float pageRotationSpeed;
   public float nextPageTurningSpeed;
   public float pageTurningSpeed;
   private ItemStack stack = ItemStack.EMPTY;

   public EnchantmentScreen(EnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
   }

   @Override
   public void tick() {
      super.tick();
      this.doTick();
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         double _snowmanxxx = mouseX - (double)(_snowman + 60);
         double _snowmanxxxx = mouseY - (double)(_snowmanx + 14 + 19 * _snowmanxx);
         if (_snowmanxxx >= 0.0 && _snowmanxxxx >= 0.0 && _snowmanxxx < 108.0 && _snowmanxxxx < 19.0 && this.handler.onButtonClick(this.client.player, _snowmanxx)) {
            this.client.interactionManager.clickButton(this.handler.syncId, _snowmanxx);
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      DiffuseLighting.disableGuiDepthLighting();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, this.backgroundWidth, this.backgroundHeight);
      RenderSystem.matrixMode(5889);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      int _snowmanxx = (int)this.client.getWindow().getScaleFactor();
      RenderSystem.viewport((this.width - 320) / 2 * _snowmanxx, (this.height - 240) / 2 * _snowmanxx, 320 * _snowmanxx, 240 * _snowmanxx);
      RenderSystem.translatef(-0.34F, 0.23F, 0.0F);
      RenderSystem.multMatrix(Matrix4f.viewboxMatrix(90.0, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.matrixMode(5888);
      matrices.push();
      MatrixStack.Entry _snowmanxxx = matrices.peek();
      _snowmanxxx.getModel().loadIdentity();
      _snowmanxxx.getNormal().loadIdentity();
      matrices.translate(0.0, 3.3F, 1984.0);
      float _snowmanxxxx = 5.0F;
      matrices.scale(5.0F, 5.0F, 5.0F);
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(20.0F));
      float _snowmanxxxxx = MathHelper.lerp(delta, this.pageTurningSpeed, this.nextPageTurningSpeed);
      matrices.translate((double)((1.0F - _snowmanxxxxx) * 0.2F), (double)((1.0F - _snowmanxxxxx) * 0.1F), (double)((1.0F - _snowmanxxxxx) * 0.25F));
      float _snowmanxxxxxx = -(1.0F - _snowmanxxxxx) * 90.0F - 90.0F;
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxx));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
      float _snowmanxxxxxxx = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.25F;
      float _snowmanxxxxxxxx = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.75F;
      _snowmanxxxxxxx = (_snowmanxxxxxxx - (float)MathHelper.fastFloor((double)_snowmanxxxxxxx)) * 1.6F - 0.3F;
      _snowmanxxxxxxxx = (_snowmanxxxxxxxx - (float)MathHelper.fastFloor((double)_snowmanxxxxxxxx)) * 1.6F - 0.3F;
      if (_snowmanxxxxxxx < 0.0F) {
         _snowmanxxxxxxx = 0.0F;
      }

      if (_snowmanxxxxxxxx < 0.0F) {
         _snowmanxxxxxxxx = 0.0F;
      }

      if (_snowmanxxxxxxx > 1.0F) {
         _snowmanxxxxxxx = 1.0F;
      }

      if (_snowmanxxxxxxxx > 1.0F) {
         _snowmanxxxxxxxx = 1.0F;
      }

      RenderSystem.enableRescaleNormal();
      BOOK_MODEL.setPageAngles(0.0F, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx);
      VertexConsumerProvider.Immediate _snowmanxxxxxxxxx = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
      VertexConsumer _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getBuffer(BOOK_MODEL.getLayer(BOOK_TEXTURE));
      BOOK_MODEL.render(matrices, _snowmanxxxxxxxxxx, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowmanxxxxxxxxx.draw();
      matrices.pop();
      RenderSystem.matrixMode(5889);
      RenderSystem.viewport(0, 0, this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
      DiffuseLighting.enableGuiDepthLighting();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantingPhrases.getInstance().setSeed((long)this.handler.getSeed());
      int _snowmanxxxxxxxxxxx = this.handler.getLapisCount();

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxx++) {
         int _snowmanxxxxxxxxxxxxx = _snowman + 60;
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx + 20;
         this.setZOffset(0);
         this.client.getTextureManager().bindTexture(TEXTURE);
         int _snowmanxxxxxxxxxxxxxxx = this.handler.enchantmentPower[_snowmanxxxxxxxxxxxx];
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (_snowmanxxxxxxxxxxxxxxx == 0) {
            this.drawTexture(matrices, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 185, 108, 19);
         } else {
            String _snowmanxxxxxxxxxxxxxxxx = "" + _snowmanxxxxxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxxx = 86 - this.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx);
            StringVisitable _snowmanxxxxxxxxxxxxxxxxxx = EnchantingPhrases.getInstance().generatePhrase(this.textRenderer, _snowmanxxxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxx = 6839882;
            if ((_snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxxxx + 1 || this.client.player.experienceLevel < _snowmanxxxxxxxxxxxxxxx) && !this.client.player.abilities.creativeMode) {
               this.drawTexture(matrices, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 185, 108, 19);
               this.drawTexture(matrices, _snowmanxxxxxxxxxxxxx + 1, _snowmanx + 15 + 19 * _snowmanxxxxxxxxxxxx, 16 * _snowmanxxxxxxxxxxxx, 239, 16, 16);
               this.textRenderer
                  .drawTrimmed(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanx + 16 + 19 * _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxx & 16711422) >> 1);
               _snowmanxxxxxxxxxxxxxxxxxxx = 4226832;
            } else {
               int _snowmanxxxxxxxxxxxxxxxxxxxx = mouseX - (_snowman + 60);
               int _snowmanxxxxxxxxxxxxxxxxxxxxx = mouseY - (_snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxxxxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxxxxxxxxxxxx < 108 && _snowmanxxxxxxxxxxxxxxxxxxxxx < 19) {
                  this.drawTexture(matrices, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 204, 108, 19);
                  _snowmanxxxxxxxxxxxxxxxxxxx = 16777088;
               } else {
                  this.drawTexture(matrices, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 166, 108, 19);
               }

               this.drawTexture(matrices, _snowmanxxxxxxxxxxxxx + 1, _snowmanx + 15 + 19 * _snowmanxxxxxxxxxxxx, 16 * _snowmanxxxxxxxxxxxx, 223, 16, 16);
               this.textRenderer.drawTrimmed(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanx + 16 + 19 * _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
               _snowmanxxxxxxxxxxxxxxxxxxx = 8453920;
            }

            this.textRenderer
               .drawWithShadow(
                  matrices,
                  _snowmanxxxxxxxxxxxxxxxx,
                  (float)(_snowmanxxxxxxxxxxxxxx + 86 - this.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxx)),
                  (float)(_snowmanx + 16 + 19 * _snowmanxxxxxxxxxxxx + 7),
                  _snowmanxxxxxxxxxxxxxxxxxxx
               );
         }
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      delta = this.client.getTickDelta();
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
      boolean _snowman = this.client.player.abilities.creativeMode;
      int _snowmanx = this.handler.getLapisCount();

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         int _snowmanxxx = this.handler.enchantmentPower[_snowmanxx];
         Enchantment _snowmanxxxx = Enchantment.byRawId(this.handler.enchantmentId[_snowmanxx]);
         int _snowmanxxxxx = this.handler.enchantmentLevel[_snowmanxx];
         int _snowmanxxxxxx = _snowmanxx + 1;
         if (this.isPointWithinBounds(60, 14 + 19 * _snowmanxx, 108, 17, (double)mouseX, (double)mouseY) && _snowmanxxx > 0 && _snowmanxxxxx >= 0 && _snowmanxxxx != null) {
            List<Text> _snowmanxxxxxxx = Lists.newArrayList();
            _snowmanxxxxxxx.add(new TranslatableText("container.enchant.clue", _snowmanxxxx.getName(_snowmanxxxxx)).formatted(Formatting.WHITE));
            if (!_snowman) {
               _snowmanxxxxxxx.add(LiteralText.EMPTY);
               if (this.client.player.experienceLevel < _snowmanxxx) {
                  _snowmanxxxxxxx.add(new TranslatableText("container.enchant.level.requirement", this.handler.enchantmentPower[_snowmanxx]).formatted(Formatting.RED));
               } else {
                  MutableText _snowmanxxxxxxxx;
                  if (_snowmanxxxxxx == 1) {
                     _snowmanxxxxxxxx = new TranslatableText("container.enchant.lapis.one");
                  } else {
                     _snowmanxxxxxxxx = new TranslatableText("container.enchant.lapis.many", _snowmanxxxxxx);
                  }

                  _snowmanxxxxxxx.add(_snowmanxxxxxxxx.formatted(_snowmanx >= _snowmanxxxxxx ? Formatting.GRAY : Formatting.RED));
                  MutableText _snowmanxxxxxxxxx;
                  if (_snowmanxxxxxx == 1) {
                     _snowmanxxxxxxxxx = new TranslatableText("container.enchant.level.one");
                  } else {
                     _snowmanxxxxxxxxx = new TranslatableText("container.enchant.level.many", _snowmanxxxxxx);
                  }

                  _snowmanxxxxxxx.add(_snowmanxxxxxxxxx.formatted(Formatting.GRAY));
               }
            }

            this.renderTooltip(matrices, _snowmanxxxxxxx, mouseX, mouseY);
            break;
         }
      }
   }

   public void doTick() {
      ItemStack _snowman = this.handler.getSlot(0).getStack();
      if (!ItemStack.areEqual(_snowman, this.stack)) {
         this.stack = _snowman;

         do {
            this.approximatePageAngle = this.approximatePageAngle + (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while (this.nextPageAngle <= this.approximatePageAngle + 1.0F && this.nextPageAngle >= this.approximatePageAngle - 1.0F);
      }

      this.ticks++;
      this.pageAngle = this.nextPageAngle;
      this.pageTurningSpeed = this.nextPageTurningSpeed;
      boolean _snowmanx = false;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         if (this.handler.enchantmentPower[_snowmanxx] != 0) {
            _snowmanx = true;
         }
      }

      if (_snowmanx) {
         this.nextPageTurningSpeed += 0.2F;
      } else {
         this.nextPageTurningSpeed -= 0.2F;
      }

      this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
      float _snowmanxxx = (this.approximatePageAngle - this.nextPageAngle) * 0.4F;
      float _snowmanxxxx = 0.2F;
      _snowmanxxx = MathHelper.clamp(_snowmanxxx, -0.2F, 0.2F);
      this.pageRotationSpeed = this.pageRotationSpeed + (_snowmanxxx - this.pageRotationSpeed) * 0.9F;
      this.nextPageAngle = this.nextPageAngle + this.pageRotationSpeed;
   }
}
