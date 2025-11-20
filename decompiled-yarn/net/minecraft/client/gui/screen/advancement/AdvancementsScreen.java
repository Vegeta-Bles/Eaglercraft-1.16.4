package net.minecraft.client.gui.screen.advancement;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.AdvancementTabC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class AdvancementsScreen extends Screen implements ClientAdvancementManager.Listener {
   private static final Identifier WINDOW_TEXTURE = new Identifier("textures/gui/advancements/window.png");
   private static final Identifier TABS_TEXTURE = new Identifier("textures/gui/advancements/tabs.png");
   private static final Text field_26553 = new TranslatableText("advancements.sad_label");
   private static final Text field_26554 = new TranslatableText("advancements.empty");
   private static final Text field_26555 = new TranslatableText("gui.advancements");
   private final ClientAdvancementManager advancementHandler;
   private final Map<Advancement, AdvancementTab> tabs = Maps.newLinkedHashMap();
   private AdvancementTab selectedTab;
   private boolean movingTab;

   public AdvancementsScreen(ClientAdvancementManager advancementHandler) {
      super(NarratorManager.EMPTY);
      this.advancementHandler = advancementHandler;
   }

   @Override
   protected void init() {
      this.tabs.clear();
      this.selectedTab = null;
      this.advancementHandler.setListener(this);
      if (this.selectedTab == null && !this.tabs.isEmpty()) {
         this.advancementHandler.selectTab(this.tabs.values().iterator().next().getRoot(), true);
      } else {
         this.advancementHandler.selectTab(this.selectedTab == null ? null : this.selectedTab.getRoot(), true);
      }
   }

   @Override
   public void removed() {
      this.advancementHandler.setListener(null);
      ClientPlayNetworkHandler _snowman = this.client.getNetworkHandler();
      if (_snowman != null) {
         _snowman.sendPacket(AdvancementTabC2SPacket.close());
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         int _snowman = (this.width - 252) / 2;
         int _snowmanx = (this.height - 140) / 2;

         for (AdvancementTab _snowmanxx : this.tabs.values()) {
            if (_snowmanxx.isClickOnTab(_snowman, _snowmanx, mouseX, mouseY)) {
               this.advancementHandler.selectTab(_snowmanxx.getRoot(), true);
               break;
            }
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.client.options.keyAdvancements.matchesKey(keyCode, scanCode)) {
         this.client.openScreen(null);
         this.client.mouse.lockCursor();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      int _snowman = (this.width - 252) / 2;
      int _snowmanx = (this.height - 140) / 2;
      this.renderBackground(matrices);
      this.drawAdvancementTree(matrices, mouseX, mouseY, _snowman, _snowmanx);
      this.drawWidgets(matrices, _snowman, _snowmanx);
      this.drawWidgetTooltip(matrices, mouseX, mouseY, _snowman, _snowmanx);
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (button != 0) {
         this.movingTab = false;
         return false;
      } else {
         if (!this.movingTab) {
            this.movingTab = true;
         } else if (this.selectedTab != null) {
            this.selectedTab.move(deltaX, deltaY);
         }

         return true;
      }
   }

   private void drawAdvancementTree(MatrixStack _snowman, int mouseY, int _snowman, int _snowman, int _snowman) {
      AdvancementTab _snowmanxxxx = this.selectedTab;
      if (_snowmanxxxx == null) {
         fill(_snowman, _snowman + 9, _snowman + 18, _snowman + 9 + 234, _snowman + 18 + 113, -16777216);
         int _snowmanxxxxx = _snowman + 9 + 117;
         drawCenteredText(_snowman, this.textRenderer, field_26554, _snowmanxxxxx, _snowman + 18 + 56 - 9 / 2, -1);
         drawCenteredText(_snowman, this.textRenderer, field_26553, _snowmanxxxxx, _snowman + 18 + 113 - 9, -1);
      } else {
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(_snowman + 9), (float)(_snowman + 18), 0.0F);
         _snowmanxxxx.render(_snowman);
         RenderSystem.popMatrix();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
      }
   }

   public void drawWidgets(MatrixStack _snowman, int _snowman, int _snowman) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      this.client.getTextureManager().bindTexture(WINDOW_TEXTURE);
      this.drawTexture(_snowman, _snowman, _snowman, 0, 0, 252, 140);
      if (this.tabs.size() > 1) {
         this.client.getTextureManager().bindTexture(TABS_TEXTURE);

         for (AdvancementTab _snowmanxxx : this.tabs.values()) {
            _snowmanxxx.drawBackground(_snowman, _snowman, _snowman, _snowmanxxx == this.selectedTab);
         }

         RenderSystem.enableRescaleNormal();
         RenderSystem.defaultBlendFunc();

         for (AdvancementTab _snowmanxxx : this.tabs.values()) {
            _snowmanxxx.drawIcon(_snowman, _snowman, this.itemRenderer);
         }

         RenderSystem.disableBlend();
      }

      this.textRenderer.draw(_snowman, field_26555, (float)(_snowman + 8), (float)(_snowman + 6), 4210752);
   }

   private void drawWidgetTooltip(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.selectedTab != null) {
         RenderSystem.pushMatrix();
         RenderSystem.enableDepthTest();
         RenderSystem.translatef((float)(_snowman + 9), (float)(_snowman + 18), 400.0F);
         this.selectedTab.drawWidgetTooltip(_snowman, _snowman - _snowman - 9, _snowman - _snowman - 18, _snowman, _snowman);
         RenderSystem.disableDepthTest();
         RenderSystem.popMatrix();
      }

      if (this.tabs.size() > 1) {
         for (AdvancementTab _snowmanxxxxx : this.tabs.values()) {
            if (_snowmanxxxxx.isClickOnTab(_snowman, _snowman, (double)_snowman, (double)_snowman)) {
               this.renderTooltip(_snowman, _snowmanxxxxx.getTitle(), _snowman, _snowman);
            }
         }
      }
   }

   @Override
   public void onRootAdded(Advancement root) {
      AdvancementTab _snowman = AdvancementTab.create(this.client, this, this.tabs.size(), root);
      if (_snowman != null) {
         this.tabs.put(root, _snowman);
      }
   }

   @Override
   public void onRootRemoved(Advancement root) {
   }

   @Override
   public void onDependentAdded(Advancement dependent) {
      AdvancementTab _snowman = this.getTab(dependent);
      if (_snowman != null) {
         _snowman.addAdvancement(dependent);
      }
   }

   @Override
   public void onDependentRemoved(Advancement dependent) {
   }

   @Override
   public void setProgress(Advancement advancement, AdvancementProgress progress) {
      AdvancementWidget _snowman = this.getAdvancementWidget(advancement);
      if (_snowman != null) {
         _snowman.setProgress(progress);
      }
   }

   @Override
   public void selectTab(@Nullable Advancement advancement) {
      this.selectedTab = this.tabs.get(advancement);
   }

   @Override
   public void onClear() {
      this.tabs.clear();
      this.selectedTab = null;
   }

   @Nullable
   public AdvancementWidget getAdvancementWidget(Advancement advancement) {
      AdvancementTab _snowman = this.getTab(advancement);
      return _snowman == null ? null : _snowman.getWidget(advancement);
   }

   @Nullable
   private AdvancementTab getTab(Advancement advancement) {
      while (advancement.getParent() != null) {
         advancement = advancement.getParent();
      }

      return this.tabs.get(advancement);
   }
}
