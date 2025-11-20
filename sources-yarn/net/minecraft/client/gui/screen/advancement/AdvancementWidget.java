package net.minecraft.client.gui.screen.advancement;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int i = advancement.getRequirementCount();
      int j = String.valueOf(i).length();
      int k = i > 1 ? client.textRenderer.getWidth("  ") + client.textRenderer.getWidth("0") * j * 2 + client.textRenderer.getWidth("/") : 0;
      int l = 29 + client.textRenderer.getWidth(this.title) + k;
      this.description = Language.getInstance()
         .reorder(
            this.wrapDescription(Texts.setStyleIfAbsent(display.getDescription().shallowCopy(), Style.EMPTY.withColor(display.getFrame().getTitleFormat())), l)
         );

      for (OrderedText lv : this.description) {
         l = Math.max(l, client.textRenderer.getWidth(lv));
      }

      this.width = l + 3 + 5;
   }

   private static float method_27572(TextHandler arg, List<StringVisitable> list) {
      return (float)list.stream().mapToDouble(arg::getWidth).max().orElse(0.0);
   }

   private List<StringVisitable> wrapDescription(Text arg, int width) {
      TextHandler lv = this.client.textRenderer.getTextHandler();
      List<StringVisitable> list = null;
      float f = Float.MAX_VALUE;

      for (int j : field_24262) {
         List<StringVisitable> list2 = lv.wrapLines(arg, width - j, Style.EMPTY);
         float g = Math.abs(method_27572(lv, list2) - (float)width);
         if (g <= 10.0F) {
            return list2;
         }

         if (g < f) {
            f = g;
            list = list2;
         }
      }

      return list;
   }

   @Nullable
   private AdvancementWidget getParent(Advancement advancement) {
      do {
         advancement = advancement.getParent();
      } while (advancement != null && advancement.getDisplay() == null);

      return advancement != null && advancement.getDisplay() != null ? this.tab.getWidget(advancement) : null;
   }

   public void renderLines(MatrixStack arg, int i, int j, boolean bl) {
      if (this.parent != null) {
         int k = i + this.parent.xPos + 13;
         int l = i + this.parent.xPos + 26 + 4;
         int m = j + this.parent.yPos + 13;
         int n = i + this.xPos + 13;
         int o = j + this.yPos + 13;
         int p = bl ? -16777216 : -1;
         if (bl) {
            this.drawHorizontalLine(arg, l, k, m - 1, p);
            this.drawHorizontalLine(arg, l + 1, k, m, p);
            this.drawHorizontalLine(arg, l, k, m + 1, p);
            this.drawHorizontalLine(arg, n, l - 1, o - 1, p);
            this.drawHorizontalLine(arg, n, l - 1, o, p);
            this.drawHorizontalLine(arg, n, l - 1, o + 1, p);
            this.drawVerticalLine(arg, l - 1, o, m, p);
            this.drawVerticalLine(arg, l + 1, o, m, p);
         } else {
            this.drawHorizontalLine(arg, l, k, m, p);
            this.drawHorizontalLine(arg, n, l, o, p);
            this.drawVerticalLine(arg, l, o, m, p);
         }
      }

      for (AdvancementWidget lv : this.children) {
         lv.renderLines(arg, i, j, bl);
      }
   }

   public void renderWidgets(MatrixStack arg, int i, int j) {
      if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
         float f = this.progress == null ? 0.0F : this.progress.getProgressBarPercentage();
         AdvancementObtainedStatus lv;
         if (f >= 1.0F) {
            lv = AdvancementObtainedStatus.OBTAINED;
         } else {
            lv = AdvancementObtainedStatus.UNOBTAINED;
         }

         this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
         this.drawTexture(arg, i + this.xPos + 3, j + this.yPos, this.display.getFrame().getTextureV(), 128 + lv.getSpriteIndex() * 26, 26, 26);
         this.client.getItemRenderer().renderInGui(this.display.getIcon(), i + this.xPos + 8, j + this.yPos + 5);
      }

      for (AdvancementWidget lv3 : this.children) {
         lv3.renderWidgets(arg, i, j);
      }
   }

   public void setProgress(AdvancementProgress progress) {
      this.progress = progress;
   }

   public void addChild(AdvancementWidget widget) {
      this.children.add(widget);
   }

   public void drawTooltip(MatrixStack arg, int i, int j, float f, int y, int l) {
      boolean bl = y + i + this.xPos + this.width + 26 >= this.tab.getScreen().width;
      String string = this.progress == null ? null : this.progress.getProgressBarFraction();
      int m = string == null ? 0 : this.client.textRenderer.getWidth(string);
      boolean bl2 = 113 - j - this.yPos - 26 <= 6 + this.description.size() * 9;
      float g = this.progress == null ? 0.0F : this.progress.getProgressBarPercentage();
      int n = MathHelper.floor(g * (float)this.width);
      AdvancementObtainedStatus lv;
      AdvancementObtainedStatus lv2;
      AdvancementObtainedStatus lv3;
      if (g >= 1.0F) {
         n = this.width / 2;
         lv = AdvancementObtainedStatus.OBTAINED;
         lv2 = AdvancementObtainedStatus.OBTAINED;
         lv3 = AdvancementObtainedStatus.OBTAINED;
      } else if (n < 2) {
         n = this.width / 2;
         lv = AdvancementObtainedStatus.UNOBTAINED;
         lv2 = AdvancementObtainedStatus.UNOBTAINED;
         lv3 = AdvancementObtainedStatus.UNOBTAINED;
      } else if (n > this.width - 2) {
         n = this.width / 2;
         lv = AdvancementObtainedStatus.OBTAINED;
         lv2 = AdvancementObtainedStatus.OBTAINED;
         lv3 = AdvancementObtainedStatus.UNOBTAINED;
      } else {
         lv = AdvancementObtainedStatus.OBTAINED;
         lv2 = AdvancementObtainedStatus.UNOBTAINED;
         lv3 = AdvancementObtainedStatus.UNOBTAINED;
      }

      int o = this.width - n;
      this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      int p = j + this.yPos;
      int q;
      if (bl) {
         q = i + this.xPos - this.width + 26 + 6;
      } else {
         q = i + this.xPos;
      }

      int s = 32 + this.description.size() * 9;
      if (!this.description.isEmpty()) {
         if (bl2) {
            this.method_2324(arg, q, p + 26 - s, this.width, s, 10, 200, 26, 0, 52);
         } else {
            this.method_2324(arg, q, p, this.width, s, 10, 200, 26, 0, 52);
         }
      }

      this.drawTexture(arg, q, p, 0, lv.getSpriteIndex() * 26, n, 26);
      this.drawTexture(arg, q + n, p, 200 - o, lv2.getSpriteIndex() * 26, o, 26);
      this.drawTexture(arg, i + this.xPos + 3, j + this.yPos, this.display.getFrame().getTextureV(), 128 + lv3.getSpriteIndex() * 26, 26, 26);
      if (bl) {
         this.client.textRenderer.drawWithShadow(arg, this.title, (float)(q + 5), (float)(j + this.yPos + 9), -1);
         if (string != null) {
            this.client.textRenderer.drawWithShadow(arg, string, (float)(i + this.xPos - m), (float)(j + this.yPos + 9), -1);
         }
      } else {
         this.client.textRenderer.drawWithShadow(arg, this.title, (float)(i + this.xPos + 32), (float)(j + this.yPos + 9), -1);
         if (string != null) {
            this.client.textRenderer.drawWithShadow(arg, string, (float)(i + this.xPos + this.width - m - 5), (float)(j + this.yPos + 9), -1);
         }
      }

      if (bl2) {
         for (int t = 0; t < this.description.size(); t++) {
            this.client.textRenderer.draw(arg, this.description.get(t), (float)(q + 5), (float)(p + 26 - s + 7 + t * 9), -5592406);
         }
      } else {
         for (int u = 0; u < this.description.size(); u++) {
            this.client.textRenderer.draw(arg, this.description.get(u), (float)(q + 5), (float)(j + this.yPos + 9 + 17 + u * 9), -5592406);
         }
      }

      this.client.getItemRenderer().renderInGui(this.display.getIcon(), i + this.xPos + 8, j + this.yPos + 5);
   }

   protected void method_2324(MatrixStack arg, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
      this.drawTexture(arg, i, j, p, q, m, m);
      this.method_2321(arg, i + m, j, k - m - m, m, p + m, q, n - m - m, o);
      this.drawTexture(arg, i + k - m, j, p + n - m, q, m, m);
      this.drawTexture(arg, i, j + l - m, p, q + o - m, m, m);
      this.method_2321(arg, i + m, j + l - m, k - m - m, m, p + m, q + o - m, n - m - m, o);
      this.drawTexture(arg, i + k - m, j + l - m, p + n - m, q + o - m, m, m);
      this.method_2321(arg, i, j + m, m, l - m - m, p, q + m, n, o - m - m);
      this.method_2321(arg, i + m, j + m, k - m - m, l - m - m, p + m, q + m, n - m - m, o - m - m);
      this.method_2321(arg, i + k - m, j + m, m, l - m - m, p + n - m, q + m, n, o - m - m);
   }

   protected void method_2321(MatrixStack arg, int i, int j, int k, int l, int m, int n, int o, int p) {
      int q = 0;

      while (q < k) {
         int r = i + q;
         int s = Math.min(o, k - q);
         int t = 0;

         while (t < l) {
            int u = j + t;
            int v = Math.min(p, l - t);
            this.drawTexture(arg, r, u, m, n, s, v);
            t += p;
         }

         q += o;
      }
   }

   public boolean shouldRender(int originX, int originY, int mouseX, int mouseY) {
      if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
         int m = originX + this.xPos;
         int n = m + 26;
         int o = originY + this.yPos;
         int p = o + 26;
         return mouseX >= m && mouseX <= n && mouseY >= o && mouseY <= p;
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
