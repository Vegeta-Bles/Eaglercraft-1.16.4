package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class InventoryScreen extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
   private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
   private float mouseX;
   private float mouseY;
   private final RecipeBookWidget recipeBook = new RecipeBookWidget();
   private boolean open;
   private boolean narrow;
   private boolean mouseDown;

   public InventoryScreen(PlayerEntity player) {
      super(player.playerScreenHandler, player.inventory, new TranslatableText("container.crafting"));
      this.passEvents = true;
      this.titleX = 97;
   }

   @Override
   public void tick() {
      if (this.client.interactionManager.hasCreativeInventory()) {
         this.client.openScreen(new CreativeInventoryScreen(this.client.player));
      } else {
         this.recipeBook.update();
      }
   }

   @Override
   protected void init() {
      if (this.client.interactionManager.hasCreativeInventory()) {
         this.client.openScreen(new CreativeInventoryScreen(this.client.player));
      } else {
         super.init();
         this.narrow = this.width < 379;
         this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
         this.open = true;
         this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
         this.children.add(this.recipeBook);
         this.setInitialFocus(this.recipeBook);
         this.addButton(new TexturedButtonWidget(this.x + 104, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, _snowman -> {
            this.recipeBook.reset(this.narrow);
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
            ((TexturedButtonWidget)_snowman).setPos(this.x + 104, this.height / 2 - 22);
            this.mouseDown = true;
         }));
      }
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      this.drawStatusEffects = !this.recipeBook.isOpen();
      if (this.recipeBook.isOpen() && this.narrow) {
         this.drawBackground(matrices, delta, mouseX, mouseY);
         this.recipeBook.render(matrices, mouseX, mouseY, delta);
      } else {
         this.recipeBook.render(matrices, mouseX, mouseY, delta);
         super.render(matrices, mouseX, mouseY, delta);
         this.recipeBook.drawGhostSlots(matrices, this.x, this.y, false, delta);
      }

      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
      this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);
      this.mouseX = (float)mouseX;
      this.mouseY = (float)mouseY;
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
      int _snowman = this.x;
      int _snowmanx = this.y;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, this.backgroundWidth, this.backgroundHeight);
      drawEntity(_snowman + 51, _snowmanx + 75, 30, (float)(_snowman + 51) - this.mouseX, (float)(_snowmanx + 75 - 50) - this.mouseY, this.client.player);
   }

   public static void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
      float _snowman = (float)Math.atan((double)(mouseX / 40.0F));
      float _snowmanx = (float)Math.atan((double)(mouseY / 40.0F));
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)x, (float)y, 1050.0F);
      RenderSystem.scalef(1.0F, 1.0F, -1.0F);
      MatrixStack _snowmanxx = new MatrixStack();
      _snowmanxx.translate(0.0, 0.0, 1000.0);
      _snowmanxx.scale((float)size, (float)size, (float)size);
      Quaternion _snowmanxxx = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
      Quaternion _snowmanxxxx = Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanx * 20.0F);
      _snowmanxxx.hamiltonProduct(_snowmanxxxx);
      _snowmanxx.multiply(_snowmanxxx);
      float _snowmanxxxxx = entity.bodyYaw;
      float _snowmanxxxxxx = entity.yaw;
      float _snowmanxxxxxxx = entity.pitch;
      float _snowmanxxxxxxxx = entity.prevHeadYaw;
      float _snowmanxxxxxxxxx = entity.headYaw;
      entity.bodyYaw = 180.0F + _snowman * 20.0F;
      entity.yaw = 180.0F + _snowman * 40.0F;
      entity.pitch = -_snowmanx * 20.0F;
      entity.headYaw = entity.yaw;
      entity.prevHeadYaw = entity.yaw;
      EntityRenderDispatcher _snowmanxxxxxxxxxx = MinecraftClient.getInstance().getEntityRenderDispatcher();
      _snowmanxxxx.conjugate();
      _snowmanxxxxxxxxxx.setRotation(_snowmanxxxx);
      _snowmanxxxxxxxxxx.setRenderShadows(false);
      VertexConsumerProvider.Immediate _snowmanxxxxxxxxxxx = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
      RenderSystem.runAsFancy(() -> _snowman.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, _snowman, _snowman, 15728880));
      _snowmanxxxxxxxxxxx.draw();
      _snowmanxxxxxxxxxx.setRenderShadows(true);
      entity.bodyYaw = _snowmanxxxxx;
      entity.yaw = _snowmanxxxxxx;
      entity.pitch = _snowmanxxxxxxx;
      entity.prevHeadYaw = _snowmanxxxxxxxx;
      entity.headYaw = _snowmanxxxxxxxxx;
      RenderSystem.popMatrix();
   }

   @Override
   protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX, double pointY) {
      return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(xPosition, yPosition, width, height, pointX, pointY);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
         this.setFocused(this.recipeBook);
         return true;
      } else {
         return this.narrow && this.recipeBook.isOpen() ? false : super.mouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.mouseDown) {
         this.mouseDown = false;
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   @Override
   protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
      boolean _snowman = mouseX < (double)left
         || mouseY < (double)top
         || mouseX >= (double)(left + this.backgroundWidth)
         || mouseY >= (double)(top + this.backgroundHeight);
      return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && _snowman;
   }

   @Override
   protected void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType) {
      super.onMouseClick(slot, invSlot, clickData, actionType);
      this.recipeBook.slotClicked(slot);
   }

   @Override
   public void refreshRecipeBook() {
      this.recipeBook.refresh();
   }

   @Override
   public void removed() {
      if (this.open) {
         this.recipeBook.close();
      }

      super.removed();
   }

   @Override
   public RecipeBookWidget getRecipeBookWidget() {
      return this.recipeBook;
   }
}
