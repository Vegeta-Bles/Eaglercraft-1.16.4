package net.minecraft.client.gui.screen.advancement;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      ClientPlayNetworkHandler lv = this.client.getNetworkHandler();
      if (lv != null) {
         lv.sendPacket(AdvancementTabC2SPacket.close());
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         int j = (this.width - 252) / 2;
         int k = (this.height - 140) / 2;

         for (AdvancementTab lv : this.tabs.values()) {
            if (lv.isClickOnTab(j, k, mouseX, mouseY)) {
               this.advancementHandler.selectTab(lv.getRoot(), true);
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
      int k = (this.width - 252) / 2;
      int l = (this.height - 140) / 2;
      this.renderBackground(matrices);
      this.drawAdvancementTree(matrices, mouseX, mouseY, k, l);
      this.drawWidgets(matrices, k, l);
      this.drawWidgetTooltip(matrices, mouseX, mouseY, k, l);
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

   private void drawAdvancementTree(MatrixStack arg, int mouseY, int j, int k, int l) {
      AdvancementTab lv = this.selectedTab;
      if (lv == null) {
         fill(arg, k + 9, l + 18, k + 9 + 234, l + 18 + 113, -16777216);
         int m = k + 9 + 117;
         drawCenteredText(arg, this.textRenderer, field_26554, m, l + 18 + 56 - 9 / 2, -1);
         drawCenteredText(arg, this.textRenderer, field_26553, m, l + 18 + 113 - 9, -1);
      } else {
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(k + 9), (float)(l + 18), 0.0F);
         lv.render(arg);
         RenderSystem.popMatrix();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
      }
   }

   public void drawWidgets(MatrixStack arg, int i, int j) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      this.client.getTextureManager().bindTexture(WINDOW_TEXTURE);
      this.drawTexture(arg, i, j, 0, 0, 252, 140);
      if (this.tabs.size() > 1) {
         this.client.getTextureManager().bindTexture(TABS_TEXTURE);

         for (AdvancementTab lv : this.tabs.values()) {
            lv.drawBackground(arg, i, j, lv == this.selectedTab);
         }

         RenderSystem.enableRescaleNormal();
         RenderSystem.defaultBlendFunc();

         for (AdvancementTab lv2 : this.tabs.values()) {
            lv2.drawIcon(i, j, this.itemRenderer);
         }

         RenderSystem.disableBlend();
      }

      this.textRenderer.draw(arg, field_26555, (float)(i + 8), (float)(j + 6), 4210752);
   }

   private void drawWidgetTooltip(MatrixStack arg, int i, int j, int k, int l) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.selectedTab != null) {
         RenderSystem.pushMatrix();
         RenderSystem.enableDepthTest();
         RenderSystem.translatef((float)(k + 9), (float)(l + 18), 400.0F);
         this.selectedTab.drawWidgetTooltip(arg, i - k - 9, j - l - 18, k, l);
         RenderSystem.disableDepthTest();
         RenderSystem.popMatrix();
      }

      if (this.tabs.size() > 1) {
         for (AdvancementTab lv : this.tabs.values()) {
            if (lv.isClickOnTab(k, l, (double)i, (double)j)) {
               this.renderTooltip(arg, lv.getTitle(), i, j);
            }
         }
      }
   }

   @Override
   public void onRootAdded(Advancement root) {
      AdvancementTab lv = AdvancementTab.create(this.client, this, this.tabs.size(), root);
      if (lv != null) {
         this.tabs.put(root, lv);
      }
   }

   @Override
   public void onRootRemoved(Advancement root) {
   }

   @Override
   public void onDependentAdded(Advancement dependent) {
      AdvancementTab lv = this.getTab(dependent);
      if (lv != null) {
         lv.addAdvancement(dependent);
      }
   }

   @Override
   public void onDependentRemoved(Advancement dependent) {
   }

   @Override
   public void setProgress(Advancement advancement, AdvancementProgress progress) {
      AdvancementWidget lv = this.getAdvancementWidget(advancement);
      if (lv != null) {
         lv.setProgress(progress);
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
      AdvancementTab lv = this.getTab(advancement);
      return lv == null ? null : lv.getWidget(advancement);
   }

   @Nullable
   private AdvancementTab getTab(Advancement advancement) {
      while (advancement.getParent() != null) {
         advancement = advancement.getParent();
      }

      return this.tabs.get(advancement);
   }
}
