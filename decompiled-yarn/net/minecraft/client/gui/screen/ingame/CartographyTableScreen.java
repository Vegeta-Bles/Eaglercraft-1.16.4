package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CartographyTableScreen extends HandledScreen<CartographyTableScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/cartography_table.png");

   public CartographyTableScreen(CartographyTableScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
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
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = this.x;
      int _snowmanx = this.y;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, this.backgroundWidth, this.backgroundHeight);
      Item _snowmanxx = this.handler.getSlot(1).getStack().getItem();
      boolean _snowmanxxx = _snowmanxx == Items.MAP;
      boolean _snowmanxxxx = _snowmanxx == Items.PAPER;
      boolean _snowmanxxxxx = _snowmanxx == Items.GLASS_PANE;
      ItemStack _snowmanxxxxxx = this.handler.getSlot(0).getStack();
      boolean _snowmanxxxxxxx = false;
      MapState _snowmanxxxxxxxx;
      if (_snowmanxxxxxx.getItem() == Items.FILLED_MAP) {
         _snowmanxxxxxxxx = FilledMapItem.getMapState(_snowmanxxxxxx, this.client.world);
         if (_snowmanxxxxxxxx != null) {
            if (_snowmanxxxxxxxx.locked) {
               _snowmanxxxxxxx = true;
               if (_snowmanxxxx || _snowmanxxxxx) {
                  this.drawTexture(matrices, _snowman + 35, _snowmanx + 31, this.backgroundWidth + 50, 132, 28, 21);
               }
            }

            if (_snowmanxxxx && _snowmanxxxxxxxx.scale >= 4) {
               _snowmanxxxxxxx = true;
               this.drawTexture(matrices, _snowman + 35, _snowmanx + 31, this.backgroundWidth + 50, 132, 28, 21);
            }
         }
      } else {
         _snowmanxxxxxxxx = null;
      }

      this.drawMap(matrices, _snowmanxxxxxxxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
   }

   private void drawMap(MatrixStack _snowman, @Nullable MapState _snowman, boolean _snowman, boolean _snowman, boolean _snowman, boolean _snowman) {
      int _snowmanxxxxxx = this.x;
      int _snowmanxxxxxxx = this.y;
      if (_snowman && !_snowman) {
         this.drawTexture(_snowman, _snowmanxxxxxx + 67, _snowmanxxxxxxx + 13, this.backgroundWidth, 66, 66, 66);
         this.drawMap(_snowman, _snowmanxxxxxx + 85, _snowmanxxxxxxx + 31, 0.226F);
      } else if (_snowman) {
         this.drawTexture(_snowman, _snowmanxxxxxx + 67 + 16, _snowmanxxxxxxx + 13, this.backgroundWidth, 132, 50, 66);
         this.drawMap(_snowman, _snowmanxxxxxx + 86, _snowmanxxxxxxx + 16, 0.34F);
         this.client.getTextureManager().bindTexture(TEXTURE);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 1.0F);
         this.drawTexture(_snowman, _snowmanxxxxxx + 67, _snowmanxxxxxxx + 13 + 16, this.backgroundWidth, 132, 50, 66);
         this.drawMap(_snowman, _snowmanxxxxxx + 70, _snowmanxxxxxxx + 32, 0.34F);
         RenderSystem.popMatrix();
      } else if (_snowman) {
         this.drawTexture(_snowman, _snowmanxxxxxx + 67, _snowmanxxxxxxx + 13, this.backgroundWidth, 0, 66, 66);
         this.drawMap(_snowman, _snowmanxxxxxx + 71, _snowmanxxxxxxx + 17, 0.45F);
         this.client.getTextureManager().bindTexture(TEXTURE);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 1.0F);
         this.drawTexture(_snowman, _snowmanxxxxxx + 66, _snowmanxxxxxxx + 12, 0, this.backgroundHeight, 66, 66);
         RenderSystem.popMatrix();
      } else {
         this.drawTexture(_snowman, _snowmanxxxxxx + 67, _snowmanxxxxxxx + 13, this.backgroundWidth, 0, 66, 66);
         this.drawMap(_snowman, _snowmanxxxxxx + 71, _snowmanxxxxxxx + 17, 0.45F);
      }
   }

   private void drawMap(@Nullable MapState state, int x, int y, float size) {
      if (state != null) {
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)x, (float)y, 1.0F);
         RenderSystem.scalef(size, size, 1.0F);
         VertexConsumerProvider.Immediate _snowman = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
         this.client.gameRenderer.getMapRenderer().draw(new MatrixStack(), _snowman, state, true, 15728880);
         _snowman.draw();
         RenderSystem.popMatrix();
      }
   }
}
