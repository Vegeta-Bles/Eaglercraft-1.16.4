package net.minecraft.client.toast;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SystemToast implements Toast {
   private final SystemToast.Type type;
   private Text title;
   private List<OrderedText> lines;
   private long startTime;
   private boolean justUpdated;
   private final int width;

   public SystemToast(SystemToast.Type type, Text title, @Nullable Text description) {
      this(type, title, getTextAsList(description), 160);
   }

   public static SystemToast create(MinecraftClient client, SystemToast.Type type, Text title, Text description) {
      TextRenderer _snowman = client.textRenderer;
      List<OrderedText> _snowmanx = _snowman.wrapLines(description, 200);
      int _snowmanxx = Math.max(200, _snowmanx.stream().mapToInt(_snowman::getWidth).max().orElse(200));
      return new SystemToast(type, title, _snowmanx, _snowmanxx + 30);
   }

   private SystemToast(SystemToast.Type type, Text title, List<OrderedText> lines, int width) {
      this.type = type;
      this.title = title;
      this.lines = lines;
      this.width = width;
   }

   private static ImmutableList<OrderedText> getTextAsList(@Nullable Text text) {
      return text == null ? ImmutableList.of() : ImmutableList.of(text.asOrderedText());
   }

   @Override
   public int getWidth() {
      return this.width;
   }

   @Override
   public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
      if (this.justUpdated) {
         this.startTime = startTime;
         this.justUpdated = false;
      }

      manager.getGame().getTextureManager().bindTexture(TEXTURE);
      RenderSystem.color3f(1.0F, 1.0F, 1.0F);
      int _snowman = this.getWidth();
      int _snowmanx = 12;
      if (_snowman == 160 && this.lines.size() <= 1) {
         manager.drawTexture(matrices, 0, 0, 0, 64, _snowman, this.getHeight());
      } else {
         int _snowmanxx = this.getHeight() + Math.max(0, this.lines.size() - 1) * 12;
         int _snowmanxxx = 28;
         int _snowmanxxxx = Math.min(4, _snowmanxx - 28);
         this.drawPart(matrices, manager, _snowman, 0, 0, 28);

         for (int _snowmanxxxxx = 28; _snowmanxxxxx < _snowmanxx - _snowmanxxxx; _snowmanxxxxx += 10) {
            this.drawPart(matrices, manager, _snowman, 16, _snowmanxxxxx, Math.min(16, _snowmanxx - _snowmanxxxxx - _snowmanxxxx));
         }

         this.drawPart(matrices, manager, _snowman, 32 - _snowmanxxxx, _snowmanxx - _snowmanxxxx, _snowmanxxxx);
      }

      if (this.lines == null) {
         manager.getGame().textRenderer.draw(matrices, this.title, 18.0F, 12.0F, -256);
      } else {
         manager.getGame().textRenderer.draw(matrices, this.title, 18.0F, 7.0F, -256);

         for (int _snowmanxx = 0; _snowmanxx < this.lines.size(); _snowmanxx++) {
            manager.getGame().textRenderer.draw(matrices, this.lines.get(_snowmanxx), 18.0F, (float)(18 + _snowmanxx * 12), -1);
         }
      }

      return startTime - this.startTime < 5000L ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
   }

   private void drawPart(MatrixStack matrices, ToastManager manager, int width, int textureV, int y, int height) {
      int _snowman = textureV == 0 ? 20 : 5;
      int _snowmanx = Math.min(60, width - _snowman);
      manager.drawTexture(matrices, 0, y, 0, 64 + textureV, _snowman, height);

      for (int _snowmanxx = _snowman; _snowmanxx < width - _snowmanx; _snowmanxx += 64) {
         manager.drawTexture(matrices, _snowmanxx, y, 32, 64 + textureV, Math.min(64, width - _snowmanxx - _snowmanx), height);
      }

      manager.drawTexture(matrices, width - _snowmanx, y, 160 - _snowmanx, 64 + textureV, _snowmanx, height);
   }

   public void setContent(Text title, @Nullable Text description) {
      this.title = title;
      this.lines = getTextAsList(description);
      this.justUpdated = true;
   }

   public SystemToast.Type getType() {
      return this.type;
   }

   public static void add(ToastManager manager, SystemToast.Type type, Text title, @Nullable Text description) {
      manager.add(new SystemToast(type, title, description));
   }

   public static void show(ToastManager manager, SystemToast.Type type, Text title, @Nullable Text description) {
      SystemToast _snowman = manager.getToast(SystemToast.class, type);
      if (_snowman == null) {
         add(manager, type, title, description);
      } else {
         _snowman.setContent(title, description);
      }
   }

   public static void addWorldAccessFailureToast(MinecraftClient client, String worldName) {
      add(client.getToastManager(), SystemToast.Type.WORLD_ACCESS_FAILURE, new TranslatableText("selectWorld.access_failure"), new LiteralText(worldName));
   }

   public static void addWorldDeleteFailureToast(MinecraftClient client, String worldName) {
      add(client.getToastManager(), SystemToast.Type.WORLD_ACCESS_FAILURE, new TranslatableText("selectWorld.delete_failure"), new LiteralText(worldName));
   }

   public static void addPackCopyFailure(MinecraftClient client, String directory) {
      add(client.getToastManager(), SystemToast.Type.PACK_COPY_FAILURE, new TranslatableText("pack.copyFailure"), new LiteralText(directory));
   }

   public static enum Type {
      TUTORIAL_HINT,
      NARRATOR_TOGGLE,
      WORLD_BACKUP,
      WORLD_GEN_SETTINGS_TRANSFER,
      PACK_LOAD_FAILURE,
      WORLD_ACCESS_FAILURE,
      PACK_COPY_FAILURE;

      private Type() {
      }
   }
}
