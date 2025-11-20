package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreditsScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier MINECRAFT_TITLE_TEXTURE = new Identifier("textures/gui/title/minecraft.png");
   private static final Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");
   private static final Identifier VIGNETTE_TEXTURE = new Identifier("textures/misc/vignette.png");
   private static final String OBFUSCATION_PLACEHOLDER = "" + Formatting.WHITE + Formatting.OBFUSCATED + Formatting.GREEN + Formatting.AQUA;
   private final boolean endCredits;
   private final Runnable finishAction;
   private float time;
   private List<OrderedText> credits;
   private IntSet centeredLines;
   private int creditsHeight;
   private float speed = 0.5F;

   public CreditsScreen(boolean endCredits, Runnable finishAction) {
      super(NarratorManager.EMPTY);
      this.endCredits = endCredits;
      this.finishAction = finishAction;
      if (!endCredits) {
         this.speed = 0.75F;
      }
   }

   @Override
   public void tick() {
      this.client.getMusicTracker().tick();
      this.client.getSoundManager().tick(false);
      float _snowman = (float)(this.creditsHeight + this.height + this.height + 24) / this.speed;
      if (this.time > _snowman) {
         this.close();
      }
   }

   @Override
   public void onClose() {
      this.close();
   }

   private void close() {
      this.finishAction.run();
      this.client.openScreen(null);
   }

   @Override
   protected void init() {
      if (this.credits == null) {
         this.credits = Lists.newArrayList();
         this.centeredLines = new IntOpenHashSet();
         Resource _snowman = null;

         try {
            int _snowmanx = 274;
            if (this.endCredits) {
               _snowman = this.client.getResourceManager().getResource(new Identifier("texts/end.txt"));
               InputStream _snowmanxx = _snowman.getInputStream();
               BufferedReader _snowmanxxx = new BufferedReader(new InputStreamReader(_snowmanxx, StandardCharsets.UTF_8));
               Random _snowmanxxxx = new Random(8124371L);

               String _snowmanxxxxx;
               while ((_snowmanxxxxx = _snowmanxxx.readLine()) != null) {
                  _snowmanxxxxx = _snowmanxxxxx.replaceAll("PLAYERNAME", this.client.getSession().getUsername());

                  int _snowmanxxxxxx;
                  while ((_snowmanxxxxxx = _snowmanxxxxx.indexOf(OBFUSCATION_PLACEHOLDER)) != -1) {
                     String _snowmanxxxxxxx = _snowmanxxxxx.substring(0, _snowmanxxxxxx);
                     String _snowmanxxxxxxxx = _snowmanxxxxx.substring(_snowmanxxxxxx + OBFUSCATION_PLACEHOLDER.length());
                     _snowmanxxxxx = _snowmanxxxxxxx + Formatting.WHITE + Formatting.OBFUSCATED + "XXXXXXXX".substring(0, _snowmanxxxx.nextInt(4) + 3) + _snowmanxxxxxxxx;
                  }

                  this.credits.addAll(this.client.textRenderer.wrapLines(new LiteralText(_snowmanxxxxx), 274));
                  this.credits.add(OrderedText.EMPTY);
               }

               _snowmanxx.close();

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 8; _snowmanxxxxxx++) {
                  this.credits.add(OrderedText.EMPTY);
               }
            }

            InputStream _snowmanxx = this.client.getResourceManager().getResource(new Identifier("texts/credits.txt")).getInputStream();
            BufferedReader _snowmanxxx = new BufferedReader(new InputStreamReader(_snowmanxx, StandardCharsets.UTF_8));

            String _snowmanxxxx;
            while ((_snowmanxxxx = _snowmanxxx.readLine()) != null) {
               _snowmanxxxx = _snowmanxxxx.replaceAll("PLAYERNAME", this.client.getSession().getUsername());
               _snowmanxxxx = _snowmanxxxx.replaceAll("\t", "    ");
               boolean _snowmanxxxxx;
               if (_snowmanxxxx.startsWith("[C]")) {
                  _snowmanxxxx = _snowmanxxxx.substring(3);
                  _snowmanxxxxx = true;
               } else {
                  _snowmanxxxxx = false;
               }

               for (OrderedText _snowmanxxxxxx : this.client.textRenderer.wrapLines(new LiteralText(_snowmanxxxx), 274)) {
                  if (_snowmanxxxxx) {
                     this.centeredLines.add(this.credits.size());
                  }

                  this.credits.add(_snowmanxxxxxx);
               }

               this.credits.add(OrderedText.EMPTY);
            }

            _snowmanxx.close();
            this.creditsHeight = this.credits.size() * 12;
         } catch (Exception var13) {
            LOGGER.error("Couldn't load credits", var13);
         } finally {
            IOUtils.closeQuietly(_snowman);
         }
      }
   }

   private void renderBackground(int mouseX, int mouseY, float tickDelta) {
      this.client.getTextureManager().bindTexture(DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
      int _snowman = this.width;
      float _snowmanx = -this.time * 0.5F * this.speed;
      float _snowmanxx = (float)this.height - this.time * 0.5F * this.speed;
      float _snowmanxxx = 0.015625F;
      float _snowmanxxxx = this.time * 0.02F;
      float _snowmanxxxxx = (float)(this.creditsHeight + this.height + this.height + 24) / this.speed;
      float _snowmanxxxxxx = (_snowmanxxxxx - 20.0F - this.time) * 0.005F;
      if (_snowmanxxxxxx < _snowmanxxxx) {
         _snowmanxxxx = _snowmanxxxxxx;
      }

      if (_snowmanxxxx > 1.0F) {
         _snowmanxxxx = 1.0F;
      }

      _snowmanxxxx *= _snowmanxxxx;
      _snowmanxxxx = _snowmanxxxx * 96.0F / 255.0F;
      Tessellator _snowmanxxxxxxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxxxxxxx = _snowmanxxxxxxx.getBuffer();
      _snowmanxxxxxxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
      _snowmanxxxxxxxx.vertex(0.0, (double)this.height, (double)this.getZOffset()).texture(0.0F, _snowmanx * 0.015625F).color(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F).next();
      _snowmanxxxxxxxx.vertex((double)_snowman, (double)this.height, (double)this.getZOffset())
         .texture((float)_snowman * 0.015625F, _snowmanx * 0.015625F)
         .color(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F)
         .next();
      _snowmanxxxxxxxx.vertex((double)_snowman, 0.0, (double)this.getZOffset()).texture((float)_snowman * 0.015625F, _snowmanxx * 0.015625F).color(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F).next();
      _snowmanxxxxxxxx.vertex(0.0, 0.0, (double)this.getZOffset()).texture(0.0F, _snowmanxx * 0.015625F).color(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F).next();
      _snowmanxxxxxxx.draw();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(mouseX, mouseY, delta);
      int _snowman = 274;
      int _snowmanx = this.width / 2 - 137;
      int _snowmanxx = this.height + 50;
      this.time += delta;
      float _snowmanxxx = -this.time * this.speed;
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, _snowmanxxx, 0.0F);
      this.client.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
      RenderSystem.enableBlend();
      this.method_29343(_snowmanx, _snowmanxx, (_snowmanxxxx, _snowmanxxxxx) -> {
         this.drawTexture(matrices, _snowmanxxxx + 0, _snowmanxxxxx, 0, 0, 155, 44);
         this.drawTexture(matrices, _snowmanxxxx + 155, _snowmanxxxxx, 0, 45, 155, 44);
      });
      RenderSystem.disableBlend();
      this.client.getTextureManager().bindTexture(EDITION_TITLE_TEXTURE);
      drawTexture(matrices, _snowmanx + 88, _snowmanxx + 37, 0.0F, 0.0F, 98, 14, 128, 16);
      RenderSystem.disableAlphaTest();
      int _snowmanxxxx = _snowmanxx + 100;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < this.credits.size(); _snowmanxxxxx++) {
         if (_snowmanxxxxx == this.credits.size() - 1) {
            float _snowmanxxxxxx = (float)_snowmanxxxx + _snowmanxxx - (float)(this.height / 2 - 6);
            if (_snowmanxxxxxx < 0.0F) {
               RenderSystem.translatef(0.0F, -_snowmanxxxxxx, 0.0F);
            }
         }

         if ((float)_snowmanxxxx + _snowmanxxx + 12.0F + 8.0F > 0.0F && (float)_snowmanxxxx + _snowmanxxx < (float)this.height) {
            OrderedText _snowmanxxxxxx = this.credits.get(_snowmanxxxxx);
            if (this.centeredLines.contains(_snowmanxxxxx)) {
               this.textRenderer.drawWithShadow(matrices, _snowmanxxxxxx, (float)(_snowmanx + (274 - this.textRenderer.getWidth(_snowmanxxxxxx)) / 2), (float)_snowmanxxxx, 16777215);
            } else {
               this.textRenderer.random.setSeed((long)((float)((long)_snowmanxxxxx * 4238972211L) + this.time / 4.0F));
               this.textRenderer.drawWithShadow(matrices, _snowmanxxxxxx, (float)_snowmanx, (float)_snowmanxxxx, 16777215);
            }
         }

         _snowmanxxxx += 12;
      }

      RenderSystem.popMatrix();
      this.client.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR);
      int _snowmanxxxxx = this.width;
      int _snowmanxxxxxx = this.height;
      Tessellator _snowmanxxxxxxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxxxxxxx = _snowmanxxxxxxx.getBuffer();
      _snowmanxxxxxxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
      _snowmanxxxxxxxx.vertex(0.0, (double)_snowmanxxxxxx, (double)this.getZOffset()).texture(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
      _snowmanxxxxxxxx.vertex((double)_snowmanxxxxx, (double)_snowmanxxxxxx, (double)this.getZOffset()).texture(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
      _snowmanxxxxxxxx.vertex((double)_snowmanxxxxx, 0.0, (double)this.getZOffset()).texture(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
      _snowmanxxxxxxxx.vertex(0.0, 0.0, (double)this.getZOffset()).texture(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
      _snowmanxxxxxxx.draw();
      RenderSystem.disableBlend();
      super.render(matrices, mouseX, mouseY, delta);
   }
}
