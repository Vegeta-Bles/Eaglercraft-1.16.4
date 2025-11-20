package net.minecraft.client.gui.screen.advancement;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.math.MathHelper;

public class AdvancementWidget extends DrawableHelper {
   private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/advancements/widgets.png");
   private static final int[] field_24262 = new int[]{0, 10, -10, 25, -25};
   private final AdvancementTab tab;
   private final Advancement advancement;
   private final AdvancementDisplay display;
   private final OrderedText title;
   private final int width;
   private final List<OrderedText> description;
   private final MinecraftClient client;
   private AdvancementWidget parent;
   private final List<AdvancementWidget> children = Lists.newArrayList();
   private AdvancementProgress progress;
   private final int xPos;
   private final int yPos;

   public AdvancementWidget(AdvancementTab tab, MinecraftClient client, Advancement advancement, AdvancementDisplay display) {
      this.tab = tab;
      this.advancement = advancement;
      this.display = display;
      this.client = client;
      this.title = Language.getInstance().reorder(client.textRenderer.trimToWidth(display.getTitle(), 163));
      this.xPos = MathHelper.floor(display.getX() * 28.0F);
      this.yPos = MathHelper.floor(display.getY() * 27.0F);
      int _snowman = advancement.getRequirementCount();
      int _snowmanx = String.valueOf(_snowman).length();
      int _snowmanxx = _snowman > 1 ? client.textRenderer.getWidth("  ") + client.textRenderer.getWidth("0") * _snowmanx * 2 + client.textRenderer.getWidth("/") : 0;
      int _snowmanxxx = 29 + client.textRenderer.getWidth(this.title) + _snowmanxx;
      this.description = Language.getInstance()
         .reorder(
            this.wrapDescription(
               Texts.setStyleIfAbsent(display.getDescription().shallowCopy(), Style.EMPTY.withColor(display.getFrame().getTitleFormat())), _snowmanxxx
            )
         );

      for (OrderedText _snowmanxxxx : this.description) {
         _snowmanxxx = Math.max(_snowmanxxx, client.textRenderer.getWidth(_snowmanxxxx));
      }

      this.width = _snowmanxxx + 3 + 5;
   }

   private static float method_27572(TextHandler _snowman, List<StringVisitable> _snowman) {
      return (float)_snowman.stream().mapToDouble(_snowman::getWidth).max().orElse(0.0);
   }

   private List<StringVisitable> wrapDescription(Text _snowman, int width) {
      TextHandler _snowmanx = this.client.textRenderer.getTextHandler();
      List<StringVisitable> _snowmanxx = null;
      float _snowmanxxx = Float.MAX_VALUE;

      for (int _snowmanxxxx : field_24262) {
         List<StringVisitable> _snowmanxxxxx = _snowmanx.wrapLines(_snowman, width - _snowmanxxxx, Style.EMPTY);
         float _snowmanxxxxxx = Math.abs(method_27572(_snowmanx, _snowmanxxxxx) - (float)width);
         if (_snowmanxxxxxx <= 10.0F) {
            return _snowmanxxxxx;
         }

         if (_snowmanxxxxxx < _snowmanxxx) {
            _snowmanxxx = _snowmanxxxxxx;
            _snowmanxx = _snowmanxxxxx;
         }
      }

      return _snowmanxx;
   }

   @Nullable
   private AdvancementWidget getParent(Advancement advancement) {
      do {
         advancement = advancement.getParent();
      } while (advancement != null && advancement.getDisplay() == null);

      return advancement != null && advancement.getDisplay() != null ? this.tab.getWidget(advancement) : null;
   }

   public void renderLines(MatrixStack _snowman, int _snowman, int _snowman, boolean _snowman) {
      if (this.parent != null) {
         int _snowmanxxxx = _snowman + this.parent.xPos + 13;
         int _snowmanxxxxx = _snowman + this.parent.xPos + 26 + 4;
         int _snowmanxxxxxx = _snowman + this.parent.yPos + 13;
         int _snowmanxxxxxxx = _snowman + this.xPos + 13;
         int _snowmanxxxxxxxx = _snowman + this.yPos + 13;
         int _snowmanxxxxxxxxx = _snowman ? -16777216 : -1;
         if (_snowman) {
            this.drawHorizontalLine(_snowman, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxx - 1, _snowmanxxxxxxxxx);
            this.drawHorizontalLine(_snowman, _snowmanxxxxx + 1, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx);
            this.drawHorizontalLine(_snowman, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxxxxx);
            this.drawHorizontalLine(_snowman, _snowmanxxxxxxx, _snowmanxxxxx - 1, _snowmanxxxxxxxx - 1, _snowmanxxxxxxxxx);
            this.drawHorizontalLine(_snowman, _snowmanxxxxxxx, _snowmanxxxxx - 1, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            this.drawHorizontalLine(_snowman, _snowmanxxxxxxx, _snowmanxxxxx - 1, _snowmanxxxxxxxx + 1, _snowmanxxxxxxxxx);
            this.drawVerticalLine(_snowman, _snowmanxxxxx - 1, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx);
            this.drawVerticalLine(_snowman, _snowmanxxxxx + 1, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx);
         } else {
            this.drawHorizontalLine(_snowman, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx);
            this.drawHorizontalLine(_snowman, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            this.drawVerticalLine(_snowman, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx);
         }
      }

      for (AdvancementWidget _snowmanxxxx : this.children) {
         _snowmanxxxx.renderLines(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public void renderWidgets(MatrixStack _snowman, int _snowman, int _snowman) {
      if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
         float _snowmanxxx = this.progress == null ? 0.0F : this.progress.getProgressBarPercentage();
         AdvancementObtainedStatus _snowmanxxxx;
         if (_snowmanxxx >= 1.0F) {
            _snowmanxxxx = AdvancementObtainedStatus.OBTAINED;
         } else {
            _snowmanxxxx = AdvancementObtainedStatus.UNOBTAINED;
         }

         this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
         this.drawTexture(_snowman, _snowman + this.xPos + 3, _snowman + this.yPos, this.display.getFrame().getTextureV(), 128 + _snowmanxxxx.getSpriteIndex() * 26, 26, 26);
         this.client.getItemRenderer().renderInGui(this.display.getIcon(), _snowman + this.xPos + 8, _snowman + this.yPos + 5);
      }

      for (AdvancementWidget _snowmanxxx : this.children) {
         _snowmanxxx.renderWidgets(_snowman, _snowman, _snowman);
      }
   }

   public void setProgress(AdvancementProgress progress) {
      this.progress = progress;
   }

   public void addChild(AdvancementWidget widget) {
      this.children.add(widget);
   }

   public void drawTooltip(MatrixStack _snowman, int _snowman, int _snowman, float _snowman, int y, int _snowman) {
      boolean _snowmanxxxxx = y + _snowman + this.xPos + this.width + 26 >= this.tab.getScreen().width;
      String _snowmanxxxxxx = this.progress == null ? null : this.progress.getProgressBarFraction();
      int _snowmanxxxxxxx = _snowmanxxxxxx == null ? 0 : this.client.textRenderer.getWidth(_snowmanxxxxxx);
      boolean _snowmanxxxxxxxx = 113 - _snowman - this.yPos - 26 <= 6 + this.description.size() * 9;
      float _snowmanxxxxxxxxx = this.progress == null ? 0.0F : this.progress.getProgressBarPercentage();
      int _snowmanxxxxxxxxxx = MathHelper.floor(_snowmanxxxxxxxxx * (float)this.width);
      AdvancementObtainedStatus _snowmanxxxxxxxxxxx;
      AdvancementObtainedStatus _snowmanxxxxxxxxxxxx;
      AdvancementObtainedStatus _snowmanxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxx >= 1.0F) {
         _snowmanxxxxxxxxxx = this.width / 2;
         _snowmanxxxxxxxxxxx = AdvancementObtainedStatus.OBTAINED;
         _snowmanxxxxxxxxxxxx = AdvancementObtainedStatus.OBTAINED;
         _snowmanxxxxxxxxxxxxx = AdvancementObtainedStatus.OBTAINED;
      } else if (_snowmanxxxxxxxxxx < 2) {
         _snowmanxxxxxxxxxx = this.width / 2;
         _snowmanxxxxxxxxxxx = AdvancementObtainedStatus.UNOBTAINED;
         _snowmanxxxxxxxxxxxx = AdvancementObtainedStatus.UNOBTAINED;
         _snowmanxxxxxxxxxxxxx = AdvancementObtainedStatus.UNOBTAINED;
      } else if (_snowmanxxxxxxxxxx > this.width - 2) {
         _snowmanxxxxxxxxxx = this.width / 2;
         _snowmanxxxxxxxxxxx = AdvancementObtainedStatus.OBTAINED;
         _snowmanxxxxxxxxxxxx = AdvancementObtainedStatus.OBTAINED;
         _snowmanxxxxxxxxxxxxx = AdvancementObtainedStatus.UNOBTAINED;
      } else {
         _snowmanxxxxxxxxxxx = AdvancementObtainedStatus.OBTAINED;
         _snowmanxxxxxxxxxxxx = AdvancementObtainedStatus.UNOBTAINED;
         _snowmanxxxxxxxxxxxxx = AdvancementObtainedStatus.UNOBTAINED;
      }

      int _snowmanxxxxxxxxxxxxxx = this.width - _snowmanxxxxxxxxxx;
      this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      int _snowmanxxxxxxxxxxxxxxx = _snowman + this.yPos;
      int _snowmanxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxx) {
         _snowmanxxxxxxxxxxxxxxxx = _snowman + this.xPos - this.width + 26 + 6;
      } else {
         _snowmanxxxxxxxxxxxxxxxx = _snowman + this.xPos;
      }

      int _snowmanxxxxxxxxxxxxxxxxx = 32 + this.description.size() * 9;
      if (!this.description.isEmpty()) {
         if (_snowmanxxxxxxxx) {
            this.method_2324(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx + 26 - _snowmanxxxxxxxxxxxxxxxxx, this.width, _snowmanxxxxxxxxxxxxxxxxx, 10, 200, 26, 0, 52);
         } else {
            this.method_2324(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, this.width, _snowmanxxxxxxxxxxxxxxxxx, 10, 200, 26, 0, 52);
         }
      }

      this.drawTexture(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxx.getSpriteIndex() * 26, _snowmanxxxxxxxxxx, 26);
      this.drawTexture(_snowman, _snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 200 - _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx.getSpriteIndex() * 26, _snowmanxxxxxxxxxxxxxx, 26);
      this.drawTexture(_snowman, _snowman + this.xPos + 3, _snowman + this.yPos, this.display.getFrame().getTextureV(), 128 + _snowmanxxxxxxxxxxxxx.getSpriteIndex() * 26, 26, 26);
      if (_snowmanxxxxx) {
         this.client.textRenderer.drawWithShadow(_snowman, this.title, (float)(_snowmanxxxxxxxxxxxxxxxx + 5), (float)(_snowman + this.yPos + 9), -1);
         if (_snowmanxxxxxx != null) {
            this.client.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxx, (float)(_snowman + this.xPos - _snowmanxxxxxxx), (float)(_snowman + this.yPos + 9), -1);
         }
      } else {
         this.client.textRenderer.drawWithShadow(_snowman, this.title, (float)(_snowman + this.xPos + 32), (float)(_snowman + this.yPos + 9), -1);
         if (_snowmanxxxxxx != null) {
            this.client.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxx, (float)(_snowman + this.xPos + this.width - _snowmanxxxxxxx - 5), (float)(_snowman + this.yPos + 9), -1);
         }
      }

      if (_snowmanxxxxxxxx) {
         for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < this.description.size(); _snowmanxxxxxxxxxxxxxxxxxx++) {
            this.client
               .textRenderer
               .draw(
                  _snowman,
                  this.description.get(_snowmanxxxxxxxxxxxxxxxxxx),
                  (float)(_snowmanxxxxxxxxxxxxxxxx + 5),
                  (float)(_snowmanxxxxxxxxxxxxxxx + 26 - _snowmanxxxxxxxxxxxxxxxxx + 7 + _snowmanxxxxxxxxxxxxxxxxxx * 9),
                  -5592406
               );
         }
      } else {
         for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < this.description.size(); _snowmanxxxxxxxxxxxxxxxxxx++) {
            this.client
               .textRenderer
               .draw(
                  _snowman,
                  this.description.get(_snowmanxxxxxxxxxxxxxxxxxx),
                  (float)(_snowmanxxxxxxxxxxxxxxxx + 5),
                  (float)(_snowman + this.yPos + 9 + 17 + _snowmanxxxxxxxxxxxxxxxxxx * 9),
                  -5592406
               );
         }
      }

      this.client.getItemRenderer().renderInGui(this.display.getIcon(), _snowman + this.xPos + 8, _snowman + this.yPos + 5);
   }

   protected void method_2324(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.drawTexture(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.method_2321(_snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman);
      this.drawTexture(_snowman, _snowman + _snowman - _snowman, _snowman, _snowman + _snowman - _snowman, _snowman, _snowman, _snowman);
      this.drawTexture(_snowman, _snowman, _snowman + _snowman - _snowman, _snowman, _snowman + _snowman - _snowman, _snowman, _snowman);
      this.method_2321(_snowman, _snowman + _snowman, _snowman + _snowman - _snowman, _snowman - _snowman - _snowman, _snowman, _snowman + _snowman, _snowman + _snowman - _snowman, _snowman - _snowman - _snowman, _snowman);
      this.drawTexture(_snowman, _snowman + _snowman - _snowman, _snowman + _snowman - _snowman, _snowman + _snowman - _snowman, _snowman + _snowman - _snowman, _snowman, _snowman);
      this.method_2321(_snowman, _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman);
      this.method_2321(_snowman, _snowman + _snowman, _snowman + _snowman, _snowman - _snowman - _snowman, _snowman - _snowman - _snowman, _snowman + _snowman, _snowman + _snowman, _snowman - _snowman - _snowman, _snowman - _snowman - _snowman);
      this.method_2321(_snowman, _snowman + _snowman - _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman + _snowman - _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman);
   }

   protected void method_2321(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      int _snowmanxxxxxxxxx = 0;

      while (_snowmanxxxxxxxxx < _snowman) {
         int _snowmanxxxxxxxxxx = _snowman + _snowmanxxxxxxxxx;
         int _snowmanxxxxxxxxxxx = Math.min(_snowman, _snowman - _snowmanxxxxxxxxx);
         int _snowmanxxxxxxxxxxxx = 0;

         while (_snowmanxxxxxxxxxxxx < _snowman) {
            int _snowmanxxxxxxxxxxxxx = _snowman + _snowmanxxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxx = Math.min(_snowman, _snowman - _snowmanxxxxxxxxxxxx);
            this.drawTexture(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowman, _snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxx += _snowman;
         }

         _snowmanxxxxxxxxx += _snowman;
      }
   }

   public boolean shouldRender(int originX, int originY, int mouseX, int mouseY) {
      if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
         int _snowman = originX + this.xPos;
         int _snowmanx = _snowman + 26;
         int _snowmanxx = originY + this.yPos;
         int _snowmanxxx = _snowmanxx + 26;
         return mouseX >= _snowman && mouseX <= _snowmanx && mouseY >= _snowmanxx && mouseY <= _snowmanxxx;
      } else {
         return false;
      }
   }

   public void addToTree() {
      if (this.parent == null && this.advancement.getParent() != null) {
         this.parent = this.getParent(this.advancement);
         if (this.parent != null) {
            this.parent.addChild(this);
         }
      }
   }

   public int getY() {
      return this.yPos;
   }

   public int getX() {
      return this.xPos;
   }
}
