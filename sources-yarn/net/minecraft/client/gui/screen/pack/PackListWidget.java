package net.minecraft.client.gui.screen.pack;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourcePackCompatibility;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;

@Environment(EnvType.CLIENT)
public class PackListWidget extends AlwaysSelectedEntryListWidget<PackListWidget.ResourcePackEntry> {
   private static final Identifier RESOURCE_PACKS_TEXTURE = new Identifier("textures/gui/resource_packs.png");
   private static final Text INCOMPATIBLE = new TranslatableText("pack.incompatible");
   private static final Text INCOMPATIBLE_CONFIRM = new TranslatableText("pack.incompatible.confirm.title");
   private final Text title;

   public PackListWidget(MinecraftClient client, int width, int height, Text title) {
      super(client, width, height, 32, height - 55 + 4, 36);
      this.title = title;
      this.centerListVertically = false;
      this.setRenderHeader(true, (int)(9.0F * 1.5F));
   }

   @Override
   protected void renderHeader(MatrixStack matrices, int x, int y, Tessellator arg2) {
      Text lv = new LiteralText("").append(this.title).formatted(Formatting.UNDERLINE, Formatting.BOLD);
      this.client
         .textRenderer
         .draw(matrices, lv, (float)(x + this.width / 2 - this.client.textRenderer.getWidth(lv) / 2), (float)Math.min(this.top + 3, y), 16777215);
   }

   @Override
   public int getRowWidth() {
      return this.width;
   }

   @Override
   protected int getScrollbarPositionX() {
      return this.right - 6;
   }

   @Environment(EnvType.CLIENT)
   public static class ResourcePackEntry extends AlwaysSelectedEntryListWidget.Entry<PackListWidget.ResourcePackEntry> {
      private PackListWidget widget;
      protected final MinecraftClient client;
      protected final Screen screen;
      private final ResourcePackOrganizer.Pack pack;
      private final OrderedText field_26590;
      private final class_5489 field_26591;
      private final OrderedText field_26784;
      private final class_5489 field_26785;

      public ResourcePackEntry(MinecraftClient arg, PackListWidget widget, Screen screen, ResourcePackOrganizer.Pack arg4) {
         this.client = arg;
         this.screen = screen;
         this.pack = arg4;
         this.widget = widget;
         this.field_26590 = method_31229(arg, arg4.getDisplayName());
         this.field_26591 = method_31230(arg, arg4.getDecoratedDescription());
         this.field_26784 = method_31229(arg, PackListWidget.INCOMPATIBLE);
         this.field_26785 = method_31230(arg, arg4.getCompatibility().getNotification());
      }

      private static OrderedText method_31229(MinecraftClient arg, Text arg2) {
         int i = arg.textRenderer.getWidth(arg2);
         if (i > 157) {
            StringVisitable lv = StringVisitable.concat(
               arg.textRenderer.trimToWidth(arg2, 157 - arg.textRenderer.getWidth("...")), StringVisitable.plain("...")
            );
            return Language.getInstance().reorder(lv);
         } else {
            return arg2.asOrderedText();
         }
      }

      private static class_5489 method_31230(MinecraftClient arg, Text arg2) {
         return class_5489.method_30891(arg.textRenderer, arg2, 157, 2);
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         ResourcePackCompatibility lv = this.pack.getCompatibility();
         if (!lv.isCompatible()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            DrawableHelper.fill(matrices, x - 1, y - 1, x + entryWidth - 9, y + entryHeight + 1, -8978432);
         }

         this.client.getTextureManager().bindTexture(this.pack.method_30286());
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(matrices, x, y, 0.0F, 0.0F, 32, 32, 32, 32);
         OrderedText lv2 = this.field_26590;
         class_5489 lv3 = this.field_26591;
         if (this.isSelectable() && (this.client.options.touchscreen || hovered)) {
            this.client.getTextureManager().bindTexture(PackListWidget.RESOURCE_PACKS_TEXTURE);
            DrawableHelper.fill(matrices, x, y, x + 32, y + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int p = mouseX - x;
            int q = mouseY - y;
            if (!this.pack.getCompatibility().isCompatible()) {
               lv2 = this.field_26784;
               lv3 = this.field_26785;
            }

            if (this.pack.canBeEnabled()) {
               if (p < 32) {
                  DrawableHelper.drawTexture(matrices, x, y, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  DrawableHelper.drawTexture(matrices, x, y, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            } else {
               if (this.pack.canBeDisabled()) {
                  if (p < 16) {
                     DrawableHelper.drawTexture(matrices, x, y, 32.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     DrawableHelper.drawTexture(matrices, x, y, 32.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.pack.canMoveTowardStart()) {
                  if (p < 32 && p > 16 && q < 16) {
                     DrawableHelper.drawTexture(matrices, x, y, 96.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     DrawableHelper.drawTexture(matrices, x, y, 96.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.pack.canMoveTowardEnd()) {
                  if (p < 32 && p > 16 && q > 16) {
                     DrawableHelper.drawTexture(matrices, x, y, 64.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     DrawableHelper.drawTexture(matrices, x, y, 64.0F, 0.0F, 32, 32, 256, 256);
                  }
               }
            }
         }

         this.client.textRenderer.drawWithShadow(matrices, lv2, (float)(x + 32 + 2), (float)(y + 1), 16777215);
         lv3.method_30893(matrices, x + 32 + 2, y + 12, 10, 8421504);
      }

      private boolean isSelectable() {
         return !this.pack.isPinned() || !this.pack.isAlwaysEnabled();
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         double f = mouseX - (double)this.widget.getRowLeft();
         double g = mouseY - (double)this.widget.getRowTop(this.widget.children().indexOf(this));
         if (this.isSelectable() && f <= 32.0) {
            if (this.pack.canBeEnabled()) {
               ResourcePackCompatibility lv = this.pack.getCompatibility();
               if (lv.isCompatible()) {
                  this.pack.enable();
               } else {
                  Text lv2 = lv.getConfirmMessage();
                  this.client.openScreen(new ConfirmScreen(bl -> {
                     this.client.openScreen(this.screen);
                     if (bl) {
                        this.pack.enable();
                     }
                  }, PackListWidget.INCOMPATIBLE_CONFIRM, lv2));
               }

               return true;
            }

            if (f < 16.0 && this.pack.canBeDisabled()) {
               this.pack.disable();
               return true;
            }

            if (f > 16.0 && g < 16.0 && this.pack.canMoveTowardStart()) {
               this.pack.moveTowardStart();
               return true;
            }

            if (f > 16.0 && g > 16.0 && this.pack.canMoveTowardEnd()) {
               this.pack.moveTowardEnd();
               return true;
            }
         }

         return false;
      }
   }
}
