package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int j = (this.width - this.backgroundWidth) / 2;
      int k = (this.height - this.backgroundHeight) / 2;

      for (int l = 0; l < 3; l++) {
         double f = mouseX - (double)(j + 60);
         double g = mouseY - (double)(k + 14 + 19 * l);
         if (f >= 0.0 && g >= 0.0 && f < 108.0 && g < 19.0 && this.handler.onButtonClick(this.client.player, l)) {
            this.client.interactionManager.clickButton(this.handler.syncId, l);
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
      int k = (this.width - this.backgroundWidth) / 2;
      int l = (this.height - this.backgroundHeight) / 2;
      this.drawTexture(matrices, k, l, 0, 0, this.backgroundWidth, this.backgroundHeight);
      RenderSystem.matrixMode(5889);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      int m = (int)this.client.getWindow().getScaleFactor();
      RenderSystem.viewport((this.width - 320) / 2 * m, (this.height - 240) / 2 * m, 320 * m, 240 * m);
      RenderSystem.translatef(-0.34F, 0.23F, 0.0F);
      RenderSystem.multMatrix(Matrix4f.viewboxMatrix(90.0, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.matrixMode(5888);
      matrices.push();
      MatrixStack.Entry lv = matrices.peek();
      lv.getModel().loadIdentity();
      lv.getNormal().loadIdentity();
      matrices.translate(0.0, 3.3F, 1984.0);
      float g = 5.0F;
      matrices.scale(5.0F, 5.0F, 5.0F);
      matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(20.0F));
      float h = MathHelper.lerp(delta, this.pageTurningSpeed, this.nextPageTurningSpeed);
      matrices.translate((double)((1.0F - h) * 0.2F), (double)((1.0F - h) * 0.1F), (double)((1.0F - h) * 0.25F));
      float n = -(1.0F - h) * 90.0F - 90.0F;
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(n));
      matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
      float o = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.25F;
      float p = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.75F;
      o = (o - (float)MathHelper.fastFloor((double)o)) * 1.6F - 0.3F;
      p = (p - (float)MathHelper.fastFloor((double)p)) * 1.6F - 0.3F;
      if (o < 0.0F) {
         o = 0.0F;
      }

      if (p < 0.0F) {
         p = 0.0F;
      }

      if (o > 1.0F) {
         o = 1.0F;
      }

      if (p > 1.0F) {
         p = 1.0F;
      }

      RenderSystem.enableRescaleNormal();
      BOOK_MODEL.setPageAngles(0.0F, o, p, h);
      VertexConsumerProvider.Immediate lv2 = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
      VertexConsumer lv3 = lv2.getBuffer(BOOK_MODEL.getLayer(BOOK_TEXTURE));
      BOOK_MODEL.render(matrices, lv3, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      lv2.draw();
      matrices.pop();
      RenderSystem.matrixMode(5889);
      RenderSystem.viewport(0, 0, this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
      DiffuseLighting.enableGuiDepthLighting();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantingPhrases.getInstance().setSeed((long)this.handler.getSeed());
      int q = this.handler.getLapisCount();

      for (int r = 0; r < 3; r++) {
         int s = k + 60;
         int t = s + 20;
         this.setZOffset(0);
         this.client.getTextureManager().bindTexture(TEXTURE);
         int u = this.handler.enchantmentPower[r];
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (u == 0) {
            this.drawTexture(matrices, s, l + 14 + 19 * r, 0, 185, 108, 19);
         } else {
            String string = "" + u;
            int v = 86 - this.textRenderer.getWidth(string);
            StringVisitable lv4 = EnchantingPhrases.getInstance().generatePhrase(this.textRenderer, v);
            int w = 6839882;
            if ((q < r + 1 || this.client.player.experienceLevel < u) && !this.client.player.abilities.creativeMode) {
               this.drawTexture(matrices, s, l + 14 + 19 * r, 0, 185, 108, 19);
               this.drawTexture(matrices, s + 1, l + 15 + 19 * r, 16 * r, 239, 16, 16);
               this.textRenderer.drawTrimmed(lv4, t, l + 16 + 19 * r, v, (w & 16711422) >> 1);
               w = 4226832;
            } else {
               int x = mouseX - (k + 60);
               int y = mouseY - (l + 14 + 19 * r);
               if (x >= 0 && y >= 0 && x < 108 && y < 19) {
                  this.drawTexture(matrices, s, l + 14 + 19 * r, 0, 204, 108, 19);
                  w = 16777088;
               } else {
                  this.drawTexture(matrices, s, l + 14 + 19 * r, 0, 166, 108, 19);
               }

               this.drawTexture(matrices, s + 1, l + 15 + 19 * r, 16 * r, 223, 16, 16);
               this.textRenderer.drawTrimmed(lv4, t, l + 16 + 19 * r, v, w);
               w = 8453920;
            }

            this.textRenderer.drawWithShadow(matrices, string, (float)(t + 86 - this.textRenderer.getWidth(string)), (float)(l + 16 + 19 * r + 7), w);
         }
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      delta = this.client.getTickDelta();
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
      boolean bl = this.client.player.abilities.creativeMode;
      int k = this.handler.getLapisCount();

      for (int l = 0; l < 3; l++) {
         int m = this.handler.enchantmentPower[l];
         Enchantment lv = Enchantment.byRawId(this.handler.enchantmentId[l]);
         int n = this.handler.enchantmentLevel[l];
         int o = l + 1;
         if (this.isPointWithinBounds(60, 14 + 19 * l, 108, 17, (double)mouseX, (double)mouseY) && m > 0 && n >= 0 && lv != null) {
            List<Text> list = Lists.newArrayList();
            list.add(new TranslatableText("container.enchant.clue", lv.getName(n)).formatted(Formatting.WHITE));
            if (!bl) {
               list.add(LiteralText.EMPTY);
               if (this.client.player.experienceLevel < m) {
                  list.add(new TranslatableText("container.enchant.level.requirement", this.handler.enchantmentPower[l]).formatted(Formatting.RED));
               } else {
                  MutableText lv2;
                  if (o == 1) {
                     lv2 = new TranslatableText("container.enchant.lapis.one");
                  } else {
                     lv2 = new TranslatableText("container.enchant.lapis.many", o);
                  }

                  list.add(lv2.formatted(k >= o ? Formatting.GRAY : Formatting.RED));
                  MutableText lv4;
                  if (o == 1) {
                     lv4 = new TranslatableText("container.enchant.level.one");
                  } else {
                     lv4 = new TranslatableText("container.enchant.level.many", o);
                  }

                  list.add(lv4.formatted(Formatting.GRAY));
               }
            }

            this.renderTooltip(matrices, list, mouseX, mouseY);
            break;
         }
      }
   }

   public void doTick() {
      ItemStack lv = this.handler.getSlot(0).getStack();
      if (!ItemStack.areEqual(lv, this.stack)) {
         this.stack = lv;

         do {
            this.approximatePageAngle = this.approximatePageAngle + (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while (this.nextPageAngle <= this.approximatePageAngle + 1.0F && this.nextPageAngle >= this.approximatePageAngle - 1.0F);
      }

      this.ticks++;
      this.pageAngle = this.nextPageAngle;
      this.pageTurningSpeed = this.nextPageTurningSpeed;
      boolean bl = false;

      for (int i = 0; i < 3; i++) {
         if (this.handler.enchantmentPower[i] != 0) {
            bl = true;
         }
      }

      if (bl) {
         this.nextPageTurningSpeed += 0.2F;
      } else {
         this.nextPageTurningSpeed -= 0.2F;
      }

      this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
      float f = (this.approximatePageAngle - this.nextPageAngle) * 0.4F;
      float g = 0.2F;
      f = MathHelper.clamp(f, -0.2F, 0.2F);
      this.pageRotationSpeed = this.pageRotationSpeed + (f - this.pageRotationSpeed) * 0.9F;
      this.nextPageAngle = this.nextPageAngle + this.pageRotationSpeed;
   }
}
