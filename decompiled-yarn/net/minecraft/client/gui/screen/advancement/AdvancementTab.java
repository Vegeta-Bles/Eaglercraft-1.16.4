package net.minecraft.client.gui.screen.advancement;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AdvancementTab extends DrawableHelper {
   private final MinecraftClient client;
   private final AdvancementsScreen screen;
   private final AdvancementTabType type;
   private final int index;
   private final Advancement root;
   private final AdvancementDisplay display;
   private final ItemStack icon;
   private final Text title;
   private final AdvancementWidget rootWidget;
   private final Map<Advancement, AdvancementWidget> widgets = Maps.newLinkedHashMap();
   private double originX;
   private double originY;
   private int minPanX = Integer.MAX_VALUE;
   private int minPanY = Integer.MAX_VALUE;
   private int maxPanX = Integer.MIN_VALUE;
   private int maxPanY = Integer.MIN_VALUE;
   private float alpha;
   private boolean initialized;

   public AdvancementTab(MinecraftClient client, AdvancementsScreen screen, AdvancementTabType type, int index, Advancement root, AdvancementDisplay display) {
      this.client = client;
      this.screen = screen;
      this.type = type;
      this.index = index;
      this.root = root;
      this.display = display;
      this.icon = display.getIcon();
      this.title = display.getTitle();
      this.rootWidget = new AdvancementWidget(this, client, root, display);
      this.addWidget(this.rootWidget, root);
   }

   public Advancement getRoot() {
      return this.root;
   }

   public Text getTitle() {
      return this.title;
   }

   public void drawBackground(MatrixStack _snowman, int _snowman, int _snowman, boolean _snowman) {
      this.type.drawBackground(_snowman, this, _snowman, _snowman, _snowman, this.index);
   }

   public void drawIcon(int x, int y, ItemRenderer itemRenderer) {
      this.type.drawIcon(x, y, this.index, itemRenderer, this.icon);
   }

   public void render(MatrixStack _snowman) {
      if (!this.initialized) {
         this.originX = (double)(117 - (this.maxPanX + this.minPanX) / 2);
         this.originY = (double)(56 - (this.maxPanY + this.minPanY) / 2);
         this.initialized = true;
      }

      RenderSystem.pushMatrix();
      RenderSystem.enableDepthTest();
      RenderSystem.translatef(0.0F, 0.0F, 950.0F);
      RenderSystem.colorMask(false, false, false, false);
      fill(_snowman, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.translatef(0.0F, 0.0F, -950.0F);
      RenderSystem.depthFunc(518);
      fill(_snowman, 234, 113, 0, 0, -16777216);
      RenderSystem.depthFunc(515);
      Identifier _snowmanx = this.display.getBackground();
      if (_snowmanx != null) {
         this.client.getTextureManager().bindTexture(_snowmanx);
      } else {
         this.client.getTextureManager().bindTexture(TextureManager.MISSING_IDENTIFIER);
      }

      int _snowmanxx = MathHelper.floor(this.originX);
      int _snowmanxxx = MathHelper.floor(this.originY);
      int _snowmanxxxx = _snowmanxx % 16;
      int _snowmanxxxxx = _snowmanxxx % 16;

      for (int _snowmanxxxxxx = -1; _snowmanxxxxxx <= 15; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = -1; _snowmanxxxxxxx <= 8; _snowmanxxxxxxx++) {
            drawTexture(_snowman, _snowmanxxxx + 16 * _snowmanxxxxxx, _snowmanxxxxx + 16 * _snowmanxxxxxxx, 0.0F, 0.0F, 16, 16, 16, 16);
         }
      }

      this.rootWidget.renderLines(_snowman, _snowmanxx, _snowmanxxx, true);
      this.rootWidget.renderLines(_snowman, _snowmanxx, _snowmanxxx, false);
      this.rootWidget.renderWidgets(_snowman, _snowmanxx, _snowmanxxx);
      RenderSystem.depthFunc(518);
      RenderSystem.translatef(0.0F, 0.0F, -950.0F);
      RenderSystem.colorMask(false, false, false, false);
      fill(_snowman, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.translatef(0.0F, 0.0F, 950.0F);
      RenderSystem.depthFunc(515);
      RenderSystem.popMatrix();
   }

   public void drawWidgetTooltip(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, 0.0F, 200.0F);
      fill(_snowman, 0, 0, 234, 113, MathHelper.floor(this.alpha * 255.0F) << 24);
      boolean _snowmanxxxxx = false;
      int _snowmanxxxxxx = MathHelper.floor(this.originX);
      int _snowmanxxxxxxx = MathHelper.floor(this.originY);
      if (_snowman > 0 && _snowman < 234 && _snowman > 0 && _snowman < 113) {
         for (AdvancementWidget _snowmanxxxxxxxx : this.widgets.values()) {
            if (_snowmanxxxxxxxx.shouldRender(_snowmanxxxxxx, _snowmanxxxxxxx, _snowman, _snowman)) {
               _snowmanxxxxx = true;
               _snowmanxxxxxxxx.drawTooltip(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, this.alpha, _snowman, _snowman);
               break;
            }
         }
      }

      RenderSystem.popMatrix();
      if (_snowmanxxxxx) {
         this.alpha = MathHelper.clamp(this.alpha + 0.02F, 0.0F, 0.3F);
      } else {
         this.alpha = MathHelper.clamp(this.alpha - 0.04F, 0.0F, 1.0F);
      }
   }

   public boolean isClickOnTab(int screenX, int screenY, double mouseX, double mouseY) {
      return this.type.isClickOnTab(screenX, screenY, this.index, mouseX, mouseY);
   }

   @Nullable
   public static AdvancementTab create(MinecraftClient minecraft, AdvancementsScreen screen, int index, Advancement root) {
      if (root.getDisplay() == null) {
         return null;
      } else {
         for (AdvancementTabType _snowman : AdvancementTabType.values()) {
            if (index < _snowman.getTabCount()) {
               return new AdvancementTab(minecraft, screen, _snowman, index, root, root.getDisplay());
            }

            index -= _snowman.getTabCount();
         }

         return null;
      }
   }

   public void move(double offsetX, double offsetY) {
      if (this.maxPanX - this.minPanX > 234) {
         this.originX = MathHelper.clamp(this.originX + offsetX, (double)(-(this.maxPanX - 234)), 0.0);
      }

      if (this.maxPanY - this.minPanY > 113) {
         this.originY = MathHelper.clamp(this.originY + offsetY, (double)(-(this.maxPanY - 113)), 0.0);
      }
   }

   public void addAdvancement(Advancement advancement) {
      if (advancement.getDisplay() != null) {
         AdvancementWidget _snowman = new AdvancementWidget(this, this.client, advancement, advancement.getDisplay());
         this.addWidget(_snowman, advancement);
      }
   }

   private void addWidget(AdvancementWidget widget, Advancement advancement) {
      this.widgets.put(advancement, widget);
      int _snowman = widget.getX();
      int _snowmanx = _snowman + 28;
      int _snowmanxx = widget.getY();
      int _snowmanxxx = _snowmanxx + 27;
      this.minPanX = Math.min(this.minPanX, _snowman);
      this.maxPanX = Math.max(this.maxPanX, _snowmanx);
      this.minPanY = Math.min(this.minPanY, _snowmanxx);
      this.maxPanY = Math.max(this.maxPanY, _snowmanxxx);

      for (AdvancementWidget _snowmanxxxx : this.widgets.values()) {
         _snowmanxxxx.addToTree();
      }
   }

   @Nullable
   public AdvancementWidget getWidget(Advancement advancement) {
      return this.widgets.get(advancement);
   }

   public AdvancementsScreen getScreen() {
      return this.screen;
   }
}
