package net.minecraft.client.gui.screen.ingame;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class LoomScreen extends HandledScreen<LoomScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/loom.png");
   private static final int PATTERN_BUTTON_ROW_COUNT = (BannerPattern.COUNT - BannerPattern.field_24417 - 1 + 4 - 1) / 4;
   private final ModelPart bannerField;
   @Nullable
   private List<Pair<BannerPattern, DyeColor>> field_21841;
   private ItemStack banner = ItemStack.EMPTY;
   private ItemStack dye = ItemStack.EMPTY;
   private ItemStack pattern = ItemStack.EMPTY;
   private boolean canApplyDyePattern;
   private boolean canApplySpecialPattern;
   private boolean hasTooManyPatterns;
   private float scrollPosition;
   private boolean scrollbarClicked;
   private int firstPatternButtonId = 1;

   public LoomScreen(LoomScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
      this.bannerField = BannerBlockEntityRenderer.createBanner();
      handler.setInventoryChangeListener(this::onInventoryChanged);
      this.titleY -= 2;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      super.render(matrices, mouseX, mouseY, delta);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      this.renderBackground(matrices);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = this.x;
      int _snowmanx = this.y;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, this.backgroundWidth, this.backgroundHeight);
      Slot _snowmanxx = this.handler.getBannerSlot();
      Slot _snowmanxxx = this.handler.getDyeSlot();
      Slot _snowmanxxxx = this.handler.getPatternSlot();
      Slot _snowmanxxxxx = this.handler.getOutputSlot();
      if (!_snowmanxx.hasStack()) {
         this.drawTexture(matrices, _snowman + _snowmanxx.x, _snowmanx + _snowmanxx.y, this.backgroundWidth, 0, 16, 16);
      }

      if (!_snowmanxxx.hasStack()) {
         this.drawTexture(matrices, _snowman + _snowmanxxx.x, _snowmanx + _snowmanxxx.y, this.backgroundWidth + 16, 0, 16, 16);
      }

      if (!_snowmanxxxx.hasStack()) {
         this.drawTexture(matrices, _snowman + _snowmanxxxx.x, _snowmanx + _snowmanxxxx.y, this.backgroundWidth + 32, 0, 16, 16);
      }

      int _snowmanxxxxxx = (int)(41.0F * this.scrollPosition);
      this.drawTexture(matrices, _snowman + 119, _snowmanx + 13 + _snowmanxxxxxx, 232 + (this.canApplyDyePattern ? 0 : 12), 0, 12, 15);
      DiffuseLighting.disableGuiDepthLighting();
      if (this.field_21841 != null && !this.hasTooManyPatterns) {
         VertexConsumerProvider.Immediate _snowmanxxxxxxx = this.client.getBufferBuilders().getEntityVertexConsumers();
         matrices.push();
         matrices.translate((double)(_snowman + 139), (double)(_snowmanx + 52), 0.0);
         matrices.scale(24.0F, -24.0F, 1.0F);
         matrices.translate(0.5, 0.5, 0.5);
         float _snowmanxxxxxxxx = 0.6666667F;
         matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
         this.bannerField.pitch = 0.0F;
         this.bannerField.pivotY = -32.0F;
         BannerBlockEntityRenderer.method_29999(
            matrices, _snowmanxxxxxxx, 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, this.field_21841
         );
         matrices.pop();
         _snowmanxxxxxxx.draw();
      } else if (this.hasTooManyPatterns) {
         this.drawTexture(matrices, _snowman + _snowmanxxxxx.x - 2, _snowmanx + _snowmanxxxxx.y - 2, this.backgroundWidth, 17, 17, 16);
      }

      if (this.canApplyDyePattern) {
         int _snowmanxxxxxxx = _snowman + 60;
         int _snowmanxxxxxxxx = _snowmanx + 13;
         int _snowmanxxxxxxxxx = this.firstPatternButtonId + 16;

         for (int _snowmanxxxxxxxxxx = this.firstPatternButtonId;
            _snowmanxxxxxxxxxx < _snowmanxxxxxxxxx && _snowmanxxxxxxxxxx < BannerPattern.COUNT - BannerPattern.field_24417;
            _snowmanxxxxxxxxxx++
         ) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx - this.firstPatternButtonId;
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxx % 4 * 14;
            int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx + _snowmanxxxxxxxxxxx / 4 * 14;
            this.client.getTextureManager().bindTexture(TEXTURE);
            int _snowmanxxxxxxxxxxxxxx = this.backgroundHeight;
            if (_snowmanxxxxxxxxxx == this.handler.getSelectedPattern()) {
               _snowmanxxxxxxxxxxxxxx += 14;
            } else if (mouseX >= _snowmanxxxxxxxxxxxx && mouseY >= _snowmanxxxxxxxxxxxxx && mouseX < _snowmanxxxxxxxxxxxx + 14 && mouseY < _snowmanxxxxxxxxxxxxx + 14) {
               _snowmanxxxxxxxxxxxxxx += 28;
            }

            this.drawTexture(matrices, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxxx, 14, 14);
            this.method_22692(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
         }
      } else if (this.canApplySpecialPattern) {
         int _snowmanxxxxxxx = _snowman + 60;
         int _snowmanxxxxxxxx = _snowmanx + 13;
         this.client.getTextureManager().bindTexture(TEXTURE);
         this.drawTexture(matrices, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0, this.backgroundHeight, 14, 14);
         int _snowmanxxxxxxxxx = this.handler.getSelectedPattern();
         this.method_22692(_snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }

      DiffuseLighting.enableGuiDepthLighting();
   }

   private void method_22692(int _snowman, int _snowman, int _snowman) {
      ItemStack _snowmanxxx = new ItemStack(Items.GRAY_BANNER);
      CompoundTag _snowmanxxxx = _snowmanxxx.getOrCreateSubTag("BlockEntityTag");
      ListTag _snowmanxxxxx = new BannerPattern.Patterns().add(BannerPattern.BASE, DyeColor.GRAY).add(BannerPattern.values()[_snowman], DyeColor.WHITE).toTag();
      _snowmanxxxx.put("Patterns", _snowmanxxxxx);
      MatrixStack _snowmanxxxxxx = new MatrixStack();
      _snowmanxxxxxx.push();
      _snowmanxxxxxx.translate((double)((float)_snowman + 0.5F), (double)(_snowman + 16), 0.0);
      _snowmanxxxxxx.scale(6.0F, -6.0F, 1.0F);
      _snowmanxxxxxx.translate(0.5, 0.5, 0.0);
      _snowmanxxxxxx.translate(0.5, 0.5, 0.5);
      float _snowmanxxxxxxx = 0.6666667F;
      _snowmanxxxxxx.scale(0.6666667F, -0.6666667F, -0.6666667F);
      VertexConsumerProvider.Immediate _snowmanxxxxxxxx = this.client.getBufferBuilders().getEntityVertexConsumers();
      this.bannerField.pitch = 0.0F;
      this.bannerField.pivotY = -32.0F;
      List<Pair<BannerPattern, DyeColor>> _snowmanxxxxxxxxx = BannerBlockEntity.method_24280(DyeColor.GRAY, BannerBlockEntity.getPatternListTag(_snowmanxxx));
      BannerBlockEntityRenderer.method_29999(
         _snowmanxxxxxx, _snowmanxxxxxxxx, 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, _snowmanxxxxxxxxx
      );
      _snowmanxxxxxx.pop();
      _snowmanxxxxxxxx.draw();
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.scrollbarClicked = false;
      if (this.canApplyDyePattern) {
         int _snowman = this.x + 60;
         int _snowmanx = this.y + 13;
         int _snowmanxx = this.firstPatternButtonId + 16;

         for (int _snowmanxxx = this.firstPatternButtonId; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            int _snowmanxxxx = _snowmanxxx - this.firstPatternButtonId;
            double _snowmanxxxxx = mouseX - (double)(_snowman + _snowmanxxxx % 4 * 14);
            double _snowmanxxxxxx = mouseY - (double)(_snowmanx + _snowmanxxxx / 4 * 14);
            if (_snowmanxxxxx >= 0.0 && _snowmanxxxxxx >= 0.0 && _snowmanxxxxx < 14.0 && _snowmanxxxxxx < 14.0 && this.handler.onButtonClick(this.client.player, _snowmanxxx)) {
               MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0F));
               this.client.interactionManager.clickButton(this.handler.syncId, _snowmanxxx);
               return true;
            }
         }

         _snowman = this.x + 119;
         _snowmanx = this.y + 9;
         if (mouseX >= (double)_snowman && mouseX < (double)(_snowman + 12) && mouseY >= (double)_snowmanx && mouseY < (double)(_snowmanx + 56)) {
            this.scrollbarClicked = true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.scrollbarClicked && this.canApplyDyePattern) {
         int _snowman = this.y + 13;
         int _snowmanx = _snowman + 56;
         this.scrollPosition = ((float)mouseY - (float)_snowman - 7.5F) / ((float)(_snowmanx - _snowman) - 15.0F);
         this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0F, 1.0F);
         int _snowmanxx = PATTERN_BUTTON_ROW_COUNT - 4;
         int _snowmanxxx = (int)((double)(this.scrollPosition * (float)_snowmanxx) + 0.5);
         if (_snowmanxxx < 0) {
            _snowmanxxx = 0;
         }

         this.firstPatternButtonId = 1 + _snowmanxxx * 4;
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      if (this.canApplyDyePattern) {
         int _snowman = PATTERN_BUTTON_ROW_COUNT - 4;
         this.scrollPosition = (float)((double)this.scrollPosition - amount / (double)_snowman);
         this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0F, 1.0F);
         this.firstPatternButtonId = 1 + (int)((double)(this.scrollPosition * (float)_snowman) + 0.5) * 4;
      }

      return true;
   }

   @Override
   protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
      return mouseX < (double)left
         || mouseY < (double)top
         || mouseX >= (double)(left + this.backgroundWidth)
         || mouseY >= (double)(top + this.backgroundHeight);
   }

   private void onInventoryChanged() {
      ItemStack _snowman = this.handler.getOutputSlot().getStack();
      if (_snowman.isEmpty()) {
         this.field_21841 = null;
      } else {
         this.field_21841 = BannerBlockEntity.method_24280(((BannerItem)_snowman.getItem()).getColor(), BannerBlockEntity.getPatternListTag(_snowman));
      }

      ItemStack _snowmanx = this.handler.getBannerSlot().getStack();
      ItemStack _snowmanxx = this.handler.getDyeSlot().getStack();
      ItemStack _snowmanxxx = this.handler.getPatternSlot().getStack();
      CompoundTag _snowmanxxxx = _snowmanx.getOrCreateSubTag("BlockEntityTag");
      this.hasTooManyPatterns = _snowmanxxxx.contains("Patterns", 9) && !_snowmanx.isEmpty() && _snowmanxxxx.getList("Patterns", 10).size() >= 6;
      if (this.hasTooManyPatterns) {
         this.field_21841 = null;
      }

      if (!ItemStack.areEqual(_snowmanx, this.banner) || !ItemStack.areEqual(_snowmanxx, this.dye) || !ItemStack.areEqual(_snowmanxxx, this.pattern)) {
         this.canApplyDyePattern = !_snowmanx.isEmpty() && !_snowmanxx.isEmpty() && _snowmanxxx.isEmpty() && !this.hasTooManyPatterns;
         this.canApplySpecialPattern = !this.hasTooManyPatterns && !_snowmanxxx.isEmpty() && !_snowmanx.isEmpty() && !_snowmanxx.isEmpty();
      }

      this.banner = _snowmanx.copy();
      this.dye = _snowmanxx.copy();
      this.pattern = _snowmanxxx.copy();
   }
}
